package com.rjkj.service.imp;

import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.rjkj.dao.DicDao;
import com.rjkj.entities.Dic;
import com.rjkj.service.PropertiesService;



@Service("PropertiesService")
public class PropertiesServiceImpl implements PropertiesService {
	@Resource(name = "DicDao")
	private DicDao zdsjDao;
	/**
	 * 系统属性
	 */
	private static Properties sysProperties;
	private Object lock = new Object();
	private volatile static PropertiesService instance;
	private static ApplicationContext applicationContext;
	public static PropertiesService getInstance() {
		if (null == instance)
			instance = (PropertiesService) getSpringContext().getBean("PropertiesService");
		return instance;
	}
	private void initProperties() {
		List<Dic> props = zdsjDao.getAllProperties(2);
		sysProperties= new Properties();
		for (Dic m : props) {
			sysProperties.setProperty(m.getDicKey(), m.getValue1());
		}
	}
	/**
	 * 属性文件以及属性表的读取服务
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dzsw.service.bur.system.IApplicationService#getProperty(java.lang
	 * .String)
	 */
	@Override
	public String getProperty(String key) {
		if (null == sysProperties) {
			synchronized (lock) {
				this.initProperties();
			}
		}
		return getPropertyValue(key);
	}
	private String getPropertyValue(String key) {
		String value = PropertiesServiceImpl.sysProperties.getProperty(key);
		if (StringUtils.isEmpty(value)) {
			value = zdsjDao.getPropertiesByKey(key);
		}
		// 处理空格
		if (!StringUtils.isEmpty(value))
			value = value.trim();
		return value;
	}
	/**
	 * @description 获得spring的上下文的服务
	 * 
	 * @author Eric (Oct 11, 2011)
	 * 
	 * @version Change History:
	 * @version <1> Oct 11, 2011 Eric Modification purpose.
	 */
	public static ApplicationContext getSpringContext() {
		if (applicationContext == null) {
			return null;
		} else
			return applicationContext;
	}
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	public static void setApplicationContext(ApplicationContext applicationContext) {
		PropertiesServiceImpl.applicationContext = applicationContext;
	}

	
}
