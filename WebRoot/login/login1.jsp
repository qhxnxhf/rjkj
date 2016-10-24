<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>95306</title>
<link href="<c:url value='/resources/login/skin1/style.css' />" rel="stylesheet" type="text/css" id="skin"/>

<script type="text/javascript" src="<c:url value='/resources/libs/js/jquery.js'/>" ></script>
<script type="text/javascript" src="<c:url value='/resources/libs/js/login.js'/>"></script>

<!--居中显示start-->
<script type="text/javascript" src="<c:url value='/resources/libs/js/method/center-plugin.js'/>"></script>
<!--居中显示end-->
<style>
/*提示信息*/	
#cursorMessageDiv {
	position: absolute;
	z-index: 99999;
	border: solid 1px #cc9933;
	background: #ffffcc;
	padding: 2px;
	margin: 0px;
	display: none;
	line-height:150%;
}
/*提示信息*/
</style>


</head>
<body >
	<div class="login_main">
		<div class="login_top">
		</div>
		<div class="login_middle">
			<div class="login_middleleft"></div>
			<div class="login_middlecenter">
					<form id="loginForm" action="login.do" class="login_form" method="post">
					<div class="login_user"><input type="text" id="username" ></div>
					<div class="login_pass"><input type="password" id="password" ></div>
					<div class="login_kaptcha">
						<input type="text" id="kaptcha" name="kaptcha"  />
						<img src="" id="kaptchaImage" name="kaptchaImage" alt="如看不清，可点击图片更换" onclick="onImageClick(this);"  />
						
					</div>
					
						
					<div class="clear"></div>
					<div class="login_button">
						<div class="login_button_left"><input type="button" onclick="login()"/></div>
						<div class="login_button_right"><input type="reset" value=""/></div>
						
						
						
						<div class="clear"></div>
					</div>
					</form>
					<div class="login_info" style="display:none;"></div>
					<div class="login_info2"></div>
					
			</div>
			<div class="login_middleright"></div>
			<div class="clear"></div>
		</div>
		<div class="login_bottom">
			<div class="login_copyright">
       
      
        <span>Copyright&copy;2016 青藏铁路公司&nbsp;&nbsp;版权所有</span>
       
</div>
		</div>
	</div>


<script type="text/javascript">
	
    
    function onImageClick(img){
	img.src="<c:url value='/security/jcaptcha.jpg?f=JJ73Vf1JrWyBJJ1V77ZbGPNzS9y8QnhpjlChcw5rmypzNddyR9Q7!1156727646!1440740788063"+new Date().getTime()+"'/>";
}
	
	$(function(){
		onImageClick(document.getElementById("kaptchaImage"));
		
		//居中
		 $('.login_main').center();
		
		 
		 document.getElementById("username").focus();
		 $("#username").keydown(function(event){
		 	if(event.keyCode==13){
				login()
			}
		 });
		 $("#password").keydown(function(event){
		 	if(event.keyCode==13){
				login()
			}
		 });
		 
		
		 
	})

	//登录
	function login() {
		var errorMsg = "";
		var loginName = document.getElementById("username");
		var password = document.getElementById("password");
		var kaptcha = document.getElementById("kaptcha");
		
		if(!loginName.value){
			errorMsg += "&nbsp;&nbsp;用户名不能为空!";
		}
		if(!password.value){
			errorMsg += "&nbsp;&nbsp;密码不能为空!";
		}
		if(!kaptcha.value){
			errorMsg += "&nbsp;&nbsp;验证码不能为空!";
		}

		if(errorMsg != ""){
			$(".login_info").html(errorMsg);
			$(".login_info").show();
		}
		else{
			$(".login_info").show();
			$(".login_info").html("&nbsp;&nbsp;正在登录中...");
			//登录处理
			$.get("<c:url value='/login/login_submit1' />",
				  {"username":loginName.value,"password":password.value,"kaptcha":kaptcha.value},
				  function(result){
				  		onImageClick(document.getElementById("kaptchaImage"));
					  if(result == null){
						  $(".login_info").html("&nbsp;&nbsp;登陆失败！");
						  return false;
					  }
					  if(result.status=="true"||result.status==true){
					  	 
					  	  $(".login_info").html("&nbsp;&nbsp;登录成功，正在转到主页...");
						  //window.location="'"+result.url+"''";  
						  window.location="<c:url value='/layout/main'/>"; 
					  }
					  else{
					  	 $(".login_info").html("&nbsp;&nbsp;"+result.message);
					  }
					  
				  },"json");
		}
	}    
	
</script>
</body>
</html>

