<%@page import="com.loccitane.user.domain.User"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="common.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title></title>
<link href="<%=url %>/css/lc_userlist.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript">
function useCoupon(seq, usercode){
	var result = confirm("해당 쿠폰을 사용처리 하시겠습니까?");
	if(result){
		location.href = "/store/couponuse/"+usercode+"/"+seq;
	}
	
}
</script>
</head>
<body>
<jsp:include page="storeManagerHeader.jsp"></jsp:include>
<jsp:useBean id="today" class="java.util.Date" />
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
		<tr>
			<td width="10%">
			</td>
			<td style="text-align:center;border-bottom:2px solid #ffcb00; height:100px;">
			<c:set var = "plength" value = "${fn:length(userData.phone)}"/>
			<c:set var = "length" value = "${fn:length(userData.username)}"/>
			<h1>
			${fn:substring(userData.username, 0, 1)}*${fn:substring(userData.username, 2, length)} 
			/
			<c:if test="${plength+0 > 10}">
				${fn:substring(userData.phone, 0, 3)}-****-${fn:substring(userData.phone, 7, plength)}
			</c:if>
			<c:if test="${plength+0 < 5}">
				${fn:substring(userData.phone, 0, 1)}**${fn:substring(userData.phone, 3, plength)}
			</c:if> 
			/
			 ${userData.grade}
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
			<th style="width:30%">쿠폰명</th>
			<th style="width:10%">쿠폰코드</th>
			<th style="width:15%">유효기간</th>
			<th>혜택</th>
			<th style="width:10%"></th>
		</tr>
		</thead><!-- #userList Header -->
		<tbody>
		<c:forEach var="coupon" items="${couponList}">
			<tr>
				<td style="text-align:left;">${coupon.cpname }</td>
				<td style="text-align:left;">${coupon.cpcode }</td>
				<td><fmt:formatDate value="${coupon.startdate}" pattern="YYYY-MM-dd"/> <br>~
					<fmt:formatDate value="${coupon.enddate}" pattern="YYYY-MM-dd"/></td>
				<td style="text-align:left;">
					<c:if test="${!empty coupon.minimum}">
				      		<fmt:formatNumber value="${coupon.minimum}" pattern="#,###" />원 이상 구매시<br>
				    </c:if>
					<fmt:formatNumber value="${coupon.dccnt}" pattern="#,###" />
					<c:if test="${coupon.dck == '1'}">원</c:if>
					<c:if test="${coupon.dck == '2'}">%</c:if>
					할인<br>
				    <c:if test="${!empty coupon.discountmax}">
				      	(최대할인  <fmt:formatNumber value="${coupon.discountmax}" pattern="#,###" />원 까지)
				      	</c:if>
				</td>
				<td>
					<c:if test="${coupon.usedyn == 'N' && coupon.enddate > today && coupon.startdate < today}">
						<input type="button" class="button-yellow-small" value="사용처리" onclick="useCoupon('${coupon.seq}','${coupon.usercode}')" >
					</c:if>
					<c:if test="${coupon.usedyn == 'Y' || coupon.enddate < today || coupon.startdate > today}">
						<input type="button" class="button-gray-small" value="사용불가" disabled="disabled" >
					</c:if>
				</td>
			</tr><!-- #userList Row -->
		</c:forEach>
		</tbody>
	</table>
	</c:if>
	<c:if test="${empty couponList}">
	<div class="nodata">
		쿠폰이 존재하지 않습니다.
	</div>
	</c:if>
</div>
</body>
</html>