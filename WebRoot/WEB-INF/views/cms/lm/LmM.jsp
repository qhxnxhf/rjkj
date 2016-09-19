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
<script type="text/javascript" src="<c:url value='/resources/libs/js/jquery.js'/>"></script>
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
		<!--左侧区域start-->
		<td class="ver01" >
			 <div class="box2"  overflow="auto" showStatus="false" panelTitle="栏目分类">
			 	<div class="cusBoxContent"  style="width:200px;height:820px;border:solid 1px #cccccc;overflow-x:auto;overflow-y:auto;">
			  	
			  		<ul id="tree" class="ztree"></ul>
			  	</div>
		  	</div>
		</td>
		<!--左侧区域end-->
		
		<!--右侧区域start-->
		<td width="100%" class="ver01" >
			
				<form action="<c:url value='/cms/lm/search'/>" id="queryForm" method="post">
				<input type="hidden" id="parentId" name="parentId" value="${parentId}"/>
				<input id="CSRFToken" name="CSRFToken" type="hidden" value=""  />
			<shiro:hasPermission name="LmM:search">
			<div class="box2" panelTitle="查询"  showStatus="false">
			<table>
				<tr>
					<td>组织名称：</td>
					<td>
						<input type="text" id="searchInput" name="deptName"/>
					</td>
					<td><button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button></td>
					<td><button type="button" onclick="resetSearch()"><span class="icon_reload">重置</span></button></td>
				</tr>
			</table>
			</div>
			</shiro:hasPermission>
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
				url: "<c:url value='/cms/lm/tree'/>",
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
		initTree();
		
		//初始化grid组件
		initGrid();
		
		//监听查询框的回车事件
		 $("#searchInput").keydown(function(event){
		 	if(event.keyCode==13){
				searchHandler();
			}
		 });
		 
		 getToken();
	}
	
	//初始化tree处理
 		function initTree() {
 			
			treeOrg =$.fn.zTree.init($("#tree"), selectionSetting);
	}
	
	
	
	//初始化Grid处理
	function initGrid() {
		grid = $("#dataBasic").quiGrid({
			columns:[
				{ display: 'ID', name: 'id',     align: 'left', width: "5%"},
				{ display: '名称', name: 'lmName',     align: 'left', width: "15%"},
				{ display: '模板', name: 'moduleName',     align: 'left', width: "5%"},
				{ display: '链接', name: 'linkUrl',     align: 'left', width: "15%"},
				{ display: 'targrt', name: 'target',     align: 'left', width: "5%"},
				{ display: '目录授权', name: 'orgIdsMl',     align: 'left', width: "10%"},
				{ display: '操作授权', name: 'orgIdsWj',     align: 'left', width: "10%"},
				{ display: '类型', name: 'nodeType',     align: 'center', width: "5%",
				 render : function(rowdata, rowindex, value, column){ return renderType(value);} },
			    { display: '状态', name: 'status',     align: 'left', width: "5%"},
           		{ display: '操作', isAllowHide: false, align: 'center', width:80,
						 render: function (rowdata, rowindex, value, column){
	                 	    return '<div class="padding_top4 padding_left5">'
	                                 + '<shiro:hasPermission name="LmM:edit"><span class="img_edit hand" title="修改" onclick="onEdit(' + rowdata.id + ')"></span></shiro:hasPermission>' 
	                                 + '<shiro:hasPermission name="LmM:auth"><span class="img_user_group hand" title="授权" onclick="onAuth(' + rowdata.id + ')"></span></shiro:hasPermission>' 
	                                 
	                                 + '<shiro:hasPermission name="LmM:delete"><span class="img_delete hand" title="删除" onclick="onDelete(' + rowdata.id+','+rowindex + ')"></span></shiro:hasPermission>'
	                            	 + '</div>';
		                 }
		            }
			  ],
		 url: '<c:url value='/cms/lm/table'/>', sortName: 'id',rownumbers:true,checkbox:true,
         height: '100%', width:"100%",pageSize:10,percentWidthMode:true,
        
         toolbar:{
        	 items:[
        		  <shiro:hasPermission name="LmM:save">
        		  {text: '新增', click: addUnit,    iconClass: 'icon_add'},
        		  </shiro:hasPermission>
        		  <shiro:hasPermission name="LmM:inherit">
        		  { line : true },
        		  {text: '级联标志', click: inheritUnit,iconClass: 'icon_add'},
        		  </shiro:hasPermission>
        		  <shiro:hasPermission name="LmM:batchdel">
        		  	{ line : true },
        		  	{text: '批量删除', click: deleteUnit, iconClass: 'icon_delete'},
        		   </shiro:hasPermission>
        		   { line : true }
        		]
         	}
		});
	}
	
	//渲染节点
	function renderType(value){
		if(value=="r"){
           return "<font color=red>树节点 </font>";
       	}
       	 if(value=="b"){
           return "<font color=blue>分类节点 </font>";
        }
        if(value=="y"){
           return "<font color=green>叶子节点 </font>";
        }
        if(value=="u"){
           return "<font color=green>链接节点 </font>";
        }
	}
	
	
	
	//新增
	function addUnit() {
		var orgid = $("#parentId").val();
		if(orgid != null && orgid != ""){
			
			if(selectTreeNode.menuType=="y"){
				top.Dialog.alert("请选择正确父级节点!");
			}else{
				top.Dialog.open({
				URL:"<c:url value='/cms/lm/preAdd?orgid='/>" + orgid+"&url=cms/lm/LmAdd",
				Title:"添加",Width:600,Height:500});
			};
			
		}else{
				top.Dialog.alert("请选择父级节点",null,null,null,2);
		}
	}
	
	
	
	//修改	
	function onEdit(rowid){
		
		top.Dialog.open({
			URL:"<c:url value='/cms/lm/preEdit?orgid='/>"+rowid+"&url=cms/lm/LmEdit",
			Title:"修改",Width:600,Height:500});
	}
	
	//授权	
	function onAuth(rowid){
		
		top.Dialog.open({
			URL:"<c:url value='/cms/lm/preAuth?orgid='/>" + rowid+"&url=cms/lm/LmAuth",
			Title:"授权",Width:600,Height:500});
	}
	
	
	
	//删除	
	function onDelete(rowid,rowidx){
		top.Dialog.confirm("确定要删除该记录吗？",function(){
		  	//删除记录
		  	
		  	$.post("<c:url value='/cms/lm/del'/>",
		  			{"ids":rowid,"CSRFToken":$("#CSRFToken").val()},
		  			function(result){
		  				getToken();
		  				handleResult(result);
					},"json");
					//刷新表格数据 
					grid.loadData();
			});
	}
		
		
	//批量删除
	function deleteUnit() {
		var rows = grid.getSelectedRows();
		var rowsLength = rows.length;
		if(rowsLength == 0) {
			top.Dialog.alert("请选中要删除的记录!");
			return;
		}
		top.Dialog.confirm("确定要删除吗？",function(){
			
			$.post("<c:url value='/cms/lm/delBatch'/>",
					//获取所有选中行
					getSelectIds(grid),
					function(result){
						getToken();
						handleResult(result);
					},
					"json");
		});
	}
	
	
	//删除后的提示
	function handleResult(result){
		if(result.status == "true"){
			top.Dialog.alert(result.message,null,null,null,1);
			grid.loadData();
			treeOrg.reAsyncChildNodes(selectTreeNode, "refresh");
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
			if(i == selectedRowsLength-1){
				ids += selectedRows[i].id ;
			}else{
				ids += selectedRows[i].id + ",";
			}
		}
		
		return {"ids":ids,"CSRFToken":$("#CSRFToken").val()};
	}
	
	
	//点击树节点刷选对应的表格数据 
	function zTreeSelect(event,treeId,treeNode) {
		var query = null;
		selectTreeNode = treeNode;
		query = {'parentId':treeNode.id};
		$("#parentId").val(treeNode.id);
		grid.setOptions({ params : query});
		    //页号重置为1
		    grid.setNewPage(1);
		    //刷新表格数据 
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
    	treeOrg.reAsyncChildNodes(selectTreeNode, "refresh");
    	
    }
	
	//处理高度自适应，每次浏览器尺寸变化时触发
	function customHeightSet(contentHeight){
		$(".cusBoxContent").height(contentHeight-55)
		$(".orgTreeContainer").height(contentHeight-30);
	}
 
 </script>
</body>
</html>