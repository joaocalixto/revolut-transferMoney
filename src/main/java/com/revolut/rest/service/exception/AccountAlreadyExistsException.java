package com.revolut.rest.service.exception;

import javax.ws.rs.ext.ExceptionMapper;

public class AccountAlreadyExistsException extends AbstractTMException implements ExceptionMapper<AbstractTMException>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4316841196571420035L;

	public AccountAlreadyExistsException(String format) {
		super(format);
	}

}
