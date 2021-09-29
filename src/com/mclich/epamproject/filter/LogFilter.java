package com.mclich.epamproject.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

@WebFilter(urlPatterns={"/*"})
public class LogFilter implements Filter 
{
	private static final Logger LOGGER=LogManager.getLogger();
	
	public static Logger getLogger()
	{
		return LogFilter.LOGGER;
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
		LogFilter.LOGGER.info("Logger created, config location: "+((LoggerContext)LogManager.getContext(false)).getConfiguration().getConfigurationSource().getLocation());
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException
	{
		LogFilter.LOGGER.info(req.getLocale());
		chain.doFilter(req, res);
	}
}