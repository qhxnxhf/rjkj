package com.rjkj.web.sys;


import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjkj.web.interceptor.CsrfTokenUtils;


@Controller
@RequestMapping("/csrf")
public class CsrfTokenControl {
	
	@RequestMapping(value="/token", method={RequestMethod.GET})
	public @ResponseBody String getImgsByType(HttpServletRequest request){
		JSONObject json = new JSONObject();
		String token= CsrfTokenUtils.getTokenForSession(request.getSession());
		request.setAttribute(CsrfTokenUtils.CSRF_TOKEN_NAME, token);
		json.put("token", token);
		String result=json.toString();
		return result;
	    
	}

}
