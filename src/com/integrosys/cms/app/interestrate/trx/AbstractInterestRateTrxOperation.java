/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.interestrate.trx;

import java.util.Date;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.interestrate.bus.IInterestRate;
import com.integrosys.cms.app.interestrate.bus.IInterestRateBusManager;
import com.integrosys.cms.app.interestrate.bus.InterestRateBusManagerFactory;
import com.integrosys.cms.app.interestrate.bus.InterestRateException;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Abstract class that contain methods that is common among the set of interest
 * rate trx operations.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public abstract class AbstractInterestRateTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {
	/**
	 * Helper method to cast a generic trx value object to a interestrate
	 * specific transaction value object.
	 * 
	 * @param trxValue transaction value
	 * @return interestrate interest rate specific transaction value
	 * @throws TrxOperationException if the trxValue is not of type
	 *         IInterestRateTrxValue
	 */
	protected IInterestRateTrxValue getInterestRateTrxValue(ITrxValue trxValue, String intRateType, Date monthYear)
			throws TrxOperationException {
		try {
			return (IInterestRateTrxValue) trxValue;
		}
		catch (ClassCastException e) {
			throw new TrxOperationException("ITrxValue is not of type IInterestRateTrxValue: " + e.toString());
		}
	}

	protected IInterestRateTrxValue getInterestRateTrxValue(ITrxValue trxValue) throws TrxOperationException {
		try {
			return (IInterestRateTrxValue) trxValue;
		}
		catch (ClassCastException e) {
			throw new TrxOperationException("ITrxValue is not of type IInterestRateTrxValue: " + e.toString());
		}
	}

	/**
	 * Create staging interest rate records.
	 * 
	 * @param value is of type IInterestRateTrxValue
	 * @return interest rate transaction value
	 * @throws TrxOperationException on errors
	 */
	protected IInterestRateTrxValue createStagingInterestRates(IInterestRateTrxValue value)
			throws TrxOperationException {
		try {
			IInterestRate[] intRates = value.getStagingInterestRates();

			for (int i = 0; i < intRates.length; i++) {
				// intRates[i].setStatus (value.getToState());
			}

			IInterestRateBusManager mgr = InterestRateBusManagerFactory.getStagingInterestRateBusManager();
			intRates = mgr.createInterestRates(intRates);
			value.setStagingInterestRates(intRates);
			return value;
		}
		catch (InterestRateException e) {
			throw new TrxOperationException("InterestRateException caught!", e);
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Create actual interest rate records.
	 * 
	 * @param value is of type IInterestRateTrxValue
	 * @return interest rate transaction value
	 * @throws TrxOperationException on errors creating the interest rate
	 */
	protected IInterestRateTrxValue createActualInterestRates(IInterestRateTrxValue value) throws TrxOperationException {
		try {
			IInterestRate[] intRates = value.getStagingInterestRates(); // create
																		// get
																		// from
																		// staging

			for (int i = 0; i < intRates.length; i++) {
				// intRates[i].setStatus (value.getToState());
			}

			IInterestRateBusManager mgr = InterestRateBusManagerFactory.getActualInterestRateBusManager();
			intRates = mgr.createInterestRates(intRates);
			value.setInterestRates(intRates); // set into actual
			return value;
		}
		catch (InterestRateException e) {
			throw new TrxOperationException("InterestRateException caught!", e);
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Updates a list of actual interest rate using a list of staging interest
	 * rate.
	 * 
	 * @param value of type IInterestRateTrxValue
	 * @return updated interest rate transaction value
	 * @throws TrxOperationException on errors updating the actual interest rate
	 */
	protected IInterestRateTrxValue updateActualInterestRates(IInterestRateTrxValue value) throws TrxOperationException {
		try {
			IInterestRate[] actual = value.getInterestRates();
			IInterestRate[] staging = value.getStagingInterestRates(); // update
																		// from
																		// staging

			if (actual != null) {
				DefaultLogger.debug(this, " Actual length: " + actual.length);
			}
			DefaultLogger.debug(this, " Staging length: " + staging.length);

			if (actual != null) {
				for (int i = 0; i < actual.length; i++) {
					staging[i].setVersionTime(actual[i].getVersionTime()); // maintain
																			// actual
																			// version
																			// time

					// staging[i].setStatus (value.getToState());
				}
			}
			IInterestRateBusManager mgr = InterestRateBusManagerFactory.getActualInterestRateBusManager();

			String intRateType = value.getIntRateType();
			Date monthYear = value.getMonthYear();

			DefaultLogger.debug(this, " Type Code: " + intRateType);
			DefaultLogger.debug(this, " monthYear: " + monthYear);

			actual = mgr.getInterestRate(intRateType, monthYear);

			if ((actual == null) || (actual.length == 0)) {
				actual = mgr.createInterestRates(staging);
			}
			else {
				for (int i = 0; i < actual.length; i++) {

					for (int j = 0; j < staging.length; j++) {

						if (actual[i].getIntRateDate().equals(staging[j].getIntRateDate())) {

							actual[i].setIntRatePercent(staging[j].getIntRatePercent());
							continue;

						}
					}
				}
				actual = mgr.updateInterestRates(actual);
			}
			value.setInterestRates(actual); // set into actual

			return value;
		}
		catch (InterestRateException e) {
			throw new TrxOperationException("InterestRateException caught!", e);
		}
	}

	/**
	 * Method to create a transaction record
	 * 
	 * @param value is of type IInterestRateTrxValue
	 * @return interest rate transaction value
	 * @throws TrxOperationException on errors
	 */
	protected IInterestRateTrxValue createTransaction(IInterestRateTrxValue value) throws TrxOperationException {
		try {
			value = prepareTrxValue(value);
			ICMSTrxValue tempValue = super.createTransaction(value);
			OBInterestRateTrxValue newValue = new OBInterestRateTrxValue(tempValue);
			newValue.setInterestRates(value.getInterestRates());
			newValue.setStagingInterestRates(value.getStagingInterestRates());
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
	 * @param value is of type IInterestRateTrxValue
	 * @return interest rate transaction value
	 * @throws TrxOperationException on errors updating the transaction
	 */
	protected IInterestRateTrxValue updateTransaction(IInterestRateTrxValue value) throws TrxOperationException {
		try {
			value = prepareTrxValue(value);

			ICMSTrxValue tempValue = super.updateTransaction(value);
			OBInterestRateTrxValue newValue = new OBInterestRateTrxValue(tempValue);
			newValue.setInterestRates(value.getInterestRates());
			newValue.setStagingInterestRates(value.getStagingInterestRates());
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
	 * @param value is of type IInterestRateTrxValue
	 * @return transaction result
	 */
	protected ITrxResult prepareResult(IInterestRateTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

	/**
	 * Helper method to prepare transaction object.
	 * 
	 * @param value of type IInterestRateTrxValue
	 * @return interestrate transaction value
	 */
	protected IInterestRateTrxValue prepareTrxValue(IInterestRateTrxValue value) {
		if (value != null) {
			IInterestRate[] actual = value.getInterestRates();
			IInterestRate[] staging = value.getStagingInterestRates();

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
