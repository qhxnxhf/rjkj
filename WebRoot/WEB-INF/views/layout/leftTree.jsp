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
<script type="text/javascript" src="<%=path%>/resources/libs/js/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/resources/libs/js/language/cn.js"></script>
<script type="text/javascript" src="<%=path%>/resources/libs/js/framework.js"></script>
<link href="<%=path%>/resources/libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link href="<%=path%>/resources/layout/skin/style.css" skinPath="<%=path%>/resources/layout/skin/" rel="stylesheet" type="text/css" id="skin"  scrollerY="false"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->

<!-- 树型抽屉导航start-->
<script type="text/javascript" src="<%=path%>/resources/libs/js/tree/ztree/ztree.js"></script>
<link href="<%=path%>/resources/libs/js/tree/ztree/ztree.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="<%=path%>/resources/libs/js/nav/treeAccordion_addtab.js"></script>
<!-- 树型抽屉导航end -->
<style>
	.ztree li span.zbutton.diy01_ico_open, .ztree li span.zbutton.diy01_ico_close{width:24px!important;height:24px!important;padding-top:0;}
</style>
<script type="text/javascript">
	var zNodes =${ztNodes};
	var fixedObj=60;
	
	function customHeightSet(contentHeight){
		$("#scrollContent").height(contentHeight-fixedObj);
	}
</script>
  </head>
  
<body leftFrame="true">
<div class="padding_top5 ali02" style="height:30px;">
<table width="100%">
	
	<tr>
		<td colspan="2" class="ali02"><a onclick="showAll()">全部展开</a>&nbsp;&nbsp;<a onclick="hideAll()">全部收缩</a></td>
	</tr>
</table>
</div>
<div id="scrollContent" style="overflow-x:hidden;">
	<div>
		<ul id="treeDemo" class="ztree ztree_accordition"></ul>
	</div>
</div>				
</body>
</html>
