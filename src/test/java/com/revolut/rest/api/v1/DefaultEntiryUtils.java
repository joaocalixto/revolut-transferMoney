package com.revolut.rest.api.v1;

import static com.revolut.rest.data.model.AccountFactory.creatNewAccout;

import com.revolut.rest.data.model.Account;

public class DefaultEntiryUtils {
	
	public static Account createExpectedNewAccout(String id){
		
		return creatNewAccout(id);
	}
	
}
