package com.prodyna.pac.ars.reservation;

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

import org.slf4j.Logger;

import com.prodyna.pac.ars.reservation.model.Reservation;
import com.prodyna.pac.ars.service.ejb.PerformanceMonitored;
import com.prodyna.pac.ars.service.ejb.Roles;


@Stateless
@PerformanceMonitored
@DeclareRoles({ Roles.ADMIN, Roles.PILOT })
public class ReservationServiceBean implements ReservationService {

	@Inject
    private Logger log;
	
	@PersistenceContext
	private EntityManager em;
	
	
	@Override
	@RolesAllowed(Roles.PILOT)
	public Reservation createReservation(@NotNull @Valid Reservation reservation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@RolesAllowed(Roles.PILOT)
	public void updateReservation(@NotNull @Valid Reservation reservation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@RolesAllowed(Roles.PILOT)
	public void cancelReservation(@Min(1) long id) {
		Reservation reservation = em.find(Reservation.class, id);
		em.remove(reservation);
		log.info("Reservation [{}] removed", id);
	}

	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed(Roles.ADMIN)
	public @NotNull List<Reservation> readAllReservations() {
		return (List<Reservation>) em.createNamedQuery("Reservation.findAll").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@RolesAllowed(Roles.PILOT)
	public List<Reservation> readAllReservationsForUser(long userId) {
		return (List<Reservation>) em.createNamedQuery("Reservation.findByUserId").setParameter("userId", userId).getResultList();
	}


}
