<%@page import="com.loccitane.user.domain.User"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="common.jsp" %>
<!DOCTYPE html>
<head>
<title></title>
<link href="<%=url %>/css/lc_default.css" rel="stylesheet" type="text/css" media="all" />
<%
	String menuName = (String)session.getAttribute("menu");
%>
</head>
<body>
<div id="header-wrapper">
		<div id="loginInfo">
			<%=user.getManagername() %> 님
			<input id="logoutbtn" type="button" class="button-gray-small" value="로그아웃" onclick="logout()">
			<input id="passwd" type="button" class="button-gray-small" value="비밀번호변경" style="width:140px;" onclick="pass()">
		</div>
	<div id="header" class="container">
		<div id="logo">
        	<span><a href="/store/menu1"><img src="<%=url %>/img/logo.png" style="width:200px"></a></span>
		</div>
		<div id="menu">
			<ul>
				<li id="menuli1"><a href="/store/menu1" id="menu1">쿠폰사용처리</a></li>
				<li id="menuli2"><a href="/store/menu2" id="menu2">쿠폰 요청</a></li>
				<li id="menuli3"><a href="/store/menu3" id="menu3">시간별사용량</a></li>
				<li id="menuli4"><a href="/store/menu4" id="menu4">일자별사용량</a></li>
				<li id="menuli5"><a href="/store/menu5" id="menu5">쿠폰별사용량</a></li>
			</ul>
		</div>
	</div>
</div>
</body>
<script type="text/javascript">
var menu =  "<%=menuName%>";
active(menu);

function active(num){
	var menu = document.getElementById("menu").getElementsByTagName("li");
	 for(var i=0; i<menu.length; i++){
		if(num == menu[i].id){
			document.getElementById(menu[i].id).className = "active";
		}else{
			document.getElementById(menu[i].id).className = "";
		}
	} 
}
function logout(){
	var result = confirm("로그아웃 하시겠습니까?");
	if(result){
		location.href = "/logout";
	}
}
function pass(){
	location.href = "/store/modify";
}
</script>
