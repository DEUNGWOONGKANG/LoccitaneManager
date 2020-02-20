<%@page import="com.loccitane.user.domain.User"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<%
	String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
	User user = (User)session.getAttribute("loginUser");
	if(user == null){
	%>
	<script>
	location.href = "/manager/logout";
	</script>
<%
		
	}
%>
