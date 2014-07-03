package com.prodyna.pac.ars.reservation;

import java.util.List;

import javax.ejb.Local;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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

@Path("/reservation")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Local
public interface ReservationService {

	@POST
	Reservation createReservation(@NotNull @Valid Reservation reservation);
	
	@PUT
	void updateReservation(@NotNull @Valid Reservation reservation);
	
	@DELETE
	@Path("{id}")
	void cancelReservation(@PathParam("id") @Min(1) long id);
	
	@GET
	@Path("{id}")
	Reservation readReservation(@PathParam("id") @Min(1) long id);
	
	@GET
	@NotNull List<Reservation> readAllReservations();
	
	@GET
	@Path("user/{userName}")
	List<Reservation> readAllReservationsForUser(@PathParam("userName") String userName);
	
	@GET
	@Path("aircraft/{aircraftId}/begin/{begin}/end/{end}")
	List<Reservation> readAllReservationForAircraftAndDateRange(@PathParam("aircraftId") long aircraftId, @PathParam("begin") long begin, @PathParam("end") long end);
	
}
