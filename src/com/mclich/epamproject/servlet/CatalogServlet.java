package com.mclich.epamproject.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mclich.epamproject.Constants;
import com.mclich.epamproject.dao.mysql.ProductDAO;
import com.mclich.epamproject.exception.CNAException;
import com.mclich.epamproject.exception.TransactionException.GetException;

@WebServlet("/catalog")
@SuppressWarnings("serial")
public class CatalogServlet extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		try
		{
			req.setAttribute("catalog", ProductDAO.getInstance().getAll());
			req.getRequestDispatcher("content/pages/catalog.jsp").forward(req, res);
		}
		catch(CNAException | GetException exc)
		{
			Constants.errorRedirect(req, res, exc, false);
		}
	}
}