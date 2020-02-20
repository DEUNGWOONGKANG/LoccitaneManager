<%@page import="com.loccitane.user.domain.User"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="common.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title></title>
<link href="<%=url %>/css/lc_userlist.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript">
function useCoupon(seq, userid){
	var result = confirm("�ش� ������ ���ó�� �Ͻðڽ��ϱ�?");
	if(result){
		location.href = "/manager/couponuse/"+userid+"/"+seq;
	}
	
}
</script>
</head>
<body>
<jsp:include page="storeManagerHeader.jsp"></jsp:include>
<jsp:useBean id="today" class="java.util.Date" />
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
		<tr>
			<td width="10%">
			</td>
			<td style="text-align:center;border-bottom:2px solid #ffcb00; height:50px;">
			<c:set var = "plength" value = "${fn:length(userData.phone)}"/>
			<c:set var = "length" value = "${fn:length(userData.username)}"/>
			<h1>
			${fn:substring(userData.username, 0, 1)}*${fn:substring(userData.username, 2, length)} 
			/ ${fn:substring(userData.phone, 0, 4)}****${fn:substring(userData.phone, 8, plength)} 
			/ ${userData.grade}
			</h1> 
			</td>
			<td width="10%">
			</td>
		</tr>
	</table>
	</form>
	<c:if test="${!empty couponList}">
	<table id="userList">
		<thead>
		<tr>
			<th>������</th>
			<th>��ȿ�Ⱓ</th>
			<th>����</th>
			<th></th>
		</tr>
		</thead><!-- #userList Header -->
		<tbody>
		<c:forEach var="coupon" items="${couponList}">
			<tr>
				<td>${coupon.cpname }</td>
				<td><fmt:formatDate value="${coupon.startdate}" pattern="YYYY-MM-dd"/> ~
					<fmt:formatDate value="${coupon.enddate}" pattern="YYYY-MM-dd"/></td>
				<td>${coupon.dccnt}
					<c:if test="${coupon.dck == '1'}">��</c:if>
					<c:if test="${coupon.dck == '2'}">%</c:if>
					����
				</td>
				<td>
					<c:if test="${coupon.usedyn == 'N' && coupon.enddate > today}">
						<input type="button" class="button-yellow-small" value="���ó��" onclick="useCoupon('${coupon.seq}','${coupon.userid}')" >
					</c:if>
					<c:if test="${coupon.usedyn == 'Y' || coupon.enddate < today}">
						<input type="button" class="button-gray-small" value="���Ұ�" disabled="disabled" >
					</c:if>
				</td>
			</tr><!-- #userList Row -->
		</c:forEach>
		</tbody>
	</table>
	</c:if>
	<c:if test="${empty couponList}">
	<div class="nodata">
		������ �������� �ʽ��ϴ�.
	</div>
	</c:if>
</div>
</body>
</html>