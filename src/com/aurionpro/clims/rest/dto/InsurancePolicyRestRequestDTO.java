/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/OBCollateral.java,v 1.35 2006/09/15 08:30:10 hshii Exp $
 */
package com.aurionpro.clims.rest.dto;

import java.math.BigDecimal;

import org.apache.struts.action.ActionErrors;

import com.integrosys.base.businfra.contact.IAddress;
import com.integrosys.cms.app.common.constant.ICMSConstant;
/**
 * This class represents a Insurance entity.
 * 
 * @author $Author: Manoj M $<br>
 * @version $Revision: 1.35 $
 * @since $String: 2023/04/01 08:30:10 $ Tag: $Name: $
 */
/**
 * @author manoj.malunjkar
 *
 */
/**
 * @author manoj.malunjkar
 *
 */
/**
 * @author manoj.malunjkar
 *
 */
public class InsurancePolicyRestRequestDTO {

	
	
	/**
	 * 
	 */
	
	private ActionErrors errors;
	
	private String actionFlag;
	private String insurancePolicyID;
	public String getActionFlag() {
		return actionFlag;
	}

	public void setActionFlag(String actionFlag) {
		this.actionFlag = actionFlag;
	}

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

	public String getNonScheme_Scheme() {
		return nonScheme_Scheme;
	}

	public void setNonScheme_Scheme(String nonScheme_Scheme) {
		this.nonScheme_Scheme = nonScheme_Scheme;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private String insuranceUniqueID;
	private String insuranceStatus;
	//For Recieved
	private String policyNo;
	private String coverNoteNumber;
	private String insuranceCompanyName;
	private String currencyCode;
	private String typeOfPerils1;
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
	private String waivedDate;
	
	//For Deffered
	private String originalTargetDate;
	private String nextDueDate;
	private String dateDeferred;
	
	
	//creditApprover
	
	//For Pending
	//originalTargetDate
	
private String acType; //set Loan
private String autoDebit;// set N
	
	private static final long serialVersionUID = -5066190587350276737L;

	private static final String ID_PREFIX = "INSR";

	private static final char ID_FILLER = '0';

	private static int ID_MAX_LENGTH = 10;

	/**
	 * Generate new id for insurance id.
	 * 
	 * @param index of type int
	 * @return new generated id
	 */
	public static String generateNewID(int index) {
		return generateNewID(ID_PREFIX, ID_FILLER, ID_MAX_LENGTH, index);
	}

	/**
	 * Helper method to generate new id for insurance id
	 * 
	 * @param prefix of type int
	 * @param filler of type char
	 * @param maxLength of type int
	 * @param index of type int
	 * @return String
	 */
	private static String generateNewID(String prefix, char filler, int maxLength, int index) {
		StringBuffer buf = new StringBuffer();
		buf.append(prefix);
		String indexStr = Integer.toString(index);
		int indexLength = indexStr.length();
		while (buf.length() + indexLength < maxLength) {
			buf.append(filler);
		}
		buf.append(indexStr);
		return buf.toString();
	}

	private String insurerName;

	private String insuranceType;

	

	

	private String insuredAddress1;

	private String insuredAddress2;

	private String insuredAddress3;

	private String insuredAddress4;

	private String category;

	private String refID;

	private String status = ICMSConstant.STATE_ACTIVE;

	private String documentNo;

	private Long lmtProfileId;

	

	private String conversionCurrency;

	private String insIssueDate;

	private Double insuranceExchangeRate;

	

	private String debitingACNo;

	
	private String takafulCommission;

	private String newAmtInsured;

	private String endorsementDate;

	private String buildingOccupation;

	private String buildingType;

	private String natureOfWork;

	private String wall;

	private Integer numberOfStorey;

	private String extensionWalls;

	private String endorsementCode;

	private String policyCustodian;

	private String insuranceClaimDate;

	private String typeOfFloor;

	

	private String typeOfPerils2;

	private String typeOfPerils3;

	private String typeOfPerils4;

	private String typeOfPerils5;



	private String remark3;

	private String bankCustomerArrange;

	private BigDecimal grossPremium;

	private String roof;

	private String extensionRoof;

	private BigDecimal nettPermByBorrower;

	private BigDecimal nettPermToInsCo;

	private BigDecimal commissionEarned;

	private BigDecimal stampDuty;

	private String policySeq;

	private Long losInsurancePolicyId;

	private BigDecimal rebateAmount;

	private BigDecimal serviceTaxPercentage;

	private BigDecimal serviceTaxAmount;
	
	
	

	public String getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}

	/**
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	

	public String getAcType() {
		return acType;
	}

	/**
	 * Get insurance address.
	 * 
	 * @return IAddress
	 */
	public String getAddress() {
		return address;
	}

	public String getAutoDebit() {
		return autoDebit;
	}

	public String getBankCustomerArrange() {
		return bankCustomerArrange;
	}

	public String getBuildingOccupation() {
		return buildingOccupation;
	}

	public String getBuildingType() {
		return buildingType;
	}

	/**
	 * Get category which this insurance belong to.
	 * 
	 * @return String
	 */
	public String getCategory() {
		return category;
	}

	public BigDecimal getCommissionEarned() {
		return commissionEarned;
	}

	public String getConversionCurrency() {
		return conversionCurrency;
	}

	public String getCoverNoteNumber() {
		return coverNoteNumber;
	}

	/**
	 * Get insurance currency code.
	 * 
	 * @return String
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	public String getDebitingACNo() {
		return debitingACNo;
	}

	/**
	 * Get insurance document number.
	 * 
	 * @return String
	 */
	public String getDocumentNo() {
		return documentNo;
	}

	/**
	 * Get effective String.
	 * 
	 * @return String
	 */
	public String getEffectiveDate() {
		return effectiveDate;
	}

	public String getEndorsementCode() {
		return endorsementCode;
	}

	public String getEndorsementDate() {
		return endorsementDate;
	}

	/**
	 * Get expiry String of insurance.
	 * 
	 * @return String
	 */
	public String getExpiryDate() {
		return expiryDate;
	}

	public String getExtensionRoof() {
		return extensionRoof;
	}

	public String getExtensionWalls() {
		return extensionWalls;
	}

	public BigDecimal getGrossPremium() {
		return grossPremium;
	}

	public String getInsIssueDate() {
		return insIssueDate;
	}

	/**
	 * Get insurable String.
	 * 
	 * @return String
	 */
	public String getInsurableAmount() {
		return insurableAmount;
	}

	public String getInsuranceClaimDate() {
		return insuranceClaimDate;
	}

	public String getInsuranceCompanyName() {
		return insuranceCompanyName;
	}

	public Double getInsuranceExchangeRate() {
		return insuranceExchangeRate;
	}

	

	public String getInsurancePremium() {
		return insurancePremium;
	}

	/**
	 * Get insurance type.
	 * 
	 * @return String
	 */
	public String getInsuranceType() {
		return insuranceType;
	}

	public String getInsuredAddress1() {
		return insuredAddress1;
	}

	public String getInsuredAddress2() {
		return insuredAddress2;
	}

	public String getInsuredAddress3() {
		return insuredAddress3;
	}

	public String getInsuredAddress4() {
		return insuredAddress4;
	}

	/**
	 * Get insured against.
	 * 
	 * @return String
	 */
	public String getInsuredAgainst() {
		return insuredAgainst;
	}

	/**
	 * Get insured String.
	 * 
	 * @return String
	 */
	public String getInsuredAmount() {
		return insuredAmount;
	}

	/**
	 * Get insurer's name.
	 * 
	 * @return String
	 */
	public String getInsurerName() {
		return insurerName;
	}

	/**
	 * Get insurance Limit Profile Id.
	 * 
	 * @return String
	 */
	public Long getLmtProfileId() {
		return lmtProfileId;
	}

	public Long getLosInsurancePolicyId() {
		return losInsurancePolicyId;
	}

	public String getNatureOfWork() {
		return natureOfWork;
	}

	public BigDecimal getNettPermByBorrower() {
		return nettPermByBorrower;
	}

	public BigDecimal getNettPermToInsCo() {
		return nettPermToInsCo;
	}

	public String getNewAmtInsured() {
		return newAmtInsured;
	}

	public String getNonSchemeScheme() {
		return nonScheme_Scheme;
	}

	public Integer getNumberOfStorey() {
		return numberOfStorey;
	}

	public String getPolicyCustodian() {
		return policyCustodian;
	}

	/**
	 * Get insurance policy number.
	 * 
	 * @return String
	 */
	public String getPolicyNo() {
		return policyNo;
	}

	public String getPolicySeq() {
		return policySeq;
	}

	public BigDecimal getRebateAmount() {
		return this.rebateAmount;
	}

	/**
	 * Get common reference id between actual and staging data.
	 * 
	 * @return String
	 */
	public String getRefID() {
		return refID;
	}

	public String getRemark1() {
		return remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public String getRemark3() {
		return remark3;
	}

	public String getRoof() {
		return roof;
	}

	public BigDecimal getServiceTaxAmount() {
		return this.serviceTaxAmount;
	}

	public BigDecimal getServiceTaxPercentage() {
		return this.serviceTaxPercentage;
	}

	public BigDecimal getStampDuty() {
		return stampDuty;
	}

	/**
	 * Get collateral status, ICMSConstant.STATE_ACTIVE or
	 * ICMSConstant.STATE_DELETED.
	 * 
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	public String getTakafulCommission() {
		return takafulCommission;
	}

	public String getTypeOfFloor() {
		return typeOfFloor;
	}

	public String getTypeOfPerils1() {
		return typeOfPerils1;
	}

	public String getTypeOfPerils2() {
		return typeOfPerils2;
	}

	public String getTypeOfPerils3() {
		return typeOfPerils3;
	}

	public String getTypeOfPerils4() {
		return typeOfPerils4;
	}

	public String getTypeOfPerils5() {
		return typeOfPerils5;
	}

	public String getWall() {
		return wall;
	}

	/**
	 * Return the hash code
	 * 
	 * @return int
	 */
	public int hashCode() {
		String hash = String.valueOf(insurancePolicyID);
		return hash.hashCode();
	}

	public void setAcType(String acType) {
		this.acType = acType;
	}

	/**
	 * Set insurance address.
	 * 
	 * @param address of type IAddress
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	public void setAutoDebit(String autoDebit) {
		this.autoDebit = autoDebit;
	}

	public void setBankCustomerArrange(String bankCustomerArrange) {
		this.bankCustomerArrange = bankCustomerArrange;
	}

	public void setBuildingOccupation(String buildingOccupation) {
		this.buildingOccupation = buildingOccupation;
	}

	public void setBuildingType(String buildingType) {
		this.buildingType = buildingType;
	}

	/**
	 * Set category of this insurance policy.
	 * 
	 * @param category of type String
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	public void setCommissionEarned(BigDecimal commissionEarned) {
		this.commissionEarned = commissionEarned;
	}

	public void setConversionCurrency(String conversionCurrency) {
		this.conversionCurrency = conversionCurrency;
	}

	public void setCoverNoteNumber(String coverNoteNumber) {
		this.coverNoteNumber = coverNoteNumber;
	}

	/**
	 * Set insurance currency code.
	 * 
	 * @param currencyCode of type String
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public void setDebitingACNo(String debitingACNo) {
		this.debitingACNo = debitingACNo;
	}

	/**
	 * Set document policy number.
	 * 
	 * @param documentNo of type String
	 */
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	/**
	 * Set effective String.
	 * 
	 * @param effectiveDate of type String
	 */
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public void setEndorsementCode(String endorsementCode) {
		this.endorsementCode = endorsementCode;
	}

	public void setEndorsementDate(String endorsementDate) {
		this.endorsementDate = endorsementDate;
	}

	/**
	 * Set expiry String of insurance.
	 * 
	 * @param expiryDate of type String
	 */
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;

	}

	public void setExtensionRoof(String extensionRoof) {
		this.extensionRoof = extensionRoof;

	}

	public void setExtensionWalls(String extensionWalls) {
		this.extensionWalls = extensionWalls;
	}

	public void setGrossPremium(BigDecimal grossPremium) {
		this.grossPremium = grossPremium;
	}

	public void setInsIssueDate(String insIssueDate) {
		this.insIssueDate = insIssueDate;
	}

	/**
	 * Set insurable String.
	 * 
	 * @param insurableAmount of type String
	 */
	public void setInsurableAmount(String insurableAmount) {
		this.insurableAmount = insurableAmount;
	}

	public void setInsuranceClaimDate(String insuranceClaimDate) {
		this.insuranceClaimDate = insuranceClaimDate;
	}

	public void setInsuranceCompanyName(String insuranceCompanyName) {
		this.insuranceCompanyName = insuranceCompanyName;
	}

	public void setInsuranceExchangeRate(Double insuranceExchangeRate) {
		this.insuranceExchangeRate = insuranceExchangeRate;
	}



	public void setInsurancePremium(String insurancePremium) {
		this.insurancePremium = insurancePremium;
	}

	/**
	 * Set insurance type.
	 * 
	 * @param insuranceType of type String
	 */
	public void setInsuranceType(String insuranceType) {
		this.insuranceType = insuranceType;
	}

	public void setInsuredAddress1(String insuredAddress1) {
		this.insuredAddress1 = insuredAddress1;
	}

	public void setInsuredAddress2(String insuredAddress2) {
		this.insuredAddress2 = insuredAddress2;
	}

	public void setInsuredAddress3(String insuredAddress3) {
		this.insuredAddress3 = insuredAddress3;
	}

	public void setInsuredAddress4(String insuredAddress4) {
		this.insuredAddress4 = insuredAddress4;
	}

	/**
	 * Set insured against.
	 * 
	 * @param insuredAgainst of type String
	 */
	public void setInsuredAgainst(String insuredAgainst) {
		this.insuredAgainst = insuredAgainst;
	}

	/**
	 * Set insured String.
	 * 
	 * @param insuredAmount of type String
	 */
	public void setInsuredAmount(String insuredAmount) {
		this.insuredAmount = insuredAmount;
	}

	/**
	 * Set insurer's name.
	 * 
	 * @param insurerName of type String
	 */
	public void setInsurerName(String insurerName) {
		this.insurerName = insurerName;
	}

	/**
	 * Set Limit Profile Id.
	 * 
	 * @param Limit Profile Id of type String
	 */
	public void setLmtProfileId(Long lmtProfileId) {
		this.lmtProfileId = lmtProfileId;
	}

	public void setLosInsurancePolicyId(Long losInsurancePolicyId) {
		this.losInsurancePolicyId = losInsurancePolicyId;
	}

	public void setNatureOfWork(String natureOfWork) {
		this.natureOfWork = natureOfWork;
	}

	public void setNettPermByBorrower(BigDecimal nettPermByBorrower) {
		this.nettPermByBorrower = nettPermByBorrower;
	}

	public void setNettPermToInsCo(BigDecimal nettPermToInsCo) {
		this.nettPermToInsCo = nettPermToInsCo;
	}

	public void setNewAmtInsured(String newAmtInsured) {
		this.newAmtInsured = newAmtInsured;
	}

	public void setNonSchemeScheme(String nonSchemeScheme) {
		this.nonScheme_Scheme = nonSchemeScheme;
	}

	public void setNumberOfStorey(Integer numberOfStorey) {
		this.numberOfStorey = numberOfStorey;
	}

	public void setPolicyCustodian(String policyCustodian) {
		this.policyCustodian = policyCustodian;
	}

	/**
	 * Set insurance policy number.
	 * 
	 * @param policyNo of type String
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public void setPolicySeq(String policySeq) {
		this.policySeq = policySeq;
	}

	public void setRebateAmount(BigDecimal rebateAmount) {
		this.rebateAmount = rebateAmount;
	}

	/**
	 * Set common reference id between actual and staging data.
	 * 
	 * @param refID of type String
	 */
	public void setRefID(String refID) {
		this.refID = refID;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}

	public void setRoof(String roof) {
		this.roof = roof;
	}

	public void setServiceTaxAmount(BigDecimal serviceTaxAmount) {
		this.serviceTaxAmount = serviceTaxAmount;
	}

	public void setServiceTaxPercentage(BigDecimal serviceTaxPercentage) {
		this.serviceTaxPercentage = serviceTaxPercentage;
	}

	public void setStampDuty(BigDecimal stampDuty) {
		this.stampDuty = stampDuty;
	}

	/**
	 * Set collateral status, ICMSConstant.STATE_ACTIVE or
	 * ICMSConstant.STATE_DELETED.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public void setTakafulCommission(String takafulCommission) {
		this.takafulCommission = takafulCommission;
	}

	public void setTypeOfFloor(String typeOfFloor) {
		this.typeOfFloor = typeOfFloor;
	}

	public void setTypeOfPerils1(String typeOfPerils1) {
		this.typeOfPerils1 = typeOfPerils1;
	}

	public void setTypeOfPerils2(String typeOfPerils2) {
		this.typeOfPerils2 = typeOfPerils2;
	}

	public void setTypeOfPerils3(String typeOfPerils3) {
		this.typeOfPerils3 = typeOfPerils3;
	}

	public void setTypeOfPerils4(String typeOfPerils4) {
		this.typeOfPerils4 = typeOfPerils4;
	}

	public void setTypeOfPerils5(String typeOfPerils5) {
		this.typeOfPerils5 = typeOfPerils5;
	}

	public void setWall(String wall) {
		this.wall = wall;
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	
	
	private String lastUpdatedBy;
	private String lastApproveBy;
	private String lastUpdatedOn;
	private String lastApproveOn;
	
	 //Uma Khot::Insurance Deferral maintainance
	
	
	public String getInsuranceStatus() {
		return insuranceStatus;
	}

	public void setInsuranceStatus(String insuranceStatus) {
		this.insuranceStatus = insuranceStatus;
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

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getLastApproveBy() {
		return lastApproveBy;
	}

	public void setLastApproveBy(String lastApproveBy) {
		this.lastApproveBy = lastApproveBy;
	}

	public String getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(String lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public String getLastApproveOn() {
		return lastApproveOn;
	}

	public void setLastApproveOn(String lastApproveOn) {
		this.lastApproveOn = lastApproveOn;
	}

	public ActionErrors getErrors() {
		return errors;
	}

	public void setErrors(ActionErrors errors) {
		this.errors = errors;
	}

	

}