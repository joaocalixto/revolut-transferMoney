package com.revolut.rest.service.exception;

import javax.ws.rs.ext.ExceptionMapper;

public class InsuficientBalanceException extends AbstractTMException  implements ExceptionMapper<AbstractTMException>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5559240396192414829L;

	public InsuficientBalanceException(String format) {
		super(format);
	}

	

}
