package com.memoirs.network.connector.interfaces;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

public interface ParamServiceRequestInterface extends ServiceRequestInterface{
	List<BasicNameValuePair> getParameters ();
}
