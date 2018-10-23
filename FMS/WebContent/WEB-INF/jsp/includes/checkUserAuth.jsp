<%@page import="com.syntelinc.fms.logic.Employee"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<% Employee emp = (Employee) session.getAttribute("emp");
	if (emp == null || emp.getAuthType() == null)
		response.sendRedirect("/login");%>