<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="head.txt"%>
</head>
<body bgcolor="cyan">
	<center>
		<font size="3">
			<table>
				<form action="helpShowMember" method="post" name="form">
					<br>分页显示全体会员 <input type="hidden" value="1" name="showPage"
						size="6"> <input type="submit" value="显示" name="submit">
				</form>
				<form action="helpShowMember" method="get" name="form">
					<br>输入要查找的会员名: <input type="text" name="logname" size="6">
					<input type="submit" value="显示" name="submit">
				</form>
			</table>
		</font>
	</center>
</body>
</html>
