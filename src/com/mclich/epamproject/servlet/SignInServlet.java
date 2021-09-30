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
import com.mclich.epamproject.exception.CNAException;
import com.mclich.epamproject.exception.TransactionException.GetException;

@WebServlet(urlPatterns={"/sign-in", "/logout"})
@SuppressWarnings("serial")
public class SignInServlet extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		if(req.getServletPath().equals("/logout"))
		{
			if(req.getSession().getAttribute("user")!=null)
			{
				req.getSession().setAttribute("logMsg", "You have successfully logged out");
				Constants.LOGGER.info("Signed out: "+req.getSession().getAttribute("user").toString());
				req.getSession().setAttribute("user", null);
			}
		}
		req.getRequestDispatcher("content/pages/sign-in.jsp").forward(req, res);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		User user=null;
		if(req.getServletPath().equals("/sign-in"))
		{
			try
			{
				user=UserDAO.getInstance().get(req.getParameter("login"), req.getParameter("password"));
			}
			catch(CNAException | GetException exc)
			{
				String msg=exc.getMessage();
				if(msg!=null)
				{
					if(msg.equals("Such user does not exist")) req.getSession().setAttribute("userNotExist", exc.getMessage()); 
					if(msg.equals("Wrong password, try again")) req.getSession().setAttribute("wrongPw", exc.getMessage()); 
					res.sendRedirect("sign-in");
				}
				else Constants.errorRedirect(req, res, exc, false);
			}
		}
		if(user!=null)
		{
			Constants.LOGGER.info("Signed in: "+user.toString());
			req.getSession().setAttribute("user", user);
			req.getSession().setAttribute("loggedAs", user.getFirstName()+" "+user.getLastName());
			res.sendRedirect("../InternetStore");
		}
	}
}