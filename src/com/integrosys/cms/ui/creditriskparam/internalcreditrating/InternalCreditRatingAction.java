package com.integrosys.cms.ui.creditriskparam.internalcreditrating;

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
 * Describe this class. Purpose: for Internal Limit Parameter 
 *  Description: Action class for Internal Limit Parameter 
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class InternalCreditRatingAction extends CommonAction implements IPin {

    private Map nameCommandMap;

    public Map getNameCommandMap() {
        return nameCommandMap;
    }

    public void setNameCommandMap(Map nameCommandMap) {
        this.nameCommandMap = nameCommandMap;
    }

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
		
		if ("credit_rating_list".equals(event) || "checker_prepare_process".equals(event)
		     || "maker_prepare_close".equals(event) || "maker_prepare_update".equals(event)) 
		{
			objArray = new ICommand[1];
//			objArray[0] = new ListInternalCreditRatingCommand();
            objArray[0] = (ICommand) getNameCommandMap().get("ListInternalCreditRatingCommand");
		} 
		else if("maker_create_item".equals(event) || "maker_edit_item".equals(event))
		{
			objArray = new ICommand[0];
		}
		else if("maker_return_add_item".equals(event))
		{
			objArray = new ICommand[2];
//			objArray[0] = new ReturnInternalCreditRatingCommand();
//			objArray[1] = new PrepareInternalCreditRatingCommand();
            objArray[0] = (ICommand) getNameCommandMap().get("ReturnInternalCreditRatingCommand");
            objArray[1] = (ICommand) getNameCommandMap().get("PrepareInternalCreditRatingCommand");
		}
		else if("maker_submit_internalCreditrating".equals(event))
		{
			objArray = new ICommand[1];
//			objArray[0] = new MakerUpdateInternalCreditRatingCommand();
            objArray[0] = (ICommand) getNameCommandMap().get("MakerUpdateInternalCreditRatingCommand");
		}
		else if("checker_approve_internalCreditrating".equals(event))
		{
			objArray = new ICommand[1];
//			objArray[0] = new CheckerApproveInternalCreditRatingCommand();
            objArray[0] = (ICommand) getNameCommandMap().get("CheckerApproveInternalCreditRatingCommand");
		}
		else if("checker_reject_internalCreditrating".equals(event))
		{
			objArray = new ICommand[1];
//			objArray[0] = new CheckerRejectInternalCreditRatingCommand();
            objArray[0] = (ICommand) getNameCommandMap().get("CheckerRejectInternalCreditRatingCommand");
		}
		else if("maker_close_internalCreditrating_confirm".equals(event))
		{
			objArray = new ICommand[1];
//			objArray[0] = new MakerCloseInternalCreditRatingCommand();
            objArray[0] = (ICommand) getNameCommandMap().get("MakerCloseInternalCreditRatingCommand");
		}
		else if("to_track".equals(event))
		{
			objArray = new ICommand[1];
//			objArray[0] = new ListInternalCreditRatingCommand();
            objArray[0] = (ICommand) getNameCommandMap().get("ListInternalCreditRatingCommand");
		}
		else if("maker_delete_item".equals(event))
		{
			objArray = new ICommand[1];
//			objArray[0] = new DeleteInternalCreditRatingItemCommand();
            objArray[0] = (ICommand) getNameCommandMap().get("DeleteInternalCreditRatingItemCommand");
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
    	if ("maker_submit_internalCreditrating".equals(event) || "maker_delete_item".equals(event))
    	{
//	    	System.out.println("************** error event *************");
    		return "maker_return_add_item";
    	}else if("checker_approve_internalCreditrating".equals(event) || "checker_reject_internalCreditrating".equals(event))
    	{
	    	return "checker_prepare_process";
    	}
    	
    	
    	return errorEvent;
	}
	
	protected boolean isValidationRequired(String event) {		

		if ("maker_submit_internalCreditrating".equals(event) ||"maker_delete_item".equals(event)
		     || "checker_approve_internalCreditrating".equals(event)
		     || "checker_reject_internalCreditrating".equals(event))
		{
			return true;
		}
		return false;	
	}
	
	public ActionErrors validateInput(ActionForm aForm, Locale locale)
	{		
		return InternalCreditRatingFormValidator.validateInternalCreditRatingForm((InternalCreditRatingForm)aForm, locale);
	}

	/**
	 * method which determines the forward name for a particular event
	 * 
	 * @param event
	 *            as String
	 * @return String
	 */
	private String getReference(String event) {

		DefaultLogger.debug(this, "Internal Credit Rating Parameter Action class::::" + event);
		
		if ("credit_rating_list".equals(event) || "maker_return_add_item".equals(event) 
		     ||"maker_delete_item".equals(event) || "maker_prepare_update".equals(event)) 
		{
			return "credit_rating_list";
		}
		else if("maker_create_item".equals(event))
		{
			return "maker_create_item";
		}
		else if("maker_edit_item".equals(event))
		{
			return "maker_edit_item";
		}
		else if("maker_submit_internalCreditrating".equals(event))
		{
			return "maker_submit_internalCreditrating";
		}
		else if("checker_approve_internalCreditrating".equals(event))
		{
			return "approve_page";
		}
		else if("checker_reject_internalCreditrating".equals(event))
		{
			return "reject_page";
		}
		else if("maker_close_internalCreditrating_confirm".equals(event))
		{
			return "close_page";
		}
		else if("work_in_process".equals(event))
    {
	    return "work_in_process_page";
    }
    else if("to_track".equals(event))
    {
	    return "after_to_track";
    }
    else if("maker_prepare_close".equals(event))
    {
	    return "maker_prepare_close";
    }
    else if ("checker_prepare_process".equals(event) || "error_return".equals(event))
    {
	    return "checker_prepare_process";
    }
		else 
		{
			return event;
		}
		
		
		
	} 
	
}