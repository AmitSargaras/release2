package com.integrosys.cms.app.component.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.component.bus.IComponent;

public class CheckerApproveUpdateComponentOperation extends	AbstractComponentTrxOperation {
	
	public CheckerApproveUpdateComponentOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_COMPONENT;
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
		IComponentTrxValue trxValue = getComponentTrxValue(anITrxValue);
		trxValue = updateActualComponent(trxValue);
		trxValue = updateComponentTrx(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private IComponentTrxValue updateActualComponent(IComponentTrxValue anICCComponentTrxValue)
			throws TrxOperationException {
		try {
			IComponent staging = anICCComponentTrxValue.getStagingComponent();
			IComponent actual = anICCComponentTrxValue.getComponent();

			IComponent updatedComponent = getComponentBusManager().updateToWorkingCopy(actual, staging);
			anICCComponentTrxValue.setComponent(updatedComponent);

			return anICCComponentTrxValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}

}
