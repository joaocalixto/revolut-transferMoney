package com.revolut.rest.data.repository;

import static com.revolut.rest.data.model.AccountFactory.creatNewAccout;

import java.util.List;

import com.revolut.rest.data.model.Account;
import com.revolut.rest.data.repository.db.AccountDAOImpl;
import com.revolut.rest.data.repository.db.IAccountDAO;
import com.revolut.rest.service.exception.AccountAlreadyExistsException;
import com.revolut.rest.service.exception.NotFoundException;

public class AccountRepositoryImpl implements IAccountRepository {
	
	IAccountDAO db = AccountDAOImpl.getInstance();
	
	public Account update(Account account) {
		return db.update(account);
	}

	@Override
	public Account create(String id) throws AccountAlreadyExistsException {
		
		Account account = creatNewAccout(id);
		
		return db.create(account);
	}

	@Override
	public Account findById(String id) throws NotFoundException {
		return db.get(id);
	}

	@Override
	public List<Account> findAll() {
		return db.listAccounts();
	}


}
