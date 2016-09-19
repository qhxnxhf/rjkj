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
	<c:if test="${dic.id != null && '' != dic.id }">修改</c:if>
	<c:if test="${dic.id == null || '' == dic.id }">添加</c:if>
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
	<form id="myFormId" action="<c:url value='/sys/dic/saveEdit' />" method="post" target="frmright">
	<div class="box1" id="formContent" whiteBg="true">
	<table class="tableStyle" formMode="line" >
		<tr>
			<td width="80" >父节点：</td>
			<td  >
			<input id="CSRFToken" name="CSRFToken" type="hidden" value="${CSRFToken}"/>
					<input type="hidden" name="parent.id" value="${dic.parent.id}"/>
					<input type="text" name="parent.dicName" value="${dic.parent.dicName}" style="width:350px;"/>
			</td>
		</tr>
		<tr>
			<td>序号：</td>
			<td><input type="text" name="orderNum"  value="${dic.orderNum}"  class="validate[required,custom[onlyNumber]]" watermark="请输入正整数" style="width:350px;"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td >名称：</td>
			<td>
				<input type="hidden" name="id" value="${dic.id}"/>
				<input type="text" name="dicName" value="${dic.dicName}" class="validate[required]" watermark="请输入" style="width:350px;"/><span class="star">*</span>
				
			</td>
		</tr>
		<tr>
			<td>类别：</td>
			<td>
				<select selectedValue="${dic.nodeType}" name="nodeType"  data='{"list":[{"value":"b","key":"分类"},{"value":"d","key":"单位"},{"value":"y","key":"部门"}]}' selWidth="350" >
				</select>
				
			</td>
		</tr>
		<tr>
			<td >关键字：</td>
			<td>
				<input type="text" name="dicKey" value="${dic.dicKey}" style="width:350px;"/><span class="star">*</span>
				
			</td>
		</tr>
		<tr>
			<td >值1：</td>
			<td>
				
				<textarea name="value1"  style="width:350px;height:80px;" >${dic.value1}</textarea><span class="star">*</span>
			</td>
		</tr>
		<tr>
			<td >值2：</td>
			<td>
				
				<textarea name="value2"  style="width:350px;height:80px;" >${dic.value2}</textarea><span class="star">*</span>
			</td>
		</tr>
		
		<tr>
			<td >简介：</td>
			<td><input type="text" name="dicBrief"  value="${dic.dicBrief}" class="validate[length[0,20]]" style="width:350px;"/><span class="star">*</span></td>
		</tr>
		
		<tr>
			<td>状态：</td>
			<td>
				<select selectedValue="${dic.status}" name="status"  data='{"list":[{"value":"y","key":"有效"},{"value":"n","key":"无效"}]}' selWidth="350" >
				</select>
				
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