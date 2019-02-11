package com.revolut.rest.api.v1.controller;
 
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.revolut.rest.data.model.Account;
import com.revolut.rest.service.IStoreAccountService;
import com.revolut.rest.service.StoreAccountServiceImpl;
import com.revolut.rest.service.exception.AbstractTMException;
import com.revolut.rest.service.exception.AccountAlreadyExistsException;

import lombok.extern.java.Log;

@Log
@Path("/account")
public class AccountController {
	
	IStoreAccountService accountService;
	
	public AccountController() {
		this.accountService = new StoreAccountServiceImpl();
	}
	
	public AccountController(IStoreAccountService accountService) {
		this.accountService = accountService;
	}

	@GET
	@Path("/ping")
	public Response amIAlive() {
		log.info("amIAlive called");
		return Response.status(200).entity("Pong").build();
	}
 
	@PUT
	@Path("/create")
	@Produces("application/json")
	@Consumes("application/json")
	public Account createAccount(@PathParam("accountId") String accountId) throws AccountAlreadyExistsException {
		return accountService.create(null);
	}
	
	@PUT
	@Path("/create/{accountId}")
	@Produces("application/json")
	@Consumes("application/json")
	public Account createAccountWithId(@PathParam("accountId") String accountId) throws AccountAlreadyExistsException {
		return accountService.create(accountId);
	}
	
	@GET
	@Path("/all")
	@Produces("application/json")
	public List<Account> listAll() {
		return accountService.listAll();
	}
	
	@POST
	@Path("/withdraw")
	@Produces("application/json")
	@Consumes("application/json")
	public Response withdraw(@QueryParam("accountId") String accountId, @QueryParam("amoount") double amount) {
 
		try {
			Account withdraw = accountService.withdraw(accountId, amount);
			return Response.status(200).entity(withdraw).build();
		} catch (AbstractTMException e) {
			return e.toResponse();
		}
 
	}
	
	@POST
	@Path("/deposit")
	@Produces("application/json")
	@Consumes("application/json")
	public Response deposit(@QueryParam("accountId") String accountId, @QueryParam("amoount") double amount) {
 
		try {
			Account withdraw = accountService.deposit(accountId, amount);
			return Response.status(200).entity(withdraw).build();
		} catch (AbstractTMException e) {
			return e.toResponse();
		}
 
	}
 
}