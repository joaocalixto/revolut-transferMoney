package com.revolut.rest.service;

import com.revolut.rest.api.v1.response.ResponseTransfer;
import com.revolut.rest.service.exception.InsuficientBalanceException;
import com.revolut.rest.service.exception.NotFoundException;

public interface IMoneyTransferService {
	
	
	ResponseTransfer transfer(String accountIdFrom, String accountIdTo, double amount) throws NotFoundException, InsuficientBalanceException;

}
