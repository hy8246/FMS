package com.syntelinc.fms.security;

import javax.servlet.http.HttpSession;

import com.syntelinc.fms.logic.Employee;

public class Authorization {
	public boolean isUserAuthorized(HttpSession ses) {
		Employee e = (Employee) ses.getAttribute("emp");
		if (e == null) 
			return false;
		if (e.getAuthType() == null)
			return false;
		return true;
	}
	
	public boolean isUserAdmin(HttpSession ses) {
		Employee e = (Employee) ses.getAttribute("emp");
		if (!isUserAuthorized(ses))
			return false;
		if (e.getAuthType().equals("A"))
			return true;
		return false;
	}
}
