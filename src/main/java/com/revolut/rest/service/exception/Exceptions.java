package com.revolut.rest.service.exception;

public class Exceptions{

	public static NotFoundException notFound(String format) {
		return new NotFoundException(format);
	}

	public static AccountAlreadyExistsException AccountAlreadyExistsException(String format) {
		return new AccountAlreadyExistsException(format);
	}
	
	public static InsuficientBalanceException InsuficientBalanceException(String format) {
		return new InsuficientBalanceException(format);
	}
}
