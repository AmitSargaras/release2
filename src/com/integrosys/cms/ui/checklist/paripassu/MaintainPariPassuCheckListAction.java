/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/checklist/document/DocumentCheckListAction.java,v 1.4 2005/10/14 13:26:44 lyng Exp $
 */
package com.integrosys.cms.ui.checklist.paripassu;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.checklist.camreceipt.FrameFlagSetterCommand;
import com.integrosys.cms.ui.checklist.camreceipt.PrepareCommand;
import com.integrosys.cms.ui.checklist.camreceipt.SaveCheckListItemCommand;
import com.integrosys.cms.ui.checklist.camreceipt.UpdateShareCheckListCommand;

/**
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/10/14 13:26:44 $ Tag: $Name: $
 */
public class MaintainPariPassuCheckListAction extends CommonAction {

	private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}
	
	private boolean isMaintainChecklistWithoutApproval = false;
	public static final String LIST = "list";
	public static final String SUBMIT = "submit";
	public static final String MAINTAIN = "maintain";


	/**
	 * This method return a Array of Command Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return ICommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this, "event: " + event);
		ICommand objArray[] = null;
		if(event.equals(LIST))
		{
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"ListPariPassuCheckListCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("FrameFlagSetterCommand");
		}
		else if(event.equals(MAINTAIN))
		{
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MaintainPariPassuCmd");
			objArray[1] = (ICommand) getNameCommandMap().get("FrameFlagSetterCommand");
		}
		else if(event.equals(SUBMIT))
		{
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"SubmitPariPassuCmd");
			objArray[1] = (ICommand) getNameCommandMap().get("FrameFlagSetterCommand");
		}
		else if(event.equals("checker_list"))
		{
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"ListPariPassuCheckListCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("FrameFlagSetterCommand");
		}
		else if(event.equals("view_maintain"))
		{
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MaintainPariPassuCmd");
			objArray[1] = (ICommand) getNameCommandMap().get("FrameFlagSetterCommand");
		}

		DefaultLogger.debug(this, "event============="+event);
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

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		return result;
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
		
		if ((resultMap.get("wip") != null) && ((String) resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference("wip");
			return aPage;
		}
		aPage.setPageReference(getReference(event));
		return aPage;
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
		if ((event != null)
			&& (event.equals(LIST))) {
		forwardName = "list";
		} 
		if ((event != null)
				&& (event.equals(MAINTAIN))) {
			forwardName = "maintain";
			} 
		
		if ((event != null)
				&& (event.equals(SUBMIT))) {
			if (this.isMaintainChecklistWithoutApproval) {
				forwardName = "after_submit_wo_approval";
			}
			else {
				forwardName = "after_submit";
			}
		} 
		if ((event != null)
				&& (event.equals("checker_list"))) {
			forwardName = "checker_list";
			} 
		
		if ((event != null)
				&& (event.equals("view_maintain"))) {
			forwardName = "view_maintain";
			} 


	
		return forwardName;
	}


	public void setMaintainChecklistWithoutApproval(
			boolean isMaintainChecklistWithoutApproval) {
		this.isMaintainChecklistWithoutApproval = isMaintainChecklistWithoutApproval;
	}
}