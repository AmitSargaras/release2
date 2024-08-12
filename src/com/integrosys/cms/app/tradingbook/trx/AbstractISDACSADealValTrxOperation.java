/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.trx;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.tradingbook.bus.IDealValuation;
import com.integrosys.cms.app.tradingbook.bus.IISDACSADealVal;
import com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager;
import com.integrosys.cms.app.tradingbook.bus.OBISDACSADealVal;
import com.integrosys.cms.app.tradingbook.bus.TradingBookBusManagerFactory;
import com.integrosys.cms.app.tradingbook.bus.TradingBookException;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Abstract class that contain methods that is common among the set of ISDA CSA
 * deal valuation trx operations.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public abstract class AbstractISDACSADealValTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {
	/**
	 * Helper method to cast a generic trx value object to a ISDA CSA deal
	 * valuation specific transaction value object.
	 * 
	 * @param trxValue transaction value
	 * @return ISDA CSA deal valuation specific transaction value
	 * @throws TrxOperationException if the trxValue is not of type
	 *         IISDACSADealValTrxValue
	 */
	protected IISDACSADealValTrxValue getISDACSADealValuationTrxValue(ITrxValue trxValue) throws TrxOperationException {
		try {
			return (IISDACSADealValTrxValue) trxValue;
		}
		catch (ClassCastException e) {
			throw new TrxOperationException("ITrxValue is not of type IISDACSADealValTrxValue: " + e.toString());
		}
	}

	/**
	 * Create staging ISDA CSA deal valuation records.
	 * 
	 * @param value is of type IISDACSADealValTrxValue
	 * @return ISDA CSA deal valuation transaction value
	 * @throws TrxOperationException on errors
	 */
	protected IISDACSADealValTrxValue createStagingISDACSADealValuation(IISDACSADealValTrxValue value)
			throws TrxOperationException {
		try {
			IISDACSADealVal[] staging = value.getStagingISDACSADealValuation();
			ArrayList newDealList = new ArrayList();

			HashMap tempKeyMap = new HashMap();
			for (int i = 0; i < staging.length; i++) {
				DefaultLogger.debug(this, "staging=" + staging[i]);
				staging[i].setStatus(value.getToState());
				if (tempKeyMap.get(new Long(staging[i].getCMSDealID())) != null) {
					throw new TrxOperationException("Duplicate CMS deal ID for stage record, CMS deal ID="
							+ staging[i].getCMSDealID());
				}
				tempKeyMap.put(new Long(staging[i].getCMSDealID()), new Long(staging[i].getCMSDealID()));
			}
			DefaultLogger.debug(this, "size for staging=" + staging.length);

			ITradingBookBusManager mgr = TradingBookBusManagerFactory.getStagingTradingBookBusManager();
			IDealValuation[] dealValArray = mgr.createDealValuation(staging);

			for (int i = 0; i < staging.length; i++) {

				OBISDACSADealVal csaVal = (OBISDACSADealVal) staging[i];
				for (int j = 0; j < dealValArray.length; j++) {
					IDealValuation dealVal = dealValArray[j];

					if (new Long(csaVal.getCMSDealID()).equals(new Long(dealVal.getCMSDealID()))) {
						csaVal.updateValuation(dealVal);
						newDealList.add(csaVal);
					}

				}
			}
			value.setStagingISDACSADealValuation(TradingBookHelper.toCSAValArray(newDealList)); // set
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
	 * Updates a list of actual ISDA CSA deal valuations using a list of staging
	 * ISDA CSA deal valuations.
	 * 
	 * @param value of type IISDACSADealValTrxValue
	 * @return updated ISDA CSA deal valuation transaction value
	 * @throws TrxOperationException on errors updating the actual ISDA CSA deal
	 *         valuations
	 */
	protected IISDACSADealValTrxValue updateActualISDACSADealValuation(IISDACSADealValTrxValue value)
			throws TrxOperationException {
		try {
			IISDACSADealVal[] actual = value.getISDACSADealValuation();
			IISDACSADealVal[] staging = value.getStagingISDACSADealValuation(); // update
																				// from
																				// staging
			DefaultLogger.debug(this, "size for actual=" + actual.length);
			DefaultLogger.debug(this, "size for staging=" + staging.length);

			for (int i = 0; i < actual.length; i++) {
				DefaultLogger.debug(this, "actual=" + actual[i]);

				IISDACSADealVal actualVal = actual[i];
				for (int j = 0; j < staging.length; j++) {
					IISDACSADealVal stagingVal = staging[j];

					if (new Long(actualVal.getCMSDealID()).equals(new Long(stagingVal.getCMSDealID()))) {
						DefaultLogger.debug(this, "staging=" + stagingVal);
						stagingVal.setVersionTime(actualVal.getVersionTime()); // maintain
																				// actual
																				// version
																				// time
						stagingVal.setStatus(value.getToState());
						break;
					}
				}
			}

			ITradingBookBusManager mgr = TradingBookBusManagerFactory.getActualTradingBookBusManager();
			actual = mgr.updateISDACSADealValuationByCMSDealID(staging);

			value.setISDACSADealValuation(actual); // set into actual

			return value;
		}
		catch (TradingBookException e) {
			throw new TrxOperationException("TradingBookException caught!", e);
		}
	}

	/**
	 * Method to create a transaction record
	 * 
	 * @param value is of type IISDACSADealValTrxValue
	 * @return ISDA CSA deal valuation transaction value
	 * @throws TrxOperationException on errors
	 */
	protected IISDACSADealValTrxValue createTransaction(IISDACSADealValTrxValue value) throws TrxOperationException {
		try {
			value = prepareTrxValue(value);
			ICMSTrxValue tempValue = super.createTransaction(value);
			OBISDACSADealValTrxValue newValue = new OBISDACSADealValTrxValue(tempValue);
			newValue.setISDACSADealValuation(value.getISDACSADealValuation());
			newValue.setStagingISDACSADealValuation(value.getStagingISDACSADealValuation());
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
	 * @param value is of type IISDACSADealValTrxValue
	 * @return ISDA CSA deal valuation transaction value
	 * @throws TrxOperationException on errors updating the transaction
	 */
	protected IISDACSADealValTrxValue updateTransaction(IISDACSADealValTrxValue value) throws TrxOperationException {
		try {
			value = prepareTrxValue(value);

			ICMSTrxValue tempValue = super.updateTransaction(value);
			OBISDACSADealValTrxValue newValue = new OBISDACSADealValTrxValue(tempValue);
			newValue.setISDACSADealValuation(value.getISDACSADealValuation());
			newValue.setStagingISDACSADealValuation(value.getStagingISDACSADealValuation());
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
	 * @param value is of type IISDACSADealValTrxValue
	 * @return transaction result
	 */
	protected ITrxResult prepareResult(IISDACSADealValTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

	/**
	 * Helper method to prepare transaction object.
	 * 
	 * @param value of type IISDACSADealValTrxValue
	 * @return ISDA CSA deal valuation transaction value
	 */
	private IISDACSADealValTrxValue prepareTrxValue(IISDACSADealValTrxValue value) {
		if (value != null) {
			IISDACSADealVal[] actual = value.getISDACSADealValuation();
			IISDACSADealVal[] staging = value.getStagingISDACSADealValuation();

			if ((actual != null)
					&& (actual.length != 0)
					&& (value.getAgreementID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)) {
				DefaultLogger.debug(this, "PrepareTrxValue for actual=" + value.getAgreementID());
				value.setReferenceID(String.valueOf(value.getAgreementID()));
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
