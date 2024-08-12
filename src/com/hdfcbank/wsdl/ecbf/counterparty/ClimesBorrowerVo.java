package com.hdfcbank.wsdl.ecbf.counterparty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClimesBorrowerVo", propOrder = {
	    "address1",
	    "address2",
	    "assetClassification",
	    "bulkBorrowerName",
	    "camDate",
	    "camExpiryDate",
	    "camNumber",
	    "camSRmExpiryDate",
	    "camSanctionedAmount",
	    "camType",
	    "constitution",
	    "contactNo",
	    "dateOfBirth",
	    "dateOfIncorporation",
	    "emailId",
	    "location",
	    "panNumber",
	    "partyId",
	    "pinCode",
	    "presentTurnOver",
	    "previousTurnOver",
	    "ramRating",
	    "rmName",
	    "segmentName",
	    "status",
	    "transactionrefNum"
	})
public class ClimesBorrowerVo {

	@XmlElement(name = "address1", nillable = true, required = true)
    protected String address1;
    
	@XmlElement(name = "address2", nillable = true, required = false)
	protected String address2;
	
	@XmlElement(name = "assetClassification", nillable = true, required = true)
    protected String assetClassification;
	
	@XmlElement(name = "bulkBorrowerName", nillable = true, required = true)
    protected String bulkBorrowerName;
	
	@XmlElement(name = "camDate", nillable = true, required = true)
    protected String camDate;
	
	@XmlElement(name = "camExpiryDate", nillable = true, required = true)
    protected String camExpiryDate;
	
	@XmlElement(name = "camNumber", nillable = true, required = true)
    protected String camNumber;
    
	@XmlElement(name = "camSRmExpiryDate", nillable = true, required = true)
	protected String camSRmExpiryDate;
	
	@XmlElement(name = "camSanctionedAmount", nillable = true, required = true)
    protected String camSanctionedAmount;
    
	@XmlElement(name = "camType", nillable = true, required = true)
	protected String camType;
    
	@XmlElement(name = "constitution", nillable = true, required = true)
    protected String constitution;

	@XmlElement(name = "contactNo", nillable = true, required = true)
	protected String contactNo;
	
	@XmlElement(name = "dateOfBirth", nillable = true, required = true)
    protected String dateOfBirth;
	
	@XmlElement(name = "dateOfIncorporation", nillable = true, required = true)
    protected String dateOfIncorporation;
	
	@XmlElement(name = "emailId", nillable = true, required = true)
    protected String emailId;
	
	@XmlElement(name = "location", nillable = true, required = true)
    protected String location;
	
	@XmlElement(name = "panNumber", nillable = true, required = true)
    protected String panNumber;
	
	@XmlElement(name = "partyId", nillable = true, required = true)
    protected String partyId;
	
	@XmlElement(name = "pinCode", nillable = true, required = true)
    protected String pinCode;
	
	@XmlElement(name = "presentTurnOver", nillable = true, required = true)
    protected String presentTurnOver;
	
	@XmlElement(name = "previousTurnOver", nillable = true, required = true)
    protected String previousTurnOver;
	
	@XmlElement(name = "ramRating", nillable = true, required = true)
    protected String ramRating;
	
	@XmlElement(name = "rmName", nillable = true, required = true)
    protected String rmName;
	
	@XmlElement(name = "segmentName", nillable = true, required = true)
    protected String segmentName;
	
	@XmlElement(name = "status", nillable = true, required = true)
	protected String status;
	
	@XmlElement(name = "transactionrefNum", nillable = true, required = true)
    protected String transactionrefNum;
	
	@XmlTransient
	protected String assetClassificationValue;
	
	@XmlTransient
	protected String constitutionValue;
	
	@XmlTransient
	protected String locationValue;
	
	@XmlTransient
	protected String segmentNameValue;
	
	@XmlTransient
	protected String ramRatingValue;

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

	public String getAssetClassification() {
		return assetClassification;
	}

	public void setAssetClassification(String assetClassification) {
		this.assetClassification = assetClassification;
	}

	public String getBulkBorrowerName() {
		return bulkBorrowerName;
	}

	public void setBulkBorrowerName(String bulkBorrowerName) {
		this.bulkBorrowerName = bulkBorrowerName;
	}

	public String getCamDate() {
		return camDate;
	}

	public void setCamDate(String camDate) {
		this.camDate = camDate;
	}

	public String getCamExpiryDate() {
		return camExpiryDate;
	}

	public void setCamExpiryDate(String camExpiryDate) {
		this.camExpiryDate = camExpiryDate;
	}

	public String getCamNumber() {
		return camNumber;
	}

	public void setCamNumber(String camNumber) {
		this.camNumber = camNumber;
	}

	public String getCamSRmExpiryDate() {
		return camSRmExpiryDate;
	}

	public void setCamSRmExpiryDate(String camSRmExpiryDate) {
		this.camSRmExpiryDate = camSRmExpiryDate;
	}

	public String getCamSanctionedAmount() {
		return camSanctionedAmount;
	}

	public void setCamSanctionedAmount(String camSanctionedAmount) {
		this.camSanctionedAmount = camSanctionedAmount;
	}

	public String getCamType() {
		return camType;
	}

	public void setCamType(String camType) {
		this.camType = camType;
	}

	public String getConstitution() {
		return constitution;
	}

	public void setConstitution(String constitution) {
		this.constitution = constitution;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getDateOfIncorporation() {
		return dateOfIncorporation;
	}

	public void setDateOfIncorporation(String dateOfIncorporation) {
		this.dateOfIncorporation = dateOfIncorporation;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getPresentTurnOver() {
		return presentTurnOver;
	}

	public void setPresentTurnOver(String presentTurnOver) {
		this.presentTurnOver = presentTurnOver;
	}

	public String getPreviousTurnOver() {
		return previousTurnOver;
	}

	public void setPreviousTurnOver(String previousTurnOver) {
		this.previousTurnOver = previousTurnOver;
	}

	public String getRamRating() {
		return ramRating;
	}

	public void setRamRating(String ramRating) {
		this.ramRating = ramRating;
	}

	public String getRmName() {
		return rmName;
	}

	public void setRmName(String rmName) {
		this.rmName = rmName;
	}

	public String getSegmentName() {
		return segmentName;
	}

	public void setSegmentName(String segmentName) {
		this.segmentName = segmentName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTransactionrefNum() {
		return transactionrefNum;
	}

	public void setTransactionrefNum(String transactionrefNum) {
		this.transactionrefNum = transactionrefNum;
	}
	
	public String getAssetClassificationValue() {
		return assetClassificationValue;
	}

	public void setAssetClassificationValue(String assetClassificationValue) {
		this.assetClassificationValue = assetClassificationValue;
	}

	public String getConstitutionValue() {
		return constitutionValue;
	}

	public void setConstitutionValue(String constitutionValue) {
		this.constitutionValue = constitutionValue;
	}

	public String getLocationValue() {
		return locationValue;
	}

	public void setLocationValue(String locationValue) {
		this.locationValue = locationValue;
	}

	public String getSegmentNameValue() {
		return segmentNameValue;
	}

	public void setSegmentNameValue(String segmentNameValue) {
		this.segmentNameValue = segmentNameValue;
	}

	public String getRamRatingValue() {
		return ramRatingValue;
	}

	public void setRamRatingValue(String ramRatingValue) {
		this.ramRatingValue = ramRatingValue;
	}
	
}