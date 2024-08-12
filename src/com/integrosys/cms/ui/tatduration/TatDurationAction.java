/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/tat/TatAction.java,v 1.19 2004/09/03 07:28:25 pooja Exp $
 */

package com.integrosys.cms.ui.tatduration;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.tatduration.trx.ITatParamTrxValue;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;

/**
 * @author $Author: pooja $<br>
 * @version $Revision: 1.19 $
 * @since $Date: 2004/09/03 07:28:25 $ Tag: $Name: $
 */
public class TatDurationAction extends CommonAction {

	/**
	 * This method return an Array of Commad Objects responsible for a event
	 * 
	 * @ param event is of type String @ return Icommand Array
	 */
	
	private Map nameCommandMap;

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	public ICommand[] getCommandChain(String event) 
	{
		ICommand objArray[] = null;

		// using TatCommand for unit testing
		if ("view_app_list".equals(event)) 
		{
			return new ICommand[] { (ICommand) getNameCommandMap().get("ReadAppListCmd") };
		}
		
		if ("read_stage_list".equals(event) || "edit_stage_list".equals(event)) 
		{
			return new ICommand[] { (ICommand) getNameCommandMap().get("ReadTatParamCmd") };
		}
		
		if ("submit_tat_duration".equals(event)) 
		{
			return new ICommand[] { (ICommand) getNameCommandMap().get("MakerSubmitTatParamDurationCmd") };
		}
		
		if ("checker_process_submit".equals(event)) 
		{
			return new ICommand[]{ (ICommand) getNameCommandMap().get("CheckerProcessTatParamCmd") };
		}
		
		if ("checker_confirm_approve_edit".equals(event)) 
		{
			return new ICommand[]{ (ICommand) getNameCommandMap().get("CheckerApproveSubmitTatParamCmd") };
		}
		
		if ("checker_confirm_reject_edit".equals(event)) 
		{
			return new ICommand[]{ (ICommand) getNameCommandMap().get("CheckerRejectSubmitTatParamCmd") };
		}
		
		if ("maker_prepare_close".equals(event)) 
		{
			return new ICommand[]{ (ICommand) getNameCommandMap().get("CheckerProcessTatParamCmd") };
		}
		
		if ("maker_confirm_close".equals(event)) 
		{
			return new ICommand[]{ (ICommand) getNameCommandMap().get("MakerCloseTatParamCmd") };
		}
		
		if ("to_track".equals(event) || "checker_view_property_index".equals(event)) 
			return new ICommand[]{ (ICommand) getNameCommandMap().get("ReadTatParamCmd") };
		
		return objArray;
	}
	
	public Map getNameCommandMap() 
	{
		return nameCommandMap;
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
		return TatDurationFormValidator.validateInput((TatDurationForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) 
	{
		boolean result = false;
//		if (event.equals("update")) {
//			result = true;
//		}
//		if (event.equals("updateBflInd")) {
//			DefaultLogger.debug(this, "entered       updateBflInd");
//			result = true;
//		}
		if (event.equals("submit_tat_duration")) {
			result = true;
		}
//		if (event.equals("refresh")) {
//			result = true;
//		}
//		if (event.equals("refreshresubmit")) {
//			result = true;
//		}
		return result;
	}

	protected String getErrorEvent(String event)
	{
		String errorEvent = getDefaultEvent();
//		if (("update".equals(event)) || ("refresh".equals(event))) {
//			errorEvent = "prepare_form";
//		}
//		if (("updateBflInd".equals(event)) || ("refresh".equals(event))) {
//			errorEvent = "prepare_form";
//		}
//		if (("submit".equals(event)) || ("refreshresubmit".equals(event))) {
//			errorEvent = "prepare_form1";
//		}

        if ("submit_tat_duration".equals(event)) {
            errorEvent = "edit_stage_list";
        }
		return errorEvent;
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
    boolean isWip = false;
	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) 
	{
		Page aPage = new Page();

        ITatParamTrxValue trxValue = (ITatParamTrxValue) resultMap.get("ITatParamTrxValue");
		if (trxValue != null) {
			String status = trxValue.getStatus();
			isWip = status.equals(ICMSConstant.STATE_PENDING_UPDATE)
					|| status.equals(ICMSConstant.STATE_REJECTED);
		} else {
			DefaultLogger.debug(this,"trxValue  is null");
		}

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
		if ("view_app_list".equals(event)) 
			forwardName = "view_app_list";

		if ("read_stage_list".equals(event) || "edit_stage_list".equals(event)) {
            if (isWip) {
                forwardName = "workInProgress";
            } else {
		        forwardName = "view_stage_list";
            }
        }
		
		if("submit_tat_duration".equals(event))
			forwardName = "after_submit_tat_duration";
		
		if("checker_process_submit".equals(event))
			forwardName = "checker_prepare_update";
		
		if("checker_confirm_approve_edit".equals(event) || "checker_confirm_reject_edit".equals(event))
			forwardName = "after_checker_process_tat_duration";
		
		if("maker_confirm_close".equals(event))
			forwardName = "after_submit_tat_duration";
		
		if ("to_track".equals(event) || "checker_view_property_index".equals(event) || "maker_prepare_close".equals(event))
            return "view_property_index";
		
		return forwardName;
	}
}
