package com.memoirs.network.exceptions;

public class CommunicationException extends NetworkException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1815341711494421787L;
	private static final String EXCEPTION_MSG = "Error occurred while exchanging messages with the server.";
	public CommunicationException() {
		super(EXCEPTION_MSG);
		// TODO Auto-generated constructor stub
	}
	public CommunicationException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
		// TODO Auto-generated constructor stub
	}
	public CommunicationException(String detailMessage) {
		super(detailMessage);
		// TODO Auto-generated constructor stub
	}
	public CommunicationException(Throwable throwable) {
		super(EXCEPTION_MSG,throwable);
		// TODO Auto-generated constructor stub
	}
	
}
