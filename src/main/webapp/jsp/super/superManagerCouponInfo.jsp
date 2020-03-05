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
	var cpcode = document.getElementById("cpcode");
	var cpname = document.getElementById("cpname");
	var dck = document.getElementsByName("dck");
	var useyn = document.getElementsByName("useyn");
	var dckCheckYn = false;
	var useynCheckYn = false;
	//할인유형 선택 여부 확인
  	for(var i=0; i<dck.length; i++){
		if(dck[i].checked == true){
			dckCheckYn = true;
		}
	} 
  	for(var i=0; i<useyn.length; i++){
		if(useyn[i].checked == true){
			useynCheckYn = true;
		}
	} 
	if(cpcode.value == ""){
		alert("쿠폰코드를 입력하세요.");
		return false;
	}else if(cpname.value == ""){
		alert("쿠폰명을 입력하세요.");
		return false;
	}else if(!dckCheckYn){
		alert("할인 종류를 선택하세요.");
		return false;
	}else if(!useynCheckYn){
		alert("사용 여부를 선택하세요.");
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
      	<form id="formdata" action="/super/couponsave" method="post" style="width:100%" onsubmit="return check()">
      	<input type="hidden" id="seq" name="seq" value="${couponData.seq }">
      	<div style="width:100%">
	      	<table class="storeAdd">
			  <tbody>
			    <tr>
			      <th>쿠폰코드</th>
				  <td><input type="text" placeholder="쿠폰코드" class="form-control" id="cpcode" name="cpcode" style="width:70%" value="${couponData.cpcode }"></td>
			    </tr>
			    <tr>
			      <th>쿠폰명</th>
				  <td><input type="text" placeholder="쿠폰명" class="form-control" id="cpname" name="cpname" style="width:70%" value="${couponData.cpname }"></td>
			    </tr>
			    <tr>
			      <th>할인종류</th>
				  <td>
				  	<c:if test="${couponData.dck == 1}">
					  	<input type="radio" name="dck" value="1" checked>정액할인(원) 
					  	<input type="radio" name="dck" value="2">정률할인(%)
				  	</c:if>
				  	<c:if test="${couponData.dck == 2}">
					  	<input type="radio" name="dck" value="1">정액할인(원) 
					  	<input type="radio" name="dck" value="2" checked>정률할인(%)
				  	</c:if>
				  </td>
			    </tr>
			    <tr>
			      <th>할인액 / 률</th>
				  <td><input type="text" placeholder="할인액 / 률" class="form-control" id="dccnt" name="dccnt" style="width:30%" value="${couponData.dccnt }"></td>
			    </tr>
			    <tr>
			      <th>사용조건</th>
				  <td>
				  	<input type="text" placeholder="최소구매금액" class="form-control" id="minimum" name="minimum" style="width:30%" value="${couponData.minimum }">원 이상 구매시 사용가능 / 
				  	최대 <input type="text" placeholder="최대할인액" class="form-control" id="dcmax" name="dcmax" style="width:30%" value="${couponData.dcmax }">원 할인
				  </td>
			    </tr>
			    <tr>
			      <th>사용여부</th>
				  <td>
				  <c:if test="${couponData.useyn == 'Y'}">
					<input type="radio" name="useyn" value="Y" checked>사용 
				  	<input type="radio" name="useyn" value="N">미사용
				  </c:if>
				  <c:if test="${couponData.useyn == 'N'}">
					<input type="radio" name="useyn" value="Y">사용 
				  	<input type="radio" name="useyn" value="N" checked>미사용
				  </c:if>
				  </td>
			    </tr>
			    <tr style="height:100px">
			      <th>메모</th>
				  <td><textarea class="form-control" id="memo" name="memo" style="width:98%;height:100%">${couponData.memo}</textarea></td>
			    </tr>
			  </tbody>
			</table>
			<div class="btndiv">
				<button type="button" class="btn btn-secondary" onclick="goback()">목록</button>
				<button type="submit" class="btn btn-warning">수정</button>
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