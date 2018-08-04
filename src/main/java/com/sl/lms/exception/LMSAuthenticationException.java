package com.sl.lms.exception;

import org.springframework.security.core.AuthenticationException;

public class LMSAuthenticationException extends AuthenticationException{

	private static final long serialVersionUID = 1L;

	private int errorCode;
	private String errorMsg;
	
	public LMSAuthenticationException(String errorMsg) {
		super(errorMsg);
		this.errorCode = 101;
		this.errorMsg = errorMsg;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
}
