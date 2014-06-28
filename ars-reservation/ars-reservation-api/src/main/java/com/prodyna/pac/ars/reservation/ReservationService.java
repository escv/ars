package com.prodyna.pac.ars.reservation;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.prodyna.pac.ars.reservation.model.Reservation;

@Path("/license")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ReservationService {

	@POST
	Reservation createReservation(Reservation reservation);
	
	@PUT
	void updateReservation(Reservation reservation);
	
	@DELETE
	@Path("{id}")
	void cancelReservation(@PathParam("id") long id);
	
	@GET
	List<Reservation> readAllReservations();
	
	@GET
	@Path("user/{userId}")
	List<Reservation> readAllReservationsForUser(@PathParam("userId") long userId);
	
}
