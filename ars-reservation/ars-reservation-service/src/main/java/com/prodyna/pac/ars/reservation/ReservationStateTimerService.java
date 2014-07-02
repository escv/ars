package com.prodyna.pac.ars.reservation;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;

@Singleton
public class ReservationStateTimerService {

	@Inject
    private Logger log;
	
	@PersistenceContext
	private EntityManager em;
	
	@Schedule(second="*/5", hour="*", persistent=false)
	public void updateReservationStates() {
		log.trace("Handling updating of Reservation states");
		
	}
	
}
