/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/trx/AbstractLimitTrxOperation.java,v 1.14 2005/11/25 06:48:26 whuang Exp $
 */
package com.integrosys.cms.app.limit.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxLogException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.OBLimit;
import com.integrosys.cms.app.limit.bus.SBLimitManager;
import com.integrosys.cms.app.limit.bus.SBLimitManagerHome;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Abstract class that contain methods that is common among the set of limit trx
 * operations
 * 
 * @author $Author: whuang $
 * @version $Revision: 1.14 $
 * @since $Date: 2005/11/25 06:48:26 $ Tag: $Name: $
 */
public abstract class AbstractLimitTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {
	/**
	 * Sets the next route requirements into the ITrxValue.
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxValue containing the required routing information for next
	 *         user
	 * @throws TransactionException on error
	 */
	public ITrxValue getNextRoute(ITrxValue value) throws TransactionException {
		return value;
	}

	/**
	 * This method defines the process that should initialised values that would
	 * be required in the <code>performProcess</code> method
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxValue object that has been initialised with required values
	 * @throws TrxOperationException on errors
	 */
	public ITrxValue preProcess(ITrxValue value) throws TrxOperationException {
		int count = 0;
		ILimitTrxValue mainTrx = getLimitTrxValue(value);
		ILimitTrxValue[] trxVals = mainTrx.getLimitTrxValues();
		if ((trxVals == null) || ((count = trxVals.length) == 0)) {
			return super.preProcess(value);
		}

		for (int i = 0; i < count; i++) {
			trxVals[i].setToState(value.getToState());
			trxVals[i].getTrxContext().setUser(mainTrx.getTrxContext().getUser());
			trxVals[i].getTrxContext().setTeam(mainTrx.getTrxContext().getTeam());
			// trxVals[i].setTrxContext (mainTrx.getTrxContext());
			// trxVals[i].getTrxContext().setTrxCountryOrigin
			// (trxVals[i].getLimit().getBookingLocation().getCountryCode());
			// trxVals[i].getTrxContext().setTrxOrganisationOrigin
			// (trxVals[i].getLimit
			// ().getBookingLocation().getOrganisationCode());
			super.preProcess(trxVals[i]);
		}
		return value;
	}

	/**
	 * Logs the transaction into the transaction log
	 * @param result is of type ITrxResult
	 * @return ITrxResult
	 * @throws TrxLogException on error
	 */
	public ITrxResult logProcess(ITrxResult result) throws TrxLogException {
		try {
			int count = 0;
			ILimitTrxValue mainTrx = getLimitTrxValue(result.getTrxValue());
			ILimitTrxValue[] trxVals = mainTrx.getLimitTrxValues();
			if ((trxVals == null) || ((count = trxVals.length) == 0)) {
				return super.logProcess(result);
			}

			for (int i = 0; i < count; i++) {
				ITrxResult trxResult = this.prepareResult(trxVals[i]);
				super.logProcess(trxResult);
			}
			return result;
		}
		catch (ClassCastException e) {
			e.printStackTrace();
			throw new TrxLogException("Caught ClassCastException!", e);
		}
		catch (TrxOperationException e) {
			throw new TrxLogException("Caught TrxOperationException", e);
		}
	}

	/**
	 * Create Actual Limit Record
	 * 
	 * @param value is of type ILimitTrxValue
	 * @return ILimitTrxValue
	 * @throws TrxOperationException on errors
	 */
	protected ILimitTrxValue createActualLimit(ILimitTrxValue value) throws TrxOperationException {
		try {
			ILimit limit = value.getStagingLimit(); // create get from staging

			SBLimitManager mgr = getActualLimitManager();
			limit = mgr.createLimit(limit);

			value.setLimit(limit); // set into actual
			return value;
		}
		catch (LimitException e) {
			throw new TrxOperationException("Caught LimitException!", e);
		}
		catch (Exception e) {
			throw new TrxOperationException("Caught Exception!", e);
		}
	}

	/**
	 * Helper method to retrieve limits out from transaction value.
	 * 
	 * @param trxVal of type ILimitTrxValue
	 * @param isActual true if to get actual limits, otherwise false
	 * @return a list of limits
	 */
	private ILimit[] retrieveLimits(ILimitTrxValue trxVal, boolean isActual) {
		ILimitTrxValue[] trxValues = trxVal.getLimitTrxValues();
		int count = trxValues.length;
		ILimit[] lmts = new OBLimit[count];
		if (isActual) {
			for (int i = 0; i < count; i++) {
				lmts[i] = trxValues[i].getLimit();
			}
		}
		else {
			for (int i = 0; i < count; i++) {
				DefaultLogger.debug(this, "before create, country: -------------------------- "
						+ trxValues[i].getTrxContext().getTrxCountryOrigin());
				lmts[i] = trxValues[i].getStagingLimit();
			}
		}
		return lmts;
	}

	/**
	 * Helper method to reset limits into actual or staging limit.
	 * 
	 * @param trxVal limit transaction value
	 * @param lmts of type ILimit[]
	 * @param isActual true if to reset actual limits, false to reset staging
	 *        limits
	 */
	private void resetLimits(ILimitTrxValue trxVal, ILimit[] lmts, boolean isActual) {
		ILimitTrxValue[] trxValues = trxVal.getLimitTrxValues();
		int count = trxValues.length;
		if (isActual) {
			for (int i = 0; i < count; i++) {
				trxValues[i].setLimit(lmts[i]);
			}
		}
		else {
			for (int i = 0; i < count; i++) {
				trxValues[i].setStagingLimit(lmts[i]); // this must be in the
														// same order.
				if (trxValues[i].getLimit() != null) {
					// reset the collateral alloc and DAP error ind.
					trxValues[i].getStagingLimit().setCollateralAllocations(
							trxValues[i].getLimit().getCollateralAllocations());
					trxValues[i].getStagingLimit().setIsDAPError(trxValues[i].getLimit().getIsDAPError());
					DefaultLogger.debug(this, "countryin reset limits: ------------------------------- "
							+ trxValues[i].getTrxContext().getTrxCountryOrigin());
				}
			}
		}
	}

	/**
	 * Create Staging Limit Record
	 * 
	 * @param value is of type ILimitTrxValue
	 * @return ILimitTrxValue
	 * @throws TrxOperationException on errors
	 */
	protected ILimitTrxValue createStagingLimit(ILimitTrxValue value) throws TrxOperationException {
		try {
			if (value.getLimitTrxValues() != null) {
				ILimit[] lmts = retrieveLimits(value, false);
				SBLimitManager mgr = getStagingLimitManager();
				ILimit[] updatedLmts = mgr.createLimits(lmts);
				resetLimits(value, updatedLmts, false);
				return value;
			}
			else {
				ILimit limit = value.getStagingLimit(); // create get from
														// staging

				ICMSCustomer customer = null;
				if (value.getTrxContext() != null) {
					customer = value.getTrxContext().getCustomer();
				}

				SBLimitManager mgr = getStagingLimitManager();
				limit = mgr.createLimitWithAccounts(customer, limit);
				ILimit actual = value.getLimit();
				if (null != actual) {
					limit.setCollateralAllocations(actual.getCollateralAllocations());
				}
				value.setStagingLimit(limit); // set into staging
				return value;
			}
		}
		catch (LimitException e) {
			throw new TrxOperationException("Caught LimitException!", e);
		}
		catch (Exception e) {
			throw new TrxOperationException("Caught Exception!", e);
		}
	}

	/**
	 * Update Actual Limit Record from Staging
	 * 
	 * @param value is of type ILimitTrxValue
	 * @return ILimitTrxValue
	 * @throws TrxOperationException on errors
	 */
	protected ILimitTrxValue updateActualLimitFromStaging(ILimitTrxValue value) throws TrxOperationException {
		try {
			/*
			 * ILimitTrxValue[] trxValues = value.getLimitTrxValues(); if
			 * (trxValues!=null) { for (int i=0; i<trxValues.length; i++) {
			 * ILimit actualLimit = trxValues[i].getLimit(); ILimit stagingLimit
			 * = trxValues[i].getStagingLimit(); ILimit newLimit = new
			 * OBLimit(stagingLimit);
			 * newLimit.setLimitID(actualLimit.getLimitID());
			 * newLimit.setVersionTime(actualLimit.getVersionTime());
			 * SBLimitManager mgr = getActualLimitManager(); newLimit =
			 * mgr.updateLimitWithAccounts(actualLimit, newLimit);
			 * trxValues[i].setLimit(newLimit); } } return value;
			 */

			ILimit actual = value.getLimit();
			ILimit limit = value.getStagingLimit(); // update from staging

			ILimit newLimit = new OBLimit(limit);
			newLimit.setLimitID(actual.getLimitID()); // but maintain actual's
														// pk
			newLimit.setVersionTime(actual.getVersionTime()); // and actual's
																// version time
			ICMSCustomer customer = null;
			if (value.getTrxContext() != null) {
				customer = value.getTrxContext().getCustomer();
			}
			SBLimitManager mgr = getActualLimitManager();
			newLimit = mgr.updateLimitWithAccounts(customer, actual, newLimit);

			value.setLimit(newLimit); // set into actual
			return value;

		}
		catch (LimitException e) {
			throw new TrxOperationException("Caught LimitException!", e);
		}
		catch (Exception e) {
			throw new TrxOperationException("Caught Exception!", e);
		}
	}

	/**
	 * Update Actual Limit Record
	 * 
	 * @param value is of type ILimitTrxValue
	 * @return ILimitTrxValue
	 * @throws TrxOperationException on errors
	 */
	protected ILimitTrxValue updateActualLimit(ILimitTrxValue value) throws TrxOperationException {
		try {
			ILimit limit = value.getLimit();

			SBLimitManager mgr = getActualLimitManager();

			if (value.isToUpdateOPLimit()) {
				limit = mgr.updateOperationalLimit(limit);
			}
			else {
				limit = mgr.updateLimit(limit);
			}

			value.setLimit(limit);
			return value;
		}
		catch (LimitException e) {
			throw new TrxOperationException("Caught LimitException!", e);
		}
		catch (Exception e) {
			throw new TrxOperationException("Caught Exception!", e);
		}
	}

	/**
	 * Update Staging Limit Record
	 * 
	 * @param value is of type ILimitTrxValue
	 * @return ILimitTrxValue
	 * @throws TrxOperationException on errors
	 */
	protected ILimitTrxValue updateStagingLimit(ILimitTrxValue value) throws TrxOperationException {
		try {
			ILimit limit = value.getStagingLimit();

			SBLimitManager mgr = getStagingLimitManager();

			if (value.isToUpdateOPLimit()) {
				limit = mgr.updateOperationalLimit(limit);
			}
			else {
				limit = mgr.updateLimit(limit);
			}

			ILimit actual = value.getLimit();
			if (null != actual) {
				limit.setCollateralAllocations(actual.getCollateralAllocations());
			}

			value.setStagingLimit(limit);
			return value;
		}
		catch (LimitException e) {
			throw new TrxOperationException("Caught LimitException!", e);
		}
		catch (Exception e) {
			throw new TrxOperationException("Caught Exception!", e);
		}
	}

	/**
	 * Method to create a transaction record
	 * 
	 * @param value is of type ILimitTrxValue
	 * @return ILimitTrxValue
	 * @throws TrxOperationException on errors
	 */
	protected ILimitTrxValue createTransaction(ILimitTrxValue value) throws TrxOperationException {
		try {
			value = prepareTrxValue(value);

			ICMSTrxValue tempValue = super.createTransaction((ICMSTrxValue) value);

			OBLimitTrxValue newValue = new OBLimitTrxValue(tempValue);
			newValue.setLimit(value.getLimit());
			newValue.setStagingLimit(value.getStagingLimit());
			return newValue;
		}
		catch (TrxOperationException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TrxOperationException("Caught Exception!", e);
		}
	}

	/**
	 * Method to update a transaction record
	 * 
	 * @param value is of type ILimitTrxValue
	 * @return ILimitTrxValue
	 * @throws TrxOperationException on errors
	 */
	protected ILimitTrxValue updateTransaction(ILimitTrxValue value) throws TrxOperationException {
		try {
			if (value.getLimitTrxValues() != null) {
				ILimitTrxValue[] trxValues = value.getLimitTrxValues();
				int count = trxValues.length;
				for (int i = 0; i < count; i++) {
					trxValues[i] = prepareTrxValue(trxValues[i]);
					ICMSTrxValue tempValue = super.updateTransaction(trxValues[i]);
					OBLimitTrxValue newValue = new OBLimitTrxValue(tempValue);
					newValue.setLimit(trxValues[i].getLimit());
					newValue.setStagingLimit(trxValues[i].getStagingLimit());
					trxValues[i] = newValue;
					if (newValue.getTransactionID().equals(newValue.getTrxReferenceID())) {
						// set the currenty trx history id coz the wrapper trx
						// value will be used
						// by transaction acknowledgement page.
						value.setCurrentTrxHistoryID(newValue.getCurrentTrxHistoryID());
					}
				}
				value.setLimitTrxValues(trxValues);
				return value;
			}
			else {
				value = prepareTrxValue(value);
				ICMSTrxValue tempValue = super.updateTransaction(value);
				OBLimitTrxValue newValue = new OBLimitTrxValue(tempValue);
				newValue.setLimit(value.getLimit());
				newValue.setStagingLimit(value.getStagingLimit());
				return newValue;
			}
		}
		catch (TrxOperationException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TrxOperationException("Caught Exception!", e);
		}
	}

	/**
	 * Get the SB for the actual storage of Limit
	 * 
	 * @return SBLimitManager
	 * @throws TrxOperationException on errors
	 */
	protected SBLimitManager getActualLimitManager() throws TrxOperationException {
		SBLimitManager home = (SBLimitManager) BeanController.getEJB(ICMSJNDIConstant.SB_LIMIT_MGR_JNDI,
				SBLimitManagerHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new TrxOperationException("SBLimitManager for Actual is null!");
		}
	}

	/**
	 * Get the SB for the actual storage of Limit
	 * 
	 * @return SBLimitManager
	 * @throws TrxOperationException on errors
	 */
	protected SBLimitManager getStagingLimitManager() throws TrxOperationException {
		SBLimitManager home = (SBLimitManager) BeanController.getEJB(ICMSJNDIConstant.SB_LIMIT_MGR_JNDI_STAGING,
				SBLimitManagerHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new TrxOperationException("SBLimitManager for Staging is null!");
		}
	}

	/**
	 * Convert a ITrxValue into a ILimitTrxValue object
	 * 
	 * @param value is of type ITrxValue
	 * @return ILimitTrxValue
	 * @throws TrxOperationException on errors
	 */
	protected ILimitTrxValue getLimitTrxValue(ITrxValue value) throws TrxOperationException {
		try {
			ILimitTrxValue trxValue = (ILimitTrxValue) value;
			return trxValue;
		}
		catch (ClassCastException e) {
			throw new TrxOperationException("Caught ClassCastException!", e);
		}
	}

	/**
	 * Prepares a result object to be returned
	 * 
	 * @param value is of type ILimitTrxValue
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(ILimitTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

	// **************** Private Methods
	/**
	 * Prepares a trx object
	 */
	private ILimitTrxValue prepareTrxValue(ILimitTrxValue value) {
		if (null != value) {
			ILimit actual = value.getLimit();
			ILimit staging = value.getStagingLimit();
			if (null != actual) {
				value.setReferenceID(String.valueOf(actual.getLimitID()));
			}
			else {
				value.setReferenceID(null);
			}
			if (null != staging) {
				value.setStagingReferenceID(String.valueOf(staging.getLimitID()));
			}
			else {
				value.setStagingReferenceID(null);
			}
			return value;
		}
		else {
			return null;
		}
	}
}