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
<script type="text/javascript" src="<%=path%>/resources/libs/js/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/resources/libs/js/language/cn.js"></script>
<script type="text/javascript" src="<%=path%>/resources/libs/js/framework.js"></script>
<link href="<%=path%>/resources/libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="<%=path%>/resources/" scrollerY="false"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->


<!--布局控件start-->
<script type="text/javascript" src="<%=path%>/resources/libs/js/nav/layout.js"></script>
<!--布局控件end-->

<!--数据表格start-->
<script src="<%=path%>/resources/libs/js/table/quiGrid.js" type="text/javascript"></script>
<!--数据表格end-->


<!-- 组合下拉框start -->
<script type="text/javascript" src="<%=path%>/resources/libs/js/form/selectCustom.js"></script>
<!-- 组合拉框end -->

<!-- 表单start -->
<script src="<%=path%>/resources/libs/js/form/form.js" type="text/javascript"></script>
<!-- 表单end -->

<!-- 日期选择框start -->
<script type="text/javascript" src="<%=path%>/resources/libs/js/form/datePicker/WdatePicker.js"></script>
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
			
				<form action="<c:url value='/cms/pub/search'/>" id="queryForm" method="post">
				<input type="hidden" id="parentId" name="parentId" value="${parentId}"/>
			<input id="CSRFToken" name="CSRFToken" type="hidden" value=""  />
			<div class="box2" panelTitle="查询"  showStatus="false">
			
			<table width="100%">
				<tr>
					<td>体检类别</td>
					<td>
						<select boxHeight="100" name='tjlb' id="tjlb" prompt="请选择" url="<%=path%>/zgjk/tjxx/typeTj"  selWidth="180" ></select>  				
					</td>
					<td>部门</td>
					<td>
						<select boxHeight="100" name='tjbm' id="tjbm" prompt="请选择" url="<%=path%>/zgjk/tjxx/deptTj"  selWidth="180" ></select>  				
					</td>
					
					<td>身份证</td>
					<td>
						<input type="text" id="carId" name="carId"  style="width:180px;"/>
					</td>
					
					
					
					<td><button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button></td>
					<td><button type="button" onclick="delHandler()"><span class="icon_find">批量删除</span></button></td>
					
					<td><button type="button" onclick="xxcsHandler()"><span class="icon_find">体检信息初筛</span></button></td>
					
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
	
		
	//初始化函数
	function initComplete(){
		//当提交表单刷新本页面时关闭弹窗
		top.Dialog.close();
		
		$("#layout1").layout({ leftWidth: 180,onEndResize:function(){
			  	grid.resetWidth();
			  }}); 
		
		
		//初始化grid组件
		initGrid();
		
		
		//监听查询框的回车事件
		 $("#searchInput").keydown(function(event){
		 	if(event.keyCode==13){
				searchHandler();
			}
		 });
	     
	}
	
	
	//初始化Grid处理
	function initGrid() {
		grid = $("#dataBasic").quiGrid({
			columns:[
				{ display: '身份证', name: 'cardId', align: 'left', width: "5%"},
				{ display: '姓名', name: 'name',     align: 'center', width: "5%" },
				{ display: '年龄', name: 'age',     align: 'left', width: "5%"},
				{ display: '性别', name: 'sex',     align: 'left', width: "5%"},
				{ display: '体检日期', name: 'tjDate',  align: 'left', width: "5%"},
				{ display: '体检类别', name: 'tjType',     align: 'left', width: "5%"},
				{ display: 'age', name: 'age',     align: 'center', width: "5%"},
				{ display: 'bmi', name: 'bmi',     align: 'center', width: "5%"},
				{ display: 'zongdan', name: 'zongdan',     align: 'center', width: "5%"},
				{ display: 'dimi', name: 'dimi',     align: 'center', width: "5%"},
				{ display: 'kfxt', name: 'kfxt',     align: 'center', width: "5%"},
				{ display: 'shzhya', name: 'shzhya',     align: 'center', width: "5%"},
				{ display: 'gaomi', name: 'gaomi',     align: 'center', width: "5%"},
				{ display: 'gysz', name: 'gysz',     align: 'center', width: "5%"},
				{ display: 'fev1', name: 'fev1',     align: 'center', width: "5%"},
				{ display: 'fev1fvc', name: 'fev1fvc',     align: 'center', width: "5%"},
				{ display: 'type1', name: 'type1',     align: 'center', width: "5%"},
				{ display: 'type2', name: 'type2',     align: 'center', width: "5%"},
				{ display: 'type3', name: 'type3',     align: 'center', width: "5%"}
				
			  ],
		isScroll: true, 
		 url: "<c:url value='/zgjk/sxjg/list'/>", sortName: 'id',rownumbers:true,checkbox:false,
         height: '100%', width:"100%",pageSize:50,
         onAfterShowData:function(data){$(".qTip").tip({ auto:true ,arrowDirection:"up"});},
         percentWidthMode:true
         
         
		});
	}
	
	function onView(rowid){
	 
    	top.Dialog.open({
        	URL:"<%=path%>/zgjk/tjuser/openUrl?url=/zgjk/tjxx/TjxxGrFind&userid="+rowid,
        	Title:"查看",Width:900,Height:600});

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
	
	//删除后的提示
	function handleResult(result){
		if(result.status == "true"||result.status == true){
			top.Dialog.alert(result.message,null,null,null,1);
			grid.loadData();
			//treeOrg.reAsyncChildNodes(selectTreeNode, "refresh");
		}else{
			top.Dialog.alert(result.message);
		}
	}
    
    //查询
    function xxcsHandler(){
    	 var query = $("#queryForm").formToArray(); 
		 	top.Dialog.confirm("确定要进行体检信息初筛吗？",function(){
			$.post("<c:url value='/zgjk/sxjg/saveSxjg'/>",
					//获取所有选中行
					query,
					function(result){
						handleResult(result);
					},
					"json");
		});
    }
    
    //查询
    function delHandler(){
    	 var query = $("#queryForm").formToArray(); 
		 	top.Dialog.confirm("确定要进行批量删除吗？",function(){
			$.post("<c:url value='/zgjk/sxjg/delSxjg'/>",
					//获取所有选中行
					query,
					function(result){
						
						handleResult(result);
					},
					"json");
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