<%@page import="com.loccitane.user.domain.User"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="common.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title></title>
<link href="<%=url %>/css/lc_userlist.css" rel="stylesheet" type="text/css" media="all" />
<link href="<%=url %>/css/lc_default.css" rel="stylesheet" type="text/css" media="all" />
<link rel="stylesheet" href="<%=url %>/css/jquery-ui.css">
<script src="<%=url %>/js/jquery-3.4.1.min.js"></script>
<script src="<%=url %>/js/jquery-ui.js"></script>
<script type="text/javascript">
$(function(){
    $("#startdate").datepicker();
});
$.datepicker.setDefaults({
    dateFormat: 'yy-mm-dd',
    prevText: '',
    nextText: '',
    monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    dayNames: ['일', '월', '화', '수', '목', '금', '토'],
    dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
    dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
    showMonthAfterYear: true,
    yearSuffix: '년'
});
</script>
</head>
<body>
<jsp:include page="storeManagerHeader.jsp"></jsp:include>
<div class="wrapper">
	<form id="searchForm" action="/store/resulttotime" method="post">
	<input type="hidden" value="<%=user.getId() %>" id="id" name="id">
	<table style="width:100%; margin-top:20px;">
		<tr height="50px">
			<td width="10%">
			</td>
			<td>
				<h1>시간별 사용량</h1>
			</td>
			<td width="10%">
			</td>
		</tr>
		<tr height="50px">
			<td width="10%">
			</td>
			<td style="border-top:2px solid #ffcb00;font-size:25px; text-align:center;padding-top:20px;">
				등급: <select class="selectBox2" id="grade" name="grade">
					<option value="ALL">전체</option>
			  		<c:forEach var="grade" items="${gradeList}">
						<c:choose>
							<c:when test="${grade.name eq srchgrade }">
								<option value="${grade.name}" selected>${grade.name}</option>
							</c:when>
							<c:otherwise>
								<option value="${grade.name}" >${grade.name}</option>
							</c:otherwise>
						</c:choose>
			  		</c:forEach>
				</select>
			  	조회날짜: <input type="text" class="inputDate" id="startdate" name="startdate" readonly value="${srchday }">
			  	<input id="logoutbtn" type="submit" class="button-yellow-small" value="조회"> 
			</td>
			<td width="10%">
			</td>
		</tr>
		<tr>
			<td width="10%">
			</td>
			<td>
				<table id="userList">
					<tr style="height:50px;">
						<th>시간대</th>
						<th>쿠폰사용량</th>
					</tr>
					<c:set var="usetotal" value="0" />
					<c:forEach var="times" items="${times}" varStatus="num">
					<tr>
						<td>${num.count-1}시 - ${num.count}시</td>
						<td>${times}</td>
						<c:set var="usetotal" value="${usetotal + times}" />
					</tr>
					</c:forEach>
					<tr>
						<th>합계</th>
						<th>
							<c:out value="${usetotal }"></c:out>
						</th>
					</tr>
				</table>
			</td>
			<td width="10%">
			</td>
		</tr>
	</table>
	</form>
</div>
</body>
</html>