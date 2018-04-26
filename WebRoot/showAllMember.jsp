<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="mybean.data.ShowByPage"%>
<jsp:useBean id="show" type="mybean.data.ShowByPage" scope="session" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="head.txt"%>
</head>

<body>
	<center>
		<br>当前显示的内容是:
		<table border="2">
			<tr>
				<th>会员名</th>
				<th>电话</th>
				<th>email</th>
				<th>简历和交友标准</th>
				<th>用户照片</th>
			</tr>
			<jsp:getProperty name="show" property="presentPageResult" />
		</table>
		<br>每页最多显示<jsp:getProperty name="show" property="pageSize" />条信息
		<br>当前显示第<font color="blue"> <jsp:getProperty name="show"
				property="showPage" />
		</font>页，共有 <font color="blue"><jsp:getProperty name="show"
				property="pageAllCount" /> </font>页. <br>单击"上一页"或"下一页"按钮查看信息
		<table>
			<tr>
				<td>
					<form action="helpShowMember" method="post">
						<input type="hidden" name="showPage"
							value="<%= show.getShowPage()-1 %>"> <input type="submit"
							name="g" value="上一页">
					</form>
				</td>
				<td>
					<form action="helpShowMember" method="post">
						<input type="hidden" name="showPage"
							value="<%= show.getShowPage()+1 %>"> <input type="submit"
							name="g" value="下一页">
					</form>
				</td>
				<td>
					<form action="helpShowMember" method="post">
						输入页码: <input type="text" name="showPage" size="5"> <input
							type="submit" name="g" value="提交">
					</form>
				</td>
			</tr>
		</table>
	</center>
</body>
</html>
