package com.integrosys.cms.app.tatdoc.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.tatdoc.bus.ITatDoc;
import com.integrosys.cms.app.tatdoc.bus.ITatDocBusManager;
import com.integrosys.cms.app.tatdoc.bus.TatDocException;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * @author Cynthia Zhou
 * @since Aug 29, 2008
 */
public abstract class AbstractTatDocTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

	private static final long serialVersionUID = 4859286979931186359L;

	private ITatDocBusManager tatDocBusManager;

	private ITatDocBusManager stageTatDocBusManager;

	public ITatDocBusManager getTatDocBusManager() {
		return tatDocBusManager;
	}

	public void setTatDocBusManager(ITatDocBusManager tatDocBusManager) {
		this.tatDocBusManager = tatDocBusManager;
	}

	public ITatDocBusManager getStageTatDocBusManager() {
		return stageTatDocBusManager;
	}

	public void setStageTatDocBusManager(ITatDocBusManager stageTatDocBusManager) {
		this.stageTatDocBusManager = stageTatDocBusManager;
	}

	/**
	 * Sets the next route requirements into the ITrxValue.
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxValue containing the required routing information for next
	 *         user
	 * @throws com.integrosys.base.businfra.transaction.TransactionException on
	 *         error
	 */
	public ITrxValue getNextRoute(ITrxValue value) throws TransactionException {
		return value;
	}

	/**
	 * This method set the primary key from the original to the copied tat
	 * document object. It is required for the case of updating staging from
	 * actual and vice versa as there is a need to perform a deep clone of the
	 * object and set the required attribute in the object to the original one
	 * so that a proper update can be done.
	 * @param anOriginal - ITatDoc type
	 * @param aCopy - ITatDoc type
	 * @return ITatDoc - the copied object with required attributes from the
	 *         original object
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException on
	 *         errors
	 */
	protected ITatDoc mergeTatDoc(ITatDoc anOriginal, ITatDoc aCopy) throws TrxOperationException {
		aCopy.setTatDocID(anOriginal.getTatDocID());
		aCopy.setVersionTime(anOriginal.getVersionTime());
		return aCopy;
	}

	// ==================================
	// Create Methods
	// ==================================
	/**
	 * Create the staging tat document object
	 * @param tatDocTrxValue - ITatDocTrxValue type
	 * @return ITatDocTrxValue - the trx object containing the created staging
	 *         tat document
	 * @throws TrxOperationException if errors
	 */
	protected ITatDocTrxValue createStagingTatDoc(ITatDocTrxValue tatDocTrxValue) throws TrxOperationException {
		try {
			ITatDoc tatDoc = getStageTatDocBusManager().create(tatDocTrxValue.getStagingTatDoc());
			tatDocTrxValue.setStagingTatDoc(tatDoc);
			tatDocTrxValue.setStagingReferenceID(String.valueOf(tatDoc.getTatDocID()));
			return tatDocTrxValue;
		}
		catch (TatDocException ex) {
			throw new TrxOperationException("failed to create staging tat doc [" + tatDocTrxValue.getStagingTatDoc()
					+ "]", ex);
		}
	}

	/**
	 * Create the actual tat document object
	 * @param tatDocTrxValue - ITatDocTrxValue type
	 * @return ITatDocTrxValue - the trx object containing the created actual
	 *         tat document
	 * @throws TrxOperationException if errors
	 */
	protected ITatDocTrxValue createActualTatDoc(ITatDocTrxValue tatDocTrxValue) throws TrxOperationException {
		ITatDoc staging = tatDocTrxValue.getStagingTatDoc();

		try {
			ITatDoc actualTatDoc = getTatDocBusManager().updateToWorkingCopy(null, staging);

			tatDocTrxValue.setTatDoc(actualTatDoc);
			tatDocTrxValue.setReferenceID(String.valueOf(actualTatDoc.getTatDocID()));

			getTatDocBusManager().insertOrRemovePendingPerfectionCreditFolder(staging,
					tatDocTrxValue.getTrxContext().getLimitProfile());

			return tatDocTrxValue;
		}
		catch (TatDocException ex) {
			throw new TrxOperationException("failed to create actual tat doc using staging [" + staging + "]", ex);
		}

	}

	/**
	 * Create a tat document transaction
	 * @param anITatDocTrxValue - ITatDocTrxValue
	 * @return ITatDocTrxValue - the tat document specific transaction object
	 *         created
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         there is any processing errors
	 */
	protected ITatDocTrxValue createTatDocTransaction(ITatDocTrxValue anITatDocTrxValue) throws TrxOperationException {
		anITatDocTrxValue = prepareTrxValue(anITatDocTrxValue);
		ICMSTrxValue trxValue = createTransaction(anITatDocTrxValue);
		OBTatDocTrxValue tatDocTrxValue = new OBTatDocTrxValue(trxValue);
		tatDocTrxValue.setStagingTatDoc(anITatDocTrxValue.getStagingTatDoc());
		tatDocTrxValue.setTatDoc(anITatDocTrxValue.getTatDoc());
		return tatDocTrxValue;
	}

	// ==================================
	// Update Methods
	// ==================================
	/**
	 * Update the actual tat document object
	 * @param anITatDocTrxValue - ITrxValue type
	 * @return ITatDocTrxValue - the tat document trx value
	 * @throws TrxOperationException on errors
	 */
	protected ITatDocTrxValue updateActualTatDoc(ITatDocTrxValue anITatDocTrxValue) throws TrxOperationException {
		try {
			ITatDoc staging = anITatDocTrxValue.getStagingTatDoc();
			ITatDoc actual = anITatDocTrxValue.getTatDoc();

			ITatDoc actualTatDoc = getTatDocBusManager().updateToWorkingCopy(actual, staging);

			getTatDocBusManager().insertOrRemovePendingPerfectionCreditFolder(actualTatDoc,
					anITatDocTrxValue.getTrxContext().getLimitProfile());

			anITatDocTrxValue.setTatDoc(actualTatDoc);
			anITatDocTrxValue.setReferenceID(String.valueOf(actualTatDoc.getTatDocID()));
			return anITatDocTrxValue;
		}
		catch (TatDocException ex) {
			throw new TrxOperationException("failed to update staging copy into actual copy for trx ["
					+ anITatDocTrxValue + "]", ex);
		}
	}

	/**
	 * Update a tat document transaction
	 * @param anITatDocTrxValue of ITatDocTrxValue type
	 * @return ITatDocTrxValue - the tat document specific transaction object
	 *         updated
	 * @throws TrxOperationException if there is any processing errors
	 */
	protected ITatDocTrxValue updateTatDocTransaction(ITatDocTrxValue anITatDocTrxValue) throws TrxOperationException {
		anITatDocTrxValue = prepareTrxValue(anITatDocTrxValue);
		ICMSTrxValue trxValue = updateTransaction(anITatDocTrxValue);
		OBTatDocTrxValue newTrxValue = new OBTatDocTrxValue(trxValue);
		newTrxValue.setStagingTatDoc(anITatDocTrxValue.getStagingTatDoc());
		newTrxValue.setTatDoc(anITatDocTrxValue.getTatDoc());
		return newTrxValue;
	}

	// ========================================================
	// Start of helper methods - to be used by child classes
	// ========================================================
	/**
	 * Prepares a trx object
	 */
	protected ITatDocTrxValue prepareTrxValue(ITatDocTrxValue tatDocTrxValue) {

		if (tatDocTrxValue != null) {
			ITatDoc actual = tatDocTrxValue.getTatDoc();
			ITatDoc staging = tatDocTrxValue.getStagingTatDoc();

			if ((actual != null) && (actual.getTatDocID() != ICMSConstant.LONG_INVALID_VALUE)) {
				tatDocTrxValue.setReferenceID(String.valueOf(actual.getTatDocID()));
			}
			else {
				tatDocTrxValue.setReferenceID(null);
			}

			if ((staging != null) && (staging.getTatDocID() != ICMSConstant.LONG_INVALID_VALUE)) {
				tatDocTrxValue.setStagingReferenceID(String.valueOf(staging.getTatDocID()));
			}
			else {
				tatDocTrxValue.setStagingReferenceID(null);
			}

			return tatDocTrxValue;
		}
		return null;
	}

	/**
	 * Helper method to cast a generic trx value object to a document item
	 * specific trx value object
	 * @param anITrxValue of ITrxValue type
	 * @return ITatDocTrxValue - the tat document specific trx value object
	 * @throws TrxOperationException if there is a ClassCastException
	 */
	protected ITatDocTrxValue getTatDocTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
		return (ITatDocTrxValue) anITrxValue;
	}

	/**
	 * Prepares a result object to be returned
	 * @param value of ITatDocTrxValue type
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(ITatDocTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

}
