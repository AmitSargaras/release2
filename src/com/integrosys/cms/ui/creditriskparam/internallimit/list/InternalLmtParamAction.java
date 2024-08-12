package com.integrosys.cms.ui.creditriskparam.internallimit.list;

import com.integrosys.cms.ui.creditriskparam.internallimit.InternalLimitAction;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import java.util.HashMap;
import java.util.Locale;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.IPin;
import com.integrosys.base.uiinfra.common.Page;

/**
 * Describe this class. Purpose: for Internal Limit Parameter 
 *  Description: Action class for Internal Limit Parameter 
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class InternalLmtParamAction extends InternalLimitAction implements IPin {

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event
	 *            is of type String
	 * @return Icommand Array
	 */
	protected ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		DefaultLogger.debug(this, "getCommandChain::::" + event);
		
		if ("internal_limit_list".equals(event)||"maker_edit_internallimit".equals(event)) 
		{
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("InternalLmtMakerReadCommand");
		} 
		else if("maker_edit_internalLmt_confirm".equals(event) || "maker_resubmit_edit_internalLmt_confirm".equals(event))
		{
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("InternalLmtMakerEditCommand");
		}
		else if("checker_process_internalLmt".equals(event) || "maker_prepare_close".equals(event))
		{
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("InternalLmtCheckerReadCommand");
		}
		else if("checker_approve_internalLmt".equals(event))
		{
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("ApproveInternalLimitListCommand");
		}
		else if("checker_reject_internalLmt".equals(event))
		{
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("RejectInternalLimitListCommand");
		}
		else if("maker_edit_internalLmt_reject".equals(event))
		{
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("InternalLmtMakerReadRejectedCommand");
		}
	  else if("maker_close_internalLmt_confirm".equals(event))
		{
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CloseInternalLimitListCommand");
		}
		else if("to_track".equals(event))
		{
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("InternalLmtCheckerReadCommand");
		}
	
		
		DefaultLogger.debug(this, "Inside doExecute()");
		return (objArray);
	}


 protected IPage getNextPage(String event, HashMap result, HashMap exceptionMap) {
		    Page aPage = new Page();
		
     if(result.get("wip")!=null && (result.get("wip")).equals("wip")){
//			System.out.println("************** getNextPage() wip *************");

    		aPage.setPageReference(getReference("work_in_process"));
    		return aPage;
    	
    	}else{
//		    System.out.println("************** getNextPage() event *************");

    		aPage.setPageReference(getReference(event));
    		return aPage;
    	}
	}
	
	protected String getErrorEvent(String event) {			

    	String errorEvent = event;
    	if ("maker_edit_internalLmt_confirm".equals(event))
    	{
//	    	System.out.println("************** error event *************");
    		return "maker_edit_internallimit";
    	}
    	else if("maker_resubmit_edit_internalLmt_confirm".equals(event))
    	{
	    	return "maker_edit_internalLmt_reject";
    	}
    	else if("checker_approve_internalLmt".equals(event) || "checker_reject_internalLmt".equals(event))
    	{
	    	return "checker_process_internalLmt";
    	}

    	return errorEvent;
	}
	
	protected boolean isValidationRequired(String event) {		

		if ("maker_edit_internalLmt_confirm".equals(event) || "maker_resubmit_edit_internalLmt_confirm".equals(event)
		     || "checker_approve_internalLmt".equals(event) || "checker_reject_internalLmt".equals(event))
		{
			return true;
		}
		return false;	
	}
	
	public ActionErrors validateInput(ActionForm aForm, Locale locale)
	{		
		return InternalLimitListFormValidator.validate((InternalLmtParameterForm)aForm, locale);
	}

	/**
	 * method which determines the forward name for a particular event
	 * 
	 * @param event
	 *            as String
	 * @return String
	 */
	private String getReference(String event) {

		DefaultLogger.debug(this, "Internal Limit Parameter Action class::::" + event);
		
		if ("internal_limit_list".equals(event)) 
		{
			return "internal_limit_list";
		}
		else if ("maker_edit_internallimit".equals(event))
	  {
			return "maker_edit_internallimit";
		}
		else if ("maker_edit_internalLmt_confirm".equals(event))
		{
			return "maker_edit_internalLmt_confirm";
		}
		else if ("maker_resubmit_edit_internalLmt_confirm".equals(event))
		{
			return "maker_resubmit_edit_internalLmt_confirm";
		}
		else if ("checker_process_internalLmt".equals(event))
		{
			return "checker_process_internalLmt";
		}
		else if("to_track".equals(event)) 
		{
      return "after_to_track";
    }
    else if("checker_approve_internalLmt".equals(event))
    {
	    return "checker_approve_internalLmt";  
    }
    else if ("checker_reject_internalLmt".equals(event))
    {
	    return "checker_reject_internalLmt";
    }
    else if("maker_edit_internalLmt_reject".equals(event))
    {
	    return "maker_edit_internalLmt_reject";
    }
    else if("maker_prepare_close".equals(event))
    {
	    return "maker_prepare_close";
    }
    else if("maker_close_internalLmt_confirm".equals(event))
    {
	    return "maker_close_internalLmt_confirm";
    }
    else if("work_in_process".equals(event))
    {
	    return "work_in_process_page";
    }
		else 
		{
			return event;
		}
		
		
		
	} 
	
}