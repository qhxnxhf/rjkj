<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>
	修改
</title>
<!--框架必需start-->
<script type="text/javascript" src="<c:url value='/resources/libs/js/jquery.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/libs/js/language/cn.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/libs/js/framework.js'/>"></script>
<link href="<c:url value='/resources/libs/css/import_basic.css'/>" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="<c:url value='/resources/'/>"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->



<!-- 表单验证start -->
<script src="<c:url value='/resources/libs/js/form/validationRule.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/libs/js/form/validation.js'/>" type="text/javascript"></script>
<!-- 表单验证end -->

<!-- 日期选择框start -->
<script type="text/javascript" src="<c:url value='/resources/libs/js/form/datePicker/WdatePicker.js'/>"></script>
<!-- 日期选择框end -->


<!-- 表单start -->
<script src="<c:url value='/resources/libs/js/form/form.js'/>" type="text/javascript"></script>
<!-- 表单end -->
<style>
.l-layout-center{
    border:none!important;
}
.l-layout-left{
    border-bottom:none!important;
}
.l-layout-drophandle-left{
    width: 10px;
}
</style>
</head>
<body>
	<form id="myFormId" action="<c:url value='/cms/safe/saveEdit'/>" method="post" target="frmright">
	<div class="box1" id="formContent" whiteBg="true">
	<table class="tableStyle" formMode="transparent">
		
		<tr>
			<td width="100">序号：</td>
			<td><input type="text" name="orderNum"  value="${safe.orderNum}"  class="validate[required,custom[onlyNumber]]" watermark="请输入正整数" style="width:300px;"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td width="100">名称：</td>
			<td>
					<input type="hidden" name="id" value="${safe.id}"/>
					<input type="hidden" name="allowed" value="${safe.allowed}"/>
					<input type="hidden" name="org.id" value="${safe.org.id}"/>
					<input type="hidden" name="dic.id" value="${safe.dic.id}"/>
					<input id="CSRFToken" name="CSRFToken" type="hidden" value="${CSRFToken}"/>
					<input type="text" name="title" value="${safe.title}" class="validate[required]" watermark="请输入" style="width:300px;"/><span class="star">*</span>
				
			</td>
		</tr>
		<tr>
			<td width="100">开始时间：</td>
			<td>
			<input type="text" class="date validate[required]" name="beginTime" value="${fn:substring(safe.beginTime, 0, 19)}" style="width:300px;" dateFmt="yyyy-MM-dd HH:mm:ss"/>
			</td>
			
		</tr>
		
		<tr>
			<td width="100">简介：</td>
			<td>
			<textarea name="brief"  class="validate[length[0,500]]" style="width:300px;height:100px;" >${safe.brief}</textarea>
			</td>
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
	top.frmright.document.getElementById(tabfrmId).contentWindow.refresh(true);
	//关闭窗口
	top.Dialog.close();
}
</script>
<!-- 异步提交end -->	
</body>
</html>
    
   
