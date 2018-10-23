package com.syntelinc.fms.logic;

import com.syntelinc.fms.logic.queries.SequenceQuery;

public class Room {
	private int roomID;
	private String roomName;
	private String roomDescription;
	private String roomPhotoURL;
	private Location roomLocation;
	private String roomCategory;
	
	public String getRoomCategory() {
		return roomCategory;
	}

	public void setRoomCategory(String roomCategory) {
		this.roomCategory = roomCategory;
	}

	public Room() {}
	
	public Room(String name, String desc, String url, Location loc) {
		this.roomID = new SequenceQuery().getNextRoom();
		this.roomName = name;
		this.roomDescription = desc;
		this.roomPhotoURL = url;
		this.roomLocation = loc;
	}
	public Room(int id, String name, String desc, String url, Location loc) {
		this.roomID = id;
		this.roomName = name;
		this.roomDescription = desc;
		this.roomPhotoURL = url;
		this.roomLocation = loc;
	}
	
	public int getRoomID() {
		return roomID;
	}
	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getRoomDescription() {
		return roomDescription;
	}
	public void setRoomDescription(String roomDescription) {
		this.roomDescription = roomDescription;
	}
	public String getRoomPhotoURL() {
		return roomPhotoURL;
	}
	public void setRoomPhotoURL(String roomPhotoURL) {
		this.roomPhotoURL = roomPhotoURL;
	}
	public Location getRoomLocation() {
		return roomLocation;
	}
	public void setRoomLocation(Location roomLocation) {
		this.roomLocation = roomLocation;
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof Room))
			return false;
		Room or = (Room) o;
		if (or.roomID != this.roomID)
			return false;
		if (!this.roomName.equals(or.roomName))
			return false;
		if (!this.roomDescription.equals(or.roomDescription))
			return false;
		if (!this.roomPhotoURL.equals(or.roomPhotoURL))
			return false;
		if (!this.roomLocation.equals(or.roomLocation))
			return false;
		
		return true;
	}

	@Override
	public String toString() {
		return "Room [roomID=" + roomID + ", roomName=" + roomName + ", roomDescription=" + roomDescription
				+ ", roomPhotoURL=" + roomPhotoURL + ", roomLocation=" + roomLocation + ", roomCategory=" + roomCategory
				+ "]";
	}

}
