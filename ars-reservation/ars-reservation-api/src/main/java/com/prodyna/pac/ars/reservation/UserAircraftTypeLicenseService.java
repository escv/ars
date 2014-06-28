package com.prodyna.pac.ars.reservation;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.prodyna.pac.ars.reservation.model.UserAircraftTypeLicense;

@Path("/license")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface UserAircraftTypeLicenseService {

	@POST
	UserAircraftTypeLicense addLicense(UserAircraftTypeLicense license);
	
	@PUT
	void updateUserAircraftTypeLicense(UserAircraftTypeLicense license);
	
	@GET
	@Path("user/{userId}")
	List<UserAircraftTypeLicense> readAllLicensesForUser(long userID);
	
	@DELETE
	@Path("{id}")
	void removeUserAircraftTypeLicense(long id);
	
	boolean checkLicense(long userId, long aircraftType, Date dateBegin, Date dateEnd);
}
