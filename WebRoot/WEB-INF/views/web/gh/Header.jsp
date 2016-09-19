<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div class="top">
            <i class="icon-top"></i>
        </div>
        <ul class="nav-top bg-nav-top">
            <li><a href="<%=path%>/web/index?url=web/gh/Index" class="font-hei" class="font-hei">首页</a></li>
            <li>
                <a href="<%=path%>/web/index?url=web/gh/List&lmid=177" class="font-hei">工会文件</a>
               
            </li>
            <li>
                <a href="<%=path%>/web/index?url=web/gh/List&lmid=178" class="font-hei">法律法规</a>
               
            </li>
            <li><a href="<%=path%>/web/index?url=web/gh/List&lmid=179" class="font-hei">民主管理</a></li>
            <li><a href="<%=path%>/web/index?url=web/gh/List&lmid=180" class="font-hei">理论调研</a></li>
            <li><a href="<%=path%>/web/index?url=web/gh/List&lmid=181" class="font-hei">计划总结</a></li>
            <li><a href="<%=path%>/index.jsp" class="font-hei">主站</a></li>
        </ul>
        
        