package com.mclich.epamproject.servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mclich.epamproject.Constants;
import com.mclich.epamproject.dao.mysql.CategoryDAO;
import com.mclich.epamproject.dao.mysql.ProductDAO;
import com.mclich.epamproject.entity.Category;
import com.mclich.epamproject.entity.Product;
import com.mclich.epamproject.entity.User;
import com.mclich.epamproject.entity.User.Role;
import com.mclich.epamproject.exception.NoRightsException;

@WebServlet("/options")
@SuppressWarnings("serial")
public class OptionsServlet extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		String action=req.getParameter("action");
		if(action==null) Constants.errorRedirect(req, res, new NoRightsException("No action has been provided"), false);
		int productId=-1;
		if(req.getParameterMap().keySet().contains("product")) productId=Integer.parseInt(req.getParameter("product"));
		if(action.equals("add"))
		{
			if(productId!=-1) res.sendRedirect("cart?add="+productId);
			else Constants.errorRedirect(req, res, new NoRightsException("No product ID has been provided"), false);
		}
		else
		{
			List<Category> categories=CategoryDAO.getInstance().getAll();
			req.setAttribute("categories", categories);
			boolean noAccess=false;
			Object attr=req.getSession().getAttribute("user");
			if(attr!=null&&attr instanceof User)
			{
				User user=(User)attr;
				if(!user.getRoles().contains(Role.ADMIN)) noAccess=true;
			}
			else noAccess=true;
			if(action.equals("create"))
			{
				if(noAccess) Constants.errorRedirect(req, res, new NoRightsException(), false);
				else req.getRequestDispatcher("content/pages/options/create-product.jsp").forward(req, res);
			}
			else if(productId==-1) Constants.errorRedirect(req, res, new NoRightsException("No product ID has been provided"), false);
			else if(noAccess) Constants.errorRedirect(req, res, new NoRightsException(), false);
			else if(action.equals("edit"))
			{
				Product product=ProductDAO.getInstance().get(productId);
				req.setAttribute("currProduct", product);
				req.getRequestDispatcher("content/pages/options/edit-product.jsp").forward(req, res);
			}
			else if(action.equals("delete"))
			{
				ProductDAO pDAO=ProductDAO.getInstance();
				pDAO.delete(pDAO.get(productId));
				req.getSession().setAttribute("deleteMsg", "Product was deleted successfully");
				res.sendRedirect("catalog");
			}
		}
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		String action=req.getParameter("action");
		if(action.equals("create"))
		{
			if(req.getParameter("category").equals("no-option"))
			{
				req.getSession().setAttribute("noCategory", "Category has not been selected");
				res.sendRedirect("options?action=create");
			}
			else
			{
				Product product=new Product(req.getParameter("name"), Float.parseFloat(req.getParameter("price")), new Category(req.getParameter("category")));
				ProductDAO.getInstance().create(product);
				Constants.LOGGER.info("Created: "+product.toString());
				req.getSession().setAttribute("createMsg", "Product was created successfully");
				res.sendRedirect("catalog");
			}
		}
		else if(action.equals("edit"))
		{
			int currProductId=Integer.parseInt(req.getParameter("product"));
			Product upd=new Product(req.getParameter("name"), Float.parseFloat(req.getParameter("price")), new Category(req.getParameter("category")));
			Product tmp=ProductDAO.getInstance().get(currProductId);
			if(upd.getName().equals(tmp.getName())&&upd.getPrice()==tmp.getPrice()&&upd.getCategory().getName().equals(tmp.getCategory().getName()))
			{
				req.getSession().setAttribute("noChanges", "No changes have been provided");
				res.sendRedirect("options?action=edit&product="+currProductId);
			}
			else
			{
				ProductDAO.getInstance().update(currProductId, upd);
				Constants.LOGGER.info("Edited: "+upd.toString());
				req.getSession().setAttribute("editMsg", "Product was edited successfully");
				res.sendRedirect("catalog");
			}
		}
	}
}