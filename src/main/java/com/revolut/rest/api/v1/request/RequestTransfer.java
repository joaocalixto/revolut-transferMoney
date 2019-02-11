package com.revolut.rest.api.v1.request;

import lombok.Data;

@Data
public class RequestTransfer {
	
	private String fromAccount;
	private String toAccount;
	private Double amount;

}
