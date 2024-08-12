package com.integrosys.cms.ui.contractfinancing.advspayment;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.contractfinancing.ContractFinancingAction;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class SG_AdvsPaymentAction extends ContractFinancingAction {

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[];

		DefaultLogger.debug(this, ">>>>>>>>>>> event=" + event);

		if (EVENT_LIST_SUMMARY.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SG_ListAdvanceSummaryCommand();
		}
		else if (EVENT_PREPARE_FORM.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new SG_PrepareAdvanceCommand();
			objArray[1] = new SG_ReadAdvanceCommand();
		}
		else if (event.equals(EVENT_CHECKER_VIEW) || EVENT_VIEW.equals(event)
				|| EVENT_MAKER_PREPARE_CREATE.equals(event) || EVENT_MAKER_PREPARE_UPDATE.equals(event)
				|| EVENT_MAKER_PREPARE_DELETE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new SG_PrepareAdvanceCommand();
			objArray[1] = new SG_ReadAdvanceCommand();
		}
		else if (EVENT_REFRESH.equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new SG_SaveAdvanceCommand();
			objArray[1] = new SG_PrepareAdvanceCommand();
			objArray[2] = new SG_ReadAdvanceCommand();
		}
		else if (EVENT_MAKER_PREPARE_CREATE_ITEM.equals(event) || EVENT_MAKER_PREPARE_UPDATE_ITEM.equals(event)
				|| EVENT_DELETE_ITEM.equals(event) || EVENT_VIEW_ITEM.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SG_SaveAdvanceCommand();
		}
		else if (EVENT_CREATE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SG_CreateAdvanceCommand();
		}
		else if (EVENT_UPDATE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SG_UpdateAdvanceCommand();
		}
		else if (EVENT_DELETE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SG_DeleteAdvanceCommand();
		}
		else if (EVENT_PROCESS_RETURN.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SG_PrepareAdvanceCommand();
		}
		else {
			objArray = super.getCommandChain(event);
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
		return SG_AdvanceValidator.validateInput((SG_AdvsPaymentForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (EVENT_CREATE.equals(event) || EVENT_UPDATE.equals(event) || EVENT_REFRESH.equals(event)
				|| EVENT_MAKER_PREPARE_CREATE_ITEM.equals(event) || EVENT_MAKER_PREPARE_UPDATE_ITEM.equals(event)
				|| EVENT_DELETE_ITEM.equals(event) || EVENT_VIEW_ITEM.equals(event) || EVENT_PREPARE_FORM.equals(event)) {
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		return EVENT_PREPARE_FORM;
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
		if ((resultMap.get("wip") != null) && (resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference("wip");
		}
		else if (resultMap.get("event") != null) {
			aPage.setPageReference(getReference((String) resultMap.get("event")));
		}
		else {
			aPage.setPageReference(getReference(event));
		}
		return aPage;
	}

	/**
	 * method which determines the forward name for a particular event
	 * 
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event) {
		String forwardName = FORWARD_PREFIX + event;
		DefaultLogger.debug(this, ">>>>>>>>>>> " + forwardName);

		return forwardName;
	}
}