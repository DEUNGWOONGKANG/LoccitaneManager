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
function gradeInfo(seq){
	location.href="/super/gradeinfo/"+seq;
}
function gradeAdd(){
	location.href="/super/gradeadd";
}

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
		    	<form id="searchForm" action="/super/couponlist" method="post" onsubmit="return check()">
				</form>
			</div>
      	</div>
      	<c:if test="${!empty gradeList}">
	      	<table class="table table-hover" style="margin-left:10px;">
			  <thead>
			    <tr>
			      <th scope="col">NO</th>
			      <th scope="col">등급코드</th>
			      <th scope="col">등급명(영문)</th>
			      <th scope="col">등급명(한글)</th>
			      <th scope="col">등급조건</th>
			      <th scope="col">사용여부</th>
			      <th scope="col">등급설명</th>
			    </tr>
			  </thead>
			  <tbody>
			  	<c:forEach var="grade" items="${gradeList}" varStatus="status">
				    <tr style="cursor:pointer;" onclick="gradeInfo('${grade.seq}')">
				      <th scope="row">${status.count}</th>
				      <td>${grade.code}</td>
				      <td>${grade.name}</td>
				      <td>${grade.kname}</td>
				      <td>${grade.minimum}</td>
				      <td>${grade.useyn}</td>
				      <td>${grade.memo}</td>
				    </tr>
				</c:forEach>
			  </tbody>
			</table>
		</c:if>
		<div style="width:80%;margin-left:auto;margin-right:auto;text-align:right;">
			<input type="button" class="btn btn-warning" value="생성" onclick="gradeAdd()">
		</div>
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