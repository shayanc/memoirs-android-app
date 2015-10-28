package com.memoirs.network.connector.abstractt;

import com.memoirs.network.connector.RequestType;
import com.memoirs.network.connector.interfaces.ServiceRequestInterface;

public class ServiceRequest implements ServiceRequestInterface{

	private String resource;
	private RequestType type;
	public ServiceRequest (String resource, RequestType type){
		this.resource = resource;
		this.type = type;
	}
	@Override
	public String getResource() {
		// TODO Auto-generated method stub
		return resource;
	}
	@Override
	public RequestType getRequestType() {
		// TODO Auto-generated method stub
		return type;
	}
	

}
