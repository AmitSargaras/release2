package com.integrosys.cms.ui.collateral.insurancepolicy;

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

public class InsurancePolicyAction {

	public static ICommand[] getCommandChain(String event) {
		ICommand objArray[] = new ICommand[1];
		if (CollateralAction.EVENT_CREATE.equals(event)) {
            objArray[0] = new AddInsurancePolicyCommand();
		}
		else if (CollateralAction.EVENT_PREPARE_UPDATE_SUB.equals(event)
				|| CollateralAction.EVENT_PREPARE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareInsurancePolicyCommand();
			objArray[1] = new ReadInsurancePolicyCommand();
		}
		else if (CollateralAction.EVENT_UPDATE.equals(event)) {
            objArray[0] = new UpdateInsurancePolicyCommand();
		}
		else if (CollateralAction.EVENT_READ.equals(event)) {
			objArray[0] = new ReadInsurancePolicyCommand();
		}
		else if (CollateralAction.EVENT_READ_RETURN.equals(event) || CollateralAction.EVENT_CANCEL.equals(event)
				|| CollateralAction.MAKER_EDIT_CANCEL_INSSTATUS.equals(event)) {
			objArray[0] = new ReadReturnCollateralCommand();
		}
		else if (CollateralAction.EVENT_REFRESH.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new RefreshInsurancePolicyCommand();
			objArray[1] = new PrepareInsurancePolicyCommand();
		}
		else if (CollateralAction.EVENT_PREPARE_FORM.equals(event)) {
			objArray[0] = new PrepareInsurancePolicyCommand();
		}
        else if (CollateralAction.EVENT_SEARCH_ACCOUNT.equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = new SearchInsurancePolicyAccountCommand();
            objArray[1] = new PrepareInsurancePolicyCommand();
        }
		
		 //Uma Khot::Insurance Deferral maintainance
        else if (CollateralAction.CHECKER_VIEW_INSURANCE_RECEIVED.equals(event) || CollateralAction.CHECKER_VIEW_INSURANCE_DEFERRED.equals(event)
        		|| CollateralAction.CHECKER_VIEW_INSURANCE_WAIVED.equals(event) || CollateralAction.CHECKER_VIEW_INSURANCE_PENDING.equals(event)
        		|| CollateralAction.MAKER_VIEW_INSURANCE_RECEIVED.equals(event) || CollateralAction.MAKER_VIEW_INSURANCE_WAIVED.equals(event)
				|| CollateralAction.MAKER_VIEW_INSURANCE_PENDING.equals(event) || CollateralAction.MAKER_VIEW_INSURANCE_DEFERRED.equals(event) ) {
        	objArray = new ICommand[2];
			objArray[0] = new FetchCurrncyInsuranceCovCmd();
			objArray[1] = new ReadInsurancePolicyCommand();
		}
        else if (CollateralAction.MAKER_ADD_INSURANCE.equals(event) || CollateralAction.MAKER_CREATE_INSURANCE_PENDING.equals(event) || CollateralAction.MAKER_CREATE_INSURANCE_DEFERRED.equals(event) || 
        		CollateralAction.MAKER_CREATE_INSURANCE_RECEIVED.equals(event) || CollateralAction.MAKER_CREATE_INSURANCE_WAIVED.equals(event)
        		|| CollateralAction.MAKER_SUBMIT_INS_DEFERRED_ERROR.equals(event) || CollateralAction.MAKER_SUBMIT_INS_WAIVED_ERROR.equals(event)
        		|| CollateralAction.MAKER_SUBMIT_INS_RECEIVED_ERROR.equals(event) || CollateralAction.MAKER_SUBMIT_INS_PENDING_ERROR.equals(event)){
			objArray = new ICommand[2];
			objArray[0] = new PrepareInsurancePolicyCommand();
			objArray[1] = new ReadInsurancePolicyCommand();
		} else if (CollateralAction.MAKER_CANCLE_SUBMIT_INSURANCE.equals(event)) {
			objArray[0] = new ReadReturnCollateralCommand();
		}
        else if (CollateralAction.MAKER_CANCLE_CREATE_INSURANCE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadReturnCollateralCommand();
			
		}else if (CollateralAction.MAKER_SUBMIT_INSURANCE_PENDING.equals(event) || CollateralAction.MAKER_SUBMIT_INSURANCE_DEFERRED.equals(event)
				| CollateralAction.MAKER_SUBMIT_INSURANCE_RECEIVED.equals(event)|| CollateralAction.MAKER_SUBMIT_INSURANCE_WAIVED.equals(event)) {
            objArray[0] = new AddInsurancePolicyCommand();
		}else if (CollateralAction.MAKER_UPDATE_INSRECEIVED_LIST.equals(event) || CollateralAction.MAKER_UPDATE_INSWAIVED_LIST.equals(event)
				|| CollateralAction.MAKER_UPDATE_INSPENDING_LIST.equals(event)|| CollateralAction.MAKER_UPDATE_INSDEFERRED_LIST.equals(event)
			
				
				|| CollateralAction.MAKER_EDIT_INSRECEIVED_LIST.equals(event) 
				) {
			 objArray[0] = new UpdateInsurancePolicyCommand();
		}else if (CollateralAction.MAKER_EDIT_INSURANCE_RECEIVED.equals(event) || CollateralAction.MAKER_UPDATE_INSURANCE_RECEIVED.equals(event)
				|| CollateralAction.MAKER_UPDATE_INSURANCE_PENDING.equals(event) || CollateralAction.MAKER_UPDATE_INSURANCE_DEFERRED.equals(event)
				|| CollateralAction.MAKER_UPDATE_INSURANCE_WAIVED.equals(event)
				) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareInsurancePolicyCommand();
			objArray[1] = new ReadInsurancePolicyCommand();
		}
		else if (CollateralAction.MAKER_UPDATE_INSPENDING_LIST_ERROR.equals(event)||CollateralAction.MAKER_UPDATE_INSDEFERRED_LIST_ERROR.equals(event)
				||CollateralAction.MAKER_UPDATE_INSWAIVED_LIST_ERROR.equals(event)
				){
		 objArray = null;
		}else if (CollateralAction.MAKER_EDIT_INSRECEIVED_LIST_ERROR.equals(event) || CollateralAction.MAKER_UPDATE_INSRECEIVED_LIST_ERROR.equals(event)
				) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareInsurancePolicyCommand();
		}
        return objArray;
	}

	/**
	 * This method is called only for create and Update command to validate the
	 * form and return the ActionErrors object.
	 * 
	 * @param aForm is of type ActionForm
	 * @param locale of type Locale
	 * @return ActionErrors
	 */
	public static ActionErrors validateInput(ActionForm aForm, Locale locale) {
        ActionErrors errors;
        if (CollateralAction.EVENT_SEARCH_ACCOUNT.equals(((InsurancePolicyForm) aForm).getEvent())) {
            errors = InsurancePolicyValidator.validateAccSearch((InsurancePolicyForm)aForm, locale);
		} else {
            errors = InsurancePolicyValidator.validateInput((InsurancePolicyForm) aForm, locale);
		}
		return errors;
	}

	public static boolean isValidationRequired(String event) {
		boolean result = false;
        if (CollateralAction.EVENT_CREATE.equals(event) || CollateralAction.EVENT_UPDATE.equals(event)
				|| CollateralAction.EVENT_REFRESH.equals(event) || CollateralAction.EVENT_SEARCH_ACCOUNT.equals(event)) {
			result = true;
		}
        
        //Uma Khot::Insurance Deferral maintainance
		
		else if (event.equals(CollateralAction.MAKER_SUBMIT_INSURANCE_PENDING))
		{
			result = true;
		}else if (event.equals(CollateralAction.MAKER_SUBMIT_INSURANCE_DEFERRED))
		{
			result = true;
		}else if (event.equals(CollateralAction.MAKER_SUBMIT_INSURANCE_RECEIVED))
		{
			result = true;
		}else if (event.equals(CollateralAction.MAKER_SUBMIT_INSURANCE_WAIVED))
		{
			result = true;
		}if (event.equals(CollateralAction.MAKER_EDIT_INSRECEIVED_LIST) || event.equals(CollateralAction.MAKER_UPDATE_INSRECEIVED_LIST) 
			|| event.equals(CollateralAction.MAKER_UPDATE_INSPENDING_LIST)  || event.equals(CollateralAction.MAKER_UPDATE_INSWAIVED_LIST)  || event.equals(CollateralAction.MAKER_UPDATE_INSDEFERRED_LIST))
		{
			result = true;
		}
		return result;
	}

	public static String getErrorEvent(String event) {
		String errorEvent = event;
		if (CollateralAction.EVENT_CREATE.equals(event) || CollateralAction.EVENT_UPDATE.equals(event)
				|| CollateralAction.EVENT_REFRESH.equals(event) || CollateralAction.EVENT_SEARCH_ACCOUNT.equals(event)) {
            errorEvent = CollateralAction.EVENT_PREPARE_FORM;
		}

		//Uma Khot::Insurance Deferral maintainance
		
		else if (CollateralAction.MAKER_SUBMIT_INSURANCE_PENDING.equals(event)) {
			return "maker_submit_ins_pending_error";
		}else if (CollateralAction.MAKER_SUBMIT_INSURANCE_DEFERRED.equals(event)) {
			return "maker_submit_ins_deferred_error";
		}else if (CollateralAction.MAKER_SUBMIT_INSURANCE_RECEIVED.equals(event)) {
			return "maker_submit_ins_received_error";
		}else if (CollateralAction.MAKER_SUBMIT_INSURANCE_WAIVED.equals(event)) {
			return "maker_submit_ins_waived_error";
		}else if (CollateralAction.MAKER_EDIT_INSRECEIVED_LIST.equals(event)) {
			return "maker_edit_insreceived_list_error";
		}else if (CollateralAction.MAKER_UPDATE_INSRECEIVED_LIST.equals(event)) {
			return "maker_update_insreceived_list_error";
		}else if (CollateralAction.MAKER_UPDATE_INSPENDING_LIST.equals(event)) {
			return "maker_update_inspending_list_error";
		}else if (CollateralAction.MAKER_UPDATE_INSWAIVED_LIST.equals(event)) {
			return "maker_update_inswaived_list_error";
		}else if (CollateralAction.MAKER_UPDATE_INSDEFERRED_LIST.equals(event)) {
			return "maker_update_insdeferred_list_error";
		}
		return errorEvent;
	}


    public static IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		if (CollateralAction.EVENT_CREATE.equals(event) || CollateralAction.EVENT_UPDATE.equals(event)
				|| CollateralAction.EVENT_READ_RETURN.equals(event) || CollateralAction.EVENT_CANCEL.equals(event) 
				|| CollateralAction.MAKER_SUBMIT_INSURANCE_PENDING.equals(event) || CollateralAction.MAKER_SUBMIT_INSURANCE_DEFERRED.equals(event)  
				||CollateralAction.MAKER_SUBMIT_INSURANCE_RECEIVED.equals(event)  || CollateralAction.MAKER_SUBMIT_INSURANCE_WAIVED.equals(event)
				|| CollateralAction.MAKER_EDIT_INSRECEIVED_LIST.equals(event) || CollateralAction.MAKER_UPDATE_INSRECEIVED_LIST.equals(event) 
				|| CollateralAction.MAKER_UPDATE_INSPENDING_LIST.equals(event) ||
				CollateralAction.MAKER_UPDATE_INSWAIVED_LIST.equals(event)  || CollateralAction.MAKER_UPDATE_INSDEFERRED_LIST.equals(event)
				|| CollateralAction.MAKER_EDIT_CANCEL_INSSTATUS.equals(event)) {
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
		}
		
		 //Uma Khot::Insurance Deferral maintainance
//		else if((null!=event && ( event.equals(CollateralAction.MAKER_SUBMIT_INSURANCE_DEFERRED)  || event.equals(CollateralAction.MAKER_SUBMIT_INSURANCE_RECEIVED) || event.equals(CollateralAction.MAKER_SUBMIT_INSURANCE_WAIVED)
//				|| event.equals(CollateralAction.MAKER_EDIT_INSRECEIVED_LIST)) || event.equals(CollateralAction.MAKER_UPDATE_INSRECEIVED_LIST)  || event.equals(CollateralAction.MAKER_UPDATE_INSPENDING_LIST)  || event.equals(CollateralAction.MAKER_UPDATE_INSWAIVED_LIST)  || event.equals(CollateralAction.MAKER_UPDATE_INSDEFERRED_LIST))){
//			aPage.setPageReference("maker_submit_insurance_page");
//		}
		else if (event.equals("maker_submit_ins_pending_error")) {
			aPage.setPageReference("maker_create_insurance_pending");
		}else if (event.equals("maker_submit_ins_deferred_error")) {
			aPage.setPageReference("maker_create_insurance_deferred");
		}else if (event.equals("maker_submit_ins_received_error")) {
			aPage.setPageReference("maker_create_insurance_received");
		}else if (event.equals("maker_submit_ins_waived_error")) {
			aPage.setPageReference("maker_create_insurance_waived");
		}else if (event.equals("maker_edit_insreceived_list_error")) {
			aPage.setPageReference("maker_edit_insurance_received");
		}else if (event.equals("maker_update_insreceived_list_error")) {
			aPage.setPageReference("maker_update_insurance_received");
		} else if (event.equals("maker_update_inspending_list_error")) {
			aPage.setPageReference("maker_update_insurance_pending");
		} else if (event.equals("maker_update_inswaived_list_error")) {
			aPage.setPageReference("maker_update_insurance_waived");
		} else if (event.equals("maker_update_insdeferred_list_error")) {
			aPage.setPageReference("maker_update_insurance_deferred");
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
