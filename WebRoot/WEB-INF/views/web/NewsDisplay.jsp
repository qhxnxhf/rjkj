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
    
    <script type="text/javascript" charset="utf-8" src="<%=path%>/resources/ueditor/ueditor.parse.js"></script>
    
    <link href="<c:url value='/resources/dcld/Styles/xg.css'/>" rel="stylesheet" />
    <link href="<c:url value='/resources/dcld/Styles/css.css'/>" rel="stylesheet" />
    <script src="<c:url value='/resources/dcld/Scripts/jquery-1.8.2.js'/>"></script>
    <script src="<c:url value='/resources/dcld/Scripts/xg.js'/>"></script>
    <script src="<c:url value='/resources/dcld/Scripts/js.js'/>"></script>
    <title></title>
    <script type="text/javascript">
    	var size = 6;
		var lmurl = "<%=path%>/web/lmNews/";
		var shownews = "<%=path%>/web/showNews/";	
    	var lmurltj = "<%=path%>/web/lmNewsTj/";
 		$(function(){
			uParse("#editor", {  rootPath: '<%=path%>/resources/ueditor'});
			$("html").removeAttr("style");
			$("body").removeAttr("style");
 		});
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
						<span style="margin:10px;font-size: 28px;  color: red;">${message}</span>
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
