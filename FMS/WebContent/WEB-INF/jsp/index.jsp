<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="includes/head.jsp" %>
    <title>Solr</title>
    <style>
        .navbar {
            margin-bottom: 0;
        }
        
        body {
        	margin-bottom: 25px;
        }
        
        .jumbotron {
        	padding-top: 15px;
        	padding-bottom: 15px;
        }
    </style>
</head>
<body>
<%@ include file="includes/navbar.jsp" %>
<div class="jumbotron">
    <div class="container-fluid text-center">
        <img src="resources/img/syntel.png" alt="Syntel Logo" style="max-height: 50px;"/>
        <br />
        <br />
        <!-- <h1>Solr</h1> -->
        <img src="resources/img/solr-logo.png" alt="Solr" />
    </div>
</div>
<div class="container-fluid">
	<div class="row">
        <div class="col-sm-12 text-center">
            <p><a href="login" class="btn btn-warning">My Reservations &raquo;</a>&nbsp;<a href="login" class="btn btn-warning">Create a Reservation &raquo;</a></p>
        </div>
	</div>
    <div class="row">
        <div class="col-sm-4 col-sm-offset-4">
            <div id="home-carousel" class="carousel slide" data-ride="carousel">
			  <!-- Indicators -->
			  <ol class="carousel-indicators">
			    <li data-target="#home-carousel" data-slide-to="0" class="active"></li>
			    <li data-target="#home-carousel" data-slide-to="1"></li>
			    <li data-target="#home-carousel" data-slide-to="2"></li>
			  </ol>
			
			  <!-- Wrapper for slides -->
			  <div class="carousel-inner" role="listbox">
			    <div class="item active">
			      <img src="resources/img/building.jpg" alt="Syntel Memphis">
			    </div>
			    <div class="item">
			      <img src="resources/img/syntel-wall.jpg" alt="Syntel Memphis">
			    </div>
			    <div class="item">
			      <img src="resources/img/team.jpg" alt="The Synovators">
			      <div class="carousel-caption">
			        The Synovators: Andrew Thomas, Gabriel Torres, John Yu, Christopher Peterson, and Jose Villa
			      </div>
			    </div>
			  </div>
			
			  <!-- Controls -->
			  <!-- <a class="left carousel-control" href="#home-carousel" role="button" data-slide="prev">
			    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
			    <span class="sr-only">Previous</span>
			  </a>
			  <a class="right carousel-control" href="#home-carousel" role="button" data-slide="next">
			    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
			    <span class="sr-only">Next</span>
			  </a>-->
			</div>
        </div>
    </div>
</div>
</body>
</html>