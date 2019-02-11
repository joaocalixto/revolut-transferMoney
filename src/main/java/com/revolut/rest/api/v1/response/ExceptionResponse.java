package com.revolut.rest.api.v1.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionResponse{
	
	private String erroMessage;
	private String cause;
}
