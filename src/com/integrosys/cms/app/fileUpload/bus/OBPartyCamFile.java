package com.integrosys.cms.app.fileUpload.bus;

import java.util.Date;


public class OBPartyCamFile extends OBCommonFile {

	public OBPartyCamFile(){
		super ();
	}

	private String partyId;
	private Date camDate;
	private Date camLoginDate;
	private Integer ramRating;
	private Integer ramRatingYear;
	private Long customerRamId;
	private Date camExpiryDate;
	private Date camExtensionDate;
	private String ramType;
	private Date ramFinalizationDate;
	private String fundedAmount;
	private String nonfundedAmount; //MORATORIUM REGULATORY UPLOAD
	private String rmEmployeeCode;
	
	//MORATORIUM REGULATORY UPLOAD
	public String getNonfundedAmount() {
		return nonfundedAmount;
	}
	public void setNonfundedAmount(String nonfundedAmount) {
		this.nonfundedAmount = nonfundedAmount;
	}
	public String getFundedAmount() {
		return fundedAmount;
	}
	public void setFundedAmount(String fundedAmount) {
		this.fundedAmount = fundedAmount;
	}
	public String getPartyId() {
		return partyId;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	public Date getCamDate() {
		return camDate;
	}
	public void setCamDate(Date camDate) {
		this.camDate = camDate;
	}
	public Date getCamLoginDate() {
		return camLoginDate;
	}
	public void setCamLoginDate(Date camLoginDate) {
		this.camLoginDate = camLoginDate;
	}
	public Integer getRamRating() {
		return ramRating;
	}
	public void setRamRating(Integer ramRating) {
		this.ramRating = ramRating;
	}
	public Integer getRamRatingYear() {
		return ramRatingYear;
	}
	public void setRamRatingYear(Integer ramRatingYear) {
		this.ramRatingYear = ramRatingYear;
	}
	public Long getCustomerRamId() {
		return customerRamId;
	}
	public void setCustomerRamId(Long customerRamId) {
		this.customerRamId = customerRamId;
	}
	public Date getCamExpiryDate() {
		return camExpiryDate;
	}
	public void setCamExpiryDate(Date camExpiryDate) {
		this.camExpiryDate = camExpiryDate;
	}
	public Date getCamExtensionDate() {
		return camExtensionDate;
	}
	public void setCamExtensionDate(Date camExtensionDate) {
		this.camExtensionDate = camExtensionDate;
	}
	public String getRamType() {
		return ramType;
	}
	public void setRamType(String ramType) {
		this.ramType = ramType;
	}
	public Date getRamFinalizationDate() {
		return ramFinalizationDate;
	}
	public void setRamFinalizationDate(Date ramFinalizationDate) {
		this.ramFinalizationDate = ramFinalizationDate;
	}
	public String getRmEmployeeCode() {
		return rmEmployeeCode;
	}
	public void setRmEmployeeCode(String rmEmployeeCode) {
		this.rmEmployeeCode = rmEmployeeCode;
	}

}
