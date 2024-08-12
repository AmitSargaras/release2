/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/SystemRejectGenerateDeferralRequestCheckListOperation.java,v 1.2 2003/09/23 03:02:14 hltan Exp $
 */
package com.integrosys.cms.app.checklist.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a system to reject a generate deferral request for a
 * checklist
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/23 03:02:14 $ Tag: $Name: $
 */
public class SystemRejectGenerateDeferralRequestCheckListOperation extends AbstractCheckListTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public SystemRejectGenerateDeferralRequestCheckListOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_SYSTEM_REJECT_GENERATE_CHECKLIST_DEFERRAL;
	}

	/**
	 * Pre process. To reset the status to awaiting
	 * @param anITrxValue is of type ITrxValue
	 * @return ITrxValue
	 * @throws TrxOperationException on error
	 */
	public ITrxValue preProcess(ITrxValue anITrxValue) throws TrxOperationException {
		anITrxValue = super.preProcess(anITrxValue);
		ICheckListTrxValue trxValue = getCheckListTrxValue(anITrxValue);
		ICheckList orgCheckList = trxValue.getCheckList();
		ICheckListItem[] orgItemList = orgCheckList.getCheckListItemList();
		for (int ii = 0; ii < orgItemList.length; ii++) {
			if (ICMSConstant.STATE_ITEM_DEFER_REQ.equals(orgItemList[ii].getItemStatus())) {

				orgItemList[ii].setItemStatus(ICMSConstant.STATE_ITEM_AWAITING);
			}
		}
		orgCheckList.setCheckListItemList(orgItemList);
		return anITrxValue;
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
		ICheckList checkList = updateActualCheckList(trxValue.getCheckList());
		trxValue = updateCheckListTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}