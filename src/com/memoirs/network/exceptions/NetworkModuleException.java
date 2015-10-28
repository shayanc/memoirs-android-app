package com.memoirs.network.exceptions;

public class NetworkModuleException extends Exception{
	
	private final static String EXCEPTION_MSG = "Error occurred while trying to execute the request";

	public NetworkModuleException() {
		super(EXCEPTION_MSG);
		// TODO Auto-generated constructor stub
	}

	public NetworkModuleException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
		// TODO Auto-generated constructor stub
	}

	public NetworkModuleException(String detailMessage) {
		super(detailMessage);
		// TODO Auto-generated constructor stub
	}

	public NetworkModuleException(Throwable throwable) {
		super(EXCEPTION_MSG,throwable);
		// TODO Auto-generated constructor stub
	}



	
}
