package com.prodyna.pac.ars.masterdata;

import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.slf4j.Logger;

import com.prodyna.pac.ars.masterdata.model.AircraftType;
import com.prodyna.pac.ars.service.ejb.PerformanceMonitored;
import com.prodyna.pac.ars.service.ejb.Roles;

@PerformanceMonitored
@Stateless
@DeclareRoles({ Roles.ADMIN, Roles.PILOT, Roles.GUEST })
public class AircraftTypeServiceBean implements AircraftTypeService {

	@Inject
    private Logger log;
	
	@PersistenceContext
	private EntityManager em;
	
	
	@Override
	@RolesAllowed(Roles.ADMIN)
	public AircraftType addAircraftType(@NotNull @Valid AircraftType aircraftType) {
		em.persist(aircraftType);
		log.info("new aircraft [{}] with ID [{}] created", aircraftType.getName(), aircraftType.getId());
		return aircraftType;
	}

	
	@Override
	@RolesAllowed({Roles.PILOT, Roles.GUEST})
	public @NotNull List<AircraftType> readAllAircraftTypes() {
		this.log.debug("Loading list of all aircrafttypes");
		return (List<AircraftType>) em.createNamedQuery("AircraftType.findAll", AircraftType.class).getResultList();
	}

	
	@Override
	@RolesAllowed(Roles.ADMIN)
	public void updateAircraftType(@NotNull @Valid AircraftType aircraftTypes) {
		em.merge(aircraftTypes);
		log.info("AircraftType [{}] with ID [{}] was updated", aircraftTypes.getName(), aircraftTypes.getId());
	}

	
	@Override
	@RolesAllowed({Roles.PILOT, Roles.GUEST})
	public AircraftType readAircraftType(@Min(1) long id) {
		return em.find(AircraftType.class, id);
	}

	
	@Override
	@RolesAllowed(Roles.ADMIN)
	public void removeAircraftType(@Min(1) long id) {
		AircraftType aircraftToRemove = em.find(AircraftType.class, id);
		em.remove(aircraftToRemove);
		log.info("AircraftType [{}] removed", id);
	}
	
	
	@Override
	@RolesAllowed({Roles.PILOT, Roles.GUEST})
	public AircraftType readAircraftTypeByName(@Size(min=3) String name) {
		return (AircraftType) em.createNamedQuery("AircraftType.findByName").setParameter("name", name).getSingleResult();
	}

}
