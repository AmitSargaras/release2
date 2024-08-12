package com.integrosys.cms.app.tatduration.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.tatduration.bus.ITatParam;
import com.integrosys.cms.app.tatduration.bus.ITatParamBusManager;
import com.integrosys.cms.app.tatduration.bus.TatParamException;
import com.integrosys.cms.app.tatdoc.bus.ITatDoc;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * @author Cynthia Zhou
 * @since Aug 29, 2008
 */
public abstract class AbstractTatParamTrxOperation extends CMSTrxOperation implements ITrxRouteOperation
{

	private static final long serialVersionUID = 4859286979931186359L;

	private ITatParamBusManager tatParamBusManager;
	private ITatParamBusManager stageTatParamBusManager;

	
	public ITatParamBusManager getTatParamBusManager() 
	{
		return tatParamBusManager;
	}

	public void setTatParamBusManager(ITatParamBusManager tatParamBusManager) 
	{
		this.tatParamBusManager = tatParamBusManager;
	}

	public ITatParamBusManager getStageTatParamBusManager() 
	{
		return stageTatParamBusManager;
	}

	public void setStageTatParamBusManager(ITatParamBusManager stageTatParamBusManager) 
	{
		this.stageTatParamBusManager = stageTatParamBusManager;
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
	public ITrxValue getNextRoute(ITrxValue value) throws TransactionException 
	{
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
	/*protected ITatDoc mergeTatDoc(ITatDoc anOriginal, ITatDoc aCopy) throws TrxOperationException {
		aCopy.setTatDocID(anOriginal.getTatDocID());
		aCopy.setVersionTime(anOriginal.getVersionTime());
		return aCopy;
	}*/

	// ==================================
	// Create Methods
	// ==================================
	/**
	 * Create the staging tat document object
	 * @param tatParamTrxValue - ITatDocTrxValue type
	 * @return ITatDocTrxValue - the trx object containing the created staging
	 *         tat document
	 * @throws TrxOperationException if errors
	 */
	protected ITatParamTrxValue createStagingTatParam(ITatParamTrxValue tatParamTrxValue) throws TrxOperationException 
	{
		try 
		{
			ITatParam stageTatParamItem = getStageTatParamBusManager().create(tatParamTrxValue.getStagingTatParam());
			tatParamTrxValue.setStagingTatParam(stageTatParamItem);
			tatParamTrxValue.setStagingReferenceID(String.valueOf(stageTatParamItem.getTatParamId()));
			tatParamTrxValue.setTatParam(tatParamTrxValue.getTatParam());
			tatParamTrxValue.setReferenceID(String.valueOf(tatParamTrxValue.getTatParam().getTatParamId()));
			return tatParamTrxValue;
		}
		catch (TatParamException ex) 
		{
			throw new TrxOperationException("failed to create staging tat doc [" + tatParamTrxValue.getStagingTatParam() + "]", ex);
		}
	}

	/**
	 * Create the actual tat document object
	 * @param tatDocTrxValue - ITatDocTrxValue type
	 * @return ITatDocTrxValue - the trx object containing the created actual
	 *         tat document
	 * @throws TrxOperationException if errors
	 */
	/*protected ITatDocTrxValue createActualTatDoc(ITatDocTrxValue tatDocTrxValue) throws TrxOperationException {
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

	}*/

	/**
	 * Create a tat document transaction
	 * @param anITatDocTrxValue - ITatDocTrxValue
	 * @return ITatDocTrxValue - the tat document specific transaction object
	 *         created
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         there is any processing errors
	 */
	/*protected ITatDocTrxValue createTatDocTransaction(ITatDocTrxValue anITatDocTrxValue) throws TrxOperationException {
		anITatDocTrxValue = prepareTrxValue(anITatDocTrxValue);
		ICMSTrxValue trxValue = createTransaction(anITatDocTrxValue);
		OBTatDocTrxValue tatDocTrxValue = new OBTatDocTrxValue(trxValue);
		tatDocTrxValue.setStagingTatDoc(anITatDocTrxValue.getStagingTatDoc());
		tatDocTrxValue.setTatDoc(anITatDocTrxValue.getTatDoc());
		return tatDocTrxValue;
	}*/

	// ==================================
	// Update Methods
	// ==================================
	/**
	 * Update the actual tat document object
	 * @param anITatDocTrxValue - ITrxValue type
	 * @return ITatDocTrxValue - the tat document trx value
	 * @throws TrxOperationException on errors
	 */
	/*protected ITatDocTrxValue updateActualTatDoc(ITatDocTrxValue anITatDocTrxValue) throws TrxOperationException {
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
	}*/

	/**
	 * Update a tat document transaction
	 * @param anITatDocTrxValue of ITatDocTrxValue type
	 * @return ITatDocTrxValue - the tat document specific transaction object
	 *         updated
	 * @throws TrxOperationException if there is any processing errors
	 */
	protected ITatParamTrxValue updateTatParamTransaction(ITatParamTrxValue anITatDocTrxValue) throws TrxOperationException 
	{
		anITatDocTrxValue = prepareTrxValue(anITatDocTrxValue);
		ICMSTrxValue trxValue = updateTransaction(anITatDocTrxValue);
		OBTatParamTrxValue newTrxValue = new OBTatParamTrxValue(trxValue);
		newTrxValue.setStagingTatParam(anITatDocTrxValue.getStagingTatParam());
		newTrxValue.setTatParam(anITatDocTrxValue.getTatParam());
		return newTrxValue;
	}

	// ========================================================
	// Start of helper methods - to be used by child classes
	// ========================================================
	/**
	 * Prepares a trx object
	 */
	protected ITatParamTrxValue prepareTrxValue(ITatParamTrxValue tatParamTrxValue)
	{

		if (tatParamTrxValue != null) 
		{
			ITatParam actual = tatParamTrxValue.getTatParam();
			ITatParam staging = tatParamTrxValue.getStagingTatParam();

			if ((actual != null) && (actual.getTatParamId() != ICMSConstant.LONG_INVALID_VALUE))
			{
				tatParamTrxValue.setReferenceID(String.valueOf(actual.getTatParamId()));
			}
			else 
			{
				tatParamTrxValue.setReferenceID(null);
			}

			if ((staging != null) && (staging.getTatParamId() != ICMSConstant.LONG_INVALID_VALUE))
			{
				tatParamTrxValue.setStagingReferenceID(String.valueOf(staging.getTatParamId()));
			}
			else 
			{
				tatParamTrxValue.setStagingReferenceID(null);
			}

			return tatParamTrxValue;
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
	protected ITatParamTrxValue getTatParamTrxValue(ITrxValue anITrxValue) throws TrxOperationException 
	{
		return (ITatParamTrxValue) anITrxValue;
	}

	/**
	 * Prepares a result object to be returned
	 * @param value of ITatParamTrxValue type
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(ITatParamTrxValue value)
	{
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

}
