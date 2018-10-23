package com.syntelinc.fms.logic.queries;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.syntelinc.fms.logic.Employee;
import com.syntelinc.fms.logic.mappers.EmployeeMapper;
public class EmployeeQuery 
{
	ApplicationContext context ;
    JdbcTemplate temp;
    
    public EmployeeQuery()
    {
    	context=new ClassPathXmlApplicationContext("spring-config.xml");
    	temp = (JdbcTemplate)context.getBean("jtemp");
    }
	
	public Employee getEmployeeByID(int id) {
		//Select * from fm_employees where EID =?
		
		try {
			Employee f  = temp.queryForObject("Select * from fm_employees where EID =?",new EmployeeMapper(),id);
			return f;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	public Employee getEmployeeByEmail(String email) {
		
		
		//Select * from fm_employees where EMAIL =?
		try {
			Employee f  = temp.queryForObject("Select * from fm_employees where EMAIL =?",new EmployeeMapper(),email);
			return f;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
//	public int updateEmployee(Employee e) {
//		int location=e.getEmployeeHomeLocation().getLocationID();
//		String type=e.getAuthType();
//		String email=e.getEmployeeEmail();
//		String name=e.getEmployeeName();
//		int id =e.getEmployeeID();
//		//update fm_employees set E_HOME_LOC=?,EAUTHYPE=?,EMAIL=?,ENAME=? WHERE EID=?;
//		int f  = temp.update("update fm_employees set E_HOME_LOC=?,EAUTHYPE=?,EMAIL=?,ENAME=? WHERE EID=?",
//				location,type,email,name,id);
//		return f;
//	}
//	
//	public int insertEmployee(Employee e) {
//		int location=e.getEmployeeHomeLocation().getLocationID();
//		String type=e.getAuthType();
//		String email=e.getEmployeeEmail();
//		String name=e.getEmployeeName();
//		int id =e.getEmployeeID();
//		//INSERT INTO FM_EMPLOYEES VALUES(?,?,?,?,?,?);
//		int f  = temp.update("INSERT INTO FM_EMPLOYEES VALUES(?,?,?,?,?)",
//				id,name,email,type,location);
//		return f;
//	}
}
