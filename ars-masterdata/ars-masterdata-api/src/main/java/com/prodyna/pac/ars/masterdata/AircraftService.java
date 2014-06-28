package com.prodyna.pac.ars.masterdata;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.prodyna.pac.ars.masterdata.model.Aircraft;

@Path("/aircraft")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface AircraftService {

	@POST
	Aircraft addAircraft(@NotNull @Valid Aircraft aircraft);
	
	@PUT
	void updateAircraft(@NotNull @Valid Aircraft aircraft);
	
	@GET
	@NotNull List<Aircraft> readAllAircrafts();
	
	@GET
	@Path("{id}")
	Aircraft readAircraft(@Min(1) long id);
	
	@GET
	@Path("name/{acName}")
	Aircraft readAircraftByName(@Size(min=3) String name);
	
	@DELETE
	@Path("{id}")
	void removeAircraft(@Min(1) long id);
}
