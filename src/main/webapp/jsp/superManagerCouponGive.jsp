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
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" >
<!-- Google fonts - Popppins for copy-->
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:300,400,800">
<!-- orion icons-->
<link rel="stylesheet" href="<%=url %>/css/orionicons.css">
<!-- theme stylesheet-->
<link rel="stylesheet" href="<%=url %>/css/style.default.css" id="theme-stylesheet">
<link rel="stylesheet" href="<%=url %>/css/jquery-ui.css">
<script src="<%=url %>/js/jquery-3.4.1.min.js"></script>
<script src="<%=url %>/js/jquery-ui.js"></script>
<!-- Favicon-->
<!-- <link rel="shortcut icon" href="img/favicon.png?3">-->
<script type="text/javascript">
$(function(){
    $("#startdate").datepicker();
    $("#enddate").datepicker();
});
$.datepicker.setDefaults({
    dateFormat: 'yy-mm-dd',
    prevText: '<',
    nextText: '>',
    monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    dayNames: ['일', '월', '화', '수', '목', '금', '토'],
    dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
    dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
    showMonthAfterYear: true,
    yearSuffix: '년'
});
function check(){
	var couponKind = document.getElementById("cpcode");
	var username = document.getElementById("username");
	var startDate = document.getElementById("startdate");
	var endDate = document.getElementById("enddate");
	
	startDate.value = $("#startdate").val();
	endDate.value = $("#enddate").val();
	if(couponKind.options[couponKind.selectedIndex].value == ""){
		alert("쿠폰 종류를 선택해주세요.");
		return false;
	}
	if(startDate.value == "" || startDate.value == null){
		alert("쿠폰사용시작일을 선택해주세요.");
		return false;
	}
	if(endDate.value == "" || endDate.value == null){
		alert("쿠폰사용종료일을 선택해주세요.");
		return false;
	}
	if(username.value == "" || username.value == null){
		alert("발행대상고객을 선택해주세요.");
		return false;
	}
	
	var result = confirm(username.value+" 고객님에게 쿠폰을 발행하시겠습니까?");
	
	if(result){
		document.getElementById("formdata").submit();
	}else{
		return false;
	}
}
</script>
</head>
  <body>
    <!-- navbar-->
    <header class="header">
      <nav class="navbar navbar-expand-lg px-4 py-2 bg-white shadow"><a href="#" class="sidebar-toggler text-gray-500 mr-4 mr-lg-5 lead"><i class="fas fa-align-left"></i></a><a href="/super/home" class="navbar-brand font-weight-bold text-uppercase text-base"><img src="<%=url %>/img/logo.png" style="width:150px"></a>
        <ul class="ml-auto d-flex align-items-center list-unstyled mb-0">
          <li class="nav-item dropdown ml-auto"><a id="userInfo" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="nav-link dropdown-toggle"><span style="font-size:12px;"><%=user.getUsername() %>님</span></a>
            <div aria-labelledby="userInfo" class="dropdown-menu">
              <a href="/manager/logout" class="dropdown-item">Logout</a>
            </div>
          </li>
        </ul>
      </nav>
    </header>
    <div class="d-flex align-items-stretch">
      <jsp:include page="superManagerMenu.jsp"></jsp:include>
      <div class="w-100 d-flex flex-wrap">
      	<form id="formdata" action="/super/coupongive" method="post" style="width:100%" onsubmit="return check()">
      	<input type="hidden" value="${userData.userid}" name="userid">
      	<div style="width:100%">
	      	<table class="couponInfo">
			<tbody>
				<tr height="100px">
					<th width="25%">쿠폰선택</th>
					<td>
						<select class="selectBox" id="cpcode" name="cpcode">
								<option value=""></option>
		   					<c:forEach var="coupon" items="${couponList}">
		   						<option value="${coupon.cpcode}">[${coupon.cpcode}] ${coupon.cpname }</option>
		   					</c:forEach>
				    	</select>
					</td>
				</tr>
				<tr height="100px">
					<th width="25%">쿠폰사용가능일</th>
					<td>
						시작일: <input type="text" class="form-control" id="startdate" name="startdate" readonly>  
						종료일: <input type="text" class="form-control" id="enddate" name="enddate" readonly>
						<input type="hidden" id="useyn" name="useyn" value="N">
					</td>
				</tr>
				<tr height="100px">
					<th width="25%">발행대상고객</th>
					<td style="border-left:1px solid #e0e0e0;">
						<input type="hidden" id="userid" name="userid">
						<input type="hidden" id="grade" name="grade" readonly>
						이&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;름: <input type="text" class="form-control" id="username" readonly value="${userData.username }">
						연락처: <input type="text" class="form-control" id="phone" name="phone" readonly value="${userData.phone }"><br>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="btndiv">
			<button type="submit" class="btn btn-warning">쿠폰발행</button>
		</div>
		<table class="couponInfo">
			<thead>
			<tr>
				<th>쿠폰번호</th>
				<th>유효기간</th>
				<th>사용여부</th>
				<th>발행자</th>
				<th>사용일자</th>
				<th>사용담당자</th>
			</tr>
			</thead><!-- #userList Header -->
			<tbody>
			<c:if test="${!empty couponList}">
			<c:forEach var="coupon" items="${couponList}">
				<tr>
					<td>${coupon.cpname }</td>
					<td><fmt:formatDate value="${coupon.startdate}" pattern="YYYY-MM-dd"/> ~
						<fmt:formatDate value="${coupon.enddate}" pattern="YYYY-MM-dd"/></td>
					<td>
						<c:if test="${coupon.usedyn == 'Y'}">사용</c:if>
						<c:if test="${coupon.usedyn == 'N'}">미사용</c:if>
					</td>
					<td>${coupon.createuser}</td>
					<td>${coupon.usedate}</td>
					<td>${coupon.usemanager}</td>
				</tr><!-- #userList Row -->
			</c:forEach>
			</c:if>
			<c:if test="${empty couponList}">
				<tr>
					<td colspan=6>
						쿠폰이 존재하지 않습니다.
					</td>
				</tr>
			</c:if>
			</tbody>
		</table>
		</div>
		</form>
      </div>
    </div>
    <!-- JavaScript files-->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="<%=url %>/js/bootstrap.js"></script>
    <script src="<%=url %>/js/jquery.cookie.js"> </script>
    <script src="https://cdn.jsdelivr.net/npm/js-cookie@2/src/js.cookie.min.js"></script>
    <script src="<%=url %>/js/front.js"></script>
  </body>
</html>