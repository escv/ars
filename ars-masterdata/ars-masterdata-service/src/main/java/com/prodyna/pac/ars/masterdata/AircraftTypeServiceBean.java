package com.prodyna.pac.ars.masterdata;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.PathParam;

import org.slf4j.Logger;

import com.prodyna.pac.ars.masterdata.model.AircraftType;
import com.prodyna.pac.ars.service.ejb.PerformanceMonitored;

@PerformanceMonitored
@Stateless
@Local(AircraftTypeService.class)
public class AircraftTypeServiceBean implements AircraftTypeService {

	@Inject
    private Logger log;
	
	@PersistenceContext
	private EntityManager em;
	
	
	@Override
	public AircraftType addAircraftType(AircraftType aircraftType) {
		em.persist(aircraftType);
		log.info("new aircraft [{}] with ID [{}] created", aircraftType.getName(), aircraftType.getId());
		return aircraftType;
	}

	
	@Override
	public List<AircraftType> readAllAircraftTypes() {
		this.log.debug("Loading list of all aircrafttypes");
		return (List<AircraftType>) em.createQuery("select c from AircraftType c", AircraftType.class).getResultList();
	}

	
	@Override
	public void updateAircraftType(AircraftType aircraftTypes) {
		em.merge(aircraftTypes);
		log.info("AircraftType [{}] with ID [{}] was updated", aircraftTypes.getName(), aircraftTypes.getId());
	}

	
	@Override
	public AircraftType readAircraftType(@PathParam("id") long id) {
		return em.find(AircraftType.class, id);
	}

	
	@Override
	public void removeAircraftType(@PathParam("id") long id) {
		AircraftType aircraftToRemove = em.find(AircraftType.class, id);
		em.remove(aircraftToRemove);
		log.info("AircraftType [{}] removed", id);
	}
	
	
	@Override
	public AircraftType readAircraftTypeByName(@PathParam("atName") String name) {
		return (AircraftType) em.createNamedQuery("AircraftType.findByName").setParameter("name", name).getSingleResult();
	}

}
