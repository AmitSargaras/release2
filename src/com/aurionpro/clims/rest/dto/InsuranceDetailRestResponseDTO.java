/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.aurionpro.clims.rest.dto;

/**
 * Describe this class. Purpose: To set get and set method for the value needed
 * by AA Detail Description: Have set and get method to store the screen value
 * and get the value from other command class
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name$
 */


public class InsuranceDetailRestResponseDTO {
	
	
	private String insurancePolicyID;
	
	private String insuranceUniqueID;
	
	private String insuranceStatus;
	//For Recieved
	private String policyNo;
	private String coverNoteNumber;
	private String insuranceCompanyName;
	private String insuranceCompanyNameId;
	private String currencyCode;
	private String typeOfPerils1;
	private String typeOfPerils1Code;
	private String insurableAmount;
	private String insuredAmount;
	private String receivedDate;
	private String effectiveDate;
	private String expiryDate;
	private String insurancePremium;
	private String nonScheme_Scheme; //defaulted
	private String address;
	private String remark1;
	private String remark2;
	private String insuredAgainst;

	//For Waived
	private String creditApprover;
	private String creditApproverCode;
	private String waivedDate;
	
	//For Deffered
	private String originalTargetDate;
	private String nextDueDate;
	private String dateDeferred;
	
	
	private String insurerName;

	private String insuranceType;

		public String getInsurancePolicyID() {
		return insurancePolicyID;
	}

	public void setInsurancePolicyID(String insurancePolicyID) {
		this.insurancePolicyID = insurancePolicyID;
	}

	public String getInsuranceUniqueID() {
		return insuranceUniqueID;
	}

	public void setInsuranceUniqueID(String insuranceUniqueID) {
		this.insuranceUniqueID = insuranceUniqueID;
	}

	public String getInsuranceStatus() {
		return insuranceStatus;
	}

	public void setInsuranceStatus(String insuranceStatus) {
		this.insuranceStatus = insuranceStatus;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getCoverNoteNumber() {
		return coverNoteNumber;
	}

	public void setCoverNoteNumber(String coverNoteNumber) {
		this.coverNoteNumber = coverNoteNumber;
	}

	public String getInsuranceCompanyName() {
		return insuranceCompanyName;
	}

	public void setInsuranceCompanyName(String insuranceCompanyName) {
		this.insuranceCompanyName = insuranceCompanyName;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getTypeOfPerils1() {
		return typeOfPerils1;
	}

	public void setTypeOfPerils1(String typeOfPerils1) {
		this.typeOfPerils1 = typeOfPerils1;
	}

	public String getInsurableAmount() {
		return insurableAmount;
	}

	public void setInsurableAmount(String insurableAmount) {
		this.insurableAmount = insurableAmount;
	}

	public String getInsuredAmount() {
		return insuredAmount;
	}

	public void setInsuredAmount(String insuredAmount) {
		this.insuredAmount = insuredAmount;
	}

	public String getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getInsurancePremium() {
		return insurancePremium;
	}

	public void setInsurancePremium(String insurancePremium) {
		this.insurancePremium = insurancePremium;
	}

	public String getNonScheme_Scheme() {
		return nonScheme_Scheme;
	}

	public void setNonScheme_Scheme(String nonScheme_Scheme) {
		this.nonScheme_Scheme = nonScheme_Scheme;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getInsuredAgainst() {
		return insuredAgainst;
	}

	public void setInsuredAgainst(String insuredAgainst) {
		this.insuredAgainst = insuredAgainst;
	}

	public String getCreditApprover() {
		return creditApprover;
	}

	public void setCreditApprover(String creditApprover) {
		this.creditApprover = creditApprover;
	}

	public String getWaivedDate() {
		return waivedDate;
	}

	public void setWaivedDate(String waivedDate) {
		this.waivedDate = waivedDate;
	}

	public String getOriginalTargetDate() {
		return originalTargetDate;
	}

	public void setOriginalTargetDate(String originalTargetDate) {
		this.originalTargetDate = originalTargetDate;
	}

	public String getNextDueDate() {
		return nextDueDate;
	}

	public void setNextDueDate(String nextDueDate) {
		this.nextDueDate = nextDueDate;
	}

	public String getDateDeferred() {
		return dateDeferred;
	}

	public void setDateDeferred(String dateDeferred) {
		this.dateDeferred = dateDeferred;
	}

	public String getInsurerName() {
		return insurerName;
	}

	public void setInsurerName(String insurerName) {
		this.insurerName = insurerName;
	}

	public String getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(String insuranceType) {
		this.insuranceType = insuranceType;
	}

	public String getTypeOfPerils1Code() {
		return typeOfPerils1Code;
	}

	public void setTypeOfPerils1Code(String typeOfPerils1Code) {
		this.typeOfPerils1Code = typeOfPerils1Code;
	}

	public String getCreditApproverCode() {
		return creditApproverCode;
	}

	public void setCreditApproverCode(String creditApproverCode) {
		this.creditApproverCode = creditApproverCode;
	}

	public String getInsuranceCompanyNameId() {
		return insuranceCompanyNameId;
	}

	public void setInsuranceCompanyNameId(String insuranceCompanyNameId) {
		this.insuranceCompanyNameId = insuranceCompanyNameId;
	}
	
	
	
}