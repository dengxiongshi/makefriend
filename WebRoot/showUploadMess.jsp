<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="mybean.data.UploadFile"%>
<jsp:useBean id="upFile" type="mybean.data.UploadFile" scope="session" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!-- 显示上传文件的反馈信息 -->
<html>
<head>
<%@ include file="head.txt"%>
</head>
<body bgcolor="cyan">
	<center>
		<font size="2" color="blue"> <br><jsp:getProperty
				name="upFile" property="backNews" />
		</font> <br>
		<font size="2"> <br>保存后的文件名字:<jsp:getProperty
				name="upFile" property="savedFileName" /> <br> 
				<img src="image/<jsp:getProperty name="upFile" property="savedFileName"/>"
				width="150" height="120">图像效果
				</img>
		</font>
	</center>
</body>
</html>
