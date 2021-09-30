package com.mclich.epamproject;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.format.DateTimeFormatter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Constants
{
	public static final Logger LOGGER=LogManager.getLogger();
	public static final DateTimeFormatter DATE_FORMAT=DateTimeFormatter.ofPattern("dd.MM.yyyy");
	public static final DecimalFormat FLOAT_FORMAT=new DecimalFormat("#.00");
	public static final DecimalFormat PRICE_FORMAT=new DecimalFormat("#,###,###,###.00 ₴");
	
	static
	{
		DecimalFormatSymbols separator=new DecimalFormatSymbols();
		separator.setDecimalSeparator('.');
		Constants.FLOAT_FORMAT.setDecimalFormatSymbols(separator);
		Constants.FLOAT_FORMAT.setRoundingMode(RoundingMode.UP);
		separator=new DecimalFormatSymbols();
		separator.setGroupingSeparator(' ');
		Constants.PRICE_FORMAT.setDecimalFormatSymbols(separator);
	}
	
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