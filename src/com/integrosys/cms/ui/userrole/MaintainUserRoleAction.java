/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.userrole;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;

/**
 * @author $Author: lyng $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2003/11/27 07:38:01 $ Tag: $Name: $
 */
public class MaintainUserRoleAction extends BizStructureIPinAction {
	
    //Andy Wong, 2 Jan 2008: define first sorting criteria when displaying team member
    public static final String FIRST_SORT = "ABBREVIATION";
	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if ((event != null) && event.equals("start")) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("ListTeamTypesCmd");
		}
		else if ("redirect_list_user_role".equals(event) || "maker_list_user_role".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("MakerListUserRoleCmd");
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
		return null;
	}

	protected boolean isValidationRequired(String event) {
		return false;
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
			aPage.setPageReference(getReference("work_in_process"));
			return aPage;
		}
		else {
			aPage.setPageReference(getReference(event));
			return aPage;
		}
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		return errorEvent;
	}

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event) {
		String forwardName = null;
		if ((event != null) && event.equals("start")) {
			forwardName = "after_start";
		}
		else if ((event != null) && event.equals("maker_list_user_role")) {
			forwardName = "maker_list_user_role_page";
		}
		else if ((event != null) && event.equals("redirect_list_user_role")) {
			forwardName = "common_submit_frame_page";
		}
		return forwardName;
	}
}