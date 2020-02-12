<%@page import="com.loccitane.user.domain.User"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title></title>
<link href="../../css/lc_userlist.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript">
function userSelect(userid, username){
	var result = confirm(username+"고객이 맞습니까?");
	if(result){
		location.href = '/manager/couponlist/'+userid;
	}
	
}
</script>
</head>
<body>
<jsp:include page="storeManagerHeader.jsp">
	<jsp:param name="menu" value="menu1"/>
</jsp:include>
<div class="wrapper">
	<form id="searchForm" action="/manager/userSearch" method="post">
	<table style="width:100%; margin-top:20px;">
		<tr height="50px">
			<td width="10%">
			</td>
			<td>
				<h1>쿠폰사용처리</h1>
			</td>
			<td width="10%">
			</td>
		</tr>
		<tr height="30px">
			<td width="10%">
			</td>
			<td style="border-top:2px solid #ffcb00">
			</td>
			<td width="10%">
			</td>
		</tr>
		<tr>
			<td width="10%">
			</td>
			<td style="text-align:center;">
				<h1>핸드폰 번호 뒷자리 4자리</h1><br>
			</td>
			<td width="10%">
			</td>
		</tr>
		<tr>
			<td width="10%">
			</td>
			<td style="text-align:center;">
				<input type="text" class="endNumInput" id="phone" name="phone" style="width:30%;height:60px;" value=${searchPhone }>
				<input id="submitbtn" type="submit" class="button-yellow" value="검색">
			</td>
			<td width="10%">
			</td>
		</tr>
	</table>
	</form>
	<c:if test="${!empty userData}">
	<table id="userList">
		<thead>
		<tr>
			<th>이름</th>
			<th>연락처</th>
			<th>등급</th>
			<th></th>
		</tr>
		</thead><!-- #userList Header -->
		<tbody>
		<c:forEach var="user" items="${userData}">
			<tr>
				<td>${user.username }</td>
				<td>${user.phone }</td>
				<td>${user.grade }</td>
				<td><input type="button" class="button-yellow-small" value="선택" onclick="userSelect('${user.userid}', '${user.username}')"></td>
			</tr><!-- #userList Row -->
		</c:forEach>
		</tbody>
	</table>
	</c:if>
	<c:if test="${empty userData}">
	<div class="nodata">
		사용자가 존재하지 않습니다.
	</div>
	</c:if>
</div>
</body>
</html>