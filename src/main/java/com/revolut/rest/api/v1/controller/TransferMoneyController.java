package com.revolut.rest.api.v1.controller;
 
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.revolut.rest.api.v1.request.RequestTransfer;
import com.revolut.rest.api.v1.response.ResponseTransfer;
import com.revolut.rest.service.IMoneyTransferService;
import com.revolut.rest.service.MoneyTransferServiceImpl;
import com.revolut.rest.service.exception.AbstractTMException;

@Path("/transfer")
public class TransferMoneyController {
	
	IMoneyTransferService accountService = new MoneyTransferServiceImpl();
	
	@GET
	@Path("/ping")
	public Response teste() {
		return Response.status(200).entity("Pong").build();
	}
 
	@POST
	@Path("/")
	@Produces("application/json")
	@Consumes("application/json")
	public Response transfer(RequestTransfer transfer) {
		try {
			ResponseTransfer transferResult = accountService.transfer(transfer.getFromAccount(), transfer.getToAccount(), transfer.getAmount());
			return Response.status(200).entity(transferResult).build();
		} catch (AbstractTMException e) {
			return e.toResponse();
		}
		
	}
 
}