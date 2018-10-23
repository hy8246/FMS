package com.syntelinc.fms.logic.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.syntelinc.fms.logic.FeatureSet;
import com.syntelinc.fms.logic.queries.FeatureQuery;
import com.syntelinc.fms.logic.queries.RoomQuery;

public class FeatureSetMapper implements RowMapper<FeatureSet>{

	@Override
	public FeatureSet mapRow(ResultSet rs, int arg1) throws SQLException {
		FeatureSet fs = new FeatureSet();
		
		fs.setFeatureSetQuantity(rs.getInt("qty"));
		
		int fsFeatureID = rs.getInt("fid");
		fs.setFeatureSetFeature(new FeatureQuery().getFeatureByID(fsFeatureID));
		
		int fsRoomID = rs.getInt("room_id");
		fs.setFeatureSetRoom(new RoomQuery().getRoomByID(fsRoomID));
		
		return fs;
	}

}
