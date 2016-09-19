<%@ page language="java" import="java.util.*;" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
    <head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
       <title>FLV</title>
       	
       	<script type="text/javascript" src="<c:url value='/resources/flowplayer/flowplayer-3.2.13.min.js'/>"></script>
    </head>

    <body>

<div id="page">
		
		<h1>Minimal Flowplayer setup</h1>
	
		<p>View commented source code to get familiar with Flowplayer installation.</p>
		
		<!-- this A tag is where your Flowplayer will be placed. it can be anywhere -->
		
	
		
		
		<div style="display:block;width:520px;height:330px; margin:10px auto;"
		 id="player"></div>
	<script>
	// var conurl = 'rtmp://172.16.0.100:5080/oflaDemo/';
	//var address = /\/\/(.*?)\//.exec(window.location.href)[1];
	var conurl = "rtmp://192.168.1.100:5080/oflaDemo/";
	flowplayer("player", "<c:url value='/resources/flowplayer/flowplayer-3.2.18.swf'/>",
		{
		    plugins: {
		        rtmp: {
		        	url: "<c:url value='/resources/flowplayer/flowplayer.rtmp-3.2.13.swf'/>",
		        	netConnectionUrl: conurl
		        },
		        controls: {
		            url: "<c:url value='/resources/flowplayer/flowplayer.controls-3.2.16.swf'/>"
		        }
		    },
		    clip: {
		        autoPlay: false,
		        provider: "rtmp",
		        autoBuffering: true,
		        url:"9.flv"
		    }
		});
	</script>
	
	
	
    </body>
</html>