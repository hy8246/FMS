<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="com.syntelinc.fms.logic.Location"%>
<%@page import="java.time.LocalDate"%>
<% DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/YY"); %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="includes/head.jsp" %>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script  src="resources/js/calendars.js"></script>
     <link rel="stylesheet" type="text/css" href="resources/css/fontawesome-all.min.css">
    <script>
    google.charts.load('current', {packages: ['corechart', 'bar']});
    google.charts.setOnLoadCallback(draw_chart);
  
    function prepareJSON()
	{
		 var jsonData = $.ajax({
	          url: '/FMS/UsageJSON',
	          dataType: "json",
	          async: false,
	          type: "GET",
	          contentType: "application/json",
	          }).responseText;
		  jsonData = jsonData.trim();
         var arrayData = JSON.parse(jsonData);
         var data = google.visualization.arrayToDataTable(arrayData);
		 return data;
	}
    
    function draw_chart()
    {
    	var period = '<%= session.getAttribute("reportPeriod") %>';
	    	switch(period)
	    	{
	    		case "DAILY":
	    			
	    			var options = {
	    	            title: 'Daily Usage report for <%= ((LocalDate)session.getAttribute("reportStart")).format(dtf) %>',
	    	            chartArea: {width: '50%'},
	    	            hAxis: {
	    	              title: 'Total hours of usage',
	    	              minValue: 0
	    	            },
	    	            vAxis: {
	    	              title: 'Rooms'
	    	            }
	    	          };
	
	    	          var chart = new google.visualization.BarChart(document.getElementById('usage'));
	
	    	          chart.draw(prepareJSON(), options);
	    			break;
	    		case "WEEKLY":
	    			var options = {
	                    title: 'Weekly Usage Report Week of <%= ((LocalDate)session.getAttribute("reportStart")).format(dtf) %> - <%= ((LocalDate)session.getAttribute("reportEnd")).format(dtf) %>',
	                    chartArea: {width: '50%'},
	                    hAxis: {
	                      title: 'Usage in %',
	                      minValue: 0
	                    },
	                    vAxis: {
	                      title: 'Rooms'
	                    }
	                 };
	                 
	                 var chart = new google.visualization.BarChart(document.getElementById('usage'));
	                 
	                          chart.draw(prepareJSON(), options);
	    			
	    			break;
	    		case "MONTHLY":
	    			
	    			var options = {
	    	              title: 'Monthly Usage Report for <%= ((LocalDate)session.getAttribute("reportStart")).getMonth().toString() %>',
	    	              chartArea: {width: '50%'},
	    	              hAxis: {
	    	                title: 'Usage in %',
	    	                minValue: 0
	    	              },
	    	              vAxis: {
	    	                title: 'Rooms'
	    	              }
	    	           };
	    	           
	    	           var chart = new google.visualization.BarChart(document.getElementById('usage'));
	    	           
	    	                    chart.draw(prepareJSON(), options);
	    			break;
	    		case "QUARTERLY":
	    			var options = {
	                    title: 'Quarterly Usage Report for Q<% DateTimeFormatter dtfq = DateTimeFormatter.ofPattern("Q YYYY");
	                    out.print(((LocalDate) session.getAttribute("reportStart")).format(dtfq)); %>',
	                    chartArea: {width: '50%'},
	                    hAxis: {
	                      title: 'Usage in %',
	                      minValue: 0
	                    },
	                    vAxis: {
	                      title: 'Rooms'
	                    }
	                 };
	                 
	                 var chart = new google.visualization.BarChart(document.getElementById('usage'));
	                 
	                          chart.draw(prepareJSON(), options);
	    			break;
	    		case "YEARLY":
	    			
	    			var options = {
	                    title: 'Yearly Usage Report for <%= ((LocalDate)session.getAttribute("reportStart")).getYear() %>',
	                    chartArea: {width: '50%'},
	                    hAxis: {
	                      title: 'Usage in %',
	                      minValue: 0
	                    },
	                    vAxis: {
	                      title: 'Rooms'
	                    }
	                 };
	                 
	                 var chart = new google.visualization.BarChart(document.getElementById('usage'));
	                 
	                          chart.draw(prepareJSON(), options);
	
	    			break;
	    		   default:
	    			break;
	
	    	}
    }


    </script>
<meta charset="UTF-8">
<style>
.topnav {
    background-color: #149c82;
    overflow: hidden;
}
.navbar-brand {
    float: left;
    color: #f2f2f2;
    text-align: center;
    
    text-decoration: none;
    font-size: 17px;
}
.Exit {
    float: right;
    color: #f2f2f2;
    background-color: #ef0719;
    text-align: center;
    padding: 14px 16px;
    text-decoration: none;
    font-size: 28px;
    font-family:"Lato","Helvetica Neue","Helvetical",Arial,Sans-serif;
}
/* Change the color of links on hover */
.topnav a:hover {
    background-color: #ddd;
    color: black;
}

/* Add a color to the active/current link */
.topnav a.active {
    background-color: #4CAF50;
    color: white;
}
</style>
</head>
<body>
<body style="margin:0;">
<div class="topnav" >
  <a class="navbar-brand" style="padding-top:3px;padding-bottom:3px;" href="home"><img src="resources/img/solr-logo.png" style="max-height:54px;" alt="Solr" /></a>
  <a class="Exit" href="javascript:window.open('','_self').close();">Exit</a>
</div>
<h1 align="center" style="font-family:sans-serif;">Usage Report</h1>
<h4 align="center" id="time">
<script>
document.getElementById("time").innerHTML = "Report generated on " +Date();
</script>
</h4>
<div id='loader' align='center'></div>
<br><br>
<script>
document.getElementById("loader").innerHTML = loadingIcon("Loading");
</script>
<script>
$(window).load(function() 
{
    $('#loader').hide();
 });
</script>
<div id="usage"></div>
<br><br>

</body>
</html>  
      