<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="<%=basePath%>">
    
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="<c:url value='/resources/dcld/Styles/xg.css'/>" rel="stylesheet" />
    <link href="<c:url value='/resources/dcld/Styles/css.css'/>" rel="stylesheet" />
    <script src="<c:url value='/resources/dcld/Scripts/jquery-1.8.2.js'/>"></script>
    <script>
    	$(function(){
   			//防止生产大表被人为拖动
			var $tableInitStyle=$('div.report').find("table.table-init-style");
			$tableInitStyle.find("th,td").removeAttr("width").removeAttr("height").removeAttr("style");    	
    	});
    </script>
    <title></title>
</head>
<body>
    <div class="report">
        ${news.newsBody}
    </div>
</body>
</html>
