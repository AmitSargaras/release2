package com.integrosys.cms.app.json.party.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public class RetrieveScmPartyRequest {
	
	@JsonProperty("uniqueReferenceID")
	private String uniqueReferenceID;
	@JsonProperty("partyId")
	private String partyId;
	@JsonProperty("status")
	private String status ;
	@JsonProperty("partyName")
	private String partyName;
	@JsonProperty("vertical")
	private String vertical;
	@JsonProperty("constitution")
	private String constitution;
	@JsonProperty("msme")
	private String msme;
	@JsonProperty("pan")
	private String pan;
	@JsonProperty("industryType")
	private String industryType;
	@JsonProperty("typeOfActivity")
	private String typeOfActivity;
	@JsonProperty("rm")
	private String rm;
	@JsonProperty("captialLine")
	private String captialLine;
	@JsonProperty("riskRating")
	private String riskRating;
	@JsonProperty("cin")
	private String cin;
	@JsonProperty("dateOfIncorporation")
	private String dateOfIncorporation;
	@JsonProperty("wilfulDefaultStatus")
	private String wilfulDefaultStatus;
	@JsonProperty("dateWilfulDefaultStatus")
	private String dateWilfulDefaultStatus;
	@JsonProperty("suitField")
	private String suitField;
	@JsonProperty("suitAmount")
	private String suitAmount;
	@JsonProperty("dateOfSuit")
	private String dateOfSuit;
	@JsonProperty("addressType")
	private String addressType;
	@JsonProperty("address1")
	private String address1;
	@JsonProperty("address2")
	private String address2;
	@JsonProperty("address3")
	private String address3;
	@JsonProperty("phone")
	private String phone;
	@JsonProperty("mobile")
	private String mobile;
	@JsonProperty("city")
	private String city;
	@JsonProperty("region")
	private String region;
	@JsonProperty("state")
	private String state;
	@JsonProperty("country")
	private String country;
	@JsonProperty("fax")
	private String fax;
	@JsonProperty("pincode")
	private String pincode;
	@JsonProperty("email")
	private String email; 
	
	public String getUniqueReferenceID() {
		return uniqueReferenceID;
	}
	public void setUniqueReferenceID(String uniqueReferenceID) {
		this.uniqueReferenceID = uniqueReferenceID;
	}
	
	public String getPartyId() {
		return partyId;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public String getVertical() {
		return vertical;
	}
	public void setVertical(String vertical) {
		this.vertical = vertical;
	}
	public String getConstitution() {
		return constitution;
	}
	public void setConstitution(String constitution) {
		this.constitution = constitution;
	}
	public String getMsme() {
		return msme;
	}
	public void setMsme(String msme) {
		this.msme = msme;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getIndustryType() {
		return industryType;
	}
	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}
	public String getTypeOfActivity() {
		return typeOfActivity;
	}
	public void setTypeOfActivity(String typeOfActivity) {
		this.typeOfActivity = typeOfActivity;
	}
	public String getRm() {
		return rm;
	}
	public void setRm(String rm) {
		this.rm = rm;
	}
	public String getCaptialLine() {
		return captialLine;
	}
	public void setCaptialLine(String captialLine) {
		this.captialLine = captialLine;
	}
	public String getRiskRating() {
		return riskRating;
	}
	public void setRiskRating(String riskRating) {
		this.riskRating = riskRating;
	}
	public String getCin() {
		return cin;
	}
	public void setCin(String cin) {
		this.cin = cin;
	}
	public String getDateOfIncorporation() {
		return dateOfIncorporation;
	}
	public void setDateOfIncorporation(String dateOfIncorporation) {
		this.dateOfIncorporation = dateOfIncorporation;
	}
	public String getWilfulDefaultStatus() {
		return wilfulDefaultStatus;
	}
	public void setWilfulDefaultStatus(String wilfulDefaultStatus) {
		this.wilfulDefaultStatus = wilfulDefaultStatus;
	}
	public String getDateWilfulDefaultStatus() {
		return dateWilfulDefaultStatus;
	}
	public void setDateWilfulDefaultStatus(String dateWilfulDefaultStatus) {
		this.dateWilfulDefaultStatus = dateWilfulDefaultStatus;
	}
	public String getSuitField() {
		return suitField;
	}
	public void setSuitField(String suitField) {
		this.suitField = suitField;
	}
	public String getSuitAmount() {
		return suitAmount;
	}
	public void setSuitAmount(String suitAmount) {
		this.suitAmount = suitAmount;
	}
	public String getDateOfSuit() {
		return dateOfSuit;
	}
	public void setDateOfSuit(String dateOfSuit) {
		this.dateOfSuit = dateOfSuit;
	}
	public String getAddressType() {
		return addressType;
	}
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getAddress3() {
		return address3;
	}
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
}
