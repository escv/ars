package com.prodyna.pac.ars.reservation;

import java.security.PrivilegedAction;
import java.util.Date;

import javax.inject.Inject;
import javax.security.auth.Subject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.ars.reservation.model.Reservation;

@RunWith(Arquillian.class)
public class ReserverationServiceTest extends SecuredTest {

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

	@Test(expected=Exception.class)
	public void testValidReservation() {
		Subject.doAs(loginContext.getSubject(), new PrivilegedAction<Void>() {
			@Override
			public Void run() {
				Reservation r = new Reservation();
				r.setBegin(new Date(System.currentTimeMillis() + 1000));
				r.setEnd(new Date(System.currentTimeMillis() + 5000));

				reservationService.createReservation(r);
				return null;
			}
		});
	}
}
