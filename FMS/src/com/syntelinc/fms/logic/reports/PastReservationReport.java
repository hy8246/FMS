package com.syntelinc.fms.logic.reports;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.syntelinc.fms.logic.Location;
import com.syntelinc.fms.logic.Reservation;
import com.syntelinc.fms.logic.queries.ReservationQuery;

public class PastReservationReport {
	private List<Reservation> reservations;
	private String period;
	private Location loc;
	
	public PastReservationReport() {
		
	}
	
	// Period --> DAILY, WEEKLY, MONTHLY, YEARLY
	public PastReservationReport(String period, Location loc) {
		this.loc = loc;
		this.period = period;
		this.setReservations();
	}
 	
	public void setReservations() {
		this.reservations = new ReservationQuery().getPastReservationsByLocation(loc.getLocationID());
	}
	
	public List<Reservation> getReservations() {
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

	public Map<String, Integer> generateReport() {
		return getStatusCounts();
	}
	
	public Map<String, Double> generatePercentageReport() {
		return getStatusPercentages(); 
	}
	
	private Map<String, Integer> getStatusCounts() {
		Map<String, Integer> totals = new HashMap<String, Integer>();
		for(int i = 0; i < reservations.size(); i++) {
			if(checkCountKey(totals, reservations.get(i).getFullReservationStatus())) {
				totals.put(reservations.get(i).getFullReservationStatus(), totals.get(reservations.get(i).getFullReservationStatus()) + 1);
			} else {
				totals.put(reservations.get(i).getFullReservationStatus(), 1);
			}
		}
		return totals;
	}
	
	private Map<String, Double> getStatusPercentages() {
		Map<String, Double> totals = new HashMap<String, Double>();
		double total = 0.0;
		for(int i = 0; i < reservations.size(); i++) {
			if(checkPercentKey(totals, reservations.get(i).getFullReservationStatus())) {
				totals.put(reservations.get(i).getFullReservationStatus(), totals.get(reservations.get(i).getFullReservationStatus()) + 1);
				total++;
			} else {
				totals.put(reservations.get(i).getFullReservationStatus(), 1.0);
				total++;
			}
		}
		
		if(total == 0.0) {
			return totals;
		} else {
			Set<String> keys = totals.keySet();
			for(String s: keys) {
				totals.put(s, totals.get(s)/total);
			}
			return totals;
		}
	}
	
	private boolean checkCountKey(Map<String, Integer> map, String key) {
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
	
	private boolean checkPercentKey(Map<String, Double> map, String key) {
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
