package com.prodyna.pac.ars.reservation;

import java.security.PrivilegedAction;
import java.util.Date;

import javax.inject.Inject;
import javax.security.auth.Subject;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.ars.masterdata.AircraftService;
import com.prodyna.pac.ars.masterdata.AircraftTypeService;
import com.prodyna.pac.ars.masterdata.UserService;
import com.prodyna.pac.ars.masterdata.model.Aircraft;
import com.prodyna.pac.ars.masterdata.model.AircraftType;
import com.prodyna.pac.ars.reservation.model.Reservation;
import com.prodyna.pac.ars.reservation.model.ReservationState;
import com.prodyna.pac.ars.reservation.model.UserAircraftTypeLicense;

@RunWith(Arquillian.class)
public class ReservationStateTimerServiceTest extends SecuredTest {

	@Inject
	private ReservationService reservationService;
	
	@Inject
	private UserAircraftTypeLicenseService licenseService;
	
	@Inject
	private UserService userService;
	
	@Inject
	private AircraftService aircraftService;
	
	@Inject
	private AircraftTypeService aircraftTypeService;
	
	@Inject
	private ReservationStateTimerService stateTimer;
	
	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap
				.create(JavaArchive.class)
				.addPackages(true, "com.prodyna.pac.ars.masterdata")
				.addPackages(true, "com.prodyna.pac.ars.reservation")
				.addPackages(true, "com.prodyna.pac.ars.service")
				.addAsResource("META-INF/persistence.xml")
				.addAsResource("META-INF/ejb-jar.xml")
				.addAsResource("META-INF/jboss-ejb3.xml")
				.addAsResource("roles.properties")
				.addAsResource("users.properties")
				.addAsManifestResource(
						new StringAsset(
								"<?xml version=\"1.0\"?><beans xmlns=\"http://java.sun.com/xml/ns/javaee\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/beans_1_0.xsd\"><decorators><class>com.prodyna.pac.ars.reservation.validation.ValidationReservationServiceDecorator</class></decorators></beans>"),
						"beans.xml");
	}

	@InSequence(0)
	@Test
	public void createInitialBaseData() {
		Subject.doAs(loginContext.getSubject(), new PrivilegedAction<Void>() {
			@Override
			public Void run() {
				AircraftType at = new AircraftType();
				at.setName("A320");
				at = aircraftTypeService.addAircraftType(at);
				
				Aircraft a1 = new Aircraft();
				a1.setName("AC1");
				a1.setTailsign("A1-545");
				a1.setType(at);
				a1 = aircraftService.addAircraft(a1);
				
				Aircraft a2 = new Aircraft();
				a2.setName("AC2");
				a2.setTailsign("A1-546");
				a2.setType(at);
				a2 = aircraftService.addAircraft(a2);
				
				UserAircraftTypeLicense lic = new UserAircraftTypeLicense();
				lic.setAircraftType(a1.getType());
				lic.setUser(userService.readUserByName("admin"));
				lic.setValidFrom(new Date(System.currentTimeMillis()-50000));
				lic.setValidUntil(new Date(System.currentTimeMillis()+70000));
				licenseService.addUserAircraftTypeLicense(lic);
				
				return null;
			}
		});
	}
	
	@InSequence(1)
	@Test
	public void testStillInRESERVED() {
		Subject.doAs(loginContext.getSubject(), new PrivilegedAction<Void>() {
			@Override
			public Void run() {
				Reservation r = new Reservation();
				r.setBegin(new Date(System.currentTimeMillis()+50000));
				r.setEnd(new Date(System.currentTimeMillis() + 60000));
				r.setAircraft(aircraftService.readAircraftByName("AC1"));
				r.setUser(userService.readUserByName("admin"));

				reservationService.createReservation(r);
				
				stateTimer.updateReservationStates();
				
				Reservation reservation = reservationService.readReservation(r.getId());
				Assert.assertEquals(ReservationState.RESERVED, reservation.getState());
				return null;
			}
		});
	}
	
	@InSequence(2)
	@Test
	public void testToLENT() {
		Subject.doAs(loginContext.getSubject(), new PrivilegedAction<Void>() {
			@Override
			public Void run() {
				Reservation r = new Reservation();
				r.setBegin(new Date(System.currentTimeMillis()));
				r.setEnd(new Date(System.currentTimeMillis() + 30000));
				r.setAircraft(aircraftService.readAircraftByName("AC1"));
				r.setUser(userService.readUserByName("admin"));

				reservationService.createReservation(r);
				
				stateTimer.updateReservationStates();
				
				Reservation reservation = reservationService.readReservation(r.getId());
				Assert.assertEquals(ReservationState.LENT, reservation.getState());
				return null;
			}
		});
	}
	
	@InSequence(3)
	@Test
	public void testToRETURNED() {
		Subject.doAs(loginContext.getSubject(), new PrivilegedAction<Void>() {
			@Override
			public Void run() {
				Reservation r = new Reservation();
				r.setBegin(new Date(System.currentTimeMillis()-10000));
				r.setEnd(new Date(System.currentTimeMillis() -  5000));
				r.setAircraft(aircraftService.readAircraftByName("AC1"));
				r.setUser(userService.readUserByName("admin"));
				r.setState(ReservationState.LENT);

				reservationService.createReservation(r);
				
				stateTimer.updateReservationStates();
				
				Reservation reservation = reservationService.readReservation(r.getId());
				Assert.assertEquals(ReservationState.RETURNED, reservation.getState());
				return null;
			}
		});
	}
}
