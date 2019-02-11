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
import com.revolut.rest.api.v1.controller.TransferMoneyController;
import com.revolut.rest.api.v1.request.RequestTransfer;

public class TransferMoneyIntegrationTest  extends JerseyTest {
	
	@Override
	protected Application configure() {
		return new ResourceConfig(TransferMoneyController.class, AccountController.class);
	}
	
	@Test
	public void givenAmIAlive_whenCorrectRequest_thenResponseIsOkAndContainsPong() {
		Response response = target("/transfer/ping").request()
				.get();

		assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());
		assertEquals("Http Content-Type should be: ", MediaType.TEXT_HTML, response.getHeaderString(HttpHeaders.CONTENT_TYPE));

		String content = response.readEntity(String.class);
		assertEquals("Content of ressponse is: ", "Pong", content);
	}
	
	@Test
	public void givenCreateAccountWithId_whenCorrectRequest_thenResponseIsOkAndContansExpectedAccount() {
		
		cleanDB();
		
		RequestTransfer giverRequestTransfer = new RequestTransfer();
		giverRequestTransfer.setAmount(200d);
		giverRequestTransfer.setFromAccount("1");
		giverRequestTransfer.setToAccount("2");
		
		Response response = target("/transfer")
								.request()
								.post(Entity.json(giverRequestTransfer));
		
		assertEquals("Http Response should be 500: ", Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
		assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON, response.getHeaderString(HttpHeaders.CONTENT_TYPE));
		
		String content = response.readEntity(String.class);
		assertEquals("Content of ressponse is: ", "{\"erroMessage\":\"Account 1 not found\",\"cause\":\"NotFoundException\"}", content);
	}
	
	@Test
	public void givenTransferMoney_whenCorrectRequest_thenResponseUpdatedAccount() {
		
		cleanDB();
		
		createAccount("1");
		createAccount("2");

		depositAmount("1", "100");
		
		RequestTransfer giverRequestTransfer = new RequestTransfer();
		giverRequestTransfer.setAmount(50d);
		giverRequestTransfer.setFromAccount("1");
		giverRequestTransfer.setToAccount("2");
		
		String transfer = target("/transfer")
								.request()
								.post(Entity.json(giverRequestTransfer), String.class);
		
		assertThat(transfer, containsString("{\"fromAccount\":{\"id\":\"1\",\"balance\":50.0},\"toAccount\":{\"id\":\"2\",\"balance\":50.0}}"));
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
