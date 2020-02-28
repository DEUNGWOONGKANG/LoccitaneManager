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
	var searchKeyword = document.getElementById("searchKeyword");
	if(searchKeyword.value == "" || searchKeyword.value == null){
		alert("검색어를 입력해주세요.");
		return false;
	}else{
		document.getElementById("searchForm").submit();
	}
}
function storeInfo(storeId){
	location.href="/super/storeinfo/"+storeId;
}
function storeAdd(){
	location.href="/super/storeadd";
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
		    	<form id="searchForm" action="/super/storelist" method="post" onsubmit="return check()">
		    		<select class="form-control" id="searchKey" name="searchKey">
						<option value="name">매장명</option>
						<option value="tel">전화번호</option>
					</select>
			  		<input type="text" class="form-control" placeholder="검색어" id="searchKeyword" name="searchKeyword">
			  		<input type="submit" class="btn btn-warning" value="검색">
				</form>
			</div>
      	</div>
      	<c:if test="${!empty storeList.content}">
	      	<table class="table table-hover" style="margin-left:10px;">
			  <thead>
			    <tr>
			      <th scope="col">NO</th>
			      <th scope="col">매장명</th>
			      <th scope="col">매장전화번호</th>
			      <th scope="col">매장코드</th>
			      <th scope="col">매장주소</th>
			    </tr>
			  </thead>
			  <tbody>
			  	<c:forEach var="store" items="${storeList.content}" varStatus="status">
				    <tr style="cursor:pointer;" onclick="storeInfo('${store.seq}')">
				      <th scope="row">${(paging.curPage-1)*10+status.count}</th>
				      <td>${store.name}</td>
				      <td>${store.tel}</td>
				      <td>${store.code}</td>
				      <td>${store.postcode} ${store.address}</td>
				    </tr>
				</c:forEach>
			  </tbody>
			</table>
		</c:if>
		<c:if test="${empty storeList.content}">
			<div style="text-align:center;width:100%">
				<h2>매장이 존재하지 않습니다.</h2>
			</div>
		</c:if>
		<div class="container">
			<div class="row">
				<div class="col">
					<ul class="pagination">
						<c:if test="${paging.curRange > 1}">
							<li class="page-item"><a class="page-link" href="/super/userlist?page=${pageNum-10 }&searchKey=${searchKey}&searchKeyword=${searchKeyword}">&lt;</a></li>
						</c:if>
						<c:forEach var="pageNum" begin="${paging.startPage }" end="${paging.endPage }">
							<c:choose>
								<c:when test="${pageNum eq  paging.curPage}">
									<li class="page-item"><a class="page-link" href="/super/userlist?page=${pageNum }&searchKey=${searchKey}&searchKeyword=${searchKeyword}"><b>${pageNum }</b></a></li>
								</c:when>
								<c:otherwise>
									<li class="page-item"><a class="page-link" href="/super/userlist?page=${pageNum }&searchKey=${searchKey}&searchKeyword=${searchKeyword}">${pageNum }</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${paging.curRange < paging.rangeCnt}">
							<li class="page-item"><a class="page-link" href="/super/userlist?page=${pageNum+10 }&searchKey=${searchKey}&searchKeyword=${searchKeyword}">></a></li>
						</c:if>
					</ul>
				</div>
			</div>
		</div>
		<div style="width:80%;margin-left:auto;margin-right:auto;text-align:right;">
			<input type="button" class="btn btn-warning" value="매장등록" onclick="storeAdd()">
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