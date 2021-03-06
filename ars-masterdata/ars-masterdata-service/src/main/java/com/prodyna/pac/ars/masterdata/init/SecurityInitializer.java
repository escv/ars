package com.prodyna.pac.ars.masterdata.init;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.prodyna.pac.ars.masterdata.model.User;
import com.prodyna.pac.ars.masterdata.model.UserRole;

@Singleton
@Startup
public class SecurityInitializer {
	
	@PersistenceContext
	private EntityManager em;
		
	@PostConstruct
	public void init() {
		try {
			// pretty ugly, but this is not productive code, testing purpose
			if (em.createQuery("SELECT r.name FROM UserRole r WHERE r.name=:name").setParameter("name", "ADMIN").getResultList().size()>0){
				// nothing to do here
				return;
			}
			
			UserRole adminRole = new UserRole();
			adminRole.setName("ADMIN");
			em.persist(adminRole);
			
			UserRole pilotRole = new UserRole();
			pilotRole.setName("PILOT");
			em.persist(pilotRole);
			
			User admin = new User();
			admin.setName("admin");
			admin.setEmail("admin@locahost.com");
			admin.setPasswordDigest("e5e9fa1ba31ecd1ae84f75caaa474f3a663f05f4"); // secret in sha1
			admin.getRoles().add(adminRole);
			admin.getRoles().add(pilotRole);
			em.persist(admin);
		} catch (Exception e) {
			// mhh, maybe already run
		}
	}
	
}
