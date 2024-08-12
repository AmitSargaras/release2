/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/loanagency/LoanAgencyAction.java,v 1.11 2004/11/02 01:57:17 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.loanagency;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.collateral.commodity.CommodityMainAction;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2004/11/02 01:57:17 $ Tag: $Name: $
 */
public class LoanAgencyAction extends CommodityMainAction {
	public static final String EVENT_VIEW_SECURITY_DOC = "view_sec_doc";

	public static final String EVENT_RETURN = "return";

	public static final String EVENT_REFRESH_PAYMENT_SCHEDULE = "refresh_payment_schedule";

	public static final String EVENT_REFRESH_INTEREST_AMT = "refresh_interest_amt";

	public static final String EVENT_VIEW_EQUAL_PAYMENT = "view_equal_payment";

	public static final String EVENT_PRINT_EQUAL_PAYMENT = "print_equal_payment";

	public static final String EVENT_EDIT_NON_EQUAL_PAYMENT = "edit_non_equal_payment";

	public static final String EVENT_VIEW_NON_EQUAL_PAYMENT = "view_non_equal_payment";

	public static final String EVENT_ADD_SUBLIMIT = "add_sublimit";

	public static final String EVENT_ADD_PARTICIPANT = "add_participant";

	public static final String EVENT_REMOVE_SUBLIMIT = "remove_sublimit";

	public static final String EVENT_REMOVE_PARTICIPANT = "remove_participant";

	public static final String EVENT_PREPARE_NOOP = "prepare_noop";

	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (EVENT_CREATE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CreateLoanAgencyCommand();
		}
		else if (EVENT_PREPARE_UPDATE_SUB.equals(event) || EVENT_PREPARE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadLoanAgencyCommand();
			objArray[1] = new PrepareLoanAgencyCommand();
		}
		else if (EVENT_READ.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadLoanAgencyCommand();
		}
		else if (EVENT_UPDATE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new UpdateLoanAgencyCommand();
		}
		else if (EVENT_READ_RETURN.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReturnLoanAgencyCommand();
		}
		else if (EVENT_VIEW_SECURITY_DOC.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ProcessingLoanAgencyCommand();
			objArray[1] = new ViewSecurityDocCommand();
		}
		else if (EVENT_EDIT_NON_EQUAL_PAYMENT.equals(event) || EVENT_VIEW_EQUAL_PAYMENT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ProcessingLoanAgencyCommand();
		}
		else if (EVENT_RETURN.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ViewReturnLoanAgencyCommand();
			objArray[1] = new PrepareLoanAgencyCommand();
		}
		else if (EVENT_REFRESH_PAYMENT_SCHEDULE.equals(event) || EVENT_REFRESH_INTEREST_AMT.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new RefreshLoanAgencyCommand();
			objArray[1] = new PrepareLoanAgencyCommand();
		}
		else if (EVENT_ADD_PARTICIPANT.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new AddParticipantCommand();
			objArray[1] = new PrepareLoanAgencyCommand();
		}
		else if (EVENT_ADD_SUBLIMIT.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new AddSubLimitCommand();
			objArray[1] = new PrepareLoanAgencyCommand();
		}
		else if (EVENT_REMOVE_PARTICIPANT.equals(event) || EVENT_REMOVE_SUBLIMIT.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new RemoveLoanAgencyItemCommand();
			objArray[1] = new PrepareLoanAgencyCommand();
		}
		else if (EVENT_PREPARE_NOOP.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareLoanAgencyCommand();
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
		DefaultLogger.debug(this, "VALIDATION REQUIRED...");
		return LoanAgencyValidator.validateInput((LoanAgencyForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (EVENT_CREATE.equals(event) || EVENT_UPDATE.equals(event) || EVENT_EDIT_NON_EQUAL_PAYMENT.equals(event)
				|| EVENT_REFRESH_INTEREST_AMT.equals(event) || EVENT_REFRESH_PAYMENT_SCHEDULE.equals(event)
				|| EVENT_VIEW_EQUAL_PAYMENT.equals(event)) {
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = event;
		if (EVENT_CREATE.equals(event) || EVENT_UPDATE.equals(event) || EVENT_REFRESH_INTEREST_AMT.equals(event)
				|| EVENT_REFRESH_PAYMENT_SCHEDULE.equals(event) || EVENT_EDIT_NON_EQUAL_PAYMENT.equals(event)
				|| EVENT_VIEW_EQUAL_PAYMENT.equals(event)) {
			errorEvent = EVENT_PREPARE_NOOP;
		}

		return errorEvent;
	}

	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		if ((resultMap.get("limitProtection") != null) && resultMap.get("limitProtection").equals("limitProtection")) {
			aPage.setPageReference("limitProtection");
			return aPage;
		}

		if (EVENT_READ_RETURN.equals(event)) {
			aPage.setPageReference(getReturnReference((String) resultMap.get("from_page")));
		}
		else if (EVENT_READ.equals(event) || EVENT_RETURN.equals(event)) {
			aPage.setPageReference(getReadReference(event, (String) resultMap.get("from_page")));
		}
		else {
			aPage.setPageReference(getReference(event));
		}
		return aPage;
	}

	private String getReference(String event) {
		if (event.equals(EVENT_CREATE) || event.equals(EVENT_UPDATE) || event.equals(EVENT_CANCEL)) {
			return EVENT_UPDATE_RETURN;
		}
		if (event.equals(EVENT_PREPARE_UPDATE_SUB) || event.equals(EVENT_ADD_PARTICIPANT)
				|| event.equals(EVENT_ADD_SUBLIMIT) || event.equals(EVENT_REFRESH_INTEREST_AMT)
				|| event.equals(EVENT_REFRESH_PAYMENT_SCHEDULE) || event.equals(EVENT_REMOVE_PARTICIPANT)
				|| event.equals(EVENT_REMOVE_SUBLIMIT) || event.equals(EVENT_PREPARE_NOOP)) {
			return EVENT_PREPARE;
		}
		return event;
	}

	private String getReturnReference(String from_event) {
		if (from_event.equals(EVENT_PREPARE_CLOSE)) {
			return EVENT_CLOSE_RETURN;
		}
		if (from_event.equals(EVENT_TRACK)) {
			return EVENT_TRACK_RETURN;
		}
		if (from_event.equals(EVENT_PROCESS)) {
			return EVENT_PROCESS_RETURN;
		}
		if (from_event.equals(EVENT_READ)) {
			return EVENT_READ_RETURN;
		}
		return from_event;
	}

	private String getReadReference(String event, String from_event) {
		if (from_event.equals(EVENT_PREPARE_CLOSE) || from_event.equals(EVENT_READ)
				|| from_event.equals(CommodityMainAction.EVENT_TRACK)) {
			return "view_loan_agency";
		}
		if (from_event.equals(EVENT_PROCESS)) {
			return "process_loan_agency";
		}
		if (event.equals(EVENT_READ)
				&& (from_event.equals(EVENT_PREPARE_UPDATE) || from_event.equals(EVENT_PROCESS_UPDATE))) {
			return "view_loan_agency";
		}
		if (event.equals(EVENT_RETURN)
				&& (from_event.equals(EVENT_PREPARE_UPDATE) || from_event.equals(EVENT_PROCESS_UPDATE))) {
			return EVENT_PREPARE;
		}
		return from_event;
	}
}
