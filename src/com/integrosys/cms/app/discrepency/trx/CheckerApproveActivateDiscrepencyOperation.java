package com.integrosys.cms.app.discrepency.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.discrepency.bus.IDiscrepency;

/**
 * 
 * @author sandiip.shinde
 * @since 01-06-2011
 */

public class CheckerApproveActivateDiscrepencyOperation extends AbstractDiscrepencyTrxOperation{

	/**
	 * Default Constructor
	 */
	public CheckerApproveActivateDiscrepencyOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_ACTIVATE_DISCREPENCY;
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
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		
		IDiscrepencyTrxValue trxValue = getDiscrepencyTrxValue(anITrxValue);
		trxValue = updateActualDiscrepency(trxValue);
		trxValue = updateDiscrepencyTrx(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private IDiscrepencyTrxValue updateActualDiscrepency(IDiscrepencyTrxValue anIDiscrepencyTrxValue)
			throws TrxOperationException {
		try {
			IDiscrepency staging = anIDiscrepencyTrxValue.getStagingDiscrepency();
			IDiscrepency actual = anIDiscrepencyTrxValue.getActualDiscrepency();

			IDiscrepency updatedDiscrepency = getDiscrepencyBusManager().updateToWorkingCopy(actual, staging);
			anIDiscrepencyTrxValue.setActualDiscrepency(updatedDiscrepency);

			return anIDiscrepencyTrxValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}
}
