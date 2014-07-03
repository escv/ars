package com.prodyna.pac.ars.masterdata;

import java.util.List;

import javax.ejb.Local;
import javax.persistence.NoResultException;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.prodyna.pac.ars.masterdata.model.UserRole;

/**
 * This Masterdata service allows to modify and read UserRole objects without any business logic.
 * The service will be exposed as a JaxRS REST JSON Service.
 * 
 * @author Andre Albert
 * @since 0.0.1
 */
@Local
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/roles")
public interface UserRoleService {

	/**
	 * Persists the given role.
	 * 
	 * @param role not part of the persistence context to be saved within. Must not yet have a Id
	 * @return the persisted UserRole will have an Id set
	 * @since 0.0.1
	 */
	@POST
	UserRole addUserRole(@NotNull @Valid UserRole role);
	
	/**
	 * Overrides the already persisted UserRole with new State.
	 * 
	 * @param aircraft the entity to save
	 * @since 0.0.1
	 */
	@PUT
	void updateUserRole(@NotNull @Valid UserRole role);
	
	/**
	 * Return all UserRoles, please use with care. Might cause performance issues on big DBs.
	 * 
	 * @return all persisted UserRole objects from the database
	 * @since 0.0.1
	 */
	@GET
	@NotNull List<UserRole> readAllUserRoles();
	
	/**
	 * Reads a single userrole based on its primary key.
	 * 
	 * @param id the unique identifier for the UserRole
	 * @return a single userrole or null if not exists
	 * @throws NoResultException if no entity exists
	 * @since 0.0.1
	 */
	@GET
	@Path("{id}")
	UserRole readUserRole(@PathParam("id") @Min(1) long id);
	
	/**
	 * Deletes the user role.
	 * 
	 * @param id
	 * @since 0.0.1
	 */
	@DELETE
	@Path("{id}")
	void removeUserRole(@PathParam("id") @Min(1) long id);
	
	/**
	 * Reads a single userrole based on its name.
	 * 
	 * @param id the unique name for the UserRole
	 * @return a single userrole or null if not exists
	 * @throws NoResultException if no entity exists
	 * @since 0.0.1
	 */
	@GET
	@Path("name/{roleName}")
	UserRole readUserRoleByName(@PathParam("roleName") @Size(min=3) String name);
}
