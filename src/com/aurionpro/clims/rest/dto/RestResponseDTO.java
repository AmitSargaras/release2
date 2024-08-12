/**
 * 
 */
package com.aurionpro.clims.rest.dto;

import java.util.List;

/**
 * @author anil.pandey
 *
 */
public class RestResponseDTO {
	
	

	private String transactionID;
	
	private String responseStatus;
	
	
	public String getTransactionID() {
		return transactionID;
	}
	public String getResponseStatus() {
		return responseStatus;
	}
	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
	
	private List<ResponseMessageDetailDTO> responseMessageList;


	public List<ResponseMessageDetailDTO> getResponseMessageList() {
		return responseMessageList;
	}
	public void setResponseMessageList(
			List<ResponseMessageDetailDTO> responseMessageList) {
		this.responseMessageList = responseMessageList;
	}

	
	
	
}
