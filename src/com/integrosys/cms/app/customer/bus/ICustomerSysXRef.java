/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/ICustomerSysXRef.java,v 1.2 2003/06/17 05:08:10 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

import java.util.Date;
import java.util.Set;

import com.integrosys.base.businfra.currency.Amount;

/**
 * This interface represents a Customer system cross-reference to a host system.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/06/17 05:08:10 $ Tag: $Name: $
 */
public interface ICustomerSysXRef extends java.io.Serializable {
	// Getters

	/**
	 * Get X-Ref primary key
	 * 
	 * @return long
	 */
	public long getXRefID();

	/**
	 * Get External System X-Ref ID
	 * 
	 * @return String
	 */
	public String getExternalXRef();

	/**
	 * Get System Booking Location
	 * 
	 * @return String
	 */
	public String getBookingLocation();

	/**
	 * Get External System Code
	 * 
	 * @return String
	 */
	public String getExternalSystemCode();

	/**
	 * Get External System Customer ID
	 * 
	 * @return String
	 */
	public String getExternalCustomerID();

	/**
	 * Get External System Customer Name
	 * 
	 * @return String
	 */
	public String getExternalCustomerName();

	/**
	 * Get External System Account Number
	 * 
	 * @return String
	 */
	public String getExternalAccountNo();

	/**
	 * Get External System Account Status
	 * 
	 * @return String
	 */
	public String getExternalAccountStatus();

	// Setters

	/**
	 * Set X-Ref primary key
	 * 
	 * @param value is of type long
	 */
	public void setXRefID(long value);

	/**
	 * Set External System X-Ref ID
	 * 
	 * @param value is of type String
	 */
	public void setExternalXRef(String value);

	/**
	 * Set System Booking Location
	 * 
	 * @param value is of type String
	 */
	public void setBookingLocation(String value);

	/**
	 * Set External System Code
	 * 
	 * @param value is of type String
	 */
	public void setExternalSystemCode(String value);

	/**
	 * Set External System Customer ID
	 * 
	 * @param value is of type String
	 */
	public void setExternalCustomerID(String value);

	/**
	 * Set External System Customer Name
	 * 
	 * @param value is of type String
	 */
	public void setExternalCustomerName(String value);

	/**
	 * Set External System Account Number
	 * 
	 * @param value is of type String
	 */
	public void setExternalAccountNo(String value);

	/**
	 * Set External System Account Status
	 * 
	 * @param value is of type String
	 */
	public void setExternalAccountStatus(String value);

	/**
	 * @return Returns the accountDelinq.
	 */
	public Boolean getAccountDelinq();

	/**
	 * @param accountDelinq The accountDelinq to set.
	 */
	public void setAccountDelinq(Boolean accountDelinq);

	/**
	 * @return Returns the accountStatus.
	 */
	public String getAccountStatus();

	/**
	 * @param accountStatus The accountStatus to set.
	 */
	public void setAccountStatus(String accountStatus);

	/**
	 * @return Returns the acctEffectiveDate.
	 */
	public Date getAcctEffectiveDate();

	/**
	 * @param acctEffectiveDate The acctEffectiveDate to set.
	 */
	public void setAcctEffectiveDate(Date acctEffectiveDate);

	/**
	 * @return Returns the externalSysCountry.
	 */
	public String getExternalSysCountry();

	/**
	 * @param externalSysCountry The externalSysCountry to set.
	 */
	public void setExternalSysCountry(String externalSysCountry);

	/**
	 * @return Returns the externalSystemCodeNum.
	 */
	public String getExternalSystemCodeNum();

	/**
	 * @param externalSystemCodeNum The externalSystemCodeNum to set.
	 */
	public void setExternalSystemCodeNum(String externalSystemCodeNum);

	/**
	 * @return Return the BookingLoctnCountry
	 */
	public String getBookingLoctnCountry();

	/**
	 * @param bookingLoctnCountry the bookingLoctnCountry to set.
	 */
	public void setBookingLoctnCountry(String bookingLoctnCountry);

	/**
	 * @return Return the bookingLoctnOrganisation
	 */
	public String getBookingLoctnOrganisation();

	/**
	 * @param bookingLoctnOrganisation the bookingLoctnOrganisation to set.
	 */
	public void setBookingLoctnOrganisation(String bookingLoctnOrganisation);

	public Amount getRVForAccount();

	public void setRVForAccount(Amount amt);
	
	public Date getLastAllocationDate();

	public void setLastAllocationDate(Date lastAllocationDate);

	public Amount getCollateralAllocatedAmt();

	public void setCollateralAllocatedAmt(Amount collateralAllocatedAmt);

	public Amount getOutstandingAmt();

	public void setOutstandingAmt(Amount outstandingAmt);

	/**
	 * Get external account type
	 * Permissible value is L(Loan) and D (Overdraft)
	 * @return
	 */
	public String getExternalAccountType();

	/**
	 * Set external account type, only L(Loan) and D(Overdraft) value
	 * @param accountType
	 */
	public void setExternalAccountType(String accountType);
	
	//added by Shiv
	
	public String getSerialNo();
	public void setSerialNo(String serialNo);
	
	public String getInterestRateType();
	public void setInterestRateType(String interestRateType);
	
	public String getIntRateFloatingType();
	public void setIntRateFloatingType(String intRateFloatingType);
	
	public String getIntRateFloatingRange();
	public void setIntRateFloatingRange(String intRateFloatingRange);
	
	public String getIntRateFloatingMarginFlag();
	public void setIntRateFloatingMarginFlag(String intRateFloatingMarginFlag);
	
	public String getIntRateFloatingMargin();
	public void setIntRateFloatingMargin(String intRateFloatingMargin);
	
	public String getReleasedAmount();
	public void setReleasedAmount(String releasedAmount);
	
	public String getUtilizedAmount();
	public void setUtilizedAmount(String utilizedAmount);
	
	public Date getReleaseDate();
	public void setReleaseDate(Date releaseDate);
	
	public void setIntradayLimitExpiryDate(Date intradayLimitExpiryDate);
	public Date getIntradayLimitExpiryDate(); 
	
	public void setDayLightLimit(String dayLightLimit); 
	public String getDayLightLimit();
	
	public void setIntradayLimitFlag(String intradayLimitFlag);
	public String getIntradayLimitFlag(); 
	
	public Date getDateOfReset();
	public void setDateOfReset(Date dateOfReset);
	
	public String getIsPermntSSICert();
	public void setIsPermntSSICert(String isPermntSSICert);
	
	public String getIsCapitalMarketExposer();
	public void setIsCapitalMarketExposer(String isCapitalMarketExposer);
	
	public String getIsRealEstateExposer();
	public void setIsRealEstateExposer(String isRealEstateExposer);
	
	public String getEstateType();
	public void setEstateType(String estateType);
	
	public String getIsPrioritySector();
	public void setIsPrioritySector(String isPrioritySector);
		
	public String getPrioritySector();
	public void setPrioritySector(String prioritySector);
	
	public String getSecurity1();
	public void setSecurity1(String security1);
	
	public String getSecurity2();
	public void setSecurity2(String security2);
	
	public String getSecurity3();
	public void setSecurity3(String security3);
	
	public String getSecurity4();
	public void setSecurity4(String security4);
	
	public String getSecurity5();
	public void setSecurity5(String security5);
	
	public String getSecurity6();
	public void setSecurity6(String security6);

	public String getFacilitySystem();
	public void setFacilitySystem(String facilitySystem);
	
	public String getFacilitySystemID();
	public void setFacilitySystemID(String facilitySystemID);
	
	public String getLineNo();
	public void setLineNo(String lineNo);
	
	public String getIntRateFix();
	public void setIntRateFix(String intRateFix);
	
	public String getCommRealEstateType();
	public void setCommRealEstateType(String commRealEstateType);
	
	public String getUploadStatus();
	public void setUploadStatus(String uploadStatus) ;
	
	//file upload audit trail at tranch level
	public  String getCreatedBy();
	public  void setCreatedBy(String createdBy);
	
	public  Date getCreatedOn();
	public  void setCreatedOn(Date createdOn);
	
	public  String getUpdatedBy();
	public  void setUpdatedBy(String updatedBy);
	
	public  Date getUpdatedOn();
	public  void setUpdatedOn(Date updatedOn);
	
	public  String getHiddenSerialNo();
	public  void setHiddenSerialNo(String hiddenSerialNo);
	

	
	public String getLiabBranch();
	public void setLiabBranch(String liabBranch);

	public String getCurrencyRestriction();
	public void setCurrencyRestriction(String currencyRestriction);

	public String getMainLineCode();
	public void setMainLineCode(String mainLineCode);

	public String getCurrency();
	public void setCurrency(String currency);

	

	public String getAvailable();
	public void setAvailable(String available);

	public String getFreeze();
	public void setFreeze(String freeze);

	public String getRevolvingLine();
	public void setRevolvingLine(String revolvingLine);

	public String getScmFlag();
	public void setScmFlag(String scmFlag);
	
	public void setVendorDtls(String vendorDtls);
	public String getVendorDtls();
	
	public String getCloseFlag();
	public void setCloseFlag(String closeFlag);

	public Date getLastavailableDate();
	public void setLastavailableDate(Date lastavailableDate);

	public Date getUploadDate();
	public void setUploadDate(Date uploadDate);

	public String getSegment();
	public void setSegment(String segment);

	public String getRuleId();
	public void setRuleId(String ruleId);

	public String getUncondiCancl();
	public void setUncondiCancl(String uncondiCancl);

	

	public String getLimitTenorDays();
	public void setLimitTenorDays(String limitTenorDays);

	public String getInternalRemarks();
	public void setInternalRemarks(String internalRemarks);

	

	public String getCoreStpRejectedReason();
	public void setCoreStpRejectedReason(String coreStpRejectedReason);

	
	public String getSourceRefNo() ;
	public void setSourceRefNo(String sourceRefNo) ;

	public Date getLimitStartDate() ;
	public void setLimitStartDate(Date limitStartDate) ;

	public String getAction() ;
	public void setAction(String action) ;

	public String getStatus() ;
	public void setStatus(String status);
	
	//Start Santosh UBS-LIMIT CR
	public String getLimitRestrictionFlag();
	public void setLimitRestrictionFlag(String limitRestrictionFlag);

	public String getBranchAllowedFlag();
	public void setBranchAllowedFlag(String branchAllowedFlag);

	public String getProductAllowedFlag();
	public void setProductAllowedFlag(String productAllowedFlag);

	public String getCurrencyAllowedFlag();
	public void setCurrencyAllowedFlag(String currencyAllowedFlag);
	
	public String getIsCapitalMarketExposerFlag();
	public void setIsCapitalMarketExposerFlag(String isCapitalMarketExposerFlag); 

	public String getSegment1Flag(); 
	public void setSegment1Flag(String segment1Flag); 

	public String getEstateTypeFlag(); 
	public void setEstateTypeFlag(String estateTypeFlag); 

	public String getPrioritySectorFlag(); 
	public void setPrioritySectorFlag(String prioritySectorFlag); 
	//End Santosh UBS-LIMIT CR
	
	public String getLimitRestriction();
	public void setLimitRestriction(String limitRestriction);

	public String getBranchAllowed();
	public void setBranchAllowed(String branchAllowed);

	public String getProductAllowed();
	public void setProductAllowed(String productAllowed);

	public String getCurrencyAllowed();
	public void setCurrencyAllowed(String currencyAllowed);

	public String getUdfAllowed();
	public void setUdfAllowed(String udfAllowed);
	
	public String getUdfAllowed_2();
	public void setUdfAllowed_2(String udfAllowed_2);
	
	public String getUncondiCanclFlag(); 
	public void setUncondiCanclFlag(String uncondiCanclFlag); 
	
	public String getUdfDelete();
	public void setUdfDelete(String udfDelete);
	
	public String getUdfDelete_2();
	public void setUdfDelete_2(String udfDelete_2);
		
	//added by santosh UBS LIMIT UOLOAD
	public ILimitXRefUdf[] getXRefUdfData();
	public void setXRefUdfData(ILimitXRefUdf[] udfData);
	
	//Covenant
	public ILineCovenant[] getLineCovenant();
	public void setLineCovenant(ILineCovenant[] covenantData);
	
	public ILimitXRefUdf2[] getXRefUdfData2();
	public void setXRefUdfData2(ILimitXRefUdf2[] udfData);
	
	public ILimitXRefCoBorrower[] getXRefCoBorrowerData();
	public void setXRefCoBorrowerData(ILimitXRefCoBorrower[] coBorrowerData);
	
	
	public String getSendToCore();
	public void setSendToCore(String sendToCore);
	
	
	public String getSendToFile();
	public void setSendToFile(String sendToFile);
	
	public String getCommRealEstateTypeFlag();
	public void setCommRealEstateTypeFlag(String commRealEstateTypeFlag);
	
	public String getIsDayLightLimitAvailable();
	public void setIsDayLightLimitAvailable(String isDayLightLimitAvailable);

	public String getDayLightLimitApproved();
	public void setDayLightLimitApproved(String dayLightLimitApproved);

	public String getTenure();
	public void setTenure(String tenure);
	
	public String getSellDownPeriod();
	public void setSellDownPeriod(String sellDownPeriod);

	public String getLiabilityId();
	public void setLiabilityId(String liabilityId);

	public String getLimitRemarks();
	public void setLimitRemarks(String limitRemarks);
	
	public String getBankingArrangement();
	public void setBankingArrangement(String bankingArrangement);
	
	public String getStockLimitFlag();
	public void setStockLimitFlag(String stockLimitFlag);
	
	public String getStockDocMonthForLmt();
	public void setStockDocMonthForLmt(String stockDocMonthForLmt);

	public String getStockDocYearForLmt();
	public void setStockDocYearForLmt(String stockDocYearForLmt);
	
	public String getAdhocLine();
	public void setAdhocLine(String adhocLine);

	public String getCheckerIdNew();
	public void setCheckerIdNew(String checkerIdNew);
	
	
	public Date getIdlEffectiveFromDate();
	public void setIdlEffectiveFromDate(Date idlEffectiveFromDate);

	public Date getIdlExpiryDate();
	public void setIdlExpiryDate(Date idlExpiryDate);

	public String getIdlAmount();
	public void setIdlAmount(String idlAmount);
	
}