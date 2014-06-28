package com.prodyna.pac.ars.reservation.validation;

import javax.ejb.EJB;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.prodyna.pac.ars.masterdata.AircraftService;
import com.prodyna.pac.ars.masterdata.model.Aircraft;
import com.prodyna.pac.ars.reservation.ReservationService;
import com.prodyna.pac.ars.reservation.UserAircraftTypeLicenseService;
import com.prodyna.pac.ars.reservation.model.Reservation;
import com.prodyna.pac.ars.service.ejb.ModificationType;

public class ValidReservationValidator implements
		ConstraintValidator<ValidReservation, Reservation> {

	private ModificationType modificationType;
	
	@EJB private ReservationService reservationService;
	@EJB private AircraftService aircraftService;
	@EJB private UserAircraftTypeLicenseService licenseService;
	
	@Override
	public void initialize(ValidReservation validReservation) {
		this.modificationType = validReservation.value();
		
	}

	@Override
	public boolean isValid(Reservation reservation, ConstraintValidatorContext ctx) {
		if (reservation.getBegin().after(reservation.getEnd())){
			return false;
		}
		
		if (modificationType.equals(ModificationType.UPDATE)) {
			Aircraft acForReg = this.aircraftService.readAircraft(reservation.getAircraft().getId());
			if (acForReg==null) {
				return false;
			}
			
		}
		return false;
	}

}
