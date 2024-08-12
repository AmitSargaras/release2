package com.integrosys.cms.app.json.party.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PartyRootRequest {
	 @JsonProperty("FetchFacilityDetailsRequestDTO") 
	    public FetchFacilityDetailsRequestDTO fetchFacilityDetailsRequestDTO;
		public SessionContext sessionContext;

	    
	    public FetchFacilityDetailsRequestDTO getFetchFacilityDetailsRequestDTO() {
			return fetchFacilityDetailsRequestDTO;
		}
		public void setFetchFacilityDetailsRequestDTO(FetchFacilityDetailsRequestDTO fetchFacilityDetailsRequestDTO) {
			this.fetchFacilityDetailsRequestDTO = fetchFacilityDetailsRequestDTO;
		}
		@JsonProperty("sessionContext") 
		public SessionContext getSessionContext() {
			return sessionContext;
		}
		public void setSessionContext(SessionContext sessionContext) {
			this.sessionContext = sessionContext;
		}
}
