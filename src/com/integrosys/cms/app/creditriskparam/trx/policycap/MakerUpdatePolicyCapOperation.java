package com.integrosys.cms.app.creditriskparam.trx.policycap;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public class MakerUpdatePolicyCapOperation extends AbstractPolicyCapTrxOperation {

	/**
	 * Default Constructor
	 */
	public MakerUpdatePolicyCapOperation() {
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_UPDATE_POLICY_CAP;
	}

	/**
	 * Process the transaction 1. Create staging record 2. Update the
	 * transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		DefaultLogger.debug(this, ">>>>>>> in MakerUpdatePolicyCapOperation.performProcess");
		IPolicyCapTrxValue trxValue = createStagingPolicyCap(getPolicyCapTrxValue(anITrxValue));
		DefaultLogger.debug(this, ">>>>>>> created staging");
		trxValue = updatePolicyCapTransaction(trxValue);
		DefaultLogger.debug(this, ">>>>>>> updated policy cap transaction");
		return super.prepareResult(trxValue);
	}

}
