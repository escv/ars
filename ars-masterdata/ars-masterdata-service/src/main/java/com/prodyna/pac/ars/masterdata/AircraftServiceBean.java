package com.prodyna.pac.ars.masterdata;

import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.slf4j.Logger;

import com.prodyna.pac.ars.masterdata.model.Aircraft;
import com.prodyna.pac.ars.service.ejb.PerformanceMonitored;
import com.prodyna.pac.ars.service.ejb.Roles;

@PerformanceMonitored
@Stateless
@DeclareRoles({ Roles.ADMIN, Roles.PILOT, Roles.GUEST })
public class AircraftServiceBean implements AircraftService {

	@Inject
    private Logger log;
	
	@PersistenceContext
	private EntityManager em;
	
	@Resource
    private SessionContext sessionContext;
	
	@Override
	@RolesAllowed(Roles.ADMIN)
	public Aircraft addAircraft(@NotNull @Valid Aircraft aircraft) {
		if (aircraft.getId()>0) {
			throw new IllegalArgumentException("Aircraft already has a persistence ID");
		}

		em.persist(aircraft);
		log.info("new aircraft [{}] with ID [{}] created", aircraft.getName(), aircraft.getId());
		return aircraft;
	}

	@Override
	@RolesAllowed({Roles.PILOT, Roles.GUEST})
	public @NotNull List<Aircraft> readAllAircrafts() {
		this.log.debug("Loading list of all aircrafts");
		return em.createNamedQuery("Aircraft.findAll", Aircraft.class).getResultList();
	}


	@Override
	@RolesAllowed(Roles.ADMIN)
	public void updateAircraft(@NotNull @Valid Aircraft aircraft) {
		em.merge(aircraft);
		log.info("Aircraft [{}] with ID [{}] was updated", aircraft.getName(), aircraft.getId());
	}

	@Override
	@RolesAllowed({Roles.PILOT, Roles.GUEST})
	public Aircraft readAircraft(@Min(1) long id) {
		Aircraft entity = em.find(Aircraft.class, id);
		if (entity==null) {
			throw new NoResultException("No Aircraft with id "+id+" exists");
		}
		return entity;
	}

	@Override
	@RolesAllowed(Roles.ADMIN)
	public void removeAircraft(@Min(1) long id) {
		Aircraft aircraftToRemove = em.find(Aircraft.class, id);
		em.remove(aircraftToRemove);
		log.info("Aircraft [{}] removed", id);
	}

	
	@Override
	@RolesAllowed({Roles.PILOT, Roles.GUEST})
	public Aircraft readAircraftByName(@Size(min=3) String name) {
		return em.createNamedQuery("Aircraft.findByName", Aircraft.class).setParameter("name", name).getSingleResult();
	}

}
