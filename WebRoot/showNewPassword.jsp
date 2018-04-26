<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="mybean.data.Password"%>
<jsp:useBean id="password" type="mybean.data.Password" scope="request" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="head.txt"%>
</head>

<body bgcolor="yellow">
	<center>
		<font size="2"> 
		<br><jsp:getProperty name="password" property="backNews" /> 
		<br>您的新密码:<jsp:getProperty name="password" property="newPassword" /> 
		<br>您的旧密码:<jsp:getProperty name="password" property="oldPassword" />
		</font>
	</center>
</body>
</html>
