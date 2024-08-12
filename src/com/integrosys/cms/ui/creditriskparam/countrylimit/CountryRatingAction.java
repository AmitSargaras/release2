/*
* Copyright Integro Technologies Pte Ltd
* $Header: $
*/
package com.integrosys.cms.ui.creditriskparam.countrylimit;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CountryRatingAction extends CountryLimitAction {

    private Map nameCommandMap;

    public Map getNameCommandMap() {
        return nameCommandMap;
    }

    public void setNameCommandMap(Map nameCommandMap) {
        this.nameCommandMap = nameCommandMap;
    }

	protected IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();

        if(resultMap.get("wip")!=null && ((String)resultMap.get("wip")).equals("wip")){
            aPage.setPageReference("wip");
            return aPage;
        }
        
        aPage.setPageReference(getReference(event, resultMap, exceptionMap));
        return aPage;
	}
	
	private String getReference(String event, HashMap resultMap, HashMap exceptionMap)
	{
		String fromEvent = (String)(resultMap.get("fromEvent"));
		if (EventConstant.EVENT_READ.equals(event))
		{
			return "read_item";
		}
		else if (EventConstant.EVENT_PREPARE_CREATE.equals(event) ||
				 EventConstant.EVENT_PREPARE_UPDATE.equals(event))
		{
			return "update_item";
		}
		else if (EventConstant.EVENT_CREATE.equals(event) ||
				 EventConstant.EVENT_SUBMIT.equals(event) ||
				 EventConstant.EVENT_CANCEL.equals(event))
		{
			return "update_return";
		}
		else if (EventConstant.EVENT_READ_RETURN.equals(event))
		{
			return fromEvent + "_return";
		}
		return event;
	}
	
	protected String getErrorEvent(String event) {
		String errorEvent = event;
		if (EventConstant.EVENT_SUBMIT.equals(event))
		{
			return EventConstant.EVENT_PREPARE_CREATE;
		}
		return errorEvent;
	}
	
	protected boolean isValidationRequired(String event) {
		if (EventConstant.EVENT_SUBMIT.equals(event))
		{
			return true;
		}
		return false;
	}
	
	public ActionErrors validateInput(ActionForm aForm, Locale locale)
	{
		return CountryLimitValidator.validateCountryRating(aForm, locale);
	}
	
	protected ICommand[] getCommandChain(String event) 
	{
		ICommand[] objArray = null;
		// TODO Auto-generated method stub
		
		if (EventConstant.EVENT_PREPARE_UPDATE.equals(event))
		{
			objArray = new ICommand[2];
//			objArray[0] = new ReadCountryRatingCmd();
//			objArray[1] = new PrepareCountryRatingCmd();
            objArray[0] = (ICommand) getNameCommandMap().get("ReadCountryRatingCmd");
            objArray[1] = (ICommand) getNameCommandMap().get("PrepareCountryRatingCmd");
		}
		else if (EventConstant.EVENT_READ.equals(event))
		{
			objArray = new ICommand[1];
//			objArray[0] = new ReadCountryRatingCmd();
            objArray[0] = (ICommand) getNameCommandMap().get("ReadCountryRatingCmd");
		}
		else if (EventConstant.EVENT_SUBMIT.equals(event))
		{
			objArray = new ICommand[1];
//			objArray[0] = new SaveCountryRatingCmd();
            objArray[0] = (ICommand) getNameCommandMap().get("SaveCountryRatingCmd");
		}
		else if (EventConstant.EVENT_CANCEL.equals(event) ||
				 EventConstant.EVENT_READ_RETURN.equals(event))
		{
			objArray = new ICommand[0];
		}
		
		return objArray;
	}
}
