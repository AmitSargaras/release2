package com.integrosys.cms.app.lei.json.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PostingDate {

	@JsonProperty("dateString") 
	private String dateString;

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}
	
	
}
