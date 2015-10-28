package com.memoirs.network;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import android.util.Log;

import com.memoirs.application.interfaces.MemoryInterface;
import com.memoirs.network.connector.NoAuthConnector;
import com.memoirs.network.connector.RequestType;
import com.memoirs.network.connector.abstractt.ParamServiceRequest;
import com.memoirs.network.connector.abstractt.ServiceRequest;
import com.memoirs.network.connector.interfaces.ConnectorInterface;
import com.memoirs.network.connector.interfaces.ParamServiceRequestInterface;
import com.memoirs.network.exceptions.CommunicationException;
import com.memoirs.network.exceptions.InvalidWebServiceResponse;
import com.memoirs.network.exceptions.MalformedJSONException;
import com.memoirs.network.exceptions.ConnectionException;
import com.memoirs.network.exceptions.NetworkModuleException;
import com.memoirs.network.exceptions.RequestErrorException;
import com.memoirs.network.interfaces.NetworkModuleInterface;

public class NetworkModule implements NetworkModuleInterface {

	private ConnectorInterface connector;
	private JSONHandler jsonHandler;
	private String webServiceURL;
	
	public NetworkModule (){
		this.jsonHandler = new JSONHandler();
		this.connector = new NoAuthConnector();
		this.webServiceURL = "http://glacial-taiga-8074.herokuapp.com/";
		
	}
	
	@Override
	public String createMemory(MemoryInterface memory) throws NetworkModuleException, UnsupportedEncodingException {
		
		try {
			String jsonMem = jsonHandler.memoryToJSON(memory);

			//build a parameter list
			List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
			parameters.add(new BasicNameValuePair(Constants.API.NEW_MEMORY.PARAMETER_TAGS.MEMORY_JSON, jsonMem));
			
			
			//build a ServiceRequest
			ParamServiceRequestInterface request = new ParamServiceRequest (parameters,
					Constants.API.NEW_MEMORY.RESOURCE + Constants.API.NEW_MEMORY.RESOURCE_TYPE,
					Constants.API.NEW_MEMORY.HTTP_TYPE);
		
			//sends the ServiceRequest
			String jsonResponse = connector.sendRequest(request, webServiceURL);
			StringBuilder temp = new StringBuilder();
			RequestStatus responseStatus = jsonHandler.JSONToPhoneNumber(temp, jsonResponse);
			
			if (responseStatus.getStatusCode() != RequestStatus.SUCCESS){
				//There was an error with the request
				throw new RequestErrorException(responseStatus);
			}
			String phoneNumber = temp.toString();
				
			return phoneNumber;	
		} 
		catch (MalformedJSONException e) {
			throw new InvalidWebServiceResponse(e);
		} catch (JSONException e) {
			throw new InvalidWebServiceResponse(e);
		}  
	}

	@Override
	public void retrieveMemory(String phoneNumber, MemoryInterface memory) throws NetworkModuleException, UnsupportedEncodingException{

		String resource = Constants.API.GET_MEMORY.RESOURCE + phoneNumber + Constants.API.GET_MEMORY.RESOURCE_TYPE;
		ServiceRequest get_mem = new ServiceRequest(resource, Constants.API.GET_MEMORY.HTTP_TYPE);
		
		try {
			String jsonResponse = connector.sendRequest(get_mem, webServiceURL);
			RequestStatus responseStatus = jsonHandler.JSONToMemory(memory, jsonResponse);
			
			if (responseStatus.getStatusCode() != RequestStatus.SUCCESS){
				throw new RequestErrorException(responseStatus);
			}
		}  catch (MalformedJSONException e) {
			throw new InvalidWebServiceResponse(e);
		} catch (JSONException e) {
			throw new InvalidWebServiceResponse(e);
		}
	}

	@Override
	public void deleteMemory(String phoneNumber) throws NetworkModuleException, UnsupportedEncodingException {
		String resource = Constants.API.DELETE_MEMORY.RESOURCE + phoneNumber + Constants.API.DELETE_MEMORY.RESOURCE_TYPE;
		ServiceRequest delete_mem = new ServiceRequest(resource, Constants.API.DELETE_MEMORY.HTTP_TYPE);
		
		try {
			String jsonResponse = connector.sendRequest(delete_mem, webServiceURL);
			RequestStatus responseStatus = jsonHandler.JSONToStatus(jsonResponse);
			
			if (responseStatus.getStatusCode() != RequestStatus.SUCCESS){
				throw new RequestErrorException(responseStatus);
			}
		}  catch (MalformedJSONException e) {
			throw new InvalidWebServiceResponse(e);
		} catch (JSONException e) {
			throw new InvalidWebServiceResponse(e);
		}
	}
}
