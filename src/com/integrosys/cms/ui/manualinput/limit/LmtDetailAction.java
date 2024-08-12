/*
 * Created on 2007-2-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.IPin;
import com.integrosys.base.uiinfra.common.Page;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class LmtDetailAction extends CommonAction implements IPin {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.uiinfra.common.CommonAction#getNextPage(java.lang
	 * .String, java.util.HashMap, java.util.HashMap)
	 */
	protected IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();

		if ((resultMap.get("wip") != null) && ((String) resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference("wip");
			return aPage;
		}/*else if (event.equals(EVENT_VALIDATE_CUSTOMER_FORM_IN_EDIT) || event.equals(EVENT_VALIDATE_CUSTOMER_FORM_IN_EDIT_SAVE)) {
			if (fromPage != null
					&& (fromPage.equals("edit_summary_details")
							|| fromPage.equals("edit_financial_details")
							|| fromPage.equals("edit_cri_details") || fromPage
							.equals("edit_cibil_details")
							|| fromPage.equals("edit_udf_details"))) {
				aPage.setPageReference("edit_summary_details");
				return aPage;
			} else {
				aPage.setPageReference("edit_summary_details");
				return aPage;
			}
		}*/

		aPage.setPageReference(getReference(event));
		return aPage;
	}

	private String getReference(String event) {
		if (EventConstant.EVENT_READ.equals(event) || EventConstant.EVENT_READ_RETURN.equals(event) || EventConstant.EVENT_CUST_READ.equals(event)) {
			return "read_page";
		}
		else if (EventConstant.EVENT_PREPARE_CREATE.equals(event) || EventConstant.EVENT_PREPARE_UPDATE.equals(event)
				|| EventConstant.EVENT_PROCESS_UPDATE.equals(event) || EventConstant.EVENT_UPDATE_RETURN.equals(event)
				|| EventConstant.EVENT_ERROR_RETURN.equals(event) || EventConstant.EVENT_DELETE_ITEM.equals(event)
				|| EventConstant.EVENT_PREPARE_CUST_UPDATE.equals(event)) {
			return "update_page";
		}
		else if (EventConstant.EVENT_PREPARE_UPDATE_REJECTED.equals(event)
				||EventConstant.EVENT_UPDATE_RETURN_REJECTED.equals(event)
				|| EventConstant.EVENT_ERROR_RETURN_REJECTED.equals(event)) {
			return "update_page_rejected";
		}
		else if (EventConstant.EVENT_PROCESS.equals(event) || EventConstant.EVENT_PROCESS_RETURN.equals(event) || EventConstant.EVENT_REJECT_ERROR.equals(event)) {
			return "process_page";
		}
		else if (EventConstant.EVENT_PREPARE_CLOSE.equals(event) || EventConstant.EVENT_CLOSE_RETURN.equals(event)
				|| EventConstant.EVENT_TRACK.equals(event) || EventConstant.EVENT_TRACK_RETURN.equals(event)) {
			return "close_page";
		}
		else if (EventConstant.EVENT_PREPARE_DELETE.equals(event) || EventConstant.EVENT_PROCESS_DELETE.equals(event)) {
			return "delete_page";
		}
		else if (EventConstant.EVENT_CREATE_SUB_ACNT.equals(event)) {
			return "create_acnt_detail";
		}
		else if (EventConstant.EVENT_UPDATE_SUB_ACNT.equals(event)) {
			return "update_acnt_detail";
		}
		else if (EventConstant.EVENT_CREATE_SUB_SEC.equals(event)) {
			return "create_lmtsec_detail";
		}
		else if (EventConstant.EVENT_CREATE.equals(event) || EventConstant.EVENT_SUBMIT.equals(event)
				|| EventConstant.EVENT_DELETE.equals(event)) {
			return "ack_submit";
		}
		else if (EventConstant.EVENT_SUBMIT_REJECTED.equals(event)) {
			return "ack_submit_rejected";
		}
		else if (EventConstant.EVENT_SAVE.equals(event)) {
			return "ack_save";
		}
		else if (EventConstant.EVENT_SAVE_REJECTED.equals(event)) {
			return "ack_save_rejected";
		}
		else if (EventConstant.EVENT_APPROVE.equals(event)) {
			return "ack_approve";
		}
		else if (EventConstant.EVENT_REJECT.equals(event)) {
			return "ack_reject";
		}
		else if (EventConstant.EVENT_CLOSE.equals(event)) {
			return "ack_close";
		}
		else if (EventConstant.EVENT_REFRESH_BOOKINGLOC.equals(event)) {
			return "refresh_loc";
		}
		else if (EventConstant.EVENT_REFRESH_PRODTYPE.equals(event)) {
			return "refresh_prod";
		}
		else if (EventConstant.EVENT_REFRESH_FACILITY_NAME.equals(event)) {
			return "refresh_fac";
		}
		else if (EventConstant.EVENT_REFRESH_RISK_TYPE.equals(event)) {
			return "refresh_risk";
		}
		else if (EventConstant.EVENT_REFRESH_FACILITY_DETAIL.equals(event)) {
			return "refresh_fac_detail";
		}
		else if (EventConstant.EVENT_REFRESH_INR_SANC.equals(event)) {
			return "refresh_inr_sanc";
		}
		else if (EventConstant.EVENT_VIEW_SUB_ACNT.equals(event)) {
			return "view_acnt_detail";
		}
		else if (EventConstant.EVENT_VIEW_SECURITY.equals(event)) {
			return "view_security_detail";
		}else if (EventConstant.EVENT_VIEW_SECURITY_REJECTED.equals(event)) {
			return "view_security_detail_rejected";
		}
		else if (EventConstant.EVENT_ERROR_APPROVE.equals(event)) {
			return EventConstant.EVENT_PROCESS;
			}
		else if (EventConstant.EVENT_REFRESH_FACILITY_LIABILITY.equals(event)) {
			return "refresh_fac_liability";
		}
		//Start: Uma:Phase 3 CR :Limit Calculation Dashboard
//		else if(EventConstant.EVENT_CALCULATE_LIMIT.equals(event)) {
//			return "calculate_limit";
//		}
		else if (EventConstant.EVENT_CANCEL.equals(event) ) {
			return "update_return";
		}
		else if (EventConstant.EVENT_CANCEL_REJECTED.equals(event) ) {
			return "update_return_rejected";
		}
		else if(EventConstant.EVENT_CREATE_LIMIT_DASHBAORD.equals(event)) {
			return "create_limit_dashboard";
			
		}
		else if(EventConstant.EVENT_DELETE_LIMIT_DASH_ITEM.equals(event)){
			return "add_limit_detail";
		}
		//End: Uma:Phase 3 CR :Limit Calculation Dashboard
		else if ((EventConstant.EVENT_EDIT_RELEASED_LINE_DETAILS.equals(event))
				|| (EventConstant.EVENT_EDIT_UDF.equals(event))) {
			return "update_sub_acnt";
		}
		else if ((EventConstant.EVENT_EDIT_RELEASED_LINE_DETAILS_REJECTED.equals(event))
				|| (EventConstant.EVENT_EDIT_UDF_REJECTED.equals(event))) {
			return "update_sub_acnt_rejected";
		}
		else if (EventConstant.EVENT_CREATE_SUB_ACNT_UBS.equals(event)) {
			return "create_acnt_detail_ubs";
		}else if (EventConstant.EVENT_CREATE_COVENANT_UBS.equals(event)) {
				return "create_covenant_detail_ubs";
		}else if (EventConstant.EVENT_VIEW_COVENANT_UBS.equals(event)) {
			return "view_covenant_detail_ubs";		
		}else if (EventConstant.EVENT_UPDATE_SUB_ACNT_UBS.equals(event)) {
			return "update_acnt_detail_ubs";
		}else if (EventConstant.EVENT_UPDATE_SUB_ACNT_UBS_REJECTED.equals(event)) {
			return "update_acnt_detail_ubs_rejected";
		}else if (EventConstant.EVENT_VIEW_SUB_ACNT_UBS.equals(event)) {
			return "view_acnt_detail_ubs";
		}else if (EventConstant.EVENT_CLOSE_SUB_ACNT_UBS.equals(event)) {
			return "close_acnt_detail_ubs";
		}else if (EventConstant.EVENT_REOPEN_SUB_ACNT_UBS.equals(event)) {
			return "reopen_acnt_detail_ubs";
		}else if (EventConstant.EVENT_CLOSE_SUB_ACNT_UBS_REJECTED.equals(event)) {
			return "close_acnt_detail_ubs_rejected";
		}else if (EventConstant.EVENT_REOPEN_SUB_ACNT_UBS_REJECTED.equals(event)) {
			return "reopen_acnt_detail_ubs_rejected";
		}else if (EventConstant.EVENT_READ_REJECTED.equals(event) ) {
			return "read_rejected_page";
		}else if (EventConstant.EVENT_VIEW_SUB_ACNT_UBS_REJECTED.equals(event)) {
			return "view_acnt_detail_ubs_rejected";
		}else if (EventConstant.EVENT_UPDATE_STATUS_SUB_ACNT_UBS.equals(event)) {
			return "updateStatus_acnt_detail_ubs";
		}
		else if(EventConstant.EVENT_CREATE_SUB_ACNT_TS.equals(event)){
			return "create_acnt_detail_ts";			
		}else if (EventConstant.EVENT_UPDATE_SUB_ACNT_TS.equals(event)) {
			return "update_acnt_detail_ts";
		}else if (EventConstant.EVENT_CLOSE_SUB_ACNT_TS.equals(event)) {
			return "close_acnt_detail_ts";
		}else if (EventConstant.EVENT_REOPEN_SUB_ACNT_TS.equals(event)) {
			return "reopen_acnt_detail_ts";
		}else if (EventConstant.EVENT_CLOSE_SUB_ACNT_TS_REJECTED.equals(event)) {
			return "close_acnt_detail_ts_rejected";
		}else if (EventConstant.EVENT_REOPEN_SUB_ACNT_TS_REJECTED.equals(event)) {
			return "reopen_acnt_detail_ts_rejected";
		}else if (EventConstant.EVENT_VIEW_SUB_ACNT_TS_REJECTED.equals(event)) {
			return "view_acnt_detail_ts_rejected";
		}else if (EventConstant.EVENT_VIEW_SUB_ACNT_TS.equals(event)) {
			return "view_acnt_detail_ts";
		}else if (EventConstant.EVENT_UPDATE_STATUS_SUB_ACNT_TS.equals(event)) {
			return "updateStatus_acnt_detail_ts";
		
		}else {
			return event;
		}
	}

	protected String getErrorEvent(String event) {
		String errorEvent = event;
		if (EventConstant.EVENT_SUBMIT.equals(event) || EventConstant.EVENT_SAVE.equals(event)
				|| EventConstant.EVENT_CREATE.equals(event)) {
			return EventConstant.EVENT_ERROR_RETURN;
		}
		if (EventConstant.EVENT_SUBMIT_REJECTED.equals(event)
				||EventConstant.EVENT_SAVE_REJECTED.equals(event)) {
			return EventConstant.EVENT_ERROR_RETURN_REJECTED;
		}
		if (EventConstant.EVENT_REJECT.equals(event)) {
			return EventConstant.EVENT_PROCESS;
		}
		if ( EventConstant.EVENT_APPROVE.equals(event)) {
			return EventConstant.EVENT_PROCESS;
		}
		return errorEvent;
	}

	protected boolean isValidationRequired(String event) {
		if (EventConstant.EVENT_SUBMIT.equals(event) || EventConstant.EVENT_CREATE.equals(event)
				|| EventConstant.EVENT_SAVE.equals(event) 
				||EventConstant.EVENT_SUBMIT_REJECTED.equals(event)
				|| EventConstant.EVENT_SAVE_REJECTED.equals(event)) { //|| EventConstant.EVENT_APPROVE.equals(event) // removed code on 31 july for un check validation on checker approve 
			return true;
		}
		return false;
	}

	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		return MILimitValidator.validateMILimit(aForm, locale);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.uiinfra.common.CommonAction#getCommandChain(java.
	 * lang.String)
	 */
	protected ICommand[] getCommandChain(String event) {
		ICommand[] objArray = null;
		// TODO Auto-generated method stub
		if (EventConstant.EVENT_PREPARE_CREATE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareCreateLmtCmd();
			objArray[1] = new PrepareLmtDetailCmd();
		}
		else if (EventConstant.EVENT_READ.equals(event) || EventConstant.EVENT_PROCESS.equals(event)
				|| EventConstant.EVENT_TRACK.equals(event) || EventConstant.EVENT_PREPARE_CLOSE.equals(event)
				|| EventConstant.EVENT_PREPARE_DELETE.equals(event) || EventConstant.EVENT_PROCESS_DELETE.equals(event)
				|| EventConstant.EVENT_CUST_READ.equals(event) 
				|| EventConstant.EVENT_READ_SECURITY.equals(event) 
				|| EventConstant.EVENT_READ_SECURITY_REJECTED.equals(event) 
				||EventConstant.EVENT_READ_REJECTED.equals(event)){  //Shiv
			objArray = new ICommand[1];
			objArray[0] = new ReadLmtDetailCmd();
		}
		else if (EventConstant.EVENT_PREPARE_UPDATE.equals(event) || EventConstant.EVENT_PROCESS_UPDATE.equals(event) 
				|| EventConstant.EVENT_PREPARE_CUST_UPDATE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadLmtDetailCmd();
			objArray[1] = new PrepareLmtDetailCmd();
		}
		else if (EventConstant.EVENT_PREPARE_UPDATE_REJECTED.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadLmtDetailCmd();
			objArray[1] = new PrepareLmtDetailCmd();
		}
		else if (EventConstant.EVENT_PROCESS_RETURN.equals(event) || EventConstant.EVENT_READ_RETURN.equals(event)
				|| EventConstant.EVENT_CLOSE_RETURN.equals(event) || EventConstant.EVENT_TRACK_RETURN.equals(event)) {
			objArray = new ICommand[2];
			 objArray[0] = new ReturnLmtDetailCmd();
			 objArray[1] = new PrepareLmtDetailCmd();
		}
		else if (EventConstant.EVENT_UPDATE_RETURN.equals(event)
				||EventConstant.EVENT_UPDATE_RETURN_REJECTED.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReturnLmtDetailCmd();
			objArray[1] = new PrepareLmtDetailCmd();
		}
		else if (EventConstant.EVENT_CREATE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerCreateLmtDetailCmd();
		}
		else if (EventConstant.EVENT_SUBMIT.equals(event)||EventConstant.EVENT_SUBMIT_REJECTED.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerUpdateLmtDetailCmd();
		}
		else if (EventConstant.EVENT_SAVE.equals(event)
				||EventConstant.EVENT_SAVE_REJECTED.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerSaveLmtDetailCmd();
		}
		else if (EventConstant.EVENT_APPROVE.equals(event)) {
			objArray = new ICommand[5];
			objArray[0] = new CheckerApproveLmtDetailCmd();
			objArray[1] = new MaintainOtherCheckListCmdLmtProp();
			objArray[2] = new SubmitOtherCheckListCmdLmtProp();
			objArray[3] = new SubmitOtherReceiptCmdLmtProp();
			objArray[4] = new ApproveOtherReceiptCmdLmtProp();
		}
		else if (EventConstant.EVENT_REJECT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerRejectLmtDetailCmd();
		}
		else if (EventConstant.EVENT_CLOSE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerCloseLmtDetailCmd();
		}
		else if (EventConstant.EVENT_CREATE_SUB_ACNT.equals(event) || EventConstant.EVENT_UPDATE_SUB_ACNT.equals(event)
				|| EventConstant.EVENT_CREATE_SUB_SEC.equals(event) || EventConstant.EVENT_CREATE_LIMIT_DASHBAORD.equals(event)
				|| EventConstant.EVENT_CREATE_SUB_ACNT_UBS.equals(event) || EventConstant.EVENT_UPDATE_SUB_ACNT_UBS.equals(event)
				|| EventConstant.EVENT_UPDATE_SUB_ACNT_UBS_REJECTED.equals(event) || EventConstant.EVENT_UPDATE_STATUS_SUB_ACNT_UBS.equals(event)
				|| EventConstant.EVENT_CREATE_SUB_ACNT_TS.equals(event) || EventConstant.EVENT_UPDATE_SUB_ACNT_TS.equals(event)
				|| EventConstant.EVENT_UPDATE_SUB_ACNT_TS_REJECTED.equals(event) || EventConstant.EVENT_UPDATE_STATUS_SUB_ACNT_TS.equals(event)
				||EventConstant.EVENT_CREATE_COVENANT_UBS.equals(event) ||EventConstant.EVENT_VIEW_COVENANT_UBS.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SaveCurWorkingLmtCmd();
		}
		
		else if (EventConstant.EVENT_DELETE_ITEM.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new DeleteItemCmd();
			objArray[1] = new PrepareLmtDetailCmd();
		}
		else if (EventConstant.EVENT_REFRESH_BOOKINGLOC.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RefreshLmtBookingLocCmd();
		}
		else if (EventConstant.EVENT_REFRESH_PRODTYPE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RefreshLmtProdTypeCmd();
		}
		else if (EventConstant.EVENT_ERROR_RETURN.equals(event)||EventConstant.EVENT_ERROR_RETURN_REJECTED.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareLmtDetailCmd();
			objArray[1] = new MapLimitListCmd();
		}
		else if (EventConstant.EVENT_DELETE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new MakerDeleteLmtDetailCmd();
		}else if (EventConstant.EVENT_REFRESH_FACILITY_NAME.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RefreshLmtFacNameCmd();
		}else if (EventConstant.EVENT_REFRESH_RISK_TYPE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RefreshRiskTypeCmd();
		}
		else if (EventConstant.EVENT_REFRESH_FACILITY_DETAIL.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RefreshLmtFacDetailCmd();
		}
		else if (EventConstant.EVENT_REFRESH_INR_SANC.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RefreshSancAmountCmd();
		}
		else if (EventConstant.EVENT_VIEW_SUB_ACNT.equals(event) || EventConstant.EVENT_VIEW_SUB_ACNT_UBS.equals(event)
				|| EventConstant.EVENT_VIEW_SUB_ACNT_UBS_REJECTED.equals(event)
				|| EventConstant.EVENT_CLOSE_SUB_ACNT_UBS.equals(event) || EventConstant.EVENT_REOPEN_SUB_ACNT_UBS.equals(event)
				|| EventConstant.EVENT_CLOSE_SUB_ACNT_UBS_REJECTED.equals(event) || EventConstant.EVENT_REOPEN_SUB_ACNT_UBS_REJECTED.equals(event)
				|| EventConstant.EVENT_VIEW_SUB_ACNT_TS.equals(event) || EventConstant.EVENT_VIEW_SUB_ACNT_TS_REJECTED.equals(event)
				|| EventConstant.EVENT_CLOSE_SUB_ACNT_TS.equals(event) || EventConstant.EVENT_REOPEN_SUB_ACNT_TS.equals(event)
				|| EventConstant.EVENT_CLOSE_SUB_ACNT_TS_REJECTED.equals(event) || EventConstant.EVENT_REOPEN_SUB_ACNT_TS_REJECTED.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ViewCurWorkingLmtCmd();
		}
		else if (EventConstant.EVENT_VIEW_SECURITY.equals(event)
				||EventConstant.EVENT_VIEW_SECURITY_REJECTED.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ViewSecurityCmd();
		}
		else if (EventConstant.EVENT_REFRESH_FACILITY_LIABILITY.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RefreshLmtFacLiabilityCmd();
		}
		
		//Start: Uma:Phase 3 CR :Limit Calculation Dashboard

		else if (EventConstant.EVENT_ADD_LIMIT_DETAIL.equals(event)){
			objArray = new ICommand[1];
			objArray[0] = new LimitDetailCmd();
		}else if (EventConstant.EVENT_DELETE_LIMIT_DASH_ITEM.equals(event)){
			objArray = new ICommand[1];
			objArray[0] = new DeleteDashboardItemCmd();
		}
		
		//End: Uma:Phase 3 CR :Limit Calculation Dashboard
		return objArray;
	}

}
