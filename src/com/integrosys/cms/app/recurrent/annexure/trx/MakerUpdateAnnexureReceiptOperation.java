/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/MakerUpdateRecurrentCheckListReceiptOperation.java,v 1.1 2003/08/14 07:17:42 hltan Exp $
 */
package com.integrosys.cms.app.recurrent.annexure.trx;

//app
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a maker to update a checklist
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/14 07:17:42 $ Tag: $Name: $
 */
public class MakerUpdateAnnexureReceiptOperation extends MakerUpdateAnnexureOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerUpdateAnnexureReceiptOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_UPDATE_ANNEXURE_CHECKLIST;
	}
}