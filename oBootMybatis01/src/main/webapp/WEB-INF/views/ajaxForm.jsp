<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>다양한 ajax Test</h1>
<a href="/helloText">helloText</a><p>
<a href="/sample/sendV02?deptno=123">sample/sendV02(객체)</a><p>
<a href="/sendV03">sendV03</a><p>
<a href="/getDeptName?deptno=10">getDeptName(EmpController)</a><p>
<a href="/listEmpAjaxForm">listEmpAjaxForm(ajax JSP 연동)</a><p>
<a href="/listEmpAjaxForm2">listEmpAjaxForm2(ajax JSP 객체리스트 get)</a><p>
<a href="/listEmpAjaxForm3">listEmpAjaxForm3(ajax List를 controller로 전송)</a><p>
</body>
</html>