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
function check(){
	var id = document.getElementById("code");
	var pw = document.getElementById("pw");
	var name = document.getElementById("name");
	if(name.value == ""){
		alert("매장명을 입력하세요.");
		name.focus();
		return false;
	}else if(id.value == ""){
		alert("매장코드를 입력하세요.");
		id.focus();
		return false;
	}else if(pw.value == ""){
		alert("비밀번호를 입력하세요.");
		pw.focus();
		return false;
	}else{
		var result = confirm("저장하시겠습니까?");
		
		if(result){
			document.getElementById("formdata").submit();
		}else{
			return false;
		}
	}
}
function goback(){
	history.back();
}

</script>
</head>
  <body>
    <!-- navbar-->
    <jsp:include page="superManagerHeader.jsp"></jsp:include>
    <div class="d-flex align-items-stretch">
      <jsp:include page="superManagerMenu.jsp"></jsp:include>
      <div class="w-100 d-flex flex-wrap">
      	<form id="formdata" action="/super/storesave" method="post" style="width:100%" onsubmit="return check()">
      	<div style="width:100%">
	      	<table class="storeAdd">
			  <tbody>
			    <tr>
			      <th>매장명<span style="font-size:10px; color:red;">(필수)</span></th>
				  <td><input type="text" placeholder="매장명" class="form-control" id="name" name="name" style="width:70%"></td>
			    </tr>
			    <tr>
			      <th>매장코드<span style="font-size:10px;">(ID,<span style="color:red;">필수</span>)</span></th>
				  <td><input type="text" placeholder="매장코드" class="form-control" id="code" name="code" style="width:70%"></td>
			    </tr>
			    <tr>
			      <th>비밀번호<span style="font-size:10px; color:red;">(필수)</span></th>
				  <td><input type="text" placeholder="비밀번호" class="form-control" id="pw" name="pw" style="width:70%"></td>
			    </tr>
			    <tr>
			      <th>담당자명</th>
				  <td><input type="text" placeholder="담당자명" class="form-control" id="managername" name="managername" style="width:70%"></td>
			    </tr>
			    <tr>
			      <th>우편번호</th>
				  <td><input type="text" placeholder="우편번호" class="form-control" id="postcode" name="postcode" style="width:30%"></td>
			    </tr>
			    <tr>
			      <th>주소</th>
				  <td><input type="text" placeholder="주소" class="form-control" id="address" name="address" style="width:90%"></td>
			    </tr>
			    <tr>
			      <th>전화번호</th>
				  <td><input type="text" placeholder="전화번호" class="form-control" id="tel" name="tel" style="width:50%"></td>
			    </tr>
			    <tr>
			      <th>위치</th>
				  <td>위도 <input type="text" placeholder="위도" class="form-control" id="latitude" name="latitude" style="width:30%">
				   | 경도 <input type="text" placeholder="경도" class="form-control" id="longtitude" name="longtitude" style="width:30%"></td>
			    </tr>
			    <tr style="height:100px">
			      <th>메모</th>
				  <td><textarea class="form-control" id="memo" name="memo" style="width:98%;height:100%"></textarea></td>
			    </tr>
			  </tbody>
			</table>
			<div class="btndiv">
				<button type="button" class="btn btn-secondary" onclick="goback()">목록</button>
				<button type="submit" class="btn btn-warning">등록</button>
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