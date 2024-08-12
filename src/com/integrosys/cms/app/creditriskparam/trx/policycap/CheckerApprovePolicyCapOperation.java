package com.integrosys.cms.app.creditriskparam.trx.policycap;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public class CheckerApprovePolicyCapOperation extends AbstractPolicyCapTrxOperation {

	/**
	 * Default Constructor
	 */
	public CheckerApprovePolicyCapOperation() {
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_POLICY_CAP;
	}

	/**
	 * Process the transaction 1. Update the actual data 2. Update the
	 * transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IPolicyCapTrxValue trxValue = getPolicyCapTrxValue(anITrxValue);
		trxValue = updateActualPolicyCap(trxValue);
		trxValue = super.updatePolicyCapTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

}
