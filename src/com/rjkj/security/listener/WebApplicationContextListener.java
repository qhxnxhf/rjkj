package com.rjkj.security.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.rjkj.util.SpringApplicationContextUtils;



public class WebApplicationContextListener implements ServletContextListener{
	private Logger logger = Logger.getLogger(this.getClass());
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try{
			WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(arg0.getServletContext());
			SpringApplicationContextUtils.setWebApplicationContext(wac);
		}catch(IllegalStateException e){
			logger.error("获取WebApplicationContext错误！", e);
			System.exit(-1);
		}
	}

}
