/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.tradingbook.bus.IGMRADeal;
import com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager;
import com.integrosys.cms.app.tradingbook.bus.TradingBookBusManagerFactory;
import com.integrosys.cms.app.tradingbook.bus.TradingBookException;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Abstract class that contain methods that is common among the set of GMRA deal
 * trx operations.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public abstract class AbstractGMRADealTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {
	/**
	 * Helper method to cast a generic trx value object to a GMRA deal specific
	 * transaction value object.
	 * 
	 * @param trxValue transaction value
	 * @return GMRA deal specific transaction value
	 * @throws TrxOperationException if the trxValue is not of type
	 *         IGMRADealTrxValue
	 */
	protected IGMRADealTrxValue getGMRADealTrxValue(ITrxValue trxValue) throws TrxOperationException {
		try {
			return (IGMRADealTrxValue) trxValue;
		}
		catch (ClassCastException e) {
			throw new TrxOperationException("ITrxValue is not of type IGMRADealTrxValue: " + e.toString());
		}
	}

	/**
	 * Create staging GMRA deal records.
	 * 
	 * @param value is of type IGMRADealTrxValue
	 * @return GMRA deal transaction value
	 * @throws TrxOperationException on errors
	 */
	protected IGMRADealTrxValue createStagingGMRADeal(IGMRADealTrxValue value) throws TrxOperationException {
		try {
			IGMRADeal intRates = value.getStagingGMRADeal();
			long agreemtID = value.getAgreementID();

			intRates.setStatus(value.getToState());

			ITradingBookBusManager mgr = TradingBookBusManagerFactory.getStagingTradingBookBusManager();
			intRates = mgr.createGMRADeal(agreemtID, intRates);
			value.setStagingGMRADeal(intRates);
			return value;
		}
		catch (TradingBookException e) {
			throw new TrxOperationException("TradingBookException caught!", e);
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Create actual GMRA deal records.
	 * 
	 * @param value is of type IGMRADealTrxValue
	 * @return GMRA deal transaction value
	 * @throws TrxOperationException on errors creating the GMRA deal
	 */
	protected IGMRADealTrxValue createActualGMRADeal(IGMRADealTrxValue value) throws TrxOperationException {
		try {
			IGMRADeal gmra = value.getStagingGMRADeal(); // create get from
															// staging
			long agreemtID = value.getAgreementID();

			gmra.setStatus(value.getToState());

			ITradingBookBusManager mgr = TradingBookBusManagerFactory.getActualTradingBookBusManager();
			gmra = mgr.createGMRADeal(agreemtID, gmra);

			value.setGMRADeal(gmra); // set into actual
			return value;
		}
		catch (TradingBookException e) {
			throw new TrxOperationException("TradingBookException caught!", e);
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Update actual GMRA deal.
	 * 
	 * @param value is of type IGMRADealTrxValue
	 * @return GMRA deal transaction value
	 * @throws TrxOperationException on errors updating the actual collateral
	 */
	protected IGMRADealTrxValue updateActualGMRADeal(IGMRADealTrxValue value) throws TrxOperationException {
		try {
			IGMRADeal actual = value.getGMRADeal();
			IGMRADeal staging = value.getStagingGMRADeal(); // update from
															// staging

			long cmsDealID = staging.getCMSDealID();
			long stageVersion = staging.getVersionTime();
			String stageStatus = staging.getStatus();

			staging.setCMSDealID(actual.getCMSDealID()); // but maintain
															// actual's pk
			staging.setVersionTime(actual.getVersionTime()); // and actual's
																// version time
			staging.setStatus(value.getToState());

			ITradingBookBusManager mgr = TradingBookBusManagerFactory.getActualTradingBookBusManager();

			actual = mgr.updateGMRADeal(staging);

			value.setGMRADeal(actual); // set into actual

			value.getStagingGMRADeal().setCMSDealID(cmsDealID);
			value.getStagingGMRADeal().setVersionTime(stageVersion);
			value.getStagingGMRADeal().setStatus(stageStatus);

			return value;
		}
		catch (TradingBookException e) {
			throw new TrxOperationException("GMRADealException caught!", e);
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Method to delete a GMRA deal record in staging.
	 * 
	 * @param value is of type IGMRADealTrxValue
	 * @return GMRA deal transaction value
	 * @throws TrxOperationException on errors
	 */
	protected IGMRADealTrxValue deleteStagingGMRADeal(IGMRADealTrxValue value) throws TrxOperationException {
		try {
			IGMRADeal deal = value.getStagingGMRADeal();
			long agreemtID = value.getAgreementID();

			deal.setStatus(ICMSConstant.STATE_DELETED);

			ITradingBookBusManager mgr = TradingBookBusManagerFactory.getStagingTradingBookBusManager();
			deal = mgr.deleteGMRADeal(deal);
			value.setStagingGMRADeal(deal);
			return value;
		}
		catch (TradingBookException e) {
			throw new TrxOperationException("TradingBookException caught!", e);
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Method to delete a GMRA deal record in actual.
	 * 
	 * @param value is of type IGMRADealTrxValue
	 * @return GMRA deal transaction value
	 * @throws TrxOperationException on errors
	 */
	protected IGMRADealTrxValue deleteActualGMRADeal(IGMRADealTrxValue value) throws TrxOperationException {
		try {

			IGMRADeal deal = value.getGMRADeal();
			long agreemtID = value.getAgreementID();

			deal.setStatus(ICMSConstant.STATE_DELETED);

			ITradingBookBusManager mgr = TradingBookBusManagerFactory.getActualTradingBookBusManager();
			deal = mgr.deleteGMRADeal(deal);
			value.setGMRADeal(deal);
			return value;
		}
		catch (TradingBookException e) {
			throw new TrxOperationException("TradingBookException caught!", e);
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Method to create a transaction record
	 * 
	 * @param value is of type IGMRADealTrxValue
	 * @return GMRA deal transaction value
	 * @throws TrxOperationException on errors
	 */
	protected IGMRADealTrxValue createTransaction(IGMRADealTrxValue value) throws TrxOperationException {
		try {
			value = prepareTrxValue(value);
			ICMSTrxValue tempValue = super.createTransaction(value);
			OBGMRADealTrxValue newValue = new OBGMRADealTrxValue(tempValue);
			newValue.setGMRADeal(value.getGMRADeal());
			newValue.setStagingGMRADeal(value.getStagingGMRADeal());
			return newValue;
		}
		catch (TrxOperationException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Method to update a transaction record.
	 * 
	 * @param value is of type IGMRADealTrxValue
	 * @return GMRA deal transaction value
	 * @throws TrxOperationException on errors updating the transaction
	 */
	protected IGMRADealTrxValue updateTransaction(IGMRADealTrxValue value) throws TrxOperationException {
		try {
			value = prepareTrxValue(value);

			ICMSTrxValue tempValue = super.updateTransaction(value);
			OBGMRADealTrxValue newValue = new OBGMRADealTrxValue(tempValue);
			newValue.setGMRADeal(value.getGMRADeal());
			newValue.setStagingGMRADeal(value.getStagingGMRADeal());
			return newValue;
		}
		catch (TrxOperationException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Prepares a result object to be returned.
	 * 
	 * @param value is of type IGMRADealTrxValue
	 * @return transaction result
	 */
	protected ITrxResult prepareResult(IGMRADealTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

	/**
	 * Helper method to prepare transaction object.
	 * 
	 * @param value of type IGMRADealTrxValue
	 * @return GMRA deal transaction value
	 */
	protected IGMRADealTrxValue prepareTrxValue(IGMRADealTrxValue value) {
		if (value != null) {
			IGMRADeal actual = value.getGMRADeal();
			IGMRADeal staging = value.getStagingGMRADeal();

			value.setReferenceID(actual != null ? String.valueOf(actual.getCMSDealID()) : null);
			value.setStagingReferenceID(staging != null ? String.valueOf(staging.getCMSDealID()) : null);
		}
		return value;
	}

}
