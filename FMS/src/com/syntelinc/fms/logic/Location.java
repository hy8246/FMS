package com.syntelinc.fms.logic;

import com.syntelinc.fms.logic.queries.SequenceQuery;

public class Location {
	private int locationID;
	private String locationStreetAddress;
	private String locationCity;
	private String locationState;
	private String locationCountry;
	
	public Location() {}
	public Location(String street, String city, String state, String country) {
		this.locationID = new SequenceQuery().getNextLocation();
		this.locationStreetAddress = street;
		this.locationCity = city;
		this.locationState = state;
		this.locationCountry = country;
	}
	public Location(int id, String street, String city, String state, String country) {
		this.locationID = id;
		this.locationStreetAddress = street;
		this.locationCity = city;
		this.locationState = state;
		this.locationCountry = country;
	}
	
	public int getLocationID() {
		return locationID;
	}
	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}
	public String getLocationStreetAddress() {
		return locationStreetAddress;
	}
	public void setLocationStreetAddress(String locationStreetAddress) {
		this.locationStreetAddress = locationStreetAddress;
	}
	public String getLocationCity() {
		return locationCity;
	}
	public void setLocationCity(String locationCity) {
		this.locationCity = locationCity;
	}
	public String getLocationState() {
		return locationState;
	}
	public void setLocationState(String locationState) {
		this.locationState = locationState;
	}
	public String getLocationCountry() {
		return locationCountry;
	}
	public void setLocationCountry(String locationCountry) {
		this.locationCountry = locationCountry;
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof Location))
			return false;
		Location ol = (Location) o;
		if (this.locationID != ol.locationID)
			return false;
		if (!this.locationStreetAddress.equals(ol.locationStreetAddress))
			return false;
		if (!this.locationCity.equals(ol.locationCity))
			return false;
		if (!this.locationState.equals(ol.locationState))
			return false;
		if (!this.locationCountry.equals(ol.locationCountry))
			return false;
		
		return true;
	}
	@Override
	public String toString() {
		return "Location [locationID=" + locationID + ", locationStreetAddress=" + locationStreetAddress
				+ ", locationCity=" + locationCity + ", locationState=" + locationState + ", locationCountry="
				+ locationCountry + "]";
	}
}
