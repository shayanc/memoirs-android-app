package com.memoirs.network.exceptions;


public class MalformedJSONException extends NetworkException{
	
	private static final long serialVersionUID = 0;
	
	public MalformedJSONException (String emptyTag){
		super ("The compulsory '" + emptyTag + "' tag is empty.");
	}
	public MalformedJSONException(Throwable e){
		super("Empty tag",e);
	}
}