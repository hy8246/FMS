<%@page import="com.syntelinc.fms.logic.Employee"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
            <a class="navbar-brand" href="index">Solr</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <% Employee currentEmp = ((Employee)session.getAttribute("emp")); 
            if (currentEmp.getAuthType() != null) {%>
            <ul class="nav navbar-nav">
                <!-- <li><a href="add_reservation/1_location.html">Create Reservation</a></li> -->
                <li><a href="my-reservations">My Reservations</a></li>
                <li><a href="new-reservation">Create Reservation</a>
                <% if(currentEmp.getAuthType().equals("A")) { %>
                <li><a href="add-resources">Add Room/Location</a></li>
                <li><a href="reports">Reports</a></li> <% } %>
            </ul><% } %>
            <ul class="nav navbar-nav navbar-right">
            <% if (currentEmp.getAuthType() != null) { %>
                <li><a href="location">Change Location</a></li> <% } %>
                <li class="active"><a href="login.html">Login</a></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>