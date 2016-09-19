<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<div class="header"> <i class="icon-logo"></i>
	<div id="fade">
		<ul>
			<li> <img src="<c:url value='/resources/dcld/Images/head_3.jpg'/>" /></li>
			<li> <img src="<c:url value='/resources/dcld/Images/head_2.jpg'/>" /></li>
			<li> <img src="<c:url value='/resources/dcld/Images/head_1.jpg'/>" /></li>
		</ul>
	</div>
	<div class="nav-top">
		<ul>
			<li class="left icon-nav-top-left"></li>
			<li class="left bg-nav-top font-hei"><a href="<%=path%>/index.jsp">首页</a></li>
			<li class="left icon-nav-top-separator"></li>
			<li class="left bg-nav-top font-hei"><a href="<%=path%>/web/index?lmid=53&url=web/DeptBrief">东辆简介</a></li>
			<li class="left icon-nav-top-separator"></li>
			<li class="left bg-nav-top font-hei"><a href="<%=path%>/web/index?lmid=21&url=web/LmNews">通知公告</a></li>
			<li class="left icon-nav-top-separator"></li>
			<li class="left bg-nav-top font-hei"><a href="<%=path%>/web/index?lmid=25&url=web/LmNews">文电管理</a></li>
			<li class="left icon-nav-top-separator"></li>
			<li class="left bg-nav-top font-hei"><a href="<%=path%>/web/index?lmid=24&url=web/LmNews">安全管理</a></li>
			<li class="left icon-nav-top-separator"></li>
			<li class="left bg-nav-top font-hei"><a href="<%=path%>/web/index?lmid=110&url=web/LmNews">标准体系</a></li>
			<li class="left icon-nav-top-separator"></li>
			<li class="left bg-nav-top font-hei"><a href="<%=path%>/web/index?lmid=27&url=web/LmNews">职工教育</a></li>
			<li class="left icon-nav-top-separator"></li>
			<li class="left bg-nav-top font-hei"><a href="<%=path%>/web/index?lmid=29&url=web/DeptBrief">电话号码</a></li>
			<li class="left bg-nav-top width-20"></li>
		</ul>
	</div>
</div>
