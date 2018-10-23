package com.syntelinc.fms.controllers;

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
import com.syntelinc.fms.logic.Room;
import com.syntelinc.fms.logic.queries.FeatureSetQuery;
import com.syntelinc.fms.logic.queries.LocationQuery;
import com.syntelinc.fms.logic.queries.RoomQuery;
import com.syntelinc.fms.logic.queries.SequenceQuery;
import com.syntelinc.fms.security.Authorization;

@Controller
@SessionAttributes({"emp","loc"})
public class NewLocationRoomController {

	@RequestMapping(value="/add-resources")
	public ModelAndView newLocRoomPage(@ModelAttribute("loc") Location loc, @ModelAttribute("emp") Employee emp, HttpSession ses) {
		ModelAndView mav = new ModelAndView();
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");
		
		mav.setViewName("admin");
		List<Room> rooms;
		rooms = new RoomQuery().getRoomsByLocation(loc.getLocationID());

		Map<Integer, ArrayList<FeatureSet>> features = new HashMap<Integer,ArrayList<FeatureSet>>();
		for (Room r : rooms) {
			features.put(r.getRoomID(), new ArrayList<FeatureSet>());
			List<FeatureSet> flist = features.get(r.getRoomID());
			List<FeatureSet> queryResult = new FeatureSetQuery().getFeaturesByRoom(r.getRoomID());
			flist.addAll(queryResult);
		}
		mav.addObject("rooms",rooms);
		mav.addObject("newRoom", ctx.getBean("room"));
		mav.addObject("features", features);
		
		if (!(new Authorization().isUserAdmin(ses)))
			mav.setViewName("redirect:/login");
		((ClassPathXmlApplicationContext) ctx).close();
		return mav;
	}
	
	@RequestMapping(value="/saveLocation", method=RequestMethod.POST)
	public String saveLocation(@ModelAttribute("location") Location loc) {
		if (loc.getLocationID() == 0) {
			loc.setLocationID(new SequenceQuery().getNextLocation());
			new LocationQuery().insertLocation(loc);
		}
		return "redirect:/add-resources";
	}
	
	
	
	@RequestMapping(value="/saveRoom", method=RequestMethod.POST)
	public String saveRoom(@RequestParam int roomID, @RequestParam String roomName, @RequestParam String roomDescription, @RequestParam String roomCategory, @RequestParam int locID) {
		if (roomID == 0) {
			/*System.out.println(r.getRoomCategory());
			r.setRoomID(new SequenceQuery().getNextRoom());
			r.setRoomLocation(locID);*/
			new RoomQuery().insertRoom(roomName, roomDescription, roomCategory, locID);
		} else {
			new RoomQuery().updateRoom(roomID, roomName, roomDescription, roomCategory);
		}
		return "redirect:/add-resources";
	}
	
	@RequestMapping(value="/deleteRoom", method=RequestMethod.POST)
	public String deleteRoom(@ModelAttribute("location") Location loc, @RequestParam int roomID) {
		if (roomID != 0)
		{
			new RoomQuery().deleteRoom(roomID);
		}
		
		return "redirect:/add-resources";
	}
	
	@RequestMapping(value="/saveFeatures", method=RequestMethod.POST)
	public String saveFeatures(@RequestParam int roomID, @RequestParam int chairs, @RequestParam int whiteboards, @RequestParam int projectors, @RequestParam int desktops, @RequestParam int televisions) {
		if (roomID != 0)
		{
			new FeatureSetQuery().chrisUpdateFeatureSet(roomID, chairs, projectors, whiteboards, desktops, televisions);
		}
		
		return "redirect:/add-resources";
	}
}
