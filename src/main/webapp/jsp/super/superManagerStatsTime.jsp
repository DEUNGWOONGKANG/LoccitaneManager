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

</script>
  </head>
  <body>
  <jsp:useBean id="today" class="java.util.Date" />
    <!-- navbar-->
    <jsp:include page="superManagerHeader.jsp"></jsp:include>
    <div class="d-flex align-items-stretch">
      <jsp:include page="superManagerMenu.jsp"></jsp:include>
      <div class="w-100 d-flex flex-wrap">
      	<div class="container-fluid px-xl-5">
      		<div class="search">	
		    	<form id="searchForm" action="/super/coupontomember" method="post" onsubmit="return check()">
		    		등급
		    		<select class="form-control" id="grade" name="grade">
						<option value="ALL">전체</option>
				  		<c:forEach var="grade" items="${gradeList}">
							<option value="${grade.name}" >${grade.name}</option>
				  		</c:forEach>
					</select>
					매장
		    		<select class="form-control" id="store" name="store">
						<option value="ALL">전체</option>
				  		<c:forEach var="grade" items="${storeList}">
							<option value="${grade.name}" >${grade.name}</option>
				  		</c:forEach>
					</select>
					<input type="text" class="form-control" id="day" name="day" value="${today}">
			  		<input type="submit" class="btn btn-warning" value="검색">
				</form>
			</div>
      	</div>
	      	<table class="table table-hover" style="margin-left:10px;">
			  <thead>
			    <tr>
			      <th scope="col">시간</th>
			      <th scope="col">사용량</th>
			    </tr>
			  </thead>
			  <tbody>
			  	<c:forEach var="coupon" items="${useCouponList}" >
				    <tr style="cursor:pointer;">
				      <th scope="row">${(paging.curPage-1)*10+status.count}</th>
				      <td>
				      </td>
				    </tr>
				</c:forEach>
			  </tbody>
			</table>
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