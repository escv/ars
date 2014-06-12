package com.prodyna.pac.ars.masterdata;

import java.util.List;

import com.prodyna.pac.ars.masterdata.model.UserAircraftTypeLicense;

public interface UserAircraftTypeLicenseService {

	UserAircraftTypeLicense addLicense(UserAircraftTypeLicense license);
	
	void updateUserAircraftTypeLicense(UserAircraftTypeLicense license);
	
	List<UserAircraftTypeLicense> readAllLicensesForUser(long userID);
	
	void removeUserAircraftTypeLicense(long id);
}
