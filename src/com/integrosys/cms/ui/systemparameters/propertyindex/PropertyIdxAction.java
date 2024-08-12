package com.integrosys.cms.ui.systemparameters.propertyindex;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.*;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Purpose: for Property Valuation By Index Description:
 * Action class for Property Valuation By Index
 *
 * @author Andy Wong
 * @version $Revision$
 * @since 16 Sep 2008
 */
public class PropertyIdxAction extends CommonAction implements IPin {

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
     * @param event is of type String
     * @return Icommand Array
     */
    protected ICommand[] getCommandChain(String event) {
        ICommand objArray[] = null;
        DefaultLogger.debug(this, "getCommandChain::::" + event);

        if ("property_index_list".equals(event) || "checker_property_index_list".equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("ListPropertyValByIndexCmd");
        } else if ("maker_prepare_create".equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = (ICommand) getNameCommandMap().get("PrepareCreatePropertyIdxCmd");
            objArray[1] = (ICommand) getNameCommandMap().get("PreparePropertyValByIndexCmd");
        } else if ("create_items".equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("SaveCurWorkingPropertyIdxCmd");
        } else
        if ("delete_items".equals(event) || "delete_edit_items".equals(event) || "delete_resubmit_edit_items".equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = (ICommand) getNameCommandMap().get("DeleteItemCmd");
            objArray[1] = (ICommand) getNameCommandMap().get("PreparePropertyValByIndexCmd");
        } else if ("maker_confirm_create".equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("MakerCreatePropertyIdxCmd");
        } else if ("maker_prepare_edit".equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = (ICommand) getNameCommandMap().get("ReadPropertyIdxCmd");
            objArray[1] = (ICommand) getNameCommandMap().get("PreparePropertyValByIndexCmd");
        } else if ("maker_confirm_edit".equals(event)
                || "maker_confirm_resubmit_edit".equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("MakerEditPropertyIdxCmd");
        } else if ("maker_prepare_delete".equals(event) || "maker_prepare_close".equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("ReadPropertyIdxCmd");
        } else if ("maker_confirm_delete".equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("MakerDeletePropertyIdxCmd");
        } else
        if ("checker_process_create".equals(event) || "checker_process_edit".equals(event) || "checker_process_delete".equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("CheckerProcessPropertyIdxCmd");
        } else if ("checker_confirm_approve_create".equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = (ICommand) getNameCommandMap().get("CheckerApproveCreatePropertyIdxCmd");
            objArray[1] = (ICommand) getNameCommandMap().get("ReloadPropertyIndexCmd");
        } else if ("checker_confirm_approve_edit".equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = (ICommand) getNameCommandMap().get("CheckerApproveEditPropertyIdxCmd");
            objArray[1] = (ICommand) getNameCommandMap().get("ReloadPropertyIndexCmd");
        } else if ("checker_confirm_approve_delete".equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = (ICommand) getNameCommandMap().get("CheckerApproveDeletePropertyIdxCmd");
            objArray[1] = (ICommand) getNameCommandMap().get("ReloadPropertyIndexCmd");
        } else if ("checker_confirm_reject_create".equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("CheckerRejectCreatePropertyIdxCmd");
        } else if ("checker_confirm_reject_edit".equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("CheckerRejectEditPropertyIdxCmd");
        } else if ("checker_confirm_reject_delete".equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("CheckerRejectDeletePropertyIdxCmd");
        } else if ("maker_prepare_resubmit".equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = (ICommand) getNameCommandMap().get("ReadPropertyIdxCmd");
            objArray[1] = (ICommand) getNameCommandMap().get("PreparePropertyValByIndexCmd");
        } else if ("maker_confirm_close".equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("MakerClosePropertyIdxCmd");
        } else if ("to_track".equals(event) || "checker_view_property_index".equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("ReadPropertyIdxCmd");
        } else
        if ("update_return_create".equals(event) || "update_return_edit".equals(event) || "update_return_resubmit_edit".equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = (ICommand) getNameCommandMap().get("ReturnPropertyIdxCmd");
            objArray[1] = (ICommand) getNameCommandMap().get("PreparePropertyValByIndexCmd");
        } else if ("error_return".equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = (ICommand) getNameCommandMap().get("SaveCurWorkingPropertyIdxCmd");
            objArray[1] = (ICommand) getNameCommandMap().get("PreparePropertyValByIndexCmd");
        }

        DefaultLogger.debug(this, "Inside doExecute()");
        return (objArray);
    }

    /**
     * Method which returns default event
     *
     * @return default event
     */
    public String getDefaultEvent() {
        return "property_index_list";
    }

    public ActionErrors validateInput(ActionForm aForm, Locale locale) {
        return PropertyIdxFormValidator.validatePropertyIdxForm(aForm, locale);
    }

    /**
     * This method is used to determine which the page to be displayed next
     * using the event Result hashmap and exception hashmap.It returns the page
     * object .
     *
     * @param event        is of type String
     * @param resultMap    is of type HashMap
     * @param exceptionMap is of type HashMap
     * @return IPage
     */
    protected IPage getNextPage(String event, HashMap result, HashMap exceptionMap) {
        Page aPage = new Page();

        if (result.get("wip") != null && ((String) result.get("wip")).equals("wip")) {
            aPage.setPageReference("wip");
            return aPage;
        }
        String page = getReference(event);
        aPage.setPageReference(page);
        return aPage;
    }

    protected String getErrorEvent(String event) {
        String errorEvent = getDefaultEvent();
        if ("maker_confirm_create".equals(event)) {
            errorEvent = "error_return";
        } else if ("create_items".equals(event)) {
            errorEvent = "maker_prepare_create";
        } else if ("checker_confirm_approve_create".equals(event) || "checker_confirm_reject_create".equals(event)) {
            errorEvent = "checker_process_create";
        } else if ("checker_confirm_approve_edit".equals(event) || "checker_confirm_reject_edit".equals(event)) {
            errorEvent = "checker_process_edit";
        } else if ("checker_confirm_approve_delete".equals(event) || "checker_confirm_reject_delete".equals(event)) {
            errorEvent = "checker_process_delete";
        } else if ("maker_confirm_resubmit_edit".equals(event)) {
            errorEvent = "maker_prepare_resubmit";
        } else if ("maker_confirm_edit".equals(event)) {
            errorEvent = "maker_prepare_edit";
        }
        return errorEvent;
    }

    protected boolean isValidationRequired(String event) {
        boolean result = false;
        if (event.equals("maker_confirm_create") || event.equals("create_items") || event.equals("checker_confirm_approve_create")
                || event.equals("checker_confirm_approve_edit") || event.equals("checker_confirm_approve_delete") || event.equals("checker_confirm_reject_create")
                || event.equals("checker_confirm_reject_edit") || event.equals("checker_confirm_reject_delete") || event.equals("maker_confirm_resubmit_edit")) {
            result = true;
        }
        return result;
    }

    /**
     * method which determines the forward name for a particular event
     *
     * @param event as String
     * @return String
     */
    private String getReference(String event) {

        DefaultLogger.debug(this, "Property Index Action class::::" + event);

        if ("property_index_list".equals(event) || "checker_property_index_list".equals(event)) {
            return "property_index_list";
        } else if ("create_items".equals(event) || "edit_items".equals(event)) {
            return "create_items";
        } else if ("delete_items".equals(event) || "error_return".equals(event)) {
            return "update_page";
        } else if ("maker_confirm_edit".equals(event) || "maker_confirm_resubmit_edit".equals(event)) {
            return "maker_confirm_edit";
        } else
        if ("checker_process_create".equals(event) || "checker_process_edit".equals(event) || "checker_process_delete".equals(event)) {
            return "checker_process_create_edit_delete";
        } else
        if ("checker_confirm_approve_create".equals(event) || "checker_confirm_approve_edit".equals(event) || "checker_confirm_approve_delete".equals(event)) {
            return "checker_confirm_approve";
        } else
        if ("checker_confirm_reject_create".equals(event) || "checker_confirm_reject_edit".equals(event) || "checker_confirm_reject_delete".equals(event)) {
            return "checker_confirm_reject";
        } else if ("maker_prepare_close".equals(event)) {
            return "view_property_index";
        } else if ("to_track".equals(event) || "checker_view_property_index".equals(event)) {
            return "view_property_index";
        } else if ("update_return_create".equals(event)) {
            return "maker_prepare_create";
        } else if ("update_return_edit".equals(event) || "delete_edit_items".equals(event)) {
            return "maker_prepare_edit";
        } else if ("update_return_resubmit_edit".equals(event) || "delete_resubmit_edit_items".equals(event)) {
            return "maker_prepare_resubmit";
        } else {
            return event;
        }


    }
}