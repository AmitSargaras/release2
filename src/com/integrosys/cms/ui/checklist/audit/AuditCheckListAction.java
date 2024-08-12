/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/checklist/audit/AuditCheckListAction.java,v 1.4 2004/07/14 04:00:20 visveswari Exp $
 */
package com.integrosys.cms.ui.checklist.audit;

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
 * @author $Author: visveswari $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/07/14 04:00:20 $ Tag: $Name: $
 */
public class AuditCheckListAction extends CommonAction {

	/**
	 * This method return a Array of Command Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return ICommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if ("list".equals(event)) {
			DefaultLogger.debug(this, "event=list");
			objArray = new ICommand[1];
			objArray[0] = new PrepareAuditCommand();
		}
		if ("list_all".equals(event) || "print_doc".equals(event)) {
			DefaultLogger.debug(this, "event=list_all");
			objArray = new ICommand[1];
			objArray[0] = new ListAuditCategoriesCommand();
		}
		if ("main_borrower_list".equals(event) || "print_main_borrower_list".equals(event)) {
			DefaultLogger.debug(this, "event=main_borrower_list");
			objArray = new ICommand[1];
			objArray[0] = new ListMainBorrowerAuditCommand();
		}
		if ("non_borrower_list".equals(event) || "print_non_borrower_list".equals(event)) {
			DefaultLogger.debug(this, "event=non_borrower_list");
			objArray = new ICommand[1];
			objArray[0] = new ListMainBorrowerAuditCommand();
		}
		if ("co_borrower_list".equals(event) || "print_co_borrower_list".equals(event)) {
			DefaultLogger.debug(this, "event=co_borrower_list");
			objArray = new ICommand[1];
			objArray[0] = new ListCoBorrowerAuditCommand();
		}
		if ("pledgor_list".equals(event) || "print_pledgor_list".equals(event)) {
			DefaultLogger.debug(this, "event=pledgor_list");
			objArray = new ICommand[1];
			objArray[0] = new ListPledgorAuditCommand();
		}
		/*
		 * if ("securities_list".equals(event) ||
		 * "print_securities_list".equals(event)) { DefaultLogger.debug(this,
		 * "event=securities_list"); objArray = new ICommand[1]; objArray[0] =
		 * new ListSecurityDocsCommand(); }
		 */

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
		return AuditCheckListFormValidator.validateInput((AuditCheckListForm) aForm, locale);
		// return
		// null;//SecurityMasterFormValidator.validateInput((SecurityMasterForm
		// )aForm,locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if ("list_all".equals(event)) {
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if ("list_all".equals(event)) {
			errorEvent = "list";
		}
		return errorEvent;
	}

	/**
	 * This method is used to determine the page to be displayed next based on
	 * the event. Result hashmap and exception hashmap. It returns the page
	 * object .
	 * 
	 * @param event is of type String
	 * @param resultMap is of type HashMap
	 * @param exceptionMap is of type HashMap
	 * @return IPage
	 */
	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		aPage.setPageReference(getReference(event));
		return aPage;
	}

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event) {
		String forwardName = null;
		if ("list".equals(event)) {
			DefaultLogger.debug(this, "forwardName=after_list_date");
			forwardName = "after_list_date";
		}
		if ("list_all".equals(event) || "print_doc".equals(event)) {
			DefaultLogger.debug(this, "forwardName=after_list");
			forwardName = "after_list";
		}
		if ("main_borrower_list".equals(event)) {
			DefaultLogger.debug(this, "forwardName=after_main_borrower_list");
			forwardName = "after_main_borrower_list";
		}
		if ("non_borrower_list".equals(event)) {
			DefaultLogger.debug(this, "forwardName=after_non_borrower_list");
			forwardName = "after_non_borrower_list";
		}
		if ("co_borrower_list".equals(event)) {
			DefaultLogger.debug(this, "forwardName=after_co_borrower_list");
			forwardName = "after_co_borrower_list";
		}
		if ("pledgor_list".equals(event)) {
			DefaultLogger.debug(this, "forwardName=after_pledgor_list");
			forwardName = "after_pledgor_list";
		}
		/*
		 * if("securities_list".equals(event)) { DefaultLogger.debug(this,
		 * "forwardName=after_securities_list"); forwardName =
		 * "after_securities_list"; }
		 */
		if ("print_main_borrower_list".equals(event)) {
			forwardName = "after_print_main_borrower_list";
		}
		if ("print_non_borrower_list".equals(event)) {
			forwardName = "after_print_non_borrower_list";
		}
		if ("print_co_borrower_list".equals(event)) {
			forwardName = "after_print_co_borrower_list";
		}
		if ("print_pledgor_list".equals(event)) {
			forwardName = "after_print_pledgor_list";
		}

		/*
		 * if("print_securities_list".equals(event)){ forwardName =
		 * "after_print_securities_list"; }
		 */

		return forwardName;
	}
}