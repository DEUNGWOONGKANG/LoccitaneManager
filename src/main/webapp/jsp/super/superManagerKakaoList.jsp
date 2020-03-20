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
function kakaoform(){
	location.href="/super/kakaoform";
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
			</div>
      	</div>
	      	<table class="table table-hover" style="margin-left:10px;">
			  <thead>
			    <tr>
			      <th scope="col">NO</th>
			      <th scope="col">발송자</th>
			      <th scope="col">발송정보</th>
			      <th scope="col">발송일자</th>
			    </tr>
			  </thead>
			  <tbody>
			  	<c:forEach var="log" items="${logList.content}" varStatus="status">
				    <tr>
				      <th scope="row">${(paging.curPage-1)*10+status.count}</th>
				      <td>${log.username}[${log.userid}]</td>
				      <td>${log.logcontent}</td>
				      <td><fmt:formatDate value="${log.logdate}" pattern="YYYY-MM-dd HH:mm:ss"/></td>
				    </tr>
				</c:forEach>
			  </tbody>
			</table>
		<div class="container">
			<ul class="pagination">
				<c:if test="${paging.curRange > 1}">
					<li class="page-item"><a class="page-link" href="/super/kakaolist?page=${(paging.curRange-2)*10+1 }">&lt;</a></li>
				</c:if>
				<c:forEach var="pageNum" begin="${paging.startPage }" end="${paging.endPage }">
					<c:choose>
						<c:when test="${pageNum eq  paging.curPage}">
							<li class="page-item"><a class="page-link" href="/super/kakaolist?page=${pageNum }"><b>${pageNum }</b></a></li>
						</c:when>
						<c:otherwise>
							<li class="page-item"><a class="page-link" href="/super/kakaolist?page=${pageNum }">${pageNum }</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${paging.curRange < paging.rangeCnt}">
					<li class="page-item"><a class="page-link" href="/super/kakaolist?page=${paging.curRange*10+1 }">></a></li>
				</c:if>
			</ul>
		</div>
		<div style="width:80%;margin-left:auto;margin-right:auto;text-align:right;">
			<input type="button" class="btn btn-warning" value="발송" onclick="kakaoform()">
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