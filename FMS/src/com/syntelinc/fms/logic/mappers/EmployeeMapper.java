package com.syntelinc.fms.logic.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.syntelinc.fms.logic.Employee;
import com.syntelinc.fms.logic.queries.LocationQuery;

public class EmployeeMapper implements RowMapper<Employee>{

	@Override
	public Employee mapRow(ResultSet rs, int arg1) throws SQLException {
		Employee e = new Employee();
		
		e.setEmployeeID(rs.getInt("eid"));
		e.setEmployeeName(rs.getString("ename"));
		e.setEmployeeEmail(rs.getString("email"));
		e.setAuthType(rs.getString("eauthtype"));
		
		int homeLocID = rs.getInt("e_home_loc");
		e.setEmployeeHomeLocation(new LocationQuery().getLocationByID(homeLocID));
		
		return e;
	}
	
}
