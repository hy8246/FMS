<%@page import="com.syntelinc.fms.logic.Location"%>
<%@page import="com.syntelinc.fms.logic.Room"%><%@page import="com.syntelinc.fms.logic.queries.RoomQuery"%><%@page import="com.syntelinc.fms.logic.queries.ReservationQuery"%><%@page import="com.syntelinc.fms.logic.Reservation"%><%@page import="java.util.List"%><%@ page language="java" contentType="application/json; charset=utf-8" pageEncoding="utf-8"%><% out.print("[");
List<Room> rooms = new RoomQuery().getRoomsByLocation(((Location) session.getAttribute("loc")).getLocationID());
for(int i = 0; i < rooms.size(); ++i) {
	Room r = rooms.get(i);
	out.print("{");
	out.print("\"id\": " + r.getRoomID() + ",");
	out.print("\"title\": \"" + r.getRoomName() + "\",");
	out.print("\"category\": \"" + r.getRoomCategory() + "\"");
	out.print("}");
	if (i != rooms.size() - 1)
		out.print(",");
}
out.print("]");
%>