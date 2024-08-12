package com.integrosys.cms.app.json.party.dto;
 
import java.util.List; 
public class FetchFacilityDetailsRequestDTO{
    public RequestString requestString;
    public List<HeaderParam> headerParams;
    
    public RequestString getRequestString() {
		return requestString;
	}
	public void setRequestString(RequestString requestString) {
		this.requestString = requestString;
	}
	public List<HeaderParam> getHeaderParams() {
		return headerParams;
	}
	public void setHeaderParams(List<HeaderParam> headerParams) {
		this.headerParams = headerParams;
	}
	
}
