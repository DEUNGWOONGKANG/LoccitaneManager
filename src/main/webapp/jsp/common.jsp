<%@page import="com.loccitane.user.domain.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
	User user = (User)session.getAttribute("loginUser");
	if(user == null){
		response.sendRedirect("/manager/logout");
	}
		
	
%>
