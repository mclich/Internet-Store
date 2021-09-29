package com.mclich.epamproject.listener;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import com.mclich.epamproject.entity.User.Role;

@WebListener
public class AppConfig implements ServletContextListener
{	
	@Override
	public void contextInitialized(ServletContextEvent event)
	{
		final ServletContext sc=event.getServletContext();
		DecimalFormatSymbols separator=new DecimalFormatSymbols();
		separator.setDecimalSeparator('.');
		//sc.setAttribute("priceFormatter", new DecimalFormat("#,###,###,###.00 ₴", separator));
		DecimalFormat df=new DecimalFormat("#.00", separator);
		df.setRoundingMode(RoundingMode.UP);
		sc.setAttribute("dateFormatter", DateTimeFormatter.ofPattern("dd.MM.yyyy"));
		sc.setAttribute("priceFormatter", df);
		sc.setAttribute("client", Role.CLIENT);
		sc.setAttribute("admin", Role.ADMIN);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event)
	{
		final ServletContext sc=event.getServletContext();
		sc.removeAttribute("dateFormatter");
		sc.removeAttribute("priceFormatter");
		sc.removeAttribute("client");
		sc.removeAttribute("admin");
	}
}