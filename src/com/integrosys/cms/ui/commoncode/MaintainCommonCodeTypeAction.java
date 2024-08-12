/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/systemparameters/MaintainSystemParametersAction.java,v 1.11 2005/09/09 11:56:07 hshii Exp $
 */
package com.integrosys.cms.ui.commoncode;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.systemparameters.CheckerReadSystemParametersCmd;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 * @since $Id$
 */
public class MaintainCommonCodeTypeAction extends CommonAction {

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this, "Event is " + event);
		ICommand objArray[] = null;
		if ("maker_list_common_code_type".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerReadCommonCodeTypeCmd();
		}
		else if ("maker_edit_common_code_type_read".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerEditPrepareCommonCodeTypeCmd();
		}
		else if ("create_common_code_type".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerCreateCommonCodeTypeCmd();
		}
		else if ("maker_edit_common_code_type".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerEditCommonCodeTypeCmd();
		}
		else if ("process".equals(event) || "edit_staging_doc_item".equals(event) || "close".equals(event)
				|| "to_track".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadStagingCommonCodeTypeCmd();
		}
		else if ("checker_approve_edit".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerApproveEditCommonCodeTypeCmd();
		}
		else if ("checker_reject_edit".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerRejectEditCommonCodeTypeCmd();
		}
		else if ("maker_edit_common_code_type_read_rejected".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerReadRejectedCommonCodeTypeCmd();
		}
		else if ("maker_edit_reject_edit".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerEditRejectedCommonCodeTypeCmd();
		}
		else if ("maker_close_common_code_type".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadStagingCommonCodeTypeCmd();
		}
		else if ("maker_cncl_reject_edit".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerCloseEditCommonTypeCmd();
		}
		else if (EVENT_VIEW.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerReadCommonCodeTypeCmd();
		}
		else if ("to_track".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerReadSystemParametersCmd();
		}
		return (objArray);
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
		DefaultLogger.debug(this, "Inside validate Input child class");
		return MaintainCommonCodeTypeValidator.validateInput((MaintainCommonCodeTypeForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals("create_common_code_type") || event.equals("maker_edit_common_code_type")
				|| event.equals("maker_edit_reject_add") || event.equals("maker_edit_reject_edit")) {
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
		aPage.setPageReference(getReference(event));
		return aPage;

	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if ("create_common_code_type".equals(event)) {
			errorEvent = "prepare_create_type_item";
		}
		else if ("maker_edit_common_code_type".equals(event)) {
			errorEvent = "maker_edit_common_code_type_read";
		}
		else if ("maker_edit_reject_edit".equals(event)) {
			errorEvent = "maker_edit_common_code_type_read_rejected";
		}
		return errorEvent;
	}

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event) {
		String forwardName = null;
		if ("maker_list_common_code_type".equals(event)) {
			forwardName = "maker_list_type_page";
		}
		else if ("maker_edit_common_code_type_read".equals(event)
				|| "maker_add_edit_common_code_type_error".equals(event)
				|| "maker_edit_common_code_type_read_rejected".equals(event)) {
			forwardName = "maker_add_edit_type_page";
		}
		else if ("prepare_create_type_item".equals(event)) {
			forwardName = "after_prepare_create_common_code_type";
		}
		else if ("create_common_code_type".equals(event) || "update_common_code_type".equals(event)
				|| "update_staging_common_code_type".equals(event)) {
			forwardName = "after_create_common_code_type";
		}
		else if ("process".equals(event)) {
			forwardName = "after_process";
		}
		else if ("maker_edit_common_code_type".equals(event) || "maker_edit_reject_edit".equals(event)) {
			forwardName = "type_maker_edit_successful";
		}
		else if ("work_in_process".equals(event)) {
			forwardName = "work_in_process_page";
		}
		else if ("checker_edit_read".equals(event)) {
			forwardName = "checker_type_page";
		}
		else if ("checker_approve_edit".equals(event)) {
			forwardName = "common_approve_page";
		}
		else if ("checker_reject_edit".equals(event)) {
			forwardName = "common_reject_page";
		}
		else if ("maker_cncl_reject_edit".equals(event)) {
			forwardName = "common_close_page";
		}
		else if ("maker_close_common_code_type".equals(event)) {
			forwardName = "maker_close_type_page";
		}
		else if ("maker_add_edit_common_code_type_error".equals(event)) {
			forwardName = "maker_add_edit_type_page";
		}
		else if ("to_track".equals(event)) {
			forwardName = "after_to_track";
		}
		else if (EVENT_VIEW.equals(event)) {
			forwardName = EVENT_VIEW;
		}
		return forwardName;
	}
}