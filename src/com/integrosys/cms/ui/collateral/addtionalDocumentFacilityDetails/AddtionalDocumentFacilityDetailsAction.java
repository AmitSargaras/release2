package com.integrosys.cms.ui.collateral.addtionalDocumentFacilityDetails;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.collateral.ActualListCmdNew;
import com.integrosys.cms.ui.collateral.CollateralAction;
import com.integrosys.cms.ui.collateral.MakerViewInsuranceStatusCmd;
import com.integrosys.cms.ui.collateral.ReadReturnCollateralCommand;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/08/13 06:42:29 $ Tag: $Name: $
 */

public class AddtionalDocumentFacilityDetailsAction {

	public static ICommand[] getCommandChain(String event) {
		ICommand objArray[] = new ICommand[1];
		
		if (CollateralAction.MAKER_ADD_ADD_DOC_FAC_DET .equals(event) || "maker_add_submit_addtionalDocumentFacilityDetails_error".equals(event)
				|| CollateralAction.MAKER_EDIT_ADD_DOC_FAC_DET .equals(event) || CollateralAction.MAKER_UPDATE_ADD_DOC_FAC_DET .equals(event)
//				|| CollateralAction.MAKER_CREATE_ADD_DOC_FAC_DET _PENDING.equals(event) || CollateralAction.MAKER_CREATE_ADD_DOC_FAC_DET _DEFERRED.equals(event) || 
//        		CollateralAction.MAKER_CREATE_ADD_DOC_FAC_DET _RECEIVED.equals(event) || CollateralAction.MAKER_CREATE_ADD_DOC_FAC_DET _WAIVED.equals(event)
//        		|| CollateralAction.MAKER_SUBMIT_ADD_DOC_FAC_DET__DEFERRED_ERROR.equals(event) || CollateralAction.MAKER_SUBMIT_ADD_DOC_FAC_DET__WAIVED_ERROR.equals(event)
//        		|| CollateralAction.MAKER_SUBMIT_ADD_DOC_FAC_DET__RECEIVED_ERROR.equals(event) || CollateralAction.MAKER_SUBMIT_ADD_DOC_FAC_DET__PENDING_ERROR.equals(event)
        		){
			objArray = new ICommand[2];
			objArray[0] = new PrepareAddtionalDocumentFacilityDetailsCommand();
			objArray[1] = new ReadAddtionalDocumentFacilityDetailsCommand();
		}else if (CollateralAction.EVENT_READ_RETURN.equals(event) || CollateralAction.EVENT_CANCEL.equals(event)
//				|| CollateralAction.MAKER_EDIT_CANCEL_ADD_DOC_FAC_DET_STATUS.equals(event)
				) {
			objArray[0] = new ReadReturnCollateralCommand();
		}else if(CollateralAction.MAKER_ADD_ADD_DOC_FAC_DET_SUBMIT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new AddAddtionalDocumentFacilityDetailsCommand();
		}else if(CollateralAction.MAKER_EDIT_ADD_DOC_FAC_DET_LIST.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new UpdateAddtionalDocumentFacilityDetailsCommand();
		}else if(CollateralAction.MAKER_UPDATE_ADD_DOC_FAC_DET_LIST.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new UpdateAddtionalDocumentFacilityDetailsCommand();
		}
		
		
		/*if (CollateralAction.EVENT_CREATE.equals(event)) {
            objArray[0] = new AddAddtionalDocumentFacilityDetailsCommand();
		}
		else if (CollateralAction.EVENT_PREPARE_UPDATE_SUB.equals(event)
				|| CollateralAction.EVENT_PREPARE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareAddtionalDocumentFacilityDetailsCommand();
			objArray[1] = new ReadAddtionalDocumentFacilityDetailsCommand();
		}
		else if (CollateralAction.EVENT_UPDATE.equals(event)) {
            objArray[0] = new UpdateAddtionalDocumentFacilityDetailsCommand();
		}
		else if (CollateralAction.EVENT_READ.equals(event)) {
			objArray[0] = new ReadAddtionalDocumentFacilityDetailsCommand();
		}
		else if (CollateralAction.EVENT_READ_RETURN.equals(event) || CollateralAction.EVENT_CANCEL.equals(event)
				|| CollateralAction.MAKER_EDIT_CANCEL_ADD_DOC_FAC_DET_STATUS.equals(event)) {
			objArray[0] = new ReadReturnCollateralCommand();
		}
		else if (CollateralAction.EVENT_REFRESH.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new RefreshAddtionalDocumentFacilityDetailsCommand();
			objArray[1] = new PrepareAddtionalDocumentFacilityDetailsCommand();
		}
		else if (CollateralAction.EVENT_PREPARE_FORM.equals(event)) {
			objArray[0] = new PrepareAddtionalDocumentFacilityDetailsCommand();
		}
        else if (CollateralAction.EVENT_SEARCH_ACCOUNT.equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = new SearchAddtionalDocumentFacilityDetailsAccountCommand();
            objArray[1] = new PrepareAddtionalDocumentFacilityDetailsCommand();
        }
		
		 //Uma Khot::Insurance Deferral maintainance
        else if (CollateralAction.CHECKER_VIEW_ADD_DOC_FAC_DET _RECEIVED.equals(event) || CollateralAction.CHECKER_VIEW_ADD_DOC_FAC_DET _DEFERRED.equals(event)
        		|| CollateralAction.CHECKER_VIEW_ADD_DOC_FAC_DET _WAIVED.equals(event) || CollateralAction.CHECKER_VIEW_ADD_DOC_FAC_DET _PENDING.equals(event)
        		|| CollateralAction.MAKER_VIEW_ADD_DOC_FAC_DET _RECEIVED.equals(event) || CollateralAction.MAKER_VIEW_ADD_DOC_FAC_DET _WAIVED.equals(event)
				|| CollateralAction.MAKER_VIEW_ADD_DOC_FAC_DET _PENDING.equals(event) || CollateralAction.MAKER_VIEW_ADD_DOC_FAC_DET _DEFERRED.equals(event) ) {
        	objArray = new ICommand[2];
			objArray[0] = new FetchAddtionalDocumentFacilityDetailsCmd();
			objArray[1] = new ReadAddtionalDocumentFacilityDetailsCommand();
		}
        else if (CollateralAction.MAKER_ADD_ADD_DOC_FAC_DET .equals(event) || CollateralAction.MAKER_CREATE_ADD_DOC_FAC_DET _PENDING.equals(event) || CollateralAction.MAKER_CREATE_ADD_DOC_FAC_DET _DEFERRED.equals(event) || 
        		CollateralAction.MAKER_CREATE_ADD_DOC_FAC_DET _RECEIVED.equals(event) || CollateralAction.MAKER_CREATE_ADD_DOC_FAC_DET _WAIVED.equals(event)
        		|| CollateralAction.MAKER_SUBMIT_ADD_DOC_FAC_DET__DEFERRED_ERROR.equals(event) || CollateralAction.MAKER_SUBMIT_ADD_DOC_FAC_DET__WAIVED_ERROR.equals(event)
        		|| CollateralAction.MAKER_SUBMIT_ADD_DOC_FAC_DET__RECEIVED_ERROR.equals(event) || CollateralAction.MAKER_SUBMIT_ADD_DOC_FAC_DET__PENDING_ERROR.equals(event)){
			objArray = new ICommand[2];
			objArray[0] = new PrepareAddtionalDocumentFacilityDetailsCommand();
			objArray[1] = new ReadAddtionalDocumentFacilityDetailsCommand();
		} else if (CollateralAction.MAKER_CANCLE_SUBMIT_ADD_DOC_FAC_DET .equals(event)) {
			objArray[0] = new ReadReturnCollateralCommand();
		}
        else if (CollateralAction.MAKER_CANCLE_CREATE_ADD_DOC_FAC_DET .equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadReturnCollateralCommand();
			
		}else if (CollateralAction.MAKER_SUBMIT_ADD_DOC_FAC_DET _PENDING.equals(event) || CollateralAction.MAKER_SUBMIT_ADD_DOC_FAC_DET _DEFERRED.equals(event)
				| CollateralAction.MAKER_SUBMIT_ADD_DOC_FAC_DET _RECEIVED.equals(event)|| CollateralAction.MAKER_SUBMIT_ADD_DOC_FAC_DET _WAIVED.equals(event)) {
            objArray[0] = new AddAddtionalDocumentFacilityDetailsCommand();
		}else if (CollateralAction.MAKER_UPDATE_ADD_DOC_FAC_DET_RECEIVED_LIST.equals(event) || CollateralAction.MAKER_UPDATE_ADD_DOC_FAC_DET_WAIVED_LIST.equals(event)
				|| CollateralAction.MAKER_UPDATE_ADD_DOC_FAC_DET_PENDING_LIST.equals(event)|| CollateralAction.MAKER_UPDATE_ADD_DOC_FAC_DET_DEFERRED_LIST.equals(event)
			
				
				|| CollateralAction.MAKER_EDIT_ADD_DOC_FAC_DET_RECEIVED_LIST.equals(event) 
				) {
			 objArray[0] = new UpdateAddtionalDocumentFacilityDetailsCommand();
		}else if (CollateralAction.MAKER_EDIT_ADD_DOC_FAC_DET _RECEIVED.equals(event) || CollateralAction.MAKER_UPDATE_ADD_DOC_FAC_DET _RECEIVED.equals(event)
				|| CollateralAction.MAKER_UPDATE_ADD_DOC_FAC_DET _PENDING.equals(event) || CollateralAction.MAKER_UPDATE_ADD_DOC_FAC_DET _DEFERRED.equals(event)
				|| CollateralAction.MAKER_UPDATE_ADD_DOC_FAC_DET _WAIVED.equals(event)
				) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareAddtionalDocumentFacilityDetailsCommand();
			objArray[1] = new ReadAddtionalDocumentFacilityDetailsCommand();
		}
		else if (CollateralAction.MAKER_UPDATE_ADD_DOC_FAC_DET_PENDING_LIST_ERROR.equals(event)||CollateralAction.MAKER_UPDATE_ADD_DOC_FAC_DET_DEFERRED_LIST_ERROR.equals(event)
				||CollateralAction.MAKER_UPDATE_ADD_DOC_FAC_DET_WAIVED_LIST_ERROR.equals(event)
				){
		 objArray = null;
		}else if (CollateralAction.MAKER_EDIT_ADD_DOC_FAC_DET_RECEIVED_LIST_ERROR.equals(event) || CollateralAction.MAKER_UPDATE_ADD_DOC_FAC_DET_RECEIVED_LIST_ERROR.equals(event)
				) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareAddtionalDocumentFacilityDetailsCommand();
		}*/
        return objArray;
	}

	
	 
	public static ActionErrors validateInput(ActionForm aForm, Locale locale) {
        ActionErrors errors;
        if (CollateralAction.EVENT_SEARCH_ACCOUNT.equals(((AddtionalDocumentFacilityDetailsForm) aForm).getEvent())) {
            errors = AddtionalDocumentFacilityDetailsValidator.validateAccSearch((AddtionalDocumentFacilityDetailsForm)aForm, locale);
		} else {
            errors = AddtionalDocumentFacilityDetailsValidator.validateInput((AddtionalDocumentFacilityDetailsForm) aForm, locale);
		}
		return errors;
	}
	
	public static boolean isValidationRequired(String event) {
		boolean result = false;
        if (CollateralAction.EVENT_CREATE.equals(event) || CollateralAction.EVENT_UPDATE.equals(event)
				|| CollateralAction.EVENT_REFRESH.equals(event) || CollateralAction.EVENT_SEARCH_ACCOUNT.equals(event)) {
			result = true;
		}else if (event.equals(CollateralAction.MAKER_ADD_ADD_DOC_FAC_DET_SUBMIT))
		{
			result = true;
		}else if (event.equals(CollateralAction.MAKER_EDIT_ADD_DOC_FAC_DET_LIST))
		{
			result = true;
		}else if (event.equals(CollateralAction.MAKER_UPDATE_ADD_DOC_FAC_DET_LIST))
		{
			result = true;
		}
        
        //Uma Khot::Insurance Deferral maintainance
		
//		else if (event.equals(CollateralAction.MAKER_SUBMIT_ADD_DOC_FAC_DET _PENDING))
//		{
//			result = true;
//		}else if (event.equals(CollateralAction.MAKER_SUBMIT_ADD_DOC_FAC_DET _DEFERRED))
//		{
//			result = true;
//		}else if (event.equals(CollateralAction.MAKER_SUBMIT_ADD_DOC_FAC_DET _RECEIVED))
//		{
//			result = true;
//		}else if (event.equals(CollateralAction.MAKER_SUBMIT_ADD_DOC_FAC_DET _WAIVED))
//		{
//			result = true;
//		}if (event.equals(CollateralAction.MAKER_EDIT_ADD_DOC_FAC_DET_RECEIVED_LIST) || event.equals(CollateralAction.MAKER_UPDATE_ADD_DOC_FAC_DET_RECEIVED_LIST) 
//			|| event.equals(CollateralAction.MAKER_UPDATE_ADD_DOC_FAC_DET_PENDING_LIST)  || event.equals(CollateralAction.MAKER_UPDATE_ADD_DOC_FAC_DET_WAIVED_LIST)  || event.equals(CollateralAction.MAKER_UPDATE_ADD_DOC_FAC_DET_DEFERRED_LIST))
//		{
//			result = true;
//		}
		return result;
	}
	
	public static String getErrorEvent(String event) {
		String errorEvent = event;
		if (CollateralAction.EVENT_CREATE.equals(event) || CollateralAction.EVENT_UPDATE.equals(event)
				|| CollateralAction.EVENT_REFRESH.equals(event) || CollateralAction.EVENT_SEARCH_ACCOUNT.equals(event)) {
            errorEvent = CollateralAction.EVENT_PREPARE_FORM;
		}else if (CollateralAction.MAKER_ADD_ADD_DOC_FAC_DET_SUBMIT.equals(event)) {
			return "maker_add_submit_addtionalDocumentFacilityDetails_error";
		}else if (CollateralAction.MAKER_EDIT_ADD_DOC_FAC_DET_LIST.equals(event)) {
			return "maker_edit_addtionalDocumentFacilityDetails_error";
		}else if (CollateralAction.MAKER_UPDATE_ADD_DOC_FAC_DET_LIST.equals(event)) {
			return "maker_update_addtionalDocumentFacilityDetails_error";
		}

		//Uma Khot::Insurance Deferral maintainance 
		
//		else if (CollateralAction.MAKER_SUBMIT_ADD_DOC_FAC_DET _PENDING.equals(event)) {
//			return "maker_submit_addtionalDocumentFacilityDetails_pending_error";
//		}else if (CollateralAction.MAKER_SUBMIT_ADD_DOC_FAC_DET _DEFERRED.equals(event)) {
//			return "maker_submit_addtionalDocumentFacilityDetails_deferred_error";
//		}else if (CollateralAction.MAKER_SUBMIT_ADD_DOC_FAC_DET _RECEIVED.equals(event)) {
//			return "maker_submit_addtionalDocumentFacilityDetails_received_error";
//		}else if (CollateralAction.MAKER_SUBMIT_ADD_DOC_FAC_DET _WAIVED.equals(event)) {
//			return "maker_submit_addtionalDocumentFacilityDetails_waived_error";
//		}else if (CollateralAction.MAKER_EDIT_ADD_DOC_FAC_DET_RECEIVED_LIST.equals(event)) {
//			return "maker_edit_addtionalDocumentFacilityDetails_received_list_error";
//		}else if (CollateralAction.MAKER_UPDATE_ADD_DOC_FAC_DET_RECEIVED_LIST.equals(event)) {
//			return "maker_update_addtionalDocumentFacilityDetails_received_list_error";
//		}else if (CollateralAction.MAKER_UPDATE_ADD_DOC_FAC_DET_PENDING_LIST.equals(event)) {
//			return "maker_update_addtionalDocumentFacilityDetails_pending_list_error";
//		}else if (CollateralAction.MAKER_UPDATE_ADD_DOC_FAC_DET_WAIVED_LIST.equals(event)) {
//			return "maker_update_addtionalDocumentFacilityDetails_waived_list_error";
//		}else if (CollateralAction.MAKER_UPDATE_ADD_DOC_FAC_DET_DEFERRED_LIST.equals(event)) {
//			return "maker_update_addtionalDocumentFacilityDetails_deferred_list_error";
//		}
		return errorEvent;
	}

	
    public static IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		if (CollateralAction.EVENT_CREATE.equals(event) || CollateralAction.EVENT_UPDATE.equals(event)
				|| CollateralAction.EVENT_READ_RETURN.equals(event) || CollateralAction.EVENT_CANCEL.equals(event)
				|| CollateralAction.MAKER_ADD_ADD_DOC_FAC_DET_SUBMIT.equals(event) || CollateralAction.MAKER_EDIT_ADD_DOC_FAC_DET_LIST.equals(event)
				|| CollateralAction.MAKER_UPDATE_ADD_DOC_FAC_DET_LIST.equals(event)) {
			
			String subtype = (String) resultMap.get("subtype");
			if (subtype == null) {
				throw new RuntimeException("URL passed is wrong");
			}
			else if (CollateralAction.EVENT_READ_RETURN.equals(event)) {
				aPage.setPageReference(subtype + "_" + (String) resultMap.get("from_event"));
			}
			else {
				aPage.setPageReference(subtype + "_update");
			}
		}else if (CollateralAction.EVENT_PREPARE_FORM.equals(event) || CollateralAction.EVENT_REFRESH.equals(event)) {
			aPage.setPageReference(CollateralAction.EVENT_PREPARE);
		}
		
		
		
		/*if (CollateralAction.EVENT_CREATE.equals(event) || CollateralAction.EVENT_UPDATE.equals(event)
				|| CollateralAction.EVENT_READ_RETURN.equals(event) || CollateralAction.EVENT_CANCEL.equals(event) 
				|| CollateralAction.MAKER_SUBMIT_ADD_DOC_FAC_DET _PENDING.equals(event) || CollateralAction.MAKER_SUBMIT_ADD_DOC_FAC_DET _DEFERRED.equals(event)  
				||CollateralAction.MAKER_SUBMIT_ADD_DOC_FAC_DET _RECEIVED.equals(event)  || CollateralAction.MAKER_SUBMIT_ADD_DOC_FAC_DET _WAIVED.equals(event)
				|| CollateralAction.MAKER_EDIT_ADD_DOC_FAC_DET_RECEIVED_LIST.equals(event) || CollateralAction.MAKER_UPDATE_ADD_DOC_FAC_DET_RECEIVED_LIST.equals(event) 
				|| CollateralAction.MAKER_UPDATE_ADD_DOC_FAC_DET_PENDING_LIST.equals(event) ||
				CollateralAction.MAKER_UPDATE_ADD_DOC_FAC_DET_WAIVED_LIST.equals(event)  || CollateralAction.MAKER_UPDATE_ADD_DOC_FAC_DET_DEFERRED_LIST.equals(event)
				|| CollateralAction.MAKER_EDIT_CANCEL_ADD_DOC_FAC_DET_STATUS.equals(event)) {
			String subtype = (String) resultMap.get("subtype");
			if (subtype == null) {
				throw new RuntimeException("URL passed is wrong");
			}
			else if (CollateralAction.EVENT_READ_RETURN.equals(event)) {
				aPage.setPageReference(subtype + "_" + (String) resultMap.get("from_event"));
			}
			else {
				aPage.setPageReference(subtype + "_update");
			}
		}
		else if (CollateralAction.EVENT_PREPARE_FORM.equals(event) || CollateralAction.EVENT_REFRESH.equals(event)) {
			aPage.setPageReference(CollateralAction.EVENT_PREPARE);
		}*/
		
		 //Uma Khot::Insurance Deferral maintainance
//		else if((null!=event && ( event.equals(CollateralAction.MAKER_SUBMIT_ADD_DOC_FAC_DET _DEFERRED)  || event.equals(CollateralAction.MAKER_SUBMIT_ADD_DOC_FAC_DET _RECEIVED) || event.equals(CollateralAction.MAKER_SUBMIT_ADD_DOC_FAC_DET _WAIVED)
//				|| event.equals(CollateralAction.MAKER_EDIT_INSRECEIVED_LIST)) || event.equals(CollateralAction.MAKER_UPDATE_INSRECEIVED_LIST)  || event.equals(CollateralAction.MAKER_UPDATE_INSPENDING_LIST)  || event.equals(CollateralAction.MAKER_UPDATE_INSWAIVED_LIST)  || event.equals(CollateralAction.MAKER_UPDATE_INSDEFERRED_LIST))){
//			aPage.setPageReference("maker_submit_addtionalDocumentFacilityDetails_page");
//		}
		else if (event.equals("maker_add_submit_addtionalDocumentFacilityDetails_error")) {
			aPage.setPageReference("maker_add_addfacdocdet");
		}else if (event.equals("maker_edit_addtionalDocumentFacilityDetails_error")) {
			aPage.setPageReference("maker_edit_add_fac_doc_det");
		}else if (event.equals("maker_update_addtionalDocumentFacilityDetails_error")) {
			aPage.setPageReference("maker_update_add_fac_doc_det");
		}else if (event.equals("maker_submit_addtionalDocumentFacilityDetails_received_error")) {
			aPage.setPageReference("maker_create_addtionalDocumentFacilityDetails_received");
		}else if (event.equals("maker_submit_addtionalDocumentFacilityDetails_waived_error")) {
			aPage.setPageReference("maker_create_addtionalDocumentFacilityDetails_waived");
		}else if (event.equals("maker_edit_addtionalDocumentFacilityDetails_received_list_error")) {
			aPage.setPageReference("maker_edit_addtionalDocumentFacilityDetails_received");
		}else if (event.equals("maker_update_addtionalDocumentFacilityDetails_received_list_error")) {
			aPage.setPageReference("maker_update_addtionalDocumentFacilityDetails_received");
		} else if (event.equals("maker_update_addtionalDocumentFacilityDetails_pending_list_error")) { 
			aPage.setPageReference("maker_update_addtionalDocumentFacilityDetails_pending");
		} else if (event.equals("maker_update_addtionalDocumentFacilityDetails_waived_list_error")) {
			aPage.setPageReference("maker_update_addtionalDocumentFacilityDetails_waived");
		} else if (event.equals("maker_update_addtionalDocumentFacilityDetails_deferred_list_error")) {
			aPage.setPageReference("maker_update_addtionalDocumentFacilityDetails_deferred");
		} 
		
		else {
			String from_event = (String) resultMap.get("from_event");
			if (CollateralAction.EVENT_READ.equals(event) && (from_event != null) && from_event.equals("process")) {
				aPage.setPageReference(CollateralAction.EVENT_PROCESS);
			}
			else {
				aPage.setPageReference(event);
			}
		}
		return aPage;
	}

}
