package com.revolut.rest.data.repository;

import java.util.List;

import com.revolut.rest.data.model.Account;
import com.revolut.rest.service.exception.AccountAlreadyExistsException;
import com.revolut.rest.service.exception.NotFoundException;

public interface IAccountRepository {
	
	Account update(Account accountId);
	
	Account create(String id) throws AccountAlreadyExistsException;
	
	Account findById(String id) throws NotFoundException;
	
	List<Account> findAll();
	
}
