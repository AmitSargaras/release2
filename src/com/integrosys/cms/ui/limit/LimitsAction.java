/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/limit/LimitsAction.java,v 1.9 2006/09/27 06:09:07 hshii Exp $
 */

package com.integrosys.cms.ui.limit;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.IPin;
import com.integrosys.base.uiinfra.common.Page;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2006/09/27 06:09:07 $ Tag: $Name: $
 */
public class LimitsAction extends CommonAction implements IPin {

	public static final String VIEW_LIMITS = "viewlimits";

	public static final String VIEW_LIMIT_DETAILS = "view_limit_details";

	public static final String PREPARE_EDIT_LIMITS = "prepare_edit_limits";

	public static final String EDIT_LIMITS = "edit_limits";

	public static final String CANCEL_EDIT_LIMITS = "cancel_edit_limits";

	public static final String CHECKER_READ_LIMITS = "checker_read_limits";

	public static final String CHECKER_APPROVE_UPDATE_LIMITS = "checker_approve_update_limits";

	public static final String CHECKER_REJECT_UPDATE_LIMITS = "checker_reject_update_limits";

	public static final String VIEW_CO_BORROWER_LIMIT_DETAILS = "view_co_borrower_limit_details";

	public static final String PREPARE_EDIT_CO_BORROWER_LIMITS = "prepare_edit_co_borrower_limits";

	public static final String EDIT_CO_BORROWER_LIMITS = "edit_co_borrower_limits";

	public static final String CANCEL_EDIT_CO_BORROWER_LIMITS = "cancel_edit_co_borrower_limits";

	public static final String CHECKER_READ_CO_BORROWER_LIMITS = "checker_read_co_borrower_limits";

	public static final String CHECKER_APPROVE_UPDATE_CO_BORROWER_LIMITS = "checker_approve_update_co_borrower_limits";

	public static final String CHECKER_REJECT_UPDATE_CO_BORROWER_LIMITS = "checker_reject_update_co_borrower_limits";

	public static final String RE_PROCESS_LIMITS = "re_process_limits";

	public static final String RE_PROCESS_CO_BORROWER_LIMITS = "re_process_co_borrower_limits";

	public static final String PREPARE_CLOSE_LIMITS = "prepare_close_limits";

	public static final String CLOSE_LIMITS = "close_limits";

	public static final String PREPARE_CLOSE_CO_BORROWER_LIMITS = "prepare_close_co_borrower_limits";

	public static final String CLOSE_CO_BORROWER_LIMITS = "close_co_borrower_limits";

	public static final String TO_TRACK = "to_track";

	public static final String TO_TRACK_CO_BORROWER = "to_track_coborrower";

	public static final String WORKING_IN_PROGRESS = "working_in_progress";

	public static final String WORKING_IN_PROGRESS_DIFFERENT_COUNTRY = "working_in_progress_different_country";

	public static final String EVENT_PREPARE_UPDATE = "prepare_update";

	public static final String EVENT_PREPARE_UPDATE_LIMIT = "prepare_update_limit";

	public static final String EVENT_PREPARE_UPDATE_ACCOUNT = "prepare_update_account";

	public static final String EVENT_PREPARE_ADD_ACCOUNT = "prepare_add_account";

	public static final String EVENT_DELETE_ITEM = "itemDelete";

	public static final String EVENT_UPDATE_RETURN = "update_return";

	public static final String EVENT_MAKER_UPDATE_COBORROWER_LIMIT = "maker_update_coborrower_limit";

	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (VIEW_LIMITS.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ViewLimitsCustomerCommand();
		}
		else if (VIEW_LIMIT_DETAILS.equals(event) || EVENT_PREPARE_UPDATE_LIMIT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ViewLimitsCommand();
			/*
			 * } else if (PREPARE_EDIT_LIMITS.equals(event)) { objArray = new
			 * ICommand[1]; objArray[0] = new PrepareEditLimitsCommand(); } else
			 * if (EDIT_LIMITS.equals(event)) { objArray = new ICommand[1];
			 * objArray[0] = new EditLimitsCommand();
			 */
		}
		else if (CHECKER_READ_LIMITS.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerReadLimitsCommand();
		}
		else if (CHECKER_APPROVE_UPDATE_LIMITS.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerApproveUpdateLimitsCommand();
		}
		else if (CHECKER_REJECT_UPDATE_LIMITS.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerRejectUpdateLimitsCommand();
		}
		else if (VIEW_CO_BORROWER_LIMIT_DETAILS.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ViewCoBorrowerCommand();
		}
		else if (PREPARE_EDIT_CO_BORROWER_LIMITS.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareEditCoBorrowerLimitsCommand();
		}
		else if (EDIT_CO_BORROWER_LIMITS.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new EditCoBorrowerLimitsCommand();
		}
		else if (CHECKER_READ_CO_BORROWER_LIMITS.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerReadCoBorrowerLimitsCommand();
		}
		else if (RE_PROCESS_LIMITS.equals(event)) {
			objArray = new ICommand[1];
			// objArray[0] = new ReProcessLimitsCommand();
			objArray[0] = new ViewLimitsCommand();
		}
		else if (RE_PROCESS_CO_BORROWER_LIMITS.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReProcessCoBorrowerLimitsCommand();
		}
		else if (PREPARE_CLOSE_LIMITS.equals(event)) {
			objArray = new ICommand[1];
			// objArray[0] = new ReProcessLimitsCommand();
			objArray[0] = new ViewLimitsCommand();
		}
		else if (CLOSE_LIMITS.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CloseLimitsCommand();
		}
		else if (PREPARE_CLOSE_CO_BORROWER_LIMITS.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ViewCoBorrowerCommand();
		}
		else if (CLOSE_CO_BORROWER_LIMITS.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CloseCoBorrowerLimitsCommand();
		}
		else if (CHECKER_APPROVE_UPDATE_CO_BORROWER_LIMITS.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerApproveUpdateCoBorrowerLimitsCommand();
		}
		else if (CHECKER_REJECT_UPDATE_CO_BORROWER_LIMITS.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerRejectUpdateCoBorrowerLimitsCommand();
		}
		else if (TO_TRACK.equals(event)) {
			objArray = new ICommand[1];
			// objArray[0] = new ReProcessLimitsCommand();
			objArray[0] = new ViewLimitsCommand();
		}
		else if (TO_TRACK_CO_BORROWER.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReProcessCoBorrowerLimitsCommand();
		}
		else if (EVENT_SUBMIT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SubmitMkLimitCommand();
		}
		else if (EVENT_PREPARE_UPDATE_ACCOUNT.equals(event) || EVENT_PREPARE_ADD_ACCOUNT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SaveLimitsCommand();
		}
		else if (EVENT_DELETE_ITEM.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new DeleteLimitAccountsCommand();
		}
		else if (EVENT_UPDATE_RETURN.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReturnLimitCommand();
		}
		else if (EVENT_APPROVE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new AppChkLimitCommand();
		}
		else if (EVENT_REJECT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RejectChkLimitCommand();
		}
		else if (EVENT_PREPARE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareLimitCommand();
		}
		else if (EVENT_MAKER_UPDATE_COBORROWER_LIMIT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new UpdateCoBorrowerCommand();
		}
		return objArray;
	}

	/**
	 * Method which determines whether a particular event has to be validated or
	 * not
	 * 
	 * @param event of type String
	 * @return boolean
	 */
	public boolean isValidationRequired(String event) {
		boolean validationRequired = false;
		if (EVENT_SUBMIT.equals(event) || EVENT_PREPARE_UPDATE_ACCOUNT.equals(event)
				|| EVENT_PREPARE_ADD_ACCOUNT.equals(event)
		/* EVENT_MAKER_UPDATE_COBORROWER_LIMIT.equals(event) */) {
			validationRequired = true;
		}
		return validationRequired;
	}

	/**
	 * This method is called only for create and Update command to validate the
	 * form and return the ActionErrors object.
	 * 
	 * @param cForm is of type ActionForm
	 * @param locale of type Locale
	 * @return ActionErrors
	 */
	public ActionErrors validateInput(ActionForm cForm, Locale locale) {
		ActionErrors errors = null;
		errors = LimitsValidator.validateInput((LimitsForm) cForm, locale);
		/*
		 * if (form.getEvent().equals(EDIT_LIMITS) ||
		 * form.getEvent().equals(EDIT_CO_BORROWER_LIMITS)) errors =
		 * EditLimitsValidator.validateInput((ViewLimitsForm)cForm,locale);
		 */
		return errors;
	}

	/**
	 * Method which determines which event to be called when error occurs.It is
	 * defaulted to the event returned by getDefaultEvent().The child classes
	 * can override this method to call their own events based on the event
	 * passed as a parameter.
	 * @param event of type String
	 * @return event of type String
	 */
	protected String getErrorEvent(String event) {
		String errorEvent = "";
		if (EVENT_PREPARE_UPDATE_ACCOUNT.equals(event) || EVENT_PREPARE_ADD_ACCOUNT.equals(event)
				|| EVENT_SUBMIT.equals(event)) {
			errorEvent = EVENT_PREPARE;
		}

		/*
		 * if (event.equals(EDIT_LIMITS)) errorEvent = CANCEL_EDIT_LIMITS; else
		 * if (event.equals(EDIT_CO_BORROWER_LIMITS)) errorEvent =
		 * CANCEL_EDIT_CO_BORROWER_LIMITS;
		 */
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
		String wip = (String) resultMap.get("wip");
		String haveSameCountryCode = (String) resultMap.get("haveSameCountryCode");
		Page aPage = new Page();
		/*
		 * if (wip!=null && wip.equals("Y") && haveSameCountryCode!=null &&
		 * haveSameCountryCode.equals("false"))
		 * aPage.setPageReference(WORKING_IN_PROGRESS_DIFFERENT_COUNTRY); else
		 * if (wip!=null && wip.equals("Y") && haveSameCountryCode!=null &&
		 * haveSameCountryCode.equals("true"))
		 * aPage.setPageReference(WORKING_IN_PROGRESS);
		 */
		if (wip != null) {
			aPage.setPageReference(WORKING_IN_PROGRESS);
		}
		else if (EVENT_PREPARE.equals(event) || EVENT_DELETE_ITEM.equals(event) || EVENT_UPDATE_RETURN.equals(event)) {
			aPage.setPageReference(EVENT_PREPARE_UPDATE_LIMIT);
		}
		else {
			aPage.setPageReference(event);
		}
		return aPage;
	}
}
