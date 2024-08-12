/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/trx/AbstractCoBorrowerLimitTrxOperation.java,v 1.10 2005/11/25 06:48:26 whuang Exp $
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
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.OBCoBorrowerLimit;
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
 * @version $Revision: 1.10 $
 * @since $Date: 2005/11/25 06:48:26 $ Tag: $Name: $
 */
public abstract class AbstractCoBorrowerLimitTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {
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
		ICoBorrowerLimitTrxValue mainTrx = (ICoBorrowerLimitTrxValue) value;
		ICoBorrowerLimitTrxValue[] trxVals = mainTrx.getCoBorrowerLimitTrxValues();
		if ((trxVals == null) || ((count = trxVals.length) == 0)) {
			return super.preProcess(value);
		}
		for (int i = 0; i < count; i++) {
			trxVals[i].setToState(value.getToState());
			trxVals[i].getTrxContext().setUser(mainTrx.getTrxContext().getUser());
			trxVals[i].getTrxContext().setTeam(mainTrx.getTrxContext().getTeam());
			// trxVals[i].setTrxContext (mainTrx.getTrxContext());
			if (trxVals[i].getLimit().getOuterLimitBookingLoc() != null) {
				// trxVals[i].getTrxContext().setTrxCountryOrigin
				// (trxVals[i].getLimit
				// ().getOuterLimitBookingLoc().getCountryCode());
				// trxVals[i].getTrxContext().setTrxOrganisationOrigin
				// (trxVals[i
				// ].getLimit().getOuterLimitBookingLoc().getOrganisationCode
				// ());
			}
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
			ICoBorrowerLimitTrxValue mainTrx = (ICoBorrowerLimitTrxValue) result.getTrxValue();
			ICoBorrowerLimitTrxValue[] trxVals = mainTrx.getCoBorrowerLimitTrxValues();
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
		catch (Exception e) {
			throw new TrxLogException("Caught TrxOperationException", e);
		}
	}

	/**
	 * Create Actual Limit Record
	 * 
	 * @param value is of type ICoBorrowerLimitTrxValue
	 * @return ICoBorrowerLimitTrxValue
	 * @throws TransactionException on errors
	 */
	protected ICoBorrowerLimitTrxValue createActualLimit(ICoBorrowerLimitTrxValue value) throws TrxOperationException {
		try {
			ICoBorrowerLimit limit = value.getStagingLimit(); // create get from
																// staging

			SBLimitManager mgr = getActualLimitManager();
			limit = mgr.createCoBorrowerLimit(limit);

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
	 * Create Staging Limit Record
	 * 
	 * @param value is of type ICoBorrowerLimitTrxValue
	 * @return ICoBorrowerLimitTrxValue
	 * @throws TransactionException on errors
	 */
	protected ICoBorrowerLimitTrxValue createStagingLimit(ICoBorrowerLimitTrxValue value) throws TrxOperationException {
		try {
			SBLimitManager mgr = getStagingLimitManager();

			if (value.getCoBorrowerLimitTrxValues() != null) {
				ICoBorrowerLimitTrxValue[] trxValues = value.getCoBorrowerLimitTrxValues();

				ICoBorrowerLimit[] coBorrowerLimits = new ICoBorrowerLimit[trxValues.length];
				for (int i = 0; i < trxValues.length; i++) {
					coBorrowerLimits[i] = trxValues[i].getStagingLimit();
				}

				ICoBorrowerLimit[] updatedLimits = mgr.createCoBorrowerLimits(coBorrowerLimits);
				for (int x = 0; x < trxValues.length; x++) {
					trxValues[x].setStagingLimit(updatedLimits[x]);
				}
			}
			else {
				ICoBorrowerLimit coBorrowerLimit = value.getStagingLimit();
				coBorrowerLimit = mgr.createCoBorrowerLimit(coBorrowerLimit);

				value.setStagingLimit(coBorrowerLimit);
			}

			return value;
		}
		catch (LimitException e) {
			DefaultLogger.debug(this, e);
			throw new TrxOperationException("Caught LimitException!", e);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, e);
			throw new TrxOperationException("Caught Exception!", e);
		}
	}

	/**
	 * Update Actual Limit Record from Staging
	 * 
	 * @param value is of type ICoBorrowerLimitTrxValue
	 * @return ICoBorrowerLimitTrxValue
	 * @throws TransactionException on errors
	 */
	protected ICoBorrowerLimitTrxValue updateActualLimitFromStaging(ICoBorrowerLimitTrxValue value)
			throws TrxOperationException {
		try {
			ICoBorrowerLimitTrxValue[] trxValues = value.getCoBorrowerLimitTrxValues();
			if (trxValues != null) {
				for (int i = 0; i < trxValues.length; i++) {
					ICoBorrowerLimit actual = trxValues[i].getLimit();
					ICoBorrowerLimit staging = trxValues[i].getStagingLimit();
					ICoBorrowerLimit newLimit = new OBCoBorrowerLimit(staging);
					newLimit.setLimitID(actual.getLimitID());
					newLimit.setVersionTime(actual.getVersionTime());
					SBLimitManager mgr = getActualLimitManager();
					newLimit = mgr.updateCoBorrowerLimit(newLimit);
					trxValues[i].setLimit(newLimit);
				}
			}
			else {
				ICoBorrowerLimit actual = value.getLimit();
				ICoBorrowerLimit staging = value.getStagingLimit();

				ICoBorrowerLimit newLimit = new OBCoBorrowerLimit(staging);
				newLimit.setLimitID(actual.getLimitID());
				newLimit.setVersionTime(actual.getVersionTime());

				SBLimitManager mgr = getActualLimitManager();
				newLimit = mgr.updateCoBorrowerLimit(newLimit);
				value.setLimit(newLimit);
			}

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
	 * @param value is of type ICoBorrowerLimitTrxValue
	 * @return ICoBorrowerLimitTrxValue
	 * @throws TransactionException on errors
	 */
	protected ICoBorrowerLimitTrxValue updateActualLimit(ICoBorrowerLimitTrxValue value) throws TrxOperationException {
		try {
			ICoBorrowerLimit limit = value.getLimit();

			SBLimitManager mgr = getActualLimitManager();
			limit = mgr.updateCoBorrowerLimit(limit);

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
	 * @param value is of type ICoBorrowerLimitTrxValue
	 * @return ICoBorrowerLimitTrxValue
	 * @throws TransactionException on errors
	 */
	protected ICoBorrowerLimitTrxValue updateStagingLimit(ICoBorrowerLimitTrxValue value) throws TrxOperationException {
		try {
			ICoBorrowerLimit limit = value.getStagingLimit();

			SBLimitManager mgr = getStagingLimitManager();
			limit = mgr.updateCoBorrowerLimit(limit);

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
	 * @param value is of type ICoBorrowerLimitTrxValue
	 * @return ICoBorrowerLimitTrxValue
	 * @throws TrxOperationException on errors
	 */
	protected ICoBorrowerLimitTrxValue createTransaction(ICoBorrowerLimitTrxValue value) throws TrxOperationException {
		try {
			value = prepareTrxValue(value);

			ICMSTrxValue tempValue = super.createTransaction((ICMSTrxValue) value);

			OBCoBorrowerLimitTrxValue newValue = new OBCoBorrowerLimitTrxValue(tempValue);
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
	 * @param value is of type ICoBorrowerLimitTrxValue
	 * @return ICoBorrowerLimitTrxValue
	 * @throws TrxOperationException on errors
	 */
	protected ICoBorrowerLimitTrxValue updateTransaction(ICoBorrowerLimitTrxValue value) throws TrxOperationException {
		try {
			ICoBorrowerLimitTrxValue[] trxValues = value.getCoBorrowerLimitTrxValues();
			if (trxValues != null) {
				for (int i = 0; i < trxValues.length; i++) {
					trxValues[i] = prepareTrxValue(trxValues[i]);
					ICMSTrxValue tempValue = super.updateTransaction(trxValues[i]);
					OBCoBorrowerLimitTrxValue newValue = new OBCoBorrowerLimitTrxValue(tempValue);
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
				value.setCoBorrowerLimitTrxValues(trxValues);
				return value;
			}
			else {
				value = prepareTrxValue(value);
				ICMSTrxValue tempValue = super.updateTransaction(value);
				OBCoBorrowerLimitTrxValue newValue = new OBCoBorrowerLimitTrxValue(tempValue);
				newValue.setLimit(value.getLimit());
				newValue.setStagingLimit(value.getStagingLimit());
				return newValue;
			}

		}
		catch (TrxOperationException e) {
			DefaultLogger.debug(this, e);
			throw e;
		}
		catch (Exception e) {
			DefaultLogger.debug(this, e);
			throw new TrxOperationException("Caught Exception!", e);
		}
	}

	/**
	 * Get the SB for the actual storage of Limit
	 * 
	 * @return SBLimitManager
	 * @throws TransactionException on errors
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
	 * @throws TransactionException on errors
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
	 * Convert a ITrxValue into a ICoBorrowerLimitTrxValue object
	 * 
	 * @param value is of type ITrxValue
	 * @return ICoBorrowerLimitTrxValue
	 * @throws TransactionException on errors
	 */
	protected ICoBorrowerLimitTrxValue getCoBorrowerLimitTrxValue(ITrxValue value) throws TrxOperationException {
		try {
			ICoBorrowerLimitTrxValue trxValue = (ICoBorrowerLimitTrxValue) value;
			return trxValue;
		}
		catch (ClassCastException e) {
			throw new TrxOperationException("Caught ClassCastException!", e);
		}
	}

	/**
	 * Prepares a result object to be returned
	 * 
	 * @param value is of type ICoBorrowerLimitTrxValue
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(ICoBorrowerLimitTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

	// **************** Private Methods
	/**
	 * Prepares a trx object
	 */
	private ICoBorrowerLimitTrxValue prepareTrxValue(ICoBorrowerLimitTrxValue value) {
		if (null != value) {
			ICoBorrowerLimit actual = value.getLimit();
			ICoBorrowerLimit staging = value.getStagingLimit();
			if (null != actual) {
				DefaultLogger.debug(this, " The CoborrowLimit ID is :" + actual.getLimitID());
				value.setReferenceID(String.valueOf(actual.getLimitID()));
			}
			else {
				value.setReferenceID(null);
			}
			if (null != staging) {
				DefaultLogger.debug(this, " The CoborrowLimit ID for Staging is :" + staging.getLimitID());
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