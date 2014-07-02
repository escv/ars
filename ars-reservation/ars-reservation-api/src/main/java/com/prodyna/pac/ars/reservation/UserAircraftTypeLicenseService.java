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

import com.prodyna.pac.ars.reservation.model.UserAircraftTypeLicense;

@Path("/license")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Local
public interface UserAircraftTypeLicenseService {

	@POST
	UserAircraftTypeLicense addUserAircraftTypeLicense(@NotNull @Valid UserAircraftTypeLicense license);
	
	@PUT
	void updateUserAircraftTypeLicense(@NotNull @Valid UserAircraftTypeLicense license);
	
	@GET
	@NotNull List<UserAircraftTypeLicense> readAllLicenses();
	
	@GET
	@Path("user/{userId}")
	@NotNull List<UserAircraftTypeLicense> readAllLicensesForUser(@PathParam("userId") @Min(1) long userID);
	
	@DELETE
	@Path("{id}")
	void removeUserAircraftTypeLicense(@PathParam("id") @Min(1) long id);
	
	//boolean checkLicense(long userId, long aircraftType, Date dateBegin, Date dateEnd);
}
