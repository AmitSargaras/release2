package com.integrosys.cms.app.json.line.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LineRootRequest {
    @JsonProperty("FetchLimitDetailsRequestDTO") 
    public FetchLimitDetailsRequestDTO fetchLimitDetailsRequestDTO;
	public SessionContext sessionContext;

    public FetchLimitDetailsRequestDTO getFetchLimitDetailsRequestDTO() {
		return fetchLimitDetailsRequestDTO;
	}
	public void setFetchLimitDetailsRequestDTO(FetchLimitDetailsRequestDTO fetchLimitDetailsRequestDTO) {
		this.fetchLimitDetailsRequestDTO = fetchLimitDetailsRequestDTO;
	}
	@JsonProperty("sessionContext") 
	public SessionContext getSessionContext() {
		return sessionContext;
	}
	public void setSessionContext(SessionContext sessionContext) {
		this.sessionContext = sessionContext;
	}
}
