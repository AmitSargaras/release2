/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/liquidation/MaintainLiquidationAction.java,v 1 2007/02/08 Lini Exp $
 */
package com.integrosys.cms.ui.liquidation;

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
 * Describe this class. Purpose: for Liquidation Description: Action class for
 * Liquidation
 * 
 * @author $Author: lini$<br>
 * @version $Revision: 1$
 * @since $Date: 2007/02/08$ Tag: $Name$
 */

public class LiquidationAction extends CommonAction {

	public static final String EV_VIEW_NPL_INFO = "view_npl_info";

	public static final String EV_VIEW_RECOVERY_EXPENSE = "view_recovery_expense";

	public static final String EV_PREPARE_EDIT_RECOVERY_EXPENSE = "prepare_edit_recovery_expense";

	public static final String EV_EDIT_RECOVERY_EXPENSE = "edit_recovery_expense";

	public static final String EV_REMOVE_RECOVERY_EXPENSE = "remove_recovery_expense";

	public static final String EV_PREPARE_ADD_RECOVERY_EXPENSE = "prepare_add_recovery_expense";

	public static final String EV_ADD_RECOVERY_EXPENSE = "add_recovery_expense";

	public static final String EV_VIEW_RECOVERY = "view_recovery";

	public static final String EV_PREPARE_EDIT_RECOVERY = "prepare_edit_recovery";

	public static final String EV_EDIT_RECOVERY = "edit_recovery";

	public static final String EV_REMOVE_RECOVERY = "remove_recovery";

	public static final String EV_PREPARE_ADD_RECOVERY = "prepare_add_recovery";

	public static final String EV_ADD_RECOVERY = "add_recovery";

	public static final String EV_VIEW_RECOVERY_INCOME = "view_recovery_income";

	public static final String EV_PREPARE_EDIT_RECOVERY_INCOME = "prepare_edit_recovery_income";

	public static final String EV_EDIT_RECOVERY_INCOME = "edit_recovery_income";

	public static final String EV_REMOVE_RECOVERY_INCOME = "remove_recovery_income";

	public static final String EV_PREPARE_ADD_RECOVERY_INCOME = "prepare_add_recovery_income";

	public static final String EV_ADD_RECOVERY_INCOME = "add_recovery_income";

	public static final String EV_MKR_EDIT_LIQ_CONFIRM = "maker_edit_liquidation_confirm";

	public static final String EV_MKR_EDIT_REJECT_LIQ_CONFIRM = "maker_edit_reject_confirm";

	public static final String EV_MKR_CLOSE_LIQ = "maker_close_liquidation";

	public static final String EV_CHKR_EDIT_LIQ = "checker_edit_liquidation";

	public static final String EV_CHKR_APPROVE_EDIT_LIQ = "checker_approve_edit_liquidation";

	public static final String EV_CHKR_REJECT_EDIT_LIQ = "checker_reject_edit_liquidation";

	public static final String EV_MKR_CLOSE_LIQ_CONFIRM = "maker_close_liquidation_confirm";

	public static final String EV_MKR_EDIT_LIQ_REJECT = "maker_edit_liquidation_reject";

	public static final String EV_CHECKER_VIEW = "checker_view";

	public static final String EV_WIP = "wip";

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (EVENT_PREPARE.equals(event)) {

		}
		else if (EV_VIEW_NPL_INFO.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ViewNPLCommand();

		}
		else if (EVENT_LIST.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ListLiquidationCommand();

		}
		else if (EV_REMOVE_RECOVERY_EXPENSE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RemoveExpenseCommand();

		}
		else if (EV_REMOVE_RECOVERY.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RemoveRecoveryCommand();

		}
		else if (EV_REMOVE_RECOVERY_INCOME.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RemoveRecoveryIncomeCommand();

		}
		else if (EV_PREPARE_ADD_RECOVERY.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareAddRecoveryCommand();

		}
		else if (EV_PREPARE_ADD_RECOVERY_INCOME.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareAddRecoveryIncomeCommand();

		}
		else if (EV_PREPARE_ADD_RECOVERY_EXPENSE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareAddExpenseCommand();

		}
		else if (EV_ADD_RECOVERY.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new AddRecoveryCommand();

		}
		else if (EV_ADD_RECOVERY_INCOME.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new AddRecoveryIncomeCommand();

		}
		else if (EV_ADD_RECOVERY_EXPENSE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new AddExpenseCommand();

		}
		else if (EV_VIEW_RECOVERY_EXPENSE.equals(event) || EV_PREPARE_EDIT_RECOVERY_EXPENSE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ViewExpenseCommand();

		}
		else if (EV_VIEW_RECOVERY.equals(event) || EV_PREPARE_EDIT_RECOVERY.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ViewRecoveryCommand();

		}
		else if (EV_VIEW_RECOVERY_INCOME.equals(event) || EV_PREPARE_EDIT_RECOVERY_INCOME.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ViewRecoveryIncomeCommand();

		}
		else if (EV_EDIT_RECOVERY_EXPENSE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new EditExpenseCommand();

		}
		else if (EV_EDIT_RECOVERY.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new EditRecoveryCommand();

		}
		else if (EV_EDIT_RECOVERY_INCOME.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new EditRecoveryIncomeCommand();

		}
		else if (EV_MKR_EDIT_LIQ_CONFIRM.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerEditLiquidationCmd();

		}
		else if (EV_MKR_EDIT_REJECT_LIQ_CONFIRM.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerEditRejectedLiquidationCmd();

		}
		else if (EV_CHKR_EDIT_LIQ.equals(event) || EV_MKR_CLOSE_LIQ.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerReadLiquidationCmd();

		}
		else if (EV_CHKR_APPROVE_EDIT_LIQ.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerApproveEditLiquidationCmd();

		}
		else if (EV_CHKR_REJECT_EDIT_LIQ.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerRejectEditLiquidationCmd();

		}
		else if (EV_MKR_CLOSE_LIQ_CONFIRM.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerCancelEditLiquidationCmd();

		}
		else if (EV_MKR_EDIT_LIQ_REJECT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerReadRejectedLiquidationCmd();

		}
		else if (EVENT_VIEW.equals(event) || EV_CHECKER_VIEW.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ListLiquidationCommand();

		}
		else if ("to_track".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerReadLiquidationCmd();

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
		return LiquidationValidator.validateInput((LiquidationForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (LiquidationAction.EV_ADD_RECOVERY_EXPENSE.equals(event)
				|| LiquidationAction.EV_EDIT_RECOVERY_EXPENSE.equals(event)
				|| LiquidationAction.EV_ADD_RECOVERY.equals(event) || LiquidationAction.EV_EDIT_RECOVERY.equals(event)
				|| LiquidationAction.EV_ADD_RECOVERY_INCOME.equals(event)
				|| LiquidationAction.EV_EDIT_RECOVERY_INCOME.equals(event) || event.equals(EV_MKR_EDIT_LIQ_CONFIRM)
				|| event.equals(EV_CHECKER_VIEW)) {
			result = true;
		}
		DefaultLogger.debug(this, "validation required" + result);
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if (EV_MKR_EDIT_LIQ_CONFIRM.equals(event)) {
			errorEvent = "maker_edit_liquidation_confirm_error";
		}
		else if (EVENT_LIST.equals(event) || EV_CHECKER_VIEW.equals(event)) {
			errorEvent = "liquidation_list_error";
		}
		else if (EV_ADD_RECOVERY_EXPENSE.equals(event)) {
			errorEvent = "edit_expense_error";
		}
		else if (EV_ADD_RECOVERY.equals(event)) {
			errorEvent = "edit_recovery_error";
		}
		else if (EV_ADD_RECOVERY_INCOME.equals(event)) {
			errorEvent = "edit_recovery_income_error";
		}
		else if (EV_EDIT_RECOVERY_EXPENSE.equals(event)) {
			errorEvent = "edit_expense_error";
		}
		else if (EV_EDIT_RECOVERY.equals(event)) {
			errorEvent = "edit_recovery_error";
		}
		else if (EV_EDIT_RECOVERY_INCOME.equals(event)) {
			errorEvent = "edit_recovery_income_error";
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
		if ((resultMap.get(EV_WIP) != null) && (resultMap.get(EV_WIP)).equals(EV_WIP)) {
			//System.out.println("************** getNextPage() wip *************"
			// );

			aPage.setPageReference(getReference(EV_WIP));
			return aPage;

		}
		else {
			// System.out.println(
			// "************** getNextPage() event *************");

			aPage.setPageReference(getReference(event));
			return aPage;
		}
	}

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */

	private String getReference(String event) {
		String forwardName = "submit_fail";
		if (EVENT_PREPARE.equals(event) || "liquidation_list_error".equals(event)) {
			forwardName = "after_prepare";

		}
		else if (EVENT_LIST.equals(event) || "maker_edit_liquidation_confirm_error".equals(event)
				|| EV_REMOVE_RECOVERY_EXPENSE.equals(event) || EV_REMOVE_RECOVERY.equals(event)
				|| EV_EDIT_RECOVERY_EXPENSE.equals(event) || EV_EDIT_RECOVERY.equals(event)
				|| EV_ADD_RECOVERY_EXPENSE.equals(event) || EV_ADD_RECOVERY.equals(event)) {
			forwardName = EVENT_LIST;

		}
		else if (EV_REMOVE_RECOVERY_INCOME.equals(event) || EV_EDIT_RECOVERY_INCOME.equals(event)
				|| EV_ADD_RECOVERY_INCOME.equals(event) || EV_PREPARE_EDIT_RECOVERY.equals(event)
				|| "edit_recovery_error".equals(event)) {
			forwardName = EV_PREPARE_ADD_RECOVERY;

		}
		else if (EVENT_SEARCH.equals(event)) {
			forwardName = "after_search";

		}
		else if (EV_MKR_EDIT_LIQ_CONFIRM.equals(event) || EV_MKR_EDIT_REJECT_LIQ_CONFIRM.equals(event)) {
			forwardName = EV_MKR_EDIT_LIQ_CONFIRM;

		}
		else if (EV_CHKR_APPROVE_EDIT_LIQ.equals(event)) {
			forwardName = "common_approve_page";

		}
		else if (EV_CHKR_REJECT_EDIT_LIQ.equals(event)) {
			forwardName = "common_reject_page";

		}
		else if (EV_MKR_CLOSE_LIQ_CONFIRM.equals(event)) {
			forwardName = "common_close_page";

		}
		else if (EV_MKR_EDIT_LIQ_REJECT.equals(event)) {
			forwardName = "maker_edit_liquidation_page";

		}
		else if (EVENT_VIEW.equals(event) || EV_CHECKER_VIEW.equals(event)) {
			forwardName = EVENT_VIEW;

		}
		else if ("to_track".equals(event)) {
			forwardName = "after_to_track";

		}
		else if (EV_PREPARE_EDIT_RECOVERY_EXPENSE.equals(event) || "edit_expense_error".equals(event)) {
			forwardName = EV_PREPARE_ADD_RECOVERY_EXPENSE;

		}
		else if (EV_PREPARE_EDIT_RECOVERY_INCOME.equals(event) || "edit_recovery_income_error".equals(event)) {
			forwardName = EV_PREPARE_ADD_RECOVERY_INCOME;
		}
		else {
			forwardName = event;
		}

		return forwardName;
	}

}
