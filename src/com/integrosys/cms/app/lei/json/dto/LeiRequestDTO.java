package com.integrosys.cms.app.lei.json.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LeiRequestDTO {

	@JsonProperty("ValidateLEINumFromCCILRequestDTO") 
	private ValidateLEINumFromCCILRequestDTO validateLEINumFromCCILRequestDTO;
	
	@JsonProperty("sessionContext") 
	private SessionContext sessionContext;
	
	public ValidateLEINumFromCCILRequestDTO getValidateLEINumFromCCILRequestDTO() {
		return validateLEINumFromCCILRequestDTO;
	}
	public void setValidateLEINumFromCCILRequestDTO(ValidateLEINumFromCCILRequestDTO validateLEINumFromCCILRequestDTO) {
		this.validateLEINumFromCCILRequestDTO = validateLEINumFromCCILRequestDTO;
	}
	public SessionContext getSessionContext() {
		return sessionContext;
	}
	public void setSessionContext(SessionContext sessionContext) {
		this.sessionContext = sessionContext;
	}	
	
}
