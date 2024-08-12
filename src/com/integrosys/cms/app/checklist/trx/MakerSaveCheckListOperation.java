/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/MakerSaveCheckListOperation.java,v 1.3 2004/03/02 12:05:15 hltan Exp $
 */
package com.integrosys.cms.app.checklist.trx;

//app
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a maker to save a checklist
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2004/03/02 12:05:15 $ Tag: $Name: $
 */
// public class MakerSaveCheckListOperation extends
// AbstractCheckListTrxOperation
public class MakerSaveCheckListOperation extends MakerUpdateCheckListReceiptOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerSaveCheckListOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_SAVE_CHECKLIST;
	}

	/**
	 * Process the transaction 1. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	/*
	 * public ITrxResult performProcess(ITrxValue anITrxValue) throws
	 * TrxOperationException { ICheckListTrxValue trxValue =
	 * createStagingCheckList(getCheckListTrxValue(anITrxValue)); trxValue =
	 * updateCheckListTransaction(trxValue); return
	 * super.prepareResult(trxValue); }
	 */
}