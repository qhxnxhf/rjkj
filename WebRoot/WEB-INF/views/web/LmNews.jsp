<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
	 <head>
	 <base href="<%=basePath%>">
	 <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	 <link href="<%=path%>/resources/dcld/Styles/xg.css" rel="stylesheet" />
	 <link href="<%=path%>/resources/dcld/Styles/css.css" rel="stylesheet" />
	 <script src="<%=path%>/resources/dcld/Scripts/jquery-1.8.2.js"></script>
	 <script src="<%=path%>/resources/dcld/Scripts/ztree.js"></script>
	 <script src="<%=path%>/resources/dcld/Scripts/xg.js"></script>
	 <script src="<%=path%>/resources/dcld/Scripts/js.js"></script>
	 <script type="text/javascript">
	
	//定义tree
	var treeOrg = null;
	//定义选中的树节点
	var selectTreeNode = null;
	
	//树属性配置
	var selectionSetting = {
			
			callback: {
			    onClick: zTreeSelect
			   
			} 
		};
		
		//点击树节点刷选对应的表格数据 
	function zTreeSelect(event,treeId,treeNode) {
		selectTreeNode = treeNode;
		var lmid=treeNode.id;
		
		$("#parentId").val(treeNode.id);
		
		var kwd=$("#search").val();
		
		contentList("list",lmid,kwd,"pager",10,5);
		
		$("#lmsub").empty();
        $("#lmsub").html(treeNode.name);
		//var fs = $("input:radio[name=fs]").filter("[checked]").val();
		//query = {'parentId':treeNode.id,'fs':fs};
		//
		
	}
	
	//初始化tree处理
 	function initTree() {
 			$.post("<%=path%>/web/lmTree?parentId=${lm.id}", {}, function(result){
    					//此处返回的是treeNodes
						treeOrg =$.fn.zTree.init($("#ztree"), selectionSetting, result.treeNodes);
						var nodes =  result.treeNodes;
						if (nodes.length>0) {
						  	treeNode=nodes[1];
							var lmid=treeNode.id;
							var kwd=$("#search").val();
							contentList("list",lmid,kwd,"pager",10,5);
							$("#lmsub").empty();
					        $("#lmsub").html(treeNode.name);
						}		
						}, "json");
									
	}
	
	//查询
function searchHandler(){
    //var lmid=$("#parentId").val();
	var kwd=$("#search").val();
	contentList("list","",kwd,"pager",10,5);
}
	
$(function (){
	//初始化tree
	initTree();
	//contentList("list",${lm.id},null,"pager",20,5);
 })
		
	 </script>
	 <title>部门介绍</title>
	 </head>
	 <body>
		<div class="wrapper">
			<div class="wrapper-left">
				<div class="wrapper-right">
					<div class="wrapper-main">
						<jsp:include page="/WEB-INF/views/web/Header.jsp"></jsp:include>
						<div class="main">
							<div class="position">
								 
								<div class="clear"></div>
							</div>
							<div class="layout-ztree-left left">
								<div class="ztree-search">
									<input type="hidden" id="parentId" name="parentId" value="${lm.id}" />
									<input id="search" type="text" class="left" value="${kwd}" />
									<i class="icon-title-search right pointer" onclick="searchHandler()"></i>
									 </div>
								<div class="clear"></div>
								<ul id="ztree" class="ztree">
								</ul>
							</div>
							<div class="layout-ztree-right">
								<div class="list center">
									<h3 class="left"> <i class="icon-title left"></i> <em class="left" id="lmsub">${lm.lmName}</em></h3>
									<br />
									<br />
									<div> <img src="<%=path%>/cms/attach/download/${lm.lmIcon}" /> </div>
									<div id="list"></div>
									<div id="pager"></div>
								</div>
							</div>
							<div class="clear"></div>
						</div>
						<jsp:include page="/WEB-INF/views/web/Footer.jsp"></jsp:include>
					</div>
				</div>
			</div>
			<div class="clear"></div>
			<div class="wrapper-bottom">
				<div class="wrapper-radius-left"></div>
				<div class="wrapper-radius-right"></div>
			</div>
		</div>
</body>
</html>
