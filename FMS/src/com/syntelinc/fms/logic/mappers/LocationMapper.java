package com.syntelinc.fms.logic.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.syntelinc.fms.logic.Location;

public class LocationMapper implements RowMapper<Location>{

	@Override
	public Location mapRow(ResultSet rs, int arg1) throws SQLException {
		Location loc = new Location();
		
		loc.setLocationID(rs.getInt("loc_id"));
		loc.setLocationStreetAddress(rs.getString("loc_street"));
		loc.setLocationCity(rs.getString("loc_city"));
		loc.setLocationState(rs.getString("loc_state"));
		loc.setLocationCountry(rs.getString("loc_country"));
		
		return loc;
	}

}
