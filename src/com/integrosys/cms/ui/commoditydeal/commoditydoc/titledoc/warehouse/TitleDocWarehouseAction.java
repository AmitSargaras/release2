/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/commoditydoc/titledoc/warehouse/TitleDocWarehouseAction.java,v 1.5 2004/07/22 13:54:04 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.commoditydoc.titledoc.warehouse;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.commoditydeal.commoditydoc.titledoc.TitleDocAction;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2004/07/22 13:54:04 $ Tag: $Name: $
 */

public class TitleDocWarehouseAction extends TitleDocAction {
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (EVENT_CREATE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CreateTitleDocWarehouseCommand();
		}
		else if (EVENT_PRE_ADD_WAREHOUSE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadTitleDocWarehouseCommand();
			objArray[1] = new PrepareTitleDocWarehouseCommand();
		}
		else if (EVENT_PRE_EDIT_WAREHOUSE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadTitleDocWarehouseCommand();
			objArray[1] = new PrepareTitleDocWarehouseCommand();
		}
		else if (EVENT_NOOP.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareTitleDocWarehouseCommand();
		}
		else if (EVENT_REFRESH.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new RefreshTitleDocWarehouseCommand();
			objArray[1] = new PrepareTitleDocWarehouseCommand();
		}
		else if (EVENT_READ.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadTitleDocWarehouseCommand();
		}
		else if (EVENT_UPDATE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new UpdateTitleDocWarehouseCommand();
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
		return TitleDocWarehouseValidator.validateInput((TitleDocWarehouseForm) aForm, locale);
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
			errorEvent = EVENT_NOOP;
		}

		return errorEvent;
	}

	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		if (event.equals(EVENT_FORWARD)) {
			return super.getNextPage(event, resultMap, exceptionMap);
		}
		if (EVENT_READ.equals(event)) {
			aPage.setPageReference(getReadReference((String) resultMap.get("from_event"), (String) resultMap
					.get("previous_event")));
		}
		else {
			aPage.setPageReference(getReference(event));
		}
		return aPage;
	}

	private String getReference(String event) {
		if (event.equals(EVENT_UPDATE) || event.equals(EVENT_CREATE) || event.equals(EVENT_CANCEL)) {
			return EVENT_EDIT_RETURN;
		}
		if (event.equals(EVENT_PRE_ADD_WAREHOUSE) || event.equals(EVENT_PRE_EDIT_WAREHOUSE) || event.equals(EVENT_NOOP)
				|| event.equals(EVENT_REFRESH)) {
			return EVENT_PREPARE;
		}
		return event;
	}

	private String getReadReference(String from_event, String previous_event) {
		if (from_event.equals(EVENT_READ) && (previous_event != null) && previous_event.equals(EVENT_USER_PROCESS)) {
			return EVENT_USER_PROCESS;
		}
		if (from_event.equals(EVENT_PREPARE_CLOSE) || from_event.equals(EVENT_READ) || from_event.equals(EVENT_TRACK)
				|| from_event.equals(EVENT_PREPARE_CLOSE_DEAL)) {
			return "view_titledoc_warehouse";
		}
		if (from_event.equals(EVENT_PROCESS)) {
			return "process_titledoc_warehouse";
		}
		return from_event;
	}

}
