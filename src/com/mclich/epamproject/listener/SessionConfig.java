package com.mclich.epamproject.listener;

import java.util.HashMap;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.mclich.epamproject.Constants;
import com.mclich.epamproject.entity.Product;

@WebListener
public class SessionConfig implements HttpSessionListener
{
	@Override
	public void sessionCreated(HttpSessionEvent event)
	{
		Constants.LOGGER.info("Session created");
		event.getSession().setAttribute("cartProducts", new HashMap<Product, Integer>());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event)
	{
		Constants.LOGGER.info("Session closed");
		event.getSession().removeAttribute("cartProducts");
	}
}