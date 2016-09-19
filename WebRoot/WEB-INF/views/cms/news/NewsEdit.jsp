<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改</title>
<!--框架必需start-->
<script type="text/javascript" src="<%=path%>/resources/libs/js/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/resources/libs/js/language/cn.js"></script>
<script type="text/javascript" src="<%=path%>/resources/libs/js/framework.js"></script>
<link href="<%=path%>/resources/libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="<%=path%>/resources/"/>

<!--框架必需end-->

<!--ueEditor编辑器start-->

<script type="text/javascript" charset="utf-8" src="<%=path%>/resources/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path%>/resources/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="<%=path%>/resources/ueditor/lang/zh-cn/zh-cn.js"></script>

<!--ueEditor编辑器start-->


<!-- 组合下拉框start -->
<script type="text/javascript" src="<%=path%>/resources/libs/js/form/selectCustom.js"></script>
<!-- 组合拉框end -->

<!-- 表单验证start -->
<script src="<%=path%>/resources/libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="<%=path%>/resources/libs/js/form/validation.js" type="text/javascript"></script>
<!-- 表单验证end -->

<!-- 表单异步提交start -->
<script src="<%=path%>/resources/libs/js/form/form.js" type="text/javascript"></script>
<!-- 表单异步提交end -->


<style type="text/css">

table {
border-collapse:collapse; /* 关键属性：合并表格内外边框(其实表格边框有2px，外面1px，里面还有1px哦) */
border:solid #999; /* 设置边框属性；样式(solid=实线)、颜色(#999=灰) */
border-width:1px 0 0 1px; /* 设置边框状粗细：上 右 下 左 = 对应：1px 0 0 1px */
} 
table th,table td {border:solid #999;border-width:0 1px 1px 0;padding:2px;}
tfoot td {text-align:center;}

.title{
width:60px;
text-align:right;
font-size: 12px; 
color: #555;
}

.items{
font-size: 12px; 
}
</style>

</head>
<body>
	
	
	
	<form id="newsFormId" action="<%=path%>/cms/news/saveEdit" method="post" target="frmright"  >
	
	<table cellspacing="1" border="0" style="width:100%;" >
	
	<td colspan="8"  >
				
				<textarea name="newsBody"  id="newsBody"  keepDefaultStyle="true" style="width:100%;height:480px;" >${doc.newsBody}</textarea>
				
				
			</td>
		</tr>
	
	
		
		
	
		<tr>
			<td ><div class="title" >标题</div></td>
			<td colspan="3" class="items">
				<input type="text" id="newsTitle" name="newsTitle"  value="${doc.newsTitle}" class="validate[required]"  style="width:430px;"/>
			</td>
			<td ><div class="title" >作者</div></td>
			<td   colspan="1" class="items">
				<input type="text" id="author" name="author"  value="${doc.author}"  style="width:120px;"/>
			</td>
			<td colspan="1" rowspan="2" class="title">
				简介
			</td>
			<td colspan="1" rowspan="2" class="items">
				<textarea name="newsBrief"  id="newsBrief"  style="width:120px;height:70px;" >${doc.newsBrief}</textarea>
				
			</td>
			
			
		</tr>
		<tr>
			<td>
			<div class="title" >
			来源(链接)
			</div></td>
			<td colspan="3" class="items">
				<input type="text" id="origin" name="origin" value="${doc.origin}"   style="width:430px;"/>
			</td>
			
			<td class="title">关键字</td>
			<td colspan="1" class="items">
				<input type="text" id="keyWd" name="keyWd"  value="${doc.keyWd}" style="width:120px;"/>
			</td>
		</tr>
		<tr>
			
		<tr>
			<td><div class="title" >限定</div></td>
			<td class="items">
				
					<select  name='allowed' class="validate[required]" selectedValue="${doc.allowed}" data='{"list":[{"value":"1","key":"公开使用"},{"value":"2","key":"登录使用"},{"value":"3","key":"本部门用"},{"value":"4","key":"个人使用"}]}' selWidth="180" ></select>
			
				<input type="hidden" name="lm.id" value="${doc.lm.id}"/>
				<input type="hidden" id="icoPath" name="icoPath" value="${doc.icoPath}"/>
					<input type="hidden" name="org.id" value="${doc.org.id}"/>
					<input type="hidden" name="id" value="${doc.id}"/>
					<input type="hidden" name="readCount" value="${doc.readCount}"/>
					<input type="hidden" name="status" value="${doc.status}"/>
				<input type="hidden" id="newslock" name="newslock" value="${doc.newslock}"/>
				<input id="CSRFToken" name="CSRFToken" type="hidden" value="${CSRFToken}"/>
			</td>
			<td><div class="title" > 模版</div></td>
			<td class="items">
				
					<select boxHeight="100" name='mesgType' id="mesgType" class="validate[required]" selectedValue="${doc.mesgType}" prompt="请选择" url="<c:url value='/sys/dic/dicSelect?parentId=3'/>"  selWidth="180" ></select>      
		
			</td>
			
			
			<td ><div class="title" > 推介 </div></td>
			<td  colspan="1" class="items">
				
				<select  id='showType' name='showType'  selectedValue="${doc.showType}" class="validate[required]"   data='{"list":[{"value":"1","key":"不推介"},{"value":"2","key":"推介"},{"value":"3","key":"头条"}]}' selWidth="120" ></select>
			
			</td>
			<td><div class="title" >序号</div></td>
			<td class="items">
				
					<input type="text" id="orderNum" name="orderNum" value="${doc.orderNum}"  class="validate[required]" style="width:120px;"/>
				
			</td>
			
		</tr>
		
		<tr style="text-align:center;">
			<td colspan="8">
				<input type="submit" value="提交"/>
				<input type="button" value="取消" onclick="top.Dialog.close()"/>
			</td>
		</tr>
	</table>
	
	</form>
<!-- 异步提交start -->
<script type="text/javascript">


	var ue;

function initComplete(){

	//window.UEDITOR_HOME_URL = "<c:url value='/resources/ueditor'/>";
	ue = UE.getEditor('newsBody');
	/* 2.传入参数表,添加到已有参数表里 */
	ue.ready(function() {
	ue.execCommand('serverparam', {
	        'newsId': '${doc.id}'
	    });
	});
		
	//表单提交
    $('#newsFormId').submit(function(){ 
	    //判断表单的客户端验证是否通过    
			var valid = $('#newsFormId').validationEngine({returnIsValid: true});		
			if(valid){

			   $(this).ajaxSubmit({
			        //表单提交成功后的回调
			        success: function(responseText, statusText, xhr, $form){
			        	//alert(responseText);
			        	var obj = jQuery.parseJSON(responseText);
			            top.Dialog.alert(obj.message,function(){
			            	closeWin();
			            })
			        }
			    }); 
			 }
		    //阻止表单默认提交事件
		    return false; 
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