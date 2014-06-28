package com.prodyna.pac.ars.masterdata;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.PathParam;

import org.slf4j.Logger;

import com.prodyna.pac.ars.masterdata.model.User;
import com.prodyna.pac.ars.service.ejb.PerformanceMonitored;

@PerformanceMonitored
@Stateless
@Local(UserService.class)
public class UserServiceBean implements UserService {

	@Inject
    private Logger log;
	
	@PersistenceContext
	private EntityManager em;
	
	
	@Override
	public User addUser(User user) {
		em.persist(user);
		log.info("new User [{}] with ID [{}] created", user.getName(), user.getId());
		return user;
	}

	
	@Override
	public List<User> readAllUsers() {
		this.log.debug("Loading list of all Users");
		return (List<User>) em.createQuery("select c from User c", User.class).getResultList();
	}
	
	@Override
	public void updateUser(User user) {
		em.merge(user);
		log.info("User [{}] with ID [{}] was updated", user.getName(), user.getId());
	}

	@Override
	public User readUser(@PathParam("id") long id) {
		return em.find(User.class, id);
	}

	@Override
	public void removeUser(@PathParam("id") long id) {
		User userToRemove = em.find(User.class, id);
		em.remove(userToRemove);
		log.info("User [{}] removed", id);
	}
	
	@Override
	public User readUserByName(@PathParam("userName") String name) {
		return (User) em.createNamedQuery("User.findByName").setParameter("name", name).getSingleResult();
	}

}
