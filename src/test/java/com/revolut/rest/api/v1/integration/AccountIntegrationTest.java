package com.revolut.rest.api.v1.integration;

import static com.revolut.rest.data.repository.db.AccountDAOImpl.cleanDB;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.revolut.rest.api.v1.controller.AccountController;

public class AccountIntegrationTest extends JerseyTest{

	
	private static final String ACCOUNT_ID = "1";
	private static final String INEXISTANT_ACCOUNT_ID = "2";

	@Override
	protected Application configure() {
		return new ResourceConfig(AccountController.class);
	}
	
	@Test
	public void givenAmIAlive_whenCorrectRequest_thenResponseIsOkAndContainsPong() {
		Response response = target("/account/ping").request()
				.get();

		assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());
		assertEquals("Http Content-Type should be: ", MediaType.TEXT_HTML, response.getHeaderString(HttpHeaders.CONTENT_TYPE));

		String content = response.readEntity(String.class);
		assertEquals("Content of ressponse is: ", "Pong", content);
	}

	@Test
	public void givenCreateAccountWithId_whenCorrectRequest_thenResponseIsOkAndContansExpectedAccount() {
		final String json = target("/account/create/3").request().put(Entity.json(""), String.class);

		assertThat(json, containsString("{\"id\":\"3\",\"balance\":0.0}"));
	}

	@Test
	public void givenRestartedServerListAllWithNoParams_whenCorrectRequest_thenResponseEmptyList() throws Exception {
		cleanDB();
		
		final String json = target("/account/all").request().get(String.class);

		assertThat(json, containsString("[]"));
	}
	
	@Test
	public void givenListAllWithNoParams_whenCorrectRequest_thenResponseEmptyList() throws Exception {
		
		createAccount(ACCOUNT_ID);
		
		final String json = target("/account/all").request().get(String.class);

		assertThat(json, containsString("[{\"id\":\"1\",\"balance\":0.0}]"));
	}
	
	@Test
	public void givenDeposit_whenCorrectRequest_thenResponseWithAccountWithUpdatedBalance() throws Exception {
		
		cleanDB();
		
		createAccount(ACCOUNT_ID);
		
		String deposit = target("/account/deposit")
				.queryParam("accountId", ACCOUNT_ID)
				.queryParam("amoount", "100")
				.request()
				.accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(""), String.class);
		
		assertThat(deposit, containsString("{\"id\":\"1\",\"balance\":100.0}"));
	}

	
	@Test
	public void givenDeposit_whenInexistantAccountRequest_thenResponseWithInexistantAccountError() throws Exception {
		
		cleanDB();
		
		Response response = target("/account/deposit")
				.queryParam("accountId", INEXISTANT_ACCOUNT_ID)
				.queryParam("amoount", "100")
				.request()
				.accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(""));
		
		
		assertEquals("Http Response should be 500: ", Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
		assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON, response.getHeaderString(HttpHeaders.CONTENT_TYPE));
		
		String content = response.readEntity(String.class);
		assertEquals("Content of ressponse is: ", "{\"erroMessage\":\"Account "+INEXISTANT_ACCOUNT_ID+" not found\",\"cause\":\"NotFoundException\"}", content);
	}
	
	@Test
	public void givenWithdraw_whenInexistantAccountRequest_thenResponseWithInexistantAccountError() throws Exception {
		
		cleanDB();
		
		Response response = target("/account/withdraw")
				.queryParam("accountId", INEXISTANT_ACCOUNT_ID)
				.queryParam("amoount", "100")
				.request()
				.accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(""));
		
		
		assertEquals("Http Response should be 500: ", Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
		assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON, response.getHeaderString(HttpHeaders.CONTENT_TYPE));
		
		String content = response.readEntity(String.class);
		assertEquals("Content of ressponse is: ", "{\"erroMessage\":\"Account "+INEXISTANT_ACCOUNT_ID+" not found\",\"cause\":\"NotFoundException\"}", content);
	}
	
	@Test
	public void givenWithdraw_whenCorrectAccountRequest_thenResponseInsuficientAmountError() throws Exception {
		
		cleanDB();
		
		createAccount(ACCOUNT_ID);
		
		Response response = target("/account/withdraw")
				.queryParam("accountId", ACCOUNT_ID)
				.queryParam("amoount", "100")
				.request()
				.accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(""));
		
		
		assertEquals("Http Response should be 500: ", Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
		assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON, response.getHeaderString(HttpHeaders.CONTENT_TYPE));
		
		String content = response.readEntity(String.class);
		assertEquals("Content of ressponse is: ", "{\"erroMessage\":\"Current account balance is 0.0\",\"cause\":\"InsuficientBalanceException\"}", content);
	}
	
	@Test
	public void givenWithdraw_whenCorrectAccountRequest_thenResponseAccountWithUpdatedBalece() throws Exception {

		cleanDB();

		createAccount(ACCOUNT_ID);

		depositAmount(ACCOUNT_ID, "100");
		
		String withdraw = target("/account/withdraw")
				.queryParam("accountId", ACCOUNT_ID)
				.queryParam("amoount", "20")
				.request()
				.accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(""), String.class);

		assertThat(withdraw, containsString("{\"id\":\"1\",\"balance\":80.0}"));
	}
	
	private void createAccount(String id) {
		target("/account/create/"+ id).request().put(Entity.json(""));
	}
	
	private void depositAmount(String id, String amount) {
		
		target("/account/deposit")
			.queryParam("accountId", id)
			.queryParam("amoount", amount)
			.request()
			.accept(MediaType.APPLICATION_JSON)
			.post(Entity.json(""));
	}
	
}
