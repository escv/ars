package com.prodyna.pac.ars.reservation;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.prodyna.pac.ars.masterdata.model.Aircraft;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Local
public interface ReservationAircraftService {

	@GET
	@Path("/aircraft/withoutregistration")
	List<Aircraft> readAircraftWithoutCurrentRegistration();
}
