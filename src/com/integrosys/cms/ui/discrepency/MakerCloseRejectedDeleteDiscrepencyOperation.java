package com.integrosys.cms.ui.discrepency;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.discrepency.trx.AbstractDiscrepencyTrxOperation;
import com.integrosys.cms.app.discrepency.trx.IDiscrepencyTrxValue;

/**
 * @author $Author: Sandeep Shinde
 * @version 1.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class MakerCloseRejectedDeleteDiscrepencyOperation extends
		AbstractDiscrepencyTrxOperation {

	private static final String DEFAULT_OPERATION_NAME = ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_DELETE_DISCREPENCY;

	private String operationName;

	/**
	 * Defaulc Constructor
	 */
	public MakerCloseRejectedDeleteDiscrepencyOperation() {
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
		IDiscrepencyTrxValue trxValue = super.getDiscrepencyTrxValue(anITrxValue);
		trxValue = updateDiscrepencyTrx(trxValue);
		return super.prepareResult(trxValue);
	}
}
