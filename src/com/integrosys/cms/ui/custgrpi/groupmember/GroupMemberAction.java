package com.integrosys.cms.ui.custgrpi.groupmember;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.cci.CIFSearchCommand;
import com.integrosys.cms.ui.custgrpi.CustGrpIdentifierAction;
import com.integrosys.cms.ui.custgrpi.Maker2PreSaveCustGrpIdentifierCommand;
import com.integrosys.cms.ui.custgrpi.PrepareCustGrpIdentifierSearchCommand;
import com.integrosys.cms.ui.custgrpi.ProcessingCustGrpIdentifierCommand;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: May 7, 2007
 * Time: 6:01:28 PM
 * To change this template use File | Settings | File Templates.
 */


public class GroupMemberAction extends CustGrpIdentifierAction {

    public static final String EVENT_EXT_SEARCH_CUSTOMER = "ext_search_customer";
    public static final String EVENT_PREPARE_EXT_SEARCH_CUSTOMER = "prepare_ext_search_customer";
    public static final String EVENT_EXT_CUSTOMER_LIST = "ext_customer_list";
    public static final String EVENT_AFTER_EXT_CUSTOMER_LIST = "after_ext_customer_list";
    public static final String EVENT_PREPARE_SEARCH_GROUP = "prepare_search_group";
    public static final String EVENT_SEARCH_GROUP = "searchGroup";
    public static final String EVENT_SEARCH_GROUP_LIST = "search_group_list";

    public static final String EVENT_ADD_NOOP = "addNoop";
    public static final String REROUTEPAGE = "reroutepage";
    public static final String SUBSTITUTEPAGE = "substitutepage";
    public static final String REASSIGNPAGE = "reassignpage";
    public static final String EVENT_CREATE = "create";
    public static final String EVENT_CREATE_CUSTOMER = "create_customer";
    public static final String EVENT_CANCEL = "cancel";
    public static final String EVENT_POPUP_SHAREHOLDER = "popup_shareholder";

    public ICommand[] getCommandChain(String event) {

        ICommand objArray[] = null;
        if (EVENT_EXT_SEARCH_CUSTOMER.equals(event)
                || EVENT_PREPARE_EXT_SEARCH_CUSTOMER.equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = new PrepareGroupMemberCommand();
            objArray[1] = new PrepareHelperGroupMemberCommand();
        } else if (EVENT_EXT_CUSTOMER_LIST.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new GroupMemberSearchCommand();
//            objArray[0] = new PrepareGroupMemberCommand();
//            objArray[1] = new ListGroupMemberCommand();
        } else if (EVENT_SEARCH_GROUP.equals(event) || EVENT_SEARCH_GROUP_LIST.equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = new PrepareGroupMemberCommand();
            objArray[1] = new SearchGroupCommand();
        } else if (EVENT_PREPARE_CREATE.equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = new PrepareGroupMemberCommand();
            objArray[1] = new PrepareHelperGroupMemberCommand();
        } else if (EVENT_CREATE.equals(event) || EVENT_ADD.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new AddGroupMemberCommand();
        } else if (EVENT_CREATE_CUSTOMER.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new AddGroupMemberCommand();
        } else if (EVENT_READ.equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = new Maker2PreSaveCustGrpIdentifierCommand();
            objArray[1] = new ReadGroupMemberCommand();
        } else if (EVENT_POPUP_SHAREHOLDER.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new PopupReadGroupMemberCommand();
        } else if (EVENT_PREPARE.equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = new PrepareGroupMemberCommand();
            objArray[1] = new PrepareHelperGroupMemberCommand();
        } else if (EVENT_PREPARE_SEARCH_GROUP.equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = new PrepareGroupMemberCommand();
            objArray[1] = new PrepareHelperGroupMemberCommand();
        } else if (EVENT_READ_RETURN.equals(event) || EVENT_CANCEL.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new ReturnGroupMemberCommand();
        } else if (EVENT_PREPARE_UPDATE.equals(event)) {
            objArray = new ICommand[3];
            objArray[0] = new PrepareGroupMemberCommand();
            objArray[1] = new PrepareHelperGroupMemberCommand();
            objArray[2] = new ReadGroupMemberCommand();
        } else if (EVENT_UPDATE.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new UpdateGroupMemberCommand();
        } else if ("process_group_list".equals(event) || "process_customer_list".equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new ReturnListGroupMemberCommand();
        } else if ("first_search".equals(event)
                || "subsequent_search".equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new GroupMemberSearchCommand();
        } else if ("after_group_list".equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = new PrepareCustGrpIdentifierSearchCommand();
            objArray[1] = new ProcessingCustGrpIdentifierCommand();

        } else {
            DefaultLogger.debug(this, "CustGrpIdentifierAction.. called ");
            objArray = super.getCommandChain(event);
        }

        return objArray;
    }


    public ActionErrors validateInput(ActionForm aForm, Locale locale) {
        DefaultLogger.debug(this, "VALIDATION REQUIRED...");
        return GroupMemberValidator.validateInput((GroupMemberForm) aForm, locale);
    }


    protected boolean isValidationRequired(String event) {

        boolean result = false;
        if (/*EVENT_CREATE.equals(event) ||  */
                EVENT_UPDATE.equals(event)) {
            result = true;
        } else if (/*EVENT_EXT_CUSTOMER_LIST.equals(event)*/
                "first_search".equals(event)) {
            result = true;
        } else if (EVENT_SEARCH_GROUP.equals(event)) {
            result = true;
        }

        return result;
    }

    protected String getErrorEvent(String event) {

        DefaultLogger.debug(this, "GroupMemberAction getErrorEvent is = " + event);

        String errorEvent = event;
        if (EVENT_CREATE.equals(event)) {
            errorEvent = "process_group_list";
        } else if (EVENT_CREATE_CUSTOMER.equals(event)) {
            errorEvent = "process_customer_list";
            errorEvent = EVENT_EXT_CUSTOMER_LIST;
        } else if (EVENT_UPDATE.equals(event)) {
            errorEvent = EVENT_PREPARE;
        } else if (/*EVENT_EXT_CUSTOMER_LIST.equals(event)*/
                "first_search".equals(event)) {
            errorEvent = "prepare_ext_search_customer";
        } else if (EVENT_SEARCH_GROUP.equals(event)) {
            errorEvent = "prepare_search_group";
        }

        return errorEvent;
    }


    /**
     * This method is used to determine the next page to be displayed using the event
     * Result hashmap and exception hashmap.It returns the page object .
     *
     * @param event        is of type String
     * @param resultMap    is of type HashMap
     * @param exceptionMap is of type HashMap
     * @return IPage
     */

    public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {

        // To return correct forward String for AJAX Search
//        if ("first_search".equals(event) || "subsequent_search".equals(event)) {
//            Page aPage = new Page();
//            aPage.setPageReference("ajax_search_cif_result");
//            return aPage;
//        }

        Page aPage = new Page();
        String forward = null;

        //String errNoSelection =(String)  resultMap.get("errNoSelection") ;
        //String errEntityID =(String)  resultMap.get("errEntityID") ;

        DefaultLogger.debug(this, "GroupSubLimitAction event is = " + event);

        if (exceptionMap.get("GROUP_ERROR") != null) {
            forward = "process_group_list";
            aPage.setPageReference(forward);
            return aPage;
        } else if (exceptionMap.get("CUSTOMER_ERROR") != null) {
            forward = EVENT_EXT_CUSTOMER_LIST;
            aPage.setPageReference(forward);
            return aPage;
        } else if (exceptionMap.get("errNoSelection") != null) {
            forward = EVENT_EXT_CUSTOMER_LIST;
            aPage.setPageReference(forward);
            return aPage;
        } else if (EVENT_CREATE.equals(event)
                || EVENT_UPDATE.equals(event)
                || EVENT_READ_RETURN.equals(event)
                || EVENT_CANCEL.equals(event)
                || EVENT_CREATE_CUSTOMER.equals(event)
                ) {
            String itemType = (String) resultMap.get("itemType");
            DefaultLogger.debug(this, "GroupMemberAction itemType = " + itemType);
            if (itemType == null) {
                throw new RuntimeException("URL passed is wrong");
            } else if (EVENT_READ_RETURN.equals(event)) {
                forward = itemType + "_" + (String) resultMap.get("from_event");
            } else {
                forward = "GROUPMEMBER_update";
            }

        } else if (event.equals(EVENT_EDIT) || event.equals(EVENT_FORWARD)) {
            DefaultLogger.debug(this, "forwardPage is: " + resultMap.get("forwardPage"));
            forward = (String) resultMap.get("forwardPage");
        } else if (EVENT_EXT_SEARCH_CUSTOMER.equals(event)) {
            forward = EVENT_PREPARE_EXT_SEARCH_CUSTOMER;
        } else if (EVENT_EXT_CUSTOMER_LIST.equals(event)||"first_search".equals(event)) {
            forward = EVENT_AFTER_EXT_CUSTOMER_LIST;
        } else if (EVENT_SEARCH_GROUP.equals(event)) {
            forward = EVENT_SEARCH_GROUP_LIST;
        } else if (REROUTEPAGE.equals(event) || REASSIGNPAGE.equals(event) || SUBSTITUTEPAGE.equals(event)) {
            forward = event;
        } else {
            forward = event;
        }
        aPage.setPageReference(forward);
        return aPage;
    }


}
