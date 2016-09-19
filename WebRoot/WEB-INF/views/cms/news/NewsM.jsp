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

<!--树组件start -->
<script type="text/javascript" src="<%=path%>/resources/libs/js/tree/ztree/ztree.js"></script>
<link href="<%=path%>/resources/libs/js/tree/ztree/ztree.css" rel="stylesheet" type="text/css"/>
<!--树组件end -->

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
					<td>
					
						起止日期
						<input type="text" id="beginTime" name="beginTime" class="dateIcon"  style="width:90px;"/>
						<input type="text" id="endTime" name="endTime" class="dateIcon"  style="width:90px;"/>
						
					</td>
					
					<td>状态：</td>
					<td>
						<select  name='status'  data='{"list":[{"value":"0","key":"状态:全部"},{"value":"1","key":"编辑"},{"value":"2","key":"待审"},{"value":"3","key":"待发"},{"value":"4","key":"发布"},{"value":"5","key":"废止"}]}' selWidth="100" ></select>
			
					</td>
					
					
					<td>
						<div style="width:100px;" class="checkboxRender">
							<select  name='allowed'  data='{"list":[{"value":"0","key":"访问限定:无"},{"value":"1","key":"公开"},{"value":"2","key":"内部"},{"value":"3","key":"本部"},{"value":"4","key":"指定"}]}' selWidth="100" ></select>
			
						</div>
					</td>
					<td>
						<div style="width:200px;" class="checkboxRender">
							<input type="radio" class="validate[required] radio" id="radio-1" name="fs" value="1" /><label for="radio-1" class="hand">级联查</label>
							<input type="radio" class="validate[required] radio" id="radio-2" name="fs" value="2" checked="checked" /><label for="radio-2" class="hand">非级联</label>
							
						</div>
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
				url: "<%=path%>/cms/news/tree",
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
		
		//getToken();
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
			columns:[
				{ display: 'ID', name: 'id', align: 'left', width: "5%"},
				{ display: '栏目', name: 'lm.lmName', align: 'left', width: "10%"},
				{ display: '标题', name: 'newsTitle',     align: 'left', width: "30%",
					render : function(rowdata, rowindex, value, column){ return '<a target="_blank" href=<c:url value="/cms/news/showNews/" />'+rowdata.id+' >'+value+'</a>';}
				},
				{ display: '发布人', name: 'user.userName',  align: 'left', width: "5%"},
				{ display: '来源', name: 'origin',     align: 'left', width: "5%"},
				{ display: '日期', name: 'pubDate',     align: 'left', width: "10%"},
				{ display: '状态', name: 'status',     align: 'center', width: "5%",
				 render : function(rowdata, rowindex, value, column){ return renderStatue(rowdata,value);}},
           		{ display: '使用限定', name: 'allowed',     align: 'center', width: "5%",
				 render : function(rowdata, rowindex, value, column){ return renderAllowed(rowdata,value);}},
				{ display: '文件保护', name: 'newslock',     align: 'center', width: "5%",
				 render : function(rowdata, rowindex, value, column){ return renderLock(value);}},
				 { display: '操作', isAllowHide: false, align: 'center', width:140,
					render: function (rowdata, rowindex, value, column){return onMainOper(rowdata);}
		        }
				 
				 
           		
			  ],
		
		isScroll: true, 
		 frozen:false,
		 url: "<%=path%>/cms/news/table", sortName: 'id',rownumbers:false,checkbox:true,
         height: '100%', width:"100%",pageSize:10,percentWidthMode:true,
         detail: { onShowDetail: showEmployees, height: 'auto'},
         onError: function (a, b){ },
         onAfterShowData:function(data){$(".qTip").tip({ auto:true ,arrowDirection:"up"});},
         
         toolbar:{
        	 items:[
        		  
        		  <shiro:hasPermission name="NewsM:save">
        		   		{ line : true },
        		  		{text: '新建', click: addNew,    iconClass: 'icon_add'},
        		  </shiro:hasPermission>
        		  <shiro:hasPermission name="NewsM:batchdel">
		        		 { line : true },
		        		 {text: '批量删除', click: delBatch, iconClass: 'icon_delete'},
        		   </shiro:hasPermission>
        		   { line : true }
        		]
         	}
		});
	}
	
	 function onAfterShowData(data){

    	$(".qTip").tip({ auto:true ,arrowDirection:"up"});

	}
	
	//显示收件人列表
    function showEmployees(row, detailPanel,callback){
             	var childGrid = document.createElement('div'); 
                		$(detailPanel).append(childGrid);
                var childGrid=$(childGrid).css('margin',10).quiGrid({
                     		columns: [
                     			{ display: '收件人', name: 'taker.userName',  align: 'left',width: "10%"},
                     			{ display: '签收时间',  name: 'receiveDate', align: 'center', width:"20%"}, 
                     			{ display: '留言信息',  name: 'leavewd', align: 'center', width:"25%"}, 
                     			{ display: '回复信息',  name: 'replywd', align: 'center', width:"25%"},
                     			{ display: '收件状态',  name: 'status', align: 'center', width:"10%",
				 					render : function(rowdata, rowindex, value, column){ return renderStatue2(rowdata,value);}}
                     ], 

                     isScroll:true,width:'850', sortName: 'id',percentWidthMode:false,pageSize:20,checkbox:false,rownumbers:true,
                     url: '<%=path%>/cms/send/taker?newsId='+row.id
                    
                });  
                var obj={};
                 	obj.id=row.id;
                    obj.grid=childGrid;
                    gridArray.push(obj);
	
    	}

//主表操作
	function onMainOper(rowdata){
		var sss='<div class="padding_top4 padding_left5">';
			if(rowdata.status==1){
				sss=sss+ '<shiro:hasPermission name="NewsM:edit"><span class="img_edit hand" title="修改正文" onclick="onEdit(' + rowdata.id + ')"></span></shiro:hasPermission>' 
	                   + '<shiro:hasPermission name="NewsM:attach"><span class="img_item hand" title="附件管理" onclick="onAttach(' + rowdata.id + ')"></span></shiro:hasPermission>' 
	                   + '<shiro:hasPermission name="NewsM:send"><span class="img_email hand" title="信息互递" onclick="onSend(' + rowdata.id + ')"></span></shiro:hasPermission>' 
	                   + '<shiro:hasPermission name="NewsM:edit"><span class="img_ok hand" title="提交" onclick="onSubmit(' + rowdata.id + ')"></span></shiro:hasPermission>' 
	                   + '<shiro:hasPermission name="NewsM:delete"><span class="img_delete hand" title="删除" onclick="onDelete(' + rowdata.id+ ')"></span></shiro:hasPermission>'
	                   + '<shiro:hasPermission name="NewsM:lock"><span class="img_guard hand" title="设定保护" onclick="onStatus(' + rowdata.id+')"></span></shiro:hasPermission>';
	                            	 
			}else{
				sss=sss+ '<shiro:hasPermission name="NewsM:send"><span class="img_email hand" title="信息互递" onclick="onSend(' + rowdata.id + ')"></span></shiro:hasPermission>' 
	                   + '<shiro:hasPermission name="NewsM:lock"><span class="img_guard hand" title="设定保护" onclick="onStatus(' + rowdata.id+')"></span></shiro:hasPermission>';
	                        
			}
			
		sss=sss+'</div>';
		return sss;
	}	
	
	//渲染节点
	function renderStatue2(rowdata,value){
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
	
	
	//渲染节点
	function renderLock(value){
		if(value==0){
           return "<font color=blue></font>";
       	}
       	 if(value==1){
           return "<font color=green>不可删</font>";
        }
        if(value==2){
           return "<font color=red>不可改</font>";
        }
	}
	
	//渲染节点
	function renderAllowed(rowdata,value){
		if(value=="1"){
           return '<font color=blue class="hand" onclick="onAllowed(' + rowdata.id + ')">公开看 </font></div>';
       	}
        if(value=="2"){
          return '<font color=green class="hand" onclick="onAllowed(' + rowdata.id + ')">登录看 </font></div>';
        }
        if(value=="3"){
           return '<font color=brown  class="hand" onclick="onAllowed(' + rowdata.id + ')">本部看 </font></div>';
        }
        if(value=="4"){
           return '<font color=red class="hand" onclick="onAllowed(' + rowdata.id + ')">个人用 </font></div>';
        }
	}
	
	
	
	
	//重置用户状态
	function onAllowed(rowid){
		  	$.post("<%=path%>/cms/news/allowed",
		  			{"newsid":rowid,"CSRFToken":$("#CSRFToken").val()},
		  			function(result){
		  				//getToken();
		  				//handleResult(result);
		  				grid.loadData();
					},"json");
	}
	
	//重置用户状态
	function onStatus(rowid){
		  	$.post("<%=path%>/cms/news/status",
		  			{"newsid":rowid,"CSRFToken":$("#CSRFToken").val()},
		  			function(result){
		  				//getToken();
		  				//handleResult(result);
		  				grid.loadData();
					},"json");
	}
	
	//新建文档
	function addNew() {
		var orgid = $("#parentId").val();
		if(orgid != null && orgid != ""){
			
			if(selectTreeNode.menuType=="y"){
				top.Dialog.open({
				URL:"<%=path%>/cms/news/preAdd?orgid="+orgid+"&url=cms/news/NewsEdit",
				Title:"新建",Width:1000,Height:760,ShowMaxButton:true,ShowMinButton:true});
			}else{
				top.Dialog.alert("请选择正确父级节点!");
			};
			
		}else{
				top.Dialog.alert("请选择父级节点",null,null,null,2);
		}
	}
	
	//修改	
	function onEdit(rowid){
		
		top.Dialog.open({
			URL:"<%=path%>/cms/news/preEdit?newsId="+rowid+"&url=cms/news/NewsEdit",
			Title:"修改",Width:920,Height:760,ShowMaxButton:true,ShowMinButton:true});
	}
	
	//提交	
	function onSubmit(rowid){
		top.Dialog.confirm("确定提交吗？",function(){
		  	
		  	$.post("<%=path%>/cms/news/tjNews",
		  			{"ids":rowid,"CSRFToken":$("#CSRFToken").val()},
		  			function(result){
		  				//getToken();
		  				grid.loadData();
					},"json");
					//刷新表格数据 
					//grid.loadData();
			});
	}
	
	
	
	//信息互递	
	function onSend(rowid){
		top.Dialog.open({
			URL:"<%=path%>/cms/send/preEdit?newsId=" + rowid+"&url=cms/news/SendM",
			Title:"信息互递",Width:920,Height:600,ShowMaxButton:true,ShowMinButton:true});
	}
	
	//附件
	function onAttach(rowid) {
		top.Dialog.open({
			URL:"<%=path%>/cms/attach/preEdit?newsId=" + rowid+"&url=cms/news/AttachM",
			Title:"附件管理",Width:800,Height:600});
	}
	
	
	//删除	
	function onDelete(rowid){
		top.Dialog.confirm("确定要删除该记录吗？",function(){
		  
		  	//删除记录
		  	$.post("<%=path%>/cms/news/del",
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
			
			$.post("<%=path%>/cms/news/delBatch",
					//获取所有选中行
					getSelectIds(grid),
					function(result){
						//getToken();
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