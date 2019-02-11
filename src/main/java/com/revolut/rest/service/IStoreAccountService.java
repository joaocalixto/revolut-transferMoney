package com.revolut.rest.service;

import java.util.List;

import com.revolut.rest.data.model.Account;
import com.revolut.rest.service.exception.AccountAlreadyExistsException;
import com.revolut.rest.service.exception.InsuficientBalanceException;
import com.revolut.rest.service.exception.NotFoundException;

public interface IStoreAccountService {
	
	List<Account> listAll();
	
	Account create(String id) throws AccountAlreadyExistsException;
	
	Account withdraw(String accountId, double amount) throws InsuficientBalanceException, NotFoundException;
	
	Account deposit(String accountId, double amount) throws NotFoundException;

}
