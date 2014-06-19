package com.prodyna.pac.ars.reservation;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.prodyna.pac.ars.service.ejb.PerformanceMonitored;


@Stateless
@Local(ReservationService.class)
@Path("/reservation")
@PerformanceMonitored
public class ReservationServiceBean implements ReservationService {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Override
	public String sayHello() {
		return "Hello";
	}

}
