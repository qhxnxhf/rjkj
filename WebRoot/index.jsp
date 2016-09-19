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
    <script src="<%=path%>/resources/dcld/Scripts/jquery.cookie.js"></script>
    
    <script src="<%=path%>/resources/dcld/Scripts/xg.js"></script>
    <script src="<%=path%>/resources/dcld/Scripts/js.js"></script>
    <title>西宁东车辆段欢迎您！</title>
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
    	var item='<h3 class="left"><i class="icon-notice left"></i></h3>';
		item+='<div id="notice"><ul>';
        $.each(result.rows, function (idx, obj) {
        	item+='<li><i class="icon-list left"></i><span class="left">['+obj.pubDate+']</span><a href="' + newsurl + obj.id + '" target="_blank">'+obj.newsTitle+'</a></li>';
        });
        item = item + '</ul></div>';

        target.empty();
        target.html(item);
        XG.Scroll.init("notice", "ul", 1, 1, "left", 10000, 3000);
    }, "json");
}       
  
  //新闻列表，带更多链接
function newsList(lmurl, lmId, size, target, newsurl, moreurl) {
    $.post(lmurl + lmId + "/" + size + ".json", {}, function (result) {
    	var item='<h4><a href="' + moreurl + lmId + '"  target="_blank" class="block"><i class="icon-box-title-left left"></i><span class="bg-box-title left">'+result.title+'</span><i class="icon-box-title-right left"></i></a></h4>';
		item+='<div class="h4"></div>';
		item+='<ul>'
        $.each(result.rows, function (idx, obj) {
            //alert(obj.docName);
            //item = item + '<li style="width:' + w1 + ';"><i class="icon-list fl" ></i><a href="' + newsurl + obj.id + '" target="_blank" class="block fl" style="width:' + w2 + ';">' + obj.newsTitle + '</a><span class="fr">' + obj.pubDate + '</span></li>';
			item=item+'<li>'+(isNew(obj.pubDate)?'<em class="icon-new"></em>':'')+'<i class="icon-list left"></i><span class="right">' + obj.pubDate.substr(2) + '</span><a href="' + newsurl + obj.id + '" target="_blank" >'+ obj.newsTitle + '</a></li>';
                
        });
        item = item + '</ul>';

        target.empty();
        target.html(item);
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

//安全天数
function safeday(lmurl, orgId, target) {
    $.post(lmurl, {"orgId":orgId}, function (result) {
       var item='<table class="center"><colgroup><col class="col-0" /><col /><col class="width-12" /></colgroup>';
                
        $.each(result.rows, function (idx, obj) {
            item=item+'<tr><td class="col-0">' + obj.title + '</td><td><em>' + obj.days + '</em>天</td><td><i class="icon-safety"></i></td></tr>';    
            
        });
        item = item + '</table>';

        target.empty();
        target.html(item);
    }, "json");
}

 
   //栏目图片横向滚动
function imgsScroll(lmurl, lmId, size, target, newsurl) {
    $.post(lmurl + lmId + "/" + size + ".json", {}, function (result) {
        var item = '<ul>';
        
        $.each(result.rows, function (idx, obj) {
           if(obj.icoPath==null||obj.icoPath==""){
		        
		        }else
            item = item + '<li class="fl"><a href="' + newsurl + obj.id + '" target="_blank" title="' + obj.newsTitle + '"><img src="<%=path%>/cms/attach/download/'+obj.icoPath+'"/></a></li>';
        });
        item = item +"</ul>";
        target.empty();
        target.html(item);
        XG.Scroll.init("show", "ul", 1, 1, "left", 1000, 2000);
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

//验证码
function onImageClick(img){
	img.src="<c:url value='/security/jcaptcha.jpg?f=JJ73Vf1JrWyBJJ1V77ZbGPNzS9y8QnhpjlChcw5rmypzNddyR9Q7!1156727646!1440740788063"+new Date().getTime()+"'/>";
}

//登录
function login() {
		var loginName = document.getElementById("username");
		var password = document.getElementById("password");
		//var kaptcha = document.getElementById("kaptcha");
		
		if(loginName.value&&password.value){
			$(".login_info").html("正在登录中...");
			$.ajax({
  				type:"post",
				url:"<%=path%>/login/login_submit",
				dataType:"json",
				data: {"username":loginName.value,"password":password.value},
				error:function(xhr,msg,e){alert("查询失败");},
				success: function(result){
					  if(result == null){
						  $(".login_info").html("登陆失败！");
						  return false;
					  }
					  if(result.status=="true"||result.status==true){
						$.cookie("username", $("#username").val(), {path: "/", expires: 180});
						window.location="<%=path%>/layout/main"; 
					  
					  }else{
					  	 $(".login_info").html(result.message);
					  }
				  }
			});
		}
		else{
			$(".login_info").html("用户名或密码不能为空!");
		}
		//if(!kaptcha.value){
		//	errorMsg += "&nbsp;&nbsp;验证码不能为空!";
		//}
	}    
	
	//查询
    function searchHandler(){
    	//var lmid=$("#parentId").val();
		var kk=$("#search").val();
		kk=encodeURI(kk);
		//kk=encodeURIComponent(kk);
		//alert("<%=path%>/web/index?url=web/MoreNews&kwd="+kk);
		
		window.location="<%=path%>/web/index?url=web/MoreNews&kwd="+kk;
		
		//contentList("list","",kwd,"pager",10,5);
    }

		var size = 6;
		var lmurl="<%=path%>/web/lmNews/";
		var lmsurl="<%=path%>/web/lmsNews/";
		var lmurltj = "<%=path%>/web/lmNewsTj/";
		var shownews="<%=path%>/web/showNews/";
	    var moreurl = "<%=path%>/web/preMoreNews?url=web/MoreNews&lmid=";
	    var moreurl2 = "<%=path%>/web/preMoreNews?url=web/product&lmid=";
	    var moreurl3 = "<%=path%>/web/index?url=web/LmNews&lmid=";
	   			
		var lmNewsPicurl="<%=path%>/web/lmNewsPic/";
		
		var newsPicUrl="<%=path%>/web/newsAttachs/";
		
		var safeUrl="<%=path%>/cms/safe/safedays";
		
		
		
		
$(function () {
	
		  // onImageClick(document.getElementById("kaptchaImage"));//验证码刷新
		   imgsShow(lmNewsPicurl, 51, 5, $("#focus"), shownews);//图片新闻，幻灯片方式展示
		   linkList1(lmurl, 47, 4, $("#linklist"));//部门链接
		   linkList2(lmurl, 66, 20, $("#link1"));//部门链接1
		   linkList2(lmurl, 67, 20, $("#link2"));//部门链接2
		   noticeList(lmurl, 121, 10, $("#sygd"), shownews,moreurl);//首页滚动
		   newsList(lmurl, 50, 7, $("#zxdt"), shownews,moreurl);//最新动态
		   newsList(lmurl, 61, 6, $("#sjwd"), shownews,moreurl);//上级文电
		   newsList(lmurl, 62, 6, $("#dnwj"), shownews,moreurl);//段内文件
		   newsList(lmsurl, 122, 6, $("#zxgz"), shownews,moreurl3);//会议记要,可以显示子栏目中的内容
		   newsList(lmurl, 64, 6, $("#aqtb"), shownews,moreurl);//安全通报
		   newsList(lmurl, 65, 6, $("#khtb"), shownews,moreurl);//考核通报
		   
		   safeday(safeUrl, 1, $("#safety"));
		   
		   newsPicList(newsPicUrl,2,5, $("#ad"), shownews);//中间图片banner展示
		   imgsScroll(lmNewsPicurl, 52, 7, $("#show"), shownews);//图片滚动展示
		   
		  var uname= $.cookie('username');
		  if(uname==undefined){
		  	$("#username").val("");
		  }else{
		  	$("#username").val(uname);
		  }
		   
		});

    </script>
    </head>

    <body>
	<div class="wrapper">
		<div class="wrapper-left">
			<div class="wrapper-right">
				<div class="wrapper-main">
					<jsp:include page="/WEB-INF/views/web/Header.jsp"></jsp:include>
					<div class="row">
                        <div id="sygd" class="notice">
                        </div>
                    </div>
					<div class="row">
                        <div class="layout-right right">
                            <div class="search box-border">
								<input id="search" type="text" class="left" /><i class="icon-title-search right pointer" onclick="searchHandler()"></i>
                            </div>
                            <div class="box">
                                <h3>
                                    <i class="icon-box-login left"></i>
                                    <span class="font-hei left">用户登录<br />
                                        <em class="login_info"></em></span>
                                </h3>
                                <div class="h3"></div>
                                <div class="login">
                                    <table>
                                        <colgroup>
                                            <col class="col-0" />
                                            <col class="col-1" />
                                            <col class="col-2" />
                                        </colgroup>
                                        <tr>
                                            <td><i class="icon-login-user"></i></td>
                                            <td>
                                                <input type="text" id="username"/>
                                            </td>
                                            <th rowspan="3" class="text-center">
												<button class="pointer font-hei" onclick="login()">登录</button>
                                            </th>
                                        </tr>
                                        <tr>
                                            <td><i class="icon-login-password"></i></td>
                                            <td>
												<input type="password" id="password"/>
                                            </td>
                                        </tr>
                                     </table>
                                </div>
                            </div>
                            <div class="box">
                                <h3>
                                    <i class="icon-box-safety left"></i>
                                    <span class="font-hei left">安全动态<br />
                                    </span>
                                </h3>
                                <div class="h3"></div>
                                <div id="safety" class="safety bg-safety">                                    
                                </div>
                            </div>
                            <div class="img-link">
                                <dl id="linklist">
                                    <dd class="left text-center bg-img-link-5">
                                        <div></div>
                                        <a href="<%=path%>/web/preMoreNews?url=web/MoreNews&lmid=22">机关科室</a>
                                    </dd>
                                    <dd class="right text-center bg-img-link-2">
                                        <div></div>
                                        <a href="<%=path%>/web/preMoreNews?url=web/MoreNews&lmid=63">生产车间</a>
                                    </dd>
                                    <dd class="left text-center bg-img-link-3">
                                        <div></div>
                                        <a href="<%=path%>/web/preMoreNews?url=web/MoreNews&lmid=28">专项工作</a>
                                    </dd>
                                    <dd class="right text-center bg-img-link-4">
                                        <div></div>
                                        <a href="<%=path%>/web/preMoreNews?url=web/MoreNews&lmid=26">工作查询</a>
                                    </dd>
                                </dl>
                                <select  class="clear" id="link1" onChange="javascript:window.open(this.options[this.selectedIndex].value)" >
                                    
                                </select>
                                <select class="clear" id="link2" onChange="javascript:window.open(this.options[this.selectedIndex].value)" >
                                    
                                </select>
                            </div>
                        </div>
                        <div class="layout-left">
                            <div class="width-465 left">
                                <div class="focus">
                                    <div id="focus">
                                    </div>
                                </div>
                            </div>
                            <div class="width-465 right bg-center-1">
                                <div id="zxdt" class="box">
                                    
                                </div>
                            </div>
                            <div class="clear-left"></div>
                            <div class="width-465 left bg-center-1">
                                <div id="sjwd" class="box">
                                    
                                </div>
                            </div>
                            <div class="width-465 right bg-center-2">
                                <div id="dnwj" class="box">
                                    
                                </div>
                            </div>
                        </div>
                        <div class="clear"></div>
					</div>
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
                    <div class="row">
						<iframe id="report" src="<%=path%>/cms/news/showNews/1" frameborder="0"></iframe>
                    </div>
                    <div class="row">
                        <div id="show">
                        </div>
                    </div>
                    <div class="row">
                        <div class="width-390 left bg-center-2">
                            <div id="zxgz" class="box">
                                
                            </div>
                        </div>
                        <div class="box-separator left"></div>
                        <div class="width-390 left bg-center-1">
                            <div id="aqtb" class="box">
                                
                            </div>
                        </div>
                        <div class="width-390 right bg-center-2">
                            <div id="khtb" class="box">
                                
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
