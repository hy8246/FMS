package com.syntelinc.fms.logic;

import org.springframework.beans.factory.annotation.Autowired;

public class FeatureSet {
	private Feature featureSetFeature;
	private Room featureSetRoom;
	private int featureSetQuantity;
	
	public FeatureSet() {}
	
	@Autowired
	public FeatureSet(Feature f, Room r, int qty) {
		this.featureSetFeature = f;
		this.featureSetRoom = r;
		this.featureSetQuantity = qty;
	}
	
	public Feature getFeatureSetFeature() {
		return featureSetFeature;
	}
	public void setFeatureSetFeature(Feature featureSetFeature) {
		this.featureSetFeature = featureSetFeature;
	}
	public Room getFeatureSetRoom() {
		return featureSetRoom;
	}
	public void setFeatureSetRoom(Room featureSetRoom) {
		this.featureSetRoom = featureSetRoom;
	}
	public int getFeatureSetQuantity() {
		return featureSetQuantity;
	}
	public void setFeatureSetQuantity(int featureSetQuantity) {
		this.featureSetQuantity = featureSetQuantity;
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof FeatureSet))
			return false;
		FeatureSet ofs = (FeatureSet) o;
		if (!(this.featureSetFeature.equals(ofs.featureSetFeature)))
			return false;
		if (!(this.featureSetRoom.equals(ofs.featureSetRoom)))
			return false;
		if (this.featureSetQuantity != ofs.featureSetQuantity)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FeatureSet [featureSetFeature=" + featureSetFeature + ", featureSetRoom=" + featureSetRoom
				+ ", featureSetQuantity=" + featureSetQuantity + "]";
	}
}
