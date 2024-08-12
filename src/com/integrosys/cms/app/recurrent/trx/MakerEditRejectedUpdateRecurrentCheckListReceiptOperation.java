/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/MakerEditRejectedUpdateRecurrentCheckListReceiptOperation.java,v 1.1 2003/08/14 07:17:42 hltan Exp $
 */
package com.integrosys.cms.app.recurrent.trx;

//app
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a maker to edit a rejected recurrent checklist update
 * transaction
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/14 07:17:42 $ Tag: $Name: $
 */
public class MakerEditRejectedUpdateRecurrentCheckListReceiptOperation extends
		MakerEditRejectedUpdateRecurrentCheckListOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerEditRejectedUpdateRecurrentCheckListReceiptOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_UPDATE_RECURRENT_CHECKLIST_RECEIPT;
	}
}