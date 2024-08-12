/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/CheckerForwardCheckListReceiptOperation.java,v 1.2 2005/07/18 10:31:15 hshii Exp $
 */
package com.integrosys.cms.app.checklist.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.checklist.bus.ICCCheckListOwner;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxForwardOperationBase;

/**
 * This operation allows a checker to reject a checklist receipt transaction
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.2 $
 * @since $Date: 2005/07/18 10:31:15 $ Tag: $Name: $
 */
public class CheckerForwardCheckListReceiptOperation extends CMSTrxForwardOperationBase {

	/**
	 * Defaulc Constructor
	 */
	public CheckerForwardCheckListReceiptOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_FORWARD;
	}

	/**
	 * Process the transaction 1. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ITrxResult trxResult = null;

		if (((ICheckListTrxValue) anITrxValue).getCheckList().getCheckListOwner() instanceof ICCCheckListOwner) {
			trxResult = (new MakerUpdateCheckListReceiptOperation()).performProcess(anITrxValue);
		}
		else {
			trxResult = super.performProcess(anITrxValue);
		}

		return trxResult;
	}

}