package com.aurionpro.clims.rest.dto;

public class PartySubLineDetailsRestRequestDTO {
	
	private static final long serialVersionUID = -114309476199266725L;
	
	
	private String subLinePartyId;	
	private String guaranteedAmt;
	
	
	public String getGuaranteedAmt() {
		return guaranteedAmt;
	}
	public void setGuaranteedAmt(String guaranteedAmt) {
		this.guaranteedAmt = guaranteedAmt;
	}
	public String getSubLinePartyId() {
		return subLinePartyId;
	}
	public void setSubLinePartyId(String subLinePartyId) {
		this.subLinePartyId = subLinePartyId;
	}	

	
}