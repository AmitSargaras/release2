/*
 * Created on 2007-2-16
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class EventConstant {
	public static final String EVENT_PREPARE_CREATE = "prepare_create";

	public static final String EVENT_READ = "read";

	public static final String EVENT_PREPARE_UPDATE = "prepare_update";
	public static final String EVENT_PREPARE_UPDATE_REJECTED = "prepare_update_rejected";

	public static final String EVENT_PREPARE_DELETE = "prepare_delete";

	// update draft
	public static final String EVENT_PROCESS_UPDATE = "process_update";

	public static final String EVENT_PROCESS_DELETE = "process_delete";

	public static final String EVENT_PROCESS = "process";

	public static final String EVENT_TRACK = "track";

	public static final String EVENT_PREPARE_CLOSE = "prepare_close";

	public static final String EVENT_READ_RETURN = "read_return";

	public static final String EVENT_UPDATE_RETURN = "update_return";
	public static final String EVENT_UPDATE_RETURN_REJECTED = "update_return_rejected";
	public static final String EVENT_PROCESS_RETURN = "process_return";

	public static final String EVENT_CLOSE_RETURN = "close_return";

	public static final String EVENT_TRACK_RETURN = "track_return";

	public static final String EVENT_ERROR_RETURN = "error_return";
	public static final String EVENT_ERROR_RETURN_REJECTED = "error_return_rejected";

	public static final String EVENT_CREATE = "create";

	public static final String EVENT_SUBMIT = "submit";
	public static final String EVENT_SUBMIT_REJECTED = "submit_rejected";

	public static final String EVENT_SAVE = "save";
	public static final String EVENT_SAVE_REJECTED = "save_rejected";

	public static final String EVENT_APPROVE = "approve";

	public static final String EVENT_REJECT = "reject";

	public static final String EVENT_CLOSE = "close";

	public static final String EVENT_CANCEL = "cancel";
	public static final String EVENT_CANCEL_REJECTED = "cancel_rejected";
	
	public static final String EVENT_DELETE = "delete";

	public static final String EVENT_CREATE_SUB_ACNT = "create_sub_acnt";

	public static final String EVENT_UPDATE_SUB_ACNT = "update_sub_acnt";

	public static final String EVENT_CREATE_SUB_SEC = "create_sub_sec";

	public static final String EVENT_DELETE_ITEM = "delete_item";

	public static final String EVENT_REFRESH_BOOKINGLOC = "refresh_bookingloc";

	public static final String EVENT_REFRESH_PRODTYPE = "refresh_prodtype";

	public static final String EVENT_REFRESH_HOSTSYSCOUNTRY = "refresh_hostsyscountry";

	public static final String EVENT_SEARCH_SECDETAIL = "search_secdetail";

	public static final String EVENT_LIST_LIMIT = "list_limit";

	public static final String EVENT_SHOW_LMT_DETAIL = "show_lmt_detail";

	public static final String EVENT_DEL_LIMIT = "del_limit";

	public static final String EVENT_CREATE_NEW_SEC = "new_sec";
	
	//Shiv
	public static final String EVENT_REFRESH_FACILITY_NAME = "refresh_facility_name";

	public static final String EVENT_REFRESH_RISK_TYPE = "refresh_risk_type";

	public static final String EVENT_REFRESH_FACILITY_DETAIL = "refresh_facility_detail";

	public static final String EVENT_VIEW_SUB_ACNT = "view_sub_acnt";
	
	public static final String EVENT_SHOW_LMT_CUST_DETAIL = "show_lmt_cust_detail";
	
	public static final String EVENT_CUST_READ = "custread";
	
	public static final String EVENT_PREPARE_CUST_UPDATE = "prepare_cust_update";
	
	public static final String EVENT_REJECT_ERROR = "reject_error";
	
	public static final String EVENT_REFRESH_INR_SANC = "refresh_inr_sanc";
	
	public static final String EVENT_VIEW_SECURITY = "view_security";
	public static final String EVENT_VIEW_SECURITY_REJECTED = "view_security_rejected";
	
	public static final String EVENT_READ_SECURITY = "read_security";
	public static final String EVENT_READ_SECURITY_REJECTED = "read_security_rejected";
	
	public static final String EVENT_ERROR_APPROVE = "error_approve";
	
	public static final String EVENT_REFRESH_FACILITY_LIABILITY = "refresh_facility_liability";
	
	public static final String EVENT_SEARCH_SECPROPDETAIL = "search_secpropdetail";
	
	//Start: Uma:Phase 3 CR :Limit Calculation Dashboard
	public static final String EVENT_CALCULATE_LIMIT= "calculate_limit";
	public static final String EVENT_CREATE_LIMIT_DASHBAORD= "create_limit_dashboard";
	public static final String EVENT_ADD_LIMIT_DETAIL ="add_limit_detail";
	public static final String EVENT_DELETE_LIMIT_DASH_ITEM="delete_limit_dashboard_item";
	//End: Uma:Phase 3 CR :Limit Calculation Dashboard 
	public static final String EVENT_CREATE_RELEASED_LINE_DETAILS="create_released_line_details";
	public static final String EVENT_CREATE_UDF="create_udf";
	public static final String EVENT_EDIT_RELEASED_LINE_DETAILS="edit_released_line_details";
	public static final String EVENT_EDIT_RELEASED_LINE_DETAILS_REJECTED="edit_released_line_details_rejected";
	public static final String EVENT_EDIT_UDF="edit_udf";
	public static final String EVENT_EDIT_UDF_REJECTED="edit_udf_rejected";
	public static final String EVENT_READ_RELEASED_LINE_DETAILS="read_released_line_details";
	public static final String EVENT_READ_UDF="read_udf";
	public static final String EVENT_READ_RELEASED_LINE_DETAILS_REJECTED="read_released_line_details_rejected";
	public static final String EVENT_READ_UDF_REJECTED="read_udf_rejected";
	public static final String EVENT_CREATE_SUB_ACNT_UBS = "create_sub_acnt_ubs";
	public static final String EVENT_CREATE_COVENANT_UBS = "create_covenant_detail_ubs";
	public static final String EVENT_VIEW_COVENANT_UBS = "view_covenant_detail_ubs";
	public static final String EVENT_CREATE_COVENANT_LINE = "create_covenant_detail_line";
	public static final String EVENT_VIEW_COVENANT_LINE = "view_covenant_detail_line";

	
	public static final String EVENT_CREATE_SUB_ACNT_TS ="create_sub_acnt_ts";

	public static final String EVENT_UPDATE_SUB_ACNT_UBS = "update_sub_acnt_ubs";
	public static final String EVENT_UPDATE_SUB_ACNT_TS = "update_sub_acnt_ts";
	public static final String EVENT_UPDATE_SUB_ACNT_UBS_REJECTED = "update_sub_acnt_ubs_rejected";
	public static final String EVENT_UPDATE_SUB_ACNT_TS_REJECTED = "update_sub_acnt_ts_rejected";
	public static final String EVENT_PREPARE_CREATE_UBS = "prepare_create_ubs";
	public static final String EVENT_ADD_CO_BORROWER_CREATE = "add_coBorrower_create";
	public static final String EVENT_ADD_CO_BORROWER = "add_coBorrower";

	public static final String EVENT_DELETE_CO_BORROWER = "delete_coBorrower_rest";
	
	public static final String EVENT_ADD_CO_BORROWER_1 = "add_coBorrower_1";
	public static final String EVENT_DELETE_CO_BORROWER_1 = "delete_coBorrower_rest_1";
	
	
	public static final String EVENT_PREPARE_CREATE_TS = "prepare_create_ts";
	public static final String EVENT_PREPARE_UPDATE_UBS = "prepare_update_ubs";
	public static final String EVENT_PREPARE_UPDATE_TS = "prepare_update_ts";
	public static final String EVENT_PREPARE_UPDATE_UBS_REJECTED = "prepare_update_ubs_rejected";
	public static final String EVENT_PREPARE_UPDATE_TS_REJECTED = "prepare_update_ts_rejected";

	public static final String EVENT_CREATE_UBS = "create_ubs";
	public static final String EVENT_CREATE_TS = "create_ts";
	public static final String EVENT_SUBMIT_UBS = "submit_ubs";
	public static final String EVENT_SUBMIT_TS = "submit_ts";
	public static final String EVENT_SUBMIT_UBS_REJECTED = "submit_ubs_rejected";
	public static final String EVENT_VIEW_SUB_ACNT_UBS = "view_sub_acnt_ubs";
	public static final String EVENT_READ_UBS = "read_ubs";
	public static final String EVENT_READ_UBS_REJECTED = "read_ubs_rejected";
	public static final String EVENT_SUBMIT_TS_REJECTED = "submit_ts_rejected";
	public static final String EVENT_VIEW_SUB_ACNT_TS = "view_sub_acnt_ts";
	public static final String EVENT_READ_TS = "read_ts";
	public static final String EVENT_READ_TS_REJECTED = "read_ts_rejected";
	
	public static final String EVENT_CLOSE_SUB_ACNT_UBS ="close_sub_acnt_ubs";
	public static final String EVENT_REOPEN_SUB_ACNT_UBS ="reopen_sub_acnt_ubs";
	public static final String EVENT_CLOSE_SUB_ACNT_UBS_REJECTED ="close_sub_acnt_ubs_rejected";
	public static final String EVENT_REOPEN_SUB_ACNT_UBS_REJECTED ="reopen_sub_acnt_ubs_rejected";
	public static final String EVENT_CLOSE_SUB_ACNT_TS ="close_sub_acnt_ts";
	public static final String EVENT_REOPEN_SUB_ACNT_TS ="reopen_sub_acnt_ts";
	public static final String EVENT_CLOSE_SUB_ACNT_TS_REJECTED ="close_sub_acnt_ts_rejected";
	public static final String EVENT_REOPEN_SUB_ACNT_TS_REJECTED ="reopen_sub_acnt_ts_rejected";

	public static final String EVENT_CLOSE_UBS = "close_ubs";
	public static final String EVENT_REOPEN_UBS = "reopen_ubs";
	public static final String EVENT_CLOSE_UBS_REJECTED = "close_ubs_rejected";
	public static final String EVENT_REOPEN_UBS_REJECTED = "reopen_ubs_rejected";
	public static final String EVENT_CLOSE_TS = "close_ts";
	public static final String EVENT_REOPEN_TS = "reopen_ts";
	public static final String EVENT_CLOSE_TS_REJECTED = "close_ts_rejected";
	public static final String EVENT_REOPEN_TS_REJECTED = "reopen_ts_rejected";

	public static final String EVENT_PREPARE_REOPEN_UBS = "prepare_reopen_ubs";
	public static final String EVENT_PREPARE_CLOSE_UBS = "prepare_close_ubs";
	public static final String EVENT_PREPARE_REOPEN_UBS_REJECTED = "prepare_reopen_ubs_rejected";
	public static final String EVENT_PREPARE_CLOSE_UBS_REJECTED = "prepare_close_ubs_rejected";
	public static final String EVENT_PREPARE_REOPEN_TS = "prepare_reopen_ts";
	public static final String EVENT_PREPARE_CLOSE_TS = "prepare_close_ts";
	public static final String EVENT_PREPARE_REOPEN_TS_REJECTED = "prepare_reopen_ts_rejected";
	public static final String EVENT_PREPARE_CLOSE_TS_REJECTED = "prepare_close_ts_rejected";

	public static final String EVENT_CLOSE_UDF = "close_udf";
	public static final String EVENT_REOPEN_UDF = "reopen_udf";
	public static final String EVENT_CLOSE_RELEASED_LINE_DETAILS="close_released_line_details";
	public static final String EVENT_REOPEN_RELEASED_LINE_DETAILS="reopen_released_line_details";
	public static final String EVENT_CLOSE_UDF_REJECTED = "close_udf_rejected";
	public static final String EVENT_REOPEN_UDF_REJECTED = "reopen_udf_rejected";
	public static final String EVENT_CLOSE_RELEASED_LINE_DETAILS_REJECTED="close_released_line_details_rejected";
	public static final String EVENT_REOPEN_RELEASED_LINE_DETAILS_REJECTED="reopen_released_line_details_rejected";
	
	//added by santosh for UBS CR
	public static final String EVENT_SHOW_REJECTED_LMT_DETAIL = "show_rejected_lmt_detail";
	public static final String EVENT_READ_REJECTED = "readRejected";
	public static final String EVENT_VIEW_SUB_ACNT_UBS_REJECTED = "view_sub_acnt_ubs_rejected";
	public static final String EVENT_VIEW_SUB_ACNT_TS_REJECTED = "view_sub_acnt_ts_rejected";
	//end santosh
	public static final String EVENT_SUBMIT_UBS_ERROR = "submit_ubs_error";
	public static final String EVENT_SUBMIT_TS_ERROR = "submit_ts_error";
	
	public static final String EVENT_UPDATE_STATUS_SUB_ACNT_UBS ="updateStatus_sub_acnt_ubs";
	public static final String EVENT_UPDATE_STATUS_UBS = "updateStatus_ubs";
	public static final String EVENT_PREPARE_UPDATE_STATUS_UBS = "prepare_updateStatus_ubs";
	public static final String EVENT_UPDATE_STATUS_UDF = "updateStatus_udf";
	public static final String EVENT_UPDATE_STATUS_RELEASED_LINE_DETAILS="updateStatus_released_line_details";
	public static final String EVENT_UPDATE_STATUS_SUB_ACNT_TS ="updateStatus_sub_acnt_ts";
	public static final String EVENT_UPDATE_STATUS_TS = "updateStatus_ts";
	public static final String EVENT_PREPARE_UPDATE_STATUS_TS = "prepare_updateStatus_ts";

	public static final String EVENT_UPDATE_STATUS_UBS_ERROR = "updateStatus_ubs_error";
	
	public static final String FETCH_UTILIZEDAMT = "fetch_utilizedAmt";
	public static final String FETCH_LIABBRANCH = "fetch_LiabBranch";
	
	public static final String FETCH_COBORROWER_NAME_FCUBS = "fetch_CoBorrowerName_FCUBS";
	public static final String FETCH_COBORROWER_NAME_BH = "fetch_CoBorrowerName_BH";
	public static final String FETCH_COBORROWER_NAME_HK = "fetch_CoBorrowerName_HK";
	public static final String FETCH_COBORROWER_NAME_GC = "fetch_CoBorrowerName_GC"; 
	
	public static final String EVENT_COVENANT_IN_EDIT = "covenant_in_edit";
	
	public static final String EVENT_RETURN_FROM_LINE_COVENANT = "return_from_line_covenant";
}
