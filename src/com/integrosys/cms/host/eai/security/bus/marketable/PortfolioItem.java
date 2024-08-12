/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.host.eai.security.bus.marketable;

import java.util.Date;

import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * This class represents portfolio item.
 * 
 * @author $Author: shphoon $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class PortfolioItem implements java.io.Serializable {

	private static final long serialVersionUID = -3651950820535766425L;

	private long itemId;

	private long collateralId;

	private String securityId;

	private String changeIndicator;

	private String updateStatusIndicator;

	private Long oldShareId;

	private Long shareId;

	private StandardCode type;

	private String certificateNumber;

	private StandardCode nomineeName;

	private Date nomineeConfirmationDate;

	private String registeredName;

	private Long units;

	private Double unitPrice;

	private Double nominalValue;

	private String custodianType;

	private String custodian;

	private Date maturityDate;

	private String blacklistedFlag;

	private String issuerName;

	private String issuerType;

	private String stockExchange;

	private String stockExchangeCountry;

	private String indexName;

	private String rIC;

	private String stockCode;

	private String iSINCode;

	private String exchangeControlObtainedFlag;

	private String baselCompliantText;

	private String governmentGuaranteeFlag;

	private String governmentName;

	private String leadManager;

	private StandardCode settlementOrganisation;

	private Date bondIssueDate;

	private Date bondMaturityDate;

	private String bondRating;

	private Date recoveryDate;

	private Double holdingPeriod;

	private String holdingPeriodUnit;

	private String recognizedExchangeFlag;

	private String clientCode;

	private String cDSNumber;

	private String localStockExchangeInd;

	private String exchangeControlObtainedDate;

	private long CMSPortfolioItemId;

	private Double exercisePrice;

	private String brokerrName;

	private String currencyCode;

	private String unitCurrencyCode;

	private String legalEnforceDate;
	
	private String equityUniqueID;

	public PortfolioItem() {
		super();
	}

	public String getBaselCompliantText() {
		return baselCompliantText;
	}

	public String getBlacklistedFlag() {
		return blacklistedFlag;
	}

	public String getBondIssueDate() {
		return MessageDate.getInstance().getString(bondIssueDate);
	}

	public String getBondMaturityDate() {
		return MessageDate.getInstance().getString(bondMaturityDate);
	}

	public String getBondRating() {
		return bondRating;
	}

	public String getBrokerrName() {
		return brokerrName;
	}

	public String getCDSNumber() {
		return cDSNumber;
	}

	public String getCertificateNumber() {
		return certificateNumber;
	}

	public String getChangeIndicator() {
		return changeIndicator;
	}

	public String getClientCode() {
		return clientCode;
	}

	public long getCMSPortfolioItemId() {
		return CMSPortfolioItemId;
	}

	public long getCollateralId() {
		return collateralId;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public String getCustodian() {
		return custodian;
	}

	public String getCustodianType() {
		return custodianType;
	}

	public String getExchangeControlObtainedDate() {
		return exchangeControlObtainedDate;
	}

	public String getExchangeControlObtainedFlag() {
		return exchangeControlObtainedFlag;
	}

	public Double getExercisePrice() {
		return exercisePrice;
	}

	public String getGovernmentGuaranteeFlag() {
		return governmentGuaranteeFlag;
	}

	public String getGovernmentName() {
		return governmentName;
	}

	public Double getHoldingPeriod() {
		return holdingPeriod;
	}

	public String getHoldingPeriodUnit() {
		return holdingPeriodUnit;
	}

	public String getIndexName() {
		return indexName;
	}

	public String getISINCode() {
		return iSINCode;
	}

	public String getIssuerName() {
		return issuerName;
	}

	public String getIssuerType() {
		return issuerType;
	}

	public long getItemId() {
		return itemId;
	}

	public Date getJDOBondIssueDate() {
		return bondIssueDate;
	}

	public Date getJDOBondMaturityDate() {
		return bondMaturityDate;
	}

	public Date getJDOExchangeControlObtainedDate() {
		return MessageDate.getInstance().getDate(exchangeControlObtainedDate);
	}

	public Date getJDOLegalEnforceDate() {
		return MessageDate.getInstance().getDate(legalEnforceDate);
	}

	public Date getJDOMaturityDate() {
		return maturityDate;
	}

	public Date getJDONomineeConfirmationDate() {
		return nomineeConfirmationDate;
	}

	public Date getJDORecoveryDate() {
		return recoveryDate;
	}

	public String getLeadManager() {
		return leadManager;
	}

	public String getLegalEnforceDate() {
		return legalEnforceDate;
	}

	public String getLocalStockExchangeInd() {
		return localStockExchangeInd;
	}

	public String getMaturityDate() {
		return MessageDate.getInstance().getString(maturityDate);
	}

	public Double getNominalValue() {
		return nominalValue;
	}

	public String getNomineeConfirmationDate() {
		return MessageDate.getInstance().getString(nomineeConfirmationDate);
	}

	public StandardCode getNomineeName() {
		return nomineeName;
	}

	public Long getOldShareId() {
		return oldShareId;
	}

	public String getRecognizedExchangeFlag() {
		return recognizedExchangeFlag;
	}

	public String getRecoveryDate() {
		return MessageDate.getInstance().getString(recoveryDate);
	}

	public String getRegisteredName() {
		return registeredName;
	}

	public String getRIC() {
		return rIC;
	}

	public String getSecurityId() {
		return securityId;
	}

	public StandardCode getSettlementOrganisation() {
		return settlementOrganisation;
	}

	public Long getShareId() {
		return shareId;
	}

	public String getStockCode() {
		return stockCode;
	}

	public String getStockExchange() {
		return stockExchange;
	}

	public String getStockExchangeCountry() {
		return stockExchangeCountry;
	}

	public StandardCode getType() {
		return type;
	}

	public String getUnitCurrencyCode() {
		return unitCurrencyCode;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public Long getUnits() {
		return units;
	}

	public String getUpdateStatusIndicator() {
		return updateStatusIndicator;
	}

	public void setBaselCompliantText(String baselCompliantText) {
		this.baselCompliantText = baselCompliantText;
	}

	public void setBlacklistedFlag(String blacklistedFlag) {
		this.blacklistedFlag = blacklistedFlag;
	}

	public void setBondIssueDate(String bondIssueDate) {
		this.bondIssueDate = MessageDate.getInstance().getDate(bondIssueDate);
	}

	public void setBondMaturityDate(String bondMaturityDate) {
		this.bondMaturityDate = MessageDate.getInstance().getDate(bondMaturityDate);
	}

	public void setBondRating(String bondRating) {
		this.bondRating = bondRating;
	}

	public void setBrokerrName(String brokerrName) {
		this.brokerrName = brokerrName;
	}

	public void setCDSNumber(String number) {
		cDSNumber = number;
	}

	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}

	public void setChangeIndicator(String changeIndicator) {
		this.changeIndicator = changeIndicator;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public void setCMSPortfolioItemId(long portfolioItemId) {
		CMSPortfolioItemId = portfolioItemId;
	}

	public void setCollateralId(long collateralId) {
		this.collateralId = collateralId;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public void setCustodian(String custodian) {
		this.custodian = custodian;
	}

	public void setCustodianType(String custodianType) {
		this.custodianType = custodianType;
	}

	public void setExchangeControlObtainedDate(String exchangeControlObtainedDate) {
		this.exchangeControlObtainedDate = exchangeControlObtainedDate;
	}

	public void setExchangeControlObtainedFlag(String exchangeControlObtainedFlag) {
		this.exchangeControlObtainedFlag = exchangeControlObtainedFlag;
	}

	public void setExercisePrice(Double exercisePrice) {
		this.exercisePrice = exercisePrice;
	}

	public void setGovernmentGuaranteeFlag(String governmentGuaranteeFlag) {
		this.governmentGuaranteeFlag = governmentGuaranteeFlag;
	}

	public void setGovernmentName(String governmentName) {
		this.governmentName = governmentName;
	}

	public void setHoldingPeriod(Double holdingPeriod) {
		this.holdingPeriod = holdingPeriod;
	}

	public void setHoldingPeriodUnit(String holdingPeriodUnit) {
		this.holdingPeriodUnit = holdingPeriodUnit;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public void setISINCode(String iSINCode) {
		this.iSINCode = iSINCode;
	}

	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}

	public void setIssuerType(String issuerType) {
		this.issuerType = issuerType;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public void setJDOBondIssueDate(Date bondIssueDate) {
		this.bondIssueDate = bondIssueDate;
	}

	public void setJDOBondMaturityDate(Date bondMaturityDate) {
		this.bondMaturityDate = bondMaturityDate;
	}

	public void setJDOExchangeControlObtainedDate(Date exchangeControlObtainedDate) {
		this.exchangeControlObtainedDate = MessageDate.getInstance().getString(exchangeControlObtainedDate);
	}

	public void setJDOLegalEnforceDate(Date legalEnforceDate) {
		this.legalEnforceDate = MessageDate.getInstance().getString(legalEnforceDate);
	}

	public void setJDOMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public void setJDONomineeConfirmationDate(Date nomineeConfirmationDate) {
		this.nomineeConfirmationDate = nomineeConfirmationDate;
	}

	public void setJDORecoveryDate(Date recoveryDate) {
		this.recoveryDate = recoveryDate;
	}

	public void setLeadManager(String leadManager) {
		this.leadManager = leadManager;
	}

	public void setLegalEnforceDate(String legalEnforceDate) {
		this.legalEnforceDate = legalEnforceDate;
	}

	public void setLocalStockExchangeInd(String localStockExchangeInd) {
		this.localStockExchangeInd = localStockExchangeInd;
	}

	public void setMaturityDate(String maturityDate) {
		this.maturityDate = MessageDate.getInstance().getDate(maturityDate);
	}

	public void setNominalValue(Double nominalValue) {
		this.nominalValue = nominalValue;
	}

	public void setNomineeConfirmationDate(String nomineeConfirmationDate) {
		this.nomineeConfirmationDate = MessageDate.getInstance().getDate(nomineeConfirmationDate);
	}

	public void setNomineeName(StandardCode nomineeName) {
		this.nomineeName = nomineeName;
	}

	public void setOldShareId(Long oldShareId) {
		this.oldShareId = oldShareId;
	}

	public void setRecognizedExchangeFlag(String recognizedExchangeFlag) {
		this.recognizedExchangeFlag = recognizedExchangeFlag;
	}

	public void setRecoveryDate(String recoveryDate) {
		this.recoveryDate = MessageDate.getInstance().getDate(recoveryDate);
	}

	public void setRegisteredName(String registeredName) {
		this.registeredName = registeredName;
	}

	public void setRIC(String rIC) {
		this.rIC = rIC;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	public void setSettlementOrganisation(StandardCode settlementOrganisation) {
		this.settlementOrganisation = settlementOrganisation;
	}

	public void setShareId(Long shareId) {
		this.shareId = shareId;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public void setStockExchange(String stockExchange) {
		this.stockExchange = stockExchange;
	}

	public void setStockExchangeCountry(String stockExchangeCountry) {
		this.stockExchangeCountry = stockExchangeCountry;
	}

	public void setType(StandardCode type) {
		this.type = type;
	}

	public void setUnitCurrencyCode(String unitCurrencyCode) {
		this.unitCurrencyCode = unitCurrencyCode;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public void setUnits(Long units) {
		this.units = units;
	}

	public void setUpdateStatusIndicator(String updateStatusIndicator) {
		this.updateStatusIndicator = updateStatusIndicator;
	}

	public String getEquityUniqueID() {
		return equityUniqueID;
	}

	public void setEquityUniqueID(String equityUniqueID) {
		this.equityUniqueID = equityUniqueID;
	}

}
