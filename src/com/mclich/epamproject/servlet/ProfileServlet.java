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

@WebServlet("/profile")
@SuppressWarnings("serial")
public class ProfileServlet extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		req.getRequestDispatcher("content/pages/profile.jsp").forward(req, res);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		UserDAO uDAO=UserDAO.getInstance();
		User user=(User)req.getSession().getAttribute("user");
		int userId=uDAO.getId(user);
		boolean flag=true;
		if(req.getParameter("login")!=null)
		{
			boolean loginExist=uDAO.getAllLogins().contains(req.getParameter("login"));
			if(loginExist)
			{
				flag=false;
				req.getRequestDispatcher("content/pages/options/edit-profile.jsp").forward(req, res);
			}
			else req.getSession().setAttribute("existingL", "Such login already exists");
			uDAO.updateLogin(userId, req.getParameter("login"));
			user.setLogin(req.getParameter("login"));
		}
		else if(req.getParameter("password")!=null)
		{
			uDAO.updatePassword(userId, DigestUtils.sha256Hex(req.getParameter("password")));
			user.setPassword(DigestUtils.sha256Hex(req.getParameter("password")));
		}
		else if(req.getParameter("email")!=null)
		{
			boolean emailExist=uDAO.getAllEmails().contains(req.getParameter("email"));
			if(emailExist)
			{
				flag=false;
				req.getRequestDispatcher("content/pages/options/edit-profile.jsp").forward(req, res);
			}
			else req.getSession().setAttribute("existingE", "Such e-mail already exists");
			uDAO.updateEmail(userId, req.getParameter("email"));
			user.setEmail(req.getParameter("email"));
		}
		else if(req.getParameter("firstName")!=null)
		{
			uDAO.updateName(userId, req.getParameter("firstName"));
			user.setFirstName(req.getParameter("firstName"));
		}
		else if(req.getParameter("lastName")!=null)
		{
			uDAO.updateSurname(userId, req.getParameter("lastName"));
			user.setLastName(req.getParameter("lastName"));
		}
		else if(req.getParameter("gender")!=null)
		{
			uDAO.updateGender(userId, req.getParameter("gender").equals("male"));
			user.setGender(req.getParameter("gender").equals("male"));
		}
		req.getSession().setAttribute("updated", "User data was updated successfully");
		if(flag) res.sendRedirect("profile");
	}
}