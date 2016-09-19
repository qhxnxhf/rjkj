package com.rjkj.security.listener;

import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpSessionEvent;

import org.apache.log4j.Logger;
import org.springframework.security.web.session.HttpSessionEventPublisher;

public class CustomSessionListener extends HttpSessionEventPublisher{

	private static final Logger logger = Logger.getLogger(CustomSessionListener.class);
	private static AtomicLong COUNT = new AtomicLong(0);
	
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		super.sessionCreated(event);
		logger.info("创建了一个SESSION:"+event.getSession().getId()+";当前SESSION数:"+(COUNT.incrementAndGet()));
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		super.sessionDestroyed(event);
		logger.info("销毁一个SESSION:"+event.getSession().getId()+";当前SESSION数:"+(COUNT.decrementAndGet()));
	}

}
