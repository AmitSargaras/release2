/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/OBCollateral.java,v 1.35 2006/09/15 08:30:10 hshii Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.customer.ILegalEntity;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.sharesecurity.bus.IShareSecurity;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranchDao;
import com.integrosys.cms.app.systemBankBranch.bus.OBSystemBankBranch;
import com.integrosys.base.techinfra.context.BeanHouse;
/**
 * This class represents a Collateral entity.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.35 $
 * @since $Date: 2006/09/15 08:30:10 $ Tag: $Name: $
 */
public class OBCollateral implements ICollateral {

	

	private static final long serialVersionUID = 1601764476219562654L;

	private long collateralID = ICMSConstant.LONG_MIN_VALUE;

	private ICollateralSubType collateralSubType;

	private ICollateralType collateralType;

	private IValuation valuation;

	private IValuation[] valuationHistory;

	private ILimitCharge[] limitCharges;

	private ICollateralLimitMap[] collateralLimits;

	private ICollateralPledgor[] pledgors;

	private boolean isLEByChargeRanking;

	private boolean isLEByJurisdiction;

	private boolean isLEByGovernLaws;

	private Date lEDateByChargeRanking;

	private Date lEDateByJurisdiction;

	private Date lEDateByGovernLaws;

	private String isLE;

	private Date lEDate;

	private String collateralLocation;

	private Date collateralMaturityDate;

	private String collateralCustodian;

	private String collateralCustodianType;

	private String cmvCcyCode;

	private Amount cmv;

	private String fsvCcyCode;

	private Amount fsv;

	private Amount fsvBalance;

	private boolean isPariPassu;

	private String isExchangeCtrlObtained = "O";

	private Date exchangeCtrlDate;

	private double margin = ICMSConstant.DOUBLE_INVALID_VALUE;

	private String sourceSecuritySubType;

	private boolean isPerfected;

	private Date perfectionDate;

	private Date lastValuationDate;

	private String lastValuer;

	private String currencyCode;

	private String sciCurrencyCode;

	private Date sciFSVDate;

	private Amount sciFSV;

	private String sciSubTypeValue;

	private String sciTypeValue;

	private String sciReferenceNote;

	private long sciBookingLocationID;

	private String sciSecurityID;

	private List secSystemName;

	private IInsurancePolicy[] insurancePolicies;
	
	private IAddtionalDocumentFacilityDetails[] additonalDocFacDetails;

	private Map insuranceMap = new HashMap();

	private String status;

	private long versionTime;

	private String securityOrganization;
	
	private ISystemBankBranch branchName;

	private List secApportionment;

	private String remargin;

	private Date lastRemarginDate;

	private Date nextRemarginDate;

	private String riskMitigationCategory;

	private String extSeniorLien;

	private IInstrument[] instrumentArray;

	private IShareSecurity[] shareSecArray;

	private List sourceSecIdAliases;

	private String comment;

	private boolean isBorrowerDependency;

	private Amount netRealisableAmount;

	private IValuation sourceValuation;

	private IValuation[] valuationFromLOS;

	private IValuation valuationIntoCMS;

	private String sourceId;

	private String collateralStatus;

	private boolean isCGCPledged;

	private String valuationType;

	private String valuer;

	private Character chargeInfoDrawAmountUsageIndicator;

	private Character chargeInfoPledgeAmountUsageIndicator;

	private String losCollateralRef;

	private Amount reservePrice;
	
	private float spread;

	private Date reservePriceDate;

	private Date createDate;

	private String toBeDischargedInd;

	
	private String collateralCode;
	
	private String secPriority;
	
	private ICashDeposit _entity = null;
	
	private String loanableAmount;
	
	private String lmtSecurityCoverage;
	
	private String propSearchId;
	
	private String typeOfCharge;
	
	//Stock DP Calculation
	private String bankingArrangement;
	
	public String getTypeOfCharge() {
		return typeOfCharge;
	}
	public void setTypeOfCharge(String typeOfCharge) {
		this.typeOfCharge = typeOfCharge;
	}

	//Added by Pramod Katkar for New Filed CR on 13-08-2013
	private String commonRevalFreq ;
	private String commonRevalFreqNo;
	private Date valuationDate ;
	private String valuationAmount;
	private Date nextValDate ;
	private String typeOfChange ;
	private String otherBankCharge ;	
	private String monitorProcess;
	private String monitorFrequency;
	
	//New General Fields
	private String primarySecurityAddress ;
	private Date securityValueAsPerCAM ;	
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
	private Date dateOfCersaiRegisteration;
	private String cersaiId;
	private Date saleDeedPurchaseDate;
	private String thirdPartyAddress;
	private String thirdPartyState;
	private String thirdPartyCity;
	private String thirdPartyPincode;
	
	private String fdRebooking;
	
	
	public String getPrimarySecurityAddress() {
		return primarySecurityAddress;
	}
	public void setPrimarySecurityAddress(String primarySecurityAddress) {
		this.primarySecurityAddress = primarySecurityAddress;
	}
	
	public Date getSecurityValueAsPerCAM() {
		return securityValueAsPerCAM;
	}
	public void setSecurityValueAsPerCAM(Date securityValueAsPerCAM) {
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
	public String getCommonRevalFreq() {
		return commonRevalFreq;
	}
	public void setCommonRevalFreq(String commonRevalFreq) {
		this.commonRevalFreq = commonRevalFreq;
	}
	public String getCommonRevalFreqNo() {
		return commonRevalFreqNo;
	}
	public void setCommonRevalFreqNo(String commonRevalFreqNo) {
		this.commonRevalFreqNo = commonRevalFreqNo;
	}
	public Date getValuationDate() {
		return valuationDate;
	}
	public void setValuationDate(Date valuationDate) {
		this.valuationDate = valuationDate;
	}
	public String getValuationAmount() {
		return valuationAmount;
	}
	public void setValuationAmount(String valuationAmount) {
		this.valuationAmount = valuationAmount;
	}
	public Date getNextValDate() {
		return nextValDate;
	}
	public void setNextValDate(Date nextValDate) {
		this.nextValDate = nextValDate;
	}
	public String getTypeOfChange() {
		return typeOfChange;
	}
	public void setTypeOfChange(String typeOfChange) {
		this.typeOfChange = typeOfChange;
	}

	//End by Pramod Katkar
	
	public String getPropSearchId() {
		return propSearchId;
	}

	public void setPropSearchId(String propSearchId) {
		this.propSearchId = propSearchId;
	}

	public String getLoanableAmount() {
		return loanableAmount;
	}

	public void setLoanableAmount(String loanableAmount) {
		this.loanableAmount = loanableAmount;
	}

	public String getCollateralCode() {
		return collateralCode;
	}

	public void setCollateralCode(String collateralCode) {
		this.collateralCode = collateralCode;
	}

	public String getSecPriority() {
		return secPriority;
	}

	public void setSecPriority(String secPriority) {
		this.secPriority = secPriority;
	}

	


	/**
	 * Default Constructor.
	 */
	public OBCollateral() {
		secApportionment = new ArrayList();
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ICollateral
	 */
	public OBCollateral(ICollateral obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof OBCollateral)) {
			return false;
		}
		else {
			if (obj.hashCode() == this.hashCode()) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	public Character getChargeInfoDrawAmountUsageIndicator() {
		return chargeInfoDrawAmountUsageIndicator;
	}

	public Character getChargeInfoPledgeAmountUsageIndicator() {
		return chargeInfoPledgeAmountUsageIndicator;
	}

	/**
	 * Get latest current market value.
	 * 
	 * @return Amount
	 */
	public Amount getCMV() {
		return cmv;
	}

	/**
	 * Get currency code for current market value.
	 * 
	 * @return String
	 */
	public String getCMVCcyCode() {
		return cmvCcyCode;
	}

	/**
	 * Get Security custodian.
	 * 
	 * @return String
	 */
	public String getCollateralCustodian() {
		return collateralCustodian;
	}

	/**
	 * Get security custodian type, internal or external.
	 * 
	 * @return String
	 */
	public String getCollateralCustodianType() {
		return collateralCustodianType;
	}

	/**
	 * Get collateral ID.
	 * 
	 * @return long
	 */
	public long getCollateralID() {
		return collateralID;
	}

	/**
	 * Get limits associated to this collateral.
	 * 
	 * @return ICollateralLimitMap[]
	 */
	public ICollateralLimitMap[] getCollateralLimits() {
		return collateralLimits;
	}

	/**
	 * Get security location.
	 * 
	 * @return String
	 */
	public String getCollateralLocation() {
		return collateralLocation;
	}

	/**
	 * Get security maturity date.
	 * 
	 * @return Date
	 */
	public Date getCollateralMaturityDate() {
		return collateralMaturityDate;
	}

	public String getCollateralStatus() {
		return collateralStatus;
	}

	/**
	 * Get subtype of this collateral.
	 * 
	 * @return ICollateralSubType
	 */
	public ICollateralSubType getCollateralSubType() {
		return collateralSubType;
	}

	/**
	 * Get type of this collateral.
	 * 
	 * @return ICollateralType
	 */
	public ICollateralType getCollateralType() {
		return collateralType;
	}

	public String getComment() {
		return comment;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	/**
	 * Get security currency code from SCI.
	 * 
	 * @return String
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * Get filtered collateral limit maps.
	 * 
	 * @return ICollateralLimitMap[]
	 */
	public ICollateralLimitMap[] getCurrentCollateralLimits() {
		if ((collateralLimits == null) || (collateralLimits.length == 0)) {
			return collateralLimits;
		}

		HashMap hashMap = new HashMap();
		CollateralLimitMapComparator comp = new CollateralLimitMapComparator();

		for (int i = 0; i < collateralLimits.length; i++) {
			String limitID = "";
			if (ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER.equals(collateralLimits[i].getCustomerCategory())) {
				limitID = collateralLimits[i].getSCILimitID();
			}
			else if (ICMSConstant.CUSTOMER_CATEGORY_CO_BORROWER.equals(collateralLimits[i].getCustomerCategory())) {
				limitID = collateralLimits[i].getSCICoBorrowerLimitID();
			}
			ICollateralLimitMap secMap = (ICollateralLimitMap) hashMap.get(limitID);
			if ((secMap != null) && (comp.compare(secMap, collateralLimits[i]) > 0)) {
				continue;
			}

			hashMap.put(limitID, collateralLimits[i]);
		}
		return (OBCollateralLimitMap[]) hashMap.values().toArray(new OBCollateralLimitMap[0]);
	}

	public Date getExchangeCtrlDate() {
		return exchangeCtrlDate;
	}

	public String getExtSeniorLien() {
		return extSeniorLien;
	}

	/**
	 * Get latest forced sale value.
	 * 
	 * @return Amount
	 */
	public Amount getFSV() {
		return fsv;
	}

	/**
	 * Get FSV balance after collateral allocation.
	 * 
	 * @return Amount
	 */
	public Amount getFSVBalance() {
		return fsvBalance;
	}

	/**
	 * Get currency code for forced sale value.
	 * 
	 * @return String
	 */
	public String getFSVCcyCode() {
		return fsvCcyCode;
	}

	public IInstrument[] getInstrumentArray() {
		return instrumentArray;
	}

	/**
	 * Get insurance map.
	 * 
	 * @return a map with key as String reference id and value as
	 *         IInsurancePolicy
	 */
	public Map getInsurance() {
		return insuranceMap;
	}

	/**
	 * Get a list of insurance policies.
	 * 
	 * @return IInsurancePolicy[]
	 */
	public IInsurancePolicy[] getInsurancePolicies() {
		// if (insuranceMap != null && !insuranceMap.isEmpty()) {
		// return (IInsurancePolicy[]) insuranceMap.values().toArray (new
		// OBInsurancePolicy[0]);
		// }
		return insurancePolicies;
	}

	public boolean getIsBorrowerDependency() {
		return isBorrowerDependency;
	}

	public boolean getIsCGCPledged() {
		return isCGCPledged;
	}

	/**
	 * Get if exchange control approval obtained.
	 * 
	 * @return String
	 */
	public String getIsExchangeCtrlObtained() {
		return isExchangeCtrlObtained;
	}

	/**
	 * Get if the Legal Enforceability .
	 * 
	 * @return boolean
	 */
	public String getIsLE() {
		return isLE;
	}

	/**
	 * Get if the Legal Enforceability by charge ranking.
	 * 
	 * @return boolean
	 */
	public boolean getIsLEByChargeRanking() {
		return isLEByChargeRanking;
	}

	/**
	 * Get if the Legal Enforceability by governing laws.
	 * 
	 * @return boolean
	 */
	public boolean getIsLEByGovernLaws() {
		return isLEByGovernLaws;
	}

	/**
	 * Get if the Legal Enforceability by jurisdiction.
	 * 
	 * @return boolean
	 */
	public boolean getIsLEByJurisdiction() {
		return isLEByJurisdiction;
	}

	/**
	 * Get if it is pari passu.
	 * 
	 * @return boolean
	 */
	public boolean getIsPariPassu() {
		return isPariPassu;
	}

	/**
	 * Get if security is perfected.
	 * 
	 * @return boolean
	 */
	public boolean getIsPerfected() {
		return isPerfected;
	}

	public Date getLastRemarginDate() {
		return lastRemarginDate;
	}

	/**
	 * Get last valuation date.
	 * 
	 * @return Date
	 */
	public Date getLastValuationDate() {
		return lastValuationDate;
	}

	/**
	 * Get last valuer name.
	 * 
	 * @return String
	 */
	public String getLastValuer() {
		return lastValuer;
	}

	/**
	 * Get legal enforceability date .
	 * 
	 * @return Date
	 */
	public Date getLEDate() {
		return lEDate;
	}

	/**
	 * Get legal enforceability date by charge ranking.
	 * 
	 * @return Date
	 */
	public Date getLEDateByChargeRanking() {
		return lEDateByChargeRanking;
	}

	/**
	 * Get legal enforceability date by governing laws.
	 * 
	 * @return Date
	 */
	public Date getLEDateByGovernLaws() {
		return lEDateByGovernLaws;
	}

	/**
	 * Get legal enforceability date by jurisdiction.
	 * 
	 * @return Date
	 */
	public Date getLEDateByJurisdiction() {
		return lEDateByJurisdiction;
	}

	/**
	 * Get collateral limit charges.
	 * 
	 * @return a list of limit charges
	 */
	public ILimitCharge[] getLimitCharges() {
		return limitCharges;
	}

	public String getLosCollateralRef() {
		return losCollateralRef;
	}

	/**
	 * Get margin value.
	 * 
	 * @return double
	 */
	public double getMargin() {
		return margin;
	}

	public Amount getNetRealisableAmount() {
		return netRealisableAmount;
	}

	public Date getNextRemarginDate() {
		return nextRemarginDate;
	}

	/**
	 * Get security perfection date.
	 * 
	 * @return Date
	 */
	public Date getPerfectionDate() {
		return perfectionDate;
	}

	/**
	 * Get pledgors of the collateral.
	 * 
	 * @return a list of pledgors
	 */
	public ICollateralPledgor[] getPledgors() {
		return pledgors;
	}

	public String getRemargin() {
		return remargin;
	}

	public Amount getReservePrice() {
		return reservePrice;
	}

	public Date getReservePriceDate() {
		return reservePriceDate;
	}

	public String getRiskMitigationCategory() {
		return riskMitigationCategory;
	}

	public float getSpread() {
		return spread;
	}

	public void setSpread(float spread) {
		this.spread = spread;
	}

	/**
	 * Get security booking location from SCI.
	 * 
	 * @return long
	 */
	public long getSCIBookingLocationID() {
		return sciBookingLocationID;
	}

	/**
	 * Get currency code from SCI.
	 * 
	 * @return String
	 */
	public String getSCICurrencyCode() {
		return sciCurrencyCode;
	}

	/**
	 * Get force sale value from SCI.
	 * 
	 * @return Amount
	 */
	public Amount getSCIFSV() {
		return sciFSV;
	}

	/**
	 * Get force sale value date from SCI.
	 * 
	 * @return Date
	 */
	public Date getSCIFSVDate() {
		return sciFSVDate;
	}

	/**
	 * Get reference note from SCI.
	 * 
	 * @return String
	 */
	public String getSCIReferenceNote() {
		return sciReferenceNote;
	}

	/**
	 * Get security reference id from SCI.
	 * 
	 * @return String
	 */
	public String getSCISecurityID() {
		return sciSecurityID;
	}

	/**
	 * Set security subtype value from SCI.
	 * 
	 * @return String
	 */
	public String getSCISubTypeValue() {
		return sciSubTypeValue;
	}

	/**
	 * Get security type from SCI.
	 * 
	 * @return String
	 */
	public String getSCITypeValue() {
		return sciTypeValue;
	}

	public List getSecApportionment() {
		return secApportionment;
	}

	public List getSecSystemName() {
		return secSystemName;
	}

	public String getSecurityOrganization() {
		return securityOrganization;
	}

	public IShareSecurity[] getShareSecArray() {
		return shareSecArray;
	}

	public String getSourceId() {
		return sourceId;
	}

	/**
	 * @return Returns the sourceSecIdAliases.
	 */
	public List getSourceSecIdAliases() {
		return sourceSecIdAliases;
	}

	public String getSourceSecuritySubType() {
		return sourceSecuritySubType;
	}

	public IValuation getSourceValuation() {
		return sourceValuation;
	}

	/**
	 * Get the status of the collateral.
	 * 
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	public String getToBeDischargedInd() {
		return toBeDischargedInd;
	}

	/**
	 * Get collateral valuation.
	 * 
	 * @return IValuation
	 */
	public IValuation getValuation() {
		return valuation;
	}

	public IValuation[] getValuationFromLOS() {
		return valuationFromLOS;
	}

	/**
	 * Get collateral's valuation history.
	 * 
	 * @return a list of valuation history
	 */
	public IValuation[] getValuationHistory() {
		return valuationHistory;
	}

	public IValuation getValuationIntoCMS() {
		return valuationIntoCMS;
	}

	public String getValuationType() {
		return valuationType;
	}

	public String getValuer() {
		return valuer;
	}

	/**
	 * Get the version of the collateral.
	 * 
	 * @return long
	 */
	public long getVersionTime() {
		return versionTime;
	}

	/**
	 * Return the hash code
	 * 
	 * @return int
	 */
	public int hashCode() {
		String hash = String.valueOf(collateralID);
		return hash.hashCode();
	}

	public void setChargeInfoDrawAmountUsageIndicator(Character chargeInfoDrawAmountUsageIndicator) {
		this.chargeInfoDrawAmountUsageIndicator = chargeInfoDrawAmountUsageIndicator;
	}

	public void setChargeInfoPledgeAmountUsageIndicator(Character chargeInfoPledgeAmountUsageIndicator) {
		this.chargeInfoPledgeAmountUsageIndicator = chargeInfoPledgeAmountUsageIndicator;
	}

	/**
	 * Set latest current market value.
	 * 
	 * @param cmv of type Amount
	 */
	public void setCMV(Amount cmv) {
		this.cmv = cmv;
	}

	/**
	 * Set currency code for current market value.
	 * 
	 * @param cmvCcyCode of type String
	 */
	public void setCMVCcyCode(String cmvCcyCode) {
		this.cmvCcyCode = cmvCcyCode;
	}

	/**
	 * Set Security custodian.
	 * 
	 * @param collateralCustodian is of type String
	 */
	public void setCollateralCustodian(String collateralCustodian) {
		this.collateralCustodian = collateralCustodian;
	}

	/**
	 * Set security custodian type, internal or external.
	 * 
	 * @param collateralCustodianType of type String
	 */
	public void setCollateralCustodianType(String collateralCustodianType) {
		this.collateralCustodianType = collateralCustodianType;
	}

	/**
	 * set collateral ID.
	 * 
	 * @param collateralID is of type long
	 */
	public void setCollateralID(long collateralID) {
		this.collateralID = collateralID;
	}

	/**
	 * Set limits associated to this collateral.
	 * 
	 * @param collateralLimits of type ICollateralLimitMap[]
	 */
	public void setCollateralLimits(ICollateralLimitMap[] collateralLimits) {
		this.collateralLimits = collateralLimits;
	}

	/**
	 * Set security location.
	 * 
	 * @param collateralLocation is of type String
	 */
	public void setCollateralLocation(String collateralLocation) {
		this.collateralLocation = collateralLocation;
	}

	/**
	 * Set security maturity date.
	 * 
	 * @param collateralMaturityDate is of type Date
	 */
	public void setCollateralMaturityDate(Date collateralMaturityDate) {
		this.collateralMaturityDate = collateralMaturityDate;
	}

	public void setCollateralStatus(String collateralStatus) {
		this.collateralStatus = collateralStatus;
	}

	/**
	 * Set subtype of this collateral.
	 * 
	 * @param collateralSubType is of type ICollateralSubType
	 */
	public void setCollateralSubType(ICollateralSubType collateralSubType) {
		this.collateralSubType = collateralSubType;
	}

	/**
	 * Set the collateral type.
	 * 
	 * @param collateralType is of type ICollateralType
	 */
	public void setCollateralType(ICollateralType collateralType) {
		this.collateralType = collateralType;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Set security currency code from SCI.
	 * 
	 * @param currencyCode of type String
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public void setExchangeCtrlDate(Date exchangeCtrlDate) {
		this.exchangeCtrlDate = exchangeCtrlDate;
	}

	public void setExtSeniorLien(String extSeniorLien) {
		this.extSeniorLien = extSeniorLien;
	}

	/**
	 * Set latest forced sale value.
	 * 
	 * @param fsv of type Amount
	 */
	public void setFSV(Amount fsv) {
		this.fsv = fsv;
	}

	/**
	 * Set FSV balance after collateral allocation.
	 * 
	 * @param fsvBalance of type Amount
	 */
	public void setFSVBalance(Amount fsvBalance) {
		this.fsvBalance = fsvBalance;
	}

	/**
	 * Set currency code for forced sale value.
	 * 
	 * @param fsvCcyCode of type String
	 */
	public void setFSVCcyCode(String fsvCcyCode) {
		this.fsvCcyCode = fsvCcyCode;
	}

	public void setInstrumentArray(IInstrument[] instrumentArray) {
		this.instrumentArray = instrumentArray;
	}

	/**
	 * Set insurance map.
	 * 
	 * @param insurance a map with key reference id and value IInsurancePolicy
	 */
	public void setInsurance(Map insurance) {
		insuranceMap = insurance;
	}

	/**
	 * Set a list of insurance policies.
	 * 
	 * @param insurancePolicies of type IInsurancePolicy[]
	 */
	public void setInsurancePolicies(IInsurancePolicy[] insurancePolicies) {
		this.insurancePolicies = insurancePolicies;
	}

	public void setIsBorrowerDependency(boolean isBorrowerDependency) {
		this.isBorrowerDependency = isBorrowerDependency;
	}

	public void setIsCGCPledged(boolean isCGCPledged) {
		this.isCGCPledged = isCGCPledged;
	}

	/**
	 * Set if exchange control approval obtained.
	 * 
	 * @param isExchangeCtrlObtained of type String
	 */
	public void setIsExchangeCtrlObtained(String isExchangeCtrlObtained) {
		this.isExchangeCtrlObtained = isExchangeCtrlObtained;
	}

	/**
	 * Set the legal enforceability .
	 * 
	 * @param isLE is of type boolean
	 */
	public void setIsLE(String isLE) {
		this.isLE = isLE;
	}

	/**
	 * Set the legal enforceability by charge ranking.
	 * 
	 * @param isLEByChargeRanking is of type boolean
	 */
	public void setIsLEByChargeRanking(boolean isLEByChargeRanking) {
		this.isLEByChargeRanking = isLEByChargeRanking;
	}

	/**
	 * Set if the legal enforceability by governing laws.
	 * 
	 * @param isLEByGovernLaws is of type boolean
	 */
	public void setIsLEByGovernLaws(boolean isLEByGovernLaws) {
		this.isLEByGovernLaws = isLEByGovernLaws;
	}

	/**
	 * Set the legal enforceability by jurisdiction.
	 * 
	 * @param isLEByJurisdiction is of type boolean
	 */
	public void setIsLEByJurisdiction(boolean isLEByJurisdiction) {
		this.isLEByJurisdiction = isLEByJurisdiction;
	}

	/**
	 * Set if it is pari passu.
	 * 
	 * @param isPariPassu of type boolean
	 */
	public void setIsPariPassu(boolean isPariPassu) {
		this.isPariPassu = isPariPassu;
	}

	/**
	 * Set if the security is perfected.
	 * 
	 * @param isPerfected of type boolean
	 */
	public void setIsPerfected(boolean isPerfected) {
		this.isPerfected = isPerfected;
	}

	public void setLastRemarginDate(Date lastRemarginDate) {
		this.lastRemarginDate = lastRemarginDate;
	}

	/**
	 * Set last valuation date.
	 * 
	 * @param lastValuationDate of type Date
	 */
	public void setLastValuationDate(Date lastValuationDate) {
		this.lastValuationDate = lastValuationDate;
	}

	/**
	 * Set last valuer name.
	 * 
	 * @param lastValuer of type String
	 */
	public void setLastValuer(String lastValuer) {
		this.lastValuer = lastValuer;
	}

	public void setLEDate(Date lEDate) {
		this.lEDate = lEDate;
	}

	/**
	 * Set legal enforceability date by charge ranking.
	 * 
	 * @param lEDateByChargeRanking is of type Date
	 */
	public void setLEDateByChargeRanking(Date lEDateByChargeRanking) {
		this.lEDateByChargeRanking = lEDateByChargeRanking;
	}

	/**
	 * Set legal enforceability date by governing laws.
	 * 
	 * @param lEDateByGovernLaws is of type Date
	 */
	public void setLEDateByGovernLaws(Date lEDateByGovernLaws) {
		this.lEDateByGovernLaws = lEDateByGovernLaws;
	}

	/**
	 * Get legal enforceability date by jurisdiction.
	 * 
	 * @param lEDateByJurisdiction is of type Date
	 */
	public void setLEDateByJurisdiction(Date lEDateByJurisdiction) {
		this.lEDateByJurisdiction = lEDateByJurisdiction;
	}

	/**
	 * Set collateral limit charges.
	 * 
	 * @param limitCharges is of type ILimitCharge[]
	 */
	public void setLimitCharges(ILimitCharge[] limitCharges) {
		this.limitCharges = limitCharges;
	}

	public void setLosCollateralRef(String losCollateralRef) {
		this.losCollateralRef = losCollateralRef;
	}

	/**
	 * Set margin value.
	 * 
	 * @param margin of type double
	 */
	public void setMargin(double margin) {
		this.margin = margin;
	}

	public void setNetRealisableAmount(Amount amt) {
		netRealisableAmount = amt;
	}

	public void setNextRemarginDate(Date nextRemarginDate) {
		this.nextRemarginDate = nextRemarginDate;
	}

	/**
	 * Set security perfection date.
	 * 
	 * @param perfectionDate of type Date
	 */
	public void setPerfectionDate(Date perfectionDate) {
		this.perfectionDate = perfectionDate;
	}

	/**
	 * Set pledgors of the collateral.
	 * 
	 * @param pledgors is of type ICollateralPledgor[]
	 */
	public void setPledgors(ICollateralPledgor[] pledgors) {
		this.pledgors = pledgors;
	}

	public void setRemargin(String remargin) {
		this.remargin = remargin;
	}

	public void setReservePrice(Amount reservePrice) {
		this.reservePrice = reservePrice;
	}

	public void setReservePriceDate(Date reservePriceDate) {
		this.reservePriceDate = reservePriceDate;
	}

	public void setRiskMitigationCategory(String riskMitigationCategory) {
		this.riskMitigationCategory = riskMitigationCategory;
	}

	/**
	 * Set security booking location from SCI.
	 * 
	 * @param sciBookingLocationID of type long
	 */
	public void setSCIBookingLocationID(long sciBookingLocationID) {
		this.sciBookingLocationID = sciBookingLocationID;
	}

	/**
	 * Set SCI currency code.
	 * 
	 * @param sciCurrencyCode of type String
	 */
	public void setSCICurrencyCode(String sciCurrencyCode) {
		this.sciCurrencyCode = sciCurrencyCode;
	}

	/**
	 * Set force sale value from SCI.
	 * 
	 * @param sciFSV of type Amount
	 */
	public void setSCIFSV(Amount sciFSV) {
		this.sciFSV = sciFSV;
	}

	/**
	 * Set force sale value date from SCI.
	 * 
	 * @param sciFSVDate of type Date
	 */
	public void setSCIFSVDate(Date sciFSVDate) {
		this.sciFSVDate = sciFSVDate;
	}

	/**
	 * Set reference note from SCI.
	 * 
	 * @param sciReferenceNote of type String
	 */
	public void setSCIReferenceNote(String sciReferenceNote) {
		this.sciReferenceNote = sciReferenceNote;
	}

	/**
	 * Set security reference id from SCI.
	 * 
	 * @param sciSecurityID of type String
	 */
	public void setSCISecurityID(String sciSecurityID) {
		this.sciSecurityID = sciSecurityID;
	}

	/**
	 * Set security subtype value from SCI.
	 * 
	 * @param sciSubTypeValue of type String
	 */
	public void setSCISubTypeValue(String sciSubTypeValue) {
		this.sciSubTypeValue = sciSubTypeValue;
	}

	/**
	 * Set security type value from SCI.
	 * 
	 * @param sciTypeValue of type String
	 */
	public void setSCITypeValue(String sciTypeValue) {
		this.sciTypeValue = sciTypeValue;
	}

	public void setSecApportionment(List apportionment) {
		secApportionment = apportionment;
	}

	public void setSecSystemName(List secSystemName) {
		this.secSystemName = secSystemName;
	}

	public void setSecurityOrganization(String org) {
		securityOrganization = org;
	}

	public void setShareSecArray(IShareSecurity[] shareSecArray) {
		this.shareSecArray = shareSecArray;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	/**
	 * @param sourceSecIdAliases The sourceSecIdAliases to set.
	 */
	public void setSourceSecIdAliases(List sourceSecIdAliases) {
		this.sourceSecIdAliases = sourceSecIdAliases;
	}

	public void setSourceSecuritySubType(String sourceSecuritySubType) {
		this.sourceSecuritySubType = sourceSecuritySubType;
	}

	public void setSourceValuation(IValuation sourceValuation) {
		this.sourceValuation = sourceValuation;
	}

	/**
	 * Set the status of the collateral
	 * 
	 * @param status is of type String
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public void setToBeDischargedInd(String toBeDischargedInd) {
		this.toBeDischargedInd = toBeDischargedInd;
	}

	/**
	 * Set collateral valuation.
	 * 
	 * @param valuation is of type IValuation
	 */
	public void setValuation(IValuation valuation) {
		this.valuation = valuation;
	}

	public void setValuationFromLOS(IValuation[] valuationFromLOS) {
		this.valuationFromLOS = valuationFromLOS;
	}

	/**
	 * Set collateral's valuation history.
	 * 
	 * @param valuationHistory is of type IValuation[]
	 */
	public void setValuationHistory(IValuation[] valuationHistory) {
		this.valuationHistory = valuationHistory;
	}

	public void setValuationIntoCMS(IValuation valuationIntoCMS) {
		this.valuationIntoCMS = valuationIntoCMS;
	}

	public void setValuationType(String valuationType) {
		this.valuationType = valuationType;
	}

	public String getLmtSecurityCoverage() {
		return lmtSecurityCoverage;
	}

	public void setLmtSecurityCoverage(String lmtSecurityCoverage) {
		this.lmtSecurityCoverage = lmtSecurityCoverage;
	}

	public void setValuer(String valuer) {
		this.valuer = valuer;
	}

	/**
	 * Set the version of the collateral.
	 * 
	 * @param versionTime is of type long
	 */
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("OBCollateral [");
		buf.append("collateralID=");
		buf.append(collateralID);
		buf.append(", losCollateralRef=");
		buf.append(losCollateralRef);
		buf.append(", sciSecurityID=");
		buf.append(sciSecurityID);
		buf.append(", collateralLocation=");
		buf.append(collateralLocation);
		buf.append(", securityOrganization=");
		buf.append(securityOrganization);
		buf.append(", sourceId=");
		buf.append(sourceId);
		buf.append(", collateralMaturityDate=");
		buf.append(collateralMaturityDate);
		buf.append(", collateralStatus=");
		buf.append(collateralStatus);
		buf.append(", collateralSubType=");
		buf.append(collateralSubType);
		buf.append(", collateralType=");
		buf.append(collateralType);
		buf.append(", currencyCode=");
		buf.append(currencyCode);
		buf.append(", isCGCPledged=");
		buf.append(isCGCPledged);
		buf.append(", sciReferenceNote=");
		buf.append(sciReferenceNote);
		buf.append(", sourceSecuritySubType=");
		buf.append(sourceSecuritySubType);
		buf.append(", status=");
		buf.append(status);
		buf.append(", toBeDischargedInd=");
		buf.append(toBeDischargedInd);
		buf.append("]");
		return buf.toString();
	}
	
	
	
	
	
	
	

//	public ISystemBankBranch getBranchName(){
//		 long branchId;
//            if(getSecurityOrganization()!=null && !getSecurityOrganization().trim().equals("")){
//			 branchId = Long.parseLong(getSecurityOrganization());
//            }else {
//            	 return null;	
//            }
//            	
//			if (branchId == 0) {
//
//			 return null;
//			}
//			
//			 OBSystemBankBranch bank = null;;
//				
//			ISystemBankBranchDao systembankbranch = (ISystemBankBranchDao)BeanHouse.get("systemBankBranchDao");
//			return	systembankbranch.getSystemBankBranch(ISystemBankBranchDao.ACTUAL_SYSTEM_BANK_BRANCH_NAME,new Long(branchId));
//		         
//	       }
//	  
//	public void setBranchName(ISystemBankBranch branchName) {
//			
//          if (null == branchName) {
//        	  this.branchName=branchName;
//        	 // setBranchName(null);
//	                    }
//	 else {
//		setSecurityOrganization(String.valueOf(branchName.getId()));
//	      }
//       }

	public ICashDeposit get_entity() {
		return _entity;
	}

	public void set_entity(ICashDeposit _entity) {
		this._entity = _entity;
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

	public Date getDateOfCersaiRegisteration() {
		return dateOfCersaiRegisteration;
	}
	public void setDateOfCersaiRegisteration(Date dateOfCersaiRegisteration) {
		this.dateOfCersaiRegisteration = dateOfCersaiRegisteration;
	}

	public String getCersaiId() {
		return cersaiId;
	}
	public void setCersaiId(String cersaiId) {
		this.cersaiId = cersaiId;
	}

	public Date getSaleDeedPurchaseDate() {
		return saleDeedPurchaseDate;
	}
	public void setSaleDeedPurchaseDate(Date saleDeedPurchaseDate) {
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
	
	public IAddtionalDocumentFacilityDetails[] getAdditonalDocFacDetails() {
		
		return additonalDocFacDetails;
	}
	
	public void setAdditonalDocFacDetails(IAddtionalDocumentFacilityDetails[] additonalDocFacDetails) {
		this.additonalDocFacDetails = additonalDocFacDetails;
		
	}
	
		List<ISecurityCoverage> securityCoverage;
	public List<ISecurityCoverage> getSecurityCoverage() {
		return securityCoverage;
	}
	public void setSecurityCoverage(List<ISecurityCoverage> securityCoverage) {
		this.securityCoverage = securityCoverage;
	}
	public String getBankingArrangement() {
		return bankingArrangement;
	}
	public void setBankingArrangement(String bankingArrangement) {
		this.bankingArrangement = bankingArrangement;
	}
	
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