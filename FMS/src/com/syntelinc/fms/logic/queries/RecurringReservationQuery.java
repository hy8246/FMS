package com.syntelinc.fms.logic.queries;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.syntelinc.fms.logic.Reservation;
import com.syntelinc.fms.logic.ReservationRecurrence;

public class RecurringReservationQuery {
	
	private LocalDateTime recurringStart;
	private LocalDateTime recurringEnd;
	private List<Reservation> newRes;
	private List<Reservation> currentRes;
	private ReservationRecurrence resRecur;
	
	public boolean insertReservations(Reservation firstRes, ReservationRecurrence rr, int roomID) {
		currentRes = new ReservationQuery().getValidFutureReservationsByRoom(roomID);
		recurringStart = firstRes.getReservationStart();
		recurringEnd = firstRes.getReservationEnd();
		firstRes.setReservationRoom(new RoomQuery().getRoomByID(roomID));
		newRes = new ArrayList<Reservation>();
		resRecur = rr;
		if (!(resRecur.isOnMonday() || resRecur.isOnTuesday() || resRecur.isOnWednesday() || resRecur.isOnThursday() || resRecur.isOnFriday()) && resRecur.getEveryPeriod().equals("WEEKS"))
			return false;
		
		if (!buildNewResList(firstRes))
			return false;

		addToDB();
		return true;
	}
		
	private boolean buildNewResList(Reservation firstRes) {
		do {
			Reservation newR = firstRes.clone();
			newR.setReservationStart(recurringStart);
			newR.setReservationEnd(recurringEnd);
			if (checkOverlaps(newR))
				return false;
			newRes.add(newR);
		} while (incrementReservationDates());
		return true;
	}
	
	private boolean checkOverlaps(Reservation newR) {
		for (Reservation r : currentRes) {
			if (r.isOverlapping(newR))
				return true;
		}
		return false;
	}
	
	private boolean incrementReservationDates() {
		if (!resRecur.isRecurring())
			return false;
		switch (resRecur.getEveryPeriod()) {
		case "DAYS":
			return dayIncrement();
		case "WEEKS":
			return weekIncrement();
		case "MONTHS":
			return monthIncrement();
		case "YEAR":
			return yearIncrement();
		default:
			return false;
		}
	}
	
	private boolean dayIncrement() {
		LocalDate date = recurringStart.toLocalDate();
		date = date.plusDays(resRecur.getEverySpace());
		switch (date.getDayOfWeek()) {
		case MONDAY:
		case TUESDAY:
		case WEDNESDAY:
		case THURSDAY:
		case FRIDAY:
			break;
		case SATURDAY:
			date = date.plusDays(2);
			break;
		case SUNDAY:
			date = date.plusDays(1);
			break;
		}
		
		if (!checkDateFinal(date))
			return false;
		
		recurringStart = LocalDateTime.of(date, recurringStart.toLocalTime());
		recurringEnd = LocalDateTime.of(date, recurringEnd.toLocalTime());
		return true;
	}
	
	private boolean weekIncrement() {
		LocalDate date = recurringStart.toLocalDate();
		looper:
		while (true) {
			date = date.plusDays(1);
			daySwitch:
			switch (date.getDayOfWeek()) {
			case MONDAY:
				if (resRecur.isOnMonday())
					break looper;
				break daySwitch;
			case TUESDAY:
				if (resRecur.isOnTuesday())
					break looper;
				break daySwitch;
			case WEDNESDAY:
				if (resRecur.isOnWednesday())
					break looper;
				break daySwitch;
			case THURSDAY:
				if (resRecur.isOnThursday())
					break looper;
				break daySwitch;
			case FRIDAY:
				if (resRecur.isOnFriday())
					break looper;
				break daySwitch;
			case SATURDAY:
				date = date.plusWeeks(resRecur.getEverySpace() - 1);
				date = date.plusDays(1);
				break daySwitch;
			case SUNDAY:
				date = date.plusWeeks(resRecur.getEverySpace() - 1);
				break daySwitch;
			}
		}

		if (!checkDateFinal(date))
			return false;
		
		recurringStart = LocalDateTime.of(date, recurringStart.toLocalTime());
		recurringEnd = LocalDateTime.of(date, recurringEnd.toLocalTime());
		return true;
	}
	
	private boolean monthIncrement() {
		LocalDate date = recurringStart.toLocalDate();
		date = date.plusMonths(resRecur.getEverySpace());
		if (date.getDayOfWeek().equals(DayOfWeek.SATURDAY))
			date = date.plusDays(2);
		else if (date.getDayOfWeek().equals(DayOfWeek.SUNDAY))
			date = date.plusDays(1);
		
		if (!checkDateFinal(date))
			return false;
		
		recurringStart = LocalDateTime.of(date, recurringStart.toLocalTime());
		recurringEnd = LocalDateTime.of(date, recurringEnd.toLocalTime());
		return true;
	}
	
	private boolean yearIncrement() {
		LocalDate date = recurringStart.toLocalDate();
		date = date.plusYears(resRecur.getEverySpace());
		if (date.getDayOfWeek().equals(DayOfWeek.SATURDAY))
			date = date.plusDays(2);
		else if (date.getDayOfWeek().equals(DayOfWeek.SUNDAY))
			date = date.plusDays(1);
		
		if (!checkDateFinal(date))
			return false;
		
		recurringStart = LocalDateTime.of(date, recurringStart.toLocalTime());
		recurringEnd = LocalDateTime.of(date, recurringEnd.toLocalTime());
		return true;
	}
	
	private void addToDB() {
		int nextGroup = new SequenceQuery().getNextReservationGroup();
		ReservationQuery rq = new ReservationQuery();
		for (Reservation r : newRes) {
			r.setReservationGroup(nextGroup);
			rq.insertReservation(r, r.getReservationRoom().getRoomID());
		}
	}
	
	private boolean checkDateFinal(LocalDate date) {
		if (resRecur.getEndType().equals("endOnDate")) {
			if (date.isAfter(resRecur.getEndsOnDate()))
				return false;
		} else if (resRecur.getEndType().equals("endsAfterTimes")) {
			if (resRecur.getEndsAfterTimes() > 1)
				resRecur.setEndsAfterTimes(resRecur.getEndsAfterTimes() - 1);
			else
				return false;
		}
		return true;
	}
}
