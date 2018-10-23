google.charts.load('current', {packages: ['corechart', 'bar']});
google.charts.setOnLoadCallback(draw_daily);
google.charts.setOnLoadCallback(draw_weekly);
google.charts.setOnLoadCallback(draw_monthly);
google.charts.setOnLoadCallback(draw_quarterly);
google.charts.setOnLoadCallback(draw_yearly);

function draw_daily() {

     
      var jsonData = $.ajax({
          url: "dailyUsage.jsp",
          dataType: "json",
          async: false
          }).responseText;
      
      var data = new google.visualization.DataTable(jsonData);
      var options = {
        title: 'Daily Usage report',
        chartArea: {width: '50%'},
        hAxis: {
          title: 'Total hours of usage',
          minValue: 0
        },
        vAxis: {
          title: 'Rooms'
        }
      };

      var chart = new google.visualization.BarChart(document.getElementById('daily_usage'));

      chart.draw(data, options);
}

function draw_weekly()
{

       var data = google.visualization.arrayToDataTable
([
       ['Rooms','Total Hours of Usage in %',{role: 'annotation'}],
       ['Training Room', 80,'80%'],
       ['Conference Room', 30,'30%'],
       ['Scrum Room 1', 50,'50%'],
       ['Scrum Room 2', 60,'60%'],
       ['Fun Room', 90,'90%']
]);

       var options = {
          title: 'Weekly Usage Report Week of 12/1/2018 - 12/7/2018',
          chartArea: {width: '50%'},
          hAxis: {
            title: 'Usage in %',
            minValue: 0
          },
          vAxis: {
            title: 'Rooms'
          }
       };
       
       var chart = new google.visualization.BarChart(document.getElementById('weekly_usage'));
       
                chart.draw(data, options);
}
function draw_monthly()
{

       var data = google.visualization.arrayToDataTable
([
       ['Rooms','Total Hours of Usage in %',{role: 'annotation'}],
       ['Training Room', 80,'80%'],
       ['Conference Room', 30,'30%'],
       ['Scrum Room 1', 50,'50%'],
       ['Scrum Room 2', 60,'60%'],
       ['Fun Room', 90,'90%']
]);

       var options = {
          title: 'Room Usage Report for October',
          chartArea: {width: '50%'},
          hAxis: {
            title: 'Usage in %',
            minValue: 0
          },
          vAxis: {
            title: 'Rooms'
          }
       };
       
       var chart = new google.visualization.BarChart(document.getElementById('monthly_usage'));
       
                chart.draw(data, options);
}
function draw_quarterly()
{
       var data = google.visualization.arrayToDataTable
       ([
              ['Rooms','Total Hours of Usage in %',{role: 'annotation'}],
              ['Training Room', 80,'80%'],
              ['Conference Room', 30,'30%'],
              ['Scrum Room 1', 50,'50%'],
              ['Scrum Room 2', 60,'60%'],
              ['Fun Room', 90,'90%']
       ]);

              var options = {
                 title: 'Room Usage Report for 1st quarter',
                 chartArea: {width: '50%'},
                 hAxis: {
                   title: 'Usage in %',
                   minValue: 0
                 },
                 vAxis: {
                   title: 'Rooms'
                 }
              };
              
              var chart = new google.visualization.BarChart(document.getElementById('quarterly_usage'));
              
                       chart.draw(data, options);

}
function draw_yearly()
{
       var data = google.visualization.arrayToDataTable
       ([
              ['Rooms','Total Hours of Usage in %',{role: 'annotation'}],
              ['Training Room', 80,'80%'],
              ['Conference Room', 30,'30%'],
              ['Scrum Room 1', 50,'50%'],
              ['Scrum Room 2', 60,'60%'],
              ['Fun Room', 90,'90%']
       ]);

              var options = {
                 title: 'Room Usage Report for year 2018',
                 chartArea: {width: '50%'},
                 hAxis: {
                   title: 'Usage in %',
                   minValue: 0
                 },
                 vAxis: {
                   title: 'Rooms'
                 }
              };
              
              var chart = new google.visualization.BarChart(document.getElementById('yearly_usage'));
              
                       chart.draw(data, options);

}
