package com.prodyna.pac.ars.reservation;

import java.security.PrivilegedAction;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.auth.Subject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.ars.masterdata.AircraftService;
import com.prodyna.pac.ars.masterdata.AircraftTypeService;
import com.prodyna.pac.ars.masterdata.UserService;
import com.prodyna.pac.ars.masterdata.model.Aircraft;
import com.prodyna.pac.ars.masterdata.model.AircraftType;
import com.prodyna.pac.ars.masterdata.model.User;
import com.prodyna.pac.ars.reservation.model.Reservation;
import com.prodyna.pac.ars.reservation.model.UserAircraftTypeLicense;

@RunWith(Arquillian.class)
public class ReserverationAircraftServiceTest extends SecuredTest {

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
								"<?xml version=\"1.0\"?><beans xmlns=\"http://java.sun.com/xml/ns/javaee\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/beans_1_0.xsd\"><decorators><class>com.prodyna.pac.ars.reservation.ValidationReservationServiceDecorator</class></decorators></beans>"),
						"beans.xml");
	}

	@Inject
	private ReservationService reservationService;
	@Inject
	private ReservationAircraftService reservationAircraftService;
	@Inject
	private UserAircraftTypeLicenseService licenseService;
	@Inject
	private UserService userService;
	@Inject
	private AircraftService aircraftService;
	@Inject
	private AircraftTypeService aircraftTypeService;
	
	@PersistenceContext
	private EntityManager em;

	@Test
	public void testCurrentlyAvailableAircrafts() {
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
				
				User admin = userService.readUserByName("admin");
				UserAircraftTypeLicense lic = new UserAircraftTypeLicense();
				lic.setAircraftType(at);
				lic.setUser(admin);
				lic.setValidFrom(new Date(System.currentTimeMillis()-5000));
				lic.setValidUntil(new Date(System.currentTimeMillis()+5000));
				lic = licenseService.addUserAircraftTypeLicense(lic);
				
				Reservation r = new Reservation();
				r.setAircraft(a1);
				r.setBegin(new Date());
				r.setEnd(new Date(System.currentTimeMillis()+1000));
				r.setUser(admin);
				
				reservationService.createReservation(r);
				
				List<Aircraft> withoutCurrentRegistration = reservationAircraftService.readAircraftWithoutCurrentRegistration();
				
				Assert.assertTrue(withoutCurrentRegistration.contains(a2));
				return null;
			}
		});
	}
}
