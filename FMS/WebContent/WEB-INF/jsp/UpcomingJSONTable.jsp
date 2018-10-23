<%@page import="javax.json.JsonValue"%>
<%@page import="com.syntelinc.fms.logic.queries.LocationQuery"%>
<%@page import="com.syntelinc.fms.logic.Room"%>
<%@page import="java.time.LocalDate"%>
<%@page import="com.syntelinc.fms.logic.Location"%>
<%@page import="com.syntelinc.fms.logic.queries.ReservationQuery"%>
<%@page import="com.syntelinc.fms.logic.Reservation"%>
<%@page import="com.syntelinc.fms.logic.reports.UpcomingReservationReport"%>
<%@page import="javax.json.Json"%>
<%@page import="javax.json.JsonBuilderFactory"%>
<%@page import="javax.json.JsonObject"%>
<%@page import="javax.json.JsonObjectBuilder"%>
<%@page import="javax.json.JsonArrayBuilder"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.util.*"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="application/json; charset=utf-8" pageEncoding="utf-8"%>
<%

List<Reservation> reservation= new UpcomingReservationReport((String) session.getAttribute("reportPeriod"),(Location) session.getAttribute("loc")).getReservations();
Iterator<Reservation> iterator = reservation.iterator();

JsonBuilderFactory factory = Json.createBuilderFactory(null);
JsonObjectBuilder builder = factory.createObjectBuilder().add("cols", factory.createArrayBuilder()
		    .add(factory.createObjectBuilder()
		        .add("id", "")
		        .add("label", "Status")
		        .add("pattern", "")
		        .add("type", "string"))
		    .add(factory.createObjectBuilder()
			        .add("id", "")
			        .add("label", "Request Time")
			        .add("pattern", "")
			        .add("type", "string"))
		    .add(factory.createObjectBuilder()
			        .add("id", "")
			        .add("label", "Start Time")
			        .add("pattern", "")
			        .add("type", "string"))
		    .add(factory.createObjectBuilder()
			        .add("id", "")
			        .add("label", "End Time")
			        .add("pattern", "")
			        .add("type", "string"))
		    .add(factory.createObjectBuilder()
			        .add("id", "")
			        .add("label", "Room")
			        .add("pattern", "")
			        .add("type", "string"))
		    .add(factory.createObjectBuilder()
			        .add("id", "")
			        .add("label", "Purpose")
			        .add("pattern", "")
			        .add("type", "string")));
		    

JsonArrayBuilder bigArray = factory.createArrayBuilder();
while(iterator.hasNext()) {
	DateTimeFormatter requestFormat = DateTimeFormatter.ofPattern("MMMM dd, yyyy hh:mm:ss a");
	DateTimeFormatter reservationFormat = DateTimeFormatter.ofPattern("MMMM dd, yyyy hh:mm a");
	Reservation r = iterator.next();
	JsonObjectBuilder obj = factory.createObjectBuilder();
   obj.add("c",factory.createArrayBuilder()
		   .add(factory.createObjectBuilder()
				   .add("v",r.getFullReservationStatus())
				   .add("f", JsonValue.NULL))
		   .add(factory.createObjectBuilder()
				   .add("v", requestFormat.format(r.getReservationRequestTime()))
				   .add("f", JsonValue.NULL))
		   .add(factory.createObjectBuilder()
				   .add("v", reservationFormat.format(r.getReservationStart()))
				   .add("f", JsonValue.NULL))
		   .add(factory.createObjectBuilder()
				   .add("v", reservationFormat.format(r.getReservationEnd()))
				   .add("f", JsonValue.NULL))
		   .add(factory.createObjectBuilder()
				   .add("v", r.getReservationRoom().getRoomName())
				   .add("f", JsonValue.NULL))
		   .add(factory.createObjectBuilder()
				   .add("v", r.getReservationPurpose())
				   .add("f", JsonValue.NULL)));
   bigArray.add(obj);
}

builder.add("rows", bigArray);
JsonObject obj = builder.build();
out.print(obj.toString());

%>