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
	var code = document.getElementById("code");
	var name = document.getElementById("name");
	var kname = document.getElementById("kname");
	var useyn = document.getElementsByName("useyn");
	var minimum = document.getElementsByName("minimum");
	var useynCheckYn = false;
	for(var i=0; i<useyn.length; i++){
		if(useyn[i].checked == true){
			useynCheckYn = true;
		}
	} 
	if(code.value == ""){
		alert("등급코드를 입력하세요.");
		code.focus();
		return false;
	}else if(name.value == ""){
		alert("등급명(영문)을 입력하세요.");
		name.focus();
		return false;
	}else if(kname.value == ""){
		alert("등급명(한글)을 입력하세요.");
		kname.focus();
		return false;
	}else if(!useynCheckYn){
		alert("사용 여부를 선택하세요.");
		return false;
	}else if(minimum.value == ""){
		alert("등급조건(최소구매액)을 입력하세요.");
		minimum.focus();
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
      	<form id="formdata" action="/super/gradesave" method="post" style="width:100%" onsubmit="return check()">
      	<input type="hidden" id="oldseq" name="oldseq" value="${grade.seq }">
      	<div style="width:100%">
	      	<table class="storeAdd">
			  <tbody>
			    <tr>
			      <th>등급코드</th>
				  <td><input type="text" placeholder="등급코드" class="form-control" id="code" name="code" style="width:70%" value="${grade.code }"></td>
			    </tr>
			    <tr>
			      <th>등급명(영문)</th>
				  <td><input type="text" placeholder="등급명(영문)" class="form-control" id="name" name="name" style="width:70%" value="${grade.name }"></td>
			    </tr>
			    <tr>
			      <th>등급명(한글)</th>
				  <td><input type="text" placeholder="등급명(한글)" class="form-control" id="kname" name="kname" style="width:70%" value="${grade.kname }"></td>
			    </tr>
			    <tr>
			      <th>사용여부</th>
				  <td>
				  <c:if test="${grade.useyn eq 'Y'}">
				  <input type="radio" name="useyn" value="Y" checked> 사용
				  <input type="radio" name="useyn" value="N"> 미사용
				  </c:if>
				  <c:if test="${grade.useyn eq 'N'}">
				  <input type="radio" name="useyn" value="Y"> 사용
				  <input type="radio" name="useyn" value="N" checked> 미사용
				  </c:if>
				  <c:if test="${empty grade.useyn}">
				  <input type="radio" name="useyn" value="Y"> 사용
				  <input type="radio" name="useyn" value="N"> 미사용
				  </c:if>
				  
				  </td>
			    </tr>
			    <tr>
			      <th>등급조건</th>
				  <td><input type="number" placeholder="최소구매액" class="form-control" id="minimum" name="minimum" style="width:30%" value="${grade.minimum }">원</td>
			    </tr>
			    <tr style="height:100px">
			      <th>메모</th>
				  <td><textarea class="form-control" id="memo" name="memo" style="width:98%;height:100%">${grade.memo }</textarea></td>
			    </tr>
			  </tbody>
			</table>
			<div class="btndiv">
				<button type="button" class="btn btn-secondary" onclick="goback()">목록</button>
				<button type="submit" class="btn btn-warning">저장</button>
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
    <script src="<%=url %>/js/front.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/js-cookie@2/src/js.cookie.min.js"></script>
  </body>
</html>