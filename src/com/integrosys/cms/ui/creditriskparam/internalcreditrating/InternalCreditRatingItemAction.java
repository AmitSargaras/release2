package com.integrosys.cms.ui.creditriskparam.internalcreditrating;

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
public class InternalCreditRatingItemAction extends InternalCreditRatingAction {

    private Map nameCommandMap;

    public Map getNameCommandMap() {
        return nameCommandMap;
    }

    public void setNameCommandMap(Map nameCommandMap) {
        this.nameCommandMap = nameCommandMap;
    }


	protected ICommand[] getCommandChain(String event) 
	{
		ICommand[] objArray = null;
		// TODO Auto-generated method stub
		if ("maker_create_item".equals(event))
		{
			objArray = new ICommand[1];
//		  objArray[0] = new PrepareInternalCreditRatingItemCommand();
            objArray[0] = (ICommand) getNameCommandMap().get("PrepareInternalCreditRatingItemCommand");
		}
		else if ("maker_edit_item".equals(event))
		{
			objArray = new ICommand[2 ];
//			objArray[0] = new ReadInternalCreditRatingItemCommand();
//			objArray[1] = new PrepareInternalCreditRatingItemCommand();
            objArray[0] = (ICommand) getNameCommandMap().get("ReadInternalCreditRatingItemCommand");
            objArray[1] = (ICommand) getNameCommandMap().get("PrepareInternalCreditRatingItemCommand");
		}
		else if ("maker_create_item_confirm".equals(event) || "maker_submit".equals(event))
		{
			objArray = new ICommand[1];
//			objArray[0] = new SaveInternalCreditRatingItemCommand();
            objArray[0] = (ICommand) getNameCommandMap().get("SaveInternalCreditRatingItemCommand");
		}
		else if("error_return".equals(event))
		{
			objArray = new ICommand[2];
//			objArray[0] = new ErrorReturnItemCommand();
//			objArray[1] = new PrepareInternalCreditRatingItemCommand();
            objArray[0] = (ICommand) getNameCommandMap().get("ErrorReturnItemCommand");
            objArray[1] = (ICommand) getNameCommandMap().get("PrepareInternalCreditRatingItemCommand");
		}
		
		return objArray;
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
	
	
	
	protected String getErrorEvent(String event) {
		String errorEvent = event;
		if ("maker_create_item_confirm".equals(event)||"maker_submit".equals(event))
		{
			return "error_return";
		}
	/*	else if ("maker_submit".equals(event))
		{
			return "error_return";
		}*/
		return errorEvent;
	}
	
	protected boolean isValidationRequired(String event) {
		if ("maker_create_item_confirm".equals(event) ||
			   "maker_submit".equals(event))
		{
			return true;
		}
		return false;
	}
	
	public ActionErrors validateInput(ActionForm aForm, Locale locale)
	{
		return InternalCreditRatingFormValidator.validateInternalCreditRatingItemForm(aForm, locale);
	}
	
	private String getReference(String event, HashMap resultMap, HashMap exceptionMap)
	{
		String fromEvent = (String)(resultMap.get("fromEvent"));
		if ("maker_create_item".equals(event) ||
				 "maker_edit_item".equals(event) || "error_return".equals(event))
		{
			return "update_item";
		}
		else if ("maker_create_item_confirm".equals(event) ||
				      "maker_submit".equals(event) ||
				      "cancel".equals(event))
		{
			return "update_return";
		}
		return event;
	}
	
	
}
