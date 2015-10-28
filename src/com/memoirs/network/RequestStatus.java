package com.memoirs.network;

public class RequestStatus {
	
	public static final int SUCCESS = 00;
	public static final int ERROR_REQUEST_PHONENUMBER_UNKNOWN = 30;
	public static final int ERROR_UNKOWN = -01;
	public static final int ERROR_UNEXPECTED = -02;
	
	private int statusCode;
	private String statusDescription;
	
	public RequestStatus (int code, String desc){
		this.statusCode = code;
		this.statusDescription = desc;
	}
	
	public int getStatusCode (){
		return this.statusCode;
	}
	
	public String getStatusDesc (){
		return this.statusDescription;
	}
}
