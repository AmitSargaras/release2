/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/marketable/IMarketableEquity.java,v 1.15 2004/02/06 04:32:46 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.marketable;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.type.marketable.linedetail.IMarketableEquityLineDetail;

/**
 * This interface represents marketable item for all marketable collaterals.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.15 $
 * @since $Date: 2004/02/06 04:32:46 $ Tag: $Name: $
 */
public interface IMarketableEquity extends Serializable {
	/**
	 * Get marketable equity id.
	 * 
	 * @return long
	 */
	public long getEquityID();

	/**
	 * Set marketable equity id.
	 * 
	 * @param equityID of type long
	 */
	public void setEquityID(long equityID);

	/**
	 * Get reference id between staging and actual data.
	 * 
	 * @return long
	 */
	public long getRefID();

	/**
	 * set reference id between staging and actual data.
	 * 
	 * @param refID of type long
	 */
	public void setRefID(long refID);

	/**
	 * Get equity type.
	 * 
	 * @return String
	 */
	public String getEquityType();

	/**
	 * Set equity type.
	 * 
	 * @param equityType of type String
	 */
	public void setEquityType(String equityType);

	/**
	 * Get certificate number.
	 * 
	 * @return String
	 */
	public String getCertificateNo();

	/**
	 * Set certificate number.
	 * 
	 * @param certificateNo of type String
	 */
	public void setCertificateNo(String certificateNo);

	/**
	 * Get registered name.
	 * 
	 * @return String
	 */
	public String getRegisteredName();

	/**
	 * Set registered name.
	 * 
	 * @param registeredName of type String
	 */
	public void setRegisteredName(String registeredName);

	/**
	 * Get number of units.
	 * 
	 * @return long
	 */
	 //Start- Added by Uma Khot- CAM QUARTER ACTIVITY CR: allow decimal in NoofUnits for Security Sub-type ‘Mutual Funds’.
	public double getNoOfUnits();

	/**
	 * Set number of units.
	 * 
	 * @param noOfUnits of type long
	 */
	public void setNoOfUnits(double noOfUnits);
    //End- Added by Uma Khot- CAM QUARTER ACTIVITY CR: allow decimal in NoofUnits for Security Sub-type ‘Mutual Funds’.
	/**
	 * Get unit price/ current market value.
	 * 
	 * @return Amount
	 */
	public Amount getCMV();

	/**
	 * Set unit price/ current market value.
	 * 
	 * @param cmv of type Amount
	 */
	public void setCMV(Amount cmv);

	/**
	 * Get currency code for unit price/CMV.
	 * 
	 * @return String
	 */
	public String getCMVCcyCode();

	/**
	 * Set currency code for unit price/CMV.
	 * 
	 * @param cmvCcyCode of type String
	 */
	public void setCMVCcyCode(String cmvCcyCode);

	/**
	 * Get forced sale value.
	 * 
	 * @return Amount
	 */
	public Amount getFSV();

	/**
	 * Set forced sale value.
	 * 
	 * @param fsv of type Amount
	 */
	public void setFSV(Amount fsv);

	/**
	 * Get currency code for FSV.
	 * 
	 * @return String
	 */
	public String getFSVCcyCode();

	/**
	 * Set currency code for FSV.
	 * 
	 * @param fsvCcyCode of type String
	 */
	public void setFSVCcyCode(String fsvCcyCode);

	/**
	 * Get nominal value.
	 * 
	 * @return Amount
	 */
	public Amount getNominalValue();

	/**
	 * Set nominal value.
	 * 
	 * @param nominalValue of type Amount
	 */
	public void setNominalValue(Amount nominalValue);

	/**
	 * Get controller/nominee/agent name.
	 * 
	 * @return String
	 */
	public String getAgentName();

	/**
	 * Set controller/nominee/bank's agent name.
	 * 
	 * @param agentName of type String
	 */
	public void setAgentName(String agentName);

	/**
	 * Get confirmation date from controller/nominee/agent's bank.
	 * 
	 * @return Date
	 */
	public Date getAgentConfirmDate();

	/**
	 * Set confirmation date from controller/nominee/agent's bank.
	 * 
	 * @param agentConfirmDate of type Date
	 */
	public void setAgentConfirmDate(Date agentConfirmDate);

	/**
	 * Get settlement organisation.
	 * 
	 * @return String
	 */
	public String getSettlementOrganisation();

	/**
	 * Set settlement organisation.
	 * 
	 * @param settlementOrganisation of type String
	 */
	public void setSettlementOrganisation(String settlementOrganisation);

	/**
	 * Get bond issue date.
	 * 
	 * @return Date
	 */
	public Date getBondIssueDate();

	/**
	 * Set bond issue date.
	 * 
	 * @param bondIssueDate of type Date
	 */
	public void setBondIssueDate(Date bondIssueDate);

	/**
	 * Get bond maturity date.
	 * 
	 * @return Date
	 */
	public Date getBondMaturityDate();

	/**
	 * Set bond maturity date.
	 * 
	 * @param bondMaturityDate of type Date
	 */
	public void setBondMaturityDate(Date bondMaturityDate);

	/**
	 * Get currency code.
	 * 
	 * @return String
	 */
	public String getItemCurrencyCode();

	/**
	 * Set currency code.
	 * 
	 * @param itemCurrencyCode of type String
	 */
	public void setItemCurrencyCode(String itemCurrencyCode);

	/**
	 * Get if exchange control approval obtained.
	 * 
	 * @return String
	 */
	public String getIsExchangeCtrlObtained();

	/**
	 * Set if exchange control approval obtained.
	 * 
	 * @param isExchangeCtrlObtained of type String
	 */
	public void setIsExchangeCtrlObtained(String isExchangeCtrlObtained);

	public Date getExchangeCtrlDate();

	public void setExchangeCtrlDate(Date exchangeCtrlDate);

	/**
	 * Get Basel Compliant unit trust collateral.
	 * 
	 * @return String
	 */
	public String getBaselCompliant();

	/**
	 * Set Basel Compliant unit trust collateral.
	 * 
	 * @param baselCompliant of type String
	 */
	public void setBaselCompliant(String baselCompliant);

	/**
	 * Get if guarantee is by government.
	 * 
	 * @return boolean
	 */
	public boolean getIsGuaranteeByGovt();

	/**
	 * Set if guarantee is by government.
	 * 
	 * @param isGuaranteeByGovt of type boolean
	 */
	public void setIsGuaranteeByGovt(boolean isGuaranteeByGovt);

	/**
	 * Get name of government.
	 * 
	 * @return String
	 */
	public String getGovernmentName();

	/**
	 * Set name of government.
	 * 
	 * @param governmentName of type String
	 */
	public void setGovernmentName(String governmentName);

	/**
	 * Get reuters identification code.
	 * 
	 * @return String
	 */
	public String getRIC();

	/**
	 * Set reuters identification code.
	 * 
	 * @param ric of type String
	 */
	public void setRIC(String ric);

	/**
	 * Get name of index.
	 * 
	 * @return String
	 */
	public String getNameOfIndex();

	/**
	 * Set name of index.
	 * 
	 * @param nameOfIndex of type String
	 */
	public void setNameOfIndex(String nameOfIndex);

	/**
	 * Get stock exchange.
	 * 
	 * @return String
	 */
	public String getStockExchange();

	/**
	 * Set stock exchange.
	 * 
	 * @param stockExchange of type String
	 */
	public void setStockExchange(String stockExchange);

	/**
	 * Get country of stock exchange.
	 * 
	 * @return String
	 */
	public String getStockExchangeCountry();

	/**
	 * Set country of stock exchange.
	 * 
	 * @param stockExchangeCountry of type String
	 */
	public void setStockExchangeCountry(String stockExchangeCountry);

	/**
	 * Get local stock exchange.
	 * 
	 * @return String
	 */
	// public String getLocalStockExchange();
	/**
	 * Set local stock exchange.
	 * 
	 * @param localStockExchange of type String
	 */
	// public void setLocalStockExchange(String localStockExchange);
	/**
	 * Get lead manager/syndicate.
	 * 
	 * @return String
	 */
	public String getLeadManager();

	/**
	 * Set lead manager/syndicate.
	 * 
	 * @param leadManager of type String
	 */
	public void setLeadManager(String leadManager);

	/**
	 * Get issuer name.
	 * 
	 * @return String
	 */
	public String getIssuerName();

	/**
	 * Set issuer name.
	 * 
	 * @param issuerName of type String
	 */
	public void setIssuerName(String issuerName);

	/**
	 * Get issuer identifier type.
	 * 
	 * @return String
	 */
	public String getIssuerIdType();

	/**
	 * Set issuer identifier type.
	 * 
	 * @param issuerIdType of type String
	 */
	public void setIssuerIdType(String issuerIdType);

	/**
	 * Get if the security is blacklisted.
	 * 
	 * @return boolean
	 */
	public boolean getIsCollateralBlacklisted();

	/**
	 * Set if the security is blacklisted.
	 * 
	 * @param isCollateralBlacklisted of type boolean
	 */
	public void setIsCollateralBlacklisted(boolean isCollateralBlacklisted);

	/**
	 * Get security maturity date.
	 * 
	 * @return Date
	 */
	public Date getCollateralMaturityDate();

	/**
	 * Set security maturity date.
	 * 
	 * @param collateralMaturityDate is of type Date
	 */
	public void setCollateralMaturityDate(Date collateralMaturityDate);

	/**
	 * Get Security custodian.
	 * 
	 * @return String
	 */
	public String getCollateralCustodian();

	/**
	 * Set Security custodian.
	 * 
	 * @param collateralCustodian is of type String
	 */
	public void setCollateralCustodian(String collateralCustodian);

	/**
	 * Get collateral custodian type, internal or external.
	 * 
	 * @return String
	 */
	public String getCollateralCustodianType();

	/**
	 * Set collateral custodian type, internal or external.
	 * 
	 * @param collateralCustodianType of type String
	 */
	public void setCollateralCustodianType(String collateralCustodianType);

	/**
	 * Get unit price.
	 * 
	 * @return Amount
	 */
	public Amount getUnitPrice();

	/**
	 * Set unit price.
	 * 
	 * @param unitPrice of type Amount
	 */
	public void setUnitPrice(Amount unitPrice);

	/**
	 * Get unit price currency code.
	 * 
	 * @return String
	 */
	public String getUnitPriceCcyCode();

	/**
	 * Set unit price currency code.
	 * 
	 * @param unitPriceCcyCode of type String
	 */
	public void setUnitPriceCcyCode(String unitPriceCcyCode);

    public Amount getValuationUnitPrice();

    public void setValuationUnitPrice(Amount valuationUnitPrice);

    public String getValuationUnitPriceCcyCode();

    public void setValuationUnitPriceCcyCode(String valuationUnitPriceCcyCode);
    
    /**
	 * Get bond rating.
	 * 
	 * @return String
	 */
	public String getBondRating();

	/**
	 * Set bond rating.
	 * 
	 * @param bondRating of type String
	 */
	public void setBondRating(String bondRating);

	/**
	 * Get status of the item, deleted or active.
	 * 
	 * @return String
	 */
	public String getStatus();

	/**
	 * Set item status, deleted or active.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);

	public String getIsinCode();

	public void setIsinCode(String isinCode);

	public String getStockCode();

	public void setStockCode(String stockCode);

	public String getRecognizeExchange();

	public void setRecognizeExchange(String recognizeExchange);

	public boolean getIsLocalStockExchange();

	public void setIsLocalStockExchange(boolean localStockExchange);

	public IMarketableEquityDetail[] getEquityDetailArray();

	public void setEquityDetailArray(IMarketableEquityDetail[] array);

	public String getClientCode();

	public void setClientCode(String clientCode);

	public String getCdsNumber();

	public void setCdsNumber(String cdsNumber);

	public Amount getExercisePrice();

	public void setExercisePrice(Amount exercisePrice);

	public String getBrokerName();

	public void setBrokerName(String brokerName);

	public Date getLeDate();

	public void setLeDate(Date leDate);
	
	public IMarketableEquityLineDetail[] getLineDetails();

	public void setLineDetails(IMarketableEquityLineDetail[] lineDetails);
	
	public String getEquityUniqueID();

	public void setEquityUniqueID(String equityUniqueID);
	

}