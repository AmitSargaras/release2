package com.integrosys.cms.ui.systemparameters.propertyindex;

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
* Purpose: for Property Valuation By Index
* Description: Action class for Property Index Item
*
* @author $Author$
* @version $Revision$
* @since $Date$
* Tag: $Name$
*/

public class PropertyIdxItemAction extends PropertyIdxAction {

    private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

    protected ICommand[] getCommandChain(String event)
	{
		DefaultLogger.debug(this," PropertyIdx Item's getCommandChain::::" + event );
		ICommand[] objArray = null;
		
		if ("create_items".equals(event))
		{
            return new ICommand[] { (ICommand) getNameCommandMap().get("PreparePropertyIdxItemCmd") };
		}
		else if ("confirm_create_items".equals(event)  || "edit_items".equals(event) || "resubmit_edit_items".equals(event))
		{
            return new ICommand[] { (ICommand) getNameCommandMap().get("SavePropertyIdxItemCmd") };
		}
		else if ("change_list_items".equals(event)) {
            return new ICommand[] { (ICommand) getNameCommandMap().get("MakerChangeListPropertyIdxParameterCmd") };
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
		else if ("cancel_create_items".equals(event) || "confirm_create_items".equals(event))
		{
			return "update_return_create";
		}
		else if("cancel_edit_items".equals(event) || "edit_items".equals(event)) {
			return "update_return_edit";
		}
		else if ("cancel_resubmit_edit_items".equals(event) || "resubmit_edit_items".equals(event)){
			return "update_return_resubmit_edit";
		}
		else if ("change_list_items".equals(event)) {
			return "create_items";
		}
		return event;
	}
	
	protected String getErrorEvent(String event) {
		String errorEvent = event;
		if ("confirm_create_items".equals(event)  || "edit_items".equals(event) || "resubmit_edit_items".equals(event))
		{
			return "create_items";
		}
		return errorEvent;
	}
	
	protected boolean isValidationRequired(String event) {
		DefaultLogger.debug(this," PropertyIdx Item's isValidationRequired::::" + event );
		if ("confirm_create_items".equals(event)  || "edit_items".equals(event) || "resubmit_edit_items".equals(event))
		{
			return true;
		}
		  return false;
	}
	
	public ActionErrors validateInput(ActionForm aForm, Locale locale)
	{
		return PropertyIdxFormValidator.validatePropertyIdxItemForm(aForm, locale);
	}
	
}
