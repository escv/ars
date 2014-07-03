package com.prodyna.pac.ars.reservation.validation;

import com.prodyna.pac.ars.masterdata.model.UserRole;
import com.prodyna.pac.ars.reservation.model.UserAircraftTypeLicense;
import com.prodyna.pac.ars.service.ejb.Roles;
import com.prodyna.pac.ars.service.ejb.Validator;

public class LicenseValidator implements Validator<UserAircraftTypeLicense> {

	@Override
	public String validate(UserAircraftTypeLicense entry) {
		if (entry.getValidFrom().after(entry.getValidUntil())) {
			return "ValidFrom must not be after ValidUntil";
		}
		if (entry.getAircraftType().getId()==0) {
			return "AircraftType does not yet exists";
		}
		if (entry.getUser().getId()==0) {
			return "User does not yet exists";
		}
		
		// user must be a pilot
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
		
		return null;
	}

}
