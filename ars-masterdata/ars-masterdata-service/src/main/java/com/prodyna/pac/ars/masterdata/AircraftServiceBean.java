package com.prodyna.pac.ars.masterdata;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.prodyna.pac.ars.masterdata.model.Aircraft;

@Stateless
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
	@Override
	public List<Aircraft> readAllAircrafts() {
		return (List<Aircraft>) em.createQuery("select c from Aircraft c", Aircraft.class);
	}

}
