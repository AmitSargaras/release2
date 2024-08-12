/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/CheckerRejectCheckListReceiptOperation.java,v 1.5 2005/07/18 10:31:15 hshii Exp $
 */
package com.integrosys.cms.app.checklist.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.checklist.bus.ICCCheckListOwner;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a checker to reject a checklist receipt transaction
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.5 $
 * @since $Date: 2005/07/18 10:31:15 $ Tag: $Name: $
 */
public class CheckerRejectCheckListReceiptOperation extends AbstractCheckListTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public CheckerRejectCheckListReceiptOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_REJECT_CHECKLIST_RECEIPT;
	}

	/**
	 * Process the transaction 1. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ICheckListTrxValue trxValue = super.getCheckListTrxValue(anITrxValue);

		// start - Added for R1.3.1 CR28
		if (trxValue.getCheckList().getCheckListOwner() instanceof ICCCheckListOwner) {
			trxValue = super.createStagingCheckList(trxValue);
		}
		// end - Added for R1.3.1 CR28

		trxValue = super.updateCheckListTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

}