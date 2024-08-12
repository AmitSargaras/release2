package com.integrosys.cms.app.contractfinancing.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 21, 2007 Tag: $Name$
 */
public class MakerCreateNDContractFinancingOperation extends AbstractContractFinancingTrxOperation {

	/**
	 * Default Constructor
	 */
	public MakerCreateNDContractFinancingOperation() {
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CREATE_CONTRACT_FINANCING;
	}

	/**
	 * Process the transaction 1. Create the staging data 2. Create the
	 * transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IContractFinancingTrxValue trxValue = super.getContractFinancingTrxValue(anITrxValue);
		trxValue = createStagingContractFinancing(trxValue);
		trxValue = createContractFinancingTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

}
