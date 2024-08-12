package com.integrosys.cms.ui.securityenvelope;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;

/**
* Describe this class.
* Purpose: for Security Envelope By Location
* Description: Action class for Security Envelope Item
*
* @author $Author$
* @version $Revision$
* @since $Date$
* Tag: $Name$
*/

public class SecEnvelopeItemAction extends SecEnvelopeAction {

  private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

    protected ICommand[] getCommandChain(String event)
	{
		DefaultLogger.debug(this," Security Envelope Item's getCommandChain::::" + event );
		ICommand[] objArray = null;
		
		if ("create_items".equals(event))
		{
            return new ICommand[] { (ICommand) getNameCommandMap().get("PrepareSecEnvelopeItemCmd") };
		}
		else if ("confirm_create_items".equals(event) || "create_itemdetail".equals(event) || "edit_items".equals(event) || "edit_itemdetail".equals(event) || "resubmit_edit_items".equals(event) || "resubmit_edit_itemdetail".equals(event))
		{
            return new ICommand[] { (ICommand) getNameCommandMap().get("SaveSecEnvelopeItemCmd") };
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
	
	private String getReference(String event, HashMap resultMap, HashMap exceptionMap)
	{ 
		if ("create_items".equals(event))
		{
			return "create_items";
		}
		else if ("cancel_create_items".equals(event) || "confirm_create_items".equals(event) || "cancel_create_itemdetail".equals(event) || "create_itemdetail".equals(event))
		{
			return "update_return_create";
		}
		else if("cancel_edit_items".equals(event) || "edit_items".equals(event) || "cancel_edit_itemdetail".equals(event) || "edit_itemdetail".equals(event)) {
			return "update_return_edit";
		}
		else if ("cancel_resubmit_edit_items".equals(event) || "resubmit_edit_items".equals(event) || "cancel_resubmit_edit_itemdetail".equals(event) || "resubmit_edit_itemdetail".equals(event)){
			return "update_return_resubmit_edit";
		}
		else if ("change_list_items".equals(event)) {
			return "create_items";
		}
		return event;
	}
	
	protected String getErrorEvent(String event) {
		String errorEvent = event;
		if ("confirm_create_items".equals(event) || "create_itemdetail".equals(event)
         || "edit_items".equals(event) || "edit_itemdetail".equals(event)
         || "resubmit_edit_items".equals(event) || "resubmit_edit_itemdetail".equals(event))
		{
			return "create_items";
		}
		return errorEvent;
	}
	
	protected boolean isValidationRequired(String event) {
		DefaultLogger.debug(this," Security Envelope Item's isValidationRequired::::" + event );
		if ("confirm_create_items".equals(event)  || "create_itemdetail".equals(event) ||
            "edit_items".equals(event) || "edit_itemdetail".equals(event) ||
            "resubmit_edit_items".equals(event) || "resubmit_edit_itemdetail".equals(event))
		{
			return true;
		}
		  return false;
	}
	
	public ActionErrors validateInput(ActionForm aForm, Locale locale)
	{
				return SecEnvelopeFormValidator.validateSecEnvelopeItemForm(aForm, locale);
	}
	
}
