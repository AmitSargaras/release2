//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral.cash.cashfd;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
//import com.integrosys.cms.ui.collateral.FDListByReciptNoCommand;
import com.integrosys.cms.ui.collateral.ReadReturnCollateralCommand;
import com.integrosys.cms.ui.collateral.RefreshFacilityIdListCommand;
import com.integrosys.cms.ui.collateral.RefreshSystemListCommand;
import com.integrosys.cms.ui.collateral.cash.AddDepositCommand;
import com.integrosys.cms.ui.collateral.cash.CashAction;
import com.integrosys.cms.ui.collateral.cash.DeleteDepositCommand;
import com.integrosys.cms.ui.collateral.cash.DisplayDepositCommand;
import com.integrosys.cms.ui.collateral.cash.OkReturnCollateralCommand;
import com.integrosys.cms.ui.collateral.cash.PrepareDepositCommand;
import com.integrosys.cms.ui.collateral.cash.PrepareEditDepositCommand;
import com.integrosys.cms.ui.collateral.cash.PrepareLienDepositCommand;
import com.integrosys.cms.ui.collateral.cash.ReadDepositCommand;
import com.integrosys.cms.ui.collateral.cash.UpdateDepositCommand;
import com.integrosys.cms.ui.collateral.cash.ViewReadDepositCommand;

public class FixedDepositAction extends CashAction {
	public static final String EVENT_SEARCH_FD_INFO = "search_fd_info";
	public static final String EVENT_ADD_FD_INFO = "add_fd_info";
	public static final String EVENT_CANCEL_FD_SEARCH = "cancel_fd_search";
	
	public static final String EVENT_SEARCH_LIEN_INFO = "search_lien_info";	
	public static final String EVENT_DISPLAY_LIEN_DETAILS = "display_lien_details";	
	public static final String EVENT_EDIT_LIEN_DETAILS = "edit_lien_details";	
	public static final String EVENT_PROCESS_LIEN_DETAILS = "process_lien_details";
	
	public static final String EVENT_ADD_LIEN = "add_lien";
	public static final String EVENT_ADD_LIEN_IN_EDIT = "add_lien_in_edit";
	public static final String EVENT_ADD_LIEN_IN_RESUBMIT = "add_lien_in_resubmit";
	
	public static final String EVENT_SAVE_LIEN = "save_lien";
	public static final String EVENT_SAVE_LIEN_IN_EDIT = "save_lien_in_edit";
	public static final String EVENT_SAVE_LIEN_IN_RESUBMIT = "save_lien_in_resubmit";
	
	public static final String EVENT_PREPARE_CREATE_LIEN  ="prepare_create_lien";
	
	public static final String EVENT_EDIT_LIEN = "edit_lien";
	public static final String EVENT_EDIT_LIEN_IN_EDIT = "edit_lien_in_edit";
	public static final String EVENT_EDIT_LIEN_IN_RESUBMIT = "edit_lien_in_resubmit";
	
	public static final String EVENT_DELETE_LIEN = "delete_lien";
	public static final String EVENT_DELETE_LIEN_IN_EDIT = "delete_lien_in_edit";
	public static final String EVENT_DELETE_LIEN_IN_RESUBMIT = "delete_lien_in_resubmit";
	
	public static final String EVENT_EDIT_CONF_LIEN = "edit_conf_lien";
	public static final String EVENT_DELETE_CONF_LIEN = "delete_conf_lien";
	
	public static final String EVENT_OK_RETURN = "ok_return";
	
	public static final String OK_CANCEL = "ok_cancel";
	
	public static final String OK_RETURN = "ok_return";
	
	public static final String EVENT_VIEW_LIEN = "view_lien";
	
	public static final String EVENT_VIEW_DISPLAY_LIEN ="view_display_lien_details";
	
	public static final String VIEW_OK_RETURN = "view_ok_return";
	
	public static final String EVENT_REFRESH_SYSTEM_ID = "refresh_system_id";
	
	public static final String EVENT_REFRESH_FACILITY_ID = "refresh_facility_id";	
	//public static final String EVENT_FD_LIST_BY_RECIPT_NO = "FD_List_By_Recipt_No";
	
	
	public ICommand[] getCommandChain(String event) {
        ICommand objArray[] = new ICommand[1];
		if (EVENT_CREATE.equals(event)) {
			objArray[0] = new AddDepositCommand();
		}
		else if (EVENT_PREPARE.equals(event)) {
			objArray[0] = new PrepareDepositCommand();
		}
		else if (EVENT_PREPARE_UPDATE_SUB.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareEditDepositCommand();
			objArray[1] = new ReadDepositCommand();
		}
		else if (EVENT_UPDATE.equals(event)) {
			objArray[0] = new UpdateDepositCommand();
		}
		else if (EVENT_DELETE.equals(event)) {
			objArray[0] = new DeleteDepositCommand();
		}
		else if (EVENT_READ.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareEditDepositCommand();
			objArray[1] = new ViewReadDepositCommand();
		}
		else if (EVENT_READ_RETURN.equals(event) || EVENT_CANCEL.equals(event)) {
			objArray[0] = new ReadReturnCollateralCommand();
		}else if (EVENT_OK_RETURN.equals(event) || EVENT_CANCEL.equals(event)) {
			objArray[0] = new OkReturnCollateralCommand();
		}  
		else if (EVENT_SEARCH_FD_INFO.equals(event)) {
			objArray[0] = new SearchDepositAccountCommand();
		}
		else if (EVENT_ADD_FD_INFO.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new AddDepositInfoCommand();
			objArray[1] = new PrepareDepositCommand();
		} else if (EVENT_CANCEL_FD_SEARCH.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReturnDepositCommand();
			objArray[1] = new PrepareDepositCommand();
		}else if (event.equals(EVENT_SAVE_LIEN)
				|| event.equals(EVENT_SAVE_LIEN_IN_EDIT)
				|| event.equals(EVENT_SAVE_LIEN_IN_RESUBMIT)) {
			objArray = new ICommand[1];
			objArray[0] = new AddDepositCommand();
		}else if (event.equals(EVENT_PROCESS_LIEN_DETAILS)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadDepositCommand();
		}else if (event.equals(EVENT_DISPLAY_LIEN_DETAILS)	) {
			objArray = new ICommand[1];
			objArray[0] = new CancelLienCommand();
		}
		else if (event.equals(EVENT_VIEW_DISPLAY_LIEN))
		 {
			objArray = new ICommand[1];
			objArray[0] = new ReadDepositCommand();
		}
		else if (EVENT_SEARCH_LIEN_INFO.equals(event)) {
			objArray[0] = new SearchLienDepositAccountCommand();
		}else if (EVENT_PREPARE_CREATE_LIEN.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareLienDepositCommand();
			
		}else if (event.equals(EVENT_ADD_LIEN)
				|| event.equals(EVENT_ADD_LIEN_IN_EDIT)
				|| event.equals(EVENT_ADD_LIEN_IN_RESUBMIT)) {
			objArray = new ICommand[1];
			objArray[0] = new AddLienCommand();
		}else if (event.equals(EVENT_EDIT_LIEN)
				|| event.equals(EVENT_EDIT_LIEN_IN_EDIT)
				|| event.equals(EVENT_EDIT_LIEN_IN_RESUBMIT)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareEditLienCommand();
			
		}else if (event.equals(EVENT_DELETE_LIEN)
						|| event.equals(EVENT_DELETE_LIEN_IN_EDIT)
						|| event.equals(EVENT_DELETE_LIEN_IN_RESUBMIT)) {
					objArray = new ICommand[1];
					objArray[0] = new PrepareEditLienCommand();
		}else if (EVENT_EDIT_CONF_LIEN.equals(event)) {
			objArray[0] = new EditLienCommand();
		}else if (EVENT_DELETE_CONF_LIEN.equals(event)) {
			objArray[0] = new DeleteLienCommand();
		}else if (EVENT_VIEW_LIEN.equals(event)) {
			objArray[0] = new ViewLienCommand();
		}else if (OK_CANCEL.equals(event)) {
			objArray[0] = new okCancelLienCommand();
		}else if (OK_RETURN.equals(event)) {
			objArray[0] = new okCancelLienCommand();
		}else if (VIEW_OK_RETURN.equals(event)) {
			objArray[0] = new okCancelLienCommand();
		}else if (EVENT_REFRESH_SYSTEM_ID.equals(event)) {
			objArray[0] = new RefreshSystemListCommand();
		}else if(EVENT_REFRESH_FACILITY_ID.equals(event)){
			objArray[0] = new RefreshFacilityIdListCommand();
		}
			/*else {
		}
		 if (EVENT_FD_LIST_BY_RECIPT_NO.equals(event)) {
			objArray[0] = new FDListByReciptNoCommand();
		}		
		*/
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
	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		DefaultLogger.debug(this, "VALIDATION REQUIRED...");
		ActionErrors errors;
		if (EVENT_SEARCH_FD_INFO.equals(((FixedDepositForm) aForm).getEvent())) {
			errors = FixedDepositValidator.validateFDInfoSearch((FixedDepositForm)aForm, locale);
		} else {
			errors = FixedDepositValidator.validateInput((FixedDepositForm) aForm, locale);
		}
		return errors;
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
        if (EVENT_CREATE.equals(event) 
        		|| EVENT_UPDATE.equals(event) 
        		|| EVENT_SEARCH_FD_INFO.equals(event)
        		|| event.equals(EVENT_SAVE_LIEN)
				
				|| event.equals(EVENT_SAVE_LIEN_IN_EDIT)
				|| event.equals(EVENT_EDIT_LIEN_DETAILS)
				|| event.equals(EVENT_SAVE_LIEN_IN_RESUBMIT)
				|| event.equals(EVENT_PROCESS_LIEN_DETAILS)
				
				
				|| event.equals(EVENT_ADD_LIEN_IN_EDIT)
				|| event.equals(EVENT_ADD_LIEN_IN_RESUBMIT)
				
				|| event.equals(EVENT_OK_RETURN)
				
        ) {
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		
		String errorEvent = getDefaultEvent();
		
		if (EVENT_CREATE.equals(event)) {
			
		} else if (EVENT_UPDATE.equals(event)) {
			
		} else if (EVENT_SEARCH_FD_INFO.equals(event)) {
			errorEvent = "search_fd_info";
		} else if (EVENT_ADD_FD_INFO.equals(event)) {
			errorEvent = "add_fd_info";
		} else if (EVENT_CANCEL_FD_SEARCH.equals(event)) {
			errorEvent = "cancel_fd_search";
		} else if (EVENT_SAVE_LIEN.equals(event)) {
			errorEvent = "save_lien";
		} else if (EVENT_DISPLAY_LIEN_DETAILS.equals(event)) {
			errorEvent = "display_lien_details";
		} else if (EVENT_VIEW_DISPLAY_LIEN.equals(event)) {
			errorEvent = "view_display_lien_details";
		}else if (EVENT_SAVE_LIEN_IN_EDIT.equals(event)) {
			errorEvent = "save_lien_in_edit";
		} else if (EVENT_EDIT_LIEN_DETAILS.equals(event)) {
			errorEvent = "edit_lien_details";
		} else if (EVENT_SAVE_LIEN_IN_RESUBMIT.equals(event)) {
			errorEvent = "save_lien_in_resubmit";
		} else if (EVENT_PROCESS_LIEN_DETAILS.equals(event)) {
			errorEvent = "process_lien_details";
		} else if (EVENT_PREPARE_CREATE_LIEN.equals(event)) {
			errorEvent = "process_lien_details";
		} else if (EVENT_VIEW_LIEN.equals(event)) {
			errorEvent = "view_lien";
		}else if (EVENT_ADD_LIEN.equals(event)) {
			errorEvent = "prepare_create_lien";
		}else if (EVENT_EDIT_CONF_LIEN.equals(event)) {
			errorEvent = "edit_lien";
		}/*else if (EVENT_OK_RETURN.equals(event)) {
			errorEvent = "ok_cancel";
		}  */ 
		
		/*String errorEvent = event;
        if (EVENT_CREATE.equals(event) 
        		|| EVENT_UPDATE.equals(event) 
        		|| EVENT_SEARCH_FD_INFO.equals(event)
        		
        ) 
        */
		else {
			errorEvent = EVENT_PREPARE;
		}

		return errorEvent;
	}

	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
        Page aPage = new Page();
        if (EVENT_CREATE.equals(event) || EVENT_UPDATE.equals(event) || EVENT_READ_RETURN.equals(event)
				|| EVENT_CANCEL.equals(event)) {
			String subtype = (String) resultMap.get("subtype");
			if (subtype == null) {
                //todo forward to error page after populating the exceptionMap
                throw new RuntimeException("URL passed is wrong");
            }
			else if (EVENT_READ_RETURN.equals(event)) {
				aPage.setPageReference(subtype + "_" + (String) resultMap.get("from_event"));
			}
			else {
				aPage.setPageReference(subtype + "_update");
			}
		}
		else if (EVENT_ADD_FD_INFO.equals(event) || EVENT_CANCEL_FD_SEARCH.equals(event) ) {
			aPage.setPageReference(EVENT_PREPARE);
		}
		else if (EVENT_PREPARE_CREATE_LIEN.equals(event) ) {
			aPage.setPageReference("prepare_create_lien");
		}
        /*
         event.equals(EVENT_ADD_LIEN)
				|| event.equals(EVENT_ADD_LIEN_IN_EDIT)
				|| event.equals(EVENT_ADD_LIEN_IN_RESUBMIT)
          */
		else if (EVENT_ADD_LIEN.equals(event) ) {
			aPage.setPageReference("add_lien");
		}
		else if (EVENT_ADD_LIEN_IN_EDIT.equals(event) ) {
			aPage.setPageReference("add_lien_in_edit");
		}
		else if (EVENT_ADD_LIEN_IN_RESUBMIT.equals(event) ) {
			aPage.setPageReference("add_lien_in_resubmit");
		}else if (EVENT_SAVE_LIEN.equals(event) ) {
			aPage.setPageReference("save_lien");
		}
		else if (EVENT_SAVE_LIEN_IN_EDIT.equals(event) ) {
			aPage.setPageReference("save_lien_in_edit");
		}
		else if (EVENT_SAVE_LIEN_IN_RESUBMIT.equals(event) ) {
			aPage.setPageReference("save_lien_in_resubmit");
		}else if (EVENT_DISPLAY_LIEN_DETAILS.equals(event) ) {
			aPage.setPageReference("display_lien_details");
		}
		else if (EVENT_VIEW_DISPLAY_LIEN.equals(event) ) {
			aPage.setPageReference("view_display_lien_details");
		}
		else if (EVENT_EDIT_LIEN_DETAILS.equals(event) ) {
			aPage.setPageReference("edit_lien_details");
		}else if (EVENT_EDIT_LIEN.equals(event) ) {
			aPage.setPageReference("edit_lien");
		}else if (EVENT_VIEW_LIEN.equals(event) ) {
			aPage.setPageReference("view_lien");
		}else if (OK_CANCEL.equals(event) ) {
			aPage.setPageReference("ok_cancel");
		}else if (OK_RETURN.equals(event) ) {
			aPage.setPageReference("ok_return");
		}else if (VIEW_OK_RETURN.equals(event) ) {
			aPage.setPageReference("view_ok_return");
		}
		else if (EVENT_PROCESS_LIEN_DETAILS.equals(event) ) {
			aPage.setPageReference("process_lien_details");
		} else if ((event != null) && event.equals("save_edited_lien_error")) {
			aPage.setPageReference("edit_lien");
			
		} else if ((event != null)
				&& event.equals("save_edited_lien_in_edit_error")) {
			aPage.setPageReference("edit_lien");
		} else if ((event != null)
				&& event.equals("save_edited_lien_in_resubmit_error")) {
			aPage.setPageReference("edit_lien");
		}else if (EVENT_EDIT_CONF_LIEN.equals(event) ) {
			aPage.setPageReference("edit_conf_lien");
		}else if (EVENT_DELETE_CONF_LIEN.equals(event) ) {
			aPage.setPageReference("delete_conf_lien");
		}else if (EVENT_OK_RETURN.equals(event) ) {
			aPage.setPageReference("ok_return");
		}else if (event.equals("refresh_system_id")) {
			aPage.setPageReference("refresh_system_id");
		}else if (event.equals("FD_List_By_Recipt_No")) {
			aPage.setPageReference("FD_List_By_Recipt_No");
		}
        
		else {
			aPage.setPageReference(event);
		}
		return aPage;
	}
}
////

 