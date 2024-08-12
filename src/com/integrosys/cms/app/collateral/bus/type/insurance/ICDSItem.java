/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/insurance/ICDSItem.java,v 1.1 2005/09/29 09:39:37 hshii Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.insurance;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.IValuation;

/**
 * This interface defines the list of attributes that will be available to a
 * Credit Default Swaps
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/09/29 09:39:37 $ Tag: $Name: $
 */
public interface ICDSItem extends Serializable {

	public final static String HEDGE_TYPE_SPECIFIC = "S";

	public final static String HEDGE_TYPE_GENERAL = "G";

	public final static String SETTLEMENT_PHYSICAL = "P";

	public final static String SETTLEMENT_CASH = "C";

	/**
	 * Get CDS Item ID
	 * 
	 * @return long
	 */
	public long getCdsItemID();

	/**
	 * Set CDS Item ID
	 * 
	 * @param cdsItemID of type long
	 */
	public void setCdsItemID(long cdsItemID);

	/**
	 * Get CDS Item Status
	 * 
	 * @return String
	 */
	public String getStatus();

	/**
	 * Set CDS Item Status
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);

	/**
	 * Get reference ID
	 * 
	 * @return long
	 */
	public long getRefID();

	/**
	 * Set reference ID
	 * 
	 * @param refID of type long
	 */
	public void setRefID(long refID);

	/**
	 * Get Bank entity
	 * 
	 * @return String
	 */
	public String getBankEntity();

	/**
	 * Set Bank Entiry
	 * 
	 * @param bankEntity of type String
	 */
	public void setBankEntity(String bankEntity);

	/**
	 * Get Hedge Type
	 * 
	 * @return String
	 */
	public String getHedgeType();

	/**
	 * Set Hedge Type
	 * 
	 * @param hedgeType of type String
	 */
	public void setHedgeType(String hedgeType);

	/**
	 * Get Hedge Reference
	 * 
	 * @return String
	 */
	public String getHedgeRef();

	/**
	 * Set Hedge Reference
	 * 
	 * @param hedgeRef of type String
	 */
	public void setHedgeRef(String hedgeRef);

	/**
	 * Get CDS Reference
	 * 
	 * @return String
	 */
	public String getCdsRef();

	/**
	 * Set CDS Reference
	 * 
	 * @param cdsRef of type String
	 */
	public void setCdsRef(String cdsRef);

	/**
	 * Get Trade ID
	 * 
	 * @return String
	 */
	public String getTradeID();

	/**
	 * Set Trade ID
	 * 
	 * @param tradeID of type String
	 */
	public void setTradeID(String tradeID);

	/**
	 * Get Trade Date
	 * 
	 * @return Date
	 */
	public Date getTradeDate();

	/**
	 * Set Trade Date
	 * 
	 * @param tradeDate of type Date
	 */
	public void setTradeDate(Date tradeDate);

	/**
	 * Get Deal Date
	 * 
	 * @return Date
	 */
	public Date getDealDate();

	/**
	 * Set Deal Date
	 * 
	 * @param dealDate of type Date
	 */
	public void setDealDate(Date dealDate);

	/**
	 * Get Start Date
	 * 
	 * @return Date
	 */
	public Date getStartDate();

	/**
	 * Set Start Date
	 * 
	 * @param startDate of type Date
	 */
	public void setStartDate(Date startDate);

	/**
	 * Get CDS Maturity Date
	 * 
	 * @return Date
	 */
	public Date getCdsMaturityDate();

	/**
	 * Set CDS Maturity Date
	 * 
	 * @param cdsMaturityDate of type Date
	 */
	public void setCdsMaturityDate(Date cdsMaturityDate);

	/**
	 * Get Tenor Frequency
	 * 
	 * @return int
	 */
	public int getTenor();

	/**
	 * Set Tenor frequency
	 * 
	 * @param tenor of type int
	 */
	public void setTenor(int tenor);

	/**
	 * Get Tenor Frequency unit
	 * 
	 * @return String
	 */
	public String getTenorUnit();

	/**
	 * Set Tenor frequency unit
	 * 
	 * @param tenorUnit of type String
	 */
	public void setTenorUnit(String tenorUnit);

	/**
	 * Get Trade Currency
	 * 
	 * @return String
	 */
	public String getTradeCurrency();

	/**
	 * Set Trade Currency
	 * 
	 * @param tradeCurrency of type String
	 */
	public void setTradeCurrency(String tradeCurrency);

	/**
	 * Get Notional hedge Amount
	 * 
	 * @return Amount
	 */
	public Amount getNotionalHedgeAmt();

	/**
	 * Set Notional Hedge Amount
	 * 
	 * @param notionalHedgeAmt of type Amount
	 */
	public void setNotionalHedgeAmt(Amount notionalHedgeAmt);

	/**
	 * Get Reference Entity
	 * 
	 * @return String
	 */
	public String getReferenceEntity();

	/**
	 * Set Reference Entity
	 * 
	 * @param referenceEntity of type String
	 */
	public void setReferenceEntity(String referenceEntity);

	/**
	 * Get CDS Booking Location
	 * 
	 * @return String
	 */
	public String getCdsBookingLoc();

	/**
	 * Set CDS Booking Location
	 * 
	 * @param cdsBookingLoc of type String
	 */
	public void setCdsBookingLoc(String cdsBookingLoc);

	/**
	 * Get Loan/Bond Booking Location
	 * 
	 * @return String
	 */
	public String getLoanBondBkLoc();

	/**
	 * Set Loan/Bond Booking Location
	 * 
	 * @param loanBondBkLoc of type String
	 */
	public void setLoanBondBkLoc(String loanBondBkLoc);

	/**
	 * Get Reference Asset
	 * 
	 * @return String
	 */
	public String getReferenceAsset();

	/**
	 * Set Reference Asset
	 * 
	 * @param referenceAsset of type String
	 */
	public void setReferenceAsset(String referenceAsset);

	/**
	 * Get Issuer
	 * 
	 * @return String
	 */
	public String getIssuer();

	/**
	 * Set Issuer
	 * 
	 * @param issuer of type String
	 */
	public void setIssuer(String issuer);

	/**
	 * Get Issuer ID
	 * 
	 * @return String
	 */
	public String getIssuerID();

	/**
	 * Set Issuer ID
	 * 
	 * @param issuerID of type String
	 */
	public void setIssuerID(String issuerID);

	/**
	 * Get Issuer details
	 * 
	 * @return String
	 */
	public String getDetailsIssuer();

	/**
	 * Set issuer details
	 * 
	 * @param detailsIssuer of type String
	 */
	public void setDetailsIssuer(String detailsIssuer);

	/**
	 * Get Dealt Price
	 * 
	 * @return Amount
	 */
	public Amount getDealtPrice();

	/**
	 * Set Dealt Price
	 * 
	 * @param dealtPrice of type Amount
	 */
	public void setDealtPrice(Amount dealtPrice);

	/**
	 * Get Residual Maturity Field
	 * 
	 * @return Amount
	 */
	public Amount getResidualMaturityField();

	/**
	 * Set Residual Maturity Field
	 * 
	 * @param residualMaturityField of type String
	 */
	public void setResidualMaturityField(Amount residualMaturityField);

	/**
	 * Get Settlement
	 * 
	 * @return String
	 */
	public String getSettlement();

	/**
	 * Set Settlement
	 * 
	 * @param settlement of type String
	 */
	public void setSettlement(String settlement);

	/**
	 * Get Par value
	 * 
	 * @return Amount
	 */
	public Amount getParValue();

	/**
	 * Set par value
	 * 
	 * @param parValue of type Amount
	 */
	public void setParValue(Amount parValue);

	/**
	 * Get Decline Market Value
	 * 
	 * @return Amount
	 */
	public Amount getDeclineMarketValue();

	/**
	 * Set Decline Market Value
	 * 
	 * @param declineMarketValue of type Amount
	 */
	public void setDeclineMarketValue(Amount declineMarketValue);

	/**
	 * Get Event Determination date
	 * 
	 * @return Date
	 */
	public Date getEventDeterminationDate();

	/**
	 * Set Event Determination Date
	 * 
	 * @param eventDeterminationDate of type Date
	 */
	public void setEventDeterminationDate(Date eventDeterminationDate);

	/**
	 * Get Compliance Certification
	 * 
	 * @return boolean
	 */
	public boolean getComplianceCert();

	/**
	 * Set Compliance Certification
	 * 
	 * @param complianceCert of type boolean
	 */
	public void setComplianceCert(boolean complianceCert);

	// Valuation Details
	/**
	 * Get Valuation Details
	 * 
	 * @return IValution
	 */
	public IValuation getValuation();

	/**
	 * Set Valuation Details
	 * 
	 * @param valuation of type IValuation
	 */
	public void setValuation(IValuation valuation);

	/**
	 * Get Valuation currency
	 * 
	 * @return String
	 */
	public String getValuationCurrency();

	/**
	 * Get Nominal value
	 * 
	 * @return Amount
	 */
	public Amount getNominalValue();

	/**
	 * Set Nominal Value
	 * 
	 * @param nominalValue of type Amount
	 */
	public void setNominalValue(Amount nominalValue);

	/**
	 * Get margin
	 * 
	 * @return double
	 */
	public double getMargin();

	/**
	 * Set margin
	 * 
	 * @param margin of type double
	 */
	public void setMargin(double margin);
}