package com.prodyna.pac.ars.reservation;

import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;

import com.prodyna.pac.ars.reservation.model.Reservation;
import com.prodyna.pac.ars.reservation.model.ReservationState;

@Singleton
public class ReservationStateTimerService {

	@Inject
    private Logger log;
	
	@PersistenceContext
	private EntityManager em;
	
	@Schedule(second="*/5", hour="*")
	public void updateReservationStates() {
		log.trace("Handling updating of Reservation states");
		List<Reservation> reservationsToLENT = em.createNamedQuery("Reservation.findToLENT", Reservation.class)
			.setParameter("srcState", ReservationState.RESERVED)
			.getResultList();
		for (Reservation r : reservationsToLENT) {
			r.setState(ReservationState.LENT);
			em.merge(r);
		}
		
		List<Reservation> reservationsToRETURNED = em.createNamedQuery("Reservation.findToRETURNED", Reservation.class)
				.setParameter("srcState", ReservationState.LENT)
				.getResultList();
		for (Reservation r : reservationsToRETURNED) {
			r.setState(ReservationState.RETURNED);
			em.merge(r);
		}
	}
	
}
