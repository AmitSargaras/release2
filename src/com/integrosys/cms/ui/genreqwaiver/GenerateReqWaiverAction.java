/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/genreqwaiver/GenerateReqWaiverAction.java,v 1.3 2003/09/17 10:35:20 elango Exp $
 */
package com.integrosys.cms.ui.genreqwaiver;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.app.common.constant.ICMSErrorCodes;
import com.integrosys.cms.ui.checklist.ccreceipt.FrameFlagSetterCommand;

/**
 * @author $Author: elango $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/09/17 10:35:20 $ Tag: $Name: $
 */
public class GenerateReqWaiverAction extends CommonAction {

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if ("generate_req_waiver".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new GenerateReqWaiverCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("submit".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new SubmitGenerateReqWaiverCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("approve".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ApproveGenerateReqWaiverCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("reject".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new RejectGenerateReqWaiverCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("process".equals(event) || "edit_staging".equals(event) || "close_item".equals(event)
				|| "to_track".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadStagingReqWaiverCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("close".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new CloseGenerateReqWaiverCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("prepare_form".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new FrameFlagSetterCommand();
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
		// return
		// SecurityReceiptFormValidator.validateInput((SecurityReceiptForm
		// )aForm,locale);
		return GenerateReqWaiverFormValidator.validateInput((GenerateReqWaiverForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if ("submit".equals(event)) {
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
		if (resultMap.get("error") != null) {
			String errorCode = (String) resultMap.get("error");
			if (ICMSErrorCodes.WAIVER_NOT_REQUIRED.equals(errorCode)) {
				aPage.setPageReference("info");
				return aPage;
			}
			if (ICMSErrorCodes.WAIVER_PENDING_TRX_EXIST.equals(errorCode)) {
				aPage.setPageReference("wip");
				return aPage;
			}
		}
		if ((resultMap.get("frame") != null) && ("false").equals((String) resultMap.get("frame"))) {
			String forwardName = frameCheck(getReference(event));
			aPage.setPageReference(forwardName);
			return aPage;
		}
		aPage.setPageReference(getReference(event));
		return aPage;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if ("total".equals(event) || "submit".equals(event)) {
			errorEvent = "prepare_form";
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
		if ("generate_req_waiver".equals(event) || "prepare_form".equals(event)) {
			forwardName = "after_generate_req_waiver";
		}
		if ("submit".equals(event)) {
			forwardName = "after_submit";
		}
		if ("process".equals(event)) {
			forwardName = "after_process";
		}
		if ("edit_staging".equals(event)) {
			forwardName = "after_edit_staging";
		}
		if ("chk_view_recurrent".equals(event)) {
			forwardName = "after_chk_view_recurrent";
		}
		if ("approve".equals(event)) {
			forwardName = "after_approve";
		}
		if ("reject".equals(event)) {
			forwardName = "after_reject";
		}
		if ("close_item".equals(event) || "to_track".equals(event)) {
			forwardName = "after_close_item";
		}
		if ("close".equals(event)) {
			forwardName = "after_close";
		}
		if ("edit_covenant".equals(event)) {
			forwardName = "after_edit_covenant";
		}
		if ("edit_recurrent".equals(event)) {
			forwardName = "after_edit_recurrent";
		}
		if ("print_doc".equals(event)) {
			forwardName = "after_print_doc";
		}
		return forwardName;
	}

	private String frameCheck(String forwardName) {
		return forwardName + "_WF";
	}
}