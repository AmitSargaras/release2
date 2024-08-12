/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/systemparameters/assetlife/MaintainAssetLifeAction.java,v 1.1 2007/01/30 Jerlin Exp $
 */
package com.integrosys.cms.ui.systemparameters.assetlife;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;

/**
 * Describe this class. Purpose: for Asset Life Description: Action class for
 * Asset Life
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: 1$
 * @since $Date: 2007/01/30$ Tag: $Name$
 */

public class MaintainAssetLifeAction extends CommonAction {

	private Map nameCommandMap;

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	protected ICommand getCommand(String name) {
		ICommand command = (ICommand) getNameCommandMap().get(name);
		Validate.notNull(command, "not able to get command given name [" + name + "]");

		return command;
	}

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if ("maker_view_assetlife".equals(event) || "maker_edit_assetlife".equals(event) || EVENT_VIEW.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("AssetLifeMakerReadCmd");
		}
		else if ("maker_edit_assetlife_confirm".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("AssetLifeMakerEditCmd");
		}
		else if ("checker_edit_assetlife".equals(event) || "maker_close_assetlife".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("AssetLifeCheckerReadCmd");
		}
		else if ("checker_approve_edit_assetlife".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = getCommand("AssetLifeCheckerApproveEditCmd");
			objArray[1] = getCommand("ReloadAssetValuationProfilesCmd");
		}
		else if ("checker_reject_edit_assetlife".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("AssetLifeCheckerRejectEditCmd");
		}
		else if ("maker_close_assetlife_confirm".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("AssetLifeMakerCancelEditCmd");
		}
		else if ("maker_edit_assetlife_reject".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("AssetLifeMakerReadRejectedCmd");
		}
		else if ("to_track".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = getCommand("AssetLifeCheckerReadCmd");
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
		return MaintainAssetLifeValidator.validateInput((MaintainAssetLifeForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals("maker_edit_assetlife_confirm")) {
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
			//System.out.println("************** getNextPage() wip *************"
			// );

			aPage.setPageReference(getReference("work_in_process"));
			return aPage;

		}
		else {
			// System.out.println(
			// "************** getNextPage() event *************");

			aPage.setPageReference(getReference(event));
			return aPage;
		}
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();

		if ("maker_edit_assetlife_confirm".equals(event)) {
			errorEvent = "maker_edit_assetlife_confirm_error";
		}

		return errorEvent;
	}

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event) {
		String forwardName = null;

		// System.out.println("-----------------------------EVENT : "+ event);

		if ("maker_view_assetlife".equals(event)) {
			forwardName = "maker_view_assetlife_page";

		}
		else if ("maker_edit_assetlife_reject".equals(event)) {
			forwardName = "maker_edit_assetlife_page";

		}
		else if ("maker_edit_assetlife".equals(event) || "maker_edit_assetlife_confirm_error".equals(event)) {
			forwardName = "maker_edit_assetlife_page";

		}
		else if ("maker_edit_assetlife_confirm".equals(event)) {
			forwardName = "maker_edit_assetlife_confirm";

		}
		else if ("work_in_process".equals(event)) {
			forwardName = "work_in_process_page";

		}
		else if ("checker_edit_assetlife".equals(event)) {
			forwardName = "checker_edit_assetlife_page";

		}
		else if ("checker_approve_edit_assetlife".equals(event)) {
			forwardName = "common_approve_page";

		}
		else if ("checker_reject_edit_assetlife".equals(event)) {
			forwardName = "common_reject_page";

		}
		else if ("maker_close_assetlife".equals(event)) {
			forwardName = "maker_close_assetlife_page";

		}
		else if ("maker_close_assetlife_confirm".equals(event)) {
			forwardName = "common_close_page";

		}
		else if (EVENT_VIEW.equals(event)) {
			forwardName = "view";

		}
		else if ("to_track".equals(event)) {
			forwardName = "after_to_track";
		}

		// System.out.println("-------------------------Forward Name : " +
		// forwardName);

		return forwardName;
	}
}
