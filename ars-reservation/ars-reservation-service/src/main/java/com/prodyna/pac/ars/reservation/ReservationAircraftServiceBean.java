package com.prodyna.pac.ars.reservation;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.prodyna.pac.ars.masterdata.model.Aircraft;
import com.prodyna.pac.ars.service.ejb.PerformanceMonitored;


@Stateless
@PerformanceMonitored
public class ReservationAircraftServiceBean implements ReservationAircraftService {
	
	@PersistenceContext
	private EntityManager em;
	
	
	@Override
	@PermitAll
	public List<Aircraft> readAircraftWithoutCurrentRegistration() {
		return em.createNamedQuery("Aircraft.findAircraftWithoutCurrentReservation", Aircraft.class).getResultList();
	}
}
