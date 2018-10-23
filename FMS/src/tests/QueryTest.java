package tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@Test
@ContextConfiguration(locations = { "classpath:spring-config.xml" })
public class QueryTest extends AbstractTestNGSpringContextTests {

	protected ApplicationContext ctx;
	protected JdbcTemplate jtemp;
	protected WriteToExcel excelBook;
	
	@AfterMethod
	public void afterMethod() {
		getDBResetString();
		((ClassPathXmlApplicationContext) ctx).close();
	}
	
	@BeforeClass
	public void beforeClass() {
		excelBook = new WriteToExcel();
	}
	
	@BeforeMethod
	public void beforeMethod() {
		ctx = new ClassPathXmlApplicationContext("spring-config.xml");
		jtemp = ctx.getBean("jtemp", JdbcTemplate.class);
	}
	
	protected void getDBResetString() {
		jtemp.execute("DELETE FROM fm_reservations");
		jtemp.execute("DELETE FROM fm_feature_sets");
		jtemp.execute("DELETE FROM fm_rooms");
		jtemp.execute("DELETE FROM fm_employees");
		jtemp.execute("DELETE FROM fm_features");
		jtemp.execute("DELETE FROM fm_locations");
		
		jtemp.execute("DROP SEQUENCE location_ids");
		jtemp.execute("DROP SEQUENCE feature_ids");
		jtemp.execute("DROP SEQUENCE room_ids");
		jtemp.execute("DROP SEQUENCE reservation_ids");
		
		jtemp.execute("create sequence feature_ids start with 1 increment by 1");
		jtemp.execute("create sequence location_ids start with 1 increment by 1");
		jtemp.execute("create sequence room_ids start with 1 increment by 1");
		jtemp.execute("create sequence reservation_ids start with 1 increment by 1");
		
		jtemp.execute("INSERT INTO fm_locations VALUES(location_ids.nextval, '255 Schilling Boulevard Suite 101', 'Collierville', 'TN', 'USA')");
		jtemp.execute("INSERT INTO fm_locations VALUES(location_ids.nextval, '205 Reidhurst Ave #200', 'Nashville', 'TN', 'USA')");
		jtemp.execute("INSERT INTO fm_locations VALUES(location_ids.nextval, '2902 West Agua Fria Freeway Suite 1020', 'Phoenix', 'AZ', 'USA')");
		jtemp.execute("INSERT INTO fm_locations VALUES(location_ids.nextval, '2nd Floor, 145 St Vincent Street','Glasgow',null,'UK')");
		
		jtemp.execute("INSERT INTO fm_employees VALUES(100, 'Gabriel', 'gabriel@email.com','U', 1)");
		jtemp.execute("INSERT INTO fm_employees VALUES(101, 'John', 'john@email.com','U', 1)");
		jtemp.execute("INSERT INTO fm_employees VALUES(102, 'Andrew', 'andrew@email.com','U', 1)");
		jtemp.execute("INSERT INTO fm_employees VALUES(103, 'Jose', 'jose@email.com','U', 1)");
		jtemp.execute("INSERT INTO fm_employees VALUES(104, 'Chris', 'chris@email.com','U', 1)");
		
		jtemp.execute("INSERT INTO fm_rooms VALUES(room_ids.nextval, 'Scrum Room 1', 'Good room for scrum meetings', 'M', 'scrum-room1 path', 1)");
		jtemp.execute("INSERT INTO fm_rooms VALUES(room_ids.nextval, 'Scrum Room 2', 'Good room for scrum meetings','M', 'scrum-room2 path', 1)");
		jtemp.execute("INSERT INTO fm_rooms VALUES(room_ids.nextval, 'Training', 'Good room for training','T', 'training-room path', 1)");
		jtemp.execute("INSERT INTO fm_rooms VALUES(room_ids.nextval, 'Rec Room', 'Good room for relaxation','R', 'rec-room path', 2)");
		jtemp.execute("INSERT INTO fm_rooms VALUES(room_ids.nextval, 'Pivotal Lab', 'Good room for lab stuff','L', 'lab-room path', 3)");
		
		jtemp.execute("INSERT INTO fm_features VALUES(feature_ids.nextval,'CHAIR')");
		jtemp.execute("INSERT INTO fm_features VALUES(feature_ids.nextval,'PROJECTOR')");
		jtemp.execute("INSERT INTO fm_features VALUES(feature_ids.nextval,'WHITEBOARD')");
		jtemp.execute("INSERT INTO fm_features VALUES(feature_ids.nextval,'DESKTOP')");
		jtemp.execute("INSERT INTO fm_features VALUES(feature_ids.nextval,'TELEVISION')");
		
		jtemp.execute("INSERT INTO fm_feature_sets VALUES(1,1,5)");
		jtemp.execute("INSERT INTO fm_feature_sets VALUES(2,1,1)");
		jtemp.execute("INSERT INTO fm_feature_sets VALUES(3,1,1)");
		jtemp.execute("INSERT INTO fm_feature_sets VALUES(1,2,5)");
		jtemp.execute("INSERT INTO fm_feature_sets VALUES(2,2,1)");
		jtemp.execute("INSERT INTO fm_feature_sets VALUES(4,3,10)");
		jtemp.execute("INSERT INTO fm_feature_sets VALUES(1,3,12)");
		jtemp.execute("INSERT INTO fm_feature_sets VALUES(2,3,1)");
		
		jtemp.execute("INSERT INTO fm_reservations VALUES(reservation_ids.nextval, TO_TIMESTAMP('2018-02-21 14:24:35', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2018-02-28 08:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2018-02-28 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Board Meeting', null, null, 'D', 1, 101)");
		jtemp.execute("INSERT INTO fm_reservations VALUES(reservation_ids.nextval, TO_TIMESTAMP('2018-03-05 10:17:46', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2018-03-08 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2018-03-08 15:00:00', 'YYYY-MM-DD HH24:MI:SS'),'Scrum Meeting', null, null, 'N', 2, 103)");
		jtemp.execute("INSERT INTO fm_reservations VALUES(reservation_ids.nextval, TO_TIMESTAMP('2018-03-07 11:30:46', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2018-03-09 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2018-03-09 16:00:00', 'YYYY-MM-DD HH24:MI:SS'),'Scrum Meeting', null, null, 'A', 1, 104)");
		jtemp.execute("INSERT INTO fm_reservations VALUES(reservation_ids.nextval, TO_TIMESTAMP('2018-03-06 09:25:54', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2018-03-07 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2018-03-07 17:00:00', 'YYYY-MM-DD HH24:MI:SS'),'Campus Hire Training', null, null, 'A', 3, 101)");
		jtemp.execute("INSERT INTO fm_reservations VALUES(reservation_ids.nextval, TO_TIMESTAMP('2018-03-07 09:25:54', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2018-03-08 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2018-03-08 17:00:00', 'YYYY-MM-DD HH24:MI:SS'),'Campus Hire Training', null, null, 'C', 3, 101)");
		jtemp.execute("INSERT INTO fm_reservations VALUES(reservation_ids.nextval, TO_TIMESTAMP('2018-03-08 09:25:54', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2018-03-09 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2018-03-09 17:00:00', 'YYYY-MM-DD HH24:MI:SS'),'Campus Hire Training', null, null, 'A', 3, 101)");
	}
}
