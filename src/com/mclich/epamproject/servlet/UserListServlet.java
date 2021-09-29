package com.mclich.epamproject.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mclich.epamproject.Constants;
import com.mclich.epamproject.dao.mysql.UserDAO;
import com.mclich.epamproject.entity.User;
import com.mclich.epamproject.entity.User.Role;
import com.mclich.epamproject.exception.CNAException;
import com.mclich.epamproject.exception.NoRightsException;
import com.mclich.epamproject.exception.TransactionException.GetException;

@WebServlet("/user-list")
@SuppressWarnings("serial")
public class UserListServlet extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		boolean noAccess=false;
		Object attr=req.getSession().getAttribute("user");
		if(attr!=null&&attr instanceof User)
		{
			User user=(User)attr;
			if(!user.getRoles().contains(Role.ADMIN)) noAccess=true;
		}
		else noAccess=true;
		if(noAccess) Constants.errorRedirect(req, res, new NoRightsException("You have no rights to enter that page"), false);
		else try
		{
			req.setAttribute("userList", UserDAO.getInstance().getAll());
			req.getRequestDispatcher("content/pages/user-list.jsp").forward(req, res);
		}
		catch(CNAException | GetException exc)
		{
			Constants.errorRedirect(req, res, exc, false);
		}
	}
}