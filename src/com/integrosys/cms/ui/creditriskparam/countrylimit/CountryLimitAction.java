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
import com.integrosys.base.techinfra.logger.DefaultLogger;

import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.base.uiinfra.common.IPin;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CountryLimitAction extends CommonAction implements IPin{

    private Map nameCommandMap;

    public Map getNameCommandMap() {
        return nameCommandMap;
    }

    public void setNameCommandMap(Map nameCommandMap) {
        this.nameCommandMap = nameCommandMap;
    }

	/* (non-Javadoc)
	 * @see com.integrosys.base.uiinfra.common.CommonAction#getNextPage(java.lang.String, java.util.HashMap, java.util.HashMap)
	 */
	protected IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();

        if(resultMap.get("wip")!=null && ((String)resultMap.get("wip")).equals("wip")){
            aPage.setPageReference("wip");
            return aPage;
        }
		
		String page = getReference(event);
		
        aPage.setPageReference( page );
        return aPage;
	}
	
	private String getReference(String event)
	{		
		
		if (EventConstant.EVENT_READ.equals(event) ||
				 EventConstant.EVENT_READ_RETURN.equals(event))
		{
			return "read_page";
		}
		else 
		if (	 EventConstant.EVENT_PREPARE_UPDATE.equals(event) ||
				 EventConstant.EVENT_PROCESS_UPDATE.equals(event) ||
				 EventConstant.EVENT_UPDATE_RETURN.equals(event) ||
				 EventConstant.EVENT_ERROR_RETURN.equals(event) ||
				 EventConstant.EVENT_DELETE_ITEM.equals(event))
		{
			return "update_page";
		}
		else if (EventConstant.EVENT_PROCESS.equals(event) ||
				 EventConstant.EVENT_PROCESS_RETURN.equals(event))
		{
			return "process_page";
		}
		else if (EventConstant.EVENT_PREPARE_CLOSE.equals(event) ||
				 EventConstant.EVENT_CLOSE_RETURN.equals(event) ||
				 EventConstant.EVENT_TRACK.equals(event) || 
				 EventConstant.EVENT_TRACK_RETURN.equals(event))
		{
			return "close_page";
		}		
		else if (EventConstant.EVENT_CREATE_ITEM.equals(event))
		{
			return "create_item_detail";
		}
		else if (EventConstant.EVENT_UPDATE_ITEM.equals(event))
		{
			return "update_item_detail";
		}
		else if (EventConstant.EVENT_PREPARE_UPDATE_RATING.equals(event))
		{
			return "update_country_rating";
		}
		else if (EventConstant.EVENT_SUBMIT.equals(event))
		{
			return "ack_submit";
		}
		else if (EventConstant.EVENT_APPROVE.equals(event))
		{
			return "ack_approve";
		}
		else if (EventConstant.EVENT_REJECT.equals(event))
		{
			return "ack_reject";
		}
		else if (EventConstant.EVENT_CLOSE.equals(event))
		{
			return "ack_close";
		}
		else
		{
			return event;
		}
	}
	
	protected String getErrorEvent(String event) {			

    	String errorEvent = event;
    	if (EventConstant.EVENT_SUBMIT.equals(event)||
    	    EventConstant.EVENT_DELETE_ITEM.equals(event))
    	{
    		return EventConstant.EVENT_ERROR_RETURN;
    	}
		else if (EventConstant.EVENT_APPROVE.equals(event) ||
			EventConstant.EVENT_REJECT.equals(event) )
    	{
    		return EventConstant.EVENT_PROCESS_RETURN;
    	}
    	return errorEvent;
	}
	
	protected boolean isValidationRequired(String event) {		

		if (EventConstant.EVENT_SUBMIT.equals(event) ||
			EventConstant.EVENT_APPROVE.equals(event) ||
			EventConstant.EVENT_REJECT.equals(event)  || 
			EventConstant.EVENT_DELETE_ITEM.equals(event)
			)
		{
			return true;
		}
		return false;	
	}
	
	public ActionErrors validateInput(ActionForm aForm, Locale locale)
	{		
		return CountryLimitValidator.validateCountryLimit(aForm, locale);
	}

	/* (non-Javadoc)
	 * @see com.integrosys.base.uiinfra.common.CommonAction#getCommandChain(java.lang.String)
	 */
	protected ICommand[] getCommandChain(String event) {
		ICommand[] objArray = null;
		// TODO Auto-generated method stub
						
		DefaultLogger.debug(this, "In action, event="+event);
		
		if (EventConstant.EVENT_READ.equals(event) ||
				 EventConstant.EVENT_PROCESS.equals(event) ||
				 EventConstant.EVENT_TRACK.equals(event) ||
				 EventConstant.EVENT_PREPARE_CLOSE.equals(event) 
				)
		{
			objArray = new ICommand[1];
//			objArray[0] = new ReadCountryLimitCmd();
            objArray[0] = (ICommand) getNameCommandMap().get("ReadCountryLimitCmd");
		}
		else if (EventConstant.EVENT_PREPARE_UPDATE.equals(event) ||
				 EventConstant.EVENT_PROCESS_UPDATE.equals(event))
		{
			objArray = new ICommand[2];
//			objArray[0] = new ReadCountryLimitCmd();
//			objArray[1] = new PrepareCountryLimitCmd();
            objArray[0] = (ICommand) getNameCommandMap().get("ReadCountryLimitCmd");
            objArray[1] = (ICommand) getNameCommandMap().get("PrepareCountryLimitCmd");
		}
		else if (EventConstant.EVENT_READ_RETURN.equals(event) ||
				 EventConstant.EVENT_CLOSE_RETURN.equals(event) ||
				 EventConstant.EVENT_TRACK_RETURN.equals(event))
		{
			objArray = new ICommand[0];			
		}
		else if (EventConstant.EVENT_UPDATE_RETURN.equals(event))
		{
			objArray = new ICommand[2];
//			objArray[0] = new ReturnCountryLimitCmd();
//			objArray[1] = new PrepareCountryLimitCmd();
            objArray[0] = (ICommand) getNameCommandMap().get("ReturnCountryLimitCmd");
            objArray[1] = (ICommand) getNameCommandMap().get("PrepareCountryLimitCmd");
		}		
		else if (EventConstant.EVENT_PROCESS_RETURN.equals(event))
		{
			objArray = new ICommand[1];
//			objArray[0] = new ReturnCountryLimitCmd();
            objArray[0] = (ICommand) getNameCommandMap().get("ReturnCountryLimitCmd");
		}	
		else if (EventConstant.EVENT_SUBMIT.equals(event))
		{
			objArray = new ICommand[1];
//			objArray[0] = new MakerUpdateCountryLimitCmd();
            objArray[0] = (ICommand) getNameCommandMap().get("MakerUpdateCountryLimitCmd");
		}
		else if (EventConstant.EVENT_APPROVE.equals(event))
		{
			objArray = new ICommand[1];
//			objArray[0] = new CheckerApproveCountryLimitCmd();
            objArray[0] = (ICommand) getNameCommandMap().get("CheckerApproveCountryLimitCmd");
		}
		else if (EventConstant.EVENT_REJECT.equals(event))
		{
			objArray = new ICommand[1];
//			objArray[0] = new CheckerRejectCountryLimitCmd();
            objArray[0] = (ICommand) getNameCommandMap().get("CheckerRejectCountryLimitCmd");
		}
		else if (EventConstant.EVENT_CLOSE.equals(event))
		{
			objArray = new ICommand[1];
//			objArray[0] = new MakerCloseCountryLimitCmd();
            objArray[0] = (ICommand) getNameCommandMap().get("MakerCloseCountryLimitCmd");
		}
		else if (EventConstant.EVENT_CREATE_ITEM.equals(event) ||
				 EventConstant.EVENT_UPDATE_ITEM.equals(event) ||
				 EventConstant.EVENT_PREPARE_UPDATE_RATING.equals(event) 
				 )
		{
			objArray = new ICommand[1];
//			objArray[0] = new SaveCurWorkingCountryLimitCmd();
            objArray[0] = (ICommand) getNameCommandMap().get("SaveCurWorkingCountryLimitCmd");
		}
		else if (EventConstant.EVENT_DELETE_ITEM.equals(event))
		{
			objArray = new ICommand[2];
//			objArray[0] = new DeleteCountryLimitItemCmd();
//			objArray[1] = new PrepareCountryLimitCmd();
            objArray[0] = (ICommand) getNameCommandMap().get("DeleteCountryLimitItemCmd");
            objArray[1] = (ICommand) getNameCommandMap().get("PrepareCountryLimitCmd");
		}
		else if (EventConstant.EVENT_ERROR_RETURN.equals(event))
		{
			objArray = new ICommand[2];
//			objArray[0] = new SaveCurWorkingCountryLimitCmd();
//			objArray[1] = new PrepareCountryLimitCmd();
            objArray[0] = (ICommand) getNameCommandMap().get("SaveCurWorkingCountryLimitCmd");
            objArray[1] = (ICommand) getNameCommandMap().get("PrepareCountryLimitCmd");
		}
		
		return objArray;
	}

}
