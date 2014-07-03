package com.prodyna.pac.ars.reservation.validation;

import java.util.List;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import com.prodyna.pac.ars.reservation.UserAircraftTypeLicenseService;
import com.prodyna.pac.ars.reservation.model.UserAircraftTypeLicense;

@Decorator
public class ValidationLicenseServiceDecorator implements UserAircraftTypeLicenseService {

	@Inject @Delegate @Any UserAircraftTypeLicenseService delegate;
	@Inject LicenseValidator validator;
	
	@Override
	public UserAircraftTypeLicense addUserAircraftTypeLicense(UserAircraftTypeLicense license) {
		String error = validator.validate(license);

		if (error!=null && !error.isEmpty()) {
			throw new IllegalArgumentException(error);
		}
		return delegate.addUserAircraftTypeLicense(license);
	}

	@Override
	public void updateUserAircraftTypeLicense(UserAircraftTypeLicense license) {
		String error = validator.validate(license);

		if (error!=null && !error.isEmpty()) {
			throw new IllegalArgumentException(error);
		}
		delegate.updateUserAircraftTypeLicense(license);
	}

	@Override
	public List<UserAircraftTypeLicense> readAllLicenses() {
		return delegate.readAllLicenses();
	}

	@Override
	public List<UserAircraftTypeLicense> readAllLicensesForUser(long userID) {
		return delegate.readAllLicensesForUser(userID);
	}

	@Override
	public void removeUserAircraftTypeLicense(long id) {
		delegate.removeUserAircraftTypeLicense(id);
	}
}
