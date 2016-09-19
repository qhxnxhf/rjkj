<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%> 
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<% 
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.user/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.user/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>基本表格模板</title>
<!--框架必需start-->
<script type="text/javascript" src="<c:url value='/resources/libs/js/jquery.js' />"></script>
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

<!-- 树形下拉框start -->
<script type="text/javascript" src="<c:url value='/resources/libs/js/form/selectTree.js' />"></script>
<!-- 树形下拉框end -->

<!-- 组合下拉框start -->
<script type="text/javascript" src="<c:url value='/resources/libs/js/form/selectCustom.js' />"></script>
<!-- 组合拉框end -->
<!--布局控件start-->
<script type="text/javascript" src="<c:url value='/resources/libs/js/nav/layout.js' />"></script>
<!--布局控件end-->

<!--数据表格start-->
<script src="<c:url value='/resources/libs/js/table/quiGrid.js' />" type="text/javascript"></script>
<!--数据表格end-->

<!-- 表单start -->
<script src="<c:url value='/resources/libs/js/form/form.js' />" type="text/javascript"></script>
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
			 <div class="box2"  overflow="auto" showStatus="false" panelTitle="组织结构树">
			 	<div class="cusBoxContent"  style="width:200px;height:820px;border:solid 1px #cccccc;overflow-x:hidden;overflow-y:auto;">
			  	
			  		<ul id="tree" class="ztree"></ul>
			  	</div>
		  	</div>
		</td>
		<!--左侧区域end-->
		
		<!--右侧区域start-->
		<td width="100%" class="ver01" >
			
				<form action="<c:url value='/sys/user/search' />" id="queryForm" method="post">
				<input type="hidden" id="parentId" name="parentId" value="${parentId}"/>
			<input id="CSRFToken" name="CSRFToken" type="hidden" value=""  />
			<div class="box2" panelTitle="查询"  showStatus="false">
			
			<table>
				<tr>
				<shiro:hasPermission name="UserM:search">
					<td>姓名：</td>
					<td>
						<input type="text" id="searchInput" name="userName"/>
						
					</td>
					<td>状态：</td>
					<td>
						<select id="userStatus" name='userStatus'  data='{"list":[{"value":"0","key":"全部"},{"value":"1","key":"待激活"},{"value":"2","key":"离线"},{"value":"3","key":"在线"},{"value":"4","key":"锁定"}]}' selWidth="100" ></select>
					</td>
						<td>角色：</td>
					<td>
						<div class="selectTree "   id="userRole" name="userRole" url="<c:url value='/sys/user/roles' />" multiMode="false" noGroup="true" selWidth="100" ></div>
						
					</td>	
					<td>
						<div style="width:180px;" class="checkboxRender">
							<input type="checkbox"  id="checkbox-1" name="wjs" value="1"  /><label for="checkbox-1" class="hand">无角色</label>
							
							<input type="radio" class="validate[required] radio" id="radio-1" name="fs" value="1" checked="checked" /><label for="radio-1" class="hand">级联查</label>
							<input type="radio" class="validate[required] radio" id="radio-2" name="fs" value="2"/><label for="radio-2" class="hand">非级联</label>
							
						</div>
					</td>
					
					<td><button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button></td>
					</shiro:hasPermission>
					<shiro:hasPermission name="UserM:excel">
					<td><button type="button" onclick="excel()"><span class="icon_export">导出</span></button></td>
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
				url: "<c:url value='/sys/user/tree'/>",
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
		getToken();
		//监听查询框的回车事件
		 $("#searchInput").keydown(function(event){
		 	if(event.keyCode==13){
				searchHandler();
			}
		 })
	}
	
	//初始化tree处理
 		function initTree() {
 			
			treeOrg =$.fn.zTree.init($("#tree"), selectionSetting);
	}
	
	
	
	//初始化Grid处理
	function initGrid() {
		grid = $("#dataBasic").quiGrid({
			columns:[
				{ display: 'ID', name: 'id',     align: 'left', width: "4%"},
				{ display: '所属部门', name: 'org.orgName',     align: 'left', width: "10%"},
				{ display: '用户姓名', name: 'userName',     align: 'left', width: "10%"},
			    { display: '账号', name: 'loginName', 	 align: 'left', width: "10%"},
			    { display: '注册日期', name: 'createTime', 	 align: 'left', width: "15%"},
			    { display: '电话', name: 'mobile', 	 align: 'left', width: "10%"},
			    { display: '角色', name: 'roles', 	 align: 'left', width: "10%"},
			    { display: '状态', name: 'status', 	 align: 'center', width: "5%",
				 render : function(rowdata, rowindex, value, column){ return renderStatue(rowdata,value);}},
           		{ display: '操作', isAllowHide: false, align: 'center', width:120,
						 render: function (rowdata, rowindex, value, column){
	                 	    return '<div class="padding_top4 padding_left5">'
	                                  + '<shiro:hasPermission name="UserM:view"><span class="img_list hand" title="查看" onclick="onView(' + rowdata.id + ')"></span></shiro:hasPermission>'
	                                  + '<shiro:hasPermission name="UserM:edit"><span class="img_edit hand" title="修改" onclick="onEdit(' + rowdata.id + ')"></span></shiro:hasPermission>' 
	                                  + '<shiro:hasPermission name="UserM:role"><span class="img_user_group hand" title="分配角色" onclick="onRole(' + rowdata.id + ')"></span></shiro:hasPermission>' 
	                                  + '<shiro:hasPermission name="UserM:pswd"><span class="img_key hand" title="密码重置" onclick="onPswd(' + rowdata.id + ')"></span></shiro:hasPermission>' 
	                                  + '<shiro:hasPermission name="UserM:delete"><span class="img_delete hand" title="删除" onclick="onDelete(' + rowdata.id+','+rowindex + ')"></span></shiro:hasPermission>'
	                             + '</div>';
		                 }
		            }
			  ],
		 url: "<c:url value='/sys/user/userlist' />", sortName: 'id',rownumbers:true,checkbox:true,
         height: '100%', width:"100%",pageSize:10,percentWidthMode:true,
        
         toolbar:{
        	 items:[
        		 <shiro:hasPermission name="UserM:save">
        		  {text: '新增用户', click: addUnit,    iconClass: 'icon_add'},
        		  </shiro:hasPermission>
        		  <shiro:hasPermission name="UserM:edit">
        		  { line : true },
        		  {text: '激活用户', click: actUnit,    iconClass: 'icon_add'},
        		 </shiro:hasPermission>
        		  <shiro:hasPermission name="UserM:batchdel">
        		  { line : true },
        		  {text: '批量删除', click: deleteUnit, iconClass: 'icon_delete'},
        		  </shiro:hasPermission>
        		  <shiro:hasPermission name="UserM:role">
        		  { line : true },
        		  {text: '角色设定', click: roleToUser,    iconClass: 'icon_add'},
        		 </shiro:hasPermission>
        		  { line : true }
        		]
         	}
		});
	}
	
	//渲染节点
	function renderSex(value){
		
        if(value=="1"){
           return "<font color=blue>男 </font>";
        }else{
           return "<font color=green>女 </font>";
        }
	}
	
	//渲染节点
	function renderStatue(rowdata,value){
		if(value=="1"){
           return '<div class="padding_top4 padding_left5"><span class="img_attention hand" title="等待激活 " onclick="onStatus(' + rowdata.id + ')"></span></div>';
       	}
        if(value=="2"){
          return '<div class="padding_top4 padding_left5"><span class="img_user_off hand" title="离线状态" onclick="onStatus(' + rowdata.id + ')"></span></div>';
        }
        if(value=="3"){
           return '<div class="padding_top4 padding_left5"><span class="img_user_worker hand" title="活动状态" onclick="onStatus(' + rowdata.id + ')"></span></div>';
        }
        if(value=="4"){
           return '<div class="padding_top4 padding_left5"><span class="img_lock hand" title="锁定状态" onclick="onStatus(' + rowdata.id + ')"></span></div>';
        }
	}
	
	//查看
	function onView(rowid){	
		top.Dialog.open({
			URL:"<c:url value='/sys/user/view?userid='/>" + rowid,
			Title:"查看",Width:500,Height:400});
	}
	//新增
	function addUnit() {
		var orgid = $("#parentId").val();
		if(orgid != null && orgid != ""){
			top.Dialog.open({
			URL:"<c:url value='/sys/user/preAdd?orgid='/>" + orgid,
			Title:"添加",Width:500,Height:400});
		
		}else{
			top.Dialog.alert("请选择用户部门",null,null,null,2);
		}
	}
	
	
	
	//修改	
	function onEdit(rowid){
		
		top.Dialog.open({
			URL:"<c:url value='/sys/user/preEdit?userid='/>" + rowid,
			Title:"修改",Width:500,Height:400});
	}
	
	//分配角色	
	function onRole(rowid){
		
		top.Dialog.open({
			URL:"<c:url value='/sys/user/preUserRoles?userid='/>" + rowid,
			Title:"用户角色配置",Width:500,Height:350});
	}
	
	//重置密码	
	function onPswd(rowid){
		top.Dialog.confirm("确定要重置该用户密码吗？",function(){
		  	//删除记录
		  	$.post("<c:url value='/sys/user/pswd' />",
		  			{"userid":rowid,"CSRFToken":$("#CSRFToken").val()},
		  			function(result){
		  				getToken();
		  				handleResult(result);
					},"json");
					//刷新表格数据 
					//grid.loadData();
			});
	}
	
	//重置用户状态
	function onStatus(rowid){
		  	$.post("<c:url value='/sys/user/status' />",
		  			{"userid":rowid,"CSRFToken":$("#CSRFToken").val()},
		  			function(result){
		  				getToken();
		  				//handleResult(result);
		  				grid.loadData();
					},"json");
	}
	
	
	
	
	//激活用户状态
	function actUnit() {
		var rows = grid.getSelectedRows();
		var rowsLength = rows.length;
		if(rowsLength == 0) {
			top.Dialog.alert("请选中要激活用户!");
			return;
		}
		top.Dialog.confirm("确定要删除吗？",function(){
			$.post("<c:url value='/sys/user/actUser' />",
					//获取所有选中行
					getSelectIds(grid),
					function(result){
						getToken();
						handleResult(result);
					},
					"json");
		});
	}
	
	
	//拍照测试	
	function onImg(rowid){
		
		top.Dialog.open({
			URL:"<c:url value='/sys/user/preImg?userid='/>" + rowid,
			Title:"拍照",Width:660,Height:400});
	}
	
	//批量设定用户角色
	function roleToUser() {
		var rows = grid.getSelectedRows();
		var rowsLength = rows.length;
		if(rowsLength == 0) {
			top.Dialog.alert("请选中要设定的用户!");
			return;
		}
		
		
		var nodes=$("#userRole").data("selectedNode");
		if(nodes==null){
			top.Dialog.alert("请选中要设定的角色!");
			return;
		}
		var roleid=nodes.id;
		var rolename=nodes.name;
		
		if(roleid==null||roleid==""){
			top.Dialog.alert("请选中要设定的角色!");
			return;
		}
		
		top.Dialog.confirm("确定要设定角色为："+rolename+"？",function(){
			$.post("<c:url value='/sys/user/roleToUsers?roleid='/>"+roleid,
					//获取所有选中行
					getSelectIds(grid),
					function(result){
						getToken();
						handleResult(result);
					},
					"json");
		});
	}
	
	//删除	
	function onDelete(rowid,rowidx){
		top.Dialog.confirm("确定要删除该记录吗？",function(){
		  	//删除记录
		  	$.post("<c:url value='/sys/user/del' />",
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
			$.post("<c:url value='/sys/user/del' />",
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
			//treeOrg.reAsyncChildNodes(selectTreeNode, "refresh");
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
		var fs = $("input:radio[name=fs]").filter("[checked]").val();
		query = {'parentId':treeNode.id,'fs':fs};
		$("#parentId").val(treeNode.id);
		grid.setOptions({ params : query});
		    //页号重置为1
		    grid.setNewPage(1);
		    //刷新表格数据 
			grid.loadData();
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
    
     //导出Excel
    function excel(){   
    	var orgid = $("#parentId").val();
		if(orgid != null && orgid != ""){
			var frm=document.getElementById("queryForm");
    		frm.action="<c:url value='/sys/user/excel' />";
    		frm.submit();
		}else{
			top.Dialog.alert("请选择导出部门",null,null,null,2);
		}
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