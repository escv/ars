package com.prodyna.pac.ars.reservation;

import java.util.Date;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.ars.reservation.model.Reservation;

@RunWith(Arquillian.class)
public class ReserverationServiceTest {

	@Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
        	.addPackages(true, "com.prodyna.pac.ars.masterdata")
        	.addPackages(true, "com.prodyna.pac.ars.reservation")
        	.addPackages(true, "com.prodyna.pac.ars.service")
        	.addAsResource("META-INF/persistence.xml")
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
	
	@Inject
    private ReservationService reservationService;
	
	@Test
	public void testValidReservation() {
		Reservation r = new Reservation();
		r.setBegin(new Date(System.currentTimeMillis()+1000));
		r.setEnd(new Date(System.currentTimeMillis()+5000));
		
		this.reservationService.createReservation(r);	
	}
}
