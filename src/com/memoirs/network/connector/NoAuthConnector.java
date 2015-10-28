package com.memoirs.network.connector;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.StrictMode;

import com.memoirs.network.Constants;
import com.memoirs.network.connector.interfaces.ConnectorInterface;
import com.memoirs.network.connector.interfaces.ParamServiceRequestInterface;
import com.memoirs.network.connector.interfaces.ServiceRequestInterface;
import com.memoirs.network.exceptions.CommunicationException;
import com.memoirs.network.exceptions.ConnectionException;
import com.memoirs.network.exceptions.NetworkException;

public class NoAuthConnector implements ConnectorInterface {

	public NoAuthConnector (){
		/*
		 * This piece of code is just a hack to allow running Network Threads on the UI thread.
		 * Once AsyncTask is implemented, we'll remove this code.
		 */
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}
	
	@Override
	public String sendRequest(ServiceRequestInterface request, String URL) 
			throws NetworkException, UnsupportedEncodingException{
		HttpRequestBase httpRequest;	
		//Builds the appropriate type of HttpRequest, based on the type of Service Request
		switch (request.getRequestType()){
		case DELETE:
			httpRequest = buildDeleteRequest (request, URL);
			break;
		case GET:
			httpRequest = buildGetRequest(request, URL);
			break;
		case POST:
			httpRequest = buildPostRequest(request, URL);
			break;
		default:
			throw new RuntimeException("Unexpected Request Type") ;
		}
		
		//Sends the request
		return send(httpRequest);
	}
	@Override
	public String sendRequest(ParamServiceRequestInterface request, String URL) 
			throws NetworkException, UnsupportedEncodingException  {
		HttpRequestBase httpRequest;	
		
		switch (request.getRequestType()){
			case GET:
				httpRequest = buildGetRequest(request, URL);
				break;
			case POST:
				httpRequest = buildPostRequest(request, URL);
				break;
			default:
				throw new RuntimeException("Unexpected Request Type") ;	
			}
		return send(httpRequest);
		
	}
	
	private String send (HttpRequestBase httpRequest) throws CommunicationException, ConnectionException  {
		HttpClient client = new DefaultHttpClient();
		HttpResponse response;
		String responseString;

		try {
			response = client.execute(httpRequest);
			responseString = EntityUtils.toString(response.getEntity(), Constants.ENCODING_FORMAT);
		} catch (ClientProtocolException e) {
			throw new CommunicationException(e);
		}catch (ConnectException e){
			throw new ConnectionException(e);
		}
		catch (ConnectTimeoutException e){
			throw new ConnectionException(e);
		}
		catch (IOException e) {
			throw new CommunicationException(e);
		}
		
		return responseString;
	}
	private HttpGet buildGetRequest (ParamServiceRequestInterface request, String URL) throws UnsupportedEncodingException{
		//build the the rest of the URL using the parameter for the request
	    String paramString = "?" + URLEncodedUtils.format(request.getParameters(), Constants.ENCODING_FORMAT);
	    String finalUrl = URL + "/" + request.getResource() + paramString;
	    
		return new HttpGet(finalUrl);
	}
	private HttpGet buildGetRequest (ServiceRequestInterface request, String URL) throws UnsupportedEncodingException{
		String finalUrl = URL + "/" + request.getResource();
		return new HttpGet(finalUrl);
	}
	
	private HttpPost buildPostRequest (ParamServiceRequestInterface request, String URL) throws UnsupportedEncodingException{
		
		HttpPost post = new HttpPost (URL + "/" + request.getResource());
		post.setEntity(new UrlEncodedFormEntity(request.getParameters()));
		return post;
	}
	
	private HttpPost buildPostRequest (ServiceRequestInterface request, String URL) throws UnsupportedEncodingException{
		HttpPost post = new HttpPost (URL + "/" + request.getResource());
		return post;
	}
	
	private HttpDelete buildDeleteRequest (ServiceRequestInterface request, String URL) throws UnsupportedEncodingException{
		HttpDelete delete = new HttpDelete (URL + "/" + request.getResource());
		return delete;
	}

}
