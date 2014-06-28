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

import com.prodyna.pac.ars.masterdata.model.AircraftType;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/aircraft-type")
public interface AircraftTypeService {

	@POST
	AircraftType addAircraftType(@NotNull @Valid AircraftType aircraftType);
	
	@PUT
	void updateAircraftType(@NotNull @Valid  AircraftType aircraft);
	
	@GET
	List<AircraftType> readAllAircraftTypes();
	
	@GET
	@Path("{id}")
	AircraftType readAircraftType(@Min(1) long id);
	
	@DELETE
	@Path("{id}")
	void removeAircraftType(@Min(1) long id);
	
	@GET
	@Path("name/{atName}")
	AircraftType readAircraftTypeByName(@Size(min=3) String name);
}
