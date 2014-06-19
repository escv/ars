package com.prodyna.pac.ars.frontend.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.prodyna.pac.ars.masterdata.model.Aircraft;

public interface AircraftManagementClient {

	@GET
	@Path("/aircraft")
	@Produces(MediaType.APPLICATION_JSON)
	List<Aircraft> readAllAircrafts();
	
}
