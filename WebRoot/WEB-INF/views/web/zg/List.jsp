<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>  
  <head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="<%=path%>/resources/zg/Styles/xg.css" rel="stylesheet" />
    <link href="<%=path%>/resources/zg/Styles/css.css" rel="stylesheet" />
    <script src="<%=path%>/resources/zg/Scripts/jquery-1.8.2.js"></script>
    <script src="<%=path%>/resources/zg/Scripts/xg.js"></script>
    <script src="<%=path%>/resources/zg/Scripts/js.js"></script>
    <title></title>
<script type="text/javascript">

 var size=10;
 var lmtaburl="<%=path%>/web/lmtab/";
var shownews="<%=path%>/web/showNews2?url=web/zg/Detail&newsid=";
		
$(function (){	
	$("#subtitle").html("${lm.lmName}");
	contentList("list","${lm.id}","","pager",shownews,10,5);
})
		
	 </script>

  </head>
  
  <body>
    <div class="wrapper">
        <div class="top">
            <i class="icon-top"></i>
        </div>
        <div class="main">
            <div class="layout-left">
                <ul class="nav-left">
                    <li class="bg-title-red"><a ><strong class="font-hei">${lm.lmName}</strong></a></li>
                    <li class="bg-title-red"><a > </a></li>
                    <li class="bg-title-red"><a > </a></li>
                    <li class="bg-title-red"><a > </a></li>
                    <li class="bg-title-red"><a > </a></li>
                   
                </ul>
            </div>
            <div class="layout-right">
                <div class="list">
                    <h3><span id="subtitle">栏目栏目</span></h3>
                    <div id="list">
                        <ul>
                            <li><i class="icon-list-red left"></i><span class="right">15/09/09</span><a href="detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list-red left"></i><span class="right">15/09/09</span><a href="detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list-red left"></i><span class="right">15/09/09</span><a href="detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list-red left"></i><span class="right">15/09/09</span><a href="detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list-red left"></i><span class="right">15/09/09</span><a href="detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list-red left"></i><span class="right">15/09/09</span><a href="detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list-red left"></i><span class="right">15/09/09</span><a href="detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list-red left"></i><span class="right">15/09/09</span><a href="detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list-red left"></i><span class="right">15/09/09</span><a href="detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list-red left"></i><span class="right">15/09/09</span><a href="detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list-red left"></i><span class="right">15/09/09</span><a href="detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list-red left"></i><span class="right">15/09/09</span><a href="detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list-red left"></i><span class="right">15/09/09</span><a href="detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list-red left"></i><span class="right">15/09/09</span><a href="detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list-red left"></i><span class="right">15/09/09</span><a href="detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                        </ul>
                    </div>
                    <div class="xg-pager" id="pager">
                        <span>首页</span><span>上一页</span><a>1</a><a>2</a><a>3</a><a>4</a><em class="xg-pager-hover">5</em><a>6</a><a>7</a><a>8</a><a>9</a><span>下一页</span><span>尾页</span><strong>总计9页</strong>
                    </div>
                </div>
            </div>
        </div>
        <div class="clear"></div>
        <div class="bottom">
            &copy; 2015 青藏铁路公司西宁东车辆段 版权所有
        </div>
    </div>
  </body>
</html>
