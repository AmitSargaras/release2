package com.integrosys.cms.app.goodsMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.goodsMaster.bus.IGoodsMaster;

/**
 * @author Santosh.Sonmankar
 * Checker approve Operation to approve update made by maker
 */
public class CheckerApproveUpdateGoodsMasterOperation extends AbstractGoodsMasterTrxOperation{
	
	/**
	 * Default Constructor
	 */
	public CheckerApproveUpdateGoodsMasterOperation() {
		super();
	}
	
	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_GOODS_MASTER;
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
		IGoodsMasterTrxValue trxValue = getGoodsMasterTrxValue(anITrxValue);
		trxValue = updateActualGoodsMaster(trxValue);
		trxValue = updateGoodsMasterTrx(trxValue);
		return super.prepareResult(trxValue);
	}
	
	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private IGoodsMasterTrxValue updateActualGoodsMaster(IGoodsMasterTrxValue anICCGoodsMasterTrxValue)
			throws TrxOperationException {
		try {
			IGoodsMaster staging = anICCGoodsMasterTrxValue.getStagingGoodsMaster();
			IGoodsMaster actual = anICCGoodsMasterTrxValue.getGoodsMaster();

			IGoodsMaster updatedGoodsMaster = getGoodsMasterBusManager().updateToWorkingCopy(actual, staging);
			anICCGoodsMasterTrxValue.setGoodsMaster(updatedGoodsMaster);

			return anICCGoodsMasterTrxValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}
	
}
