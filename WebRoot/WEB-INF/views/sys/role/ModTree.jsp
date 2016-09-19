<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%> 
<% 
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>基本表格模板</title>
<!--框架必需start-->
<script type="text/javascript" src="<c:url value='/resources/libs/js/jquery.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/libs/js/language/cn.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/libs/js/framework.js' />"></script>
<link href="<c:url value='/resources/libs/css/import_basic.css' />" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="<c:url value='/resources/' />" scrollerY="false"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->

<!--树组件start -->
<script type="text/javascript" src="<c:url value='/resources/libs/js/tree/ztree/ztree.js' />"></script>
<link href="<c:url value='/resources/libs/js/tree/ztree/ztree.css' />" rel="stylesheet" type="text/css"/>
<!--树组件end -->

<!--布局控件start-->
<script type="text/javascript" src="<c:url value='/resources/libs/js/nav/layout.js' />"></script>
<!--布局控件end-->


<style>
.l-layout-center{
    border:none!important;
}
.l-layout-left{
    border-bottom:none!important;
}
.l-layout-drophandle-left{
    width: 10px;
}
</style>
</head>
<body>
<fieldset> 
     <legend>${role.roleName}</legend>
 
<div id="scrollContent" style="widht:100%;height:360px;overflow-x:hidden;">
	      <input type="hidden" id="roleId" name="roleid" value="${role.id}"/>
	      <input id="CSRFToken" name="CSRFToken" type="hidden" value="${CSRFToken}"/>
	      <div>
			  <ul id="treeMod" class="ztree"></ul>
		  </div>
</div>

</fieldset>
<div class="height_15"></div>
 <input type="button" value="保存选择结果" onclick="getSelectValue();"/>
 <script type="text/javascript">
 	
	//定义tree
	var treeMod = null;
	//定义选中的树节点
	var selectTreeNode = null;
	
	
	//树属性配置
	var selectionSetting = {
			check: {
        		enable: true,
        		chkboxType: { "Y" : "ps", "N" : "ps" }
    		}
		};
		
	//初始化函数
	function initComplete(){
		$.post("<c:url value='/sys/role/treeAll' />", {"roleid":"${role.id}"}, function(result){
    		//此处返回的是treeNodes
			treeMod =$.fn.zTree.init($("#treeMod"), selectionSetting, result.treeNodes);}, "json");
	}
	
	
    //选择结果
	function getSelectValue(){
		//得到选中的数据集
		var checkedNodes = treeMod.getCheckedNodes(true);
		//得到未选中的数据集
		var checkedNodes2 = treeMod.getCheckedNodes(false);
		var msg = "";
		var roleid = document.getElementById("roleId").value;
		for(var i = 0; i < checkedNodes.length; i++){
			msg += "," + checkedNodes[i].id;
		}
		if(msg == ""){
			msg = "无选择";
			top.Dialog.confirm("确定要删除分的功能的吗？",function(){
		  	//删除记录
		  	 $.post("<c:url value='/sys/role/update2' />",
		  			{"ids":"","id":roleid,"CSRFToken":$("#CSRFToken").val()},
		  			function(result){
		  				closeWin();
					},"json");
			});
		}else{
		    msg = msg.substring(1);
		    $.post("<c:url value='/sys/role/update2' />",
		  			{"ids":msg,"id":roleid,"CSRFToken":$("#CSRFToken").val()},
		  			function(result){
		  				closeWin();
					},"json");
		}
		
	}
	
	
	function closeWin(){
	
	//刷新数据
	//top.frmright.page_101.refresh(update);
	﻿﻿var tabfrmId="page_"+top.frmright.getCurrentTabId();
	top.frmright.document.getElementById(tabfrmId).contentWindow.refresh(true);
	//关闭窗口
	top.Dialog.close();
}
	
	//处理高度自适应，每次浏览器尺寸变化时触发
	function customHeightSet(contentHeight){
		$(".cusBoxContent").height(contentHeight-55)
		$(".orgTreeContainer").height(contentHeight-30);
	}
 
 </script>
</body>
</html>