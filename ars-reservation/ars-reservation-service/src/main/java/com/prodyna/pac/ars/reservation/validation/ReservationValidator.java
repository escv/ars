package com.prodyna.pac.ars.reservation.validation;

import java.util.List;

import javax.inject.Inject;

import com.prodyna.pac.ars.masterdata.model.UserRole;
import com.prodyna.pac.ars.reservation.ReservationService;
import com.prodyna.pac.ars.reservation.UserAircraftTypeLicenseService;
import com.prodyna.pac.ars.reservation.model.Reservation;
import com.prodyna.pac.ars.reservation.model.UserAircraftTypeLicense;
import com.prodyna.pac.ars.service.ejb.Roles;
import com.prodyna.pac.ars.service.ejb.Validator;

public class ReservationValidator implements Validator<Reservation> {

	@Inject UserAircraftTypeLicenseService licenseService;
	
	private ReservationService reservationService;
	
	// CDI does not work here to to a dependencycicle
	public void setReservationService(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

	@Override
	public String validate(Reservation entry) {
		if (entry.getBegin().after(entry.getEnd())) {
			return "End Date must not before Begin Date";
		}
		if (entry.getAircraft().getId()==0) {
			return "Aircraft does not yet exists";
		}
		if (entry.getUser().getId()==0) {
			return "User does not yet exists";
		}
		
		// check if the User has a PILOT role
		boolean isPilot = false;
		for (UserRole r : entry.getUser().getRoles()) {
			if (Roles.PILOT.equals(r.getName())) {
				isPilot = true;
			}
		}
		
		if (!isPilot) {
			throw new  IllegalArgumentException("User is not a PILOT");
		}
		
		// check user aircrafttype license
		List<UserAircraftTypeLicense> licensesOfUser = licenseService.readAllLicensesForUser(entry.getUser().getId());
		boolean matchesLicense = false;
		for (UserAircraftTypeLicense license : licensesOfUser) {
			if (       license.getAircraftType().equals(entry.getAircraft().getType()) 
					&& license.getValidFrom().before(entry.getBegin()) 
					&& license.getValidUntil().after(entry.getEnd())) {
				matchesLicense = true;
				break;
			}
		}
		if (!matchesLicense) {
			return "User does not have appropriate license for aircrafttype";
		}
		
		// check if there is not a different Reservation already exists
		if (reservationService!=null) {
			List<Reservation> otherReservationsForAircraft = 
					reservationService.readAllReservationForAircraftAndDateRange(
							entry.getAircraft().getId(), entry.getBegin().getTime(), entry.getEnd().getTime());
			if (!otherReservationsForAircraft.isEmpty()) {
				throw new IllegalArgumentException("Aircraft already has a reservation for this timeslot");
			}
		}
		
		return null;
	}

}
