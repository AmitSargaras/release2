/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/profile/CheckerRejectCreateProfileOperation.java,v 1.2 2004/06/04 04:54:03 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.profile;

//app
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a checker to reject a checklist transaction
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 04:54:03 $ Tag: $Name: $
 */
public class CheckerRejectCreateProfileOperation extends CheckerRejectProfileOperation {

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_CREATE_REJECT_COMMODITY_MAIN;
	}

}