package com.syntelinc.fms.logic.reports;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.syntelinc.fms.logic.*;
import com.syntelinc.fms.logic.queries.ReservationQuery;
import com.syntelinc.fms.logic.queries.RoomQuery;

public class UsageReport {
	private List<Reservation> reservations;
	private String period;
	private Location loc;
	private LocalDate start;
	private LocalDate end;
	private List<String> rooms;
	
	public UsageReport() {
		
	}
	
	// Period --> DAILY, WEEKLY, MONTHLY, YEARLY
	public UsageReport(String period, LocalDate start, LocalDate end, Location loc) {
		this.start = start;
		this.end = end;
		this.loc = loc;
		this.period = period;
		this.setReservations();
	}
 	
	public void setReservations() {
		this.reservations = new ReservationQuery().getCurrentLocationReservationsByDateRange(start, end, loc.getLocationID());
	}
	
	public void getAllRoomNames() {
		List<Room> roomObjs = new RoomQuery().getRoomsByLocation(loc.getLocationID());
		rooms = new ArrayList<String>();
		for(Room r: roomObjs) {
			rooms.add(r.getRoomName());
		}
	}
	
	public List<Reservation> getReserations() {
		return this.reservations;
	}

	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}

	public String getPeriod() {
		return period;
	}
	
	public void setPeriod(String type) {
		period = type;
	}

	public Map<String, Double> generateReport() {
		getAllRoomNames();
		if(this.period.equals("DAILY")) {
			return getUsageHours();
		} else {
			return getUsagePercentage();
		}
	}
	
	
	private Map<String, Double> getUsageHours() {
		Map<String, Double> totals = new HashMap<String, Double>();
		for(int i = 0; i < rooms.size(); i++) {
			totals.put(rooms.get(i), 0.0);
		}
		for(int i = 0; i < reservations.size(); i++) {
			totals.put(reservations.get(i).getReservationRoom().getRoomName(), totals.get(reservations.get(i).getReservationRoom().getRoomName()) + reservations.get(i).getReservationLength());
		}
		return totals;
	}

	private Map<String, Double> getUsagePercentage() {
		Map<String, Double> totals = new HashMap<String, Double>();
		for(int i = 0; i < reservations.size(); i++) {
			if(checkKey(totals, reservations.get(i).getReservationRoom().getRoomName())) {
				totals.put(reservations.get(i).getReservationRoom().getRoomName(), totals.get(reservations.get(i).getReservationRoom().getRoomName()) + reservations.get(i).getReservationDayPercentage(period));
			} else {
				totals.put(reservations.get(i).getReservationRoom().getRoomName(), (double) reservations.get(i).getReservationDayPercentage(period));
			}
		}
		return totals;
	}
	
	private boolean checkKey(Map<String, Double> map, String key) {
		Set<String> keys = map.keySet();
		Iterator<String> iter = keys.iterator();
		while(iter.hasNext()) {
			String val = iter.next();
			if(val.equals(key)) {
				return true;
			}
		}
		
		return false;
	}
}
