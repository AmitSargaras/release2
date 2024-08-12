/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/AbstractRecurrentCheckListTrxOperation.java,v 1.2 2003/08/04 08:53:23 hltan Exp $
 */
package com.integrosys.cms.app.recurrent.annexure.trx;

//java

import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList;
import com.integrosys.cms.app.recurrent.bus.RecurrentException;
import com.integrosys.cms.app.recurrent.bus.SBRecurrentBusManager;
import com.integrosys.cms.app.recurrent.trx.IRecurrentCheckListTrxValue;
import com.integrosys.cms.app.recurrent.trx.OBRecurrentCheckListTrxValue;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Abstract class that contain methods that is common among the set of document
 * item trx operations
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/04 08:53:23 $ Tag: $Name: $
 */
public abstract class AbstractAnnexureTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

	private static final long serialVersionUID = 6007774936877055904L;

	private SBRecurrentBusManager recurrentBusManager;

	private SBRecurrentBusManager stagingRecurrentBusManager;

	/**
	 * @return the recurrentBusManager
	 */
	public SBRecurrentBusManager getRecurrentBusManager() {
		return recurrentBusManager;
	}

	/**
	 * @return the stagingRecurrentBusManager
	 */
	public SBRecurrentBusManager getStagingRecurrentBusManager() {
		return stagingRecurrentBusManager;
	}

	/**
	 * @param recurrentBusManager the recurrentBusManager to set
	 */
	public void setRecurrentBusManager(SBRecurrentBusManager recurrentBusManager) {
		this.recurrentBusManager = recurrentBusManager;
	}

	/**
	 * @param stagingRecurrentBusManager the stagingRecurrentBusManager to set
	 */
	public void setStagingRecurrentBusManager(SBRecurrentBusManager stagingRecurrentBusManager) {
		this.stagingRecurrentBusManager = stagingRecurrentBusManager;
	}

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
	 * This method set the primary key from the original to the copied checklist
	 * objects. It is required for the case of updating staging from actual and
	 * vice versa as there is a need to perform a deep clone of the object and
	 * set the required attribute in the object to the original one so that a
	 * proper update can be done.
	 * @param anOriginal - IRecurrentCheckList
	 * @param aCopy - IRecurrentCheckList
	 * @return IRecurrentCheckList - the copied object with required attributes
	 *         from the original recurrent checklist
	 * @throws TrxOperationException on errors
	 */
	protected IRecurrentCheckList mergeCheckList(IRecurrentCheckList anOriginal, IRecurrentCheckList aCopy)
			throws TrxOperationException {
		aCopy.setCheckListID(anOriginal.getCheckListID());
		aCopy.setVersionTime(anOriginal.getVersionTime());
		return aCopy;
	}

	/**
	 * Create the staging recurrent checklist
	 * @param anICheckListTrxValue - IRecurrentCheckListTrxValue
	 * @return IRecurrentCheckListTrxValue - the trx object containing the
	 *         created staging recurrent checklist
	 * @throws TrxOperationException if errors
	 */
	protected IRecurrentCheckListTrxValue createStagingCheckList(IRecurrentCheckListTrxValue anICheckListTrxValue)
			throws TrxOperationException {
		try {
			IRecurrentCheckList checkList = getStagingRecurrentBusManager().create(
					anICheckListTrxValue.getStagingCheckList());
			anICheckListTrxValue.setStagingCheckList(checkList);
			anICheckListTrxValue.setStagingReferenceID(String.valueOf(checkList.getCheckListID()));
			return anICheckListTrxValue;
		}
		catch (RecurrentException ex) {
			throw new TrxOperationException("failed to create staging checklist", ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException(
					"failed to operate over the recurrent remote interface, throwing root cause", ex.getCause());
		}
	}

	/**
	 * Update a checklist transaction
	 * @param anICheckListTrxValue - ITrxValue
	 * @return IRecurrentCheckListTrxValue - the document item specific
	 *         transaction object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	protected IRecurrentCheckListTrxValue updateCheckListTransaction(IRecurrentCheckListTrxValue anICheckListTrxValue)
			throws TrxOperationException {
		anICheckListTrxValue = prepareTrxValue(anICheckListTrxValue);
		ICMSTrxValue tempValue = super.updateTransaction(anICheckListTrxValue);

		OBRecurrentCheckListTrxValue newValue = new OBRecurrentCheckListTrxValue(tempValue);
		newValue.setCheckList(anICheckListTrxValue.getCheckList());
		newValue.setStagingCheckList(anICheckListTrxValue.getStagingCheckList());

		return newValue;
	}

	/**
	 * Helper method to cast a generic trx value object to a document item
	 * specific trx value object
	 * @param anITrxValue - ITrxValue
	 * @return IRecurrentCheckListTrxValue - the document item specific trx
	 *         value object
	 * @throws TrxOperationException if there is a ClassCastException
	 */
	protected IRecurrentCheckListTrxValue  getCheckListTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
		return (IRecurrentCheckListTrxValue) anITrxValue;
	}

	/**
	 * Prepares a trx object
	 */
	protected IRecurrentCheckListTrxValue prepareTrxValue(IRecurrentCheckListTrxValue anICheckListTrxValue) {
		if (anICheckListTrxValue != null) {
			IRecurrentCheckList actual = anICheckListTrxValue.getCheckList();
			IRecurrentCheckList staging = anICheckListTrxValue.getStagingCheckList();

			if (actual != null) {
				anICheckListTrxValue.setReferenceID(String.valueOf(actual.getCheckListID()));
			}
			else {
				anICheckListTrxValue.setReferenceID(null);
			}

			if (staging != null) {
				anICheckListTrxValue.setStagingReferenceID(String.valueOf(staging.getCheckListID()));
			}
			else {
				anICheckListTrxValue.setStagingReferenceID(null);
			}

			return anICheckListTrxValue;
		}
		return null;
	}

	/**
	 * Prepares a result object to be returned
	 * 
	 * @param value is of type ICheckListTrxValue
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(IRecurrentCheckListTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}
}