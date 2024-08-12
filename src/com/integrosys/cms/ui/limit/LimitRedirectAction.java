/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/CollateralRedirectAction.java,v 1.7 2006/07/19 11:11:27 wltan Exp $
 */
package com.integrosys.cms.ui.limit;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: wltan $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/07/19 11:11:27 $ Tag: $Name: $
 */
public class LimitRedirectAction extends CommonAction {

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this, "entering getCommandChain...");
		ICommand objArray[] = null;
		objArray = new ICommand[1];

		objArray[0] = new RedirectLimitCommand();

		return (objArray);
	}

	/**
	 * This method is used to determine which the page to be displayed next
	 * using the event Result hashmap and exception hashmap. It returns the page
	 * object.
	 * 
	 * @param event is of type String
	 * @param resultMap is of type HashMap
	 * @param exceptionMap is of type HashMap
	 * @return IPage
	 */
	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		DefaultLogger.debug(this, "entering getNextPage...");
		Page aPage = new Page();

		if ((resultMap.get("trxSubType") != null)
				&& resultMap.get("trxSubType").equals(ICMSConstant.MANUAL_INPUT_TRX_TYPE)) {
			aPage.setPageReference(ICMSConstant.MANUAL_INPUT_TRX_TYPE);
		}
		else {
			aPage.setPageReference("NORMAL_" + event);
		}
		return aPage;
	}

	protected boolean isValidationRequired(String event) {
		return false;
	}

}
