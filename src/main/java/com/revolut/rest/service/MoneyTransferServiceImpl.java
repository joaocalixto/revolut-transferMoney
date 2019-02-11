package com.revolut.rest.service;

import com.revolut.rest.api.v1.response.ResponseTransfer;
import com.revolut.rest.data.model.Account;
import com.revolut.rest.service.exception.InsuficientBalanceException;
import com.revolut.rest.service.exception.NotFoundException;

public class MoneyTransferServiceImpl implements IMoneyTransferService {
	
	IStoreAccountService accountService = new StoreAccountServiceImpl();


	public ResponseTransfer transfer(String accountIdFrom, String accountIdTo, double amount) throws NotFoundException, InsuficientBalanceException{
		
		Account withdraw = accountService.withdraw(accountIdFrom, amount);
		
		Account deposit = accountService.deposit(accountIdTo, amount);
		
		ResponseTransfer resultTransfer = ResponseTransfer.builder().fromAccount(withdraw).toAccount(deposit).build();
		
		return resultTransfer;
	}
}
