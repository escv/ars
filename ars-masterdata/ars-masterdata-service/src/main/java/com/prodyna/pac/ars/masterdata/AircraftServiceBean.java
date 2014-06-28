package com.prodyna.pac.ars.masterdata;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.PathParam;

import org.slf4j.Logger;

import com.prodyna.pac.ars.masterdata.model.Aircraft;
import com.prodyna.pac.ars.service.ejb.PerformanceMonitored;

@PerformanceMonitored
@Stateless
@Local(AircraftService.class)
public class AircraftServiceBean implements AircraftService {

	@Inject
    private Logger log;
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Aircraft addAircraft(Aircraft aircraft) {
		if (aircraft.getId()>0) {
			throw new IllegalArgumentException("Aircraft already has a persistence ID");
		}

		em.persist(aircraft);
		log.info("new aircraft [{}] with ID [{}] created", aircraft.getName(), aircraft.getId());
		return aircraft;
	}

	@Override
	public List<Aircraft> readAllAircrafts() {
		this.log.debug("Loading list of all aircrafts");
		return (List<Aircraft>) em.createNamedQuery("Aircraft.findAll", Aircraft.class).getResultList();
	}


	@Override
	public void updateAircraft(Aircraft aircraft) {
		em.merge(aircraft);
		log.info("Aircraft [{}] with ID [{}] was updated", aircraft.getName(), aircraft.getId());
	}

	@Override
	public Aircraft readAircraft(@PathParam("id") long id) {
		return em.find(Aircraft.class, id);
	}


	@Override
	public void removeAircraft(@PathParam("id") long id) {
		Aircraft aircraftToRemove = em.find(Aircraft.class, id);
		em.remove(aircraftToRemove);
		log.info("Aircraft [{}] removed", id);
	}

	
	@Override
	public Aircraft readAircraftByName(@PathParam("acName") String name) {
		return (Aircraft) em.createNamedQuery("Aircraft.findByName").setParameter("name", name).getSingleResult();
	}

}
