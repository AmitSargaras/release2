/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.host.eai.security.bus.asset;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * This class represents Cheque Detail of Asset Based.
 * 
 * @author shphoon
 * @author Chong Jun Yong
 * @since 1.1
 */

public class ChequeDetail implements Serializable {
	/**
	 * Default constructor.
	 */
	public ChequeDetail() {
		super();
	}

	private long assetPDCId;

	private long collateralId;

	private String LOSSecurityId;

	private String LOSChequeId;

	public String getLOSChequeId() {
		return LOSChequeId;
	}

	private long CmsChequeId;

	private String changeIndicator;

	private String updateStatusIndicator;

	private String chequeType;

	private String chequeAmountCurrency;

	private Double chequeAmount;

	private String receivableByBank;

	private String toCredit;

	private String locationBank;

	// private String issuer;
	private StandardCode issuer;

	private String draweeBank;

	private String issueCountry;

	private Date issueDate;

	private Date expiryDate;

	private String custodianType;

	private String securityCustodian;

	private Double margin;

	private Double valueBeforeMargin;

	private String exchangeControlObtained;

	private String sourceId;

	private StandardCode bankAcctLocation;

	public long getAssetPDCId() {
		return assetPDCId;
	}

	public void setAssetPDCId(long assetPDCId) {
		this.assetPDCId = assetPDCId;
	}

	public long getCollateralId() {
		return collateralId;
	}

	public void setCollateralId(long collateralId) {
		this.collateralId = collateralId;
	}

	public String getSecurityId() {
		return LOSSecurityId;
	}

	public void setSecurityId(String securityId) {
		this.LOSSecurityId = securityId;
	}

	public String getChangeIndicator() {
		return changeIndicator;
	}

	public void setChangeIndicator(String changeIndicator) {
		this.changeIndicator = changeIndicator;
	}

	public String getUpdateStatusIndicator() {
		return updateStatusIndicator;
	}

	public void setUpdateStatusIndicator(String updateStatusIndicator) {
		this.updateStatusIndicator = updateStatusIndicator;
	}

	public String getChequeType() {
		return chequeType;
	}

	public void setChequeType(String chequeType) {
		this.chequeType = chequeType;
	}

	public String getChequeAmountCurrency() {
		return chequeAmountCurrency;
	}

	public void setChequeAmountCurrency(String chequeAmountCurrency) {
		this.chequeAmountCurrency = chequeAmountCurrency;
	}

	public Double getChequeAmount() {
		return chequeAmount;
	}

	public void setChequeAmount(Double chequeAmount) {
		this.chequeAmount = chequeAmount;
	}

	public String getReceivableByBank() {
		return receivableByBank;
	}

	public void setReceivableByBank(String receivableByBank) {
		this.receivableByBank = receivableByBank;
	}

	public String getToCredit() {
		return toCredit;
	}

	public void setToCredit(String toCredit) {
		this.toCredit = toCredit;
	}

	public String getLocationBank() {
		return locationBank;
	}

	public void setLocationBank(String locationBank) {
		this.locationBank = locationBank;
	}

	public StandardCode getIssuer() {
		return issuer;
	}

	public void setIssuer(StandardCode issuer) {
		this.issuer = issuer;
	}

	public String getDraweeBank() {
		return draweeBank;
	}

	public void setDraweeBank(String draweeBank) {
		this.draweeBank = draweeBank;
	}

	public String getIssueCountry() {
		return issueCountry;
	}

	public void setIssueCountry(String issueCountry) {
		this.issueCountry = issueCountry;
	}

	public String getIssueDate() {
		return MessageDate.getInstance().getString(issueDate);
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = MessageDate.getInstance().getDate(issueDate);
	}

	public Date getJDOIssueDate() {
		return issueDate;
	}

	public void setJDOIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public String getExpiryDate() {
		return MessageDate.getInstance().getString(expiryDate);
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = MessageDate.getInstance().getDate(expiryDate);
	}

	public Date getJDOExpiryDate() {
		return expiryDate;
	}

	public void setJDOExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getCustodianType() {
		return custodianType;
	}

	public void setCustodianType(String custodianType) {
		this.custodianType = custodianType;
	}

	public String getSecurityCustodian() {
		return securityCustodian;
	}

	public void setSecurityCustodian(String securityCustodian) {
		this.securityCustodian = securityCustodian;
	}

	public Double getMargin() {
		return margin;
	}

	public void setMargin(Double margin) {
		this.margin = margin;
	}

	public Double getValueBeforeMargin() {
		return valueBeforeMargin;
	}

	public void setValueBeforeMargin(Double valueBeforeMargin) {
		this.valueBeforeMargin = valueBeforeMargin;
	}

	public String getExchangeControlObtained() {
		return exchangeControlObtained;
	}

	public void setExchangeControlObtained(String exchangeControlObtained) {
		this.exchangeControlObtained = exchangeControlObtained;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public void setLOSChequeId(String chequeId) {
		LOSChequeId = chequeId;
	}

	public long getCmsChequeId() {
		return CmsChequeId;
	}

	public void setCmsChequeId(long cmsChequeId) {
		CmsChequeId = cmsChequeId;
	}

	public StandardCode getBankAcctLocation() {
		return bankAcctLocation;
	}

	public void setBankAcctLocation(StandardCode bankAcctLocation) {
		this.bankAcctLocation = bankAcctLocation;
	}
}
