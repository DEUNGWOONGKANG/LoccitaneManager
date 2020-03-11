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
      	<c:if test="${!empty couponList.content}">
	      	<table class="table table-hover" style="margin-left:10px;">
			  <thead>
			    <tr>
			      <th scope="col">NO</th>
			      <th scope="col">쿠폰코드</th>
			      <th scope="col">쿠폰명</th>
			      <th scope="col">쿠폰번호</th>
			      <th scope="col">소유자</th>
			      <th scope="col">유효기간</th>
			      <th scope="col">상태</th>
			    </tr>
			  </thead>
			  <tbody>
			  	<c:forEach var="coupon" items="${couponList.content}" varStatus="status">
				    <tr style="cursor:pointer;">
				      <th scope="row">${(paging.curPage-1)*10+status.count}</th>
				      <td>${coupon.cpcode}</td>
				      <td>${coupon.cpname}</td>
				      <td>${coupon.couponno}</td>
				      <td>${coupon.username}[${coupon.usercode}]</td>
				      <td>
				      	<fmt:formatDate value="" pattern="YYYY-MM-dd"/>~
				      	<fmt:formatDate value="${coupon.enddate}" pattern="YYYY-MM-dd"/>
				      </td>
				      <td>
				      	<c:if test="${coupon.usedyn eq 'Y'}">사용</c:if>
				      	<c:if test="${coupon.usedyn eq 'N'}">미사용</c:if>
				      </td>
				    </tr>
				</c:forEach>
			  </tbody>
			</table>
		</c:if>
		<c:if test="${empty couponList.content}">
			<div style="text-align:center;width:100%">
				<h2>리스트가 존재하지 않습니다.</h2>
			</div>
		</c:if>
		<c:if test="${!empty couponList.content}">
		<div class="container">
			<div class="row">
				<div class="col">
					<ul class="pagination">
						<c:if test="${paging.curRange > 1}">
							<li class="page-item"><a class="page-link" href="/super/coupontomember?page=${(paging.curRange-2)*10+1 }&searchKey=${searchKey}&searchKeyword=${searchKeyword}">&lt;</a></li>
						</c:if>
						<c:forEach var="pageNum" begin="${paging.startPage }" end="${paging.endPage }">
							<c:choose>
								<c:when test="${pageNum eq  paging.curPage}">
									<li class="page-item"><a class="page-link" href="/super/coupontomember?page=${pageNum }&searchKey=${searchKey}&searchKeyword=${searchKeyword}"><b>${pageNum }</b></a></li>
								</c:when>
								<c:otherwise>
									<li class="page-item"><a class="page-link" href="/super/coupontomember?page=${pageNum }&searchKey=${searchKey}&searchKeyword=${searchKeyword}">${pageNum }</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${paging.curRange < paging.rangeCnt}">
							<li class="page-item"><a class="page-link" href="/super/coupontomember?page=${paging.curRange*10+1 }&searchKey=${searchKey}&searchKeyword=${searchKeyword}">></a></li>
						</c:if>
					</ul>
				</div>
			</div>
		</div>
		</c:if>
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