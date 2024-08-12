/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/security/SecurityAction.java,v 1.4 2006/10/10 07:59:24 jzhan Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.security;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.collateral.commodity.CommodityMainAction;
import com.integrosys.cms.ui.collateral.commodity.secapportion.DeleteApportionmentCommand;
import com.integrosys.cms.ui.collateral.commodity.secapportion.ReadReturnSecCommand;

/**
 * Description
 * 
 * @author $Author: jzhan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2006/10/10 07:59:24 $ Tag: $Name: $
 */

public class SecurityAction extends CommodityMainAction {
	public ICommand[] getCommandChain(String event) {

		ICommand objArray[] = null;
		if (EVENT_PREPARE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareSecurityCommand();
		}
		else if (EVENT_PREPARE_UPDATE_SUB.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareSecurityCommand();
			objArray[1] = new ReadSecurityCommand();
		}
		else if (EVENT_READ.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadSecurityCommand();
		}
		else if (EVENT_UPDATE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new UpdateSecurityCommand();
		}
		else if (EVENT_READ_RETURN.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReturnSecurityCommand();
		}
		else if (EVENT_DELETE_ITEM.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareSecurityCommand();
			objArray[1] = new DeleteApportionmentCommand();
		}
		else if ("prepare_update_sub_apportion".equals(event) || "prepare_apportion".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SaveCurCollateralCommand();
		}
		else if ("process_return_apportion".equals(event) || "read_return_apportion".equals(event)
				|| "close_return_apportion".equals(event) || "track_return_apportion".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadSecurityCommand();
		}
		else if ("update_return_apportion".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareSecurityCommand();
			objArray[1] = new ReadReturnSecCommand();

		}

		return objArray;
	}

	/**
	 * This method is called only for create and Update command to validate the
	 * form and return the ActionErrors object.
	 * 
	 * @param aForm is of type ActionForm
	 * @param locale of type Locale
	 * @return ActionErrors
	 */
	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		DefaultLogger.debug(this, "VALIDATION REQUIRED...");
		return SecurityValidator.validateInput((SecurityForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (EVENT_UPDATE.equals(event)) {
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = event;
		if (EVENT_UPDATE.equals(event)) {
			errorEvent = EVENT_PREPARE;
		}

		return errorEvent;
	}

	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		if (EVENT_READ_RETURN.equals(event)) {
			aPage.setPageReference(getReturnReference((String) resultMap.get("from_page")));
		}
		else if (EVENT_READ.equals(event)) {
			aPage.setPageReference(getReadReference((String) resultMap.get("from_page")));
		}
		else {
			aPage.setPageReference(getReference(event));
		}
		return aPage;
	}

	private String getReference(String event) {
		if (event.equals(EVENT_UPDATE) || event.equals(EVENT_CANCEL)) {
			return EVENT_UPDATE_RETURN;
		}
		if (event.equals(EVENT_PREPARE_UPDATE_SUB)) {
			return EVENT_PREPARE;
		}
		return event;
	}

	private String getReturnReference(String from_event) {
		if (from_event.equals(EVENT_PREPARE_CLOSE)) {
			return EVENT_CLOSE_RETURN;
		}
		if (from_event.equals(EVENT_TRACK)) {
			return EVENT_TRACK_RETURN;
		}
		if (from_event.equals(EVENT_PROCESS)) {
			return EVENT_PROCESS_RETURN;
		}
		if (from_event.equals(EVENT_READ)) {
			return EVENT_READ_RETURN;
		}
		return from_event;
	}

	private String getReadReference(String from_event) {
		if (from_event.equals(EVENT_PREPARE_CLOSE) || from_event.equals(EVENT_READ)
				|| from_event.equals(CommodityMainAction.EVENT_TRACK)) {
			return "view_security";
		}
		if (from_event.equals(EVENT_PROCESS)) {
			return "process_security";
		}
		return from_event;
	}
}
