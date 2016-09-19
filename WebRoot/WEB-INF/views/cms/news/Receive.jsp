<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>
	修改
</title>
<!--框架必需start-->
<script type="text/javascript" src="<c:url value='/resources/libs/js/jquery.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/libs/js/language/cn.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/libs/js/framework.js'/>"></script>
<link href="<c:url value='/resources/libs/css/import_basic.css'/>" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="<c:url value='/resources/'/>"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->



<!-- 表单验证start -->
<script src="<c:url value='/resources/libs/js/form/validationRule.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/libs/js/form/validation.js'/>" type="text/javascript"></script>
<!-- 表单验证end -->

<!-- 表单异步提交start -->
<script src="<c:url value='/resources/libs/js/form/form.js'/>" type="text/javascript"></script>
<!-- 表单异步提交end -->


<!--布局控件start-->
<script type="text/javascript" src="<c:url value='/resources/libs/js/nav/layout.js'/>"></script>
<!--布局控件end-->

<!--数据表格start-->
<script src="<c:url value='/resources/libs/js/table/quiGrid.js'/>" type="text/javascript"></script>
<!--数据表格end-->




</head>
<body>
	
	<form id="myFormId" action="<c:url value='/cms/inbox/save'/>" method="post" target="frmright">
	<div class="box1" id="formContent" whiteBg="true">
	<table class="tableStyle" formMode="line" >
		
		
		<tr>
			<td colspan="4">
				<IFRAME height="800px" width="100%" frameBorder=0 id=contentfm name=contentfm src=<c:url value='/cms/news/showNews/'/>${rec.news.id}  allowTransparency="true"></IFRAME>
			</td>
		</tr>
		
		
		
		<tr>
			<td >留言：</td>
			<td>
			<input type="hidden" name="id" value="${rec.id}"/>
			<textarea name="leavewd"  style="width:350px;height:50px;" >${rec.leavewd}</textarea>
			
			</td>
			<td >回复：</td>
			<td>
			<textarea name="replywd"  style="width:350px;height:50px;" >${rec.replywd}</textarea>
			
			
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<input type="submit" value="回复"/>
				<input type="button" value="转发" onclick="onSend(${rec.news.id})"/>
				<input type="button" value="关闭" onclick="top.Dialog.close()"/>
			</td>
		</tr>
		
	</table>
		<div style="margin: 0;padding: 0 5px 0 0;">
			<div id="dataBasic"></div>
		</div>
	</div>
	</form>
	
	
<!-- 异步提交start -->
<script type="text/javascript">

//定义grid
var grid = null;

function initComplete(){
	$("#layout1").layout({ leftWidth: 180,onEndResize:function(){
			  	grid.resetWidth();
			  }}); 
	//初始化grid组件
	initGrid();
	
    //表单提交
    $('#myFormId').submit(function(){ 
	    //判断表单的客户端验证是否通过
			var valid = $('#myFormId').validationEngine({returnIsValid: true});
			if(valid){
			   $(this).ajaxSubmit({
			        //表单提交成功后的回调
			        success: function(responseText, statusText, xhr, $form){
			        	//alert(responseText);
			        	var obj = jQuery.parseJSON(responseText);
			            top.Dialog.alert(obj.message,function(){
			            	closeWin();
			            })
			        }
			    }); 
			 }
		    //阻止表单默认提交事件
		    return false; 
	});
	

	
}


//初始化Grid处理
function initGrid() {
		grid = $("#dataBasic").quiGrid({
			columns: [
                     			{ display: '收件人', name: 'taker.userName',  align: 'left',width: "10%"},
                     			{ display: '发送时间',  name: 'sendDate', align: 'center', width:"20%"},
                     			{ display: '留言信息',  name: 'leavewd', align: 'center', width:"20%"}, 
                     			{ display: '回复信息',  name: 'replywd', align: 'center', width:"20%"},
                     			{ display: '签收时间',  name: 'receiveDate', align: 'center', width:"20%"}, 
                     			{ display: '收件状态',  name: 'status', align: 'center', width:"10%",
				 					render : function(rowdata, rowindex, value, column){ return renderStatue(rowdata,value);}}
                     ], 

                     isScroll:true, width:'100%', sortName: 'id',percentWidthMode:false,pageSize:20,checkbox:false,rownumbers:true,
                     url: '<%=path%>/cms/send/taker?newsId='+${rec.news.id}
         
         
		});
	}

//渲染节点
	function renderStatue(rowdata,value){
		if(value=="1"){
           return '<font color=red>待签收</font>';
       	}
        if(value=="2"){
          return '<font color=green>已签收</font>';
        }
        if(value=="3"){
           return '<font color=yellow>禁用</font>';
        }
        
	}
	
	//信息互递	
	function onSend(rowid){
		top.Dialog.open({
			URL:"<c:url value='/cms/send/preEdit?newsId='/>" + rowid+"&url=cms/news/SendM",
			Title:"信息互递",Width:920,Height:700,ShowMaxButton:true,ShowMinButton:true});
	}
	
//重置
function closeWin(){
	
	//刷新数据
	//top.frmright.page_101.refresh(update);
	﻿﻿var tabfrmId="page_"+top.frmright.getCurrentTabId();
	top.frmright.document.getElementById(tabfrmId).contentWindow.refresh(true);
	//关闭窗口
	top.Dialog.close();
}
</script>
<!-- 异步提交end -->	
</body>
</html>