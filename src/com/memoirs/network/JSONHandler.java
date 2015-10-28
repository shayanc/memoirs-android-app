package com.memoirs.network;

import java.util.Iterator;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Base64;

import com.memoirs.application.interfaces.MemoryInterface;
import com.memoirs.network.exceptions.MalformedJSONException;

public class JSONHandler {
	
    // JSON Node names
    private static final String TAG_MEMORY = "memory";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADDITIONAL_INFO = "additional_info";
    private static final String TAG_AUDIO = "audio";
    private static final String TAG_PICTURE = "img";
    private static final String TAG_AUDIO_BASE64 = "audio_base64";
    private static final String TAG_PICTURE_BASE64 = "picture_base64";
    private static final String TAG_PHONE_NUMBER = "phone_number";
    private static final String TAG_RESPONSE = "response";
    private static final String TAG_REQUEST_STATUS = "request_status";
    private static final String TAG_CODE = "code";
    private static final String TAG_DESCRIPTION = "description";
    
    public JSONHandler(){}
    
	public RequestStatus JSONToMemory(MemoryInterface memory, String json) throws JSONException, MalformedJSONException{

		JSONObject jsonObject = new JSONObject(json);

		JSONObject response = jsonObject.getJSONObject(TAG_RESPONSE);
		
	// Build Memory Interface object
		Object memoryObject = response.get(TAG_MEMORY);
		if (memoryObject instanceof JSONObject){
			JSONObject memoryJson = (JSONObject) memoryObject;
			String name = memoryJson.getString(TAG_NAME);
/*				if (name.equals("")){
					name = null;
				}*/
			
			// Build AdditionalInfo object
			Object additionalInfoObject = memoryJson.get(TAG_ADDITIONAL_INFO);
			Bundle additionalInfo = null;
			if (additionalInfoObject instanceof JSONObject){
				JSONObject additionalInfoJson = (JSONObject) additionalInfoObject;
				additionalInfo = JSONToBundle(additionalInfoJson);
			}
			
			// Get media and convert from Base64 to bytes			
			String picture_base64 = memoryJson.getString(TAG_PICTURE_BASE64);
			String audio_base64 = memoryJson.getString(TAG_AUDIO_BASE64);
			if (audio_base64.equals("")){
				throw new MalformedJSONException(TAG_AUDIO_BASE64);
			}
			
			byte [] picture = null;
			if (!picture_base64.equals("")){
				picture = decodeBase64ToMedia(picture_base64);	
			}
			byte [] audio = decodeBase64ToMedia(audio_base64);
			
			memory.setName(name);
			memory.setAddInfo(additionalInfo);
			memory.setAudio(audio);
			memory.setPicture(picture);			
		}
		
		return responseJSONToStatus(response);
	}
	
	public RequestStatus JSONToPhoneNumber(StringBuilder phoneNumber, String json) throws JSONException, MalformedJSONException{

		JSONObject jsonObject = new JSONObject(json);
		JSONObject response = jsonObject.getJSONObject(TAG_RESPONSE);

		Object memoryObject = response.get(TAG_MEMORY);
		if (memoryObject instanceof JSONObject) {
			JSONObject memoryJson = (JSONObject) memoryObject;
			phoneNumber.append(memoryJson.getString(TAG_PHONE_NUMBER));
		}			
		return responseJSONToStatus(response);
	}
	
	public RequestStatus JSONToStatus(String json) throws JSONException, MalformedJSONException{
		JSONObject jsonObject = new JSONObject(json);
		JSONObject response = jsonObject.getJSONObject(TAG_RESPONSE);
		
		return responseJSONToStatus(response);
	}
	
	public String memoryToJSON(MemoryInterface memory) throws RuntimeException {
		try{
			JSONObject jsonMemoryObject = new JSONObject();
			JSONObject jsonObject = new JSONObject();
			String pictureBase64 = null;
			String audioBase64;
			byte [] picture_data = memory.getPicture();
			
			if (picture_data != null){
				 pictureBase64 = encodeMediaToBase64(memory.getPicture());
			}
	
			//TODO getAudio should never return null
			audioBase64 = encodeMediaToBase64(memory.getAudio());
			
			// Create Additional Info JSONObject
			Bundle additionalInfo = memory.getAddInfo();
			JSONObject additionalInfoObject = null;
			if (additionalInfo != null){
				additionalInfoObject = bundleToJSON(additionalInfo);
			}		
	
			jsonMemoryObject.put(TAG_NAME, memory.getName());
			jsonMemoryObject.put(TAG_ADDITIONAL_INFO, additionalInfoObject);
			jsonMemoryObject.put(TAG_PICTURE, pictureBase64);
			jsonMemoryObject.put(TAG_AUDIO, audioBase64);
			jsonObject.put(TAG_MEMORY, jsonMemoryObject);
			
			return jsonObject.toString();
		} catch (JSONException e){
//			TODO reconsider this later
			throw new RuntimeException("It wasn't possible to create json from the memory provided", e);			
		}
	}
	
	RequestStatus responseJSONToStatus(JSONObject response) throws JSONException, MalformedJSONException{
		JSONObject requestStatusJson = response.getJSONObject(TAG_REQUEST_STATUS);

		String codeString = requestStatusJson.getString(TAG_CODE);
		if (codeString.equals("")){
			throw new MalformedJSONException(TAG_CODE);
		}
		int code = Integer.parseInt(codeString);
		String description = requestStatusJson.getString(TAG_DESCRIPTION);
		RequestStatus requestStatus = new RequestStatus(code, description);

		return requestStatus;
	}
	
	Bundle JSONToBundle(JSONObject json) throws JSONException{
		Bundle bundle = new Bundle();

	    Iterator<?> iter = json.keys();
	    while( iter.hasNext() ){
            String key = (String)iter.next();
	        Object value = json.get(key);
	        if (value instanceof JSONObject){
	        	bundle.putBundle(key, JSONToBundle((JSONObject) value));
	        }
	        else if (value instanceof Integer){
	        	bundle.putString(key, Integer.toString((Integer) value));
	        }
	        else {
	        	bundle.putString(key, (String) value);
	        }
		}
		return bundle;
	}
	
	JSONObject bundleToJSON(Bundle bundle) throws JSONException{	
		JSONObject json = new JSONObject();
		Set<String> keys = bundle.keySet();
		Iterator<?> iter = keys.iterator();
	    while( iter.hasNext() ){
            String key = (String)iter.next();
	        Object value = bundle.get(key);
	        if (value instanceof Bundle){
	        	json.put(key, bundleToJSON((Bundle) value));
	        }
	        else if (value instanceof Integer){
	        	json.put(key, Integer.toString((Integer) value));
	        }
	        else{
	        	json.put(key, (String) value);
	        }
	    }
	    return json;
	}

	
	String encodeMediaToBase64(byte [] media){
		return Base64.encodeToString(media, Base64.DEFAULT);
	}
	
	byte [] decodeBase64ToMedia(String base64){
		return Base64.decode(base64, Base64.DEFAULT);
	}
	

}