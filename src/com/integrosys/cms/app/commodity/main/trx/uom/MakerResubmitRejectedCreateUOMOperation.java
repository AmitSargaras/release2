/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/uom/MakerResubmitRejectedCreateUOMOperation.java,v 1.2 2004/06/04 04:54:22 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.uom;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 04:54:22 $ Tag: $Name: $
 */
public class MakerResubmitRejectedCreateUOMOperation extends MakerResubmitRejectedUOMOperation {
	/**
	 * Get the name of the current operation
	 * 
	 * @return String - the name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CREATE_RESUBMIT_COMMODITY_MAIN;
	}
}
