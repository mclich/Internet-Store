package com.mclich.epamproject.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mclich.epamproject.Constants;
import com.mclich.epamproject.dao.mysql.OrderDAO;
import com.mclich.epamproject.dao.mysql.ProductDAO;
import com.mclich.epamproject.entity.Order;
import com.mclich.epamproject.entity.Product;
import com.mclich.epamproject.entity.User;
import com.mclich.epamproject.exception.CNAException;
import com.mclich.epamproject.exception.TransactionException.CreateException;
import com.mclich.epamproject.exception.TransactionException.GetException;

@WebServlet("/cart")
@SuppressWarnings("serial")
public class CartServlet extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		if(req.getParameterMap().size()==1)
		{
			int id=Integer.parseInt(req.getParameter("add")!=null?req.getParameter("add"):req.getParameter("remove")!=null?req.getParameter("remove"):req.getParameter("delete"));
			@SuppressWarnings("unchecked")
			Map<Product, Integer> cartProducts=(HashMap<Product, Integer>)req.getSession().getAttribute("cartProducts");
			if(cartProducts!=null)
			{
				if(req.getParameterMap().containsKey("add"))
				{
					Product product=null;
					Product tmp=null;
					try
					{
						product=ProductDAO.getInstance().get(id);
					}
					catch(CNAException | GetException exc)
					{
						Constants.errorRedirect(req, res, exc, false);
					}
					for(Product p:cartProducts.keySet())
					{
						if(product.getId()==p.getId()&&product.getName().equals(p.getName()))
						{
							tmp=p;
							break;
						}
					}
					if(tmp!=null)
					{
						if(cartProducts.get(tmp)<100) cartProducts.put(tmp, cartProducts.get(tmp)+1);
					}
					else cartProducts.put(product, 1);
				}
				else if(req.getParameterMap().containsKey("remove"))
				{
					for(Product p:cartProducts.keySet())
					{
						if(p.getId()==id&&cartProducts.get(p)>1) cartProducts.put(p, cartProducts.get(p)-1);
					}
				}
				else if(req.getParameterMap().containsKey("delete"))
				{
					for(Product p:cartProducts.keySet())
					{
						if(p.getId()==id) cartProducts.remove(p);
					}
				}
			}
		}
		req.getRequestDispatcher("content/pages/cart.jsp").forward(req, res);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		@SuppressWarnings("unchecked")
		Map<Product, Integer> cartProducts=(HashMap<Product, Integer>)req.getSession().getAttribute("cartProducts");
		Map<Product, Integer> orderProducts=new HashMap<>(cartProducts);
		User user=(User)req.getSession().getAttribute("user");
		User copy=new User(user.getLogin(), user.getPassword(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getGender());
		Order order=new Order(copy, orderProducts);
		copy.addOrder(order);
		try
		{
			OrderDAO.getInstance().create(order);
		}
		catch(CNAException | CreateException exc)
		{
			Constants.errorRedirect(req, res, exc, false);
		}
		cartProducts.clear();
		req.getSession().setAttribute("purchase", "Order was created successfully, status: "+order.getStatus().toString());
		res.sendRedirect("cart");
	}
}