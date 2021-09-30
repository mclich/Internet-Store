package com.mclich.epamproject.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mclich.epamproject.Constants;
import com.mclich.epamproject.dao.mysql.ProductDAO;

@WebServlet("/catalog")
@SuppressWarnings("serial")
public class CatalogServlet extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		Constants.LOGGER.warn("Start getting products...");
		req.setAttribute("catalog", ProductDAO.getInstance().getAll());
		Constants.LOGGER.warn("Getting products finished");
		req.getRequestDispatcher("content/pages/catalog.jsp").forward(req, res);
	}
}