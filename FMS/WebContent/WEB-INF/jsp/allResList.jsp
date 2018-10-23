<%@page import="com.syntelinc.fms.logic.Location"%>
<%@page import="com.syntelinc.fms.logic.queries.ReservationQuery"%><%@page import="com.syntelinc.fms.logic.Reservation"%><%@page import="java.util.List"%><%@ page language="java" contentType="application/json; charset=utf-8" pageEncoding="utf-8"%><%
out.print("[");
List<Reservation> reservations = new ReservationQuery().getReservationsByLocation(((Location) session.getAttribute("loc")).getLocationID());
for(int i = 0; i < reservations.size(); ++i) {
	Reservation res = reservations.get(i);
	String stat = res.getReservationStatus();
	if (stat.equals("D") || stat.equals("C") || stat.equals("N"))
		continue;
	out.print("{");
	out.print("\"id\": " + res.getReservationID() + ",");
	out.print("\"title\": \"" + res.getReservationPurpose() + "\",");
	out.print("\"start\": \"" + res.getReservationStart() + ":00\",");
	out.print("\"end\": \"" + res.getReservationEnd() + ":00\",");
	out.print("\"resourceID\": \"" + res.getReservationRoom().getRoomID() + "\"");
	out.print("}");
	if (i != reservations.size() - 1)
		out.print(",");
}
out.print("]");
%>