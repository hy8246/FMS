<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<link rel="stylesheet" type="text/css" href="resources/css/fontawesome-all.min.css">
	<script type="text/javascript" src="resources/js/jquery.js"></script>
    <script  src="resources/js/calendars.js"></script>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    
    <script>
    google.charts.load("current", {packages:["corechart"]});
    google.charts.load('current', {'packages':['table']});
    google.charts.load('current', {packages: ['corechart', 'bar']});
    google.charts.setOnLoadCallback(drawChart);
    google.charts.setOnLoadCallback(drawTable);
    google.charts.setOnLoadCallback(drawbar);
    function prepareJSONChart()
    {
    		 var jsonData = $.ajax({
    	          url: 'PastJSONChart',
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
    function prepareJSONBar()
    {
    		 var jsonData = $.ajax({
    	          url: 'PastJSONBar',
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
    function prepareJSONTable()
    {
    		 var jsonData = $.ajax({
    	          url: 'PastJSONTable',
    	          dataType: "json",
    	          async: false,
    	          type: "GET",
    	          contentType: "application/json",
    	          }).responseText;
    		  jsonData = jsonData.trim();
    		 
             var data = new google.visualization.DataTable(jsonData);
             
    		 return data;
    }
   /////////////////////////////////////////////////////////////////////////////////
   /////Percentage of Reservation By Status
    function drawChart() 
    {

       var options = 
       {
    		title: 'Reservation Distribution Percentage',
    		titleTextStyle: {fontSize: 18},
       	  chartArea: {width:"350",height:"150"},
       	  legend: {textStyle: {"fontSize":"18"}},
         is3D: true,
         slices: {1: {color: 'orange'}, 0: {color: 'chartreuse'},2: {color:'red'},}
       
       };

       var chart = new google.visualization.PieChart(document.getElementById('percentDistribution'));
       chart.draw(prepareJSONChart(), options);
   }
    
   /////////////////////////////////////////////////////////////////////////////////
   ///Number of Reservations By Status
   function drawbar() 
   {

          var view = new google.visualization.DataView(prepareJSONBar());
			//var view=prepareJSONBar();
       		 view.setColumns([0, 1,
                          { calc: "stringify",
                            sourceColumn: 1,
                            type: "string",
                            role: "annotation" }
                      ]);

         var options = {
           title: "Reservation Distribution",
           width: '100%',
           height: '100%',
           bar: {groupWidth: "35%"},
           legend: { position: "none" },
           hAxis: {
               title: 'Number of Reservations',
               minValue: 0
             },
             vAxis: {
               title: 'Status'
             }
         };
         var chart = new google.visualization.BarChart(document.getElementById("numberDistribution"));
         chart.draw(view, options);
       
   }
   /////////////////////////////////////////////////////////////////////////////////
   function drawTable() 
   {
   	
   	 var table = new google.visualization.Table(document.getElementById('tablesss'));
   	 table.draw(prepareJSONTable(), {showRowNumber: true, width: '100%', height: '100%'});
   }

    </script>
    
<style>
.google-visualization-table-td 
{
      text-align: center !important;
}
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
<body style="margin:0;">
<div class="topnav" >
  <a class="navbar-brand" style="padding-top:3px;padding-bottom:3px;" href="home"><img src="resources/img/solr-logo.png" style="max-height:54px;" alt="Solr" /></a>
  <a class="Exit" href="javascript:window.open('','_self').close();">Exit</a>
</div>
<h1 align="center" style="font-family:sans-serif;">Past Reservation Report</h1>

<h4 align="center" id="time">
<script>
document.getElementById("time").innerHTML = "Report generated on " +Date();
</script>
</h4>
<div id='loader' align='center'></div>
<br><br>

<script>
document.getElementById("loader").innerHTML = loadingIcon("Loading");
$(window).load(function() 
{
    $('#loader').hide();
 });
</script>
  <table align="center" width="60%" border="1"  style="border-spacing: 15px;background-color:LightGrey;">	
  	
  	<tr>
  		<td id="percentDistribution" style="width: 49%; height: 100%;"></td>
  		<td id="numberDistribution" style="width: 49%; height: 100%;"></td>
  	</tr>
  	<tr>
  		<td id="tablesss" colspan="2" ></td>
  	</tr>
  </table>

</body>
</html>