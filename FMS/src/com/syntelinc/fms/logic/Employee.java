package com.syntelinc.fms.logic;

public class Employee {
	private int employeeID;
	private String employeeName;
	private String employeeEmail;
	private String authType;
	private Location employeeHomeLocation;
	public int getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeEmail() {
		return employeeEmail;
	}
	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}
	public String getAuthType() {
		return authType;
	}
	public void setAuthType(String authType) {
		this.authType = authType;
	}
	public Location getEmployeeHomeLocation() {
		return employeeHomeLocation;
	}
	public void setEmployeeHomeLocation(Location employeeHomeLocation) {
		this.employeeHomeLocation = employeeHomeLocation;
	}
	@Override
	public String toString() {
		return "Employee [employeeID=" + employeeID + ", employeeName=" + employeeName + ", employeeEmail="
				+ employeeEmail + ", authType=" + authType + ", employeeHomeLocation=" + employeeHomeLocation + "]";
	}
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Employee))
			return false;
		Employee e = (Employee) o;
		
		if (e.employeeID != this.employeeID)
			return false;
		if (!e.employeeName.equals(this.employeeName))
			return false;
		if (!e.employeeEmail.equals(this.employeeEmail))
			return false;
		if (!e.authType.equals(this.authType))
			return false;
		if (!e.employeeHomeLocation.equals(this.employeeHomeLocation))
			return false;
		return true;
	}
}
