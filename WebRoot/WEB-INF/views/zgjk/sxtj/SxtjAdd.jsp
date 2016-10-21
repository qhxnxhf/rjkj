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
	添加
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

</head>
<body>
	<form id="myFormId" action="<c:url value='/zgjk/sxtj/saveAdd'/>" method="post" target="frmright">
	<div class="box1" id="formContent" whiteBg="true">
	<table class="tableStyle" formMode="transparent">
		<tr>
			<td width="80">序号</td>
			<td>
				<input type="text" name="orderNum"  value="${dic.parent.id}"  class="validate[required,custom[onlyNumber]]" watermark="请输入正整数" style="width:200px;"/>
			</td>
			<td width="80">分类</td>
			<td>
			<input id="CSRFToken" name="CSRFToken" type="hidden" value="${CSRFToken}"/>
					<input type="hidden" name="parent.id" value="${dic.parent.id}"/>
					<input type="text" name="parent.typeName" value="${dic.parent.typeName}" style="width:200px;"/>
			</td>
		</tr>
		
		<tr>
			<td >名称</td>
			<td >
			<input type="text" name="typeName" value="${dic.typeName}" class="validate[required]" watermark="请输入" style="width:200px;"/>
			</td>
			
			<td >字段映射</td>
			<td>
			<input type="text" name="mappingField" value="${dic.mappingField}" class="validate[custom[onlyNumberWide]]" watermark="请输入" style="width:200px;"/>
			</td>
		</tr>
		
		
		<tr>
			<td >查询SQL</td>
			<td colspan="3">
			<textarea name="typeSql"  style="width:470px;height:50px;" >${dic.typeSql}</textarea>
			</td>
		</tr>
		<tr>
			<td >查询说明</td>
			<td colspan="3">
			<textarea name="typeBrief"  style="width:470px;height:50px;" >${dic.typeBrief}</textarea>
			
			</td>
		</tr>
		<tr>
			<td >健康提示</td>
			<td colspan="3">
			<textarea name="jkts"  style="width:470px;height:100px;" >${dic.jkts}</textarea>
			
			</td>
		</tr>
		<tr>
			<td>类别</td>
			<td>
				<select  name="nodeType"  data='{"list":[{"value":"b","key":"分类"},{"value":"d","key":"部门"},{"value":"y","key":"科室"}]}' selWidth="200">
				</select>
				
			</td>
			<td>状态</td>
			<td>
				<select  name="status"  data='{"list":[{"value":"y","key":"有效"},{"value":"n","key":"禁用"}]}' selWidth="200">
				</select>
				
			</td>
		</tr>
		
		<tr>
			<td colspan="4">
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
	var update = false;
	var isupdate = "${organize.id}";
	if(isupdate != ''){
		update = true;
	}else{
		update = false;
	}
	//刷新数据
	//top.frmright.page_101.refresh(update);
	﻿﻿var tabfrmId="page_"+top.frmright.getCurrentTabId();
	top.frmright.document.getElementById(tabfrmId).contentWindow.refresh(update);
	//关闭窗口
	top.Dialog.close();
}
</script>
<!-- 异步提交end -->	
</body>
</html>