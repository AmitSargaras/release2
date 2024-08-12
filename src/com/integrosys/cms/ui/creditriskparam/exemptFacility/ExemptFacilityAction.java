/**
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.creditriskparam.exemptFacility;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import java.util.HashMap;
import java.util.Locale;

/**
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $
 * Tag: $Name:  $
 */
public class ExemptFacilityAction extends CommonAction {

    public static final String EVENT_CHECKER_PROCESS         = "checker_process";
    public static final String EVENT_CHECKER_APPROVE         = "checker_approve";
    public static final String EVENT_CHECKER_REJECT          = "checker_reject";
    public static final String EVENT_MAKER_CLOSE             = "maker_close";
    public static final String EVENT_MAKER_CLOSE_CONFIRM     = "maker_close_confirm";
    public static final String EVENT_TO_TRACK                = "to_track";


    public static final String EV_MKR_EDIT_LIQ_REJECT       ="maker_edit_reject";
    public static final String EV_MKR_EDIT_LIQ_CONFIRM      ="maker_edit_exempt_fac_confirm";
    public static final String EV_MKR_EDIT_REJECT_LIQ_CONFIRM ="maker_edit_reject_confirm";
    public static final String EVENT_CHECKER_VIEW              ="checker_view";
    public static final String EV_WIP                       ="wip";

    public static final String EV_PREPARE_ADD               ="prepare_add";
    public static final String EV_ADD                       ="add";
    public static final String EV_ADD_ERROR                 ="add_error";
    public static final String EV_EDIT_ERROR                ="edit_error";
    public static final String EV_EDIT                      ="edit";
    public static final String EV_PREPARE_EDIT              ="prepare_edit";
    public static final String EV_REMOVE                    ="remove";
    public static final String EV_REFRESH                   ="refresh";

    public static final String EVENT_SUBMIT_ERROR            = "submit_error";
    public static final String EVENT_CHECKER_PROCESS_ERROR   = "checker_process_error";
    public static final String EVENT_MAKER_CLOSE_ERROR       = "maker_close_error";


   /**
     * This method return a Array of Commad Objects responsible for a event
     *
     * @param event is of type String
     * @return Icommand Array
     */
    public ICommand[] getCommandChain(String event) {
        ICommand objArray [] = null;
        if ((EVENT_TO_TRACK.equals(event)) ||
                (EVENT_CHECKER_PROCESS.equals(event)) ||
                (EVENT_LIST.equals(event)) ||
                (EVENT_MAKER_CLOSE.equals(event)) ||
                (EVENT_READ.equals(event))) {
            objArray = new ICommand[1];
            objArray[0] = new ReadExemptFacilityCommand();

        } else if (EV_PREPARE_ADD.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new PrepareAddExemptFacilityCommand();

        } else if (EV_ADD.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new AddExemptFacilityCommand();

        } else if (EV_EDIT.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new EditExemptFacilityCommand();

        } else if (EV_REMOVE.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new RemoveExemptFacilityCommand();

        } else if (EV_REFRESH.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new RefreshExemptFacilityCommand();

        } else if (EVENT_VIEW.equals(event) ||
                   EV_PREPARE_EDIT.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new ViewExemptFacilityCommand();

        }  else if (EVENT_SUBMIT.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new SubmitExemptFacilityCommand();

        } else if (EVENT_CHECKER_APPROVE.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new ApproveExemptFacilityCommand();

        } else if (EVENT_CHECKER_REJECT.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new RejectExemptFacilityCommand();

        } else if (EVENT_MAKER_CLOSE_CONFIRM.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new CloseExemptFacilityCommand();
        }
        return (objArray);
    }

    /**
     * This method is called only for create and Update command to validate the form
     * and return the ActionErrors object.
     *
     * @param aForm is of type ActionForm
     * @param locale of type Locale
     * @return ActionErrors
     */
    public ActionErrors validateInput(ActionForm aForm, Locale locale) {
        return ExemptFacilityValidator.validateInput((ExemptFacilityForm)aForm,locale);
    }

    protected boolean isValidationRequired(String event) {
        boolean result = false;
        if (event.equals(EV_ADD) || event.equals(EV_EDIT) || event.equals(EVENT_SUBMIT)
             || EV_REMOVE.equals(event)
             || EVENT_CHECKER_APPROVE.equals(event) ||
                EVENT_CHECKER_REJECT.equals(event) | EVENT_MAKER_CLOSE_CONFIRM.equals(event))
            result = true;
        return result;
    }

    /**
     * This method is used to determine which the page to be displayed next using the event
     * Result hashmap and exception hashmap.It returns the page object .
     *
     * @param event  is of type String
     * @param resultMap is of type HashMap
     * @param exceptionMap is of type HashMap
     * @return IPage
     */
    public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
        Page aPage = new Page();

        if(resultMap.get(EV_WIP)!=null && (resultMap.get(EV_WIP)).equals(EV_WIP)){
            aPage.setPageReference(getReference(EV_WIP));
        }else if (!exceptionMap.isEmpty()) {
			if (exceptionMap.get(ICMSUIConstant.EXEMPT_FACILITY_DUPLICATE) != null) {
                aPage.setPageReference(EV_PREPARE_ADD);
            }
        if (exceptionMap.get("noItemSelected") != null) {
                aPage.setPageReference(EVENT_LIST);
            }
        }

        else if (EV_ADD_ERROR.equals(event)){
            aPage.setPageReference(EV_PREPARE_ADD);
        }else if (EV_EDIT_ERROR.equals(event)){
            aPage.setPageReference(EV_PREPARE_EDIT);
        }else if (EVENT_SUBMIT_ERROR.equals(event)){
            aPage.setPageReference(EVENT_LIST);
        }else if (EVENT_CHECKER_PROCESS_ERROR.equals(event)){
            aPage.setPageReference(EVENT_CHECKER_PROCESS);
        }else if (EVENT_MAKER_CLOSE_ERROR.equals(event)){
            aPage.setPageReference(EVENT_MAKER_CLOSE);
        }else{
            aPage.setPageReference(getReference(event));
        }
        return aPage;
    }

    protected String getErrorEvent(String event) {
        String errorEvent = getDefaultEvent();

        if(EV_ADD.equals(event) || EV_EDIT.equals(event)) {
            errorEvent = EV_ADD_ERROR;
        }else if (EVENT_SUBMIT.equals(event) || EV_REMOVE.equals(event)) {
            errorEvent =  EVENT_SUBMIT_ERROR;
        } else if (EVENT_CHECKER_APPROVE.equals(event)) {
            errorEvent =  EVENT_CHECKER_PROCESS_ERROR;
        } else if (EVENT_CHECKER_REJECT.equals(event)) {
            errorEvent =  EVENT_CHECKER_PROCESS_ERROR;
        } else if (EVENT_MAKER_CLOSE_CONFIRM.equals(event)) {
            errorEvent =  EVENT_MAKER_CLOSE_ERROR;
        }
        return errorEvent;
    }

    /**
     * method which determines the forward name for a particular event
     * @param event as String
     * @return String
     */
    private String getReference(String event) {
        String forwardName = EVENT_LIST;
        if (EVENT_LIST.equals(event) ||
                EV_REMOVE.equals(event) ||
                EV_EDIT.equals(event) ||
                EV_ADD.equals(event))
            forwardName = EVENT_LIST;
        else
            forwardName = event;

        DefaultLogger.debug(this, ">>>>>>>>>>> " + forwardName);
        return forwardName;
    }
}