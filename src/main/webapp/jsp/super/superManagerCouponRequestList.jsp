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
if("${request}" == "Y"){
	alert("승인완료되었습니다.");	
}
function check(){
	var searchKeyword = document.getElementById("searchKeyword");
	if(searchKeyword.value == "" || searchKeyword.value == null){
		alert("검색어를 입력해주세요.");
		return false;
	}else{
		document.getElementById("searchForm").submit();
	}
}
function approval(seq){
	var result = confirm("쿠폰발행을 승인하시겠습니까?");
	
	if(result){
		location.href="/super/approval/"+seq;
	}else{
		return false;
	}
}
function allApproval(){
	var result = confirm("모든 쿠폰 발행을 승인하시겠습니까?");
	
	if(result){
		location.href="/super/allapproval";
	}else{
		return false;
	}
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
		    	<form id="searchForm" action="/super/couponrequest" method="post" onsubmit="return check()">
		    		<select class="form-control" id="searchKey" name="searchKey">
						<option value="cpcode">쿠폰코드</option>
						<option value="username">고객명</option>
						<option value="createuser">발행자</option>
					</select>
			  		<input type="text" class="form-control" placeholder="검색어" id="searchKeyword" name="searchKeyword">
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
			      <th scope="col">고객명</th>
			      <th scope="col">유효기간</th>
			      <th scope="col">발행사유</th>
			      <th scope="col">승인</th>
			    </tr>
			  </thead>
			  <tbody>
			  	<c:forEach var="coupon" items="${couponList.content}" varStatus="status">
				    <tr style="cursor:pointer;" onclick="couponInfo('${coupon.seq}')">
				      <th scope="row">${(paging.curPage-1)*10+status.count}</th>
				      <td>${coupon.cpcode}</td>
				      <td>${coupon.cpname}</td>
				      <td>${coupon.couponno}</td>
				      <td>${coupon.username}</td>
				      <td>
				      	<fmt:formatDate value="${coupon.startdate}" pattern="YYYY-MM-dd"/> ~ 
						<fmt:formatDate value="${coupon.enddate}" pattern="YYYY-MM-dd"/>
				      </td>
				      <td>${coupon.reason}</td>
				      <td>
				      	<c:if test="${coupon.requestyn eq 'Y'}">
				      		<button type="button" class="btn btn-secondary">승인완료</button>
				      	</c:if>
				      	<c:if test="${coupon.requestyn eq 'N'}">
							<button type="button" class="btn btn-warning" onclick="approval('${coupon.seq}')">승인</button>
				      	</c:if>
				      </td>
				    </tr>
				</c:forEach>
			  </tbody>
			</table>
		</c:if>
		<c:if test="${empty couponList.content}">
			<div style="text-align:center;width:100%">
				<h2>쿠폰이 존재하지 않습니다.</h2>
			</div>
		</c:if>
		<c:if test="${!empty couponList.content}">
		<div class="container">
			<div class="row">
				<div class="col">
					<ul class="pagination">
						<c:if test="${paging.curRange > 1}">
							<li class="page-item"><a class="page-link" href="/super/couponrequest?page=${(paging.curRange-2)*10+1}&searchKey=${searchKey}&searchKeyword=${searchKeyword}">&lt;</a></li>
						</c:if>
						<c:forEach var="pageNum" begin="${paging.startPage }" end="${paging.endPage }">
							<c:choose>
								<c:when test="${pageNum eq  paging.curPage}">
									<li class="page-item"><a class="page-link" href="/super/couponrequest?page=${pageNum }&searchKey=${searchKey}&searchKeyword=${searchKeyword}"><b>${pageNum }</b></a></li>
								</c:when>
								<c:otherwise>
									<li class="page-item"><a class="page-link" href="/super/couponrequest?page=${pageNum }&searchKey=${searchKey}&searchKeyword=${searchKeyword}">${pageNum }</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${paging.curRange < paging.rangeCnt}">
							<li class="page-item"><a class="page-link" href="/super/couponrequest?page=${paging.curRange*10+1 }&searchKey=${searchKey}&searchKeyword=${searchKeyword}">></a></li>
						</c:if>
					</ul>
				</div>
			</div>
		</div>
		</c:if>
		<div style="width:80%;margin-left:auto;margin-right:auto;text-align:right;">
			<input type="button" class="btn btn-warning" value="전체승인" onclick="allApproval()">
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