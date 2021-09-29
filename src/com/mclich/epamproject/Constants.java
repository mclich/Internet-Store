package com.mclich.epamproject;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Constants
{
	public static void errorRedirect(HttpServletRequest req, HttpServletResponse res, Exception exc) throws ServletException, IOException
	{
		Constants.errorRedirect(req, res, exc, true);
	}
	
	public static void errorRedirect(HttpServletRequest req, HttpServletResponse res, Exception exc, boolean included) throws ServletException, IOException
	{
		if(included) req.setAttribute(RequestDispatcher.ERROR_EXCEPTION, exc);
		else req.setAttribute("exception", exc);
		req.getRequestDispatcher("content/pages/error.jsp").forward(req, res);
	}
}