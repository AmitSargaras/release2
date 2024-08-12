/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/IInsurancePolicy.java,v 1.6 2006/04/11 08:21:38 pratheepa Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.integrosys.base.businfra.contact.IAddress;
import com.integrosys.base.businfra.currency.Amount;

/**
 * This interface represents an Insurance entity.
 * 
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2006/04/11 08:21:38 $ Tag: $Name: $
 */
public interface IAddtionalDocumentFacilityDetails extends Serializable {
	public static final String STOCK = "STK";

	public static final String FAO = "FAO";
	
	public long getAddDocFacDetID();
	public void setAddDocFacDetID(long addDocFacDetID);
	
	public Long getLmtProfileId();
	
	public String getRefID();

	public String getRemark1();
	
	public String getStatus();

//	public String getAcType();

	/**
	 * Get insurance address.
	 * 
	 * @return IAddress
	 */
//	public IAddress getAddress();
//
//	public String getAutoDebit();
//
//	public String getBankCustomerArrange();
//
//	public String getBuildingOccupation();
//
//	public String getBuildingType();

	/**
	 * Get category which this insurance belong to.
	 * 
	 * @return String
	 */
//	public String getCategory();
//
//	public BigDecimal getCommissionEarned();
//
//	public String getConversionCurrency();

	/*** added by thurein ***/

//	public String getCoverNoteNumber();

	/**
	 * Get insurance currency code.
	 * 
	 * @return String
	 */
//	public String getCurrencyCode();
//
//	public String getDebitingACNo();

	/**
	 * Get insurance document number.
	 * 
	 * @return String
	 */
	public String getDocumentNo();

	/**
	 * Get effective date.
	 * 
	 * @return Date
	 */
//	public Date getEffectiveDate();
//
//	public String getEndorsementCode();
//
//	public Date getEndorsementDate();

	/**
	 * Get expiry date of insurance.
	 * 
	 * @return Date
	 */
//	public Date getExpiryDate();
//
//	public String getExtensionRoof();
//
//	public String getExtensionWalls();
//
//	public BigDecimal getGrossPremium();
//
//	public Date getInsIssueDate();

	/**
	 * Get insurable amount.
	 * 
	 * @return Amount
	 */
//	public Amount getInsurableAmount();
//
//	public Date getInsuranceClaimDate();

//	public String getInsuranceCompanyName();

//	public Double getInsuranceExchangeRate();

	/**
	 * Get insurance id. x
	 * @return long
	 */

//	public Amount getInsurancePremium();

	/**
	 * Get insurance type.
	 * 
	 * @return String
	 */
//	public String getInsuranceType();
//
//	public String getInsuredAddress1();

	/**
	 * Get insured against.
	 * 
	 * @return String
	 */
//	public String getInsuredAgainst();

	/**
	 * Get insured amount.
	 * 
	 * @return Amount
	 */
//	public Amount getInsuredAmount();

	/**
	 * Get insurer's name.
	 * 
	 * @return String
	 */
//	public String getInsurerName();

	/**
	 * Get Limit Profile id.
	 * 
	 * @return String
	 */
	
//	public Long getLosInsurancePolicyId();

//	public String getNatureOfWork();
//
//	public BigDecimal getNettPermByBorrower();
//
//	public BigDecimal getNettPermToInsCo();

//	public Amount getNewAmtInsured();

//	public String getNonSchemeScheme();
//
//	public Integer getNumberOfStorey();
//
//	public String getPolicyCustodian();

	/**
	 * Get insurance policy number.
	 * 
	 * @return String
	 */
//	public String getPolicyNo();
//
//	public String getPolicySeq();

	/**
	 * Retrieve the rebate amount for the insurance policy
	 * @return the rebate amount for the insurance policy
	 */
//	public BigDecimal getRebateAmount();

	/**
	 * Get common reference id between actual and staging data.
	 * 
	 * @return String
	 */
	
//	public String getRemark2();
//
//	public String getRemark3();

//	public String getRoof();

	/**
	 * Retrieve the service tax (in amount) for the insurance policy
	 * @return the service tax (in amount) for the insurance policy
	 */
//	public BigDecimal getServiceTaxAmount();

	/**
	 * Retrieve the service tax (in percentage) for the insurance policy
	 * @return the service tax (in percentage) for the insurance policy
	 */
//	public BigDecimal getServiceTaxPercentage();
//
//	public BigDecimal getStampDuty();

	/**
	 * Get collateral status, ICMSConstant.STATE_ACTIVE or
	 * ICMSConstant.STATE_DELETED.
	 * 
	 * @return String
	 */
	

//	public Amount getTakafulCommission();

//	public String getTypeOfFloor();
//
//	public String getTypeOfPerils1();
//
//	public String getTypeOfPerils2();
//
//	public String getTypeOfPerils3();
//
//	public String getTypeOfPerils4();
//
//	public String getTypeOfPerils5();

//	public String getWall();
//
//	public void setAcType(String acType);

	/**
	 * Set insurance address.
	 * 
	 * @param address of type IAddress
	 */
//	public void setAddress(IAddress address);
//
//	public void setAutoDebit(String autoDebit);
//
//	public void setBankCustomerArrange(String bankCustomerArrange);
//
//	public void setBuildingOccupation(String buildingOccupation);
//
//	public void setBuildingType(String buildingType);

	/**
	 * Set category of this insurance policy.
	 * 
	 * @param category of type String
	 */
//	public void setCategory(String category);
//
//	public void setCommissionEarned(BigDecimal commissionEarned);
//
//	public void setConversionCurrency(String conversionCurrency);
//
//	public void setCoverNoteNumber(String coverNoteNumber);

	/**
	 * Set insurance currency code.
	 * 
	 * @param currencyCode of type String
	 */
//	public void setCurrencyCode(String currencyCode);

//	public void setDebitingACNo(String debitingACNo);

	/**
	 * Set insurance document number.
	 * 
	 * @param documentNo of type String
	 */

	public void setDocumentNo(String documentNo);

	/**
	 * Set effective date.
	 * 
	 * @param effectiveDate of type Date
	 */
//	public void setEffectiveDate(Date effectiveDate);
//
//	public void setEndorsementCode(String endorsementCode);

//	public void setEndorsementDate(Date endorsementDate);

	/**
	 * Set expiry date of insurance.
	 * 
	 * @param expiryDate of type Date
	 */
//	public void setExpiryDate(Date expiryDate);
//
//	public void setExtensionRoof(String extensionRoof);
//
//	public void setExtensionWalls(String extensionWalls);
//
//	public void setGrossPremium(BigDecimal grossPremium);

//	public void setInsIssueDate(Date insIssueDate);

	/**
	 * Set insurable amount.
	 * 
	 * @param insurableAmount of type Amount
	 */
//	public void setInsurableAmount(Amount insurableAmount);
//
//	public void setInsuranceClaimDate(Date insuranceClaimDate);
//
//	public void setInsuranceCompanyName(String insuranceCompanyName);
//
//	public void setInsuranceExchangeRate(Double insuranceExchangeRate);

	/**
	 * Set insurance id.
	 * 
	 * @param insurancePolicyID of type long
	 */
//	public void setInsurancePolicyID(long insurancePolicyID);
//
//	public void setInsurancePremium(Amount insurancePremium);

	/**
	 * Set insurance type.
	 * 
	 * @param insuranceType of type String
	 */
//	public void setInsuranceType(String insuranceType);

	/**
	 * Set insured against.
	 * 
	 * @param insuredAgainst of type String
	 */
//	public void setInsuredAgainst(String insuredAgainst);

	/**
	 * Set insured amount.
	 * 
	 * @param insuredAmount of type Amount
	 */
//	public void setInsuredAmount(Amount insuredAmount);

	/**
	 * Set insurer's name.
	 * 
	 * @param insurerName of type String
	 */
//	public void setInsurerName(String insurerName);

	/**
	 * Set Limit Profile id.
	 * 
	 * @param Limit Profile id of type String
	 */

//	public void setLosInsurancePolicyId(Long losInsurancePolicyId);

//	public void setNatureOfWork(String natureOfWork);
//
//	public void setNettPermByBorrower(BigDecimal nettPermByBorrower);
//
//	public void setNettPermToInsCo(BigDecimal nettPermToInsCo);
//
//	public void setNewAmtInsured(Amount newAmtInsured);
//
//	public void setNonSchemeScheme(String nonSchemeScheme);
//
//	public void setNumberOfStorey(Integer numberOfStorey);
//
//	public void setPolicyCustodian(String policyCustodian);

	/**
	 * Set insurance policy number.
	 * 
	 * @param policyNo of type String
	 */

//	public void setPolicyNo(String policyNo);
//
//	public void setPolicySeq(String policySeq);

	/**
	 * Set common reference id between actual and staging data.
	 * 
	 * @param refID of type long
	 */
	

	public void setRemark1(String remark1);
//
//	public void setRemark2(String remark2);
//
//	public void setRemark3(String remark3);

	/**
	 * To set the rebate amount for the insurance policy
	 * @param rebateAmount the rebate amount for the insurance policy
	 */
//	public void setRebateAmount(BigDecimal rebateAmount);
//
//	public void setRoof(String roof);

	/**
	 * To set the service tax (in amount) for the insurance policy
	 * @param serviceTaxAmount service tax (in amount) for the insurance policy
	 */
//	public void setServiceTaxAmount(BigDecimal serviceTaxAmount);

	/**
	 * To set the service tax (in percentage) for the insurance policy
	 * @param serviceTaxPercentage service tax (in percentage) for the insurance
	 *        policy
	 */
//	public void setServiceTaxPercentage(BigDecimal serviceTaxPercentage);
//
//	public void setStampDuty(BigDecimal stampDuty);

	/**
	 * Set collateral status, ICMSConstant.STATE_ACTIVE or
	 * ICMSConstant.STATE_DELETED.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);
	
	public void setLmtProfileId(Long lmtProfileId);
	
	public void setRefID(String refID);


//	public void setTakafulCommission(Amount takafulCommission);

//	public void setTypeOfFloor(String typeOfFloor);
//
//	public void setTypeOfPerils1(String typeOfPerils1);
//
//	public void setTypeOfPerils2(String typeOfPerils2);
//
//	public void setTypeOfPerils3(String typeOfPerils3);
//
//	public void setTypeOfPerils4(String typeOfPerils4);
//
//	public void setTypeOfPerils5(String typeOfPerils5);

//	public void setWall(String wall);
	
//	public Date getOriginalTargetDate();
//
//	public void setOriginalTargetDate(Date originalTargetDate);
//
//	public Date getNextDueDate();
//
//	public void setNextDueDate(Date nextDueDate);
//	public Date getDateDeferred();
//
//	public void setDateDeferred(Date dateDeferred);
//
//	public String getCreditApprover();
//
//	public void setCreditApprover(String creditApprover);
//
//	public Date getWaivedDate();
//
//	public void setWaivedDate(Date waivedDate);
	
	public Date getReceivedDate() ;
	public void setReceivedDate(Date receivedDate) ;
	
	public String getLastUpdatedBy();
	public void setLastUpdatedBy(String lastUpdatedBy);
	
	public String getLastApproveBy();
	public void setLastApproveBy(String lastApproveBy);
	
	public Date getLastUpdatedOn();
	public void setLastUpdatedOn(Date lastUpdatedOn);
	
	public Date getLastApproveOn();
	public void setLastApproveOn(Date lastApproveOn);
	
	public String getDocFacilityAmount();
	public void setDocFacilityAmount(String docFacilityAmount);
	
	public String getUniqueAddDocFacDetID();
	public void setUniqueAddDocFacDetID(String uniqueAddDocFacDetID);
	
	public String getDocFacilityCategory();
	public void setDocFacilityCategory(String docFacilityCategory);

	public String getDocFacilityType();
	public void setDocFacilityType(String docFacilityType);
	
	public String getDocFacilityTotalAmount();
	public void setDocFacilityTotalAmount(String docFacilityTotalAmount);
	
	public String getAddFacDocStatus();
	public void setAddFacDocStatus(String addFacDocStatus);
	

}