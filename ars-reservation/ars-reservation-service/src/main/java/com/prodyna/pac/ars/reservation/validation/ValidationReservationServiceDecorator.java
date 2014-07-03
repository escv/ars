package com.prodyna.pac.ars.reservation.validation;

import java.util.List;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import com.prodyna.pac.ars.reservation.ReservationService;
import com.prodyna.pac.ars.reservation.model.Reservation;

@Decorator
public class ValidationReservationServiceDecorator implements
		ReservationService {

	@Inject @Delegate @Any ReservationService delegate;
	@Inject ReservationValidator validator;

	@Override
	public Reservation createReservation(Reservation reservation) {
		validator.setReservationService(delegate);
		String error = validator.validate(reservation);

		if (error!=null && !error.isEmpty()) {
			throw new IllegalArgumentException(error);
		}
		return this.delegate.createReservation(reservation);
	}

	@Override
	public void updateReservation(Reservation reservation) {
		if (reservation.getEnd().before(reservation.getBegin())) {
			throw new IllegalArgumentException("End date must not before Begin date");
		}
		validator.setReservationService(delegate);
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
	public List<Reservation> readAllReservationsForUser(String userName) {
		return this.delegate.readAllReservationsForUser(userName);
	}

	@Override
	public List<Reservation> readAllReservationForAircraftAndDateRange(long aircraftId, long begin, long end) {
		return this.delegate.readAllReservationForAircraftAndDateRange(aircraftId, begin, end);
	}
	
	@Override
	public Reservation readReservation(long id) {
		return delegate.readReservation(id);
	}

}
