package com.revolut.rest.api.v1.response;


import com.revolut.rest.data.model.Account;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseTransfer {
	
	private Account fromAccount;
	private Account toAccount;

}
