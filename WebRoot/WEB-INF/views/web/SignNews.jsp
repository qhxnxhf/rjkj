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
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="<c:url value='/resources/dcld/Styles/xg.css'/>" rel="stylesheet" />
    <link href="<c:url value='/resources/dcld/Styles/css.css'/>" rel="stylesheet" />
    <script src="<c:url value='/resources/dcld/Scripts/jquery-1.8.2.js'/>"></script>
    <script src="<c:url value='/resources/dcld/Scripts/xg.js'/>"></script>
    <script src="<c:url value='/resources/dcld/Scripts/js.js'/>"></script>
    <!--框架必需start-->
	<script type="text/javascript" src="<c:url value='/resources/libs/js/jquery.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/libs/js/language/cn.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/libs/js/framework.js'/>"></script>
	<link href="<c:url value='/resources/libs/css/import_basic.css'/>" rel="stylesheet" type="text/css"/>
	<link rel="stylesheet" type="text/css" id="skin" prePath="<c:url value='/resources/'/>"/>
	<link rel="stylesheet" type="text/css" id="customSkin"/>
	<!--框架必需end-->
    
    <script type="text/javascript" charset="utf-8" src="<%=path%>/resources/ueditor/ueditor.parse.js"></script>
    
    <!--数据表格start-->
	<script src="<c:url value='/resources/libs/js/table/quiGrid.js'/>" type="text/javascript"></script>
	<!--数据表格end-->
    
    <title></title>
    <script type="text/javascript">
    	
 		
 		//定义grid
	var grid = null;
 		
 		//初始化Grid处理
	function initGrid() {
		grid = $("#dataBasic").quiGrid({
			columns: [
                     			{ display: '签收人', name: 'taker.userName',  align: 'left',width: "10%"},
                     			{ display: '发送时间',  name: 'sendDate', align: 'center', width:"20%"},
                     			{ display: '留言信息',  name: 'leavewd', align: 'center', width:"20%"}, 
                     			{ display: '回复信息',  name: 'replywd', align: 'center', width:"20%"},
                     			{ display: '签收时间',  name: 'receiveDate', align: 'center', width:"20%"}, 
                     			{ display: '收件状态',  name: 'status', align: 'center', width:"10%",
				 					render : function(rowdata, rowindex, value, column){ return renderStatue(rowdata,value);}}
                     ], 

                     isScroll:true, width:'100%', sortName: 'id',percentWidthMode:false,pageSize:20,checkbox:false,rownumbers:true,
                     url: '<%=path%>/cms/send/taker?newsId=${news.id==null?0:news.id}'
         
         
		});
	}

	//渲染节点
	function renderStatue(rowdata,value){
		if(value=="1"){
           return '<font color=red>待签阅</font>';
       	}
        if(value=="2"){
          return '<font color=green>已签阅</font>';
        }
        if(value=="3"){
           return '<font color=yellow>禁用</font>';
        }
        
	}
	
	//签收新闻	
	function onSignNews(){
		  $.post("<%=path%>/web/signNews",
		  			{"newsId":'${news.id}'},
		  			function(result){
		  				handleResult(result);
					},"json");
	}
		
	function handleResult(result){
		if(result.status == "false"){
			 $("#login_info").html(result.message);
			
		}else{
			grid.loadData();
		}
	}
	
	var size = 6;
		var lmurl = "<%=path%>/web/lmNews/";
		var shownews = "<%=path%>/web/showNews/";	
    	var lmurltj = "<%=path%>/web/lmNewsTj/";
 		
 		$(function (){
 			onSignNews();
			//初始化grid组件
			initGrid();
			//
			uParse("#editor", {  rootPath: '<%=path%>/resources/ueditor'});
			$("body").removeAttr("style");
 		});
 		
 		//function initComplete(){
 		//	onSignNews();
		//	初始化grid组件
		//	initGrid();
			//
		//	uParse("#editor", {  rootPath: '<%=path%>/resources/ueditor'});
			//$("body").removeAttr("style");
 		
 		//}
 		
    </script>
    </head>

    <body class="bg-website">
	<div class="wrapper">
		<div class="wrapper-left">
			<div class="wrapper-right">
				<div class="wrapper-main">
					<jsp:include page="/WEB-INF/views/web/Header.jsp"></jsp:include>
					<div class="main center">
						<div class="detail center">
						<span id="login_info" style="padding:10px;font-size: 28px; color: blue;">${message}</span>
							<div class="text-center" style="margin:20px 0px 0px 0px;font-size: 28px; font-weight: bold; color: red;">
							<span style="line-height:38px;">${news.newsTitle} 
							</span>
							</div>
							<div class="text-center" style="margin:10px auto;padding:5px;width:1050px;font-size: 16px;border-bottom: 3px solid #ff0000;">
							<span style="margin:0px 10px;">发布：${news.user.userName}</span>
							<span style="margin:0px 10px;">来源：${news.origin}</span>
							<span style="margin:0px 10px;">日期：${news.pubDate}</span>
							<span style="margin:0px 10px;">访问量：${news.readCount}</span>
							</div>
							<div id="editor">
								${news.newsBody}
							</div>
							
							<div style="width:1070px;margin: 0;padding: 0 5px 0 0;">
							<div id="dataBasic"></div>
							</div>
						</div>
						
						
						<div class="clear"></div>
					</div>
					<jsp:include page="/WEB-INF/views/web/Footer.jsp"></jsp:include>
				</div>
			</div>
		</div>
		<div class="clear"></div>
		<div class="wrapper-bottom">
			<div class="wrapper-radius-left"></div>
			<div class="wrapper-radius-right"></div>
		</div>
	</div>
</body>
</html>
