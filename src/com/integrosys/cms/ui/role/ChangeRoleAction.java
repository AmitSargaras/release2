package com.integrosys.cms.ui.role;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;

public class ChangeRoleAction extends CommonAction {

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if ("changeRole".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ChangeRoleCommand();
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
		// ICommonUser lu = (ICommonUser) resultMap.get(IGlobalConstant.USER);
		// //System.out.println("From Welcome Action -- User --"+ lu);
		// ITeam team = (ITeam) resultMap.get(IGlobalConstant.USER_TEAM);
		// //System.out.println("From Welcome Action -- Team --"+ team);
		// ITeamMembership membership = null;
		// String memType =
		// (String)resultMap.get(IGlobalConstant.TEAM_TYPE_MEMBERSHIP_TYPE_NAME
		// );
		// System.out.println("VVVVVV WelcomeAction "+memType);
		// try {
		// if(memType == null ||memType.trim().length()==0 )
		// throw new Exception("ITeamMembership not resolved !"); //CR33
		// } catch(Throwable be) {
		// throw new RuntimeException("ITeamMembership not resolved !");
		// }
		// // role is a combination of team type(get the biz code) and
		// membership type
		// String teamType = team.getTeamType().getBusinessCode();
		// //String memType =
		// membership.getTeamTypeMembership().getMembershipType
		// ().getMembershipTypeName();
		// aPage.setPageReference(getReference(event,
		// teamType.toUpperCase()+memType.toUpperCase()));
		// return aPage; Page aPage = new Page();

		aPage.setPageReference("welcome");

		return aPage;
	}

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event, String role) {
		// TODO: use constants instead
		// System.out.println("Test --- returning forwardName --- >"+role);
		return role;
	}

}
