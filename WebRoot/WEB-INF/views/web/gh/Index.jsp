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
    
    <title> </title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="<%=path%>/resources/gh/Styles/xg.css" rel="stylesheet" />
    <link href="<%=path%>/resources/gh/Styles/css.css" rel="stylesheet" />
    <script src="<%=path%>/resources/gh/Scripts/jquery-1.8.2.js"></script>
    <script src="<%=path%>/resources/gh/Scripts/xg.js"></script>
    <script src="<%=path%>/resources/gh/Scripts/js.js"></script>
    <title></title>
	
	<script type="text/javascript">
			
		//图片幻灯片展示
		function imgsShow(lmurl, lmId, size, target, newsurl) {
		    $.post(lmurl + lmId + "/" + size + ".json", {}, function (result) {
		        var item = "<ul>";
		        var focus_width = 600;
		        var focus_height = 262;
		        var text_height = 18;
		        var swf_height = focus_height + text_height;
		        var pics = '', links = '', texts = '';
		        //var fs="${fileserverurl}";
		        $.each(result.rows, function (idx, obj) {
		        if(obj.icoPath==null||obj.icoPath==""){
		        
		        }else
		        	item=item+'<li><img src="<%=path%>/cms/attach/download/'+obj.icoPath+'" /><a href="' + newsurl + obj.id + '" target="_blank" >' + obj.newsTitle + '</a></li>';
		           
		        });
		        var item = item+"</ul>";
		        target.empty();
		        target.html(item);
		        XG.Focus.init("focus");
		    }, "json");
		}
		

       
   //新闻列表，带更多链接
function newsBox(lmurl, lmId, size, target, newsurl, moreurl) {
    $.post(lmurl + lmId + "/" + size + ".json", {}, function (result) {
    	var item='<h3><span class="bg-title red font-hei"><i></i>'+result.title+'</span><a href="' + moreurl + lmId + '" target="_blank">MORE&nbsp;&gt;&gt;</a></h3><div class="word"><ul>'
		
        $.each(result.rows, function (idx, obj) {
            //alert(obj.docName);
            //item = item + '<li style="width:' + w1 + ';"><i class="icon-list fl" ></i><a href="' + newsurl + obj.id + '" target="_blank" class="block fl" style="width:' + w2 + ';">' + obj.newsTitle + '</a><span class="fr">' + obj.pubDate + '</span></li>';
			//item=item+'<li>'+(isNew(obj.pubDate)?'<em class="icon-new"></em>':'')+'<i class="icon-list left"></i><span class="right">' + obj.pubDate.substr(2) + '</span><a href="' + newsurl + obj.id + '" target="_blank" >'+ obj.newsTitle + '</a></li>';
            item=item+'<li><i class="icon-list red left"></i><span class="right">' + obj.pubDate.substr(2) + '</span><a href="' + newsurl + obj.id + '" target="_blank" >'+ obj.newsTitle + '</a></li>';
        });
        item = item + '</ul></div>';

        target.empty();
        target.html(item);
       	target.find("li").hover(function () {
	        $(this).find("i").toggleClass("red yellow");
	    }, function () {
	        $(this).find("i").toggleClass("red yellow");
	    });         
    }, "json");
}



   //中间的长条图片
function imgsFadeLoop(lmurl, lmId, size, target, newsurl) {
    $.post(lmurl + lmId + "/" + size + ".json", {}, function (result) {
       var item = '<ul>';
        $.each(result.rows, function (idx, obj) {
           if(obj.icoPath==null||obj.icoPath==""){
		        
		    }else{
            	item = item + '<li class="fl"><a href="' + newsurl + obj.id + '" target="_blank" ><img src="<%=path%>/cms/attach/download/'+obj.icoPath+'"/></a></li>';
				//item=item+'<li><img src="<%=path%>/cms/attach/download/'+obj.icoPath+'" /><a href="' + newsurl + obj.id + '" target="_blank" >' + obj.newsTitle + '</a></li>';
		    }
        });
        item = item +"</ul>";
        target.empty();
		target.html(item);
		//XG.Scroll.init("ad", "ul", 1, 1, "up", 1000, 3000);
		XG.FadeLoop.init(target.attr("id"), 1000, 3000);
    }, "json");
}


function tabInit(postURL, domID, categoryID, categoryURL, shownews, categoryTabSize, contentSize) {	
	$.ajax({  
        type: "post",  
       	url: postURL, 
       	data :{ "pId": categoryID, "size": categoryTabSize},
       	cache:false,  
        dataType:"json", 
        success: function(result){  
        	var $dom = $("#" + domID);
        	var color = "";
        	if ($dom.hasClass("red")) { color = "red"; }
        	if ($dom.hasClass("yellow")) { color = "yellow"; }
	        var html = '<h3><span class="bg-title '+color+' font-hei"><i></i>' + result.title + '</span><a href="' +categoryURL+ categoryID + '" target="_blank">MORE&nbsp;&gt;&gt;</a></h3>';
            
			$dom.html(html);
	        var $dl = $("<dl/>").appendTo($dom);//alert($dom.html());
	        $.each(result.tabs, function (index, obj) {
		         var $dt= $("<dt/>").html(obj.lmName).appendTo($dl);		         
		         var $div = $("<div/>").addClass("word").appendTo($dom);
			     var $ul=$("<ul/>").appendTo($div);
			     
			     newsList(obj.id, contentSize, $ul, shownews,color);
	        });
	        
			$dom.each(function (index, obj) {
		        var $tabLeft = $("<i/>").addClass("icon-tab current-left " + color);
		        var $tabRight = $("<i/>").addClass("icon-tab current-right " + color);
		        var $dt = $(obj).find("dl dt");
		        var $div = $(obj).find("div");
		        $dt.first().addClass("hover").append($tabLeft).append($tabRight);
		        $div.addClass("none");
		        $div.first().removeClass("none").addClass("block");
		        $dt.each(function (idx, o) {
		            $(o).hover(function () {
		                $dt.removeClass("hover");
		                $(this).addClass("hover").append($tabLeft).append($tabRight);
		                $div.removeClass("block").addClass("none");
		                $div.eq(idx).removeClass("none").addClass("block");
		            }, function () { });
		        });
		    });	 
        }  
});

}

function newsList(lmId, size, target, newsurl,color) { 
		
    $.ajax({  
        type: "post",  
       	url: "<%=path%>/web/lmNews/" + lmId + "/" + size + ".json", 
       	data :{},
       //	cache:false,  
       //	async:false,
        dataType:"json", 
        success: function(result){
       		var item="";
        	$.each(result.rows, function (idx, o) {
            	//item=item+'<li><i class="icon-list left"></i><span class="right">' + o.pubDate.substr(2) + '</span><a href="' + newsurl + o.id + '" target="_blank">'+ o.newsTitle + '</a></li>';
             	item=item+'<li><i class="icon-list '+color+' left"></i><span class="right">' + o.pubDate.substr(2) + '</span><a href="' + newsurl + o.id + '" target="_blank">'+ o.newsTitle + '</a></li>';
                            
             }); 
        	target.html(item);
        	target.find("li").hover(function () {
		        $(this).find("i").toggleClass("red yellow");
		    }, function () {
		        $(this).find("i").toggleClass("red yellow");
		    }); 
         }
   });
}

//栏目图片横向滚动
function imgsScroll(lmurl, lmId, size, target, newsurl) {
    $.post(lmurl + lmId + "/" + size + ".json", {}, function (result) {
        var item = '<ul>';
        $.each(result.rows, function (idx, obj) {
           if(obj.icoPath==null||obj.icoPath==""){
		        
		        }else
            //item = item + '<li class="fl"><a href="' + newsurl + obj.id + '" target="_blank" title="' + obj.newsTitle + '"><img src="<%=path%>/cms/attach/download/'+obj.icoPath+'"/></a></li>';
        	item = item + '<li><a href="' + newsurl + obj.id + '" target="_blank" ><img src="<%=path%>/cms/attach/download/'+obj.icoPath+'"/><h3>' + obj.newsTitle + '</h3><h6></h6></a></li>';
        
        });
        item = item +"</ul>";
        target.empty();
        target.html(item);
       // XG.Scroll.init("show", "ul", 1, 1, "left", 1000, 2000);
        XG.Scroll.init("scroll", "ul", 1, 1, "left", 3000, 2000);
    }, "json");
}

	var size = 6;
		var moreurl="<%=path%>/web/index?url=web/gh/List&lmid=";
		var moreurl2="<%=path%>/web/index?url=web/gh/List2&lmid=";
		//var lmtaburl="<%=path%>/web/lmtabNews/";
		var lmurl="<%=path%>/web/lmNews/";
		var lmtaburl="<%=path%>/web/lmtab/";
		var lmurltj = "<%=path%>/web/lmNewsTj/";
		var shownews="<%=path%>/web/showNews2?url=web/gh/Detail&newsid=";
	    //var moreurl = "<%=path%>/web/preMoreNews?url=web/MoreNews&lmid=";
	    //var moreurl2 = "<%=path%>/web/preMoreNews?url=web/product&lmid=";
	   			
		var lmNewsPicurl="<%=path%>/web/lmNewsPic/";
		
		var newsPicUrl="<%=path%>/web/newsAttachs/";
		
		
		
		
$(function () {	
		 
		  imgsShow(lmNewsPicurl, 188, 5, $("#focus"), shownews);//幻灯片方式展示
		  newsBox(lmurl, 189, 10, $("#ghxx"), shownews, moreurl2);//工会信息
		  
		  tabInit(lmtaburl, "zhxx", 182, moreurl, shownews, 4, 8);//综合信息
		  tabInit(lmtaburl, "wqzx", 183, moreurl, shownews, 4, 8);//维权在线
		  
		  imgsFadeLoop(lmNewsPicurl,217,2, $("#ad"), shownews);//中间图片banner展示
		  
		  tabInit(lmtaburl, "zzbz", 184, moreurl, shownews, 4, 8);//组织保障
		  tabInit(lmtaburl, "qzsc", 185, moreurl, shownews, 4, 8);//群众生产
		  
		  imgsScroll(lmNewsPicurl, 190, 8, $("#scroll"), shownews);//职工风采 图片滚动展示
		  
	});


    </script>
	
	
  </head>
  
  <body>
    <div class="wrapper">
        
        
        <jsp:include page="/WEB-INF/views/web/gh/Header.jsp"></jsp:include>
		<div class="clear"></div>
        <div class="row box-layout">
            <div class="width-590 box-separator left focus">
                <div id="focus">
                    
                </div>
            </div>
            <div class="width-590 box-separator left">
                <div class="box red" id="ghxx">
                    
                </div>
            </div>
        </div>
        <div class="clear"></div>
        <div class="row">
            <div id="ad">
                <ul>
                    <li>
                        <img src="Images/ad_1.jpg" />
                    </li>
                    <li>
                        <img src="Images/ad_2.jpg" />
                    </li>
                </ul>
            </div>
        </div>
        <div class="clear"></div>
        <div class="row box-layout">
            <div class="width-590 box-separator left">
                <div class="box yellow" id="zhxx">
                    <h3><span class="bg-title yellow font-hei"><i></i>栏目栏目</span><a href="List.html">MORE&nbsp;&gt;&gt;</a></h3>
                    <dl>
                        <dt>栏目栏目</dt>
                        <dt>栏目栏目</dt>
                        <dt>栏目栏目</dt>
                    </dl>
                    <div class="word">
                        <ul>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">1一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                        </ul>
                    </div>
                    <div class="word">
                        <ul>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">2一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                        </ul>
                    </div>
                    <div class="word">
                        <ul>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">3一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="width-590 box-separator left">
                <div class="box yellow" id="wqzx">
                    <h3><span class="bg-title yellow font-hei"><i></i>栏目栏目</span><a href="List.html">MORE&nbsp;&gt;&gt;</a></h3>
                    <dl>
                        <dt>栏目栏目</dt>
                        <dt>栏目栏目</dt>
                        <dt>栏目栏目</dt>
                    </dl>
                    <div class="word">
                        <ul>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">1一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                        </ul>
                    </div>
                    <div class="word">
                        <ul>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">2一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                        </ul>
                    </div>
                    <div class="word">
                        <ul>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">3一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list yellow left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div class="clear"></div>
        <div class="row box-layout">
            <div class="width-590 box-separator left">
                <div class="box red" id="zzbz">
                    <h3><span class="bg-title red font-hei"><i></i>栏目栏目</span><a href="List.html">MORE&nbsp;&gt;&gt;</a></h3>
                    <dl>
                        <dt>栏目栏目</dt>
                        <dt>栏目栏目</dt>
                        <dt>栏目栏目</dt>
                    </dl>
                    <div class="word">
                        <ul>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">1一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                        </ul>
                    </div>
                    <div class="word">
                        <ul>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">2一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                        </ul>
                    </div>
                    <div class="word">
                        <ul>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">3一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="width-590 box-separator left">
                <div class="box red" id="qzsc">
                    <h3><span class="bg-title red font-hei"><i></i>栏目栏目</span><a href="List.html">MORE&nbsp;&gt;&gt;</a></h3>
                    <dl>
                        <dt>栏目栏目</dt>
                        <dt>栏目栏目</dt>
                        <dt>栏目栏目</dt>
                    </dl>
                    <div class="word">
                        <ul>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">1一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                        </ul>
                    </div>
                    <div class="word">
                        <ul>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">2一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                        </ul>
                    </div>
                    <div class="word">
                        <ul>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">3一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                            <li><i class="icon-list red left"></i><span class="right">15/09/09</span><a href="Detail.html">一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息一条信息</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div class="clear"></div>
        <div class="row">
            <div id="scroll">
                <ul>
                    <li>
                        <a href="Detail.html">
                            <img src="Images/show_1.jpg" />
                            <h3>标题标题</h3>
                            <h6></h6>
                        </a>
                    </li>
                    <li>
                        <a href="Detail.html">
                            <img src="Images/show_2.jpg" />
                            <h3>标题标题</h3>
                            <h6></h6>
                        </a>
                    </li>
                    <li>
                        <a href="Detail.html">
                            <img src="Images/show_3.jpg" />
                            <h3>标题标题</h3>
                            <h6></h6>
                        </a>
                    </li>
                    <li>
                        <a href="Detail.html">
                            <img src="Images/show_4.jpg" />
                            <h3>标题标题</h3>
                            <h6></h6>
                        </a>
                    </li>
                    <li>
                        <a href="Detail.html">
                            <img src="Images/show_5.jpg" />
                            <h3>标题标题</h3>
                            <h6></h6>
                        </a>
                    </li>
                    <li>
                        <a href="Detail.html">
                            <img src="Images/show_6.jpg" />
                            <h3>标题标题</h3>
                            <h6></h6>
                        </a>
                    </li>
                    <li>
                        <a href="Detail.html">
                            <img src="Images/show_7.jpg" />
                            <h3>标题标题</h3>
                            <h6></h6>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="clear"></div>
        <jsp:include page="/WEB-INF/views/web/gh/Footer.jsp"></jsp:include>
    </div>
  </body>
</html>
