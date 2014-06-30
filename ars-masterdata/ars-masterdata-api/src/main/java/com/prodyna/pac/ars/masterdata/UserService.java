package com.prodyna.pac.ars.masterdata;

import java.util.List;

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

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface UserService {

	@POST
	User addUser(@NotNull @Valid User user);
	
	@PUT
	void updateUser(@NotNull @Valid User user);
	
	@GET
	@NotNull List<User> readAllUsers();
	
	@GET
	@Path("{id}")
	User readUser(@PathParam("id") @Min(1) long id);
	
	@DELETE
	@Path("{id}")
	void removeUser(@PathParam("id") @Min(1) long id);
	
	@GET
	@Path("name/{userName}")
	User readUserByName(@PathParam("userName") @Size(min=2) String name);
}
