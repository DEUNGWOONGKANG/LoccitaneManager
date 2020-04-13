<%@page import="com.loccitane.user.domain.User"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="common.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title></title>
<script type="text/javascript">
function check(){
	document.getElementById("searchForm").submit();
}
</script>
</head>
<body>
<jsp:include page="storeManagerHeader.jsp"></jsp:include>
<div class="wrapper">
	<form id="searchForm" action="/store/userSearch" method="post" onsubmit="return check()">
	<table style="width:100%; margin-top:20px;">
		<tr height="50px">
			<td width="10%">
			</td>
			<td>
				<h1>쿠폰사용처리</h1>
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
				<h1>핸드폰 번호 검색</h1><br>
				<input type="number" class="endNumInput" id="phone" name="phone">
				<br><br>
				<input id="submitbtn" type="submit" class="button-yellow" value="검색">
			</td>
			<td width="10%">
			</td>
		</tr>
	</table>
	</form>
</div>
</body>
</html>