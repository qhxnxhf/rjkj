package com.rjkj.web.interceptor;

import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;



public class CustomWebInterceptor implements WebRequestInterceptor{

	private Logger logger = Logger.getLogger(this.getClass());
	
	//@Resource(name="SessionService")
	//private SessionService sessionService;
	
	/**
	 * 请求方法之前的处理   controller里的方法调用之前
	 */
	@Override
	public void preHandle(WebRequest request) throws Exception {
		
		ServletWebRequest req = (ServletWebRequest)request;
		String url = req.getRequest().getRequestURL().toString();
		if (url.indexOf("resources") == -1) {
			StringBuilder sb = new StringBuilder(128);
			Map<String, String[]> paramMap = request.getParameterMap();
			// DispatcherServletWebRequest
			/**
			DzjyTbMerchantInfo user = sessionService.getCurUser();
			
			sb.append("[").append(
					null == user ? IpUtils.getRealRemoteAddr(req.getRequest()) : (null == user.getUsername() ? user
							.getUnitId() : user.getUsername())).append("] URI:").append(url).append(" 请求参数：").append(
					StringUtil.toString(paramMap));
			logger.info(sb);
			**/
			
			
			for (String key : paramMap.keySet()) {
				if ("logistics_interface".equals(key) || "msg_type".equals(key)) {
					continue;
				}
				String[] v = paramMap.get(key);
				if (!isValid(v)) {
					throw new Exception("异常参数：" + Arrays.toString(v));
				}
			}
		}
	}

	/**
	 * 请求方法之后的处理   controller里的方法调用之后
	 */
	@Override
	public void postHandle(WebRequest request, ModelMap model) throws Exception {
//		Log4jNestedDiagnosticContextInterceptor
// OpenEntityManagerInViewInterceptor
//OpenPersistenceManagerInViewInterceptor
//OpenSessionInViewInterceptor
// OpenSessionInViewInterceptor

	}
	
	/**
	 * 整个请求完成之后的处理
	 */
	@Override
	public void afterCompletion(WebRequest request, Exception ex)
			throws Exception {
//		不管用啊。。。。。   
//		if(request instanceof DispatcherServletWebRequest){
//			DispatcherServletWebRequest req = (DispatcherServletWebRequest)request;
//			String uri = req.getRequest().getRequestURI();
//			if(-1 != uri.indexOf("error.html") || -1 != uri.indexOf("404.html")){
//				((DispatcherServletWebRequest)request).getResponse().setStatus(200);
//			}
//		}
	}
	
	
	//过滤 ‘
	//ORACLE 注解 --  /**/
	//关键字过滤 update ,delete 
	//static String reg = "(\\b(select|update|delete|insert|trancate|char|into|substr|ascii|declare|exec|master|drop|execute)\\b)";
	static String reg ="(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
		+ "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute|DBMS_XMLQUERY.GETXML|DBMS_XMLQUERY.NEWCONTEXT)\\b)";

	static Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);

	/***************************************************************************
	 * 参数校验
	 * 
	 * @param str
	 */
	public static boolean isValid(Object obj) {
		if(obj != null){
			String s = "";
			if (obj instanceof Object[]) {
				s = Arrays.toString((Object[]) obj);
			} else {
				s = obj.toString();
			}
			s = URLDecoder.decode(s.replaceAll("%", "%25"));
			if (sqlPattern.matcher(s).find()) {
				return false;
			}
		}
		return true;
	}
	
	
}
