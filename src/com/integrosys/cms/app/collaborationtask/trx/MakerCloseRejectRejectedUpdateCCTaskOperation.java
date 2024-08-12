/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/trx/MakerCloseRejectRejectedUpdateCCTaskOperation.java,v 1.1 2006/04/13 06:28:49 jzhai Exp $
 */
package com.integrosys.cms.app.collaborationtask.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a maker to close a rejected cc task transaction
 * 
 * @author $Author: jzhai $
 * @version $Revision: 1.1 $
 * @since $Date: 2006/04/13 06:28:49 $ Tag: $Name: $
 */
public class MakerCloseRejectRejectedUpdateCCTaskOperation extends AbstractCCTaskTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerCloseRejectRejectedUpdateCCTaskOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CLOSE_REJECT_REJECTED_UPDATE_CC_TASK;
	}

	/**
	 * Process the transaction 1. Create staging from the actual 2. Update the
	 * transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ICCTaskTrxValue trxValue = getCCTaskTrxValue(anITrxValue);
		trxValue = updateCCTaskTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}