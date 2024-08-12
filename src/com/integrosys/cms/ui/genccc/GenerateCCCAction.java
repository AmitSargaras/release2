/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/genccc/GenerateCCCAction.java,v 1.10 2005/12/12 10:03:12 hmbao Exp $
 */
package com.integrosys.cms.ui.genccc;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.checklist.ccreceipt.FrameFlagSetterCommand;
import com.integrosys.cms.ui.common.ViewOuterLimitBCACommand;

/**
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2005/12/12 10:03:12 $ Tag: $Name: $
 */
public class GenerateCCCAction extends CommonAction {

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if ("list_ccc".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ListGenerateCCCCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("generate_ccc".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new PrepareGenerateCCCCommand();
			objArray[1] = new GenerateCCCCommand();
			objArray[2] = new FrameFlagSetterCommand();
		}
		else if ("refresh_gen_ccc".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new RefreshGenerateCCCCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("submit".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new SubmitGenerateCCCCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("approve".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ApproveGenerateCCCCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("reject".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new RejectGenerateCCCCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("process".equals(event) || "edit_staging".equals(event) || "close_item".equals(event)
				|| "to_track".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new PrepareGenerateCCCCommand();
			objArray[1] = new ReadStagingGenerateCCCCommand();
			objArray[2] = new FrameFlagSetterCommand();
		}
		else if ("close".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new CloseGenerateCCCCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("total".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new PrepareGenerateCCCCommand();
			objArray[1] = new TotalGenerateCCCCommand();
			objArray[2] = new FrameFlagSetterCommand();
		}
		else if ("print_doc".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PreparePrintGenerateCCCCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("prepare_form".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareGenerateCCCCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("view_print_doc".equals(event)) {// CMS-210
			objArray = new ICommand[2];
			objArray[0] = new PrepareViewPrintCCCCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("update_view_outer_limit_bca".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new SaveGenerateCCCCommand();
			objArray[1] = new ViewOuterLimitBCACommand();
			objArray[2] = new FrameFlagSetterCommand();
		}
		else if ("view_outer_limit_bca".equals(event) || "checker_view_outer_limit_bca".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ViewOuterLimitBCACommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("checker_return".equals(event) || "view_return".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new ViewReturnGenerateCCCCommand();
			objArray[1] = new PrepareGenerateCCCCommand();
			objArray[2] = new FrameFlagSetterCommand();
		}
		else if ("update_return".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new ReturnGenerateCCCCommand();
			objArray[1] = new PrepareGenerateCCCCommand();
			objArray[2] = new FrameFlagSetterCommand();
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
		return GenerateCCCFormValidator.validateInput((GenerateCCCForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if ("total".equals(event) || "submit".equals(event) || "update_view_outer_limit_bca".equals(event)) {
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
			aPage.setPageReference("info");
			return aPage;
		}
		if ((resultMap.get("wip") != null) && ((String) resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference("wip");
			return aPage;
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
		if ("total".equals(event) || "submit".equals(event) || "update_view_outer_limit_bca".equals(event)) {
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
		if ("list_ccc".equals(event) || "edit_staging_checklist_item".equals(event) || "view_ok".equals(event)) {
			forwardName = "after_list_ccc";
		}
		else if ("generate_ccc".equals(event) || "total".equals(event) || "prepare_form".equals(event)
				|| "update_return".equals(event) || "refresh_gen_ccc".equals(event)) {
			forwardName = "after_generate_ccc";
		}
		else if ("submit".equals(event)) {
			forwardName = "after_submit";
		}
		else if ("process".equals(event) || "checker_return".equals(event)) {
			forwardName = "after_process";
		}
		else if ("edit_staging".equals(event)) {
			forwardName = "after_edit_staging";
		}
		else if ("approve".equals(event)) {
			forwardName = "after_approve";
		}
		else if ("reject".equals(event)) {
			forwardName = "after_reject";
		}
		else if ("close_item".equals(event) || "to_track".equals(event) || "view_return".equals(event)
				|| "view_print_doc".equals(event)) {
			forwardName = "after_close_item";
		}
		else if ("close".equals(event)) {
			forwardName = "after_close";
		}
		else if ("print_doc".equals(event)) {
			forwardName = "after_print_doc";
		}
		else if ("update_view_outer_limit_bca".equals(event) || "view_outer_limit_bca".equals(event)) {
			forwardName = "view_outer_limit_bca";
		}
		else if ("checker_view_outer_limit_bca".equals(event)) {
			forwardName = "checker_view_outer_limit_bca";
		}
		/*
		 * if("view_print_doc".equals(event)){//cms-210 forwardName =
		 * "after_view_print_doc"; }
		 */
		return forwardName;
	}

	private String frameCheck(String forwardName) {
		return forwardName + "_WF";
	}
}