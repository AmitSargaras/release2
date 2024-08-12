package com.integrosys.cms.app.creditriskparam.trx.policycap;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Update/Submit PolicyCapGroup by Maker
 * 
 * @author $Author: Siew Kheat $<br>
 * @version $Revision: $
 * @since $Date: 28/Aug/2007 $ Tag: $Name: $
 */
public class MakerUpdatePolicyCapGroupOperation extends AbstractPolicyCapGroupTrxOperation {

	private static final long serialVersionUID = 3453844569573502804L;

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.integrosys.base.businfra.transaction.AbstractTrxOperation#
	 * getOperationName()
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_UPDATE_POLICY_CAP_GROUP;
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
		DefaultLogger.debug(this, ">>>>>>> in MakerUpdatePolicyCapGroupOperation.performProcess");
		IPolicyCapGroupTrxValue trxValue = createStagingPolicyCapGroup(getPolicyCapGroupTrxValue(anITrxValue));
		DefaultLogger.debug(this, ">>>>>>> created staging");
		trxValue = updatePolicyCapGroupTransaction(trxValue);
		DefaultLogger.debug(this, ">>>>>>> updated policy cap transaction");
		return super.prepareResult(trxValue);
	}

}
