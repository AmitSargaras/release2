/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/MakerCloseDraftUpdateRecurrentCheckListReceiptOperation.java,v 1.1 2005/04/07 02:48:44 htli Exp $
 */
package com.integrosys.cms.app.recurrent.trx;

//app
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a maker to close a rejected checklist transaction
 * 
 * @author $Author: htli $
 * @version $Revision: 1.1 $
 * @since $Date: 2005/04/07 02:48:44 $ Tag: $Name: $
 */
public class MakerCloseDraftUpdateRecurrentCheckListReceiptOperation extends
		MakerCloseRejectedUpdateRecurrentCheckListOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerCloseDraftUpdateRecurrentCheckListReceiptOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_RECURRENT_CHECKLIST_RECEIPT;
	}
}