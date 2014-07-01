package com.prodyna.pac.ars.masterdata;

import java.util.List;

import javax.ejb.Local;
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

@Local
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/roles")
public interface UserRoleService {

	@POST
	UserRole addUserRole(@NotNull @Valid UserRole role);
	
	@PUT
	void updateUserRole(@NotNull @Valid UserRole role);
	
	@GET
	@NotNull List<UserRole> readAllUserRoles();
	
	@GET
	@Path("{id}")
	UserRole readUserRole(@PathParam("id") @Min(1) long id);
	
	@DELETE
	@Path("{id}")
	void removeUserRole(@PathParam("id") @Min(1) long id);
	
	@GET
	@Path("name/{roleName}")
	UserRole readUserRoleByName(@PathParam("roleName") @Size(min=3) String name);
}
