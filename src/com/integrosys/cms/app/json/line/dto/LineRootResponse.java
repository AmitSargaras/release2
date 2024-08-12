package com.integrosys.cms.app.json.line.dto;

public class LineRootResponse {
	public Status status;
    public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
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
	public ResponseString getResponseString() {
		return responseString;
	}
	public void setResponseString(ResponseString responseString) {
		this.responseString = responseString;
	}
	public Object maintenanceType;
    public Object configVersionId;
    public ResponseString responseString;
}
