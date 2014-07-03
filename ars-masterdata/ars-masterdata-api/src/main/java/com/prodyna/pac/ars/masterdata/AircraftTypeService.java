package com.prodyna.pac.ars.masterdata;

import java.util.List;

import javax.ejb.Local;
import javax.persistence.NoResultException;
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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.prodyna.pac.ars.masterdata.model.AircraftType;

/**
 * This Masterdata service allows to modify and read AircraftType objects without any business logic.
 * The service will be exposed as a JaxRS REST JSON Service.
 * 
 * @author Andre Albert
 * @since 0.0.1
 */
@Local
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/aircraft-type")
public interface AircraftTypeService {

	/**
	 * Persists the given AircraftType.
	 * 
	 * @param aircraftType not part of the persistence context to be saved within. Must not yet have a Id
	 * @return the persisted aircraftType will have an Id set
	 * @since 0.0.1
	 */
	@POST
	AircraftType addAircraftType(@NotNull @Valid AircraftType aircraftType);
	
	/**
	 * Overrides the already persisted AircraftType with new State.
	 * 
	 * @param aircraftType the entity to save
	 * @since 0.0.1
	 */
	@PUT
	void updateAircraftType(@NotNull @Valid AircraftType aircraft);
	
	/**
	 * Return all AircraftTypes, please use with care. Might cause performance issues on big DBs.
	 * 
	 * @return all persisted AircraftType objects from the database
	 * @since 0.0.1
	 */
	@GET
	@NotNull List<AircraftType> readAllAircraftTypes();
	
	/**
	 * Reads a single aircrafttype based on its primary key.
	 * 
	 * @param id the unique identifier for the AircraftType
	 * @return a single aircrafttype or null if not exists
	 * @throws NoResultException if no entity exists
	 * @since 0.0.1
	 */
	@GET
	@Path("{id}")
	AircraftType readAircraftType(@PathParam("id") @Min(1) long id);
	
	/**
	 * Deletes the aircrafttype.
	 * 
	 * @param id
	 * @since 0.0.1
	 */
	@DELETE
	@Path("{id}")
	void removeAircraftType(@PathParam("id") @Min(1) long id);
	
	/**
	 * Reads a single aircrafttype based on its name.
	 * 
	 * @param id the unique name for the AircraftType
	 * @return a single aircrafttype or null if not exists
	 * @throws NoResultException if no entity exists
	 * @since 0.0.1
	 */
	@GET
	@Path("name/{atName}")
	AircraftType readAircraftTypeByName(@PathParam("atName") @Size(min=3) String name);
}
