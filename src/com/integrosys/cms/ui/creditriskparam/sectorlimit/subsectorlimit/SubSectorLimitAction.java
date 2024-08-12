package com.integrosys.cms.ui.creditriskparam.sectorlimit.subsectorlimit;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.creditriskparam.sectorlimit.SectorLimitAction;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import java.util.HashMap;
import java.util.Locale;

public class SubSectorLimitAction extends SectorLimitAction {

	public final static String EVENT_RETURN_FRM_ECO = "return_frm_ecoSector";
	public final static String EVENT_DELETE_ECO_ITEM = "delete_eco_item";
	public final static String EVENT_EDIT_ECO_ITEM = "edit_item";
	public final static String EVENT_READ_SUBITEM = "read_subitem";
	public final static String EVENT_ADD_ITEMS = "add_eco_items";
    public final static String EVENT_CHECKER_VIEW_SUBITEM = "checker_view_subitem";
    public final static String EVENT_CHECKER_SUB_RETURN = "checker_sub_return";
	
    protected ICommand[] getCommandChain(String event) {
        ICommand[] objArray = null;
        DefaultLogger.debug(this, "<<<<< commandchain event: "+event);
        
        if (EVENT_PREPARE_UPDATE.equals(event) ||
        		EVENT_PREPARE.equals(event)) {
        	objArray = new ICommand[2];
        	objArray[0] = (ICommand) getNameCommandMap().get("ReadSubSectorLmtCmd") ;
        	objArray[1] = (ICommand) getNameCommandMap().get("PrepareSubSectorLmtCmd") ;
        } else if (EVENT_CREATE.equals(event)) {
            return new ICommand[] { (ICommand) getNameCommandMap().get("AddSubSectorLmtCmd") };
        	//objArray = new ICommand[1];
        	//objArray[0] = new AddSubSectorLmtCmd();
        } else if (EVENT_UPDATE.equals(event)) {
            return new ICommand[] { (ICommand) getNameCommandMap().get("UpdateSubSectorLmtCmd") };
        } else if (EVENT_ERROR_RETURN.equals(event)) {
            return new ICommand[] { (ICommand) getNameCommandMap().get("PrepareSubSectorLmtCmd") };
        }else if (EVENT_ADD_ITEM.equals(event) ||
        		EVENT_EDIT_ECO_ITEM.equals(event)||
        		EVENT_ADD_ITEMS.equals(event)) {
            return new ICommand[] { (ICommand) getNameCommandMap().get("ProcessingSubSectorLimitCmd") };
        } else if (EVENT_RETURN_FRM_ECO.equals(event)) {
        	objArray = new ICommand[2];
        	objArray[0] = (ICommand) getNameCommandMap().get("ReturnSubSectorLimitCmd");
        	objArray[1] = (ICommand) getNameCommandMap().get("PrepareReturnSubSectorLimitCmd");
        }else if (EVENT_DELETE_ECO_ITEM.equals(event)) {
        	objArray = new ICommand[2];
        	objArray[0] = (ICommand) getNameCommandMap().get("DeleteEcoSectorlimitCmd");
        	objArray[1] = (ICommand) getNameCommandMap().get("PrepareReturnSubSectorLimitCmd");
        }else if(EVENT_READ_SUBITEM.equals(event)){
            return new ICommand[] { (ICommand) getNameCommandMap().get("ReadSubItemCmd") };
        }else if(EVENT_CHECKER_VIEW_SUBITEM.equals(event)){
            return new ICommand[] { (ICommand) getNameCommandMap().get("ReadSubSectorLimitTrxCmd") };
        }
        return objArray;
    }
    
    protected boolean isValidationRequired(String event) {
		//return false;
    	return EVENT_CREATE.equals(event) ||
    	EVENT_UPDATE.equals(event)||
    	EVENT_ADD_ITEM.equals(event)||
    	EVENT_EDIT_ECO_ITEM.equals(event)||
    	EVENT_ADD_ITEMS.equals(event) ||
    	EVENT_DELETE_ECO_ITEM.equals(event);

    }
    
    protected String getErrorEvent(String event) {
        return EVENT_ERROR_RETURN;        
    }
    
    public ActionErrors validateInput(ActionForm form, Locale locale) {
    	return SubSectorLimitValidator.validateInput((SubSectorLimitForm)form, locale);
    }

    protected IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
        Page aPage = new Page();

        String page = getReference(event);

        DefaultLogger.debug(this, "<<<< page: "+page);
        aPage.setPageReference( page );
        return aPage;
    }
    
    private String getReference(String event) {
    	if (EVENT_PREPARE_UPDATE.equals(event) ||
    			EVENT_ERROR_RETURN.equals(event) ||
    			//EVENT_RETURN.equals(event)
    			EVENT_RETURN_FRM_ECO.equals(event)||
    			EVENT_DELETE_ECO_ITEM.equals(event)
    			)
    		return EVENT_PREPARE;
    	
    	if (EVENT_CREATE.equals(event) ||
    			EVENT_UPDATE.equals(event))
    		return EVENT_RETURN;
    	
    	if(EVENT_ADD_ITEMS.equals(event)){
    		return EVENT_ADD_ITEM;
    	}
    	
    	return event;
    }
}
