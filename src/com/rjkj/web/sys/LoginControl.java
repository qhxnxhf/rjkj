package com.rjkj.web.sys;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.rjkj.dao.OrgDao;
import com.rjkj.dao.UserDao;
import com.rjkj.entities.User;
import com.rjkj.security.RandomValidateCode;
import com.rjkj.util.IpUtils;



@Controller
@RequestMapping("/login")
public class LoginControl {
	private static final Log log = LogFactory.getLog(LoginControl.class);  
	//protected ErrMg error; 
	 
	@Autowired
	private UserDao userService;
	
	@Autowired
	private OrgDao orgService;
	
	private static final String LOGIN = "login/login";
	private static final String REGIST = "login/regist";
	//private static final String seccess = "management/examuser/cjgl/search";
	
	
	
	@RequestMapping(value="/login", method={RequestMethod.GET, RequestMethod.POST})
	public String login(Map<String, Object> map) {
		return LOGIN;
	}
	
	
	@RequestMapping(value="/regist", method={RequestMethod.GET, RequestMethod.POST})
	public String regist(Map<String, Object> map) {
		return REGIST;
	}
	
	
	@RequestMapping(value="/login_submit", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String loginSubmit(HttpServletRequest request) throws ParseException  {
		 JSONObject obj = new JSONObject();
		 
		String loginName=request.getParameter("username");
		String loginPwd=request.getParameter("password");
		//String remember=(String)request.getParameter("rememberMe");
		String grantIp=IpUtils.getRealRemoteAddr(request);
		String userid = null;  
		AuthenticationToken token = new UsernamePasswordToken(loginName,loginPwd);  
	    Subject currentUser = SecurityUtils.getSubject();  
	     
	     try {  
	    	 currentUser.login(token);  
	            userid =currentUser.getPrincipal().toString();  
	            User loginuser=this.userService.findById(User.class,Long.valueOf(userid));
	            loginuser.setStatus("3");
	            loginuser.setLoginTime(new Date());
	            
	            loginuser.setAllowIp(grantIp);
	            userService.update(loginuser);
	            HttpSession session = request.getSession(true);  
	            session.setAttribute("LoginUser", loginuser);  
	            obj.put("status", "true");
				obj.put("message", "登录成功");
	           
	            
	      } catch (UnknownAccountException ae) {  
	            log.info("用户认证失败:" + "username wasn't in the system.");  
	            //error.setErrorMessage("username wasn't in the system.");  
	            obj.put("status", "false");
				obj.put("message", "用户认证失败:用户账号不存在！");
	      } catch (IncorrectCredentialsException ae) {  
	            log.info("用户认证失败:" + "password didn't match.");  
	            //error.setErrorMessage("password didn't match."); 
	            obj.put("status", "false");
				obj.put("message", "用户认证失败:密码错误！");
	      } catch (LockedAccountException ae) {  
	            log.info("用户认证失败:" + "account for that username is locked - can't login.");  
	            //error.setErrorMessage("account for that username is locked - can't login."); 
	            obj.put("status", "false");
				obj.put("message", "用户认证失败:用户账号被禁用！");
	      } catch (AuthenticationException ae) {  
	            log.info("用户认证失败:" + "unexpected condition.");  
	            //error.setErrorMessage("unexpected condition."); 
	            obj.put("status", "false");
				obj.put("message", "用户认证失败！");
	      } 
	          
	     // return this.doReturnError(request, response, error, "error.jsp");  
	     //return buffer.toString();
	     return obj.toString();//.toJSONString();
	}

	@RequestMapping(value="/login_submit1", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String loginSubmit1(HttpServletRequest request) throws ParseException  {
		 JSONObject obj = new JSONObject();
		 
		String loginName=request.getParameter("username");
		String loginPwd=request.getParameter("password");
		String kaptcha=request.getParameter("kaptcha");
		String remember=request.getParameter("rememberMe");
		String grantIp=IpUtils.getRealRemoteAddr(request);
		
		String kaptchaExpected = (String)request.getSession().getAttribute(RandomValidateCode.RANDOMCODEKEY);
		request.getSession().setAttribute(RandomValidateCode.RANDOMCODEKEY,"");
		request.getSession().removeAttribute(RandomValidateCode.RANDOMCODEKEY);
		
		if(kaptcha ==null||!kaptcha.equalsIgnoreCase(kaptchaExpected)){ 
			obj.put("status", "false");
			obj.put("message", "验证码错误！");
			
			return obj.toString();//.toJSONString();
		}
		
		
		 String userid = null;  
		 AuthenticationToken token = new UsernamePasswordToken(loginName,loginPwd,Boolean.valueOf(remember));  
	     Subject currentUser = SecurityUtils.getSubject();  
	     try {  
	            currentUser.login(token);  
	            userid =currentUser.getPrincipal().toString();  
	            User loginuser=this.userService.findById(User.class,Long.valueOf(userid));
	            loginuser.setStatus("3");
	            loginuser.setLoginTime(new Date());
	            
	            loginuser.setAllowIp(grantIp);
	            userService.update(loginuser);
	            HttpSession session = request.getSession(true);  
	            session.setAttribute("LoginUser", loginuser);  
	            obj.put("status", "true");
				obj.put("message", "登录成功");
				
	            
	      } catch (UnknownAccountException ae) {  
	            log.info("用户认证失败:" + "username wasn't in the system.");  
	            //error.setErrorMessage("username wasn't in the system.");  
	            obj.put("status", "false");
				obj.put("message", "账号不存在！");
	      } catch (IncorrectCredentialsException ae) {  
	            log.info("用户认证失败:" + "password didn't match.");  
	            //error.setErrorMessage("password didn't match."); 
	            obj.put("status", "false");
				obj.put("message", "密码错误！");
	      } catch (LockedAccountException ae) {  
	            log.info("用户认证失败:" + "account for that username is locked - can't login.");  
	            //error.setErrorMessage("account for that username is locked - can't login."); 
	            obj.put("status", "false");
				obj.put("message", "账号禁用！");
	      } catch (AuthenticationException ae) {  
	            log.info("用户认证失败:" + "unexpected condition.");  
	            //error.setErrorMessage("unexpected condition."); 
	            obj.put("status", "false");
				obj.put("message", "认证失败！");
	      } 
	          
	     // return this.doReturnError(request, response, error, "error.jsp");  
	     //return buffer.toString();
	     return obj.toString();//.toJSONString();
	}
	
	
	
	@RequestMapping(value="/logout", method={RequestMethod.GET, RequestMethod.POST})
	public  String logout(HttpServletRequest request, HttpServletResponse response) throws ParseException  {
		// User loginuser=(User) request.getSession().getAttribute("LoginUser");
	     Subject subject = SecurityUtils.getSubject();  
		 if (subject.isAuthenticated()) {  
		     subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存  
		 }
	    	 
		 //String home=request.getContextPath();
		 //request.getSession().invalidate();
		 //response.sendRedirect(home);
	     return "redirect:/";  
	   
	}
	
	

	/**
	@RequestMapping(value="/regist_submit", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String registSubmit(HttpServletRequest request) throws ParseException  {
		
		JSONObject obj = new JSONObject();
		String captcha=(String)request.getParameter("kaptcha");
		
		String kaptchaExpected = (String)request.getSession().getAttribute(RandomValidateCode.RANDOMCODEKEY);
		
		if(captcha ==null||!captcha.equalsIgnoreCase(kaptchaExpected)){ 
			obj.put("statusCode", "false");
			obj.put("message", "验证码错误！");
			return obj.toString();//.toJSONString();
		}
		
		
		String loginName=(String)request.getParameter("username");
		String loginPwd=(String)request.getParameter("password");
		String userName=(String)request.getParameter("realname");
		String sex=(String)request.getParameter("sex");
		String phone=(String)request.getParameter("phone");
		String email=(String)request.getParameter("email");
		
		
		
		QyzxQhOrg org=orgService.findById(QyzxQhOrg.class, 1L);
		User user=new User();
		user.setOrg(org);
		user.setUserName(userName);
		user.setPlainPassword(loginPwd);
		user.setLoginName(loginName);
		user.setCreateTime(new Date());
		user.setStatus("2");
		if(sex.equals("true")){
			user.setSex("1");
		}else{
			user.setSex("2");
			
		}
		
		user.setMobile(phone);
		user.setEmail(email);
		
		String result="";
			
		try {
			this.userService.saveUser(user);
			//result="{'status':'true','message':'注册成功！'}";
			obj.put("status", "true");
			obj.put("message", "注册成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//result="{'status':'false','message':'"+e+"'}";
			
			obj.put("status", "false");
			obj.put("message", e);
		}
		
		result=obj.toString();//.toJSONString();
	
		return result;
	      
	}
	
	**/
	
}
