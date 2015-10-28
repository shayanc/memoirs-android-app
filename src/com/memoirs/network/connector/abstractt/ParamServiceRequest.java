package com.memoirs.network.connector.abstractt;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.memoirs.network.connector.RequestType;
import com.memoirs.network.connector.interfaces.ParamServiceRequestInterface;

public class ParamServiceRequest extends ServiceRequest implements ParamServiceRequestInterface{
	protected List<BasicNameValuePair> parameters;
	
	public ParamServiceRequest (List<BasicNameValuePair> parameters, String resource,RequestType type){
		super(resource, type);
		if ( type == RequestType.DELETE)
			throw new RuntimeException("DELETE service request can not have a parameter");
		this.parameters = parameters;
	}
	@Override
	public List<BasicNameValuePair> getParameters() {
		// TODO Auto-generated method stub
		return parameters;
	}
	
	
}
