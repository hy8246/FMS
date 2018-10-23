<%@page import="com.syntelinc.fms.logic.queries.LocationQuery"%>
<%@page import="com.syntelinc.fms.logic.Room"%>
<%@page import="java.time.LocalDate"%>
<%@page import="com.syntelinc.fms.logic.Location"%>
<%@page import="com.syntelinc.fms.logic.queries.ReservationQuery"%>
<%@page import="com.syntelinc.fms.logic.Reservation"%>
<%@page import="com.syntelinc.fms.logic.reports.PastReservationReport"%>
<%@page import="java.util.*"%>
<%@page import="java.util.List"%><%@ page language="java" contentType="application/json; charset=utf-8" pageEncoding="utf-8"%>
<%
//var data = google.visualization.arrayToDataTable([
//['Task', 'Hours per Day'],
//['Pending',15],
//['Approved',75],
//['Denied',  10]
//]);

Map<String,Integer> reservation = new PastReservationReport((String) session.getAttribute("reportPeriod"),(Location) session.getAttribute("loc")).generateReport();
//Map<String,Double> reservationP = new UpcomingReservationReport((String) session.getAttribute("period"), LocalDate.parse((String)session.getAttribute("reportStart")),LocalDate.parse((String)session.getAttribute("reportEnd")),(Location) session.getAttribute("loc")).generatePercentageReport();

Set<String> set = reservation.keySet();
//Set<String> setP = reservationP.keySet();

Iterator<String> iterator = set.iterator();
//Iterator<String> iteratorp = setP.iterator();
//daily
out.print("[[{\"label\":\"Status\",\"type\":\"string\"},{\"label\":\"Count\",\"type\":\"number\"}]");


if (iterator.hasNext())
	out.print(",");
while(iterator.hasNext()) 
{
   //String r = iteratorp.next();
   String rp = iterator.next();
   out.print("[");
	out.print("\"" + rp + "\",");
	out.print(reservation.get(rp) + "");
	out.print("]");
	if (iterator.hasNext())
		out.print(",");  
}

out.print("]");


%>