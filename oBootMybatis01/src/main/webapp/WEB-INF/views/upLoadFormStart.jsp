<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	upLoad Image : <img alt="upLoad Image" src="${pageContext.request.contextPath }/upload/${savedName}">
	<form action="uploadForm" id="form1" method="post" enctype="multipart/form-data">
	<input type="file" name="file1"><p>
	<input type="text" name="title"><p>
	<input type="submit" >
	</form>
</body>
</html>