package com.omega.backend.forum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class ForumException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ForumException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ForumException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ForumException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ForumException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ForumException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	
}
