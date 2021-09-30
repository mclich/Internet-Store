package com.mclich.epamproject.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import com.mclich.epamproject.Constants;
import com.mclich.epamproject.entity.User.Role;

@WebListener
public class AppConfig implements ServletContextListener
{	
	@Override
	public void contextInitialized(ServletContextEvent event)
	{
		//Constants.LOGGER.info("Application initialized, config location: "+((LoggerContext)LogManager.getContext(false)).getConfiguration().getConfigurationSource().getLocation());
		Constants.LOGGER.info("Application initialized");
		final ServletContext sc=event.getServletContext();
		sc.setAttribute("lang", "en");
		sc.setAttribute("dateFormatter", Constants.DATE_FORMAT);
		sc.setAttribute("floatFormatter", Constants.FLOAT_FORMAT);
		sc.setAttribute("priceFormatter", Constants.PRICE_FORMAT);
		sc.setAttribute("client", Role.CLIENT);
		sc.setAttribute("admin", Role.ADMIN);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event)
	{
		Constants.LOGGER.info("Application closed");
		final ServletContext sc=event.getServletContext();
		sc.removeAttribute("lang");
		sc.removeAttribute("dateFormatter");
		sc.removeAttribute("floatFormatter");
		sc.removeAttribute("priceFormatter");
		sc.removeAttribute("client");
		sc.removeAttribute("admin");
	}
}