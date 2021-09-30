package com.mclich.epamproject.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;
import com.mclich.epamproject.Constants;
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
		boolean loginNotExist=!uDAO.getAllLogins().contains(req.getParameter("login"));
		boolean emailNotExist=!uDAO.getAllEmails().contains(req.getParameter("email"));
		if(loginNotExist&&emailNotExist)
		{
			User toUpdate=new User(req.getParameter("login"), DigestUtils.sha256Hex(req.getParameter("password")), req.getParameter("email"), req.getParameter("firstName"), req.getParameter("lastName"), req.getParameter("gender").equals("male"));
			uDAO.update(uDAO.getId((User)req.getSession().getAttribute("user")), toUpdate);
			Constants.LOGGER.info("Edited: "+toUpdate.toString());
			req.getSession().setAttribute("user", toUpdate);
			req.getSession().setAttribute("updated", "User data was updated successfully");
			res.sendRedirect("profile");
		}
		else
		{
			if(!loginNotExist&&!emailNotExist) req.getSession().setAttribute("existingLE", "Such login and email already exists");
			else if(!loginNotExist) req.getSession().setAttribute("existingL", "Such login already exists");
			else if(!emailNotExist) req.getSession().setAttribute("existingE", "Such email already exists");
			req.getRequestDispatcher("content/pages/options/edit-profile.jsp").forward(req, res);
		}
	}
}