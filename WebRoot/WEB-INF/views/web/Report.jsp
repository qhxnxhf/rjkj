<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="<%=basePath%>">
    
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="<c:url value='/resources/dcld/Styles/xg.css'/>" rel="stylesheet" />
    <link href="<c:url value='/resources/dcld/Styles/css.css'/>" rel="stylesheet" />
    <title></title>
</head>
<body>
    <div class="report">
        <table>
            <tr>
                <th></th>
                <th colspan="17"><i class="icon-title left"></i>部属货车完成情况</th>
                <th colspan="8"><i class="icon-title left"></i>自备车完成情况</th>
                <th colspan="2"><i class="icon-title left"></i>厂轮</th>
            </tr>
            <tr>
                <td rowspan="2" class="border-right"><strong>11.10</strong></td>
                <td colspan="2">厂修</td>
                <td colspan="3">段修</td>
                <td colspan="3">换装改造</td>
                <td colspan="3">临修</td>
                <td colspan="2">临修整治</td>
                <td colspan="2">拉条改造</td>
                <td colspan="2">钩尾框改造</td>
                <td colspan="2" class="border-left">厂修</td>
                <td colspan="2">段修</td>
                <td colspan="2">圆销改造</td>
                <td colspan="2">钩尾框改造</td>
                <td class="border-left">计划</td>
                <td>60</td>
            </tr>
            <tr>
                <td>当日</td>
                <td>合计</td>
                <td>计划</td>
                <td>当日</td>
                <td>合计</td>
                <td>计划</td>
                <td>当日</td>
                <td>合计</td>
                <td>计划</td>
                <td>当日</td>
                <td>合计</td>
                <td>当日</td>
                <td>合计</td>
                <td>当日</td>
                <td>合计</td>
                <td>当日</td>
                <td>合计</td>
                <td class="border-left">当日</td>
                <td>合计</td>
                <td>当日</td>
                <td>合计</td>
                <td>当日</td>
                <td>合计</td>
                <td>当日</td>
                <td>合计</td>
                <td class="border-left">当日</td>
                <td>合计</td>
            </tr>
            <tr>
                <td class="border-right"><strong>西宁</strong></td>
                <td>2</td>
                <td>11</td>
                <td>280</td>
                <td>15</td>
                <td>100</td>
                <td></td>
                <td></td>
                <td></td>
                <td>294</td>
                <td>10</td>
                <td>41</td>
                <td>8</td>
                <td>27</td>
                <td>102</td>
                <td>2</td>
                <td>8</td>
                <td>8</td>
                <td class="border-left"></td>
                <td>1</td>
                <td>3</td>
                <td></td>
                <td></td>
                <td></td>
                <td>1</td>
                <td>3</td>
                <td class="border-left"></td>
                <td></td>
            </tr>
            <tr>
                <td class="border-right"><strong>格尔木</strong></td>
                <td>2</td>
                <td>11</td>
                <td>280</td>
                <td>15</td>
                <td>100</td>
                <td></td>
                <td></td>
                <td></td>
                <td>294</td>
                <td>10</td>
                <td>41</td>
                <td>8</td>
                <td>27</td>
                <td>102</td>
                <td>2</td>
                <td>8</td>
                <td>8</td>
                <td class="border-left"></td>
                <td></td>
                <td></td>
                <td>3</td>
                <td></td>
                <td>3</td>
                <td></td>
                <td></td>
                <td colspan="2" class="border-left">年累计</td>
            </tr>
            <tr>
                <td class="border-right"><strong>日超欠</strong></td>
                <td colspan="2">2</td>
                <td>114</td>
                <td colspan="2">15</td>
                <td colspan="3">100</td>
                <td>222</td>
                <td colspan="2">12</td>
                <td colspan="2">294</td>
                <td colspan="2">10</td>
                <td colspan="2">41</td>
                <td colspan="2" class="border-left"></td>
                <td colspan="2"></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td class="border-left"></td>
                <td></td>
            </tr>
            <tr>
                <td class="border-right"><strong>月合计</strong></td>
                <td colspan="2">2</td>
                <td>114</td>
                <td colspan="2">15</td>
                <td colspan="3">100</td>
                <td>222</td>
                <td colspan="2">12</td>
                <td colspan="2">294</td>
                <td colspan="2">10</td>
                <td colspan="2">41</td>
                <td colspan="2" class="border-left">1</td>
                <td colspan="2">1</td>
                <td colspan="2">1</td>
                <td colspan="2">1</td>
                <td class="border-left">123</td>
                <td>123</td>
            </tr>
            <tr>
                <td class="border-right"><strong>年累计</strong></td>
                <td colspan="2">2</td>
                <td>114</td>
                <td colspan="2">15</td>
                <td colspan="3">100</td>
                <td>222</td>
                <td colspan="2">12</td>
                <td colspan="2">294</td>
                <td colspan="2">10</td>
                <td colspan="2">41</td>
                <td colspan="2" class="border-left">234</td>
                <td colspan="2">234</td>
                <td colspan="2">234</td>
                <td colspan="2">234</td>
                <td colspan="2" class="border-left">234</td>
            </tr>
        </table>
    </div>
</body>
</html>
