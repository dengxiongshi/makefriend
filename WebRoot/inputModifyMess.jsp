<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!-- 提供修改信息的界面 -->
<html>
<head>
<%@ include file="head.txt"%>
</head>
<body bgcolor="pink">
	<center>
		<font size="2">
			<form action="helpModifyMess" name="form" method="post">
				<table>
					<tr>
						<td>新联系电话:</td>
						<td><input type="text" name="newPhone"></td>
					</tr>
					<tr>
						<td>新电子邮件:</td>
						<td><input type="text" name="newEmail"></td>
					</tr>
				</table>
				<table>
					<tr>
						<td>新简历和交友标准:</td>
					</tr>
					<tr>
						<td><textarea name="newMessage" rows="6" cols="30"></textarea>
						</td>
					</tr>
					<tr>
						<td><input type="submit" name="g" value="提交修改"></td>
					</tr>
					<tr>
						<td><input type="reset" value="重置"></td>
					</tr>
				</table>
			</form>
		</font>
	</center>
</body>
</html>
