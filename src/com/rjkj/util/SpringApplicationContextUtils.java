package com.rjkj.util;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author lunshipeng
 *
 */
public class SpringApplicationContextUtils {
	private static final Logger logger = Logger.getLogger(SpringApplicationContextUtils.class);
	private static WebApplicationContext webApplicationContext = null;
	
	public static WebApplicationContext getWebApplicationContext(){
		return webApplicationContext;
	}
	
	public static void setWebApplicationContext(WebApplicationContext webApplicationContext){
		if(null == SpringApplicationContextUtils.webApplicationContext){
			SpringApplicationContextUtils.webApplicationContext = webApplicationContext;
		}else{
			logger.error("重复出初始化WebApplicationContext。。。");
			System.exit(-1);
		}
	}
	
	public static <T> T getBean(String name,Class<T> cla){
		return (T)webApplicationContext.getBean(name);
	}
	
}
