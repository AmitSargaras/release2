package com.integrosys.cms.ui.bridgingloan;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.IPin;
import com.integrosys.base.uiinfra.common.Page;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class BridgingLoanAction extends CommonAction implements IBridgingLoanUIConstant, IPin {
	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;

		if (EVENT_LIST_SUMMARY.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ListBridgingLoanSummaryCommand();
		}
		else if (EVENT_PREPARE_FORM.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareBridgingLoanCommand();
			objArray[1] = new ListPropertyTypeSummaryCommand();
		}
		else if (EVENT_MAKER_PREPARE_CREATE.equals(event)) { // save and submit
																// might be able
																// to combine
																// into 1
																// command
			objArray = new ICommand[4];
			objArray[0] = new PrepareBridgingLoanCommand();
			objArray[1] = new SetLimitDetailsCommand();
			objArray[2] = new ReadBridgingLoanCommand();
			objArray[3] = new ListPropertyTypeSummaryCommand();
		}
		else if (EVENT_MAKER_PREPARE_UPDATE.equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new PrepareBridgingLoanCommand();
			objArray[1] = new ReadBridgingLoanCommand();
			objArray[2] = new ListPropertyTypeSummaryCommand();
		}
		else if (EVENT_VIEW.equals(event) || EVENT_MAKER_PREPARE_DELETE.equals(event) || EVENT_PROCESS.equals(event)
				|| EVENT_MAKER_PREPARE_CLOSE.equals(event) || EVENT_TO_TRACK.equals(event)
				|| EVENT_MAKER_PROCESS.equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new PrepareBridgingLoanCommand();
			objArray[1] = new ReadBridgingLoanCommand();
			objArray[2] = new ListPropertyTypeSummaryCommand();
		}
		else if (EVENT_SAVE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SaveBridgingLoanCommand();
		}
		else if (EVENT_SUBMIT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SubmitBridgingLoanCommand();
		}
		else if (EVENT_MAKER_NAVIGATE_TAB.equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new ProcessBridgingLoanCommand();
			objArray[1] = new NavigateBridgingLoanCommand();
			objArray[2] = new ReadBridgingLoanCommand();
		}
		else if (EVENT_APPROVE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ApproveBridgingLoanCommand();
		}
		else if (EVENT_REJECT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RejectBridgingLoanCommand();
		}
		else if (EVENT_MAKER_CLOSE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CloseBridgingLoanCommand();
		}
		else if (EVENT_DELETE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new DeleteBridgingLoanCommand();
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
		return BridgingLoanValidator.validateInput((BridgingLoanForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		DefaultLogger.debug(this, "Inside isValidationRequired");
		DefaultLogger.debug(this, "event=" + event);
		if (EVENT_SUBMIT.equals(event) || EVENT_SAVE.equals(event) || EVENT_MAKER_NAVIGATE_TAB.equals(event)) {
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

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();

		if (EVENT_SUBMIT.equals(event) || EVENT_SAVE.equals(event) || EVENT_MAKER_NAVIGATE_TAB.equals(event)) {
			errorEvent = EVENT_PREPARE_FORM;
		}
		return errorEvent;
	}

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event) {
		String forwardName = FORWARD_PREFIX + event;
		DefaultLogger.debug(this, ">>>>>>>>>>> " + forwardName);
		return forwardName;
	}
}