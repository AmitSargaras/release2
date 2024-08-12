package com.integrosys.cms.app.lei.json.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ExtendedReply {

	@JsonProperty("messages") 
	private Object messages;

	public Object getMessages() {
		return messages;
	}

	public void setMessages(Object messages) {
		this.messages = messages;
	}
	
}
