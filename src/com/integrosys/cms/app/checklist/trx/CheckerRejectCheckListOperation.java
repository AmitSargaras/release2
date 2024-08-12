/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/CheckerRejectCheckListOperation.java,v 1.2 2003/07/23 07:32:14 hltan Exp $
 */
package com.integrosys.cms.app.checklist.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a checker to reject a checklist transaction
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/23 07:32:14 $ Tag: $Name: $
 */
public class CheckerRejectCheckListOperation extends AbstractCheckListTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public CheckerRejectCheckListOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_REJECT_CHECKLIST;
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
		trxValue = super.updateCheckListTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

}