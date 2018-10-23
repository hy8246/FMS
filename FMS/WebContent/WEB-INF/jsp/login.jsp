<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="includes/head.jsp" %>
<title>Login - Solr</title>
</head>
<body>
<%@ include file="includes/navbar.jsp" %>
<div class="container-fluid">
	<div class="col-sm-4 col-sm-offset-4" style="margin-top:25px;">
		<form class="form-horizontal" method="post" action="tryLogin">
	  		<fieldset>
		    	<legend>Login</legend>
   			 	<div class="form-group">
			    	<label for="useremail" class="col-sm-4 control-label">Enter Email</label>
			    	<div class="col-sm-8">
			        	<input type="email" name="email" class="form-control" tabindex="1"></input>
		    		</div>
		 		</div>
	   	 		<div class="form-group">
		    		<label for="password" class="col-sm-4 control-label">Enter Password</label>
	      			<div class="col-sm-8">
        				<input type="password" name="password" class="form-control" tabindex="2"></input>
	      			</div>
		 		</div>  
	           	<div class="form-group">
	     	    	<div class="col-xs-12">
	             		<button type="submit" class="btn btn-success pull-right">Login</button>
               		</div>
               	</div>
           	</fieldset>
		</form>
	</div>
</div>
</body>
</html>