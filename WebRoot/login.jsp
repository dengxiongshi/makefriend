<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<!-- 负责提供输入登录信息的界面 -->
<html>
<head>
<%@ include file="head.txt"%>
</head>

<body bgcolor="pink">
	<font size="2"><center>
			<br> <br>
			<form action="helpLogin" method="post">
				<table border="2">
					<tr>
						<th>请您登录</th>
					</tr>
					<tr>
						<td>登录名称:<input type="text" name="logname"></td>
					</tr>
					<tr>
						<td>输入密码:<input type="password" name="password"></td>
					</tr>
				</table>
				<br> <input type="submit" name="g" value="提交">
			</form>
		</center> </font>
</body>
</html>
