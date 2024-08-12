package com.integrosys.cms.app.json.party.dto;

public class PartyRootResponse {
	    public Status status;
	    public Object maintenanceType;
	    public Object configVersionId;
	    public ResponseString responseString;
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
		
}
