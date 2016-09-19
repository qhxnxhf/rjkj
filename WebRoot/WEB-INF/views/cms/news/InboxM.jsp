<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%> 
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<% 
	String path = request.getContextPath();
	//String CSRFToken=CsrfTokenUtils.getTokenForSession(request.getSession());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>基本表格模板</title>
<!--框架必需start-->
<script type="text/javascript" src="<c:url value='/resources/libs/js/jquery.js'/> "></script>
<script type="text/javascript" src="<c:url value='/resources/libs/js/language/cn.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/libs/js/framework.js'/>"></script>
<link href="<c:url value='/resources/libs/css/import_basic.css'/>" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="<c:url value='/resources/'/>" scrollerY="false"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->

<!--树组件start -->
<script type="text/javascript" src="<c:url value='/resources/libs/js/tree/ztree/ztree.js'/>"></script>
<link href="<c:url value='/resources/libs/js/tree/ztree/ztree.css'/>" rel="stylesheet" type="text/css"/>
<!--树组件end -->

<!--布局控件start-->
<script type="text/javascript" src="<c:url value='/resources/libs/js/nav/layout.js'/>"></script>
<!--布局控件end-->

<!--数据表格start-->
<script src="<c:url value='/resources/libs/js/table/quiGrid.js'/>" type="text/javascript"></script>
<!--数据表格end-->

<!-- 表单start -->
<script src="<c:url value='/resources/libs/js/form/form.js'/>" type="text/javascript"></script>
<!-- 表单end -->

<!-- 日期选择框start -->
<script type="text/javascript" src="<c:url value='/resources/libs/js/form/datePicker/WdatePicker.js'/>"></script>
<!-- 日期选择框end -->
 


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

<table width="100%" >
	<tr>
		
		<!--左侧区域end-->
		
		<!--右侧区域start-->
		<td width="100%" class="ver01" >
			
				<form action="<c:url value='/cms/news/search'/>" id="queryForm" method="post">
				<input type="hidden" id="parentId" name="parentId" value="${parentId}"/>
			<input id="CSRFToken" name="CSRFToken" type="hidden" value=""  />
			<div class="box2" panelTitle="查询"  showStatus="false">
			<table width="100%">
				<tr>
					<shiro:hasPermission name="NewsM:search">
					<td>标题：</td>
					<td>
						<input type="text" id="keyword" name="keyword"  style="width:120px;"/>
					</td>
					<td>发件人：</td>
					<td>
						<input type="text" id="senderName" name="senderName"  style="width:120px;"/>
					</td>
					
					<td>
					
						起止日期
						<input type="text" id="beginTime" name="beginTime" class="dateIcon"  style="width:90px;"/>
						<input type="text" id="endTime" name="endTime" class="dateIcon"  style="width:90px;"/>
						
					</td>
					
					<td>状态：</td>
					<td>
						<select  name='status'  data='{"list":[{"value":"0","key":"状态:全部"},{"value":"1","key":"编辑"},{"value":"2","key":"审核"},{"value":"3","key":"生效"},{"value":"4","key":"废止"}]}' selWidth="120" ></select>
			
					</td>
				
					
					<td><button type="button" onclick="searchHandler2()"><span class="icon_find">查询</span></button></td>
					</shiro:hasPermission>
					
				</tr>
			</table>
			</div>
			
			</form>
				
			<div style="margin: 0;padding: 0 5px 0 0;">
				<div id="dataBasic"></div>
			</div>
		</td>
		<!--右侧区域end-->
	</tr>
	</table>
 
 <script type="text/javascript">
 	
	//定义grid
	var grid = null;
	
	var gridArray=[];
	
	//定义tree
	var treeOrg = null;
	//定义选中的树节点
	var selectTreeNode = null;
	
	
	
	
	//树属性配置
	var selectionSetting = {
			async: {
				enable: true,
				dataType: 'JSON',
				//返回的JSON数据的名字
				//dataName: 'treeNodes',
				url: "<c:url value='/cms/news/tree'/>",
				autoParam: ["id"]
			},
			callback: {
			    onClick: zTreeSelect
			   
			} 
		};
		
	//初始化函数
	function initComplete(){
		//当提交表单刷新本页面时关闭弹窗
		top.Dialog.close();
		
		$("#layout1").layout({ leftWidth: 180,onEndResize:function(){
			  	grid.resetWidth();
			  }}); 
		
		//初始化tree
		//initTree();
		
		//初始化grid组件
		initGrid();
		
		getToken();
		//监听查询框的回车事件
		 $("#searchInput").keydown(function(event){
		 	if(event.keyCode==13){
				searchHandler();
			}
		 });
		 //获取当前主题风格，用于设置日期控件的皮肤
	    try {
	        dateSkin=themeColor;
	    }catch(e){}
	    //自定义点击触发日期控件
	    document.getElementById('beginTime').onfocus=function(){
	        var endtimeTf=$dp.$('endTime');
	        WdatePicker({
	            skin:dateSkin,onpicked:function(){endtimeTf.focus();},maxDate:'#F{$dp.$D(\'endTime\')}'
	        });
	    }
	    document.getElementById('endTime').onfocus=function(){
	        //这里设置了最大日期为当前日期，如果不需要则把maxDate:'%y-%M-%d'去掉
	    WdatePicker({skin:dateSkin,maxDate:'%y-%M-%d',minDate:'#F{$dp.$D(\'beginTime\')}'});
	    }
	}
	
	//初始化tree处理
 		function initTree() {
 			
			treeOrg =$.fn.zTree.init($("#tree"), selectionSetting);
	}
	
	
	
	//初始化Grid处理
	function initGrid() {
		grid = $("#dataBasic").quiGrid({
			columns: [	
                     			{ display: '发件人', name: 'sender.userName',  align: 'left',width: "5%"},
                     			{ display: '发送时间',  name: 'sendDate', align: 'center', width:"10%"},
                     			
                     			{ display: '文件标题',  name: 'news.newsTitle', align: 'left', width:"20%"}, 
                     			
                     			{ display: '留言信息',  name: 'leavewd', align: 'center', width:"20%"}, 
                     			{ display: '回复信息',  name: 'replywd', align: 'center', width:"20%"},
                     			{ display: '签收时间',  name: 'receiveDate', align: 'center', width:"10%"}, 
                     			{ display: '收件状态',  name: 'status', align: 'center', width:"5%",
				 					render : function(rowdata, rowindex, value, column){ return renderStatue(rowdata,value);}},
				 				{ display: '操作', isAllowHide: false, align: 'center', width:150,
									render: function (rowdata, rowindex, value, column){
	                 	    				return '<div class="padding_top4 padding_left5">'
	                                 + '<shiro:hasPermission name="NewsM:edit"><span class="img_edit hand" title="签收" onclick="onOpen(' + rowdata.id + ')"></span></shiro:hasPermission>' 
	                                 + '<shiro:hasPermission name="NewsM:send"><span class="img_email hand" title="转发" onclick="onSend(' + rowdata.news.id + ')"></span></shiro:hasPermission>' 
	                                 
	                                 + '</div>';
		                 }
		        }
                     ], 

                     isScroll:true, height: '100%',width:'100%', sortName: 'id',percentWidthMode:false,pageSize:50,checkbox:false,rownumbers:true,
                     url: '<%=path%>/cms/inbox/table'
         
         
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
	
	
	
	//签收	
	function onOpen(rowid){
		top.Dialog.open({
			URL:"<c:url value='/cms/inbox/open/'/>" + rowid,
			Title:"签收",Width:1300,Height:700,ShowMaxButton:true,ShowMinButton:true});
  
	}
	
	
	
	//信息互递	
	function onSend(rowid){
		top.Dialog.open({
			URL:"<c:url value='/cms/send/preEdit?newsId='/>" + rowid+"&url=cms/news/SendM",
			Title:"信息互递",Width:920,Height:650,ShowMaxButton:true,ShowMinButton:true});
	}
	
	
	
	//删除后的提示
	function handleResult(result){
		if(result.status == "true"){
			top.Dialog.alert(result.message,null,null,null,1);
			grid.loadData();
			//treeOrg.reAsyncChildNodes(selectTreeNode, "refresh");
		}else{
			top.Dialog.alert(result.message);
		}
	}
	
	
    
    //查询
    function searchHandler(){
    	 var query = $("#queryForm").formToArray(); 
		 grid.setOptions({ params : query});
		 //页号重置为1
		 grid.setNewPage(1);
		//刷新表格数据 
		grid.loadData();
    }
    
    //全表查询
    function searchHandler2(){
    	//$("#parentId").val("");
    	 var query = $("#queryForm").formToArray(); 
		 grid.setOptions({ params : query});
		 //页号重置为1
		 grid.setNewPage(1);
		//刷新表格数据 
		grid.loadData();
    }
    
    
    
    //重置查询
    function resetSearch(){
    	$("#queryForm")[0].reset();
		searchHandler();
    }
    
    //刷新表格数据并重置排序和页数
    function refresh(isUpdate){
    	if(!isUpdate){
    		//重置排序
        	grid.options.sortName='id';
        	grid.options.sortOrder="desc";
        	//页号重置为1
    		grid.setNewPage(1);
    	}
    	
    	grid.loadData();
    	//treeOrg.reAsyncChildNodes(selectTreeNode, "refresh");
    	
    }
	
	//处理高度自适应，每次浏览器尺寸变化时触发
	function customHeightSet(contentHeight){
		$(".cusBoxContent").height(contentHeight-55)
		$(".orgTreeContainer").height(contentHeight-30);
	}
 
 </script>
</body>
</html>