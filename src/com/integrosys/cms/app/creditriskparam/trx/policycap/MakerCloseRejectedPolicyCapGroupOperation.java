/**
 * 
 */
package com.integrosys.cms.app.creditriskparam.trx.policycap;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Close Rejected PolicyCapGroup by Maker
 * 
 * @author $Author: Siew Kheat $<br>
 * @version $Revision: $
 * @since $Date: 28/Aug/2007 $ Tag: $Name: $
 */
public class MakerCloseRejectedPolicyCapGroupOperation extends AbstractPolicyCapGroupTrxOperation {

	private static final long serialVersionUID = -8527540023397985010L;

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.integrosys.base.businfra.transaction.AbstractTrxOperation#
	 * getOperationName()
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CLOSE_POLICY_CAP_GROUP;
	}

	/**
	 * Process (Update) the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IPolicyCapGroupTrxValue trxValue = super.getPolicyCapGroupTrxValue(anITrxValue);
		trxValue = updatePolicyCapGroupTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}
