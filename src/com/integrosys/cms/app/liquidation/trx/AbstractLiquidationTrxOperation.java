/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.trx;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.liquidation.bus.ILiquidation;
import com.integrosys.cms.app.liquidation.bus.ILiquidationBusManager;
import com.integrosys.cms.app.liquidation.bus.IRecovery;
import com.integrosys.cms.app.liquidation.bus.IRecoveryExpense;
import com.integrosys.cms.app.liquidation.bus.IRecoveryIncome;
import com.integrosys.cms.app.liquidation.bus.LiquidationBusManagerFactory;
import com.integrosys.cms.app.liquidation.bus.LiquidationException;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Abstract class that contain methods that is common among the set of
 * liquidation trx operations.
 * 
 * @author $Author: lini$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public abstract class AbstractLiquidationTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {
	/**
	 * Helper method to cast a generic trx value object to a Liquidation
	 * specific transaction value object.
	 * 
	 * @param trxValue transaction value
	 * @return Liquidation liquidation specific transaction value
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         the trxValue is not of type ILiquidationTrxValue
	 */
	protected ILiquidationTrxValue getLiquidationTrxValue(ITrxValue trxValue, String liqType, Date monthYear)
			throws TrxOperationException {
		return (ILiquidationTrxValue) trxValue;
	}

	protected ILiquidationTrxValue getLiquidationTrxValue(ITrxValue trxValue) throws TrxOperationException {
		return (ILiquidationTrxValue) trxValue;
	}

	/**
	 * Create staging liquidation records.
	 * 
	 * @param value is of type ILiquidationTrxValue
	 * @return liquidation transaction value
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException on
	 *         errors
	 */
	protected ILiquidationTrxValue createStagingLiquidations(ILiquidationTrxValue value) throws TrxOperationException {
		try {
			ILiquidation liqs = value.getStagingLiquidation();

			ILiquidationBusManager mgr = LiquidationBusManagerFactory.getStagingLiquidationBusManager();
			liqs = mgr.createLiquidation(liqs);
			value.setStagingLiquidation(liqs);
			value.setStagingReferenceID(String.valueOf(liqs.getLiquidationID()));
			return value;
		}
		catch (LiquidationException e) {
			throw new TrxOperationException("failed to create staging liquidation", e);
		}
	}

	/**
	 * Create actual liquidation records.
	 * 
	 * @param value is of type ILiquidationTrxValue
	 * @return liquidation transaction value
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException on
	 *         errors creating the liquidation
	 */
	protected ILiquidationTrxValue createActualLiquidations(ILiquidationTrxValue value) throws TrxOperationException {
		try {
			ILiquidation liqs = value.getStagingLiquidation(); // create get
			// from staging
			/*
			 * 
			 * for (int i=0; i<liqs.length; i++) { // liqs[i].setStatus
			 * (value.getToState()); }
			 */

			ILiquidationBusManager mgr = LiquidationBusManagerFactory.getActualLiquidationBusManager();
			liqs = mgr.createLiquidation(liqs);
			value.setLiquidation(liqs); // set into actual
			value.setReferenceID(String.valueOf(liqs.getLiquidationID()));
			return value;
		}
		catch (LiquidationException e) {
			throw new TrxOperationException("failed to create actual liquidation", e);
		}
	}

	/**
	 * Updates a list of actual liquidation using a list of staging liquidation.
	 * 
	 * @param value of type ILiquidationTrxValue
	 * @return updated liquidation transaction value
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException on
	 *         errors updating the actual liquidation
	 */
	protected ILiquidationTrxValue updateActualLiquidations(ILiquidationTrxValue value) throws TrxOperationException {
		try {
			ILiquidation actual = value.getLiquidation();
			ILiquidation staging = value.getStagingLiquidation(); // update from
			// staging

			if (actual != null) {
				staging = mergeLiquidation(actual, staging);
			}

			ILiquidationBusManager mgr = LiquidationBusManagerFactory.getActualLiquidationBusManager();
			actual = mgr.updateLiquidation(staging);

			value.setLiquidation(actual);
			value.setReferenceID(actual.getLiquidationID().toString());

			return value;
		}
		catch (LiquidationException e) {
			throw new TrxOperationException("failed to update liqudation from staging", e);
		}
	}

	/**
	 * Method to create a transaction record
	 * 
	 * @param value is of type ILiquidationTrxValue
	 * @return liquidation transaction value
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException on
	 *         errors
	 */
	protected ILiquidationTrxValue createTransaction(ILiquidationTrxValue value) throws TrxOperationException {
		value = prepareTrxValue(value);

		ICMSTrxValue tempValue = super.createTransaction(value);

		OBLiquidationTrxValue newValue = new OBLiquidationTrxValue(tempValue);
		newValue.setLiquidation(value.getLiquidation());
		newValue.setStagingLiquidation(value.getStagingLiquidation());

		return newValue;
	}

	/**
	 * Method to update a transaction record.
	 * 
	 * @param value is of type ILiquidationTrxValue
	 * @return liquidation transaction value
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException on
	 *         errors updating the transaction
	 */
	protected ILiquidationTrxValue updateTransaction(ILiquidationTrxValue value) throws TrxOperationException {
		value = prepareTrxValue(value);

		ICMSTrxValue tempValue = super.updateTransaction(value);
		OBLiquidationTrxValue newValue = new OBLiquidationTrxValue(tempValue);

		newValue.setLiquidation(value.getLiquidation());
		newValue.setStagingLiquidation(value.getStagingLiquidation());

		return newValue;

	}

	/**
	 * Prepares a result object to be returned.
	 * 
	 * @param value is of type ILiquidationTrxValue
	 * @return transaction result
	 */
	protected ITrxResult prepareResult(ILiquidationTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

	/**
	 * Helper method to prepare transaction object.
	 * 
	 * @param value of type ILiquidationTrxValue
	 * @return Liquidation transaction value
	 */
	protected ILiquidationTrxValue prepareTrxValue(ILiquidationTrxValue value) {
		return value;
	}

	protected ILiquidation mergeLiquidation(ILiquidation actual, ILiquidation staging) throws TrxOperationException {

		// get actual liquidation id
		Long liquidationID = actual.getLiquidationID();
		DefaultLogger.debug(this, "actual liquidationID=" + liquidationID);

		ILiquidation liquidationStaging = null;
		try {
			liquidationStaging = (ILiquidation) CommonUtil.deepClone(staging);
		}
		catch (IOException e) {
			throw new TrxOperationException("failed to clone the staging copy to merge back to actual", e);
		}
		catch (ClassNotFoundException e) {
			throw new TrxOperationException("failed to clone the staging copy, which class is not found, possible ?", e);
		}

		if (actual == null) {
			liquidationStaging.setLiquidationID(new Long(ICMSConstant.LONG_INVALID_VALUE));
		}
		else {
			liquidationStaging.setLiquidationID(actual.getLiquidationID());
			liquidationStaging.setVersionTime(actual.getVersionTime());

			if (actual.getRecoveryExpense() == null) {
				if (liquidationStaging.getRecoveryExpense() != null) {
					Iterator stagingReIterator = liquidationStaging.getRecoveryExpense().iterator();
					IRecoveryExpense re;
					while (stagingReIterator.hasNext()) {
						re = (IRecoveryExpense) stagingReIterator.next();
						re.setRecoveryExpenseID(new Long(ICMSConstant.LONG_INVALID_VALUE));
					}
				}
			}
			else {
				if (liquidationStaging.getRecoveryExpense() != null) {
					Iterator stagingReIterator = liquidationStaging.getRecoveryExpense().iterator();
					IRecoveryExpense stagingRe;
					while (stagingReIterator.hasNext()) {
						stagingRe = (IRecoveryExpense) stagingReIterator.next();

						Iterator actualReIterator = actual.getRecoveryExpense().iterator();
						IRecoveryExpense actualRe;
						while (actualReIterator.hasNext()) {
							actualRe = (IRecoveryExpense) actualReIterator.next();

							if (actualRe.getRefID() == stagingRe.getRefID()) {
								stagingRe.setRecoveryExpenseID(actualRe.getRecoveryExpenseID());
								stagingRe.setVersionTime(actualRe.getVersionTime());
							}
						}
					}
				}
			}

			if (actual.getRecovery() == null) {
				if (liquidationStaging.getRecovery() != null) {
					Iterator stagingRcIterator = liquidationStaging.getRecovery().iterator();
					IRecovery re;
					while (stagingRcIterator.hasNext()) {
						re = (IRecovery) stagingRcIterator.next();
						re.setRecoveryID(new Long(ICMSConstant.LONG_INVALID_VALUE));
					}
				}
			}
			else {
				if (liquidationStaging.getRecovery() != null) {
					Iterator stagingRcIterator = liquidationStaging.getRecovery().iterator();
					IRecovery stagingRc;
					while (stagingRcIterator.hasNext()) {
						stagingRc = (IRecovery) stagingRcIterator.next();

						Iterator actualRcIterator = actual.getRecovery().iterator();
						IRecovery actualRc;
						while (actualRcIterator.hasNext()) {
							actualRc = (IRecovery) actualRcIterator.next();

							if (actualRc.getRefID() == stagingRc.getRefID()) {
								stagingRc.setRecoveryID(actualRc.getRecoveryID());
								stagingRc.setVersionTime(actualRc.getVersionTime());

								// merging Recovery Income
								if (stagingRc.getRecoveryIncome() != null) {
									Iterator stagingRiIterator = stagingRc.getRecoveryIncome().iterator();
									IRecoveryIncome stagingRi;

									while (stagingRiIterator.hasNext()) {
										stagingRi = (IRecoveryIncome) stagingRiIterator.next();

										Iterator actualRiIterator = actualRc.getRecoveryIncome().iterator();
										IRecoveryIncome actualRi;

										while (actualRiIterator.hasNext()) {
											actualRi = (IRecoveryIncome) actualRiIterator.next();

											if (actualRi.getRefID() == stagingRi.getRefID()) {
												stagingRi.setRecoveryIncomeID(actualRi.getRecoveryIncomeID());
												stagingRi.setVersionTime(actualRi.getVersionTime());
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		return liquidationStaging;
	}
}
