package com.memoirs.network.connector.interfaces;

import com.memoirs.network.connector.RequestType;

public interface ServiceRequestInterface {
	RequestType getRequestType (); 
	String getResource();
}
