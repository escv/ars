package com.prodyna.pac.ars.reservation;

import java.util.Date;
import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;

import com.prodyna.pac.ars.masterdata.model.Aircraft;
import com.prodyna.pac.ars.reservation.model.Reservation;
import com.prodyna.pac.ars.service.ejb.PerformanceMonitored;
import com.prodyna.pac.ars.service.ejb.Roles;


@Stateless
@PerformanceMonitored
@DeclareRoles({ Roles.ADMIN, Roles.PILOT, Roles.GUEST })
public class ReservationAircraftServiceBean implements ReservationAircraftService {

	@Inject
    private Logger log;
	
	@PersistenceContext
	private EntityManager em;
	
	
	@Override
	@RolesAllowed({Roles.PILOT, Roles.GUEST})
	public List<Aircraft> readAircraftWithoutCurrentRegistration() {
		return (List<Aircraft>) em.createNamedQuery("Aircraft.findAircraftWithoutCurrentReservation").getResultList();
	}
}
