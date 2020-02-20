<%@page import="com.loccitane.user.domain.User"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="common.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title></title>
<link href="<%=url %>/css/lc_userlist.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript">
function userSelect(userid, username){
	var result = confirm(username+"���� �½��ϱ�?");
	if(result){
		location.href = '/manager/couponlist/'+userid;
	}
}

function dormant(){
	window.open('<%=url%>/jsp/dormantPop.jsp', 'dormant', 'height='+ screen.height + 'width=' + screen.width + 'fullscreen=yes');
}
</script>
</head>
<body>
<jsp:include page="storeManagerHeader.jsp"></jsp:include>
<div class="wrapper">
	<form id="searchForm" action="/manager/userSearch" method="post">
	<table style="width:100%; margin-top:20px;">
		<tr height="50px">
			<td width="10%">
			</td>
			<td>
				<h1>�������ó��</h1>
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
				<h1>�ڵ��� ��ȣ ���ڸ� 4�ڸ�</h1><br>
			</td>
			<td width="10%">
			</td>
		</tr>
		<tr>
			<td width="10%">
			</td>
			<td style="text-align:center;">
				<input type="text" class="endNumInput" id="phone" name="phone" style="width:30%;height:60px;" value=${searchPhone }>
				<input id="submitbtn" type="submit" class="button-yellow" value="�˻�">
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
			<th>�̸�</th>
			<th>����ó</th>
			<th>���</th>
			<th>������������</th>
			<th></th>
		</tr>
		</thead><!-- #userList Header -->
		<tbody>
		<c:forEach var="user" items="${userData}">
			<c:if test="${user.status == '1'}">
			<tr>
				<td>
				<c:set var = "length" value = "${fn:length(user.username)}"/>
				${fn:substring(user.username, 0, 1)}*${fn:substring(user.username, 2, length)}
				</td>
				<td>
				<c:set var = "plength" value = "${fn:length(user.phone)}"/>
				${fn:substring(user.phone, 0, 4)}****${fn:substring(user.phone, 8, plength)}
				</td>
				<td>${user.grade }</td>
				<td><fmt:formatDate value="${user.lastpurchase}" pattern="YYYY-MM-dd"/></td>
				<td><input type="button" class="button-yellow-small" value="����" onclick="userSelect('${user.userid}', '${user.username}')"></td>
			</tr><!-- �������� -->
			</c:if>
			<c:if test="${user.status == '9'}">
			<tr class="usen">
				<td>
				<c:set var = "length" value = "${fn:length(user.username)}"/>
				${fn:substring(user.username, 0, 1)}*${fn:substring(user.username, 2, length)}
				</td>
				<td>
				<c:set var = "plength" value = "${fn:length(user.phone)}"/>
				${fn:substring(user.phone, 0, 4)}****${fn:substring(user.phone, 8, plength)}
				</td>
				<td>${user.grade }</td>
				<td>
				<fmt:formatDate value="${user.lastpurchase}" pattern="YYYY-MM-dd"/>
				<fmt:formatDate value="${today}" pattern="YYYY-MM-dd"/>
				</td>
				<td><input type="button" class="button-gray-small" value="�޸�" onclick="dormant()"></td>
			</tr><!-- �޸� -->
			</c:if>
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