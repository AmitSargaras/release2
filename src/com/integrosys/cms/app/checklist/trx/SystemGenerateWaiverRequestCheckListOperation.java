/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/SystemGenerateWaiverRequestCheckListOperation.java,v 1.1 2003/09/12 17:38:20 hltan Exp $
 */
package com.integrosys.cms.app.checklist.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a system to generate a waiver request for a checklist
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/12 17:38:20 $ Tag: $Name: $
 */
public class SystemGenerateWaiverRequestCheckListOperation extends AbstractCheckListTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public SystemGenerateWaiverRequestCheckListOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_SYSTEM_GENERATE_CHECKLIST_WAIVER;
	}

	/**
	 * Process the transaction 1. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ICheckListTrxValue trxValue = createStagingCheckList(getCheckListTrxValue(anITrxValue));
		trxValue = updateCheckListTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}