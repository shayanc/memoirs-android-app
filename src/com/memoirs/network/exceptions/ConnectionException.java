package com.memoirs.network.exceptions;

public class ConnectionException extends NetworkException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String EXCEPTION_MSG = "Unable to Connect to the WebService. The Service is either down or too busy.";
	public ConnectionException() {
		super(EXCEPTION_MSG);
		// TODO Auto-generated constructor stub
	}
	public ConnectionException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
		// TODO Auto-generated constructor stub
	}
	public ConnectionException(String detailMessage) {
		super(detailMessage);
		// TODO Auto-generated constructor stub
	}
	public ConnectionException(Throwable throwable) {
		super(EXCEPTION_MSG,throwable);
		// TODO Auto-generated constructor stub
	}


}
