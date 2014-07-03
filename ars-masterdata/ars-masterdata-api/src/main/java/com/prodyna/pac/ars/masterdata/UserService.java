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

import com.prodyna.pac.ars.masterdata.model.User;

/**
 * This Masterdata service allows to modify and read User objects without any business logic.
 * The service will be exposed as a JaxRS REST JSON Service.
 * 
 * @author Andre Albert
 * @since 0.0.1
 */
@Local
@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface UserService {

	/**
	 * Persists the given User.
	 * 
	 * @param user not part of the persistence context to be saved within. Must not yet have a Id
	 * @return the persisted User will have an Id set
	 * @since 0.0.1
	 */
	@POST
	User addUser(@NotNull @Valid User user);
	
	/**
	 * Overrides the already persisted User with new State.
	 * 
	 * @param aircraft the entity to save
	 * @since 0.0.1
	 */
	@PUT
	void updateUser(@NotNull @Valid User user);
	
	/**
	 * Return all Users, please use with care. Might cause performance issues on big DBs.
	 * 
	 * @return all persisted User objects from the database
	 * @since 0.0.1
	 */
	@GET
	@NotNull List<User> readAllUsers();
	
	/**
	 * Reads a single user based on its primary key.
	 * 
	 * @param id the unique identifier for the User
	 * @return a single user or null if not exists
	 * @throws NoResultException if no entity exists
	 * @since 0.0.1
	 */
	@GET
	@Path("{id}")
	User readUser(@PathParam("id") @Min(1) long id);
	
	/**
	 * Deletes the user.
	 * 
	 * @param id
	 * @since 0.0.1
	 */
	@DELETE
	@Path("{id}")
	void removeUser(@PathParam("id") @Min(1) long id);
	
	/**
	 * Reads a single user based on its name.
	 * 
	 * @param id the unique name for the User
	 * @return a single user or null if not exists
	 * @throws NoResultException if no entity exists
	 * @since 0.0.1
	 */
	@GET
	@Path("name/{userName}")
	User readUserByName(@PathParam("userName") @Size(min=2) String name);
}
