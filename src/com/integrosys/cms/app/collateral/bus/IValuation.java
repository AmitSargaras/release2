/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/IValuation.java,v 1.14 2006/02/22 00:54:56 jzhan Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;

/**
 * This interface represents valuation details of the collateral.
 * 
 * @author $Author: jzhan $<br>
 * @version $Revision: 1.14 $
 * @since $Date: 2006/02/22 00:54:56 $ Tag: $Name: $
 */
public interface IValuation extends Serializable {
	/**
	 * Get value after margin.
	 * 
	 * @return Amount
	 */
	public Amount getAfterMarginValue();

	/**
	 * Get value before margin.
	 * 
	 * @return Amount
	 */
	public Amount getBeforeMarginValue();

	/**
	 * Get current market value (CMV).
	 * 
	 * @return Amount
	 */
	public Amount getCMV();

	/**
	 * Get collateral id.
	 * 
	 * @return long
	 */
	public long getCollateralID();

	/**
	 * Get the comments
	 * 
	 * @return comments
	 */
	public String getComments();

	/**
	 * Get currency used for the valuation.
	 * 
	 * @return String
	 */
	public String getCurrencyCode();

	public Date getEvaluationDateFSV();

	/**
	 * Get Forced Sale Value (FSV).
	 * 
	 * @return Amount
	 */
	public Amount getFSV();

	/**
	 * Get evaluation date of FSV.
	 * 
	 * @return Date
	 */
	public Date getFSVEvaluationDate();

	/**
	 * Get last evaluation date.
	 * 
	 * @return Date
	 */
	public Date getLastEvaluationDate();

	public Long getLosValuationId();

	/**
	 * Get next revaluation date.
	 * 
	 * @return Date
	 */
	public Date getNextRevaluationDate();

	/**
	 * Get non revaluation frequency.
	 * 
	 * @return int
	 */
	public int getNonRevaluationFreq();

	/**
	 * Get unit of measurement of non revaluation frequency.
	 * 
	 * @return String
	 */
	public String getNonRevaluationFreqUnit();

	public Amount getOlv();

	public Double getRemainusefullife();

	public Amount getReservePrice();

	public Date getReservePriceDate();

	/**
	 * Get revaluation date.
	 * 
	 * @return Date
	 */
	public Date getRevaluationDate();

	/**
	 * Get revaluation frequency.
	 * 
	 * @return int
	 */
	public int getRevaluationFreq();

	/**
	 * Get unit of measurement of revaluation frequency.
	 * 
	 * @return String
	 */
	public String getRevaluationFreqUnit();

	public String getSourceId();

	public String getSourceType();

	/**
	 * Get the update date.
	 * 
	 * @return Date
	 */
	public Date getUpdateDate();

	public String getValuationbasis();

	/**
	 * Get the valuation date.
	 * 
	 * @return Date
	 */
	public Date getValuationDate();

	/**
	 * Get valuation id.
	 * 
	 * @return long
	 */
	public long getValuationID();

	public String getValuationType();

	/**
	 * Get valuer name.
	 * 
	 * @return String
	 */
	public String getValuerName();

	/**
	 * Set value after margin.
	 * 
	 * @param afterMarginValue of type Amount
	 */
	public void setAfterMarginValue(Amount afterMarginValue);

	/**
	 * Set value before margin.
	 * 
	 * @param beforeMarginValue of type Amount
	 */
	public void setBeforeMarginValue(Amount beforeMarginValue);

	/**
	 * Set current market value.
	 * 
	 * @param cMV is of type Amount
	 */
	public void setCMV(Amount cMV);

	/**
	 * Set collateral id.
	 * 
	 * @param collateralID of type long
	 */
	public void setCollateralID(long collateralID);

	/**
	 * Set the comments
	 * 
	 * @param comments of type String
	 */
	public void setComments(String comments);

	/**
	 * Set currency used for the valuation.
	 * 
	 * @param currencyCode is of type String
	 */
	public void setCurrencyCode(String currencyCode);

	public void setEvaluationDateFSV(Date evaluationDateFSV);

	/**
	 * set forced sale value.
	 * 
	 * @param fSV is of type Amount
	 */
	public void setFSV(Amount fSV);

	/**
	 * Set evaluation date of FSV.
	 * 
	 * @param fSVEvaluationDate of type Date
	 */
	public void setFSVEvaluationDate(Date fSVEvaluationDate);

	/**
	 * Set last evaluation date.
	 * 
	 * @param lastEvaluationDate of type Date
	 */
	public void setLastEvaluationDate(Date lastEvaluationDate);

	public void setLosValuationId(Long losValuationId);

	/**
	 * Set next revaluation date.
	 * 
	 * @param nextRevaluationDate of type Date
	 */
	public void setNextRevaluationDate(Date nextRevaluationDate);

	/**
	 * Set non revaluation frequency.
	 * 
	 * @param nonRevaluationFreq of type int
	 */
	public void setNonRevaluationFreq(int nonRevaluationFreq);

	/**
	 * Set unit of measurement of non revaluation frequency.
	 * 
	 * @param nonRevaluationFreqUnit of type String
	 */
	public void setNonRevaluationFreqUnit(String nonRevaluationFreqUnit);

	public void setOlv(Amount olv);

	public void setRemainusefullife(Double remainusefullife);

	public void setReservePrice(Amount reservePrice);

	public void setReservePriceDate(Date reservePriceDate);

	/**
	 * Set revaluation date.
	 * 
	 * @param revaluationDate of type Date
	 */
	public void setRevaluationDate(Date revaluationDate);

	/**
	 * Set revaluation frequency.
	 * 
	 * @param revaluationFreq of type int
	 */
	public void setRevaluationFreq(int revaluationFreq);

	/**
	 * Set unit of measurement of revaluation frequency.
	 * 
	 * @param revaluationFreqUnit of type String
	 */
	public void setRevaluationFreqUnit(String revaluationFreqUnit);

	public void setSourceId(String sourceId);

	public void setSourceType(String sourceType);

	/**
	 * Set update date.
	 * 
	 * @param updateDate is of type Date
	 */
	public void setUpdateDate(Date updateDate);

	public void setValuationbasis(String valuationbasis);

	/**
	 * Set valuation date.
	 * 
	 * @param valuationDate is of type Date
	 */
	public void setValuationDate(Date valuationDate);

	/**
	 * Set valuation id.
	 * 
	 * @param valuationID is of type long
	 */
	public void setValuationID(long valuationID);

	public void setValuationType(String valuationType);

	/**
	 * Set valuer name.
	 * 
	 * @param valuerName is of type String
	 */
	public void setValuerName(String valuerName);

}