package com.integrosys.cms.app.limit.bus;

import java.util.Date;
import java.util.Set;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

public interface ILimitCovenant  extends java.io.Serializable {
	
	public long getCovenantId();

	public void setCovenantId(long covenantId);

	/*public String getLineId() ;

	public void setLineId(String lineId);*/
	
	/*public Long getFacilityId();

	public void setFacilityId(Long facilityId);*/
	
	public String getCovenantReqd();
	
	public void setCovenantReqd(String covenantReqd);
	
	public String getCountryRestrictionReqd() ;

	public void setCountryRestrictionReqd(String countryRestrictionReqd);

	public String getDrawerReqd();

	public void setDrawerReqd(String drawerReqd) ;

	public String getDraweeReqd() ;

	public void setDraweeReqd(String draweeReqd);

	public String getBeneficiaryReqd() ;

	public void setBeneficiaryReqd(String beneficiaryReqd);

	public String getCombinedTenorReqd() ;

	public void setCombinedTenorReqd(String combinedTenorReqd) ;

	public String getRunningAccountReqd() ;

	public void setRunningAccountReqd(String runningAccountReqd);

	public String getSellDownReqd() ;

	public void setSellDownReqd(String sellDownReqd) ;

	public String getLastAvailableDateReqd() ;

	public void setLastAvailableDateReqd(String lastAvailableDateReqd);

	public String getMoratoriumReqd();

	public void setMoratoriumReqd(String moratoriumReqd);

	public String getGoodsRestrictionReqd() ;

	public void setGoodsRestrictionReqd(String goodsRestrictionReqd) ;

	public String getCurrencyRestrictionReqd();

	public void setCurrencyRestrictionReqd(String currencyRestrictionReqd);

	public String getBankRestrictionReqd() ;

	public void setBankRestrictionReqd(String bankRestrictionReqd);

	public String getBuyersRatingReqd() ;

	public void setBuyersRatingReqd(String buyersRatingReqd);

	public String getEcgcCoverReqd() ;

	public void setEcgcCoverReqd(String ecgcCoverReqd);
	
	public long getFacilityFK();
	
	public void setFacilityFK(long facilityId) ;
	
	public String getRestrictedCountryname() ;

	public void setRestrictedCountryname(String restrictedCountryname);

	public String getRestrictedAmount() ;

	public void setRestrictedAmount(String restrictedAmount);

	public String getDrawerName() ;

	public void setDrawerName(String drawerName) ;

	public String getDrawerAmount() ;

	public void setDrawerAmount(String drawerAmount);

	public String getDrawerCustId();

	public void setDrawerCustId(String drawerCustId) ;

	public String getDrawerCustName();

	public void setDrawerCustName(String drawerCustName);

	public String getDraweeName();

	public void setDraweeName(String draweeName) ;

	public String getDraweeAmount() ;

	public void setDraweeAmount(String draweeAmount);

	public String getDraweeCustId() ;

	public void setDraweeCustId(String draweeCustId) ;

	public String getDraweeCustName();

	public void setDraweeCustName(String draweeCustName);

	public String getBeneName() ;

	public void setBeneName(String beneName);

	public String getBeneAmount();

	public void setBeneAmount(String beneAmount);

	public String getBeneCustId() ;

	public void setBeneCustId(String beneCustId);

	public String getBeneCustName() ;

	public void setBeneCustName(String beneCustName) ;

	public String getMaxCombinedTenor();

	public void setMaxCombinedTenor(String maxCombinedTenor);

	public String getPreshipmentLinkage() ;

	public void setPreshipmentLinkage(String preshipmentLinkage) ;

	public String getPostShipmentLinkage() ;

	public void setPostShipmentLinkage(String postShipmentLinkage) ;

	public String getRunningAccount() ;

	public void setRunningAccount(String runningAccount);

	public String getOrderBackedbylc() ;

	public void setOrderBackedbylc(String orderBackedbylc);

	public String getIncoTerm();

	public void setIncoTerm(String incoTerm);

	public String getIncoTermMarginPercent() ;

	public void setIncoTermMarginPercent(String incoTermMarginPercent);

	public String getIncoTermDesc();

	public void setIncoTermDesc(String incoTermDesc);

	public String getModuleCode() ;

	public void setModuleCode(String moduleCode) ;

	public String getCommitmentTenor() ;

	public void setCommitmentTenor(String commitmentTenor) ;

	public String getSellDown() ;

	public void setSellDown(String sellDown);

	public Date getLastAvailableDate() ;

	public void setLastAvailableDate(Date lastAvailableDate);

	public String getMoratoriumPeriod() ;

	public void setMoratoriumPeriod(String moratoriumPeriod);

	public String getEmiFrequency() ;

	public void setEmiFrequency(String emiFrequency) ;

	public String getNoOfInstallments() ;

	public void setNoOfInstallments(String noOfInstallments) ;

	public String getRestrictedCurrency() ;

	public void setRestrictedCurrency(String restrictedCurrency) ;

	public String getRestrictedBank() ;

	public void setRestrictedBank(String restrictedBank);

	public String getBuyersRating();

	public void setBuyersRating(String buyersRating);

	public String getAgencyMaster() ;

	public void setAgencyMaster(String agencyMaster);

	public String getRatingMaster();

	public void setRatingMaster(String ratingMaster);

	public String getEcgcCover() ;

	public void setEcgcCover(String ecgcCover);
	
	public String getRestrictedCurrencyAmount();
	
	public void setRestrictedCurrencyAmount(String restrictedCurrencyAmount) ;
	
	public String getRestrictedBankAmount();
	
	public void setRestrictedBankAmount(String restrictedBankAmount) ;

	public String getRestrictedCountryInd();

	public void setRestrictedCountryInd(String restrictedCountryInd);

	public String getRestrictedBankInd();

	public void setRestrictedBankInd(String restrictedBankInd);

	public String getRestrictedCurrencyInd();

	public void setRestrictedCurrencyInd(String restrictedCurrencyInd);

	public String getDrawerInd();

	public void setDrawerInd(String drawerInd);

	public String getDraweeInd();

	public void setDraweeInd(String draweeInd);

	public String getBeneInd();

	public void setBeneInd(String beneInd);

	public String getGoodsRestrictionInd();

	public void setGoodsRestrictionInd(String goodsRestrictionInd);

	public String getGoodsRestrictionCode();

	public void setGoodsRestrictionCode(String goodsRestrictionCode);

	public String getGoodsRestrictionParentCode();

	public void setGoodsRestrictionParentCode(String goodsRestrictionParentCode);
	
	public String getSingleCovenantInd();
	public void setSingleCovenantInd(String singleCovenantInd);
	
	public String getGoodsRestrictionChildCode();
	public void setGoodsRestrictionChildCode(String goodsRestrictionChildCode);

	public String getGoodsRestrictionSubChildCode();
	public void setGoodsRestrictionSubChildCode(String goodsRestrictionSubChildCode);

	public String getGoodsRestrictionComboCode();
	public void setGoodsRestrictionComboCode(String goodsRestrictionComboCode);

	public String getIsNewEntry();
	public void setIsNewEntry(String isNewEntry) ;

}
