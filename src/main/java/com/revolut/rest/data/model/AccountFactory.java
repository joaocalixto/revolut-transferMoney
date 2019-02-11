package com.revolut.rest.data.model;

import com.revolut.rest.data.util.IdGenerator;

public class AccountFactory {
	
	public static Account creatNewAccout(String id){
		return Account.builder()
				.id(id == null ? IdGenerator.generateID() : id)
				.balance(0.0d)
				.build();
	}

}
