package com.memoirs.network;

import com.memoirs.network.connector.RequestType;

public class Constants {

	public static final String ENCODING_FORMAT = "utf-8";
	public static class API{
		
		//Create memory service
		public static class NEW_MEMORY{
			public static final String RESOURCE = "/memories";
			public static final String RESOURCE_TYPE = ".json";
			public static final RequestType HTTP_TYPE = RequestType.POST;
			
			public static class PARAMETER_TAGS{
				public static final String MEMORY_JSON = "request";
			}
		}
		//retrieve memory service
		public static class GET_MEMORY{
			public static final String RESOURCE = "/memories/";
			public static final String RESOURCE_TYPE = ".json";
			public static final RequestType HTTP_TYPE = RequestType.GET;
			
			public static class PARAMETER_TAGS{
			}
		}
		
		
		//delete memory
		public static class DELETE_MEMORY{
			public static final String RESOURCE = "/memories/";
			public static final String RESOURCE_TYPE = ".json";
			public static final RequestType HTTP_TYPE = RequestType.DELETE;
			
			public static class PARAMETER_TAGS{
			}
		}
	}
}
