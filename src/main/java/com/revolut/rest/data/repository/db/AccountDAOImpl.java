package com.revolut.rest.data.repository.db;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import com.revolut.rest.data.model.Account;
import com.revolut.rest.service.exception.AccountAlreadyExistsException;
import com.revolut.rest.service.exception.Exceptions;
import com.revolut.rest.service.exception.NotFoundException;

import lombok.extern.java.Log;

@Log
public class AccountDAOImpl implements IAccountDAO{
	
    private static ConcurrentMap<String, Account> accountMap;
    
    static AccountDAOImpl INSTANCE = new AccountDAOImpl();
    
    private AccountDAOImpl() {
    	log.info("Init DB");
        accountMap = new ConcurrentHashMap<>();
    }
    
    public static void cleanDB() {
    	log.info("Cleaning DB");
    	accountMap = new ConcurrentHashMap<>();
    }
    
    public static AccountDAOImpl getInstance() {
    	return INSTANCE;
    }
    
    @Override
    public Account create(Account account) throws AccountAlreadyExistsException {
    	
        if (null != accountMap.putIfAbsent(account.getId(), account)) {
            throw Exceptions.AccountAlreadyExistsException(String.format("Account already exists with id %s", account.getId()));
        }
        return account;
    }
    @Override
    public Account get(String id) throws NotFoundException {
		return getThrowNotFound(id);
    }
    
    // Alternate implementation to throw exceptions instead of return nulls for not found.
    public Account getThrowNotFound(String id) throws NotFoundException {
        Account Account = accountMap.get(id);
        if (null == Account) {
            throw Exceptions.notFound(String.format("Account %s not found", id));
        }
        return Account;
    }
    @Override
    public Account update(Account Account) {
        // This means no Account existed so update failed. return null
        if (null == accountMap.replace(Account.getId(), Account)) {
            return null;
        }
        // Update succeeded return the Account
        return Account;
    }
	@Override
	public boolean delete(String id) {
		 if (accountMap.containsKey(id)) {
			 accountMap.remove(id);
			   return true;
			 } else {
			   return false;
			 }
	}
	@Override
	public List<Account> listAccounts() {
		return accountMap.values()
                .stream()
                .collect(Collectors.toList());
	}
	
	
    
}