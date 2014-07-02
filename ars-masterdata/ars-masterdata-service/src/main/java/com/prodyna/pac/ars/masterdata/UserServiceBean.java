package com.prodyna.pac.ars.masterdata;

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
import javax.validation.constraints.Size;

import org.slf4j.Logger;

import com.prodyna.pac.ars.masterdata.model.User;
import com.prodyna.pac.ars.service.ejb.PerformanceMonitored;
import com.prodyna.pac.ars.service.ejb.Roles;

@PerformanceMonitored
@Stateless
@DeclareRoles({ Roles.ADMIN, Roles.PILOT })
public class UserServiceBean implements UserService {

	@Inject
    private Logger log;
	
	@PersistenceContext
	private EntityManager em;
	
	
	@Override
	@RolesAllowed(Roles.ADMIN)
	public User addUser(@NotNull @Valid User user) {
		em.persist(user);
		log.info("new User [{}] with ID [{}] created", user.getName(), user.getId());
		return user;
	}

	
	@Override
	@RolesAllowed(Roles.PILOT)
	public @NotNull List<User> readAllUsers() {
		this.log.debug("Loading list of all Users");
		return (List<User>) em.createNamedQuery("User.findAll", User.class).getResultList();
	}
	
	@Override
	@RolesAllowed(Roles.ADMIN)
	public void updateUser(@NotNull @Valid User user) {
		em.merge(user);
		log.info("User [{}] with ID [{}] was updated", user.getName(), user.getId());
	}

	@Override
	@RolesAllowed(Roles.PILOT)
	public User readUser(@Min(1) long id) {
		return em.find(User.class, id);
	}

	@Override
	@RolesAllowed(Roles.ADMIN)
	public void removeUser(@Min(1) long id) {
		User userToRemove = em.find(User.class, id);
		em.remove(userToRemove);
		log.info("User [{}] removed", id);
	}
	
	@Override
	@RolesAllowed(Roles.PILOT)
	public User readUserByName(@Size(min=2) String name) {
		return (User) em.createNamedQuery("User.findByName").setParameter("name", name).getSingleResult();
	}

}
