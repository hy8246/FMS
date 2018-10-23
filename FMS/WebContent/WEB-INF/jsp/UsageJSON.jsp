<%@page import="com.syntelinc.fms.logic.queries.LocationQuery"%>
<%@page import="com.syntelinc.fms.logic.Room"%>
<%@page import="java.time.LocalDate"%>
<%@page import="com.syntelinc.fms.logic.Location"%>
<%@page import="com.syntelinc.fms.logic.queries.ReservationQuery"%>
<%@page import="com.syntelinc.fms.logic.Reservation"%>
<%@page import="com.syntelinc.fms.logic.reports.UsageReport"%>
<%@page import="java.util.*"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.List"%><%@ page language="java" contentType="application/json; charset=utf-8" pageEncoding="utf-8"%>
<%
//Map<String,Double> usage = new UsageReport("DAILY", LocalDate.parse((String)session.getAttribute("reportStart")),LocalDate.parse((String)session.getAttribute("reportEnd")),(Location) session.getAttribute("loc")).generateReport();
Map<String,Double> usage = new UsageReport((String) session.getAttribute("reportPeriod"), (LocalDate)session.getAttribute("reportStart"),(LocalDate)session.getAttribute("reportEnd"),(Location) session.getAttribute("loc")).generateReport();
Set<String> set = usage.keySet();
Iterator<String> iterator = set.iterator();
String label = "";
if (((String)session.getAttribute("reportPeriod")).equals("DAILY")) {
	out.print("[[{\"label\":\"Rooms\",\"type\":\"string\"},{\"label\":\"Total Hours of Usage in hrs\",\"type\":\"number\"},{\"type\":\"string\",\"role\": \"annotation\"}]");
	label="hours";
}//weekly///monthly///quarterly//yearly
else {
	out.print("[[{\"label\":\"Rooms\",\"type\":\"string\"},{\"label\":\"Total Hours of Usage in %\",\"type\":\"number\"},{\"type\":\"string\",\"role\": \"annotation\"}]");
	label = "%";
}

if (iterator.hasNext())
	out.print(",");
while(iterator.hasNext()) 
{
	String r = iterator.next();
	DecimalFormat df = new DecimalFormat(".##");
	double percent = Double.parseDouble(df.format(usage.get(r)));
   out.print("[");
	out.print("\"" + r + "\",");
	out.print(percent + ",");
	out.print("\"" + percent + " " + label + "\"");
	out.print("]");
	if (iterator.hasNext())
		out.print(",");  
}

out.print("]");
%>