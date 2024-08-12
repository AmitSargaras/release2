/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/warehouse/CheckerRejectCreateWarehouseOperation.java,v 1.3 2004/08/17 06:52:48 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.warehouse;

//app
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a checker to reject a checklist transaction
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2004/08/17 06:52:48 $ Tag: $Name: $
 */
public class CheckerRejectCreateWarehouseOperation extends CheckerRejectWarehouseOperation {

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_CREATE_REJECT_COMMODITY_MAIN;
	}

}