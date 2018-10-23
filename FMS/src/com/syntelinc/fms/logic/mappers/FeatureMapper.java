package com.syntelinc.fms.logic.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.syntelinc.fms.logic.Feature;

public class FeatureMapper implements RowMapper<Feature>{

	@Override
	public Feature mapRow(ResultSet rs, int arg1) throws SQLException {
		Feature f = new Feature();
		
		f.setFeatureID(rs.getInt("fid"));
		f.setFeatureDescription(rs.getString("fdesc"));
		
		return f;
	}

}
