package com.revolut.rest.service;

import java.util.List;

import com.revolut.rest.data.model.Account;
import com.revolut.rest.data.repository.AccountRepositoryImpl;
import com.revolut.rest.data.repository.IAccountRepository;
import com.revolut.rest.service.exception.AccountAlreadyExistsException;
import com.revolut.rest.service.exception.Exceptions;
import com.revolut.rest.service.exception.InsuficientBalanceException;
import com.revolut.rest.service.exception.NotFoundException;

public class StoreAccountServiceImpl implements IStoreAccountService {
	
	IAccountRepository accountRepository = new AccountRepositoryImpl();

	public Account create(String id) throws AccountAlreadyExistsException {
		return accountRepository.create(id);
	}

	public Account withdraw(String accountId, double amount) throws InsuficientBalanceException, NotFoundException {
		
		Account account = accountRepository.findById(accountId);
		
		if(account.getBalance() - amount < 0) {
			throw Exceptions.InsuficientBalanceException(String.format("Current account balance is %s", account.getBalance()));
		}
		
		account.setBalance(account.getBalance() - amount);
		
		return accountRepository.update(account);
	}

	public Account deposit(String accountId, double amount) throws NotFoundException {
		
		Account account = accountRepository.findById(accountId);
		
		account.setBalance(account.getBalance() + amount);
		
		return accountRepository.update(account);

	}

	@Override
	public List<Account> listAll() {
		return accountRepository.findAll();
	}


}
