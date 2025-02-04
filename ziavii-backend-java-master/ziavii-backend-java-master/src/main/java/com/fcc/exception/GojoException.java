package com.fcc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class GojoException extends RuntimeException {
	
	public static final String AUTH_ERROR = "AUTH ERROR";
	public static final String INVALID_USER = "Invalid User";
	public static final String NOT_VERIFIED = "Email not verified";
	public static final String NO_SUBS = "No active subscription";
	
	private ApiErrorCode errorCode;

	public ApiErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ApiErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public GojoException(String message) {
		super(message);
	}
	
	public GojoException(ApiErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public GojoException(ApiErrorCode errorCode) {
		super();
		this.errorCode = errorCode;
	}
	
}
