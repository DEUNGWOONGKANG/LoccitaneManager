<%@page import="com.loccitane.user.domain.User"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title></title>
<link rel="stylesheet" href="../../css/lc_common.css">
<script type="text/javascript">
function loadScript(){
	if("${check}" == "N"){
		alert("�������� �ʴ� �����ID�Դϴ�.");
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
		alert("�޴��� �� ��ȣ 4�ڸ��� �Է��ϼ���");
		return false;
	}else{
		document.loginForm.submit();
	}
	
}

</script>
</head>
<body leftmargin="0" rightmargin="0" topmargin="0" onload="loadScript();">
<div id="headline"></div>
<div id="logo_div"><img id="logo" src="../../img/logo.png" width="50%"></div>
<form id="loginForm" action="/user/login" method="post" onsubmit="return check()">
<table width="100%">
	<tr>
		<td colspan=3 height="200px">
		</td>
	</tr>
	<tr>
		<td width="5%">
		</td>
		<td>
			<div class="infoTitle">�޴��� �� ��ȣ 4�ڸ��� �Է��ϼ���.</div>
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
			<div id="infoText">�� Ȯ���� ���� ������ �޴��� �� ��ȣ 4�ڸ��� �Է��ϼ���.
			<br>�Է� �� ���� Ȯ�� �������� �̵��մϴ�.</div>
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
				<input type="hidden" value="${userid}" id="userid" name="userid">
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
			<input id="submitbtn" type="submit" class="button-yellow" value="Ȯ��">
		</td>
		<td width="5%">
		</td>
	</tr>
</table>
</form>
</body>
</html>