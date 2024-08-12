package com.integrosys.cms.ui.collateral.insprotection.insswap.cds;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/09/29 09:41:57 $ Tag: $Name: $
 */

public class CDSItemForm extends CommonForm implements Serializable {

	public static final String CDSItemMAPPER = "com.integrosys.cms.ui.collateral.insprotection.insswap.cds.CDSItemMapper";

	private String bankEntity = "";

	private String hedgeType = "";

	private String hedgeRef = "";

	private String cdsRef = "";

	private String tradeID = "";

	private String tradeDate = "";

	private String dealDate = "";

	private String startDate = "";

	private String cdsMaturityDate = "";

	private String tenor = "";

	private String tenorUnit = "";

	private String tradeCurrency = "";

	private String notionalHedgeAmt = "";

	private String referenceEntity = "";

	private String cdsBookingLoc = "";

	private String loanBondBkLoc = "";

	private String referenceAsset = "";

	private String issuer = "";

	private String issuerID = "";

	private String detailsIssuer = "";

	private String dealtPrice = "";

	private String residualMaturityField = "";

	private String settlement = "";

	private String parValue = "";

	private String declineMarketValue = "";

	private String eventDeterminationDate = "";

	private String complianceCert = "";

	// valuation details
	private String valuationDate = "";

	private String valuationCurrency = "";

	private String nominalValue = "";

	private String cdsMargin = "";

	private String nonStdFreq = "";

	private String nonStdFreqUnit = "";

	private String valuationCMV = "";

	private String valuationFSV = "";

	private String revalFreq = "";

	private String revalDate = "";

	public String getBankEntity() {
		return this.bankEntity;
	}

	public void setBankEntity(String bankEntity) {
		this.bankEntity = bankEntity;
	}

	public String getHedgeType() {
		return this.hedgeType;
	}

	public void setHedgeType(String hedgeType) {
		this.hedgeType = hedgeType;
	}

	public String getHedgeRef() {
		return this.hedgeRef;
	}

	public void setHedgeRef(String hedgeRef) {
		this.hedgeRef = hedgeRef;
	}

	public String getCdsRef() {
		return this.cdsRef;
	}

	public void setCdsRef(String cdsRef) {
		this.cdsRef = cdsRef;
	}

	public String getTradeID() {
		return this.tradeID;
	}

	public void setTradeID(String tradeID) {
		this.tradeID = tradeID;
	}

	public String getTradeDate() {
		return this.tradeDate;
	}

	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}

	public String getDealDate() {
		return this.dealDate;
	}

	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}

	public String getStartDate() {
		return this.startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getCdsMaturityDate() {
		return this.cdsMaturityDate;
	}

	public void setCdsMaturityDate(String cdsMaturityDate) {
		this.cdsMaturityDate = cdsMaturityDate;
	}

	public String getTenor() {
		return this.tenor;
	}

	public void setTenor(String tenor) {
		this.tenor = tenor;
	}

	public String getTenorUnit() {
		return this.tenorUnit;
	}

	public void setTenorUnit(String tenorUnit) {
		this.tenorUnit = tenorUnit;
	}

	public String getTradeCurrency() {
		return this.tradeCurrency;
	}

	public void setTradeCurrency(String tradeCurrency) {
		this.tradeCurrency = tradeCurrency;
	}

	public String getNotionalHedgeAmt() {
		return this.notionalHedgeAmt;
	}

	public void setNotionalHedgeAmt(String notionalHedgeAmt) {
		this.notionalHedgeAmt = notionalHedgeAmt;
	}

	public String getReferenceEntity() {
		return this.referenceEntity;
	}

	public void setReferenceEntity(String referenceEntity) {
		this.referenceEntity = referenceEntity;
	}

	public String getCdsBookingLoc() {
		return this.cdsBookingLoc;
	}

	public void setCdsBookingLoc(String cdsBookingLoc) {
		this.cdsBookingLoc = cdsBookingLoc;
	}

	public String getLoanBondBkLoc() {
		return this.loanBondBkLoc;
	}

	public void setLoanBondBkLoc(String loanBondBkLoc) {
		this.loanBondBkLoc = loanBondBkLoc;
	}

	public String getReferenceAsset() {
		return this.referenceAsset;
	}

	public void setReferenceAsset(String referenceAsset) {
		this.referenceAsset = referenceAsset;
	}

	public String getIssuer() {
		return this.issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getIssuerID() {
		return this.issuerID;
	}

	public void setIssuerID(String issuerID) {
		this.issuerID = issuerID;
	}

	public String getDetailsIssuer() {
		return this.detailsIssuer;
	}

	public void setDetailsIssuer(String detailsIssuer) {
		this.detailsIssuer = detailsIssuer;
	}

	public String getDealtPrice() {
		return this.dealtPrice;
	}

	public void setDealtPrice(String dealtPrice) {
		this.dealtPrice = dealtPrice;
	}

	public String getResidualMaturityField() {
		return this.residualMaturityField;
	}

	public void setResidualMaturityField(String residualMaturityField) {
		this.residualMaturityField = residualMaturityField;
	}

	public String getSettlement() {
		return this.settlement;
	}

	public void setSettlement(String settlement) {
		this.settlement = settlement;
	}

	public String getParValue() {
		return this.parValue;
	}

	public void setParValue(String parValue) {
		this.parValue = parValue;
	}

	public String getDeclineMarketValue() {
		return this.declineMarketValue;
	}

	public void setDeclineMarketValue(String declineMarketValue) {
		this.declineMarketValue = declineMarketValue;
	}

	public String getEventDeterminationDate() {
		return this.eventDeterminationDate;
	}

	public void setEventDeterminationDate(String eventDeterminationDate) {
		this.eventDeterminationDate = eventDeterminationDate;
	}

	public String getComplianceCert() {
		return this.complianceCert;
	}

	public void setComplianceCert(String complianceCert) {
		this.complianceCert = complianceCert;
	}

	// Valuation Details
	public String getValuationDate() {
		return this.valuationDate;
	}

	public void setValuationDate(String valuationDate) {
		this.valuationDate = valuationDate;
	}

	public String getValuationCurrency() {
		return this.valuationCurrency;
	}

	public void setValuationCurrency(String valuationCurrency) {
		this.valuationCurrency = valuationCurrency;
	}

	public String getNominalValue() {
		return this.nominalValue;
	}

	public void setNominalValue(String nominalValue) {
		this.nominalValue = nominalValue;
	}

	public String getCdsMargin() {
		return this.cdsMargin;
	}

	public void setCdsMargin(String cdsMargin) {
		this.cdsMargin = cdsMargin;
	}

	public String getNonStdFreq() {
		return this.nonStdFreq;
	}

	public void setNonStdFreq(String nonStdFreq) {
		this.nonStdFreq = nonStdFreq;
	}

	public String getNonStdFreqUnit() {
		return this.nonStdFreqUnit;
	}

	public void setNonStdFreqUnit(String nonStdFreqUnit) {
		this.nonStdFreqUnit = nonStdFreqUnit;
	}

	public String getValuationCMV() {
		return this.valuationCMV;
	}

	public void setValuationCMV(String valuationCMV) {
		this.valuationCMV = valuationCMV;
	}

	public String getValuationFSV() {
		return this.valuationFSV;
	}

	public void setValuationFSV(String valuationFSV) {
		this.valuationFSV = valuationFSV;
	}

	public String getRevalFreq() {
		return this.revalFreq;
	}

	public void setRevalFreq(String revalFreq) {
		this.revalFreq = revalFreq;
	}

	public String getRevalDate() {
		return this.revalDate;
	}

	public void setRevalDate(String revalDate) {
		this.revalDate = revalDate;
	}

	public String[][] getMapper() {
		String[][] input = { { "form.CDSItem", CDSItemMAPPER }, };
		return input;
	}
}