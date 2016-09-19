<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>
	修改
</title>
<!--框架必需start-->
<script type="text/javascript" src="<c:url value='/resources/libs/js/jquery.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/libs/js/language/cn.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/libs/js/framework.js'/>"></script>
<link href="<c:url value='/resources/libs/css/import_basic.css'/>" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="<c:url value='/resources/'/>"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->



<!-- 表单验证start -->
<script src="<c:url value='/resources/libs/js/form/validationRule.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/libs/js/form/validation.js'/>" type="text/javascript"></script>
<!-- 表单验证end -->



<!-- 表单异步提交start -->
<script src="<c:url value='/resources/libs/js/form/form.js'/>" type="text/javascript"></script>
<!-- 表单异步提交end -->

<!-- 树组件start -->
<script type="text/javascript" src="<c:url value='/resources/libs/js/tree/ztree/ztree.js'/>"></script>
<link type="text/css" rel="stylesheet" href="<c:url value='/resources/libs/js/tree/ztree/ztree.css'/>"></link>
<!-- 树组件end -->

<!-- 树形下拉框start -->
<script type="text/javascript" src="<c:url value='/resources/libs/js/form/selectTree.js'/>"></script>
<!-- 树形下拉框end -->

<!-- 组合下拉框start -->
<script type="text/javascript" src="<c:url value='/resources/libs/js/form/selectCustom.js'/>"></script>
<!-- 组合拉框end -->
<!-- 上传文件列表start -->
<script type="text/javascript" src="<c:url value='/resources/js/ajaxFileUploader/ajaxfileupload.js'/>"></script>
<!-- 上传文件列表end -->

<!--图片预览start-->
<script type="text/javascript" src="<c:url value='/resources/libs/js/pic/imgPreview.js'/>"></script>
<!--图片预览end-->
</head>
<body>
<input id="addfile" style="display: none" keepDefaultStyle="true"  name="addfile" type="file" accept=".png,.jpg,.gif,.jpeg" />
	
	<form id="myFormId" action="<c:url value='/cms/lm/saveEdit'/>" method="post" target="frmright">
	<div class="box1" id="formContent" whiteBg="true">
	<table class="tableStyle" formMode="line" >
		<tr>
			<td width="100" >父节点：</td>
			<td  >
				<input type="hidden" name="id" value="${dic.id}"/>
				<div class="selectTree " multiMode="false" name="parent.id"  selectedValue="${dic.parent.id}" url="<c:url value='/cms/lm/lmPrtTree?parentId=1&selfId=${dic.id}'/>"  selWidth="350" ></div>	
				
			</td>
		</tr>
		<tr>
			<td>序号：</td>
			<td><input type="text" name="orderNum"  value="${dic.orderNum}"  class="validate[required,custom[onlyNumber]]" watermark="请输入正整数" style="width:350px;"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td >名称：</td>
			<td>
				<input id="CSRFToken" name="CSRFToken" type="hidden" value="${CSRFToken}"/>
				<input type="hidden" name="id" value="${dic.id}"/>
				<input type="hidden" name="orgIdsMl" value="${dic.orgIdsMl}"/>
				<input type="hidden" name="orgIdsWj" value="${dic.orgIdsWj}"/>
				<input type="text" name="lmName" value="${dic.lmName}" class="validate[required]" watermark="请输入" style="width:350px;"/><span class="star">*</span>
				
			</td>
		</tr>
		<tr>
			<td >图标：</td>
			<td>
			<input type="text" id="lmIcon" name="lmIcon"  value="${dic.lmIcon}" style="width:350px;"/>
			<a href="javascript:;" id="upload" ref="${fileserverurl}${dic.lmIcon}" title="点击上传图片" keepDefaultStyle="true" class=" imgPreview">logo</a>
			
			</td>
		</tr>
		<tr>
			<td >URL：</td>
			<td>
				<input type="text" name="linkUrl"  value="${dic.linkUrl}" watermark="请输入" style="width:350px;"/>
			</td>
		</tr>
		<tr>
			<td>Target：</td>
			<td>
				<select  name="target"  selectedValue="${dic.target}" data='{"list":[{"value":"_blank","key":"新页"},{"value":"frmright","key":"当前"}]}' selWidth="350" >
				</select>
				
			</td>
		</tr>
		<tr>
			<td>类别：</td>
			<td>
				<select selectedValue="${dic.nodeType}" name="nodeType"  data='{"list":[{"value":"b","key":"分类"},{"value":"y","key":"叶子"},{"value":"u","key":"链接"}]}' selWidth="350" >
				</select>
				
			</td>
		</tr>
		<tr>
			<td>模板设定：</td>
			<td>
				<select boxHeight="100" name='moduleId' id="moduleId" selectedValue="${dic.moduleId}" class="validate[required]"  prompt="请选择" url="<c:url value='/sys/dic/dicSelect?parentId=3'/>"  selWidth="140" ></select>      
				<input type="hidden" name="moduleName" id="moduleName" value="${dic.moduleName}" />
				
			</td>
		</tr>
		<tr>
			<td>状态：</td>
			<td>
				<select selectedValue="${dic.status}" name="status"  data='{"list":[{"value":"y","key":"有效"},{"value":"n","key":"无效"}]}' selWidth="350" >
				</select>
				
			</td>
		</tr>
		
		<tr>
			<td >简介：</td>
			<td>
			<textarea name="lmBrief"  style="width:350px;height:80px;" >${dic.lmBrief}</textarea><span class="star">*</span>
			
			
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="submit" value="提交"/>
				<input type="button" value="取消" onclick="top.Dialog.close()"/>
			</td>
		</tr>
	</table>
	</div>
	</form>
<!-- 异步提交start -->
<script type="text/javascript">
function initComplete(){

	$("#moduleId").bind("change",function(){
		    if(!$(this).attr("relValue")){
		        top.Dialog.alert("没有选择节点");
		    }else{
		        //var node=$(this).data("selectedNode");
				$("#moduleName").val($(this).attr("relText"));
		    }
	});
	
    //表单提交
    $('#myFormId').submit(function(){ 
	    //判断表单的客户端验证是否通过
			var valid = $('#myFormId').validationEngine({returnIsValid: true});
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
	
//点击打开文件选择器  
    $("#upload").on('click', function() {  
        $('#addfile').click();  
    });  
      
    //选择文件之后执行上传 
	$('#addfile').on('change', function() {  
		upload();
	});
	
}


//logo设置
function upload(){
	$.ajaxFileUpload({
			type : 'POST',
			url : "<c:url value='/upload'/>",
			ecureuri:false,
			fileElementId:'addfile',
			dataType: 'text',
			data: {},
			error:function (data, status, e)//服务器响应失败处理函数
			{
						  var msg  = eval("("+data+")");
						  if(msg.success==true){var path = msg.path.substring(1,msg.path.length);
						
						  $("#img"+file).attr("src",'${fileserverurl}'+path);$("#"+file).val(path);}
						   { 
						     if(msg.msg==null)alert("上传文件失败");
						     else alert(msg.msg);
						     }
						  //alert("上传文件失败");
		     },
			success : function(data,status) {
					var msg = eval("("+data+")");
					if(msg.success==true){ 
						var path = msg.path.substring(1,msg.path.length);
						$("#lmIcon").val(path);
						$("#upload").attr("ref",'${fileserverurl}'+path); 
						if(msg.msg==""){
							//alert("OK");
							top.Dialog.alert("OK",null,null,null,2);
						}else{alert(msg.msg);}
					}
			} 
	});						
							
}
	
//重置
function closeWin(){
	
	//刷新数据
	//top.frmright.page_101.refresh(update);
	﻿﻿var tabfrmId="page_"+top.frmright.getCurrentTabId();
	top.frmright.document.getElementById(tabfrmId).contentWindow.refresh(true);
	//关闭窗口
	top.Dialog.close();
}
</script>
<!-- 异步提交end -->	
</body>
</html>