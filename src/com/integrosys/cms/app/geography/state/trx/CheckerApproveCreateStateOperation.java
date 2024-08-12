package com.integrosys.cms.app.geography.state.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.geography.state.bus.StateReplicationUtils;

/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */

public class CheckerApproveCreateStateOperation extends AbstractStateTrxOperation{

	/* * Default Constructor
	 */
	public CheckerApproveCreateStateOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_STATE;
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
		
		IStateTrxValue trxValue = getStateTrxValue(anITrxValue);
		trxValue = createActualState(trxValue);
		trxValue = updateStateTrx(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private IStateTrxValue createActualState(IStateTrxValue anIStateTrxValue)
			throws TrxOperationException {
		try {
			IState staging = anIStateTrxValue.getStagingState();
			IState replicateStaging = StateReplicationUtils.replicateStateForCreateStagingCopy(staging);			

			IState updatedState = getStateBusManager().createState(replicateStaging);
			anIStateTrxValue.setActualState(updatedState);

			return anIStateTrxValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}
}
