package com.syntelinc.fms.logic.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.syntelinc.fms.logic.Room;
import com.syntelinc.fms.logic.queries.LocationQuery;

public class RoomMapper implements RowMapper<Room>{

	@Override
	public Room mapRow(ResultSet rs, int arg1) throws SQLException {
		Room r = new Room();
		
		r.setRoomID(rs.getInt("room_id"));
		r.setRoomName(rs.getString("room_name"));
		r.setRoomDescription(rs.getString("room_desc"));
		r.setRoomPhotoURL(rs.getString("room_photo_url"));
		r.setRoomCategory(rs.getString("room_category"));
		
		int roomLocID = rs.getInt("room_loc");
		r.setRoomLocation(new LocationQuery().getLocationByID(roomLocID));
		
		return r;
	}
}
