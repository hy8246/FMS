package com.syntelinc.fms.logic.queries;

import java.util.List;

import org.springframework.context.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import com.syntelinc.fms.logic.Location;
import com.syntelinc.fms.logic.mappers.LocationMapper;
public class LocationQuery 
{
	ApplicationContext context ;
    JdbcTemplate temp;
    public LocationQuery()
    {
    	context=new ClassPathXmlApplicationContext("spring-config.xml");
    	temp = (JdbcTemplate)context.getBean("jtemp");
    }
	public Location getLocationByID(int id) {
		//Select * from fm_locations WHERE LOC_ID=id;
		try {
			Location f  = temp.queryForObject("Select * from fm_locations WHERE LOC_ID=?",new LocationMapper(),id);
			return f;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	public Location getLocationByAddress(String address) {
		//Select * from fm_locations WHERE LOC_STREET=address;
		try {
			Location f  = temp.queryForObject("Select * from fm_locations WHERE LOC_STREET=?",new LocationMapper(),address);
			return f;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	public List<String> getAddress(String city) {
		//Select * from fm_locations WHERE LOC_CITY=city;
		
		List<String> f  = temp.query("Select DISCTINCT(loc_street) from fm_locations WHERE LOC_CITY=?",new SingleColumnRowMapper<String>(),city);
		return f;
	}
	public List<String> getCities(String state, String country) {
		//Select loc_city from fm_locations where loc_country=country_val and fm_sate = state_val;
		List<String> f = temp.query("Select DISCTINCT(loc_city) from fm_locations where loc_STATE=? and loc_country = ?",new SingleColumnRowMapper<String>(),state, country);
		return f;
	}
	public List<String> getCities(String country) {
		List<String> f = temp.query("Select DISCTINCT(loc_city) from fm_locations where loc_country = ?",new SingleColumnRowMapper<String>(), country);
		return f;
	}
	public List<String> getStates(String country) {
		//Select loc_state from fm_locations where loc_country=country_val;
		List<String> f  = temp.query("Select DISCTINCT(loc_state) from fm_locations where loc_country=?",new SingleColumnRowMapper<String>(),country);
		return f;
	}
	public List<String> getCountries() {
		//Select LOC_COUNTRY FROM FM_LOCATIONS;
		List<String> f  = temp.query("Select DISCTINCT(LOC_COUNTRY) FROM FM_LOCATIONS",new SingleColumnRowMapper<String>());
		
		return f;
	}
	public int insertLocation(Location loc) {
		int id=loc.getLocationID();
		String street=loc.getLocationStreetAddress();
		String city=loc.getLocationCity();
		String state=loc.getLocationState();
		String country=loc.getLocationCountry();
		//INSERT INTO FM_LOCATIONS VALUES();
		int f = temp.update("INSERT INTO FM_LOCATIONS VALUES(?,?,?,?,?)",id,street,city,state,country);
		return f;
	}
}
