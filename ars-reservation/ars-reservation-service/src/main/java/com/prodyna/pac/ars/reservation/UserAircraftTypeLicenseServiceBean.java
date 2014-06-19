package com.prodyna.pac.ars.reservation;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;

import com.prodyna.pac.ars.reservation.model.UserAircraftTypeLicense;
import com.prodyna.pac.ars.service.ejb.PerformanceMonitored;

@PerformanceMonitored
@Stateless
@Local(UserAircraftTypeLicenseService.class)
@Path("/license")
public class UserAircraftTypeLicenseServiceBean implements
		UserAircraftTypeLicenseService {

	@Inject
    private Logger log;
	
	@PersistenceContext
	private EntityManager em;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public UserAircraftTypeLicense addLicense(UserAircraftTypeLicense license) {
		if (license.getId()>0) {
			throw new IllegalArgumentException("License already has a persistence ID");
		}
		log.debug("Adding license");
		em.persist(license);
		log.info("new license with ID [{}] for user [{}] created", license.getId(), license.getUser().getName());
		
		return license;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public void updateUserAircraftTypeLicense(UserAircraftTypeLicense license) {
		em.merge(license);
		log.info("License with ID [{}] was updated", license.getId());
	}

	
	@GET
	@Path("user/{userId}")
	@Override
	@SuppressWarnings("unchecked")
	public List<UserAircraftTypeLicense> readAllLicensesForUser(@PathParam("userId") long userId) {
		return (List<UserAircraftTypeLicense>) em.createNamedQuery("License.findByUserId").setParameter("userId", userId).getResultList();
	}

	@DELETE
	@Path("{id}")
	@Override
	public void removeUserAircraftTypeLicense(@PathParam("id") long id) {
		UserAircraftTypeLicense licenseToRemove = em.find(UserAircraftTypeLicense.class, id);
		em.remove(licenseToRemove);
		log.info("UserAircraftTypeLicense [{}] removed", id);
	}

	@Override
	public boolean checkLicense(long userId, long aircraftType, Date dateBegin,
			Date dateEnd) {
		// TODO Auto-generated method stub
		return false;
	}

}
