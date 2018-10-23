package com.syntelinc.fms.logic.math;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateAdjuster {
	
	public static LocalDate[] adjustDates(String dateString, String period) {
		LocalDate date = LocalDate.parse(dateString);
		LocalDate today = LocalDate.now();
		if (date.isAfter(today))
			date = today;
		LocalDate start = getStartDate(date, period);
		LocalDate end = getEndDate(date,period);
		return new LocalDate[] {start,end};
	}
	
	public static LocalDate[] adjustDates(String dateString, String period, DateTimeFormatter dtf) {
		LocalDate date = LocalDate.parse(dateString, dtf);
		LocalDate today = LocalDate.now();
		if (date.isAfter(today))
			date = today;
		LocalDate start = getStartDate(date, period);
		LocalDate end = getEndDate(date,period);
		return new LocalDate[] {start,end};
	}
	
	private static LocalDate getStartDate(LocalDate date, String period) {
		switch (period) {
		case "DAILY":
			return getStartDateDaily(date);
		case "WEEKLY":
			return getStartDateWeekly(date);
		case "MONTHLY":
			return getStartDateMonthly(date);
		case "QUARTERLY":
			return getStartDateQuarterly(date);
		case "YEARLY":
			return getStartDateYearly(date);
		default:
			throw new IllegalArgumentException("Invalid period passed; Must be DAILY, WEEKLY, MONTHLY, QUARTERLY, or YEARLY.");
		}
	}
	
	private static LocalDate getEndDate(LocalDate date, String period) {
		switch (period) {
		case "DAILY":
			return getEndDateDaily(date);
		case "WEEKLY":
			return getEndDateWeekly(date);
		case "MONTHLY":
			return getEndDateMonthly(date);
		case "QUARTERLY":
			return getEndDateQuarterly(date);
		case "YEARLY":
			return getEndDateYearly(date);
		default:
			throw new IllegalArgumentException("Invalid period passed; Must be DAILY, WEEKLY, MONTHLY, QUARTERLY, or YEARLY.");
		}
	}
	
	private static LocalDate getStartDateDaily(LocalDate date) {
		return date;
	}
	
	private static LocalDate getStartDateWeekly(LocalDate date) {
		switch(date.getDayOfWeek()) {
		case SUNDAY:
			return date.plusDays(1);
		case MONDAY:
			return date;
		case TUESDAY:
			return date.minusDays(1);
		case WEDNESDAY:
			return date.minusDays(2);
		case THURSDAY:
			return date.minusDays(3);
		case FRIDAY:
			return date.minusDays(4);
		case SATURDAY:
			return date.minusDays(5);
		}
		return date;
	}
	
	private static LocalDate getStartDateMonthly(LocalDate date) {
		return LocalDate.of(date.getYear(), date.getMonthValue(), 1);
	}
	
	private static LocalDate getStartDateQuarterly(LocalDate date) {
		switch (date.getMonth()) {
		case JANUARY:
		case FEBRUARY:
		case MARCH:
			date = LocalDate.of(date.getYear(), 1, 1);
			break;
		case APRIL:
		case MAY:
		case JUNE:
			date = LocalDate.of(date.getYear(), 4, 1);
		case JULY:
		case AUGUST:
		case SEPTEMBER:
			date = LocalDate.of(date.getYear(), 7, 1);
		case OCTOBER:
		case NOVEMBER:
		case DECEMBER:
			date = LocalDate.of(date.getYear(), 10, 1);
		}
		
		return date;
	}
	
	private static LocalDate getStartDateYearly(LocalDate date) {
		return LocalDate.of(date.getYear(),1,1);
	}
	private static LocalDate getEndDateDaily(LocalDate date) {
		return date;
	}
	
	private static LocalDate getEndDateWeekly(LocalDate date) {		
		switch(date.getDayOfWeek()) {
		case SUNDAY:
			date = date.plusDays(5);
			break;
		case MONDAY:
			date = date.plusDays(4);
			break;
		case TUESDAY:
			date = date.plusDays(3);
			break;
		case WEDNESDAY:
			date = date.plusDays(2);
			break;
		case THURSDAY:
			date = date.plusDays(1);
			break;
		case FRIDAY:
			break;
		case SATURDAY:
			date = date.minusDays(1);
		}
		return date;
	}
	
	private static LocalDate getEndDateMonthly(LocalDate date) {
		return LocalDate.of(date.getYear(), date.getMonthValue(), date.lengthOfMonth());
	}
	
	private static LocalDate getEndDateQuarterly(LocalDate date) {
		switch (date.getMonth()) {
		case JANUARY:
		case FEBRUARY:
		case MARCH:
			date = LocalDate.of(date.getYear(), 3, 31);
			break;
		case APRIL:
		case MAY:
		case JUNE:
			date = LocalDate.of(date.getYear(), 6, 30);
		case JULY:
		case AUGUST:
		case SEPTEMBER:
			date = LocalDate.of(date.getYear(), 9, 30);
		case OCTOBER:
		case NOVEMBER:
		case DECEMBER:
			date = LocalDate.of(date.getYear(), 12, 31);
		}
		
		return date;
	}
	
	private static LocalDate getEndDateYearly(LocalDate date) {
		return LocalDate.of(date.getYear(),12,31);
	}
}
