<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="includes/checkAdminAuth.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="includes/head.jsp" %>

    <title>Reports - Solr</title>
    <style>
		.google-visualization-table-td 
		{
		      text-align: center !important;
		}
	</style>
</head>
<body>
<%@ include file="includes/navbar.jsp" %>
<div class="container-fluid">
    <div class="col-sm-4 col-sm-offset-4" style="margin-top:25px;">
        <form class="form-horizontal" target="_blank" action="report-view">
            <fieldset>
                <legend>Choose Report</legend>
                <div class="form-group">
                    <label for="report" class="col-lg-3 control-label">Report</label>
                    <div class="col-lg-9">
                        <select class="form-control" name="type" id="report">
                            <option>Usage</option>
                            <option>Previous Reservation</option>
                            <option>Upcoming Reservation</option>
                        </select>
                    </div>
                </div>
                <div id="usageDateGroup">
	                <div class="form-group">
	                    <label for="type" class="col-lg-3 control-label">Type</label>
	                    <div class="col-lg-9">
	                        <select class="form-control" name="period" id="type">
	                            <option value="DAILY">Daily</option>
	                            <option value="WEEKLY">Weekly</option>
	                            <option value="MONTHLY">Monthly</option>
	                            <option value="QUARTERLY">Quarterly</option>
	                            <option value="YEARLY">Yearly</option>
	                        </select>
	                    </div>
	                </div>
	                <div class="form-group" id="optionalyear" style="display:none;">
	                    <label for="selectyear" class="col-lg-3 control-label">Year</label>
	                    <div class="col-lg-9">
	                       <div class="input-group date" id="selectyear">
	                            <input type="text" name="year" id="year" class="form-control" />
	                            <span class="input-group-addon">
	                            	<span class="far fa-calendar-alt"></span>
	                            </span>
	                        </div>
	                    </div>
	                </div>
	                <div class="form-group" id="optionalquarter" style="display:none;">
	                    <label for="quarter" class="col-lg-3 control-label">Quarter</label>
	                    <div class="col-lg-9">
	                        <select class="form-control" name="quarter" id="quarter">
	                            <option value="January">Q1</option>
	                            <option value="April">Q2</option>
	                            <option value="July">Q3</option>
	                            <option value="October">Q4</option>
	                        </select>
	                    </div>
	                </div>                
	                
	                <div class="form-group" id="optionalDate">
	                    <label for="date" class="col-lg-3 control-label">Date</label>
	                    <div class="col-lg-9">
	                        <div class="input-group date" id="start">
	                            <input type="text" name="date" id="date" class="form-control" />
	                            <span class="input-group-addon">
	                                <span class="far fa-calendar-alt"></span>
	                            </span>
	                        </div>
	                    </div>
	                </div>
                </div>  
                <div class="form-group">
                    <div class="col-lg-9 col-lg-offset-3">
                        <a href="home" class="btn btn-default">Cancel</a>
                        <button type="submit" id="mybutton" class="btn btn-success">Generate</button>
                    </div> 
            	</div>  	
            </fieldset>
        </form>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function(){
        $("#start").datetimepicker({
            format: "MMMM D, YYYY",
            calendarWeeks: true,
            daysOfWeekDisabled: [ 0, 6 ]
        });
        
        $("#selectyear").datetimepicker({
            format: "YYYY",
        });       
            
        $("#report").on("change", function(e) {
        	if ($(this).val() == "Usage")
        		$("#usageDateGroup").show();
        	else
        		$("#usageDateGroup").hide();
        })
        
        $("#type").on("change", function(e){
            switch($(this).val()) {
                case "DAILY":
                    $("#start").data("DateTimePicker").format("MMMM D, YYYY");
                    break;
                case "WEEKLY":
                	$("#start").data("DateTimePicker").format("MMMM D, YYYY");
                    break;
                case "MONTHLY":
                    $("#start").data("DateTimePicker").format("MMMM YYYY");
                    break;
                case "QUARTERLY":
                	$("#optionalDate").hide();
                	$("#optionalyear").show();
                	$("#optionalquarter").show();
                	$("#start").data("DateTimePicker").format("YYYY");
                    break;
                case "YEARLY":
                    $("#start").data("DateTimePicker").format("YYYY");
                    break;
            }
        });
        
        $("#type").val("DAILY");
    });
    
</script>
</body>

</html>