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

<!-- 双向选择器start -->
<script type="text/javascript" src="<c:url value='/resources/libs/js/form/lister.js' />"></script>
<!-- 双向选择器end -->

<!--布局控件start-->
<script type="text/javascript" src="<c:url value='/resources/libs/js/nav/layout.js' />"></script>
<!--布局控件end-->


 


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
			 	<div class="cusBoxContent"  style="width:250px;height:550px;border:solid 1px #cccccc;overflow-x:hidden;overflow-y:auto;">
			  	
			  		<ul id="tree" class="ztree"></ul>
			  	</div>
		  	</div>
		</td>
		<!--左侧区域end-->
		
		<!--右侧区域start-->
		<td width="100%" class="ver01" >
			
				<form action="<c:url value='/cms/send/userlist' />" id="queryForm" method="post">
				<input type="hidden" id="parentId" name="parentId" value="${parentId}"/>
				<input type="hidden" id="newsId" name="newsId" value="${newsId}"/>
			<input id="CSRFToken" name="CSRFToken" type="hidden" value=""  />
			<div class="box2" panelTitle="查询"  showStatus="false">
			
			<table>
				<tr>
				
					<td>姓名：</td>
					<td>
						<input type="text" id="userName" name="userName" style="width:250px;" />
						
						
					</td>
					
							
					<td>
						<div style="width:150px;" class="checkboxRender">
							
							<input type="radio" class="validate[required] radio" id="radio-1" name="fs" value="1"  /><label for="radio-1" class="hand">级联查</label>
							<input type="radio" class="validate[required] radio" id="radio-2" name="fs" value="2" checked="checked"/><label for="radio-2" class="hand">非级联</label>
							
						</div>
					</td>
					
					<td><button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button></td>
					<td><button type="button" onclick="onSave()"><span class="icon_save">发送</span></button></td>
					
				</tr>
				<tr>
				<td>留言：</td>
				<td colspan="4">
					<textarea name="leavewd" id="leavewd" style="width:550px;height:50px;" ></textarea>
			
				</td>
				
				</tr>
			</table>
			</div>
			
			</form>
				
			<div style="margin: 0;padding: 0 5px 0 0;">
				<div class="lister"  id="lister5" listerWidth="270" listerHeight="400" keepDefaultStyle="true"></div>

			</div>
		</td>
		<!--右侧区域end-->
	</tr>
	</table>
 
 <script type="text/javascript">
 	
	//定义grid
	//var grid = null;
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
				url: "<c:url value='/cms/send/tree'/>",
				autoParam: ["id"]
			},
			callback: {
			    onClick: zTreeSelect
			   
			} 
		};
		
	//初始化函数
	function initComplete(){
		//当提交表单刷新本页面时关闭弹窗
		//top.Dialog.close();
		
		$("#layout1").layout({ leftWidth: 180,onEndResize:function(){
			  	//grid.resetWidth();
			  }}); 
		
		//初始化tree
		initTree();
		
		initLister();
    	

		
		
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
	
	//初始化tree处理
 	function initLister() {
 			query = {'parentId':${org.id},'newsId':${newsId}};
 			$.post("<c:url value='/cms/send/userlist' />",
		  			query,
		  			function(result){
            			$("#lister5").data("data",result);
            			$("#lister5").render(); 
					},"json");
			
	}
	
	
	//点击树节点刷选对应的表格数据 
	function zTreeSelect(event,treeId,treeNode) {
		var query = null;
		selectTreeNode = treeNode;
		var fs = $("input:radio[name=fs]").filter("[checked]").val();
		var userName=$("#userName").val();
		query = {'parentId':treeNode.id,'fs':fs,'userName':userName,'newsId':${newsId}};
		$("#parentId").val(treeNode.id);
        // alert(JSON.stringify($("#lister5").data("selectedNodes")));
       var  aa =$("#lister5").data("selectedNodes");
       if(aa==null) aa=new Array();
       $.post("<c:url value='/cms/send/userlist' />",
		  		query,
		  		function(result){
		  			var fflist=result.fromList;
		  			var fromList=new Array();
		  			var f=0;
		  			var flag=true;
		  			for(var i=0;i<fflist.length;i++){
		  				var ff=fflist[i];
		  				flag=true;
		  				for(var j=0;j<aa.length;j++){
		  					var tt=aa[j];
		  					if(ff.value==tt.value){
			  					flag=false;
			  					continue;
		  					}
		  				}
		  				if(flag){
		  					fromList[f]=ff;
		  					f=f+1;
		  				}
		  			};
		  			var res={fromList:fromList,toList:aa};
            		$("#lister5").data("data",res);
            		$("#lister5").render(); 
				},"json");
	
	}
	
    
    //查询
    function searchHandler(){
    	 var query = $("#queryForm").formToArray(); 
		 $.post("<c:url value='/cms/send/userlist' />",
		  			query,
		  			function(result){
            			$("#lister5").data("data",result);
            			$("#lister5").render(); 
					},"json");
    }
    
    
    //查询
    function onSave(){
    	 var newsId=$("#newsId").val();
    	 var relValue=$("#lister5").attr("relValue");
    	 query = {'newsId':newsId,'relValue':relValue};
    	 //alert(JSON.stringify(query));
		 $.post("<c:url value='/cms/send/save' />",
		  			query,
		  			function(result){
            			handleResult(result);
					},"json");
    }
    
    
    //重置查询
    function resetSearch(){
    	$("#queryForm")[0].reset();
		searchHandler();
    }
    
    
    function handleResult(result){
		if(result.status == "true"||result.status == true){
			closeWin();
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
	//处理高度自适应，每次浏览器尺寸变化时触发
	function customHeightSet(contentHeight){
		$(".cusBoxContent").height(contentHeight-55)
		$(".orgTreeContainer").height(contentHeight-30);
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
</body>
</html>