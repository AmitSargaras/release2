package com.integrosys.cms.app.json.party.dto;

import com.fasterxml.jackson.annotation.JsonProperty; 
public class RequestString{
    @JsonProperty("PartyDetails") 
    public PartyDetails partyDetails;

	public PartyDetails getPartyDetails() {
		return partyDetails;
	}

	public void setPartyDetails(PartyDetails partyDetails) {
		this.partyDetails = partyDetails;
	}
}
