/*
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 */
package com.integrosys.cms.host.eai.security.bus.insurance;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * This class represents credit default swaps detail of type Insurance.
 * 
 * @author $Author: shphoon $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */

public class CreditDefaultSwapsDetail implements Serializable {

	private static final long serialVersionUID = -211770707325947231L;

	private long cDSId;

	private long collateralId;

	private String securityId;

	private String changeIndicator;

	private String updateStatusIndicator;

	private String bankEntity;

	private String hedgeType;

	private String hedgeReference;

	private String cDSReference;

	private String tradeId;

	private Date tradeDate;

	private Date dealDate;

	private Date startDate;

	private Date cDSMaturityDate;

	private Long tenor;

	private String tenorUnit;

	private String tradeCurrency;

	private Double notionalHedgedAmount;

	private String referenceEntity;

	private StandardCode cDSBookingLocation;

	private StandardCode loanBondBookingLocation;

	private String referenceAsset;

	private String issuer;

	private String issuerId;

	private String issuerDetail;

	private Double dealtPrice;

	private Double residualMaturity;

	private String settlement;

	private Double parValue;

	private Double declineMarketValue;

	private Date eventDeterminationDate;

	private String complianceCertificate;

	private Date valuationDate;

	private String valuationCurrency;

	private Double margin;

	private Double nominalValue;

	private Double cMV;

	private Double fSV;

	private String sourceId;

	/**
	 * Default constructor.
	 */
	public CreditDefaultSwapsDetail() {
		super();
	}

	public String getBankEntity() {
		return bankEntity;
	}

	public StandardCode getCDSBookingLocation() {
		return cDSBookingLocation;
	}

	public long getCDSId() {
		return cDSId;
	}

	public String getCDSMaturityDate() {
		return MessageDate.getInstance().getString(cDSMaturityDate);
	}

	public String getCDSReference() {
		return cDSReference;
	}

	public String getChangeIndicator() {
		return changeIndicator;
	}

	public Double getCMV() {
		return cMV;
	}

	public long getCollateralId() {
		return collateralId;
	}

	public String getComplianceCertificate() {
		return complianceCertificate;
	}

	public String getDealDate() {
		return MessageDate.getInstance().getString(dealDate);
	}

	public Double getDealtPrice() {
		return dealtPrice;
	}

	public Double getDeclineMarketValue() {
		return declineMarketValue;
	}

	public String getEventDeterminationDate() {
		return MessageDate.getInstance().getString(eventDeterminationDate);
	}

	public Double getFSV() {
		return fSV;
	}

	public String getHedgeReference() {
		return hedgeReference;
	}

	public String getHedgeType() {
		return hedgeType;
	}

	public String getIssuer() {
		return issuer;
	}

	public String getIssuerDetail() {
		return issuerDetail;
	}

	public String getIssuerId() {
		return issuerId;
	}

	public Date getJDOCDSMaturityDate() {
		return cDSMaturityDate;
	}

	public Date getJDODealDate() {
		return dealDate;
	}

	public Date getJDOEventDeterminationDate() {
		return eventDeterminationDate;
	}

	public Date getJDOStartDate() {
		return startDate;
	}

	public Date getJDOTradeDate() {
		return tradeDate;
	}

	public Date getJDOValuationDate() {
		return valuationDate;
	}

	public StandardCode getLoanBondBookingLocation() {
		return loanBondBookingLocation;
	}

	public Double getMargin() {
		return margin;
	}

	public Double getNominalValue() {
		return nominalValue;
	}

	public Double getNotionalHedgedAmount() {
		return notionalHedgedAmount;
	}

	public Double getParValue() {
		return parValue;
	}

	public String getReferenceAsset() {
		return referenceAsset;
	}

	public String getReferenceEntity() {
		return referenceEntity;
	}

	public Double getResidualMaturity() {
		return residualMaturity;
	}

	public String getSecurityId() {
		return securityId;
	}

	public String getSettlement() {
		return settlement;
	}

	public String getSourceId() {
		return sourceId;
	}

	public String getStartDate() {
		return MessageDate.getInstance().getString(startDate);
	}

	public Long getTenor() {
		return tenor;
	}

	public String getTenorUnit() {
		return tenorUnit;
	}

	public String getTradeCurrency() {
		return tradeCurrency;
	}

	public String getTradeDate() {
		return MessageDate.getInstance().getString(tradeDate);
	}

	public String getTradeId() {
		return tradeId;
	}

	public String getUpdateStatusIndicator() {
		return updateStatusIndicator;
	}

	public String getValuationCurrency() {
		return valuationCurrency;
	}

	public String getValuationDate() {
		return MessageDate.getInstance().getString(valuationDate);
	}

	public void setBankEntity(String bankEntity) {
		this.bankEntity = bankEntity;
	}

	public void setCDSBookingLocation(StandardCode cDSBookingLocation) {
		this.cDSBookingLocation = cDSBookingLocation;
	}

	public void setCDSId(long cDSId) {
		this.cDSId = cDSId;
	}

	public void setCDSMaturityDate(String cDSMaturityDate) {
		this.cDSMaturityDate = MessageDate.getInstance().getDate(cDSMaturityDate);
	}

	public void setCDSReference(String cDSReference) {
		this.cDSReference = cDSReference;
	}

	public void setChangeIndicator(String changeIndicator) {
		this.changeIndicator = changeIndicator;
	}

	public void setCMV(Double cMV) {
		this.cMV = cMV;
	}

	public void setCollateralId(long collateralId) {
		this.collateralId = collateralId;
	}

	public void setComplianceCertificate(String complianceCertificate) {
		this.complianceCertificate = complianceCertificate;
	}

	public void setDealDate(String dealDate) {
		this.dealDate = MessageDate.getInstance().getDate(dealDate);
	}

	public void setDealtPrice(Double dealtPrice) {
		this.dealtPrice = dealtPrice;
	}

	public void setDeclineMarketValue(Double declineMarketValue) {
		this.declineMarketValue = declineMarketValue;
	}

	public void setEventDeterminationDate(String eventDeterminationDate) {
		this.eventDeterminationDate = MessageDate.getInstance().getDate(eventDeterminationDate);
	}

	public void setFSV(Double fSV) {
		this.fSV = fSV;
	}

	public void setHedgeReference(String hedgeReference) {
		this.hedgeReference = hedgeReference;
	}

	public void setHedgeType(String hedgeType) {
		this.hedgeType = hedgeType;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public void setIssuerDetail(String issuerDetail) {
		this.issuerDetail = issuerDetail;
	}

	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
	}

	public void setJDOCDSMaturityDate(Date cDSMaturityDate) {
		this.cDSMaturityDate = cDSMaturityDate;
	}

	public void setJDODealDate(Date dealDate) {
		this.dealDate = dealDate;
	}

	public void setJDOEventDeterminationDate(Date eventDeterminationDate) {
		this.eventDeterminationDate = eventDeterminationDate;
	}

	public void setJDOStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setJDOTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	public void setJDOValuationDate(Date valuationDate) {
		this.valuationDate = valuationDate;
	}

	public void setLoanBondBookingLocation(StandardCode loanBondBookingLocation) {
		this.loanBondBookingLocation = loanBondBookingLocation;
	}

	public void setMargin(Double margin) {
		this.margin = margin;
	}

	public void setNominalValue(Double nominalValue) {
		this.nominalValue = nominalValue;
	}

	public void setNotionalHedgedAmount(Double notionalHedgedAmount) {
		this.notionalHedgedAmount = notionalHedgedAmount;
	}

	public void setParValue(Double parValue) {
		this.parValue = parValue;
	}

	public void setReferenceAsset(String referenceAsset) {
		this.referenceAsset = referenceAsset;
	}

	public void setReferenceEntity(String referenceEntity) {
		this.referenceEntity = referenceEntity;
	}

	public void setResidualMaturity(Double residualMaturity) {
		this.residualMaturity = residualMaturity;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	public void setSettlement(String settlement) {
		this.settlement = settlement;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public void setStartDate(String startDate) {
		this.startDate = MessageDate.getInstance().getDate(startDate);
	}

	public void setTenor(Long tenor) {
		this.tenor = tenor;
	}

	public void setTenorUnit(String tenorUnit) {
		this.tenorUnit = tenorUnit;
	}

	public void setTradeCurrency(String tradeCurrency) {
		this.tradeCurrency = tradeCurrency;
	}

	public void setTradeDate(String tradeDate) {
		this.tradeDate = MessageDate.getInstance().getDate(tradeDate);
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public void setUpdateStatusIndicator(String updateStatusIndicator) {
		this.updateStatusIndicator = updateStatusIndicator;
	}

	public void setValuationCurrency(String valuationCurrency) {
		this.valuationCurrency = valuationCurrency;
	}

	public void setValuationDate(String valuationDate) {
		this.valuationDate = MessageDate.getInstance().getDate(valuationDate);
	}

}
