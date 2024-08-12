/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/CheckerRejectRecurrentCheckListReceiptOperation.java,v 1.1 2003/08/14 07:17:42 hltan Exp $
 */
package com.integrosys.cms.app.recurrent.trx;

//app
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a checker to reject a recurrent checklist receipt
 * transaction
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/14 07:17:42 $ Tag: $Name: $
 */
public class CheckerRejectRecurrentCheckListReceiptOperation extends CheckerRejectRecurrentCheckListOperation {
	/**
	 * Defaulc Constructor
	 */
	public CheckerRejectRecurrentCheckListReceiptOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_REJECT_RECURRENT_CHECKLIST_RECEIPT;
	}
}