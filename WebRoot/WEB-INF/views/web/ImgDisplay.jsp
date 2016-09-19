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
    <link href="<c:url value='/resources/dcld/Styles/xg.css'/>" rel="stylesheet" />
    <link href="<c:url value='/resources/dcld/Styles/css.css'/>" rel="stylesheet" />
    <script src="<c:url value='/resources/dcld/Scripts/jquery-1.8.2.js'/>"></script>
   	<script src="<c:url value='/resources/dcld/Scripts/xg.js'/>"></script>
   	<script src="<c:url value='/resources/dcld/Scripts/js.js'/>"></script>
    
    <title></title>

<script type="text/javascript">
var imgs;
var index=0;
var max=0;

//图片列表
function newsPicList(lmurl,size,target,newsurl){
		$.post(lmurl+"/"+size+".json",
				{},
				function(result){
					
					imgs=result.rows;
					max=imgs.length;
					index=0;
					var obj=imgs[0];
					
					$("#imgshow").empty();
					$("#imgbrief").empty();
					$("#imgbody").css("background-image","url(<%=path%>/cms/attach/download/"+obj.id+")");
					if(obj.brief==undefined||obj.brief=="null") obj.brief="";
					$("#imgpge").html((index+1)+"/"+max) ;
					$("#imgbrief").html("["+obj.title+"]"+obj.brief) ;
				},
				"json");
	}

//上一张图片
function getPro(){
	if(index-1<0){
		index=0;
		
	}else{
		index=index-1;
	}
	var obj=imgs[index];
	$("#imgshow").empty();
	$("#imgbrief").empty();
	
	
	$("#imgbody").css("background-image","url(<%=path%>/cms/attach/download/"+obj.id+")");
	if(obj.brief==undefined||obj.brief=="null") obj.brief="";
	$("#imgpge").html((index+1)+"/"+max) ;
	$("#imgbrief").html("["+obj.title+"]"+obj.brief) ;
					
}

//下一张图片
function getNext(){
	if(index+1>=max){
		index=max-1;
		
	}else{
		index=index+1;
	}
	var obj=imgs[index];
	$("#imgshow").empty();
	$("#imgbrief").empty();
	//$("#imgshow").attr("src","${fileserverurl}"+obj.filePath);
	
	$("#imgbody").css("background-image","url(<%=path%>/cms/attach/download/"+obj.id+")");
	if(obj.brief==undefined||obj.brief=="null") obj.brief="";
	$("#imgpge").html((index+1)+"/"+max) ;
	$("#imgbrief").html("["+obj.title+"]"+obj.brief) ;
}
	
var size=10;
var lmurltj = "<%=path%>/web/lmNewsTj/";
var lmurl="<%=path%>/web/newsAttachs/${news.id}";
var shownews="<%=path%>/web/showNews/";

$(function () {
	newsPicList(lmurl,200,$("#qy11"),shownews);
	
	$("#imgbody").mouseover(function(e){
	
		$("#jtl").show();
		$("#jtr").show();
	});
	
	$("#imgbody").mouseout(function(e){
	
		$("#jtl").hide();
		$("#jtr").hide();
	});
	
	$("#jtl").hide();
	$("#jtr").hide();
	
 	//imgsLink(lmurltj, 200, 6, $("#hzlj"), "${fileserverurl}");//合作链接
 	
})



</script>
<style>
	.bkimg{ 
		width:1000px;
		padding:20px 4px 20px 4px; 
		margin:10px auto;
		background: url("<%=path%>/resources/web/Images/bkimg/14.jpg") 
	}
	
   .title1{
	   width:900px; 
	   margin:0 auto;
	   border-bottom: #707070 1px solid; 
	   padding-bottom: 10px;
	   color: #000; 
	   font-size: 22px; 
	   font-weight: bold ;
	   text-align: center;
   }
    .title2{
	    width:900px; 
	    margin:10px auto;
	    font-size: 12px;
	    text-align: center;
    }
    
    .content{
    	line-height:25px;
    }
    
    .newsbody{
    	 width:900px; 
    	 margin:20px auto;
    }
    
	.jtl{
		float:left;
		width:20%;
		height:100%;
		cursor:pointer;
		background:no-repeat center center;
		background-image:url('<%=path%>/resources/web/Images/left1.png') ;
	}
	
	.jtr{
	
		float:right;
		width:20%;
		height:100%;
		cursor:pointer;
		background:no-repeat center center;
		background-image:url('<%=path%>/resources/web/Images/right1.png') ;
	}
	
	.imgbody{
		background:no-repeat center center; 
		width:900px;
		height:500px;
		margin:0px auto;
		border:1px solid #dedede;"
	}
	
	.imgpge{
		float:left;
		height:50px;
		width:80px;
		margin:0px auto;
		padding:6px 5px 4px 5px; 
		font-size: 34px;
	    text-align: center;
		
	}
	
	.imgbrief{
		float:left;
		height:50px;
		width:800px;
		margin:0px auto;
		padding:6px 5px 4px 5px; 
		font-size: 14px;
		
	}
	
</style>

</head>
<body class="bg-website">
	
    <div class="wrapper">
        <div class="wrapper-left">
            <div class="wrapper-right">
                <div class="wrapper-main">
                
<jsp:include page="/WEB-INF/views/web/Header.jsp"></jsp:include>
	<div class="main center" >
		
        <div class="bkimg "> 
        
			<p class="title1">${news.newsTitle} ${message}</p>
			<p class="title2">
				作者：${news.author}&nbsp;&nbsp;来源：${news.origin}&nbsp;&nbsp; 发布日期：${news.pubDate}&nbsp;&nbsp; 访问量：${news.readCount} 
			</p>	
			<div id="imgbody" class="imgbody">
            	<div class="jtr" id="jtr" onClick="getNext();" ></div>
            
            	<div class="jtl" id="jtl" onClick="getPro();" ></div>
            </div>
            <div class="newsbody">
            	<div id="imgpge" class="imgpge">
            
            	</div>
            	<div id="imgbrief" class="imgbrief"> </div>
            </div>
            <div class="clear"></div>
            <p class="title1"></p>
            <div class="newsbody" >
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
