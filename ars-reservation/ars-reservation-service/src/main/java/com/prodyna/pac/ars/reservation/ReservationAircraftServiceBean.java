package com.prodyna.pac.ars.reservation;

import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.prodyna.pac.ars.masterdata.model.Aircraft;
import com.prodyna.pac.ars.service.ejb.PerformanceMonitored;
import com.prodyna.pac.ars.service.ejb.Roles;


@Stateless
@PerformanceMonitored
@DeclareRoles({ Roles.ADMIN, Roles.PILOT, Roles.GUEST })
public class ReservationAircraftServiceBean implements ReservationAircraftService {
	
	@PersistenceContext
	private EntityManager em;
	
	
	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed({Roles.PILOT, Roles.GUEST})
	public List<Aircraft> readAircraftWithoutCurrentRegistration() {
		return (List<Aircraft>) em.createNamedQuery("Aircraft.findAircraftWithoutCurrentReservation").getResultList();
	}
}
