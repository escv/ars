package com.prodyna.pac.ars.reservation;

import java.util.List;

import javax.inject.Inject;

import com.prodyna.pac.ars.reservation.model.Reservation;
import com.prodyna.pac.ars.reservation.model.UserAircraftTypeLicense;
import com.prodyna.pac.ars.service.ejb.Validator;

public class ReservationValidator implements Validator<Reservation> {

	@Inject UserAircraftTypeLicenseService licenseService;
	
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
		
		// TODO check if aircraft is available for this timeslot

		return null;
	}

}
