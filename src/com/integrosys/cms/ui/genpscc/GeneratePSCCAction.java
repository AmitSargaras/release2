/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/genpscc/GeneratePSCCAction.java,v 1.6 2005/12/12 10:03:45 hmbao Exp $
 */
package com.integrosys.cms.ui.genpscc;

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
 * @version $Revision: 1.6 $
 * @since $Date: 2005/12/12 10:03:45 $ Tag: $Name: $
 */
public class GeneratePSCCAction extends CommonAction {

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if ("generate_pscc".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new PrepareGeneratePSCCCommand();
			objArray[1] = new GeneratePSCCCommand();
			objArray[2] = new FrameFlagSetterCommand();
		}
		else if ("refresh_gen_pscc".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new RefreshGeneratePSCCCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("submit".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new SubmitGeneratePSCCCommand();
			objArray[1] = new RefreshGeneratePSCCCommand();
			objArray[2] = new FrameFlagSetterCommand();
		}
		else if ("approve".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ApproveGeneratePSCCCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("reject".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new RejectGeneratePSCCCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("process".equals(event) || "edit_staging".equals(event) || "close_item".equals(event)
				|| "to_track".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new PrepareGeneratePSCCCommand();
			objArray[1] = new ReadStagingGeneratePSCCCommand();
			objArray[2] = new FrameFlagSetterCommand();
		}
		else if ("close".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new CloseGeneratePSCCCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("total".equals(event)) {
			objArray = new ICommand[4];
			objArray[0] = new PrepareGeneratePSCCCommand();
			objArray[1] = new TotalGeneratePSCCCommand();
			objArray[2] = new RefreshGeneratePSCCCommand();
			objArray[3] = new FrameFlagSetterCommand();
		}
		else if ("prepare_form".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareGeneratePSCCCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("print_doc".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PreparePrintGeneratePSCCCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("update_view_outer_limit_bca".equals(event)) {
			objArray = new ICommand[4];
			objArray[0] = new SaveGeneratePSCCCommand();
			objArray[1] = new ViewOuterLimitBCACommand();
			objArray[2] = new RefreshGeneratePSCCCommand();
			objArray[3] = new FrameFlagSetterCommand();
		}
		else if ("view_outer_limit_bca".equals(event) || "checker_view_outer_limit_bca".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ViewOuterLimitBCACommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("checker_return".equals(event) || "view_return".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new ViewReturnGeneratePSCCCommand();
			objArray[1] = new PrepareGeneratePSCCCommand();
			objArray[2] = new FrameFlagSetterCommand();
		}
		else if ("update_return".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new ReturnGeneratePSCCCommand();
			objArray[1] = new PrepareGeneratePSCCCommand();
			objArray[2] = new FrameFlagSetterCommand();
		}
		else if ("view_generate_pscc".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new GeneratePSCCCommand();
			objArray[1] = new FrameFlagSetterCommand();
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
		return GeneratePSCCFormValidator.validateInput((GeneratePSCCForm) aForm, locale);
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
		if ((resultMap.get("na") != null) && ((String) resultMap.get("na")).equals("na")) {
			aPage.setPageReference("na");
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
		if ("generate_pscc".equals(event) || "total".equals(event) || "prepare_form".equals(event)
				|| "update_return".equals(event) || "refresh_gen_pscc".equals(event)) {
			forwardName = "after_generate_pscc";
		}
		if ("submit".equals(event)) {
			forwardName = "after_submit";
		}
		if ("process".equals(event) || "checker_return".equals(event)) {
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
		if ("close_item".equals(event) || "to_track".equals(event) || "view_return".equals(event)
				|| "view_generate_pscc".equals(event)) {
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
		if ("update_view_outer_limit_bca".equals(event) || "view_outer_limit_bca".equals(event)) {
			forwardName = "view_outer_limit_bca";
		}
		if ("checker_view_outer_limit_bca".equals(event)) {
			forwardName = "checker_view_outer_limit_bca";
		}

		return forwardName;
	}

	private String frameCheck(String forwardName) {
		return forwardName + "_WF";
	}
}