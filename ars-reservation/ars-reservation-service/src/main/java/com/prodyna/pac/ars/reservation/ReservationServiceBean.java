package com.prodyna.pac.ars.reservation;

import java.util.Date;
import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;

import com.prodyna.pac.ars.reservation.model.Reservation;
import com.prodyna.pac.ars.reservation.model.ReservationState;
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
		if (reservation.getId() > 0) {
			throw new IllegalArgumentException("Reservation already has a persistence ID");
		}
		log.debug("Adding license");
		reservation.setCreated(new Date());
		if (reservation.getState() == null) {
			reservation.setState(ReservationState.RESERVED);
		}
		em.persist(reservation);
		log.info("new reservation with ID [{}] for user [{}] created", reservation.getId(), 
				reservation.getUser().getName());

		return reservation;
	}

	@Override
	@PermitAll
	public Reservation readReservation(long id) {
		Reservation entity = em.find(Reservation.class, id);
		if (entity==null) {
			throw new NoResultException("No Reservation with id "+id+" exists");
		}
		return entity;
		
	}
	
	@Override
	@RolesAllowed(Roles.PILOT)
	public void updateReservation(@NotNull @Valid Reservation reservation) {
		em.merge(reservation);
		log.info("Reservation with ID [{}] was updated", reservation.getId());
	}

	@Override
	@RolesAllowed(Roles.PILOT)
	public void cancelReservation(@Min(1) long id) {
		Reservation reservation = em.find(Reservation.class, id);
		em.remove(reservation);
		log.info("Reservation [{}] removed", id);
	}

	@Override
	@PermitAll
	public @NotNull
	List<Reservation> readAllReservations() {
		return em.createNamedQuery("Reservation.findAll", Reservation.class).getResultList();
	}

	@Override
	@RolesAllowed({Roles.PILOT, Roles.ADMIN})
	public List<Reservation> readAllReservationsForUser(String userName) {
		return em.createNamedQuery("Reservation.findByUserName", Reservation.class).setParameter("userName", userName)
				.getResultList();
	}

	@Override
	public List<Reservation> readAllReservationForAircraftAndDateRange(long aircraftId, long begin, long end) {
		return em.createNamedQuery("Reservation.findByAircraftAndDateRange", Reservation.class)
				.setParameter("aircraftId", aircraftId)
				.setParameter("beginDate", new Date(begin), TemporalType.TIMESTAMP)
				.setParameter("endDate", new Date(end), TemporalType.TIMESTAMP).getResultList();
	}

}
