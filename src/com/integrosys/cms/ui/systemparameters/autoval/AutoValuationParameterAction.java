/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/ccglobal/CCGlobalAction.java,v 1.3 2003/08/23 07:30:45 elango Exp $
 */
package com.integrosys.cms.ui.systemparameters.autoval;

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
 * Describe this class. Purpose: for Auto Valuation Parameters Description:
 * Action class for Auto Valuation Parameters
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class AutoValuationParameterAction extends CommonAction {

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if ("auto_val_param_list".equals(event) || "checker_view".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ListAutoValuationParameterCommand();
		}
		else if ("maker_prepare_create".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareAutoValuationParameterCommand();
		}
		else if ("maker_prepare_edit".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareEditAutoValuationParameterCommand();
		}
		else if ("maker_prepare_delete".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareDeleteAutoValuationParameterCommand();
		}
		else if ("view_auto_val_param".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadAutoValuationParameterCommand();
		}
		else if ("maker_confirm_create".equals(event) || "maker_confirm_resubmit_create".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CreateAutoValuationParameterCommand();
		}
		else if ("maker_confirm_edit".equals(event) || "maker_confirm_resubmit_edit".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new EditAutoValuationParameterCommand();
		}
		else if ("maker_confirm_delete".equals(event) || "maker_confirm_resubmit_delete".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new DeleteAutoValuationParameterCommand();
		}
		else if ("checker_process_create".equals(event) || "checker_process_edit".equals(event)
				|| "checker_process_delete".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerProcessAutoValuationParameterCommand();
		}
		else if ("checker_confirm_approve_create".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ApproveCreateAutoValuationParameterCommand();
		}
		else if ("checker_confirm_approve_edit".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ApproveEditAutoValuationParameterCommand();
		}
		else if ("checker_confirm_approve_delete".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ApproveDeleteAutoValuationParameterCommand();
		}
		else if ("checker_confirm_reject_create".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RejectCreateAutoValuationParameterCommand();
		}
		else if ("checker_confirm_reject_edit".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RejectEditAutoValuationParameterCommand();
		}
		else if ("checker_confirm_reject_delete".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RejectDeleteAutoValuationParameterCommand();
		}
		else if ("maker_confirm_close_create".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CloseCreateAutoValuationParameterCommand();
		}
		else if ("maker_confirm_close_edit".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CloseEditAutoValuationParameterCommand();
		}
		else if ("maker_confirm_close_delete".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CloseDeleteAutoValuationParameterCommand();
		}
		else if ("maker_prepare_close_create".equals(event) || "maker_prepare_close_edit".equals(event)
				|| "maker_prepare_close_delete".equals(event) || "to_track".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadStagingAutoValuationParameterCommand();
		}
		else if ("maker_prepare_resubmit_create".equals(event) || "maker_prepare_resubmit_edit".equals(event)
				|| "maker_prepare_resubmit_delete".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerPrepareResubmitAutoValuationParameterCommand();
		}
		else if ("maker_resubmit_change_list_create".equals(event) || "maker_resubmit_change_list_edit".equals(event)
				|| "maker_prepare_change_list_create".equals(event) || "maker_prepare_change_list_edit".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerChangeListAutoValuationParameterCommand();
		}

		return (objArray);
	}

	/**
	 * Method which returns default event
	 * @return default event
	 */
	public String getDefaultEvent() {
		return "auto_val_param_list";
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
		return AutoValuationParameterFormValidator.validateInput((AutoValuationParameterForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals("maker_confirm_create") || event.equals("maker_confirm_edit")
				|| event.equals("maker_confirm_resubmit_create") || (event.equals("maker_confirm_resubmit_edit"))) {
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

		if ((resultMap.get("trx_disallowed") != null)
				&& ((String) resultMap.get("trx_disallowed")).equals("disallowed")) {
			aPage.setPageReference("trx_disallowed");
			return aPage;
		}

		aPage.setPageReference(getReference(event));
		return aPage;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if ("maker_confirm_create".equals(event)) {
			errorEvent = "maker_prepare_create";
		}
		if ("maker_confirm_edit".equals(event)) {
			errorEvent = "maker_prepare_edit";
		}
		if ("maker_confirm_resubmit_create".equals(event)) {
			errorEvent = "maker_prepare_resubmit_create";
		}
		if ("maker_confirm_resubmit_edit".equals(event)) {
			errorEvent = "maker_prepare_resubmit_edit";
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
		if ("auto_val_param_list".equals(event)) {
			forwardName = "after_auto_val_param_list";
		}
		if ("view_auto_val_param".equals(event) || "maker_prepare_close_create".equals(event)
				|| "maker_prepare_close_edit".equals(event) || "maker_prepare_close_delete".equals(event)
				|| "to_track".equals(event)) {
			forwardName = "after_view_auto_val_param";
		}
		if ("maker_confirm_create".equals(event) || "maker_confirm_resubmit_create".equals(event)) {
			forwardName = "after_maker_confirm_create";
		}
		if ("maker_confirm_edit".equals(event) || "maker_confirm_resubmit_edit".equals(event)) {
			forwardName = "after_maker_confirm_edit";
		}
		if ("maker_confirm_delete".equals(event) || "maker_confirm_resubmit_delete".equals(event)) {
			forwardName = "after_maker_confirm_delete";
		}
		if ("maker_prepare_create".equals(event) || "maker_prepare_change_list_create".equals(event)) {
			forwardName = "after_maker_prepare_create";
		}
		if ("maker_prepare_edit".equals(event) || "maker_prepare_change_list_edit".equals(event)) {
			forwardName = "after_maker_prepare_edit";
		}
		if ("checker_process_create".equals(event) || "checker_process_edit".equals(event)
				|| "checker_process_delete".equals(event)) {
			forwardName = "after_checker_process";
		}
		if ("maker_prepare_resubmit".equals(event)) {
			forwardName = "after_maker_prepare_update";
		}
		if ("checker_confirm_approve_create".equals(event) || "checker_confirm_approve_edit".equals(event)
				|| "checker_confirm_approve_delete".equals(event)) {
			forwardName = "after_checker_confirm_approve";
		}
		if ("checker_confirm_reject_create".equals(event) || "checker_confirm_reject_edit".equals(event)
				|| "checker_confirm_reject_delete".equals(event)) {
			forwardName = "after_checker_confirm_reject";
		}
		if ("maker_prepare_resubmit_create".equals(event) || "maker_prepare_resubmit_edit".equals(event)
				|| "maker_resubmit_change_list_create".equals(event) || "maker_resubmit_change_list_edit".equals(event)) {
			forwardName = "after_maker_prepare_resubmit";
		}
		if ("maker_prepare_resubmit_delete".equals(event) || "maker_prepare_delete".equals(event)) {
			forwardName = "after_maker_prepare_delete";
		}

		DefaultLogger.debug(this, "coming here reference =============");
		if ("maker_confirm_close_create".equals(event) || "maker_confirm_close_edit".equals(event)
				|| "maker_confirm_close_delete".equals(event)) {
			forwardName = "after_maker_confirm_close";
		}

		if ("checker_view".equals(event)) {
			forwardName = "checker_view";
		}

		DefaultLogger.debug(this, "going out reference =============");

		return forwardName;
	}
}