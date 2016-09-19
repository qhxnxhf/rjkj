<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title> </title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="<%=path%>/resources/jj/Styles/xg.css" rel="stylesheet" />
    <link href="<%=path%>/resources/jj/Styles/css.css" rel="stylesheet" />
    <script src="<%=path%>/resources/jj/Scripts/jquery-1.8.2.js"></script>
    <script src="<%=path%>/resources/jj/Scripts/xg.js"></script>
    <script src="<%=path%>/resources/jj/Scripts/js.js"></script>
	
	<script type="text/javascript">
	
	
	var shownews="<%=path%>/web/showNews2?url=web/jj/Detail&newsid=";	
	
	//查询
    function searchHandler(){
    	//var lmid=$("#parentId").val();
		var kwd=$("#search").val();
		contentList("list","",kwd,"pager",10,5);
    }
	
	function navclick(lmName,id){
		
		$("#subtitle").empty();
		$("#subtitle").html(lmName);
		
		contentList("list",id,"","pager",shownews,10,5);
	}
 
    
    //地区介绍导航栏（本地调用）
    function navList(lmurl,lmId,size,target){  
    	$.ajax({  
	        type: "post",  
	       	url: lmurl, 
	       	data :{ "pId": lmId, "size": size},
	       	cache:false,  
	        dataType:"json", 
	        success: function(result){  
	        	
	        	var item="";
					//$("#picTest2").attr("src","<%=path%>/cms/attach/download/"+result.lmicon);
				item='<ul class="nav-left"><li class="bg-nav-left"><a><i></i><strong>'+result.title+'</strong></a></li>';
		        $.each(result.tabs, function (index, obj) {
			        if(index==0) navclick(obj.lmName,obj.id); 
			        item=item+"<li class='bg-nav-left' onClick=navclick('"+obj.lmName+"',"+obj.id+") ><a><i></i>"+obj.lmName+"</a></li>";	         
		        });
		        item=item+"</ul>";
				target.empty();
				target.html(item) ;
	        }  
		});
	}
    
 var size=10;
 var lmtaburl="<%=path%>/web/lmtab/";
 
 
$(function (){
	navList(lmtaburl,${lm.id},10,$("#navleft"));//地区介绍导航链接
})
		
	 </script>
	
	
  </head>
  
  <body>
     <div class="wrapper">
        <jsp:include page="/WEB-INF/views/web/jj/Header.jsp"></jsp:include>
        <div class="main">
            <div class="layout-left" id="navleft">
                <ul class="nav-left">
                    <li class="bg-nav-left"><a href="List.html"><i></i>栏目栏目</a></li>
                    <li class="bg-nav-left"><a href="List.html"><i></i>栏目栏目</a></li>
                    <li class="bg-nav-left"><a href="List.html"><i></i>栏目栏目</a></li>
                    <li class="bg-nav-left"><a href="List.html"><i></i>栏目栏目</a></li>
                    <li class="bg-nav-left"><a href="List.html"><i></i>栏目栏目</a></li>
                    <li class="bg-nav-left"><a href="List.html"><i></i>栏目栏目</a></li>
                    <li class="bg-nav-left"><a href="List.html"><i></i>栏目栏目</a></li>
                    <li class="bg-nav-left"><a href="List.html"><i></i>栏目栏目</a></li>
                    <li class="bg-nav-left"><a href="List.html"><i></i>栏目栏目</a></li>
                    <li class="bg-nav-left"><a href="List.html"><i></i>栏目栏目</a></li>
                </ul>
            </div>
            <div class="layout-right">
                <div class="list">
                    <h3><i class="icon-title-left"></i><span id="subtitle">栏目栏目</span><i class="icon-title-right"></i></h3>
                    <div id="list">
                        <ul>
                            <li><i class="icon-list left"></i><span class="right">15/09/09</span><a href="detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list left"></i><span class="right">15/09/09</span><a href="detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list left"></i><span class="right">15/09/09</span><a href="detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list left"></i><span class="right">15/09/09</span><a href="detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list left"></i><span class="right">15/09/09</span><a href="detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list left"></i><span class="right">15/09/09</span><a href="detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list left"></i><span class="right">15/09/09</span><a href="detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list left"></i><span class="right">15/09/09</span><a href="detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list left"></i><span class="right">15/09/09</span><a href="detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list left"></i><span class="right">15/09/09</span><a href="detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list left"></i><span class="right">15/09/09</span><a href="detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list left"></i><span class="right">15/09/09</span><a href="detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list left"></i><span class="right">15/09/09</span><a href="detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list left"></i><span class="right">15/09/09</span><a href="detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list left"></i><span class="right">15/09/09</span><a href="detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                        </ul>
                    </div>
                    <div class="xg-pager" id="pager">
                        <span>首页</span><span>上一页</span><a>1</a><a>2</a><a>3</a><a>4</a><em class="xg-pager-hover">5</em><a>6</a><a>7</a><a>8</a><a>9</a><span>下一页</span><span>尾页</span><strong>总计9页</strong>
                    </div>
                </div>
            </div>
        </div>
        <div class="clear"></div>
        <jsp:include page="/WEB-INF/views/web/jj/Footer.jsp"></jsp:include>
    </div>
</body>
</html>
