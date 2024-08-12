package com.integrosys.cms.ui.manualinput.limit.covenant;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

public class LmtCovenantDetailForm extends TrxContextForm implements Serializable,ILmtCovenantConstants {
	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String facilityId;
	   private String covenantId;
	   public String getCovenantId() {
		return covenantId;
	}
	public void setCovenantId(String covenantId) {
		this.covenantId = covenantId;
	}

	private String lineNo;
	   private String covenantRequired;
	   private String countryRestriction;
	   private String drawerRestriction;
	   private String draweeRestriction;
	   private String beneRestriction;
	   private String combinedTenorRestriction;
	   private String runningAccountRestriction;
	   private String sellDownRestriction;
	   private String availDateRestriction;
	   private String moratoriumRestriction;
	   private String goodsRestriction;
	   private String currencyRestriction;
	   private String bankRestriction;
	   private String buyersRatingRestriction;
	   private String ecgcCoverRestriction;
	   private String countryRestrictionName;
	   private String countryRestrictionAmt;
	   private String countryRestrictioncurrency;
	   private String drawerName;
	   private String drawerAmount;
	   private String drawerCustName;
	   private String drawerCustId;
	   private String draweeName;
	   private String draweeAmount;
	   private String draweeCustName;
	   private String draweeCustId;
	   private String beneficiaryName;
	   private String beneficiaryAmount;
	   private String beneficiaryCustName;
	   private String beneficiaryCustId;
	   private String maxCombinedTenor;
	   private String preShipmentLinkage;
	   private String postShipmentLinkage;
	   private String runningAccount;
	   private String orderBackedByLC;
	   private String incoTerm;
	   private String incoMargin;
	   private String incoDescription;
	   private String moduleCode;
	   private String commitmentTenor;
	   private String sellDown;
	   private String lastAvailableDate;
	   private String moratorium;
	   private String emiFrequency;
	   private String noOfInsallments;
	   private String goodsRestrictionCode;
	   private String goodsRestrictionParentCode;
	   private String goodsRestrictionChildCode;
	   private String goodsRestrictionSubChildCode;
	   private String currencyRestrictionCode;
	   private String bankRestrictionCode;
	   private String bankRestrictionAmount;
	   private String currencyRestrictionAmount;
	   private String buyersRating;
	   private String agencyMasterCode;
	   private String ratingMasterCode;
	   private String ecgcCover;
	   public String getCountryListEmpty() {
		return countryListEmpty;
	}
	public void setCountryListEmpty(String countryListEmpty) {
		this.countryListEmpty = countryListEmpty;
	}
	public String getCurrencyListEmpty() {
		return currencyListEmpty;
	}
	public void setCurrencyListEmpty(String currencyListEmpty) {
		this.currencyListEmpty = currencyListEmpty;
	}
	public String getBankListEmpty() {
		return bankListEmpty;
	}
	public void setBankListEmpty(String bankListEmpty) {
		this.bankListEmpty = bankListEmpty;
	}
	public String getDrawerListEmpty() {
		return drawerListEmpty;
	}
	public void setDrawerListEmpty(String drawerListEmpty) {
		this.drawerListEmpty = drawerListEmpty;
	}
	public String getDraweeListEmpty() {
		return draweeListEmpty;
	}
	public void setDraweeListEmpty(String draweeListEmpty) {
		this.draweeListEmpty = draweeListEmpty;
	}
	public String getBeneListEmpty() {
		return beneListEmpty;
	}
	public void setBeneListEmpty(String beneListEmpty) {
		this.beneListEmpty = beneListEmpty;
	}

	private String countryListEmpty;
	   private String currencyListEmpty;
	   private String bankListEmpty;
	   private String drawerListEmpty;
	   private String draweeListEmpty;
	   private String beneListEmpty;
	   private String parentGoodsListEmpty;
	   
	   public String getFacilityId() {
			return facilityId;
		}
		public String getParentGoodsListEmpty() {
		return parentGoodsListEmpty;
	}
	public void setParentGoodsListEmpty(String parentGoodsListEmpty) {
		this.parentGoodsListEmpty = parentGoodsListEmpty;
	}
		public void setFacilityId(String facilityId) {
			this.facilityId = facilityId;
		}
		
		public String getLineNo() {
			return lineNo;
		}
		public void setLineNo(String lineNo) {
			this.lineNo = lineNo;
		}
	   
	   public String getCovenantRequired() {
			return covenantRequired;
		}
		public void setCovenantRequired(String covenantRequired) {
			this.covenantRequired = covenantRequired;
		}
		public String getCountryRestrictionName() {
			return countryRestrictionName;
		}
		public void setCountryRestrictionName(String countryRestrictionName) {
			this.countryRestrictionName = countryRestrictionName;
		}
		public String getCountryRestrictionAmt() {
			return countryRestrictionAmt;
		}
		public void setCountryRestrictionAmt(String countryRestrictionAmt) {
			this.countryRestrictionAmt = countryRestrictionAmt;
		}
		public String getCountryRestrictioncurrency() {
			return countryRestrictioncurrency;
		}
		public void setCountryRestrictioncurrency(String countryRestrictioncurrency) {
			this.countryRestrictioncurrency = countryRestrictioncurrency;
		}
		public String getDrawerName() {
			return drawerName;
		}
		public void setDrawerName(String drawerName) {
			this.drawerName = drawerName;
		}
		public String getDrawerId() {
			return drawerAmount;
		}
		public void setDrawerAmount(String drawerAmount) {
			this.drawerAmount = drawerAmount;
		}
		public String getDrawerCustName() {
			return drawerCustName;
		}
		public void setDrawerCustName(String drawerCustName) {
			this.drawerCustName = drawerCustName;
		}
		public String getDrawerCustId() {
			return drawerCustId;
		}
		public void setDrawerCustId(String drawerCustId) {
			this.drawerCustId = drawerCustId;
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
		public String getDraweeCustName() {
			return draweeCustName;
		}
		public void setDraweeCustName(String draweeCustName) {
			this.draweeCustName = draweeCustName;
		}
		public String getDraweeCustId() {
			return draweeCustId;
		}
		public void setDraweeCustId(String draweeCustId) {
			this.draweeCustId = draweeCustId;
		}
		public String getBeneficiaryName() {
			return beneficiaryName;
		}
		public void setBeneficiaryName(String beneficiaryName) {
			this.beneficiaryName = beneficiaryName;
		}
		public String getBeneficiaryAmount() {
			return beneficiaryAmount;
		}
		public void setBeneficiaryAmount(String beneficiaryAmount) {
			this.beneficiaryAmount = beneficiaryAmount;
		}
		public String getBeneficiaryCustName() {
			return beneficiaryCustName;
		}
		public void setBeneficiaryCustName(String beneficiaryCustName) {
			this.beneficiaryCustName = beneficiaryCustName;
		}
		public String getBeneficiaryCustId() {
			return beneficiaryCustId;
		}
		public void setBeneficiaryCustId(String beneficiaryCustId) {
			this.beneficiaryCustId = beneficiaryCustId;
		}
		public String getMaxCombinedTenor() {
			return maxCombinedTenor;
		}
		public void setMaxCombinedTenor(String maxCombinedTenor) {
			this.maxCombinedTenor = maxCombinedTenor;
		}
		public String getPreShipmentLinkage() {
			return preShipmentLinkage;
		}
		public void setPreShipmentLinkage(String preShipmentLinkage) {
			this.preShipmentLinkage = preShipmentLinkage;
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
		public String getOrderBackedByLC() {
			return orderBackedByLC;
		}
		public void setOrderBackedByLC(String orderBackedByLC) {
			this.orderBackedByLC = orderBackedByLC;
		}
		public String getIncoTerm() {
			return incoTerm;
		}
		public void setIncoTerm(String incoTerm) {
			this.incoTerm = incoTerm;
		}
		public String getIncoMargin() {
			return incoMargin;
		}
		public void setIncoMargin(String incoMargin) {
			this.incoMargin = incoMargin;
		}
		public String getIncoDescription() {
			return incoDescription;
		}
		public void setIncoDescription(String incoDescription) {
			this.incoDescription = incoDescription;
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
		public String getLastAvailableDate() {
			return lastAvailableDate;
		}
		public void setLastAvailableDate(String lastAvailableDate) {
			this.lastAvailableDate = lastAvailableDate;
		}
		public String getMoratorium() {
			return moratorium;
		}
		public void setMoratorium(String moratorium) {
			this.moratorium = moratorium;
		}
		public String getEmiFrequency() {
			return emiFrequency;
		}
		public void setEmiFrequency(String emiFrequency) {
			this.emiFrequency = emiFrequency;
		}
		public String getNoOfInsallments() {
			return noOfInsallments;
		}
		public void setNoOfInsallments(String noOfInsallments) {
			this.noOfInsallments = noOfInsallments;
		}
		public String getGoodsRestrictionCode() {
			return goodsRestrictionCode;
		}
		public void setGoodsRestrictionCode(String goodsRestrictionCode) {
			this.goodsRestrictionCode = goodsRestrictionCode;
		}
		public String getCurrencyRestrictionCode() {
			return currencyRestrictionCode;
		}
		public void setCurrencyRestrictionCode(String currencyRestrictionCode) {
			this.currencyRestrictionCode = currencyRestrictionCode;
		}
		public String getBankRestrictionCode() {
			return bankRestrictionCode;
		}
		public void setBankRestrictionCode(String bankRestrictionCode) {
			this.bankRestrictionCode = bankRestrictionCode;
		}
		public String getBuyersRating() {
			return buyersRating;
		}
		public void setBuyersRating(String buyersRating) {
			this.buyersRating = buyersRating;
		}
		public String getAgencyMasterCode() {
			return agencyMasterCode;
		}
		public void setAgencyMasterCode(String agencyMasterCode) {
			this.agencyMasterCode = agencyMasterCode;
		}
		
		public String getEcgcCover() {
			return ecgcCover;
		}
		public void setEcgcCover(String ecgcCover) {
			this.ecgcCover = ecgcCover;
		}
		   
		   public String getCountryRestriction() {
			return countryRestriction;
		}

		public void setCountryRestriction(String countryRestriction) {
			this.countryRestriction = countryRestriction;
		}

		public String getDrawerRestriction() {
			return drawerRestriction;
		}

		public void setDrawerRestriction(String drawerRestriction) {
			this.drawerRestriction = drawerRestriction;
		}

		public String getDraweeRestriction() {
			return draweeRestriction;
		}

		public void setDraweeRestriction(String draweeRestriction) {
			this.draweeRestriction = draweeRestriction;
		}

		public String getBeneRestriction() {
			return beneRestriction;
		}

		public void setBeneRestriction(String beneRestriction) {
			this.beneRestriction = beneRestriction;
		}

		public String getCombinedTenorRestriction() {
			return combinedTenorRestriction;
		}

		public void setCombinedTenorRestriction(String combinedTenorRestriction) {
			this.combinedTenorRestriction = combinedTenorRestriction;
		}

		public String getRunningAccountRestriction() {
			return runningAccountRestriction;
		}

		public void setRunningAccountRestriction(String runningAccountRestriction) {
			this.runningAccountRestriction = runningAccountRestriction;
		}

		public String getSellDownRestriction() {
			return sellDownRestriction;
		}

		public void setSellDownRestriction(String sellDownRestriction) {
			this.sellDownRestriction = sellDownRestriction;
		}

		public String getAvailDateRestriction() {
			return availDateRestriction;
		}

		public void setAvailDateRestriction(String availDateRestriction) {
			this.availDateRestriction = availDateRestriction;
		}

		public String getMoratoriumRestriction() {
			return moratoriumRestriction;
		}

		public void setMoratoriumRestriction(String moratoriumRestriction) {
			this.moratoriumRestriction = moratoriumRestriction;
		}

		public String getGoodsRestriction() {
			return goodsRestriction;
		}

		public void setGoodsRestriction(String goodsRestriction) {
			this.goodsRestriction = goodsRestriction;
		}

		public String getCurrencyRestriction() {
			return currencyRestriction;
		}

		public void setCurrencyRestriction(String currencyRestriction) {
			this.currencyRestriction = currencyRestriction;
		}

		public String getBankRestriction() {
			return bankRestriction;
		}

		public void setBankRestriction(String bankRestriction) {
			this.bankRestriction = bankRestriction;
		}

		public String getBuyersRatingRestriction() {
			return buyersRatingRestriction;
		}

		public void setBuyersRatingRestriction(String buyersRatingRestriction) {
			this.buyersRatingRestriction = buyersRatingRestriction;
		}

		public String getRatingMasterCode() {
			return ratingMasterCode;
		}

		public void setRatingMasterCode(String ratingMasterCode) {
			this.ratingMasterCode = ratingMasterCode;
		}

		public String getDrawerAmount() {
			return drawerAmount;
		}
		
		public String[][] getMapper() {
			String[][] input = { {COVENANT_DETAIL_FORM, COVENANT_DETAIL_MAPPER } };
			return input;
		}
		public String getEcgcCoverRestriction() {
			return ecgcCoverRestriction;
		}
		public void setEcgcCoverRestriction(String ecgcCoverRestriction) {
			this.ecgcCoverRestriction = ecgcCoverRestriction;
		}
		
		public String getBankRestrictionAmount() {
			return bankRestrictionAmount;
		}
		public void setBankRestrictionAmount(String bankRestrictionAmount) {
			this.bankRestrictionAmount = bankRestrictionAmount;
		}
		public String getCurrencyRestrictionAmount() {
			return currencyRestrictionAmount;
		}
		public void setCurrencyRestrictionAmount(String currencyRestrictionAmount) {
			this.currencyRestrictionAmount = currencyRestrictionAmount;
		}
		public String getGoodsRestrictionParentCode() {
			return goodsRestrictionParentCode;
		}
		public void setGoodsRestrictionParentCode(String goodsRestrictionParentCode) {
			this.goodsRestrictionParentCode = goodsRestrictionParentCode;
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
		
}
