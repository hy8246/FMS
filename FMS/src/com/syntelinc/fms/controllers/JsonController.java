package com.syntelinc.fms.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.syntelinc.fms.security.Authorization;

@Controller
public class JsonController {

	@RequestMapping(value="UsageJSON")
	public String usageJson(HttpSession ses) {
		String page = "UsageJSON";
		if (!(new Authorization().isUserAdmin(ses)))
			page= "redirect:/login";
		return page;
	}
	
	@RequestMapping(value="PastJSONBar")
	public String pjBar(HttpSession ses) {
		String page = "PastJSONBar";
		if (!(new Authorization().isUserAdmin(ses)))
			page= "redirect:/login";
		return page;
	}
	
	@RequestMapping(value="PastJSONChart")
	public String pjChart(HttpSession ses) {
		String page = "PastJSONChart";
		if (!(new Authorization().isUserAdmin(ses)))
			page= "redirect:/login";
		return page;
	}
	
	@RequestMapping(value="PastJSONTable")
	public String pjTable(HttpSession ses) {
		String page = "PastJSONTable";
		if (!(new Authorization().isUserAdmin(ses)))
			page= "redirect:/login";
		return page;
	}
	
	@RequestMapping(value="UpcomingJSONBar")
	public String ujBar(HttpSession ses) {
		String page = "UpcomingJSONBar";
		if (!(new Authorization().isUserAdmin(ses)))
			page= "redirect:/login";
		return page;
	}
	
	@RequestMapping(value="UpcomingJSONChart")
	public String ujChart(HttpSession ses) {
		String page = "UpcomingJSONChart";
		if (!(new Authorization().isUserAdmin(ses)))
			page= "redirect:/login";
		return page;
	}
	
	@RequestMapping(value="UpcomingJSONTable")
	public String ujTable(HttpSession ses) {
		String page = "UpcomingJSONTable";
		if (!(new Authorization().isUserAdmin(ses)))
			page= "redirect:/login";
		return page;
	}
}
