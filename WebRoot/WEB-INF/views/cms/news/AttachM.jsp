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
<title>修改</title>
<!--框架必需start-->
<script type="text/javascript" src="<c:url value='/resources/libs/js/jquery.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/libs/js/language/cn.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/libs/js/framework.js'/>"></script>
<link href="<c:url value='/resources/libs/css/import_basic.css'/>" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="<c:url value='/resources/'/>"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->

<!--数据表格start-->
<script src="<c:url value='/resources/libs/js/table/quiGrid.js'/>" type="text/javascript"></script>
<!--数据表格end-->

<!-- 表单验证start -->
<script src="<c:url value='/resources/libs/js/form/validationRule.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/libs/js/form/validation.js'/>" type="text/javascript"></script>
<!-- 表单验证end -->

<!-- 表单异步提交start -->
<script src="<c:url value='/resources/libs/js/form/form.js'/>" type="text/javascript"></script>
<!-- 表单异步提交end -->

</head>
<body>
	
	<div class="box1" id="formContent" whiteBg="true" >
	${news.newsTitle}
	<div style="margin: 0;padding: 5px 0 5px 0;">
		<div id="dataBasic"></div>
	</div>
	<form id="myFormId" >
	<table class="tableStyle" formMode="line" style="width:100%;" >
		<tr>
			<td ><div style="width:40px;" >序号</div></td>
			<td colspan="2">
				<input type="text" id="orderNum" name="orderNum" class="validate[required custom[onlyNumber]]"  style="width:90px;" />
				<input type="text"  id="id" name="id" disabled="true"  style="width:250px;"/>
			</td>
			<td rowspan="4">
				<div style="margin:0px; width:300px;height:220px;overflow:auto;">  
					<img src=""  border="0" id="addImg" width="280px" >
				</div> 
			</td>
		</tr>
		<tr>
			<td ><div style="width:40px; " >标题</div></td>
			<td colspan="2">
				
				<input type="text" id="title" name="title"  class="validate[required]"   style="width:350px;"/>
				<input id="CSRFToken" name="CSRFToken" type="hidden" value="${CSRFToken}"/>
			</td>
		</tr>
		<tr>
			<td><div style="width:40px;" >摘要</div></td>
			<td colspan="2">
				
				<textarea id="brief" name="brief"  style="width:350px;height:80px;" ></textarea>
			</td>
		</tr>
		
		
		<tr>
			<td colspan="3">
                <button type="button" onclick="save()" ><span class="icon_add">保  存</span></button>&nbsp;
                <button type="button" onclick="onNewsIcon()" ><span class="icon_add">新闻封面</span></button>&nbsp;
                <button type="button" onclick="onLmIcon()" ><span class="icon_add">栏目封面</span></button>&nbsp;
                <button type="button" onclick="onDel()" ><span class="icon_add">删  除</span></button>&nbsp;
				<input type="button" value="关  闭" onclick="top.Dialog.close()"/>
			</td>
		</tr>
	</table>
	</form>
	</div>
	
		
	
<!-- 异步提交start -->
<script type="text/javascript">


function initComplete(){
	//初始化grid组件
	initGrid();
	getToken();
   
}


//初始化Grid处理
function initGrid() {
		grid = $("#dataBasic").quiGrid({
		columns:[
				{ display: '序号', name: 'orderNum',  align: 'left', width: "10%"},
				{ display: '标题', name: 'title',     align: 'left', width: "30%",
				 	render : function(rowdata, rowindex, value, column){ return '<div title="'+rowdata.brief+'" class="qTip" >'+value+'</div>';}},
				{ display: '文件名', name: 'fileOrigName', align: 'left', width: "20%",
					render : function(rowdata, rowindex, value, column){ 
						return '<a target="_blank" href=<c:url value="/cms/attach/download/" />'+rowdata.id+' >'+value+'</a>';}},
				{ display: '文件类型', name: 'suffix',  align: 'left', width: "5%"},
				{ display: '大小', name: 'fileSize',     align: 'left', width: "10%"},
				{ display: '用途', name: 'status',     align: 'left', width: "10%",
					render : function(rowdata, rowindex, value, column){ 
						return renderStatus(value);}}
			  ],
			isScroll:true,
			url: "<c:url value='/cms/attach/table?newsId=${news.id}'/>", 
			sortName: 'orderNum',
			rownumbers:false,
			checkbox:false,
	        height: '250', 
	        width:"100%",
	        pageSize:10,
	        onSelectRow:selRow,
	        onAfterShowData:function(data){$(".qTip").tip({ auto:true ,arrowDirection:"left"});},
	        percentWidthMode:true
		});
}
	
//渲染节点
	function renderStatus(value){
		if(value==""||value=="1"){
           return "<font color=blue>新闻附件 </font>";
       	}
       	 if(value=="2"){
           return "<font color=green>新闻封面 </font>";
        }
        if(value=="3"){
           return "<font color=green>栏目封面</font>";
        }
	}

function selRow(node, rowindex, rowDomElement){
				$("#id").val(node.id);
				$("#orderNum").val(node.orderNum);
				$("#title").val(node.title);
				$("#brief").val(node.brief);
				$("#addImg").attr("src","<c:url value='/cms/attach/download/' />"+node.id);
}

function save(){
            var valid = $('#myFormId').validationEngine({returnIsValid: true});
            if(valid){
	            $.post("<c:url value='/cms/attach/saveAdd'/>",
		  			{"id":$("#id").val(),"CSRFToken":$("#CSRFToken").val(),"orderNum":$("#orderNum").val(),"title":$("#title").val(),"brief":$("#brief").val()},
		  			function(result){
		  				getToken();
		  				grid.loadData();
					},"json");
           }
}

function onNewsIcon() {
 var rowid=$("#id").val();
		if(rowid != null && rowid != ""){
		$.post("<c:url value='/cms/attach/setIcon'/>",
				{"attId":rowid,"CSRFToken":$("#CSRFToken").val()},
				function(result){
						getToken();
						handleResult(result);
				},
				"json");
		}else{
				top.Dialog.alert("请选择父级节点",null,null,null,2);
		}
}

function onLmIcon() {
 var rowid=$("#id").val();
		if(rowid != null && rowid != ""){
		$.post("<c:url value='/cms/attach/setLmIcon'/>",
				{"attId":rowid,"CSRFToken":$("#CSRFToken").val()},
				function(result){
						getToken();
						handleResult(result);
				},
				"json");
		}else{
				top.Dialog.alert("请选择父级节点",null,null,null,2);
		}
}
	
//删除	
function onDel(){
var rowid=$("#id").val();

	top.Dialog.confirm("确定要删除该记录吗？",function(){
		  	//删除记录
		  	$.post("<c:url value='/cms/attach/del'/>",
					{"attId":rowid,"CSRFToken":$("#CSRFToken").val()},
		  			function(result){
		  				getToken();
		  				handleResult(result);
					},"json");
					//刷新列表数据 
			});		
		
}

//删除	
	function onDelete(rowid,rowidx){
	alert(rowid);
		top.Dialog.confirm("确定要删除该记录吗？",function(){
		  
		  	//删除记录
		  	$.post("<c:url value='/cms/attach/del'/>",
		  			{"ids":rowid,"CSRFToken":$("#CSRFToken").val()},
		  			function(result){
		  				getToken();
		  				handleResult(result);
					},"json");
					//刷新表格数据 
					//grid.loadData();
			});
	}
	
//删除后的提示
function handleResult(result){
		if(result.status == "true"){
			grid.loadData();
		}else{
			top.Dialog.alert(result.message);
		}
		
	}

function getToken(){
  			$.ajax({
  				type:"get",
				url:"<c:url value='/csrf/token'/>",
				dataType:"json",
				error:function(xhr,msg,e){alert("查询失败");},
				success:function(html){
				   $("#CSRFToken").val(html.token);
				}
			});
}

//重置
function closeWin(){
	//刷新数据
	//top.frmright.page_101.refresh(update);
	﻿﻿var tabfrmId="page_"+top.frmright.getCurrentTabId();
	top.frmright.document.getElementById(tabfrmId).contentWindow.refresh();
	//关闭窗口
	top.Dialog.close();
}
</script>
<!-- 异步提交end -->	
</body>
</html>