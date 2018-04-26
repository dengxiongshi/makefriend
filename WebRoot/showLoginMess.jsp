<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="mybean.data.Login"%>
<jsp:useBean id="login" type="mybean.data.Login" scope="session" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!-- 负责显示登录反馈信息 -->
<html>
<head>
<%@ include file="head.txt"%>
</head>

<body bgcolor="pink">
	<center>
		<font size="4" color="blue"> <br><jsp:getProperty
				name="login" property="backNews" />
		</font> 
		<font size="2" color="cyan"> 
		<% if(login.getSuccess()==true){
    	%> 		<br>登录会员名称:<jsp:getProperty name="login" property="logname" />
		<% }
			else{
    	%> 		<br>登录会员名称:<jsp:getProperty name="login" property="logname" />
				<br>登录会员密码:<jsp:getProperty name="login" property="password" />
			<% }
   			 %>
		</font>
	</center>
</body>
</html>
