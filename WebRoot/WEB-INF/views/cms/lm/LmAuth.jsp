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



<!-- 表单异步提交start -->
<script src="<c:url value='/resources/libs/js/form/form.js'/>" type="text/javascript"></script>
<!-- 表单异步提交end -->

<!-- 树组件start -->
<script type="text/javascript" src="<c:url value='/resources/libs/js/tree/ztree/ztree.js'/>"></script>
<link type="text/css" rel="stylesheet" href="<c:url value='/resources/libs/js/tree/ztree/ztree.css'/>"></link>
<!-- 树组件end -->

<!-- 树形下拉框start -->
<script type="text/javascript" src="<c:url value='/resources/libs/js/form/selectTree.js'/>"></script>
<!-- 树形下拉框end -->

<!-- 组合下拉框start -->
<script type="text/javascript" src="<c:url value='/resources/libs/js/form/selectCustom.js'/>"></script>
<!-- 组合拉框end -->

</head>
<body>
	<form id="myFormId" action="<c:url value='/cms/lm/saveAuth'/>" method="post" target="frmright">
	<div class="box1" id="formContent" whiteBg="true">
	<table class="tableStyle" formMode="line" >
		<tr>
			<td width="100" >父节点：</td>
			<td  >
					<input id="CSRFToken" name="CSRFToken" type="hidden" value="${CSRFToken}"/>
				<input type="hidden" name="parent.id" value="${dic.parent.id}"/>
				<input type="hidden" name="orderNum"  value="${dic.orderNum}"   />
				<input type="hidden" name="linkUrl"  value="${dic.linkUrl}" />
				<input type="hidden" name="target"  value="${dic.target}" />
				<input type="hidden" name="nodeType"  value="${dic.nodeType}" />
				<input type="hidden" name="status"  value="${dic.status}" />
				
				<input type="text" name="parent.lmName" value="${dic.parent.lmName}" style="width:350px;"/>
			
			</td>
		</tr>
		
		<tr>
			<td >名称：</td>
			<td>
				<input type="hidden" name="id" value="${dic.id}"/>
				<input type="text" name="lmName" value="${dic.lmName}" class="validate[required]" watermark="请输入" style="width:350px;"/><span class="star">*</span>
				
			</td>
		</tr>
		
		
		<tr>
			<td >栏目定制授权：</td>
			<td>
				<div class="selectTree " selectedValue="${dic.orgIdsMl}" name="orgIdsMl"  url="<c:url value='/sys/org/orgTree?parentId=1'/>"  multiMode="true" selWidth="350" ></div>	
				
				
			</td>
		</tr>
		<tr>
			<td >新闻发布授权：</td>
			<td>
				<div class="selectTree " selectedValue="${dic.orgIdsWj}" name="orgIdsWj"  url="<c:url value='/sys/org/orgTree?parentId=1'/>"  multiMode="true" selWidth="350" ></div>	
				
			</td>
		</tr>
		<tr>
		<td>授权方式：</td>
		<td>
				<div style="width:200px;" class="checkboxRender">
					
					<input type="radio" class="validate[required] radio" id="radio-1" name="sqfs" value="1" checked="checked" /><label for="radio-1" class="hand">单独授权</label>
					<input type="radio" class="validate[required] radio" id="radio-2" name="sqfs" value="2"/><label for="radio-2" class="hand">级联授权</label>
					
				</div>
			</td>
		</tr>
		<tr>
			<td >简介：</td>
			<td>
			<textarea name="lmBrief"  style="width:350px;height:80px;" >${dic.lmBrief}</textarea><span class="star">*</span>
			
			
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