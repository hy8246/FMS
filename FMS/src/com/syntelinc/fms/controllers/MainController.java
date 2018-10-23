package com.syntelinc.fms.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.syntelinc.fms.logic.Employee;
import com.syntelinc.fms.logic.queries.EmployeeQuery;

@Controller
@SessionAttributes({"emp","loc"})
public class MainController {
	
	@RequestMapping(value="/")
	public String homeRedirect() {
		return "redirect:/home";
	}
	
	@RequestMapping(value="/home")
	public String welcome() {
		String page = "index";
		return page;
	}
	
	@RequestMapping(value="/login")
	public ModelAndView loginPage(HttpSession ses) {
		ModelAndView mav = new ModelAndView();
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");
		
		mav.setViewName("login");
		
		ses.invalidate();
		mav.addObject("emp", ctx.getBean("employee"));
		mav.addObject("loc", ctx.getBean("location"));
		((ClassPathXmlApplicationContext)ctx).close();
		
		return mav;
	}
	
	@RequestMapping(value="/tryLogin", method=RequestMethod.POST)
	public ModelAndView login(@RequestParam String email, @RequestParam String password) {
		ModelAndView mav = new ModelAndView();
		
		Employee e = new EmployeeQuery().getEmployeeByEmail(email);
		if (e != null) {
			mav.setViewName("redirect:/my-reservations");
			mav.addObject("emp", e);
			mav.addObject("loc", e.getEmployeeHomeLocation());
		} else {
			mav.setViewName("redirect:/login?error=true");
		}
		
		return mav;
	}
}
