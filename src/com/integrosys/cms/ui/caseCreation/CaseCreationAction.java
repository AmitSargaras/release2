/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.caseCreation;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.IPin;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.app.holiday.bus.HolidayException;
import com.integrosys.cms.ui.caseCreationUpdate.ListOptionsCmd;

/**
 * @author $Author: Abhijit R$ Action For Holiday
 */
public class CaseCreationAction extends CommonAction implements IPin {

	
	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event
	 *            is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {

//		System.out.println("In Maintain Holiday Action with event --" + event);

		ICommand objArray[] = null;
		if (event.equals("CaseCreationListViewCommand")) {
			objArray = new ICommand[2];
			objArray[0] = new ListCaseCreationCmd();
			objArray[1] = new ListOptionsCmd();

		} else if ((event != null) && event.equals("CaseCreationRequestCommand")) {
			objArray = new ICommand[1];
			objArray[0] = new MakerCreateCaseCreationCmd();
		}  
		else if (event.equals("refresh_coordinator_detail")) {
			objArray = new ICommand[1];
			objArray[0] = new RefreshCoordinatorDetailCmd();
		}

		return (objArray);
	}

	/**
	 * This method is called only for create and Update command to validate the
	 * form and return the ActionErrors object.
	 * 
	 * @param aForm
	 *            is of type ActionForm
	 * @param locale
	 *            of type Locale
	 * @return ActionErrors
	 */
	public ActionErrors validateInput(ActionForm aForm, Locale locale)  {
		return CaseCreationValidator.validateInput(aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals("CaseCreationRequestCommand"))

		{
			result = true;
		}
		return result;
	}

	/**
	 * This method is used to determine which the page to be displayed next
	 * using the event Result hashmap and exception hashmap.It returns the page
	 * object .
	 * 
	 * @param event
	 *            is of type String
	 * @param resultMap
	 *            is of type HashMap
	 * @param exceptionMap
	 *            is of type HashMap
	 * @return IPage
	 */
	public IPage getNextPage(String event, HashMap resultMap,
			HashMap exceptionMap) {
		Page aPage = new Page();
		DefaultLogger.debug(this, " Exception map error is "
				+ exceptionMap.isEmpty());
			aPage.setPageReference(getReference(event));
			return aPage;

		
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if ("CaseCreationRequestCommand".equals(event)) {
			errorEvent = "CaseCreationListViewCommand";
		}
		
		return errorEvent;
	}

	/**
	 * method which determines the forward name for a particular event
	 * 
	 * @param event
	 *            as String
	 * @return String
	 */
	private String getReference(String event) {
		String forwardName = null;
		if (event.equals("CaseCreationListViewCommand")	) {
			forwardName = "view_case_page";
		} else if ((event != null) && event.equals("CaseCreationRequestCommand")) {
			forwardName = "common_submit_page";
		} else if ((event != null)
				&& (event.equals("refresh_coordinator_detail"))) {
			forwardName = "refresh_coordinator_detail";
		}

		return forwardName;
	}

}