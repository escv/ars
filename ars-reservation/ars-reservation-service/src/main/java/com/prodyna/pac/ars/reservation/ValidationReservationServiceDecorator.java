package com.prodyna.pac.ars.reservation;

import java.util.List;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import com.prodyna.pac.ars.reservation.model.Reservation;

@Decorator
public class ValidationReservationServiceDecorator implements
		ReservationService {

	@Inject @Delegate @Any ReservationService delegate;
	
	@Override
	public Reservation createReservation(Reservation reservation) {
		return this.delegate.createReservation(reservation);
	}

	@Override
	public void updateReservation(Reservation reservation) {
		this.delegate.updateReservation(reservation);

	}

	@Override
	public void cancelReservation(long id) {
		this.delegate.cancelReservation(id);

	}

	@Override
	public List<Reservation> readAllReservations() {
		return this.delegate.readAllReservations();
	}

	@Override
	public List<Reservation> readAllReservationsForUser(long userId) {
		return this.delegate.readAllReservationsForUser(userId);
	}

}
