package com.prodyna.pac.ars.masterdata;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.prodyna.pac.ars.masterdata.model.Aircraft;

@Stateless
@Local(AircraftService.class)
@Path("/aircraft")
public class AircraftServiceBean implements AircraftService {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Aircraft addAircraft(Aircraft aircraft) {
		em.persist(aircraft);
		return aircraft;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<Aircraft> readAllAircrafts() {
		return (List<Aircraft>) em.createQuery("select c from Aircraft c", Aircraft.class);
	}

}
