<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!-- 负责提供上传文件的表单 -->
<html>
<head>
<%@ include file="head.txt"%>
</head>
<body bgcolor="yellow">
	<font size="2" color="blue">
		<center>
			<br>文件将被上传到Web服务目录mkfriend的子目录image中. 
			<br>选择要上传的图像照片文件(名字不可以含有非ASCII码字符，比如汉字等):
			<form action="helpUpload" method="post" enctype="multipart/form-data">
				<input type="file" name="filename" size="40"> <input
					type="submit" name="g" value="提交">
			</form>
		</center>
	</font>
</body>
</html>
