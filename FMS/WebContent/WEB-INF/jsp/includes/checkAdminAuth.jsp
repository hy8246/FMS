<%@page import="com.syntelinc.fms.logic.Employee"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@ include file="checkUserAuth.jsp" %>
<% if (emp == null || !(emp.getAuthType().equals("A") || emp.getAuthType().equals("S")) )
		response.sendRedirect("/login");%>