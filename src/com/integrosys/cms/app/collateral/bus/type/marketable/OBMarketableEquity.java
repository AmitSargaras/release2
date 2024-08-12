/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/marketable/OBMarketableEquity.java,v 1.15 2004/02/06 04:32:46 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.marketable;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.type.marketable.linedetail.IMarketableEquityLineDetail;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents marketable item for all marketable collaterals.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.15 $
 * @since $Date: 2004/02/06 04:32:46 $ Tag: $Name: $
 */
public class OBMarketableEquity implements IMarketableEquity {
	private long equityID = ICMSConstant.LONG_MIN_VALUE;

	private long refID = ICMSConstant.LONG_MIN_VALUE;

	private String equityType;

	private String certificateNo;

	private String registeredName;
	//Start- Added by Uma Khot- CAM QUARTER ACTIVITY CR: allow decimal in NoofUnits for Security Sub-type ‘Mutual Funds’.
	private double noOfUnits;
	//End- Added by Uma Khot- CAM QUARTER ACTIVITY CR: allow decimal in NoofUnits for Security Sub-type ‘Mutual Funds’.
	private Amount cmv;

	private String cmvCcyCode;

	private Amount fsv;

	private String fsvCcyCode;

	private Amount nominalValue;

	private String agentName;

	private Date agentConfirmDate;

	private String settlementOrganisation;

	private Date bondIssueDate;

	private Date bondMaturityDate;

	// private String currencyCode;
	private String itemCurrencyCode;

	private String isExchangeCtrlObtained;

	private Date exchangeCtrlDate;

	private String baselCompliant;

	private boolean isGuaranteeByGovt;

	private String governmentName;

	private String ric;

	private String nameOfIndex;

	private String stockExchange;

	private String stockExchangeCountry;

	// private String localStockExchange;
	private String leadManager;

	private String issuerName;

	private String issuerIdType;

	private boolean isCollateralBlacklisted;

	private boolean isLocalStockExchange;

	private Date collateralMaturityDate;

	private String collateralCustodian;

	private String collateralCustodianType;

	private Amount unitPrice;

	private String unitPriceCcyCode;

    private Amount valuationUnitPrice;

    private String valuationUnitPriceCcyCode;

	private String bondRating;

	private String status = ICMSConstant.STATE_ACTIVE;

	private String isinCode;

	private String stockCode;

	private String recognizeExchange;

	private String clientCode;

	private String cdsNumber;

	private Amount exercisePrice;

	private String brokerName;

	private Date leDate;
	
	private IMarketableEquityLineDetail[] lineDetails;

	public Date getLeDate() {
		return leDate;
	}

	public void setLeDate(Date leDate) {
		this.leDate = leDate;
	}

	private IMarketableEquityDetail[] equityDetails;

	private String equityUniqueID;

	/**
	 * Default Constructor.
	 */
	public OBMarketableEquity() {
		super();
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IMarketableEquity
	 */
	public OBMarketableEquity(IMarketableEquity obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get marketable equity id.
	 * 
	 * @return long
	 */
	public long getEquityID() {
		return equityID;
	}

	/**
	 * Set marketable equity id.
	 * 
	 * @param equityID of type long
	 */
	public void setEquityID(long equityID) {
		this.equityID = equityID;
	}

	/**
	 * Get reference id between staging and actual data.
	 * 
	 * @return long
	 */
	public long getRefID() {
		return refID;
	}

	/**
	 * set reference id between staging and actual data.
	 * 
	 * @param refID of type long
	 */
	public void setRefID(long refID) {
		this.refID = refID;
	}

	/**
	 * Get equity type.
	 * 
	 * @return String
	 */
	public String getEquityType() {
		return equityType;
	}

	/**
	 * Set equity type.
	 * 
	 * @param equityType of type String
	 */
	public void setEquityType(String equityType) {
		this.equityType = equityType;
	}

	/**
	 * Get certificate number.
	 * 
	 * @return String
	 */
	public String getCertificateNo() {
		return certificateNo;
	}

	/**
	 * Set certificate number.
	 * 
	 * @param certificateNo of type String
	 */
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	/**
	 * Get registered name.
	 * 
	 * @return String
	 */
	public String getRegisteredName() {
		return registeredName;
	}

	/**
	 * Set registered name.
	 * 
	 * @param registeredName of type String
	 */
	public void setRegisteredName(String registeredName) {
		this.registeredName = registeredName;
	}

	/**
	 * Get number of units.
	 * 
	 * @return long
	 */
	 //Start- Added by Uma Khot- CAM QUARTER ACTIVITY CR: allow decimal in NoofUnits for Security Sub-type ‘Mutual Funds’.
	public double getNoOfUnits() {
		return noOfUnits;
	}

	/**
	 * Set number of units.
	 * 
	 * @param noOfUnits of type long
	 */
	public void setNoOfUnits(double noOfUnits) {
		this.noOfUnits = noOfUnits;
	}
//End- Added by Uma Khot- CAM QUARTER ACTIVITY CR: allow decimal in NoofUnits for Security Sub-type ‘Mutual Funds’.
	/**
	 * Get unit price/ current market value.
	 * 
	 * @return Amount
	 */
	public Amount getCMV() {
		return cmv;
	}

	/**
	 * Set unit price/ current market value.
	 * 
	 * @param cmv of type Amount
	 */
	public void setCMV(Amount cmv) {
		this.cmv = cmv;
	}

	/**
	 * Get currency code for unit price/CMV.
	 * 
	 * @return String
	 */
	public String getCMVCcyCode() {
		return cmvCcyCode;
	}

	/**
	 * Set currency code for unit price/CMV.
	 * 
	 * @param cmvCcyCode of type String
	 */
	public void setCMVCcyCode(String cmvCcyCode) {
		this.cmvCcyCode = cmvCcyCode;
	}

	/**
	 * Get minimal forced sale value.
	 * 
	 * @return Amount
	 */
	public Amount getFSV() {
		return fsv;
	}

	/**
	 * Set minimal forced sale value.
	 * 
	 * @param fsv of type Amount
	 */
	public void setFSV(Amount fsv) {
		this.fsv = fsv;
	}

	/**
	 * Get currency code for FSV.
	 * 
	 * @return String
	 */
	public String getFSVCcyCode() {
		return fsvCcyCode;
	}

	/**
	 * Set currency code for FSV.
	 * 
	 * @param fsvCcyCode of type String
	 */
	public void setFSVCcyCode(String fsvCcyCode) {
		this.fsvCcyCode = fsvCcyCode;
	}

	/**
	 * Get nominal value.
	 * 
	 * @return Amount
	 */
	public Amount getNominalValue() {
		return nominalValue;
	}

	/**
	 * Set nominal value.
	 * 
	 * @param nominalValue of type Amount
	 */
	public void setNominalValue(Amount nominalValue) {
		this.nominalValue = nominalValue;
	}

	/**
	 * Get controller/nominee/agent name.
	 * 
	 * @return String
	 */
	public String getAgentName() {
		return agentName;
	}

	/**
	 * Set controller/nominee/bank's agent name.
	 * 
	 * @param agentName of type String
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	/**
	 * Get confirmation date from controller/nominee/agent's bank.
	 * 
	 * @return Date
	 */
	public Date getAgentConfirmDate() {
		return agentConfirmDate;
	}

	/**
	 * Set confirmation date from controller/nominee/agent's bank.
	 * 
	 * @param agentConfirmDate of type Date
	 */
	public void setAgentConfirmDate(Date agentConfirmDate) {
		this.agentConfirmDate = agentConfirmDate;
	}

	/**
	 * Get settlement organisation.
	 * 
	 * @return String
	 */
	public String getSettlementOrganisation() {
		return settlementOrganisation;
	}

	/**
	 * Set settlement organisation.
	 * 
	 * @param settlementOrganisation of type String
	 */
	public void setSettlementOrganisation(String settlementOrganisation) {
		this.settlementOrganisation = settlementOrganisation;
	}

	/**
	 * Get bond issue date.
	 * 
	 * @return Date
	 */
	public Date getBondIssueDate() {
		return bondIssueDate;
	}

	/**
	 * Set bond issue date.
	 * 
	 * @param bondIssueDate of type Date
	 */
	public void setBondIssueDate(Date bondIssueDate) {
		this.bondIssueDate = bondIssueDate;
	}

	/**
	 * Get bond maturity date.
	 * 
	 * @return Date
	 */
	public Date getBondMaturityDate() {
		return bondMaturityDate;
	}

	/**
	 * Set bond maturity date.
	 * 
	 * @param bondMaturityDate of type Date
	 */
	public void setBondMaturityDate(Date bondMaturityDate) {
		this.bondMaturityDate = bondMaturityDate;
	}

	/**
	 * Get currency code.
	 * 
	 * @return String
	 */
	public String getItemCurrencyCode() {
		return itemCurrencyCode;
	}

	/**
	 * Set currency code.
	 * 
	 * @param itemCurrencyCode of type String
	 */
	public void setItemCurrencyCode(String itemCurrencyCode) {
		this.itemCurrencyCode = itemCurrencyCode;
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
	 * Set if exchange control approval obtained.
	 * 
	 * @param isExchangeCtrlObtained of type String
	 */
	public void setIsExchangeCtrlObtained(String isExchangeCtrlObtained) {
		this.isExchangeCtrlObtained = isExchangeCtrlObtained;
	}

	/**
	 * Get Basel Compliant unit trust collateral.
	 * 
	 * @return String
	 */
	public String getBaselCompliant() {
		return baselCompliant;
	}

	/**
	 * Set Basel Compliant unit trust collateral.
	 * 
	 * @param baselCompliant of type String
	 */
	public void setBaselCompliant(String baselCompliant) {
		this.baselCompliant = baselCompliant;
	}

	/**
	 * Get if guarantee is by government.
	 * 
	 * @return boolean
	 */
	public boolean getIsGuaranteeByGovt() {
		return isGuaranteeByGovt;
	}

	/**
	 * Set if guarantee is by government.
	 * 
	 * @param isGuaranteeByGovt of type boolean
	 */
	public void setIsGuaranteeByGovt(boolean isGuaranteeByGovt) {
		this.isGuaranteeByGovt = isGuaranteeByGovt;
	}

	/**
	 * Get name of government.
	 * 
	 * @return String
	 */
	public String getGovernmentName() {
		return governmentName;
	}

	/**
	 * Set name of government.
	 * 
	 * @param governmentName of type String
	 */
	public void setGovernmentName(String governmentName) {
		this.governmentName = governmentName;
	}

	/**
	 * Get reuters identification code.
	 * 
	 * @return String
	 */
	public String getRIC() {
		return ric;
	}

	/**
	 * Set reuters identification code.
	 * 
	 * @param ric of type String
	 */
	public void setRIC(String ric) {
		this.ric = ric;
	}

	/**
	 * Get name of index.
	 * 
	 * @return String
	 */
	public String getNameOfIndex() {
		return nameOfIndex;
	}

	/**
	 * Set name of index.
	 * 
	 * @param nameOfIndex of type String
	 */
	public void setNameOfIndex(String nameOfIndex) {
		this.nameOfIndex = nameOfIndex;
	}

	/**
	 * Get stock exchange.
	 * 
	 * @return String
	 */
	public String getStockExchange() {
		return stockExchange;
	}

	/**
	 * Set stock exchange.
	 * 
	 * @param stockExchange of type String
	 */
	public void setStockExchange(String stockExchange) {
		this.stockExchange = stockExchange;
	}

	/**
	 * Get country of stock exchange.
	 * 
	 * @return String
	 */
	public String getStockExchangeCountry() {
		return stockExchangeCountry;
	}

	/**
	 * Set country of stock exchange.
	 * 
	 * @param stockExchangeCountry of type String
	 */
	public void setStockExchangeCountry(String stockExchangeCountry) {
		this.stockExchangeCountry = stockExchangeCountry;
	}

	public boolean getIsLocalStockExchange() {
		return isLocalStockExchange;
	}

	public void setIsLocalStockExchange(boolean localStockExchange) {
		isLocalStockExchange = localStockExchange;
	}

	/**
	 * Get local stock exchange.
	 * 
	 * @return String
	 */
	/*
	 * public String getLocalStockExchange() { return localStockExchange; }
	 */
	/**
	 * Set local stock exchange.
	 * 
	 * @param localStockExchange of type String
	 */
	/*
	 * public void setLocalStockExchange(String localStockExchange) {
	 * this.localStockExchange = localStockExchange; }
	 */

	/**
	 * Get lead manager/syndicate.
	 * 
	 * @return String
	 */
	public String getLeadManager() {
		return leadManager;
	}

	/**
	 * Set lead manager/syndicate.
	 * 
	 * @param leadManager of type String
	 */
	public void setLeadManager(String leadManager) {
		this.leadManager = leadManager;
	}

	/**
	 * Get issuer name.
	 * 
	 * @return String
	 */
	public String getIssuerName() {
		return issuerName;
	}

	/**
	 * Set issuer name.
	 * 
	 * @param issuerName of type String
	 */
	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}

	/**
	 * Get issuer identifier type.
	 * 
	 * @return String
	 */
	public String getIssuerIdType() {
		return issuerIdType;
	}

	/**
	 * Set issuer identifier type.
	 * 
	 * @param issuerIdType of type String
	 */
	public void setIssuerIdType(String issuerIdType) {
		this.issuerIdType = issuerIdType;
	}

	/**
	 * Get if the security is blacklisted.
	 * 
	 * @return boolean
	 */
	public boolean getIsCollateralBlacklisted() {
		return isCollateralBlacklisted;
	}

	/**
	 * Set if the security is blacklisted.
	 * 
	 * @param isCollateralBlacklisted of type boolean
	 */
	public void setIsCollateralBlacklisted(boolean isCollateralBlacklisted) {
		this.isCollateralBlacklisted = isCollateralBlacklisted;
	}

	/**
	 * Get security maturity date.
	 * 
	 * @return Date
	 */
	public Date getCollateralMaturityDate() {
		return collateralMaturityDate;
	}

	/**
	 * Set security maturity date.
	 * 
	 * @param collateralMaturityDate is of type Date
	 */
	public void setCollateralMaturityDate(Date collateralMaturityDate) {
		this.collateralMaturityDate = collateralMaturityDate;
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
	 * Set Security custodian.
	 * 
	 * @param collateralCustodian is of type String
	 */
	public void setCollateralCustodian(String collateralCustodian) {
		this.collateralCustodian = collateralCustodian;
	}

	/**
	 * Get collateral custodian type, internal or external.
	 * 
	 * @return String
	 */
	public String getCollateralCustodianType() {
		return collateralCustodianType;
	}

	/**
	 * Set collateral custodian type, internal or external.
	 * 
	 * @param collateralCustodianType of type String
	 */
	public void setCollateralCustodianType(String collateralCustodianType) {
		this.collateralCustodianType = collateralCustodianType;
	}

	/**
	 * Get unit price.
	 * 
	 * @return Amount
	 */
	public Amount getUnitPrice() {
		return unitPrice;
	}

	/**
	 * Set unit price.
	 * 
	 * @param unitPrice of type Amount
	 */
	public void setUnitPrice(Amount unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * Get unit price currency code.
	 * 
	 * @return String
	 */
	public String getUnitPriceCcyCode() {
		return unitPriceCcyCode;
	}


    public Amount getValuationUnitPrice() {
        return valuationUnitPrice;
    }

    public void setValuationUnitPrice(Amount valuationUnitPrice) {
        this.valuationUnitPrice = valuationUnitPrice;
    }

    public String getValuationUnitPriceCcyCode() {
        return valuationUnitPriceCcyCode;
    }

    public void setValuationUnitPriceCcyCode(String valuationUnitPriceCcyCode) {
        this.valuationUnitPriceCcyCode = valuationUnitPriceCcyCode;
    }

    /**
	 * Set unit price currency code.
	 * 
	 * @param unitPriceCcyCode of type String
	 */
	public void setUnitPriceCcyCode(String unitPriceCcyCode) {
		this.unitPriceCcyCode = unitPriceCcyCode;
	}

	/**
	 * Get status of the item, deleted or active.
	 * 
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Set item status, deleted or active.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Get bond rating.
	 * 
	 * @return String
	 */
	public String getBondRating() {
		return bondRating;
	}

	/**
	 * Set bond rating.
	 * 
	 * @param bondRating of type String
	 */
	public void setBondRating(String bondRating) {
		this.bondRating = bondRating;
	}

	/**
	 * @return Returns the isinCode.
	 */
	public String getIsinCode() {
		return isinCode;
	}

	/**
	 * @param isinCode The isinCode to set.
	 */
	public void setIsinCode(String isinCode) {
		this.isinCode = isinCode;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getRecognizeExchange() {
		return recognizeExchange;
	}

	public void setRecognizeExchange(String recognizeExchange) {
		this.recognizeExchange = recognizeExchange;
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/**
	 * Get Equity Details in array
	 */
	public IMarketableEquityDetail[] getEquityDetailArray() {

		return this.equityDetails;
	}

	/**
	 * Set the equityArray
	 * @param equityDetails
	 */
	public void setEquityDetailArray(IMarketableEquityDetail[] equityDetails) {
		this.equityDetails = equityDetails;
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
		else if (!(obj instanceof OBMarketableEquity)) {
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
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + ((equityType == null) ? 0 : equityType.hashCode());
		return result;
	}
		

	public Date getExchangeCtrlDate() {
		return exchangeCtrlDate;
	}

	public void setExchangeCtrlDate(Date exchangeCtrlDate) {
		this.exchangeCtrlDate = exchangeCtrlDate;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getCdsNumber() {
		return cdsNumber;
	}

	public void setCdsNumber(String cdsNumber) {
		this.cdsNumber = cdsNumber;
	}

	public Amount getExercisePrice() {
		return exercisePrice;
	}

	public void setExercisePrice(Amount exercisePrice) {
		this.exercisePrice = exercisePrice;
	}

	public String getBrokerName() {
		return brokerName;
	}

	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}

	public IMarketableEquityLineDetail[] getLineDetails() {
		return lineDetails;
	}

	public void setLineDetails(IMarketableEquityLineDetail[] lineDetails) {
		this.lineDetails = lineDetails;
	}

	
	public String getEquityUniqueID() {
		return equityUniqueID;
	}

	public void setEquityUniqueID(String equityUniqueID) {
		this.equityUniqueID = equityUniqueID;
	}
}