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
	if("${saveyn}" == "Y"){
		alert("저장되었습니다.");	
	}
function check(){
	var result = confirm("저장하시겠습니까?");
	
	if(result){
		document.getElementById("formdata").submit();
	}else{
		return false;
	}
}
function couponGive(){
	var userId = document.getElementById("userid").value;
	location.href="/super/coupongive/"+userId;
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
      	<form id="formdata" action="/super/modifyuser" method="post" style="width:50%" onsubmit="return check()">
      	<input type="hidden" value="${userData.userid}" name="userid" id="userid">
      	<div style="width:100%">
	      	<table class="userInfo">
			  <tbody>
			    <tr>
			      <th>회원번호</th>
				  <td>${userData.userid}</td>
			    </tr>
			    <tr>
			      <th>이름</th>
				  <td>${userData.username}</td>
			    </tr>
			    <tr>
			      <th>연락처</th>
				  <td>${userData.phone}</td>
			    </tr>
			    <tr>
			      <th>회원등급</th>
				  <td>
				  	<select class="selectB" id="grade" name="grade">
				  		<c:forEach var="grade" items="${gradeList}">
				  			<c:choose>
							<c:when test="${grade.name eq userData.grade }">
								<option value="${grade.name}" selected>${grade.name}</option>
							</c:when>
							<c:otherwise>
								<option value="${grade.name}" >${grade.name}</option>
							</c:otherwise>
							</c:choose>
				  		</c:forEach>
					</select>
				  </td>
			    </tr>
			    <tr>
			      <th scope="row">알람수신여부</th>
				  <td>
				  	<c:choose>
						<c:when test="${userData.alarmyn eq 'Y' }">
							<input type="radio" name="alarmyn" value="Y" checked>수신허용
							<input type="radio" name="alarmyn" value="N">수신거부
						</c:when>
						<c:otherwise>
							<input type="radio" name="alarmyn" value="Y">수신허용
							<input type="radio" name="alarmyn" value="N" checked>수신거부
						</c:otherwise>
					</c:choose>
				  </td>
			    </tr>
			    <tr>
			      <th scope="row">마지막수정일</th>
				  <td><fmt:formatDate value="${userData.lastupdate}" pattern="YYYY-MM-dd"/></td>
			    </tr>
			  </tbody>
			</table>
			<div class="btndiv">
				<button type="button" class="btn btn-secondary">목록</button>
				<button type="submit" class="btn btn-warning">수정</button>
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
			<div class="btndiv">
				<button type="button" class="btn btn-warning" onclick="couponGive()">쿠폰발행</button>
			</div>
		</div>
		</form>
      </div>
    </div>
    <!-- JavaScript files-->
    <script src="<%=url %>/js/jquery-3.4.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="<%=url %>/js/bootstrap.js"></script>
    <script src="<%=url %>/js/jquery.cookie.js"> </script>
    <script src="https://cdn.jsdelivr.net/npm/js-cookie@2/src/js.cookie.min.js"></script>
    <script src="<%=url %>/js/front.js"></script>
  </body>
</html>