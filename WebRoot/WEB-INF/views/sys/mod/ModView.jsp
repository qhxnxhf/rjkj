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
<title>查看用户信息</title>
<!--框架必需start-->
<script type="text/javascript" src="<c:url value='/resources/libs/js/jquery.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/libs/js/language/cn.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/libs/js/framework.js'/>"></script>
<link href="<c:url value='/resources/libs/css/import_basic.css'/>" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="<c:url value='/resources/'/>"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->
</head>
<body>
	<div class="box1" id="formContent" whiteBg="true">
	<table class="tableStyle" formMode="view">
		<tr>
			<td>上级节点：</td>
			<td>
					<input type="hidden" name="parent.id" value="${module.parent.id}"/>
					<input type="text" name="parent.name" value="${module.parent.name}"/>
			</td>
		</tr>
		<tr>
			<td width="150">序号：</td>
			<td><input type="text" name="priority"  value="${module.orderNum}"  /></td>
		</tr>
		<tr>
			<td width="150">功能名称：</td>
			<td>
				<input type="text" name="name" value="${module.name}" /><span class="star">*</span>
				
			</td>
		</tr>
		<tr>
			<td width="150">功能代号：</td>
			<td>
				<input type="text" name="sn" value="${module.sn}"/><span class="star">*</span>
				
			</td>
		</tr>
		
		<tr>
			<td>节点类别：</td>
			<td>
				<select selectedValue="${module.type}" name="type"  data='{"list":[{"value":"r","key":"树根"},{"value":"b","key":"分类"},{"value":"m","key":"功能"},{"value":"y","key":"权限"}]}'>
				</select>
				
			</td>
		</tr>
		<tr>
			<td width="150">功能URL：</td>
			<td>
				<input type="text" name="url" value="${module.url}" />
				
			</td>
		</tr>
		
		<tr>
			<td width="150">简介：</td>
			<td><input type="text" name="description"  value="${module.description}"  /></td>
		</tr>
		
	</table>
	</div>
</body>
</html>