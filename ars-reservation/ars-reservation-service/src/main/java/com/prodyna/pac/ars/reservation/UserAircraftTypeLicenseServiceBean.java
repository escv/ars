package com.prodyna.pac.ars.reservation;

import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.PathParam;

import org.slf4j.Logger;

import com.prodyna.pac.ars.reservation.model.UserAircraftTypeLicense;
import com.prodyna.pac.ars.service.ejb.PerformanceMonitored;
import com.prodyna.pac.ars.service.ejb.Roles;

@PerformanceMonitored
@Stateless
@DeclareRoles({ Roles.ADMIN, Roles.PILOT })
public class UserAircraftTypeLicenseServiceBean implements
		UserAircraftTypeLicenseService {

	@Inject
    private Logger log;
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	@RolesAllowed(Roles.ADMIN)
	public UserAircraftTypeLicense addUserAircraftTypeLicense(@NotNull @Valid UserAircraftTypeLicense license) {
		if (license.getId()>0) {
			throw new IllegalArgumentException("License already has a persistence ID");
		}
		log.debug("Adding license");
		em.persist(license);
		log.info("new license with ID [{}] for user [{}] created", license.getId(), license.getUser().getName());
		
		return license;
	}

	@Override
	@RolesAllowed(Roles.ADMIN)
	public void updateUserAircraftTypeLicense(@NotNull @Valid UserAircraftTypeLicense license) {
		em.merge(license);
		log.info("License with ID [{}] was updated", license.getId());
	}

	
	
	@Override
	@SuppressWarnings("unchecked")
	@RolesAllowed(Roles.PILOT)
	public @NotNull List<UserAircraftTypeLicense> readAllLicensesForUser(@Min(1) long userId) {
		return (List<UserAircraftTypeLicense>) em.createNamedQuery("License.findByUserId").setParameter("userId", userId).getResultList();
	}

	
	@Override
	@RolesAllowed(Roles.ADMIN)
	public void removeUserAircraftTypeLicense(@PathParam("id") @Min(1) long id) {
		UserAircraftTypeLicense licenseToRemove = em.find(UserAircraftTypeLicense.class, id);
		em.remove(licenseToRemove);
		log.info("UserAircraftTypeLicense [{}] removed", id);
	}
/*
	@Override
	public boolean checkLicense(long userId, long aircraftType, Date dateBegin,
			Date dateEnd) {
		return false;
	}
*/
	@Override
	@RolesAllowed(Roles.ADMIN)
	public List<UserAircraftTypeLicense> readAllLicenses() {
		this.log.debug("Loading list of all Licenses");
		return (List<UserAircraftTypeLicense>) em.createNamedQuery("License.findAll", UserAircraftTypeLicense.class).getResultList();
	}

}
