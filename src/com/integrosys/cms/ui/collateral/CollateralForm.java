/*

 * Copyright Integro Technologies Pte Ltd

 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/CollateralForm.java,v 1.27 2006/07/27 04:41:58 jzhan Exp $

 */

package com.integrosys.cms.ui.collateral;

import java.io.Serializable;
import org.apache.commons.lang.StringUtils;
import java.math.BigDecimal;
import java.util.Date;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * 
 * @author $Author: jzhan $<br>
 * 
 * @version $Revision: 1.27 $
 * 
 * @since $Date: 2006/07/27 04:41:58 $
 * 
 * Tag: $Name: $
 */

public abstract class CollateralForm extends TrxContextForm implements Serializable {

	private String[] deletePledge;

	private String[] deletePledgor;

	private String collateralMaturityDate = "";

	private String exchangeControl = "";

	private String exchangeControlDate = "";

	private String yesNo = "";

	private String collateralStatus = "";

	private String collateralLoc = "";

	private String collateralCurrency = "";

	private String sciCurrency = "";

	private String leCharge = "";

	private String leJurisdiction = "";

	private String leGoverningLaw = "";

	private String leDateCharge = "";

	private String leDateJurisdiction = "";

	private String leDateGovernginLaw = "";

	private String chargeId = "";

	private String natureOfCharge = "";

	private String amtCharge = "";

	private String currencyCharge = "";

	private String legalChargeDate = "";

	private String chargeType = "";

	private String interest = "";

	private String confirmChargeDate = "";

	private String secCustodianInt = "";

	private String secCustodianExt = "";

	private String secCustodianType = "";

	private String margin = "";

	private String valBefMargin = "";

	private String valAftMargin = "";

	private String valDate = "";

	private String valCurrency = "";

	private String valuer = "";

	private String revalFreq = "";

	private String revalFreqUnit = "";

	private String comments = "";

	private String amountCMV = "";

	private String amountFSV = "";

	private String evalDateFSV = "";

	private String amountSPV = "";

	private String lastValDate = "";

	private String nextRevalDate = "";

	private String isSSC = "";

	private String subTypeCode = "";

	private String itemType = "";

	private String nonStdFreq = "";

	private String nonStdFreqUnit = "";

	private String nonStdFreqUnitDesc = "";

	private String le = "";

	private String leDate = "";

	private String securityOrganization = "";

	private String[] deletedApportionments;

	private String remargin = "";

	private String lastRemarginDate = "";

	private String nextRemarginDate = "";

	private String riskMitigationCategory = "";

	private String perfectionDate = "";

	private String[] secInstrument;

	private String secEnvRisky = "";

	private String dateSecurityEnv = "";

	private String borrowerDependency = "";

	private String cgcPledged = "";

	private String valuerInGCMS = "";

	private String revalFreqInGCMS = "";

	private String revalFreqUnitInGCMS = "";

	private String valuationTypeInGCMS = "";

	private String reservePriceDate = "";

	private String reservePrice = "";
	
	private String spread = "";

	private String netRealisableSecValue = "";

	private String valuationType = "";

	private String olv = "";

	private String remainusefullife = "";

	private String valuationbasis = "";
	
	private String collateralName = "";
	
	private String sourceSecuritySubType = "";
	
	private String sourceID = "";
	
   private String goodStatus;
   
   private String secPriority ="";
   
   private String custodian1;
	private String custodian2;
	
	private String typeOfCharge= "";
	
	//Added by Pramod Katkar for New Filed CR on 13-08-2013
   
	private String commonRevalFreq = "";
	private String commonRevalFreqNo = "";
	private String valuationDate ;
	private String valuationAmount;
	private String nextValDate ;
	private String typeOfChange ;
	private String otherBankCharge ;	
	private String monitorProcess;
	private String monitorFrequency;
	
	//New General Fields
	private String primarySecurityAddress ;
	private String securityValueAsPerCAM ;	
	private String secondarySecurityAddress;
	private String securityMargin;
	private String chargePriority;
	
	//CERSAI fields
	private String securityOwnership;
	private String ownerOfProperty;
	private String thirdPartyEntity;
	private String cinForThirdParty;
	private String cersaiTransactionRefNumber;
	private String cersaiSecurityInterestId;
	private String cersaiAssetId;
	private String dateOfCersaiRegisteration;
	private String cersaiId;
	private String saleDeedPurchaseDate;
	private String thirdPartyAddress;
	private String thirdPartyState;
	private String thirdPartyCity;
	private String thirdPartyPincode;
	private String statePincodeString;
	private String collateralCategory;	
	private String cersaiApplicableInd;
	
	private String fdRebooking;
	
	private String coverageAmount = StringUtils.EMPTY;
	private String coveragePercentage = StringUtils.EMPTY;
	private String adHocCoverageAmount = StringUtils.EMPTY;

    private SecurityCoverageForm securityCoverageForm;
	
	public String getPrimarySecurityAddress() {
		return primarySecurityAddress;
	}
	public void setPrimarySecurityAddress(String primarySecurityAddress) {
		this.primarySecurityAddress = primarySecurityAddress;
	}
	public String getSecurityValueAsPerCAM() {
		return securityValueAsPerCAM;
	}
	public void setSecurityValueAsPerCAM(String securityValueAsPerCAM) {
		this.securityValueAsPerCAM = securityValueAsPerCAM;
	}
	public String getSecondarySecurityAddress() {
		return secondarySecurityAddress;
	}
	public void setSecondarySecurityAddress(String secondarySecurityAddress) {
		this.secondarySecurityAddress = secondarySecurityAddress;
	}
	public String getSecurityMargin() {
		return securityMargin;
	}
	public void setSecurityMargin(String securityMargin) {
		this.securityMargin = securityMargin;
	}
	public String getChargePriority() {
		return chargePriority;
	}
	public void setChargePriority(String chargePriority) {
		this.chargePriority = chargePriority;
	}
	

	public String getFdRebooking() {
		return fdRebooking;
	}

	public void setFdRebooking(String fdRebooking) {
		this.fdRebooking = fdRebooking;
	}
	
	public String getMonitorProcess() {
		return monitorProcess;
	}
	public void setMonitorProcess(String monitorProcess) {
		this.monitorProcess = monitorProcess;
	}
	public String getMonitorFrequency() {
		return monitorFrequency;
	}
	public void setMonitorFrequency(String monitorFrequency) {
		this.monitorFrequency = monitorFrequency;
	}
	public String getOtherBankCharge() {
		return otherBankCharge;
	}
	public void setOtherBankCharge(String otherBankCharge) {
		this.otherBankCharge = otherBankCharge;
	}
	public String getCommonRevalFreqNo() {
		return commonRevalFreqNo;
	}
	public void setCommonRevalFreqNo(String commonRevalFreqNo) {
		this.commonRevalFreqNo = commonRevalFreqNo;
	}
	public String getValuationDate() {
		return valuationDate;
	}
	public void setValuationDate(String valuationDate) {
		this.valuationDate = valuationDate;
	}
	public String getValuationAmount() {
		return valuationAmount;
	}
	public void setValuationAmount(String valuationAmount) {
		this.valuationAmount = valuationAmount;
	}
	public String getNextValDate() {
		return nextValDate;
	}
	public void setNextValDate(String nextValDate) {
		this.nextValDate = nextValDate;
	}
	public String getTypeOfChange() {
		return typeOfChange;
	}
	public void setTypeOfChange(String typeOfChange) {
		this.typeOfChange = typeOfChange;
	}
	public String getCommonRevalFreq() {
		return commonRevalFreq;
	}
	public void setCommonRevalFreq(String commonRevalFreq) {
		this.commonRevalFreq = commonRevalFreq;
	}
	
	//End by Pramod Katkar
	
   //Fields added by Dattatray Thorat for change request - Start
   
   public String getCustodian1() {
		return custodian1;
	}

	public void setCustodian1(String custodian1) {
		this.custodian1 = custodian1;
	}

	public String getCustodian2() {
		return custodian2;
	}

	public void setCustodian2(String custodian2) {
		this.custodian2 = custodian2;
	}


private String loanableAmount = "";

	public String getLoanableAmount() {
		return loanableAmount;
	}
	
	public String getSecPriority() {
		return secPriority;
	}

	public void setSecPriority(String secPriority) {
		this.secPriority = secPriority;
	}

	public void setLoanableAmount(String loanableAmount) {
		this.loanableAmount = loanableAmount;
	}
	//Fields added by Dattatray Thorat for change request - End
	
	public String getGoodStatus() {
		return goodStatus;
	}

	public void setGoodStatus(String goodStatus) {
		this.goodStatus = goodStatus;
	}
	
	

	public String getValuationbasis() {
		return valuationbasis;
	}

	public void setValuationbasis(String valuationbasis) {
		this.valuationbasis = valuationbasis;
	}

	public String getRemainusefullife() {
		return remainusefullife;
	}

	public void setRemainusefullife(String remainusefullife) {
		this.remainusefullife = remainusefullife;
	}

	public String getOlv() {
		return olv;
	}

	public void setOlv(String olv) {
		this.olv = olv;
	}

	public String getValuationType() {
		return valuationType;
	}

	public void setValuationType(String valuationType) {
		this.valuationType = valuationType;
	}

	public String getReservePrice() {
		return reservePrice;
	}

	public void setReservePrice(String reservePrice) {
		this.reservePrice = reservePrice;
	}

	public String getNetRealisableSecValue() {
		return netRealisableSecValue;
	}

	public void setNetRealisableSecValue(String netRealisableSecValue) {
		this.netRealisableSecValue = netRealisableSecValue;
	}

	public String getSpread() {
		return spread;
	}

	public void setSpread(String spread) {
		this.spread = spread;
	}

	public String getReservePriceDate() {
		return reservePriceDate;
	}

	public void setReservePriceDate(String reservePriceDate) {
		this.reservePriceDate = reservePriceDate;
	}

	public String getValuerInGCMS() {
		return valuerInGCMS;
	}

	public void setValuerInGCMS(String valuerInGCMS) {
		this.valuerInGCMS = valuerInGCMS;
	}

	public String getRevalFreqInGCMS() {
		return this.revalFreqInGCMS;
	}

	public void setRevalFreqInGCMS(String revalFreqInGCMS) {
		this.revalFreqInGCMS = revalFreqInGCMS;
	}

	public String getRevalFreqUnitInGCMS() {
		return this.revalFreqUnitInGCMS;
	}

	public void setRevalFreqUnitInGCMS(String revalFreqUnitInGCMS) {
		this.revalFreqUnitInGCMS = revalFreqUnitInGCMS;
	}

	public String getValuationTypeInGCMS() {
		return this.valuationTypeInGCMS;
	}

	public void setValuationTypeInGCMS(String valuationTypeInGCMS) {
		this.valuationTypeInGCMS = valuationTypeInGCMS;
	}

	public String getCollateralMaturityDate() {
		return this.collateralMaturityDate;
	}

	public void setCollateralMaturityDate(String collateralMaturityDate) {
		this.collateralMaturityDate = collateralMaturityDate;
	}

	public String getExchangeControl() {
		return this.exchangeControl;
	}

	public void setExchangeControl(String exchangeControl) {
		this.exchangeControl = exchangeControl;
	}

	public String getYesNo() {
		return this.yesNo;
	}

	public void setYesNo(String yesNo) {
		this.yesNo = yesNo;
	}

	public String getCollateralLoc() {
		return this.collateralLoc;
	}

	public void setCollateralLoc(String collateralLoc) {
		this.collateralLoc = collateralLoc;
	}

	public String getCollateralCurrency() {
		return this.collateralCurrency;
	}

	public void setCollateralCurrency(String collateralCurrency) {
		this.collateralCurrency = collateralCurrency;
	}

	public String getSciCurrency() {
		return this.sciCurrency;
	}

	public void setSciCurrency(String sciCurrency) {
		this.sciCurrency = sciCurrency;
	}

	public String getLeCharge() {
		return this.leCharge;
	}

	public void setLeCharge(String leCharge) {
		this.leCharge = leCharge;
	}

	public String getLeJurisdiction() {
		return this.leJurisdiction;
	}

	public void setLeJurisdiction(String leJurisdiction) {
		this.leJurisdiction = leJurisdiction;
	}

	public String getLeGoverningLaw() {
		return this.leGoverningLaw;
	}

	public void setLeGoverningLaw(String leGoverningLaw) {
		this.leGoverningLaw = leGoverningLaw;
	}

	public String getLeDateCharge() {
		return this.leDateCharge;
	}

	public void setLeDateCharge(String leDateCharge) {
		this.leDateCharge = leDateCharge;
	}

	public String getLeDateJurisdiction() {
		return this.leDateJurisdiction;
	}

	public void setLeDateJurisdiction(String leDateJurisdiction) {
		this.leDateJurisdiction = leDateJurisdiction;
	}

	public String getLeDateGovernginLaw() {
		return this.leDateGovernginLaw;
	}

	public void setLeDateGovernginLaw(String leDateGovernginLaw) {
		this.leDateGovernginLaw = leDateGovernginLaw;
	}

	public String getChargeId() {
		return this.chargeId;
	}

	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}

	public String getNatureOfCharge() {
		return this.natureOfCharge;
	}

	public void setNatureOfCharge(String natureOfCharge) {
		this.natureOfCharge = natureOfCharge;
	}

	public String getAmtCharge() {
		return this.amtCharge;
	}

	public void setAmtCharge(String amtCharge) {
		this.amtCharge = amtCharge;
	}

	public String getInterest() {
		return this.interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getCurrencyCharge() {
		return this.currencyCharge;
	}

	public void setCurrencyCharge(String currencyCharge) {
		this.currencyCharge = currencyCharge;
	}

	public String getLegalChargeDate() {
		return this.legalChargeDate;
	}

	public void setLegalChargeDate(String legalChargeDate) {
		this.legalChargeDate = legalChargeDate;
	}

	public String getChargeType() {
		return this.chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public String getConfirmChargeDate() {
		return this.confirmChargeDate;
	}

	public void setConfirmChargeDate(String confirmChargeDate) {
		this.confirmChargeDate = confirmChargeDate;
	}

	public String getSecCustodianInt() {
		return this.secCustodianInt;
	}

	public void setSecCustodianInt(String secCustodianInt) {
		this.secCustodianInt = secCustodianInt;
	}

	public String getSecCustodianExt() {
		return this.secCustodianExt;
	}

	public void setSecCustodianExt(String secCustodianExt) {
		this.secCustodianExt = secCustodianExt;
	}

	public String getSecCustodianType() {
		return this.secCustodianType;
	}

	public void setSecCustodianType(String secCustodianType) {
		this.secCustodianType = secCustodianType;
	}

	public String getMargin() {
		return this.margin;
	}

	public void setMargin(String margin) {
		this.margin = margin;
	}

	public String getHaircut() {
		if ((this.margin != null) && !"".equals(margin.trim())) {
			try {
				int marginInt = Integer.parseInt(margin);
				return String.valueOf(100 - marginInt);
			}
			catch (Exception ex) {
			}
		}
		return "";
	}

	public void setHaircut(String haircut) {
		if ((haircut != null) && !"".equals(haircut)) {
			try {
				int haircutInt = Integer.parseInt(haircut);
				this.margin = String.valueOf(100 - haircutInt);
			}
			catch (Exception ex) {
			}
		}
	}

	public String getValBefMargin() {
		return this.valBefMargin;
	}

	public void setValBefMargin(String valBefMargin) {
		this.valBefMargin = valBefMargin;
	}

	public String getValAftMargin() {
		return this.valAftMargin;
	}

	public void setValAftMargin(String valAftMargin) {
		this.valAftMargin = valAftMargin;
	}

	public String getValDate() {
		return this.valDate;
	}

	public void setValDate(String valDate) {
		this.valDate = valDate;
	}

	public String getValCurrency() {
		return this.valCurrency;
	}

	public void setValCurrency(String valCurrency) {
		this.valCurrency = valCurrency;
	}

	public String getValuer() {
		return this.valuer;
	}

	public void setValuer(String valuer) {
		this.valuer = valuer;
	}

	public String getRevalFreq() {
		return this.revalFreq;
	}

	public void setRevalFreq(String revalFreq) {
		this.revalFreq = revalFreq;
	}

	public String getRevalFreqUnit() {
		return this.revalFreqUnit;
	}

	public void setRevalFreqUnit(String revalFreqUnit) {
		this.revalFreqUnit = revalFreqUnit;
	}

	public String getAmountCMV() {
		return this.amountCMV;
	}

	public void setAmountCMV(String amountCMV) {
		this.amountCMV = amountCMV;
	}

	public String getAmountFSV() {
		return this.amountFSV;
	}

	public void setAmountFSV(String amountFSV) {
		this.amountFSV = amountFSV;
	}

	public String getEvalDateFSV() {
		return this.evalDateFSV;
	}

	public void setEvalDateFSV(String evalDateFSV) {
		this.evalDateFSV = evalDateFSV;
	}

	public String getAmountSPV() {
		return this.amountSPV;
	}

	public void setAmountSPV(String amountSPV) {
		this.amountSPV = amountSPV;
	}

	public String getNextRevalDate() {
		return this.nextRevalDate;
	}

	public void setNextRevalDate(String nextRevalDate) {
		this.nextRevalDate = nextRevalDate;
	}

	public String getLastValDate() {
		return this.lastValDate;
	}

	public void setLastValDate(String lastValDate) {
		this.lastValDate = lastValDate;
	}

	public String getIsSSC() {
		return this.isSSC;
	}

	public void setIsSSC(String isSSC) {
		this.isSSC = isSSC;
	}

	public String getSubTypeCode() {
		return this.subTypeCode;
	}

	public void setSubTypeCode(String subTypeCode) {
		this.subTypeCode = subTypeCode;
	}

	public String getItemType() {
		return this.itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public void reset() {
	}

	public String getNonStdFreq() {
		return nonStdFreq;
	}

	public void setNonStdFreq(String nonStdFreq) {
		this.nonStdFreq = nonStdFreq;
	}

	public String getNonStdFreqUnit() {
		return nonStdFreqUnit;
	}

	public void setNonStdFreqUnit(String nonStdFreqUnit) {
		this.nonStdFreqUnit = nonStdFreqUnit;
	}

	/**
	 * @return Returns the nonStdFreqUnitDesc.
	 */
	public String getNonStdFreqUnitDesc() {
		return nonStdFreqUnitDesc;
	}

	/**
	 * @param nonStdFreqUnitDesc The nonStdFreqUnitDesc to set.
	 */
	public void setNonStdFreqUnitDesc(String nonStdFreqUnitDesc) {
		this.nonStdFreqUnitDesc = nonStdFreqUnitDesc;
	}

	/**
	 * @return Returns the comments.
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments The comments to set.
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getLe() {
		return this.le;
	}

	public void setLe(String le) {
		this.le = le;
	}

	public String getLeDate() {
		return this.leDate;
	}

	public void setLeDate(String leDate) {
		this.leDate = leDate;
	}

	public String getSecurityOrganization() {
		return securityOrganization;
	}

	public void setSecurityOrganization(String org) {
		securityOrganization = org;
	}

	/**
	 * @return Returns the deletedApportionments.
	 */
	public String[] getDeletedApportionments() {
		return deletedApportionments;
	}

	/**
	 * @param deletedApportionments The deletedApportionments to set.
	 */
	public void setDeletedApportionments(String[] deletedApportionments) {
		this.deletedApportionments = deletedApportionments;
	}

	public String getRemargin() {
		return remargin;
	}

	public void setRemargin(String remargin) {
		this.remargin = remargin;
	}

	public String getLastRemarginDate() {
		return lastRemarginDate;
	}

	public void setLastRemarginDate(String lastRemarginDate) {
		this.lastRemarginDate = lastRemarginDate;
	}

	public String getNextRemarginDate() {
		return nextRemarginDate;
	}

	public void setNextRemarginDate(String nextRemarginDate) {
		this.nextRemarginDate = nextRemarginDate;
	}

	public String getRiskMitigationCategory() {
		return riskMitigationCategory;
	}

	public void setRiskMitigationCategory(String riskMitigationCategory) {
		this.riskMitigationCategory = riskMitigationCategory;
	}

	public String getPerfectionDate() {
		return perfectionDate;
	}

	public void setPerfectionDate(String perfectionDate) {
		this.perfectionDate = perfectionDate;
	}

	public String[] getSecInstrument() {
		return secInstrument;
	}

	public void setSecInstrument(String[] secInstrument) {
		this.secInstrument = secInstrument;
	}

	public String getSecEnvRisky() {
		return secEnvRisky;
	}

	public void setSecEnvRisky(String secEnvRisky) {
		this.secEnvRisky = secEnvRisky;
	}

	public String getDateSecurityEnv() {
		return dateSecurityEnv;
	}

	public void setDateSecurityEnv(String dateSecurityEnv) {
		this.dateSecurityEnv = dateSecurityEnv;
	}

	public String getBorrowerDependency() {
		return borrowerDependency;
	}

	public void setBorrowerDependency(String borrowerDependency) {
		this.borrowerDependency = borrowerDependency;
	}

	public String getExchangeControlDate() {
		return exchangeControlDate;
	}

	public void setExchangeControlDate(String exchangeControlDate) {
		this.exchangeControlDate = exchangeControlDate;
	}

	public String getCgcPledged() {
		return cgcPledged;
	}

	public void setCgcPledged(String cgcPledged) {
		this.cgcPledged = cgcPledged;
	}

	public String getCollateralStatus() {
		return collateralStatus;
	}

	public void setCollateralStatus(String collateralStatus) {
		this.collateralStatus = collateralStatus;
	}

	public String[] getDeletePledge() {
		return deletePledge;
	}

	public void setDeletePledge(String[] deletePledge) {
		this.deletePledge = deletePledge;
	}

	public String[] getDeletePledgor() {
		return deletePledgor;
	}

	public void setDeletePledgor(String[] deletePledgor) {
		this.deletePledgor = deletePledgor;
	}

	public String getCollateralName() {
		return collateralName;
	}

	public void setCollateralName(String collateralName) {
		this.collateralName = collateralName;
	}

	public String getSourceSecuritySubType() {
		return sourceSecuritySubType;
	}

	public void setSourceSecuritySubType(String sourceSecuritySubType) {
		this.sourceSecuritySubType = sourceSecuritySubType;
	}

	public String getSourceID() {
		return sourceID;
	}

	public void setSourceID(String sourceID) {
		this.sourceID = sourceID;
	}
	
	public String[][] getMapper() {
		DefaultLogger.debug(this, "Getting Mapper");
		String[][] input = { { "theOBTrxContext", TRX_MAPPER },
				 };
		return input;
	}


	public String getTypeOfCharge() {
		return typeOfCharge;
	}
	public void setTypeOfCharge(String typeOfCharge) {
		this.typeOfCharge = typeOfCharge;
	}


	public String getOwnerOfProperty() {
		return ownerOfProperty;
	}
	public void setOwnerOfProperty(String ownerOfProperty) {
		this.ownerOfProperty = ownerOfProperty;
	}


	public String getSecurityOwnership() {
		return securityOwnership;
	}
	public void setSecurityOwnership(String securityOwnership) {
		this.securityOwnership = securityOwnership;
	}


	public String getThirdPartyEntity() {
		return thirdPartyEntity;
	}
	public void setThirdPartyEntity(String thirdPartyEntity) {
		this.thirdPartyEntity = thirdPartyEntity;
	}


	public String getCinForThirdParty() {
		return cinForThirdParty;
	}
	public void setCinForThirdParty(String cinForThirdParty) {
		this.cinForThirdParty = cinForThirdParty;
	}


	public String getCersaiTransactionRefNumber() {
		return cersaiTransactionRefNumber;
	}
	public void setCersaiTransactionRefNumber(String cersaiTransactionRefNumber) {
		this.cersaiTransactionRefNumber = cersaiTransactionRefNumber;
	}


	public String getCersaiSecurityInterestId() {
		return cersaiSecurityInterestId;
	}
	public void setCersaiSecurityInterestId(String cersaiSecurityInterestId) {
		this.cersaiSecurityInterestId = cersaiSecurityInterestId;
	}


	public String getCersaiAssetId() {
		return cersaiAssetId;
	}
	public void setCersaiAssetId(String cersaiAssetId) {
		this.cersaiAssetId = cersaiAssetId;
	}


	public String getDateOfCersaiRegisteration() {
		return dateOfCersaiRegisteration;
	}
	public void setDateOfCersaiRegisteration(String dateOfCersaiRegisteration) {
		this.dateOfCersaiRegisteration = dateOfCersaiRegisteration;
	}


	public String getCersaiId() {
		return cersaiId;
	}
	public void setCersaiId(String cersaiId) {
		this.cersaiId = cersaiId;
	}


	public String getSaleDeedPurchaseDate() {
		return saleDeedPurchaseDate;
	}
	public void setSaleDeedPurchaseDate(String saleDeedPurchaseDate) {
		this.saleDeedPurchaseDate = saleDeedPurchaseDate;
	}


	public String getThirdPartyAddress() {
		return thirdPartyAddress;
	}
	public void setThirdPartyAddress(String thirdPartyAddress) {
		this.thirdPartyAddress = thirdPartyAddress;
	}


	public String getThirdPartyState() {
		return thirdPartyState;
	}
	public void setThirdPartyState(String thirdPartyState) {
		this.thirdPartyState = thirdPartyState;
	}


	public String getThirdPartyCity() {
		return thirdPartyCity;
	}
	public void setThirdPartyCity(String thirdPartyCity) {
		this.thirdPartyCity = thirdPartyCity;
	}


	public String getThirdPartyPincode() {
		return thirdPartyPincode;
	}
	public void setThirdPartyPincode(String thirdPartyPincode) {
		this.thirdPartyPincode = thirdPartyPincode;
	}


	public String getStatePincodeString() {
		return statePincodeString;
	}
	public void setStatePincodeString(String statePincodeString) {
		this.statePincodeString = statePincodeString;
	}


	public String getCollateralCategory() {
		return collateralCategory;
	}
	public void setCollateralCategory(String collateralCategory) {
		this.collateralCategory = collateralCategory;
	}


	public String getCersaiApplicableInd() {
		return cersaiApplicableInd;
	}
	public void setCersaiApplicableInd(String cersaiApplicableInd) {
		this.cersaiApplicableInd = cersaiApplicableInd;
	}

	public String getCoverageAmount() {
		return coverageAmount;
	}

	public void setCoverageAmount(String coverageAmount) {
		this.coverageAmount = coverageAmount;
	}

	public String getCoveragePercentage() {
		return coveragePercentage;
	}

	public void setCoveragePercentage(String coveragePercentage) {
		this.coveragePercentage = coveragePercentage;
	}

	public SecurityCoverageForm getSecurityCoverageForm() {
		return securityCoverageForm;
	}
	public void setSecurityCoverageForm(SecurityCoverageForm securityCoverageForm) {
		this.securityCoverageForm = securityCoverageForm;
	}
	
	private String bankingArrangement;
	public String getBankingArrangement() {
		return bankingArrangement;
	}

	public void setBankingArrangement(String bankingArrangement) {
		this.bankingArrangement = bankingArrangement;
	}


	public String getAdHocCoverageAmount() {
		return adHocCoverageAmount;
	}

	public void setAdHocCoverageAmount(String adHocCoverageAmount) {
		this.adHocCoverageAmount = adHocCoverageAmount;
	}


	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";

	private String termLoanOutstdAmt;
	private String marginAssetCover;
	private String recvGivenByClient;



	public String getTermLoanOutstdAmt() {
		return termLoanOutstdAmt;
	}

	public void setTermLoanOutstdAmt(String termLoanOutstdAmt) {
		this.termLoanOutstdAmt = termLoanOutstdAmt;
	}

	public String getMarginAssetCover() {
		return marginAssetCover;
	}

	public void setMarginAssetCover(String marginAssetCover) {
		this.marginAssetCover = marginAssetCover;
	}

	public String getRecvGivenByClient() {
		return recvGivenByClient;
	}

	public void setRecvGivenByClient(String recvGivenByClient) {
		this.recvGivenByClient = recvGivenByClient;
	}
	
	
}
