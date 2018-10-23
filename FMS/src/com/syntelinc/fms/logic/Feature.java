package com.syntelinc.fms.logic;

import com.syntelinc.fms.logic.queries.SequenceQuery;

public class Feature {
	private int featureID;
	private String featureDescription;
	
	public Feature(){}
	public Feature(String desc) {
		this.featureID = new SequenceQuery().getNextFeature();
		this.featureDescription = desc;
	}
	public Feature(int id, String desc) {
		this.featureID = id;
		this.featureDescription = desc;
	}
	
	public int getFeatureID() {
		return featureID;
	}
	public void setFeatureID(int featureID) {
		this.featureID = featureID;
	}
	public String getFeatureDescription() {
		return featureDescription;
	}
	public void setFeatureDescription(String featureDescription) {
		this.featureDescription = featureDescription;
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof Feature))
			return false;
		Feature of = (Feature) o;
		if (this.featureID != of.featureID)
			return false;
		if (!this.featureDescription.equals(of.featureDescription))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Feature [featureID=" + featureID + ", featureDescription=" + featureDescription + "]";
	}
}
