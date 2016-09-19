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
					<shiro:hasPermission name="PubM:search">
					<td>标题：</td>
					<td>
						<input type="text" id="keyword" name="keyword"  style="width:120px;"/>
					</td>
					
					<td>
					
						起止日期
						<input type="text" id="beginTime" name="beginTime" class="dateIcon"  style="width:90px;"/>
						<input type="text" id="endTime" name="endTime" class="dateIcon"  style="width:90px;"/>
						
					</td>
					
					<td>状态：</td>
					<td>
						<select  name='status'   data='{"list":[{"value":"2","key":"待审"},{"value":"3","key":"待发"},{"value":"4","key":"发布"},{"value":"5","key":"废止"}]}' selWidth="120" ></select>
			
					</td>
					<td>审核意见：</td>
					<td>
						<input type="text" id="shyj" name="shyj"  style="width:160px;"/>
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
	
	
	
	
	//初始化Grid处理
	function initGrid() {
		grid = $("#dataBasic").quiGrid({
			columns:[
				{ display: '栏目', name: 'lm.lmName', align: 'left', width: "10%"},
				{ display: '标题', name: 'newsTitle',     align: 'left', width: "30%",
					render : function(rowdata, rowindex, value, column){ return '<a target="_blank" href=<c:url value="/cms/news/showNews/" />'+rowdata.id+' >'+value+'</a>';}
				},
				{ display: '发布人', name: 'user.userName',  align: 'left', width: "10%"},
				{ display: '来源', name: 'origin',     align: 'left', width: "10%"},
				{ display: '日期', name: 'pubDate',     align: 'left', width: "15%"},
				{ display: '限定', name: 'allowed',     align: 'center', width: "10%",
				 render : function(rowdata, rowindex, value, column){ return renderAllowed(value);} },
				 { display: '状态', name: 'status',     align: 'center', width: "5%",
				 render : function(rowdata, rowindex, value, column){ return renderStatue(rowdata,value);}},
				  { display: '操作', isAllowHide: false, align: 'center', width:120,
					render: function (rowdata, rowindex, value, column){return onMainOper(rowdata);}
		        }
           		
			  ],
		
		isScroll: true, 
		 url: "<c:url value='/cms/pub/pubtable'/>", sortName: 'id',rownumbers:false,checkbox:true,
         height: '100%', width:"100%",pageSize:10,
         onAfterShowData:function(data){$(".qTip").tip({ auto:true ,arrowDirection:"up"});},
         percentWidthMode:true,
        toolbar:{
        	 items:[
        		 
        		  <shiro:hasPermission name="PubM:batchdel">
		        		 { line : true },
		        		 {text: '批量删除', click: delBatch, iconClass: 'icon_delete'},
        		   </shiro:hasPermission>
        		   { line : true }
        		]
         	}
         
         
		});
	}
	
	
	
	
	
	//渲染节点
	function renderStatue(rowdata,value){
		if(value=="1"){
           return '<div title="'+rowdata.remark+'" class="qTip" ><font color=blue>在编辑</font></div>';
       	}
        if(value=="2"){
          
          return '<div title="'+rowdata.remark+'" class="qTip" ><font color=red>待审核</font></div>';
        }
        if(value=="3"){
           
           return '<div title="'+rowdata.remark+'" class="qTip" ><font color=orange>待发布</font></div>';
        }
        if(value=="4"){
          
           return '<div title="'+rowdata.remark+'" class="qTip" ><font color=green>已发布</font></div>';
        }
        if(value=="5"){
           
           return '<div title="'+rowdata.remark+'" class="qTip" ><font color=yellow>已废止</font></div>';
        }
	}
	
	//主表操作
	function onMainOper(rowdata){
		var sss='<div class="padding_top4 padding_left5">';
			if(rowdata.status>=3){
				sss=sss + '<shiro:hasPermission name="PubM:auth"><span class="img_ok hand" title="审核通过" onclick="onAuth1(' + rowdata.id + ')"></span>' 
	                     + '<span class="img_no hand" title="审核否决" onclick="onAuth2(' + rowdata.id + ')"></span></shiro:hasPermission>' 
	                     + '<shiro:hasPermission name="PubM:delete"><span class="img_delete hand" title="删除" onclick="onDelete(' + rowdata.id + ')"></span></shiro:hasPermission>';
	                            	       	 
			}
			
		sss=sss+'</div>';
		return sss;
	}	
	
	//渲染节点
	function renderAllowed(value){
		if(value=="1"){
           return "<font color=blue>公开使用 </font>";
       	}
       	 if(value=="2"){
           return "<font color=green>登录使用 </font>";
        }
        if(value=="3"){
           return "<font color=green>本部门用</font>";
        }
        if(value=="4"){
           return "<font color=red>个人使用</font>";
        }
        
	}
	
	
	
	//提报审核	
	function onAuth1(rowid){
		top.Dialog.confirm("确定通过审核发布吗？",function(){
		  	var shyj=$("#shyj").val();
		  	$.post("<c:url value='/cms/pub/authNews'/>",
		  			{"ids":rowid,"zt":4,"shyj":"同意！"+shyj,"CSRFToken":$("#CSRFToken").val()},
		  			function(result){
		  				grid.loadData();
					},"json");
					//刷新表格数据 
					//grid.loadData();
			});
	}
	
	//提报审核	
	function onAuth2(rowid){
		top.Dialog.confirm("确定退回吗？",function(){
		  	var shyj=$("#shyj").val();
		  	$.post("<c:url value='/cms/pub/authNews'/>",
		  			{"ids":rowid,"zt":2,"shyj":"否决!"+shyj,"CSRFToken":$("#CSRFToken").val()},
		  			function(result){
		  				grid.loadData();
					},"json");
					//刷新表格数据 
					//grid.loadData();
			});
	}
	
	//删除	
	function onDelete(rowid){
		top.Dialog.confirm("确定要删除该记录吗？",function(){
		  
		  	//删除记录
		  	$.post("<%=path%>/cms/pub/del",
		  			{"ids":rowid,"CSRFToken":$("#CSRFToken").val()},
		  			function(result){
		  				//getToken();
		  				handleResult(result);
					},"json");
					//刷新表格数据 
					//grid.loadData();
			});
	}
	
	//批量删除
	function delBatch() {
		var rows = grid.getSelectedRows();
		var rowsLength = rows.length;
		if(rowsLength == 0) {
			top.Dialog.alert("请选中要删除的记录!");
			return;
		}
		top.Dialog.confirm("确定要删除吗？",function(){
			
			$.post("<%=path%>/cms/pub/delBatch",
					//获取所有选中行
					getSelectIds(grid),
					function(result){
						//getToken();
						handleResult(result);
						
					},
					"json");
		});
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