/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/ManagerApproveUpdateCheckListReceiptOperation.java,v 1.1 2003/09/14 11:03:53 hltan Exp $
 */
package com.integrosys.cms.app.checklist.trx;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a manager to approve the checklist receipt updating
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/14 11:03:53 $ Tag: $Name: $
 */
public class ManagerApproveUpdateCheckListReceiptOperation extends CheckerApproveUpdateCheckListReceiptOperation {
	/**
	 * Defaulc Constructor
	 */
	public ManagerApproveUpdateCheckListReceiptOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MANAGER_VERIFY_CHECKLIST_RECEIPT;
	}

	protected boolean requireStateChange(String anItemStatus) {
		if ((anItemStatus.equals(ICMSConstant.STATE_ITEM_PENDING_WAIVER_REQ))
				|| (anItemStatus.equals(ICMSConstant.STATE_ITEM_PENDING_DEFER_REQ))
				|| (anItemStatus.equals(ICMSConstant.STATE_ITEM_PENDING_COMPLETE))
				|| (anItemStatus.equals(ICMSConstant.STATE_ITEM_PENDING_RENEWAL))
				|| (anItemStatus.equals(ICMSConstant.STATE_ITEM_PENDING_WAIVER))
				|| (anItemStatus.equals(ICMSConstant.STATE_ITEM_PENDING_DEFERRAL))
				|| (anItemStatus.equals(ICMSConstant.STATE_ITEM_PENDING_DELETE))) {
			return true;
		}
		return false;
	}
}