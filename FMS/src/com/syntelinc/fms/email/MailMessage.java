package com.syntelinc.fms.email;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.syntelinc.fms.logic.Reservation;
public class MailMessage {
	
	public static void sendEmail(Reservation r)
	{
		//Resource r=new ClassPathResource("applicationContext.xml");  
				ApplicationContext b = new ClassPathXmlApplicationContext("spring-config.xml");
			
				MailMail m=(MailMail)b.getBean("mailMail");  
				String sender="John";//write here sender gmail id  
				String receiver=r.getReservationEmployee().getEmployeeEmail();//write here receiver id  
				String subject="SOLR Reservation Confirmation";
				LocalDateTime start= r.getReservationStart();
				LocalDateTime end = r.getReservationEnd();
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm:ssa");
				String body=r.getReservationEmployee().getEmployeeName() + ","
							+"\n\nThank you for reserving facilities with Syntel through Syntel Online Location Reservations. "
							+"Below is a summary of you reservation. Please contact Syntel Online Location Reservation or your site administrator with any question."
							+"\nReservation information:"
							+"\n\n\tRoom: " + r.getReservationRoom().getRoomName()
							+"\n\tDate: " + start.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) 
							+"\n\tTime: " + start.format(dtf) + " - " + end.format(dtf)
							+"\n";
				m.sendMail(sender,receiver,subject,body);  
				
				((ClassPathXmlApplicationContext) b).close();
	}
	
}
