<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%> 
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<% 
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>基本表格模板</title>
<!--框架必需start-->
<script type="text/javascript" src="<c:url value='/resources/libs/js/jquery.js' />" ></script>
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
		
		<!--右侧区域start-->
		<td width="100%" class="ver01" >
		<shiro:hasPermission name="RoleM:search">
			<div class="box2" panelTitle="查询"  showStatus="false">
				<form action="<c:url value='/sys/role/search'/>" id="queryForm" method="post">
				<input id="CSRFToken" name="CSRFToken" type="hidden" value=""  />
			<table>
				<tr>
					<td>角色名称：</td>
					<td>
						<input type="text" id="searchInput" name="roleName"/>
					</td>
					<td><button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button></td>
					<td><button type="button" onclick="resetSearch()"><span class="icon_reload">重置查询</span></button></td>
				</tr>
			</table>
			
			</form>
				</div>
				</shiro:hasPermission>
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
		
	//初始化函数
	function initComplete(){
		//当提交表单刷新本页面时关闭弹窗
		top.Dialog.close();
		
		$("#layout1").layout({ leftWidth: 180,onEndResize:function(){
			  	grid.resetWidth();
			  }}); 
		
		//初始化grid组件
		initGrid();
		getToken();
		//监听查询框的回车事件
		 $("#searchInput").keydown(function(event){
		 	if(event.keyCode==13){
				searchHandler();
			}
		 })
	}
	
	//初始化Grid处理
	function initGrid() {
		grid = $("#dataBasic").quiGrid({
			columns:[
				{ display: '角色名称', name: 'roleName',     align: 'left', width: "10%"},
				{ display: '角色编码', name: 'roleCode',     align: 'left', width: "10%"},
				{ display: '操作', isAllowHide: false, align: 'center', width:120,
						 render: function (rowdata, rowindex, value, column){
	                 	    return '<div class="padding_top4 padding_left5">'
	                                  + '<shiro:hasPermission name="RoleM:edit"><span class="img_edit hand" title="修改" onclick="onEdit(' + rowdata.id + ')"></span></shiro:hasPermission>' 
	                                  + '<shiro:hasPermission name="RoleM:module"><span class="img_user_group hand" title="功能分配" onclick="onModule2(' + rowdata.id + ')"></span></shiro:hasPermission>'
	                                   + '<shiro:hasPermission name="RoleM:delete"><span class="img_delete hand" title="删除" onclick="onDelete(' + rowdata.id+','+rowindex + ')"></span></shiro:hasPermission>'
	                             + '</div>';
		                 }
		            }
			  ],
		 url: '<c:url value='/sys/role/rolelist'/>', sortName: 'id',rownumbers:true,checkbox:true,
         height: '100%', width:"100%",pageSize:10,percentWidthMode:true,
        
         toolbar:{
        	 items:[
        	 	<shiro:hasPermission name="RoleM:save">
        		  {text: '新增', click: addUnit,    iconClass: 'icon_add'},
        		  </shiro:hasPermission>
        		  { line : true }
        		  
        		]
         	}
		});
	}
	
	//给角色分配功能(动态加载树)
	function onModule(roleid){
		top.Dialog.open({
			URL:"<c:url value='/sys/role/preModule?roleid='/>" + roleid,
			Title:"角色功能分配",Width:350,Height:450});
	}
	
	//给角色分配功能（全部加载）
	function onModule2(roleid){
		top.Dialog.open({
			URL:"<c:url value='/sys/role/preModule2?roleid='/>" + roleid,
			Title:"角色功能分配",Width:350,Height:450});
	}
	
	
	//给角色指定工作流（全部加载）
	function onWkflow(roleid){
		top.Dialog.open({
			URL:"<c:url value='/sys/role/preFlow?roleid='/>" + roleid,
			Title:"给角色指定工作流",Width:550,Height:450});
	}
	
	//新增
	function addUnit() {
				top.Dialog.open({
				URL:"<c:url value='/sys/role/preAdd'/>",
				Title:"添加角色",Width:500,Height:200});
	}
	
	//修改	
	function onEdit(roleid){
		top.Dialog.open({
			URL:"<c:url value='/sys/role/preEdit?roleid='/>" + roleid,
			Title:"修改角色",Width:500,Height:200});
	}
	//删除	
	function onDelete(roleid,roleidx){
		top.Dialog.confirm("确定要删除该记录吗？",function(){
		  	//删除记录
		  	$.post("<c:url value='/sys/role/del'/>",
		  			{"ids":roleid,"CSRFToken":$("#CSRFToken").val()},
		  			function(result){
		  				getToken();
		  				handleResult(result);
					},"json");
					//刷新表格数据 
					grid.loadData();
			});
	}
	
	
	//删除后的提示
	function handleResult(result){
		if(result.status == "true"){
			top.Dialog.alert(result.message,null,null,null,1);
			grid.loadData();
		}else{
			top.Dialog.alert(result.message);
		}
	}
	
	//获取所有选中行获取选中行的id 格式为 ids=1&ids=2 
	function getSelectIds(grid) {
		var selectedRows = grid.getSelectedRows();
		var selectedRowsLength = selectedRows.length;
		var ids = "";
		
		for(var i = 0;i<selectedRowsLength;i++) {
			ids += selectedRows[i].userId + ",";
		}
		return {"ids":ids,"CSRFToken":$("#CSRFToken").val()};
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
	//处理高度自适应，每次浏览器尺寸变化时触发
	function customHeightSet(contentHeight){
		$(".cusBoxContent").height(contentHeight-55)
		$(".orgTreeContainer").height(contentHeight-30);
	}
 
 </script>
</body>
</html>