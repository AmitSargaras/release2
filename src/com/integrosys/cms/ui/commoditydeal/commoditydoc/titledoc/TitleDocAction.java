/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/commoditydoc/titledoc/TitleDocAction.java,v 1.10 2004/07/06 10:31:18 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.commoditydoc.titledoc;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.commoditydeal.commoditydoc.CommDocAction;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2004/07/06 10:31:18 $ Tag: $Name: $
 */

public class TitleDocAction extends CommDocAction {
	public final static String EVENT_PRE_ADD_WAREHOUSE = "prepare_add_warehouse";

	public final static String EVENT_PRE_EDIT_WAREHOUSE = "prepare_edit_warehouse";

	public final static String EVENT_VIEW_RETURN = "view_return";

	public static final String EVENT_RETURN = "return";

	public static final String EVENT_EDIT_RETURN = "edit_return";

	public static final String EVENT_NOOP = "noop";

	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (EVENT_CREATE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CreateTitleDocCommand();
		}
		else if (EVENT_NOOP.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareTitleDocCommand();
		}
		else if (EVENT_PREPARE_UPDATE_SUB.equals(event) || EVENT_PREPARE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadTitleDocCommand();
			objArray[1] = new PrepareTitleDocCommand();
		}
		else if (EVENT_READ.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadTitleDocCommand();
		}
		else if (EVENT_UPDATE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new UpdateTitleDocCommand();
			objArray[1] = new ListTitleDocCommand();
		}
		else if (EVENT_PRE_ADD_WAREHOUSE.equals(event) || EVENT_PRE_EDIT_WAREHOUSE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ProcessingTitleDocCommand();
		}
		else if (EVENT_RETURN.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReturnTitleDocCommand();
		}
		else if (EVENT_EDIT_RETURN.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReturnTitleDocCommand();
			objArray[1] = new PrepareTitleDocCommand();
		}
		else if (EVENT_LIST_TITLE_DOC.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ListTitleDocCommand();
		}
		else if (EVENT_VIEW_RETURN.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ListTitleDocCommand();
			objArray[1] = new ViewReturnTitleDocCommand();
		}
		else if (EVENT_REFRESH.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new RefreshTitleDocCommand();
			objArray[1] = new PrepareTitleDocCommand();
		}
		else if (EVENT_REMOVE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new DeleteWarehouseReceiptCommand();
			objArray[1] = new PrepareTitleDocCommand();
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
		return TitleDocValidator.validateInput((TitleDocForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (EVENT_CREATE.equals(event) || EVENT_UPDATE.equals(event)) {
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = event;
		if (EVENT_CREATE.equals(event) || EVENT_UPDATE.equals(event)) {
			errorEvent = EVENT_NOOP;
		}

		return errorEvent;
	}

	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		if (event.equals(EVENT_FORWARD)) {
			return super.getNextPage(event, resultMap, exceptionMap);
		}
		if (EVENT_VIEW_RETURN.equals(event)) {
			aPage.setPageReference(getReturnReference((String) resultMap.get("from_event"), (String) resultMap
					.get("return_page")));
		}
		else if (EVENT_READ.equals(event) || EVENT_RETURN.equals(event)) {
			aPage.setPageReference(getReadReference((String) resultMap.get("from_event"), (String) resultMap
					.get("previous_event")));
		}
		else {
			aPage.setPageReference(getReference(event, (String) resultMap.get("return_page")));
		}
		return aPage;
	}

	private String getReference(String event, String return_event) {
		if (event.equals(EVENT_UPDATE) || event.equals(EVENT_CREATE) || event.equals(EVENT_CANCEL)) {
			if ((return_event != null) && return_event.equals(EVENT_LIST_TITLE_DOC)) {
				return EVENT_LIST_TITLE_DOC;
			}
			else {
				return EVENT_UPDATE_RETURN;
			}
		}
		if (event.equals(EVENT_PREPARE_UPDATE_SUB) || event.equals(EVENT_REFRESH) || event.equals(EVENT_EDIT_RETURN)
				|| event.equals(EVENT_REMOVE) || event.equals(EVENT_NOOP)) {
			return EVENT_PREPARE;
		}
		return event;
	}

	private String getReturnReference(String from_event, String return_event) {
		if ((return_event != null) && return_event.equals(EVENT_LIST_TITLE_DOC)) {
			return EVENT_LIST_TITLE_DOC;
		}
		if (from_event.equals(EVENT_PREPARE_CLOSE) || from_event.equals(EVENT_PREPARE_CLOSE_DEAL)) {
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
		if (from_event.equals(EVENT_PREPARE_UPDATE) || from_event.equals(EVENT_PROCESS_UPDATE)
				|| from_event.equals(EVENT_PREPARE_ADD_DEAL)) {
			return EVENT_UPDATE_RETURN;
		}
		return from_event;
	}

	private String getReadReference(String from_event, String previous_event) {
		if (from_event.equals(EVENT_READ) && (previous_event != null) && previous_event.equals(EVENT_USER_PROCESS)) {
			return EVENT_USER_PROCESS;
		}
		if (from_event.equals(EVENT_PREPARE_CLOSE) || from_event.equals(EVENT_READ) || from_event.equals(EVENT_TRACK)
				|| from_event.equals(EVENT_PREPARE_CLOSE_DEAL) || from_event.equals(EVENT_PREPARE_ADD_DEAL)
				|| from_event.equals(EVENT_PREPARE_UPDATE) || from_event.equals(EVENT_PROCESS_UPDATE)) {
			return "view_title_doc";
		}
		if (from_event.equals(EVENT_PROCESS)) {
			return "process_title_doc";
		}
		return from_event;
	}

}
