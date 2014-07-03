
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

import com.prodyna.pac.ars.masterdata.model.Aircraft;

/**
 * This Masterdata service allows to modify and read Aircraft objects without any business logic.
 * The service will be exposed as a JaxRS REST JSON Service.
 * 
 * @author Andre Albert
 * @since 0.0.1
 */
@Path("/aircraft")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Local
public interface AircraftService {

	/**
	 * Persists the given Aircraft.
	 * 
	 * @param aircraft not part of the persistence context to be saved within. Must not yet have a Id
	 * @return the persisted aircraft will have an Id set
	 * @since 0.0.1
	 */
	@POST
	Aircraft addAircraft(@NotNull @Valid Aircraft aircraft);
	
	/**
	 * Overrides the already persisted Aircraft with new State.
	 * 
	 * @param aircraft the entity to save
	 * @since 0.0.1
	 */
	@PUT
	void updateAircraft(@NotNull @Valid Aircraft aircraft);
	
	/**
	 * Return all Aircrafts, please use with care. Might cause performance issues on big DBs.
	 * 
	 * @return all persisted Aircraft objects from the database
	 * @since 0.0.1
	 */
	@GET
	@NotNull List<Aircraft> readAllAircrafts();
	
	/**
	 * Reads a single aircraft based on its primary key.
	 *
	 * @param id the unique identifier for the Aircraft
	 * @return a single aircraft or null if not exists
	 * @throws NoResultException if no entity exists
	 * @since 0.0.1
	 */
	@GET
	@Path("{id}")
	Aircraft readAircraft(@PathParam("id") @Min(1) long id);
	
	/**
	 * Reads a single aircraft based on its name.
	 *
	 * @param name the unique name for the Aircraft
	 * @return a single aircraft or null if not exists
	 * @throws NoResultException if no entity exists
	 * @since 0.0.1
	 */
	@GET
	@Path("name/{acName}")
	Aircraft readAircraftByName(@Size(min=3) String name);
	
	/**
	 * Deletes the aircraft.
	 * 
	 * @param id
	 * @since 0.0.1
	 */
	@DELETE
	@Path("{id}")
	void removeAircraft(@PathParam("id") @Min(1) long id);
}
