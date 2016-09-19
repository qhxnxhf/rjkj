package com.rjkj.web.interceptor;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CsrfTokenUtils {

	public static final String CSRF_TOKEN_NAME = "CSRFToken";
	
	public static final String CSRF_TOKEN_HEADER_NAME = "CSRFTokenHeader";
	
	public static String getTokenForSession(HttpSession session){
		String token = null;
		synchronized (session) {
			token = (String)session.getAttribute(CSRF_TOKEN_NAME);
			if (token == null) {
				token = UUID.randomUUID().toString();
				session.setAttribute(CSRF_TOKEN_NAME, token);
			}
			
		}
		return token;
	}
	
	public static boolean validCsrfToken(HttpServletRequest request) {
		
		
			String token = request.getParameter(CSRF_TOKEN_NAME);
			if (token == null) {
				token = request.getHeader(CSRF_TOKEN_HEADER_NAME);
			}
			String sessionToken = (String) request.getSession().getAttribute(CSRF_TOKEN_NAME);
			if (sessionToken == null || token == null) {
				return false;
			}
			request.getSession().removeAttribute(CSRF_TOKEN_NAME);
			return sessionToken.equals(token);
		
	}
	
}
