/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/trx/MakerEditRejectRejectedUpdateCCTaskOperation.java,v 1.1 2006/04/13 06:29:44 jzhai Exp $
 */
package com.integrosys.cms.app.collaborationtask.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a maker to edit a rejected cc task update transaction
 * 
 * @author $Author: jzhai $
 * @version $Revision: 1.1 $
 * @since $Date: 2006/04/13 06:29:44 $ Tag: $Name: $
 */
public class MakerEditRejectRejectedUpdateCCTaskOperation extends AbstractCCTaskTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerEditRejectRejectedUpdateCCTaskOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_EDIT_REJECT_REJECTED_UPDATE_CC_TASK;
	}

	/**
	 * Process the transaction 1. Create Staging record 2. Update the
	 * transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ICCTaskTrxValue trxValue = createStagingCCTask(getCCTaskTrxValue(anITrxValue));
		trxValue = updateCCTaskTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}