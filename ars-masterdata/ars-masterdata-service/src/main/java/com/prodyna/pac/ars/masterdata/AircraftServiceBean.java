package com.prodyna.pac.ars.masterdata;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;

import com.prodyna.pac.ars.masterdata.model.Aircraft;
import com.prodyna.pac.ars.service.ejb.PerformanceMonitored;

@PerformanceMonitored
@Stateless
@Local(AircraftService.class)
@Path("/aircraft")
public class AircraftServiceBean implements AircraftService {

	@Inject
    private Logger log;
	
	@PersistenceContext
	private EntityManager em;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Aircraft addAircraft(Aircraft aircraft) {
		/*
		if (aircraft.getType()==null) {
			throw new IllegalArgumentException("Aircraft must have an aircraft type");
		}
		*/
		em.persist(aircraft);
		log.info("new aircraft [{}] with ID [{}] created", aircraft.getName(), aircraft.getId());
		return aircraft;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<Aircraft> readAllAircrafts() {
		this.log.debug("Loading list of all aircrafts");
		return (List<Aircraft>) em.createQuery("select c from Aircraft c", Aircraft.class).getResultList();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public void updateAircraft(Aircraft aircraft) {
		em.merge(aircraft);
		log.info("Aircraft [{}] with ID [{}] was updated", aircraft.getName(), aircraft.getId());
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Aircraft readAircraft(@PathParam("id") long id) {
		return em.find(Aircraft.class, id);
	}

	@DELETE
	@Path("{id}")
	@Override
	public void removeAircraft(@PathParam("id") long id) {
		Aircraft aircraftToRemove = em.find(Aircraft.class, id);
		em.remove(aircraftToRemove);
		log.info("Aircraft [{}] removed", id);
	}

}
