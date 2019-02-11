package com.revolut.rest.service.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import com.google.gson.Gson;
import com.revolut.rest.api.v1.response.ExceptionResponse;

public class AbstractTMException extends Exception implements ExceptionMapper<AbstractTMException>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1573297405568567817L;

	public AbstractTMException(String format) {
		super(format);
	}

	public Response toResponse() {
		
		Gson gson = new Gson();

		return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
				.entity(gson.toJson(ExceptionResponse.builder()
						.cause(this.getClass().getSimpleName())
						.erroMessage(this.getMessage())))
				.type(MediaType.APPLICATION_JSON)
				.build();
	}

	@Override
	public Response toResponse(AbstractTMException exception) {
		Gson gson = new Gson();

		return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
				.entity(gson.toJson(ExceptionResponse.builder()
						.cause(this.getClass().getSimpleName())
						.erroMessage(this.getMessage())))
				.type(MediaType.APPLICATION_JSON)
				.build();
	}

}
