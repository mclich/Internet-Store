package com.mclich.epamproject.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;
import com.mclich.epamproject.dao.mysql.UserDAO;
import com.mclich.epamproject.entity.User;

@WebServlet("/register")
@SuppressWarnings("serial")
public class RegisterServlet extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		req.getRequestDispatcher("content/pages/register.jsp").forward(req, res);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		UserDAO uDAO=UserDAO.getInstance();
		boolean loginNotExist=!uDAO.getAllLogins().contains(req.getParameter("login"));
		boolean emailNotExist=!uDAO.getAllEmails().contains(req.getParameter("email"));
		if(loginNotExist&&emailNotExist)
		{
			User user=new User(req.getParameter("login"), DigestUtils.sha256Hex(req.getParameter("password")), req.getParameter("email"), req.getParameter("firstName"), req.getParameter("lastName"), req.getParameter("gender").equals("male"));
			uDAO.create(user);
			req.getSession().setAttribute("user", user);
			req.getSession().setAttribute("loggedAs", "You are logged in as "+user.getFirstName()+" "+user.getLastName());
			res.sendRedirect("../InternetStore");
		}
		else
		{
			String msg=new String();
			if(!loginNotExist&&!emailNotExist) msg="Such login and email already exists";
			else if(!loginNotExist) msg="Such login already exists";
			else if(!emailNotExist) msg="Such email already exists";
			req.getSession().setAttribute("existing", msg);
			req.getRequestDispatcher("content/pages/options/edit-profile.jsp").forward(req, res);
		}
	}
}