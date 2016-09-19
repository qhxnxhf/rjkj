<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>
	<c:if test="${organize.id != null && '' != organize.id }">修改</c:if>
	<c:if test="${organize.id == null || '' == organize.id }">添加</c:if>
</title>
<!--框架必需start-->
<script type="text/javascript" src="<c:url value='/resources/libs/js/jquery.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/libs/js/language/cn.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/libs/js/framework.js' />"></script>
<link href="<c:url value='/resources/libs/css/import_basic.css' />" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="<c:url value='/resources/' />"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->


<!-- 表单验证start -->
<script src="<c:url value='/resources/libs/js/form/validationRule.js' />" type="text/javascript"></script>
<script src="<c:url value='/resources/libs/js/form/validation.js' />" type="text/javascript"></script>
<!-- 表单验证end -->

<!-- 表单异步提交start -->
<script src="<c:url value='/resources/libs/js/form/form.js' />" type="text/javascript"></script>
<!-- 表单异步提交end -->

</head>
<body>
	<form id="myFormId" action="<c:url value='/sys/role/update' />" method="post" target="frmright">
	<div class="box1" id="formContent" whiteBg="true">
	<table class="tableStyle" formMode="transparent">
		
		<tr>
			<td width="150">角色名称：</td>
			<td>
			<input id="CSRFToken" name="CSRFToken" type="hidden" value="${CSRFToken}"/>
				<input type="hidden" name="id" value="${role.id}"/>
				<input type="hidden" name="status" value="${role.status}"/>
				<input type="text" name="roleName" value="${role.roleName}" class="validate[required]" watermark="请输入" style="width:200px;" /><span class="star">*</span>
				
			</td>
		</tr>
		<tr>
			<td width="150">角色代号：</td>
			<td>
				<input type="text" name="roleCode" value="${role.roleCode}" class="validate[required,custom[noSpecialCaracters]]" watermark="请输入英文或数字" style="width:200px;" /><span class="star">*</span>
				
			</td>
		</tr>
		
		<tr>
			<td width="150">简介：</td>
			<td><input type="text" name="roleRemark"  value="${role.roleRemark}" class="validate[length[0,20]]" style="width:200px;" /><span class="star">*</span></td>
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
			        	//alert(responseText);
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
}

//重置
function closeWin(){
	//刷新数据
	//top.frmright.page_101.refresh(update);
	﻿﻿var tabfrmId="page_"+top.frmright.getCurrentTabId();
	top.frmright.document.getElementById(tabfrmId).contentWindow.refresh();
	
	//关闭窗口
	top.Dialog.close();
}
</script>
<!-- 异步提交end -->	
</body>
</html>