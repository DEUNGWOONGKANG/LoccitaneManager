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
			  	<input type="radio" name="alarmyn" value="1">수신허용 <input type="radio" name="alarmyn" value="2">수신거부
			  </td>
		    </tr>
		    <tr>
		      <th scope="row">마지막수정일</th>
			  <td><fmt:formatDate value="${user.lastupdate}" pattern="YYYY-MM-dd"/></td>
		    </tr>
		  </tbody>
		</table>
			<div>
				<button type="button" class="btn btn-secondary">Secondary</button>
					<button type="button" class="btn btn-success">Success</button>
					<button type="button" class="btn btn-danger">Danger</button>
					<button type="button" class="btn btn-warning">Warning</button>
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