package com.syntelinc.fms.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.syntelinc.fms.logic.Employee;
import com.syntelinc.fms.logic.Location;
import com.syntelinc.fms.logic.Reservation;
import com.syntelinc.fms.logic.Room;
import com.syntelinc.fms.logic.queries.ReservationQuery;
import com.syntelinc.fms.logic.queries.RoomQuery;
import com.syntelinc.fms.security.Authorization;

@Controller
@SessionAttributes({"emp","loc"})
public class MyReservationController {
	
	@RequestMapping(value="/my-reservations")
	public ModelAndView myResPage(@ModelAttribute("emp") Employee emp, @ModelAttribute("loc") Location loc, HttpSession ses) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("reservations");
		
		List<Reservation> reservations = new ReservationQuery().getValidReservationsByEmployee(emp.getEmployeeID());
		List<Room> rooms = new RoomQuery().getRoomsByLocation(loc.getLocationID());
		mav.addObject("reservations", reservations);
		mav.addObject("rooms", rooms);
		
		
		if (!(new Authorization().isUserAuthorized(ses)))
			mav.setViewName("redirect:/login");
		
		return mav;
	}
}
