<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=9" ></meta>
	<title>西宁东车辆段</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<!--框架必需start-->

<link href="<%=path%>/resources/libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link href="<%=path%>/resources/libs/skins/blue/style.css" rel="stylesheet" type="text/css" id="theme" themeColor="blue"/>
<link href="<%=path%>/resources/layout/skin/style.css" skinPath="<%=path%>/resources/layout/skin/" rel="stylesheet" type="text/css" id="skin" />

<script type="text/javascript" src="<%=path%>/resources/libs/js/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/resources/libs/js/language/cn.js"></script>
<script type="text/javascript" src="<%=path%>/resources/libs/js/main.js"></script>
<!--框架必需end-->

<!--弹窗组件start-->
<script type="text/javascript" src="<%=path%>/resources/libs/js/popup/drag.js"></script>
<script type="text/javascript" src="<%=path%>/resources/libs/js/popup/dialog.js"></script>
<!--弹窗组件end-->

<!--分隔条start-->
<script type="text/javascript" src="<%=path%>/resources/libs/js/nav/spliter.js"></script>
<!--分隔条end-->
<script>
function bookmarksite(title, url){
    if (window.sidebar) // firefox
        window.sidebar.addPanel(title, url, "");
    else 
        if (window.opera && window.print) { // opera
            var elem = document.createElement('a');
            elem.setAttribute('href', url);
            elem.setAttribute('title', title);
            elem.setAttribute('rel', 'sidebar');
            elem.click();
        }
        else 
            if (document.all)// ie
                window.external.AddFavorite(url, title);
}
function windowClose(){
window.opener=null;
  window.open('', '_self'); //IE7必需的.
  window.close();
}
function backHome(){
	document.getElementById("frmleft").contentWindow.homeHandler();
	document.getElementById("frmright").contentWindow.tabAddHandler("tab_index");
}




</script>
<style>
a {
	behavior:url("<%=path%>/resources/libs/js/method/focus.htc")
}
</style>

  </head>
  
  <body>
    
<div id="mainFrame">
<!--头部与导航start-->
<div id="hbox">
	<div id="bs_bannercenter">
	<div id="bs_bannerright">
	<div id="bs_bannerleft">	
	</div>
	</div>
	
	</div>
	<div id="bs_navcenter">
	<div id="bs_navleft">
	<div id="bs_navright">
		<div class="bs_nav">
			<div class="bs_navleft">
				<li class="fontTitle">字号:</li>
				<li class="fontChange"><span><a href="javascript:;" setFont="16">大</a></span></li>
				<li class="fontChange"><span><a href="javascript:;" setFont="14">中</a></span></li>
				<li class="fontChange"><span><a href="javascript:;" setFont="12">小</a></span></li>
				<div class="clear"></div>	
			</div>	
			<div class="float_left padding_top2 padding_left5">
				【今天是
				<script>
					var weekDayLabels = new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
					var now = new Date();
				    var year=now.getFullYear();
					var month=now.getMonth()+1;
					var day=now.getDate()
				    var currentime = year+"年"+month+"月"+day+"日 "+weekDayLabels[now.getDay()]
					document.write(currentime)
				</script>】
				当前用户：${loginUser.org.orgName}_${loginUser.userName}
			</div>	
			<div class="float_right padding_top2 padding_right5">
				<span class="icon_home hand" onclick='backHome()'>返回导航页</span>
				<span class="icon_fullscreen hand" id="fullSrceen" hideNav="true">开启全屏</span>
				
				<span class="icon_exit hand" onclick='top.Dialog.confirm("确定要退出系统吗",function(){window.location="<c:url value='/login/logout' />"});'>退出系统</span>
		
				<div class="clear"></div>
			</div>
			<div class="clear"></div>
		</div>
	</div>
	</div>
	</div>
</div>
<!--头部与导航end-->

<table width="100%" cellpadding="0" cellspacing="0" class="table_border0">
	<tr>
		<!--左侧区域start-->
		<td id="hideCon" class="ver01 ali01">
							<div id="lbox">
								<div id="lbox_topcenter">
								<div id="lbox_topleft">
								<div id="lbox_topright">
								</div>
								</div>
								</div>
								<div id="lbox_middlecenter">
								<div id="lbox_middleleft">
								<div id="lbox_middleright">
										<div id="bs_left">
										<IFRAME height="100%" width="100%"  frameBorder=0 id=frmleft name=frmleft src="<%=path%>/layout/preTree"  allowTransparency="true"></IFRAME>
										</div>
										<!--更改左侧栏的宽度需要修改id="bs_left"的样式-->
								</div>
								</div>
								</div>
								<div id="lbox_bottomcenter">
								<div id="lbox_bottomleft">
								<div id="lbox_bottomright">
									<div class="lbox_foot"></div>
								</div>
								</div>
								</div>
							</div>
		</td>
		<!--左侧区域end-->
		
		<!--分隔栏区域start-->
		<td class="spliter main_shutiao" targetId="hideCon" beforeClickTip="收缩面板" afterClickTip="展开面板" beforeClickClass="bs_leftArr" afterClickClass="bs_rightArr">
		</td>
		<!--分隔栏区域end-->
		
		<!--右侧区域start-->
		<td class="ali01 ver01"  width="100%">
							<div id="rbox">
								<div id="rbox_topcenter">
								<div id="rbox_topleft">
								<div id="rbox_topright">
								</div>
								</div>
								</div>
								<div id="rbox_middlecenter">
								<div id="rbox_middleleft">
								<div id="rbox_middleright">
									<div id="bs_right">
									       <IFRAME height="100%" width="100%" frameBorder=0 id=frmright name=frmright src="<%=path%>/layout/openUrl?url=layout/open"  allowTransparency="true"></IFRAME>
									</div>
								</div>
								</div>
								</div>
								<div id="rbox_bottomcenter" >
								<div id="rbox_bottomleft">
								<div id="rbox_bottomright">

								</div>
								</div>
								</div>
							</div>
		</td>
		<!--右侧区域end-->
	</tr>
</table>

<!--尾部区域start-->
<div id="fbox">
	<div id="bs_footcenter">
	<div id="bs_footleft">
	<div id="bs_footright">
		<span>西宁东车辆段&nbsp;&nbsp;版权所有</span>
       
	</div>
	</div>
	</div>
</div>
</div>
<!--尾部区域end-->

<!--浏览器resize事件修正start-->
<div id="resizeFix"></div>
<!--浏览器resize事件修正end-->

<!--窗口任务栏区域start-->
<div id="dialogTask" class="dialogTaskBg" style="display:none;"></div>
<!--窗口任务栏区域end-->

<!--载进度条start-->
<div class="progressBg" id="progress" style="display:none;"><div class="progressBar"></div></div>
<!--载进度条end-->
  </body>
</html>
