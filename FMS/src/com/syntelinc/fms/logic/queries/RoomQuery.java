package com.syntelinc.fms.logic.queries;

import java.util.List;

import org.springframework.context.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.syntelinc.fms.logic.Room;
import com.syntelinc.fms.logic.mappers.RoomMapper;
public class RoomQuery {
	ApplicationContext context ;
    JdbcTemplate temp;
    public RoomQuery()
    {
    	context=new ClassPathXmlApplicationContext("spring-config.xml");
    	temp = (JdbcTemplate)context.getBean("jtemp");
    }
	public Room getRoomByID(int id) {
		//SELECT * FROM FM_ROOMS WHERE ROOM_ID=id;
		try {
			Room f  = temp.queryForObject("SELECT * FROM FM_ROOMS WHERE ROOM_ID=?",new RoomMapper(),id);
			return f;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	
	}
	public List<Room> getRoomsByLocation(int locID) {
		//SELECT * FROM FM_ROOMS WHERE ROOM_LOC=locID;
		List<Room> f  = temp.query("SELECT * FROM FM_ROOMS WHERE ROOM_LOC=?",new RoomMapper(),locID);
		return f;
	}
	public List<Room> getRoomsByLocationAndCategory(int locID, String cat) {
		//SELECT * FROM FM_ROOMS WHERE ROOM_LOC=locID;
		List<Room> f  = temp.query("SELECT * FROM FM_ROOMS WHERE ROOM_LOC=? AND room_category = ?",new RoomMapper(),locID,cat);
		return f;
	}
	
	public int updateRoom(int roomID, String roomName, String roomDescription, String roomCategory) {
		//Update FM_ROOMS SET ROOM_iD=?,ROOM_NAME=?,ROOM_DESC=?,ROOM_PHOTO_URL=?,ROOM_LOC=? WHERE ROOM_ID=?;
		int f  = temp.update("Update FM_ROOMS SET ROOM_NAME=?,ROOM_DESC=?,ROOM_CATEGORY=? WHERE ROOM_ID=?",
				roomName, roomDescription, roomCategory, roomID);
		return f;
	}
	
	public int insertRoom(String roomName, String roomDesc, String cat, int location)  {
		//Insert into fm_rooms values(roomID,roomname,roomdesc,photoURL,roomlocationID);
		//Select * from fm_room where ROOM_NAME=?,ROOM_LOC;
		int f  = temp.update("Insert into fm_rooms (room_id, room_name, room_desc, room_category, room_loc) values(?,?,?,?,?)",
				new SequenceQuery().getNextRoom(),roomName,roomDesc,cat, location);
		return f;
	}
	
	public int deleteRoom(int id)  {
		temp.update("delete from fm_feature_sets where room_id = ?", id);
		temp.update("delete from fm_reservations where res_room = ?", id);
		return temp.update("delete from fm_rooms where room_id = ?", id);
	}
}
