/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.interestrate.proxy;

import java.util.Date;

import com.integrosys.cms.app.interestrate.bus.IInterestRate;
import com.integrosys.cms.app.interestrate.bus.InterestRateException;
import com.integrosys.cms.app.interestrate.trx.IInterestRateTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This interface defines the services that are available in CMS with respect to
 * the interestrate life cycle.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface IInterestRateProxy {

	/**
	 * Gets the interest rate trx value by interest rate type and month.
	 * 
	 * @param ctx transaction context
	 * @param intRateType the type of interest rate
	 * @param monthYear the date for the interest rate
	 * @return interest rate transaction value for the type and month
	 * @throws InterestRateException on errors encountered
	 */
	public IInterestRateTrxValue getInterestRateTrxValue(ITrxContext ctx, String intRateType, Date monthYear)
			throws InterestRateException;

	/**
	 * Gets the interest rate trx value by transaction id.
	 * 
	 * @param ctx transaction context
	 * @param trxID transaction id
	 * @return interest rate transaction value
	 * @throws InterestRateException on errors encountered
	 */
	public IInterestRateTrxValue getInterestRateTrxValueByTrxID(ITrxContext ctx, String trxID)
			throws InterestRateException;

	/**
	 * Maker updates a list of interest rate.
	 * 
	 * @param ctx transaction context
	 * @param trxVal interest rate transaction value
	 * @param intRates a list of interest rate objects to use for updating.
	 * @return updated interest rate transaction value
	 * @throws InterestRateException on errors encountered
	 */
	public IInterestRateTrxValue makerUpdateInterestRate(ITrxContext ctx, IInterestRateTrxValue trxVal,
			IInterestRate[] intRates) throws InterestRateException;

	/**
	 * Maker saves a list of interest rate.
	 * 
	 * @param ctx transaction context
	 * @param trxVal interest rate transaction value
	 * @param intRates a list of interest rate objects to use for updating.
	 * @return updated interest rate transaction value
	 * @throws InterestRateException on errors encountered
	 */
	public IInterestRateTrxValue makerSaveInterestRate(ITrxContext ctx, IInterestRateTrxValue trxVal,
			IInterestRate[] intRates) throws InterestRateException;

	/**
	 * Maker cancel interest rate updated by him/her, or rejected by a checker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal interest rate transaction value
	 * @return the updated interest rate transaction value
	 * @throws InterestRateException on errors encountered
	 */
	public IInterestRateTrxValue makerCancelUpdateInterestRate(ITrxContext ctx, IInterestRateTrxValue trxVal)
			throws InterestRateException;

	/**
	 * Checker approve interest rate updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal interest rate transaction value
	 * @return updated transaction value
	 * @throws InterestRateException on errors encountered
	 */
	public IInterestRateTrxValue checkerApproveUpdateInterestRate(ITrxContext ctx, IInterestRateTrxValue trxVal)
			throws InterestRateException;

	/**
	 * Checker reject interest rate updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal interest rate transaction value
	 * @return updated interest rate transaction value
	 * @throws InterestRateException on errors encountered
	 */
	public IInterestRateTrxValue checkerRejectUpdateInterestRate(ITrxContext ctx, IInterestRateTrxValue trxVal)
			throws InterestRateException;

}
