/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * This interface defines the services of a Trading Book business manager.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface ITradingBookBusManager extends Serializable {
	// ******************** Methods for ISDA Deal ****************

	/**
	 * Gets the ISDA CSA deal by CMS deal ID.
	 * 
	 * @param cmsDealID CMS deal ID
	 * @return IISDACSADeal
	 * @throws TradingBookException on errors encountered
	 */
	public IISDACSADeal getISDACSADeal(long cmsDealID) throws TradingBookException;

	/**
	 * Gets list of ISDA CSA deal by agreement ID.
	 * 
	 * @param agreementID agreement ID
	 * @return array of IISDACSADeal
	 * @throws TradingBookException on errors encountered
	 */
	public IISDACSADeal[] getISDACSADealByAgreementID(long agreementID) throws TradingBookException;

	/**
	 * Gets list of ISDA CSA deal valuation by group ID.
	 * 
	 * @param groupID group ID
	 * @return array of IISDACSADealVal
	 * @throws TradingBookException on errors encountered
	 */
	public IISDACSADealVal[] getISDACSADealValuationByGroupID(long groupID) throws TradingBookException;

	/**
	 * Updates the input list of deal valuation by CMS deal ID.
	 * 
	 * @param value a list of deal valuation
	 * @return array of IISDACSADealVal
	 * @throws TradingBookException on errors encountered
	 */
	public IISDACSADealVal[] updateISDACSADealValuationByCMSDealID(IDealValuation[] value) throws TradingBookException;

	// ******************** Methods for Deal Valuation ****************

	/**
	 * Gets list of deal valuation by agreement ID.
	 * 
	 * @param agreementID agreement ID
	 * @return array of IDealValuation
	 * @throws TradingBookException on errors encountered
	 */
	public IDealValuation[] getDealValuation(long agreementID) throws TradingBookException;

	/**
	 * Gets deal valuation by CMS deal ID.
	 * 
	 * @param cmsDealID CMS deal ID
	 * @return IDealValuation
	 * @throws TradingBookException on errors encountered
	 */
	public IDealValuation getDealValuationByCMSDealID(long cmsDealID) throws TradingBookException;

	/**
	 * Creates the input list of deal valuation.
	 * 
	 * @param value a list of deal valuation
	 * @return array of IDealValuation
	 * @throws TradingBookException on errors encountered
	 */
	public IDealValuation[] createDealValuation(IDealValuation[] value) throws TradingBookException;

	/**
	 * Updates the input list of deal valuation.
	 * 
	 * @param value a list of deal valuation
	 * @return array of IDealValuation
	 * @throws TradingBookException on error updating the deal valuation
	 */
	public IDealValuation[] updateDealValuation(IDealValuation[] value) throws TradingBookException;

	/**
	 * Updates the input list of deal valuation by CMS deal ID.
	 * 
	 * @param value a list of deal valuation
	 * @return array of IGMRADealVal
	 * @throws TradingBookException on error updating the deal valuation
	 */
	public IGMRADealVal[] updateGMRADealValuationByCMSDealID(IDealValuation[] value) throws TradingBookException;

	// ******************** Methods for GMRA Deal ****************

	/**
	 * Gets the GMRA deal valuation by group ID.
	 * 
	 * @param groupID group ID
	 * @return array of IGMRADealVal
	 * @throws TradingBookException on errors encountered
	 */
	public IGMRADealVal[] getGMRADealValuationByGroupID(long groupID) throws TradingBookException;

	/**
	 * Gets the GMRA deal details by CMS deal ID.
	 * 
	 * @param cmsDealID CMS deal ID
	 * @return IGMRADeal
	 * @throws TradingBookException on errors encountered
	 */
	public IGMRADeal getGMRADeal(long cmsDealID) throws TradingBookException;

	/**
	 * Gets the GMRA deal by agreement ID.
	 * 
	 * @param agreementID the agreement ID
	 * @return array of IGMRADeal
	 * @throws TradingBookException on errors encountered
	 */
	public IGMRADeal[] getGMRADealByAgreementID(long agreementID) throws TradingBookException;

	/**
	 * Creates GMRA deal for specific agreement ID.
	 * 
	 * @param agreementID the agreement ID
	 * @param value the GMRA deal of type IGMRADeal
	 * @return IGMRADeal
	 * @throws TradingBookException on erros creating the GMRA deal
	 */
	public IGMRADeal createGMRADeal(long agreementID, IGMRADeal value) throws TradingBookException;

	/**
	 * Updates the input list of GMRA deal.
	 * 
	 * @param value the GMRA deal of type IGMRADeal
	 * @return IGMRADeal
	 * @throws TradingBookException on error updating the GMRA deal
	 */
	public IGMRADeal updateGMRADeal(IGMRADeal value) throws TradingBookException;

	/**
	 * Delete the input list of GMRA deal.
	 * 
	 * @param value the GMRA deal of type IGMRADeal
	 * @return IGMRADeal
	 * @throws TradingBookException on error deleting the GMRA deal
	 */
	public IGMRADeal deleteGMRADeal(IGMRADeal value) throws TradingBookException;

	// ******************** Methods for Cash Margin ****************

	/**
	 * Get a list of cash margin by agreement id.
	 * 
	 * @param agreementID the agreement id
	 * @return array of ICashMargin
	 * @throws TradingBookException on error getting the cash margin
	 */
	public ICashMargin[] getCashMarginByAgreementID(long agreementID) throws TradingBookException;

	/**
	 * Get a list of cash margin by its group id.
	 * 
	 * @param groupID the group id
	 * @return array of ICashMargin
	 * @throws TradingBookException on error getting the cash margin
	 */
	public ICashMargin[] getCashMarginByGroupID(long groupID) throws TradingBookException;

	/**
	 * Creates a list of cash margin for specific agreement id.
	 * 
	 * @param agreementID the agreement id
	 * @param value a list of cash margin of type ICashMargin
	 * @return array of ICashMargin
	 * @throws TradingBookException on erros creating the cash margin
	 */
	public ICashMargin[] createCashMargin(long agreementID, ICashMargin[] value) throws TradingBookException;

	/**
	 * Updates the input list of cash margin for specific agreement id.
	 * 
	 * @param agreementID the agreement id
	 * @param value a list of cash margin of type ICashMargin
	 * @return array of ICashMargin
	 * @throws TradingBookException on error updating the cash margin
	 * @throws RemoteException on error during remote method call
	 */
	public ICashMargin[] updateCashMargin(long agreementID, ICashMargin[] value) throws TradingBookException;

}
