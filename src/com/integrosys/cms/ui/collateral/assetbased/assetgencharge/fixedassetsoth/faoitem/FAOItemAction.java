/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/fixedassetsoth/faoitem/FAOItemAction.java,v 1.3 2005/08/26 10:11:43 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.fixedassetsoth.faoitem;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.fixedassetsoth.FAOAction;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/08/26 10:11:43 $ Tag: $Name: $
 */

public class FAOItemAction extends FAOAction {

	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (EVENT_CREATE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CreateFAOItemCommand();
		}
		else if (EVENT_PREPARE_UPDATE_SUB.equals(event) || EVENT_PREPARE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadFAOItemCommand();
			objArray[1] = new PrepareFAOItemCommand();
		}
		else if (EVENT_READ.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadFAOItemCommand();
		}
		else if (EVENT_UPDATE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new UpdateFAOItemCommand();
		}
		else if (EVENT_READ_RETURN.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReturnFAOItemCommand();
		}
		else if (EVENT_PREPARE_FORM.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareFAOItemCommand();
		}
		else if (EVENT_REFRESH.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new RefreshFAOItemCommand();
			objArray[1] = new PrepareFAOItemCommand();
		}
		else {
			objArray = super.getCommandChain(event);
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
		return FAOItemValidator.validateInput((FAOItemForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (EVENT_CREATE.equals(event) || EVENT_UPDATE.equals(event) || EVENT_REFRESH.equals(event)) {
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = event;
		if (EVENT_CREATE.equals(event) || EVENT_UPDATE.equals(event) || EVENT_REFRESH.equals(event)) {
			errorEvent = EVENT_PREPARE_FORM;
		}

		return errorEvent;
	}

	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		if (event.equals(EVENT_FORWARD)) {
			return super.getNextPage(event, resultMap, exceptionMap);
		}
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
		if (event.equals(EVENT_UPDATE) || event.equals(EVENT_CREATE) || event.equals(EVENT_CANCEL)) {
			return EVENT_UPDATE_RETURN;
		}
		if (event.equals(EVENT_PREPARE_UPDATE_SUB) || event.equals(EVENT_PREPARE_FORM) || event.equals(EVENT_REFRESH)) {
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
		if (from_event.equals(EVENT_PREPARE_UPDATE) || from_event.equals(EVENT_PROCESS_UPDATE)) {
			return EVENT_UPDATE_RETURN;
		}

		return from_event;
	}

	private String getReadReference(String from_event) {
		if (from_event.equals(EVENT_PREPARE_CLOSE) || from_event.equals(EVENT_READ) || from_event.equals(EVENT_TRACK)
				|| from_event.equals(EVENT_PREPARE_UPDATE) || from_event.equals(EVENT_PROCESS_UPDATE)) {
			return "view_FAOItem";
		}
		if (from_event.equals(EVENT_PROCESS)) {
			return "process_FAOItem";
		}
		return from_event;
	}
}
