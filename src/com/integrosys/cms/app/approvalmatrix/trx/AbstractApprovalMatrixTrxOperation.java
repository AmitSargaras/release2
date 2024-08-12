/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/bond/AbstractBondTrxOperation.java,v 1.1 2003/08/06 08:10:08 btchng Exp $
 */
package com.integrosys.cms.app.approvalmatrix.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.approvalmatrix.bus.ApprovalMatrixException;
import com.integrosys.cms.app.approvalmatrix.bus.IApprovalMatrixBusManager;
import com.integrosys.cms.app.approvalmatrix.bus.IApprovalMatrixGroup;
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
public abstract class AbstractApprovalMatrixTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

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
	
	
	public IApprovalMatrixBusManager approvalMatrixBusManager;
	
	public IApprovalMatrixBusManager approvalMatrixBusManagerStaging;



	public IApprovalMatrixBusManager getApprovalMatrixBusManager() {
		return approvalMatrixBusManager;
	}

	public void setApprovalMatrixBusManager(IApprovalMatrixBusManager approvalMatrixBusManager) {
		this.approvalMatrixBusManager = approvalMatrixBusManager;
	}

	public IApprovalMatrixBusManager getApprovalMatrixBusManagerStaging() {
		return approvalMatrixBusManagerStaging;
	}

	public void setApprovalMatrixBusManagerStaging(IApprovalMatrixBusManager approvalMatrixBusManagerStaging) {
		this.approvalMatrixBusManagerStaging = approvalMatrixBusManagerStaging;
	}

	/**
	 * Create the staging document item doc
	 * @param anIApprovalMatrixGroupTrxValue - IApprovalMatrixGroupTrxValue
	 * @return IApprovalMatrixGroupTrxValue - the trx object containing the created
	 *         staging document item
	 * @throws TrxOperationException if errors
	 */
	protected IApprovalMatrixTrxValue createStagingApprovalMatrixGroup(IApprovalMatrixTrxValue anIApprovalMatrixGroupTrxValue)
			throws TrxOperationException {
		try {
			IApprovalMatrixGroup approvalMatrixGroup = getApprovalMatrixBusManagerStaging().createApprovalMatrixGroup(
					anIApprovalMatrixGroupTrxValue.getStagingApprovalMatrixGroup());
			anIApprovalMatrixGroupTrxValue.setStagingApprovalMatrixGroup(approvalMatrixGroup);
			anIApprovalMatrixGroupTrxValue.setStagingReferenceID(String.valueOf(approvalMatrixGroup.getApprovalMatrixGroupID()));
			return anIApprovalMatrixGroupTrxValue;
		}
		catch (ApprovalMatrixException e) {
			throw new TrxOperationException(e.toString());
		}
	}

	/**
	 * Update a approvalMatrixGroup transaction
	 * @param anIApprovalMatrixGroupTrxValue - ITrxValue
	 * @return IApprovalMatrixGroupTrxValue - the document item specific transaction
	 *         object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	protected IApprovalMatrixTrxValue updateApprovalMatrixGroupTransaction(IApprovalMatrixTrxValue anIApprovalMatrixGroupTrxValue)
			throws TrxOperationException {
		try {
			anIApprovalMatrixGroupTrxValue = prepareTrxValue(anIApprovalMatrixGroupTrxValue);

			DefaultLogger.debug(this, "anIApprovalMatrixGroupTrxValue's version time = "
					+ anIApprovalMatrixGroupTrxValue.getVersionTime());

			ICMSTrxValue tempValue = super.updateTransaction(anIApprovalMatrixGroupTrxValue);

			DefaultLogger.debug(this, "tempValue's version time = " + tempValue.getVersionTime());

			OBApprovalMatrixTrxValue newValue = new OBApprovalMatrixTrxValue(tempValue);
			newValue.setApprovalMatrixGroup(anIApprovalMatrixGroupTrxValue.getApprovalMatrixGroup());
			newValue.setStagingApprovalMatrixGroup(anIApprovalMatrixGroupTrxValue.getStagingApprovalMatrixGroup());

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
	 * @return IApprovalMatrixGroupTrxValue - the document item specific trx value
	 *         object
	 * @throws TrxOperationException if there is a ClassCastException
	 */
	protected IApprovalMatrixTrxValue getApprovalMatrixGroupTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			return (IApprovalMatrixTrxValue) anITrxValue;
		}
		catch (ClassCastException cex) {
			throw new TrxOperationException("The ITrxValue is not of type OBApprovalMatrixGroupTrxValue: " + cex.toString());
		}
	}

	protected IApprovalMatrixBusManager getStagingApprovalMatrixBusManager() {
		return getApprovalMatrixBusManagerStaging();
	}


	/**
	 * Prepares a trx object
	 */
	protected IApprovalMatrixTrxValue prepareTrxValue(IApprovalMatrixTrxValue anIApprovalMatrixGroupTrxValue) {
		if (anIApprovalMatrixGroupTrxValue != null) {
			IApprovalMatrixGroup actual = anIApprovalMatrixGroupTrxValue.getApprovalMatrixGroup();
			IApprovalMatrixGroup staging = anIApprovalMatrixGroupTrxValue.getStagingApprovalMatrixGroup();
			if (actual != null) {
				anIApprovalMatrixGroupTrxValue.setReferenceID(String.valueOf(actual.getApprovalMatrixGroupID()));
			}
			else {
				anIApprovalMatrixGroupTrxValue.setReferenceID(null);
			}
			if (staging != null) {
				anIApprovalMatrixGroupTrxValue.setStagingReferenceID(String.valueOf(staging.getApprovalMatrixGroupID()));
			}
			else {
				anIApprovalMatrixGroupTrxValue.setStagingReferenceID(null);
			}
			return anIApprovalMatrixGroupTrxValue;
		}
		return null;
	}

	/**
	 * Prepares a result object to be returned
	 * 
	 * @param value is of type IApprovalMatrixGroupTrxValue
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(IApprovalMatrixTrxValue value) {
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
	protected IApprovalMatrixGroup mergeApprovalMatrixGroup(IApprovalMatrixGroup anOriginal, IApprovalMatrixGroup aCopy)
			throws TrxOperationException {
		aCopy.setApprovalMatrixGroupID(anOriginal.getApprovalMatrixGroupID());
		aCopy.setVersionTime(anOriginal.getVersionTime());

		return aCopy;
	}

}