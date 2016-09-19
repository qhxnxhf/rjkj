<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改</title>
<!--框架必需start-->
<script type="text/javascript" src="<c:url value='/resources/libs/js/jquery.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/libs/js/language/cn.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/libs/js/framework.js' />"></script>
<link href="<c:url value='/resources/libs/css/import_basic.css' />" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="<c:url value='/resources/' />"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->

<!-- 日期控件start -->
<script type="text/javascript" src="<c:url value='/resources/libs/js/form/datePicker/WdatePicker.js' />"></script>
<!-- 日期控件end -->

<!-- 树组件start -->
<script type="text/javascript" src="<c:url value='/resources/libs/js/tree/ztree/ztree.js' />"></script>
<link type="text/css" rel="stylesheet" href="<c:url value='/resources/libs/js/tree/ztree/ztree.css' />"></link>
<!-- 树组件end -->

<!-- 树形下拉框start -->
<script type="text/javascript" src="<c:url value='/resources/libs/js/form/selectTree.js' />"></script>
<!-- 树形下拉框end -->

<!-- 组合下拉框start -->
<script type="text/javascript" src="<c:url value='/resources/libs/js/form/selectCustom.js' />"></script>
<!-- 组合拉框end -->

<!-- 表单验证start -->
<script src="<c:url value='/resources/libs/js/form/validationRule.js' />" type="text/javascript"></script>
<script src="<c:url value='/resources/libs/js/form/validation.js' />" type="text/javascript"></script>
<!-- 表单验证end -->

<!-- 表单异步提交start -->
<script src="<c:url value='/resources/libs/js/form/form.js' />" type="text/javascript"></script>
<!-- 表单异步提交end -->

</head>
<body>
	<form id="myFormId" action="<c:url value='/sys/user/update' />" method="post" target="frmright" >
	<div class="box1" id="formContent" whiteBg="true">
	<table class="tableStyle" formMode="line" >
		
		<tr>
			<td width="30%" >账号：</td>
			<td width="70%" >
			<input id="CSRFToken" name="CSRFToken" type="hidden" value="${CSRFToken}"/>
				<input type="hidden" id="id" name="id"  value="${user.id}"/>
				<input type="hidden" id="roles" name="roles"  value="${user.roles}"/>
				<input type="text" id="username" name="loginName" value="${user.loginName}" class="validate[required]" style="width:200px;"/><span id="forusername" class="star">*</span>
				
			</td>
		</tr>
		<tr>
			<td width="150">姓名：</td>
			<td>
				<input type="text"  name="userName" value="${user.userName}"  class="validate[required,length[0,20]]" style="width:200px;" /><span class="star">*</span>
		
			</td>
		</tr>
		<tr>
			<td>所属部门：</td>
			<td>
				<div class="selectTree validate[required]" selectedValue="${user.org.id}" name="org.id" id="organize" url="<c:url value='/sys/user/orgtree' />"  selWidth="200" ></div><span class="star">*</span>	
			</td>
		</tr>
		<tr>
			<td>性别：</td>
			<td>
				
				<div style="width:200px;" class="checkboxRender">
					<input type="radio" class="validate[required] radio" id="radio-1" name="sex" value="1"  /><label for="radio-1" class="hand">男</label>
					<input type="radio" class="validate[required] radio" id="radio-2" name="sex" value="2"  /><label for="radio-2" class="hand">女</label><span class="star">*</span>
				</div>
			</td>
		</tr>
		
		<tr>
		<td>电子邮件：</td>
			<td>
				<input type="text" name="email" value="${user.email}" class="validate[custom[email]]" style="width:200px;" /><span class="star">*</span>
			</td>
		</tr>
		<tr>
			<td>手机号码：</td>
			<td><input type="text" name="mobile" value="${user.mobile}" class=" validate[custom[mobilephone]]" style="width:200px;" /><span class="star">*</span></td>
			
		</tr>
		<tr>
			<td>IP地址：</td>
			<td><input type="text" name="grantIp" value="${user.allowIp}" class=" validate[custom[ip]]" style="width:200px;" /><span class="star">*</span></td>
		</tr>
		
		<tr>
			<td colspan="2">
				<input type="submit" value="提交"/>
				<input type="button" value="取消" onclick="top.Dialog.close()"/>
			</td>
		</tr>
	</table>
	</div>
	</form>
<!-- 异步提交start -->
<script type="text/javascript">
function initComplete(){
    //表单提交
    $('#myFormId').submit(function(){ 
	    	//判断表单的客户端验证是否通过
			var valid = $('#myFormId').validationEngine({returnIsValid: true});
			if(valid){
			   $(this).ajaxSubmit({
			        //表单提交成功后的回调
			        success: function(responseText, statusText, xhr, $form){
			            var obj = jQuery.parseJSON(responseText);
			            top.Dialog.alert(obj.message,function(){
			            	closeWin();
			            })
			        }
			    }); 
			 }
		    
		    //阻止表单默认提交事件
		    return false; 
	});
   
   $("#username").bind("blur",function(){
   		var name=$("#username").val();
   		var id=$("#id").val();
   		$.post("<c:url value='/sys/user/findUser' />",
		  			{"username":name,"userid":id},
		  			function(result){
		  				if(result.status == "true"){
							$("#forusername").html(name+":此账号无效");
    						$("#loginName").attr("value","");
    						$("#loginName").render();
						}else{
							$("#forusername").html("*");
    						$("#loginName").render();
						}
		  			
					},"json");
    	
	})
   
	initRadio();
}


function initRadio(){
		var sex="${user.sex}";
		var status="${user.status}";
		if(sex=="1"){
			$("input:radio[name=sex]").eq(0).attr("checked",true);
		}else{
			$("input:radio[name=sex]").eq(1).attr("checked",true);
		}
	
	}

/**
 * 重置
 */
function closeWin(){
	//刷新数据
	﻿﻿var tabfrmId="page_"+top.frmright.getCurrentTabId();
		top.frmright.document.getElementById(tabfrmId).contentWindow.refresh();
	//关闭窗口
		top.Dialog.close();
}
</script>
<!-- 异步提交end -->	
</body>
</html>