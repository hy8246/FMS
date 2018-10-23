package com.syntelinc.fms.logic.queries;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
public class SequenceQuery {
	ApplicationContext context;
	JdbcTemplate jtemp;
	
	public SequenceQuery() {
		context = new ClassPathXmlApplicationContext("spring-config.xml");
		jtemp = (JdbcTemplate) context.getBean("jtemp");
	}
	
	public int getNextFeature() {
		String featureSeq = "SELECT feature_ids.nextval FROM dual";
		int featureId = jtemp.queryForObject(featureSeq, Integer.class);
		return featureId;
	}
	
	public int getNextLocation() {
		String locationSeq = "SELECCT location_ids.nextval FROM dual";
		int locationId = jtemp.queryForObject(locationSeq, Integer.class);
		return locationId;
	}
	
	public int getNextRoom() {
		String roomSeq = "SELECT room_ids.nextval FROM dual";
		int roomId = jtemp.queryForObject(roomSeq, Integer.class);
		return roomId;
	}
	
	public int getNextReservation() {
		String reservationSeq = "SELECT reservation_ids.nextval FROM dual";
		int reservationId = jtemp.queryForObject(reservationSeq, Integer.class);
		return reservationId;
	}
	
	public int getNextReservationGroup() {
		String resGroupSeq = "SELECT reservation_group_ids.nextval FROM dual";
		int resGroup = jtemp.queryForObject(resGroupSeq, Integer.class);
		return resGroup;
	}
}
