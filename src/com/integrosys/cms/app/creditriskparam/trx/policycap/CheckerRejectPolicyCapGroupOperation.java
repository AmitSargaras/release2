/**
 * 
 */
package com.integrosys.cms.app.creditriskparam.trx.policycap;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Reject PolicyCapGroup Operation by Checker
 * 
 * @author $Author: Siew Kheat $<br>
 * @version $Revision: $
 * @since $Date: 28/Aug/2007 $ Tag: $Name: $
 */
public class CheckerRejectPolicyCapGroupOperation extends AbstractPolicyCapGroupTrxOperation {

	private static final long serialVersionUID = -777859861320849308L;

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_REJECT_POLICY_CAP_GROUP;
	}

	/**
	 * Process the transaction 1. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		IPolicyCapGroupTrxValue trxValue = getPolicyCapGroupTrxValue(value);
		trxValue = super.updatePolicyCapGroupTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}
