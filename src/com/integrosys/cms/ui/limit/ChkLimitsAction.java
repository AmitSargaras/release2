package com.integrosys.cms.ui.limit;

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
 * @author $Author: pooja $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/02 10:22:39 $ Tag: $Name: $
 */
public class ChkLimitsAction extends CommonAction {

	/**
	 * This method return an Array of Commad Objects responsible for a event
	 * 
	 * @ param event is of type String @ return Icommand Array
	 */
	private static final String REROUTEPAGE = "reroutepage";

	private static final String SUBSTITUTEPAGE = "substitutepage";

	private static final String REASSIGNPAGE = "reassignpage";

	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (EVENT_PREPARE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ViewLimitsCustomerCommand();
		}
		if (EVENT_LIST.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ViewLimitsCustomerCommand();
		}
		if (EVENT_SEARCH.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ViewLimitsCustomerCommand();
		}
		if (REROUTEPAGE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ViewLimitsCustomerCommand();
		}
		if (SUBSTITUTEPAGE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ViewLimitsCustomerCommand();
		}
		if (REASSIGNPAGE.equals(event)) {
			objArray = new ICommand[1];
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
		return null;
	}

	/**
	 * This method is used to determine the next page to be displayed using the
	 * event Result hashmap and exception hashmap.It returns the page object .
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
		String forwardName = "submit_fail";
		if (EVENT_PREPARE.equals(event)) {
			forwardName = "after_prepare";
		}
		if (EVENT_LIST.equals(event)) {
			forwardName = "after_list";
		}
		if (EVENT_SEARCH.equals(event)) {
			forwardName = "after_search";
		}
		if (REROUTEPAGE.equals(event) || REASSIGNPAGE.equals(event) || SUBSTITUTEPAGE.equals(event)) {
			forwardName = event;
		}

		return forwardName;
	}
}
