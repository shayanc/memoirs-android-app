package com.memoirs.network.exceptions;

public class InvalidWebServiceResponse extends WebServiceException{
	private static final long serialVersionUID = 0;
	
	private static final String EXCEPTION_MSG = "Web Service response is invalid";
	public InvalidWebServiceResponse(Throwable e){
		super(EXCEPTION_MSG, e);		
	}
	public InvalidWebServiceResponse (){
		super(EXCEPTION_MSG);
	}

}
