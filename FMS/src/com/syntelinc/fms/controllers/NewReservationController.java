package com.syntelinc.fms.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.syntelinc.fms.logic.Employee;
import com.syntelinc.fms.logic.FeatureSet;
import com.syntelinc.fms.logic.Location;
import com.syntelinc.fms.logic.Reservation;
import com.syntelinc.fms.logic.ReservationRecurrence;
import com.syntelinc.fms.logic.Room;
import com.syntelinc.fms.logic.queries.FeatureSetQuery;
import com.syntelinc.fms.logic.queries.RecurringReservationQuery;
import com.syntelinc.fms.logic.queries.ReservationQuery;
import com.syntelinc.fms.logic.queries.RoomQuery;
import com.syntelinc.fms.logic.queries.SequenceQuery;
import com.syntelinc.fms.security.Authorization;

@Controller
@SessionAttributes({"emp","loc"})
public class NewReservationController {
	
	@RequestMapping(value="/new-reservation")
	public ModelAndView newResPage(@ModelAttribute("reservation") Reservation someReservation ,@ModelAttribute("date") String date, @ModelAttribute("room-category") String cat,@ModelAttribute("loc") Location loc, @ModelAttribute("emp") Employee emp, HttpSession ses) {
		ModelAndView mav = new ModelAndView();
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");
		
		mav.setViewName("create");
		/*LocalDate ldate = LocalDate.now();
		if (date.equals("today")) {
			ldate = LocalDate.now();
			mav.addObject("date",ldate.toString());
		} else {
			ldate = LocalDate.parse(date);
			mav.addObject("date", date);
		}
		mav.addObject("room-category", "All");*/
		List<Room> rooms;
		//if (cat.equals("All")) {
			rooms = new RoomQuery().getRoomsByLocation(loc.getLocationID());
//		} else {
//			rooms = new RoomQuery().getRoomsByLocationAndCategory(loc.getLocationID(), cat);
//		}
		
		List<Reservation> res = new ArrayList<Reservation>();
		Map<Integer, ArrayList<FeatureSet>> features = new HashMap<Integer,ArrayList<FeatureSet>>();
		for (Room r : rooms) {
			features.put(r.getRoomID(), new ArrayList<FeatureSet>());
			List<FeatureSet> flist = features.get(r.getRoomID());
			List<FeatureSet> queryResult = new FeatureSetQuery().getFeaturesByRoom(r.getRoomID());
			flist.addAll(queryResult);
//			if (emp.getAuthType().equals("U")) {
				res.addAll(new ReservationQuery().getValidReservationsByRoom(r.getRoomID()));
//			} else {
//				res.addAll(new ReservationQuery().getReservationsByRoom(r.getRoomID()));
//			}
		}
		
		
		mav.addObject("rooms", rooms);
		mav.addObject("reservations", res);
		mav.addObject("features", features);
		
		if (!(new Authorization().isUserAuthorized(ses)))
			mav.setViewName("redirect:/login");
		
		((ClassPathXmlApplicationContext) ctx).close();
		
		return mav;
	}
	
	@RequestMapping(value="/saveReservation",method=RequestMethod.POST)
	// public ModelAndView save(@ModelAttribute("reservation") Reservation res, @RequestParam int roomID, HttpSession ses) {
	public ModelAndView save(@RequestParam int roomID, @RequestParam String reservationStart, @RequestParam String reservationEnd, @RequestParam String reservationPurpose,
							 @RequestParam boolean recurring, @RequestParam int everySpace, @RequestParam String everyPeriod,
							 @RequestParam boolean onMonday, @RequestParam boolean onTuesday, @RequestParam boolean onWednesday, @RequestParam boolean onThursday, @RequestParam boolean onFriday,
							 @RequestParam String endType, @RequestParam String endsOnDate, @RequestParam int endsAfterTimes,
							 HttpSession ses, @RequestParam(required=false) String supername, @RequestParam(required=false) String superpass) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		DateTimeFormatter recurringFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
		
		Reservation res = new Reservation();
		res.setReservationID(new SequenceQuery().getNextReservation());
		res.setReservationRequestTime(LocalDateTime.now());
		res.setReservationStart(LocalDateTime.parse(reservationStart, formatter));
		res.setReservationEnd(LocalDateTime.parse(reservationEnd, formatter));
		res.setReservationPurpose(reservationPurpose);
		res.setReservationEmployee((Employee) ses.getAttribute("emp"));
		res.setReservationGroup(new SequenceQuery().getNextReservationGroup());
		res.setReservationRoom(new RoomQuery().getRoomByID(roomID));
		
		if (res.getReservationRoom().getRoomCategory().equals("X") && !res.getReservationEmployee().getAuthType().equals("S"))
			return new ModelAndView("redirect:/new-reservation?restrictedError=1");
		if (recurring)
		{
			ReservationRecurrence recurrence = new ReservationRecurrence();
			recurrence.setRecurring(true);
			recurrence.setEverySpace(everySpace);
			recurrence.setEveryPeriod(everyPeriod);
			
			recurrence.setOnMonday(onMonday);
			recurrence.setOnTuesday(onTuesday);
			recurrence.setOnWednesday(onWednesday);
			recurrence.setOnThursday(onThursday);
			recurrence.setOnFriday(onFriday);
			
			recurrence.setEndType(endType);
			recurrence.setEndsAfterTimes(endsAfterTimes);
			recurrence.setEndsOnDate(LocalDate.parse(endsOnDate, recurringFormatter));
			
			if (new RecurringReservationQuery().insertReservations(res, recurrence, roomID) == false)
				return new ModelAndView("redirect:/new-reservation?recurringError=1");
		} else {
			new ReservationQuery().insertReservation(res, roomID);
		}
		//MailMessage.sendEmail(res);
		
		return new ModelAndView("redirect:/new-reservation");
	}
	
	@RequestMapping(value="/updateReservation",method=RequestMethod.POST)
    public ModelAndView update(@RequestParam int resID, @RequestParam int roomID, @RequestParam String reservationStart, @RequestParam String reservationEnd, @RequestParam String reservationPurpose, HttpSession ses) {
        // MMMM D, YYYY h:mm A
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy h:mm a");
         
        Reservation res = new Reservation();
        res.setReservationID(resID);
        res.setReservationStart(LocalDateTime.parse(reservationStart, formatter));
        res.setReservationEnd(LocalDateTime.parse(reservationEnd, formatter));
        res.setReservationPurpose(reservationPurpose);
        res.setReservationRequestTime(LocalDateTime.now());
        res.setReservationEmployee((Employee) ses.getAttribute("emp"));
        new ReservationQuery().updateReservation(res, roomID);
        return new ModelAndView("redirect:/my-reservations");
    }
     
    @RequestMapping(value="/deleteReservation",method=RequestMethod.POST)
    public ModelAndView update(@RequestParam int resID, HttpSession ses) {
        new ReservationQuery().deleteReservation(resID, ((Employee) ses.getAttribute("emp")).getEmployeeID());
        return new ModelAndView("redirect:/my-reservations");
    }
}