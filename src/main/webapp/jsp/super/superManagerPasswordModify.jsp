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
	alert("비밀번호가 변경되었습니다.");
}else if("${saveyn}" == "N"){
	alert("비밀번호 변경에 실패하였습니다.");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
}
function check(){
	vvar newpw = document.getElementById("newpw").value;
	var newpw2 = document.getElementById("newpw2").value;

	if(newpw == newpw2){
		var result = confirm("비밀번호를 변경하시겠습니까?");
		if(result){
			document.getElementById("searchForm").submit();
		}else{
			return false;
		}
	}else{
		alert("변경할 비밀번호가 일치하지 않습니다.");
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
      	<form id="searchForm" action="/passwordModify" method="post" style="width:100%" onsubmit="return check()">
      	<input type="hidden" id="type" name="type" value="super">
      	<div style="width:100%">
	      	<table class="userInfo" style="width:700px;">
			  <tbody>
			    <tr>
			      <th>기존 비밀번호</th>
				  <td style="width:"><input type="text" placeholder="기존 비밀번호" class="form-control" id="oldpw" name="oldpw" style="width:70%"></td>
			    </tr>
			    <tr>
			      <th>변경할 비밀번호</th>
				  <td><input type="text" placeholder="변경할 비밀번호" class="form-control" id="newpw" name="newpw" style="width:70%"></td>
			    </tr>
			    <tr>
			      <th>변경할 비밀번호 확인</th>
				  <td><input type="text" placeholder="변경할 비밀번호 확인" class="form-control" id="newps2" name="newps2" style="width:70%"></td>
			    </tr>
			  </tbody>
			</table>
			<div class="btndiv">
				<button type="submit" class="btn btn-warning">변경</button>
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