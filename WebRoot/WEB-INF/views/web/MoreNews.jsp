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
<link href="<c:url value='/resources/dcld/Styles/xg.css'/>" rel="stylesheet" />
<link href="<c:url value='/resources/dcld/Styles/css.css'/>" rel="stylesheet" />
<script src="<c:url value='/resources/dcld/Scripts/jquery-1.8.2.js'/>"></script>
<script src="<c:url value='/resources/dcld/Scripts/xg.js'/>"></script>
<script src="<c:url value='/resources/dcld/Scripts/js.js'/>"></script>
<title>更多</title>
<script type="text/javascript">
    
    //安全天数
function safeday(lmurl, orgId, target) {
    $.post(lmurl, {"orgId":orgId}, function (result) {
       var item='<table class="center"><colgroup><col class="col-0" /><col /><col class="width-12" /></colgroup>';
                
        $.each(result.rows, function (idx, obj) {
            item=item+'<tr><td class="col-0">' + obj.title + '</td><td><em>' + obj.days + '</em>天</td><td><i class="icon-safety"></i></td></tr>';    
            
        });
        item = item + '</table>';

        target.empty();
        target.html(item);
    }, "json");
}

//链接
function linkList1(lmurl, lmId, size, target) {
    $.post(lmurl + lmId + "/" + size + ".json", {}, function (result) {
        var item='';
        $.each(result.rows, function (idx, obj) {
           item=item+'<dd class="';
           if(idx%2==0){item+='left';}else{item+='right';}
           item=item+' text-center bg-img-link-'+idx+'"><div></div><a target="_blank" href="'+obj.origin+'">'+obj.newsTitle+'</a></dd>';
        });
        target.empty();
        target.html(item);
    }, "json");
}

 //链接
function linkList2(lmurl, lmId, size, target) {
    $.post(lmurl + lmId + "/" + size + ".json", {}, function (result) {
        var item='<option>----'+ result.title +'----</option>';
        $.each(result.rows, function (idx, obj) {
            item=item+'<option value="'+obj.origin+'">'+obj.newsTitle+'</option>';
        });
        target.empty();
        target.html(item);
    }, "json");
}
    
  //查询
    function searchHandler(){
    	//var lmid=$("#parentId").val();
		var kwd=$("#search").val();
		contentList("list","",kwd,"pager",20,5);
    }  
		
var safeUrl="<%=path%>/cms/safe/safedays";
var lmurl="<%=path%>/web/lmNews/";
$(function (){	
		var kwd=$("#search").val();
		if(kwd!=null){
			contentList("list","",kwd,"pager",20,5);
		}
		if("${lm.id}"!=null){
			contentList("list","${lm.id}",null,"pager",20,5);
		}
		if("${lm.id}"!=null&&kwd!=null){
			contentList("list","${lm.id}",kwd,"pager",20,5);
		}
	
		safeday(safeUrl, 1, $("#safety"));
		linkList1(lmurl, 47, 4, $("#linklist"));//部门链接
		linkList2(lmurl, 66, 20, $("#link1"));//部门链接1
		linkList2(lmurl, 67, 20, $("#link2"));//部门链接2
 })
    
</script>
</head>
<body>
<div class="wrapper">
	<div class="wrapper-left">
		<div class="wrapper-right">
			<div class="wrapper-main">
				<jsp:include page="/WEB-INF/views/web/Header.jsp"></jsp:include>
				<div class="main">
					<div class="layout-list-right right">
							<div class="box">
                                <h3>
                                    <i class="icon-box-safety left"></i>
                                    <span class="font-hei left">安全动态<br />
                                    </span>
                                </h3>
                                <div class="h3"></div>
                                <div id="safety" class="safety bg-safety">
                                    
                                </div>
                            </div>
                            <div class="img-link">
                                <dl id="linklist">
                                    <dd class="left text-center bg-img-link-5">
                                        <div></div>
                                        <a href="<%=path%>/web/preMoreNews?url=web/MoreNews&lmid=22">机关科室</a>
                                    </dd>
                                    <dd class="right text-center bg-img-link-2">
                                        <div></div>
                                        <a href="<%=path%>/web/preMoreNews?url=web/MoreNews&lmid=63">生产车间</a>
                                    </dd>
                                    <dd class="left text-center bg-img-link-3">
                                        <div></div>
                                        <a href="<%=path%>/web/preMoreNews?url=web/MoreNews&lmid=28">专项工作</a>
                                    </dd>
                                    <dd class="right text-center bg-img-link-4">
                                        <div></div>
                                        <a href="<%=path%>/web/preMoreNews?url=web/MoreNews&lmid=26">工作查询</a>
                                    </dd>
                                </dl>
                                <select  class="clear" id="link1" onChange="javascript:window.open(this.options[this.selectedIndex].value)" >
                                    
                                </select>
                                <select class="clear" id="link2" onChange="javascript:window.open(this.options[this.selectedIndex].value)" >
                                    
                                </select>
                            </div>
					<div class="box">
                                <h3>
                                    <i class="icon-box-link left"></i>
                                    <span class="font-hei left">友情链接<br />
                                    </span>
                                </h3>
                                <div class="h3"></div>
                                <div>
                                    <table class="link">
                                        <colgroup>
                                            <col class="width-12" />
                                            <col />
                                        </colgroup>
                                        <colgroup>
                                            <col class="width-12" />
                                            <col />
                                        </colgroup>
                                        <tr>
                                            <td><i class="icon-link"></i></td>
                                            <td><a href="#" target="_blank">青藏铁路公司网</a></td>
                                            <td><i class="icon-link"></i></td>
                                            <td><a href="#" target="_blank">路风监察信息网</a></td>
                                        </tr>
                                        <tr>
                                            <td><i class="icon-link"></i></td>
                                            <td><a href="#" target="_blank">安全风险管理信息系统</a></td>
                                            <td><i class="icon-link"></i></td>
                                            <td><a href="#" target="_blank">安全生产信息管理系统</a></td>
                                        </tr>
                                        <tr>
                                            <td><i class="icon-link"></i></td>
                                            <td><a href="#" target="_blank">高价互换配件信息管理系统</a></td>
                                            <td><i class="icon-link"></i></td>
                                            <td><a href="#" target="_blank">铁路货车结算审核系统登录</a></td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
					
					
					</div>
					<div class="layout-list-left">
						<div class="position">
							<div class="clear-left"></div>
						</div>
						<div class="list center">
							<h3 class="left"> <i class="icon-title left"></i> <em class="left">${lm.lmName}</em> </h3>
							<div class="ztree-search right width-260">
									<i class="icon-title-search right pointer" onclick="searchHandler()"></i>
									<input type="hidden" id="parentId" name="parentId" value="${lm.id}" />
									<input id="search" type="text" class="right" value="${kwd}" />
									 </div>
							<h6 class="clear-left"></h6>
							<div id="list"></div>
							<div id="pager"> </div>
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
