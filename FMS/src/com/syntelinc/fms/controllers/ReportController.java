package com.syntelinc.fms.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.syntelinc.fms.logic.math.DateAdjuster;
import com.syntelinc.fms.security.Authorization;

@Controller
@SessionAttributes({"reportStart","reportEnd","reportPeriod"})
public class ReportController {

	@RequestMapping("/report-landing")
	public String reportLanding(HttpSession ses) {
		String page = "reports";
		if (!(new Authorization().isUserAdmin(ses)))
			page= "redirect:/login";
		return page;
	}
	
	@RequestMapping("/report-view")
	public ModelAndView requestReport(@RequestParam("date") String startDate, @RequestParam("quarter") String quarter, @RequestParam("year") String year, @RequestParam("type") String type, @RequestParam("period") String period, HttpSession ses) {
		ModelAndView mav = new ModelAndView();
		
		switch (type) {
		case "Usage":
			mav.setViewName("UsageReport");
			break;
		case "Upcoming Reservation":
			mav.setViewName("UpcomingReport");
			break;
		case "Previous Reservation":
			mav.setViewName("PastReport");
			break;
		default:
			mav.setViewName("redirect:/report-landing");
		}
		
		DateTimeFormatter dtf = DateTimeFormatter.ISO_OFFSET_DATE;
		LocalDate[] reportRange = new LocalDate[2];
		switch (period) {
		case "DAILY":
		case "WEEKLY":
			dtf= DateTimeFormatter.ofPattern("MMMM d, yyyy");
			reportRange = DateAdjuster.adjustDates(startDate, period, dtf);
			break;
		case "MONTHLY":
			dtf = DateTimeFormatter.ofPattern("MMMM yyyy dd");
			reportRange = DateAdjuster.adjustDates(startDate + " 01", period, dtf);
			break;
		case "QUARTERLY":
			dtf = DateTimeFormatter.ofPattern("MMMM yyyy dd");
			reportRange = DateAdjuster.adjustDates(quarter + " " + year + " 01", period, dtf);
			break;
		case "YEARLY":
			dtf = DateTimeFormatter.ofPattern("yyyy MM DD");
			reportRange = DateAdjuster.adjustDates(startDate + " 01" + " 01", period, dtf);
			break;
		}
		mav.addObject("reportEnd", reportRange[1]);
		mav.addObject("reportStart", reportRange[0]);
		mav.addObject("reportPeriod",period);
		
		if (!(new Authorization().isUserAdmin(ses)))
			mav.setViewName("redirect:/login");
		
		return mav;
	}
	
//	@RequestMapping(value="/json-test")
//	public String test() {
//		return "dailyUsage";
//	}
}
