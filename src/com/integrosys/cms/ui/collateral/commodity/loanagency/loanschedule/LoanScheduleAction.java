/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/loanagency/loanschedule/LoanScheduleAction.java,v 1.7 2004/08/04 05:48:28 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.loanagency.loanschedule;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.collateral.commodity.loanagency.LoanAgencyAction;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2004/08/04 05:48:28 $ Tag: $Name: $
 */
public class LoanScheduleAction extends LoanAgencyAction {

	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (EVENT_PREPARE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareLoanScheduleCommand();
		}
		else if (EVENT_PREPARE_UPDATE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadLoanScheduleCommand();
			objArray[1] = new PrepareLoanScheduleCommand();
		}
		else if (EVENT_READ.equals(event) || EVENT_PRINT_EQUAL_PAYMENT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadLoanScheduleCommand();
		}
		else if (EVENT_UPDATE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new UpdateLoanScheduleCommand();
		}
		else if (EVENT_REFRESH.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new RefreshLoanScheduleCommand();
			objArray[1] = new PrepareLoanScheduleCommand();
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
		return LoanScheduleValidator.validateInput((LoanScheduleForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (EVENT_UPDATE.equals(event) || EVENT_REFRESH.equals(event)) {
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = event;
		if (EVENT_UPDATE.equals(event) || EVENT_REFRESH.equals(event)) {
			errorEvent = EVENT_PREPARE;
		}

		return errorEvent;
	}

	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		aPage.setPageReference(getReference(event, (String) resultMap.get("from_page")));
		return aPage;
	}

	private String getReference(String event, String from_event) {
		if (event.equals(EVENT_UPDATE) || event.equals(EVENT_CANCEL)) {
			return EVENT_RETURN;
		}
		if (event.equals(EVENT_READ)) {
			if (from_event.equals(EVENT_PROCESS)) {
				return EVENT_PROCESS;
			}
		}
		if (event.equals(EVENT_PREPARE_UPDATE) || event.equals(EVENT_REFRESH)) {
			return EVENT_PREPARE;
		}
		return event;
	}
}
