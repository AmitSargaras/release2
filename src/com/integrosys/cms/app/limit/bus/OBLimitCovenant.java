package com.integrosys.cms.app.limit.bus;



import java.util.Comparator;
import java.util.Date;

import org.springframework.util.comparator.NullSafeComparator;

import com.integrosys.base.techinfra.util.AccessorUtil;

public class OBLimitCovenant implements ILimitCovenant {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long covenantId = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	private long facilityFK = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	public long getFacilityFK() {
		return facilityFK;
	}

	public void setFacilityFK(long facilityFK) {
		this.facilityFK = facilityFK;
	}



	private Long facilityId;
	//private String lineId;
	private String countryRestrictionReqd;
	public String getCovenantReqd() {
		return covenantReqd;
	}

	public void setCovenantReqd(String covenantReqd) {
		this.covenantReqd = covenantReqd;
	}



	private String covenantReqd;
	private String drawerReqd;
	private String draweeReqd;
	private String beneficiaryReqd;
	private String combinedTenorReqd;
	private String runningAccountReqd;
	private String sellDownReqd;
	private String lastAvailableDateReqd;
	private String moratoriumReqd;
	private String goodsRestrictionReqd;
	private String currencyRestrictionReqd;
	private String bankRestrictionReqd;
	private String buyersRatingReqd;
	private String ecgcCoverReqd;
	private String restrictedCountryname;
	private String restrictedAmount;
	private String drawerName;
	private String drawerAmount;
	private String drawerCustId;
	private String drawerCustName;
	private String draweeName;
	private String draweeAmount;
	private String draweeCustId;
	private String draweeCustName;
	private String beneName;
	private String beneAmount;
	private String beneCustId;
	private String beneCustName;
	private String maxCombinedTenor;
	private String preshipmentLinkage;
	private String postShipmentLinkage;
	private String runningAccount;
	private String orderBackedbylc;
	private String incoTerm;
	private String incoTermMarginPercent;
	private String incoTermDesc;
	private String moduleCode;
	private String commitmentTenor;
	private String sellDown;
	private Date lastAvailableDate;
	private String moratoriumPeriod ;
	private String emiFrequency;
	private String noOfInstallments;
	private String restrictedCurrency ;
	private String restrictedCurrencyAmount ;
	private String restrictedBankAmount;
	private String restrictedBank;
	private String buyersRating ;
	private String agencyMaster ;
	private String ratingMaster ;
	private String ecgcCover ;
	
	
	private String singleCovenantInd;
	private String goodsRestrictionInd;
	private String goodsRestrictionCode;
	private String goodsRestrictionParentCode;
	private String goodsRestrictionChildCode;
	private String goodsRestrictionSubChildCode;
	private String goodsRestrictionComboCode;
	
	private String restrictedCountryInd;
	private String restrictedBankInd;
	private String restrictedCurrencyInd;
	//private String restrictedBeneInd;
	
	private String drawerInd;
	private String draweeInd;
	private String beneInd;
	
	
	
	public long getCovenantId() {
		return covenantId;
	}
	
	private String isNewEntry;
	
	public String getIsNewEntry() {
		return isNewEntry;
	}

	public void setIsNewEntry(String isNewEntry) {
		this.isNewEntry = isNewEntry;
	}

	public void setCovenantId(long covenantId) {
		this.covenantId = covenantId;
	}

	public String getRestrictedCountryname() {
		return restrictedCountryname;
	}

	public void setRestrictedCountryname(String restrictedCountryname) {
		this.restrictedCountryname = restrictedCountryname;
	}

	public String getRestrictedAmount() {
		return restrictedAmount;
	}

	public void setRestrictedAmount(String restrictedAmount) {
		this.restrictedAmount = restrictedAmount;
	}

	public String getDrawerName() {
		return drawerName;
	}

	public void setDrawerName(String drawerName) {
		this.drawerName = drawerName;
	}

	public String getDrawerAmount() {
		return drawerAmount;
	}

	public void setDrawerAmount(String drawerAmount) {
		this.drawerAmount = drawerAmount;
	}

	public String getDrawerCustId() {
		return drawerCustId;
	}

	public void setDrawerCustId(String drawerCustId) {
		this.drawerCustId = drawerCustId;
	}

	public String getDrawerCustName() {
		return drawerCustName;
	}

	public void setDrawerCustName(String drawerCustName) {
		this.drawerCustName = drawerCustName;
	}

	public String getDraweeName() {
		return draweeName;
	}

	public void setDraweeName(String draweeName) {
		this.draweeName = draweeName;
	}

	public String getDraweeAmount() {
		return draweeAmount;
	}

	public void setDraweeAmount(String draweeAmount) {
		this.draweeAmount = draweeAmount;
	}

	public String getDraweeCustId() {
		return draweeCustId;
	}

	public void setDraweeCustId(String draweeCustId) {
		this.draweeCustId = draweeCustId;
	}

	public String getDraweeCustName() {
		return draweeCustName;
	}

	public void setDraweeCustName(String draweeCustName) {
		this.draweeCustName = draweeCustName;
	}

	public String getBeneName() {
		return beneName;
	}

	public void setBeneName(String beneName) {
		this.beneName = beneName;
	}

	public String getBeneAmount() {
		return beneAmount;
	}

	public void setBeneAmount(String beneAmount) {
		this.beneAmount = beneAmount;
	}

	public String getBeneCustId() {
		return beneCustId;
	}

	public void setBeneCustId(String beneCustId) {
		this.beneCustId = beneCustId;
	}

	public String getBeneCustName() {
		return beneCustName;
	}

	public void setBeneCustName(String beneCustName) {
		this.beneCustName = beneCustName;
	}

	public String getMaxCombinedTenor() {
		return maxCombinedTenor;
	}

	public void setMaxCombinedTenor(String maxCombinedTenor) {
		this.maxCombinedTenor = maxCombinedTenor;
	}

	public String getPreshipmentLinkage() {
		return preshipmentLinkage;
	}

	public void setPreshipmentLinkage(String preshipmentLinkage) {
		this.preshipmentLinkage = preshipmentLinkage;
	}

	public String getPostShipmentLinkage() {
		return postShipmentLinkage;
	}

	public void setPostShipmentLinkage(String postShipmentLinkage) {
		this.postShipmentLinkage = postShipmentLinkage;
	}

	public String getRunningAccount() {
		return runningAccount;
	}

	public void setRunningAccount(String runningAccount) {
		this.runningAccount = runningAccount;
	}

	public String getOrderBackedbylc() {
		return orderBackedbylc;
	}

	public void setOrderBackedbylc(String orderBackedbylc) {
		this.orderBackedbylc = orderBackedbylc;
	}

	public String getIncoTerm() {
		return incoTerm;
	}

	public void setIncoTerm(String incoTerm) {
		this.incoTerm = incoTerm;
	}

	public String getIncoTermMarginPercent() {
		return incoTermMarginPercent;
	}

	public void setIncoTermMarginPercent(String incoTermMarginPercent) {
		this.incoTermMarginPercent = incoTermMarginPercent;
	}

	public String getIncoTermDesc() {
		return incoTermDesc;
	}

	public void setIncoTermDesc(String incoTermDesc) {
		this.incoTermDesc = incoTermDesc;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getCommitmentTenor() {
		return commitmentTenor;
	}

	public void setCommitmentTenor(String commitmentTenor) {
		this.commitmentTenor = commitmentTenor;
	}

	public String getSellDown() {
		return sellDown;
	}

	public void setSellDown(String sellDown) {
		this.sellDown = sellDown;
	}

	public Date getLastAvailableDate() {
		return lastAvailableDate;
	}

	public void setLastAvailableDate(Date lastAvailableDate) {
		this.lastAvailableDate = lastAvailableDate;
	}

	public String getMoratoriumPeriod() {
		return moratoriumPeriod;
	}

	public void setMoratoriumPeriod(String moratoriumPeriod) {
		this.moratoriumPeriod = moratoriumPeriod;
	}

	public String getEmiFrequency() {
		return emiFrequency;
	}

	public void setEmiFrequency(String emiFrequency) {
		this.emiFrequency = emiFrequency;
	}

	public String getNoOfInstallments() {
		return noOfInstallments;
	}

	public void setNoOfInstallments(String noOfInstallments) {
		this.noOfInstallments = noOfInstallments;
	}

	public String getRestrictedCurrency() {
		return restrictedCurrency;
	}

	public void setRestrictedCurrency(String restrictedCurrency) {
		this.restrictedCurrency = restrictedCurrency;
	}

	public String getRestrictedBank() {
		return restrictedBank;
	}

	public void setRestrictedBank(String restrictedBank) {
		this.restrictedBank = restrictedBank;
	}

	public String getBuyersRating() {
		return buyersRating;
	}

	public void setBuyersRating(String buyersRating) {
		this.buyersRating = buyersRating;
	}

	public String getAgencyMaster() {
		return agencyMaster;
	}

	public void setAgencyMaster(String agencyMaster) {
		this.agencyMaster = agencyMaster;
	}

	public String getRatingMaster() {
		return ratingMaster;
	}

	public void setRatingMaster(String ratingMaster) {
		this.ratingMaster = ratingMaster;
	}

	public String getEcgcCover() {
		return ecgcCover;
	}

	public void setEcgcCover(String ecgcCover) {
		this.ecgcCover = ecgcCover;
	}

	
	public OBLimitCovenant() {}
	
	public OBLimitCovenant(ILimitCovenant value) {
		this();
		AccessorUtil.copyValue(value, this);
	}
	

	/*public long getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(long facilityId) {
		this.facilityId = facilityId;
	}*/

	/*public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}*/

	public String getCountryRestrictionReqd() {
		return countryRestrictionReqd;
	}

	public void setCountryRestrictionReqd(String countryRestrictionReqd) {
		this.countryRestrictionReqd = countryRestrictionReqd;
	}

	public String getDrawerReqd() {
		return drawerReqd;
	}

	public void setDrawerReqd(String drawerReqd) {
		this.drawerReqd = drawerReqd;
	}

	public String getDraweeReqd() {
		return draweeReqd;
	}

	public void setDraweeReqd(String draweeReqd) {
		this.draweeReqd = draweeReqd;
	}

	public String getBeneficiaryReqd() {
		return beneficiaryReqd;
	}

	public void setBeneficiaryReqd(String beneficiaryReqd) {
		this.beneficiaryReqd = beneficiaryReqd;
	}

	public String getCombinedTenorReqd() {
		return combinedTenorReqd;
	}

	public void setCombinedTenorReqd(String combinedTenorReqd) {
		this.combinedTenorReqd = combinedTenorReqd;
	}

	public String getRunningAccountReqd() {
		return runningAccountReqd;
	}

	public void setRunningAccountReqd(String runningAccountReqd) {
		this.runningAccountReqd = runningAccountReqd;
	}

	public String getSellDownReqd() {
		return sellDownReqd;
	}

	public void setSellDownReqd(String sellDownReqd) {
		this.sellDownReqd = sellDownReqd;
	}

	public String getLastAvailableDateReqd() {
		return lastAvailableDateReqd;
	}

	public void setLastAvailableDateReqd(String lastAvailableDateReqd) {
		this.lastAvailableDateReqd = lastAvailableDateReqd;
	}

	public String getMoratoriumReqd() {
		return moratoriumReqd;
	}

	public void setMoratoriumReqd(String moratoriumReqd) {
		this.moratoriumReqd = moratoriumReqd;
	}

	public String getGoodsRestrictionReqd() {
		return goodsRestrictionReqd;
	}

	public void setGoodsRestrictionReqd(String goodsRestrictionReqd) {
		this.goodsRestrictionReqd = goodsRestrictionReqd;
	}

	public String getCurrencyRestrictionReqd() {
		return currencyRestrictionReqd;
	}

	public void setCurrencyRestrictionReqd(String currencyRestrictionReqd) {
		this.currencyRestrictionReqd = currencyRestrictionReqd;
	}

	public String getBankRestrictionReqd() {
		return bankRestrictionReqd;
	}

	public void setBankRestrictionReqd(String bankRestrictionReqd) {
		this.bankRestrictionReqd = bankRestrictionReqd;
	}

	public String getBuyersRatingReqd() {
		return buyersRatingReqd;
	}

	public void setBuyersRatingReqd(String buyersRatingReqd) {
		this.buyersRatingReqd = buyersRatingReqd;
	}

	public String getEcgcCoverReqd() {
		return ecgcCoverReqd;
	}

	public void setEcgcCoverReqd(String ecgcCoverReqd) {
		this.ecgcCoverReqd = ecgcCoverReqd;
	}
	
	
	
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/*public Long getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(Long facilityId) {
		this.facilityId = facilityId;
	}*/

	public String getRestrictedCurrencyAmount() {
		return restrictedCurrencyAmount;
	}

	public void setRestrictedCurrencyAmount(String restrictedCurrencyAmount) {
		this.restrictedCurrencyAmount = restrictedCurrencyAmount;
	}

	public String getRestrictedBankAmount() {
		return restrictedBankAmount;
	}

	public void setRestrictedBankAmount(String restrictedBankAmount) {
		this.restrictedBankAmount = restrictedBankAmount;
	}

	public String getGoodsRestrictionInd() {
		return goodsRestrictionInd;
	}

	public void setGoodsRestrictionInd(String goodsRestrictionInd) {
		this.goodsRestrictionInd = goodsRestrictionInd;
	}

	public String getGoodsRestrictionCode() {
		return goodsRestrictionCode;
	}

	public void setGoodsRestrictionCode(String goodsRestrictionCode) {
		this.goodsRestrictionCode = goodsRestrictionCode;
	}

	public String getGoodsRestrictionParentCode() {
		return goodsRestrictionParentCode;
	}

	public void setGoodsRestrictionParentCode(String goodsRestrictionParentCode) {
		this.goodsRestrictionParentCode = goodsRestrictionParentCode;
	}
	
	public String getSingleCovenantInd() {
		return singleCovenantInd;
	}

	public void setSingleCovenantInd(String singleCovenantInd) {
		this.singleCovenantInd = singleCovenantInd;
	}

	public String getRestrictedCountryInd() {
		return restrictedCountryInd;
	}

	public void setRestrictedCountryInd(String restrictedCountryInd) {
		this.restrictedCountryInd = restrictedCountryInd;
	}

	public String getRestrictedBankInd() {
		return restrictedBankInd;
	}

	public void setRestrictedBankInd(String restrictedBankInd) {
		this.restrictedBankInd = restrictedBankInd;
	}

	public String getRestrictedCurrencyInd() {
		return restrictedCurrencyInd;
	}

	public void setRestrictedCurrencyInd(String restrictedCurrencyInd) {
		this.restrictedCurrencyInd = restrictedCurrencyInd;
	}

	public String getDrawerInd() {
		return drawerInd;
	}

	public void setDrawerInd(String drawerInd) {
		this.drawerInd = drawerInd;
	}

	public String getDraweeInd() {
		return draweeInd;
	}

	public void setDraweeInd(String draweeInd) {
		this.draweeInd = draweeInd;
	}

	public String getBeneInd() {
		return beneInd;
	}

	public void setBeneInd(String beneInd) {
		this.beneInd = beneInd;
	}

	public String getGoodsRestrictionChildCode() {
		return goodsRestrictionChildCode;
	}

	public void setGoodsRestrictionChildCode(String goodsRestrictionChildCode) {
		this.goodsRestrictionChildCode = goodsRestrictionChildCode;
	}

	public String getGoodsRestrictionSubChildCode() {
		return goodsRestrictionSubChildCode;
	}

	public void setGoodsRestrictionSubChildCode(String goodsRestrictionSubChildCode) {
		this.goodsRestrictionSubChildCode = goodsRestrictionSubChildCode;
	}

	public String getGoodsRestrictionComboCode() {
		return goodsRestrictionComboCode;
	}

	public void setGoodsRestrictionComboCode(String goodsRestrictionComboCode) {
		this.goodsRestrictionComboCode = goodsRestrictionComboCode;
	}

	public static class Comparators {
        public static Comparator<ILimitCovenant> GOODS_RESTRICTION_COMPARATOR = new Comparator<ILimitCovenant>() {
            @Override
            public int compare(ILimitCovenant l1, ILimitCovenant l2) {
            	return NullSafeComparator.NULLS_HIGH.compare(l1.getGoodsRestrictionComboCode(), l2.getGoodsRestrictionComboCode());
            }
        };
        
    }

	
}
