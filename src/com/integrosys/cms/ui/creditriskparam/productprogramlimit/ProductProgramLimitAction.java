package com.integrosys.cms.ui.creditriskparam.productprogramlimit;

import com.integrosys.base.uiinfra.common.*;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

/**
 * Author: Priya
 * Date: Oct 5, 2009
 */
public class ProductProgramLimitAction extends CommonAction implements IPin {
	public final static String EVENT_PREPARE_UPDATE = "prepare_update";
	public final static String EVENT_ERROR_RETURN = "error_return";
	public final static String EVENT_CHECKER_ERROR = "checker_error";
	public final static String EVENT_RETURN = "return";
	public final static String EVENT_CHECKER_PROCESS = "checker_process";
	public final static String EVENT_CLOSE = "close";
	public final static String EVENT_PREPARE_DELETE = "prepare_delete";
	public final static String EVENT_VIEW_RETURN = "view_return";
	public final static String EVENT_PREPARE_CLOSE = "prepare_close";
	public final static String EVENT_TRACK = "track";
	public final static String EVENT_MAKER_PROCESS = "maker_process";
	public final static String EVENT_MAKER_PROCESS_DELETE = "maker_process_delete";
	public final static String EVENT_ADD_ITEM = "add_item";
	public final static String EVENT_EDIT_ITEM = "edit_item";
	public final static String EVENT_DELETE_ITEM = "delete_item";
	public final static String EVENT_PREPARE_EDIT_PRODUCT = "prepare_edit_product";

    private transient Map nameCommandMap;

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
        }else if (!exceptionMap.isEmpty()) {
        	if (exceptionMap.get(ICMSUIConstant.PRODUCTLIMIT_DUPLICATE_PRODUCT) != null) {
                aPage.setPageReference(EVENT_ADD_ITEM);
            }
        }
       
        String page = getReference(event);

        aPage.setPageReference( page );
        return aPage;
    }

    protected ICommand[] getCommandChain(String event) {
        ICommand[] objArray = null;

        DefaultLogger.debug(this, "In action, event="+event);

        if (EVENT_LIST.equals(event)) {
            return new ICommand[] { (ICommand) getNameCommandMap().get("ListProductLimitCmd") };
        } else if (EVENT_VIEW.equals(event)) {
            return new ICommand[] { (ICommand) getNameCommandMap().get("ReadProductLimitCmd") };
        } else if (	EVENT_PREPARE_UPDATE.equals(event) ||
        		EVENT_PREPARE.equals(event) ||
        		EVENT_MAKER_PROCESS.equals(event) ||
        		EVENT_PREPARE_EDIT_PRODUCT.equals(event)) {
            objArray = new ICommand[2];
        	objArray[0] = (ICommand) getNameCommandMap().get("PrepareProductLimitCmd");
        	objArray[1] = (ICommand) getNameCommandMap().get("ReadProductLimitTrxCmd");
        } else if (EVENT_PREPARE_DELETE.equals(event) ||
        		EVENT_PREPARE_CLOSE.equals(event) ||
        		EVENT_TRACK.equals(event) ||
        		EVENT_MAKER_PROCESS_DELETE.equals(event) ||
        		EVENT_CHECKER_PROCESS.equals(event)) {
            return new ICommand[] { (ICommand) getNameCommandMap().get("ReadProductLimitTrxCmd") };
        } else if (EVENT_ERROR_RETURN.equals(event)) {
            return new ICommand[] { (ICommand) getNameCommandMap().get("PrepareProductLimitCmd") };
        } else if (EVENT_SUBMIT.equals(event)) {
            return new ICommand[] { (ICommand) getNameCommandMap().get("SubmitProductLimitCmd") };
        } else if (EVENT_APPROVE.equals(event)) {
            return new ICommand[] { (ICommand) getNameCommandMap().get("ApproveProductLimitCmd") };
        } else if (EVENT_REJECT.equals(event)) {
            return new ICommand[] { (ICommand) getNameCommandMap().get("RejectProductLimitCmd") };
        } else if (EVENT_CLOSE.equals(event)) {
            return new ICommand[] { (ICommand) getNameCommandMap().get("CloseProductLimitCmd") };
        } else if (EVENT_DELETE.equals(event)) {
            return new ICommand[] { (ICommand) getNameCommandMap().get("DeleteProductLimitCmd") };
        } else if (EVENT_ADD_ITEM.equals(event) ||
        		EVENT_EDIT_ITEM.equals(event)) {
            return new ICommand[] { (ICommand) getNameCommandMap().get("ProcessingProductLimitCmd") };
        } else if (EVENT_DELETE_ITEM.equals(event)) {
        	objArray = new ICommand[2];
        	objArray[0] = (ICommand) getNameCommandMap().get("DeleteSubProductLmtsCmd");
        	objArray[1] = (ICommand) getNameCommandMap().get("PrepareProductLimitCmd");
        } else if (EVENT_RETURN.equals(event)) {
        	objArray = new ICommand[2];
        	objArray[0] = (ICommand) getNameCommandMap().get("ReturnProductLimitCmd");
        	objArray[1] = (ICommand) getNameCommandMap().get("PrepareProductLimitCmd");
        }

        return objArray;
    }

    protected String getErrorEvent(String event) {
        String errorEvent = event;

        if (EVENT_SUBMIT.equals(event) ||
        		EVENT_DELETE_ITEM.equals(event) ||
        		EVENT_ADD_ITEM.equals(event)||
        		EVENT_EDIT_ITEM.equals(event))
        	return EVENT_ERROR_RETURN;
        
        if (EVENT_APPROVE.equals(event) ||
        		EVENT_REJECT.equals(event))
            return EVENT_CHECKER_ERROR;
        
        if (EVENT_DELETE.equals(event) ||
        		EVENT_CLOSE.equals(event))
        	return EVENT_VIEW_RETURN;
        
        return errorEvent;
    }

    protected boolean isValidationRequired(String event) {
        return  EVENT_APPROVE.equals(event) ||
                EVENT_REJECT.equals(event) ||
                EVENT_CLOSE.equals(event) ||
                EVENT_SUBMIT.equals(event) ||
                EVENT_DELETE_ITEM.equals(event)||
                EVENT_ADD_ITEM.equals(event) ||
                EVENT_EDIT_ITEM.equals(event)
                ;
    }

    public ActionErrors validateInput(ActionForm form, Locale locale) {
        return ProductProgramLimitValidator.validateInput((ProductProgramLimitForm) form, locale);
    }

    private String getReference(String event) {
    	if (EVENT_PREPARE_UPDATE.equals(event) ||
    			EVENT_DELETE_ITEM.equals(event) ||
        		EVENT_ERROR_RETURN.equals(event) ||
        		EVENT_MAKER_PROCESS.equals(event) ||
        		EVENT_RETURN.equals(event) ||
        		EVENT_PREPARE_EDIT_PRODUCT.equals(event)) {
            return EVENT_PREPARE;
    	}
    	
    	if (EVENT_MAKER_PROCESS_DELETE.equals(event)) {
    		return EVENT_VIEW;
    	}
    	
    	if(EVENT_PREPARE_CLOSE.equals(event) ||
    			EVENT_PREPARE_DELETE.equals(event)||
    			EVENT_TRACK.equals(event)){
    		return EVENT_TRACK;
    	}
    	
    	if (EVENT_CHECKER_ERROR.equals(event)) {
    		return EVENT_CHECKER_PROCESS; 
    	}
        return event;
    }
}
