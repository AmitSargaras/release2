/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/warehouse/list/WarehouseListAction.java,v 1.7 2004/08/30 12:40:01 hshii Exp $
 */
package com.integrosys.cms.ui.commodityglobal.warehouse.list;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.commodityglobal.warehouse.WarehouseAction;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2004/08/30 12:40:01 $ Tag: $Name: $
 */

public class WarehouseListAction extends WarehouseAction {
	public final static String EVENT_SELECT = "select";

	public final static String EVENT_PRE_CLOSE = "prepare_close";

	public final static String EVENT_RETURN = "return";

	public final static String EVENT_TO_TRACK = "to_track";

	public final static String EVENT_PROCESS_UPDATE = "process_update";

	public final static String EVENT_CLOSE = "close";

	public final static String EVENT_NOOP = "noop";

	public final static String EVENT_PROCESS_NOOP = "process_noop";

	public final static String EVENT_PROCESS_RETURN = "process_return";

	public final static String EVENT_TRACK_RETURN = "track_return";

	public final static String EVENT_CLOSE_RETURN = "close_return";

	public final static String EVENT_READ_RETURN = "read_return";

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (event.equals(EVENT_PREPARE) || event.equals(EVENT_PROCESS) || event.equals(EVENT_PROCESS_UPDATE)
				|| event.equals(EVENT_TO_TRACK) || event.equals(EVENT_PRE_CLOSE) || event.equals(EVENT_READ)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadWarehouseListCommand();
			objArray[1] = new PrepareWarehouseListCommand();
		}
		else if (event.equals(EVENT_DELETE)) {
			objArray = new ICommand[1];
			objArray[0] = new DeleteWarehouseListCommand();
		}
		else if (event.equals(EVENT_SUBMIT)) {
			objArray = new ICommand[1];
			objArray[0] = new SubmitWarehouseListCommand();
		}
		else if (EVENT_UPDATE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new UpdateWarehouseListCommand();
		}
		else if (event.equals(EVENT_SELECT)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareWarehouseCommand();
		}
		else if (event.equals(EVENT_APPROVE)) {
			objArray = new ICommand[1];
			objArray[0] = new ApproveWarehouseListCommand();
		}
		else if (event.equals(EVENT_REJECT)) {
			objArray = new ICommand[1];
			objArray[0] = new RejectWarehouseListCommand();
		}
		else if (event.equals(EVENT_CLOSE)) {
			objArray = new ICommand[1];
			objArray[0] = new CloseWarehouseListCommand();
		}
		else if (event.equals(EVENT_NOOP)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareWarehouseListCommand();
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
		return WarehouseListValidator.validateInput((WarehouseListForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals(EVENT_PREPARE) || event.equals(EVENT_APPROVE) || event.equals(EVENT_SUBMIT)
				|| event.equals(EVENT_REJECT) || event.equals(EVENT_SELECT) || EVENT_UPDATE.equals(event)) {
			result = true;
		}
		return result;
	}

	/**
	 * This method is used to determine which the page to be displayed next
	 * using the event Result hashmap and exception hashmap.It returns the page
	 * object .
	 * 
	 * @param event is of type String
	 * @param resultMap is of type HashMap
	 * @param exceptionMap is of type HashMap
	 * @return IPage
	 */
	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		if ((resultMap.get("wip") != null) && ((String) resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference("wip");
			return aPage;
		}

		aPage.setPageReference(getReference(event));
		return aPage;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if (event.equals(EVENT_PREPARE)) {
			errorEvent = EVENT_SELECT;
		}
		else if (event.equals(EVENT_SUBMIT) || EVENT_UPDATE.equals(event)) {
			errorEvent = EVENT_NOOP;
		}
		else if (event.equals(EVENT_APPROVE) || event.equals(EVENT_REJECT)) {
			errorEvent = EVENT_PROCESS_NOOP;
		}

		return errorEvent;
	}

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event) {
		if (event.equals(EVENT_RETURN) || event.equals(EVENT_DELETE) || event.equals(EVENT_PROCESS_UPDATE)
				|| event.equals(EVENT_NOOP)) {
			return EVENT_PREPARE;
		}
		if (event.equals(EVENT_PROCESS_NOOP)) {
			return EVENT_PROCESS;
		}
		if (event.equals(EVENT_TRACK_RETURN)) {
			return EVENT_TO_TRACK;
		}
		if (event.equals(EVENT_CLOSE_RETURN)) {
			return EVENT_PRE_CLOSE;
		}
		if (event.equals(EVENT_PROCESS_RETURN)) {
			return EVENT_PROCESS;
		}
		if (event.equals(EVENT_READ_RETURN)) {
			return EVENT_READ;
		}
		return event;
	}
}
