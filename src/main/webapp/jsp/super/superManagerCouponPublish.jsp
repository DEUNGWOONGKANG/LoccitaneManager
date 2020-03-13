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
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" >
<!-- Google fonts - Popppins for copy-->
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:300,400,800">
<!-- orion icons-->
<link rel="stylesheet" href="<%=url %>/css/orionicons.css">
<!-- theme stylesheet-->
<link rel="stylesheet" href="<%=url %>/css/style.default.css" id="theme-stylesheet">
<link rel="stylesheet" href="<%=url %>/css/jquery-ui.css">
<script src="<%=url %>/js/jquery-3.4.1.min.js"></script>
<script src="<%=url %>/js/jquery-ui.js"></script>
<!-- Favicon-->
<!-- <link rel="shortcut icon" href="img/favicon.png?3">-->
<script type="text/javascript">
if("${saveyn}" == "Y"){
	alert("쿠폰이 발행되었습니다.");	
}
function usertype(type){
	if(type == "user"){
		$('#gradetr').css('display', 'none');
		$('#usertr1').css('display', '');
		$('#usertr2').css('display', '');
	}else if(type == "grade"){
		$('#gradetr').css('display', '');
		$('#usertr1').css('display', 'none');
		$('#usertr2').css('display', 'none');
	}
}
$(function(){
    $("#startdate").datepicker();
    $("#enddate").datepicker();
});
$.datepicker.setDefaults({
    dateFormat: 'yy-mm-dd',
    prevText: '<',
    nextText: '>',
    monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    dayNames: ['일', '월', '화', '수', '목', '금', '토'],
    dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
    dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
    showMonthAfterYear: true,
    yearSuffix: '년'
});
function check(){
	var couponKind = document.getElementById("cpcode");
	var username = document.getElementById("username");
	var startDate = $("#startdate").val();
	var endDate = $("#enddate").val();
	var reason = document.getElementById("reason");
	var reason_etc = document.getElementById("reason_etc");
	var type = document.getElementsByName("type");
	var typeCheckYn = false;
	var typeVal = "";
	var username = document.getElementById("username");
	
	for(var i=0; i<type.length; i++){
		if(type[i].checked == true){
			typeCheckYn = true;
			typeVal = type[i].value;
		}
	} 
	var start = startDate.split('-');
	var end = endDate.split('-');
	var stdt = new Date();
	var eddt = new Date();
	stdt.setFullYear(start[0], start[1]-1, start[2]);
	eddt.setFullYear(end[0], end[1]-1, end[2]);
	if(couponKind.options[couponKind.selectedIndex].value == ""){
		alert("쿠폰 종류를 선택해주세요.");
		return false;
	}
	if(startDate == "" || startDate == null){
		alert("쿠폰사용시작일을 선택해주세요.");
		return false;
	}
	if(endDate == "" || endDate == null){
		alert("쿠폰사용종료일을 선택해주세요.");
		return false;
	}
	if(stdt > eddt){
		alert("시작일과 종료일을 확인해주세요.");
		return false;
	}
	if(!typeCheckYn){
		alert("발행대상타입을 선택해주세요.");
		return false;
	}else{
		if(typeVal == "user"){
			if(username.value == "" || username.value == null){
				alert("발행대상고객을 선택해주세요");
				return false;
			}
		}
	}
	if(reason.options[reason.selectedIndex].value == "3"){
		if(reason_etc.value == "" || reason_etc.value == null){
			alert("발행사유를 입력해주세요.");
			return false;		
		}
	}
	
	var result = confirm(username.value+" 고객님에게 쿠폰을 발행하시겠습니까?");
	
	if(result){
		document.getElementById("formdata").submit();
	}else{
		return false;
	}
}
function change(){
	var reason = document.getElementById("reason");
	var val = reason.options[reason.selectedIndex].value;

	if(val == "3"){
		document.getElementById("reason_etc").readOnly = false;
	}else{
		document.getElementById("reason_etc").readOnly = true;
		document.getElementById("reason_etc").value = "";
	}
}
function userSearch(){
	var searchKey = document.getElementById("searchKey");
	var searchKeyword = document.getElementById("searchKeyword").value;
	searchKeyword = searchKeyword.replace(/ /g,"");
	if(searchKeyword == "" || searchKeyword == null){
		alert("검색어를 입력해주세요.");
		return false;
	}
	
	window.open('/store/userSearch/'+searchKey.options[searchKey.selectedIndex].value+'/'+searchKeyword
			, 'userSearchPop', 'height='+ screen.height + 'width=' + screen.width + 'fullscreen=yes');
}
</script>
</head>
  <body>
    <!-- navbar-->
    <jsp:include page="superManagerHeader.jsp"></jsp:include>
    <div class="d-flex align-items-stretch">
      <jsp:include page="superManagerMenu.jsp"></jsp:include>
      <div class="w-100 d-flex flex-wrap">
      	<form id="formdata" action="/super/couponpublish" method="post" style="width:100%" onsubmit="return check()">
      	<div style="width:100%">
	      	<table class="couponInfo">
			<tbody>
				<tr height="70px">
					<th width="25%">쿠폰선택</th>
					<td>
						<select class="form-control" id="cpcode" name="cpcode">
								<option value=""></option>
		   					<c:forEach var="allcoupon" items="${allCoupon}">
		   						<option value="${allcoupon.cpcode}">[${allcoupon.cpcode}] ${allcoupon.cpname }</option>
		   					</c:forEach>
				    	</select>
					</td>
				</tr>
				<tr height="70px">
					<th width="25%">쿠폰사용가능일</th>
					<td>
						시작일: <input type="text" class="form-control" id="startdate" name="startdate" readonly>  
						종료일: <input type="text" class="form-control" id="enddate" name="enddate" readonly>
						<input type="hidden" id="useyn" name="useyn" value="N">
					</td>
				</tr>
				<tr height="50px">
					<th width="25%">발행대상타입</th>
					<td>
						<input type="radio" name="type" value="user" onclick="usertype('user')"> 사용자별 발행&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="type" value="grade" onclick="usertype('grade')"> 등급별 발행
					</td>
				</tr>
				<tr height="70px" id="usertr1" style="display:none;">
					<th width="25%" rowspan=2>발행대상고객(사용자별)</th>
					<td>
						<select class="form-control" id="searchKey">
							<option value="username">이름</option>
							<option value="phone">전화번호</option>
							<option value="usercode">회원번호</option>
						</select>
						<input type="text" id="searchKeyword" class="form-control">  
						<input id="searchbtn" type="button" class="btn btn-warning" value="검색" onclick="userSearch()">
					</td>
				</tr>
				<tr height="70px" id="usertr2" style="display:none;">
					<td style="border-left:1px solid #e0e0e0;">
						<input type="hidden" id="usercode" name="usercode">
						이&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;름: <input type="text" class="form-control" id="username" readonly >
						연락처: <input type="text" class="form-control" id="phone" name="phone" readonly ><br>
					</td>
				</tr>
				<tr height="70px" id="gradetr" style="display:none;">
					<th width="25%">발행대상고객(등급별)</th>
					<td>
						<select id="grade" name="grade" class="form-control">
							<option value="ALL">&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;모든등급 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </option>
							<c:forEach var="grade" items="${gradeList}">
								<option value="${grade.name}" >${grade.name}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr height="70px">
					<th width="25%">발행사유</th>
					<td>
						<select id="reason" name="reason" class="form-control" onchange="change()">
							<option value="1">1. 교환/환불</option>
							<option value="2">2. 사용기한 만료</option>
							<option value="3">3. 기타</option>
						</select>
						<input type="text" class="form-control" id="reason_etc" name="reason_etc" readonly style="width:70%">
					</td>
				</tr>
			</tbody>
			</table>
			<div class="btndiv">
				<button type="submit" class="btn btn-warning">쿠폰발행</button>
			</div>
		</div>
		</form>
      </div>
    </div>
    <!-- JavaScript files-->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="<%=url %>/js/bootstrap.js"></script>
    <script src="<%=url %>/js/jquery.cookie.js"> </script>
    <script src="https://cdn.jsdelivr.net/npm/js-cookie@2/src/js.cookie.min.js"></script>
    <script src="<%=url %>/js/front.js"></script>
  </body>
</html>