package com.integrosys.cms.app.json.line.dto; 
import com.fasterxml.jackson.annotation.JsonProperty; 
import java.util.List; 
public class ResponseString{
    public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public List<String> getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(List<String> responseMessage) {
		this.responseMessage = responseMessage;
	}
	@JsonProperty("StatusCode") 
    public int statusCode;
    @JsonProperty("StatusMessage") 
    public String statusMessage;
    @JsonProperty("ResponseMessage") 
    public List<String> responseMessage;
}
