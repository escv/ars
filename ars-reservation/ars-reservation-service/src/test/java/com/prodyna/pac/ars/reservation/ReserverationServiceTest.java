package com.prodyna.pac.ars.reservation;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJBException;
import javax.inject.Inject;

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
import com.prodyna.pac.ars.masterdata.UserRoleService;
import com.prodyna.pac.ars.masterdata.UserService;
import com.prodyna.pac.ars.masterdata.model.Aircraft;
import com.prodyna.pac.ars.masterdata.model.AircraftType;
import com.prodyna.pac.ars.masterdata.model.User;
import com.prodyna.pac.ars.masterdata.model.UserRole;
import com.prodyna.pac.ars.reservation.model.Reservation;
import com.prodyna.pac.ars.reservation.model.UserAircraftTypeLicense;

@RunWith(Arquillian.class)
public class ReserverationServiceTest extends SecuredTest {

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
	private UserRoleService userRoleService;

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
	}

	@InSequence(1)
	@Test(expected = EJBException.class)
	public void testInvalidDateRange() {
		Reservation r = new Reservation();
		r.setBegin(new Date(System.currentTimeMillis() + 50000));
		r.setEnd(new Date(System.currentTimeMillis() + 10000));
		r.setAircraft(aircraftService.readAircraftByName("AC1"));
		r.setUser(userService.readUserByName("admin"));

		reservationService.createReservation(r);
	}

	@InSequence(2)
	@Test(expected = EJBException.class)
	public void testNoPersistCascade() {
		Reservation r = new Reservation();
		r.setBegin(new Date(System.currentTimeMillis() + 10000));
		r.setEnd(new Date(System.currentTimeMillis() + 30000));
		r.setAircraft(new Aircraft());
		r.setUser(new User());

		reservationService.createReservation(r);
	}

	@InSequence(3)
	@Test(expected = EJBException.class)
	public void testUserHasNoLicense() {
		Reservation r = new Reservation();
		r.setBegin(new Date(System.currentTimeMillis() + 10000));
		r.setEnd(new Date(System.currentTimeMillis() + 30000));
		r.setAircraft(aircraftService.readAircraftByName("AC1"));
		r.setUser(userService.readUserByName("admin"));

		reservationService.createReservation(r);
	}

	@InSequence(4)
	@Test(expected = EJBException.class)
	public void testUserHasNoValidLicense() {
		Reservation r = new Reservation();
		r.setBegin(new Date(System.currentTimeMillis() + 10000));
		r.setEnd(new Date(System.currentTimeMillis() + 30000));
		Aircraft aircraft = aircraftService.readAircraftByName("AC1");
		r.setAircraft(aircraft);
		User user = userService.readUserByName("admin");
		r.setUser(user);

		UserAircraftTypeLicense lic = new UserAircraftTypeLicense();
		lic.setAircraftType(aircraft.getType());
		lic.setUser(user);
		lic.setValidFrom(new Date(System.currentTimeMillis() - 50000));
		lic.setValidUntil(new Date(System.currentTimeMillis() - 30000));
		licenseService.addUserAircraftTypeLicense(lic);

		reservationService.createReservation(r);
	}

	@InSequence(5)
	@Test
	public void testValidReservation() {
		Aircraft aircraft = aircraftService.readAircraftByName("AC1");
		User user = userService.readUserByName("admin");

		UserAircraftTypeLicense lic = new UserAircraftTypeLicense();
		lic.setAircraftType(aircraft.getType());
		lic.setUser(user);
		lic.setValidFrom(new Date(System.currentTimeMillis() - 500000));
		lic.setValidUntil(new Date(System.currentTimeMillis() + 600000));
		licenseService.addUserAircraftTypeLicense(lic);

		Reservation r = new Reservation();
		r.setBegin(new Date(System.currentTimeMillis() + 100000));
		r.setEnd(new Date(System.currentTimeMillis() + 300000));
		r.setAircraft(aircraft);
		r.setUser(user);

		reservationService.createReservation(r);
	}

	@InSequence(6)
	@Test(expected = EJBException.class)
	public void testAlreadyReserved() {
		Aircraft aircraft = aircraftService.readAircraftByName("AC1");
		User user2 = new User();
		user2.setEmail("user2@test.de");
		user2.setName("user2");
		user2.setPasswordDigest("e5e9fa1ba31ecd1ae84f75caaa474f3a663f05f4");
		Set<UserRole> roles = new HashSet<>();
		roles.add(userRoleService.readUserRoleByName("PILOT"));
		user2.setRoles(roles);
		user2 = userService.addUser(user2);

		UserAircraftTypeLicense lic = new UserAircraftTypeLicense();
		lic.setAircraftType(aircraft.getType());
		lic.setUser(user2);
		lic.setValidFrom(new Date(System.currentTimeMillis() - 500000));
		lic.setValidUntil(new Date(System.currentTimeMillis() + 600000));
		licenseService.addUserAircraftTypeLicense(lic);

		Reservation r = new Reservation();
		r.setBegin(new Date(System.currentTimeMillis() + 100000));
		r.setEnd(new Date(System.currentTimeMillis() + 300000));
		r.setUser(user2);
		r.setAircraft(aircraft);

		reservationService.createReservation(r);
	}
}
