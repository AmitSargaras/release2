/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/ICollateral.java,v 1.29 2006/07/27 04:36:03 jzhan Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.sharesecurity.bus.IShareSecurity;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;

/**
 * This interface represents a Collateral entity.
 * 
 * @author $Author: jzhan $<br>
 * @version $Revision: 1.29 $
 * @since $Date: 2006/07/27 04:36:03 $ Tag: $Name: $
 */
public interface ICollateral extends Serializable {

	/**
	 * To indicate the usage of charge information of the limit security linkage
	 * is Percentage (%)
	 */
	public final static Character CHARGE_INFO_PERCENTAGE_USAGE = new Character('P');

	/**
	 * To indicate the usage of charge information of the limit security linkage
	 * is Amount ($)
	 */
	public final static Character CHARGE_INFO_AMOUNT_USAGE = new Character('A');

	public Character getChargeInfoDrawAmountUsageIndicator();

	public Character getChargeInfoPledgeAmountUsageIndicator();

	/**
	 * Get latest current market value.
	 * 
	 * @return Amount
	 */
	public Amount getCMV();

	/**
	 * Get currency code for current market value.
	 * 
	 * @return String
	 */
	public String getCMVCcyCode();

	/**
	 * Get Security custodian.
	 * 
	 * @return String
	 */
	public String getCollateralCustodian();

	/**
	 * Get security custodian type, internal or external.
	 * 
	 * @return String
	 */
	public String getCollateralCustodianType();

	/**
	 * Get collateral ID.
	 * 
	 * @return long
	 */
	public long getCollateralID();

	/**
	 * Get limits associated to this collateral.
	 * 
	 * @return ICollateralLimitMap[]
	 */
	public ICollateralLimitMap[] getCollateralLimits();

	/**
	 * Get security location.
	 * 
	 * @return String
	 */
	public String getCollateralLocation();

	/**
	 * Get security maturity date.
	 * 
	 * @return Date
	 */
	public Date getCollateralMaturityDate();

	/* added by naveen */

	public String getCollateralStatus();

	/**
	 * Get subtype of this collateral.
	 * 
	 * @return ICollateralSubType
	 */
	public ICollateralSubType getCollateralSubType();

	/**
	 * Get type of this collateral.
	 * 
	 * @return ICollateralType
	 */
	public ICollateralType getCollateralType();

	public String getComment();

	public Date getCreateDate();

	/**
	 * Get security currency code from SCI.
	 * 
	 * @return String
	 */
	public String getCurrencyCode();

	/**
	 * Get filtered collateral limit maps.
	 * 
	 * @return ICollateralLimitMap[]
	 */
	public ICollateralLimitMap[] getCurrentCollateralLimits();

	public Date getExchangeCtrlDate();

	/**
	 * Get external senior lien from SCI.
	 * 
	 * @return String
	 */
	public String getExtSeniorLien();

	/**
	 * Get latest forced sale value.
	 * 
	 * @return Amount
	 */
	public Amount getFSV();

	/**
	 * Get FSV balance after collateral allocation.
	 * 
	 * @return Amount
	 */
	public Amount getFSVBalance();

	/**
	 * Get currency code for forced sale value.
	 * 
	 * @return String
	 */
	public String getFSVCcyCode();

	public IInstrument[] getInstrumentArray();

	/**
	 * Get insurance map.
	 * 
	 * @return a map with key as String reference id and value as
	 *         IInsurancePolicy
	 */
	public Map getInsurance();

	/**
	 * Get a list of insurance policies.
	 * 
	 * @return IInsurancePolicy[]
	 */
	public IInsurancePolicy[] getInsurancePolicies();
	public IAddtionalDocumentFacilityDetails[] getAdditonalDocFacDetails();
	
	public List<ISecurityCoverage> getSecurityCoverage();
	public void setSecurityCoverage(List<ISecurityCoverage> coverageList);
	
	public boolean getIsBorrowerDependency();

	public boolean getIsCGCPledged();

	/**
	 * Get if exchange control approval obtained.
	 * 
	 * @return String
	 */
	public String getIsExchangeCtrlObtained();

	/**
	 * Get if the Legal Enforceability .
	 * 
	 */
	public String getIsLE();

	/**
	 * Get if the Legal Enforceability by charge ranking.
	 * 
	 * @return boolean
	 */
	public boolean getIsLEByChargeRanking();

	/**
	 * Get if the Legal Enforceability by governing laws.
	 * 
	 * @return boolean
	 */
	public boolean getIsLEByGovernLaws();

	/**
	 * Get if the Legal Enforceability by jurisdiction.
	 * 
	 * @return boolean
	 */
	public boolean getIsLEByJurisdiction();

	/**
	 * Get if it is pari passu.
	 * 
	 * @return boolean
	 */
	public boolean getIsPariPassu();

	/**
	 * Get if security is perfected.
	 * 
	 * @return boolean
	 */
	public boolean getIsPerfected();

	/**
	 * Get last remargin value date from SCI.
	 * 
	 * @return Date
	 */
	public Date getLastRemarginDate();

	/**
	 * Get last valuation date.
	 * 
	 * @return Date
	 */
	public Date getLastValuationDate();

	/**
	 * Get last valuer name.
	 * 
	 * @return String
	 */
	public String getLastValuer();

	/**
	 * Get legal enforceability date .
	 * 
	 * @return Date
	 */
	public Date getLEDate();

	/**
	 * Get legal enforceability date by charge ranking.
	 * 
	 * @return Date
	 */
	public Date getLEDateByChargeRanking();

	/**
	 * Get legal enforceability date by governing laws.
	 * 
	 * @return Date
	 */
	public Date getLEDateByGovernLaws();

	/**
	 * Get legal enforceability date by jurisdiction.
	 * 
	 * @return Date
	 */
	public Date getLEDateByJurisdiction();

	/**
	 * Get collateral limit charges.
	 * 
	 * @return a list of limit charges
	 */
	public ILimitCharge[] getLimitCharges();

	/**
	 * Retrieve the collateral reference (or source id) of the LOS system
	 * 
	 * @return the collateral reference of LOS
	 */
	public String getLosCollateralRef();

	/**
	 * Get margin value.
	 * 
	 * @return double
	 */
	public double getMargin();

	public Amount getNetRealisableAmount();

	/**
	 * Get next remargin value date from SCI.
	 * 
	 * @return Date
	 */
	public Date getNextRemarginDate();

	/**
	 * Get security perfection date.
	 * 
	 * @return Date
	 */
	public Date getPerfectionDate();

	/**
	 * Get pledgors of the collateral.
	 * 
	 * @return a list of pledgors
	 */
	public ICollateralPledgor[] getPledgors();

	/**
	 * Get remargin value from SCI.
	 * 
	 * @return String
	 */
	public String getRemargin();

	public Amount getReservePrice();
	
	public float getSpread();

	public Date getReservePriceDate();

	/**
	 * Get risk mitigation category from SCI.
	 * 
	 * @return String
	 */
	public String getRiskMitigationCategory();

	/**
	 * Get security booking location from SCI.
	 * 
	 * @return long
	 */
	public long getSCIBookingLocationID();

	/**
	 * Get currency code from SCI.
	 * 
	 * @return String
	 */
	public String getSCICurrencyCode();

	/**
	 * Get force sale value from SCI.
	 * 
	 * @return Amount
	 */
	public Amount getSCIFSV();

	/**
	 * Get force sale value date from SCI.
	 * 
	 * @return Date
	 */
	public Date getSCIFSVDate();

	/**
	 * Get reference note from SCI.
	 * 
	 * @return String
	 */
	public String getSCIReferenceNote();

	/**
	 * Get security reference id from SCI.
	 * 
	 * @return String
	 */
	public String getSCISecurityID();

	/**
	 * Set security subtype value from SCI.
	 * 
	 * @return String
	 */
	public String getSCISubTypeValue();

	/**
	 * Get security type from SCI.
	 * 
	 * @return String
	 */
	public String getSCITypeValue();

	public List getSecApportionment();

	public List getSecSystemName();

	public String getSecurityOrganization();

	public IShareSecurity[] getShareSecArray();

	public String getSourceId();

	public List getSourceSecIdAliases();

	/**
	 * Get security sub type / Backend Collateral Code value
	 * 
	 * @return double
	 */
	public String getSourceSecuritySubType();

	public IValuation getSourceValuation();

	/**
	 * Get the status of the collateral.
	 * 
	 * @return String
	 */
	public String getStatus();

	public String getToBeDischargedInd();

	/**
	 * Get collateral valuation.
	 * 
	 * @return IValuation
	 */
	public IValuation getValuation();

	public IValuation[] getValuationFromLOS();

	/**
	 * Get collateral's valuation history.
	 * 
	 * @return a list of valuation history
	 */
	public IValuation[] getValuationHistory();

	public IValuation getValuationIntoCMS();

	public String getValuationType();

	public String getValuer();

	/**
	 * Get the version of the collateral.
	 * 
	 * @return long
	 */
	public long getVersionTime();

	public void setChargeInfoDrawAmountUsageIndicator(Character chargeInfoDrawAmountUsageIndicator);

	public void setChargeInfoPledgeAmountUsageIndicator(Character chargeInfoPledgeAmountUsageIndicator);

	/**
	 * Set latest current market value.
	 * 
	 * @param cmv of type Amount
	 */
	public void setCMV(Amount cmv);

	/**
	 * Set currency code for current market value.
	 * 
	 * @param cmvCcyCode of type String
	 */
	public void setCMVCcyCode(String cmvCcyCode);

	/**
	 * Set Security custodian.
	 * 
	 * @param collateralCustodian is of type String
	 */
	public void setCollateralCustodian(String collateralCustodian);

	/**
	 * Set security custodian type, internal or external.
	 * 
	 * @param collateralCustodianType of type String
	 */
	public void setCollateralCustodianType(String collateralCustodianType);

	/**
	 * set collateral ID.
	 * 
	 * @param collateralID is of type long
	 */
	public void setCollateralID(long collateralID);

	/**
	 * Set limits associated to this collateral.
	 * 
	 * @param collateralLimits of type ICollateralLimitMap[]
	 */
	public void setCollateralLimits(ICollateralLimitMap[] collateralLimits);

	/**
	 * Set security location.
	 * 
	 * @param collateralLocation is of type String
	 */
	public void setCollateralLocation(String collateralLocation);

	/**
	 * Set security maturity date.
	 * 
	 * @param collateralMaturityDate is of type Date
	 */
	public void setCollateralMaturityDate(Date collateralMaturityDate);

	public void setCollateralStatus(String collateralStatus);

	/**
	 * Set subtype of this collateral.
	 * 
	 * @param collateralSubType is of type ICollateralSubType
	 */
	public void setCollateralSubType(ICollateralSubType collateralSubType);

	/**
	 * Set the collateral type.
	 * 
	 * @param collateralType is of type ICollateralType
	 */
	public void setCollateralType(ICollateralType collateralType);

	public void setComment(String comment);

	public void setCreateDate(Date createDate);

	/**
	 * Set security currency code from SCI.
	 * 
	 * @param currencyCode of type String
	 */
	public void setCurrencyCode(String currencyCode);

	public void setExchangeCtrlDate(Date exchangeCtrlDate);

	/**
	 * Set external senior lien from SCI.
	 * 
	 * @param extSeniorLien of type String
	 */
	public void setExtSeniorLien(String extSeniorLien);

	/**
	 * Set latest forced sale value.
	 * 
	 * @param fsv of type Amount
	 */
	public void setFSV(Amount fsv);

	/**
	 * Set FSV balance after collateral allocation.
	 * 
	 * @param fsvBalance of type Amount
	 */
	public void setFSVBalance(Amount fsvBalance);

	/**
	 * Set currency code for forced sale value.
	 * 
	 * @param fsvCcyCode of type String
	 */
	public void setFSVCcyCode(String fsvCcyCode);

	public void setInstrumentArray(IInstrument[] instrumentArray);

	/**
	 * Set insurance map.
	 * 
	 * @param insurance a map with key reference id and value IInsurancePolicy
	 */
	public void setInsurance(Map insurance);

	/**
	 * Set a list of insurance policies.
	 * 
	 * @param insurancePolicies of type IInsurancePolicy[]
	 */
	public void setInsurancePolicies(IInsurancePolicy[] insurancePolicies);
	public void setAdditonalDocFacDetails(IAddtionalDocumentFacilityDetails[] additonalDocFacDetails);

	public void setIsBorrowerDependency(boolean isBorrowerDependency);

	public void setIsCGCPledged(boolean isCGCPledged);

	/**
	 * Set if exchange control approval obtained.
	 * 
	 * @param isExchangeCtrlObtained of type String
	 */
	public void setIsExchangeCtrlObtained(String isExchangeCtrlObtained);

	/**
	 * Set the legal enforceability .
	 * 
	 * @param isLE is of type String
	 */
	public void setIsLE(String isLE);

	/**
	 * Set the legal enforceability by charge ranking.
	 * 
	 * @param isLEByChargeRanking is of type boolean
	 */
	public void setIsLEByChargeRanking(boolean isLEByChargeRanking);

	/**
	 * Set if the legal enforceability by governing laws.
	 * 
	 * @param isLEByGovernLaws is of type boolean
	 */
	public void setIsLEByGovernLaws(boolean isLEByGovernLaws);

	/**
	 * Set the legal enforceability by jurisdiction.
	 * 
	 * @param isLEByJurisdiction is of type boolean
	 */
	public void setIsLEByJurisdiction(boolean isLEByJurisdiction);

	/**
	 * Set if it is pari passu.
	 * 
	 * @param isPariPassu of type boolean
	 */
	public void setIsPariPassu(boolean isPariPassu);

	/**
	 * Set if the security is perfected.
	 * 
	 * @param isPerfected of type boolean
	 */
	public void setIsPerfected(boolean isPerfected);

	/**
	 * Set last remargin value date from SCI.
	 * 
	 * @param lastRemarginDate of type Date
	 */
	public void setLastRemarginDate(Date lastRemarginDate);

	/**
	 * Set last valuation date.
	 * 
	 * @param lastValuationDate of type Date
	 */
	public void setLastValuationDate(Date lastValuationDate);

	/**
	 * Set last valuer name.
	 * 
	 * @param lastValuer of type String
	 */
	public void setLastValuer(String lastValuer);

	/**
	 * Set legal enforceability date .
	 * 
	 * @param lEDate is of type Date
	 */
	public void setLEDate(Date lEDate);

	/**
	 * Set legal enforceability date by charge ranking.
	 * 
	 * @param lEDateByChargeRanking is of type Date
	 */
	public void setLEDateByChargeRanking(Date lEDateByChargeRanking);

	/**
	 * Set legal enforceability date by governing laws.
	 * 
	 * @param lEDateByGovernLaws is of type Date
	 */
	public void setLEDateByGovernLaws(Date lEDateByGovernLaws);

	/**
	 * Get legal enforceability date by jurisdiction.
	 * 
	 * @param lEDateByJurisdiction is of type Date
	 */
	public void setLEDateByJurisdiction(Date lEDateByJurisdiction);

	/**
	 * Set collateral limit charges.
	 * 
	 * @param limitCharges is of type ILimitCharge[]
	 */
	public void setLimitCharges(ILimitCharge[] limitCharges);

	public void setLosCollateralRef(String losCollateralRef);

	/**
	 * Set margin value.
	 * 
	 * @param margin of type double
	 */
	public void setMargin(double margin);

	public void setNetRealisableAmount(Amount amt);

	/**
	 * Set next remargin value date from SCI.
	 * 
	 * @param nextRemarginDate of type Date
	 */
	public void setNextRemarginDate(Date nextRemarginDate);

	/**
	 * Set security perfection date.
	 * 
	 * @param perfectionDate of type Date
	 */
	public void setPerfectionDate(Date perfectionDate);

	/*
	 * public List getValuationFromLOS(); public void setValuationFromLOS(List
	 * valuationFrom);
	 * 
	 * public IValuation getValuationIntoGCMS(); public void
	 * setValuationIntoGCMS(IValuation valuation);
	 * 
	 * 
	 * public List getSourceValuationDetails(); public void
	 * setSourceValuationDetails(List list);
	 */

	/**
	 * Set pledgors of the collateral.
	 * 
	 * @param pledgors is of type ICollateralPledgor[]
	 */
	public void setPledgors(ICollateralPledgor[] pledgors);

	/**
	 * Set remargin value from SCI.
	 * 
	 * @param remargin of type String
	 */
	public void setRemargin(String remargin);

	public void setReservePrice(Amount reservePrice);
	
	public void setSpread(float spread);
	
	public void setReservePriceDate(Date reservePriceDate);

	/**
	 * Set risk mitigation category from SCI.
	 * 
	 * @param riskMitigationCategory of type String
	 */
	public void setRiskMitigationCategory(String riskMitigationCategory);

	/**
	 * Set security booking location from SCI.
	 * 
	 * @param sciBookingLocationID of type String
	 */
	public void setSCIBookingLocationID(long sciBookingLocationID);

	/**
	 * Set SCI currency code.
	 * 
	 * @param sciCurrencyCode of type String
	 */
	public void setSCICurrencyCode(String sciCurrencyCode);

	/**
	 * Set force sale value from SCI.
	 * 
	 * @param sciFSV of type Amount
	 */
	public void setSCIFSV(Amount sciFSV);

	/**
	 * Set force sale value date from SCI.
	 * 
	 * @param sciFSVDate of type Date
	 */
	public void setSCIFSVDate(Date sciFSVDate);

	/**
	 * Set reference note from SCI.
	 * 
	 * @param sciReferenceNote of type String
	 */
	public void setSCIReferenceNote(String sciReferenceNote);

	/**
	 * Set security reference id from SCI.
	 * 
	 * @param sciSecurityID of type String
	 */
	public void setSCISecurityID(String sciSecurityID);

	/**
	 * Set security subtype value from SCI.
	 * 
	 * @param sciSubTypeValue of type String
	 */
	public void setSCISubTypeValue(String sciSubTypeValue);

	/**
	 * Set security type value from SCI.
	 * 
	 * @param sciTypeValue of type String
	 */
	public void setSCITypeValue(String sciTypeValue);

	public void setSecApportionment(List apportionments);

	public void setSecurityOrganization(String org);

	public void setShareSecArray(IShareSecurity[] shareSecArray);

	public void setSourceId(String sourceId);

	public void setSourceSecIdAliases(List l);

	/**
	 * Get security sub type / Backend Collateral Code value
	 * 
	 * @return double
	 */
	public void setSourceSecuritySubType(String sourceSecuritySubType);

	public void setSourceValuation(IValuation sourceValuation);

	/**
	 * Set the status of the collateral
	 * 
	 * @param status is of type String
	 */
	public void setStatus(String status);

	public void setToBeDischargedInd(String toBeDischargedInd);

	/**
	 * Set collateral valuation.
	 * 
	 * @param valuation is of type IValuation
	 */

	public void setValuation(IValuation valuation);

	public void setValuationFromLOS(IValuation[] valuationFromLOS);

	/**
	 * Set collateral's valuation history.
	 * 
	 * @param valuationHistory is of type IValuation[]
	 */
	public void setValuationHistory(IValuation[] valuationHistory);

	public void setValuationIntoCMS(IValuation valuationIntoMCS);

	public void setValuationType(String valuationType);
	
	public void setValuer(String valuer);
	/**
	 * Set the version of the collateral.
	 * 
	 * @param versionTime is of type long
	 */
	public void setVersionTime(long versionTime);

	
	
//	public ISystemBankBranch getBranchName();
//  
//   public void setBranchName(ISystemBankBranch branchName);
	
	public String getCollateralCode();
	
	public void setCollateralCode(String collateralCode);
	
	public String getSecPriority();
	
	public void setSecPriority(String secPriority);

	public String getLoanableAmount() ;

	public void setLoanableAmount(String loanableAmount);
	
	public String getLmtSecurityCoverage();
	public void setLmtSecurityCoverage(String lmtSecurityCoverage);
	//Added by Pramod Katkar for New Filed CR on 13-08-2013
	public String getCommonRevalFreq();
	
	public void setCommonRevalFreq(String commonRevalFreq);
	
	public String getCommonRevalFreqNo();
	
	public void setCommonRevalFreqNo(String commonRevalFreqNo);
	
	public Date getValuationDate();
	
	public void setValuationDate(Date valuationDate);
	
	public String getValuationAmount();
	
	public void setValuationAmount(String valuationAmount);
	
	public Date getNextValDate();
	
	public void setNextValDate(Date nextValDate);
	
	public String getTypeOfChange();
	
	public void setTypeOfChange(String typeOfChange);
	
	public String getOtherBankCharge();
	
	public void setOtherBankCharge(String otherBankCharge);
	
	public String getMonitorProcess();
	
	public void setMonitorProcess(String monitorProcess);
	
	public String getMonitorFrequency();
	
	public void setMonitorFrequency(String monitorFrequency);
	
	//End by Pramod Katkar
	
	public String getTypeOfCharge();
	
	public void setTypeOfCharge(String typeOfCharge);
	
	public String getBankingArrangement();
	public void setBankingArrangement(String bankingArrangement);

	/* CERSAI CR */
	public String getOwnerOfProperty();
	public void setOwnerOfProperty(String ownerOfProperty);

	public String getSecurityOwnership();
	public void setSecurityOwnership(String securityOwnership);

	public String getThirdPartyEntity();
	public void setThirdPartyEntity(String thirdPartyEntity);

	public String getCinForThirdParty();
	public void setCinForThirdParty(String cinForThirdParty);

	public String getCersaiTransactionRefNumber();
	public void setCersaiTransactionRefNumber(String cersaiTransactionRefNumber);

	public String getCersaiSecurityInterestId();
	public void setCersaiSecurityInterestId(String cersaiSecurityInterestId);

	public String getCersaiAssetId();
	public void setCersaiAssetId(String cersaiAssetId);

	public Date getDateOfCersaiRegisteration();
	public void setDateOfCersaiRegisteration(Date dateOfCersaiRegisteration);

	public String getCersaiId();
	public void setCersaiId(String cersaiId);

	public Date getSaleDeedPurchaseDate();
	public void setSaleDeedPurchaseDate(Date saleDeedPurchaseDate);

	public String getThirdPartyAddress();
	public void setThirdPartyAddress(String thirdPartyAddress);

	public String getThirdPartyState();
	public void setThirdPartyState(String thirdPartyState);

	public String getThirdPartyCity();
	public void setThirdPartyCity(String thirdPartyCity);

	public String getThirdPartyPincode();
	public void setThirdPartyPincode(String thirdPartyPincode);
	
	public String getFdRebooking();
	public void setFdRebooking(String fdRebooking);
	
	
	//New General Fields Added
	public String getPrimarySecurityAddress();
	public void setPrimarySecurityAddress(String primarySecurityAddress);

	public Date getSecurityValueAsPerCAM();
	public void setSecurityValueAsPerCAM(Date securityValueAsPerCAM);

	public String getSecondarySecurityAddress();
	public void setSecondarySecurityAddress(String secondarySecurityAddress);

	public String getSecurityMargin();
	public void setSecurityMargin(String securityMargin);

	public String getChargePriority();
	public void setChargePriority(String chargePriority);
	
	public String getTermLoanOutstdAmt();
	public void setTermLoanOutstdAmt(String termLoanOutstdAmt);
	
	
	public String getMarginAssetCover();
	public void setMarginAssetCover(String marginAssetCover);
	
	
	public String getRecvGivenByClient();
	public void setRecvGivenByClient(String recvGivenByClient);
}