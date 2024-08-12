/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/OBCustomerSysXRef.java,v 1.2 2003/08/22 11:13:26 sathish Exp $
 */
package com.integrosys.cms.app.customer.bus;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;

/**
 * This class represents a Customer system cross-reference to a host system.
 * 
 * @author $Author: sathish $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/22 11:13:26 $ Tag: $Name: $
 */
public class OBCustomerSysXRef implements ICustomerSysXRef {
	private long _id = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String _ref = null;

	private String _bookingLoc = null;

	private String _systemCode = null;

	private String _customerID = null;

	private String _customerName = null;

	private String _accountNo = null;

	private String _accountStatus = null;

	private String externalSysCountry;

	private Boolean accountDelinq;

	private Date acctEffectiveDate;

	private String accountStatus;

	private String externalSystemCodeNum;

	private String bookingLoctnCountry;

	private String bookingLoctnOrganisation;

	private Amount rVForAccount;
	
	private Date lastAllocationDate;
	
	private Amount collateralAllocatedAmt;
	
	private Amount outstandingAmt;

    private String externalAccountType;
    
    
    //added by Shiv
    private String serialNo;

	private String interestRateType;
	
	private String intRateFloatingType;
	
	private String intRateFloatingRange;
	
	private String intRateFloatingMarginFlag;
	
	private String intRateFloatingMargin;
	
	private String releasedAmount;
	
	private String utilizedAmount;
	
	private Date releaseDate;
	
	private Date intradayLimitExpiryDate;
		
	private String dayLightLimit;
	
	private Date dateOfReset;
	
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
	
	private String updatedBy;

	private Date createdOn;
	
	private Date updatedOn;
	
	
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
	
	private String scmFlag;
	
	private String vendorDtls;
	
	private String closeFlag;
	
	private Date lastavailableDate;
	
	private Date uploadDate;
	
	private String segment;
	
	private String ruleId;
	
	private String uncondiCancl;
	
	
	
	
	//Start Santosh UBS_LIMIT CR
		private String limitRestrictionFlag;
		
		private String branchAllowedFlag;
		
		private String productAllowedFlag;
		
		private String currencyAllowedFlag;
		
		private String isCapitalMarketExposerFlag;
		
		private String segment1Flag;
			
		private String estateTypeFlag;
			
		private String prioritySectorFlag;
		
	

		private String uncondiCanclFlag;
		
		private String udfDelete;		
		
		private String isDayLightLimitAvailable;
		
		private String dayLightLimitApproved;
		
		private String stockLimitFlag;
		
		private String projectFinance;
		
		private String projectLoan;
		
		private String infaFlag;
		
		private String escod_l1;
		
		private String escod_l2;
		
		private String escod_l3;
		
		private String revised_escod_l1;
		
		private String revised_escod_l2;
		
		private String revised_escod_l3;
		
		private String acod;
		
		private String delay_level;
		
		private String ext_asset_class;
		
		private String rev_ext_asset_class_date_L1;
		
		private String rev_ext_asset_class_date_L2;
		
		private String rev_ext_asset_class_date_L3;
		
		private String scod;
		
		private String rev_asset_class_date;
		
		private String rev_asset_class;
		
		private String rev_asset_class_L1;
		
		private String rev_asset_class_L2;
		
		private String rev_asset_class_L3;
		
		private String ext_asset_class_date;
		
		
		
		private String tenure;
		
		private String sellDownPeriod;
		
		private String liabilityId;
		
		private String limitRemarks;
		
		private String specovenanta;
		
		private String drawername;
		
		private String draweename;
		
		private String benename;
		
		private String maxcombinedtenor;
		
		private String runningaccount;
		
		private String selldown;
		
		private String lastavailabledate;
		
		private String moratoriumperiod;
		
		private String restrictedgoodscode;
		
		private String restrictedcurrency;
		
		private String restrictedbank;
		
		private String buyersrating;
		
		private String ecgccover;
		
		private String restrictedamount;
		
		private String draweramount;
		
		private String draweeamount;
		
		private String beneamount;
		
		private String preshipmentlinkage;
		
		private String orderbackedbylc;
		
		private String emifrequency;
		
		private String agencymaster;
		
		private String drawercustid;
		
		private String draweecustid;
		
		private String benecustid;
		
		private String postshipmentlinkage;
		
		private String incoterm;
		
		private String noofinstallments;
		
		private String ratingmaster;
		
		private String drawercustname;
		
		private String draweecustname;
		
		private String benecustname;
		
		private String modulecode;
		
		private String countryrest_req;
		
		private String drawer_req;
		
		private String drawee_req;
		
		private String beneficiary_req;
		
		private String combinedtenor_req;
		
		private String runningaccount_req;
		
		private String selldown_req;
		
		private String lastavailabledt_req;
		
		private String moratorium_req;
		
		private String goodsrest_req;
		
		private String currencyrest_req;
		
		private String bankrest_req;
		
		private String buyersrating_req;
		
		private String ecgccover_req;
		
		
		
		private String stockDocMonth;
		
		private String stockDocYear;
		
		private String facilityId;
		
		private String stockDocMonthForLmt;
		
		private String stockDocYearForLmt;
		
		private String checkerIdNew;
		
		
		
		
		public String getCheckerIdNew() {
			return checkerIdNew;
		}

		public void setCheckerIdNew(String checkerIdNew) {
			this.checkerIdNew = checkerIdNew;
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

		public String getFacilityId() {
			return facilityId;
		}

		public void setFacilityId(String facilityId) {
			this.facilityId = facilityId;
		}

		public String getStockDocMonth() {
			return stockDocMonth;
		}

		public void setStockDocMonth(String stockDocMonth) {
			this.stockDocMonth = stockDocMonth;
		}

		public String getStockDocYear() {
			return stockDocYear;
		}

		public void setStockDocYear(String stockDocYear) {
			this.stockDocYear = stockDocYear;
		}

		
		private String bankingArrangement;

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

	public String getScmFlag() {
		return scmFlag;
	}

	public void setScmFlag(String scmFlag) {
		this.scmFlag = scmFlag;
	}
	
	public String getVendorDtls() {
		return vendorDtls;
	}

	public void setVendorDtls(String vendorDtls) {
		this.vendorDtls = vendorDtls;
	}
	
	public String getCloseFlag() {
		return closeFlag;
	}

	public void setCloseFlag(String closeFlag) {
		this.closeFlag = closeFlag;
	}

	public Date getLastavailableDate() {
		return lastavailableDate;
	}

	public void setLastavailableDate(Date lastavailableDate) {
		this.lastavailableDate = lastavailableDate;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}
	
	public String getCountry_Rest_Req() {
		return countryrest_req;
	}

	public void setCountry_Rest_Req(String countryrest_req) {
		this.countryrest_req = countryrest_req;
	}
	
	public String getDrawer_Req() {
		return drawer_req;
	}

	public void setDrawer_Req(String drawer_req) {
		this.drawer_req = drawer_req;
	}
	
	public String getDrawee_Req() {
		return drawee_req;
	}

	public void setDrawee_Req(String drawee_req) {
		this.drawee_req = drawee_req;
	}
	
	public String getBeneficiary_Req() {
		return beneficiary_req;
	}

	public void setBeneficiary_Req(String beneficiary_req) {
		this.beneficiary_req = beneficiary_req;
	}
	
	public String getCombined_Tenor_Req() {
		return combinedtenor_req;
	}

	public void setCombined_Tenor_Req(String combinedtenor_req) {
		this.combinedtenor_req = combinedtenor_req;
	}
	
	public String getRunning_Account_Req() {
		return runningaccount_req;
	}

	public void setRunning_Account_Req(String runningaccount_req) {
		this.runningaccount_req = runningaccount_req;
	}
	
	public String getSell_Down_Req() {
		return selldown_req;
	}

	public void setSell_Down_Req(String selldown_req) {
		this.selldown_req = selldown_req;
	}
	
	public String getLast_Available_Date_Req() {
		return lastavailabledt_req;
	}

	public void setLast_Available_Date_Req(String lastavailabledt_req) {
		this.lastavailabledt_req = lastavailabledt_req;
	}
	
	public String getMoratorium_Req() {
		return moratorium_req;
	}

	public void setMoratorium_Req(String moratorium_req) {
		this.moratorium_req = moratorium_req;
	}
	
	public String getGoods_Rest_Req() {
		return goodsrest_req;
	}

	public void setGoods_Rest_Req(String goodsrest_req) {
		this.goodsrest_req = goodsrest_req;
	}
	
	public String getCurrency_Rest_Req() {
		return currencyrest_req;
	}

	public void setCurrency_Rest_Req(String currencyrest_req) {
		this.currencyrest_req = currencyrest_req;
	}
	
	public String getBank_Rest_Req() {
		return bankrest_req;
	}

	public void setBank_Rest_Req(String bankrest_req) {
		this.bankrest_req = bankrest_req;
	}
	
	public String getBuyers_Rating_Req() {
		return buyersrating_req;
	}

	public void setBuyers_Rating_Req(String buyersrating_req) {
		this.buyersrating_req = buyersrating_req;
	}
	
	public String getEcgc_Cover_Req() {
		return ecgccover_req;
	}

	public void setEcgc_Cover_Req(String ecgccover_req) {
		this.ecgccover_req = ecgccover_req;
	}
	
	public String getSpeCov() {
		return specovenanta;
	}

	public void setSpeCov(String specovenanta) {
		this.specovenanta = specovenanta;
	}
	
	public String getDrawerName() {
		return drawername;
	}

	public void setDrawerName(String drawername) {
		this.drawername = drawername;
	}
	
	public String getDraweeName() {
		return draweename;
	}

	public void setDraweeName(String draweename) {
		this.draweename = draweename;
	}
	
	public String getBeneName() {
		return benename;
	}

	public void setBeneName(String benename) {
		this.benename = benename;
	}
	
	public String getMaxCombinedTenor() {
		return maxcombinedtenor;
	}

	public void setMaxCombinedTenor(String maxcombinedtenor) {
		this.maxcombinedtenor = maxcombinedtenor;
	}
	
	public String getRunningAccount() {
		return runningaccount;
	}

	public void setRunningAccount(String runningaccount) {
		this.runningaccount = runningaccount;
	}
	
	public String getSellDown() {
		return selldown;
	}

	public void setSellDown(String selldown) {
		this.selldown = selldown;
	}
	
	public String getLastAvailableDate() {
		return lastavailabledate;
	}

	public void setLastAvailableDate(String lastavailabledate) {
		this.lastavailabledate = lastavailabledate;
	}
	
	public String getMoratoriumPeriod() {
		return moratoriumperiod;
	}

	public void setMoratoriumPeriod(String moratoriumperiod) {
		this.moratoriumperiod = moratoriumperiod;
	}
	
	public String getRestrictedGoodsCode() {
		return restrictedgoodscode;
	}

	public void setRestrictedGoodsCode(String restrictedgoodscode) {
		this.restrictedgoodscode = restrictedgoodscode;
	}
	
	public String getRestrictedCurrency() {
		return restrictedcurrency;
	}

	public void setRestrictedCurrency(String restrictedcurrency) {
		this.restrictedcurrency = restrictedcurrency;
	}
	
	public String getRestrictedBank() {
		return restrictedbank;
	}

	public void setRestrictedBank(String restrictedbank) {
		this.restrictedbank = restrictedbank;
	}
	
	public String getBuyersRating() {
		return buyersrating;
	}

	public void setBuyersRating(String buyersrating) {
		this.buyersrating = buyersrating;
	}
	
	public String getEcgcCover() {
		return ecgccover;
	}

	public void setEcgcCoverg(String ecgccover) {
		this.ecgccover = ecgccover;
	}
	
	public String getRestrictedAmount() {
		return restrictedamount;
	}

	public void setRestrictedAmount(String restrictedamount) {
		this.restrictedamount = restrictedamount;
	}
	
	public String getDrawerAmount() {
		return draweramount;
	}

	public void setDrawerAmount(String draweramount) {
		this.draweramount = draweramount;
	}
	
	public String getDraweeAmount() {
		return draweeamount;
	}

	public void setDraweeAmount(String draweeamount) {
		this.draweeamount = draweeamount;
	}
	
	public String getBeneAmount() {
		return beneamount;
	}

	public void setBeneAmount(String beneamount) {
		this.beneamount = beneamount;
	}
	
	public String getPreShipmentLinkage() {
		return preshipmentlinkage;
	}

	public void setPreShipmentLinkage(String preshipmentlinkage) {
		this.preshipmentlinkage = preshipmentlinkage;
	}
	
	public String getOrderbAckedByLc() {
		return orderbackedbylc;
	}

	public void setOrderbAckedByLc(String orderbackedbylc) {
		this.orderbackedbylc = orderbackedbylc;
	}
	
	public String getEmiFrequency() {
		return emifrequency;
	}

	public void setEmiFrequency(String emifrequency) {
		this.emifrequency = emifrequency;
	}
	
	public String getAgencyMaster() {
		return agencymaster;
	}

	public void setAgencyMaster(String agencymaster) {
		this.agencymaster = agencymaster;
	}
	
	public String getDrawerCustId() {
		return drawercustid;
	}

	public void setDrawerCustId(String drawercustid) {
		this.drawercustid = drawercustid;
	}
	
	public String getDraweeCustId() {
		return draweecustid;
	}

	public void setDraweeCustId(String draweecustid) {
		this.draweecustid = draweecustid;
	}
	
	public String getBeneCustId() {
		return benecustid;
	}

	public void setBeneCustId(String benecustid) {
		this.benecustid = benecustid;
	}
	
	public String getPostShipmentLinkage() {
		return postshipmentlinkage;
	}

	public void setPostShipmentLinkage(String postshipmentlinkage) {
		this.postshipmentlinkage = postshipmentlinkage;
	}
	
	public String getIncoTerm() {
		return incoterm;
	}

	public void setIncoTerm(String incoterm) {
		this.incoterm = incoterm;
	}
	
	public String getNoOfInstallments() {
		return noofinstallments;
	}

	public void setNoOfInstallments(String noofinstallments) {
		this.noofinstallments = noofinstallments;
	}
	
	public String getRatingMaster() {
		return ratingmaster;
	}

	public void setRatingMaster(String ratingmaster) {
		this.ratingmaster = ratingmaster;
	}
	
	public String getDrawerCustName() {
		return drawercustname;
	}

	public void setDrawerCustName(String drawercustname) {
		this.drawercustname = drawercustname;
	}
	
	public String getDraweeCustName() {
		return draweecustname;
	}

	public void setDraweeCustName(String draweecustname) {
		this.draweecustname = draweecustname;
	}
	
	public String getBeneCustName() {
		return benecustname;
	}

	public void setBeneCustName(String benecustname) {
		this.benecustname = benecustname;
	}
	
	public String getModuleCode() {
		return modulecode;
	}

	public void setModuleCode(String modulecode) {
		this.modulecode = modulecode;
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

	public String getUdfAllowed() {
		return udfAllowed;
	}

	public void setUdfAllowed(String udfAllowed) {
		this.udfAllowed = udfAllowed;
	}



	
	
	private String limitTenorDays;
	
	private String limitRestriction;
	
	private String branchAllowed;
	
	private String productAllowed;
	
	private String currencyAllowed;
	
	private String internalRemarks;
	
	
	
	private String coreStpRejectedReason;
	
	private String udfAllowed;
	
	
	private String sourceRefNo;
	
	private Date limitStartDate;
	
	private Date idlEffectiveFromDate;
	
	private Date idlExpiryDate;
	
	private String idlAmount;
	
	
	public Date getIdlEffectiveFromDate() {
		return idlEffectiveFromDate;
	}

	public void setIdlEffectiveFromDate(Date idlEffectiveFromDate) {
		this.idlEffectiveFromDate = idlEffectiveFromDate;
	}

	public Date getIdlExpiryDate() {
		return idlExpiryDate;
	}

	public void setIdlExpiryDate(Date idlExpiryDate) {
		this.idlExpiryDate = idlExpiryDate;
	}

	public String getIdlAmount() {
		return idlAmount;
	}

	public void setIdlAmount(String idlAmount) {
		this.idlAmount = idlAmount;
	}
	
	public String getSourceRefNo() {
		return sourceRefNo;
	}

	public void setSourceRefNo(String sourceRefNo) {
		this.sourceRefNo = sourceRefNo;
	}

	public Date getLimitStartDate() {
		return limitStartDate;
	}

	public void setLimitStartDate(Date limitStartDate) {
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

	private String action;
	
	private String status;
	
	
	
	

	//Code added for Upload Status Column
	private String uploadStatus;
	public String getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(String uploadStatus) {
		this.uploadStatus = uploadStatus;
	}

	public String getCommRealEstateType() {
		return commRealEstateType;
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

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	public void setIntradayLimitExpiryDate(Date intradayLimitExpiryDate) {
		this.intradayLimitExpiryDate = intradayLimitExpiryDate;
	}
	
	public Date getIntradayLimitExpiryDate() {
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

	public Date getDateOfReset() {
		return dateOfReset;
	}

	public void setDateOfReset(Date dateOfReset) {
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
	 * Default Constructor
	 */
	public OBCustomerSysXRef() {
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type ICustomerSysXRef
	 */
	public OBCustomerSysXRef(ICustomerSysXRef value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	// Getters

	/**
	 * Get X-Ref primary key
	 * 
	 * @return long
	 */
	public long getXRefID() {
		return _id;
	}

	/**
	 * Get External System X-Ref ID
	 * 
	 * @return String
	 */
	public String getExternalXRef() {
		return _ref;
	}

	/**
	 * Get System Booking Location
	 * 
	 * @return String
	 */
	public String getBookingLocation() {
		return _bookingLoc;
	}

	/**
	 * Get External System Code
	 * 
	 * @return String
	 */
	public String getExternalSystemCode() {
		return _systemCode;
	}

	/**
	 * Get External System Customer ID
	 * 
	 * @return String
	 */
	public String getExternalCustomerID() {
		return _customerID;
	}

	/**
	 * Get External System Customer Name
	 * 
	 * @return String
	 */
	public String getExternalCustomerName() {
		return _customerName;
	}

	/**
	 * Get External System Account Number
	 * 
	 * @return String
	 */
	public String getExternalAccountNo() {
		return _accountNo;
	}

	/**
	 * Get External System Account Status
	 * 
	 * @return String
	 */
	public String getExternalAccountStatus() {
		return _accountStatus;
	}

	// Setters

	/**
	 * Set X-Ref primary key
	 * 
	 * @param value is of type long
	 */
	public void setXRefID(long value) {
		_id = value;
	}

	/**
	 * Set External System X-Ref ID
	 * 
	 * @param value is of type String
	 */
	public void setExternalXRef(String value) {
		_ref = value;
	}

	/**
	 * Set System Booking Location
	 * 
	 * @param value is of type String
	 */
	public void setBookingLocation(String value) {
		_bookingLoc = value;
	}

	/**
	 * Set External System Code
	 * 
	 * @param value is of type String
	 */
	public void setExternalSystemCode(String value) {
		_systemCode = value;
	}

	/**
	 * Set External System Customer ID
	 * 
	 * @param value is of type String
	 */
	public void setExternalCustomerID(String value) {
		_customerID = value;
	}

	/**
	 * Set External System Customer Name
	 * 
	 * @param value is of type String
	 */
	public void setExternalCustomerName(String value) {
		_customerName = value;
	}

	/**
	 * Set External System Account Number
	 * 
	 * @param value is of type String
	 */
	public void setExternalAccountNo(String value) {
		_accountNo = value;
	}

	/**
	 * Set External System Account Status
	 * 
	 * @param value is of type String
	 */
	public void setExternalAccountStatus(String value) {
		_accountStatus = value;
	}

	/**
	 * @return Returns the accountDelinq.
	 */
	public Boolean getAccountDelinq() {
		return accountDelinq;
	}

	/**
	 * @param accountDelinq The accountDelinq to set.
	 */
	public void setAccountDelinq(Boolean accountDelinq) {
		this.accountDelinq = accountDelinq;
	}

	/**
	 * @return Returns the accountStatus.
	 */
	public String getAccountStatus() {
		return accountStatus;
	}

	/**
	 * @param accountStatus The accountStatus to set.
	 */
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	/**
	 * @return Returns the acctEffectiveDate.
	 */
	public Date getAcctEffectiveDate() {
		return acctEffectiveDate;
	}

	/**
	 * @param acctEffectiveDate The acctEffectiveDate to set.
	 */
	public void setAcctEffectiveDate(Date acctEffectiveDate) {
		this.acctEffectiveDate = acctEffectiveDate;
	}

	/**
	 * @return Returns the externalSysCountry.
	 */
	public String getExternalSysCountry() {
		return externalSysCountry;
	}

	/**
	 * @param externalSysCountry The externalSysCountry to set.
	 */
	public void setExternalSysCountry(String externalSysCountry) {
		this.externalSysCountry = externalSysCountry;
	}

	/**
	 * @return Returns the externalSystemCodeNum.
	 */
	public String getExternalSystemCodeNum() {
		return externalSystemCodeNum;
	}

	/**
	 * @param externalSystemCodeNum The externalSystemCodeNum to set.
	 */
	public void setExternalSystemCodeNum(String externalSystemCodeNum) {
		this.externalSystemCodeNum = externalSystemCodeNum;
	}

	/**
	 * @return Return the BookingLoctnCountry
	 */
	public String getBookingLoctnCountry() {
		return this.bookingLoctnCountry;
	}

	/**
	 * @param bookingLoctnCountry the bookingLoctnCountry to set.
	 */
	public void setBookingLoctnCountry(String bookingLoctnCountry) {
		this.bookingLoctnCountry = bookingLoctnCountry;
	}

	/**
	 * @return Return the bookingLoctnOrganisation
	 */
	public String getBookingLoctnOrganisation() {
		return this.bookingLoctnOrganisation;
	}

	/**
	 * @param bookingLoctnOrganisation the bookingLoctnOrganisation to set.
	 */
	public void setBookingLoctnOrganisation(String bookingLoctnOrganisation) {
		this.bookingLoctnOrganisation = bookingLoctnOrganisation;
	}

	public Amount getRVForAccount() {
		return this.rVForAccount;
	}

	public void setRVForAccount(Amount amt) {
		this.rVForAccount = amt;
	}
	
	public Date getLastAllocationDate() {
		return lastAllocationDate;
	}

	public void setLastAllocationDate(Date lastAllocationDate) {
		this.lastAllocationDate = lastAllocationDate;
	}

	public Amount getCollateralAllocatedAmt() {
		return collateralAllocatedAmt;
	}

	public void setCollateralAllocatedAmt(Amount collateralAllocatedAmt) {
		this.collateralAllocatedAmt = collateralAllocatedAmt;
	}

	public Amount getOutstandingAmt() {
		return outstandingAmt;
	}

	public void setOutstandingAmt(Amount outstandingAmt) {
		this.outstandingAmt = outstandingAmt;
	}	

	/**
	 * Return a String representation of the object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

    public String getExternalAccountType() {
        return externalAccountType;
    }

    public void setExternalAccountType(String externalAccountType) {
        this.externalAccountType = externalAccountType;
    }

	public String getCreatedBy() {
		return createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
		
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setCreatedBy(String createdBy) {
		 this.createdBy = createdBy;
		
	}

	public void setCreatedOn(Date createdOn) {
		 this.createdOn = createdOn;
		
	}

	public void setUpdatedBy(String updatedBy) {
		 this.updatedBy = updatedBy;
		
	}

	public void setUpdatedOn(Date updatedOn) {
		 this.updatedOn = updatedOn;
		
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

	//added by santosh UBS LImit upload
		private ILimitXRefUdf[] XRefUdfData = null;
		
		public ILimitXRefUdf[] getXRefUdfData() {
			return XRefUdfData;
		}

		public void setXRefUdfData(ILimitXRefUdf[] xRefUdfData) {
			XRefUdfData = xRefUdfData;
		}
		//end santosh
       private ILimitXRefUdf2[] XRefUdfData2 = null;
		
		public ILimitXRefUdf2[] getXRefUdfData2() {
			return XRefUdfData2;
		}
		
private ILineCovenant[] lineCovenant = null;
		
		public ILineCovenant[] getLineCovenant() {
	return lineCovenant;
}

public void setLineCovenant(ILineCovenant[] lineCovenant) {
	this.lineCovenant = lineCovenant;
}

		

		public void setXRefUdfData2(ILimitXRefUdf2[] xRefUdfData2) {
			XRefUdfData2 = xRefUdfData2;
		}
		
		
        private ILimitXRefCoBorrower[] XRefCoBorrowerData = null;

        public ILimitXRefCoBorrower[] getXRefCoBorrowerData() {
			return XRefCoBorrowerData;
		}

		public void setXRefCoBorrowerData(ILimitXRefCoBorrower[] xRefCoBorrowerData) {
			XRefCoBorrowerData = xRefCoBorrowerData;
		}

		

		private String sendToCore;
		public String getSendToCore() {
			return sendToCore;
		}

		public void setSendToCore(String sendToCore) {
			this.sendToCore = sendToCore;
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
	
		public void setIsDayLightLimitAvailable(String isDayLightLimitAvailable) {
			this.isDayLightLimitAvailable = isDayLightLimitAvailable;
		}

		public String getDayLightLimitApproved() {
			return dayLightLimitApproved;
		}

		public void setDayLightLimitApproved(String dayLightLimitApproved) {
			this.dayLightLimitApproved = dayLightLimitApproved;
		}

		public String getProjectFinance() {
			return projectFinance;
		}

		public void setProjectFinance(String projectFinance) {
			this.projectFinance = projectFinance;
		}

		public String getProjectLoan() {
			return projectLoan;
		}

		public void setProjectLoan(String projectLoan) {
			this.projectLoan = projectLoan;
		}

		public String getInfaFlag() {
			return infaFlag;
		}

		public void setInfaFlag(String infaFlag) {
			this.infaFlag = infaFlag;
		}

		public String getEscod_l1() {
			return escod_l1;
		}

		public void setEscod_l1(String escod_l1) {
			this.escod_l1 = escod_l1;
		}

		public String getEscod_l2() {
			return escod_l2;
		}

		public void setEscod_l2(String escod_l2) {
			this.escod_l2 = escod_l2;
		}

		public String getEscod_l3() {
			return escod_l3;
		}

		public void setEscod_l3(String escod_l3) {
			this.escod_l3 = escod_l3;
		}

		public String getRevised_escod_l1() {
			return revised_escod_l1;
		}

		public void setRevised_escod_l1(String revised_escod_l1) {
			this.revised_escod_l1 = revised_escod_l1;
		}

		public String getRevised_escod_l2() {
			return revised_escod_l2;
		}

		public void setRevised_escod_l2(String revised_escod_l2) {
			this.revised_escod_l2 = revised_escod_l2;
		}

		public String getAcod() {
			return acod;
		}

		public void setAcod(String acod) {
			this.acod = acod;
		}

		public String getDelay_level() {
			return delay_level;
		}

		public void setDelay_level(String delay_level) {
			this.delay_level = delay_level;
		}

		public String getExt_asset_class() {
			return ext_asset_class;
		}

		public void setExt_asset_class(String ext_asset_class) {
			this.ext_asset_class = ext_asset_class;
		}

		public String getRev_ext_asset_class_date_L1() {
			return rev_ext_asset_class_date_L1;
		}

		public void setRev_ext_asset_class_date_L1(String rev_ext_asset_class_date_L1) {
			this.rev_ext_asset_class_date_L1 = rev_ext_asset_class_date_L1;
		}

		public String getRev_ext_asset_class_date_L2() {
			return rev_ext_asset_class_date_L2;
		}

		public void setRev_ext_asset_class_date_L2(String rev_ext_asset_class_date_L2) {
			this.rev_ext_asset_class_date_L2 = rev_ext_asset_class_date_L2;
		}

		public String getRev_ext_asset_class_date_L3() {
			return rev_ext_asset_class_date_L3;
		}

		public void setRev_ext_asset_class_date_L3(String rev_ext_asset_class_date_L3) {
			this.rev_ext_asset_class_date_L3 = rev_ext_asset_class_date_L3;
		}

		public String getScod() {
			return scod;
		}

		public void setScod(String scod) {
			this.scod = scod;
		}

		public String getRevised_escod_l3() {
			return revised_escod_l3;
		}

		public void setRevised_escod_l3(String revised_escod_l3) {
			this.revised_escod_l3 = revised_escod_l3;
		}

		public String getRev_asset_class_date() {
			return rev_asset_class_date;
		}

		public void setRev_asset_class_date(String rev_asset_class_date) {
			this.rev_asset_class_date = rev_asset_class_date;
		}
		
		public String getIsDayLightLimitAvailable() {
			return isDayLightLimitAvailable;
		}

		public String getRev_asset_class() {
			return rev_asset_class;
		}

		public void setRev_asset_class(String rev_asset_class) {
			this.rev_asset_class = rev_asset_class;
		}

		public String getRev_asset_class_L1() {
			return rev_asset_class_L1;
		}

		public void setRev_asset_class_L1(String rev_asset_class_L1) {
			this.rev_asset_class_L1 = rev_asset_class_L1;
		}

		public String getRev_asset_class_L2() {
			return rev_asset_class_L2;
		}

		public void setRev_asset_class_L2(String rev_asset_class_L2) {
			this.rev_asset_class_L2 = rev_asset_class_L2;
		}

		public String getRev_asset_class_L3() {
			return rev_asset_class_L3;
		}

		public void setRev_asset_class_L3(String rev_asset_class_L3) {
			this.rev_asset_class_L3 = rev_asset_class_L3;
		}

		public String getExt_asset_class_date() {
			return ext_asset_class_date;
		}

		public void setExt_asset_class_date(String ext_asset_class_date) {
			this.ext_asset_class_date = ext_asset_class_date;
		}


		public String getBankingArrangement() {
			return bankingArrangement;
		}

		public void setBankingArrangement(String bankingArrangement) {
			this.bankingArrangement = bankingArrangement;
			
		}

		// UDF2
		private String udfAllowed_2;

		public String getUdfAllowed_2() {
			return udfAllowed_2;
		}

		public void setUdfAllowed_2(String udfAllowed_2) {
			this.udfAllowed_2 = udfAllowed_2;
		}
		

		private String coBorrowerId_1;
		private String coBorrowerName_1;
		
		private String coBorrowerId_2;
		private String coBorrowerName_2;
		
		private String coBorrowerId_3;
		private String coBorrowerName_3;
		
		private String coBorrowerId_4;
		private String coBorrowerName_4;
		
		private String coBorrowerId_5;
		private String coBorrowerName_5;		
		private String adhocLine;

		public String getAdhocLine() {
			return adhocLine;
		}

		public void setAdhocLine(String adhocLine) {
			this.adhocLine = adhocLine;
		}
		/*public String getCoBorrowerName_2() {
			return coBorrowerName_2;
		}

		public void setCoBorrowerName_2(String coBorrowerName_2) {
			this.coBorrowerName_2 = coBorrowerName_2;
		}*/



		public String getCoBorrowerId_1() {
			return coBorrowerId_1;
		}

		public void setCoBorrowerId_1(String coBorrowerId_1) {
			this.coBorrowerId_1 = coBorrowerId_1;
		}

		public String getCoBorrowerName_1() {
			return coBorrowerName_1;
		}

		public void setCoBorrowerName_1(String coBorrowerName_1) {
			this.coBorrowerName_1 = coBorrowerName_1;
		}

		public String getCoBorrowerId_2() {
			return coBorrowerId_2;
		}

		public void setCoBorrowerId_2(String coBorrowerId_2) {
			this.coBorrowerId_2 = coBorrowerId_2;
		}

		public String getCoBorrowerName_2() {
			return coBorrowerName_2;
		}

		public void setCoBorrowerName_2(String coBorrowerName_2) {
			this.coBorrowerName_2 = coBorrowerName_2;
		}

		public String getCoBorrowerId_3() {
			return coBorrowerId_3;
		}

		public void setCoBorrowerId_3(String coBorrowerId_3) {
			this.coBorrowerId_3 = coBorrowerId_3;
		}

		public String getCoBorrowerName_3() {
			return coBorrowerName_3;
		}

		public void setCoBorrowerName_3(String coBorrowerName_3) {
			this.coBorrowerName_3 = coBorrowerName_3;
		}

		public String getCoBorrowerId_4() {
			return coBorrowerId_4;
		}

		public void setCoBorrowerId_4(String coBorrowerId_4) {
			this.coBorrowerId_4 = coBorrowerId_4;
		}

		public String getCoBorrowerName_4() {
			return coBorrowerName_4;
		}

		public void setCoBorrowerName_4(String coBorrowerName_4) {
			this.coBorrowerName_4 = coBorrowerName_4;
		}

		public String getCoBorrowerId_5() {
			return coBorrowerId_5;
		}

		public void setCoBorrowerId_5(String coBorrowerId_5) {
			this.coBorrowerId_5 = coBorrowerId_5;
		}

		public String getCoBorrowerName_5() {
			return coBorrowerName_5;
		}

		public void setCoBorrowerName_5(String coBorrowerName_5) {
			this.coBorrowerName_5 = coBorrowerName_5;
		}

	/*	public void setRestCoBorrowerList(List coBorrow) {
			// TODO Auto-generated method stub
			
		}*/
		
		private List restCoBorrowerList;

		public List getRestCoBorrowerList() {
			return restCoBorrowerList;
		}

		public void setRestCoBorrowerList(List restCoBorrowerList) {
			this.restCoBorrowerList = restCoBorrowerList;
		}
		
		
}