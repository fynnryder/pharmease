package com.easepharm.utils;

import org.json.JSONObject;

public class Response {
	
	private boolean isValid;
	private String status;
	private String response;
	JSONObject responseJson = new JSONObject();
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public JSONObject getResponseJson() {
		try{
			responseJson.put("status",this.status );
			responseJson.put("response",this.response );
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return responseJson;
	}
	public void setResponseJson(JSONObject responseJson) {
		this.responseJson = responseJson;
	}
}
