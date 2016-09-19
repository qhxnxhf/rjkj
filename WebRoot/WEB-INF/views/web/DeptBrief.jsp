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
<link href="<%=path%>/resources/dcld/Styles/xg.css" rel="stylesheet" />
<link href="<%=path%>/resources/dcld/Styles/css.css" rel="stylesheet" />
<script src="<%=path%>/resources/dcld/Scripts/jquery-1.8.2.js"></script>
<script src="<%=path%>/resources/dcld/Scripts/xg.js"></script>
<script src="<%=path%>/resources/dcld/Scripts/js.js"></script>

<script type="text/javascript" charset="utf-8" src="<%=path%>/resources/ueditor/ueditor.parse.js"></script>


<title>部门介绍</title>
<script type="text/javascript">
    
    //快捷导航
function navList2(lmurl, lmId, size, target) {

    $.post(lmurl + lmId + "/" + size + ".json",
            {},
            function (result) {
                var item = "";
                item = '<h2><i class="icon-title fl"></i>' + result.title + '</h2><ul>';
                $.each(result.rows, function (idx, obj) {
                    //alert(obj.docName);
                    item = item + '<li><i class="icon-list fl"></i><a href="' + obj.origin + '" target="_blank">' + obj.newsTitle + '</a></li>';

                });
                item = item + "</ul>";
                target.empty();
                target.html(item);

            },
            "json");
}



//头条热点新闻列表
function hotList(lmurl, lmId, size, target, shownews, title) {

    $.post(lmurl + lmId + "/" + size + ".json",
            {},
            function (result) {
                var item = "";
                item = '<h2><i class="icon-title fl"></i>' + title + '</h2><ol>';
                $.each(result.rows, function (idx, obj) {
                    //alert(obj.docName);
                    item = item + '<li><a href="' + shownews + obj.id + '" target="_blank">' + obj.newsTitle + '</a></li>';

                });
                item = item + "</ol>";
                target.empty();
                target.html(item);

            },
            "json");
}
    
   
    //内容调用
    function setContent(newsId){
    	$.post(shownews+newsId+".json",
					{},
					function(result){
						
					$("#title").empty();
					$("#title").html(result.newsTitle) ;
						
						//var content=result.newsBody;
						$("#editor").empty();
						$("#editor").html(result.newsBody) ;
						uParse("#editor", {  rootPath: '<%=path%>/resources/ueditor'});
						//XG.Editor.init("editor");
					},
					"json");
    }
    
    //地区介绍导航栏（本地调用）
    function navList(lmurl,lmId,size,target){
			$.post(lmurl+lmId+"/"+size,
					{},
					function(result){
					var item="";
					$("#picTest2").attr("src","<%=path%>/cms/attach/download/"+result.lmicon);
					item='<h3><i class="icon-title left"></i><span class="left">'+result.title+'</span></h3> <ul>';
					
						$.each(result.rows, function(idx, obj) {
							//alert(obj.docName);
							item=item+'<li class="pointer" onClick="setContent('+obj.id+');"><span>'+obj.newsTitle+'</span></li>';
							if(idx==0){
								setContent(obj.id);
							}
						});
						item=item+"</ul>";
						target.empty();
						target.html(item) ;
						
					},
					"json");
		}
		
		
		
    
 var size=10;
 var lmurltj = "<%=path%>/web/lmNewsPx/";
 var shownews="<%=path%>/web/getNews/";
		
$(function (){
		navList(lmurltj,${lm.id},10,$("#dqjs"));//地区介绍导航链接
			
 })
    
</script>
</head>
<body>
<div class="wrapper">
	<div class="wrapper-left">
		<div class="wrapper-right">
			<div class="wrapper-main">
				<jsp:include page="/WEB-INF/views/web/Header.jsp"></jsp:include>
				<div class="main">
					<div class="position">
						<div class="clear"></div>
					</div>
					<div class="layout-content-left left" id="dqjs" >
						<h3><i class="icon-title left"></i><span class="left">大标题</span></h3>
						<ul>
							<li><span>标题1</span></li>
							<li><span>标题2</span></li>
							<li><span>标题3</span></li>
						</ul>
					</div>
					<div class="layout-content-right"> 
						
						<img src="Images/ad_2.jpg" id="picTest2" />
						<div style="padding:10px 4px 6px 4px; margin:4px 0px 20px 0px;auto;text-align: center; border: 1px solid #0092d4;"><span style="font-size: 28px; font-weight: bold;" id="title">标题</span></div>
						<div id="editor"> 富文本(格式不受当前页面影响，由用户自定义)<br />
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
