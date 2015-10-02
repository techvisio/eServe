package com.techvisio.eserve.util;

public class EISystemException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EISystemException() {
		super();
	}

	public EISystemException(String message, Throwable cause) {
		super(message + (cause == null ? "" : (" : " + cause.getMessage())),
				cause);
	}

	public EISystemException(String message) {
		super(message);
	}

	public EISystemException(Throwable cause) {
		super(cause);
	}
}
