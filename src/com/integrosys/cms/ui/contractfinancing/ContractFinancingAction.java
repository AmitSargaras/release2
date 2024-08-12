//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.contractfinancing;

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
 * Created by IntelliJ IDEA. User: kienleong Date: 2007/03/02 To change this
 * template use Options | File Templates.
 */
public class ContractFinancingAction extends CommonAction implements IContractFinancingUIConstant, IPin {

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;

		if (EVENT_LIST_SUMMARY.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ListContractFinancingSummaryCommand();
		}
		else if (EVENT_PREPARE_FORM.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareContractFinancingCommand();
			objArray[1] = new ListFacilityTypeSummaryCommand();
		}
		else if (EVENT_MAKER_PREPARE_CREATE.equals(event)) {
			objArray = new ICommand[4];
			objArray[0] = new PrepareContractFinancingCommand();
			objArray[1] = new SetLimitDetailsCommand();
			objArray[2] = new ReadContractFinancingCommand();
			objArray[3] = new ListFacilityTypeSummaryCommand();
		}
		else if (EVENT_MAKER_PREPARE_UPDATE.equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new PrepareContractFinancingCommand();
			objArray[1] = new ReadContractFinancingCommand();
			objArray[2] = new ListFacilityTypeSummaryCommand();
		}
		else if (EVENT_VIEW.equals(event) || EVENT_MAKER_PREPARE_DELETE.equals(event) || EVENT_PROCESS.equals(event)
				|| EVENT_MAKER_PREPARE_CLOSE.equals(event) || EVENT_TO_TRACK.equals(event)
				|| EVENT_MAKER_PROCESS.equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new PrepareContractFinancingCommand();
			objArray[1] = new ReadContractFinancingCommand();
			objArray[2] = new ListFacilityTypeSummaryCommand();
		}
		else if (EVENT_SAVE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SaveContractFinancingCommand();
		}
		else if (EVENT_SUBMIT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SubmitContractFinancingCommand();
		}
		else if (EVENT_MAKER_NAVIGATE_TAB.equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new ProcessContractFinancingCommand();
			objArray[1] = new NavigateContractFinancingCommand();
			objArray[2] = new ReadContractFinancingCommand();
		}
		else if (EVENT_APPROVE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ApproveContractFinancingCommand();
		}
		else if (EVENT_REJECT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RejectContractFinancingCommand();
		}
		else if (EVENT_MAKER_CLOSE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CloseContractFinancingCommand();
		}
		else if (EVENT_DELETE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new DeleteContractFinancingCommand();
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
		return ContractDetailValidator.validateInput((ContractDetailForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
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