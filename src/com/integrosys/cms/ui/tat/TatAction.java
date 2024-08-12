/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/tat/TatAction.java,v 1.19 2004/09/03 07:28:25 pooja Exp $
 */

package com.integrosys.cms.ui.tat;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;

/**
 * @author $Author: pooja $<br>
 * @version $Revision: 1.19 $
 * @since $Date: 2004/09/03 07:28:25 $ Tag: $Name: $
 */
public class TatAction extends CommonAction {

	/**
	 * This method return an Array of Commad Objects responsible for a event
	 * 
	 * @ param event is of type String @ return Icommand Array
	 */

	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;

		// using TatCommand for unit testing
		if ("tat".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new TatCommand();
		}
		if ("redirectupdate".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RedirectUpdateCommand();
		}
		if ("redirectresubmit".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RedirectResubmitCommand();
		}
		if ("redirectclose".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RedirectCloseCommand();
		}
		if ("refresh".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new CreateTatCommand();
			objArray[1] = new CreateTatLimitProfileCommand();
		}

		if ("createTat".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new CreateTatCommand();
			objArray[1] = new CreateTatLimitProfileCommand();
		}
		if ("update".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new CreateTatLimitProfileCommand();
			objArray[1] = new UpdateTatCommand();
		}
		if ("updateBflInd".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new CreateTatLimitProfileCommand();
			objArray[1] = new UpdateBflIndTatCommand();
		}
		if ("submit".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new MakerResubmitTatLimitProfileCommand();
			objArray[1] = new UpdateTatCommand();
		}
		// using TatCommand for unit testing
		if ("chkTatList".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new TatCommand();
		}
		if ("chkTatApRej".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerApproveRejectTatCommand();

		}
		if ("approve".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new AppChkTatCommand();
		}
		if ("reject".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RejChkTatCommand();
		}
		// using TatCommand for unit testing
		if ("chkTatListSub".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new TatCommand();
		}
		if ("resubmit".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new MakerResubmitTatCommand();
			objArray[1] = new MakerResubmitTatLimitProfileCommand();
		}
		if ("refreshresubmit".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new MakerResubmitTatCommand();
			objArray[1] = new MakerResubmitTatLimitProfileCommand();
		}
		if ("close".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new MakerResubmitTatCommand();
			objArray[1] = new MakerResubmitTatLimitProfileCommand();
		}

		if ("cancel".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CancelMkTatCommand();
		}
		if ("prepare_form".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CreateTatLimitProfileCommand();
		}
		if ("prepare_form1".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CreateTatLimitProfileCommand();
		}
		if ("to_track".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new CreateTatCommand();
			objArray[1] = new CreateTatLimitProfileCommand();
		}
		if ("viewTat".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new CreateTatCommand();
			objArray[1] = new CreateTatLimitProfileCommand();
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
		return TatsFormValidator.validateInput((TatsForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals("update")) {
			result = true;
		}
		if (event.equals("updateBflInd")) {
			DefaultLogger.debug(this, "entered       updateBflInd");
			result = true;
		}
		if (event.equals("submit")) {
			result = true;
		}
		if (event.equals("refresh")) {
			result = true;
		}
		if (event.equals("refreshresubmit")) {
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if (("update".equals(event)) || ("refresh".equals(event))) {
			errorEvent = "prepare_form";
		}
		if (("updateBflInd".equals(event)) || ("refresh".equals(event))) {
			errorEvent = "prepare_form";
		}
		if (("submit".equals(event)) || ("refreshresubmit".equals(event))) {
			errorEvent = "prepare_form1";
		}
		return errorEvent;
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
		if ((resultMap.get("wip") != null) && (resultMap.get("wip")).equals("passive")) {
			DefaultLogger.debug(this, "Inside wip");
			aPage.setPageReference("wip");
			return aPage;
		}
		if ((resultMap.get("Error_BFL") != null) && (resultMap.get("Error_BFL")).equals("Error_BFL")) {
			DefaultLogger.debug(this, "Inside Error_BFL");
			aPage.setPageReference("after_createTat");
			return aPage;
		}
		if ((resultMap.get("Error_BFL") != null) && (resultMap.get("Error_BFL")).equals("Error_Res_BFL")) {
			DefaultLogger.debug(this, "Inside Error_BFL");
			aPage.setPageReference("after_resubmit");
			return aPage;
		}
		if ((resultMap.get("updateToDo") != null) && (resultMap.get("updateToDo")).equals("Tat")) {
			DefaultLogger.debug(this, "Inside Tat");
			aPage.setPageReference("update_tat");
			return aPage;
		}
		if ((resultMap.get("updateToDo") != null) && (resultMap.get("updateToDo")).equals("LimitProfile")) {
			DefaultLogger.debug(this, "Inside LimitProfile");
			aPage.setPageReference("update_limit_profile");
			return aPage;
		}
		if ((resultMap.get("resubmitToDo") != null) && (resultMap.get("resubmitToDo")).equals("Tat")) {
			DefaultLogger.debug(this, "Inside Tat");
			aPage.setPageReference("resubmit_tat");
			return aPage;
		}
		if ((resultMap.get("resubmitToDo") != null) && (resultMap.get("resubmitToDo")).equals("LimitProfile")) {
			DefaultLogger.debug(this, "Inside LimitProfile");
			aPage.setPageReference("resubmit_limit_profile");
			return aPage;
		}
		if ((resultMap.get("closeToDo") != null) && (resultMap.get("closeToDo")).equals("Tat")) {
			DefaultLogger.debug(this, "Inside Tat");
			aPage.setPageReference("close_tat");
			return aPage;
		}
		if ((resultMap.get("closeToDo") != null) && (resultMap.get("closeToDo")).equals("LimitProfile")) {
			DefaultLogger.debug(this, "Inside LimitProfile");
			aPage.setPageReference("close_limit_profile");
			return aPage;
		}
		aPage.setPageReference(getReference(event));
		return aPage;
	}

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event) {
		String forwardName = "submit_fail";
		if (("tat").equals(event)) {
			forwardName = "after_tat";
		}
		if (("createTat").equals(event) || "prepare_form".equals(event) || ("refresh").equals(event)) {
			forwardName = "after_createTat";
		}
		if (("update").equals(event)) {
			forwardName = "after_update";
		}
		if (("updateBflInd").equals(event)) {
			forwardName = "after_update";
		}
		if (("submit").equals(event)) {
			forwardName = "after_submit";
		}
		if (("chkTatList").equals(event)) {
			forwardName = "after_chkTatList";
		}
		if (("chkTatApRej").equals(event)) {
			forwardName = "after_chkTatApRej";
		}
		if (("approve").equals(event)) {
			forwardName = "after_approve";
		}
		if (("reject").equals(event)) {
			forwardName = "after_reject";
		}
		if (("chkTatListSub").equals(event)) {
			forwardName = "after_chkTatListSub";
		}
		if (("resubmit").equals(event) || "prepare_form1".equals(event) || "refreshresubmit".equals(event)) {
			forwardName = "after_resubmit";
		}
		if (("close").equals(event)) {
			forwardName = "after_close";
		}

		if (("cancel").equals(event)) {
			forwardName = "after_cancel";
		}
		if (("to_track").equals(event)) {
			forwardName = "after_to_track";
		}

		if (("viewTat").equals(event)) {
			forwardName = "after_viewTat";
		}

		return forwardName;
	}
}
