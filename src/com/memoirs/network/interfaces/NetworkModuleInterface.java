package com.memoirs.network.interfaces;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.memoirs.application.interfaces.MemoryInterface;
import com.memoirs.network.exceptions.InvalidWebServiceResponse;
import com.memoirs.network.exceptions.NetworkException;
import com.memoirs.network.exceptions.NetworkModuleException;
import com.memoirs.network.exceptions.RequestErrorException;
import com.memoirs.network.exceptions.WebServiceException;

public interface NetworkModuleInterface {

	public String createMemory (MemoryInterface memory) throws NetworkModuleException, UnsupportedEncodingException;
	public void retrieveMemory (String phoneNumber, MemoryInterface memory) throws NetworkModuleException, UnsupportedEncodingException;
	public void deleteMemory (String phoneNumber) throws NetworkModuleException, UnsupportedEncodingException;
}
