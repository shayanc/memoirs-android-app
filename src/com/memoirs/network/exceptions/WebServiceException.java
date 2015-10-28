package com.memoirs.network.exceptions;

public class WebServiceException extends NetworkModuleException{

	private final static String EXCEPTION_MSG = "Error occurred while the WebService was processing the request.";
	public WebServiceException() {
		super(EXCEPTION_MSG);
		// TODO Auto-generated constructor stub
	}

	public WebServiceException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
		// TODO Auto-generated constructor stub
	}

	public WebServiceException(String detailMessage) {
		super(detailMessage);
		// TODO Auto-generated constructor stub
	}

	public WebServiceException(Throwable throwable) {
		super(EXCEPTION_MSG,throwable);
		// TODO Auto-generated constructor stub
	}
}
