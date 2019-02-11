package com.revolut.rest.data.repository.db;

import java.util.List;

import com.revolut.rest.data.model.Account;
import com.revolut.rest.service.exception.AccountAlreadyExistsException;
import com.revolut.rest.service.exception.NotFoundException;

public interface IAccountDAO {

	Account create(Account account) throws AccountAlreadyExistsException;

	Account get(String id) throws NotFoundException;

	Account update(Account Account);

	boolean delete(String id); 

	List<Account> listAccounts();
	

}
