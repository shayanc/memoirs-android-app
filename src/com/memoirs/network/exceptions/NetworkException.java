package com.memoirs.network.exceptions;

public class NetworkException extends NetworkModuleException{

	private final static String EXCEPTION_MSG = "Error occurred while trying to establish communication with the server";

	public NetworkException() {
		super(EXCEPTION_MSG);
		// TODO Auto-generated constructor stub
	}

	public NetworkException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
		// TODO Auto-generated constructor stub
	}

	public NetworkException(String detailMessage) {
		super(detailMessage);
		// TODO Auto-generated constructor stub
	}

	public NetworkException(Throwable throwable) {
		super(EXCEPTION_MSG,throwable);
		// TODO Auto-generated constructor stub
	}


}
