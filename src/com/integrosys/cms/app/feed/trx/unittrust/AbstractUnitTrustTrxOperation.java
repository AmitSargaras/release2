/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/unittrust/AbstractUnitTrustTrxOperation.java,v 1.1 2003/08/08 04:26:15 btchng Exp $
 */
package com.integrosys.cms.app.feed.trx.unittrust;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.feed.bus.unittrust.IUnitTrustFeedBusManager;
import com.integrosys.cms.app.feed.bus.unittrust.IUnitTrustFeedGroup;
import com.integrosys.cms.app.feed.bus.unittrust.UnitTrustFeedGroupException;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Abstract class that contain methods that is common among the set of document
 * item trx operations
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/08 04:26:15 $ Tag: $Name: $
 */
public abstract class AbstractUnitTrustTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

	/**
	 * Sets the next route requirements into the ITrxValue.
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxValue containing the required routing information for next
	 * user
	 * @throws TransactionException on error
	 */
	public IUnitTrustFeedBusManager unitTrustFeedBusManager;

	public IUnitTrustFeedBusManager unitTrustFeedBusManagerStaging;

	public IUnitTrustFeedBusManager getUnitTrustFeedBusManager() {
		return unitTrustFeedBusManager;
	}

	public void setUnitTrustFeedBusManager(IUnitTrustFeedBusManager unitTrustFeedBusManager) {
		this.unitTrustFeedBusManager = unitTrustFeedBusManager;
	}

	public IUnitTrustFeedBusManager getUnitTrustFeedBusManagerStaging() {
		return unitTrustFeedBusManagerStaging;
	}

	public void setUnitTrustFeedBusManagerStaging(IUnitTrustFeedBusManager unitTrustFeedBusManagerStaging) {
		this.unitTrustFeedBusManagerStaging = unitTrustFeedBusManagerStaging;
	}

	public ITrxValue getNextRoute(ITrxValue value) throws TransactionException {
		return value;
	}

	/**
	 * Create the staging document item doc
	 * @param anIUnitTrustFeedGroupTrxValue - IUnitTrustFeedGroupTrxValue
	 * @return IUnitTrustFeedGroupTrxValue - the trx object containing the
	 * created staging document item
	 * @throws TrxOperationException if errors
	 */
	protected IUnitTrustFeedGroupTrxValue createStagingUnitTrustFeedGroup(
			IUnitTrustFeedGroupTrxValue anIUnitTrustFeedGroupTrxValue) throws TrxOperationException {
		try {
			// IUnitTrustFeedBusManager manager =
			// UnitTrustFeedBusManagerFactory.getStagingUnitTrustFeedBusManager();
			IUnitTrustFeedGroup stockFeedGroup = getUnitTrustFeedBusManagerStaging().createUnitTrustFeedGroup(
					anIUnitTrustFeedGroupTrxValue.getStagingUnitTrustFeedGroup());

			anIUnitTrustFeedGroupTrxValue.setStagingUnitTrustFeedGroup(stockFeedGroup);
			anIUnitTrustFeedGroupTrxValue.setStagingReferenceID(String
					.valueOf(stockFeedGroup.getUnitTrustFeedGroupID()));

			return anIUnitTrustFeedGroupTrxValue;
		}
		catch (UnitTrustFeedGroupException e) {
			throw new TrxOperationException(e.toString());
		}
	}

	/**
	 * Update a stockFeedGroup transaction
	 * @param anIUnitTrustFeedGroupTrxValue - ITrxValue
	 * @return IUnitTrustFeedGroupTrxValue - the document item specific
	 * transaction object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	protected IUnitTrustFeedGroupTrxValue updateUnitTrustFeedGroupTransaction(
			IUnitTrustFeedGroupTrxValue anIUnitTrustFeedGroupTrxValue) throws TrxOperationException {
		try {
			anIUnitTrustFeedGroupTrxValue = prepareTrxValue(anIUnitTrustFeedGroupTrxValue);

			DefaultLogger.debug(this, "anIUnitTrustFeedGroupTrxValue's version time = "
					+ anIUnitTrustFeedGroupTrxValue.getVersionTime());

			ICMSTrxValue tempValue = super.updateTransaction(anIUnitTrustFeedGroupTrxValue);

			DefaultLogger.debug(this, "tempValue's version time = " + tempValue.getVersionTime());

			OBUnitTrustFeedGroupTrxValue newValue = new OBUnitTrustFeedGroupTrxValue(tempValue);
			newValue.setUnitTrustFeedGroup(anIUnitTrustFeedGroupTrxValue.getUnitTrustFeedGroup());
			newValue.setStagingUnitTrustFeedGroup(anIUnitTrustFeedGroupTrxValue.getStagingUnitTrustFeedGroup());

			DefaultLogger.debug(this, "newValue's version time = " + newValue.getVersionTime());

			return newValue;
		}
		catch (TransactionException tex) {
			throw new TrxOperationException(tex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}

	/**
	 * Helper method to cast a generic trx value object to a document item
	 * specific trx value object
	 * @param anITrxValue - ITrxValue
	 * @return IUnitTrustFeedGroupTrxValue - the document item specific trx
	 * value object
	 * @throws TrxOperationException if there is a ClassCastException
	 */
	protected IUnitTrustFeedGroupTrxValue getUnitTrustFeedGroupTrxValue(ITrxValue anITrxValue)
			throws TrxOperationException {
		try {
			return (IUnitTrustFeedGroupTrxValue) anITrxValue;
		}
		catch (ClassCastException cex) {
			throw new TrxOperationException("The ITrxValue is not of type OBUnitTrustFeedGroupTrxValue: "
					+ cex.toString());
		}
	}

	/**
	 * Prepares a trx object
	 */
	protected IUnitTrustFeedGroupTrxValue prepareTrxValue(IUnitTrustFeedGroupTrxValue anIUnitTrustFeedGroupTrxValue) {
		if (anIUnitTrustFeedGroupTrxValue != null) {
			IUnitTrustFeedGroup actual = anIUnitTrustFeedGroupTrxValue.getUnitTrustFeedGroup();
			IUnitTrustFeedGroup staging = anIUnitTrustFeedGroupTrxValue.getStagingUnitTrustFeedGroup();
			if (actual != null) {
				anIUnitTrustFeedGroupTrxValue.setReferenceID(String.valueOf(actual.getUnitTrustFeedGroupID()));
			}
			else {
				anIUnitTrustFeedGroupTrxValue.setReferenceID(null);
			}
			if (staging != null) {
				anIUnitTrustFeedGroupTrxValue.setStagingReferenceID(String.valueOf(staging.getUnitTrustFeedGroupID()));
			}
			else {
				anIUnitTrustFeedGroupTrxValue.setStagingReferenceID(null);
			}
			return anIUnitTrustFeedGroupTrxValue;
		}
		return null;
	}

	/**
	 * Prepares a result object to be returned
	 * 
	 * @param value is of type IUnitTrustFeedGroupTrxValue
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(IUnitTrustFeedGroupTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

	/**
	 * This method set the primary key from the original to the copied checklist
	 * objects. It is required for the case of updating staging from actual and
	 * vice versa as there is a need to perform a deep clone of the object and
	 * set the required attribute in the object to the original one so that a
	 * proper update can be done.
	 * @param anOriginal
	 * @param aCopy - ICheckList
	 * @return ICheckList - the copied object with required attributes from the
	 * original checklist
	 * @throws TrxOperationException on errors
	 */
	protected IUnitTrustFeedGroup mergeUnitTrustFeedGroup(IUnitTrustFeedGroup anOriginal, IUnitTrustFeedGroup aCopy)
			throws TrxOperationException {
		aCopy.setUnitTrustFeedGroupID(anOriginal.getUnitTrustFeedGroupID());
		aCopy.setVersionTime(anOriginal.getVersionTime());

		return aCopy;
	}

}