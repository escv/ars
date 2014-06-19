package com.prodyna.pac.ars.reservation;

import java.util.Date;
import java.util.List;

import com.prodyna.pac.ars.reservation.model.UserAircraftTypeLicense;

public interface UserAircraftTypeLicenseService {

	UserAircraftTypeLicense addLicense(UserAircraftTypeLicense license);
	
	void updateUserAircraftTypeLicense(UserAircraftTypeLicense license);
	
	List<UserAircraftTypeLicense> readAllLicensesForUser(long userID);
	
	void removeUserAircraftTypeLicense(long id);
	
	boolean checkLicense(long userId, long aircraftType, Date dateBegin, Date dateEnd);
}
