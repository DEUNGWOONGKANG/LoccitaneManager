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
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
    <!-- Google fonts - Popppins for copy-->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:300,400,800">
    <!-- orion icons-->
    <link rel="stylesheet" href="<%=url %>/css/orionicons.css">
    <!-- theme stylesheet-->
    <link rel="stylesheet" href="<%=url %>/css/style.default.css" id="theme-stylesheet">
    <!-- Favicon-->
    <!-- <link rel="shortcut icon" href="img/favicon.png?3">-->
<script type="text/javascript">
function check(){
	var searchKeyword = document.getElementById("searchKeyword");
	if(searchKeyword.value == "" || searchKeyword.value == null){
		alert("검색어를 입력해주세요.");
		return false;
	}else{
		document.getElementById("searchForm").submit();
	}
}
function userInfo(userid){
	location.href="/super/userinfo/"+userid;
}
</script>
  </head>
  <body>
    <!-- navbar-->
    <header class="header">
      <nav class="navbar navbar-expand-lg px-4 py-2 bg-white shadow"><a href="#" class="sidebar-toggler text-gray-500 mr-4 mr-lg-5 lead"><i class="fas fa-align-left"></i></a><a href="index.html" class="navbar-brand font-weight-bold text-uppercase text-base"><img src="<%=url %>/img/logo.png" style="width:150px"></a>
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
      <div id="sidebar" class="sidebar py-3">
        <div class="text-gray-400 text-uppercase px-3 px-lg-4 py-4 font-weight-bold small headings-font-family">ADMIN PAGE</div>
        <ul class="sidebar-menu list-unstyled">
          <li class="sidebar-list-item"><a href="/super/home" class="sidebar-link text-muted"><i class="o-home-1 mr-3 text-gray"></i><span><b>Home</b></span></a></li>
          <li class="sidebar-list-item"><a href="#" data-toggle="collapse" data-target="#pages" aria-expanded="false" aria-controls="pages" class="sidebar-link text-muted"><i class="o-database-1 mr-3 text-gray"></i><span><b>계정관리</b></span></a>
            <div id="pages" class="collapse show">
              <ul class="sidebar-menu list-unstyled border-left border-primary border-thick">
                <li class="sidebar-list-item"><a href="/super/userlist" class="sidebar-link text-muted pl-lg-5 active"><b>회원정보</b></a></li>
                <li class="sidebar-list-item"><a href="#" class="sidebar-link text-muted pl-lg-5"><b>엑셀업로드</b></a></li>
              </ul>
            </div>
          </li>
              <li class="sidebar-list-item"><a href="#" class="sidebar-link text-muted"><i class="o-imac-screen-1 mr-3 text-gray"></i><span><b>매장관리</b></span></a></li>
          <li class="sidebar-list-item"><a href="#" data-toggle="collapse" data-target="#pages2" aria-expanded="false" aria-controls="pages2" class="sidebar-link text-muted"><i class="o-wireframe-1 mr-3 text-gray"></i><span><b>쿠폰관리</b></span></a>
           <div id="pages2" class="collapse">
             <ul class="sidebar-menu list-unstyled border-left border-primary border-thick">
               <li class="sidebar-list-item"><a href="#" class="sidebar-link text-muted pl-lg-5"><b>쿠폰관리</b></a></li>
               <li class="sidebar-list-item"><a href="#" class="sidebar-link text-muted pl-lg-5"><b>사용자별쿠폰관리</b></a></li>
             </ul>
           </div>
         </li>
          <li class="sidebar-list-item"><a href="#" data-toggle="collapse" data-target="#pages3" aria-expanded="false" aria-controls="pages3" class="sidebar-link text-muted"><i class="o-sales-up-1 mr-3 text-gray"></i><span><b>통계</b></span></a>
           <div id="pages3" class="collapse">
             <ul class="sidebar-menu list-unstyled border-left border-primary border-thick">
               <li class="sidebar-list-item"><a href="#" class="sidebar-link text-muted pl-lg-5"><b>시간별 사용량</b></a></li>
               <li class="sidebar-list-item"><a href="#" class="sidebar-link text-muted pl-lg-5"><b>일자별 사용량</b></a></li>
               <li class="sidebar-list-item"><a href="#" class="sidebar-link text-muted pl-lg-5"><b>쿠폰별 사용량</b></a></li>
             </ul>
           </div>
         </li>
              <li class="sidebar-list-item"><a href="#" class="sidebar-link text-muted"><i class="o-table-content-1 mr-3 text-gray"></i><span><b>등급관리</b></span></a></li>
              <li class="sidebar-list-item"><a href="#" class="sidebar-link text-muted"><i class="o-survey-1 mr-3 text-gray"></i><span><b>카카오톡 발송관리</b></span></a></li>
        </ul>
      </div>
      <div class="w-100 d-flex flex-wrap">
      	<div class="container-fluid px-xl-5">
      		<div class="search">	
		    	<form id="searchForm" action="/super/userlist" method="post" onsubmit="return check()">
		    		<select class="form-control" id="searchKey" name="searchKey">
						<option value="username">이름</option>
						<option value="phone">전화번호</option>
						<option value="userid">회원번호</option>
					</select>
			  		<input type="text" class="form-control" placeholder="검색어" id="searchKeyword" name="searchKeyword">
			  		<input type="submit" class="btn btn-warning" value="검색">
				</form>
			</div>
      	</div>
      	<c:if test="${!empty userList.content}">
	      	<table class="table table-hover" style="margin-left:10px;">
			  <thead>
			    <tr>
			      <th scope="col">NO</th>
			      <th scope="col">고객번호</th>
			      <th scope="col">전화번호</th>
			      <th scope="col">이름</th>
			      <th scope="col">등급</th>
			      <th scope="col">마지막구매일</th>
			    </tr>
			  </thead>
			  <tbody>
			  	<c:forEach var="user" items="${userList.content}" varStatus="status">
				    <tr style="cursor:pointer;" onclick="userInfo('${user.userid}')">
				      <th scope="row">${(paging.curPage-1)*10+status.count}</th>
				      <td>${user.vipcode}</td>
				      <td>
				      	<c:set var = "plength" value = "${fn:length(user.phone)}"/>
						${fn:substring(user.phone, 0, 4)}****${fn:substring(user.phone, 8, plength)}
					  </td>
				      <td>
				      	<c:set var = "length" value = "${fn:length(user.username)}"/>
						${fn:substring(user.username, 0, 1)}*${fn:substring(user.username, 2, length)}
				      </td>
				      <td>${user.grade}</td>
				      <td><fmt:formatDate value="${user.lastpurchase}" pattern="YYYY-MM-dd"/></td>
				    </tr>
				</c:forEach>
			  </tbody>
			</table>
		</c:if>
		<c:if test="${empty userList.content}">
			<div style="text-align:center;width:100%">
				<h2>사용자가 존재하지 않습니다.</h2>
			</div>
		</c:if>
		<div class="container">
			<div class="row">
				<div class="col">
					<ul class="pagination">
						<c:if test="${paging.curRange > 1}">
							<li class="page-item"><a class="page-link" href="/super/userlist?page=${pageNum-10 }&searchKey=${searchKey}&searchKeyword=${searchKeyword}"><</a></li>
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