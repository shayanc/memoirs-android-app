package com.memoirs.network.exceptions;

import com.memoirs.network.RequestStatus;

public class RequestErrorException extends WebServiceException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3013612321573591638L;
	private RequestStatus status;
	private static final String EXCEPTION_MSG = "The request made had an error.";
	public RequestErrorException ( RequestStatus error) {
		super(EXCEPTION_MSG);
		this.status = error;
	}
	public RequestStatus getRequestStatus (){
		return status;
	}

}
