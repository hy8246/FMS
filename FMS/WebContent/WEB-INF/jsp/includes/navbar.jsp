<%@page import="com.syntelinc.fms.logic.Employee"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <!-- <a class="navbar-brand" href="home">Solr</a> -->
            <a class="navbar-brand" style="padding-top:3px;padding-bottom:3px;" href="home"><img src="resources/img/solr-logo.png" style="max-height:54px;" alt="Solr" /></a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <% Employee currentEmp = ((Employee)session.getAttribute("emp")); 
        		//Employee currentEmp = new Employee();
            if (currentEmp != null && currentEmp.getAuthType() != null) {%>
	            <ul class="nav navbar-nav">
	                <li><a href="my-reservations">My Reservations</a></li>
	                <li><a href="new-reservation">Create Reservation</a>
	                <% if(currentEmp.getAuthType().equals("A") || currentEmp.getAuthType().equals("S")) { %>
	               		<li><a href="add-resources">Add Room</a></li>
	                	<li><a href="report-landing">Reports</a></li>
	                <% } %>
	            </ul>
            <% } %>
            <ul class="nav navbar-nav navbar-right">
            	<% if (currentEmp != null && currentEmp.getAuthType() != null) { %>
            		<li><a>Welcome, <% out.print(currentEmp.getEmployeeName()); %></a></li>
<!--                 	<li><a href="location" style="background-color:#079FEF;"> </span>&nbsp;Change Location</a></li> -->
                	<li><a href="login" style="background-color:#EF0719;"><span class="fa far fa-fw fa-sign-out-alt"></span>&nbsp;Logout</a></li>
                <% } else { %>
                	<li><a href="login" style="background-color:#EF0719;">Login</a></li>
                <% } %>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>