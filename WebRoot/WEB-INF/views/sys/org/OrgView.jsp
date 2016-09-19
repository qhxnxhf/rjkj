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
			<td>所属部门：</td>
			<td>
					
					<input type="text" name="parentName" value="${organize.parent.name}" style="width:200px;"/>
			</td>
		</tr>
		<tr>
			<td width="150">序号：</td>
			<td><input type="text" name="orderNum"  value="${organize.orderNum}"  style="width:200px;"/></td>
		</tr>
		<tr>
			<td width="150">名称：</td>
			<td>
				
				<input type="text" name="organize.name" value="${organize.name}" style="width:200px;"/>
				
			</td>
		</tr>
		<tr>
			<td>类别：</td>
			<td>
				<select selectedValue="${organize.type}" name="organize.type"  data='{"list":[{"value":"r","key":"树根"},{"value":"b","key":"分类"},{"value":"d","key":"部门"},{"value":"y","key":"科室"}]}' selWidth="200">
				</select>
				
			</td>
		</tr>
		<tr>
			<td>状态：</td>
			<td>
				<select selectedValue="${organize.allowed}" name="allowed"  data='{"list":[{"value":"y","key":"有效"},{"value":"n","key":"禁用"}]}' selWidth="200">
				</select>
				
			</td>
		</tr>
		<tr>
			<td width="150">简介：</td>
			<td><input type="text" name="organize.description"  value="${organize.description}" style="width:200px;"/></td>
		</tr>
		
	</table>
	</div>
</body>
</html>