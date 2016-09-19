<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<div class="top">
    <i class="icon-logo"></i>
</div>
<ul class="nav bg-nav-top">
    <li><i class="icon-nav"></i><a href="<%=path%>/web/index?url=web/jj/Index" class="font-hei">政工首页</a></li>
    <li><i class="icon-nav"></i><a href="<%=path%>/web/index?url=web/jj/List&lmid=129" class="font-hei">要闻速览</a></li>
    <li><i class="icon-nav"></i><a href="<%=path%>/web/index?url=web/jj/List&lmid=130" class="font-hei">信息公开</a></li>
    <li><i class="icon-nav"></i><a href="<%=path%>/web/index?url=web/jj/List&lmid=131" class="font-hei">领导讲话</a></li>
    <li><i class="icon-nav"></i><a href="<%=path%>/web/index?url=web/jj/List&lmid=132" class="font-hei">工作动态</a></li>
    <li><i class="icon-nav"></i><a href="<%=path%>/web/index?url=web/jj/List&lmid=133" class="font-hei">学习园地</a></li>
    <li><i class="icon-nav"></i><a href="<%=path%>/web/index?url=web/jj/List&lmid=134" class="font-hei">廉政文化</a></li>
	<li><i class="icon-nav"></i><a href="<%=path%>/index.jsp" class="font-hei">主站</a></li>
</ul>
