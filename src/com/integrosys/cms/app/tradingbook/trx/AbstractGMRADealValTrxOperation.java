/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.trx;

import java.util.ArrayList;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.tradingbook.bus.IDealValuation;
import com.integrosys.cms.app.tradingbook.bus.IGMRADealVal;
import com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager;
import com.integrosys.cms.app.tradingbook.bus.OBGMRADealVal;
import com.integrosys.cms.app.tradingbook.bus.TradingBookBusManagerFactory;
import com.integrosys.cms.app.tradingbook.bus.TradingBookException;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Abstract class that contain methods that is common among the set of GMRA deal
 * valuation trx operations.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public abstract class AbstractGMRADealValTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {
	/**
	 * Helper method to cast a generic trx value object to a GMRA deal valuation
	 * specific transaction value object.
	 * 
	 * @param trxValue transaction value
	 * @return GMRA deal valuation specific transaction value
	 * @throws TrxOperationException if the trxValue is not of type
	 *         IGMRADealValTrxValue
	 */
	protected IGMRADealValTrxValue getGMRADealValuationTrxValue(ITrxValue trxValue) throws TrxOperationException {
		try {
			return (IGMRADealValTrxValue) trxValue;
		}
		catch (ClassCastException e) {
			throw new TrxOperationException("ITrxValue is not of type IGMRADealValTrxValue: " + e.toString());
		}
	}

	/**
	 * Create staging GMRA deal valuation records.
	 * 
	 * @param value is of type IGMRADealValTrxValue
	 * @return GMRA deal valuation transaction value
	 * @throws TrxOperationException on errors
	 */
	protected IGMRADealValTrxValue createStagingGMRADealValuation(IGMRADealValTrxValue value)
			throws TrxOperationException {
		try {
			IGMRADealVal[] staging = value.getStagingGMRADealValuation();
			IGMRADealVal[] actual = value.getGMRADealValuation();

			if (actual != null) {
				DefaultLogger.debug(this, " Actual length: " + actual.length);
			}
			DefaultLogger.debug(this, " Staging length: " + staging.length);

			for (int i = 0; i < staging.length; i++) {
				staging[i].setStatus(value.getToState());
			}

			ITradingBookBusManager mgr = TradingBookBusManagerFactory.getStagingTradingBookBusManager();
			IDealValuation[] stageValArray = mgr.createDealValuation(staging);

			ITradingBookBusManager actualMgr = TradingBookBusManagerFactory.getActualTradingBookBusManager();
			IDealValuation[] actualValArray = actualMgr.updateDealValuation(actual);

			ArrayList stageDealList = new ArrayList();

			for (int i = 0; i < staging.length; i++) {
				DefaultLogger.debug(this, " Staging : " + staging[i]);

				OBGMRADealVal gmraVal = (OBGMRADealVal) staging[i];
				for (int j = 0; j < stageValArray.length; j++) {
					IDealValuation dealVal = stageValArray[j];

					if (new Long(gmraVal.getCMSDealID()).equals(new Long(dealVal.getCMSDealID()))) {
						gmraVal.updateValuation(dealVal);
						stageDealList.add(gmraVal);
						break;
					}
				}
			}

			ArrayList actualDealList = new ArrayList();

			for (int i = 0; i < actual.length; i++) {
				DefaultLogger.debug(this, " Actual : " + actual[i]);

				OBGMRADealVal gmraVal = (OBGMRADealVal) actual[i];
				for (int k = 0; k < actualValArray.length; k++) {
					IDealValuation dealVal = actualValArray[k];

					if (new Long(gmraVal.getCMSDealID()).equals(new Long(dealVal.getCMSDealID()))) {

						OBGMRADealVal actualVal = new OBGMRADealVal(gmraVal);
						actualVal.updateValuation(dealVal);
						actualDealList.add(actualVal);
						break;
					}
				}
			}

			value.setGMRADealValuation(TradingBookHelper.toGMRAValArray(actualDealList)); // set
																							// into
																							// actual

			value.setStagingGMRADealValuation(TradingBookHelper.toGMRAValArray(stageDealList)); // set
																								// into
																								// actual

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
	 * Updates a list of actual GMRA deal valuations using a list of staging
	 * GMRA deal valuations.
	 * 
	 * @param value of type IGMRADealValTrxValue
	 * @return updated GMRA deal valuation transaction value
	 * @throws TrxOperationException on errors updating the actual GMRA deal
	 *         valuations
	 */
	protected IGMRADealValTrxValue updateActualGMRADealValuation(IGMRADealValTrxValue value)
			throws TrxOperationException {
		try {
			IGMRADealVal[] actual = value.getGMRADealValuation();
			IGMRADealVal[] staging = value.getStagingGMRADealValuation();
			ArrayList newDealList = new ArrayList();

			if (actual != null) {
				DefaultLogger.debug(this, " Actual length: " + actual.length);
			}
			DefaultLogger.debug(this, " Staging length: " + staging.length);

			for (int i = 0; i < actual.length; i++) {
				staging[i].setVersionTime(actual[i].getVersionTime()); // maintain
																		// actual
																		// version
																		// time
				staging[i].setStatus(value.getToState());
			}

			ITradingBookBusManager mgr = TradingBookBusManagerFactory.getActualTradingBookBusManager();
			actual = mgr.updateGMRADealValuationByCMSDealID(staging);

			value.setGMRADealValuation(actual); // set into actual

			return value;
		}
		catch (TradingBookException e) {
			throw new TrxOperationException("TradingBookException caught!", e);
		}
	}

	/**
	 * Method to create a transaction record
	 * 
	 * @param value is of type IGMRADealValTrxValue
	 * @return GMRA deal valuation transaction value
	 * @throws TrxOperationException on errors
	 */
	protected IGMRADealValTrxValue createTransaction(IGMRADealValTrxValue value) throws TrxOperationException {
		try {
			value = prepareTrxValue(value);
			ICMSTrxValue tempValue = super.createTransaction(value);
			OBGMRADealValTrxValue newValue = new OBGMRADealValTrxValue(tempValue);
			newValue.setGMRADealValuation(value.getGMRADealValuation());
			newValue.setStagingGMRADealValuation(value.getStagingGMRADealValuation());
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
	 * @param value is of type IGMRADealValTrxValue
	 * @return GMRA deal valuation transaction value
	 * @throws TrxOperationException on errors updating the transaction
	 */
	protected IGMRADealValTrxValue updateTransaction(IGMRADealValTrxValue value) throws TrxOperationException {
		try {
			value = prepareTrxValue(value);

			ICMSTrxValue tempValue = super.updateTransaction(value);
			OBGMRADealValTrxValue newValue = new OBGMRADealValTrxValue(tempValue);
			newValue.setGMRADealValuation(value.getGMRADealValuation());
			newValue.setStagingGMRADealValuation(value.getStagingGMRADealValuation());
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
	 * @param value is of type IGMRADealValTrxValue
	 * @return transaction result
	 */
	protected ITrxResult prepareResult(IGMRADealValTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

	/**
	 * Helper method to prepare transaction object.
	 * 
	 * @param value of type IGMRADealValTrxValue
	 * @return GMRA deal valuation transaction value
	 */
	private IGMRADealValTrxValue prepareTrxValue(IGMRADealValTrxValue value) {
		if (value != null) {
			IGMRADealVal[] actual = value.getGMRADealValuation();
			IGMRADealVal[] staging = value.getStagingGMRADealValuation();

			if ((actual != null)
					&& (actual.length != 0)
					&& (actual[0].getGroupID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)) {
				DefaultLogger.debug(this, "PrepareTrxValue for actual=" + actual[0].getGroupID());
				value.setReferenceID(String.valueOf(actual[0].getGroupID()));
			}
			else {
				value.setReferenceID(null);
			}

			if ((staging != null)
					&& (staging.length != 0)
					&& (staging[0].getGroupID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)) {
				DefaultLogger.debug(this, "PrepareTrxValue for staging=" + staging[0].getGroupID());
				value.setStagingReferenceID(String.valueOf(staging[0].getGroupID()));
			}
			else {
				value.setStagingReferenceID(null);
			}

		}
		return value;
	}
}
