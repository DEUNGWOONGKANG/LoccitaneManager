<%@page import="com.loccitane.user.domain.User"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<link rel="stylesheet" href="../../css/lc_common.css">
<script type="text/javascript">
function loadScript(){
	if("${check}" == "N"){
		alert("존재하지 않는 사용자ID입니다.");
		var phone = document.getElementById("phone");
		var submitbtn = document.getElementById("submitbtn");
		
		phone.style.background = "#EAEAEA";
		phone.readOnly = true;
		submitbtn.style.display = "none";
	}
}
function check(){
	var phone = document.getElementById("phone");
	if(phone.value == "" || phone.value.length != 4){
		alert("휴대폰 뒷 번호 4자리를 입력하세요");
		return false;
	}else{
		document.getElementById("loginForm").submit();
	}
	
}

</script>
</head>
<body leftmargin="0" rightmargin="0" topmargin="0" onload="loadScript();">
<div id="headline"></div>
<div id="logo_div"><img id="logo" src="../../img/logo.png" width="50%"></div>
<form id="loginForm" action="/user/login" method="post" onsubmit="return check()">
<table style="width:100%">
	<tr>
		<td colspan=3 height="200px">
		</td>
	</tr>
	<tr>
		<td width="5%">
		</td>
		<td>
			<div class="infoTitle">휴대폰 뒷 번호 4자리를 입력하세요.</div>
		</td>
		<td width="5%">
		</td>
	</tr>
	<tr>
		<td colspan=3 height="50px">
		</td>
	</tr>
	<tr>
		<td width="5%">
		</td>
		<td>
			<div id="infoText">고객 확인을 위해 본인의 휴대폰 뒷 번호 4자리를 입력하세요.
			<br>입력 후 쿠폰 확인 페이지로 이동합니다.</div>
		</td>
		<td width="5%">
		</td>
	</tr>
	<tr>
		<td colspan=3 height="100px">
		</td>
	</tr>
	<tr>
		<td width="5%">
		</td>
		<td width="90%" style="text-align:center;">
				<input type="password" class="endNumInput" id="phone" name="phone" style="width:70%;height:100px;">
				<input type="hidden" value="${usercode}" id="usercode" name="usercode">
		</td>
		<td width="5%">
		</td>
	</tr>
	<tr>
		<td colspan=3 height="100px">
		</td>
	</tr>
	<tr>
		<td width="5%">
		</td>
		<td width="90%" style="text-align:center;">
			<input id="submitbtn" type="submit" class="button-yellow" value="확인">
		</td>
		<td width="5%">
		</td>
	</tr>
</table>
</form>
</body>
</html>