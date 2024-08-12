package com.integrosys.cms.app.baselmaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.baselmaster.bus.IBaselMaster;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.component.bus.IComponent;
import com.integrosys.cms.app.component.trx.IComponentTrxValue;

public class CheckerApproveUpdateBaselOperation extends AbstractBaselTrxOperation{
	

	
	public CheckerApproveUpdateBaselOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_BASEL;
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
		IBaselMasterTrxValue trxValue = getBaselTrxValue(anITrxValue);
		trxValue = updateActualBasel(trxValue);
		trxValue = updateBaselTrx(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private IBaselMasterTrxValue updateActualBasel(IBaselMasterTrxValue anICCBaselTrxValue)
			throws TrxOperationException {
		try {
			IBaselMaster staging = anICCBaselTrxValue.getStagingBaselMaster();
			IBaselMaster actual = anICCBaselTrxValue.getBaselMaster();

			IBaselMaster updatedBasel = getBaselBusManager().updateToWorkingCopy(actual, staging);
			anICCBaselTrxValue.setBaselMaster(updatedBasel);

			return anICCBaselTrxValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}



}
