/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/documentlocation/DocumentLocationAction.java,v 1.8 2005/09/08 09:04:10 hshii Exp $
 */
package com.integrosys.cms.ui.documentlocation;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2005/09/08 09:04:10 $ Tag: $Name: $
 */

public class DocumentLocationAction extends CommonAction {

	public static final String EVENT_PREPARE_UPDATE_DOC_LOCATION = "prepare_update";

	public static final String EVENT_EDIT_REJECTED_DOC_LOCATION = "edit_rejected";

	public static final String EVENT_PREPARE_CLOSE_DOC_LOCATION = "prepare_close";

	public static final String EVENT_CLOSE_DOC_LOCATION = "close";

	public static final String EVENT_TRACK_DOC_LOCATION = "to_track";

	public static final String EVENT_REFRESH_DOC_LOCATION = "refresh";

	public static final String EVENT_PREPARE_FORM = "prepare_form";

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (event.equals(EVENT_LIST)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareDocLocationListCommand();
		}
		else if (event.equals(EVENT_PREPARE_UPDATE_DOC_LOCATION) || event.equals(EVENT_EDIT_REJECTED_DOC_LOCATION)) {
			objArray = new ICommand[3];
			objArray[0] = new ReadDocLocationCommand();
			objArray[1] = new PrepareDocLocationCommand();
			objArray[2] = new FrameFlagSetterCommand();
		}
		else if (event.equals(EVENT_CREATE)) {
			objArray = new ICommand[2];
			objArray[0] = new CreateDocLocationCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if (event.equals(EVENT_UPDATE)) {
			objArray = new ICommand[2];
			objArray[0] = new UpdateDocLocationCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if (event.equals(EVENT_PREPARE_CLOSE_DOC_LOCATION) || event.equals(EVENT_TRACK_DOC_LOCATION)
				|| event.equals(EVENT_PROCESS)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadDocLocationCommand();
		}
		else if (event.equals(EVENT_CLOSE_DOC_LOCATION)) {
			objArray = new ICommand[1];
			objArray[0] = new CloseDocLocationCommand();
		}
		else if (event.equals(EVENT_APPROVE)) {
			objArray = new ICommand[1];
			objArray[0] = new ApproveDocLocationCommand();
		}
		else if (event.equals(EVENT_REJECT)) {
			objArray = new ICommand[1];
			objArray[0] = new RejectDocLocationCommand();
		}
		else if (event.equals(EVENT_REFRESH_DOC_LOCATION)) {
			objArray = new ICommand[3];
			objArray[0] = new RefreshDocLocationCommand();
			objArray[1] = new PrepareDocLocationCommand();
			objArray[2] = new FrameFlagSetterCommand();
		}
		else if (event.equals(EVENT_PREPARE_FORM)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareDocLocationCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if (EVENT_VIEW.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadDocLocationCommand();
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
		return DocumentLocationValidator.validateInput((DocumentLocationForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals(EVENT_CREATE) || event.equals(EVENT_UPDATE)) {
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
		if ((resultMap.get("doc_loc") != null) && (resultMap.get("doc_loc")).equals("doc_loc_not_required")) {
			aPage.setPageReference("update_not_required");
			return aPage;
		}
		if ((resultMap.get("wip") != null) && resultMap.get("wip").equals("wip")) {
			aPage.setPageReference("wip");
			return aPage;
		}
		if ((resultMap.get("wip_checklist") != null) && resultMap.get("wip_checklist").equals("wip_checklist")) {
			aPage.setPageReference("wip_checklist");
			return aPage;
		}
		if ((resultMap.get("frame") != null) && resultMap.get("frame").equals("WF")) {
			aPage.setPageReference(getReference(event) + "_WF");
			return aPage;
		}
		if (event.equals(EVENT_TRACK_DOC_LOCATION)) {
			aPage.setPageReference("to_track_" + resultMap.get("strUser"));
			return aPage;
		}
		aPage.setPageReference(getReference(event));
		return aPage;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if (event.equals(EVENT_CREATE) || event.equals(EVENT_UPDATE)) {
			errorEvent = EVENT_PREPARE_FORM;
		}
		return errorEvent;
	}

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event) {
		if (event.equals(EVENT_LIST)) {
			return "after_list";
		}
		if (event.equals(EVENT_PREPARE_UPDATE_DOC_LOCATION) || event.equals(EVENT_EDIT_REJECTED_DOC_LOCATION)
				|| event.equals(EVENT_REFRESH_DOC_LOCATION) || event.equals(EVENT_PREPARE_FORM)) {
			return "after_prepare_update";
		}
		if (event.equals(EVENT_UPDATE) || event.equals(EVENT_CREATE)) {
			return "after_submit";
		}
		if (event.equals(EVENT_CLOSE_DOC_LOCATION)) {
			return "after_close";
		}
		if (event.equals(EVENT_PREPARE_CLOSE_DOC_LOCATION)) {
			return "after_prepare_close";
		}
		if (event.equals(EVENT_PROCESS)) {
			return "chk_view";
		}
		return event;
	}
}
