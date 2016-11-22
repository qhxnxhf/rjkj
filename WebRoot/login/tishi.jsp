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
<title>添加</title>
<!--框架必需start-->
<script type="text/javascript" src="<c:url value='/resources/libs/js/jquery.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/libs/js/language/cn.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/libs/js/framework.js' />"></script>
<link href="<c:url value='/resources/libs/css/import_basic.css' />" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="<c:url value='/resources/' />"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->


<!-- 表单验证start -->
<script src="<c:url value='/resources/libs/js/form/validationRule.js' />" type="text/javascript"></script>
<script src="<c:url value='/resources/libs/js/form/validation.js' />" type="text/javascript"></script>
<!-- 表单验证end -->

<!-- 表单异步提交start -->
<script src="<c:url value='/resources/libs/js/form/form.js' />" type="text/javascript"></script>
<!-- 表单异步提交end -->

</head>
<body>
<div class="box1" panelWidth="550" position="center">

    

    <div class="msg_icon3"></div>
	
	
    <div class="padding_left50 padding_right15 padding_top20 minHeight_100 font_14 font_bold" >

   本系统内职工体检资料已被妥善保管，作保密处理，受检者资料有可能会用于公司职工健康状况分析，但不会对外披露其受检者的体检信息。隐私权得到法律保护；使用者必须遵守各种信息资料的保密制度。保证职工的隐私权，未经准许不得随意查阅和编辑；在有必要调取应用时，必须在授权范围内进行，同时保证不改动数据库机构，不影响系统正常使用，所有操作和使用者在被告知同意后，才能登陆。

    </div>

</div>
<table class="tableStyle" formMode="transparent" >
<tr>
<td><input type="button" value="同意" onclick="top.Dialog.close()"/>
<input type="button" value="离开" onclick="cancel()"/></td>
</tr>
</table>

<!-- 异步提交start -->
<script type="text/javascript">

function cancel(){
 top.location.href="http://10.216.4.46";
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