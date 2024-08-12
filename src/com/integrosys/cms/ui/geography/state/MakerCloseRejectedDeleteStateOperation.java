package com.integrosys.cms.ui.geography.state;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.geography.state.trx.AbstractStateTrxOperation;
import com.integrosys.cms.app.geography.state.trx.IStateTrxValue;

/**
 * @author $Author: Sandeep Shinde
 * @version 1.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class MakerCloseRejectedDeleteStateOperation extends
		AbstractStateTrxOperation {

	private static final String DEFAULT_OPERATION_NAME = ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_DELETE_STATE;

	private String operationName;

	/**
	 * Defaulc Constructor
	 */
	public MakerCloseRejectedDeleteStateOperation() {
		operationName = DEFAULT_OPERATION_NAME;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	/**
	 * Process the transaction 1. Update the transaction record
	 * 
	 * @param anITrxValue
	 *            - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException
	 *             if encounters any error during the processing of the
	 *             transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue)
			throws TrxOperationException {
		IStateTrxValue trxValue = super.getStateTrxValue(anITrxValue);
		trxValue = updateStateTrx(trxValue);
		return super.prepareResult(trxValue);
	}
}
