<%@page import="com.loccitane.user.domain.User"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="common.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title></title>
<link href="<%=url %>/css/lc_userlist.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript">
function userSelect(userid, username, phone, grade, seq){
	var result = confirm(username+"���� �½��ϱ�?");
	if(result){
		window.opener.document.getElementById("username").value = username;
		window.opener.document.getElementById("userid").value = userid;
		window.opener.document.getElementById("phone").value = phone;
		window.opener.document.getElementById("grade").value = grade;

		window.close();
	}
	
}
</script>
</head>
<body>
<div class="wrapper">
	<c:if test="${!empty userData}">
	<table id="userList">
		<thead>
		<tr>
			<th>�̸�</th>
			<th>����ó</th>
			<th>���</th>
			<th></th>
		</tr>
		</thead><!-- #userList Header -->
		<tbody>
		<c:forEach var="user" items="${userData}">
			<tr>
				<td>${user.username }</td>
				<td>${user.phone }</td>
				<td>${user.grade }</td>
				<td>
				<input type="button" class="button-yellow-small" value="����" onclick="userSelect('${user.userid}', '${user.username}','${user.phone}', '${user.grade}', '${user.seq}')">
				</td>
			</tr><!-- #userList Row -->
		</c:forEach>
		</tbody>
	</table>
	</c:if>
	<c:if test="${empty userData}">
	<div class="nodata">
		����ڰ� �������� �ʽ��ϴ�.
	</div>
	</c:if>
</div>
</body>
</html>