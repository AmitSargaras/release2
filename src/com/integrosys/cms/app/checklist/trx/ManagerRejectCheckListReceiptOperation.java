/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/ManagerRejectCheckListReceiptOperation.java,v 1.1 2003/09/14 11:03:53 hltan Exp $
 */
package com.integrosys.cms.app.checklist.trx;

//app
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a manager to reject a checklist receipt transaction
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/14 11:03:53 $ Tag: $Name: $
 */
public class ManagerRejectCheckListReceiptOperation extends CheckerRejectCheckListReceiptOperation {
	/**
	 * Defaulc Constructor
	 */
	public ManagerRejectCheckListReceiptOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MANAGER_REJECT_CHECKLIST_RECEIPT;
	}
}