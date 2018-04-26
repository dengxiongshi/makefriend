<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="mybean.data.ModifyMessage"%>
<jsp:useBean id="modify" type="mybean.data.ModifyMessage"
	scope="request" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!-- 显示修改的反馈信息 -->
<html>
<head>
<%@ include file="head.txt"%>
</head>

<body bgcolor="yellow">
	<font size="3">
		<center>
			<jsp:getProperty name="modify" property="backNews" />, 您修改信息如下:
			<table border="1">
				<tr>
					<td>新电话</td>
					<td>新email</td>
					<td>新简历和交友标准</td>
				</tr>
				<tr>
					<td><jsp:getProperty name="modify" property="newPhone" /></td>
					<td><jsp:getProperty name="modify" property="newEmail" /></td>
					<td><textarea>
						<jsp:getProperty name="modify" property="newMessage"/>
					</textarea></td>
				</tr>
			</table>
		</center>
	</font>
</body>
</html>
