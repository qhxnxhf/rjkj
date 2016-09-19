<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!--框架必需start-->
<script type="text/javascript" src="<c:url value='/resources/libs/js/jquery.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/libs/js/language/cn.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/libs/js/framework.js' />"></script>
<link href="<c:url value='/resources/libs/css/import_basic.css' />" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="<c:url value='/resources/' />"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->

<!--动态选项卡start-->
<script type="text/javascript" src="<c:url value='/resources/libs/js/nav/dynamicTab.js' />"></script>
<!--动态选项卡end-->

<script>

function test(title){
		alert(title);
}
function tabAddHandler(mid,mtitle,murl){
		tab.add( {
		id :mid,
		title :mtitle,
		url :murl,
		isClosed :true
	});
	tab.activate(mid)
}
var currentTabId;
 var tab;	
$( function() {
	 tab = new TabView( {
		containerId :'tab_menu',
		pageid :'page',
		cid :'tab1',
		position :"top"
	});
	tab.add( {
		id :'tab_index',
		title :"快捷导航",
		url :"nav",
		isClosed :false
	});
	
	$("body").bind("dynamicTabActived",function(e,tabId){
		if(tabId=="tab_index"){
			top.frmleft.hideAll();
		}
		else{
			top.frmleft.getNodesByParam("id",tabId);
		}
		currentTabId=tabId;
	})
});

function getCurrentTabId(){
	return currentTabId;
}

function customHeightSet(contentHeight){
	$("#page").height(contentHeight-30);
}
</script>
  </head>
  
  <body>
<div id="tab_menu"></div>
<div id="page"></div>
  </body>
</html>
