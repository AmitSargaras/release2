package com.integrosys.cms.ui.custgrpi;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: Nov 9, 2007
 * Time: 3:56:31 PM
 * To change this template use File | Settings | File Templates.
 */


public class CustGrpIdentifierAction extends CommonAction implements IPin {


    public static final String EVENT_SAVE = "save";
    public static final String EVENT_LIST = "list";
    public static final String EVENT_CANCEL_VIEW = "view_return";
    public static final String EVENT_SEARCH_CUSTOMER = "search_customer";
    public static final String EVENT_SUBMIT = "submit";
    public static final String EVENT_PREPARE_CREATE = "prepare_create";
    public static final String EVENT_ADD = "add";
    public static final String EVENT_EDIT = "edit";
    public static final String EVENT_MAKER2_EDIT = "maker2_edit";
    public static final String EVENT_MAKER2_SUBMIT = "maker2_submit";
    public static final String EVENT_MAKER2_SAVE = "maker2_save";
    public static final String EVENT_MAKER2_PRE_SAVE = "maker2_pre_save";
    public static final String EVENT_READ = "read";
    public static final String EVENT_REMOVE = "remove";
    public static final String EVENT_ADD_NOOP = "addNoop";
    public static final String REROUTEPAGE = "reroutepage";
    public static final String SUBSTITUTEPAGE = "substitutepage";
    public static final String REASSIGNPAGE = "reassignpage";
    public static final String CHECKER_EDIT_CGID = "checker_edit_cgid";
    public static final String CHECKER2_EDIT_CGID = "checker2_edit_cgid";
    public static final String CHECKER_APPROVE_EDIT_CGID = "checker_approve_edit_cgid";
    public static final String CHECKER2_APPROVE_EDIT_CGID = "checker2_approve_edit_cgid";
    public static final String CHECKER_REJECT_EDIT_CGID = "checker_reject_edit_cgid";
    public static final String CHECKER2_REJECT_EDIT_CGID = "checker2_reject_edit_cgid";
    public static final String MAKER_CLOSE_CGID = "maker_close_cgid";
    public static final String MAKER2_CLOSE_CGID = "maker2_close_cgid";
    public static final String EV_WIP = "wip";
    public static final String EVENT_READ_RETURN = "read_return";
    public static final String EVENT_PREPARE_UPDATE = "prepare_update";
    public static final String EVENT_PREPARE2_UPDATE = "prepare2_update";
    public static final String EVENT_PREPARE_UPDATE_SUB = "prepare_update_sub";
    public static final String EVENT_DELETE_ITEM = "itemDelete";
    public static final String EVENT_DELETE2_ITEM = "itemDelete2";
    public static final String EVENT_PREPARE = "prepare";
    public static final String EVENT_PREPARE2 = "prepare2";
    public static final String EVENT_SEARCH_MEMEBER = "searchMemeber";
    public static final String EVENT_SEARCH_GROUP = "searchGroup";
    public static final String EVENT_PREPARE_FORM = "prepare_form";
    public static final String EVENT_UPDATE_RETURN = "update_return";
    public static final String EVENT_PROCESS_UPDATE = "process_update";
    public static final String EVENT_PROCESS2_UPDATE = "process2_update";
    public static final String EVENT_TRACK_RETURN = "track_return";
    public static final String EVENT_PROCESS_RETURN = "process_return";
    public static final String EVENT_CLOSE_RETURN = "close_return";
    public static final String EVENT_VIEW_SUBGROUP = "view_subgroup";
    public static final String EVENT_UPDATE_RETURN_SUBLIMIT = "update_return_sublimit";
    public static final String EVENT_UPDATE_RETURN_OTRLIMIT = "update_return_otrlimit";

    public ICommand[] getCommandChain(String event) {
        ICommand objArray[] = null;
        if ("prepare_custGrp_search".equals(event) || "prepare_search".equals(event)) {
            objArray = new ICommand[1];
            // objArray[0] = new PrepareCounterpartySearchCommand();
            objArray[0] = new PrepareCustGrpIdentifierSearchCommand();
        } else if (EVENT_ADD.equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = new PrepareCustGrpIdentifierCommand();
            objArray[1] = new PrepareCreateCustGrpIdentifierCommand();
        } else if (EVENT_PREPARE.equals(event)
                || EVENT_SEARCH_MEMEBER.equals(event)
                || EVENT_PREPARE_FORM.equals(event)
                ) {
            objArray = new ICommand[2];
            objArray[0] = new PrepareCustGrpIdentifierSearchCommand();
            objArray[1] = new ProcessingCustGrpIdentifierCommand();
        } else if (EVENT_PREPARE2.equals(event)
                ) {
            objArray = new ICommand[2];
            objArray[0] = new Maker2ProcessingCustGrpIdentifierCommand();
            objArray[1] = new ProcessingCustGrpIdentifierCommand();
        } else if (EVENT_PREPARE_CREATE.equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = new PrepareCustGrpIdentifierCommand();
            objArray[1] = new CreateCustGrpIdentifierCommand();
        } else if (EVENT_PROCESS_UPDATE.equals(event) || EVENT_PROCESS2_UPDATE.equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = new PrepareCustGrpIdentifierCommand();
            objArray[1] = new ReadCustGrpIdentifierCommand();
        } else if (EVENT_PREPARE_UPDATE.equals(event) || EVENT_PREPARE2_UPDATE.equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = new PrepareCustGrpIdentifierCommand();
            objArray[1] = new PrepareUpdateCustGrpIdentifierCommand();
        } else if (EVENT_SAVE.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new SaveCustGrpIdentifierCommand();
        } else if (EVENT_MAKER2_SAVE.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new Maker2SaveCustGrpIdentifierCommand();
        } else if (EVENT_LIST.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new ListCustGrpIdentifierCommand();
        } else if (EVENT_CANCEL_VIEW.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new CancelListCustGrpIdentifierCommand();
        } else if (EVENT_READ.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new ReadCustGrpIdentifierCommand();
        } else if (EVENT_VIEW_SUBGROUP.equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = new CheckerReadCustGrpIdentifierCommand();
            objArray[1] = new PopupSubGroupCustGrpIdentifierCommand();
        } else if (EVENT_MAKER2_EDIT.equals(event)) {
            objArray = new ICommand[3];
            objArray[0] = new PrepareCustGrpIdentifierCommand();
            objArray[1] = new ReadCustGrpIdentifierCommand();
            objArray[2] = new EditCustGrpIdentifierCommand();
        } else if (EVENT_EDIT.equals(event)) {
            objArray = new ICommand[3];
            objArray[0] = new PrepareCustGrpIdentifierCommand();
            objArray[1] = new ReadCustGrpIdentifierCommand();
            objArray[2] = new EditCustGrpIdentifierCommand();
        } else if ("return".equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new ReturnCustGrpIdentifierCommand();
        } else if (EVENT_PROCESS_RETURN.equals(event)
                || EVENT_READ_RETURN.equals(event)
                || EVENT_CLOSE_RETURN.equals(event)
                || EVENT_TRACK_RETURN.equals(event)
                || "maker2_edit_return".equals(event)
                || "process2_update_return".equals(event)
                || "maker2_close_return".equals(event)
                || "maker2_edit_cgid_reject_return".equals(event)
                || "prepare_delete_return".equals(event)
                || "checker_edit_cgid_return".equals(event)    // when error in validation of remarks
                || "checker2_edit_cgid__return".equals(event)
                ) {
            objArray = new ICommand[2];
            objArray[0] = new PrepareCustGrpIdentifierCommand();
            objArray[1] = new ReturnCustGrpIdentifierCommand();
        } else if (EVENT_SEARCH_CUSTOMER.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new PrepareCustGrpIdentifierSearchCommand();
        } else if (EVENT_REMOVE.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new RemoveCustGrpIdentifierCommand();
        } else if (EVENT_SUBMIT.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new SubmitCustGrpIdentifierCommand();
        } else if (EVENT_MAKER2_SUBMIT.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new Maker2SubmitCustGrpIdentifierCommand();
        } else if (CHECKER_EDIT_CGID.equals(event)
                || CHECKER2_EDIT_CGID.equals(event)
                || MAKER_CLOSE_CGID.equals(event)
                || "to_track".equals(event)
                || MAKER2_CLOSE_CGID.equals(event)
                ) {
            objArray = new ICommand[1];
            objArray[0] = new CheckerReadCustGrpIdentifierCommand();
        } else if (CHECKER_APPROVE_EDIT_CGID.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new CheckerApproveEditCustGrpIdentifierCommand();
        } else if (CHECKER2_APPROVE_EDIT_CGID.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new Checker2ApproveEditCustGrpIdentifierCommand();
        } else if ("checker_reject_edit_cgid".equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new CheckerRejectEditCustGrpIdentifierCommand();
        } else if (CHECKER2_REJECT_EDIT_CGID.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new Checker2RejectEditCustGrpIdentifierCommand();
        } else if ("prepare_delete".equals(event) || "prepare_delete_return".equals(event) || "prepare_rejected_delete".equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new ReadCustGrpIdentifierCommand();
        } else if ("delete".equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new DeleteCustGrpIdentifierCommand();
        } else if ("maker_edit_cgid_reject".equals(event) || "maker2_edit_cgid_reject".equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = new PrepareCustGrpIdentifierCommand();
            objArray[1] = new MakerReadRejectedCustGrpIdentifierCommand();
        } else if ("maker_close_cgid".equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new MakerReadRejectedCustGrpIdentifierCommand();
        } else if ("maker_close_cgid_confirm".equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new MakerCancelEditCustGrpIdentifierCommand();
        } else if ("maker2_close_cgid_confirm".equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new Maker2CancelEditCustGrpIdentifierCommand();
        } else if ("viewLimitProfile".equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new ProcessDetailsCustomerCommand();
        } else if (event.startsWith("prepare_itemType")) {
            objArray = new ICommand[1];
            objArray[0] = new ProcessingCustGrpIdentifierCommand();
        } else if (EVENT_UPDATE_RETURN.equals(event)
                || EVENT_UPDATE_RETURN_SUBLIMIT.equals(event)
                || EVENT_UPDATE_RETURN_OTRLIMIT.equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = new PrepareCustGrpIdentifierCommand();
            objArray[1] = new ReturnCustGrpIdentifierCommand();
        } else if (EVENT_DELETE_ITEM.equals(event) || EVENT_DELETE2_ITEM.equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = new RemoveCustGrpIdentifierCommand();
            objArray[1] = new PrepareCustGrpIdentifierCommand();
        }

        return (objArray);
    }

    /**
     * This method is called only for create and Update command to validate the form
     * and return the ActionErrors object.
     *
     * @param aForm  is of type ActionForm
     * @param locale of type Locale
     * @return ActionErrors
     */
    public ActionErrors validateInput(ActionForm aForm, Locale locale) {
        DefaultLogger.debug(this, "Inside validate Input child class");
        return CustGrpIdentifierFormValidator.validateInput((CustGrpIdentifierForm) aForm, locale);
    }

    protected boolean isValidationRequired(String event) {
        boolean result = false;
        if (EVENT_SUBMIT.equals(event)
                || EVENT_SAVE.equals(event)
                || EVENT_PREPARE.equals(event)
                || EVENT_MAKER2_SUBMIT.equals(event)
                || EVENT_MAKER2_SAVE.equals(event)
                || EVENT_PREPARE2.equals(event)
                //added by Jitu for validation for remarks columns
                || CHECKER_APPROVE_EDIT_CGID.equals(event)
                || CHECKER_REJECT_EDIT_CGID.equals(event)
                || CHECKER2_APPROVE_EDIT_CGID.equals(event)
                || CHECKER2_REJECT_EDIT_CGID.equals(event)
                || EVENT_DELETE_ITEM.equals(event)
                || EVENT_DELETE2_ITEM.equals(event)
                ) {
            result = true;
        }
        if (EVENT_LIST.equals(event) || event.equals("custGrp_customer_list")
                || event.equals("customer_list")) {
            result = true;
        }
        return result;
    }

    protected String getErrorEvent(String event) {
        String errorEvent = getDefaultEvent();

        if (EVENT_SUBMIT.equals(event)
                || EVENT_SAVE.equals(event)
                || EVENT_PREPARE.equals(event)) {
            return EVENT_PREPARE_CREATE;
        } else if (EVENT_MAKER2_SUBMIT.equals(event)
                || EVENT_MAKER2_SAVE.equals(event)
                || EVENT_PREPARE2.equals(event)
                ) {
            return EVENT_PREPARE2_UPDATE;
        } else if ("customer_list".equals(event) || EVENT_LIST.equals(event)) {
            errorEvent = "prepare_custGrp_search";
        } else if (CHECKER_APPROVE_EDIT_CGID.equals(event)
                || CHECKER_REJECT_EDIT_CGID.equals(event)
                ) {
            return "checker_edit_cgid_return";
        } else if (CHECKER2_APPROVE_EDIT_CGID.equals(event)
                || CHECKER2_REJECT_EDIT_CGID.equals(event)
                ) {
            return "checker2_edit_cgid__return";
        } else if (EVENT_DELETE_ITEM.equals(event)) {
        	return EVENT_PREPARE_CREATE;
        } else if (EVENT_DELETE2_ITEM.equals(event)) {
        	return EVENT_PREPARE2_UPDATE;
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
        Page aPage = new Page();
        String forward = null;

        ICustGrpIdentifierTrxValue trxValue = (ICustGrpIdentifierTrxValue) resultMap.get(CustGroupUIHelper.service_groupTrxValue);
        boolean isWip = false;
//        boolean isDeletedReject = false;
        if (trxValue != null) {
            DefaultLogger.debug(this, "*************************trxValue.getStatus(): " + trxValue.getStatus());
            DefaultLogger.debug(this, "*************************trxValue.getFromState(): " + trxValue.getFromState());
            String status = trxValue.getStatus();
           
            isWip = status.equals(ICMSConstant.STATE_DRAFT)
                    || status.equals(ICMSConstant.STATE_PENDING_UPDATE)
                    || status.equals(ICMSConstant.STATE_PENDING_DELETE)
                    || status.equals(ICMSConstant.STATE_REJECTED)
                    || status.equals(ICMSConstant.STATE_REJECTED_DELETE)
                    ;

//            isDeletedReject = ICMSConstant.STATE_REJECTED_DELETE.equals(status) && ICMSConstant.STATE_PENDING_DELETE.equals(fromState);
        } else {
        	DefaultLogger.debug(this,"trxValue  is null");
        }

        if (resultMap.get("errorGroupNameExist") != null && ((String) resultMap.get("errorGroupNameExist")).equals("true")) {
            aPage.setPageReference("checker2_edit_cgid_error");
            return aPage;
        }

        if (!exceptionMap.isEmpty()) {
            if (exceptionMap.get("errorGroupNameExist") != null || exceptionMap.get("masterGroupErr") != null) {
            	DefaultLogger.debug(this,"Error in errorGroupNameExist so " + exceptionMap);
                forward = "checker2_edit_cgid_error";
            } else if (exceptionMap.get("errDuplicate") != null) {
            	DefaultLogger.debug(this,"Error in errDuplicate so " + exceptionMap);
                forward = "addNoop";
            } else if (exceptionMap.get("isBGELInd") != null) {
            	DefaultLogger.debug(this,"Error in isBGELInd so " + exceptionMap);
                forward = EVENT_PREPARE2_UPDATE;
            } else if (exceptionMap.get("errorGrpNameExist") != null || exceptionMap.get("masterGroupInd") != null) {
            	DefaultLogger.debug(this,"Error in maker submit so " + exceptionMap);
                forward = EVENT_PREPARE_CREATE;
            } else if (exceptionMap.get("hasLimitBookErr") != null) {
            	DefaultLogger.debug(this,"Error in hasLimitBookErr so " + exceptionMap);
                forward = "prepare_delete";
            }
            DefaultLogger.debug(this,"getNextPage >>>>>>>>>>>error found  = " + forward);
            aPage.setPageReference(forward);
            return aPage;

        } else if (isWip && (EVENT_EDIT.equals(event)
                || EVENT_MAKER2_EDIT.equals(event)
                || "prepare_update".equals(event)
                || "prepare_delete".equals(event))) {
            forward = "workInProgress";
        } else if (event.equals(EVENT_PREPARE)) {
            String itemType = (String) resultMap.get("itemType");
            DefaultLogger.debug(this, "<<<<<<<<< itemType : " + itemType);
            if (itemType != null) {
                aPage.setPageReference(event + "_" + itemType);
                return aPage;
            } else {
                aPage.setPageReference(forward);
                return aPage;
            }
        } else if (event.equals(EVENT_PREPARE_CREATE)) {
            String itemType = (String) resultMap.get("itemType");
            DefaultLogger.debug(this, "<<<<<<<<< itemType : " + itemType);
            if (StringUtils.equals(itemType, CustGroupUIHelper.GROUPOTRLIMIT)
                    || StringUtils.equals(itemType, CustGroupUIHelper.GROUPSUBLIMIT)) {
                aPage.setPageReference(EVENT_PREPARE2_UPDATE);
                return aPage;
            } else {
                forward = getReference(event);
                aPage.setPageReference(forward);
                return aPage;
            }
        } else if (event.equals(EVENT_PREPARE2)) {
            String itemType = (String) resultMap.get("itemType");
            DefaultLogger.debug(this, "<<<<<<<<< itemType : " + itemType);
            if (itemType != null) {
                aPage.setPageReference(event + "_" + itemType);
                return aPage;
            } else {
                aPage.setPageReference(forward);
                return aPage;
            }
        } else if (EVENT_DELETE_ITEM.equals(event) || EVENT_DELETE2_ITEM.equals(event)) {
            DefaultLogger.debug(this, "ResultMap is :" + resultMap);
            String itemType = (String) resultMap.get("itemType");
            if (itemType == null) {
                throw new RuntimeException("URL passed is wrong");
            } else if(CustGroupUIHelper.GROUPMEMBER.equals(itemType)) {
                aPage.setPageReference("update_return");
                return aPage;
            } else {
                aPage.setPageReference("update_return_maker2");
                return aPage;
            }
        } else if (EV_WIP.equals(resultMap.get(EV_WIP))) {
            aPage.setPageReference(getReference(EV_WIP));
            return aPage;
        } else if (EVENT_UPDATE_RETURN_OTRLIMIT.equals(event)
                || EVENT_UPDATE_RETURN_SUBLIMIT.equals(event)) {
            aPage.setPageReference("update_return_maker2");
            return aPage;
//        } else if ("maker_edit_cgid_reject".equals(event) && isDeletedReject) {
//            aPage.setPageReference("prepare_delete");
//            return aPage;
        } else {
            forward = getReference(event);
        }
        aPage.setPageReference(forward);
        return aPage;
    }

    /**
     * method which determines the forward name for a particular event
     *
     * @param event as String
     * @return String
     */
    private String getReference(String event) {
        String forwardName = "submit_fail";

        if ("cancel".equals(event)) {
            forwardName = "after_add";
        } else if ("search".equals(event)) {
            forwardName = "after_search";
        } else if (REROUTEPAGE.equals(event)
                || REASSIGNPAGE.equals(event)
                || SUBSTITUTEPAGE.equals(event)) {
            forwardName = event;
        } else if (EVENT_EDIT.equals(event) || "prepare_create".equals(event)) {
            forwardName = "prepare_update";
        } else if (EVENT_MAKER2_EDIT.equals(event)) {
            forwardName = EVENT_PREPARE2_UPDATE;
        } else if ("prepare_search".equals(event)) {
            forwardName = "after_prepare";
        } else if ("add".equals(event)) {
            forwardName = "after_add";
        } else if ("remove".equals(event)) {
            forwardName = "after_add";
        } else if (EVENT_SAVE.equals(event) || EVENT_MAKER2_SAVE.equals(event)) {
            forwardName = "ack_save";
        } else if (CHECKER_APPROVE_EDIT_CGID.equals(event)
//                || "checker_reject_edit_cgid".equals(event)
//                || CHECKER2_REJECT_EDIT_CGID.equals(event)
                || CHECKER2_APPROVE_EDIT_CGID.equals(event)
                ) {
            forwardName = "common_approve_page";
        } else if (CHECKER_REJECT_EDIT_CGID.equals(event)
                || CHECKER2_REJECT_EDIT_CGID.equals(event)
//                || CHECKER2_APPROVE_EDIT_CGID.equals(event)
                ) {
            forwardName = "common_reject_page";
        } else if (EVENT_DELETE.equals(event)
                || EVENT_SUBMIT.equals(event)
                || EVENT_MAKER2_SUBMIT.equals(event)) {
            forwardName = "ack_submit";
        } else if ("maker_edit_cgid_reject".equals(event)) {
            forwardName = "maker_edit_cgid_reject";
        } else if ("maker_close_cgid".equals(event)) {
            forwardName = "maker_close_cgid_page";
        } else if ("maker2_close_cgid".equals(event)) {
            forwardName = "maker2_close_cgid_page";
        } else if ("maker_close_cgid_confirm".equals(event) || "maker2_close_cgid_confirm".equals(event)) {
            forwardName = "common_close_page";
        } else if (EVENT_LIST.equals(event) || EVENT_CANCEL_VIEW.equals(event)) {
            forwardName = "after_list";
        } else if ("to_track".equals(event)) {
            forwardName = "after_to_track";
        } else if ("prepare_rejected_delete".equals(event)) {
            forwardName = "prepare_delete";
        } else {
            forwardName = event;
        }
        return forwardName;
    }
}
