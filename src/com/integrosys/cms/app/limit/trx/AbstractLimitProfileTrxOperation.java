/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/trx/AbstractLimitProfileTrxOperation.java,v 1.6 2004/04/13 08:23:16 lyng Exp $
 */
package com.integrosys.cms.app.limit.trx;

//ofa
import java.util.Iterator;
import java.util.List;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.IThresholdRating;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
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
 * @author $Author: lyng $
 * @version $Revision: 1.6 $
 * @since $Date: 2004/04/13 08:23:16 $ Tag: $Name: $
 */
public abstract class AbstractLimitProfileTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

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
	 * Pre process. Prepares the transaction object for persistance
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxValue
	 * @throws TrxOperationException on error
	 */
	public ITrxValue preProcess(ITrxValue value) throws TrxOperationException {
		value = super.preProcess(value);

		ILimitProfileTrxValue trxValue = getLimitProfileTrxValue(value);

		ILimitProfile stage = trxValue.getStagingLimitProfile();
		if (null != stage) {
			// bca status is the business status, not the cms internal trx
			// status
			// stage.setBCAStatus(trxValue.getToState());
			trxValue.setStagingLimitProfile(stage);
		}
		return trxValue;
	}

	/**
	 * Create Actual LimitProfile Record
	 * 
	 * @param value is of type ILimitProfileTrxValue
	 * @return ILimitProfileTrxValue
	 * @throws TransactionException on errors
	 */
	protected ILimitProfileTrxValue createActualLimitProfile(ILimitProfileTrxValue value) throws TrxOperationException {
		try {
			ILimitProfile limit = value.getStagingLimitProfile(); // create get
			// from
			// staging

			limit.setBCAStatus(value.getToState());
			limit.setCMSCreateInd(true);
			if (limit.getTradingAgreement() != null) {
				limit.getTradingAgreement().setStatus(value.getToState());
				List thRateList = limit.getTradingAgreement().getThresholdRatingList();
				if (thRateList != null) {
					Iterator i = thRateList.iterator();
					while (i.hasNext()) {
						IThresholdRating rate = (IThresholdRating) i.next();
						rate.setStatus(value.getToState());
					}
				}
			}
			SBLimitManager mgr = getActualLimitManager();
			limit = mgr.createLimitProfile(limit);

			value.setLimitProfile(limit); // set into actual
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
	 * Create Staging LimitProfile Record
	 * 
	 * @param value is of type ILimitProfileTrxValue
	 * @return ILimitProfileTrxValue
	 * @throws TransactionException on errors
	 */
	protected ILimitProfileTrxValue createStagingLimitProfile(ILimitProfileTrxValue value) throws TrxOperationException {
		try {
			ILimitProfile limit = value.getStagingLimitProfile(); // create get
			// from
			// staging
			limit.setCustRef(ICMSConstant.DEFAULT_CUST_REF);

			limit.setBCAStatus(value.getToState());
			limit.setCMSCreateInd(true);
			if (limit.getTradingAgreement() != null) {
				limit.getTradingAgreement().setStatus(value.getToState());
				List thRateList = limit.getTradingAgreement().getThresholdRatingList();
				if (thRateList != null) {
					Iterator i = thRateList.iterator();
					while (i.hasNext()) {
						IThresholdRating rate = (IThresholdRating) i.next();
						rate.setStatus(value.getToState());
					}
				}
			}

			SBLimitManager mgr = getStagingLimitManager();
			limit = mgr.createLimitProfile(limit);

			ILimitProfile actual = value.getLimitProfile();
			if (null != actual) {
				limit.setLimits(actual.getLimits());
			}

			value.setStagingLimitProfile(limit); // set into staging
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
	 * Update Actual LimitProfile Record
	 * 
	 * @param value is of type ILimitProfileTrxValue
	 * @return ILimitProfileTrxValue
	 * @throws TransactionException on errors
	 */
	protected ILimitProfileTrxValue updateActualLimitProfileFromStaging(ILimitProfileTrxValue value)
			throws TrxOperationException {
		try {
			ILimitProfile actual = value.getLimitProfile();
			ILimitProfile limit = value.getStagingLimitProfile();

			ILimitProfile newLimitProfile = new OBLimitProfile();
			AccessorUtil.copyValue(limit, newLimitProfile);

			newLimitProfile.setLimitProfileID(actual.getLimitProfileID());
			newLimitProfile.setVersionTime(actual.getVersionTime());

			if ((newLimitProfile.getTradingAgreement() != null) && (actual.getTradingAgreement() != null)) {
				newLimitProfile.getTradingAgreement().setVersionTime(actual.getTradingAgreement().getVersionTime());
				newLimitProfile.getTradingAgreement().setStatus(value.getToState());
				List thRateList = newLimitProfile.getTradingAgreement().getThresholdRatingList();
				if (thRateList != null) {
					Iterator i = thRateList.iterator();
					while (i.hasNext()) {
						IThresholdRating rate = (IThresholdRating) i.next();
						rate.setStatus(value.getToState());
					}
				}
			}

			SBLimitManager mgr = getActualLimitManager();
			newLimitProfile = mgr.updateLimitProfile(newLimitProfile);

			value.setLimitProfile(newLimitProfile); // set into actual
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
	 * Update Actual LimitProfile Record
	 * 
	 * @param value is of type ILimitProfileTrxValue
	 * @return ILimitProfileTrxValue
	 * @throws TransactionException on errors
	 */
	protected ILimitProfileTrxValue updateActualLimitProfile(ILimitProfileTrxValue value) throws TrxOperationException {
		try {
			ILimitProfile limit = value.getLimitProfile();

			SBLimitManager mgr = getActualLimitManager();
			limit = mgr.updateLimitProfile(limit);

			value.setLimitProfile(limit);
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
	 * Update Staging LimitProfile Record
	 * 
	 * @param value is of type ILimitProfileTrxValue
	 * @return ILimitProfileTrxValue
	 * @throws TransactionException on errors
	 */
	protected ILimitProfileTrxValue updateStagingLimitProfile(ILimitProfileTrxValue value) throws TrxOperationException {
		try {
			ILimitProfile limit = value.getStagingLimitProfile();

			SBLimitManager mgr = getStagingLimitManager();
			limit = mgr.updateLimitProfile(limit);

			ILimitProfile actual = value.getLimitProfile();
			if (null != actual) {
				limit.setLimits(actual.getLimits());
			}

			value.setStagingLimitProfile(limit);
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
	 * Method to delete a limit profile record in staging.
	 * 
	 * @param value is of type ILimitProfileTrxValue
	 * @return limit profile transaction value
	 * @throws TrxOperationException on errors
	 */
	protected ILimitProfileTrxValue deleteStagingLimitProfile(ILimitProfileTrxValue value) throws TrxOperationException {
		try {
			ILimitProfile limitProfile = value.getStagingLimitProfile();
			SBLimitManager mgr = getStagingLimitManager();
			limitProfile = mgr.removeLimitProfile(limitProfile);
			value.setStagingLimitProfile(limitProfile);
			return value;
		}
		catch (LimitException e) {
			throw new TrxOperationException("LimitException caught!", e);
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Method to delete a limit Profile record in actual.
	 * 
	 * @param value is of type ILimitProfileTrxValue
	 * @return limit Profile transaction value
	 * @throws TrxOperationException on errors
	 */
	protected ILimitProfileTrxValue deleteActualLimitProfile(ILimitProfileTrxValue value) throws TrxOperationException {
		try {

			ILimitProfile limitProfile = value.getLimitProfile();
			SBLimitManager mgr = getActualLimitManager();
			limitProfile = mgr.removeLimitProfile(limitProfile);
			value.setLimitProfile(limitProfile);
			return value;
		}
		catch (LimitException e) {
			throw new TrxOperationException("LimitException caught!", e);
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Method to create a transaction record
	 * 
	 * @param value is of type ILimitProfileTrxValue
	 * @return ILimitProfileTrxValue
	 * @throws TrxOperationException on errors
	 */
	protected ILimitProfileTrxValue createTransaction(ILimitProfileTrxValue value) throws TrxOperationException {
		try {
			value = prepareTrxValue(value);
			value.setTransactionSubType("MANUAL");
			ICMSTrxValue tempValue = super.createTransaction((ICMSTrxValue) value);

			OBLimitProfileTrxValue newValue = new OBLimitProfileTrxValue(tempValue);
			newValue.setLimitProfile(value.getLimitProfile());
			newValue.setStagingLimitProfile(value.getStagingLimitProfile());
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
	 * @param value is of type ILimitProfileTrxValue
	 * @return ILimitProfileTrxValue
	 * @throws TrxOperationException on errors
	 */
	protected ILimitProfileTrxValue updateTransaction(ILimitProfileTrxValue value) throws TrxOperationException {
		try {
			value = prepareTrxValue(value);

			ICMSTrxValue tempValue = super.updateTransaction((ICMSTrxValue) value);

			OBLimitProfileTrxValue newValue = new OBLimitProfileTrxValue(tempValue);
			newValue.setLimitProfile(value.getLimitProfile());
			newValue.setStagingLimitProfile(value.getStagingLimitProfile());
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
	 * Get the SB for the actual storage of LimitProfile
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
	 * Get the SB for the actual storage of LimitProfile
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
	 * Convert a ITrxValue into a ILimitProfileTrxValue object
	 * 
	 * @param value is of type ITrxValue
	 * @return ILimitProfileTrxValue
	 * @throws TransactionException on errors
	 */
	protected ILimitProfileTrxValue getLimitProfileTrxValue(ITrxValue value) throws TrxOperationException {
		try {
			ILimitProfileTrxValue trxValue = (ILimitProfileTrxValue) value;
			return trxValue;
		}
		catch (ClassCastException e) {
			throw new TrxOperationException("Caught ClassCastException!", e);
		}
	}

	/**
	 * Prepares a result object to be returned
	 * 
	 * @param value is of type ILimitProfileTrxValue
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(ILimitProfileTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

	// **************** Private Methods
	/**
	 * Prepares a trx object
	 */
	private ILimitProfileTrxValue prepareTrxValue(ILimitProfileTrxValue value) {
		if (null != value) {
			ILimitProfile actual = value.getLimitProfile();
			ILimitProfile staging = value.getStagingLimitProfile();
			if (null != actual) {
				value.setReferenceID(String.valueOf(actual.getLimitProfileID()));
			}
			else {
				value.setReferenceID(null);
			}
			if (null != staging) {
				value.setStagingReferenceID(String.valueOf(staging.getLimitProfileID()));
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