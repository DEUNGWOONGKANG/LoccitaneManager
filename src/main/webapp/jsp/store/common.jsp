<%@page import="com.loccitane.store.domain.Store"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
	Store user = (Store)session.getAttribute("loginUser");
	if(user == null){
		response.sendRedirect("/logout");
	}
		
	
%>
