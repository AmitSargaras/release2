/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/bond/AbstractBondTrxOperation.java,v 1.1 2003/08/06 08:10:08 btchng Exp $
 */
package com.integrosys.cms.app.digitalLibrary.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.digitalLibrary.bus.DigitalLibraryException;
import com.integrosys.cms.app.digitalLibrary.bus.IDigitalLibraryBusManager;
import com.integrosys.cms.app.digitalLibrary.bus.IDigitalLibraryGroup;
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
 * @since $Date: 2003/08/06 08:10:08 $ Tag: $Name: $
 */
public abstract class AbstractDigitalLibraryTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

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
	
	
	public IDigitalLibraryBusManager digitalLibraryBusManager;
	
	public IDigitalLibraryBusManager digitalLibraryBusManagerStaging;



	public IDigitalLibraryBusManager getDigitalLibraryBusManager() {
		return digitalLibraryBusManager;
	}

	public void setDigitalLibraryBusManager(IDigitalLibraryBusManager digitalLibraryBusManager) {
		this.digitalLibraryBusManager = digitalLibraryBusManager;
	}

	public IDigitalLibraryBusManager getDigitalLibraryBusManagerStaging() {
		return digitalLibraryBusManagerStaging;
	}

	public void setDigitalLibraryBusManagerStaging(IDigitalLibraryBusManager digitalLibraryBusManagerStaging) {
		this.digitalLibraryBusManagerStaging = digitalLibraryBusManagerStaging;
	}

	/**
	 * Create the staging document item doc
	 * @param anIDigitalLibraryGroupTrxValue - IDigitalLibraryGroupTrxValue
	 * @return IDigitalLibraryGroupTrxValue - the trx object containing the created
	 *         staging document item
	 * @throws TrxOperationException if errors
	 */
	protected IDigitalLibraryTrxValue createStagingDigitalLibraryGroup(IDigitalLibraryTrxValue anIDigitalLibraryGroupTrxValue)
			throws TrxOperationException {
		try {
			IDigitalLibraryGroup digitalLibraryGroup = getDigitalLibraryBusManagerStaging().createDigitalLibraryGroup(
					anIDigitalLibraryGroupTrxValue.getStagingDigitalLibraryGroup());
			anIDigitalLibraryGroupTrxValue.setStagingDigitalLibraryGroup(digitalLibraryGroup);
			anIDigitalLibraryGroupTrxValue.setStagingReferenceID(String.valueOf(digitalLibraryGroup.getDigitalLibraryGroupID()));
			return anIDigitalLibraryGroupTrxValue;
		}
		catch (DigitalLibraryException e) {
			throw new TrxOperationException(e.toString());
		}
	}

	/**
	 * Update a digitalLibraryGroup transaction
	 * @param anIDigitalLibraryGroupTrxValue - ITrxValue
	 * @return IDigitalLibraryGroupTrxValue - the document item specific transaction
	 *         object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	protected IDigitalLibraryTrxValue updateDigitalLibraryGroupTransaction(IDigitalLibraryTrxValue anIDigitalLibraryGroupTrxValue)
			throws TrxOperationException {
		try {
			anIDigitalLibraryGroupTrxValue = prepareTrxValue(anIDigitalLibraryGroupTrxValue);

			DefaultLogger.debug(this, "anIDigitalLibraryGroupTrxValue's version time = "
					+ anIDigitalLibraryGroupTrxValue.getVersionTime());

			ICMSTrxValue tempValue = super.updateTransaction(anIDigitalLibraryGroupTrxValue);

			DefaultLogger.debug(this, "tempValue's version time = " + tempValue.getVersionTime());

			OBDigitalLibraryTrxValue newValue = new OBDigitalLibraryTrxValue(tempValue);
			newValue.setDigitalLibraryGroup(anIDigitalLibraryGroupTrxValue.getDigitalLibraryGroup());
			newValue.setStagingDigitalLibraryGroup(anIDigitalLibraryGroupTrxValue.getStagingDigitalLibraryGroup());

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
	 * @return IDigitalLibraryGroupTrxValue - the document item specific trx value
	 *         object
	 * @throws TrxOperationException if there is a ClassCastException
	 */
	protected IDigitalLibraryTrxValue getDigitalLibraryGroupTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			return (IDigitalLibraryTrxValue) anITrxValue;
		}
		catch (ClassCastException cex) {
			throw new TrxOperationException("The ITrxValue is not of type OBDigitalLibraryGroupTrxValue: " + cex.toString());
		}
	}

	protected IDigitalLibraryBusManager getStagingDigitalLibraryBusManager() {
		return getDigitalLibraryBusManagerStaging();
	}


	/**
	 * Prepares a trx object
	 */
	protected IDigitalLibraryTrxValue prepareTrxValue(IDigitalLibraryTrxValue anIDigitalLibraryGroupTrxValue) {
		if (anIDigitalLibraryGroupTrxValue != null) {
			IDigitalLibraryGroup actual = anIDigitalLibraryGroupTrxValue.getDigitalLibraryGroup();
			IDigitalLibraryGroup staging = anIDigitalLibraryGroupTrxValue.getStagingDigitalLibraryGroup();
			if (actual != null) {
				anIDigitalLibraryGroupTrxValue.setReferenceID(String.valueOf(actual.getDigitalLibraryGroupID()));
			}
			else {
				anIDigitalLibraryGroupTrxValue.setReferenceID(null);
			}
			if (staging != null) {
				anIDigitalLibraryGroupTrxValue.setStagingReferenceID(String.valueOf(staging.getDigitalLibraryGroupID()));
			}
			else {
				anIDigitalLibraryGroupTrxValue.setStagingReferenceID(null);
			}
			return anIDigitalLibraryGroupTrxValue;
		}
		return null;
	}

	/**
	 * Prepares a result object to be returned
	 * 
	 * @param value is of type IDigitalLibraryGroupTrxValue
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(IDigitalLibraryTrxValue value) {
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
	protected IDigitalLibraryGroup mergeDigitalLibraryGroup(IDigitalLibraryGroup anOriginal, IDigitalLibraryGroup aCopy)
			throws TrxOperationException {
		aCopy.setDigitalLibraryGroupID(anOriginal.getDigitalLibraryGroupID());
		aCopy.setVersionTime(anOriginal.getVersionTime());

		return aCopy;
	}

}