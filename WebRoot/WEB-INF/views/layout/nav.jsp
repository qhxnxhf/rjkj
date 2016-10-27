<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!--框架必需start-->
<script type="text/javascript" src="<%=path%>/resources/libs/js/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/resources/libs/js/language/cn.js"></script>
<script type="text/javascript" src="<%=path%>/resources/libs/js/framework.js"></script>
<link href="<%=path%>/resources/libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="<%=path%>/resources/"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->

<!--修正IE6不支持PNG图start-->
<style>
img {
	behavior:url("<%=path%>/resources/libs/js/method/pngFix/pngbehavior.htc");
}
</style>
<!--修正IE6不支持PNG图end-->

<!--动画方式入场效果start-->
<script type="text/javascript" src="<c:url value='/resources/libs/js/pic/jomino.js' />"></script>
<script>
	 $(function(){
		$(".navIcon").jomino();
	});
	function customHeightSet(contentHeight){
		$("#scrollContent").height(contentHeight);
	}
	
	
	//修改	
	function onEdit(){
		
		top.Dialog.open({
			URL:"<c:url value='/sys/user/preEdit?userid=" + "${loginUser.id}' />",
			Title:"个人基础信息设置",Width:500,Height:400});
	}
	
	//更改密码	
	function onPswd(){
		
		top.Dialog.open({
			URL:"<c:url value='/sys/user/prePswdSet?userid=" + "${loginUser.id}' />",
			Title:"更改密码",Width:500,Height:200});
	}
	
	//更改密码	
	function openUrl(url){
		//top.Dialog.open({URL:url,ID:winid,Title:title,ShowMaxButton:true,ShowMinButton:true,MinToTask:true}).max();
		
		javascript:window.open(url);
	}
	
	function openTab(id,title,url){
		//top.Dialog.open({URL:url,ID:winid,Title:title,ShowMaxButton:true,ShowMinButton:true,MinToTask:true}).max();
		
		top.frmright.tabAddHandler(id,title,url);
		
	}
	
</script>
<!--动画方式入场效果end-->
  </head>
  
  <body>

<div id="scrollContent">
	<table width="100%" height="90%">
		<tr><td class="ali02 ver02">
			<div  style="width:800px;margin:0 auto;">
			<table class="ali01 ver01">
				<tr height="146">
					<td width="260">
						<div class="navIcon"  style="display:none;">
							<div class="navIcon_left"><img src="<%=path%>/resources/libs/icons/png/01.png"/></div>
							<div class="navIcon_right">
								<div class="navIcon_right_title">个人中心</div>
								<div class="navIcon_right_con" onclick="onEdit()">
									个人信息
								</div>
								<div class="navIcon_right_con" onclick="onPswd()">
									更改密码
								</div>
							</div>
							<div class="clear"></div>
						</div>
					</td>
					<td>
						<div class="navIcon" onclick="openTab(15,'收件箱','<%=path%>/layout/openUrl?url=/cms/news/InboxM')" style="display:none;">
							<div class="navIcon_left"><img src="<c:url value='/resources/libs/icons/png/04.png' />"/></div>
							<div class="navIcon_right">
								<div class="navIcon_right_title">预留</div>
								<div class="navIcon_right_con">
								
									
								</div>
							</div>
							<div class="clear"></div>
						</div>
					</td>
					<td>
						<div class="navIcon" onclick="openTab(14,'发件箱','<%=path%>/layout/openUrl?url=/cms/news/NewsM')" style="display:none;">
							<div class="navIcon_left"><img src="<c:url value='/resources/libs/icons/png/32.png' />"/></div>
							<div class="navIcon_right">
								<div class="navIcon_right_title">预留</div>
								<div class="navIcon_right_con">
									
								</div>
							</div>
							<div class="clear"></div>
						</div>
					</td>
					
					
					
				</tr>
				<tr height="146">
					
					<td width="260">
						<div class="navIcon" onclick="top.Dialog.alert('预留')" style="display:none;">
							<div class="navIcon_left"><img src="<%=path%>/resources/libs/icons/png/02.png"/></div>
							<div class="navIcon_right">
								<div class="navIcon_right_title">预留</div>
								<div class="navIcon_right_con">
									
									<div class="navIcon_right_con" >
									
									</div>
									
								</div>
							</div>
							<div class="clear"></div>
						</div>
					</td>
					<td width="260">
						<div class="navIcon" onclick="top.Dialog.alert('预留')" style="display:none;">
							<div class="navIcon_left"><img src="<%=path%>/resources/libs/icons/png/69.png"/></div>
							<div class="navIcon_right">
								<div class="navIcon_right_title">预留</div>
								<div class="navIcon_right_con">
									
									<div class="navIcon_right_con" >
									
									</div>
									
								</div>
							</div>
							<div class="clear"></div>
						</div>
					</td>
					
					<td width="260">
						<div class="navIcon" onclick="top.Dialog.alert('预留')" style="display:none;">
							<div class="navIcon_left"><img src="<%=path%>/resources/libs/icons/png/38.png"/></div>
							<div class="navIcon_right">
								<div class="navIcon_right_title">预留</div>
								<div class="navIcon_right_con">
									
									<div class="navIcon_right_con">
									
									</div>
									
								</div>
							</div>
							<div class="clear"></div>
						</div>
					</td>
					
				</tr>
				
				
				<tr height="146">
					
					<td width="260">
						<div class="navIcon" onclick="top.Dialog.alert('预留')" style="display:none;">
							<div class="navIcon_left"><img src="<%=path%>/resources/libs/icons/png/71.png"/></div>
							<div class="navIcon_right">
								<div class="navIcon_right_title">预留</div>
								<div class="navIcon_right_con">
									
									<div class="navIcon_right_con" >
								
									</div>
									
								</div>
							</div>
							<div class="clear"></div>
						</div>
					</td>
					<td>
						<div class="navIcon" onclick="top.Dialog.alert('预留')" style="display:none;">
							<div class="navIcon_left"><img src="<%=path%>/resources/libs/icons/png/39.png"/></div>
							<div class="navIcon_right">
								<div class="navIcon_right_title">预留</div>
								<div class="navIcon_right_con">
									
								</div>
							</div>
							<div class="clear"></div>
						</div>
					</td>
					<td>
						<div class="navIcon" onclick="top.Dialog.alert('预留')" style="display:none;">
							<div class="navIcon_left"><img src="<%=path%>/resources/libs/icons/png/50.png"/></div>
							<div class="navIcon_right">
								<div class="navIcon_right_title">预留</div>
								<div class="navIcon_right_con">
									
								</div>
							</div>
							<div class="clear"></div>
						</div>
					</td>
				</tr>
				
				
			</table>
			</div>
		</td></tr>
	</table>
</div>
  </body>
</html>
