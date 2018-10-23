package com.syntelinc.fms.logic.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.syntelinc.fms.logic.Reservation;
import com.syntelinc.fms.logic.queries.EmployeeQuery;
import com.syntelinc.fms.logic.queries.RoomQuery;

public class ReservationMapper implements RowMapper<Reservation> {

	@Override
	public Reservation mapRow(ResultSet rs, int arg1) throws SQLException {
		Reservation r = new Reservation();
		
		r.setReservationID(rs.getInt("res_id"));
		r.setReservationRequestTime(rs.getTimestamp("res_request_time").toLocalDateTime());
		r.setReservationStart(rs.getTimestamp("res_start").toLocalDateTime());
		r.setReservationEnd(rs.getTimestamp("res_end").toLocalDateTime());
		r.setReservationPurpose(rs.getString("res_purpose"));
		r.setReservationComment(rs.getString("res_comment"));
		r.setReservationRating(rs.getInt("res_rating"));
		r.setReservationStatus(rs.getString("res_status"));
		r.setReservationGroup(rs.getInt("res_group"));
		
		int resRoomID = rs.getInt("res_room");
		r.setReservationRoom(new RoomQuery().getRoomByID(resRoomID));
		
		int empID = rs.getInt("res_emp");
		r.setReservationEmployee(new EmployeeQuery().getEmployeeByID(empID));
		
		return r;
	}

}
