<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>LogOut</title>
</head>
<body>
<%
	session.invalidate(); // ���� �ʱ�ȭ
%>
<script>
alert("�α׾ƿ� �Ǿ����ϴ�.");
location.href='/';
</script>
</body>
</html>