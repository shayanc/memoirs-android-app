package com.memoirs.network.connector.interfaces;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.memoirs.network.exceptions.CommunicationException;
import com.memoirs.network.exceptions.ConnectionException;
import com.memoirs.network.exceptions.NetworkException;


public interface ConnectorInterface {
	String sendRequest (ServiceRequestInterface request, String URL) 
			throws NetworkException, UnsupportedEncodingException;
	String sendRequest (ParamServiceRequestInterface request, String URL) 
			throws NetworkException, UnsupportedEncodingException;
}
