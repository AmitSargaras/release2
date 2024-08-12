package com.integrosys.cms.app.ws.dto;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


public class ResponseDTO {

	private static final long serialVersionUID = 5998360441359264877L;
	


	public ResponseDTO() {
		super();
	}

	private String transactionID;
	
	private String responseStatus;
	
	

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	public String getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}

	
	public String getTransactionID() {
		return transactionID;
	}

	
}

