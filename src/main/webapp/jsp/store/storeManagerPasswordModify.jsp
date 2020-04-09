<%@page import="com.loccitane.user.domain.User"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="common.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title></title>
<script type="text/javascript">
if("${saveyn}" == "Y"){
	alert("비밀번호가 변경되었습니다.");
}else if("${saveyn}" == "N"){
	alert("비밀번호 변경에 실패하였습니다.");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
}
function check(){
	var newpw = document.getElementById("newpw").value;
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
<jsp:include page="storeManagerHeader.jsp"></jsp:include>
<div class="wrapper">
	<form id="searchForm" action="/passwordModify" method="post" onsubmit="return check()">
	<input type="hidden" id="type" name="type" value="store">
	<table style="width:100%; margin-top:20px;">
		<tr height="50px">
			<td width="10%">
			</td>
			<td>
				<h1>비밀번호 변경</h1>
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
			<td style="text-align:center; border:2px solid #ffcb00;height:500px;">
				<input type="password" class="endNumInput" id="oldpw" name="oldpw" placeholder="기존 비밀번호">
				<br><br>
				<input type="password" class="endNumInput" id="newpw" name="newpw" placeholder="변경할 비밀번호"><br><br>
				<input type="password" class="endNumInput" id="newpw2" name="newpw2" placeholder="변경할 비밀번호 확인"><br><br><br>
				<input id="submitbtn" type="submit" class="button-yellow" value="변경">
			</td>
			<td width="10%">
			</td>
		</tr>
	</table>
	</form>
</div>
</body>
</html>