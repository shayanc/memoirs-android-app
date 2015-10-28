package com.memoirs.network.async;

import java.io.UnsupportedEncodingException;

import com.memoirs.network.async.interfaces.RequestErrorHolderInterface;
import com.memoirs.network.exceptions.NetworkModuleException;

public class RequestErrorHolder implements RequestErrorHolderInterface{

	private Exception exception;
	public RequestErrorHolder (Exception e){
		this.exception = e;
	}
	@Override
	public boolean hasErrorOccurred() throws NetworkModuleException, UnsupportedEncodingException  {
		
		if ( exception != null){
			if (exception instanceof NetworkModuleException){
				throw (NetworkModuleException)exception;
			}
			else if (exception instanceof UnsupportedEncodingException){
				throw (UnsupportedEncodingException) exception;
			}
		}
		
		return false;
	}

}
