package com.integrosys.cms.app.tatduration.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.tatduration.bus.ITatParam;
import com.integrosys.cms.app.tatduration.bus.ITatParamConstant;
import com.integrosys.cms.app.tatduration.bus.TatParamException;

/**
 * Title: CLIMS Description: Copyright: Integro Technologies Sdn Bhd Author:
 * Andy Wong Date: Jan 18, 2008
 */

public class CheckerApproveTatParamOperation extends AbstractTatParamTrxOperation 
{
	/**
	 * Default Constructor
	 */
	public CheckerApproveTatParamOperation() 
	{
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() 
	{
		return ITatParamConstant.ACTION_CHECKER_APPROVE_TAT_DURATION;
	}

	/**
	 * Process the transaction 1. Update the actual data 2. Update the
	 * transaction record
	 * 
	 * @param anITrxValue of ITrxValue type
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException 
	{
		anITrxValue = super.preProcess(anITrxValue);
		ITatParamTrxValue trxValue = getTatParamTrxValue(anITrxValue);
		trxValue = updateActualTatParam(trxValue);
		trxValue = updateTatParamTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private ITatParamTrxValue updateActualTatParam(ITatParamTrxValue trxValue) throws TrxOperationException 
	{
		try {
			ITatParam staging = trxValue.getStagingTatParam();
			ITatParam actual = trxValue.getTatParam();

			ITatParam updatedTatParam = getTatParamBusManager().updateToWorkingCopy(actual, staging);
			trxValue.setTatParam(updatedTatParam);

			return trxValue;
		}
		catch (TatParamException ex) 
		{
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("Exception in updateActualTatParam(): " + ex.toString());
		}
	}
}
