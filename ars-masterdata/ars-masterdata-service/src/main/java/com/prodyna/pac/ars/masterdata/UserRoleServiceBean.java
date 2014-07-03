package com.prodyna.pac.ars.masterdata;

import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.slf4j.Logger;

import com.prodyna.pac.ars.masterdata.model.UserRole;
import com.prodyna.pac.ars.service.ejb.PerformanceMonitored;
import com.prodyna.pac.ars.service.ejb.Roles;

@PerformanceMonitored
@Stateless
@DeclareRoles({ Roles.ADMIN, Roles.PILOT, Roles.GUEST })
public class UserRoleServiceBean implements UserRoleService {

	@Inject
    private Logger log;
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	@RolesAllowed(Roles.ADMIN)
	public UserRole addUserRole(@NotNull @Valid UserRole UserRole) {
		em.persist(UserRole);
		log.info("new userrole [{}] with ID [{}] created", UserRole.getName(), UserRole.getId());
		return UserRole;
	}

	
	@Override
	@RolesAllowed({Roles.PILOT, Roles.GUEST})
	public @NotNull List<UserRole> readAllUserRoles() {
		this.log.debug("Loading list of all UserRoles");
		return em.createNamedQuery("UserRole.findAll", UserRole.class).getResultList();
	}

	
	@Override
	@RolesAllowed(Roles.ADMIN)
	public void updateUserRole(@NotNull @Valid UserRole UserRoles) {
		em.merge(UserRoles);
		log.info("UserRole [{}] with ID [{}] was updated", UserRoles.getName(), UserRoles.getId());
	}

	
	@Override
	@RolesAllowed({Roles.PILOT, Roles.GUEST})
	public UserRole readUserRole(@Min(1) long id) {
		UserRole entity = em.find(UserRole.class, id);
		if (entity==null) {
			throw new NoResultException("No UserRole with id "+id+" exists");
		}
		return entity;
	}

	
	@Override
	@RolesAllowed(Roles.ADMIN)
	public void removeUserRole(@Min(1) long id) {
		UserRole roleToRemove = em.find(UserRole.class, id);
		em.remove(roleToRemove);
		log.info("UserRole [{}] removed", id);
	}
	
	
	@Override
	@RolesAllowed({Roles.PILOT, Roles.GUEST})
	public UserRole readUserRoleByName(@Size(min=3) String name) {
		return em.createNamedQuery("UserRole.findByName", UserRole.class).setParameter("name", name).getSingleResult();
	}

}
