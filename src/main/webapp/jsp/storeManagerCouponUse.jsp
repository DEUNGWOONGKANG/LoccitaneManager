<%@page import="com.loccitane.user.domain.User"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title></title>
<script type="text/javascript">
function check(){
	var phone = document.getElementById("phone");
	if(phone.value == "" || phone.value.length != 4){
		alert("�޴��� �� ��ȣ 4�ڸ��� �Է��ϼ���");
		return false;
	}else{
		document.searchForm.submit();
	}
}
</script>
</head>
<body>
<jsp:include page="storeManagerHeader.jsp"></jsp:include>
<div class="wrapper">
	<form id="searchForm" action="/manager/userSearch" method="post" onsubmit="return check()">
	<table style="width:100%; margin-top:20px;">
		<tr height="50px">
			<td width="10%">
			</td>
			<td>
				<h1>�������ó��</h1>
			</td>
			<td width="10%">
			</td>
		</tr>
		<tr height="50px">
			<td width="10%">
			</td>
			<td style="border-top:2px solid #ffcb00">
			</td>
			<td width="10%">
			</td>
		</tr>
		<tr>
			<td width="10%">
			</td>
			<td style="text-align:center; border:2px solid #ffcb00;height:400px;">
				<h1>�ڵ��� ��ȣ ���ڸ� 4�ڸ�</h1><br>
				<input type="text" class="endNumInput" id="phone" name="phone">
				<br><br>
				<input id="submitbtn" type="submit" class="button-yellow" value="�˻�">
			</td>
			<td width="10%">
			</td>
		</tr>
	</table>
	</form>
</div>
</body>
</html>