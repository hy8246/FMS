package com.syntelinc.fms.logic;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import com.syntelinc.fms.logic.queries.SequenceQuery;

public class Reservation {
	private int reservationID;
	@DateTimeFormat(pattern="yyyy-MM-ddThh:mm:ss")
	private LocalDateTime reservationRequestTime;
	@DateTimeFormat(pattern="yyyy-MM-ddThh:mm:ss")
	private LocalDateTime reservationStart;
	@DateTimeFormat(pattern="yyyy-MM-ddThh:mm:ss")
	private LocalDateTime reservationEnd;
	private String reservationPurpose;
	@Autowired
	private Room reservationRoom;
	@Autowired
	private Employee reservationEmployee;
	private String reservationComment;
	private int reservationRating;
	private String reservationStatus;
	private int reservationGroup;
	
	public Reservation() {}
	
	public Reservation(LocalDateTime reqTime, LocalDateTime start, LocalDateTime end,
						String purpose, Room room, Employee emp, String comment,
						int rating, String status) {
		this.reservationID = new SequenceQuery().getNextReservation();
		this.reservationRequestTime = reqTime;
		this.reservationStart = start;
		this.reservationEnd = end;
		this.reservationPurpose = purpose;
		this.reservationRoom = room;
		this.reservationEmployee = emp;
		this.reservationComment = comment;
		this.reservationRating = rating;
		this.reservationStatus = status;
	}
	
	public int getReservationID() {
		return reservationID;
	}
	public void setReservationID(int reservationID) {
		this.reservationID = reservationID;
	}
	public LocalDateTime getReservationRequestTime() {
		return reservationRequestTime;
	}
	public void setReservationRequestTime(LocalDateTime reservationRequestTime) {
		this.reservationRequestTime = reservationRequestTime;
	}
	public LocalDateTime getReservationStart() {
		return reservationStart;
	}
	public void setReservationStart(LocalDateTime reservationStart) {
		this.reservationStart = reservationStart;
	}
	public LocalDateTime getReservationEnd() {
		return reservationEnd;
	}
	public void setReservationEnd(LocalDateTime reservationEnd) {
		this.reservationEnd = reservationEnd;
	}
	public String getReservationPurpose() {
		return reservationPurpose;
	}
	public void setReservationPurpose(String reservationPurpose) {
		this.reservationPurpose = reservationPurpose;
	}
	public Room getReservationRoom() {
		return reservationRoom;
	}
	public void setReservationRoom(Room reservationRoom) {
		this.reservationRoom = reservationRoom;
	}
	public Employee getReservationEmployee() {
		return reservationEmployee;
	}
	public void setReservationEmployee(Employee reservationEmployee) {
		this.reservationEmployee = reservationEmployee;
	}
	public String getReservationComment() {
		return reservationComment;
	}
	public void setReservationComment(String reservationComment) {
		this.reservationComment = reservationComment;
	}
	public int getReservationRating() {
		return reservationRating;
	}
	public void setReservationRating(int reservationRating) {
		this.reservationRating = reservationRating;
	}
	public String getReservationStatus() {
		return reservationStatus;
	}
	public void setReservationStatus(String reservationStatus) {
		this.reservationStatus = reservationStatus;
	}
	
	public int getReservationGroup() {
		return reservationGroup;
	}

	public void setReservationGroup(int reservationGroup) {
		this.reservationGroup = reservationGroup;
	}

	public double getReservationLength() {
		double minutes = ChronoUnit.MINUTES.between(this.reservationStart, this.reservationEnd);
		double hours = minutes/60;
		return hours;
	}
	
	// Percentage of hours reserved
	// 
	public double getReservationDayPercentage(String period) {
		switch (period) {
		case "DAILY":
			return 100 * this.getReservationLength() / 10;
		case "WEEKLY":
			return 100 * this.getReservationLength() / 50;
		case "MONTHLY":
			return 100 * this.getReservationLength() /( 10 * this.getReservationStart().getMonth().minLength() * 5 / 7);
		case "QUARTERLY":
			return 100 * this.getReservationLength() /( 10 * 365 / 4 * 5 / 7);
		case "YEARLY":
			return 100 * this.getReservationLength() /(10 * 365 * 5 / 7);
		}
		return 0;
	}
	
	public boolean isOverlapping(Reservation r) {
		if (this.reservationStart.isAfter(r.reservationStart) && (this.reservationStart.isAfter(r.reservationEnd) || this.reservationStart.isEqual(r.reservationEnd)))
			return false;
		if (this.reservationEnd.isBefore(r.getReservationStart()) || this.reservationEnd.isEqual(r.getReservationStart()))
			return false;
		return true;
	}
	
	public String getFullReservationStatus() {
		switch(reservationStatus) {
			case "P":
				return "Pending";
			case "A":
				return "Accepted";
			case "D":
				return "Denied";
			case "C":
				return "Canceled";
			case "N":
				return "No Show";
			case "F":
				return "Finished";
			default:
				return "Other";
		}
	}

	@Override
	public String toString() {
		return "Reservation [reservationID=" + reservationID + ", reservationRequestTime=" + reservationRequestTime
				+ ", reservationStart=" + reservationStart + ", reservationEnd=" + reservationEnd
				+ ", reservationPurpose=" + reservationPurpose + ", reservationRoom=" + reservationRoom
				+ ", reservationEmployee=" + reservationEmployee + ", reservationComment=" + reservationComment
				+ ", reservationRating=" + reservationRating + ", reservationStatus=" + reservationStatus + "]";
	}
	
	public Reservation clone() {
		Reservation r = new Reservation();
		r.reservationComment = this.reservationComment;
		r.reservationEmployee = this.reservationEmployee;
		r.reservationEnd = this.reservationEnd;
		r.reservationGroup = this.reservationGroup;
		r.reservationID = new SequenceQuery().getNextReservation();
		r.reservationPurpose = this.reservationPurpose;
		r.reservationRating = this.reservationRating;
		r.reservationRequestTime = this.reservationRequestTime;
		r.reservationRoom = this.reservationRoom;
		r.reservationStart = this.reservationStart;
		r.reservationStatus = this.reservationStatus;
		
		return r;
	}
}
