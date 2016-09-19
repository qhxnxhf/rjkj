/**
 * Copyright (c) 2008 by Hewlett-Packard Company
 * All rights reserved.
 * The information contained herein is confidential and proprietary to
 * Hewlett-Packard Company, and considered a trade secret as defined under
 * civil and criminal statutes.  Hewlett-Packard Company shall pursue its
 * civil and criminal remedies in the event of unauthorized use or
 * misappropriation of its trade secrets.  Use of this information
 * by anyone other than authorized employees of Hewlett-Packard Company is
 * granted only under a written non-disclosure agreement, expressly
 * prescribing the scope and manner of such use.
 *
 */

package com.rjkj.util;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;

/**
 * <p>Title: </p>  
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Hewlett-Packard Company</p>
 * <p>Created on 2011-9-27 上午10:32:11</p> 
 * @author Yu Lin, Jiang
 * @email yulin.jiang@hp.com
 * @version 1.0
 */
public class JsonConverter {
	private static final Logger log = Logger.getLogger(JsonConverter.class);
	
	@SuppressWarnings("unchecked")
	public static String  toJson(Object o){
		String json = "";
		//nested exception is java.lang.NoClassDefFoundError: org/apache/commons/lang/exception/NestableRuntimeException] with root cause
//		try {
//			
//			if ( o instanceof Object[] || o  instanceof List )
//				json = JSONArray.fromObject(o).toString();
//			else 
//				json = JSONObject.fromObject(o).toString();
//			log.debug(json);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		json = new Gson().toJson(o);
		
		return json;
	}
	
	@SuppressWarnings("unchecked")
	public static String toSendJsonHandleNullNumber(Object o){
		String json = "";
		try {
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerDefaultValueProcessor(Integer.class, new DefaultNumberValueProcessor());
			jsonConfig.registerDefaultValueProcessor(Short.class, new DefaultNumberValueProcessor());
			jsonConfig.registerDefaultValueProcessor(Float.class, new DefaultNumberValueProcessor());
			jsonConfig.registerDefaultValueProcessor(Long.class, new DefaultNumberValueProcessor());
			jsonConfig.registerDefaultValueProcessor(Double.class, new DefaultNumberValueProcessor());
			if ( o instanceof Object[] || o  instanceof List )
				json = JSONArray.fromObject(o,jsonConfig).toString();
			else 
				json = JSONObject.fromObject(o,jsonConfig).toString();
			log.debug(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	@SuppressWarnings("unchecked")
	public static String sendSuccess(){
		Map map = new HashMap();
		map.put("success","true");
		map.put("msg","操作成功！");
		return toJson(map);
	}
	
	@SuppressWarnings("unchecked")
	public static String sendSuccess(String msg){
		Map map = new HashMap();
		map.put("success","true");
		map.put("msg","操作成功："+msg);
		return toJson(map);
	}
	
	@SuppressWarnings("unchecked")
	public static String sendFailure(){
		Map map = new HashMap();
		map.put("success","false");
		map.put("msg","操作失败！");
		return toJson(map);
	}
	
	@SuppressWarnings("unchecked")
	public static String sendFailure(String msg){
		Map map = new HashMap();
		map.put("success","false");
		map.put("msg","操作失败："+msg);
		return toJson(map);
	}
	
	public static String toEJson(Object o){
		String json = "";
		try {
			JsonConfig jsonConfig = configJson();
			if ( o instanceof Object[] || o  instanceof List )
				json = JSONArray.fromObject(o,jsonConfig).toString();
			else 
				json = JSONObject.fromObject(o,jsonConfig).toString();
			log.debug(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}
	/**
	 * @description 注册自定义的json解析器
	 * @author Eric (Oct 21, 2011)
	 * 
	 * @version Change History:
	 * @version  <1> Oct 21, 2011 Eric  Modification purpose. 
	 */
	public static JsonConfig configJson(){
		JsonConfig jsonConfig = new JsonConfig();   
        jsonConfig.setExcludes(new String[]{"handler","hibernateLazyInitializer"});   
        jsonConfig.setIgnoreDefaultExcludes(false);   
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);   
        return jsonConfig;   
	}
	
	
	private static class DefaultNumberValueProcessor implements DefaultValueProcessor{
		@Override
		public Object getDefaultValue(Class type) {
			return "";
		}
	}
}



