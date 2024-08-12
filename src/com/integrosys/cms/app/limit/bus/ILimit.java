/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/ILimit.java,v 1.26 2005/10/14 05:11:46 whuang Exp $
 */
package com.integrosys.cms.app.limit.bus;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.ejbsupport.IValueObject;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.customer.bus.ICoBorrowerDetails;

/**
 * This interface represents a Limit record.
 * 
 * @author $Author: whuang $
 * @version $Revision: 1.26 $
 * @since $Date: 2005/10/14 05:11:46 $ Tag: $Name: $
 */
public interface ILimit extends java.io.Serializable, IValueObject {
	// Getters

	/**
	 * <p>
	 * Retrieve the account type of this limit. It's more related to it's
	 * Product Type and Facility Type relationship.
	 * <p>
	 * <b>Note</b> Not all implementation of the CMS have this value
	 * @return the account type associated to this limit.
	 */
	public String getAccountType();

	public String getAcfNo();

	/**
	 * Get Activated Limit Amount
	 * 
	 * @return Amount
	 */
	public Amount getActivatedLimitAmount();

	/**
	 * Get the actual security coverage amount
	 * 
	 * @return Amount
	 */
	public Amount getActualSecCoverageAmt();

	/**
	 * Get the actual security coverage amount based on FSV
	 * 
	 * @return Amount
	 */
	public Amount getActualSecCoverageFSVAmt();

	/**
	 * Get the actual security coverage amount based on OMV
	 * 
	 * @return Amount
	 */
	public Amount getActualSecCoverageOMVAmt();

	/**
	 * Get the actual security coverage percentage
	 * 
	 * @return float
	 */
	public float getActualSecurityCoverage();

	/**
	 * Get Approved Limit Amount
	 * 
	 * @return Amount
	 */
	public Amount getApprovedLimitAmount();

	/**
	 * Get Booking Location
	 * 
	 * @return IBookingLocation
	 */
	public IBookingLocation getBookingLocation();

	/**
	 * Get all co-borrower limits.
	 * 
	 * @return ICoBorrowerLimit[]
	 */
	public ICoBorrowerLimit[] getCoBorrowerLimits();

	public Set getCoBorrowerLimitsSet();

	/**
	 * Get All Collateral Allocations
	 * 
	 * @return ICollateralAllocation[]
	 */
	public ICollateralAllocation[] getCollateralAllocations();

	public Set getCollateralAllocationsSet();

	public Amount getDrawingLimitAmount();

	/**
	 * Get the is limit exisitng indicator. If true, this limit exist in the
	 * previous version of the BCA
	 * 
	 * @return boolean
	 */
	public boolean getExistingInd();

	/**
	 * @return the facilityCode
	 */
	public String getFacilityCode();

	/**
	 * Get Facility Description
	 * 
	 * @return String
	 */
	public String getFacilityDesc();

	public String getFacilityDescNum();

	/**
	 * @return the facilitySequence
	 */
	public long getFacilitySequence();

	/**
	 * Get the host record change status
	 * 
	 * @return String
	 */
	public String getHostStatus();

	public Integer getInnerLimitOrdering();

	public Double getInterestRate();

	public boolean getIsChanged();

	/**
	 * Get if the limit encounter DAP error.
	 * 
	 * @return boolean
	 */
	public boolean getIsDAPError();

	/**
	 * Check if inner and outer limit are of the same BCA.
	 * 
	 * @return boolean
	 */
	public boolean getIsInnerOuterSameBCA();

	/**
	 * Check if the limit is zerorised.
	 * 
	 * @return boolean
	 */
	public boolean getIsLimitZerorised();

	public boolean getIsZerorisedChanged();

	public boolean getIsZerorisedDateChanged();

	public boolean getIsZerorisedReasonChanged();

	public Date getLastUpdatedDate();

	/**
	 * Set customer CIF for limit
	 * @return
	 */
	public String getLEReference();

	/**
	 * Get Limit Activated Indicator
	 * 
	 * @return boolean
	 */
	public boolean getLimitActivatedInd();

	/**
	 * Get Limit Advise Indicator
	 * 
	 * @return boolean
	 */
	public boolean getLimitAdviseInd();

	/**
	 * Get Limit CMMSN
	 * 
	 * @return String
	 */
	public String getLimitCMMSN();

	/**
	 * Get Limit Committed Indicator
	 * 
	 * @return boolean
	 */
	public boolean getLimitCommittedInd();

	/**
	 * Get Limit Condition Text
	 * 
	 * @return String
	 */
	public String getLimitConditionText();

	public String getLimitDesc();

	public String getLimitDescNum();

	/**
	 * Get Limit Expiry Date
	 * 
	 * @return Date
	 */
	public Date getLimitExpiryDate();

	/**
	 * Get Limit Fee
	 * 
	 * @return String
	 */
	public String getLimitFee();

	/**
	 * Get Limit ID
	 * 
	 * @return long
	 */
	public long getLimitID();

	/**
	 * Get Limit Interest
	 * 
	 * @return float
	 */
	public float getLimitInterest();

	/**
	 * Get the limit profile ID
	 * 
	 * @return long
	 */
	public long getLimitProfileID();

	// Setters

	public String getLimitProfileReferenceNumber();

	/**
	 * Get Limit Reference
	 * 
	 * @return String
	 */
	public String getLimitRef();

	/**
	 * Get Limit Secured Type
	 * 
	 * @return String
	 */
	public String getLimitSecuredType();

	/**
	 * Get Limit Status
	 * 
	 * @return String
	 */
	public String getLimitStatus();

	/**
	 * Get the limit system x-ref
	 * 
	 * @return ILimitSysXRef[]
	 */
	public ILimitSysXRef[] getLimitSysXRefs();


	public Set getLimitSysXRefsSet();
	
	/**
	 * Get the limit Covenant
	 * 
	 * @return ILimitCovenant[]
	 */
	public ILimitCovenant[] getLimitCovenant();

	public Set getLimitCovenantSet();

	/**
	 * Get Limit Tenor
	 * 
	 * @return long
	 */
	public Long getLimitTenor();

	/**
	 * Get Limit Tenor Unit
	 * 
	 * @return String
	 */
	public String getLimitTenorUnit();

	public String getLimitTenorUnitNum();

	/**
	 * Get Limit Type
	 * 
	 * @return String
	 */
	public String getLimitType();

	public String getLoanType();

	/**
	 * Return LOS Limit Reference number. Not supposed to be internal key of
	 * other system.
	 * 
	 * @return LOS Limit Reference number.
	 */
	public String getLosLimitRef();

	/**
	 * Get all co-borrower limits whose status is not DELETED.
	 * 
	 * @return ICoBorrowerLimit[]
	 */
	public ICoBorrowerLimit[] getNonDeletedCoBorrowerLimits();

	public ICollateralAllocation[] getNonDeletedCollateralAllocations();

	public Long getOmnibusEnvelopeId();

	public String getOmnibusEnvelopeName();

	public List<IFacilityCoBorrowerDetails> getCoBorrowerDetails();
	
	public void setCoBorrowerDetails(List<IFacilityCoBorrowerDetails> coBorrowerDetailsList);
	/**
	 * Get operational limit if the limit is tied to commodities.
	 * 
	 * @return Amount
	 */
	public Amount getOperationalLimit();

	/**
	 * Get Outer Limit ID
	 * 
	 * @return String
	 */
	public long getOuterLimitID();

	/**
	 * Get outer limit profile id.
	 * 
	 * @return long
	 */
	
	public long getOuterLimitProfileID();

	/**
	 * Get the Outer Limit Ref
	 * 
	 * @return String
	 */
	public String getOuterLimitRef();

	public Amount getOutstandingAmount();

	/**
	 * get the product code <productDesc>|<limit currency>|<accountType>
	 *
	 * @return productCode of type String
	 */
	public String getProductCode();

	/**
	 * Get Product Description
	 * 
	 * @return String
	 */
	public String getProductDesc();

	public String getProductDescNum();

	public String getProductGroup();

	public String getProductGroupNum();

	/**
	 * Get the required security coverage percentage
	 * 
	 * @return float
	 */
	public String getRequiredSecurityCoverage();

	/**
	 * Get Shared Limit Indicator
	 * 
	 * @return boolean
	 */
	public boolean getSharedLimitInd();

	public String getSourceId();

	/**
	 * Get the zerorisation date.
	 * 
	 * @return Date
	 */
	public Date getZerorisedDate();

	/**
	 * Get the reason of zerorisation.
	 * 
	 * @return String
	 */
	public String getZerorisedReason();

	/**
	 * To set the account type associated to this limit. Related to it's Product
	 * Type and Facility Type.
	 * @param accountType the account type to be associated this this limit.
	 */
	public void setAccountType(String accountType);

	public void setAcfNo(String acfNo);

	/**
	 * Set Activated Limit Amount
	 * 
	 * @param value is of type Amount
	 */
	public void setActivatedLimitAmount(Amount value);

	/**
	 * Set the actual security coverage amount
	 * 
	 * @param value is of type Amount
	 */
	public void setActualSecCoverageAmt(Amount value);

	/**
	 * Set the actual security coverage amount based on FSV
	 * 
	 * @param value is of type Amount
	 */
	public void setActualSecCoverageFSVAmt(Amount value);

	/**
	 * Set the actual security coverage amount based on OMV
	 * 
	 * @param value is of type Amount
	 */
	public void setActualSecCoverageOMVAmt(Amount value);

	/**
	 * Set the actual security coverage percentage
	 * 
	 * @param value is of type float
	 */
	public void setActualSecurityCoverage(float value);

	/**
	 * Set Approved Limit Amount
	 * 
	 * @param value is of type Amount
	 */
	public void setApprovedLimitAmount(Amount value);

	/**
	 * Set Booking Location
	 * 
	 * @param value is of type IBookingLocation
	 */
	public void setBookingLocation(IBookingLocation value);

	/**
	 * Set all co-borrower limits.
	 * 
	 * @param value is of type ICoBorrowerLimit[]
	 */
	public void setCoBorrowerLimits(ICoBorrowerLimit[] value);

	public void setCoBorrowerLimitsSet(Set coBorrowerLimitsSet);

	/**
	 * Set All Collateral Allocations
	 * 
	 * @param value is of type ICollateralAllocation[]
	 */
	public void setCollateralAllocations(ICollateralAllocation[] value);

	public void setCollateralAllocationsSet(Set collateralAllocationsSet);

	public void setDrawingLimitAmount(Amount drawingLimitAmount);

	/**
	 * Set the is limit exisitng indicator. If true, this limit exist in the
	 * previous version of the BCA
	 * 
	 * @param value is of type boolean
	 */
	public void setExistingInd(boolean value);

	/**
	 * @param facilityCode the facilityCode to set
	 */
	public void setFacilityCode(String facilityCode);

	/**
	 * Set Facility Description
	 * 
	 * @param value is of type String
	 */
	public void setFacilityDesc(String value);

	public void setFacilityDescNum(String facilityDescNum);

	/**
	 * @param facilitySequence the facilitySequence to set
	 */
	public void setFacilitySequence(long facilitySequence);

	/**
	 * Set the host record change status
	 * 
	 * @param value is of type String
	 */
	public void setHostStatus(String value);

	public void setInnerLimitOrdering(Integer innerLimitOrdering);

	public void setInterestRate(Double interestRate);

	public void setIsChanged(boolean isChanged);

	/**
	 * Set if the limit encounters DAP error.
	 * 
	 * @param isDAPError of type boolean
	 */
	public void setIsDAPError(boolean isDAPError);

	/**
	 * Set an indicator whether the limit is zerorised.
	 * 
	 * @param isLimitZerorised of type boolean
	 */
	public void setIsLimitZerorised(boolean isLimitZerorised);

	public void setIsZerorisedChanged(boolean isChanged);

	public void setIsZerorisedDateChanged(boolean isChanged);

	public void setIsZerorisedReasonChanged(boolean isChanged);

	public void setLastUpdatedDate(Date lastUpdatedDate);

	/**
	 * Get customer CIF for limit
	 * @param lEReference
	 */
	public void setLEReference(String lEReference);

	/**
	 * Set Limit Activated Indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setLimitActivatedInd(boolean value);

	/**
	 * Set Limit Advise Indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setLimitAdviseInd(boolean value);

	/**
	 * Set Limit CMMSN
	 * 
	 * @param value is of type String
	 */
	public void setLimitCMMSN(String value);

	/**
	 * Set Limit Committed Indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setLimitCommittedInd(boolean value);

	/**
	 * Set Limit Condition Text
	 * 
	 * @param value is of type String
	 */
	public void setLimitConditionText(String value);

	public void setLimitDesc(String limitDesc);

	public void setLimitDescNum(String limitDescNum);

	/**
	 * Set Limit Expiry Date
	 * 
	 * @param value is of type Date
	 */
	public void setLimitExpiryDate(Date value);

	/**
	 * Set Limit Fee
	 * 
	 * @param value is of type String
	 */
	public void setLimitFee(String value);

	/**
	 * Set Limit ID
	 * 
	 * @param value is of type long
	 */
	public void setLimitID(long value);

	/**
	 * Set Limit Interest
	 * 
	 * @param value is of type float
	 */
	public void setLimitInterest(float value);

	/**
	 * Set the limit profile ID
	 * 
	 * @param value is of type long
	 */
	public void setLimitProfileID(long value);

	public void setLimitProfileReferenceNumber(String limitProfileReferenceNumber);

	/**
	 * Set Limit Reference
	 * 
	 * @param value is of type String
	 */
	public void setLimitRef(String value);

	/**
	 * Set Limit Secured Type
	 * 
	 * @param value is of type String
	 */
	public void setLimitSecuredType(String value);

	/**
	 * Set Limit Status
	 * 
	 * @param value is of type String
	 */
	public void setLimitStatus(String value);

	/**
	 * Set the limit system x-ref
	 * 
	 * @param value is of type ILimitSysXRef[]
	 */
	public void setLimitSysXRefs(ILimitSysXRef[] value);

	public void setLimitSysXRefsSet(Set limitSysXRefsSet);
	
	/**
	 * Set the limit system covenant
	 * 
	 * @param value is of type ILimitCovenant[]
	 */
	public void setLimitCovenant(ILimitCovenant[] value);

	public void setLimitCovenantSet(Set limitCovenantSet);

	/**
	 * Set Limit Tenor
	 * 
	 * @param value is of type long
	 */
	public void setLimitTenor(Long value);

	/**
	 * Set Limit Tenor Unit
	 * 
	 * @param value is of type String
	 */
	public void setLimitTenorUnit(String value);

	public void setLimitTenorUnitNum(String limitTenorUnitNum);

	/**
	 * Set Limit Type
	 * 
	 * @param value is of type String
	 */
	public void setLimitType(String value);

	public void setLoanType(String loanType);

	/**
	 * Set the LOS Limit Reference Number. Not suppose to be internal key of
	 * other system.
	 * 
	 * @param losLimitRef los limit reference number
	 */
	public void setLosLimitRef(String losLimitRef);

	public void setOmnibusEnvelopeId(Long omnibusEnvelopeId);

	public void setOmnibusEnvelopeName(String omnibusEnvelopeName);

	/**
	 * Set operational limit if the limit is tied to commodities.
	 * 
	 * @param operationalLimit of type Amount
	 */
	public void setOperationalLimit(Amount operationalLimit);

	/**
	 * Set Outer Limit ID
	 * 
	 * @param value is of type long
	 */
	public void setOuterLimitID(long value);

	/**
	 * Set outer limit profile id.
	 * 
	 * @param outerLimitProfileID of type long
	 */
	public void setOuterLimitProfileID(long outerLimitProfileID);

	/**
	 * Set the Outer Limit Ref
	 * 
	 * @param value is of type String
	 */
	public void setOuterLimitRef(String value);

	public void setOutstandingAmount(Amount outstandingAmount);

	/**
	 * Set Product Description
	 * 
	 * @param value is of type String
	 */
	public void setProductDesc(String value);

	public void setProductDescNum(String productDescNum);

	public void setProductGroup(String productGroup);

	public void setProductGroupNum(String productGroupNum);

	/**
	 * Set the required security coverage percentage
	 * 
	 * @param value is of type float
	 */
	public void setRequiredSecurityCoverage(String value);

	/**
	 * Set Shared Limit Indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setSharedLimitInd(boolean value);

	public void setSourceId(String sourceId);
	
	/**
	 * Set the zerorisation date.
	 * 
	 * @param zerorisedDate of type Date
	 */
	public void setZerorisedDate(Date zerorisedDate);
	
	/**
	 * Set the reason of zerorisation.
	 * 
	 * @param zerorisedReason of type String
	 */
	public void setZerorisedReason(String zerorisedReason);

	public String getDisbursedInd();

	public void setDisbursedInd(String disbursedInd);
	
	//Shiv
	
	public String getFacilityName();
	public void setFacilityName(String facilityName);
	
	public String getFacilitySystem();
	public void setFacilitySystem(String facilitySystem);
	
	public String getFacilitySystemID();
	public void setFacilitySystemID(String facilitySystemID);
	
	public String getLineNo();
	public void setLineNo(String lineNo);
	
	public String getPurpose();
	public void setPurpose(String purpose);
	
	public String getOtherPurpose();
	public void setOtherPurpose(String otherPurpose);
	
	public String getIsDP();
	public void setIsDP(String isDP);
	
	public String getRelationShipManager();
	public void setRelationShipManager(String relationShipManager);
	
	public String getIsReleased();
	public void setIsReleased(String isReleased);
	
	public String getGrade();
	public void setGrade(String grade);
	
	public String getIsSecured();
	public void setIsSecured(String isSecured);
	
	public String getIsAdhoc();
	public void setIsAdhoc(String isAdhoc);
	
	public String getCurrencyCode();
	public void setCurrencyCode(String currencyCode);

	public String getFacilityType();
	public void setFacilityType(String facilityType);
	
	public String getFacilityCat();
	public void setFacilityCat(String facilityCat);
	
	public String getReleasableAmount();
	public void setReleasableAmount(String releasableAmount);
	
	public String getAdhocLmtAmount();
	public void setAdhocLmtAmount(String adhocLmtAmount);
	
	public String getIsAdhocToSum();
	public void setIsAdhocToSum(String isAdhocToSum);
	
	public  String getGuarantee();
	public  void setGuarantee(String guarantee);
	
	public  String getSubPartyName();
	public  void setSubPartyName(String subPartyName);
	
	public  String getSubFacilityName();
	public  void setSubFacilityName(String subFacilityName);
	
	public  String getLiabilityID();
	public  void setLiabilityID(String liabilityID);
	
	public String getTotalReleasedAmount();
	public void setTotalReleasedAmount(String totalReleasedAmount);
	
	public String getMainFacilityId();
    public void setMainFacilityId(String mainFacilityId);
    
    public char getIsFromCamonlineReq();
	public void setIsFromCamonlineReq(char isFromCamonlineReq);
	
	public String getSyndicateLoan();
	public void setSyndicateLoan(String syndicateLoan);
	
	

	public String getProjectLoan();

	public void setProjectLoan(String projectLoan);

	public String getInfaProjectFlag();

	public void setInfaProjectFlag(String infaProjectFlag);

	public String getWhlmreupSCOD();

	public void setWhlmreupSCOD(String whlmreupSCOD);

	public Date getScodDate();

	public void setScodDate(Date scodDate);

	public String getRemarksSCOD();

	public void setRemarksSCOD(String remarksSCOD);

	public String getExstAssetClass();

	public void setExstAssetClass(String exstAssetClass);

	public Date getExstAssClassDate();

	public void setExstAssClassDate(Date exstAssClassDate);

	public String getRevisedAssetClass();

	public void setRevisedAssetClass(String revisedAssetClass);

	public Date getRevsdAssClassDate();

	public void setRevsdAssClassDate(Date revsdAssClassDate);
	public Date getActualCommOpsDate();

	public void setActualCommOpsDate(Date actualCommOpsDate);

	public String getLelvelDelaySCOD();

	public void setLelvelDelaySCOD(String lelvelDelaySCOD);

	public String getPrincipalInterestSchFlag();

	public void setPrincipalInterestSchFlag(String principalInterestSchFlag);

	public String getProjectDelayedFlag();

	public void setProjectDelayedFlag(String projectDelayedFlag);

	public String getReasonLevelOneDelay();

	public void setReasonLevelOneDelay(String reasonLevelOneDelay);

	public String getChngInRepaySchedule();

	public void setChngInRepaySchedule(String chngInRepaySchedule);

	public Date getEscodLevelOneDelayDate();

	public void setEscodLevelOneDelayDate(Date escodLevelOneDelayDate);

	public Date getEscodLevelSecondDelayDate();

	public void setEscodLevelSecondDelayDate(Date escodLevelSecondDelayDate);

	public String getReasonLevelThreeDelay();

	public void setReasonLevelThreeDelay(String reasonLevelThreeDelay);

	public Date getEscodLevelThreeDelayDate();

	public void setEscodLevelThreeDelayDate(Date escodLevelThreeDelayDate);

	public String getLegalOrArbitrationLevel2Flag();

	public void setLegalOrArbitrationLevel2Flag(String legalOrArbitrationLevel2Flag);

	public String getChngOfOwnPrjFlagNonInfraLevel2();

	public void setChngOfOwnPrjFlagNonInfraLevel2(String chngOfOwnPrjFlagNonInfraLevel2);

	public String getReasonBeyondPromoterLevel2Flag();

	public void setReasonBeyondPromoterLevel2Flag(String reasonBeyondPromoterLevel2Flag);

	public String getChngOfProjScopeNonInfraLevel2();

	public void setChngOfProjScopeNonInfraLevel2(String chngOfProjScopeNonInfraLevel2);

	public String getChngOfOwnPrjFlagInfraLevel2();

	public void setChngOfOwnPrjFlagInfraLevel2(String chngOfOwnPrjFlagInfraLevel2);

	public String getChngOfProjScopeInfraLevel2();

	public void setChngOfProjScopeInfraLevel2(String chngOfProjScopeInfraLevel2);

	public String getLegalOrArbitrationLevel3Flag();

	public void setLegalOrArbitrationLevel3Flag(String legalOrArbitrationLevel3Flag);

	public String getChngOfOwnPrjFlagNonInfraLevel3();

	public void setChngOfOwnPrjFlagNonInfraLevel3(String chngOfOwnPrjFlagNonInfraLevel3);

	public String getReasonBeyondPromoterLevel3Flag();

	public void setReasonBeyondPromoterLevel3Flag(String reasonBeyondPromoterLevel3Flag);

	public String getChngOfProjScopeNonInfraLevel3();

	public void setChngOfProjScopeNonInfraLevel3(String chngOfProjScopeNonInfraLevel3);

	public String getChngOfOwnPrjFlagInfraLevel3();

	public void setChngOfOwnPrjFlagInfraLevel3(String chngOfOwnPrjFlagInfraLevel3);

	public String getChngOfProjScopeInfraLevel3();

	public void setChngOfProjScopeInfraLevel3(String chngOfProjScopeInfraLevel3);

	public String getLeaglOrArbiProceed();

	public void setLeaglOrArbiProceed(String leaglOrArbiProceed);

	public String getDetailsRsnByndPro();

	public void setDetailsRsnByndPro(String detailsRsnByndPro);

	public String getRecvPriorOfSCOD();

	public void setRecvPriorOfSCOD(String recvPriorOfSCOD);

	public String getReasonLevelTwoDelay();

	public void setReasonLevelTwoDelay(String reasonLevelTwoDelay);

	public String getPromotersCapRunFlag();

	public void setPromotersCapRunFlag(String promotersCapRunFlag);

	public String getChangeInScopeBeforeSCODFlag();

	public void setChangeInScopeBeforeSCODFlag(String changeInScopeBeforeSCODFlag);

	public String getPromotersHoldEquityFlag();

	public void setPromotersHoldEquityFlag(String promotersHoldEquityFlag);

	public String getCostOverrunOrg25CostViabilityFlag();

	public void setCostOverrunOrg25CostViabilityFlag(String costOverrunOrg25CostViabilityFlag);

	public String getHasProjectViabReAssFlag();

	public void setHasProjectViabReAssFlag(String hasProjectViabReAssFlag);

	public String getProjectViabReassesedFlag();

	public void setProjectViabReassesedFlag(String projectViabReassesedFlag);

	public Date getRevsedESCODStpDate();

	public void setRevsedESCODStpDate(Date revsedESCODStpDate);

		

	public String getExstAssetClassL1() ;

	public void setExstAssetClassL1(String exstAssetClassL1) ;

	public Date getExstAssClassDateL1();

	public void setExstAssClassDateL1(Date exstAssClassDateL1);

	public String getExstAssetClassL2();

	public void setExstAssetClassL2(String exstAssetClassL2);

	public Date getExstAssClassDateL2();

	public void setExstAssClassDateL2(Date exstAssClassDateL2);

	public String getExstAssetClassL3();

	public void setExstAssetClassL3(String exstAssetClassL3);

	public Date getExstAssClassDateL3();

	public void setExstAssClassDateL3(Date exstAssClassDateL3);

	public String getRevisedAssetClassL1();

	public void setRevisedAssetClassL1(String revisedAssetClassL1);

	public Date getRevsdAssClassDateL1() ;

	public void setRevsdAssClassDateL1(Date revsdAssClassDateL1);

	public String getRevisedAssetClass_L2();

	public void setRevisedAssetClass_L2(String revisedAssetClass_L2);

	public Date getRevsdAssClassDate_L2();

	public void setRevsdAssClassDate_L2(Date revsdAssClassDate_L2);

	public String getRevisedAssetClass_L3();

	public void setRevisedAssetClass_L3(String revisedAssetClass_L3);

	public Date getRevsdAssClassDate_L3();

	public void setRevsdAssClassDate_L3(Date revsdAssClassDate_L3) ;

	public String getProjectDelayedFlagL2() ;

	public void setProjectDelayedFlagL2(String projectDelayedFlagL2) ;

	public String getProjectDelayedFlagL3() ;

	public void setProjectDelayedFlagL3(String projectDelayedFlagL3) ;

	public String getLeaglOrArbiProceedLevel3();

	public void setLeaglOrArbiProceedLevel3(String leaglOrArbiProceedLevel3) ;

	public String getDetailsRsnByndProLevel3();

	public void setDetailsRsnByndProLevel3(String detailsRsnByndProLevel3);

	public String getChngInRepayScheduleL2();

	public void setChngInRepayScheduleL2(String chngInRepayScheduleL2);

	public String getChngInRepayScheduleL3();

	public void setChngInRepayScheduleL3(String chngInRepayScheduleL3);

	public String getPromotersCapRunFlagL2();

	public void setPromotersCapRunFlagL2(String promotersCapRunFlagL2);

	public String getPromotersCapRunFlagL3();

	public void setPromotersCapRunFlagL3(String promotersCapRunFlagL3);

	public String getChangeInScopeBeforeSCODFlagL2() ;

	public void setChangeInScopeBeforeSCODFlagL2(String changeInScopeBeforeSCODFlagL2);

	public String getChangeInScopeBeforeSCODFlagL3() ;

	public void setChangeInScopeBeforeSCODFlagL3(String changeInScopeBeforeSCODFlagL3);

	public String getPromotersHoldEquityFlagL2() ;

	public void setPromotersHoldEquityFlagL2(String promotersHoldEquityFlagL2) ;

	public String getPromotersHoldEquityFlagL3();

	public void setPromotersHoldEquityFlagL3(String promotersHoldEquityFlagL3) ;
	

	public String getCostOverrunOrg25CostViabilityFlagL2() ;

	public void setCostOverrunOrg25CostViabilityFlagL2(String costOverrunOrg25CostViabilityFlagL2);

	public String getCostOverrunOrg25CostViabilityFlagL3();

	public void setCostOverrunOrg25CostViabilityFlagL3(String costOverrunOrg25CostViabilityFlagL3);

	public String getHasProjectViabReAssFlagL2();

	public void setHasProjectViabReAssFlagL2(String hasProjectViabReAssFlagL2);

	public String getHasProjectViabReAssFlagL3() ;

	public void setHasProjectViabReAssFlagL3(String hasProjectViabReAssFlagL3) ;

	public String getProjectViabReassesedFlagL2() ;

	public void setProjectViabReassesedFlagL2(String projectViabReassesedFlagL2) ;

	public String getProjectViabReassesedFlagL3() ;

	public void setProjectViabReassesedFlagL3(String projectViabReassesedFlagL3) ;

	public Date getRevsedESCODStpDateL2() ;

	public void setRevsedESCODStpDateL2(Date revsedESCODStpDateL2) ;

	public Date getRevsedESCODStpDateL3();

	public void setRevsedESCODStpDateL3(Date revsedESCODStpDateL3);
	
	public String getProjectFinance();

	public void setProjectFinance(String projectFinance);

	public String getPurposeBoolean();
	public void setPurposeBoolean(String purposeBoolean);
	
	public String getLimitRemarks();
	public void setLimitRemarks(String limitRemarks);
	
	public String getBankingArrangement();
	public void setBankingArrangement(String bankingArrangement);

	public String getClauseAsPerPolicy();
	public void setClauseAsPerPolicy(String clauseAsPerPolicy);
	
	public String getAdhocFacility();
	public void setAdhocFacility(String adhocFacility);
	
	public Date getAdhocLastAvailDate();
	public void setAdhocLastAvailDate(Date adhocLastAvailDate);
	
	public String getMultiDrawdownAllow();
	public void setMultiDrawdownAllow(String multiDrawdownAllow);
	
	public String getAdhocTenor();
	public void setAdhocTenor(String adhocTenor);
	
	public Date getAdhocFacilityExpDate();
	public void setAdhocFacilityExpDate(Date adhocFacilityExpDate);
	
	public String getGeneralPurposeLoan();
	public void setGeneralPurposeLoan(String generalPurposeLoan);

	public String getIsDPRequired();
	public void setIsDPRequired(String isDPRequired);
	
	Long getTenor();

	void setTenor(Long tenor);

	String getTenorUnit();

	void setTenorUnit(String tenorUnit);

	Double getMargin();

	void setMargin(Double margin);

	String getTenorDesc();

	void setTenorDesc(String tenorDesc);

	String getPutCallOption();

	void setPutCallOption(String putCallOption);

	Date getLoanAvailabilityDate();

	void setLoanAvailabilityDate(Date loanAvailabilityDate);

	Date getOptionDate();

	void setOptionDate(Date optionDate);
	
	String getRiskType();

	void setRiskType(String riskType);


}