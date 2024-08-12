/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.proxy;

import com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail;
import com.integrosys.cms.app.tradingbook.bus.ICashMargin;
import com.integrosys.cms.app.tradingbook.bus.IGMRADeal;
import com.integrosys.cms.app.tradingbook.bus.IGMRADealDetail;
import com.integrosys.cms.app.tradingbook.bus.IGMRADealSummary;
import com.integrosys.cms.app.tradingbook.bus.IGMRADealVal;
import com.integrosys.cms.app.tradingbook.bus.IISDACSADealDetail;
import com.integrosys.cms.app.tradingbook.bus.IISDACSADealSummary;
import com.integrosys.cms.app.tradingbook.bus.IISDACSADealVal;
import com.integrosys.cms.app.tradingbook.bus.TradingBookException;
import com.integrosys.cms.app.tradingbook.trx.ICashMarginTrxValue;
import com.integrosys.cms.app.tradingbook.trx.IGMRADealTrxValue;
import com.integrosys.cms.app.tradingbook.trx.IGMRADealValTrxValue;
import com.integrosys.cms.app.tradingbook.trx.IISDACSADealValTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This interface defines the services that are available in CMS with respect to
 * the trading book life cycle.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface ITradingBookProxy {

	// ******************** Proxy methods for ISDA Deal ****************

	/**
	 * Gets the ISDA CSA deal summary by limit profile ID and agreement ID.
	 * 
	 * @param limitProfileID limit profile ID
	 * @param agreementID agreement ID
	 * @return IISDACSADealSummary
	 * @throws TradingBookException on errors encountered
	 */
	public IISDACSADealSummary getISDACSADealSummaryByAgreement(long limitProfileID, long agreementID)
			throws TradingBookException;

	/**
	 * Gets the ISDA CSA deal details by CMS deal ID.
	 * 
	 * @param cmsDealID CMS deal ID
	 * @return IISDACSADealDetail
	 * @throws TradingBookException on errors encountered
	 */
	public IISDACSADealDetail getISDACSADeal(long cmsDealID) throws TradingBookException;

	// ******************** Proxy methods for ISDA Deal Valuation
	// ****************

	/**
	 * Gets the ISDA CSA deal valuation trx value by agreement ID.
	 * 
	 * @param ctx transaction context
	 * @param agreementID agreement ID
	 * @return ISDA CSA deal valuation transaction value for the agreement ID
	 * @throws TradingBookException on errors encountered
	 */
	public IISDACSADealValTrxValue getISDACSADealValuationTrxValue(ITrxContext ctx, long agreementID)
			throws TradingBookException;

	/**
	 * Gets the ISDA CSA deal valuation trx value by transaction id.
	 * 
	 * @param ctx transaction context
	 * @param trxID transaction id
	 * @return ISDA CSA deal valuation transaction value
	 * @throws TradingBookException on errors encountered
	 */
	public IISDACSADealValTrxValue getISDACSADealValuationTrxValueByTrxID(ITrxContext ctx, String trxID)
			throws TradingBookException;

	/**
	 * Maker updates a list of ISDA CSA deal valuation.
	 * 
	 * @param ctx transaction context
	 * @param trxVal ISDA CSA deal valuation transaction value
	 * @param value a list of ISDA CSA deal valuation objects to use for
	 *        updating.
	 * @return updated ISDA CSA deal valuation transaction value
	 * @throws TradingBookException on errors encountered
	 */
	public IISDACSADealValTrxValue makerUpdateISDACSADealValuation(ITrxContext ctx, IISDACSADealValTrxValue trxVal,
			IISDACSADealVal[] value) throws TradingBookException;

	/**
	 * Maker saves a list of ISDA CSA deal valuation.
	 * 
	 * @param ctx transaction context
	 * @param trxVal ISDA CSA deal valuation transaction value
	 * @param value a list of ISDA CSA deal valuation objects to use for
	 *        updating.
	 * @return updated ISDA CSA deal valuation transaction value
	 * @throws TradingBookException on errors encountered
	 */
	public IISDACSADealValTrxValue makerSaveISDACSADealValuation(ITrxContext ctx, IISDACSADealValTrxValue trxVal,
			IISDACSADealVal[] value) throws TradingBookException;

	/**
	 * Maker cancel ISDA CSA deal valuation updated by him/her, or rejected by a
	 * checker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal ISDA CSA deal valuation transaction value
	 * @return the updated ISDA CSA deal valuation transaction value
	 * @throws TradingBookException on errors encountered
	 */
	public IISDACSADealValTrxValue makerCancelUpdateISDACSADealValuation(ITrxContext ctx, IISDACSADealValTrxValue trxVal)
			throws TradingBookException;

	/**
	 * Checker approve ISDA CSA deal valuation updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal ISDA CSA deal valuation transaction value
	 * @return the updated ISDA CSA deal valuation transaction value
	 * @throws TradingBookException on errors encountered
	 */
	public IISDACSADealValTrxValue checkerApproveUpdateISDACSADealValuation(ITrxContext ctx,
			IISDACSADealValTrxValue trxVal) throws TradingBookException;

	/**
	 * Checker reject ISDA CSA deal valuation updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal ISDA CSA deal valuation transaction value
	 * @return updated ISDA CSA deal valuation transaction value
	 * @throws TradingBookException on errors encountered
	 */
	public IISDACSADealValTrxValue checkerRejectUpdateISDACSADealValuation(ITrxContext ctx,
			IISDACSADealValTrxValue trxVal) throws TradingBookException;

	// ******************** Proxy methods for GMRA Deal ****************

	/**
	 * Gets the GMRA deal summary by limit profile ID and agreement ID.
	 * 
	 * @param ctx transaction context
	 * @param limitProfileID limit profile ID
	 * @param agreementID agreement ID
	 * @return IGMRADealSummary
	 * @throws TradingBookException on errors encountered
	 */
	public IGMRADealSummary getGMRADealSummaryByAgreement(long limitProfileID, long agreementID)
			throws TradingBookException;

	/**
	 * Gets the GMRA deal details by CMS Deal ID.
	 * 
	 * @param ctx transaction context
	 * @param cmsDealID CMS Deal ID
	 * @return IGMRADealDetail
	 * @throws TradingBookException on errors encountered
	 */
	public IGMRADealDetail getGMRADeal(long cmsDealID) throws TradingBookException;

	public ICPAgreementDetail getCounterPartyAgreementDetail(long agreementID) throws TradingBookException;

	// ******************** Proxy methods for Add/Edit/Remove GMRA Deal
	// ****************

	/**
	 * Gets the GMRA deal trx value by CMS Deal ID.
	 * 
	 * @param ctx transaction context
	 * @param cmsDealID CMS Deal ID
	 * @return GMRA deal transaction value for the CMS Deal ID
	 * @throws TradingBookException on errors encountered
	 */
	public IGMRADealTrxValue getGMRADealTrxValue(ITrxContext ctx, long cmsDealID) throws TradingBookException;

	/**
	 * Gets list of GMRA deal trx value by agreement ID.
	 * 
	 * @param ctx transaction context
	 * @param agreementID agreement ID
	 * @return GMRA deal transaction value for the agreement ID
	 * @throws TradingBookException on errors encountered
	 */
	public IGMRADealTrxValue[] getGMRADealTrxValueByAgreementID(ITrxContext ctx, long agreementID)
			throws TradingBookException;

	/**
	 * Gets the GMRA deal trx value by transaction id.
	 * 
	 * @param ctx transaction context
	 * @param trxID transaction id
	 * @return GMRA deal transaction value
	 * @throws TradingBookException on errors encountered
	 */
	public IGMRADealTrxValue getGMRADealTrxValueByTrxID(ITrxContext ctx, String trxID) throws TradingBookException;

	/**
	 * Maker updates a GMRA deal.
	 * 
	 * @param ctx transaction context
	 * @param trxVal GMRA deal transaction value
	 * @param agreementID agreement ID
	 * @param value a GMRA deal object to use for updating.
	 * @return updated GMRA deal transaction value
	 * @throws TradingBookException on errors encountered
	 */
	public IGMRADealTrxValue makerCreateGMRADeal(ITrxContext ctx, IGMRADealTrxValue trxVal, long agreementID,
			IGMRADeal value) throws TradingBookException;

	/**
	 * Maker updates a GMRA deal.
	 * 
	 * @param ctx transaction context
	 * @param trxVal GMRA deal transaction value
	 * @param value a GMRA deal object to use for updating.
	 * @return updated GMRA deal transaction value
	 * @throws TradingBookException on errors encountered
	 */
	public IGMRADealTrxValue makerUpdateGMRADeal(ITrxContext ctx, IGMRADealTrxValue trxVal, IGMRADeal value)
			throws TradingBookException;

	/**
	 * Maker close GMRA deal created by him/her, or rejected by a checker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal GMRA deal transaction value
	 * @return the updated GMRA deal transaction value
	 * @throws TradingBookException on errors encountered
	 */
	public IGMRADealTrxValue makerCloseCreateGMRADeal(ITrxContext ctx, IGMRADealTrxValue trxVal)
			throws TradingBookException;

	/**
	 * Maker close GMRA deal updated by him/her, or rejected by a checker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal GMRA deal transaction value
	 * @return the updated GMRA deal transaction value
	 * @throws TradingBookException on errors encountered
	 */
	public IGMRADealTrxValue makerCloseUpdateGMRADeal(ITrxContext ctx, IGMRADealTrxValue trxVal)
			throws TradingBookException;

	/**
	 * Checker approve GMRA deal updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal GMRA deal transaction value
	 * @return the updated GMRA deal transaction value
	 * @throws TradingBookException on errors encountered
	 */
	public IGMRADealTrxValue checkerApproveUpdateGMRADeal(ITrxContext ctx, IGMRADealTrxValue trxVal)
			throws TradingBookException;

	/**
	 * Checker approve GMRA deal deleted by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal GMRA deal transaction value
	 * @return the updated GMRA deal transaction value
	 * @throws TradingBookException on errors encountered
	 * @throws DeleteGMRADealException if the GMRA deal could not be deleted due
	 *         to the GMRA deal still involves in valuation transaction.
	 */
	public IGMRADealTrxValue checkerApproveDeleteGMRADeal(ITrxContext ctx, IGMRADealTrxValue trxVal)
			throws TradingBookException, DeleteGMRADealException;

	/**
	 * Checker reject GMRA deal updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal GMRA deal transaction value
	 * @return updated GMRA deal transaction value
	 * @throws TradingBookException on errors encountered
	 */
	public IGMRADealTrxValue checkerRejectUpdateGMRADeal(ITrxContext ctx, IGMRADealTrxValue trxVal)
			throws TradingBookException;

	/**
	 * Maker delete a GMRA deal.
	 * 
	 * @param ctx transaction context
	 * @param trxVal GMRA deal transaction value
	 * @param cmsDealID GMRA deal ID to be deleted
	 * @return updated GMRA deal transaction value
	 * @throws TradingBookException on errors encountered
	 */
	public IGMRADealTrxValue makerDeleteGMRADeal(ITrxContext ctx, IGMRADealTrxValue trxVal) throws TradingBookException;

	// ******************** Proxy methods for Input Valuation GMRA Deal
	// ****************

	/**
	 * Gets the GMRA deal valuation trx value by agreement ID.
	 * 
	 * @param ctx transaction context
	 * @param agreementID the agreement ID
	 * @return GMRA deal valuation transaction value
	 * @throws TradingBookException on errors encountered
	 */
	public IGMRADealValTrxValue getGMRADealValuationTrxValue(ITrxContext ctx, long agreementID)
			throws TradingBookException;

	/**
	 * Gets the GMRA deal valuation trx value by transaction id.
	 * 
	 * @param ctx transaction context
	 * @param trxID transaction id
	 * @return GMRA deal valuation transaction value
	 * @throws TradingBookException on errors encountered
	 */
	public IGMRADealValTrxValue getGMRADealValuationTrxValueByTrxID(ITrxContext ctx, String trxID)
			throws TradingBookException;

	/**
	 * Maker updates a list of GMRA deal valuation.
	 * 
	 * @param ctx transaction context
	 * @param trxVal GMRA deal valuation transaction value
	 * @param value a list of GMRA deal valuation objects to use for updating.
	 * @return updated GMRA deal valuation transaction value
	 * @throws TradingBookException on errors encountered
	 */
	public IGMRADealValTrxValue makerUpdateGMRADealValuation(ITrxContext ctx, IGMRADealValTrxValue trxVal,
			IGMRADealVal[] value) throws TradingBookException;

	/**
	 * Maker saves a list of GMRA deal valuation.
	 * 
	 * @param ctx transaction context
	 * @param trxVal GMRA deal valuation transaction value
	 * @param value a list of GMRA deal valuation objects to use for updating.
	 * @return updated GMRA deal valuation transaction value
	 * @throws TradingBookException on errors encountered
	 */
	public IGMRADealValTrxValue makerSaveGMRADealValuation(ITrxContext ctx, IGMRADealValTrxValue trxVal,
			IGMRADealVal[] value) throws TradingBookException;

	/**
	 * Maker cancel GMRA deal valuation updated by him/her, or rejected by a
	 * checker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal GMRA deal valuation transaction value
	 * @return the updated GMRA deal valuation transaction value
	 * @throws TradingBookException on errors encountered
	 */
	public IGMRADealValTrxValue makerCancelUpdateGMRADealValuation(ITrxContext ctx, IGMRADealValTrxValue trxVal)
			throws TradingBookException;

	/**
	 * Checker approve GMRA deal valuation updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal GMRA deal valuation transaction value
	 * @return the updated GMRA deal valuation transaction value
	 * @throws TradingBookException on errors encountered
	 */
	public IGMRADealValTrxValue checkerApproveUpdateGMRADealValuation(ITrxContext ctx, IGMRADealValTrxValue trxVal)
			throws TradingBookException;

	/**
	 * Checker reject GMRA deal valuation updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal GMRA deal valuation transaction value
	 * @return updated GMRA deal valuation transaction value
	 * @throws TradingBookException on errors encountered
	 */
	public IGMRADealValTrxValue checkerRejectUpdateGMRADealValuation(ITrxContext ctx, IGMRADealValTrxValue trxVal)
			throws TradingBookException;

	// ******************** Proxy methods for Cash Margin ****************

	/**
	 * Gets the cash margin trx value by agreement ID.
	 * 
	 * @param ctx transaction context
	 * @param agreementID the agreement ID
	 * @return cash margin transaction value
	 * @throws TradingBookException on errors encountered
	 */
	public ICashMarginTrxValue getCashMarginTrxValue(ITrxContext ctx, long agreementID) throws TradingBookException;

	/**
	 * Gets the cash margin trx value by transaction id.
	 * 
	 * @param ctx transaction context
	 * @param trxID transaction id
	 * @return cash margin transaction value
	 * @throws TradingBookException on errors encountered
	 */
	public ICashMarginTrxValue getCashMarginTrxValueByTrxID(ITrxContext ctx, String trxID) throws TradingBookException;

	/**
	 * Maker updates a list of cash margin.
	 * 
	 * @param ctx transaction context
	 * @param trxVal cash margin transaction value
	 * @param value a list of cash margin objects to use for updating.
	 * @return updated cash margin transaction value
	 * @throws TradingBookException on errors encountered
	 */
	public ICashMarginTrxValue makerUpdateCashMargin(ITrxContext ctx, ICashMarginTrxValue trxVal, ICashMargin[] value)
			throws TradingBookException;

	/**
	 * Maker saves a list of cash margin.
	 * 
	 * @param ctx transaction context
	 * @param trxVal cash margin transaction value
	 * @param value a list of cash margin objects to use for updating.
	 * @return updated cash margin transaction value
	 * @throws TradingBookException on errors encountered
	 */
	public ICashMarginTrxValue makerSaveCashMargin(ITrxContext ctx, ICashMarginTrxValue trxVal, ICashMargin[] value)
			throws TradingBookException;

	/**
	 * Maker cancel cash margin updated by him/her, or rejected by a checker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal cash margin transaction value
	 * @return the updated cash margin transaction value
	 * @throws TradingBookException on errors encountered
	 */
	public ICashMarginTrxValue makerCancelUpdateCashMargin(ITrxContext ctx, ICashMarginTrxValue trxVal)
			throws TradingBookException;

	/**
	 * Checker approve cash margin updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal cash margin transaction value
	 * @return the updated cash margin transaction value
	 * @throws TradingBookException on errors encountered
	 */
	public ICashMarginTrxValue checkerApproveUpdateCashMargin(ITrxContext ctx, ICashMarginTrxValue trxVal)
			throws TradingBookException;

	/**
	 * Checker reject cash margin updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal cash margin transaction value
	 * @return updated cash margin transaction value
	 * @throws TradingBookException on errors encountered
	 */
	public ICashMarginTrxValue checkerRejectUpdateCashMargin(ITrxContext ctx, ICashMarginTrxValue trxVal)
			throws TradingBookException;

}
