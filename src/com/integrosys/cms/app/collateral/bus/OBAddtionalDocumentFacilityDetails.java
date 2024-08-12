/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/OBInsurancePolicy.java,v 1.7 2006/04/11 08:22:03 pratheepa Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.math.BigDecimal;
import java.util.Date;

import com.integrosys.base.businfra.contact.IAddress;
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents an Insurance entity.
 * 
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/04/11 08:22:03 $ Tag: $Name: $
 */
public class OBAddtionalDocumentFacilityDetails implements IAddtionalDocumentFacilityDetails {

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

	private long addDocFacDetID = ICMSConstant.LONG_INVALID_VALUE;

	private String policyNo;

//	private String insurerName;
//
//	private String insuranceType;

	private Date expiryDate;

	private String currencyCode;

//	private Amount insurableAmount;
//
//	private Amount insuredAmount;

	private Date effectiveDate;

//	private String insuredAgainst;

//	private IAddress address;
//
//	private String insuredAddress1;
//
//	private String insuredAddress2;
//
//	private String insuredAddress3;
//
//	private String insuredAddress4;

	private String category;

	private String refID;

	private String status = ICMSConstant.STATE_ACTIVE;

	private String documentNo;

	private Long lmtProfileId;

	private String coverNoteNumber;

	private String conversionCurrency;

//	private Date insIssueDate;
//
//	private Double insuranceExchangeRate;
//
//	private String insuranceCompanyName;
//
//	private String debitingACNo;

	private String acType;

	private String nonScheme_Scheme;

//	private Amount insurancePremium;

	private String autoDebit;

//	private Amount takafulCommission;

//	private Amount newAmtInsured;

	private Date endorsementDate;

	private String buildingOccupation;

	private String buildingType;

	private String natureOfWork;

	private String wall;

	private Integer numberOfStorey;

	private String extensionWalls;

	private String endorsementCode;

	private String policyCustodian;

//	private Date insuranceClaimDate;

//	private String typeOfFloor;
//
//	private String typeOfPerils1;
//
//	private String typeOfPerils2;
//
//	private String typeOfPerils3;
//
//	private String typeOfPerils4;
//
//	private String typeOfPerils5;

	private String remark1;

//	private String remark2;
//
//	private String remark3;

	private String bankCustomerArrange;

	private BigDecimal grossPremium;

	private String roof;

	private String extensionRoof;

	private BigDecimal nettPermByBorrower;

	private BigDecimal nettPermToInsCo;

	private BigDecimal commissionEarned;

	private BigDecimal stampDuty;

	private String policySeq;

//	private Long losInsurancePolicyId;

	private BigDecimal rebateAmount;

	private BigDecimal serviceTaxPercentage;

	private BigDecimal serviceTaxAmount;
	
	private Date receivedDate;
	

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	/**
	 * Default Constructor.
	 */
	public OBAddtionalDocumentFacilityDetails() {
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IInsurancePolicy
	 */
	public OBAddtionalDocumentFacilityDetails(IAddtionalDocumentFacilityDetails obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof OBAddtionalDocumentFacilityDetails)) {
			return false;
		}
		else {
			if (obj.hashCode() == this.hashCode()) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	public String getAcType() {
		return acType;
	}

	/**
	 * Get insurance address.
	 * 
	 * @return IAddress
	 */
//	public IAddress getAddress() {
//		return address;
//	}

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

//	public String getDebitingACNo() {
//		return debitingACNo;
//	}

	/**
	 * Get insurance document number.
	 * 
	 * @return String
	 */
	public String getDocumentNo() {
		return documentNo;
	}

	/**
	 * Get effective date.
	 * 
	 * @return Date
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public String getEndorsementCode() {
		return endorsementCode;
	}

	public Date getEndorsementDate() {
		return endorsementDate;
	}

	/**
	 * Get expiry date of insurance.
	 * 
	 * @return Date
	 */
	public Date getExpiryDate() {
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

//	public Date getInsIssueDate() {
//		return insIssueDate;
//	}

	/**
	 * Get insurable amount.
	 * 
	 * @return Amount
	 */
//	public Amount getInsurableAmount() {
//		return insurableAmount;
//	}

//	public Date getInsuranceClaimDate() {
//		return insuranceClaimDate;
//	}
//
//	public String getInsuranceCompanyName() {
//		return insuranceCompanyName;
//	}
//
//	public Double getInsuranceExchangeRate() {
//		return insuranceExchangeRate;
//	}

	/**
	 * Get insurance id.
	 * 
	 * @return long
	 */
//	public long getAddDocFacDetID() {
//		return addDocFacDetID;
//	}
	
	public long getAddDocFacDetID() {
		return addDocFacDetID;
	}

	public void setAddDocFacDetID(long addDocFacDetID) {
		this.addDocFacDetID = addDocFacDetID;
	}
	

//	public Amount getInsurancePremium() {
//		return insurancePremium;
//	}

	/**
	 * Get insurance type.
	 * 
	 * @return String
	 */
//	public String getInsuranceType() {
//		return insuranceType;
//	}
//
//	public String getInsuredAddress1() {
//		return insuredAddress1;
//	}
//
//	public String getInsuredAddress2() {
//		return insuredAddress2;
//	}
//
//	public String getInsuredAddress3() {
//		return insuredAddress3;
//	}
//
//	public String getInsuredAddress4() {
//		return insuredAddress4;
//	}

	/**
	 * Get insured against.
	 * 
	 * @return String
	 */
//	public String getInsuredAgainst() {
//		return insuredAgainst;
//	}

	/**
	 * Get insured amount.
	 * 
	 * @return Amount
	 */
//	public Amount getInsuredAmount() {
//		return insuredAmount;
//	}

	/**
	 * Get insurer's name.
	 * 
	 * @return String
	 */
//	public String getInsurerName() {
//		return insurerName;
//	}

	/**
	 * Get insurance Limit Profile Id.
	 * 
	 * @return String
	 */
	public Long getLmtProfileId() {
		return lmtProfileId;
	}

//	public Long getLosInsurancePolicyId() {
//		return losInsurancePolicyId;
//	}

	public String getNatureOfWork() {
		return natureOfWork;
	}

	public BigDecimal getNettPermByBorrower() {
		return nettPermByBorrower;
	}

	public BigDecimal getNettPermToInsCo() {
		return nettPermToInsCo;
	}

//	public Amount getNewAmtInsured() {
//		return newAmtInsured;
//	}

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

//	public String getRemark2() {
//		return remark2;
//	}
//
//	public String getRemark3() {
//		return remark3;
//	}

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

//	public Amount getTakafulCommission() {
//		return takafulCommission;
//	}
//
//	public String getTypeOfFloor() {
//		return typeOfFloor;
//	}
//
//	public String getTypeOfPerils1() {
//		return typeOfPerils1;
//	}
//
//	public String getTypeOfPerils2() {
//		return typeOfPerils2;
//	}
//
//	public String getTypeOfPerils3() {
//		return typeOfPerils3;
//	}
//
//	public String getTypeOfPerils4() {
//		return typeOfPerils4;
//	}
//
//	public String getTypeOfPerils5() {
//		return typeOfPerils5;
//	}

	public String getWall() {
		return wall;
	}

	/**
	 * Return the hash code
	 * 
	 * @return int
	 */
	public int hashCode() {
		String hash = String.valueOf(addDocFacDetID);
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
//	public void setAddress(IAddress address) {
//		this.address = address;
//	}

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

//	public void setDebitingACNo(String debitingACNo) {
//		this.debitingACNo = debitingACNo;
//	}

	/**
	 * Set document policy number.
	 * 
	 * @param documentNo of type String
	 */
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	/**
	 * Set effective date.
	 * 
	 * @param effectiveDate of type Date
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public void setEndorsementCode(String endorsementCode) {
		this.endorsementCode = endorsementCode;
	}

	public void setEndorsementDate(Date endorsementDate) {
		this.endorsementDate = endorsementDate;
	}

	/**
	 * Set expiry date of insurance.
	 * 
	 * @param expiryDate of type Date
	 */
	public void setExpiryDate(Date expiryDate) {
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

//	public void setInsIssueDate(Date insIssueDate) {
//		this.insIssueDate = insIssueDate;
//	}

	/**
	 * Set insurable amount.
	 * 
	 * @param insurableAmount of type Amount
	 */
//	public void setInsurableAmount(Amount insurableAmount) {
//		this.insurableAmount = insurableAmount;
//	}
//
//	public void setInsuranceClaimDate(Date insuranceClaimDate) {
//		this.insuranceClaimDate = insuranceClaimDate;
//	}
//
//	public void setInsuranceCompanyName(String insuranceCompanyName) {
//		this.insuranceCompanyName = insuranceCompanyName;
//	}
//
//	public void setInsuranceExchangeRate(Double insuranceExchangeRate) {
//		this.insuranceExchangeRate = insuranceExchangeRate;
//	}

	/**
	 * Set insurance id.
	 * 
	 * @param insurancePolicyID of type long
	 */
//	public void setInsurancePolicyID(long insurancePolicyID) {
//		this.insurancePolicyID = insurancePolicyID;
//	}
//
//	public void setInsurancePremium(Amount insurancePremium) {
//		this.insurancePremium = insurancePremium;
//	}

	/**
	 * Set insurance type.
	 * 
	 * @param insuranceType of type String
	 */
//	public void setInsuranceType(String insuranceType) {
//		this.insuranceType = insuranceType;
//	}
//
//	public void setInsuredAddress1(String insuredAddress1) {
//		this.insuredAddress1 = insuredAddress1;
//	}
//
//	public void setInsuredAddress2(String insuredAddress2) {
//		this.insuredAddress2 = insuredAddress2;
//	}
//
//	public void setInsuredAddress3(String insuredAddress3) {
//		this.insuredAddress3 = insuredAddress3;
//	}
//
//	public void setInsuredAddress4(String insuredAddress4) {
//		this.insuredAddress4 = insuredAddress4;
//	}

	/**
	 * Set insured against.
	 * 
	 * @param insuredAgainst of type String
	 */
//	public void setInsuredAgainst(String insuredAgainst) {
//		this.insuredAgainst = insuredAgainst;
//	}

	/**
	 * Set insured amount.
	 * 
	 * @param insuredAmount of type Amount
	 */
//	public void setInsuredAmount(Amount insuredAmount) {
//		this.insuredAmount = insuredAmount;
//	}

	/**
	 * Set insurer's name.
	 * 
	 * @param insurerName of type String
	 */
//	public void setInsurerName(String insurerName) {
//		this.insurerName = insurerName;
//	}

	/**
	 * Set Limit Profile Id.
	 * 
	 * @param Limit Profile Id of type String
	 */
	public void setLmtProfileId(Long lmtProfileId) {
		this.lmtProfileId = lmtProfileId;
	}

//	public void setLosInsurancePolicyId(Long losInsurancePolicyId) {
//		this.losInsurancePolicyId = losInsurancePolicyId;
//	}

	public void setNatureOfWork(String natureOfWork) {
		this.natureOfWork = natureOfWork;
	}

	public void setNettPermByBorrower(BigDecimal nettPermByBorrower) {
		this.nettPermByBorrower = nettPermByBorrower;
	}

	public void setNettPermToInsCo(BigDecimal nettPermToInsCo) {
		this.nettPermToInsCo = nettPermToInsCo;
	}

//	public void setNewAmtInsured(Amount newAmtInsured) {
//		this.newAmtInsured = newAmtInsured;
//	}

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

//	public void setRemark2(String remark2) {
//		this.remark2 = remark2;
//	}
//
//	public void setRemark3(String remark3) {
//		this.remark3 = remark3;
//	}

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

//	public void setTakafulCommission(Amount takafulCommission) {
//		this.takafulCommission = takafulCommission;
//	}
//
//	public void setTypeOfFloor(String typeOfFloor) {
//		this.typeOfFloor = typeOfFloor;
//	}
//
//	public void setTypeOfPerils1(String typeOfPerils1) {
//		this.typeOfPerils1 = typeOfPerils1;
//	}
//
//	public void setTypeOfPerils2(String typeOfPerils2) {
//		this.typeOfPerils2 = typeOfPerils2;
//	}
//
//	public void setTypeOfPerils3(String typeOfPerils3) {
//		this.typeOfPerils3 = typeOfPerils3;
//	}
//
//	public void setTypeOfPerils4(String typeOfPerils4) {
//		this.typeOfPerils4 = typeOfPerils4;
//	}
//
//	public void setTypeOfPerils5(String typeOfPerils5) {
//		this.typeOfPerils5 = typeOfPerils5;
//	}

	public void setWall(String wall) {
		this.wall = wall;
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer buf = new StringBuffer(getClass().getName());
		buf.append("@").append(System.identityHashCode(this)).append(", ");

		buf.append("Policy Number: [").append(policyNo).append("], ");
		buf.append("Policy Seq: [").append(policySeq).append("], ");
//		buf.append("Insurer Name: [").append(insurerName).append("], ");
//		buf.append("Insurance Type: [").append(insuranceType).append("], ");
//		buf.append("Insurable Amount: [").append(insurableAmount).append("], ");
//		buf.append("Insured Amount: [").append(insuredAmount).append("], ");
		buf.append("Effective Date: [").append(effectiveDate).append("], ");
		buf.append("Expiry Date: [").append(expiryDate).append("]");

		return buf.toString();
	}
	
	private String lastUpdatedBy;
	private String lastApproveBy;
	private Date lastUpdatedOn;
	private Date lastApproveOn;
	
	private String addFacDocStatus;
	private Date originalTargetDate;
	private Date nextDueDate;
	private Date dateDeferred;
	private String creditApprover;
	private Date waivedDate;
	
	private String docFacilityAmount = "";
	
	private String uniqueAddDocFacDetID = "";
	
	private String docFacilityCategory = "";
	
	private String docFacilityType = "";
	
	private String docFacilityTotalAmount = "";	

	

	public String getAddFacDocStatus() {
		return addFacDocStatus;
	}

	public void setAddFacDocStatus(String addFacDocStatus) {
		this.addFacDocStatus = addFacDocStatus;
	}

	public Date getOriginalTargetDate() {
		return originalTargetDate;
	}

	public void setOriginalTargetDate(Date originalTargetDate) {
		this.originalTargetDate = originalTargetDate;
	}

	public Date getNextDueDate() {
		return nextDueDate;
	}

	public void setNextDueDate(Date nextDueDate) {
		this.nextDueDate = nextDueDate;
	}

	public Date getDateDeferred() {
		return dateDeferred;
	}

	public void setDateDeferred(Date dateDeferred) {
		this.dateDeferred = dateDeferred;
	}

	public String getCreditApprover() {
		return creditApprover;
	}

	public void setCreditApprover(String creditApprover) {
		this.creditApprover = creditApprover;
	}

	public Date getWaivedDate() {
		return waivedDate;
	}

	public void setWaivedDate(Date waivedDate) {
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

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public Date getLastApproveOn() {
		return lastApproveOn;
	}

	public void setLastApproveOn(Date lastApproveOn) {
		this.lastApproveOn = lastApproveOn;
	}

	public String getDocFacilityAmount() {
		return docFacilityAmount;
	}

	public void setDocFacilityAmount(String docFacilityAmount) {
		this.docFacilityAmount = docFacilityAmount;
	}

	
	public String getUniqueAddDocFacDetID() {
		return uniqueAddDocFacDetID;
	}

	public void setUniqueAddDocFacDetID(String uniqueAddDocFacDetID) {
		this.uniqueAddDocFacDetID = uniqueAddDocFacDetID;
	}

	public String getDocFacilityCategory() {
		return docFacilityCategory;
	}

	public void setDocFacilityCategory(String docFacilityCategory) {
		this.docFacilityCategory = docFacilityCategory;
	}

	public String getDocFacilityType() {
		return docFacilityType;
	}

	public void setDocFacilityType(String docFacilityType) {
		this.docFacilityType = docFacilityType;
	}

	public String getDocFacilityTotalAmount() {
		return docFacilityTotalAmount;
	}

	public void setDocFacilityTotalAmount(String docFacilityTotalAmount) {
		this.docFacilityTotalAmount = docFacilityTotalAmount;
	}

	

}