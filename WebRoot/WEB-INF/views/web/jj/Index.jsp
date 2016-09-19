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
    <link href="<%=path%>/resources/jj/Styles/xg.css" rel="stylesheet" />
    <link href="<%=path%>/resources/jj/Styles/css.css" rel="stylesheet" />
    <script src="<%=path%>/resources/jj/Scripts/jquery-1.8.2.js"></script>
    <script src="<%=path%>/resources/jj/Scripts/xg.js"></script>
    <script src="<%=path%>/resources/jj/Scripts/js.js"></script>
    
   
    
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
		

       
   //首页滚动
function noticeList(lmurl, lmId, size, target, newsurl, moreurl) {
    $.post(lmurl + lmId + "/" + size + ".json", {}, function (result) {
        var item='<h3 class="left"><i class="icon-title-left"></i><em><i class="icon-notice"></i></em><i class="icon-title-right"></i></h3><div id="notice" ><ul>';       
		
        $.each(result.rows, function (idx, obj) {
        	//item+='<li><i class="icon-list left"></i><span class="left">['+obj.pubDate+']</span><a href="' + newsurl + obj.id + '" target="_blank">'+obj.newsTitle+'</a></li>';
       		item+='<li><i class="icon-notice-list left"></i><span class="left">['+obj.pubDate+']</span><a href="' + newsurl + obj.id + '" target="_blank">'+obj.newsTitle+'</a></li>';
        });
        item = item + '</ul></div>';
        target.empty();
        target.html(item);
        XG.Scroll.init("notice", "ul", 1, 1, "left", 10000, 3000);
    }, "json");
}     


 //链接
function linkList1(lmurl, lmId, size, target) {
    $.post(lmurl + lmId + "/" + size + ".json", {}, function (result) {
        var item='';
        $.each(result.rows, function (idx, obj) {
           item=item+'<dd class="';
           if(idx%2==0){item+='left';}else{item+='right';}
           item=item+' text-center bg-img-link-'+idx+'"><div></div><a target="_blank" href="'+obj.origin+'">'+obj.newsTitle+'</a></dd>';
        });
        target.empty();
        target.html(item);
    }, "json");
}

 //链接
function linkList2(lmurl, lmId, size, target) {
    $.post(lmurl + lmId + "/" + size + ".json", {}, function (result) {
        var item='<option>----'+ result.title +'----</option>';
        $.each(result.rows, function (idx, obj) {
            item=item+'<option value="'+obj.origin+'">'+obj.newsTitle+'</option>';
        });
        target.empty();
        target.html(item);
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



 
function categoryTabInit(postURL, domID, categoryID, categoryURL, contentURL, categoryTabSize, contentSize) {	
    $.post(postURL, { "pId": categoryID, "size1": categoryTabSize, "size2": contentSize }, function (result) {
    	
        var $dom = $("#" + domID).addClass("box");
        var html = '<h3><i class="icon-title-left"></i><span>' + result.title + '</span><i class="icon-title-right"></i></h3>';
        html += '<h6><a href="' + categoryURL + categoryID + '">MORE&nbsp;&gt;&gt;</a></h6>';
        $dom.html(html);
        var $dl = $("<dl/>").appendTo($dom);
        $.each(result.category, function (index, obj) {
            $("<dt/>").html(obj.title).appendTo($dl);
            var $ul = $("<ul/>").appendTo($dom);
            $.each(obj.contents, function (idx, o) {
                $("<li/>").html('<i class="icon-list left"></i><span class="right">' + o.pubDate.substr(2) + '</span><a href="' + contentURL + o.id + '">' + o.newsTitle + '</a>').appendTo($ul);
            });
        });
        
	    $(".box ul li").hover(function () {
	        $(this).find("i").removeClass("icon-list").addClass("icon-list-hover");
	    }, function () {
	        $(this).find("i").removeClass("icon-list-hover").addClass("icon-list");
	    });
	     $("div.box").each(function (index, obj) {
	        var $boxTitleLeft = $("<i/>").addClass("icon-box-title-left");
	        var $boxTitleRight = $("<i/>").addClass("icon-box-title-right");
	        var $dt = $(obj).find("dl dt");
	        var $ul = $(obj).find("ul");
	        $dt.first().addClass("hover").append($boxTitleLeft).append($boxTitleRight);
	        $ul.addClass("none");
	        $ul.first().removeClass("none").addClass("block");
	        $dt.each(function (idx, o) {
	            $(o).hover(function () {
	                $dt.removeClass("hover");
	                $(this).addClass("hover").append($boxTitleLeft).append($boxTitleRight);
	                $ul.removeClass("block").addClass("none");
	                $ul.eq(idx).removeClass("none").addClass("block");
	            }, function () { });
	        });
	    });
    }, "json");
}


function tabInit(postURL, domID, categoryID, categoryURL, shownews, categoryTabSize, contentSize,width) {	
	$.ajax({  
        type: "post",  
       	url: postURL, 
       	data :{ "pId": categoryID, "size": categoryTabSize},
       	cache:false,  
        dataType:"json", 
        success: function(result){  
        	var $dom = $("#" + domID).addClass("box");
	        var html = '<h3><i class="icon-title-left"></i><span>' + result.title + '</span><i class="icon-title-right"></i></h3>';
	        html += '<h6><a href="' +categoryURL+ categoryID + '" target="_blank" >MORE&nbsp;&gt;&gt;</a></h6>';
	        $dom.html(html);
	        var $dl = $("<dl/>").appendTo($dom);//alert($dom.html());
	        $.each(result.tabs, function (index, obj) {
		         var $dt= $("<dt/>").html(obj.lmName).appendTo($dl);		         
		         var $div = $("<div/>").appendTo($dom);
		         if(obj.moduleId==392){
		         	$div.css("width","1188px").css("position","relative");
		         	imgsScroll(obj.id, contentSize, $div, shownews);
		         }else{
			         newsList(obj.id, contentSize, $div, shownews,width);
		          }
	        });
        }  
});

}

function newsList(lmId, size, target, newsurl,width) { 
	if(width){target.css("width",width+"px");}
	var $ul=$("<ul/>").addClass("word").appendTo(target);	
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
            	item=item+'<li><i class="icon-list left"></i><span class="right">' + o.pubDate.substr(2) + '</span><a href="' + newsurl + o.id + '" target="_blank">'+ o.newsTitle + '</a></li>';
             }); 
        	$ul.html(item);
        	
		     $("div.box").each(function (index, obj) {
		        var $boxTitleLeft = $("<i/>").addClass("icon-box-title-left");
		        var $boxTitleRight = $("<i/>").addClass("icon-box-title-right");
		        var $dt = $(obj).find("dl dt");
		        var $div = $(obj).find("div");
		        $dt.first().addClass("hover").append($boxTitleLeft).append($boxTitleRight);
		        $div.addClass("none");
		        $div.first().removeClass("none").addClass("block");
		        $dt.each(function (idx, o) {
		            $(o).hover(function () {
		                $dt.removeClass("hover");
		                $(this).addClass("hover").append($boxTitleLeft).append($boxTitleRight);
		                $div.removeClass("block").addClass("none");
		                $div.eq(idx).removeClass("none").addClass("block");
		            }, function () { });
		        });
		    });
		    $(".box ul li").hover(function () {
		        $(this).find("i").removeClass("icon-list").addClass("icon-list-hover");
		    }, function () {
		        $(this).find("i").removeClass("icon-list-hover").addClass("icon-list");
		    });
         }
   })
}


   //栏目图片横向滚动
function imgsScroll(lmId, size, target, newsurl) {
    $.post("<%=path%>/web/lmNewsPic/" + lmId + "/" + size + ".json", {}, function (result) {
    var id="subtab"+lmId; 
    target.attr("id",id);
	var item = '<ul class="pic">';
        $.each(result.rows, function (idx, obj) {
           if(obj.icoPath==null||obj.icoPath==""){
		        
		    }else{
            	item = item +'<li><a href="' + newsurl + obj.id + '" target="_blank"><img src="<%=path%>/cms/attach/download/'+obj.icoPath+'" /><h3>' + obj.newsTitle + '</h3> <h6></h6></a></li>';
        	}
        });
        item = item +'</ul>';
       	target.html(item);
       	XG.Scroll.init(id, "ul", 1, 1, "left", 1000, 2000);
       	
		     $("div.box").each(function (index, obj) {
		        var $boxTitleLeft = $("<i/>").addClass("icon-box-title-left");
		        var $boxTitleRight = $("<i/>").addClass("icon-box-title-right");
		        var $dt = $(obj).find("dl dt");
		        var $div = $(obj).find("div");
		        $dt.first().addClass("hover").append($boxTitleLeft).append($boxTitleRight);
		        $div.addClass("none");
		        $div.first().removeClass("none").addClass("block");
		        $dt.each(function (idx, o) {
		            $(o).hover(function () {
		                $dt.removeClass("hover");
		                $(this).addClass("hover").append($boxTitleLeft).append($boxTitleRight);
		                $div.removeClass("block").addClass("none");
		                $div.eq(idx).removeClass("none").addClass("block");
		            }, function () { });
		        });
		    });
    }, "json");
}

	var size = 6;
		var moreurl="<%=path%>/web/index?url=web/jj/List&lmid=";
		//var lmtaburl="<%=path%>/web/lmtabNews/";
		var lmurl="<%=path%>/web/lmNews/";
		var lmtaburl="<%=path%>/web/lmtab/";
		var lmurltj = "<%=path%>/web/lmNewsTj/";
		var shownews="<%=path%>/web/showNews2?url=web/jj/Detail&newsid=";
	    //var moreurl = "<%=path%>/web/preMoreNews?url=web/MoreNews&lmid=";
	    //var moreurl2 = "<%=path%>/web/preMoreNews?url=web/product&lmid=";
	   			
		var lmNewsPicurl="<%=path%>/web/lmNewsPic/";
		
		var newsPicUrl="<%=path%>/web/newsAttachs/";
		
		
		
		
$(function () {	
		  noticeList(lmurl, 138, 10, $("#gdxx"), shownews,moreurl);//通知滚动
		  imgsShow(lmNewsPicurl, 135, 5, $("#focus"), shownews);//幻灯片方式展示
		  tabInit(lmtaburl, "test1", 129, moreurl, shownews, 4, 8);//要闻
		  tabInit(lmtaburl, "test2", 130, moreurl, shownews, 4, 8);//信息公开
		  imgsFadeLoop(lmNewsPicurl,153,2, $("#ad"), shownews);//中间图片banner展示
		  
		  tabInit(lmtaburl, "ldjh", 131, moreurl, shownews, 4, 8);//领导讲话
		  tabInit(lmtaburl, "gzdt", 132, moreurl, shownews, 4, 8);//工作动态
		  tabInit(lmtaburl, "xxyd", 133, moreurl, shownews, 4, 8);//学习园地
		  tabInit(lmtaburl, "lzwh", 134, moreurl, shownews, 4, 8,1188);//廉政文化
		  
	});


    </script>
    </head>
  
 <body>
    <div class="wrapper">
		<jsp:include page="/WEB-INF/views/web/jj/Header.jsp"></jsp:include>
		<div class="clear"></div>
		<div class="row">
            <div class="notice" id="gdxx">
                
            </div>
        </div>
        <div class="clear"></div>
		
        <div class="row box-layout">
            <div class="width-380 box-separator left">
                <div class="focus">
                    <h3><i class="icon-title-left"></i><span>图片展示</span><i class="icon-title-right"></i></h3>
                    <div id="focus">
                        <ul>
                            <li>
                                <img src="Images/focus_1.jpg" />
                                <a href="detail.html">第一个标题第一个标题第一个标题</a>
                            </li>
                            <li>
                                <img src="Images/focus_2.jpg" />
                                <a href="detail.html">第二个标题第二个标题第二个标题</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="width-380 box-separator left" >
                <div id="test1" >
                </div>
            </div>
            <div class="width-380 box-separator left">
                <div id="test2">
                   
                </div>
            </div>
        </div>
        <div class="clear"></div>
        
        <div class="row">
            <div id="ad">
                
            </div>
        </div>
        <div class="clear"></div>
        <div class="row box-layout">
            <div class="width-380 box-separator left">
                <div id="ldjh">
                   
                </div>
            </div>
            <div class="width-380 box-separator left">
                <div id="gzdt">
                   
                </div>
            </div>
            <div class="width-380 box-separator left">
                <div id="xxyd">
                   
                </div>
            </div>
        </div>
        <div class="clear"></div>
        <div class="row box-layout">
            <div class="width-1200 box-separator left">
                <div id="lzwh">
                   
                </div>
            </div>
           
        </div>
        <div class="clear"></div>
        <jsp:include page="/WEB-INF/views/web/jj/Footer.jsp"></jsp:include>
    </div>
</body>
</html>
