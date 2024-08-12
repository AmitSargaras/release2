package com.integrosys.cms.ui.securityenvelope;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.*;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Purpose: for Security Envelope Description:
 * Action class for Security Envelope
 *
 * @author Erene Wong
 * @version $Revision$
 * @since 28 Jan 2010
 */
public class SecEnvelopeAction extends CommonAction implements IPin {

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

        if ("sec_envelope_list".equals(event)) {
            objArray = new ICommand[3];
            objArray[0] = (ICommand) getNameCommandMap().get("ListSecEnvelopeByLocCmd");
            objArray[1] = (ICommand) getNameCommandMap().get("ReadSecEnvelopeCmd");
            objArray[2] = (ICommand) getNameCommandMap().get("PrepareSecEnvelopeByLocCmd");
        } else if ("maker_prepare_create".equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = (ICommand) getNameCommandMap().get("PrepareCreateSecEnvelopeCmd");
            objArray[1] = (ICommand) getNameCommandMap().get("PrepareSecEnvelopeByLocCmd");
        } else if ("create_items".equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("SaveCurWorkingSecEnvelopeCmd");
        } else if ("delete_items".equals(event) || "delete_edit_items".equals(event) || "delete_resubmit_edit_items".equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = (ICommand) getNameCommandMap().get("DeleteSecEnvelopeItemCmd");
            objArray[1] = (ICommand) getNameCommandMap().get("PrepareSecEnvelopeByLocCmd");
        } else if ("maker_confirm_create".equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("MakerCreateSecEnvelopeCmd");
        } else if ("maker_prepare_edit".equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = (ICommand) getNameCommandMap().get("ReadSecEnvelopeCmd");
            objArray[1] = (ICommand) getNameCommandMap().get("PrepareSecEnvelopeByLocCmd");
        } else if ("maker_confirm_edit".equals(event)
                || "maker_confirm_resubmit_edit".equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("MakerEditSecEnvelopeCmd");
        } else if ("maker_prepare_delete".equals(event) || "maker_prepare_close".equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("ReadSecEnvelopeCmd");
        } else if ("checker_process_create".equals(event) || "checker_process_edit".equals(event) || "checker_process_delete".equals(event)) {
            DefaultLogger.debug(this, "6");
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("CheckerProcessSecEnvelopeCmd");
        } else if ("checker_confirm_approve_create".equals(event)) {
            DefaultLogger.debug(this, "7");
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("CheckerApproveCreateSecEnvelopeCmd");
        } else if ("checker_confirm_approve_edit".equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("CheckerApproveEditSecEnvelopeCmd");
        } else if ("checker_confirm_reject_create".equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("CheckerRejectCreateSecEnvelopeCmd");
        }   else if ("checker_confirm_reject_edit".equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("CheckerRejectEditSecEnvelopeCmd");
        } else if ("maker_prepare_resubmit".equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = (ICommand) getNameCommandMap().get("ReadSecEnvelopeCmd");
            objArray[1] = (ICommand) getNameCommandMap().get("PrepareSecEnvelopeByLocCmd");
        } else if ("maker_confirm_close".equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("MakerCloseSecEnvelopeCmd");
        } else if ("checker_view_sec_envelope".equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = (ICommand) getNameCommandMap().get("ListSecEnvelopeByLocCmd");
            objArray[1] = (ICommand) getNameCommandMap().get("ReadSecEnvelopeCmd");
        } else
        if ("update_return_create".equals(event) || "update_return_edit".equals(event) || "update_return_resubmit_edit".equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = (ICommand) getNameCommandMap().get("ReturnSecEnvelopeCmd");
            objArray[1] = (ICommand) getNameCommandMap().get("PrepareSecEnvelopeByLocCmd");
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
        DefaultLogger.debug(this, "Inside getDefaultEvent()");
        return "sec_envelope_list";
    }

   public ActionErrors validateInput(ActionForm aForm, Locale locale) {
        return SecEnvelopeFormValidator.validateSecEnvelopeForm(aForm, locale);
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
        DefaultLogger.debug(this, "Inside getNextPage()");
        if (result.get("wip") != null && ((String) result.get("wip")).equals("wip")) {
            aPage.setPageReference("wip");
            return aPage;
        }
        String page = getReference(event);
        aPage.setPageReference(page);
        return aPage;
    }

    protected String getErrorEvent(String event) {
        DefaultLogger.debug(this, "Inside getErrorEvent()");
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
        DefaultLogger.debug(this, "Inside isValidationRequired()" + event);
        boolean result = false;        
        if  (event.equals("maker_confirm_create") || event.equals("create_items") 
        	|| event.equals("checker_confirm_approve_create") || event.equals("checker_confirm_reject_create")
          	|| event.equals("checker_confirm_approve_edit") || event.equals("checker_confirm_reject_edit") 
          	|| event.equals("maker_confirm_resubmit_edit")) {
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

        if ("sec_envelope_list".equals(event) || "checker_sec_envelope_list".equals(event)) {
            return "sec_envelope_list";
        } else if ("create_items".equals(event) || "create_itemdetail".equals(event) || "edit_items".equals(event) || "edit_itemdetail".equals(event)){
            return "create_items";
        } else if ("delete_items".equals(event) || "error_return".equals(event)) {
            return "update_page";
        } else if ("maker_confirm_edit".equals(event) || "maker_confirm_resubmit_edit".equals(event)) {
            return "maker_confirm_edit";
        } else if ("checker_process_create".equals(event) || "checker_process_edit".equals(event) || "checker_process_delete".equals(event)) {
            return "checker_process_create_edit_delete";
        } else if ("checker_confirm_approve_create".equals(event) || "checker_confirm_approve_edit".equals(event) || "checker_confirm_approve_delete".equals(event)) {
            return "checker_confirm_approve";
        } else if ("checker_confirm_reject_create".equals(event) || "checker_confirm_reject_edit".equals(event) || "checker_confirm_reject_delete".equals(event)) {
            return "checker_confirm_reject";
        } else if ("maker_prepare_close".equals(event)) {
            return "view_security_envelope";
        } else if ("to_track".equals(event) || "checker_view_sec_envelope".equals(event)) {
            return "view_security_envelope";
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