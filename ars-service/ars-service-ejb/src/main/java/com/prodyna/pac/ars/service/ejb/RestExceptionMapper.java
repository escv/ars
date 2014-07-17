package com.prodyna.pac.ars.service.ejb;

import javax.ejb.EJBAccessException;
import javax.persistence.NoResultException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RestExceptionMapper implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception exception) {
		if (exception instanceof IllegalArgumentException) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		if (exception instanceof NoResultException) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		if (exception instanceof EJBAccessException) {
			return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}

}
