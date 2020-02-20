<%@page import="com.loccitane.user.domain.User"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="common.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title></title>
<link href="<%=url %>/css/lc_userlist.css" rel="stylesheet" type="text/css" media="all" />
<link rel="stylesheet" href="<%=url %>/css/jquery-ui.css">
<script src="<%=url %>/js/jquery-3.4.1.min.js"></script>
<script src="<%=url %>/js/jquery-ui.js"></script>
<script type="text/javascript">
if("${giveyn}" == "Y"){
	alert("쿠폰이 발행되었습니다.");
}
$(function(){
    $("#cptmstartdate").datepicker();
    $("#cptmenddate").datepicker();
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

function userSearch(){
	var searchKey = document.getElementById("searchKey");
	var searchKeyword = document.getElementById("searchKeyword").value;
	searchKeyword = searchKeyword.replace(/ /g,"");
	if(searchKeyword == "" || searchKeyword == null){
		alert("검색어를 입력해주세요.");
		return false;
	}
	
	window.open('/manager/userSearch/'+searchKey.options[searchKey.selectedIndex].value+'/'+searchKeyword
			, 'userSearchPop', 'height='+ screen.height + 'width=' + screen.width + 'fullscreen=yes');
}
function check(){
	var couponKind = document.getElementById("couponKind");
	var username = document.getElementById("username");
	var startDate = document.getElementById("cptmstartdate");
	var endDate = document.getElementById("cptmenddate");
	
	startDate.value = $("#cptmstartdate").val();
	endDate.value = $("#cptmenddate").val();
	if(couponKind.options[couponKind.selectedIndex].value == ""){
		alert("쿠폰 종류를 선택해주세요.");
		return false;
	}
	if(startDate.value == "" || startDate.value == null){
		alert("쿠폰사용시작일을 선택해주세요.");
		return false;
	}
	if(endDate.value == "" || endDate.value == null){
		alert("쿠폰사용종료일을 선택해주세요.");
		return false;
	}
	
	if(username.value == "" || username.value == null){
		alert("발행대상고객을 선택해주세요.");
		return false;
	}
	
	var result = confirm(username.value+" 고객님에게 쿠폰을 발행하시겠습니까?");
	
	if(result){
		document.getElementById("searchForm").submit();
	}else{
		return false;
	}
}
</script>
</head>
<body>
<jsp:include page="storeManagerHeader.jsp"></jsp:include>
<div class="wrapper">
<form id="couponGive" action="/manager/coupongive" method="post" onsubmit="return check()">
	<table style="width:100%; margin-top:20px;">
		<tr height="50px">
			<td width="10%">
			</td>
			<td>
				<h1>쿠폰 요청</h1>
			</td>
			<td width="10%">
			</td>
		</tr>
		<tr height="30px">
			<td width="10%">
			</td>
			<td style="border-top:2px solid #ffcb00">
			</td>
			<td width="10%">
			</td>
		</tr>
	</table>
	<table id="userList">
		<tbody>
			<tr height="100px">
				<td width="25%">쿠폰선택</td>
				<td>
					<select class="selectBox" id="couponKind" name="cptmcpcode">
							<option value=""></option>
	   					<c:forEach var="coupon" items="${couponList}">
	   						<option value="${coupon.cpcode}">[${coupon.cpcode}] ${coupon.cpname }</option>
	   					</c:forEach>
			    	</select>
				</td>
			</tr>
			<tr height="100px">
				<td width="25%">쿠폰사용가능일</td>
				<td>
					시작일: <input type="text" class="inputDate" id="cptmstartdate" name="cptmstartdate" readonly>  
					종료일: <input type="text" class="inputDate" id="cptmenddate" name="cptmenddate" readonly>
					<input type="hidden" id="useyn" name="cptmusedyn" value="N">
				</td>
			</tr>
			<tr height="100px">
				<td width="25%" rowspan=2>발행대상고객</td>
				<td>
					<select id="searchKey" class="selectBox2">
						<option value="username">이름</option>
						<option value="phone">전화번호</option>
						<option value="userid">회원번호</option>
					</select>
					<input type="text" id="searchKeyword" class="inputText">  
					<input id="searchbtn" type="button" class="button-yellow-small" value="검색" onclick="userSearch()">
				</td>
			</tr>
			<tr height="150px">
				<td style="border-left:1px solid #e0e0e0;">
					<input type="hidden" id="userid" name="userid">
					<input type="hidden" id="grade" name="grade" readonly>
					이&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;름: <input type="text" class="viewText" id="username" readonly><br>
					전화번호: <input type="text" class="viewText" id="phone" name="phone" readonly><br>
				</td>
			</tr>
		</tbody>
	</table>
	<div style="text-align:center;margin-top:30px;">
		<input id="submitbtn" type="submit" class="button-yellow" value="쿠폰발행">
	</div>
</form>
</div>
</body>
</html>