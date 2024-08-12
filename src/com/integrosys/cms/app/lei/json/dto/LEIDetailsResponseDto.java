package com.integrosys.cms.app.lei.json.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class LEIDetailsResponseDto {
	
	@JsonProperty("LEINumber")
	private String leiNumber;
	@JsonProperty("LegalName")
	private String legalName;
	@JsonProperty("NextRenewalDate")
	private String nextRenewalDate;
	@JsonProperty("LegalForm")
	private String legalForm;
	@JsonProperty("RegistrationStatus")
	private String registrationStatus;
	
	
	public String getLeiNumber() {
		return leiNumber;
	}
	public void setLeiNumber(String leiNumber) {
		this.leiNumber = leiNumber;
	}
	public String getLegalName() {
		return legalName;
	}
	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}
	public String getNextRenewalDate() {
		return nextRenewalDate;
	}
	public void setNextRenewalDate(String nextRenewalDate) {
		this.nextRenewalDate = nextRenewalDate;
	}
	public String getLegalForm() {
		return legalForm;
	}
	public void setLegalForm(String legalForm) {
		this.legalForm = legalForm;
	}
	public String getRegistrationStatus() {
		return registrationStatus;
	}
	public void setRegistrationStatus(String registrationStatus) {
		this.registrationStatus = registrationStatus;
	}
	@Override
	public String toString() {
		return "{LEINumber:"+leiNumber+",LegalName:"+legalName+",NextRenewalDate:"+nextRenewalDate+",LegalForm:"+legalForm+",RegistrationStatus:"+registrationStatus+"}";
	}
	
}
