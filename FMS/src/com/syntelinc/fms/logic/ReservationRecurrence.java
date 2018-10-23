package com.syntelinc.fms.logic;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class ReservationRecurrence {
	private boolean recurring;
	private int everySpace;
	private String everyPeriod;
	private boolean onMonday;
	private boolean onTuesday;
	private boolean onWednesday;
	private boolean onThursday;
	private boolean onFriday;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate endsOnDate;
	private int endsAfterTimes;
	private String endType;
	public boolean isRecurring() {
		return recurring;
	}
	public void setRecurring(boolean recurring) {
		this.recurring = recurring;
	}
	public int getEverySpace() {
		return everySpace;
	}
	public void setEverySpace(int everySpace) {
		this.everySpace = everySpace;
	}
	public String getEveryPeriod() {
		return everyPeriod;
	}
	public void setEveryPeriod(String everyPeriod) {
		this.everyPeriod = everyPeriod;
	}
	public boolean isOnMonday() {
		return onMonday;
	}
	public void setOnMonday(boolean onMonday) {
		this.onMonday = onMonday;
	}
	public boolean isOnTuesday() {
		return onTuesday;
	}
	public void setOnTuesday(boolean onTuesday) {
		this.onTuesday = onTuesday;
	}
	public boolean isOnWednesday() {
		return onWednesday;
	}
	public void setOnWednesday(boolean onWednesday) {
		this.onWednesday = onWednesday;
	}
	public boolean isOnThursday() {
		return onThursday;
	}
	public void setOnThursday(boolean onThursday) {
		this.onThursday = onThursday;
	}
	public boolean isOnFriday() {
		return onFriday;
	}
	public void setOnFriday(boolean onFriday) {
		this.onFriday = onFriday;
	}
	public LocalDate getEndsOnDate() {
		return endsOnDate;
	}
	public void setEndsOnDate(LocalDate endsOnDate) {
		this.endsOnDate = endsOnDate;
	}
	public int getEndsAfterTimes() {
		return endsAfterTimes;
	}
	public void setEndsAfterTimes(int endsAfterTimes) {
		this.endsAfterTimes = endsAfterTimes;
	}
	public String getEndType() {
		return endType;
	}
	public void setEndType(String endType) {
		this.endType = endType;
	}
	@Override
	public String toString() {
		return "ReservationRecurrence [recurring=" + recurring + ", everySpace=" + everySpace + ", everyPeriod="
				+ everyPeriod + ", onMonday=" + onMonday + ", onTuesday=" + onTuesday + ", onWednesday=" + onWednesday
				+ ", onThursday=" + onThursday + ", onFriday=" + onFriday + ", endsOnDate=" + endsOnDate
				+ ", endsAfterTimes=" + endsAfterTimes + ", endType=" + endType + "]";
	}
}
