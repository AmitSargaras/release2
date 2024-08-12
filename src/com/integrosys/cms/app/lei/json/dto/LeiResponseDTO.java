package com.integrosys.cms.app.lei.json.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LeiResponseDTO {

	@JsonProperty("status") 
	private StatusDTO status;
	@JsonProperty("maintenanceType")
	private Object maintenanceType;
	@JsonProperty("configVersionId")
	private Object configVersionId;
	@JsonProperty("responseString")
	private RetrieveLEICCILResponse responseString;
	
	public StatusDTO getStatus() {
		return status;
	}
	public void setStatus(StatusDTO status) {
		this.status = status;
	}
	public Object getMaintenanceType() {
		return maintenanceType;
	}
	public void setMaintenanceType(Object maintenanceType) {
		this.maintenanceType = maintenanceType;
	}
	public Object getConfigVersionId() {
		return configVersionId;
	}
	public void setConfigVersionId(Object configVersionId) {
		this.configVersionId = configVersionId;
	}
	public RetrieveLEICCILResponse getResponseString() {
		return responseString;
	}
	public void setResponseString(RetrieveLEICCILResponse responseString) {
		this.responseString = responseString;
	}
	
}
