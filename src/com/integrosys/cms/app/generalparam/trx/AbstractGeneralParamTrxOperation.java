/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/bond/AbstractBondTrxOperation.java,v 1.1 2003/08/06 08:10:08 btchng Exp $
 */
package com.integrosys.cms.app.generalparam.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.generalparam.bus.GeneralParamGroupException;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamBusManager;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Abstract class that contain methods that is common among the set of document
 * item trx operations
 * 
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public abstract class AbstractGeneralParamTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

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
	
	
	public IGeneralParamBusManager generalParamBusManager;
	
	public IGeneralParamBusManager generalParamBusManagerStaging;



	public IGeneralParamBusManager getGeneralParamBusManager() {
		return generalParamBusManager;
	}

	public void setGeneralParamBusManager(IGeneralParamBusManager generalParamBusManager) {
		this.generalParamBusManager = generalParamBusManager;
	}

	public IGeneralParamBusManager getGeneralParamBusManagerStaging() {
		return generalParamBusManagerStaging;
	}

	public void setGeneralParamBusManagerStaging(IGeneralParamBusManager generalParamBusManagerStaging) {
		this.generalParamBusManagerStaging = generalParamBusManagerStaging;
	}

	/**
	 * Create the staging document item doc
	 * @param anIGeneralParamGroupTrxValue - IGeneralParamGroupTrxValue
	 * @return IGeneralParamGroupTrxValue - the trx object containing the created
	 *         staging document item
	 * @throws TrxOperationException if errors
	 */
	protected IGeneralParamGroupTrxValue createStagingGeneralParamGroup(IGeneralParamGroupTrxValue anIGeneralParamGroupTrxValue)
			throws TrxOperationException {
		try {
			IGeneralParamGroup generalParamGroup = getGeneralParamBusManagerStaging().createGeneralParamGroup(
					anIGeneralParamGroupTrxValue.getStagingGeneralParamGroup());
			anIGeneralParamGroupTrxValue.setStagingGeneralParamGroup(generalParamGroup);
			anIGeneralParamGroupTrxValue.setStagingReferenceID(String.valueOf(generalParamGroup.getGeneralParamGroupID()));
			return anIGeneralParamGroupTrxValue;
		}
		catch (GeneralParamGroupException e) {
			throw new TrxOperationException(e.toString());
		}
	}

	/**
	 * Update a generalParamGroup transaction
	 * @param anIGeneralParamGroupTrxValue - ITrxValue
	 * @return IGeneralParamGroupTrxValue - the document item specific transaction
	 *         object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	protected IGeneralParamGroupTrxValue updateGeneralParamGroupTransaction(IGeneralParamGroupTrxValue anIGeneralParamGroupTrxValue)
			throws TrxOperationException {
		try {
			anIGeneralParamGroupTrxValue = prepareTrxValue(anIGeneralParamGroupTrxValue);

			DefaultLogger.debug(this, "anIGeneralParamGroupTrxValue's version time = "
					+ anIGeneralParamGroupTrxValue.getVersionTime());

			ICMSTrxValue tempValue = super.updateTransaction(anIGeneralParamGroupTrxValue);

			DefaultLogger.debug(this, "tempValue's version time = " + tempValue.getVersionTime());

			OBGeneralParamGroupTrxValue newValue = new OBGeneralParamGroupTrxValue(tempValue);
			newValue.setGeneralParamGroup(anIGeneralParamGroupTrxValue.getGeneralParamGroup());
			newValue.setStagingGeneralParamGroup(anIGeneralParamGroupTrxValue.getStagingGeneralParamGroup());

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
	 * @return IGeneralParamGroupTrxValue - the document item specific trx value
	 *         object
	 * @throws TrxOperationException if there is a ClassCastException
	 */
	protected IGeneralParamGroupTrxValue getGeneralParamGroupTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			return (IGeneralParamGroupTrxValue) anITrxValue;
		}
		catch (ClassCastException cex) {
			throw new TrxOperationException("The ITrxValue is not of type OBGeneralParamGroupTrxValue: " + cex.toString());
		}
	}

	protected IGeneralParamBusManager getStagingGeneralParamBusManager() {
		return getGeneralParamBusManagerStaging();
	}


	/**
	 * Prepares a trx object
	 */
	protected IGeneralParamGroupTrxValue prepareTrxValue(IGeneralParamGroupTrxValue anIGeneralParamGroupTrxValue) {
		if (anIGeneralParamGroupTrxValue != null) {
			IGeneralParamGroup actual = anIGeneralParamGroupTrxValue.getGeneralParamGroup();
			IGeneralParamGroup staging = anIGeneralParamGroupTrxValue.getStagingGeneralParamGroup();
			if (actual != null) {
				anIGeneralParamGroupTrxValue.setReferenceID(String.valueOf(actual.getGeneralParamGroupID()));
			}
			else {
				anIGeneralParamGroupTrxValue.setReferenceID(null);
			}
			if (staging != null) {
				anIGeneralParamGroupTrxValue.setStagingReferenceID(String.valueOf(staging.getGeneralParamGroupID()));
			}
			else {
				anIGeneralParamGroupTrxValue.setStagingReferenceID(null);
			}
			return anIGeneralParamGroupTrxValue;
		}
		return null;
	}

	/**
	 * Prepares a result object to be returned
	 * 
	 * @param value is of type IGeneralParamGroupTrxValue
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(IGeneralParamGroupTrxValue value) {
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
	 *         original checklist
	 * @throws TrxOperationException on errors
	 */
	protected IGeneralParamGroup mergeGeneralParamGroup(IGeneralParamGroup anOriginal, IGeneralParamGroup aCopy)
			throws TrxOperationException {
		aCopy.setGeneralParamGroupID(anOriginal.getGeneralParamGroupID());
		aCopy.setVersionTime(anOriginal.getVersionTime());

		return aCopy;
	}

}