package com.integrosys.cms.app.contractfinancing.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 21, 2007 Tag: $Name$
 */
public class MakerCloseDeleteContractFinancingOperation extends AbstractContractFinancingTrxOperation {

	/**
	 * Default Constructor
	 */
	public MakerCloseDeleteContractFinancingOperation() {
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CLOSE_DELETE_CONTRACT_FINANCING;
	}

	/**
	 * Process (Update) the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IContractFinancingTrxValue trxValue = super.getContractFinancingTrxValue(anITrxValue);
		DefaultLogger.debug(this, "in MakerCloseDeleteContractFinancingOperation");
		trxValue = updateContractFinancingTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

}
