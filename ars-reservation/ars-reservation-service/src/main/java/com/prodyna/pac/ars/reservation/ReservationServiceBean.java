package com.prodyna.pac.ars.reservation;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;

import com.prodyna.pac.ars.reservation.model.Reservation;
import com.prodyna.pac.ars.reservation.validation.ValidReservation;
import com.prodyna.pac.ars.service.ejb.ModificationType;
import com.prodyna.pac.ars.service.ejb.PerformanceMonitored;


@Stateless
@Local(ReservationService.class)
@PerformanceMonitored
public class ReservationServiceBean implements ReservationService {

	@Inject
    private Logger log;
	
	@PersistenceContext
	private EntityManager em;
	
	
	@Override
	public Reservation createReservation(@ValidReservation(ModificationType.CREATE) Reservation reservation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateReservation(@ValidReservation(ModificationType.UPDATE) Reservation reservation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelReservation(long id) {
		Reservation reservation = em.find(Reservation.class, id);
		em.remove(reservation);
		log.info("Reservation [{}] removed", id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Reservation> readAllReservations() {
		return (List<Reservation>) em.createNamedQuery("Reservation.findAll").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Reservation> readAllReservationsForUser(long userId) {
		return (List<Reservation>) em.createNamedQuery("Reservation.findByUserId").setParameter("userId", userId).getResultList();
	}


}
