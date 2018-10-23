<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@page import="java.util.List"%>
<%@page import="com.syntelinc.fms.logic.queries.LocationQuery"%>
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
    <div class="col-sm-4 col-sm-offset-4">
        <form:form class="form-horizontal" action="changeLocation" modelAttribute="newLoc"  method="POST">
            <fieldset>
                <legend>Choose Location</legend>
                <div class="form-group">
                    <label for="country" class="col-lg-3 control-label">Country</label>
                    <div class="col-lg-9">
                        <select class="form-control" id="country">
                            <option>Europe</option>
                            <option>India</option>
                            <option selected="selected">United States</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="state" class="col-lg-3 control-label">State</label>
                    <div class="col-lg-9">
                        <select class="form-control" id="state">
                            <option value="AL">Alabama</option>
                            <option value="AK">Alaska</option>
                            <option value="AZ" selected="selected">Arizona</option>
                            <option value="AR">Arkansas</option>
                            <option value="CA">California</option>
                            <option value="CO">Colorado</option>
                            <option value="CT">Connecticut</option>
                            <option value="DE">Delaware</option>
                            <option value="DC">District Of Columbia</option>
                            <option value="FL">Florida</option>
                            <option value="GA">Georgia</option>
                            <option value="HI">Hawaii</option>
                            <option value="ID">Idaho</option>
                            <option value="IL">Illinois</option>
                            <option value="IN">Indiana</option>
                            <option value="IA">Iowa</option>
                            <option value="KS">Kansas</option>
                            <option value="KY">Kentucky</option>
                            <option value="LA">Louisiana</option>
                            <option value="ME">Maine</option>
                            <option value="MD">Maryland</option>
                            <option value="MA">Massachusetts</option>
                            <option value="MI">Michigan</option>
                            <option value="MN">Minnesota</option>
                            <option value="MS">Mississippi</option>
                            <option value="MO">Missouri</option>
                            <option value="MT">Montana</option>
                            <option value="NE">Nebraska</option>
                            <option value="NV">Nevada</option>
                            <option value="NH">New Hampshire</option>
                            <option value="NJ">New Jersey</option>
                            <option value="NM">New Mexico</option>
                            <option value="NY">New York</option>
                            <option value="NC">North Carolina</option>
                            <option value="ND">North Dakota</option>
                            <option value="OH">Ohio</option>
                            <option value="OK">Oklahoma</option>
                            <option value="OR">Oregon</option>
                            <option value="PA">Pennsylvania</option>
                            <option value="RI">Rhode Island</option>
                            <option value="SC">South Carolina</option>
                            <option value="SD">South Dakota</option>
                            <option value="TN">Tennessee</option>
                            <option value="TX">Texas</option>
                            <option value="UT">Utah</option>
                            <option value="VT">Vermont</option>
                            <option value="VA">Virginia</option>
                            <option value="WA">Washington</option>
                            <option value="WV">West Virginia</option>
                            <option value="WI">Wisconsin</option>
                            <option value="WY">Wyoming</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="city" class="col-lg-3 control-label">City</label>
                    <div class="col-lg-9">
                        <select class="form-control" id="city">
                            <option selected="selected">Phoenix</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="address" class="col-lg-3 control-label">Address</label>
                    <div class="col-lg-9">
                        <select class="form-control" id="address">
                            <option selected="selected">2902 W Agua Fria Freeway, Phoenix, AZ 85027</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-lg-9 col-lg-offset-3">
                        <a href="home" class="btn btn-default">Cancel</a>
                        <a href="home" class="btn btn-success">Save</a>
                    </div>
                </div>
            </fieldset>
        </form:form>
    </div>
</div>
</body>
</html>