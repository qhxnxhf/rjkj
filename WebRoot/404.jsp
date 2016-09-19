<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%response.setStatus(200);%>
<!DOCTYPE html>
<html>
  <head>
    <title>404</title>
    <meta name="content-type" content="text/html; charset=UTF-8">
    <!--<link rel="stylesheet" type="text/css" href="./styles.css">-->
  </head>
  
  <body>
  	<h1>
  	<c:if test="${msg==null}">抱歉，没找到您想查看的页面！</c:if>
  	<c:if test="${msg!=null}">${msg}</c:if>
  	</h1>
  </body>
</html>
