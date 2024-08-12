package com.integrosys.cms.app.lei.json.dto;
import com.fasterxml.jackson.annotation.JsonProperty;


public class ValidateLEINumFromCCILRequestDTO {
	
	@JsonProperty("requestString") 
	private RetrieveLEICCILRequest requestString;

	public RetrieveLEICCILRequest getRequestString() {
		return requestString;
	}

	public void setRequestString(RetrieveLEICCILRequest requestString) {
		this.requestString = requestString;
	}

	
}
