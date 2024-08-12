/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/commodityprofile/list/CommProfileListAction.java,v 1.7 2006/05/17 07:10:31 hmbao Exp $
 */
package com.integrosys.cms.ui.commodityglobal.commodityprofile.list;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.commodityglobal.commodityprofile.CommProfileAction;

/**
 * Description
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/05/17 07:10:31 $ Tag: $Name: $
 */

public class CommProfileListAction extends CommProfileAction {
	public final static String EVENT_PRE_CLOSE = "prepare_close";

	public final static String EVENT_TO_TRACK = "to_track";

	public final static String EVENT_PROCESS_UPDATE = "process_update";

	public final static String EVENT_CLOSE = "close";

	public final static String EVENT_UPDATE_RETURN = "update_return";

	public final static String EVENT_PROCESS_RETURN = "process_return";

	public final static String EVENT_TRACK_RETURN = "track_return";

	public final static String EVENT_CLOSE_RETURN = "close_return";

	public final static String EVENT_READ_RETURN = "read_return";

	public final static String EVENT_PRE_NOOP = "prepare_noop";

	public final static String EVENT_PRE_SEARCH = "prepare_search";

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (event.equals(EVENT_PREPARE) || event.equals(EVENT_PRE_CLOSE) || event.equals(EVENT_TO_TRACK)
				|| event.equals(EVENT_PROCESS) || event.equals(EVENT_PROCESS_UPDATE) || event.equals(EVENT_READ)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadCommProfileListCommand();
			objArray[1] = new PrepareCommProfileListCommand();
		}
		else if (event.equals(EVENT_DELETE)) {
			objArray = new ICommand[2];
			objArray[0] = new DeleteCommProfileListCommand();
			objArray[1] = new PrepareCommProfileListCommand();
		}
		else if (event.equals(EVENT_SUBMIT)) {
			objArray = new ICommand[1];
			objArray[0] = new SubmitCommProfileListCommand();
		}
		else if (event.equals(EVENT_UPDATE)) {
			objArray = new ICommand[1];
			objArray[0] = new UpdateCommProfileListCommand();
		}
		else if (event.equals(EVENT_UPDATE_RETURN)) {
			objArray = new ICommand[2];
			objArray[0] = new ReturnCommProfileListCommand();
			objArray[1] = new PrepareCommProfileListCommand();
		}
		else if (event.equals(EVENT_TRACK_RETURN) || event.equals(EVENT_READ_RETURN)
				|| event.equals(EVENT_PROCESS_RETURN) || event.equals(EVENT_CLOSE_RETURN)) {
			objArray = new ICommand[1];
			objArray[0] = new ReturnCommProfileListCommand();
		}
		else if (event.equals(EVENT_CLOSE)) {
			objArray = new ICommand[1];
			objArray[0] = new CloseCommProfileListCommand();
		}
		else if (event.equals(EVENT_APPROVE)) {
			objArray = new ICommand[1];
			objArray[0] = new ApproveCommProfileListCommand();
		}
		else if (event.equals(EVENT_REJECT)) {
			objArray = new ICommand[1];
			objArray[0] = new RejectCommProfileListCommand();
		}
		else if (event.equals(EVENT_PRE_NOOP)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareCommProfileListCommand();
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
		return CommProfileListValidator.validateInput((CommProfileListForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals(EVENT_PREPARE) || event.equals(EVENT_APPROVE) || event.equals(EVENT_SUBMIT)
				|| event.equals(EVENT_REJECT) || event.equals(EVENT_UPDATE)) {
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
		if ((resultMap.get("no_record") != null) && ((String) resultMap.get("no_record")).equals("no_record")) {
			aPage.setPageReference("no_record");
			return aPage;
		}
		aPage.setPageReference(getReference(event));
		return aPage;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if (event.equals(EVENT_SUBMIT) || event.equals(EVENT_UPDATE)) {
			errorEvent = EVENT_PRE_NOOP;
		}
		else if (event.equals(EVENT_APPROVE) || event.equals(EVENT_REJECT)) {
			errorEvent = EVENT_PROCESS_RETURN;
		}
		return errorEvent;
	}

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event) {
		if (event.equals(EVENT_UPDATE_RETURN) || event.equals(EVENT_PROCESS_UPDATE) || event.equals(EVENT_DELETE)
				|| event.equals(EVENT_PRE_NOOP)) {
			return EVENT_PREPARE;
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
