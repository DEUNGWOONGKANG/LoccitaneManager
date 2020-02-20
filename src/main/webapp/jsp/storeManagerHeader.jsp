<%@page import="com.loccitane.user.domain.User"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@include file="common.jsp" %>
<!DOCTYPE html>
<head>
<meta charset="EUC-KR">
<title></title>
<link href="<%=url %>/css/lc_default.css" rel="stylesheet" type="text/css" media="all" />
<%
	String menuName = (String)session.getAttribute("menu");
%>
</head>
<body>
<div id="header-wrapper">
		<div id="loginInfo">
			<%=user.getUsername() %> ��
			<input id="logoutbtn" type="button" class="button-gray-small" value="�α׾ƿ�" onclick="logout()">
		</div>
	<div id="header" class="container">
		<div id="logo">
        	<span><a href="/manager/menu1"><img src="<%=url %>/img/logo.png" style="width:200px"></a></span>
		</div>
		<div id="menu">
			<ul>
				<li id="menuli1"><a href="/manager/menu1" id="menu1">�������ó��</a></li>
				<li id="menuli2"><a href="/manager/menu2" id="menu2">���� ��û</a></li>
				<li id="menuli3"><a href="#" id="menu3">�ð�����뷮</a></li>
				<li id="menuli4"><a href="#" id="menu4">���ں���뷮</a></li>
				<li id="menuli5"><a href="#" id="menu5">��������뷮</a></li>
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
	var result = confirm("�α׾ƿ� �Ͻðڽ��ϱ�?");
	if(result){
		location.href = "/manager/logout";
	}
}
</script>
