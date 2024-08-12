/*
 * Created on 2007-2-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.io.Serializable;
import java.util.List;
import com.integrosys.cms.app.customer.bus.ILimitXRefCoBorrower;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.cms.ui.udf.IUserExtendable;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class XRefDetailForm extends CommonForm implements Serializable ,IUserExtendable{
	private String fromEvent;

	private String limitProfileID;

	private String indexID;
	
	private String lineDetailId;

	private String isCreate;

	private String hostSystemCountry;

	private String hostSystemCountryDisp;

	private String hostSystemName;

	private String hostSystemNameDisp;

	private String accountNo;

	private String acctClassification;

	private String effectiveDate;

	private String acctStatus;

	private String netOutstandingAmt;

	private String netOutstandingCcy;

	private String realizableVal;

	private String rvCcy;
	
	
	//added by Shiv facility detail module.
	private String customerID;

	private String serialNo;
	
	private String interestRateType;
	
	private String intRateFloatingType;
	
	private String intRateFloatingRange;
	
	private String intRateFloatingMarginFlag;
	
	private String intRateFloatingMargin;
	
	private String releasedAmount;
	
	private String utilizedAmount;
	
	private String releaseDate;
	
	private String intradayLimitExpiryDate;
	
	private String dayLightLimit;
	
	private String dateOfReset;
	
	private String isPermntSSICert;
	
	private String isCapitalMarketExposer;
	
	private String isRealEstateExposer;
	
	private String estateType;
	
	private String isPrioritySector;
	
	private String prioritySector;
	
	private String security1;
	
	private String security2;
	
	private String security3;
	
	private String security4;
	
	private String security5;
	
	private String security6;
	
	private String facilitySystem;

	private String facilitySystemID;
	
	private String lineNo;
	
	private String intRateFix;
	
	private String commRealEstateType;
	
	private String createdBy;
	
	private String createdOn;
	
	private String updatedBy;
	
	private String updatedOn;
	
	private String hiddenSerialNo;
	
	private String edited;
	
	
	private String liabBranch;
	
	private String currencyRestriction;
	
	private String mainLineCode;
	
	private String currency;
	
	private String intradayLimitFlag;
	
	
	private String available;
	
	private String freeze;
	
	private String revolvingLine;
	
	private String closeFlag;
	
	private String lastavailableDate;
	
	private String uploadDate;
	
	private String segment;
	
	private String ruleId;
	
	private String uncondiCancl;
	
	
	
	private String limitTenorDays;
	
	private String limitRestriction;
	
	private String branchAllowed;
	
	private String productAllowed;
	
	private String currencyAllowed;
	
	private String internalRemarks;
	
	
	
	private String coreStpRejectedReason;
	
	private String udfAllowed;
	
	private String sourceRefNo;
	
	private String limitStartDate;
	
	private String action;
	
	private String status;
	
	//added by Santosh ubs limit
	private String udfDelete;
	
	private String tenure;
	
	private String sellDownPeriod;
	
	private String liabilityId;
	
	private String limitRemarks;
		
	
	private String isDayLightLimitAvailable;
	
	private String dayLightLimitApproved;
	
	private String stockLimitFlag;

	private String stockDocMonthForLmt;
	
	private String stockDocYearForLmt;
	
	private String scfStatus;
	
	private String scfFailedReason;
	
	private String radioInterfaceCompleted;
	
	private String responserequestDate;
	
	private String checkerIdNew;
	
	
	private String idlEffectiveFromDate;
	
	private String idlExpiryDate;
	
	private String idlAmount;
	
	private String releaseableAmountCheck;
	
	private String idlApplicableFlagCheck;
	

	public String getReleaseableAmountCheck() {
		return releaseableAmountCheck;
	}

	public void setReleaseableAmountCheck(String releaseableAmountCheck) {
		this.releaseableAmountCheck = releaseableAmountCheck;
	}

	public String getIdlApplicableFlagCheck() {
		return idlApplicableFlagCheck;
	}

	public void setIdlApplicableFlagCheck(String idlApplicableFlagCheck) {
		this.idlApplicableFlagCheck = idlApplicableFlagCheck;
	}

	public String getIdlEffectiveFromDate() {
		return idlEffectiveFromDate;
	}

	public void setIdlEffectiveFromDate(String idlEffectiveFromDate) {
		this.idlEffectiveFromDate = idlEffectiveFromDate;
	}

	public String getIdlExpiryDate() {
		return idlExpiryDate;
	}

	public void setIdlExpiryDate(String idlExpiryDate) {
		this.idlExpiryDate = idlExpiryDate;
	}

	public String getIdlAmount() {
		return idlAmount;
	}

	public void setIdlAmount(String idlAmount) {
		this.idlAmount = idlAmount;
	}
	
	
	public String getCheckerIdNew() {
		return checkerIdNew;
	}

	public void setCheckerIdNew(String checkerIdNew) {
		this.checkerIdNew = checkerIdNew;
	}
	
	public String getRadioInterfaceCompleted() {
		return radioInterfaceCompleted;
	}

	public void setRadioInterfaceCompleted(String radioInterfaceCompleted) {
		this.radioInterfaceCompleted = radioInterfaceCompleted;
	}

	public String getResponserequestDate() {
		return responserequestDate;
	}

	public void setResponserequestDate(String responserequestDate) {
		this.responserequestDate = responserequestDate;
	}


	
	/*private String ecbfStatus;
	
	private String ecbfUpdatedDate;
	
	public String getEcbfUpdatedDate() {
		return ecbfUpdatedDate;
	}

	public void setEcbfUpdatedDate(String ecbfUpdatedDate) {
		this.ecbfUpdatedDate = ecbfUpdatedDate;
	}

	public String getEcbfStatus() {
		return ecbfStatus;
	}

	public void setEcbfStatus(String ecbfStatus) {
		this.ecbfStatus = ecbfStatus;
	}
	*/
	public String getScfStatus() {
		return scfStatus;
	}

	public void setScfStatus(String scfStatus) {
		this.scfStatus = scfStatus;
	}

	public String getScfFailedReason() {
		return scfFailedReason;
	}

	public void setScfFailedReason(String scfFailedReason) {
		this.scfFailedReason = scfFailedReason;
	}

	
	public String getStockDocMonthForLmt() {
		return stockDocMonthForLmt;
	}

	public void setStockDocMonthForLmt(String stockDocMonthForLmt) {
		this.stockDocMonthForLmt = stockDocMonthForLmt;
	}

	public String getStockDocYearForLmt() {
		return stockDocYearForLmt;
	}

	public void setStockDocYearForLmt(String stockDocYearForLmt) {
		this.stockDocYearForLmt = stockDocYearForLmt;
	}

	public String getStockLimitFlag() {
		return stockLimitFlag;
	}

	public void setStockLimitFlag(String stockLimitFlag) {
		this.stockLimitFlag = stockLimitFlag;
	}

	public String getUdfDelete() {
		return udfDelete;
	}

	public void setUdfDelete(String udfDelete) {
		this.udfDelete = udfDelete;
	}

	private String udfDelete_2;
	public String getUdfDelete_2() {
		return udfDelete_2;
	}

	public void setUdfDelete_2(String udfDelete_2) {
		this.udfDelete_2 = udfDelete_2;
	}
	
	public String getSourceRefNo() {
		return sourceRefNo;
	}


	public void setSourceRefNo(String sourceRefNo) {
		this.sourceRefNo = sourceRefNo;
	}


	public String getLimitStartDate() {
		return limitStartDate;
	}


	public void setLimitStartDate(String limitStartDate) {
		this.limitStartDate = limitStartDate;
	}


	public String getAction() {
		return action;
	}


	public void setAction(String action) {
		this.action = action;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	//Start Santosh UBS_LIMIT CR
		private String limitRestrictionFlag;
			
		private String branchAllowedFlag;
			
		private String productAllowedFlag;
			
		private String currencyAllowedFlag;
		
		private String isCapitalMarketExposerFlag;
		
		private String segment1Flag;
			
		private String estateTypeFlag;
			
		private String prioritySectorFlag;
		
		private String allAliasBranchCodeList;
		
		private String aliasBranchCodes;
		
		private String allProductAllowedList;
		
		private String productCodes;
		private String allCurrencyAllowedList;
		
		private String currencyCodes;	
		
		private String uncondiCanclFlag;
		
		private String bankingArrangement;
		
		//added by santosh udf limit
		private String allUdfList;
		private String udfList;
		
		private String scmFlag;
		
		private String vendorDtls;
		
		public String getVendorDtls() {
			return vendorDtls;
		}

		public void setVendorDtls(String vendorDtls) {
			this.vendorDtls = vendorDtls;
		}

		public String getScmFlag() {
			return scmFlag;
		}

		public void setScmFlag(String scmFlag) {
			this.scmFlag = scmFlag;
		}

		public String getAllUdfList() {
			return allUdfList;
		}


		public void setAllUdfList(String allUdfList) {
			this.allUdfList = allUdfList;
		}


		public String getUdfList() {
			return udfList;
		}


		public void setUdfList(String udfList) {
			this.udfList = udfList;

		}
		//End santosh udf limit
		private String limitType;
		
		public String getLimitType() {
			return limitType;
		}


		public void setLimitType(String limitType) {
			this.limitType = limitType;
		}


		/**
		 * @return the uncondiCanclFlag
		 */
		public String getUncondiCanclFlag() {
			return uncondiCanclFlag;
		}


		/**
		 * @param uncondiCanclFlag the uncondiCanclFlag to set
		 */
		public void setUncondiCanclFlag(String uncondiCanclFlag) {
			this.uncondiCanclFlag = uncondiCanclFlag;
		}


		public String getAllAliasBranchCodeList() {
			return allAliasBranchCodeList;
		}


		public void setAllAliasBranchCodeList(String allAliasBranchCodeList) {
			this.allAliasBranchCodeList = allAliasBranchCodeList;
		}


		public String getAliasBranchCodes() {
			return aliasBranchCodes;
		}


		public void setAliasBranchCodes(String aliasBranchCodes) {
			this.aliasBranchCodes = aliasBranchCodes;
		}


		public String getAllProductAllowedList() {
			return allProductAllowedList;
		}


		public void setAllProductAllowedList(String allProductAllowedList) {
			this.allProductAllowedList = allProductAllowedList;
		}


		public String getProductCodes() {
			return productCodes;
		}


		public void setProductCodes(String productCodes) {
			this.productCodes = productCodes;
		}


		public String getAllCurrencyAllowedList() {
			return allCurrencyAllowedList;
		}


		public void setAllCurrencyAllowedList(String allCurrencyAllowedList) {
			this.allCurrencyAllowedList = allCurrencyAllowedList;
		}


		public String getCurrencyCodes() {
			return currencyCodes;
		}


		public void setCurrencyCodes(String currencyCodes) {
			this.currencyCodes = currencyCodes;
		}


		public String getAppendAliasBranchCodeList() {
			return appendAliasBranchCodeList;
		}


		public void setAppendAliasBranchCodeList(String appendAliasBranchCodeList) {
			this.appendAliasBranchCodeList = appendAliasBranchCodeList;
		}

		private String appendAliasBranchCodeList;
		
		
		
		
		
		public String getLimitRestrictionFlag() {
			return limitRestrictionFlag;
		}

		public void setLimitRestrictionFlag(String limitRestrictionFlag) {
			this.limitRestrictionFlag = limitRestrictionFlag;
		}

		public String getBranchAllowedFlag() {
			return branchAllowedFlag;
		}

		public void setBranchAllowedFlag(String branchAllowedFlag) {
			this.branchAllowedFlag = branchAllowedFlag;
		}

		public String getProductAllowedFlag() {
			return productAllowedFlag;
		}

		public void setProductAllowedFlag(String productAllowedFlag) {
			this.productAllowedFlag = productAllowedFlag;
		}
		
		public String getCurrencyAllowedFlag() {
			return currencyAllowedFlag;
		}

		public void setCurrencyAllowedFlag(String currencyAllowedFlag) {
			this.currencyAllowedFlag = currencyAllowedFlag;
		}
		
		public String getIsCapitalMarketExposerFlag() {
			return isCapitalMarketExposerFlag;
		}

		public void setIsCapitalMarketExposerFlag(String isCapitalMarketExposerFlag) {
			this.isCapitalMarketExposerFlag = isCapitalMarketExposerFlag;
		}

		public String getSegment1Flag() {
			return segment1Flag;
		}

		public void setSegment1Flag(String segment1Flag) {
			this.segment1Flag = segment1Flag;
		}

		public String getEstateTypeFlag() {
			return estateTypeFlag;
		}

		public void setEstateTypeFlag(String estateTypeFlag) {
			this.estateTypeFlag = estateTypeFlag;
		}

		public String getPrioritySectorFlag() {
			return prioritySectorFlag;
		}

		public void setPrioritySectorFlag(String prioritySectorFlag) {
			this.prioritySectorFlag = prioritySectorFlag;
		}
		//End Santosh UBS_LIMIT CR
		
	public String getLiabBranch() {
		return liabBranch;
	}


	public void setLiabBranch(String liabBranch) {
		this.liabBranch = liabBranch;
	}


	public String getCurrencyRestriction() {
		return currencyRestriction;
	}


	public void setCurrencyRestriction(String currencyRestriction) {
		this.currencyRestriction = currencyRestriction;
	}


	public String getMainLineCode() {
		return mainLineCode;
	}


	public void setMainLineCode(String mainLineCode) {
		this.mainLineCode = mainLineCode;
	}


	public String getCurrency() {
		return currency;
	}


	public void setCurrency(String currency) {
		this.currency = currency;
	}


	


	public String getAvailable() {
		return available;
	}


	public void setAvailable(String available) {
		this.available = available;
	}


	public String getFreeze() {
		return freeze;
	}


	public void setFreeze(String freeze) {
		this.freeze = freeze;
	}


	public String getRevolvingLine() {
		return revolvingLine;
	}


	public void setRevolvingLine(String revolvingLine) {
		this.revolvingLine = revolvingLine;
	}


	public String getCloseFlag() {
		return closeFlag;
	}


	public void setCloseFlag(String closeFlag) {
		this.closeFlag = closeFlag;
	}


	public String getLastavailableDate() {
		return lastavailableDate;
	}


	public void setLastavailableDate(String lastavailableDate) {
		this.lastavailableDate = lastavailableDate;
	}


	public String getUploadDate() {
		return uploadDate;
	}


	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}


	public String getSegment() {
		return segment;
	}


	public void setSegment(String segment) {
		this.segment = segment;
	}


	public String getRuleId() {
		return ruleId;
	}


	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}


	public String getUncondiCancl() {
		return uncondiCancl;
	}


	public void setUncondiCancl(String uncondiCancl) {
		this.uncondiCancl = uncondiCancl;
	}


	

	public String getLimitTenorDays() {
		return limitTenorDays;
	}


	public void setLimitTenorDays(String limitTenorDays) {
		this.limitTenorDays = limitTenorDays;
	}


	public String getLimitRestriction() {
		return limitRestriction;
	}


	public void setLimitRestriction(String limitRestriction) {
		this.limitRestriction = limitRestriction;
	}


	public String getBranchAllowed() {
		return branchAllowed;
	}


	public void setBranchAllowed(String branchAllowed) {
		this.branchAllowed = branchAllowed;
	}


	public String getProductAllowed() {
		return productAllowed;
	}


	public void setProductAllowed(String productAllowed) {
		this.productAllowed = productAllowed;
	}


	public String getCurrencyAllowed() {
		return currencyAllowed;
	}


	public void setCurrencyAllowed(String currencyAllowed) {
		this.currencyAllowed = currencyAllowed;
	}


	public String getInternalRemarks() {
		return internalRemarks;
	}


	public void setInternalRemarks(String internalRemarks) {
		this.internalRemarks = internalRemarks;
	}

	public String getCoreStpRejectedReason() {
		return coreStpRejectedReason;
	}


	public void setCoreStpRejectedReason(String coreStpRejectedReason) {
		this.coreStpRejectedReason = coreStpRejectedReason;
	}


	public String getUdfAllowed() {
		return udfAllowed;
	}


	public void setUdfAllowed(String udfAllowed) {
		this.udfAllowed = udfAllowed;
	}


	//Code added for Upload Status Column
	private String uploadStatus;
	public String getCommRealEstateType() {
		return commRealEstateType;
	}

	
	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public void setCommRealEstateType(String commRealEstateType) {
		this.commRealEstateType = commRealEstateType;
	}

	public String getIntRateFix() {
		return intRateFix;
	}

	public void setIntRateFix(String intRateFix) {
		this.intRateFix = intRateFix;
	}

	public String getFacilitySystem() {
		return facilitySystem;
	}

	public void setFacilitySystem(String facilitySystem) {
		this.facilitySystem = facilitySystem;
	}

	public String getFacilitySystemID() {
		return facilitySystemID;
	}

	public void setFacilitySystemID(String facilitySystemID) {
		this.facilitySystemID = facilitySystemID;
	}

	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	
	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getInterestRateType() {
		return interestRateType;
	}

	public void setInterestRateType(String interestRateType) {
		this.interestRateType = interestRateType;
	}

	public String getIntRateFloatingType() {
		return intRateFloatingType;
	}

	public void setIntRateFloatingType(String intRateFloatingType) {
		this.intRateFloatingType = intRateFloatingType;
	}

	public String getIntRateFloatingRange() {
		return intRateFloatingRange;
	}

	public void setIntRateFloatingRange(String intRateFloatingRange) {
		this.intRateFloatingRange = intRateFloatingRange;
	}

	public String getIntRateFloatingMarginFlag() {
		return intRateFloatingMarginFlag;
	}

	public void setIntRateFloatingMarginFlag(String intRateFloatingMarginFlag) {
		this.intRateFloatingMarginFlag = intRateFloatingMarginFlag;
	}

	public String getIntRateFloatingMargin() {
		return intRateFloatingMargin;
	}

	public void setIntRateFloatingMargin(String intRateFloatingMargin) {
		this.intRateFloatingMargin = intRateFloatingMargin;
	}

	public String getReleasedAmount() {
		return releasedAmount;
	}

	public void setReleasedAmount(String releasedAmount) {
		this.releasedAmount = releasedAmount;
	}

	public String getUtilizedAmount() {
		return utilizedAmount;
	}

	public void setUtilizedAmount(String utilizedAmount) {
		this.utilizedAmount = utilizedAmount;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public void setIntradayLimitExpiryDate(String intradayLimitExpiryDate) {
		this.intradayLimitExpiryDate = intradayLimitExpiryDate;
	}
	
	public String getIntradayLimitExpiryDate() {
		return intradayLimitExpiryDate;
	}
	
	public void setDayLightLimit(String dayLightLimit) {
		this.dayLightLimit = dayLightLimit;
	}
	
	public String getDayLightLimit() {
		return dayLightLimit;
	}
	
	/**
	 * @return the intradayLimitFlag
	 */
	public String getIntradayLimitFlag() {
		return intradayLimitFlag;
	}

	/**
	 * @param intradayLimit the intradayLimit to set
	 */
	public void setIntradayLimitFlag(String intradayLimitFlag) {
		this.intradayLimitFlag = intradayLimitFlag;
	}


	public String getDateOfReset() {
		return dateOfReset;
	}

	public void setDateOfReset(String dateOfReset) {
		this.dateOfReset = dateOfReset;
	}

	public String getIsPermntSSICert() {
		return isPermntSSICert;
	}

	public void setIsPermntSSICert(String isPermntSSICert) {
		this.isPermntSSICert = isPermntSSICert;
	}

	public String getIsCapitalMarketExposer() {
		return isCapitalMarketExposer;
	}

	public void setIsCapitalMarketExposer(String isCapitalMarketExposer) {
		this.isCapitalMarketExposer = isCapitalMarketExposer;
	}

	public String getIsRealEstateExposer() {
		return isRealEstateExposer;
	}

	public void setIsRealEstateExposer(String isRealEstateExposer) {
		this.isRealEstateExposer = isRealEstateExposer;
	}

	public String getEstateType() {
		return estateType;
	}

	public void setEstateType(String estateType) {
		this.estateType = estateType;
	}

	public String getIsPrioritySector() {
		return isPrioritySector;
	}

	public void setIsPrioritySector(String isPrioritySector) {
		this.isPrioritySector = isPrioritySector;
	}

	public String getPrioritySector() {
		return prioritySector;
	}

	public void setPrioritySector(String prioritySector) {
		this.prioritySector = prioritySector;
	}


	
	public String getSecurity1() {
		return security1;
	}


	public void setSecurity1(String security1) {
		this.security1 = security1;
	}


	public String getSecurity2() {
		return security2;
	}


	public void setSecurity2(String security2) {
		this.security2 = security2;
	}


	public String getSecurity3() {
		return security3;
	}


	public void setSecurity3(String security3) {
		this.security3 = security3;
	}


	public String getSecurity4() {
		return security4;
	}


	public void setSecurity4(String security4) {
		this.security4 = security4;
	}


	public String getSecurity5() {
		return security5;
	}


	public void setSecurity5(String security5) {
		this.security5 = security5;
	}


	public String getSecurity6() {
		return security6;
	}


	public void setSecurity6(String security6) {
		this.security6 = security6;
	}


	/**
	 * @return Returns the limitProfileId.
	 */
	public String getLimitProfileID() {
		return limitProfileID;
	}

	/**
	 * @param limitProfileId The limitProfileId to set.
	 */
	public void setLimitProfileID(String limitProfileID) {
		this.limitProfileID = limitProfileID;
	}

	/**
	 * @return Returns the fromEvent.
	 */
	public String getFromEvent() {
		return fromEvent;
	}

	/**
	 * @param fromEvent The fromEvent to set.
	 */
	public void setFromEvent(String fromEvent) {
		this.fromEvent = fromEvent;
	}

	/**
	 * @return Returns the indexID.
	 */
	public String getIndexID() {
		return indexID;
	}

	/**
	 * @param indexID The indexID to set.
	 */
	public void setIndexID(String indexID) {
		this.indexID = indexID;
	}

	/**
	 * @return Returns the isCreate.
	 */
	public String getIsCreate() {
		return isCreate;
	}

	/**
	 * @param isCreate The isCreate to set.
	 */
	public void setIsCreate(String isCreate) {
		this.isCreate = isCreate;
	}

	/**
	 * @return Returns the accountNo.
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * @param accountNo The accountNo to set.
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/**
	 * @return Returns the hostSystemCountry.
	 */
	public String getHostSystemCountry() {
		return hostSystemCountry;
	}

	/**
	 * @param hostSystemCountry The hostSystemCountry to set.
	 */
	public void setHostSystemCountry(String hostSystemCountry) {
		this.hostSystemCountry = hostSystemCountry;
	}

	/**
	 * @return Returns the hostSystemName.
	 */
	public String getHostSystemName() {
		return hostSystemName;
	}

	/**
	 * @param hostSystemName The hostSystemName to set.
	 */
	public void setHostSystemName(String hostSystemName) {
		this.hostSystemName = hostSystemName;
	}

	/**
	 * @return Returns the acctClassification.
	 */
	public String getAcctClassification() {
		return acctClassification;
	}

	/**
	 * @param acctClassification The acctClassification to set.
	 */
	public void setAcctClassification(String acctClassification) {
		this.acctClassification = acctClassification;
	}

	/**
	 * @return Returns the acctStatus.
	 */
	public String getAcctStatus() {
		return acctStatus;
	}

	/**
	 * @param acctStatus The acctStatus to set.
	 */
	public void setAcctStatus(String acctStatus) {
		this.acctStatus = acctStatus;
	}

	/**
	 * @return Returns the hostSystemCountryDisp.
	 */
	public String getHostSystemCountryDisp() {
		return hostSystemCountryDisp;
	}

	/**
	 * @param hostSystemCountryDisp The hostSystemCountryDisp to set.
	 */
	public void setHostSystemCountryDisp(String hostSystemCountryDisp) {
		this.hostSystemCountryDisp = hostSystemCountryDisp;
	}

	/**
	 * @return Returns the hostSystemNameDisp.
	 */
	public String getHostSystemNameDisp() {
		return hostSystemNameDisp;
	}

	/**
	 * @param hostSystemNameDisp The hostSystemNameDisp to set.
	 */
	public void setHostSystemNameDisp(String hostSystemNameDisp) {
		this.hostSystemNameDisp = hostSystemNameDisp;
	}

	/**
	 * @return Returns the effectiveDate.
	 */
	public String getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * @param effectiveDate The effectiveDate to set.
	 */
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getNetOutstandingAmt() {
		return netOutstandingAmt;
	}

	public void setNetOutstandingAmt(String netOutstandingAmt) {
		this.netOutstandingAmt = netOutstandingAmt;
	}

	public String getNetOutstandingCcy() {
		return netOutstandingCcy;
	}

	public void setNetOutstandingCcy(String netOutstandingCcy) {
		this.netOutstandingCcy = netOutstandingCcy;
	}

	public String getRealizableVal() {
		return realizableVal;
	}

	public void setRealizableVal(String realizableVal) {
		this.realizableVal = realizableVal;
	}

	public String getRvCcy() {
		return rvCcy;
	}

	public void setRvCcy(String rvCcy) {
		this.rvCcy = rvCcy;
	}
	
	

	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}


	public String getUpdatedBy() {
		return updatedBy;
	}


	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}


	public String getUpdatedOn() {
		return updatedOn;
	}


	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}


	public String[][] getMapper() {
		// TODO Auto-generated method stub
		String[][] input = { { "xrefDetailForm", "com.integrosys.cms.ui.manualinput.limit.XRefDetailMapper" }, };
		return input;
	}

	
	

	public String getLineDetailId() {
		return lineDetailId;
	}


	public void setLineDetailId(String lineDetailId) {
		this.lineDetailId = lineDetailId;
	}


	public String getUploadStatus() {
		return uploadStatus;
	}


	public void setUploadStatus(String uploadStatus) {
		this.uploadStatus = uploadStatus;
	}


	public String getHiddenSerialNo() {
		return hiddenSerialNo;
	}


	public void setHiddenSerialNo(String hiddenSerialNo) {
		this.hiddenSerialNo = hiddenSerialNo;
	}


	public String getEdited() {
		return edited;
	}


	public void setEdited(String edited) {
		this.edited = edited;
	}
	
	// Start Santosh UBS-LIMIT-UPLOAD
		private String udf1_Label;
		private String udf2_Label;
		private String udf3_Label;
		private String udf4_Label;
		private String udf5_Label;
		private String udf6_Label;
		private String udf7_Label;
		private String udf8_Label;
		private String udf9_Label;
		private String udf10_Label;
		private String udf11_Label;
		private String udf12_Label;
		private String udf13_Label;
		private String udf14_Label;
		private String udf15_Label;
		private String udf16_Label;
		private String udf17_Label;
		private String udf18_Label;
		private String udf19_Label;
		private String udf20_Label;
		private String udf21_Label;
		private String udf22_Label;
		private String udf23_Label;
		private String udf24_Label;
		private String udf25_Label;
		private String udf26_Label;
		private String udf27_Label;
		private String udf28_Label;
		private String udf29_Label;
		private String udf30_Label;
		private String udf31_Label;
		private String udf32_Label;
		private String udf33_Label;
		private String udf34_Label;
		private String udf35_Label;
		private String udf36_Label;
		private String udf37_Label;
		private String udf38_Label;
		private String udf39_Label;
		private String udf40_Label;
		private String udf41_Label;
		private String udf42_Label;
		private String udf43_Label;
		private String udf44_Label;
		private String udf45_Label;
		private String udf46_Label;
		private String udf47_Label;
		private String udf48_Label;
		private String udf49_Label;
		private String udf50_Label;
		private String udf51_Label;
		private String udf52_Label;
		private String udf53_Label;
		private String udf54_Label;
		private String udf55_Label;
		private String udf56_Label;
		private String udf57_Label;
		private String udf58_Label;
		private String udf59_Label;
		private String udf60_Label;
		private String udf61_Label;
		private String udf62_Label;
		private String udf63_Label;
		private String udf64_Label;
		private String udf65_Label;
		private String udf66_Label;
		private String udf67_Label;
		private String udf68_Label;
		private String udf69_Label;
		private String udf70_Label;
		private String udf71_Label;
		private String udf72_Label;
		private String udf73_Label;
		private String udf74_Label;
		private String udf75_Label;
		private String udf76_Label;
		private String udf77_Label;
		private String udf78_Label;
		private String udf79_Label;
		private String udf80_Label;
		private String udf81_Label;
		private String udf82_Label;
		private String udf83_Label;
		private String udf84_Label;
		private String udf85_Label;
		private String udf86_Label;
		private String udf87_Label;
		private String udf88_Label;
		private String udf89_Label;
		private String udf90_Label;
		private String udf91_Label;
		private String udf92_Label;
		private String udf93_Label;
		private String udf94_Label;
		private String udf95_Label;
		private String udf96_Label;
		private String udf97_Label;
		private String udf98_Label;
		private String udf99_Label;
		private String udf100_Label;
		
		private String udf101_Label;
		private String udf102_Label;
		private String udf103_Label;
		private String udf104_Label;
		private String udf105_Label;
		 
		 	private String udf106_Label;
		    private String udf107_Label;
		    private String udf108_Label;
		    private String udf109_Label;
		    private String udf110_Label;
		    private String udf111_Label;
		    private String udf112_Label;
		    private String udf113_Label;
		    private String udf114_Label;
		    private String udf115_Label;
		  
		    private String udf116_Label;
		    private String udf117_Label;
		    private String udf118_Label;
		    private String udf119_Label;
		    private String udf120_Label;
		    private String udf121_Label;
		    private String udf122_Label;
		    private String udf123_Label;
		    private String udf124_Label;
		    private String udf125_Label;
		    private String udf126_Label;
		    private String udf127_Label;
		    private String udf128_Label;
		    private String udf129_Label;
		    private String udf130_Label;
		    private String udf131_Label;
		    private String udf132_Label;
		    private String udf133_Label;
		    private String udf134_Label;
		    private String udf135_Label;
		    private String udf136_Label;
		    private String udf137_Label;
		    private String udf138_Label;
		    private String udf139_Label;
		    private String udf140_Label;
		    private String udf141_Label;
		    private String udf142_Label;
		    private String udf143_Label;
		    private String udf144_Label;
		    private String udf145_Label;
		    private String udf146_Label;
		    private String udf147_Label;
		    private String udf148_Label;
		    private String udf149_Label;
		    private String udf150_Label;

		    private String udf151_Label;
		    private String udf152_Label;
		    private String udf153_Label;
		    private String udf154_Label;
		    private String udf155_Label;
		    private String udf156_Label;
		    private String udf157_Label;
		    private String udf158_Label;
		    private String udf159_Label;
		    private String udf160_Label;
		    private String udf161_Label;
		    private String udf162_Label;
		    private String udf163_Label;
		    private String udf164_Label;
		    private String udf165_Label;
		    private String udf166_Label;
		    private String udf167_Label;
		    private String udf168_Label;
		    private String udf169_Label;
		    private String udf170_Label;
		    private String udf171_Label;
		    private String udf172_Label;
		    private String udf173_Label;
		    private String udf174_Label;
		    private String udf175_Label;
		    private String udf176_Label;
		    private String udf177_Label;
		    private String udf178_Label;
		    private String udf179_Label;
		    private String udf180_Label;
		    private String udf181_Label;
		    private String udf182_Label;
		    private String udf183_Label;
		    private String udf184_Label;
		    private String udf185_Label;
		    private String udf186_Label;
		    private String udf187_Label;
		    private String udf188_Label;
		    private String udf189_Label;
		    private String udf190_Label;
		    private String udf191_Label;
		    private String udf192_Label;
		    private String udf193_Label;
		    private String udf194_Label;
		    private String udf195_Label;
		    private String udf196_Label;
		    private String udf197_Label;
		    private String udf198_Label;
		    private String udf199_Label;
		    private String udf200_Label;
			
		    private String udf201_Label;
		    private String udf202_Label;
		    private String udf203_Label;
		    private String udf204_Label;
		    private String udf205_Label;
		    private String udf206_Label;
		    private String udf207_Label;
		    private String udf208_Label;
		    private String udf209_Label;
		    private String udf210_Label;
		    private String udf211_Label;
		    private String udf212_Label;
		    private String udf213_Label;
		    private String udf214_Label;
		    private String udf215_Label;

		    
		private String udf1;
		private String udf2;
		private String udf3;
		private String udf4;
		private String udf5;
		private String udf6;
		private String udf7;
		private String udf8;
		private String udf9;
		private String udf10;
		private String udf11;
		private String udf12;
		private String udf13;
		private String udf14;
		private String udf15;
		private String udf16;
		private String udf17;
		private String udf18;
		private String udf19;
		private String udf20;
		private String udf21;
		private String udf22;
		private String udf23;
		private String udf24;
		private String udf25;
		private String udf26;
		private String udf27;
		private String udf28;
		private String udf29;
		private String udf30;
		private String udf31;
		private String udf32;
		private String udf33;
		private String udf34;
		private String udf35;
		private String udf36;
		private String udf37;
		private String udf38;
		private String udf39;
		private String udf40;
		private String udf41;
		private String udf42;
		private String udf43;
		private String udf44;
		private String udf45;
		private String udf46;
		private String udf47;
		private String udf48;
		private String udf49;
		private String udf50;
		private String udf51;
		private String udf52;
		private String udf53;
		private String udf54;
		private String udf55;
		private String udf56;
		private String udf57;
		private String udf58;
		private String udf59;
		private String udf60;
		private String udf61;
		private String udf62;
		private String udf63;
		private String udf64;
		private String udf65;
		private String udf66;
		private String udf67;
		private String udf68;
		private String udf69;
		private String udf70;
		private String udf71;
		private String udf72;
		private String udf73;
		private String udf74;
		private String udf75;
		private String udf76;
		private String udf77;
		private String udf78;
		private String udf79;
		private String udf80;
		private String udf81;
		private String udf82;
		private String udf83;
		private String udf84;
		private String udf85;
		private String udf86;
		private String udf87;
		private String udf88;
		private String udf89;
		private String udf90;
		private String udf91;
		private String udf92;
		private String udf93;
		private String udf94;
		private String udf95;
		private String udf96;
		private String udf97;
		private String udf98;
		private String udf99;
		private String udf100;
		
		private String udf101;
		private String udf102;
		private String udf103;
		private String udf104;
		private String udf105;
		  
		private String udf106;
	    private String udf107;
	    private String udf108;
	    private String udf109;
	    private String udf110;
	    private String udf111;
	    private String udf112;
	    private String udf113;
	    private String udf114;
	    private String udf115;
	    
	    private String udf116;
	    private String udf117;
	    private String udf118;
	    private String udf119;
	    private String udf120;
	    private String udf121;
	    private String udf122;
	    private String udf123;
	    private String udf124;
	    private String udf125;
	    private String udf126;
	    private String udf127;
	    private String udf128;
	    private String udf129;
	    private String udf130;
	    private String udf131;
	    private String udf132;
	    private String udf133;
	    private String udf134;
	    private String udf135;
	    private String udf136;
	    private String udf137;
	    private String udf138;
	    private String udf139;
	    private String udf140;
	    private String udf141;
	    private String udf142;
	    private String udf143;
	    private String udf144;
	    private String udf145;
	    private String udf146;
	    private String udf147;
	    private String udf148;
	    private String udf149;
	    private String udf150;

	    private String udf151;
	    private String udf152;
	    private String udf153;
	    private String udf154;
	    private String udf155;
	    private String udf156;
	    private String udf157;
	    private String udf158;
	    private String udf159;
	    private String udf160;
	    private String udf161;
	    private String udf162;
	    private String udf163;
	    private String udf164;
	    private String udf165;
	    private String udf166;
	    private String udf167;
	    private String udf168;
	    private String udf169;
	    private String udf170;
	    private String udf171;
	    private String udf172;
	    private String udf173;
	    private String udf174;
	    private String udf175;
	    private String udf176;
	    private String udf177;
	    private String udf178;
	    private String udf179;
	    private String udf180;
	    private String udf181;
	    private String udf182;
	    private String udf183;
	    private String udf184;
	    private String udf185;
	    private String udf186;
	    private String udf187;
	    private String udf188;
	    private String udf189;
	    private String udf190;
	    private String udf191;
	    private String udf192;
	    private String udf193;
	    private String udf194;
	    private String udf195;
	    private String udf196;
	    private String udf197;
	    private String udf198;
	    private String udf199;
	    private String udf200;
	    private String udf201;
		private String udf202;
		private String udf203;
		private String udf204;
		private String udf205;
		private String udf206;
	    private String udf207;
	    private String udf208;
	    private String udf209;
	    private String udf210;
	    private String udf211;
	    private String udf212;
	    private String udf213;
	    private String udf214;
	    private String udf215;
	 	
		private String udf1_Flag;
		private String udf2_Flag;
		private String udf3_Flag;
		private String udf4_Flag;
		private String udf5_Flag;
		private String udf6_Flag;
		private String udf7_Flag;
		private String udf8_Flag;
		private String udf9_Flag;
		private String udf10_Flag;
		private String udf11_Flag;
		private String udf12_Flag;
		private String udf13_Flag;
		private String udf14_Flag;
		private String udf15_Flag;
		private String udf16_Flag;
		private String udf17_Flag;
		private String udf18_Flag;
		private String udf19_Flag;
		private String udf20_Flag;
		private String udf21_Flag;
		private String udf22_Flag;
		private String udf23_Flag;
		private String udf24_Flag;
		private String udf25_Flag;
		private String udf26_Flag;
		private String udf27_Flag;
		private String udf28_Flag;
		private String udf29_Flag;
		private String udf30_Flag;
		private String udf31_Flag;
		private String udf32_Flag;
		private String udf33_Flag;
		private String udf34_Flag;
		private String udf35_Flag;
		private String udf36_Flag;
		private String udf37_Flag;
		private String udf38_Flag;
		private String udf39_Flag;
		private String udf40_Flag;
		private String udf41_Flag;
		private String udf42_Flag;
		private String udf43_Flag;
		private String udf44_Flag;
		private String udf45_Flag;
		private String udf46_Flag;
		private String udf47_Flag;
		private String udf48_Flag;
		private String udf49_Flag;
		private String udf50_Flag;
		private String udf51_Flag;
		private String udf52_Flag;
		private String udf53_Flag;
		private String udf54_Flag;
		private String udf55_Flag;
		private String udf56_Flag;
		private String udf57_Flag;
		private String udf58_Flag;
		private String udf59_Flag;
		private String udf60_Flag;
		private String udf61_Flag;
		private String udf62_Flag;
		private String udf63_Flag;
		private String udf64_Flag;
		private String udf65_Flag;
		private String udf66_Flag;
		private String udf67_Flag;
		private String udf68_Flag;
		private String udf69_Flag;
		private String udf70_Flag;
		private String udf71_Flag;
		private String udf72_Flag;
		private String udf73_Flag;
		private String udf74_Flag;
		private String udf75_Flag;
		private String udf76_Flag;
		private String udf77_Flag;
		private String udf78_Flag;
		private String udf79_Flag;
		private String udf80_Flag;
		private String udf81_Flag;
		private String udf82_Flag;
		private String udf83_Flag;
		private String udf84_Flag;
		private String udf85_Flag;
		private String udf86_Flag;
		private String udf87_Flag;
		private String udf88_Flag;
		private String udf89_Flag;
		private String udf90_Flag;
		private String udf91_Flag;
		private String udf92_Flag;
		private String udf93_Flag;
		private String udf94_Flag;
		private String udf95_Flag;
		private String udf96_Flag;
		private String udf97_Flag;
		private String udf98_Flag;
		private String udf99_Flag;
		private String udf100_Flag;
		
		private String udf101_Flag;
		private String udf102_Flag;
		private String udf103_Flag;
		private String udf104_Flag;
		private String udf105_Flag;
		private String udf106_Flag;
		    private String udf107_Flag;
		    private String udf108_Flag;
		    private String udf109_Flag;
		    private String udf110_Flag;
		    private String udf111_Flag;
		    private String udf112_Flag;
		    private String udf113_Flag;
		    private String udf114_Flag;
		    private String udf115_Flag;
		    
		    private String udf116_Flag;
		    private String udf117_Flag;
		    private String udf118_Flag;
		    private String udf119_Flag;
		    private String udf120_Flag;
		    private String udf121_Flag;
		    private String udf122_Flag;
		    private String udf123_Flag;
		    private String udf124_Flag;
		    private String udf125_Flag;
		    private String udf126_Flag;
		    private String udf127_Flag;
		    private String udf128_Flag;
		    private String udf129_Flag;
		    private String udf130_Flag;
		    private String udf131_Flag;
		    private String udf132_Flag;
		    private String udf133_Flag;
		    private String udf134_Flag;
		    private String udf135_Flag;
		    private String udf136_Flag;
		    private String udf137_Flag;
		    private String udf138_Flag;
		    private String udf139_Flag;
		    private String udf140_Flag;
		    private String udf141_Flag;
		    private String udf142_Flag;
		    private String udf143_Flag;
		    private String udf144_Flag;
		    private String udf145_Flag;
		    private String udf146_Flag;
		    private String udf147_Flag;
		    private String udf148_Flag;
		    private String udf149_Flag;
		    private String udf150_Flag;

		    private String udf151_Flag;
		    private String udf152_Flag;
		    private String udf153_Flag;
		    private String udf154_Flag;
		    private String udf155_Flag;
		    private String udf156_Flag;
		    private String udf157_Flag;
		    private String udf158_Flag;
		    private String udf159_Flag;
		    private String udf160_Flag;
		    private String udf161_Flag;
		    private String udf162_Flag;
		    private String udf163_Flag;
		    private String udf164_Flag;
		    private String udf165_Flag;
		    private String udf166_Flag;
		    private String udf167_Flag;
		    private String udf168_Flag;
		    private String udf169_Flag;
		    private String udf170_Flag;
		    private String udf171_Flag;
		    private String udf172_Flag;
		    private String udf173_Flag;
		    private String udf174_Flag;
		    private String udf175_Flag;
		    private String udf176_Flag;
		    private String udf177_Flag;
		    private String udf178_Flag;
		    private String udf179_Flag;
		    private String udf180_Flag;
		    private String udf181_Flag;
		    private String udf182_Flag;
		    private String udf183_Flag;
		    private String udf184_Flag;
		    private String udf185_Flag;
		    private String udf186_Flag;
		    private String udf187_Flag;
		    private String udf188_Flag;
		    private String udf189_Flag;
		    private String udf190_Flag;
		    private String udf191_Flag;
		    private String udf192_Flag;
		    private String udf193_Flag;
		    private String udf194_Flag;
		    private String udf195_Flag;
		    private String udf196_Flag;
		    private String udf197_Flag;
		    private String udf198_Flag;
		    private String udf199_Flag;
		    private String udf200_Flag;
		
		    private String udf201_Flag;
		    private String udf202_Flag;
		    private String udf203_Flag;
		    private String udf204_Flag;
		    private String udf205_Flag;
		    private String udf206_Flag;
		    private String udf207_Flag;
		    private String udf208_Flag;
		    private String udf209_Flag;
		    private String udf210_Flag;
		    private String udf211_Flag;
		    private String udf212_Flag;
		    private String udf213_Flag;
		    private String udf214_Flag;
		    private String udf215_Flag;
		
		private long udfId;
		
		
		public long getUdfId() {
			return udfId;
		}

		public void setUdfId(long id) {
			this.udfId = id;
		}
		
		public String getUdf1_Label() {
			return udf1_Label;
		}
		public void setUdf1_Label(String udf1_Label) {
			this.udf1_Label = udf1_Label;
		}
		public String getUdf2_Label() {
			return udf2_Label;
		}
		public void setUdf2_Label(String udf2_Label) {
			this.udf2_Label = udf2_Label;
		}
		public String getUdf3_Label() {
			return udf3_Label;
		}
		public void setUdf3_Label(String udf3_Label) {
			this.udf3_Label = udf3_Label;
		}
		public String getUdf4_Label() {
			return udf4_Label;
		}
		public void setUdf4_Label(String udf4_Label) {
			this.udf4_Label = udf4_Label;
		}
		public String getUdf5_Label() {
			return udf5_Label;
		}
		public void setUdf5_Label(String udf5_Label) {
			this.udf5_Label = udf5_Label;
		}
		public String getUdf6_Label() {
			return udf6_Label;
		}
		public void setUdf6_Label(String udf6_Label) {
			this.udf6_Label = udf6_Label;
		}
		public String getUdf7_Label() {
			return udf7_Label;
		}
		public void setUdf7_Label(String udf7_Label) {
			this.udf7_Label = udf7_Label;
		}
		public String getUdf8_Label() {
			return udf8_Label;
		}
		public void setUdf8_Label(String udf8_Label) {
			this.udf8_Label = udf8_Label;
		}
		public String getUdf9_Label() {
			return udf9_Label;
		}
		public void setUdf9_Label(String udf9_Label) {
			this.udf9_Label = udf9_Label;
		}
		public String getUdf10_Label() {
			return udf10_Label;
		}
		public void setUdf10_Label(String udf10_Label) {
			this.udf10_Label = udf10_Label;
		}
		public String getUdf11_Label() {
			return udf11_Label;
		}
		public void setUdf11_Label(String udf11_Label) {
			this.udf11_Label = udf11_Label;
		}
		public String getUdf12_Label() {
			return udf12_Label;
		}
		public void setUdf12_Label(String udf12_Label) {
			this.udf12_Label = udf12_Label;
		}
		public String getUdf13_Label() {
			return udf13_Label;
		}
		public void setUdf13_Label(String udf13_Label) {
			this.udf13_Label = udf13_Label;
		}
		public String getUdf14_Label() {
			return udf14_Label;
		}
		public void setUdf14_Label(String udf14_Label) {
			this.udf14_Label = udf14_Label;
		}
		public String getUdf15_Label() {
			return udf15_Label;
		}
		public void setUdf15_Label(String udf15_Label) {
			this.udf15_Label = udf15_Label;
		}
		public String getUdf16_Label() {
			return udf16_Label;
		}
		public void setUdf16_Label(String udf16_Label) {
			this.udf16_Label = udf16_Label;
		}
		public String getUdf17_Label() {
			return udf17_Label;
		}
		public void setUdf17_Label(String udf17_Label) {
			this.udf17_Label = udf17_Label;
		}
		public String getUdf18_Label() {
			return udf18_Label;
		}
		public void setUdf18_Label(String udf18_Label) {
			this.udf18_Label = udf18_Label;
		}
		public String getUdf19_Label() {
			return udf19_Label;
		}
		public void setUdf19_Label(String udf19_Label) {
			this.udf19_Label = udf19_Label;
		}
		public String getUdf20_Label() {
			return udf20_Label;
		}
		public void setUdf20_Label(String udf20_Label) {
			this.udf20_Label = udf20_Label;
		}
		public String getUdf21_Label() {
			return udf21_Label;
		}
		public void setUdf21_Label(String udf21_Label) {
			this.udf21_Label = udf21_Label;
		}
		public String getUdf22_Label() {
			return udf22_Label;
		}
		public void setUdf22_Label(String udf22_Label) {
			this.udf22_Label = udf22_Label;
		}
		public String getUdf23_Label() {
			return udf23_Label;
		}
		public void setUdf23_Label(String udf23_Label) {
			this.udf23_Label = udf23_Label;
		}
		public String getUdf24_Label() {
			return udf24_Label;
		}
		public void setUdf24_Label(String udf24_Label) {
			this.udf24_Label = udf24_Label;
		}
		public String getUdf25_Label() {
			return udf25_Label;
		}
		public void setUdf25_Label(String udf25_Label) {
			this.udf25_Label = udf25_Label;
		}
		public String getUdf26_Label() {
			return udf26_Label;
		}
		public void setUdf26_Label(String udf26_Label) {
			this.udf26_Label = udf26_Label;
		}
		public String getUdf27_Label() {
			return udf27_Label;
		}
		public void setUdf27_Label(String udf27_Label) {
			this.udf27_Label = udf27_Label;
		}
		public String getUdf28_Label() {
			return udf28_Label;
		}
		public void setUdf28_Label(String udf28_Label) {
			this.udf28_Label = udf28_Label;
		}
		public String getUdf29_Label() {
			return udf29_Label;
		}
		public void setUdf29_Label(String udf29_Label) {
			this.udf29_Label = udf29_Label;
		}
		public String getUdf30_Label() {
			return udf30_Label;
		}
		public void setUdf30_Label(String udf30_Label) {
			this.udf30_Label = udf30_Label;
		}
		public String getUdf31_Label() {
			return udf31_Label;
		}
		public void setUdf31_Label(String udf31_Label) {
			this.udf31_Label = udf31_Label;
		}
		public String getUdf32_Label() {
			return udf32_Label;
		}
		public void setUdf32_Label(String udf32_Label) {
			this.udf32_Label = udf32_Label;
		}
		public String getUdf33_Label() {
			return udf33_Label;
		}
		public void setUdf33_Label(String udf33_Label) {
			this.udf33_Label = udf33_Label;
		}
		public String getUdf34_Label() {
			return udf34_Label;
		}
		public void setUdf34_Label(String udf34_Label) {
			this.udf34_Label = udf34_Label;
		}
		public String getUdf35_Label() {
			return udf35_Label;
		}
		public void setUdf35_Label(String udf35_Label) {
			this.udf35_Label = udf35_Label;
		}
		public String getUdf36_Label() {
			return udf36_Label;
		}
		public void setUdf36_Label(String udf36_Label) {
			this.udf36_Label = udf36_Label;
		}
		public String getUdf37_Label() {
			return udf37_Label;
		}
		public void setUdf37_Label(String udf37_Label) {
			this.udf37_Label = udf37_Label;
		}
		public String getUdf38_Label() {
			return udf38_Label;
		}
		public void setUdf38_Label(String udf38_Label) {
			this.udf38_Label = udf38_Label;
		}
		public String getUdf39_Label() {
			return udf39_Label;
		}
		public void setUdf39_Label(String udf39_Label) {
			this.udf39_Label = udf39_Label;
		}
		public String getUdf40_Label() {
			return udf40_Label;
		}
		public void setUdf40_Label(String udf40_Label) {
			this.udf40_Label = udf40_Label;
		}
		public String getUdf41_Label() {
			return udf41_Label;
		}
		public void setUdf41_Label(String udf41_Label) {
			this.udf41_Label = udf41_Label;
		}
		public String getUdf42_Label() {
			return udf42_Label;
		}
		public void setUdf42_Label(String udf42_Label) {
			this.udf42_Label = udf42_Label;
		}
		public String getUdf43_Label() {
			return udf43_Label;
		}
		public void setUdf43_Label(String udf43_Label) {
			this.udf43_Label = udf43_Label;
		}
		public String getUdf44_Label() {
			return udf44_Label;
		}
		public void setUdf44_Label(String udf44_Label) {
			this.udf44_Label = udf44_Label;
		}
		public String getUdf45_Label() {
			return udf45_Label;
		}
		public void setUdf45_Label(String udf45_Label) {
			this.udf45_Label = udf45_Label;
		}
		public String getUdf46_Label() {
			return udf46_Label;
		}
		public void setUdf46_Label(String udf46_Label) {
			this.udf46_Label = udf46_Label;
		}
		public String getUdf47_Label() {
			return udf47_Label;
		}
		public void setUdf47_Label(String udf47_Label) {
			this.udf47_Label = udf47_Label;
		}
		public String getUdf48_Label() {
			return udf48_Label;
		}
		public void setUdf48_Label(String udf48_Label) {
			this.udf48_Label = udf48_Label;
		}
		public String getUdf49_Label() {
			return udf49_Label;
		}
		public void setUdf49_Label(String udf49_Label) {
			this.udf49_Label = udf49_Label;
		}
		public String getUdf50_Label() {
			return udf50_Label;
		}
		public void setUdf50_Label(String udf50_Label) {
			this.udf50_Label = udf50_Label;
		}
		public String getUdf51_Label() {
			return udf51_Label;
		}
		public void setUdf51_Label(String udf51_Label) {
			this.udf51_Label = udf51_Label;
		}
		public String getUdf52_Label() {
			return udf52_Label;
		}
		public void setUdf52_Label(String udf52_Label) {
			this.udf52_Label = udf52_Label;
		}
		public String getUdf53_Label() {
			return udf53_Label;
		}
		public void setUdf53_Label(String udf53_Label) {
			this.udf53_Label = udf53_Label;
		}
		public String getUdf54_Label() {
			return udf54_Label;
		}
		public void setUdf54_Label(String udf54_Label) {
			this.udf54_Label = udf54_Label;
		}
		public String getUdf55_Label() {
			return udf55_Label;
		}
		public void setUdf55_Label(String udf55_Label) {
			this.udf55_Label = udf55_Label;
		}
		public String getUdf56_Label() {
			return udf56_Label;
		}
		public void setUdf56_Label(String udf56_Label) {
			this.udf56_Label = udf56_Label;
		}
		public String getUdf57_Label() {
			return udf57_Label;
		}
		public void setUdf57_Label(String udf57_Label) {
			this.udf57_Label = udf57_Label;
		}
		public String getUdf58_Label() {
			return udf58_Label;
		}
		public void setUdf58_Label(String udf58_Label) {
			this.udf58_Label = udf58_Label;
		}
		public String getUdf59_Label() {
			return udf59_Label;
		}
		public void setUdf59_Label(String udf59_Label) {
			this.udf59_Label = udf59_Label;
		}
		public String getUdf60_Label() {
			return udf60_Label;
		}
		public void setUdf60_Label(String udf60_Label) {
			this.udf60_Label = udf60_Label;
		}
		public String getUdf61_Label() {
			return udf61_Label;
		}
		public void setUdf61_Label(String udf61_Label) {
			this.udf61_Label = udf61_Label;
		}
		public String getUdf62_Label() {
			return udf62_Label;
		}
		public void setUdf62_Label(String udf62_Label) {
			this.udf62_Label = udf62_Label;
		}
		public String getUdf63_Label() {
			return udf63_Label;
		}
		public void setUdf63_Label(String udf63_Label) {
			this.udf63_Label = udf63_Label;
		}
		public String getUdf64_Label() {
			return udf64_Label;
		}
		public void setUdf64_Label(String udf64_Label) {
			this.udf64_Label = udf64_Label;
		}
		public String getUdf65_Label() {
			return udf65_Label;
		}
		public void setUdf65_Label(String udf65_Label) {
			this.udf65_Label = udf65_Label;
		}
		public String getUdf66_Label() {
			return udf66_Label;
		}
		public void setUdf66_Label(String udf66_Label) {
			this.udf66_Label = udf66_Label;
		}
		public String getUdf67_Label() {
			return udf67_Label;
		}
		public void setUdf67_Label(String udf67_Label) {
			this.udf67_Label = udf67_Label;
		}
		public String getUdf68_Label() {
			return udf68_Label;
		}
		public void setUdf68_Label(String udf68_Label) {
			this.udf68_Label = udf68_Label;
		}
		public String getUdf69_Label() {
			return udf69_Label;
		}
		public void setUdf69_Label(String udf69_Label) {
			this.udf69_Label = udf69_Label;
		}
		public String getUdf70_Label() {
			return udf70_Label;
		}
		public void setUdf70_Label(String udf70_Label) {
			this.udf70_Label = udf70_Label;
		}
		public String getUdf71_Label() {
			return udf71_Label;
		}
		public void setUdf71_Label(String udf71_Label) {
			this.udf71_Label = udf71_Label;
		}
		public String getUdf72_Label() {
			return udf72_Label;
		}
		public void setUdf72_Label(String udf72_Label) {
			this.udf72_Label = udf72_Label;
		}
		public String getUdf73_Label() {
			return udf73_Label;
		}
		public void setUdf73_Label(String udf73_Label) {
			this.udf73_Label = udf73_Label;
		}
		public String getUdf74_Label() {
			return udf74_Label;
		}
		public void setUdf74_Label(String udf74_Label) {
			this.udf74_Label = udf74_Label;
		}
		public String getUdf75_Label() {
			return udf75_Label;
		}
		public void setUdf75_Label(String udf75_Label) {
			this.udf75_Label = udf75_Label;
		}
		public String getUdf76_Label() {
			return udf76_Label;
		}
		public void setUdf76_Label(String udf76_Label) {
			this.udf76_Label = udf76_Label;
		}
		public String getUdf77_Label() {
			return udf77_Label;
		}
		public void setUdf77_Label(String udf77_Label) {
			this.udf77_Label = udf77_Label;
		}
		public String getUdf78_Label() {
			return udf78_Label;
		}
		public void setUdf78_Label(String udf78_Label) {
			this.udf78_Label = udf78_Label;
		}
		public String getUdf79_Label() {
			return udf79_Label;
		}
		public void setUdf79_Label(String udf79_Label) {
			this.udf79_Label = udf79_Label;
		}
		public String getUdf80_Label() {
			return udf80_Label;
		}
		public void setUdf80_Label(String udf80_Label) {
			this.udf80_Label = udf80_Label;
		}
		public String getUdf81_Label() {
			return udf81_Label;
		}
		public void setUdf81_Label(String udf81_Label) {
			this.udf81_Label = udf81_Label;
		}
		public String getUdf82_Label() {
			return udf82_Label;
		}
		public void setUdf82_Label(String udf82_Label) {
			this.udf82_Label = udf82_Label;
		}
		public String getUdf83_Label() {
			return udf83_Label;
		}
		public void setUdf83_Label(String udf83_Label) {
			this.udf83_Label = udf83_Label;
		}
		public String getUdf84_Label() {
			return udf84_Label;
		}
		public void setUdf84_Label(String udf84_Label) {
			this.udf84_Label = udf84_Label;
		}
		public String getUdf85_Label() {
			return udf85_Label;
		}
		public void setUdf85_Label(String udf85_Label) {
			this.udf85_Label = udf85_Label;
		}
		public String getUdf86_Label() {
			return udf86_Label;
		}
		public void setUdf86_Label(String udf86_Label) {
			this.udf86_Label = udf86_Label;
		}
		public String getUdf87_Label() {
			return udf87_Label;
		}
		public void setUdf87_Label(String udf87_Label) {
			this.udf87_Label = udf87_Label;
		}
		public String getUdf88_Label() {
			return udf88_Label;
		}
		public void setUdf88_Label(String udf88_Label) {
			this.udf88_Label = udf88_Label;
		}
		public String getUdf89_Label() {
			return udf89_Label;
		}
		public void setUdf89_Label(String udf89_Label) {
			this.udf89_Label = udf89_Label;
		}
		public String getUdf90_Label() {
			return udf90_Label;
		}
		public void setUdf90_Label(String udf90_Label) {
			this.udf90_Label = udf90_Label;
		}
		public String getUdf91_Label() {
			return udf91_Label;
		}
		public void setUdf91_Label(String udf91_Label) {
			this.udf91_Label = udf91_Label;
		}
		public String getUdf92_Label() {
			return udf92_Label;
		}
		public void setUdf92_Label(String udf92_Label) {
			this.udf92_Label = udf92_Label;
		}
		public String getUdf93_Label() {
			return udf93_Label;
		}
		public void setUdf93_Label(String udf93_Label) {
			this.udf93_Label = udf93_Label;
		}
		public String getUdf94_Label() {
			return udf94_Label;
		}
		public void setUdf94_Label(String udf94_Label) {
			this.udf94_Label = udf94_Label;
		}
		public String getUdf95_Label() {
			return udf95_Label;
		}
		public void setUdf95_Label(String udf95_Label) {
			this.udf95_Label = udf95_Label;
		}
		public String getUdf96_Label() {
			return udf96_Label;
		}
		public void setUdf96_Label(String udf96_Label) {
			this.udf96_Label = udf96_Label;
		}
		public String getUdf97_Label() {
			return udf97_Label;
		}
		public void setUdf97_Label(String udf97_Label) {
			this.udf97_Label = udf97_Label;
		}
		public String getUdf98_Label() {
			return udf98_Label;
		}
		public void setUdf98_Label(String udf98_Label) {
			this.udf98_Label = udf98_Label;
		}
		public String getUdf99_Label() {
			return udf99_Label;
		}
		public void setUdf99_Label(String udf99_Label) {
			this.udf99_Label = udf99_Label;
		}
		public String getUdf100_Label() {
			return udf100_Label;
		}
		public void setUdf100_Label(String udf100_Label) {
			this.udf100_Label = udf100_Label;
		}
		public String getUdf1() {
			return udf1;
		}
		public void setUdf1(String udf1) {
			this.udf1 = udf1;
		}
		public String getUdf2() {
			return udf2;
		}
		public void setUdf2(String udf2) {
			this.udf2 = udf2;
		}
		public String getUdf3() {
			return udf3;
		}
		public void setUdf3(String udf3) {
			this.udf3 = udf3;
		}
		public String getUdf4() {
			return udf4;
		}
		public void setUdf4(String udf4) {
			this.udf4 = udf4;
		}
		public String getUdf5() {
			return udf5;
		}
		public void setUdf5(String udf5) {
			this.udf5 = udf5;
		}
		public String getUdf6() {
			return udf6;
		}
		public void setUdf6(String udf6) {
			this.udf6 = udf6;
		}
		public String getUdf7() {
			return udf7;
		}
		public void setUdf7(String udf7) {
			this.udf7 = udf7;
		}
		public String getUdf8() {
			return udf8;
		}
		public void setUdf8(String udf8) {
			this.udf8 = udf8;
		}
		public String getUdf9() {
			return udf9;
		}
		public void setUdf9(String udf9) {
			this.udf9 = udf9;
		}
		public String getUdf10() {
			return udf10;
		}
		public void setUdf10(String udf10) {
			this.udf10 = udf10;
		}
		public String getUdf11() {
			return udf11;
		}
		public void setUdf11(String udf11) {
			this.udf11 = udf11;
		}
		public String getUdf12() {
			return udf12;
		}
		public void setUdf12(String udf12) {
			this.udf12 = udf12;
		}
		public String getUdf13() {
			return udf13;
		}
		public void setUdf13(String udf13) {
			this.udf13 = udf13;
		}
		public String getUdf14() {
			return udf14;
		}
		public void setUdf14(String udf14) {
			this.udf14 = udf14;
		}
		public String getUdf15() {
			return udf15;
		}
		public void setUdf15(String udf15) {
			this.udf15 = udf15;
		}
		public String getUdf16() {
			return udf16;
		}
		public void setUdf16(String udf16) {
			this.udf16 = udf16;
		}
		public String getUdf17() {
			return udf17;
		}
		public void setUdf17(String udf17) {
			this.udf17 = udf17;
		}
		public String getUdf18() {
			return udf18;
		}
		public void setUdf18(String udf18) {
			this.udf18 = udf18;
		}
		public String getUdf19() {
			return udf19;
		}
		public void setUdf19(String udf19) {
			this.udf19 = udf19;
		}
		public String getUdf20() {
			return udf20;
		}
		public void setUdf20(String udf20) {
			this.udf20 = udf20;
		}
		public String getUdf21() {
			return udf21;
		}
		public void setUdf21(String udf21) {
			this.udf21 = udf21;
		}
		public String getUdf22() {
			return udf22;
		}
		public void setUdf22(String udf22) {
			this.udf22 = udf22;
		}
		public String getUdf23() {
			return udf23;
		}
		public void setUdf23(String udf23) {
			this.udf23 = udf23;
		}
		public String getUdf24() {
			return udf24;
		}
		public void setUdf24(String udf24) {
			this.udf24 = udf24;
		}
		public String getUdf25() {
			return udf25;
		}
		public void setUdf25(String udf25) {
			this.udf25 = udf25;
		}
		public String getUdf26() {
			return udf26;
		}
		public void setUdf26(String udf26) {
			this.udf26 = udf26;
		}
		public String getUdf27() {
			return udf27;
		}
		public void setUdf27(String udf27) {
			this.udf27 = udf27;
		}
		public String getUdf28() {
			return udf28;
		}
		public void setUdf28(String udf28) {
			this.udf28 = udf28;
		}
		public String getUdf29() {
			return udf29;
		}
		public void setUdf29(String udf29) {
			this.udf29 = udf29;
		}
		public String getUdf30() {
			return udf30;
		}
		public void setUdf30(String udf30) {
			this.udf30 = udf30;
		}
		public String getUdf31() {
			return udf31;
		}
		public void setUdf31(String udf31) {
			this.udf31 = udf31;
		}
		public String getUdf32() {
			return udf32;
		}
		public void setUdf32(String udf32) {
			this.udf32 = udf32;
		}
		public String getUdf33() {
			return udf33;
		}
		public void setUdf33(String udf33) {
			this.udf33 = udf33;
		}
		public String getUdf34() {
			return udf34;
		}
		public void setUdf34(String udf34) {
			this.udf34 = udf34;
		}
		public String getUdf35() {
			return udf35;
		}
		public void setUdf35(String udf35) {
			this.udf35 = udf35;
		}
		public String getUdf36() {
			return udf36;
		}
		public void setUdf36(String udf36) {
			this.udf36 = udf36;
		}
		public String getUdf37() {
			return udf37;
		}
		public void setUdf37(String udf37) {
			this.udf37 = udf37;
		}
		public String getUdf38() {
			return udf38;
		}
		public void setUdf38(String udf38) {
			this.udf38 = udf38;
		}
		public String getUdf39() {
			return udf39;
		}
		public void setUdf39(String udf39) {
			this.udf39 = udf39;
		}
		public String getUdf40() {
			return udf40;
		}
		public void setUdf40(String udf40) {
			this.udf40 = udf40;
		}
		public String getUdf41() {
			return udf41;
		}
		public void setUdf41(String udf41) {
			this.udf41 = udf41;
		}
		public String getUdf42() {
			return udf42;
		}
		public void setUdf42(String udf42) {
			this.udf42 = udf42;
		}
		public String getUdf43() {
			return udf43;
		}
		public void setUdf43(String udf43) {
			this.udf43 = udf43;
		}
		public String getUdf44() {
			return udf44;
		}
		public void setUdf44(String udf44) {
			this.udf44 = udf44;
		}
		public String getUdf45() {
			return udf45;
		}
		public void setUdf45(String udf45) {
			this.udf45 = udf45;
		}
		public String getUdf46() {
			return udf46;
		}
		public void setUdf46(String udf46) {
			this.udf46 = udf46;
		}
		public String getUdf47() {
			return udf47;
		}
		public void setUdf47(String udf47) {
			this.udf47 = udf47;
		}
		public String getUdf48() {
			return udf48;
		}
		public void setUdf48(String udf48) {
			this.udf48 = udf48;
		}
		public String getUdf49() {
			return udf49;
		}
		public void setUdf49(String udf49) {
			this.udf49 = udf49;
		}
		public String getUdf50() {
			return udf50;
		}
		public void setUdf50(String udf50) {
			this.udf50 = udf50;
		}
		public String getUdf51() {
			return udf51;
		}
		public void setUdf51(String udf51) {
			this.udf51 = udf51;
		}
		public String getUdf52() {
			return udf52;
		}
		public void setUdf52(String udf52) {
			this.udf52 = udf52;
		}
		public String getUdf53() {
			return udf53;
		}
		public void setUdf53(String udf53) {
			this.udf53 = udf53;
		}
		public String getUdf54() {
			return udf54;
		}
		public void setUdf54(String udf54) {
			this.udf54 = udf54;
		}
		public String getUdf55() {
			return udf55;
		}
		public void setUdf55(String udf55) {
			this.udf55 = udf55;
		}
		public String getUdf56() {
			return udf56;
		}
		public void setUdf56(String udf56) {
			this.udf56 = udf56;
		}
		public String getUdf57() {
			return udf57;
		}
		public void setUdf57(String udf57) {
			this.udf57 = udf57;
		}
		public String getUdf58() {
			return udf58;
		}
		public void setUdf58(String udf58) {
			this.udf58 = udf58;
		}
		public String getUdf59() {
			return udf59;
		}
		public void setUdf59(String udf59) {
			this.udf59 = udf59;
		}
		public String getUdf60() {
			return udf60;
		}
		public void setUdf60(String udf60) {
			this.udf60 = udf60;
		}
		public String getUdf61() {
			return udf61;
		}
		public void setUdf61(String udf61) {
			this.udf61 = udf61;
		}
		public String getUdf62() {
			return udf62;
		}
		public void setUdf62(String udf62) {
			this.udf62 = udf62;
		}
		public String getUdf63() {
			return udf63;
		}
		public void setUdf63(String udf63) {
			this.udf63 = udf63;
		}
		public String getUdf64() {
			return udf64;
		}
		public void setUdf64(String udf64) {
			this.udf64 = udf64;
		}
		public String getUdf65() {
			return udf65;
		}
		public void setUdf65(String udf65) {
			this.udf65 = udf65;
		}
		public String getUdf66() {
			return udf66;
		}
		public void setUdf66(String udf66) {
			this.udf66 = udf66;
		}
		public String getUdf67() {
			return udf67;
		}
		public void setUdf67(String udf67) {
			this.udf67 = udf67;
		}
		public String getUdf68() {
			return udf68;
		}
		public void setUdf68(String udf68) {
			this.udf68 = udf68;
		}
		public String getUdf69() {
			return udf69;
		}
		public void setUdf69(String udf69) {
			this.udf69 = udf69;
		}
		public String getUdf70() {
			return udf70;
		}
		public void setUdf70(String udf70) {
			this.udf70 = udf70;
		}
		public String getUdf71() {
			return udf71;
		}
		public void setUdf71(String udf71) {
			this.udf71 = udf71;
		}
		public String getUdf72() {
			return udf72;
		}
		public void setUdf72(String udf72) {
			this.udf72 = udf72;
		}
		public String getUdf73() {
			return udf73;
		}
		public void setUdf73(String udf73) {
			this.udf73 = udf73;
		}
		public String getUdf74() {
			return udf74;
		}
		public void setUdf74(String udf74) {
			this.udf74 = udf74;
		}
		public String getUdf75() {
			return udf75;
		}
		public void setUdf75(String udf75) {
			this.udf75 = udf75;
		}
		public String getUdf76() {
			return udf76;
		}
		public void setUdf76(String udf76) {
			this.udf76 = udf76;
		}
		public String getUdf77() {
			return udf77;
		}
		public void setUdf77(String udf77) {
			this.udf77 = udf77;
		}
		public String getUdf78() {
			return udf78;
		}
		public void setUdf78(String udf78) {
			this.udf78 = udf78;
		}
		public String getUdf79() {
			return udf79;
		}
		public void setUdf79(String udf79) {
			this.udf79 = udf79;
		}
		public String getUdf80() {
			return udf80;
		}
		public void setUdf80(String udf80) {
			this.udf80 = udf80;
		}
		public String getUdf81() {
			return udf81;
		}
		public void setUdf81(String udf81) {
			this.udf81 = udf81;
		}
		public String getUdf82() {
			return udf82;
		}
		public void setUdf82(String udf82) {
			this.udf82 = udf82;
		}
		public String getUdf83() {
			return udf83;
		}
		public void setUdf83(String udf83) {
			this.udf83 = udf83;
		}
		public String getUdf84() {
			return udf84;
		}
		public void setUdf84(String udf84) {
			this.udf84 = udf84;
		}
		public String getUdf85() {
			return udf85;
		}
		public void setUdf85(String udf85) {
			this.udf85 = udf85;
		}
		public String getUdf86() {
			return udf86;
		}
		public void setUdf86(String udf86) {
			this.udf86 = udf86;
		}
		public String getUdf87() {
			return udf87;
		}
		public void setUdf87(String udf87) {
			this.udf87 = udf87;
		}
		public String getUdf88() {
			return udf88;
		}
		public void setUdf88(String udf88) {
			this.udf88 = udf88;
		}
		public String getUdf89() {
			return udf89;
		}
		public void setUdf89(String udf89) {
			this.udf89 = udf89;
		}
		public String getUdf90() {
			return udf90;
		}
		public void setUdf90(String udf90) {
			this.udf90 = udf90;
		}
		public String getUdf91() {
			return udf91;
		}
		public void setUdf91(String udf91) {
			this.udf91 = udf91;
		}
		public String getUdf92() {
			return udf92;
		}
		public void setUdf92(String udf92) {
			this.udf92 = udf92;
		}
		public String getUdf93() {
			return udf93;
		}
		public void setUdf93(String udf93) {
			this.udf93 = udf93;
		}
		public String getUdf94() {
			return udf94;
		}
		public void setUdf94(String udf94) {
			this.udf94 = udf94;
		}
		public String getUdf95() {
			return udf95;
		}
		public void setUdf95(String udf95) {
			this.udf95 = udf95;
		}
		public String getUdf96() {
			return udf96;
		}
		public void setUdf96(String udf96) {
			this.udf96 = udf96;
		}
		public String getUdf97() {
			return udf97;
		}
		public void setUdf97(String udf97) {
			this.udf97 = udf97;
		}
		public String getUdf98() {
			return udf98;
		}
		public void setUdf98(String udf98) {
			this.udf98 = udf98;
		}
		public String getUdf99() {
			return udf99;
		}
		public void setUdf99(String udf99) {
			this.udf99 = udf99;
		}
		public String getUdf100() {
			return udf100;
		}
		public void setUdf100(String udf100) {
			this.udf100 = udf100;
		}
		public String getUdf1_Flag() {
			return udf1_Flag;
		}
		public void setUdf1_Flag(String udf1_Flag) {
			this.udf1_Flag = udf1_Flag;
		}
		public String getUdf2_Flag() {
			return udf2_Flag;
		}
		public void setUdf2_Flag(String udf2_Flag) {
			this.udf2_Flag = udf2_Flag;
		}
		public String getUdf3_Flag() {
			return udf3_Flag;
		}
		public void setUdf3_Flag(String udf3_Flag) {
			this.udf3_Flag = udf3_Flag;
		}
		public String getUdf4_Flag() {
			return udf4_Flag;
		}
		public void setUdf4_Flag(String udf4_Flag) {
			this.udf4_Flag = udf4_Flag;
		}
		public String getUdf5_Flag() {
			return udf5_Flag;
		}
		public void setUdf5_Flag(String udf5_Flag) {
			this.udf5_Flag = udf5_Flag;
		}
		public String getUdf6_Flag() {
			return udf6_Flag;
		}
		public void setUdf6_Flag(String udf6_Flag) {
			this.udf6_Flag = udf6_Flag;
		}
		public String getUdf7_Flag() {
			return udf7_Flag;
		}
		public void setUdf7_Flag(String udf7_Flag) {
			this.udf7_Flag = udf7_Flag;
		}
		public String getUdf8_Flag() {
			return udf8_Flag;
		}
		public void setUdf8_Flag(String udf8_Flag) {
			this.udf8_Flag = udf8_Flag;
		}
		public String getUdf9_Flag() {
			return udf9_Flag;
		}
		public void setUdf9_Flag(String udf9_Flag) {
			this.udf9_Flag = udf9_Flag;
		}
		public String getUdf10_Flag() {
			return udf10_Flag;
		}
		public void setUdf10_Flag(String udf10_Flag) {
			this.udf10_Flag = udf10_Flag;
		}
		public String getUdf11_Flag() {
			return udf11_Flag;
		}
		public void setUdf11_Flag(String udf11_Flag) {
			this.udf11_Flag = udf11_Flag;
		}
		public String getUdf12_Flag() {
			return udf12_Flag;
		}
		public void setUdf12_Flag(String udf12_Flag) {
			this.udf12_Flag = udf12_Flag;
		}
		public String getUdf13_Flag() {
			return udf13_Flag;
		}
		public void setUdf13_Flag(String udf13_Flag) {
			this.udf13_Flag = udf13_Flag;
		}
		public String getUdf14_Flag() {
			return udf14_Flag;
		}
		public void setUdf14_Flag(String udf14_Flag) {
			this.udf14_Flag = udf14_Flag;
		}
		public String getUdf15_Flag() {
			return udf15_Flag;
		}
		public void setUdf15_Flag(String udf15_Flag) {
			this.udf15_Flag = udf15_Flag;
		}
		public String getUdf16_Flag() {
			return udf16_Flag;
		}
		public void setUdf16_Flag(String udf16_Flag) {
			this.udf16_Flag = udf16_Flag;
		}
		public String getUdf17_Flag() {
			return udf17_Flag;
		}
		public void setUdf17_Flag(String udf17_Flag) {
			this.udf17_Flag = udf17_Flag;
		}
		public String getUdf18_Flag() {
			return udf18_Flag;
		}
		public void setUdf18_Flag(String udf18_Flag) {
			this.udf18_Flag = udf18_Flag;
		}
		public String getUdf19_Flag() {
			return udf19_Flag;
		}
		public void setUdf19_Flag(String udf19_Flag) {
			this.udf19_Flag = udf19_Flag;
		}
		public String getUdf20_Flag() {
			return udf20_Flag;
		}
		public void setUdf20_Flag(String udf20_Flag) {
			this.udf20_Flag = udf20_Flag;
		}
		public String getUdf21_Flag() {
			return udf21_Flag;
		}
		public void setUdf21_Flag(String udf21_Flag) {
			this.udf21_Flag = udf21_Flag;
		}
		public String getUdf22_Flag() {
			return udf22_Flag;
		}
		public void setUdf22_Flag(String udf22_Flag) {
			this.udf22_Flag = udf22_Flag;
		}
		public String getUdf23_Flag() {
			return udf23_Flag;
		}
		public void setUdf23_Flag(String udf23_Flag) {
			this.udf23_Flag = udf23_Flag;
		}
		public String getUdf24_Flag() {
			return udf24_Flag;
		}
		public void setUdf24_Flag(String udf24_Flag) {
			this.udf24_Flag = udf24_Flag;
		}
		public String getUdf25_Flag() {
			return udf25_Flag;
		}
		public void setUdf25_Flag(String udf25_Flag) {
			this.udf25_Flag = udf25_Flag;
		}
		public String getUdf26_Flag() {
			return udf26_Flag;
		}
		public void setUdf26_Flag(String udf26_Flag) {
			this.udf26_Flag = udf26_Flag;
		}
		public String getUdf27_Flag() {
			return udf27_Flag;
		}
		public void setUdf27_Flag(String udf27_Flag) {
			this.udf27_Flag = udf27_Flag;
		}
		public String getUdf28_Flag() {
			return udf28_Flag;
		}
		public void setUdf28_Flag(String udf28_Flag) {
			this.udf28_Flag = udf28_Flag;
		}
		public String getUdf29_Flag() {
			return udf29_Flag;
		}
		public void setUdf29_Flag(String udf29_Flag) {
			this.udf29_Flag = udf29_Flag;
		}
		public String getUdf30_Flag() {
			return udf30_Flag;
		}
		public void setUdf30_Flag(String udf30_Flag) {
			this.udf30_Flag = udf30_Flag;
		}
		public String getUdf31_Flag() {
			return udf31_Flag;
		}
		public void setUdf31_Flag(String udf31_Flag) {
			this.udf31_Flag = udf31_Flag;
		}
		public String getUdf32_Flag() {
			return udf32_Flag;
		}
		public void setUdf32_Flag(String udf32_Flag) {
			this.udf32_Flag = udf32_Flag;
		}
		public String getUdf33_Flag() {
			return udf33_Flag;
		}
		public void setUdf33_Flag(String udf33_Flag) {
			this.udf33_Flag = udf33_Flag;
		}
		public String getUdf34_Flag() {
			return udf34_Flag;
		}
		public void setUdf34_Flag(String udf34_Flag) {
			this.udf34_Flag = udf34_Flag;
		}
		public String getUdf35_Flag() {
			return udf35_Flag;
		}
		public void setUdf35_Flag(String udf35_Flag) {
			this.udf35_Flag = udf35_Flag;
		}
		public String getUdf36_Flag() {
			return udf36_Flag;
		}
		public void setUdf36_Flag(String udf36_Flag) {
			this.udf36_Flag = udf36_Flag;
		}
		public String getUdf37_Flag() {
			return udf37_Flag;
		}
		public void setUdf37_Flag(String udf37_Flag) {
			this.udf37_Flag = udf37_Flag;
		}
		public String getUdf38_Flag() {
			return udf38_Flag;
		}
		public void setUdf38_Flag(String udf38_Flag) {
			this.udf38_Flag = udf38_Flag;
		}
		public String getUdf39_Flag() {
			return udf39_Flag;
		}
		public void setUdf39_Flag(String udf39_Flag) {
			this.udf39_Flag = udf39_Flag;
		}
		public String getUdf40_Flag() {
			return udf40_Flag;
		}
		public void setUdf40_Flag(String udf40_Flag) {
			this.udf40_Flag = udf40_Flag;
		}
		public String getUdf41_Flag() {
			return udf41_Flag;
		}
		public void setUdf41_Flag(String udf41_Flag) {
			this.udf41_Flag = udf41_Flag;
		}
		public String getUdf42_Flag() {
			return udf42_Flag;
		}
		public void setUdf42_Flag(String udf42_Flag) {
			this.udf42_Flag = udf42_Flag;
		}
		public String getUdf43_Flag() {
			return udf43_Flag;
		}
		public void setUdf43_Flag(String udf43_Flag) {
			this.udf43_Flag = udf43_Flag;
		}
		public String getUdf44_Flag() {
			return udf44_Flag;
		}
		public void setUdf44_Flag(String udf44_Flag) {
			this.udf44_Flag = udf44_Flag;
		}
		public String getUdf45_Flag() {
			return udf45_Flag;
		}
		public void setUdf45_Flag(String udf45_Flag) {
			this.udf45_Flag = udf45_Flag;
		}
		public String getUdf46_Flag() {
			return udf46_Flag;
		}
		public void setUdf46_Flag(String udf46_Flag) {
			this.udf46_Flag = udf46_Flag;
		}
		public String getUdf47_Flag() {
			return udf47_Flag;
		}
		public void setUdf47_Flag(String udf47_Flag) {
			this.udf47_Flag = udf47_Flag;
		}
		public String getUdf48_Flag() {
			return udf48_Flag;
		}
		public void setUdf48_Flag(String udf48_Flag) {
			this.udf48_Flag = udf48_Flag;
		}
		public String getUdf49_Flag() {
			return udf49_Flag;
		}
		public void setUdf49_Flag(String udf49_Flag) {
			this.udf49_Flag = udf49_Flag;
		}
		public String getUdf50_Flag() {
			return udf50_Flag;
		}
		public void setUdf50_Flag(String udf50_Flag) {
			this.udf50_Flag = udf50_Flag;
		}
		public String getUdf51_Flag() {
			return udf51_Flag;
		}
		public void setUdf51_Flag(String udf51_Flag) {
			this.udf51_Flag = udf51_Flag;
		}
		public String getUdf52_Flag() {
			return udf52_Flag;
		}
		public void setUdf52_Flag(String udf52_Flag) {
			this.udf52_Flag = udf52_Flag;
		}
		public String getUdf53_Flag() {
			return udf53_Flag;
		}
		public void setUdf53_Flag(String udf53_Flag) {
			this.udf53_Flag = udf53_Flag;
		}
		public String getUdf54_Flag() {
			return udf54_Flag;
		}
		public void setUdf54_Flag(String udf54_Flag) {
			this.udf54_Flag = udf54_Flag;
		}
		public String getUdf55_Flag() {
			return udf55_Flag;
		}
		public void setUdf55_Flag(String udf55_Flag) {
			this.udf55_Flag = udf55_Flag;
		}
		public String getUdf56_Flag() {
			return udf56_Flag;
		}
		public void setUdf56_Flag(String udf56_Flag) {
			this.udf56_Flag = udf56_Flag;
		}
		public String getUdf57_Flag() {
			return udf57_Flag;
		}
		public void setUdf57_Flag(String udf57_Flag) {
			this.udf57_Flag = udf57_Flag;
		}
		public String getUdf58_Flag() {
			return udf58_Flag;
		}
		public void setUdf58_Flag(String udf58_Flag) {
			this.udf58_Flag = udf58_Flag;
		}
		public String getUdf59_Flag() {
			return udf59_Flag;
		}
		public void setUdf59_Flag(String udf59_Flag) {
			this.udf59_Flag = udf59_Flag;
		}
		public String getUdf60_Flag() {
			return udf60_Flag;
		}
		public void setUdf60_Flag(String udf60_Flag) {
			this.udf60_Flag = udf60_Flag;
		}
		public String getUdf61_Flag() {
			return udf61_Flag;
		}
		public void setUdf61_Flag(String udf61_Flag) {
			this.udf61_Flag = udf61_Flag;
		}
		public String getUdf62_Flag() {
			return udf62_Flag;
		}
		public void setUdf62_Flag(String udf62_Flag) {
			this.udf62_Flag = udf62_Flag;
		}
		public String getUdf63_Flag() {
			return udf63_Flag;
		}
		public void setUdf63_Flag(String udf63_Flag) {
			this.udf63_Flag = udf63_Flag;
		}
		public String getUdf64_Flag() {
			return udf64_Flag;
		}
		public void setUdf64_Flag(String udf64_Flag) {
			this.udf64_Flag = udf64_Flag;
		}
		public String getUdf65_Flag() {
			return udf65_Flag;
		}
		public void setUdf65_Flag(String udf65_Flag) {
			this.udf65_Flag = udf65_Flag;
		}
		public String getUdf66_Flag() {
			return udf66_Flag;
		}
		public void setUdf66_Flag(String udf66_Flag) {
			this.udf66_Flag = udf66_Flag;
		}
		public String getUdf67_Flag() {
			return udf67_Flag;
		}
		public void setUdf67_Flag(String udf67_Flag) {
			this.udf67_Flag = udf67_Flag;
		}
		public String getUdf68_Flag() {
			return udf68_Flag;
		}
		public void setUdf68_Flag(String udf68_Flag) {
			this.udf68_Flag = udf68_Flag;
		}
		public String getUdf69_Flag() {
			return udf69_Flag;
		}
		public void setUdf69_Flag(String udf69_Flag) {
			this.udf69_Flag = udf69_Flag;
		}
		public String getUdf70_Flag() {
			return udf70_Flag;
		}
		public void setUdf70_Flag(String udf70_Flag) {
			this.udf70_Flag = udf70_Flag;
		}
		public String getUdf71_Flag() {
			return udf71_Flag;
		}
		public void setUdf71_Flag(String udf71_Flag) {
			this.udf71_Flag = udf71_Flag;
		}
		public String getUdf72_Flag() {
			return udf72_Flag;
		}
		public void setUdf72_Flag(String udf72_Flag) {
			this.udf72_Flag = udf72_Flag;
		}
		public String getUdf73_Flag() {
			return udf73_Flag;
		}
		public void setUdf73_Flag(String udf73_Flag) {
			this.udf73_Flag = udf73_Flag;
		}
		public String getUdf74_Flag() {
			return udf74_Flag;
		}
		public void setUdf74_Flag(String udf74_Flag) {
			this.udf74_Flag = udf74_Flag;
		}
		public String getUdf75_Flag() {
			return udf75_Flag;
		}
		public void setUdf75_Flag(String udf75_Flag) {
			this.udf75_Flag = udf75_Flag;
		}
		public String getUdf76_Flag() {
			return udf76_Flag;
		}
		public void setUdf76_Flag(String udf76_Flag) {
			this.udf76_Flag = udf76_Flag;
		}
		public String getUdf77_Flag() {
			return udf77_Flag;
		}
		public void setUdf77_Flag(String udf77_Flag) {
			this.udf77_Flag = udf77_Flag;
		}
		public String getUdf78_Flag() {
			return udf78_Flag;
		}
		public void setUdf78_Flag(String udf78_Flag) {
			this.udf78_Flag = udf78_Flag;
		}
		public String getUdf79_Flag() {
			return udf79_Flag;
		}
		public void setUdf79_Flag(String udf79_Flag) {
			this.udf79_Flag = udf79_Flag;
		}
		public String getUdf80_Flag() {
			return udf80_Flag;
		}
		public void setUdf80_Flag(String udf80_Flag) {
			this.udf80_Flag = udf80_Flag;
		}
		public String getUdf81_Flag() {
			return udf81_Flag;
		}
		public void setUdf81_Flag(String udf81_Flag) {
			this.udf81_Flag = udf81_Flag;
		}
		public String getUdf82_Flag() {
			return udf82_Flag;
		}
		public void setUdf82_Flag(String udf82_Flag) {
			this.udf82_Flag = udf82_Flag;
		}
		public String getUdf83_Flag() {
			return udf83_Flag;
		}
		public void setUdf83_Flag(String udf83_Flag) {
			this.udf83_Flag = udf83_Flag;
		}
		public String getUdf84_Flag() {
			return udf84_Flag;
		}
		public void setUdf84_Flag(String udf84_Flag) {
			this.udf84_Flag = udf84_Flag;
		}
		public String getUdf85_Flag() {
			return udf85_Flag;
		}
		public void setUdf85_Flag(String udf85_Flag) {
			this.udf85_Flag = udf85_Flag;
		}
		public String getUdf86_Flag() {
			return udf86_Flag;
		}
		public void setUdf86_Flag(String udf86_Flag) {
			this.udf86_Flag = udf86_Flag;
		}
		public String getUdf87_Flag() {
			return udf87_Flag;
		}
		public void setUdf87_Flag(String udf87_Flag) {
			this.udf87_Flag = udf87_Flag;
		}
		public String getUdf88_Flag() {
			return udf88_Flag;
		}
		public void setUdf88_Flag(String udf88_Flag) {
			this.udf88_Flag = udf88_Flag;
		}
		public String getUdf89_Flag() {
			return udf89_Flag;
		}
		public void setUdf89_Flag(String udf89_Flag) {
			this.udf89_Flag = udf89_Flag;
		}
		public String getUdf90_Flag() {
			return udf90_Flag;
		}
		public void setUdf90_Flag(String udf90_Flag) {
			this.udf90_Flag = udf90_Flag;
		}
		public String getUdf91_Flag() {
			return udf91_Flag;
		}
		public void setUdf91_Flag(String udf91_Flag) {
			this.udf91_Flag = udf91_Flag;
		}
		public String getUdf92_Flag() {
			return udf92_Flag;
		}
		public void setUdf92_Flag(String udf92_Flag) {
			this.udf92_Flag = udf92_Flag;
		}
		public String getUdf93_Flag() {
			return udf93_Flag;
		}
		public void setUdf93_Flag(String udf93_Flag) {
			this.udf93_Flag = udf93_Flag;
		}
		public String getUdf94_Flag() {
			return udf94_Flag;
		}
		public void setUdf94_Flag(String udf94_Flag) {
			this.udf94_Flag = udf94_Flag;
		}
		public String getUdf95_Flag() {
			return udf95_Flag;
		}
		public void setUdf95_Flag(String udf95_Flag) {
			this.udf95_Flag = udf95_Flag;
		}
		public String getUdf96_Flag() {
			return udf96_Flag;
		}
		public void setUdf96_Flag(String udf96_Flag) {
			this.udf96_Flag = udf96_Flag;
		}
		public String getUdf97_Flag() {
			return udf97_Flag;
		}
		public void setUdf97_Flag(String udf97_Flag) {
			this.udf97_Flag = udf97_Flag;
		}
		public String getUdf98_Flag() {
			return udf98_Flag;
		}
		public void setUdf98_Flag(String udf98_Flag) {
			this.udf98_Flag = udf98_Flag;
		}
		public String getUdf99_Flag() {
			return udf99_Flag;
		}
		public void setUdf99_Flag(String udf99_Flag) {
			this.udf99_Flag = udf99_Flag;
		}
		public String getUdf100_Flag() {
			return udf100_Flag;
		}
		public void setUdf100_Flag(String udf100_Flag) {
			this.udf100_Flag = udf100_Flag;
		}
		// End Santosh UBS-LIMIT-UPLOAD
		
		private String sendToCore;

		public String getSendToCore() {
			return sendToCore;
		}

		public void setSendToCore(String sendToCore) {
			this.sendToCore = sendToCore;
		}
		private String lineAction;

		public String getLineAction() {
			return lineAction;
		}

		public void setLineAction(String lineAction) {
			this.lineAction = lineAction;
		}
		
		
		
		private String sendToFile;

		public String getSendToFile() {
			return sendToFile;
		}

		public void setSendToFile(String sendToFile) {
			this.sendToFile = sendToFile;
		}
		
		private String commRealEstateTypeFlag;

		public String getCommRealEstateTypeFlag() {
			return commRealEstateTypeFlag;
		}

		public void setCommRealEstateTypeFlag(String commRealEstateTypeFlag) {
			this.commRealEstateTypeFlag = commRealEstateTypeFlag;
		}

		public String getTenure() {
			return tenure;
		}

		public void setTenure(String tenure) {
			this.tenure = tenure;
		}

		public String getSellDownPeriod() {
			return sellDownPeriod;
		}

		public void setSellDownPeriod(String sellDownPeriod) {
			this.sellDownPeriod = sellDownPeriod;
		}

		public String getLiabilityId() {
			return liabilityId;
		}

		public void setLiabilityId(String liabilityId) {
			this.liabilityId = liabilityId;
		}

		public String getLimitRemarks() {
			return limitRemarks;
		}

		public void setLimitRemarks(String limitRemarks) {
			this.limitRemarks = limitRemarks;
		}
		

		public String getIsDayLightLimitAvailable() {
			return isDayLightLimitAvailable;
		}

		public void setIsDayLightLimitAvailable(String isDayLightLimitAvailable) {
			this.isDayLightLimitAvailable = isDayLightLimitAvailable;
		}

		public String getDayLightLimitApproved() {
			return dayLightLimitApproved;
		}

		public void setDayLightLimitApproved(String dayLightLimitApproved) {
			this.dayLightLimitApproved = dayLightLimitApproved;
		}


		public String getBankingArrangement() {
			return bankingArrangement;
		}

		public void setBankingArrangement(String bankingArrangement) {
			this.bankingArrangement = bankingArrangement;
		}


		 	public String getUdf101_Label() {
			return udf101_Label;
		}

		public void setUdf101_Label(String udf101_Label) {
			this.udf101_Label = udf101_Label;
		}

		public String getUdf102_Label() {
			return udf102_Label;
		}

		public void setUdf102_Label(String udf102_Label) {
			this.udf102_Label = udf102_Label;
		}

		public String getUdf103_Label() {
			return udf103_Label;
		}

		public void setUdf103_Label(String udf103_Label) {
			this.udf103_Label = udf103_Label;
		}

		public String getUdf104_Label() {
			return udf104_Label;
		}

		public void setUdf104_Label(String udf104_Label) {
			this.udf104_Label = udf104_Label;
		}

		public String getUdf105_Label() {
			return udf105_Label;
		}

		public void setUdf105_Label(String udf105_Label) {
			this.udf105_Label = udf105_Label;
		}

		public String getUdf101() {
			return udf101;
		}

		public void setUdf101(String udf101) {
			this.udf101 = udf101;
		}

		public String getUdf102() {
			return udf102;
		}

		public void setUdf102(String udf102) {
			this.udf102 = udf102;
		}

		public String getUdf103() {
			return udf103;
		}

		public void setUdf103(String udf103) {
			this.udf103 = udf103;
		}

		public String getUdf104() {
			return udf104;
		}

		public void setUdf104(String udf104) {
			this.udf104 = udf104;
		}

		public String getUdf105() {
			return udf105;
		}

		public void setUdf105(String udf105) {
			this.udf105 = udf105;
		}

		public String getUdf101_Flag() {
			return udf101_Flag;
		}

		public void setUdf101_Flag(String udf101_Flag) {
			this.udf101_Flag = udf101_Flag;
		}

		public String getUdf102_Flag() {
			return udf102_Flag;
		}

		public void setUdf102_Flag(String udf102_Flag) {
			this.udf102_Flag = udf102_Flag;
		}

		public String getUdf103_Flag() {
			return udf103_Flag;
		}

		public void setUdf103_Flag(String udf103_Flag) {
			this.udf103_Flag = udf103_Flag;
		}

		public String getUdf104_Flag() {
			return udf104_Flag;
		}

		public void setUdf104_Flag(String udf104_Flag) {
			this.udf104_Flag = udf104_Flag;
		}

		public String getUdf105_Flag() {
			return udf105_Flag;
		}

		public void setUdf105_Flag(String udf105_Flag) {
			this.udf105_Flag = udf105_Flag;
		}

				public String getUdf106_Label() {
			return udf106_Label;
		}

		public void setUdf106_Label(String udf106_Label) {
			this.udf106_Label = udf106_Label;
		}

		public String getUdf107_Label() {
			return udf107_Label;
		}

		public void setUdf107_Label(String udf107_Label) {
			this.udf107_Label = udf107_Label;
		}

		public String getUdf108_Label() {
			return udf108_Label;
		}

		public void setUdf108_Label(String udf108_Label) {
			this.udf108_Label = udf108_Label;
		}

		public String getUdf109_Label() {
			return udf109_Label;
		}

		public void setUdf109_Label(String udf109_Label) {
			this.udf109_Label = udf109_Label;
		}

		public String getUdf110_Label() {
			return udf110_Label;
		}

		public void setUdf110_Label(String udf110_Label) {
			this.udf110_Label = udf110_Label;
		}

		public String getUdf111_Label() {
			return udf111_Label;
		}

		public void setUdf111_Label(String udf111_Label) {
			this.udf111_Label = udf111_Label;
		}

		public String getUdf112_Label() {
			return udf112_Label;
		}

		public void setUdf112_Label(String udf112_Label) {
			this.udf112_Label = udf112_Label;
		}

		public String getUdf113_Label() {
			return udf113_Label;
		}

		public void setUdf113_Label(String udf113_Label) {
			this.udf113_Label = udf113_Label;
		}

		public String getUdf114_Label() {
			return udf114_Label;
		}

		public void setUdf114_Label(String udf114_Label) {
			this.udf114_Label = udf114_Label;
		}

		public String getUdf115_Label() {
			return udf115_Label;
		}

		public void setUdf115_Label(String udf115_Label) {
			this.udf115_Label = udf115_Label;
		}

		

   
		public String getUdf106() {
			return udf106;
		}

		public void setUdf106(String udf106) {
			this.udf106 = udf106;
		}

		public String getUdf107() {
			return udf107;
		}

		public void setUdf107(String udf107) {
			this.udf107 = udf107;
		}

		public String getUdf108() {
			return udf108;
		}

		public void setUdf108(String udf108) {
			this.udf108 = udf108;
		}

		public String getUdf109() {
			return udf109;
		}

		public void setUdf109(String udf109) {
			this.udf109 = udf109;
		}

		public String getUdf110() {
			return udf110;
		}

		public void setUdf110(String udf110) {
			this.udf110 = udf110;
		}

		public String getUdf111() {
			return udf111;
		}

		public void setUdf111(String udf111) {
			this.udf111 = udf111;
		}

		public String getUdf112() {
			return udf112;
		}

		public void setUdf112(String udf112) {
			this.udf112 = udf112;
		}

		public String getUdf113() {
			return udf113;
		}

		public void setUdf113(String udf113) {
			this.udf113 = udf113;
		}

		public String getUdf114() {
			return udf114;
		}

		public void setUdf114(String udf114) {
			this.udf114 = udf114;
		}

		public String getUdf115() {
			return udf115;
		}

		public void setUdf115(String udf115) {
			this.udf115 = udf115;
		}

		

	  
		public String getUdf106_Flag() {
			return udf106_Flag;
		}

		public void setUdf106_Flag(String udf106_Flag) {
			this.udf106_Flag = udf106_Flag;
		}

		public String getUdf107_Flag() {
			return udf107_Flag;
		}

		public void setUdf107_Flag(String udf107_Flag) {
			this.udf107_Flag = udf107_Flag;
		}

		public String getUdf108_Flag() {
			return udf108_Flag;
		}

		public void setUdf108_Flag(String udf108_Flag) {
			this.udf108_Flag = udf108_Flag;
		}

		public String getUdf109_Flag() {
			return udf109_Flag;
		}

		public void setUdf109_Flag(String udf109_Flag) {
			this.udf109_Flag = udf109_Flag;
		}

		public String getUdf110_Flag() {
			return udf110_Flag;
		}

		public void setUdf110_Flag(String udf110_Flag) {
			this.udf110_Flag = udf110_Flag;
		}

		public String getUdf111_Flag() {
			return udf111_Flag;
		}

		public void setUdf111_Flag(String udf111_Flag) {
			this.udf111_Flag = udf111_Flag;
		}

		public String getUdf112_Flag() {
			return udf112_Flag;
		}

		public void setUdf112_Flag(String udf112_Flag) {
			this.udf112_Flag = udf112_Flag;
		}

		public String getUdf113_Flag() {
			return udf113_Flag;
		}

		public void setUdf113_Flag(String udf113_Flag) {
			this.udf113_Flag = udf113_Flag;
		}

		public String getUdf114_Flag() {
			return udf114_Flag;
		}

		public void setUdf114_Flag(String udf114_Flag) {
			this.udf114_Flag = udf114_Flag;
		}

		public String getUdf115_Flag() {
			return udf115_Flag;
		}

		public void setUdf115_Flag(String udf115_Flag) {
			this.udf115_Flag = udf115_Flag;
		}


		//Addedfor - UBS Limit_2
		private String allUdfList_2;
		private String udfList_2;
		private String udfAllowed_2;
			
		public String getAllUdfList_2() {
			return allUdfList_2;
		}

		public void setAllUdfList_2(String allUdfList_2) {
			this.allUdfList_2 = allUdfList_2;
		}

		public String getUdfList_2() {
			return udfList_2;
		}

		public void setUdfList_2(String udfList_2) {
			this.udfList_2 = udfList_2;
		}

		public String getUdfAllowed_2() {
			return udfAllowed_2;
		}

		public void setUdfAllowed_2(String udfAllowed_2) {
			this.udfAllowed_2 = udfAllowed_2;
		}

		public String getUdf116_Label() {
			return udf116_Label;
		}

		public void setUdf116_Label(String udf116_Label) {
			this.udf116_Label = udf116_Label;
		}

		public String getUdf117_Label() {
			return udf117_Label;
		}

		public void setUdf117_Label(String udf117_Label) {
			this.udf117_Label = udf117_Label;
		}

		public String getUdf118_Label() {
			return udf118_Label;
		}

		public void setUdf118_Label(String udf118_Label) {
			this.udf118_Label = udf118_Label;
		}

		public String getUdf119_Label() {
			return udf119_Label;
		}

		public void setUdf119_Label(String udf119_Label) {
			this.udf119_Label = udf119_Label;
		}

		public String getUdf120_Label() {
			return udf120_Label;
		}

		public void setUdf120_Label(String udf120_Label) {
			this.udf120_Label = udf120_Label;
		}

		public String getUdf121_Label() {
			return udf121_Label;
		}

		public void setUdf121_Label(String udf121_Label) {
			this.udf121_Label = udf121_Label;
		}

		public String getUdf122_Label() {
			return udf122_Label;
		}

		public void setUdf122_Label(String udf122_Label) {
			this.udf122_Label = udf122_Label;
		}

		public String getUdf123_Label() {
			return udf123_Label;
		}

		public void setUdf123_Label(String udf123_Label) {
			this.udf123_Label = udf123_Label;
		}

		public String getUdf124_Label() {
			return udf124_Label;
		}

		public void setUdf124_Label(String udf124_Label) {
			this.udf124_Label = udf124_Label;
		}

		public String getUdf125_Label() {
			return udf125_Label;
		}

		public void setUdf125_Label(String udf125_Label) {
			this.udf125_Label = udf125_Label;
		}

		public String getUdf126_Label() {
			return udf126_Label;
		}

		public void setUdf126_Label(String udf126_Label) {
			this.udf126_Label = udf126_Label;
		}

		public String getUdf127_Label() {
			return udf127_Label;
		}

		public void setUdf127_Label(String udf127_Label) {
			this.udf127_Label = udf127_Label;
		}

		public String getUdf128_Label() {
			return udf128_Label;
		}

		public void setUdf128_Label(String udf128_Label) {
			this.udf128_Label = udf128_Label;
		}

		public String getUdf129_Label() {
			return udf129_Label;
		}

		public void setUdf129_Label(String udf129_Label) {
			this.udf129_Label = udf129_Label;
		}

		public String getUdf130_Label() {
			return udf130_Label;
		}

		public void setUdf130_Label(String udf130_Label) {
			this.udf130_Label = udf130_Label;
		}

		public String getUdf131_Label() {
			return udf131_Label;
		}

		public void setUdf131_Label(String udf131_Label) {
			this.udf131_Label = udf131_Label;
		}

		public String getUdf132_Label() {
			return udf132_Label;
		}

		public void setUdf132_Label(String udf132_Label) {
			this.udf132_Label = udf132_Label;
		}

		public String getUdf133_Label() {
			return udf133_Label;
		}

		public void setUdf133_Label(String udf133_Label) {
			this.udf133_Label = udf133_Label;
		}

		public String getUdf134_Label() {
			return udf134_Label;
		}

		public void setUdf134_Label(String udf134_Label) {
			this.udf134_Label = udf134_Label;
		}

		public String getUdf135_Label() {
			return udf135_Label;
		}

		public void setUdf135_Label(String udf135_Label) {
			this.udf135_Label = udf135_Label;
		}

		public String getUdf136_Label() {
			return udf136_Label;
		}

		public void setUdf136_Label(String udf136_Label) {
			this.udf136_Label = udf136_Label;
		}

		public String getUdf137_Label() {
			return udf137_Label;
		}

		public void setUdf137_Label(String udf137_Label) {
			this.udf137_Label = udf137_Label;
		}

		public String getUdf138_Label() {
			return udf138_Label;
		}

		public void setUdf138_Label(String udf138_Label) {
			this.udf138_Label = udf138_Label;
		}

		public String getUdf139_Label() {
			return udf139_Label;
		}

		public void setUdf139_Label(String udf139_Label) {
			this.udf139_Label = udf139_Label;
		}

		public String getUdf140_Label() {
			return udf140_Label;
		}

		public void setUdf140_Label(String udf140_Label) {
			this.udf140_Label = udf140_Label;
		}

		public String getUdf141_Label() {
			return udf141_Label;
		}

		public void setUdf141_Label(String udf141_Label) {
			this.udf141_Label = udf141_Label;
		}

		public String getUdf142_Label() {
			return udf142_Label;
		}

		public void setUdf142_Label(String udf142_Label) {
			this.udf142_Label = udf142_Label;
		}

		public String getUdf143_Label() {
			return udf143_Label;
		}

		public void setUdf143_Label(String udf143_Label) {
			this.udf143_Label = udf143_Label;
		}

		public String getUdf144_Label() {
			return udf144_Label;
		}

		public void setUdf144_Label(String udf144_Label) {
			this.udf144_Label = udf144_Label;
		}

		public String getUdf145_Label() {
			return udf145_Label;
		}

		public void setUdf145_Label(String udf145_Label) {
			this.udf145_Label = udf145_Label;
		}

		public String getUdf146_Label() {
			return udf146_Label;
		}

		public void setUdf146_Label(String udf146_Label) {
			this.udf146_Label = udf146_Label;
		}

		public String getUdf147_Label() {
			return udf147_Label;
		}

		public void setUdf147_Label(String udf147_Label) {
			this.udf147_Label = udf147_Label;
		}

		public String getUdf148_Label() {
			return udf148_Label;
		}

		public void setUdf148_Label(String udf148_Label) {
			this.udf148_Label = udf148_Label;
		}

		public String getUdf149_Label() {
			return udf149_Label;
		}

		public void setUdf149_Label(String udf149_Label) {
			this.udf149_Label = udf149_Label;
		}

		public String getUdf150_Label() {
			return udf150_Label;
		}

		public void setUdf150_Label(String udf150_Label) {
			this.udf150_Label = udf150_Label;
		}

		public String getUdf151_Label() {
			return udf151_Label;
		}

		public void setUdf151_Label(String udf151_Label) {
			this.udf151_Label = udf151_Label;
		}

		public String getUdf152_Label() {
			return udf152_Label;
		}

		public void setUdf152_Label(String udf152_Label) {
			this.udf152_Label = udf152_Label;
		}

		public String getUdf153_Label() {
			return udf153_Label;
		}

		public void setUdf153_Label(String udf153_Label) {
			this.udf153_Label = udf153_Label;
		}

		public String getUdf154_Label() {
			return udf154_Label;
		}

		public void setUdf154_Label(String udf154_Label) {
			this.udf154_Label = udf154_Label;
		}

		public String getUdf155_Label() {
			return udf155_Label;
		}

		public void setUdf155_Label(String udf155_Label) {
			this.udf155_Label = udf155_Label;
		}

		public String getUdf156_Label() {
			return udf156_Label;
		}

		public void setUdf156_Label(String udf156_Label) {
			this.udf156_Label = udf156_Label;
		}

		public String getUdf157_Label() {
			return udf157_Label;
		}

		public void setUdf157_Label(String udf157_Label) {
			this.udf157_Label = udf157_Label;
		}

		public String getUdf158_Label() {
			return udf158_Label;
		}

		public void setUdf158_Label(String udf158_Label) {
			this.udf158_Label = udf158_Label;
		}

		public String getUdf159_Label() {
			return udf159_Label;
		}

		public void setUdf159_Label(String udf159_Label) {
			this.udf159_Label = udf159_Label;
		}

		public String getUdf160_Label() {
			return udf160_Label;
		}

		public void setUdf160_Label(String udf160_Label) {
			this.udf160_Label = udf160_Label;
		}

		public String getUdf161_Label() {
			return udf161_Label;
		}

		public void setUdf161_Label(String udf161_Label) {
			this.udf161_Label = udf161_Label;
		}

		public String getUdf162_Label() {
			return udf162_Label;
		}

		public void setUdf162_Label(String udf162_Label) {
			this.udf162_Label = udf162_Label;
		}

		public String getUdf163_Label() {
			return udf163_Label;
		}

		public void setUdf163_Label(String udf163_Label) {
			this.udf163_Label = udf163_Label;
		}

		public String getUdf164_Label() {
			return udf164_Label;
		}

		public void setUdf164_Label(String udf164_Label) {
			this.udf164_Label = udf164_Label;
		}

		public String getUdf165_Label() {
			return udf165_Label;
		}

		public void setUdf165_Label(String udf165_Label) {
			this.udf165_Label = udf165_Label;
		}

		public String getUdf166_Label() {
			return udf166_Label;
		}

		public void setUdf166_Label(String udf166_Label) {
			this.udf166_Label = udf166_Label;
		}

		public String getUdf167_Label() {
			return udf167_Label;
		}

		public void setUdf167_Label(String udf167_Label) {
			this.udf167_Label = udf167_Label;
		}

		public String getUdf168_Label() {
			return udf168_Label;
		}

		public void setUdf168_Label(String udf168_Label) {
			this.udf168_Label = udf168_Label;
		}

		public String getUdf169_Label() {
			return udf169_Label;
		}

		public void setUdf169_Label(String udf169_Label) {
			this.udf169_Label = udf169_Label;
		}

		public String getUdf170_Label() {
			return udf170_Label;
		}

		public void setUdf170_Label(String udf170_Label) {
			this.udf170_Label = udf170_Label;
		}

		public String getUdf171_Label() {
			return udf171_Label;
		}

		public void setUdf171_Label(String udf171_Label) {
			this.udf171_Label = udf171_Label;
		}

		public String getUdf172_Label() {
			return udf172_Label;
		}

		public void setUdf172_Label(String udf172_Label) {
			this.udf172_Label = udf172_Label;
		}

		public String getUdf173_Label() {
			return udf173_Label;
		}

		public void setUdf173_Label(String udf173_Label) {
			this.udf173_Label = udf173_Label;
		}

		public String getUdf174_Label() {
			return udf174_Label;
		}

		public void setUdf174_Label(String udf174_Label) {
			this.udf174_Label = udf174_Label;
		}

		public String getUdf175_Label() {
			return udf175_Label;
		}

		public void setUdf175_Label(String udf175_Label) {
			this.udf175_Label = udf175_Label;
		}

		public String getUdf176_Label() {
			return udf176_Label;
		}

		public void setUdf176_Label(String udf176_Label) {
			this.udf176_Label = udf176_Label;
		}

		public String getUdf177_Label() {
			return udf177_Label;
		}

		public void setUdf177_Label(String udf177_Label) {
			this.udf177_Label = udf177_Label;
		}

		public String getUdf178_Label() {
			return udf178_Label;
		}

		public void setUdf178_Label(String udf178_Label) {
			this.udf178_Label = udf178_Label;
		}

		public String getUdf179_Label() {
			return udf179_Label;
		}

		public void setUdf179_Label(String udf179_Label) {
			this.udf179_Label = udf179_Label;
		}

		public String getUdf180_Label() {
			return udf180_Label;
		}

		public void setUdf180_Label(String udf180_Label) {
			this.udf180_Label = udf180_Label;
		}

		public String getUdf181_Label() {
			return udf181_Label;
		}

		public void setUdf181_Label(String udf181_Label) {
			this.udf181_Label = udf181_Label;
		}

		public String getUdf182_Label() {
			return udf182_Label;
		}

		public void setUdf182_Label(String udf182_Label) {
			this.udf182_Label = udf182_Label;
		}

		public String getUdf183_Label() {
			return udf183_Label;
		}

		public void setUdf183_Label(String udf183_Label) {
			this.udf183_Label = udf183_Label;
		}

		public String getUdf184_Label() {
			return udf184_Label;
		}

		public void setUdf184_Label(String udf184_Label) {
			this.udf184_Label = udf184_Label;
		}

		public String getUdf185_Label() {
			return udf185_Label;
		}

		public void setUdf185_Label(String udf185_Label) {
			this.udf185_Label = udf185_Label;
		}

		public String getUdf186_Label() {
			return udf186_Label;
		}

		public void setUdf186_Label(String udf186_Label) {
			this.udf186_Label = udf186_Label;
		}

		public String getUdf187_Label() {
			return udf187_Label;
		}

		public void setUdf187_Label(String udf187_Label) {
			this.udf187_Label = udf187_Label;
		}

		public String getUdf188_Label() {
			return udf188_Label;
		}

		public void setUdf188_Label(String udf188_Label) {
			this.udf188_Label = udf188_Label;
		}

		public String getUdf189_Label() {
			return udf189_Label;
		}

		public void setUdf189_Label(String udf189_Label) {
			this.udf189_Label = udf189_Label;
		}

		public String getUdf190_Label() {
			return udf190_Label;
		}

		public void setUdf190_Label(String udf190_Label) {
			this.udf190_Label = udf190_Label;
		}

		public String getUdf191_Label() {
			return udf191_Label;
		}

		public void setUdf191_Label(String udf191_Label) {
			this.udf191_Label = udf191_Label;
		}

		public String getUdf192_Label() {
			return udf192_Label;
		}

		public void setUdf192_Label(String udf192_Label) {
			this.udf192_Label = udf192_Label;
		}

		public String getUdf193_Label() {
			return udf193_Label;
		}

		public void setUdf193_Label(String udf193_Label) {
			this.udf193_Label = udf193_Label;
		}

		public String getUdf194_Label() {
			return udf194_Label;
		}

		public void setUdf194_Label(String udf194_Label) {
			this.udf194_Label = udf194_Label;
		}

		public String getUdf195_Label() {
			return udf195_Label;
		}

		public void setUdf195_Label(String udf195_Label) {
			this.udf195_Label = udf195_Label;
		}

		public String getUdf196_Label() {
			return udf196_Label;
		}

		public void setUdf196_Label(String udf196_Label) {
			this.udf196_Label = udf196_Label;
		}

		public String getUdf197_Label() {
			return udf197_Label;
		}

		public void setUdf197_Label(String udf197_Label) {
			this.udf197_Label = udf197_Label;
		}

		public String getUdf198_Label() {
			return udf198_Label;
		}

		public void setUdf198_Label(String udf198_Label) {
			this.udf198_Label = udf198_Label;
		}

		public String getUdf199_Label() {
			return udf199_Label;
		}

		public void setUdf199_Label(String udf199_Label) {
			this.udf199_Label = udf199_Label;
		}

		public String getUdf200_Label() {
			return udf200_Label;
		}

		public void setUdf200_Label(String udf200_Label) {
			this.udf200_Label = udf200_Label;
		}

		public String getUdf201_Label() {
			return udf201_Label;
		}

		public void setUdf201_Label(String udf201_Label) {
			this.udf201_Label = udf201_Label;
		}

		public String getUdf202_Label() {
			return udf202_Label;
		}

		public void setUdf202_Label(String udf202_Label) {
			this.udf202_Label = udf202_Label;
		}

		public String getUdf203_Label() {
			return udf203_Label;
		}

		public void setUdf203_Label(String udf203_Label) {
			this.udf203_Label = udf203_Label;
		}

		public String getUdf204_Label() {
			return udf204_Label;
		}

		public void setUdf204_Label(String udf204_Label) {
			this.udf204_Label = udf204_Label;
		}

		public String getUdf205_Label() {
			return udf205_Label;
		}

		public void setUdf205_Label(String udf205_Label) {
			this.udf205_Label = udf205_Label;
		}

		public String getUdf206_Label() {
			return udf206_Label;
		}

		public void setUdf206_Label(String udf206_Label) {
			this.udf206_Label = udf206_Label;
		}

		public String getUdf207_Label() {
			return udf207_Label;
		}

		public void setUdf207_Label(String udf207_Label) {
			this.udf207_Label = udf207_Label;
		}

		public String getUdf208_Label() {
			return udf208_Label;
		}

		public void setUdf208_Label(String udf208_Label) {
			this.udf208_Label = udf208_Label;
		}

		public String getUdf209_Label() {
			return udf209_Label;
		}

		public void setUdf209_Label(String udf209_Label) {
			this.udf209_Label = udf209_Label;
		}

		public String getUdf210_Label() {
			return udf210_Label;
		}

		public void setUdf210_Label(String udf210_Label) {
			this.udf210_Label = udf210_Label;
		}

		public String getUdf211_Label() {
			return udf211_Label;
		}

		public void setUdf211_Label(String udf211_Label) {
			this.udf211_Label = udf211_Label;
		}

		public String getUdf212_Label() {
			return udf212_Label;
		}

		public void setUdf212_Label(String udf212_Label) {
			this.udf212_Label = udf212_Label;
		}

		public String getUdf213_Label() {
			return udf213_Label;
		}

		public void setUdf213_Label(String udf213_Label) {
			this.udf213_Label = udf213_Label;
		}

		public String getUdf214_Label() {
			return udf214_Label;
		}

		public void setUdf214_Label(String udf214_Label) {
			this.udf214_Label = udf214_Label;
		}

		public String getUdf215_Label() {
			return udf215_Label;
		}

		public void setUdf215_Label(String udf215_Label) {
			this.udf215_Label = udf215_Label;
		}

		public String getUdf116() {
			return udf116;
		}

		public void setUdf116(String udf116) {
			this.udf116 = udf116;
		}

		public String getUdf117() {
			return udf117;
		}

		public void setUdf117(String udf117) {
			this.udf117 = udf117;
		}

		public String getUdf118() {
			return udf118;
		}

		public void setUdf118(String udf118) {
			this.udf118 = udf118;
		}

		public String getUdf119() {
			return udf119;
		}

		public void setUdf119(String udf119) {
			this.udf119 = udf119;
		}

		public String getUdf120() {
			return udf120;
		}

		public void setUdf120(String udf120) {
			this.udf120 = udf120;
		}

		public String getUdf121() {
			return udf121;
		}

		public void setUdf121(String udf121) {
			this.udf121 = udf121;
		}

		public String getUdf122() {
			return udf122;
		}

		public void setUdf122(String udf122) {
			this.udf122 = udf122;
		}

		public String getUdf123() {
			return udf123;
		}

		public void setUdf123(String udf123) {
			this.udf123 = udf123;
		}

		public String getUdf124() {
			return udf124;
		}

		public void setUdf124(String udf124) {
			this.udf124 = udf124;
		}

		public String getUdf125() {
			return udf125;
		}

		public void setUdf125(String udf125) {
			this.udf125 = udf125;
		}

		public String getUdf126() {
			return udf126;
		}

		public void setUdf126(String udf126) {
			this.udf126 = udf126;
		}

		public String getUdf127() {
			return udf127;
		}

		public void setUdf127(String udf127) {
			this.udf127 = udf127;
		}

		public String getUdf128() {
			return udf128;
		}

		public void setUdf128(String udf128) {
			this.udf128 = udf128;
		}

		public String getUdf129() {
			return udf129;
		}

		public void setUdf129(String udf129) {
			this.udf129 = udf129;
		}

		public String getUdf130() {
			return udf130;
		}

		public void setUdf130(String udf130) {
			this.udf130 = udf130;
		}

		public String getUdf131() {
			return udf131;
		}

		public void setUdf131(String udf131) {
			this.udf131 = udf131;
		}

		public String getUdf132() {
			return udf132;
		}

		public void setUdf132(String udf132) {
			this.udf132 = udf132;
		}

		public String getUdf133() {
			return udf133;
		}

		public void setUdf133(String udf133) {
			this.udf133 = udf133;
		}

		public String getUdf134() {
			return udf134;
		}

		public void setUdf134(String udf134) {
			this.udf134 = udf134;
		}

		public String getUdf135() {
			return udf135;
		}

		public void setUdf135(String udf135) {
			this.udf135 = udf135;
		}

		public String getUdf136() {
			return udf136;
		}

		public void setUdf136(String udf136) {
			this.udf136 = udf136;
		}

		public String getUdf137() {
			return udf137;
		}

		public void setUdf137(String udf137) {
			this.udf137 = udf137;
		}

		public String getUdf138() {
			return udf138;
		}

		public void setUdf138(String udf138) {
			this.udf138 = udf138;
		}

		public String getUdf139() {
			return udf139;
		}

		public void setUdf139(String udf139) {
			this.udf139 = udf139;
		}

		public String getUdf140() {
			return udf140;
		}

		public void setUdf140(String udf140) {
			this.udf140 = udf140;
		}

		public String getUdf141() {
			return udf141;
		}

		public void setUdf141(String udf141) {
			this.udf141 = udf141;
		}

		public String getUdf142() {
			return udf142;
		}

		public void setUdf142(String udf142) {
			this.udf142 = udf142;
		}

		public String getUdf143() {
			return udf143;
		}

		public void setUdf143(String udf143) {
			this.udf143 = udf143;
		}

		public String getUdf144() {
			return udf144;
		}

		public void setUdf144(String udf144) {
			this.udf144 = udf144;
		}

		public String getUdf145() {
			return udf145;
		}

		public void setUdf145(String udf145) {
			this.udf145 = udf145;
		}

		public String getUdf146() {
			return udf146;
		}

		public void setUdf146(String udf146) {
			this.udf146 = udf146;
		}

		public String getUdf147() {
			return udf147;
		}

		public void setUdf147(String udf147) {
			this.udf147 = udf147;
		}

		public String getUdf148() {
			return udf148;
		}

		public void setUdf148(String udf148) {
			this.udf148 = udf148;
		}

		public String getUdf149() {
			return udf149;
		}

		public void setUdf149(String udf149) {
			this.udf149 = udf149;
		}

		public String getUdf150() {
			return udf150;
		}

		public void setUdf150(String udf150) {
			this.udf150 = udf150;
		}

		public String getUdf151() {
			return udf151;
		}

		public void setUdf151(String udf151) {
			this.udf151 = udf151;
		}

		public String getUdf152() {
			return udf152;
		}

		public void setUdf152(String udf152) {
			this.udf152 = udf152;
		}

		public String getUdf153() {
			return udf153;
		}

		public void setUdf153(String udf153) {
			this.udf153 = udf153;
		}

		public String getUdf154() {
			return udf154;
		}

		public void setUdf154(String udf154) {
			this.udf154 = udf154;
		}

		public String getUdf155() {
			return udf155;
		}

		public void setUdf155(String udf155) {
			this.udf155 = udf155;
		}

		public String getUdf156() {
			return udf156;
		}

		public void setUdf156(String udf156) {
			this.udf156 = udf156;
		}

		public String getUdf157() {
			return udf157;
		}

		public void setUdf157(String udf157) {
			this.udf157 = udf157;
		}

		public String getUdf158() {
			return udf158;
		}

		public void setUdf158(String udf158) {
			this.udf158 = udf158;
		}

		public String getUdf159() {
			return udf159;
		}

		public void setUdf159(String udf159) {
			this.udf159 = udf159;
		}

		public String getUdf160() {
			return udf160;
		}

		public void setUdf160(String udf160) {
			this.udf160 = udf160;
		}

		public String getUdf161() {
			return udf161;
		}

		public void setUdf161(String udf161) {
			this.udf161 = udf161;
		}

		public String getUdf162() {
			return udf162;
		}

		public void setUdf162(String udf162) {
			this.udf162 = udf162;
		}

		public String getUdf163() {
			return udf163;
		}

		public void setUdf163(String udf163) {
			this.udf163 = udf163;
		}

		public String getUdf164() {
			return udf164;
		}

		public void setUdf164(String udf164) {
			this.udf164 = udf164;
		}

		public String getUdf165() {
			return udf165;
		}

		public void setUdf165(String udf165) {
			this.udf165 = udf165;
		}

		public String getUdf166() {
			return udf166;
		}

		public void setUdf166(String udf166) {
			this.udf166 = udf166;
		}

		public String getUdf167() {
			return udf167;
		}

		public void setUdf167(String udf167) {
			this.udf167 = udf167;
		}

		public String getUdf168() {
			return udf168;
		}

		public void setUdf168(String udf168) {
			this.udf168 = udf168;
		}

		public String getUdf169() {
			return udf169;
		}

		public void setUdf169(String udf169) {
			this.udf169 = udf169;
		}

		public String getUdf170() {
			return udf170;
		}

		public void setUdf170(String udf170) {
			this.udf170 = udf170;
		}

		public String getUdf171() {
			return udf171;
		}

		public void setUdf171(String udf171) {
			this.udf171 = udf171;
		}

		public String getUdf172() {
			return udf172;
		}

		public void setUdf172(String udf172) {
			this.udf172 = udf172;
		}

		public String getUdf173() {
			return udf173;
		}

		public void setUdf173(String udf173) {
			this.udf173 = udf173;
		}

		public String getUdf174() {
			return udf174;
		}

		public void setUdf174(String udf174) {
			this.udf174 = udf174;
		}

		public String getUdf175() {
			return udf175;
		}

		public void setUdf175(String udf175) {
			this.udf175 = udf175;
		}

		public String getUdf176() {
			return udf176;
		}

		public void setUdf176(String udf176) {
			this.udf176 = udf176;
		}

		public String getUdf177() {
			return udf177;
		}

		public void setUdf177(String udf177) {
			this.udf177 = udf177;
		}

		public String getUdf178() {
			return udf178;
		}

		public void setUdf178(String udf178) {
			this.udf178 = udf178;
		}

		public String getUdf179() {
			return udf179;
		}

		public void setUdf179(String udf179) {
			this.udf179 = udf179;
		}

		public String getUdf180() {
			return udf180;
		}

		public void setUdf180(String udf180) {
			this.udf180 = udf180;
		}

		public String getUdf181() {
			return udf181;
		}

		public void setUdf181(String udf181) {
			this.udf181 = udf181;
		}

		public String getUdf182() {
			return udf182;
		}

		public void setUdf182(String udf182) {
			this.udf182 = udf182;
		}

		public String getUdf183() {
			return udf183;
		}

		public void setUdf183(String udf183) {
			this.udf183 = udf183;
		}

		public String getUdf184() {
			return udf184;
		}

		public void setUdf184(String udf184) {
			this.udf184 = udf184;
		}

		public String getUdf185() {
			return udf185;
		}

		public void setUdf185(String udf185) {
			this.udf185 = udf185;
		}

		public String getUdf186() {
			return udf186;
		}

		public void setUdf186(String udf186) {
			this.udf186 = udf186;
		}

		public String getUdf187() {
			return udf187;
		}

		public void setUdf187(String udf187) {
			this.udf187 = udf187;
		}

		public String getUdf188() {
			return udf188;
		}

		public void setUdf188(String udf188) {
			this.udf188 = udf188;
		}

		public String getUdf189() {
			return udf189;
		}

		public void setUdf189(String udf189) {
			this.udf189 = udf189;
		}

		public String getUdf190() {
			return udf190;
		}

		public void setUdf190(String udf190) {
			this.udf190 = udf190;
		}

		public String getUdf191() {
			return udf191;
		}

		public void setUdf191(String udf191) {
			this.udf191 = udf191;
		}

		public String getUdf192() {
			return udf192;
		}

		public void setUdf192(String udf192) {
			this.udf192 = udf192;
		}

		public String getUdf193() {
			return udf193;
		}

		public void setUdf193(String udf193) {
			this.udf193 = udf193;
		}

		public String getUdf194() {
			return udf194;
		}

		public void setUdf194(String udf194) {
			this.udf194 = udf194;
		}

		public String getUdf195() {
			return udf195;
		}

		public void setUdf195(String udf195) {
			this.udf195 = udf195;
		}

		public String getUdf196() {
			return udf196;
		}

		public void setUdf196(String udf196) {
			this.udf196 = udf196;
		}

		public String getUdf197() {
			return udf197;
		}

		public void setUdf197(String udf197) {
			this.udf197 = udf197;
		}

		public String getUdf198() {
			return udf198;
		}

		public void setUdf198(String udf198) {
			this.udf198 = udf198;
		}

		public String getUdf199() {
			return udf199;
		}

		public void setUdf199(String udf199) {
			this.udf199 = udf199;
		}

		public String getUdf200() {
			return udf200;
		}

		public void setUdf200(String udf200) {
			this.udf200 = udf200;
		}

		public String getUdf201() {
			return udf201;
		}

		public void setUdf201(String udf201) {
			this.udf201 = udf201;
		}

		public String getUdf202() {
			return udf202;
		}

		public void setUdf202(String udf202) {
			this.udf202 = udf202;
		}

		public String getUdf203() {
			return udf203;
		}

		public void setUdf203(String udf203) {
			this.udf203 = udf203;
		}

		public String getUdf204() {
			return udf204;
		}

		public void setUdf204(String udf204) {
			this.udf204 = udf204;
		}

		public String getUdf205() {
			return udf205;
		}

		public void setUdf205(String udf205) {
			this.udf205 = udf205;
		}

		public String getUdf206() {
			return udf206;
		}

		public void setUdf206(String udf206) {
			this.udf206 = udf206;
		}

		public String getUdf207() {
			return udf207;
		}

		public void setUdf207(String udf207) {
			this.udf207 = udf207;
		}

		public String getUdf208() {
			return udf208;
		}

		public void setUdf208(String udf208) {
			this.udf208 = udf208;
		}

		public String getUdf209() {
			return udf209;
		}

		public void setUdf209(String udf209) {
			this.udf209 = udf209;
		}

		public String getUdf210() {
			return udf210;
		}

		public void setUdf210(String udf210) {
			this.udf210 = udf210;
		}

		public String getUdf211() {
			return udf211;
		}

		public void setUdf211(String udf211) {
			this.udf211 = udf211;
		}

		public String getUdf212() {
			return udf212;
		}

		public void setUdf212(String udf212) {
			this.udf212 = udf212;
		}

		public String getUdf213() {
			return udf213;
		}

		public void setUdf213(String udf213) {
			this.udf213 = udf213;
		}

		public String getUdf214() {
			return udf214;
		}

		public void setUdf214(String udf214) {
			this.udf214 = udf214;
		}

		public String getUdf215() {
			return udf215;
		}

		public void setUdf215(String udf215) {
			this.udf215 = udf215;
		}

		public String getUdf116_Flag() {
			return udf116_Flag;
		}

		public void setUdf116_Flag(String udf116_Flag) {
			this.udf116_Flag = udf116_Flag;
		}

		public String getUdf117_Flag() {
			return udf117_Flag;
		}

		public void setUdf117_Flag(String udf117_Flag) {
			this.udf117_Flag = udf117_Flag;
		}

		public String getUdf118_Flag() {
			return udf118_Flag;
		}

		public void setUdf118_Flag(String udf118_Flag) {
			this.udf118_Flag = udf118_Flag;
		}

		public String getUdf119_Flag() {
			return udf119_Flag;
		}

		public void setUdf119_Flag(String udf119_Flag) {
			this.udf119_Flag = udf119_Flag;
		}

		public String getUdf120_Flag() {
			return udf120_Flag;
		}

		public void setUdf120_Flag(String udf120_Flag) {
			this.udf120_Flag = udf120_Flag;
		}

		public String getUdf121_Flag() {
			return udf121_Flag;
		}

		public void setUdf121_Flag(String udf121_Flag) {
			this.udf121_Flag = udf121_Flag;
		}

		public String getUdf122_Flag() {
			return udf122_Flag;
		}

		public void setUdf122_Flag(String udf122_Flag) {
			this.udf122_Flag = udf122_Flag;
		}

		public String getUdf123_Flag() {
			return udf123_Flag;
		}

		public void setUdf123_Flag(String udf123_Flag) {
			this.udf123_Flag = udf123_Flag;
		}

		public String getUdf124_Flag() {
			return udf124_Flag;
		}

		public void setUdf124_Flag(String udf124_Flag) {
			this.udf124_Flag = udf124_Flag;
		}

		public String getUdf125_Flag() {
			return udf125_Flag;
		}

		public void setUdf125_Flag(String udf125_Flag) {
			this.udf125_Flag = udf125_Flag;
		}

		public String getUdf126_Flag() {
			return udf126_Flag;
		}

		public void setUdf126_Flag(String udf126_Flag) {
			this.udf126_Flag = udf126_Flag;
		}

		public String getUdf127_Flag() {
			return udf127_Flag;
		}

		public void setUdf127_Flag(String udf127_Flag) {
			this.udf127_Flag = udf127_Flag;
		}

		public String getUdf128_Flag() {
			return udf128_Flag;
		}

		public void setUdf128_Flag(String udf128_Flag) {
			this.udf128_Flag = udf128_Flag;
		}

		public String getUdf129_Flag() {
			return udf129_Flag;
		}

		public void setUdf129_Flag(String udf129_Flag) {
			this.udf129_Flag = udf129_Flag;
		}

		public String getUdf130_Flag() {
			return udf130_Flag;
		}

		public void setUdf130_Flag(String udf130_Flag) {
			this.udf130_Flag = udf130_Flag;
		}

		public String getUdf131_Flag() {
			return udf131_Flag;
		}

		public void setUdf131_Flag(String udf131_Flag) {
			this.udf131_Flag = udf131_Flag;
		}

		public String getUdf132_Flag() {
			return udf132_Flag;
		}

		public void setUdf132_Flag(String udf132_Flag) {
			this.udf132_Flag = udf132_Flag;
		}

		public String getUdf133_Flag() {
			return udf133_Flag;
		}

		public void setUdf133_Flag(String udf133_Flag) {
			this.udf133_Flag = udf133_Flag;
		}

		public String getUdf134_Flag() {
			return udf134_Flag;
		}

		public void setUdf134_Flag(String udf134_Flag) {
			this.udf134_Flag = udf134_Flag;
		}

		public String getUdf135_Flag() {
			return udf135_Flag;
		}

		public void setUdf135_Flag(String udf135_Flag) {
			this.udf135_Flag = udf135_Flag;
		}

		public String getUdf136_Flag() {
			return udf136_Flag;
		}

		public void setUdf136_Flag(String udf136_Flag) {
			this.udf136_Flag = udf136_Flag;
		}

		public String getUdf137_Flag() {
			return udf137_Flag;
		}

		public void setUdf137_Flag(String udf137_Flag) {
			this.udf137_Flag = udf137_Flag;
		}

		public String getUdf138_Flag() {
			return udf138_Flag;
		}

		public void setUdf138_Flag(String udf138_Flag) {
			this.udf138_Flag = udf138_Flag;
		}

		public String getUdf139_Flag() {
			return udf139_Flag;
		}

		public void setUdf139_Flag(String udf139_Flag) {
			this.udf139_Flag = udf139_Flag;
		}

		public String getUdf140_Flag() {
			return udf140_Flag;
		}

		public void setUdf140_Flag(String udf140_Flag) {
			this.udf140_Flag = udf140_Flag;
		}

		public String getUdf141_Flag() {
			return udf141_Flag;
		}

		public void setUdf141_Flag(String udf141_Flag) {
			this.udf141_Flag = udf141_Flag;
		}

		public String getUdf142_Flag() {
			return udf142_Flag;
		}

		public void setUdf142_Flag(String udf142_Flag) {
			this.udf142_Flag = udf142_Flag;
		}

		public String getUdf143_Flag() {
			return udf143_Flag;
		}

		public void setUdf143_Flag(String udf143_Flag) {
			this.udf143_Flag = udf143_Flag;
		}

		public String getUdf144_Flag() {
			return udf144_Flag;
		}

		public void setUdf144_Flag(String udf144_Flag) {
			this.udf144_Flag = udf144_Flag;
		}

		public String getUdf145_Flag() {
			return udf145_Flag;
		}

		public void setUdf145_Flag(String udf145_Flag) {
			this.udf145_Flag = udf145_Flag;
		}

		public String getUdf146_Flag() {
			return udf146_Flag;
		}

		public void setUdf146_Flag(String udf146_Flag) {
			this.udf146_Flag = udf146_Flag;
		}

		public String getUdf147_Flag() {
			return udf147_Flag;
		}

		public void setUdf147_Flag(String udf147_Flag) {
			this.udf147_Flag = udf147_Flag;
		}

		public String getUdf148_Flag() {
			return udf148_Flag;
		}

		public void setUdf148_Flag(String udf148_Flag) {
			this.udf148_Flag = udf148_Flag;
		}

		public String getUdf149_Flag() {
			return udf149_Flag;
		}

		public void setUdf149_Flag(String udf149_Flag) {
			this.udf149_Flag = udf149_Flag;
		}

		public String getUdf150_Flag() {
			return udf150_Flag;
		}

		public void setUdf150_Flag(String udf150_Flag) {
			this.udf150_Flag = udf150_Flag;
		}

		public String getUdf151_Flag() {
			return udf151_Flag;
		}

		public void setUdf151_Flag(String udf151_Flag) {
			this.udf151_Flag = udf151_Flag;
		}

		public String getUdf152_Flag() {
			return udf152_Flag;
		}

		public void setUdf152_Flag(String udf152_Flag) {
			this.udf152_Flag = udf152_Flag;
		}

		public String getUdf153_Flag() {
			return udf153_Flag;
		}

		public void setUdf153_Flag(String udf153_Flag) {
			this.udf153_Flag = udf153_Flag;
		}

		public String getUdf154_Flag() {
			return udf154_Flag;
		}

		public void setUdf154_Flag(String udf154_Flag) {
			this.udf154_Flag = udf154_Flag;
		}

		public String getUdf155_Flag() {
			return udf155_Flag;
		}

		public void setUdf155_Flag(String udf155_Flag) {
			this.udf155_Flag = udf155_Flag;
		}

		public String getUdf156_Flag() {
			return udf156_Flag;
		}

		public void setUdf156_Flag(String udf156_Flag) {
			this.udf156_Flag = udf156_Flag;
		}

		public String getUdf157_Flag() {
			return udf157_Flag;
		}

		public void setUdf157_Flag(String udf157_Flag) {
			this.udf157_Flag = udf157_Flag;
		}

		public String getUdf158_Flag() {
			return udf158_Flag;
		}

		public void setUdf158_Flag(String udf158_Flag) {
			this.udf158_Flag = udf158_Flag;
		}

		public String getUdf159_Flag() {
			return udf159_Flag;
		}

		public void setUdf159_Flag(String udf159_Flag) {
			this.udf159_Flag = udf159_Flag;
		}

		public String getUdf160_Flag() {
			return udf160_Flag;
		}

		public void setUdf160_Flag(String udf160_Flag) {
			this.udf160_Flag = udf160_Flag;
		}

		public String getUdf161_Flag() {
			return udf161_Flag;
		}

		public void setUdf161_Flag(String udf161_Flag) {
			this.udf161_Flag = udf161_Flag;
		}

		public String getUdf162_Flag() {
			return udf162_Flag;
		}

		public void setUdf162_Flag(String udf162_Flag) {
			this.udf162_Flag = udf162_Flag;
		}

		public String getUdf163_Flag() {
			return udf163_Flag;
		}

		public void setUdf163_Flag(String udf163_Flag) {
			this.udf163_Flag = udf163_Flag;
		}

		public String getUdf164_Flag() {
			return udf164_Flag;
		}

		public void setUdf164_Flag(String udf164_Flag) {
			this.udf164_Flag = udf164_Flag;
		}

		public String getUdf165_Flag() {
			return udf165_Flag;
		}

		public void setUdf165_Flag(String udf165_Flag) {
			this.udf165_Flag = udf165_Flag;
		}

		public String getUdf166_Flag() {
			return udf166_Flag;
		}

		public void setUdf166_Flag(String udf166_Flag) {
			this.udf166_Flag = udf166_Flag;
		}

		public String getUdf167_Flag() {
			return udf167_Flag;
		}

		public void setUdf167_Flag(String udf167_Flag) {
			this.udf167_Flag = udf167_Flag;
		}

		public String getUdf168_Flag() {
			return udf168_Flag;
		}

		public void setUdf168_Flag(String udf168_Flag) {
			this.udf168_Flag = udf168_Flag;
		}

		public String getUdf169_Flag() {
			return udf169_Flag;
		}

		public void setUdf169_Flag(String udf169_Flag) {
			this.udf169_Flag = udf169_Flag;
		}

		public String getUdf170_Flag() {
			return udf170_Flag;
		}

		public void setUdf170_Flag(String udf170_Flag) {
			this.udf170_Flag = udf170_Flag;
		}

		public String getUdf171_Flag() {
			return udf171_Flag;
		}

		public void setUdf171_Flag(String udf171_Flag) {
			this.udf171_Flag = udf171_Flag;
		}

		public String getUdf172_Flag() {
			return udf172_Flag;
		}

		public void setUdf172_Flag(String udf172_Flag) {
			this.udf172_Flag = udf172_Flag;
		}

		public String getUdf173_Flag() {
			return udf173_Flag;
		}

		public void setUdf173_Flag(String udf173_Flag) {
			this.udf173_Flag = udf173_Flag;
		}

		public String getUdf174_Flag() {
			return udf174_Flag;
		}

		public void setUdf174_Flag(String udf174_Flag) {
			this.udf174_Flag = udf174_Flag;
		}

		public String getUdf175_Flag() {
			return udf175_Flag;
		}

		public void setUdf175_Flag(String udf175_Flag) {
			this.udf175_Flag = udf175_Flag;
		}

		public String getUdf176_Flag() {
			return udf176_Flag;
		}

		public void setUdf176_Flag(String udf176_Flag) {
			this.udf176_Flag = udf176_Flag;
		}

		public String getUdf177_Flag() {
			return udf177_Flag;
		}

		public void setUdf177_Flag(String udf177_Flag) {
			this.udf177_Flag = udf177_Flag;
		}

		public String getUdf178_Flag() {
			return udf178_Flag;
		}

		public void setUdf178_Flag(String udf178_Flag) {
			this.udf178_Flag = udf178_Flag;
		}

		public String getUdf179_Flag() {
			return udf179_Flag;
		}

		public void setUdf179_Flag(String udf179_Flag) {
			this.udf179_Flag = udf179_Flag;
		}

		public String getUdf180_Flag() {
			return udf180_Flag;
		}

		public void setUdf180_Flag(String udf180_Flag) {
			this.udf180_Flag = udf180_Flag;
		}

		public String getUdf181_Flag() {
			return udf181_Flag;
		}

		public void setUdf181_Flag(String udf181_Flag) {
			this.udf181_Flag = udf181_Flag;
		}

		public String getUdf182_Flag() {
			return udf182_Flag;
		}

		public void setUdf182_Flag(String udf182_Flag) {
			this.udf182_Flag = udf182_Flag;
		}

		public String getUdf183_Flag() {
			return udf183_Flag;
		}

		public void setUdf183_Flag(String udf183_Flag) {
			this.udf183_Flag = udf183_Flag;
		}

		public String getUdf184_Flag() {
			return udf184_Flag;
		}

		public void setUdf184_Flag(String udf184_Flag) {
			this.udf184_Flag = udf184_Flag;
		}

		public String getUdf185_Flag() {
			return udf185_Flag;
		}

		public void setUdf185_Flag(String udf185_Flag) {
			this.udf185_Flag = udf185_Flag;
		}

		public String getUdf186_Flag() {
			return udf186_Flag;
		}

		public void setUdf186_Flag(String udf186_Flag) {
			this.udf186_Flag = udf186_Flag;
		}

		public String getUdf187_Flag() {
			return udf187_Flag;
		}

		public void setUdf187_Flag(String udf187_Flag) {
			this.udf187_Flag = udf187_Flag;
		}

		public String getUdf188_Flag() {
			return udf188_Flag;
		}

		public void setUdf188_Flag(String udf188_Flag) {
			this.udf188_Flag = udf188_Flag;
		}

		public String getUdf189_Flag() {
			return udf189_Flag;
		}

		public void setUdf189_Flag(String udf189_Flag) {
			this.udf189_Flag = udf189_Flag;
		}

		public String getUdf190_Flag() {
			return udf190_Flag;
		}

		public void setUdf190_Flag(String udf190_Flag) {
			this.udf190_Flag = udf190_Flag;
		}

		public String getUdf191_Flag() {
			return udf191_Flag;
		}

		public void setUdf191_Flag(String udf191_Flag) {
			this.udf191_Flag = udf191_Flag;
		}

		public String getUdf192_Flag() {
			return udf192_Flag;
		}

		public void setUdf192_Flag(String udf192_Flag) {
			this.udf192_Flag = udf192_Flag;
		}

		public String getUdf193_Flag() {
			return udf193_Flag;
		}

		public void setUdf193_Flag(String udf193_Flag) {
			this.udf193_Flag = udf193_Flag;
		}

		public String getUdf194_Flag() {
			return udf194_Flag;
		}

		public void setUdf194_Flag(String udf194_Flag) {
			this.udf194_Flag = udf194_Flag;
		}

		public String getUdf195_Flag() {
			return udf195_Flag;
		}

		public void setUdf195_Flag(String udf195_Flag) {
			this.udf195_Flag = udf195_Flag;
		}

		public String getUdf196_Flag() {
			return udf196_Flag;
		}

		public void setUdf196_Flag(String udf196_Flag) {
			this.udf196_Flag = udf196_Flag;
		}

		public String getUdf197_Flag() {
			return udf197_Flag;
		}

		public void setUdf197_Flag(String udf197_Flag) {
			this.udf197_Flag = udf197_Flag;
		}

		public String getUdf198_Flag() {
			return udf198_Flag;
		}

		public void setUdf198_Flag(String udf198_Flag) {
			this.udf198_Flag = udf198_Flag;
		}

		public String getUdf199_Flag() {
			return udf199_Flag;
		}

		public void setUdf199_Flag(String udf199_Flag) {
			this.udf199_Flag = udf199_Flag;
		}

		public String getUdf200_Flag() {
			return udf200_Flag;
		}

		public void setUdf200_Flag(String udf200_Flag) {
			this.udf200_Flag = udf200_Flag;
		}

		public String getUdf201_Flag() {
			return udf201_Flag;
		}

		public void setUdf201_Flag(String udf201_Flag) {
			this.udf201_Flag = udf201_Flag;
		}

		public String getUdf202_Flag() {
			return udf202_Flag;
		}

		public void setUdf202_Flag(String udf202_Flag) {
			this.udf202_Flag = udf202_Flag;
		}

		public String getUdf203_Flag() {
			return udf203_Flag;
		}

		public void setUdf203_Flag(String udf203_Flag) {
			this.udf203_Flag = udf203_Flag;
		}

		public String getUdf204_Flag() {
			return udf204_Flag;
		}

		public void setUdf204_Flag(String udf204_Flag) {
			this.udf204_Flag = udf204_Flag;
		}

		public String getUdf205_Flag() {
			return udf205_Flag;
		}

		public void setUdf205_Flag(String udf205_Flag) {
			this.udf205_Flag = udf205_Flag;
		}

		public String getUdf206_Flag() {
			return udf206_Flag;
		}

		public void setUdf206_Flag(String udf206_Flag) {
			this.udf206_Flag = udf206_Flag;
		}

		public String getUdf207_Flag() {
			return udf207_Flag;
		}

		public void setUdf207_Flag(String udf207_Flag) {
			this.udf207_Flag = udf207_Flag;
		}

		public String getUdf208_Flag() {
			return udf208_Flag;
		}

		public void setUdf208_Flag(String udf208_Flag) {
			this.udf208_Flag = udf208_Flag;
		}

		public String getUdf209_Flag() {
			return udf209_Flag;
		}

		public void setUdf209_Flag(String udf209_Flag) {
			this.udf209_Flag = udf209_Flag;
		}

		public String getUdf210_Flag() {
			return udf210_Flag;
		}

		public void setUdf210_Flag(String udf210_Flag) {
			this.udf210_Flag = udf210_Flag;
		}

		public String getUdf211_Flag() {
			return udf211_Flag;
		}

		public void setUdf211_Flag(String udf211_Flag) {
			this.udf211_Flag = udf211_Flag;
		}

		public String getUdf212_Flag() {
			return udf212_Flag;
		}

		public void setUdf212_Flag(String udf212_Flag) {
			this.udf212_Flag = udf212_Flag;
		}

		public String getUdf213_Flag() {
			return udf213_Flag;
		}

		public void setUdf213_Flag(String udf213_Flag) {
			this.udf213_Flag = udf213_Flag;
		}

		public String getUdf214_Flag() {
			return udf214_Flag;
		}

		public void setUdf214_Flag(String udf214_Flag) {
			this.udf214_Flag = udf214_Flag;
		}

		public String getUdf215_Flag() {
			return udf215_Flag;
		}

		public void setUdf215_Flag(String udf215_Flag) {
			this.udf215_Flag = udf215_Flag;
		}

	private String adhocLine;
		private String adhocLastAvailDate;
		private String adhocFacilityExpDate;
		private String adhocFacility ;
		private String generalPurposeLoan ;
		private String adhocTenor;
		private String multiDrawdownAllow;

		public String getAdhocLine() {
			return adhocLine;
		}

		public void setAdhocLine(String adhocLine) {
			this.adhocLine = adhocLine;
		}
		public String getAdhocLastAvailDate() {
			return adhocLastAvailDate;
		}

		public void setAdhocLastAvailDate(String adhocLastAvailDate) {
			this.adhocLastAvailDate = adhocLastAvailDate;
		}

		public String getAdhocFacilityExpDate() {
			return adhocFacilityExpDate;
		}

		public void setAdhocFacilityExpDate(String adhocFacilityExpDate) {
			this.adhocFacilityExpDate = adhocFacilityExpDate;
		}

		public String getAdhocFacility() {
			return adhocFacility;
		}

		public void setAdhocFacility(String adhocFacility) {
			this.adhocFacility = adhocFacility;
		}

		public String getGeneralPurposeLoan() {
			return generalPurposeLoan;
		}

		public void setGeneralPurposeLoan(String generalPurposeLoan) {
			this.generalPurposeLoan = generalPurposeLoan;
		}
		public String getAdhocTenor() {
			return adhocTenor;
		}

		public void setAdhocTenor(String adhocTenor) {
			this.adhocTenor = adhocTenor;
		}

		public String getMultiDrawdownAllow() {
			return multiDrawdownAllow;
		}

		public void setMultiDrawdownAllow(String multiDrawdownAllow) {
			this.multiDrawdownAllow = multiDrawdownAllow;
		}
		

		private String coBorrowerId;
		private String coBorrowerName;

		private List restCoBorrowerList;
		private String LineCoBorrowerLiabIds;

		public String getCoBorrowerId() {
			return coBorrowerId;
		}

		public void setCoBorrowerId(String coBorrowerId) {
			this.coBorrowerId = coBorrowerId;
		}

		public String getCoBorrowerName() {
			return coBorrowerName;
		}

		public void setCoBorrowerName(String coBorrowerName) {
			this.coBorrowerName = coBorrowerName;
		}

		public List getRestCoBorrowerList() {
			return restCoBorrowerList;
		}

		public void setRestCoBorrowerList(List restCoBorrowerList) {
			this.restCoBorrowerList = restCoBorrowerList;
		}

		public String getLineCoBorrowerLiabIds() {
			return LineCoBorrowerLiabIds;
		}

		public void setLineCoBorrowerLiabIds(String lineCoBorrowerLiabIds) {
			LineCoBorrowerLiabIds = lineCoBorrowerLiabIds;
		}
		
	

}