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
	alert("������ ����Ǿ����ϴ�.");
}
$(function(){
    $("#cptmstartdate").datepicker();
    $("#cptmenddate").datepicker();
});
$.datepicker.setDefaults({
    dateFormat: 'yy-mm-dd',
    prevText: '<',
    nextText: '>',
    monthNames: ['1��', '2��', '3��', '4��', '5��', '6��', '7��', '8��', '9��', '10��', '11��', '12��'],
    monthNamesShort: ['1��', '2��', '3��', '4��', '5��', '6��', '7��', '8��', '9��', '10��', '11��', '12��'],
    dayNames: ['��', '��', 'ȭ', '��', '��', '��', '��'],
    dayNamesShort: ['��', '��', 'ȭ', '��', '��', '��', '��'],
    dayNamesMin: ['��', '��', 'ȭ', '��', '��', '��', '��'],
    showMonthAfterYear: true,
    yearSuffix: '��'
});

function userSearch(){
	var searchKey = document.getElementById("searchKey");
	var searchKeyword = document.getElementById("searchKeyword").value;
	searchKeyword = searchKeyword.replace(/ /g,"");
	if(searchKeyword == "" || searchKeyword == null){
		alert("�˻�� �Է����ּ���.");
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
		alert("���� ������ �������ּ���.");
		return false;
	}
	if(startDate.value == "" || startDate.value == null){
		alert("�������������� �������ּ���.");
		return false;
	}
	if(endDate.value == "" || endDate.value == null){
		alert("��������������� �������ּ���.");
		return false;
	}
	
	if(username.value == "" || username.value == null){
		alert("��������� �������ּ���.");
		return false;
	}
	
	var result = confirm(username.value+" ���Կ��� ������ �����Ͻðڽ��ϱ�?");
	
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
				<h1>���� ��û</h1>
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
				<td width="25%">��������</td>
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
				<td width="25%">������밡����</td>
				<td>
					������: <input type="text" class="inputDate" id="cptmstartdate" name="cptmstartdate" readonly>  
					������: <input type="text" class="inputDate" id="cptmenddate" name="cptmenddate" readonly>
					<input type="hidden" id="useyn" name="cptmusedyn" value="N">
				</td>
			</tr>
			<tr height="100px">
				<td width="25%" rowspan=2>�������</td>
				<td>
					<select id="searchKey" class="selectBox2">
						<option value="username">�̸�</option>
						<option value="phone">��ȭ��ȣ</option>
						<option value="userid">ȸ����ȣ</option>
					</select>
					<input type="text" id="searchKeyword" class="inputText">  
					<input id="searchbtn" type="button" class="button-yellow-small" value="�˻�" onclick="userSearch()">
				</td>
			</tr>
			<tr height="150px">
				<td style="border-left:1px solid #e0e0e0;">
					<input type="hidden" id="userid" name="userid">
					<input type="hidden" id="grade" name="grade" readonly>
					��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��: <input type="text" class="viewText" id="username" readonly><br>
					��ȭ��ȣ: <input type="text" class="viewText" id="phone" name="phone" readonly><br>
				</td>
			</tr>
		</tbody>
	</table>
	<div style="text-align:center;margin-top:30px;">
		<input id="submitbtn" type="submit" class="button-yellow" value="��������">
	</div>
</form>
</div>
</body>
</html>