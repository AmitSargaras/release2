/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/CheckerRejectRecurrentCheckListOperation.java,v 1.1 2003/07/28 02:17:38 hltan Exp $
 */
package com.integrosys.cms.app.recurrent.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a checker to reject a recurrent checklist transaction
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/28 02:17:38 $ Tag: $Name: $
 */
public class CheckerRejectRecurrentCheckListOperation extends AbstractRecurrentCheckListTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public CheckerRejectRecurrentCheckListOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_REJECT_RECURRENT_CHECKLIST;
	}

	/**
	 * Process the transaction 1. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IRecurrentCheckListTrxValue trxValue = super.getCheckListTrxValue(anITrxValue);
		trxValue = super.updateCheckListTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}