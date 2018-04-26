<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="mybean.data.Register"%>
<jsp:useBean id="register" type="mybean.data.Register" scope="request" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<!-- 负责显示注册反馈信息 -->
<html>
<head>
<%@ include file="head.txt"%>
</head>

<body bgcolor="cyan">
	<center>
		<font size=4 color="blue"> 
		<br><jsp:getProperty name="register" property="backNews" />
		</font>
		<table>
			<tr>
				<td>注册的会员名称:</td>
				<td><jsp:getProperty name="register" property="logname" /></td>
			</tr>
			<tr>
				<td>注册的电子邮件:</td>
				<td><jsp:getProperty name="register" property="email" /></td>
			</tr>
			<tr>
				<td>注册的联系电话:</td>
				<td><jsp:getProperty name="register" property="phone" /></td>
			</tr>
		</table>

		<table>
			<tr>
				<td>您的简历和交友标准:</td>
			</tr>
			<tr>
				<td><textarea rows="6" cols="30" name="message">
					<jsp:getProperty name="register" property="message"/>
					</textarea>
				</td>
			</tr>
		</table>
	</center>
</body>
</html>
