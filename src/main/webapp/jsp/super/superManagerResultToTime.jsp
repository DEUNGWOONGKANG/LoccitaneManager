<%@include file="common.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<%    request.setCharacterEncoding("UTF-8"); %>
<html>
  <head>
    <title>Loccitane Admin Page</title>
    <!-- Bootstrap CSS-->
    <link rel="stylesheet" href="<%=url %>/css/bootstrap.css">
    <!-- Font Awesome CSS-->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css">
    <!-- Google fonts - Popppins for copy-->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:300,400,800">
    <!-- orion icons-->
    <link rel="stylesheet" href="<%=url %>/css/orionicons.css">
    <!-- theme stylesheet-->
    <link rel="stylesheet" href="<%=url %>/css/style.default.css" id="theme-stylesheet">
    <!-- Favicon-->
    <!-- <link rel="shortcut icon" href="img/favicon.png?3">-->
<script type="text/javascript">
$(function(){
    $("#startdate").datepicker();
    $("#enddate").datepicker();
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
    <!-- navbar-->
    <jsp:include page="superManagerHeader.jsp"></jsp:include>
    <div class="d-flex align-items-stretch">
      <jsp:include page="superManagerMenu.jsp"></jsp:include>
      <div class="w-100 d-flex flex-wrap">
      	<div class="container-fluid px-xl-5">
      		<div class="search">	
		    	<form id="searchForm" action="/super/resulttotime" method="post" onsubmit="return check()">
		    		매장:
		    		<select class="form-control" id="shop" name="shop">
	    				<option value="ALL">전체</option>
						<c:forEach var="store" items="${storeList}">
							<c:choose>
								<c:when test="${store.id eq srchid }">
									<option value="${store.id}" selected>${store.name}</option>
								</c:when>
								<c:otherwise>
									<option value="${store.id}" >${store.name}</option>
								</c:otherwise>
							</c:choose>
				  		</c:forEach>
					</select>
		    		등급:
		    		<select class="form-control" id="grade" name="grade">
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
			  		조회날짜:<input type="text" class="form-control" id="startdate" name="startdate" readonly value="${srchday }" style="width:120px;">
			  		<input type="submit" class="btn btn-warning" value="조회">
				</form>
			</div>
      	</div>
      	<table class="table table-hover" style="margin-left:10px;">
		  <thead>
		    <tr>
		      <th scope="col">시간대</th>
		      <th scope="col">쿠폰사용량</th>
		    </tr>
		  </thead>
		  <tbody>
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
		  </tbody>
		</table>
      </div>
    </div>
    <script src="<%=url %>/js/jquery-3.4.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="<%=url %>/js/bootstrap.js"></script>
    <script src="<%=url %>/js/jquery.cookie.js"> </script>
    <script src="https://cdn.jsdelivr.net/npm/js-cookie@2/src/js.cookie.min.js"></script>
    <script src="<%=url %>/js/front.js"></script>
  </body>
</html>