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

//新闻图片列表
function newsPicList(lmurl,newsid,size,target,newsurl){
		$.post(lmurl+"/"+newsid+"/"+size+".json",
				{},
				function(result){
					var item = '<ul>';
			        $.each(result.rows, function (idx, obj) {
			            item = item + '<li class="fl"><a href="'+ obj.brief + '" target="_blank" title="' + obj.title + '"><img src="<%=path%>/cms/attach/download/'+obj.id+'"/></a></li>';
			        });
			        item = item +"</ul>";
			        target.empty();
			        target.html(item);
					//XG.Scroll.init("ad", "ul", 1, 1, "up", 1000, 3000);
  					XG.FadeLoop.init("ad", 1000, 3000);
				},
				"json");
	}
	
	
//新闻列表，带更多链接
function newsList(lmurl, lmId, size, target, newsurl, moreurl) {
    $.post(lmurl + lmId + "/" + size + ".json", {}, function (result) {
    	var item='<h3><span class="bg-title-red font-hei">'+result.title+'</span><a href="' + moreurl + lmId + '"  target="_blank" class="block">MORE&nbsp;&gt;&gt;</a></h3>';
		
		item+='<ul>'
        $.each(result.rows, function (idx, obj) {
            //alert(obj.docName);
            item = item +'<li><i class="icon-list-red left"></i><span class="right">' + obj.pubDate.substr(2) + '</span><a href="' + newsurl + obj.id + '" target="_blank">'+ obj.newsTitle + '</a></li>';
			//item=item+'<li>'+(isNew(obj.pubDate)?'<em class="icon-new"></em>':'')+'<i class="icon-list left"></i><span class="right">' + obj.pubDate.substr(2) + '</span><a href="' + newsurl + obj.id + '" target="_blank" >'+ obj.newsTitle + '</a></li>';
                
        });
        item = item + '</ul>';

        target.empty();
        target.html(item);
    }, "json");
}


	var size = 6;
		var moreurl="<%=path%>/web/index?url=web/zg/List&lmid=";
		var lmurl="<%=path%>/web/lmNews/";
		//var shownews="<%=path%>/web/showNews/";
		var shownews="<%=path%>/web/showNews2?url=web/zg/Detail&newsid=";
		var lmNewsPicurl="<%=path%>/web/lmNewsPic/";
		
		
		
		
		
		
$(function () {	
		 
		  imgsShow(lmNewsPicurl, 158, 5, $("#focus"), shownews);//幻灯片方式展示
		  newsList(lmurl, 168, 7, $("#zgdt"), shownews,moreurl);//政工动态
		  newsList(lmurl, 161, 7, $("#zbgz"), shownews,moreurl);//支部工作
		  newsList(lmurl, 162, 7, $("#dwwj"), shownews,moreurl);//党委文件
		  newsList(lmurl, 159, 7, $("#ldjh"), shownews,moreurl);//领导讲话
		  newsList(lmurl, 172, 7, $("#xxzl"), shownews,moreurl);//学习资料
		  imgsFadeLoop(lmNewsPicurl,175,2, $("#ad"), shownews);//中间图片banner展示
			
		  newsList(lmurl, 164, 7, $("#cxzy"), shownews,moreurl);//创造争优
		  newsList(lmurl, 163, 7, $("#dnpp"), shownews,moreurl);//党内品牌
		  newsList(lmurl, 169, 7, $("#zgdy"), shownews,moreurl);//政工调研
		  
		  newsList(lmurl, 265, 7, $("#zxxx"), shownews,moreurl);//创造争优
		  newsList(lmurl, 266, 7, $("#fzdy"), shownews,moreurl);//党内品牌
		  newsList(lmurl, 267, 7, $("#hyjy"), shownews,moreurl);//政工调研
		  
		   newsList(lmurl, 173, 7, $("#jljj"), shownews,moreurl);//交流借鉴
		  newsList(lmurl, 174, 7, $("#whyd"), shownews,moreurl);//文化园地
		  newsList(lmurl, 170, 7, $("#wlyq"), shownews,moreurl);//网络舆情
		  
		   newsList(lmurl, 165, 7, $("#twwj"), shownews,moreurl);//团委文件
		  newsList(lmurl, 166, 7, $("#twtz"), shownews,moreurl);//团委通知
		  newsList(lmurl, 167, 7, $("#twyx"), shownews,moreurl);//团委要讯	
			
		  // newsPicList(newsPicUrl,2,5, $("#ad"), shownews);//中间图片banner展示
		  imgsScroll(lmNewsPicurl, 160, 8, $("#scroll"), shownews);//党员风采 图片滚动展示
	});


    </script>
    
</head>
<body>
    <div class="wrapper">
        <jsp:include page="/WEB-INF/views/web/zg/Header.jsp"></jsp:include>
        
		<div class="clear"></div>
        <div class="row box-layout">
            <div class="width-400 box-separator left focus">
                <div id="focus">
                    
                </div>
            </div>
            <div class="width-400 box-separator left">
                <div class="box" id="zgdt">
                    
                </div>
            </div>
            <div class="width-360 box-separator left">
                <div class="box" id="zbgz">
                    
                </div>
            </div>
        </div>
        <div class="clear"></div>
        <div class="row box-layout">
             <div class="width-400 box-separator left">
                <div class="box" id="dwwj">
                    
                </div>
            </div>
            <div class="width-400 box-separator left">
                <div class="box" id="ldjh">
                    
                </div>
            </div>
            <div class="width-360 box-separator left">
                <div class="box" id="xxzl">
                    
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
            <div class="width-400 box-separator left">
                <div class="box" id="cxzy">
                    
                </div>
            </div>
            <div class="width-400 box-separator left">
                <div class="box" id="dnpp">
                    
                </div>
            </div>
            <div class="width-360 box-separator left">
                <div class="box" id="zgdy">
                    
                </div>
            </div>
        </div>
        <div class="clear"></div>
        <div class="row box-layout">
            <div class="width-400 box-separator left">
                <div class="box" id="zxxx">
                    
                </div>
            </div>
            <div class="width-400 box-separator left">
                <div class="box" id="fzdy">
                    
                </div>
            </div>
            <div class="width-360 box-separator left">
                <div class="box" id="hyjy">
                    
                </div>
            </div>
        </div>
        
        <div class="clear"></div>
        <div class="row box-layout">
            <div class="width-400 box-separator left">
                <div class="box" id="jljj">
                    
                </div>
            </div>
            <div class="width-400 box-separator left">
                <div class="box" id="whyd">
                    
                </div>
            </div>
            <div class="width-360 box-separator left">
                <div class="box" id="wlyq">
                    
                </div>
            </div>
        </div>
        <div class="clear"></div>
        <div class="row box-layout">
            <div class="width-400 box-separator left">
                <div class="box" id="twwj">
                    
                </div>
            </div>
            <div class="width-400 box-separator left">
                <div class="box" id="twtz">
                    
                </div>
            </div>
            <div class="width-360 box-separator left">
                <div class="box" id="twyx">
                    
                </div>
            </div>
        </div>
        <div class="clear"></div>
        <div class="row">
            <div id="scroll">
                <ul class="pic">
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
        <jsp:include page="/WEB-INF/views/web/zg/Footer.jsp"></jsp:include>
    </div>
</body>
</html>
