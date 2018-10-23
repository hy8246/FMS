package com.syntelinc.fms.logic.queries;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.context.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.syntelinc.fms.logic.Reservation;
import com.syntelinc.fms.logic.mappers.ReservationMapper;
public class ReservationQuery {
	ApplicationContext context ;
    JdbcTemplate temp;
    public ReservationQuery()
    {
    	context=new ClassPathXmlApplicationContext("spring-config.xml");
    	temp = (JdbcTemplate)context.getBean("jtemp");
    }
	public Reservation getReservationByID(int id) {
		
		// SELECT * FROM fm_reservations WHERE res_id = id
		try {
			Reservation f  = temp.queryForObject("SELECT * FROM fm_reservations WHERE res_id =?",new ReservationMapper(),id);
			return f;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	public List<Reservation> getReservationsByEmployee(int empID) {
		// SELECT * FROM fm_reservations WHERE res_emp = empID
		List<Reservation> f  = temp.query("SELECT * FROM fm_reservations WHERE res_emp =?",new ReservationMapper(),empID);
		
		return f;
	}
	public List<Reservation> getValidReservationsByEmployee(int empID) {
		// SELECT * FROM fm_reservations WHERE res_emp = empID
		List<Reservation> f  = temp.query("SELECT * FROM fm_reservations WHERE res_emp =? AND res_status IN ('P','A','F')",new ReservationMapper(),empID);
		
		return f;
	}
	public List<Reservation> getCurrentLocationReservationsByStatus(String status, int locationID) {
		// SELECT * FROM fm_reservations WHERE res_status = status
		List<Reservation> f  = temp.query("SELECT * FROM fm_reservations fr, fm_rooms frm WHERE fr.res_id = frm.room_id AND frm.room_loc = ? AND fr.res_status = ?",new ReservationMapper(),status,locationID);
		return f;
	}
//	public List<Reservation> getAllReservationsByStatus(String status) {
//		// SELECT * FROM fm_reservations WHERE res_status = status
//		List<Reservation> f  = temp.query("SELECT * FROM fm_reservations WHERE res_status =?",new ReservationMapper(),status);
//		return f;
//	}
	public List<Reservation> getReservationsByRoom(int roomID) {
		// SELECT * FROM fm_reservations WHERE res_room = roomID
		List<Reservation> f  = temp.query("SELECT * FROM fm_reservations WHERE res_room =?",new ReservationMapper(),roomID);
		return f;
	}
	public List<Reservation> getReservationsByRoomAndDate(int roomID,LocalDate date) {
		// SELECT * FROM fm_reservations WHERE res_room = roomID
		Timestamp start = Timestamp.valueOf(LocalDateTime.of(date, LocalTime.of(0, 0,0)));
		Timestamp end = Timestamp.valueOf(LocalDateTime.of(date, LocalTime.of(23, 59,59)));
		List<Reservation> f  = temp.query("SELECT * FROM fm_reservations WHERE res_room =? AND res_start>=? and res_end<=?",new ReservationMapper(),roomID,start,end);
		return f;
	}
	public List<Reservation> getValidReservationsByRoomAndDate(int roomID,LocalDate date) {
		// SELECT * FROM fm_reservations WHERE res_room = roomID
		Timestamp start = Timestamp.valueOf(LocalDateTime.of(date, LocalTime.of(0, 0,0)));
		Timestamp end = Timestamp.valueOf(LocalDateTime.of(date, LocalTime.of(23, 59,59)));
		List<Reservation> f  = temp.query("SELECT * FROM fm_reservations WHERE res_room =? AND res_start>=? and res_end<=? AND res_status IN ('A','P','F')",new ReservationMapper(),roomID,start,end);
		return f;
	}
	public List<Reservation> getValidReservationsByRoom(int roomID) {
		// SELECT * FROM fm_reservations WHERE res_room = roomID
		List<Reservation> f  = temp.query("SELECT * FROM fm_reservations WHERE res_room =? AND res_status IN ('A','P','F')",new ReservationMapper(),roomID);
		return f;
	}
	public List<Reservation> getValidReservationsByRoomAndDateRange(int roomID,LocalDate startDate,LocalDate endDate) {
		// SELECT * FROM fm_reservations WHERE res_room = roomID
		Timestamp start = Timestamp.valueOf(LocalDateTime.of(startDate, LocalTime.of(0, 0,0)));
		Timestamp end = Timestamp.valueOf(LocalDateTime.of(endDate, LocalTime.of(23, 59,59)));
		List<Reservation> f  = temp.query("SELECT * FROM fm_reservations WHERE res_room =? AND res_start>=? and res_end<=? AND res_status IN ('A','P','F')",new ReservationMapper(),roomID,start,end);
		return f;
	}
	public List<Reservation> getReservationsByLocationAndDate(LocalDate date, int locationID) {
		// SELECT * FROM fm_reservation WHERE res_start = date AND 
		return getCurrentLocationReservationsByDateRange(date, date, locationID);
	}
	public List<Reservation> getValidReservationsByLocationDate(LocalDate date, int locationID) {
		// SELECT * FROM fm_reservation WHERE res_start = date AND 
		Timestamp start = Timestamp.valueOf(LocalDateTime.of(date, LocalTime.of(0, 0,0)));
		Timestamp end = Timestamp.valueOf(LocalDateTime.of(date, LocalTime.of(23, 59,59)));
		List<Reservation> f = temp.query("SELECT * FROM fm_reservations fr, fm_rooms frm WHERE fr.res_room=frm.room_id AND frm.room_loc = ? AND fr.res_start>=? AND fr.res_end<=? AND fr.res_status IN ('P','A','F')",
				new ReservationMapper(),locationID,start,end);
		return f;
	}
	public List<Reservation> getValidReservationsByLocation(int locationID) {
		// SELECT * FROM fm_reservation WHERE res_start = date AND 
		List<Reservation> f = temp.query("SELECT * FROM fm_reservations fr, fm_rooms frm WHERE fr.res_room=frm.room_id AND frm.room_loc = ? AND fr.res_status IN ('P','A','F')",
				new ReservationMapper(),locationID);
		return f;
	}
	public List<Reservation> getReservationsByLocation(int locationID) {
		// SELECT * FROM fm_reservation WHERE res_start = date AND 
		List<Reservation> f = temp.query("SELECT * FROM fm_reservations fr, fm_rooms frm WHERE fr.res_room=frm.room_id AND frm.room_loc = ?",
				new ReservationMapper(),locationID);
		return f;
	}
	public List<Reservation> getFutureReservationsByLocation(int locationID) {
		// SELECT * FROM fm_reservation WHERE res_start = date AND 
		List<Reservation> f = temp.query("SELECT * FROM fm_reservations fr, fm_rooms frm WHERE fr.res_room=frm.room_id AND frm.room_loc = ? AND fr.res_start > ?",
				new ReservationMapper(),locationID,Timestamp.valueOf(LocalDateTime.now()));
		return f;
	}
	public List<Reservation> getValidFutureReservationsByLocation(int locationID) {
		// SELECT * FROM fm_reservation WHERE res_start = date AND 
		List<Reservation> f = temp.query("SELECT * FROM fm_reservations fr, fm_rooms frm WHERE fr.res_room=frm.room_id AND frm.room_loc = ? AND fr.res_start > ? AND fr.res_status IN ('P','A','F')",
				new ReservationMapper(),locationID,Timestamp.valueOf(LocalDateTime.now()));
		return f;
	}
	public List<Reservation> getValidFutureReservationsByRoom(int roomID) {
		// SELECT * FROM fm_reservation WHERE res_start = date AND 
		List<Reservation> f = temp.query("SELECT * FROM fm_reservations WHERE res_room=? AND res_start > ? AND res_status IN ('P','A','F')",
				new ReservationMapper(),roomID,Timestamp.valueOf(LocalDateTime.now()));
		return f;
	}
	public List<Reservation> getPastReservationsByLocation(int locationID) {
		// SELECT * FROM fm_reservation WHERE res_start = date AND 
		List<Reservation> f = temp.query("SELECT * FROM fm_reservations fr, fm_rooms frm WHERE fr.res_room=frm.room_id AND frm.room_loc = ? AND fr.res_start < ?",
				new ReservationMapper(),locationID,Timestamp.valueOf(LocalDateTime.now()));
		return f;
	}
//	public List<Reservation> getAllReservationsByDate(LocalDate date) {
//		// SELECT * FROM fm_reservation WHERE res_start
//		Timestamp start = Timestamp.valueOf(LocalDateTime.of(date, LocalTime.of(0, 0,0)));
//		Timestamp end = Timestamp.valueOf(LocalDateTime.of(date, LocalTime.of(23, 59,59)));
//		List<Reservation> f = temp.query("SELECT * FROM fm_reservations fr WHERE fr.res_start>=? AND fr.res_end<=?",
//				new ReservationMapper(),start,end);
//		
//		return f;
//	}
	public List<Reservation> getCurrentLocationReservationsByDateRange(LocalDate startDate, LocalDate endDate, int locationID) {
		Timestamp start = Timestamp.valueOf(LocalDateTime.of(startDate, LocalTime.of(0, 0,0)));
		Timestamp end = Timestamp.valueOf(LocalDateTime.of(endDate, LocalTime.of(23, 59,59)));
		String statement = "SELECT * FROM fm_reservations fr, fm_rooms frm WHERE fr.res_room=frm.room_id AND frm.room_loc = ? AND fr.res_start >= ? AND fr.res_end <= ?";
		List<Reservation> f = temp.query(statement,
				new ReservationMapper(),locationID,start,end);
		
		return f;
	}
//	public List<Reservation> getAllReservationsByDateRange(LocalDate startDate, LocalDate endDate) {
//		Timestamp start = Timestamp.valueOf(LocalDateTime.of(startDate, LocalTime.of(0, 0,0)));
//		Timestamp end = Timestamp.valueOf(LocalDateTime.of(endDate, LocalTime.of(23, 59,59)));
//		List<Reservation> f = temp.query("SELECT * FROM fm_reservations fr WHERE fr.res_start>=? AND fr.res_end<=?",
//				new ReservationMapper(),start,end);
//		
//		return f;
//	}
	public List<Reservation> getReservationsByGroup(int id) {
		List<Reservation> f = temp.query("SELECT * FROM fm_reservations WHERE reservation_group_ids = ?", new ReservationMapper(),id);
		return f;
	}
	
	public int updateReservation(Reservation r, int reservationRoom) 
	{
		int reservationID=r.getReservationID();
		Timestamp reservationRequestTime=Timestamp.valueOf(r.getReservationRequestTime());
		Timestamp reservationStart=Timestamp.valueOf(r.getReservationStart());
		Timestamp reservationEnd=Timestamp.valueOf(r.getReservationEnd());
		String reservationPurpose=r.getReservationPurpose();
		
		int f  = temp.update("UPDATE fm_reservations SET res_request_time = ?, res_start = ?,res_end = ?,res_purpose = ?,res_room = ? WHERE res_id = ? AND res_emp = ?",
                reservationRequestTime,reservationStart,reservationEnd,reservationPurpose,
                reservationRoom, reservationID, r.getReservationEmployee().getEmployeeID());
		return f;
	}
	public int insertReservation(Reservation r, int reservationRoom) 
	{
		int reservationID=r.getReservationID();
		Timestamp reservationRequestTime=Timestamp.valueOf(r.getReservationRequestTime());
		Timestamp reservationStart=Timestamp.valueOf(r.getReservationStart());
		Timestamp reservationEnd=Timestamp.valueOf(r.getReservationEnd());
		String reservationPurpose=r.getReservationPurpose();
		//int reservationRoom=r.getReservationRoom().getRoomID();
		int reservationEmployeeID=r.getReservationEmployee().getEmployeeID();
//		String reservationComment=r.getReservationComment();
//		int reservationRating=r.getReservationRating();
//		String reservationStatus=r.getReservationStatus();
		
		int f  = temp.update("Insert into fm_reservations (res_id, res_request_time, res_start, res_end, res_purpose, res_status, res_room, res_emp, res_group) values(?,?,?,?,?,?,?,?,?)",
                reservationID,reservationRequestTime,reservationStart,reservationEnd,reservationPurpose,"A",
                reservationRoom,reservationEmployeeID, r.getReservationGroup());
		
		return f;
	}
	
	public int deleteReservation(int r, int empID) 
    {       
        return temp.update("UPDATE fm_reservations SET res_status = 'C' WHERE res_id = ? AND res_emp = ?", r, empID);
    }
}
