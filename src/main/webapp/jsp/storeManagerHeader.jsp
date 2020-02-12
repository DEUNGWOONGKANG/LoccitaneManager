<%@page import="com.loccitane.user.domain.User"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<head>
<meta charset="EUC-KR">
<title></title>
<link href="http://localhost:8081/css/lc_default.css" rel="stylesheet" type="text/css" media="all" />
<%
	String menuName = (String)session.getAttribute("menu");
%>
</head>
<body>
<div id="header-wrapper">
	<div id="header" class="container">
		<div id="logo">
        	<span><img src="/img/logo.png" style="width:200px"></span>
		</div>
		<div id="menu">
			<ul>
				<li id="menuli1"><a href="/manager/menu1" id="menu1" onclick="active('menuli1')">쿠폰사용처리</a></li>
				<li id="menuli2"><a href="#" id="menu2" onclick="active('menuli2')">시간별 사용량</a></li>
				<li id="menuli3"><a href="#" id="menu3" onclick="active('menuli3')">일자별 사용량</a></li>
				<li id="menuli4"><a href="#" id="menu4" onclick="active('menuli4')">쿠폰별 사용량</a></li>
				<li id="menuli5"><a href="#" id="menu5" onclick="active('menuli5')">쿠폰 요청</a></li>
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
</script>
