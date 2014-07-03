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

import com.prodyna.pac.ars.masterdata.AircraftTypeService;
import com.prodyna.pac.ars.masterdata.UserRoleService;
import com.prodyna.pac.ars.masterdata.UserService;
import com.prodyna.pac.ars.masterdata.model.AircraftType;
import com.prodyna.pac.ars.masterdata.model.User;
import com.prodyna.pac.ars.masterdata.model.UserRole;
import com.prodyna.pac.ars.reservation.model.UserAircraftTypeLicense;

@RunWith(Arquillian.class)
public class UserAircraftTypeLicenseServiceTest extends SecuredTest {

	@Inject
	private UserAircraftTypeLicenseService licenseService;

	@Inject
	private UserService userService;

	@Inject
	private UserRoleService userRoleService;

	@Inject
	private AircraftTypeService aircraftTypeService;

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
								"<?xml version=\"1.0\"?><beans xmlns=\"http://java.sun.com/xml/ns/javaee\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/beans_1_0.xsd\"><decorators><class>com.prodyna.pac.ars.reservation.validation.ValidationLicenseServiceDecorator</class></decorators></beans>"),
						"beans.xml");
	}

	@InSequence(0)
	@Test(expected = EJBException.class)
	public void testNoPersistCascade() {
		UserAircraftTypeLicense lic = new UserAircraftTypeLicense();
		lic.setUser(userService.readUserByName("admin"));
		lic.setValidFrom(new Date(System.currentTimeMillis() - 20000));
		lic.setValidUntil(new Date(System.currentTimeMillis() + 20000));
		lic.setAircraftType(new AircraftType());

		licenseService.addUserAircraftTypeLicense(lic);
	}

	@InSequence(1)
	@Test(expected = EJBException.class)
	public void testInvalidDateRange() {
		AircraftType at = new AircraftType();
		at.setName("A320");
		at = aircraftTypeService.addAircraftType(at);

		UserAircraftTypeLicense lic = new UserAircraftTypeLicense();
		lic.setUser(userService.readUserByName("admin"));
		lic.setValidFrom(new Date(System.currentTimeMillis() - 10000));
		lic.setValidUntil(new Date(System.currentTimeMillis() - 20000));
		lic.setAircraftType(at);

		licenseService.addUserAircraftTypeLicense(lic);
	}

	@InSequence(2)
	@Test(expected = EJBException.class)
	public void testUserIsNoPilot() {
		User user2 = new User();
		user2.setEmail("user2@test.de");
		user2.setName("user2");
		user2.setPasswordDigest("e5e9fa1ba31ecd1ae84f75caaa474f3a663f05f4");
		Set<UserRole> roles = new HashSet<>();
		roles.add(userRoleService.readUserRoleByName("ADMIN"));
		user2.setRoles(roles);
		user2 = userService.addUser(user2);

		UserAircraftTypeLicense lic = new UserAircraftTypeLicense();
		lic.setUser(user2);
		lic.setValidFrom(new Date(System.currentTimeMillis() - 20000));
		lic.setValidUntil(new Date(System.currentTimeMillis() + 20000));
		lic.setAircraftType(aircraftTypeService.readAircraftTypeByName("A320"));

		licenseService.addUserAircraftTypeLicense(lic);
	}

	@InSequence(3)
	@Test
	public void testValidLicense() {
		UserAircraftTypeLicense lic = new UserAircraftTypeLicense();
		lic.setUser(userService.readUserByName("admin"));
		lic.setValidFrom(new Date(System.currentTimeMillis() - 10000));
		lic.setValidUntil(new Date(System.currentTimeMillis() + 20000));
		lic.setAircraftType(aircraftTypeService.readAircraftTypeByName("A320"));

		licenseService.addUserAircraftTypeLicense(lic);
	}

}
