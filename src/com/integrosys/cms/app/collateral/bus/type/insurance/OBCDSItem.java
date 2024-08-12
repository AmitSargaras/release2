/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/insurance/OBCDSItem.java,v 1.1 2005/09/29 09:39:37 hshii Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.insurance;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.IValuation;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/09/29 09:39:37 $ Tag: $Name: $
 */

public class OBCDSItem implements ICDSItem {

	private long cdsItemID = ICMSConstant.LONG_INVALID_VALUE;

	private String status = ICMSConstant.STATE_ACTIVE;

	private long refID = ICMSConstant.LONG_INVALID_VALUE;

	private String bankEntity;

	private String hedgeType;

	private String hedgeRef;

	private String cdsRef;

	private String tradeID;

	private Date tradeDate;

	private Date dealDate;

	private Date startDate;

	private Date cdsMaturityDate;

	private int tenor = ICMSConstant.INT_INVALID_VALUE;

	private String tenorUnit;

	private String tradeCurrency;

	private Amount notionalHedgeAmt;

	private String referenceEntity;

	private String cdsBookingLoc;

	private String loanBondBkLoc;

	private String referenceAsset;

	private String issuer;

	private String issuerID;

	private String detailsIssuer;

	private Amount dealtPrice;

	private Amount residualMaturityField;

	private String settlement;

	private Amount parValue;

	private Amount declineMarketValue;

	private Date eventDeterminationDate;

	private boolean complianceCert;

	// valuation details
	private IValuation valuation;

	private Amount nominalValue;

	private double margin = ICMSConstant.DOUBLE_INVALID_VALUE;

	/**
	 * Default Constructor.
	 */
	public OBCDSItem() {
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ICDSItem
	 */
	public OBCDSItem(ICDSItem obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get CDS Item ID
	 * 
	 * @return long
	 */
	public long getCdsItemID() {
		return this.cdsItemID;
	}

	/**
	 * Set CDS Item ID
	 * 
	 * @param cdsItemID of type long
	 */
	public void setCdsItemID(long cdsItemID) {
		this.cdsItemID = cdsItemID;
	}

	/**
	 * Get CDS Item Status
	 * 
	 * @return String
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * Set CDS Item Status
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Get reference ID
	 * 
	 * @return long
	 */
	public long getRefID() {
		return this.refID;
	}

	/**
	 * Set reference ID
	 * 
	 * @param refID of type long
	 */
	public void setRefID(long refID) {
		this.refID = refID;
	}

	/**
	 * Get Bank entity
	 * 
	 * @return String
	 */
	public String getBankEntity() {
		return this.bankEntity;
	}

	/**
	 * Set Bank Entiry
	 * 
	 * @param bankEntity of type String
	 */
	public void setBankEntity(String bankEntity) {
		this.bankEntity = bankEntity;
	}

	/**
	 * Get Hedge Type
	 * 
	 * @return String
	 */
	public String getHedgeType() {
		return this.hedgeType;
	}

	/**
	 * Set Hedge Type
	 * 
	 * @param hedgeType of type String
	 */
	public void setHedgeType(String hedgeType) {
		this.hedgeType = hedgeType;
	}

	/**
	 * Get Hedge Reference
	 * 
	 * @return String
	 */
	public String getHedgeRef() {
		return this.hedgeRef;
	}

	/**
	 * Set Hedge Reference
	 * 
	 * @param hedgeRef of type String
	 */
	public void setHedgeRef(String hedgeRef) {
		this.hedgeRef = hedgeRef;
	}

	/**
	 * Get CDS Reference
	 * 
	 * @return String
	 */
	public String getCdsRef() {
		return this.cdsRef;
	}

	/**
	 * Set CDS Reference
	 * 
	 * @param cdsRef of type String
	 */
	public void setCdsRef(String cdsRef) {
		this.cdsRef = cdsRef;
	}

	/**
	 * Get Trade ID
	 * 
	 * @return String
	 */
	public String getTradeID() {
		return this.tradeID;
	}

	/**
	 * Set Trade ID
	 * 
	 * @param tradeID of type String
	 */
	public void setTradeID(String tradeID) {
		this.tradeID = tradeID;
	}

	/**
	 * Get Trade Date
	 * 
	 * @return Date
	 */
	public Date getTradeDate() {
		return this.tradeDate;
	}

	/**
	 * Set Trade Date
	 * 
	 * @param tradeDate of type Date
	 */
	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	/**
	 * Get Deal Date
	 * 
	 * @return Date
	 */
	public Date getDealDate() {
		return this.dealDate;
	}

	/**
	 * Set Deal Date
	 * 
	 * @param dealDate of type Date
	 */
	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}

	/**
	 * Get Start Date
	 * 
	 * @return Date
	 */
	public Date getStartDate() {
		return this.startDate;
	}

	/**
	 * Set Start Date
	 * 
	 * @param startDate of type Date
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Get CDS Maturity Date
	 * 
	 * @return Date
	 */
	public Date getCdsMaturityDate() {
		return this.cdsMaturityDate;
	}

	/**
	 * Set CDS Maturity Date
	 * 
	 * @param cdsMaturityDate of type Date
	 */
	public void setCdsMaturityDate(Date cdsMaturityDate) {
		this.cdsMaturityDate = cdsMaturityDate;
	}

	/**
	 * Get Tenor Frequency
	 * 
	 * @return int
	 */
	public int getTenor() {
		return this.tenor = tenor;
	}

	/**
	 * Set Tenor frequency
	 * 
	 * @param tenor of type int
	 */
	public void setTenor(int tenor) {
		this.tenor = tenor;
	}

	/**
	 * Get Tenor Frequency unit
	 * 
	 * @return String
	 */
	public String getTenorUnit() {
		return this.tenorUnit = tenorUnit;
	}

	/**
	 * Set Tenor frequency unit
	 * 
	 * @param tenorUnit of type String
	 */
	public void setTenorUnit(String tenorUnit) {
		this.tenorUnit = tenorUnit;
	}

	/**
	 * Get Trade Currency
	 * 
	 * @return String
	 */
	public String getTradeCurrency() {
		return this.tradeCurrency;
	}

	/**
	 * Set Trade Currency
	 * 
	 * @param tradeCurrency of type String
	 */
	public void setTradeCurrency(String tradeCurrency) {
		this.tradeCurrency = tradeCurrency;
	}

	/**
	 * Get Notional hedge Amount
	 * 
	 * @return Amount
	 */
	public Amount getNotionalHedgeAmt() {
		return this.notionalHedgeAmt;
	}

	/**
	 * Set Notional Hedge Amount
	 * 
	 * @param notionalHedgeAmt of type Amount
	 */
	public void setNotionalHedgeAmt(Amount notionalHedgeAmt) {
		this.notionalHedgeAmt = notionalHedgeAmt;
	}

	/**
	 * Get Reference Entity
	 * 
	 * @return String
	 */
	public String getReferenceEntity() {
		return this.referenceEntity;
	}

	/**
	 * Set Reference Entity
	 * 
	 * @param referenceEntity of type String
	 */
	public void setReferenceEntity(String referenceEntity) {
		this.referenceEntity = referenceEntity;
	}

	/**
	 * Get CDS Booking Location
	 * 
	 * @return String
	 */
	public String getCdsBookingLoc() {
		return this.cdsBookingLoc;
	}

	/**
	 * Set CDS Booking Location
	 * 
	 * @param cdsBookingLoc of type String
	 */
	public void setCdsBookingLoc(String cdsBookingLoc) {
		this.cdsBookingLoc = cdsBookingLoc;
	}

	/**
	 * Get Loan/Bond Booking Location
	 * 
	 * @return String
	 */
	public String getLoanBondBkLoc() {
		return this.loanBondBkLoc;
	}

	/**
	 * Set Loan/Bond Booking Location
	 * 
	 * @param loanBondBkLoc of type String
	 */
	public void setLoanBondBkLoc(String loanBondBkLoc) {
		this.loanBondBkLoc = loanBondBkLoc;
	}

	/**
	 * Get Reference Asset
	 * 
	 * @return String
	 */
	public String getReferenceAsset() {
		return this.referenceAsset;
	}

	/**
	 * Set Reference Asset
	 * 
	 * @param referenceAsset of type String
	 */
	public void setReferenceAsset(String referenceAsset) {
		this.referenceAsset = referenceAsset;
	}

	/**
	 * Get Issuer
	 * 
	 * @return String
	 */
	public String getIssuer() {
		return this.issuer;
	}

	/**
	 * Set Issuer
	 * 
	 * @param issuer of type String
	 */
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	/**
	 * Get Issuer ID
	 * 
	 * @return String
	 */
	public String getIssuerID() {
		return this.issuerID;
	}

	/**
	 * Set Issuer ID
	 * 
	 * @param issuerID of type String
	 */
	public void setIssuerID(String issuerID) {
		this.issuerID = issuerID;
	}

	/**
	 * Get Issuer details
	 * 
	 * @return String
	 */
	public String getDetailsIssuer() {
		return this.detailsIssuer;
	}

	/**
	 * Set issuer details
	 * 
	 * @param detailsIssuer of type String
	 */
	public void setDetailsIssuer(String detailsIssuer) {
		this.detailsIssuer = detailsIssuer;
	}

	/**
	 * Get Dealt Price
	 * 
	 * @return Amount
	 */
	public Amount getDealtPrice() {
		return this.dealtPrice;
	}

	/**
	 * Set Dealt Price
	 * 
	 * @param dealtPrice of type Amount
	 */
	public void setDealtPrice(Amount dealtPrice) {
		this.dealtPrice = dealtPrice;
	}

	/**
	 * Get Residual Maturity Field
	 * 
	 * @return Amount
	 */
	public Amount getResidualMaturityField() {
		return this.residualMaturityField;
	}

	/**
	 * Set Residual Maturity Field
	 * 
	 * @param residualMaturityField of type Amount
	 */
	public void setResidualMaturityField(Amount residualMaturityField) {
		this.residualMaturityField = residualMaturityField;
	}

	/**
	 * Get Settlement
	 * 
	 * @return String
	 */
	public String getSettlement() {
		return this.settlement;
	}

	/**
	 * Set Settlement
	 * 
	 * @param settlement of type String
	 */
	public void setSettlement(String settlement) {
		this.settlement = settlement;
	}

	/**
	 * Get Par value
	 * 
	 * @return Amount
	 */
	public Amount getParValue() {
		return this.parValue;
	}

	/**
	 * Set par value
	 * 
	 * @param parValue of type Amount
	 */
	public void setParValue(Amount parValue) {
		this.parValue = parValue;
	}

	/**
	 * Get Decline Market Value
	 * 
	 * @return Amount
	 */
	public Amount getDeclineMarketValue() {
		return this.declineMarketValue;
	}

	/**
	 * Set Decline Market Value
	 * 
	 * @param declineMarketValue of type Amount
	 */
	public void setDeclineMarketValue(Amount declineMarketValue) {
		this.declineMarketValue = declineMarketValue;
	}

	/**
	 * Get Event Determination date
	 * 
	 * @return Date
	 */
	public Date getEventDeterminationDate() {
		return this.eventDeterminationDate;
	}

	/**
	 * Set Event Determination Date
	 * 
	 * @param eventDeterminationDate of type Date
	 */
	public void setEventDeterminationDate(Date eventDeterminationDate) {
		this.eventDeterminationDate = eventDeterminationDate;
	}

	/**
	 * Get Compliance Certification
	 * 
	 * @return boolean
	 */
	public boolean getComplianceCert() {
		return this.complianceCert;
	}

	/**
	 * Set Compliance Certification
	 * 
	 * @param complianceCert of type boolean
	 */
	public void setComplianceCert(boolean complianceCert) {
		this.complianceCert = complianceCert;
	}

	// Valuation Details
	/**
	 * Get Valuation Details
	 * 
	 * @return IValution
	 */
	public IValuation getValuation() {
		return this.valuation;
	}

	/**
	 * Set Valuation Details
	 * 
	 * @param valuation of type IValuation
	 */
	public void setValuation(IValuation valuation) {
		this.valuation = valuation;
	}

	/**
	 * Get Valuation currency
	 * 
	 * @return String
	 */
	public String getValuationCurrency() {
		if (getValuation() != null) {
			return getValuation().getCurrencyCode();
		}
		return null;
	}

	/**
	 * Get Nominal value
	 * 
	 * @return Amount
	 */
	public Amount getNominalValue() {
		return this.nominalValue;
	}

	/**
	 * Set Nominal Value
	 * 
	 * @param nominalValue of type Amount
	 */
	public void setNominalValue(Amount nominalValue) {
		this.nominalValue = nominalValue;
	}

	/**
	 * Get margin
	 * 
	 * @return double
	 */
	public double getMargin() {
		return this.margin;
	}

	/**
	 * Set margin
	 * 
	 * @param margin of type double
	 */
	public void setMargin(double margin) {
		this.margin = margin;
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
	 * Return the hash code
	 * 
	 * @return int
	 */
	public int hashCode() {
		String hash = String.valueOf(cdsItemID);
		return hash.hashCode();
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
		else if (!(obj instanceof OBCDSItem)) {
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
}