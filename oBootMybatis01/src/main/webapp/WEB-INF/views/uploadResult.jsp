<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이미지 업로드 확인 & 삭제</title>
</head>
<body>
	<b>Image : </b> ${savedName } <p>
	<b>upLoad Image : </b> 
	<img alt="UpLoad Image" 
		 src="${pageContext.request.contextPath }/upload/${savedName}"><p>
		 
	<a href ="uploadFileDelete?delFile=${savedName}">upload삭제Test</a>
	<a href ="uploadFileDelete?delFile3=${savedName}">upload삭제Test3</a>
</body>
</html>