/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/genddn/GenerateDDNAction.java,v 1.13 2006/09/06 10:01:29 czhou Exp $
 */
package com.integrosys.cms.ui.genddn;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.common.ViewOuterLimitBCACommand;

/**
 * @author $Author: czhou $<br>
 * @version $Revision: 1.13 $
 * @since $Date: 2006/09/06 10:01:29 $ Tag: $Name: $
 */
public class GenerateDDNAction extends CommonAction {

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this, "Event : " + event);
		ICommand objArray[] = null;
		if ("approve".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ApproveGenerateDDNCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("reject".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new RejectGenerateDDNCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("process".equals(event) || "edit_staging".equals(event) || "close_item".equals(event)
				|| "to_track".equals(event)) {
//			objArray = new ICommand[5];
//			objArray[0] = new PrepareGenerateDDNCommand();
//			objArray[1] = new PrepareDeferredListCommand();
//			objArray[2] = new ReadStagingGenerateDDNCommand();
//			objArray[3] = new RefreshGenerateDDNCommand();
//			objArray[4] = new FrameFlagSetterCommand();
            objArray = new ICommand[4];
			objArray[0] = new PrepareDeferredListCommand();
			objArray[1] = new ReadStagingGenerateDDNCommand();
			objArray[2] = new RefreshGenerateDDNCommand();
			objArray[3] = new FrameFlagSetterCommand();
		}
		else if ("close".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new CloseGenerateDDNCommand();
			objArray[1] = new PrepareDeferredListCommand();
			objArray[2] = new FrameFlagSetterCommand();
		}
		else if ("prepare_form".equals(event)) {
			objArray = new ICommand[3];
			// objArray[0] = new PrepareGenerateDDNCommand();
			objArray[0] = new PrepareDeferredListCommand();
			objArray[1] = new RefreshGenerateDDNCommand();
			objArray[2] = new FrameFlagSetterCommand();
		}
		else if ("print_doc".equals(event)) {
			objArray = new ICommand[3];
			objArray[0]= new PrepareCommand();
			objArray[1] = new PreparePrintGenerateDDNCommand();
			objArray[2] = new FrameFlagSetterCommand();
		}
		else if ("update_view_outer_limit_bca".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new SaveGenerateDDNCommand();
			objArray[1] = new ViewOuterLimitBCACommand();
			objArray[2] = new FrameFlagSetterCommand();
		}
		else if ("view_outer_limit_bca".equals(event) || "checker_view_outer_limit_bca".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ViewOuterLimitBCACommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("checker_return".equals(event) || "view_return".equals(event)) {
//			objArray = new ICommand[3];
//			objArray[0] = new ViewReturnGenerateDDNCommand();
//			objArray[1] = new PrepareGenerateDDNCommand();
//			objArray[2] = new FrameFlagSetterCommand();
            objArray = new ICommand[2];
			objArray[0] = new ViewReturnGenerateDDNCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("update_return".equals(event)) {
//			objArray = new ICommand[3];
//			objArray[0] = new ReturnGenerateDDNCommand();
//			objArray[1] = new PrepareGenerateDDNCommand();
//			objArray[2] = new FrameFlagSetterCommand();
            objArray = new ICommand[2];
			objArray[0] = new ReturnGenerateDDNCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("generate_ddn".equals(event)) { // cr36
//			objArray = new ICommand[4];
//			objArray[0] = new PrepareGenerateDDNCommand();
//			objArray[1] = new PrepareDeferredListCommand();
//			objArray[2] = new GenerateDDNCommand();
//			objArray[3] = new FrameFlagSetterCommand();
            objArray = new ICommand[3];
			objArray[0] = new PrepareDeferredListCommand();
			objArray[1] = new GenerateDDNCommand();
			objArray[2] = new FrameFlagSetterCommand();
		}
		else if ("refresh_gen_ddn".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new RefreshGenerateDDNCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("submit_ddn".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new SubmitGenerateDDNCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("total_ddn".equals(event)) {
//			objArray = new ICommand[3];
//			objArray[0] = new PrepareGenerateDDNCommand();
//			objArray[1] = new TotalGenerateDDNCommand();
//			objArray[2] = new FrameFlagSetterCommand();
            objArray = new ICommand[2];
			objArray[0] = new TotalGenerateDDNCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("update_remarks".equals(event) || "view_remarks".equals(event)) {
			objArray = new ICommand[4];
			objArray[0] = new GenerateDDNCommand();
			objArray[1] = new PrepareDeferredListCommand();
			objArray[2] = new PrepareRemarksGenerateDDNCommand();
			objArray[3] = new FrameFlagSetterCommand();
		}
		else if ("submit_remarks".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new SubmitGenerateDDNCommand();
			objArray[1] = new FrameFlagSetterCommand();
		}
		else if ("view_generate_ddn".equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new PrepareDeferredListCommand();
			objArray[1] = new GenerateDDNCommand();
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
		GenerateDDNForm frm = (GenerateDDNForm) aForm;
		return GenerateDDNFormValidator.validateInput((GenerateDDNForm) aForm, locale);

	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
        /**
         * Chee Hong - No validation required for GenDDN in ABCLIMS
         * */
//		if ("total_ddn".equals(event) || "submit_ddn".equals(event) || "update_view_outer_limit_bca".equals(event)) {
//			result = true;
//		}
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
		// DefaultLogger.debug(this,"result map"+resultMap);
		if (resultMap.get("error") != null) {
			DefaultLogger.debug(this, "Page Reference" + "is INFO ------>");
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
		if ("total_ddn".equals(event) || "submit_ddn".equals(event) || "update_view_outer_limit_bca".equals(event)) {
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
		if ("generate_ddn".equals(event) || "total_ddn".equals(event) || "prepare_form".equals(event)
				|| "update_return".equals(event) || "refresh_gen_ddn".equals(event)) {
			forwardName = "after_generate_ddn";
		}
		if ("submit_ddn".equals(event) || "submit_remarks".equals(event)) {
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
				|| "view_generate_ddn".equals(event) || "view_remarks".equals(event)) {
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
		if ("update_remarks".equals(event)) {
			forwardName = "after_update_remarks";
		}
		return forwardName;
	}

	private String frameCheck(String forwardName) {
		return forwardName + "_WF";
	}
}