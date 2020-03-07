<%@page import="com.loccitane.user.domain.User"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="common.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title></title>
<link href="<%=url %>/css/lc_userlist.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript">
function userSelect(usercode, username){
	var result = confirm(username+"고객이 맞습니까?");
	if(result){
		location.href = '/store/couponlist/'+usercode;
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
	<form id="searchForm" action="/store/userSearch" method="post">
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
				<input type="number" class="endNumInput" id="phone" name="phone" style="width:30%;height:60px;" value=${searchPhone }>
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
			<th>마지막구매일</th>
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
			      	<c:if test="${plength+0 > 10}">
						${fn:substring(user.phone, 0, 3)}-****-${fn:substring(user.phone, 7, plength)}
					</c:if>
					<c:if test="${plength+0 < 5}">
						${fn:substring(user.phone, 0, 1)}**${fn:substring(user.phone, 3, plength)}
					</c:if>
				</td>
				<td>${user.grade }</td>
				<td><fmt:formatDate value="${user.lastpurchase}" pattern="YYYY-MM-dd"/></td>
				<td><input type="button" class="button-yellow-small" value="선택" onclick="userSelect('${user.usercode}', '${user.username}')"></td>
			</tr><!-- 정상사용자 -->
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
				<td><input type="button" class="button-gray-small" value="휴면" onclick="dormant()"></td>
			</tr><!-- 휴면 -->
			</c:if>
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