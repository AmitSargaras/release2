/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.bizstructure;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;

/**
 * @author $Author: ravi $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/17 15:39:16 $ Tag: $Name: $
 */
public class AddUserAction extends BizStructureIPinAction {

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if ("start".equals(event) || "start_rejected".equals(event) || "list".equals(event)
				|| "list_rejected".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("ListUsersCmd");
		}
		else if ("add_users_for_add".equals(event) || "add_users_for_add_reject".equals(event)
				|| "add_users_for_edit".equals(event) || "add_users_for_edit_reject".equals(event)
				|| "cancle_add_users_for_add".equals(event) || "cancle_add_users_for_add_reject".equals(event)
				|| "cancle_add_users_for_edit".equals(event) || "cancle_add_users_for_edit_reject".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("ForwardMapCmd");
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

		if ("start".equals(event)) {
			forwardName = "after_start";
		}
		else if ("start_rejected".equals(event)) {
			forwardName = "after_start_rejected";
		}
		else if ("list".equals(event)) {
			forwardName = "after_start";
		}
		else if ("list_rejected".equals(event)) {
			forwardName = "after_start_rejected";
		}
		else if ("add_users_for_add".equals(event)) {
			forwardName = "add_users_for_add";
		}
		else if ("add_users_for_add_reject".equals(event)) {
			forwardName = "add_users_for_add_reject";
		}
		else if ("add_users_for_edit".equals(event)) {
			forwardName = "add_users_for_edit";
		}
		else if ("add_users_for_edit_reject".equals(event)) {
			forwardName = "add_users_for_edit_reject";
		}
		else if ("cancle_add_users_for_add".equals(event)) {
			forwardName = "add_users_for_add";
		}
		else if ("cancle_add_users_for_add_reject".equals(event)) {
			forwardName = "add_users_for_add_reject";
		}
		else if ("cancle_add_users_for_edit".equals(event)) {
			forwardName = "add_users_for_edit";
		}
		else if ("cancle_add_users_for_edit_reject".equals(event)) {
			forwardName = "add_users_for_edit_reject";
		}
		return forwardName;
	}
}
