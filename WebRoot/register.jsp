<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!-- 负责提供输入注册信息 -->
<html>
<head>
<%@ include file="head.txt"%>
</head>

<body bgcolor="cyan">
	<font size=2>
		<center>
			<form action="helpRegister" name="form" method="post">
				<table>
					输入您的信息，会员名字必须由字母和数字组成，带*号项必须填写.
					<tr>
						<td>会员名称:</td>
						<td><input type="text" name="logname">*</td>
					</tr>
					<tr>
						<td>设置密码:</td>
						<td><input type="password" name="password">*</td>
					</tr>
					<tr>
						<td>电子邮件:</td>
						<td><input type="text" name="email"></td>
					</tr>
					<tr>
						<td>联系电话:</td>
						<td><input type="text" name="phone"></td>
					</tr>
				</table>
				
				<table>
					<tr>
						<td><font size="2">输入您的简历和交友标准:</font></td>
					</tr>
					<tr>
						<td><textarea rows="6" name="message" cols="30"></textarea></td>
					</tr>
					<tr>
						<td><input type="submit" name="g" value="提交"></td>
					</tr>
				</table>
			</form>
		</center>
	</font>
</body>
</html>

