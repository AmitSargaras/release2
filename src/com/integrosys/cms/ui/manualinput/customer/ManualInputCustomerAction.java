/**
 * Copyrfight Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.ui.manualinput.customer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;

/**
 * Manual input customer action.
 * 
 * @author $Author: Jerlin, Pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class ManualInputCustomerAction extends CommonAction {

	/** Property Keys */
	public static final String SEARCH_CIF_COUNT_KEY = "manualInputCustomer.searchCIF.count";

	public static final String SEARCH_CIF_FREQUENCY_KEY = "manualInputCustomer.searchCIF.frequency";

	// Event Added by Sandeep Shinde on 25-Feb-2011 Start

	private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	public static final String EVENT_LIST_CUSTOMERS = "list_customers";
	
	public static final String EVENT_LIST_CUSTOMERS_BRANCH = "list_customers_branch";
	
	public static final String EVENT_LIST_CUSTOMER_CHECKER = "list_customer_checker";
	
	public static final String EVENT_LIST_CUSTOMER_BRCHECKER = "list_customer_brchecker";

	public static final String EVENT_PREPARE_CREATE_CUSTOMER = "prepare_create_customer";
	
	public static final String EVENT_PREPARE_CREATE_CUSTOMER_BRMAKER = "prepare_create_customer_brmaker";

	public static final String EVENT_CREATE_CUSTOMER = "create_customer";
	
	public static final String EVENT_CREATE_CUSTOMER_BRMAKER = "create_customer_brmaker";

	public static final String EVENT_PREPARE_EDIT_CUSTOMER_DETAILS = "edit_customer_details";
	
	public static final String EVENT_PREPARE_EDIT_CUSTOMER_DETAILS_BRMAKER = "edit_customer_details_brmaker";

	public static final String EVENT_EDIT_CUSTOMER = "edit_customer";

	public static final String EVENT_PREPARE_DELETE_CUSTOMER_DETAILS = "prepare_delete_customer";
	
	public static final String EVENT_PREPARE_DELETE_SUMMARY_DETAILS = "prepare_delete_summary_details";

	public static final String EVENT_PREPARE_DELETE_FINANCIAL_DETAILS = "prepare_delete_financial_details";

	public static final String EVENT_PREPARE_DELETE_CRI_DETAILS = "prepare_delete_cri_details";

	public static final String EVENT_PREPARE_DELETE_CIBIL_DETAILS = "prepare_delete_cibil_details";

	public static final String EVENT_MAKER_DELETE_CUSTOMER = "maker_delete_customer";

	public static final String EVENT_DELETE_CUSTOMER_DETAILS = "delete_customer_details";

	public static final String EVENT_TOTRACK_CHECKER = "totrack_checker";

	public static final String EVENT_TOTRACK_MAKER = "totrack_maker"; // maker_create
	
	public static final String EVENT_MAKER_SAVE_PROCESS = "maker_save_process"; // maker_create

	public static final String EVENT_TO_TRACK_SUMMARY_DETAILS = "to_track_summary_details";

	public static final String EVENT_TO_TRACK_FINANCIAL_DETAILS = "to_track_financial_details";

	public static final String EVENT_TO_TRACK_CRI_DETAILS = "to_track_cri_details";

	public static final String EVENT_TO_TRACK_CIBIL_DETAILS = "to_track_cibil_details";

	public static final String EVENT_CHECKER_APPROVE_CREATE_CUSTOMER = "checker_approve_create_customer";
	
	public static final String EVENT_CHECKER_APPROVE_CREATE_CUSTOMER_BRCHECKER = "checker_approve_create_customer_brchecker";

	public static final String EVENT_CHECKER_REJECT_CREATE_CUSTOMER = "checker_reject_create_customer";

	public static final String EVENT_CREATE_CHECKER_PROCESS = "create_checker_process";

	public static final String EVENT_CREATE_CHECKER_SUMMARY_DETAILS = "create_checker_summary_details";

	public static final String EVENT_CREATE_CHECKER_FINANCIAL_DETAILS = "create_checker_financial_details";

	public static final String EVENT_CREATE_CHECKER_CRI_DETAILS = "create_checker_cri_details";

	public static final String EVENT_CREATE_CHECKER_CIBIL_DETAILS = "create_checker_cibil_details";

	public static final String EVENT_MAKER_EDIT = "checker_edit";

	public static final String EVENT_MAKER_UPDATE_CUSTOMER = "maker_update_customer";
	
	public static final String EVENT_MAKER_UPDATE_CUSTOMER_BRMAKER = "maker_update_customer_brmaker";

	public static final String EVENT_CHECKER_UPDATE_SUMMARY_DETAILS = "checker_update_summary_details";

	public static final String EVENT_CHECKER_UPDATE_FINANCIAL_DETAILS = "checker_update_financial_details";

	public static final String EVENT_CHECKER_UPDATE_CRI_DETAILS = "checker_update_cri_details";

	public static final String EVENT_CHECKER_UPDATE_CIBIL_DETAILS = "checker_update_cibil_details";

	public static final String EVENT_CLOSE_SUMMARY_DETAILS = "close_summary_details";

	public static final String EVENT_CLOSE_FINANCIAL_DETAILS = "close_financial_details";

	public static final String EVENT_CLOSE_CRI_DETAILS = "close_cri_details";

	public static final String EVENT_CLOSE_CIBIL_DETAILS = "close_cibil_details";

	public static final String EVENT_CHECKER_APPROVE_UPDATE_CUSTOMER = "checker_approve_update_customer";

	public static final String EVENT_CHECKER_REJECT_UPDATE_CUSTOMER = "checker_reject_update_customer";

	public static final String EVENT_MAKER_CANCEL_UPDATE = "maker_cancel_update";

	public static final String EVENT_MAKER_PREPARE_RESUBMIT_UPDATE = "maker_prepare_resubmit_update";

	public static final String EVENT_MAKER_RESUBMIT_UPDATE = "maker_resubmit_update";

	public static final String EVENT_MAKER_RESUBMIT_UPDATE_CUSTOMER = "maker_resubmit_update_customer";

	public static final String EVENT_TOTRACK_MAKER_DELETE = "totrack_maker_delete";

	public static final String EVENT_TODO_CHECKER_DELETE = "todo_checker_delete";

	public static final String EVENT_TODO_CHECKER_DELETE_SUMMARY_DETAILS = "todo_checker_delete_summary_details";

	public static final String EVENT_TODO_CHECKER_DELETE_FINANCIAL_DETAILS = "todo_checker_delete_financial_details";

	public static final String EVENT_TODO_CHECKER_DELETE_CRI_DETAILS = "todo_checker_delete_cri_details";

	public static final String EVENT_TODO_CHECKER_DELETE_CIBIL_DETAILS = "todo_checker_delete_cibil_details";

	public static final String EVENT_CHECKER_APPROVE_DELETE_CUSTOMER = "checker_approve_delete_customer";

	public static final String EVENT_CHECKER_REJECT_DELETE_CUSTOMER = "checker_reject_delete_customer";

	public static final String EVENT_REJECTED_DELETE_READ = "rejected_delete_read";

	public static final String EVENT_PROCESS_PAGE = "process_page";
	
	public static final String EVENT_PROCESS_PAGE_BRMAKER = "process_page_brmaker";

	public static final String EVENT_PREPARE_CLOSE_PAGE = "prepare_close_page";
	
	public static final String EVENT_MAKER_SAVE_CLOSE = "maker_save_close";	

	public static final String EVENT_CLOSE_PAGE = "close_page";

	public static final String EVENT_DISPLAY_SUMMARY_DETAILS = "display_summary_details";

	public static final String EVENT_DISPLAY_FINANCIAL_DETAILS = "display_financial_details";

	public static final String EVENT_DISPLAY_CRI_DETAILS = "display_cri_details";

	public static final String EVENT_DISPLAY_CIBIL_DETAILS = "display_cibil_details";

	public static final String EVENT_EDIT_SUMMARY_DETAILS = "edit_summary_details";

	public static final String EVENT_EDIT_FINANCIAL_DETAILS = "edit_financial_details";

	public static final String EVENT_EDIT_CRI_DETAILS = "edit_cri_details";

	public static final String EVENT_EDIT_CIBIL_DETAILS = "edit_cibil_details";

	public static final String EVENT_PROCESS_SUMMARY_DETAILS = "process_summary_details";

	public static final String EVENT_PROCESS_FINANCIAL_DETAILS = "process_financial_details";

	public static final String EVENT_PROCESS_CRI_DETAILS = "process_cri_details";

	public static final String EVENT_PROCESS_CIBIL_DETAILS = "process_cibil_details";

	public static final String EVENT_VIEW_SUMMARY_DETAILS = "view_summary_details";

	public static final String EVENT_VIEW_FINANCIAL_DETAILS = "view_financial_details";

	public static final String EVENT_VIEW_CRI_DETAILS = "view_cri_details";

	public static final String EVENT_VIEW_CIBIL_DETAILS = "view_cibil_details";
	
	public static final String EVENT_VIEW_SUMMARY_DETAILS_BRANCH = "view_summary_details_branch";

	public static final String EVENT_VIEW_FINANCIAL_DETAILS_BRANCH = "view_financial_details_branch";

	public static final String EVENT_VIEW_CRI_DETAILS_BRANCH = "view_cri_details_branch";

	public static final String EVENT_VIEW_CIBIL_DETAILS_BRANCH = "view_cibil_details_branch";
	
	public static final String EVENT_VIEW_UDF_DETAILS_BRANCH= "view_udf_details_branch";
	
	public static final String EVENT_VIEW_SUMMARY_DETAILS_CHECKER = "view_summary_details_checker";

	public static final String EVENT_VIEW_FINANCIAL_DETAILS_CHECKER = "view_financial_details_checker";

	public static final String EVENT_VIEW_CRI_DETAILS_CHECKER = "view_cri_details_checker";

	public static final String EVENT_VIEW_CIBIL_DETAILS_CHECKER = "view_cibil_details_checker";
	
	public static final String EVENT_VIEW_SUMMARY_DETAILS_BRCHECKER = "view_summary_details_brchecker";

	public static final String EVENT_VIEW_FINANCIAL_DETAILS_BRCHECKER = "view_financial_details_brchecker";

	public static final String EVENT_VIEW_CRI_DETAILS_BRCHECKER = "view_cri_details_brchecker";

	public static final String EVENT_VIEW_CIBIL_DETAILS_BRCHECKER = "view_cibil_details_brchecker";

	public static final String EVENT_REFRESH_REGION_ID = "refresh_region_id";

	public static final String EVENT_REFRESH_STATE_ID = "refresh_state_id";

	public static final String EVENT_REFRESH_CITY_ID = "refresh_city_id";

	public static final String EVENT_REFRESH_RM_ID = "refresh_rm_id";

	public static final String EVENT_ADD_OTHER_SYSTEM = "add_other_system";

	public static final String EVENT_ADD_OTHER_SYSTEM_IN_EDIT = "add_other_system_in_edit";

	public static final String EVENT_ADD_OTHER_SYSTEM_IN_RESUBMIT = "add_other_system_in_resubmit";

	public static final String LIST_PAGINATION = "list_pagination";

	public static final String EVENT_SAVE_EDITED_SYSTEM_IN_RESUBMIT = "save_edited_system_in_resubmit";

	public static final String EVENT_SAVE_OTHER_SYSTEM = "save_other_system";

	public static final String EVENT_SAVE_OTHER_SYSTEM_IN_EDIT = "save_other_system_in_edit";

	public static final String EVENT_SAVE_OTHER_SYSTEM_IN_RESUBMIT = "save_other_system_in_resubmit";

	public static final String EVENT_DISPLAY_BANK_LIST = "display_bank_list";

	public static final String EVENT_DISPLAY_BANK_LIST_IN_EDIT = "display_bank_list_in_edit";
	
	public static final String EVENT_DISPLAY_BANK_LIST_IFSC_CODE = "display_bank_list_ifsc_code";
	
	public static final String EVENT_DISPLAY_BANK_LIST_IN_EDIT_IFSC_CODE = "display_bank_list_in_edit_ifsc_code";
	
	public static final String EVENT_DISPLAY_BANK_LIST_IN_RESUBMIT_IFSC_CODE = "display_bank_list_in_resubmit_ifsc_code";

	public static final String EVENT_DISPLAY_BANK_LIST_IN_RESUBMIT = "display_bank_list_in_resubmit";

	public static final String EVENT_SAVE_OTHER_BANK = "save_other_bank";

	public static final String EVENT_SAVE_OTHER_BANK_IN_EDIT = "save_other_bank_in_edit";

	public static final String EVENT_SAVE_OTHER_BANK_IN_RESUBMIT = "save_other_bank_in_resubmit";

	public static final String EVENT_ADD_SUB_LINE = "add_sub_line";

	public static final String EVENT_ADD_SUB_LINE_IN_EDIT = "add_sub_line_in_edit";

	public static final String EVENT_ADD_SUB_LINE_IN_RESUBMIT = "add_sub_line_in_resubmit";
	
	public static final String EVENT_ADD_VENDOR = "add_vendor_name";

	public static final String EVENT_ADD_VENDOR_IN_EDIT = "add_vendor_name_in_edit";

	public static final String EVENT_ADD_VENDOR_IN_RESUBMIT = "add_vendor_name_in_resubmit";
	
	public static final String EVENT_SAVE_VENDOR = "save_vendor_name";

	public static final String EVENT_SAVE_VENDOR_IN_EDIT = "save_vendor_name_in_edit";

	public static final String EVENT_SAVE_VENDOR_IN_RESUBMIT = "save_vendor_name_in_resubmit";
	
	public static final String EVENT_PREPARE_ADD_CO_BORROWER_DETAILS = "prepare_add_co_borrower_details";
	
	public static final String EVENT_VIEW_CO_BORROWER_DETAILS = "view_co_borrower_details";
	
	public static final String EVENT_PREPARE_EDIT_CO_BORROWER_DETAILS = "prepare_edit_co_borrower_details";
	
	public static final String EVENT_EDIT_CO_BORROWER_DETAILS = "edit_co_borrower_details";
	
	public static final String EVENT_ADD_CO_BORROWER_DETAILS = "add_co_borrower_details";
	
	public static final String EVENT_REMOVE_CO_BORROWER_DETAILS = "remove_co_borrower_details";
	
	public static final String EVENT_SAVE_CO_BORROWER_DETAILS = "save_co_borrower_details";
	
	public static final String EVENT_UPDATE_SESSION_ADD_CO_BORROWER_DETAILS = "update_session_add_co_borrower_details";
	
	public static final String EVENT_UPDATE_SESSION_VIEW_CO_BORROWER_DETAILS = "update_session_view_co_borrower_details";
	
	public static final String EVENT_UPDATE_SESSION_EDIT_CO_BORROWER_DETAILS = "update_session_edit_co_borrower_details";
	
	public static final String EVENT_UPDATE_SESSION_REMOVE_CO_BORROWER_DETAILS = "update_session_remove_co_borrower_details";
	
	public static final String EVENT_RETURN_CREATE_CO_BORROWER_DETAILS = "return_create_co_borrower_details";
	
	public static final String EVENT_RETURN_PROCESS_CO_BORROWER_DETAILS = "return_process_co_borrower_details";
	
	public static final String EVENT_RETURN_CO_BORROWER_DETAILS = "return_co_borrower_details";
	
	public static final String EVENT_DISPLAY_PARTY_LIST = "display_party_list";

	public static final String EVENT_DISPLAY_PARTY_LIST_IN_EDIT = "display_party_list_in_edit";

	public static final String EVENT_DISPLAY_PARTY_LIST_IN_RESUBMIT = "display_party_list_in_resubmit";

	public static final String EVENT_EDIT_DISPLAY_PARTY_LIST = "edit_display_party_list";

	public static final String EVENT_EDIT_DISPLAY_PARTY_LIST_IN_EDIT = "edit_display_party_list_in_edit";

	public static final String EVENT_EDIT_DISPLAY_PARTY_LIST_IN_RESUBMIT = "edit_display_party_list_in_resubmit";

	public static final String EVENT_ADD_PARTY_GROUP = "add_party_group";

	public static final String EVENT_ADD_PARTY_GROUP_IN_EDIT = "add_party_group_in_edit";

	public static final String EVENT_ADD_PARTY_GROUP_IN_RESUBMIT = "add_party_group_in_resubmit";

	public static final String EVENT_EDITED_PARTY_GROUP = "edited_party_group";

	public static final String EVENT_EDITED_PARTY_GROUP_IN_EDIT = "edited_party_group_in_edit";

	public static final String EVENT_EDITED_PARTY_GROUP_IN_RESUBMIT = "edited_party_group_in_resubmit";

	public static final String EVENT_SAVE_PARTY_GROUP = "save_party_group";

	public static final String EVENT_SAVE_PARTY_GROUP_IN_EDIT = "save_party_group_in_edit";

	public static final String EVENT_SAVE_PARTY_GROUP_IN_RESUBMIT = "save_party_group_in_resubmit";

	public static final String EVENT_SAVE_EDITED_PARTY_GROUP = "save_edited_party_group";

	public static final String EVENT_SAVE_EDITED_PARTY_GROUP_IN_EDIT = "save_edited_party_group_in_edit";

	public static final String EVENT_SAVE_EDITED_PARTY_GROUP_IN_RESUBMIT = "save_edited_party_group_in_resubmit";

	public static final String EVENT_EDIT_SYSTEM = "edit_system";

	public static final String EVENT_EDIT_SYSTEM_IN_EDIT = "edit_system_in_edit";

	public static final String EVENT_EDIT_DIRECTOR_IN_EDIT = "edit_director_in_edit";

	public static final String EVENT_EDIT_DIRECTOR_IN_RESUBMIT = "edit_director_in_resubmit";

	public static final String EVENT_EDIT_SYSTEM_IN_RESUBMIT = "edit_system_in_resubmit";

	public static final String EVENT_EDIT_SUBLINE_PARTY = "edit_subline_party";

	public static final String EVENT_EDIT_SUBLINE_PARTY_IN_EDIT = "edit_subline_party_in_edit";

	public static final String EVENT_EDIT_SUBLINE_PARTY_IN_RESUBMIT = "edit_subline_party_in_resubmit";
	
	public static final String EVENT_EDIT_VENDOR = "edit_vendor";

	public static final String EVENT_EDIT_VENDOR_IN_EDIT = "edit_vendor_in_edit";

	public static final String EVENT_EDIT_VENDOR_IN_RESUBMIT = "edit_vendor_in_resubmit";
	
	public static final String EVENT_SAVE_EDITED_VENDOR = "save_edited_vendor";

	public static final String EVENT_SAVE_EDITED_VENDOR_IN_EDIT = "save_edited_vendor_in_edit";

	public static final String EVENT_SAVE_EDITED_VENDOR_IN_RESUBMIT = "save_edited_vendor_in_resubmit";

	public static final String EVENT_DELETE_SYSTEM = "delete_system";

	public static final String EVENT_DELETE_SYSTEM_IN_EDIT = "delete_system_in_edit";

	public static final String EVENT_DELETE_SYSTEM_IN_RESUBMIT = "delete_system_in_resubmit";
	
	public static final String EVENT_DELETE_VENDOR = "delete_vendor";

	public static final String EVENT_DELETE_VENDOR_IN_EDIT = "delete_vendor_in_edit";

	public static final String EVENT_DELETE_VENDOR_IN_RESUBMIT = "delete_vendor_in_resubmit";
	
	public static final String EVENT_SAVE_DELETE_VENDOR = "vendor_deleted";

	public static final String EVENT_SAVE_DELETE_VENDOR_IN_EDIT = "vendor_deleted_in_edit";

	public static final String EVENT_SAVE_DELETE_VENDOR_IN_RESUBMIT = "vendor_deleted_in_resubmit";

	public static final String EVENT_DELETE_SUBLINE_PARTY = "delete_subline_party";

	public static final String EVENT_DELETE_SUBLINE_PARTY_IN_EDIT = "delete_subline_party_in_edit";

	public static final String EVENT_DELETE_SUBLINE_PARTY_IN_RESUBMIT = "delete_subline_party_in_resubmit";

	public static final String EVENT_SAVE_EDITED_SYSTEM = "save_edited_system";

	public static final String EVENT_SAVE_EDITED_SYSTEM_IN_EDIT = "save_edited_system_in_edit";

	public static final String EVENT_SYSTEM_DELETED = "system_deleted";

	public static final String EVENT_SYSTEM_DELETED_IN_EDIT = "system_deleted_in_edit";

	public static final String EVENT_SYSTEM_DELETED_IN_RESUBMIT = "system_deleted_in_resubmit";

	public static final String EVENT_CONFIRM_DELETE_PARTY_GROUP = "confirm_delete_party_group";

	public static final String EVENT_CONFIRM_DELETE_PARTY_GROUP_IN_EDIT = "confirm_delete_party_group_in_edit";

	public static final String EVENT_CONFIRM_DELETE_PARTY_GROUP_IN_RESUBMIT = "confirm_delete_party_group_in_resubmit";

	public static final String EVENT_COPY_OFFICE_TO_REG = "copy_office_to_reg";

	public static final String EVENT_COPY_OFFICE_TO_REG_EDIT = "copy_office_to_reg_edit";

	public static final String EVENT_COPY_OFFICE_TO_REG_RESUBMIT = "copy_office_to_reg_resubmit";

	public static final String EVENT_MAKER_RESUBMIT_CREATE_CUSTOMER = "maker_resubmit_create_customer";
	
	public static final String EVENT_MAKER_RESUBMIT_CREATE_CUSTOMER_BRMAKER = "maker_resubmit_create_customer_brmaker";

	public static final String EVENT_MAKER_RESUBMIT_EDIT_CUSTOMER = "maker_resubmit_edit_customer";

	public static final String EVENT_MAKER_RESUBMIT_DELETE_CUSTOMER = "maker_resubmit_delete_customer";

	public static final String EVENT_ADD_DIRECTOR = "add_director";

	public static final String EVENT_ADD_DIRECTOR_IN_EDIT = "add_director_in_edit";

	public static final String EVENT_ADD_DIRECTOR_IN_RESUBMIT = "add_director_in_resubmit";
	
	public static final String EVENT_SAVE_DIRECTOR = "save_director";

	public static final String EVENT_SAVE_DIRECTOR_IN_EDIT = "save_director_in_edit";

	public static final String EVENT_SAVE_DIRECTOR_IN_RESUBMIT = "save_director_in_resubmit";

	public static final String EVENT_EDIT_DIRECTOR = "edit_director";

	public static final String EVENT_DELETE_DIRECTOR = "delete_director";

	public static final String EVENT_DELETE_DIRECTOR_IN_EDIT = "delete_director_in_edit";

	public static final String EVENT_DELETE_DIRECTOR_IN_RESUBMIT = "delete_director_in_resubmit";

	public static final String EVENT_CONFIRM_DELETE_DIRECTOR = "confirm_delete_director";

	public static final String EVENT_CONFIRM_DELETE_DIRECTOR_IN_EDIT = "confirm_delete_director_in_edit";

	public static final String EVENT_CONFIRM_DELETE_DIRECTOR_IN_RESUBMIT = "confirm_delete_director_in_resubmit";

	public static final String EVENT_SAVE_EDITED_DIRECTOR = "save_edited_director";

	public static final String EVENT_SAVE_EDITED_DIRECTOR_ERROR = "save_edited_director_error";
	
	public static final String EVENT_SAVE_EDITED_DIRECTOR_IN_EDIT = "save_edited_director_in_edit";

	public static final String EVENT_SAVE_EDITED_DIRECTOR_IN_EDIT_ERROR = "save_edited_director_in_edit_error";
	
	public static final String EVENT_SAVE_EDITED_DIRECTOR_IN_RESUBMIT = "save_edited_director_in_resubmit";

	public static final String EVENT_SAVE_EDITED_DIRECTOR_IN_RESUBMIT_ERROR = "save_edited_director_in_resubmit_error";
	
	public static final String EVENT_REFRESH_RBI_CODE = "refresh_rbi_code";

	public static final String EVENT_CREATE_CUSTOMER_ERROR = "create_customer_error";
	
	public static final String EVENT_CREATE_CUSTOMER_BRMAKER_ERROR = "create_customer_brmaker_error";
	
	public static final String EVENT_CREATE_DRAFT_CUSTOMER_ERROR = "create_draft_customer_error";
	
	public static final String EVENT_CREATE_DRAFT_CUSTOMER_BRMAKER_ERROR = "create_draft_customer_brmaker_error";
	
	public static final String EVENT_UPDATE_DRAFT_CUSTOMER_ERROR = "update_draft_customer_error";
	
	public static final String EVENT_UPDATE_DRAFT_CUSTOMER_BRMAKER_ERROR = "update_draft_customer_brmaker_error";
	
	public static final String EVENT_SAVE_CUSTOMER_ERROR = "save_customer_error";
	
	public static final String EVENT_EDIT_CUSTOMER_ERROR = "edit_customer_error";
	
	public static final String EVENT_EDIT_CUSTOMER_BRMAKER_ERROR = "edit_customer_brmaker_error";
	
	public static final String EVENT_SAVE_CUSTOMER_IN_EDIT_ERROR = "save_customer_in_edit_error";

	public static final String EVENT_VALIDATE_CUSTOMER_FORM = "validate_customer_form";
	
	public static final String EVENT_VALIDATE_CUSTOMER_FORM_SAVE = "validate_customer_form_save";
	
	public static final String EVENT_VALIDATE_CUSTOMER_FORM_IN_EDIT = "validate_customer_form_in_edit";
	
	public static final String EVENT_VALIDATE_CUSTOMER_FORM_IN_EDIT_SAVE = "validate_customer_form_in_edit_save";
	
	public static final String EVENT_VALIDATE_CUSTOMER_FORM_IN_RESUBMIT = "validate_customer_form_in_resubmit";

	public static final String EVENT_MAKER_RESUBMIT_UPDATE_CUSTOMER_ERROR = "maker_resubmit_update_customer_error";
	
	public static final String EVENT_MAKER_RESUBMIT_CREATE_CUSTOMER_ERROR = "maker_resubmit_create_customer_error";
	
public static final String EVENT_MAKER_RESUBMIT_UPDATE_CUSTOMER_BRMAKER_ERROR = "maker_resubmit_update_customer_brmaker_error";
	
	public static final String EVENT_MAKER_RESUBMIT_CREATE_CUSTOMER_BRMAKER_ERROR = "maker_resubmit_create_customer_brmaker_error";
	
	public static final String EVENT_SAVE_CUSTOMER = "save_customer";
	
	public static final String EVENT_CREATE_DRAFT_CUSTOMER = "create_draft_customer";
	
	public static final String EVENT_UPDATE_DRAFT_CUSTOMER = "update_draft_customer";
	
	public static final String EVENT_SAVE_CUSTOMER_IN_EDIT = "save_customer_in_edit";
	
	public static final String EVENT_MAKER_CLOSE_DRAFT_CUSTOMER = "maker_close_draft_customer";
	
	public static final String EVENT_ADD_CRI_FAC_LIST = "add_cri_fac_list";
	
	public static final String EVENT_EDIT_CRI_FAC_LIST = "edit_cri_fac_list";
	
	public static final String EVENT_PROCESS_CRI_FAC_LIST = "process_cri_fac_list";
	
	public static final String EVENT_EDIT_CRI_LIMIT_LIST = "edit_cri_limit_list";
	
	public static final String EVENT_SAVE_CRI_FAC_LIST = "save_cri_fac_list";
	
	public static final String EVENT_REMOVE_CRI_FAC = "remove_cri_fac";
	
	public static final String EVENT_EDIT_REMOVE_CRI_FAC = "edit_remove_cri_fac";
	
	public static final String EVENT_PROCESS_REMOVE_CRI_FAC = "process_remove_cri_fac";
	
	public static final String EVENT_EDIT_REMOVE_CRI_LIMIT = "edit_remove_cri_limit";
	
	public static final String EVENT_REFRESH_RBI_IND_CODE = "refresh_rbi_industry_code";
	
	public static final String EVENT_CHECKER_LIST_PARTY_DETAIL = "checker_list_party_detail";
	
	public static final String EVENT_BRCHECKER_LIST_PARTY_DETAIL = "brchecker_list_party_detail";
	
	public static final String EVENT_SAVE_DIRECTOR_ERROR = "save_director_error";
	
	public static final String EVENT_SAVE_DIRECTOR_IN_EDIT_ERROR = "save_director_in_edit_error";
	
	public static final String EVENT_SAVE_DIRECTOR_IN_RESUBMIT_ERROR = "save_director_in_resubmit_error";
	
	public static final String EVENT_VIEW_OTHERBANK_BY_INDEX = "view_other_bank_by_index";
	
	public static final String EVENT_REFRESH_FACILITY_LIST = "refresh_facility_list";
	
	public static final String EVENT_REFRESH_FACILITY_DETAIL_LIST = "refresh_facility_detail_list";
	
	public static final String EVENT_REFRESH_TRANCH_DETAIL_LIST = "refresh_tranch_detail_list";
	
	public static final String EVENT_EDIT_CRI_FACILITY_ERROR = "edit_cri_facility_error";
	// ------------ UDF -------------------
	public static final String EVENT_DISPLAY_UDF_DETAILS = "display_udf_details";
	public static final String EVENT_PROCESS_UDF_DETAILS = "process_udf_details";
	public static final String EVENT_CLOSE_UDF_DETAILS = "close_udf_details";
	public static final String EVENT_CREATE_CHECKER_UDF_DETAILS = "create_checker_udf_details";
	public static final String EVENT_TO_TRACK_UDF_DETAILS = "to_track_udf_details";
	public static final String EVENT_VIEW_UDF_DETAILS = "view_udf_details";
	public static final String EVENT_EDIT_UDF_DETAILS = "edit_udf_details";
	public static final String EVENT_CHECKER_UPDATE_UDF_DETAILS = "checker_update_udf_details";
	public static final String EVENT_UDF_DETAILS_CHECKER = "view_udf_details_checker";
	
	public static final String EVENT_VIEW_OTHERBANK_BY_INDEX_IN_RESUBMIT = "view_other_bank_by_index_in_resubmit";
	
	public static final String EVENT_VIEW_OTHERBANK_BY_INDEX_IN_EDIT = "view_other_bank_by_index_in_edit";
		
	public static final String EVENT_PREPARE_CHECKER = "prepare_checker";
	
	public static final String EVENT_PREPARE_BRCHECKER = "prepare_brchecker";
		
	public static final String EVENT_TO_TRACK_VIEW_DIRECTOR = "to_track_view_director";
		
	public static final String EVENT_CHECKER_UPDATE_VIEW_DIRECTOR = "checker_update_view_director";
		
	public static final String EVENT_CHECKER_CREATE_VIEW_DIRECTOR = "checker_create_view_director";
		
	public static final String EVENT_CLOSE_MAKER_VIEW_DIRECTOR = "close_maker_view_director";
		
	public static final String EVENT_CHECKER_VIEW_DIRECTOR = "checker_view_director";
	
	public static final String EVENT_CHECKER_VIEW_DIRECTOR_BRMAKER = "checker_view_director_brmaker";
		
	public static final String EVENT_MAKER_VIEW_DIRECTOR = "maker_view_director";
					
	public static final String EVENT_CALCULATE_FINANCIAL_DETAIL = "calculate_financial_detail";
					
	public static final String EVENT_CALCULATE_FINANCIAL_DETAIL_IN_EDIT = "calculate_financial_detail_in_edit";
					
	public static final String EVENT_CALCULATE_FINANCIAL_DETAIL_IN_RESUBMIT = "calculate_financial_detail_in_resubmit";
	
	public static final String EVENT_PREPARE_BRANCH_MAKER = "prepare_branch_maker";
					
	public static final String EVENT_LIST_CUSTOMERS_BRMAKER = "list_customers_brmaker";
					
	public static final String EVENT_CREATE_CHECKER_PROCESS_BRCHECKER = "create_checker_process_brchecker";
					
	public static final String EVENT_CHECKER_REJECT_CREATE_CUSTOMER_BRCHECKER = "checker_reject_create_customer_brchecker";
					
	public static final String EVENT_MAKER_RESUBMIT_UPDATE_BRMAKER = "maker_resubmit_update_brmaker";
					
	public static final String EVENT_PREPARE_CLOSE_PAGE_BRMAKER = "prepare_close_page_brmaker";
					
	public static final String EVENT_CLOSE_PAGE_BRMAKER = "close_page_brmaker";
					
	public static final String EVENT_EDIT_CUSTOMER_DETAILS_BRMAKER = "edit_customer_details_brmaker";
					
	public static final String EVENT_EDIT_CUSTOMER_BRMAKER = "edit_customer_brmaker";
					
	public static final String EVENT_CHECKER_APPROVE_UPDATE_CUSTOMER_BRMAKER = "checker_approve_update_customer_brmaker";
				
	public static final String EVENT_CHECKER_REJECT_UPDATE_CUSTOMER_BRMAKER = "checker_reject_update_customer_brmaker";
					
	public static final String EVENT_MAKER_RESUBMIT_UPDATE_CUSTOMER_BRMAKER = "maker_resubmit_update_customer_brmaker";
						
	public static final String EVENT_SAVE_CUSTOMER_BRMAKER = "save_customer_brmaker";
						
	public static final String EVENT_MAKER_SAVE_PROCESS_BRMAKER = "maker_save_process_brmaker";
	
	public static final String EVENT_CREATE_DRAFT_CUSTOMER_BRMAKER = "create_draft_customer_brmaker";
	
	public static final String EVENT_UPDATE_DRAFT_CUSTOMER_BRMAKER = "update_draft_customer_brmaker";
	
	public static final String EVENT_SAVE_CUSTOMER_IN_EDIT_BRMAKER = "save_customer_in_edit_brmaker";
	
	public static final String EVENT_MAKER_SAVE_CLOSE_BRMAKER = "maker_save_close_brmaker";
	
	public static final String EVENT_MAKER_CLOSE_DRAFT_CUSTOMER_BRMAKER = "maker_close_draft_customer_brmaker";
	
	public static final String EVENT_DELETE_BANKING_METHOD = "delete_banking_method";
	
	public static final String EVENT_DELETE_BANKING_METHOD_IN_EDIT = "delete_banking_method_in_edit";
	
	public static final String EVENT_DELETE_BANKING_METHOD_IN_RESUBMIT = "delete_banking_method_in_resubmit";
	
	public static final String EVENT_VALIDATE_PAN_NO_WITH_NSDL = "validate_pan_no_with_NSDL";
	
	public static final String EVENT_VALIDATE_LEI_CODE_WITH_CCIL = "validate_lei_code_with_CCIL";

	public static final String EVENT_VALIDATE_LEI_DETAILS_CHANGE = "validate_lei_details_change";

	public static final String EVENT_POPULATE_RM_DATA ="populate_rm_data";
	
	// Event Added by Sandeep Shinde on 25-Feb-2011 End
	
	//Uma Khot:Added for Valid Rating CR
	public static final String EVENT_PREPARE_PARTY_MAKER = "prepare_party_maker";
	public static final String EVENT_LIST_CUSTOMERS_PARTY_MAKER ="list_customers_partymaker";
	public static final String EVENT_VIEW_CUSTOMERS_PARTY_MAKER ="view_customer_partymaker";
	public static final String EVENT_PREPARE_EDIT_CUSTOMER_DETAILS_PARTYMAKER ="edit_customer_details_partymaker";
	public static final String EVENT_SAVE_CUSTOMER_IN_EDIT_PARTYMAKER ="save_customer_in_edit_partymaker";
	public static final String EVENT_ADD_OTHER_SYSTEM_PARTYMAKER ="add_other_system_partymaker";
	public static final String EVENT_ADD_OTHER_SYSTEM_IN_EDIT_PARTYMAKER ="add_other_system_in_edit_partymaker";
	public static final String EVENT_ADD_OTHER_SYSTEM_IN_RESUBMIT_PARTYMAKER ="add_other_system_in_resubmit_partymaker";
	public static final String EVENT_DISPLAY_SUMMARY_DETAILS_PARTYMAKER = "display_summary_details_partymaker";
	public static final String EVENT_PROCESS_SUMMARY_DETAILS_PARTYMAKER ="process_summary_details_partymaker";
	public static final String EVENT_SAVE_OTHER_SYSTEM_PARTYMAKER = "save_other_system_partymaker";
	public static final String EVENT_SAVE_OTHER_SYSTEM_IN_EDIT_PARTYMAKER = "save_other_system_in_edit_partymaker";
	public static final String EVENT_SAVE_OTHER_SYSTEM_IN_RESUBMIT_PARTYMAKER = "save_other_system_in_resubmit_partymaker";
	public static final String EVENT_EDIT_SUMMARY_DETAILS_PARTYMAKER="edit_summary_details_partymaker";
	public static final String EVENT_EDIT_SYSTEM_PARTYMAKER="edit_system_partymaker";
	public static final String EVENT_EDIT_SYSTEM_IN_EDIT_PARTYMAKER="edit_system_in_edit_partymaker";
	public static final String EVENT_EDIT_SYSTEM_IN_RESUBMIT_PARTYMAKER="edit_system_in_resubmit_partymaker";
	public static final String EVENT_SAVE_EDITED_SYSTEM_PARTYMAKER="save_edited_system_partymaker";
	public static final String EVENT_SAVE_EDITED_SYSTEM_IN_EDIT_PARTYMAKER="save_edited_system_in_edit_partymaker";
	public static final String EVENT_SAVE_EDITED_SYSTEM_IN_RESUBMIT_PARTYMAKER="save_edited_system_in_resubmit_partymaker";
	public static final String EVENT_DELETE_SYSTEM_IN_EDIT_PARTYMAKER="delete_system_in_edit_partymaker";
	public static final String EVENT_DELETE_SYSTEM_PARTYMAKER = "delete_system_partymaker";
	public static final String EVENT_DELETE_SYSTEM_IN_RESUBMIT_PARTYMAKER = "delete_system_in_resubmit_partymaker";
	private static final Object EVENT_SYSTEM_DELETED_PARTYMAKER = "system_deleted_partymaker";
	private static final Object EVENT_SYSTEM_DELETED_IN_EDIT_PARTYMAKER = "system_deleted_in_edit_partymaker";
	private static final Object EVENT_SYSTEM_DELETED_IN_RESUBMIT_PARTYMAKER = "system_deleted_in_resubmit_partymaker";
	private static final Object EVENT_EDIT_CUSTOMER_PARTYMAKER = "edit_customer_partymaker";
	public static final String EVENT_EDIT_CUSTOMER_PARTYMAKER_ERROR = "edit_customer_partymaker_error";
	public static final String EVENT_CREATE_DRAFT_CUSTOMER_PARTYMAKER_ERROR = "create_draft_customer_partymaker_error";
	public static final String EVENT_UPDATE_DRAFT_CUSTOMER_PARTYMAKER_ERROR = "update_draft_customer_partymaker_error";
	public static final String EVENT_UPDATE_DRAFT_CUSTOMER_PARTYMAKER = "update_draft_customer_partymaker";
	public static final String EVENT_VIEW_SUMMARY_DETAILS_PARTYMAKER ="view_summary_details_partymaker";
	public static final String EVENT_VIEW_FINANCIAL_DETAILS_PARTYMAKER ="view_financial_details_partymaker";
	public static final String EVENT_VIEW_CRI_DETAILS_PARTYMAKER ="view_cri_details_partymaker";
	public static final String EVENT_VIEW_CIBIL_DETAILS_PARTYMAKER ="view_cibil_details_partymaker";
	public static final String EVENT_VIEW_UDF_DETAILS_PARTYMAKER ="view_udf_details_partymaker";
	public static final String EVENT_MAKER_SAVE_CLOSE_PARTYMAKER = "maker_save_close_partymaker";
	public static final String EVENT_MAKER_SAVE_PROCESS_PARTYMAKER = "maker_save_process_partymaker";
	public static final String EVENT_PREPARE_CLOSE_PAGE_PARTYMAKER = "prepare_close_page_partymaker";
	public static final String EVENT_MAKER_CLOSE_DRAFT_CUSTOMER_PARTYMAKER = "maker_close_draft_customer_partymaker";
	public static final String EVENT_CLOSE_PAGE_PARTYMAKER = "close_page_partymaker";
	public static final String EVENT_CREATE_DRAFT_CUSTOMER_PARTYMAKER = "create_draft_customer_partymaker";
	public static final String EVENT_MAKER_RESUBMIT_UPDATE_CUSTOMER_PARTYMAKER_ERROR = "maker_resubmit_update_customer_partymaker_error";
	public static final String EVENT_MAKER_RESUBMIT_CREATE_CUSTOMER_PARTYMAKER_ERROR = "maker_resubmit_create_customer_partymaker_error";
	public static final String EVENT_PROCESS_PAGE_PARTYMAKER = "process_page_partymaker";
	public static final String EVENT_MAKER_RESUBMIT_CREATE_CUSTOMER_PARTYMAKER = "maker_resubmit_create_customer_partymaker";
	public static final String EVENT_MAKER_RESUBMIT_UPDATE_CUSTOMER_PARTYMAKER = "maker_resubmit_update_customer_partymaker";
	
	public static final String EVENT_CHECKER_APPROVE_UPDATE_CUSTOMER_PARTYMAKER = "checker_approve_update_customer_partymaker";
	public static final String EVENT_CHECKER_REJECT_UPDATE_CUSTOMER_PARTYMAKER = "checker_reject_update_customer_partymaker";
	public static final String EVENT_MAKER_UPDATE_CUSTOMER_PARTYMAKER = "maker_update_customer_partymaker";
	
	public static final String EVENT_PREPARE_CREATE_CUSTOMER_PARTYMAKER = "prepare_create_customer_partymaker";
	public static final String EVENT_CREATE_CUSTOMER_PARTYMAKER = "create_customer_partymaker";
	public static final String EVENT_CREATE_CHECKER_PROCESS_PARTYMAKER = "create_checker_process_partymaker";
	public static final String EVENT_CHECKER_REJECT_CREATE_CUSTOMER_PARTYMAKER = "checker_reject_create_customer_partymaker";
	public static final String EVENT_CHECKER_APPROVE_CREATE_CUSTOMER_PARTYMAKER = "checker_approve_create_customer_partymaker";
	
	public static final String EVENT_SAVE_CUSTOMER_PARTYMAKER = "save_customer_partymaker";
	public static final String EVENT_SAVE_CUSTOMER_PARTYMAKER_ERROR = "save_customer_partymaker_error";
	public static final String EVENT_SAVE_CUSTOMER_IN_EDIT_PARTYMAKER_ERROR = "save_customer_in_edit_partymaker_error";
	
	public static final String EVENT_REFRESH_SECURITY_TYPE = "refresh_security_type";
	
	public static final String EVENT_DISPLAY_BANKING_METHOD_DETAILS = "display_banking_method_details";
	
	public static final String EVENT_CREATE_CHECKER_BANKING_METHOD_DETAILS = "create_checker_banking_method_details";
	
	public static final String EVENT_PROCESS_BANKING_METHOD_DETAILS = "process_banking_method_details";
	
	public static final String EVENT_CLOSE_BANKING_METHOD_DETAILS = "close_banking_method_details";
	
	public static final String EVENT_EDIT_BANKING_METHOD_DETAILS = "edit_banking_method_details";
	
	public static final String EVENT_CHECKER_UPDATE_BANKING_METHOD_DETAILS = "checker_update_banking_method_details";
	
	public static final String EVENT_VIEW_BANKING_METHOD_DETAILS = "view_banking_method_details";
	
	public static final String EVENT_VIEW_BANKING_METHOD_DETAILS_CHECKER = "view_banking_method_details_checker";
	
	public static final String EVENT_VIEW_BANKING_METHOD_DETAILS_PARTYMAKER ="view_banking_method_details_partymaker";
	
	public static final String EVENT_VIEW_BANKING_METHOD_DETAILS_BRANCH = "view_banking_method_details_branch";
	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event
	 *            is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this, "ManualInputCustomerAction ---- Event is : "
				+ event);
		ICommand objArray[] = null;
		if (EVENT_PREPARE.equals(event) || EVENT_PREPARE_CHECKER.equals(event) || EVENT_PREPARE_BRANCH_MAKER.equals(event) || EVENT_PREPARE_BRCHECKER.equals(event) || EVENT_PREPARE_PARTY_MAKER.equals(event) ) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareManualInputCustomerCommand();
		} else if ("first_search".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
			"ManualInputCustomerSearchCommand");
			//objArray[0] = new ManualInputCustomerSearchCommand();
		} else if ("view_customer".equals(event) || "view_customer_checker".equals(event) || "view_customer_brchecker".equals(event) || "view_customer_branch".equals(event) || EVENT_VIEW_CUSTOMERS_PARTY_MAKER.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
			"ManualInputCustomerSearchCommand");
			//objArray[0] = new ManualInputCustomerSearchCommand();
		} else if ("subsequent_search".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
			"ManualInputCustomerSearchCommand");
			//objArray[0] = new ManualInputCustomerSearchCommand();
		} else if (EVENT_PREPARE_EDIT_CUSTOMER_DETAILS.equals(event) || EVENT_PREPARE_EDIT_CUSTOMER_DETAILS_BRMAKER.equals(event) || EVENT_PREPARE_EDIT_CUSTOMER_DETAILS_PARTYMAKER.equals(event)) {
			objArray = new ICommand[1];
			// objArray[0] = new ManualInputPrepareEditCustomerCommand();
			objArray[0] = (ICommand) getNameCommandMap().get(
					"ManualInputPrepareEditCustomerCommand");
		} else if (event.equals(EVENT_EDIT_CUSTOMER)
				|| event.equals(EVENT_MAKER_RESUBMIT_UPDATE)
				|| event.equals(EVENT_MAKER_RESUBMIT_UPDATE_BRMAKER)
				|| event.equals(EVENT_MAKER_RESUBMIT_UPDATE_CUSTOMER)
					|| event.equals(EVENT_MAKER_RESUBMIT_UPDATE_CUSTOMER_BRMAKER)
				|| event.equals(EVENT_EDIT_CUSTOMER_BRMAKER)
				|| event.equals(EVENT_EDIT_CUSTOMER_PARTYMAKER)
				|| event.equals(EVENT_MAKER_RESUBMIT_UPDATE_CUSTOMER_PARTYMAKER)
		) {
			objArray = new ICommand[1];
			objArray[0] = new ManualInputEditCustomerCommand();
		}else if (event.equals(EVENT_UPDATE_DRAFT_CUSTOMER) || event.equals(EVENT_UPDATE_DRAFT_CUSTOMER_BRMAKER) || event.equals(EVENT_UPDATE_DRAFT_CUSTOMER_PARTYMAKER)) {
			objArray = new ICommand[1];
			objArray[0] = new ManualInputEditDraftCustomerCommand();
		} else if (EVENT_LIST_CUSTOMERS.equals(event)|| EVENT_BRCHECKER_LIST_PARTY_DETAIL.equals(event)   || EVENT_CHECKER_LIST_PARTY_DETAIL.equals(event) ||EVENT_LIST_CUSTOMER_CHECKER.equals(event) ||EVENT_LIST_CUSTOMER_BRCHECKER.equals(event) || EVENT_LIST_CUSTOMERS_BRMAKER.equals(event) || EVENT_LIST_CUSTOMERS_PARTY_MAKER.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ManualInputListCustomerCommand();
		} else if (EVENT_PREPARE_CREATE_CUSTOMER.equals(event) || EVENT_PREPARE_CREATE_CUSTOMER_BRMAKER.equals(event) || EVENT_PREPARE_CREATE_CUSTOMER_PARTYMAKER.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"ManualInputPrepareCreateCustomerCommand");
			objArray[1] = (ICommand) getNameCommandMap().get(
			"DisplaySummaryDetailsCommand");
		} else if (EVENT_CREATE_CUSTOMER.equals(event) || EVENT_CREATE_CUSTOMER_BRMAKER.equals(event)  || EVENT_CREATE_CUSTOMER_PARTYMAKER.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ManualInputCreateCustomerCommand();
		}  else if ( EVENT_CREATE_DRAFT_CUSTOMER.equals(event) ||  EVENT_CREATE_DRAFT_CUSTOMER_BRMAKER.equals(event) || EVENT_CREATE_DRAFT_CUSTOMER_PARTYMAKER.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ManualInputCreateDraftCustomerCommand();
		}else if (EVENT_MAKER_RESUBMIT_CREATE_CUSTOMER.equals(event) || EVENT_MAKER_RESUBMIT_CREATE_CUSTOMER_BRMAKER.equals(event) || EVENT_MAKER_RESUBMIT_CREATE_CUSTOMER_PARTYMAKER.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ManualInputResubmitCreateCustomerCommand();
		} else if (EVENT_PREPARE_DELETE_CUSTOMER_DETAILS.equals(event)) {
			objArray = new ICommand[1];
			// objArray[0] = new ManualInputPrepareEditCustomerCommand();
			objArray[0] = (ICommand) getNameCommandMap().get(
					"ManualInputPrepareEditCustomerCommand");
		} else if (EVENT_MAKER_RESUBMIT_DELETE_CUSTOMER.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ManualInputResubmitDeleteCustomerCommand();
		} else if (EVENT_MAKER_DELETE_CUSTOMER.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ManualInputDeleteCustomerCommand();
		} else if (EVENT_DELETE_CUSTOMER_DETAILS.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ManualInputDeleteCustomerCommand();
		} else if (EVENT_TOTRACK_MAKER.equals(event)
				|| EVENT_REJECTED_DELETE_READ.equals(event)
				|| EVENT_TOTRACK_CHECKER.equals(event)
				) {
			objArray = new ICommand[1];
			objArray[0] = new ManualInputCheckerReadCustomerCommand();
		} else if (EVENT_CREATE_CHECKER_PROCESS.equals(event) || EVENT_CREATE_CHECKER_PROCESS_BRCHECKER.equals(event) || EVENT_CREATE_CHECKER_PROCESS_PARTYMAKER.equals(event)) {
			objArray = new ICommand[1];
			//objArray[0] = new ManualInputCheckerReadCustomerCommand();
			objArray[0] = (ICommand) getNameCommandMap().get(
			"ManualInputCheckerReadCustomerCommand");
		} else if (EVENT_PROCESS_PAGE.equals(event)
				|| EVENT_MAKER_SAVE_PROCESS.equals(event)
					|| EVENT_MAKER_SAVE_PROCESS_BRMAKER.equals(event)
					|| EVENT_PROCESS_PAGE_BRMAKER.equals(event)
					|| EVENT_MAKER_SAVE_PROCESS_PARTYMAKER.equals(event)
					||  EVENT_PROCESS_PAGE_PARTYMAKER.equals(event)
		) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"ManualInputPrepareProcessCustomerCommand");
		} else if (EVENT_MAKER_UPDATE_CUSTOMER.equals(event)
				|| EVENT_MAKER_UPDATE_CUSTOMER_BRMAKER.equals(event)
				|| EVENT_PREPARE_CLOSE_PAGE.equals(event)
				|| EVENT_MAKER_SAVE_CLOSE.equals(event)
				|| EVENT_MAKER_SAVE_CLOSE_BRMAKER.equals(event)
					|| EVENT_PREPARE_CLOSE_PAGE_BRMAKER.equals(event)
					|| EVENT_MAKER_SAVE_CLOSE_PARTYMAKER.equals(event)
					|| EVENT_PREPARE_CLOSE_PAGE_PARTYMAKER.equals(event)
					|| EVENT_MAKER_UPDATE_CUSTOMER_PARTYMAKER.equals(event)
		) {
			objArray = new ICommand[1];
			objArray[0] = new ManualInputCheckerReadCustomerCommand();
		} else if (EVENT_MAKER_EDIT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ManualInputCheckerReadCustomerCommand();
		} else if (EVENT_CHECKER_APPROVE_CREATE_CUSTOMER.equals(event)  || EVENT_CHECKER_APPROVE_CREATE_CUSTOMER_BRCHECKER.equals(event) || EVENT_CHECKER_APPROVE_CREATE_CUSTOMER_PARTYMAKER.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerApproveCreateCustomerCommand();
		} else if (EVENT_CHECKER_REJECT_CREATE_CUSTOMER.equals(event) || EVENT_CHECKER_REJECT_CREATE_CUSTOMER_BRCHECKER.equals(event)  || EVENT_CHECKER_REJECT_CREATE_CUSTOMER_PARTYMAKER.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerRejectCreateCustomerCommand();
		} else if (EVENT_CHECKER_APPROVE_UPDATE_CUSTOMER.equals(event) || EVENT_CHECKER_APPROVE_UPDATE_CUSTOMER_BRMAKER.equals(event) || EVENT_CHECKER_APPROVE_UPDATE_CUSTOMER_PARTYMAKER.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerApproveUpdateCustomerCommand();
		} else if (EVENT_CHECKER_REJECT_UPDATE_CUSTOMER.equals(event) || EVENT_CHECKER_REJECT_UPDATE_CUSTOMER_BRMAKER.equals(event) || EVENT_CHECKER_REJECT_UPDATE_CUSTOMER_PARTYMAKER.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerRejectUpdateCustomerCommand();
		} else if (EVENT_MAKER_PREPARE_RESUBMIT_UPDATE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ManualInputCheckerReadCustomerCommand();
		} else if (EVENT_TODO_CHECKER_DELETE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ManualInputCheckerReadCustomerCommand();
		} else if (EVENT_CHECKER_APPROVE_DELETE_CUSTOMER.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerApproveDeleteCustomerCommand();
		} else if (EVENT_CHECKER_REJECT_DELETE_CUSTOMER.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CheckerRejectDeleteCustomerCommand();
		} else if (EVENT_CLOSE_PAGE.equals(event) ||  EVENT_CLOSE_PAGE_BRMAKER.equals(event) ||  EVENT_CLOSE_PAGE_PARTYMAKER.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ManualInputMakerCloseCustomerCommand();
		}else if (EVENT_MAKER_CLOSE_DRAFT_CUSTOMER.equals(event) || EVENT_MAKER_CLOSE_DRAFT_CUSTOMER_BRMAKER.equals(event) || EVENT_MAKER_CLOSE_DRAFT_CUSTOMER_PARTYMAKER.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ManualInputMakerCloseDraftCustomerCommand();
		} else if (event.equals(EVENT_REFRESH_REGION_ID)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"RefreshCountryCommmand");
		}  else if (event.equals(EVENT_REFRESH_RBI_IND_CODE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"RefreshRbiIndustryCodeCommand");
		} else if (event.equals(EVENT_REFRESH_STATE_ID)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"RefreshCityCommmand");
		} else if (event.equals(EVENT_REFRESH_CITY_ID)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"RefreshCityTownCommand");
		} else if (event.equals(EVENT_REFRESH_RM_ID)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"RefreshRelationshipMgrCommand");
		} else if (event.equals(EVENT_ADD_OTHER_SYSTEM)
				|| event.equals(EVENT_ADD_OTHER_SYSTEM_IN_EDIT)
				|| event.equals(EVENT_ADD_OTHER_SYSTEM_IN_RESUBMIT)
				||event.equals(EVENT_ADD_OTHER_SYSTEM_PARTYMAKER)
				|| event.equals(EVENT_ADD_OTHER_SYSTEM_IN_EDIT_PARTYMAKER)
				|| event.equals(EVENT_ADD_OTHER_SYSTEM_IN_RESUBMIT_PARTYMAKER)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"AddPrepareOtherSystemCommand");
		} else if (event.equals(EVENT_ADD_DIRECTOR)
				|| event.equals(EVENT_ADD_DIRECTOR_IN_EDIT)
				|| event.equals(EVENT_ADD_DIRECTOR_IN_RESUBMIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"AddPrepareDirectorCommand");
		} else if (event.equals(EVENT_SAVE_DIRECTOR)
				|| event.equals(EVENT_SAVE_DIRECTOR_IN_EDIT)
				|| event.equals(EVENT_SAVE_DIRECTOR_IN_RESUBMIT)
				) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"SaveDirectorCommand");
		}else if (event.equals(EVENT_ADD_VENDOR)
				|| event.equals(EVENT_ADD_VENDOR_IN_EDIT)
				|| event.equals(EVENT_ADD_VENDOR_IN_RESUBMIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"AddPrepareVendorCommand");	
		}else if (event.equals("save_vendor_name")
				|| event.equals("save_vendor_name_in_edit")
				|| event.equals("save_vendor_name_in_resubmit")) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"AddNewVendorCommand");	
		} else if (event.equals(EVENT_SAVE_OTHER_SYSTEM)
				|| event.equals(EVENT_SAVE_OTHER_SYSTEM_IN_EDIT)
				|| event.equals(EVENT_SAVE_OTHER_SYSTEM_IN_RESUBMIT)
				|| event.equals(EVENT_SAVE_OTHER_SYSTEM_PARTYMAKER)
				|| event.equals(EVENT_SAVE_OTHER_SYSTEM_IN_EDIT_PARTYMAKER)
				|| event.equals(EVENT_SAVE_OTHER_SYSTEM_IN_RESUBMIT_PARTYMAKER)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"AddOtherSystemCommand");
		} else if (event.equals(EVENT_DISPLAY_BANK_LIST)
				|| event.equals(EVENT_DISPLAY_BANK_LIST_IN_EDIT)
				|| event.equals(EVENT_DISPLAY_BANK_LIST_IN_RESUBMIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"DisplayBankByNameCommand");
		} else if(event.equals(EVENT_DISPLAY_BANK_LIST_IFSC_CODE)
				||event.equals(EVENT_DISPLAY_BANK_LIST_IN_EDIT_IFSC_CODE)
				||event.equals(EVENT_DISPLAY_BANK_LIST_IN_RESUBMIT_IFSC_CODE)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"DisplayBankByIFSCCodeCommand");
		} else if (event.equals(EVENT_SAVE_OTHER_BANK)
				|| event.equals(EVENT_SAVE_OTHER_BANK_IN_EDIT)
				|| event.equals(EVENT_SAVE_OTHER_BANK_IN_RESUBMIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"AddOtherBankCommand");
		} else if (event.equals(EVENT_ADD_SUB_LINE)
				|| event.equals(EVENT_ADD_SUB_LINE_IN_EDIT)
				|| event.equals(EVENT_ADD_SUB_LINE_IN_RESUBMIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"AddPrepareSubLineCommand");
		} else if (event.equals(EVENT_DISPLAY_PARTY_LIST)
				|| event.equals(EVENT_EDIT_DISPLAY_PARTY_LIST)
				|| event.equals(EVENT_EDIT_DISPLAY_PARTY_LIST_IN_EDIT)
				|| event.equals(EVENT_DISPLAY_PARTY_LIST_IN_EDIT)
				|| event.equals(EVENT_DISPLAY_PARTY_LIST_IN_RESUBMIT)
				|| event.equals(EVENT_EDIT_DISPLAY_PARTY_LIST_IN_RESUBMIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"DisplayPartyByFacilityCommand");
		} else if (event.equals(EVENT_ADD_PARTY_GROUP)
				|| event.equals(EVENT_ADD_PARTY_GROUP_IN_EDIT)
				|| event.equals(EVENT_ADD_PARTY_GROUP_IN_RESUBMIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"AddPartyGroupCommand");
		} else if (event.equals(EVENT_EDITED_PARTY_GROUP)
				|| event.equals(EVENT_EDITED_PARTY_GROUP_IN_EDIT)
				|| event.equals(EVENT_EDITED_PARTY_GROUP_IN_RESUBMIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"EditedPartyGroupCommand");
		} else if (event.equals(EVENT_SAVE_PARTY_GROUP)
				|| event.equals(EVENT_SAVE_PARTY_GROUP_IN_EDIT)
				|| event.equals(EVENT_SAVE_PARTY_GROUP_IN_RESUBMIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"SavePartyGroupCommand");
		} else if (event.equals(EVENT_SAVE_EDITED_PARTY_GROUP)
				|| event.equals(EVENT_SAVE_EDITED_PARTY_GROUP_IN_EDIT)
				|| event.equals(EVENT_SAVE_EDITED_PARTY_GROUP_IN_RESUBMIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"SaveEditedPartyGroupCommand");
		} else if (event.equals(EVENT_DISPLAY_SUMMARY_DETAILS) 
				|| (event.equals(EVENT_PROCESS_SUMMARY_DETAILS))
				|| event.equals(EVENT_DISPLAY_SUMMARY_DETAILS_PARTYMAKER) 
				|| event.equals(EVENT_PROCESS_SUMMARY_DETAILS_PARTYMAKER)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"DisplaySummaryDetailsCommand");
		} else if (event.equals(EVENT_DISPLAY_CIBIL_DETAILS)
				|| event.equals(EVENT_EDIT_CIBIL_DETAILS)
				|| event.equals(EVENT_PROCESS_CIBIL_DETAILS)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"DisplayCibilDetailsCommand");
		} else if (event.equals(EVENT_EDIT_SYSTEM)
				|| event.equals(EVENT_EDIT_SYSTEM_IN_EDIT)
				|| event.equals(EVENT_EDIT_SYSTEM_IN_RESUBMIT)
				|| event.equals(EVENT_EDIT_SYSTEM_PARTYMAKER)
				|| event.equals(EVENT_EDIT_SYSTEM_IN_EDIT_PARTYMAKER)
				|| event.equals(EVENT_EDIT_SYSTEM_IN_RESUBMIT_PARTYMAKER)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"PrepareEditSystemCommand");
		} else if (event.equals(EVENT_EDIT_VENDOR)
				|| event.equals(EVENT_EDIT_VENDOR_IN_EDIT)
				|| event.equals(EVENT_EDIT_VENDOR_IN_RESUBMIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"PrepareEditVendorCommand");
		} else if (event.equals("save_edited_vendor_in_edit")
				|| event.equals("save_edited_vendor")
				|| event.equals("save_edited_vendor_in_resubmit")
				) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"SaveVendorNameCommand");
		}	else if (event.equals(EVENT_EDIT_SUBLINE_PARTY)
					|| event.equals(EVENT_EDIT_SUBLINE_PARTY_IN_EDIT)
					|| event.equals(EVENT_EDIT_SUBLINE_PARTY_IN_RESUBMIT)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get(
						"PrepareEditSublinePartyCommand");
				
		} else if (event.equals(EVENT_DELETE_SYSTEM)
				|| event.equals(EVENT_DELETE_SYSTEM_IN_EDIT)
				|| event.equals(EVENT_DELETE_SYSTEM_IN_RESUBMIT)
				|| event.equals(EVENT_DELETE_SYSTEM_PARTYMAKER)
				|| event.equals(EVENT_DELETE_SYSTEM_IN_EDIT_PARTYMAKER)
				|| event.equals(EVENT_DELETE_SYSTEM_IN_RESUBMIT_PARTYMAKER)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"PrepareEditSystemCommand");
		} else if (event.equals(EVENT_DELETE_SUBLINE_PARTY)
				|| event.equals(EVENT_DELETE_SUBLINE_PARTY_IN_EDIT)
				|| event.equals(EVENT_DELETE_SUBLINE_PARTY_IN_RESUBMIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"PrepareDeleteSublinePartyCommand");
		}else if (event.equals(EVENT_DELETE_VENDOR)
				|| event.equals(EVENT_DELETE_VENDOR_IN_EDIT)
				|| event.equals(EVENT_DELETE_VENDOR_IN_RESUBMIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"PrepareDeleteVendorPartyCommand");
		}else if (event.equals("vendor_deleted")
					|| event.equals("vendor_deleted_in_edit")
					|| event.equals("vendor_deleted_in_resubmit")) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get(
						"ConfirmDeleteVendorPartyCommand");	
		} else if (event.equals(EVENT_SAVE_EDITED_SYSTEM)
				|| event.equals(EVENT_SAVE_EDITED_SYSTEM_IN_EDIT)
				|| event.equals(EVENT_SAVE_EDITED_SYSTEM_IN_RESUBMIT)
				|| event.equals(EVENT_SAVE_EDITED_SYSTEM_PARTYMAKER)
				|| event.equals(EVENT_SAVE_EDITED_SYSTEM_IN_EDIT_PARTYMAKER)
				|| event.equals(EVENT_SAVE_EDITED_SYSTEM_IN_RESUBMIT_PARTYMAKER)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"SaveEditedSystemCommand");
		} else if (event.equals(EVENT_SYSTEM_DELETED)
				|| event.equals(EVENT_SYSTEM_DELETED_IN_EDIT)
				|| event.equals(EVENT_SYSTEM_DELETED_IN_RESUBMIT)
				|| event.equals(EVENT_SYSTEM_DELETED_PARTYMAKER)
				|| event.equals(EVENT_SYSTEM_DELETED_IN_EDIT_PARTYMAKER)
				|| event.equals(EVENT_SYSTEM_DELETED_IN_RESUBMIT_PARTYMAKER)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"SystemDeletedCommand");
		} else if (event.equals(EVENT_CONFIRM_DELETE_PARTY_GROUP)
				|| event.equals(EVENT_CONFIRM_DELETE_PARTY_GROUP_IN_EDIT)
				|| event.equals(EVENT_CONFIRM_DELETE_PARTY_GROUP_IN_RESUBMIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"ConfirmDeletePartyGroupCommand");
		} else if (event.equals(EVENT_COPY_OFFICE_TO_REG)
				|| event.equals(EVENT_COPY_OFFICE_TO_REG_EDIT)
				|| event.equals(EVENT_COPY_OFFICE_TO_REG_RESUBMIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CopyOfficeToRegisterCommand");
		} else if (event.equals(EVENT_EDIT_DIRECTOR)
				|| event.equals(EVENT_EDIT_DIRECTOR_IN_EDIT)
				|| event.equals(EVENT_EDIT_DIRECTOR_IN_RESUBMIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"PrepareEditDirectorCommand");
		} else if (event.equals(EVENT_SAVE_EDITED_DIRECTOR)
				|| event.equals(EVENT_SAVE_EDITED_DIRECTOR_IN_EDIT)
				|| event.equals(EVENT_SAVE_EDITED_DIRECTOR_IN_RESUBMIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"SaveEditedDirectorCommand");
		} else if (event.equals(EVENT_DELETE_DIRECTOR)
				|| event.equals(EVENT_DELETE_DIRECTOR_IN_EDIT)
				|| event.equals(EVENT_DELETE_DIRECTOR_IN_RESUBMIT))
		{
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"PrepareEditDirectorCommand");
		} else if (event.equals(EVENT_TO_TRACK_VIEW_DIRECTOR)
			||	event.equals(EVENT_CHECKER_UPDATE_VIEW_DIRECTOR)
			||	event.equals(EVENT_CHECKER_CREATE_VIEW_DIRECTOR)
			||	event.equals(EVENT_CLOSE_MAKER_VIEW_DIRECTOR)
			||	event.equals(EVENT_CHECKER_VIEW_DIRECTOR)
			||	event.equals(EVENT_CHECKER_VIEW_DIRECTOR_BRMAKER)
	        ||	event.equals(EVENT_MAKER_VIEW_DIRECTOR)
			
		)
		{
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"ViewPrepareEditDirectorCommand");
		} else if (event.equals(EVENT_CONFIRM_DELETE_DIRECTOR)
				|| event.equals(EVENT_CONFIRM_DELETE_DIRECTOR_IN_EDIT)
				|| event.equals(EVENT_CONFIRM_DELETE_DIRECTOR_IN_RESUBMIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"ConfirmDeleteDirectorCommand");
		}else if (event.equals(EVENT_DELETE_BANKING_METHOD)
				|| event.equals(EVENT_DELETE_BANKING_METHOD_IN_EDIT)
				|| event.equals(EVENT_DELETE_BANKING_METHOD_IN_RESUBMIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"ConfirmDeleteBankingMethodCommand");
		} else if (event.equals(EVENT_REFRESH_RBI_CODE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"ConfirmDeleteDirectorCommand");
		} else if (event.equals(EVENT_CREATE_CUSTOMER_ERROR)
				|| event.equals(EVENT_CREATE_CUSTOMER_BRMAKER_ERROR)
				|| event.equals(EVENT_EDIT_CUSTOMER_ERROR)
					|| event.equals(EVENT_EDIT_CUSTOMER_BRMAKER_ERROR)
				||event.equals(EVENT_CREATE_DRAFT_CUSTOMER_ERROR)
				||event.equals(EVENT_CREATE_DRAFT_CUSTOMER_BRMAKER_ERROR)
				
				||event.equals(EVENT_UPDATE_DRAFT_CUSTOMER_ERROR)
				||event.equals(EVENT_UPDATE_DRAFT_CUSTOMER_BRMAKER_ERROR)
				
				|| event.equals(EVENT_MAKER_RESUBMIT_UPDATE_CUSTOMER_ERROR)
				|| event.equals(EVENT_MAKER_RESUBMIT_CREATE_CUSTOMER_ERROR)
				
				|| event.equals(EVENT_EDIT_CUSTOMER_PARTYMAKER_ERROR)
				|| event.equals(EVENT_CREATE_DRAFT_CUSTOMER_PARTYMAKER_ERROR)
				||event.equals(EVENT_UPDATE_DRAFT_CUSTOMER_PARTYMAKER_ERROR)
				|| event.equals(EVENT_MAKER_RESUBMIT_UPDATE_CUSTOMER_PARTYMAKER_ERROR)
				|| event.equals("create_customer_partymaker_error")
			
					
				) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CreateCustomerErrorCommand");
		}
		else if (event.equals(EVENT_SAVE_DIRECTOR_ERROR)
				|| event.equals(EVENT_SAVE_DIRECTOR_IN_EDIT_ERROR)
				|| event.equals(EVENT_SAVE_DIRECTOR_IN_RESUBMIT_ERROR)
				|| event.equals(EVENT_SAVE_EDITED_DIRECTOR_IN_RESUBMIT_ERROR)
				|| event.equals(EVENT_SAVE_EDITED_DIRECTOR_IN_EDIT_ERROR)
				|| event.equals(EVENT_SAVE_EDITED_DIRECTOR_ERROR)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"SaveDirectorErrorCommand");
		}
		else if (event.equals(EVENT_SAVE_CUSTOMER_ERROR)
				|| event.equals(EVENT_SAVE_CUSTOMER_IN_EDIT_ERROR)
				||event.equals(EVENT_SAVE_CUSTOMER_PARTYMAKER_ERROR)
				|| event.equals(EVENT_SAVE_CUSTOMER_IN_EDIT_PARTYMAKER_ERROR)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"SaveCustomerErrorCommand");
		}
		else if (event.equals(EVENT_VALIDATE_CUSTOMER_FORM) 
				|| event.equals(EVENT_VALIDATE_CUSTOMER_FORM_IN_EDIT) 
				|| event.equals(EVENT_VALIDATE_CUSTOMER_FORM_IN_RESUBMIT)) {
			objArray = new ICommand[3];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"DisplaySummaryDetailsCommand");
			objArray[1] = (ICommand) getNameCommandMap().get(
					"DisplayCibilDetailsCommand");
			objArray[2] = (ICommand) getNameCommandMap().get(
					"CreateCustomerErrorCommand");
		}else if (event.equals(EVENT_VALIDATE_CUSTOMER_FORM_SAVE)
			||	event.equals(EVENT_VALIDATE_CUSTOMER_FORM_IN_EDIT_SAVE)) {
			objArray = new ICommand[3];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"DisplaySummaryDetailsCommand");
			objArray[1] = (ICommand) getNameCommandMap().get(
					"DisplayCibilDetailsCommand");
			objArray[2] = (ICommand) getNameCommandMap().get(
					"SaveCustomerErrorCommand");
		} else if (event.equals(EVENT_SAVE_CUSTOMER) || event.equals(EVENT_SAVE_CUSTOMER_BRMAKER) || event.equals(EVENT_SAVE_CUSTOMER_PARTYMAKER)) {
			objArray = new ICommand[1];
			objArray[0] = new ManualInputSaveCustomerCommand();
		}  else if ( event.equals(EVENT_SAVE_CUSTOMER_IN_EDIT) ||  event.equals(EVENT_SAVE_CUSTOMER_IN_EDIT_BRMAKER) || EVENT_SAVE_CUSTOMER_IN_EDIT_PARTYMAKER.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ManualInputSaveEditCustomerCommand();
		} else if (event.equals(EVENT_ADD_CRI_FAC_LIST) || event.equals(EVENT_EDIT_CRI_FAC_LIST) || event.equals(EVENT_PROCESS_CRI_FAC_LIST)
				) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"AddPrepareCriFacilityCommand");
		}else if (event.equals(EVENT_SAVE_CRI_FAC_LIST) || event.equals("edit_cri_facility") || event.equals("process_cri_facility")
				) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"SaveCriFacilityCommand");
		} else if (event.equals(EVENT_REMOVE_CRI_FAC) || event.equals(EVENT_EDIT_REMOVE_CRI_FAC) || event.equals(EVENT_PROCESS_REMOVE_CRI_FAC)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"PrepareRemoveCriFacCommand");
		}else if (EVENT_REFRESH_FACILITY_LIST.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RefreshLmtFacListCmd();
		}else if (EVENT_REFRESH_FACILITY_DETAIL_LIST.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RefreshLmtFacDetailListCmd();
		}else if (EVENT_REFRESH_TRANCH_DETAIL_LIST.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RefreshLmtTranchDetailListCmd();
		}else if (EVENT_EDIT_CRI_FACILITY_ERROR.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SaveCriErrorCommand();
		}else if (EVENT_CALCULATE_FINANCIAL_DETAIL.equals(event)
				|| EVENT_CALCULATE_FINANCIAL_DETAIL_IN_EDIT.equals(event)
						|| EVENT_CALCULATE_FINANCIAL_DETAIL_IN_RESUBMIT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CalculateFinanceDetailCommand();
		}
		else if (event.equals(EVENT_VIEW_OTHERBANK_BY_INDEX)
				|| event.equals(EVENT_VIEW_OTHERBANK_BY_INDEX_IN_EDIT)
				|| event.equals(EVENT_VIEW_OTHERBANK_BY_INDEX_IN_RESUBMIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"ViewOtherBankByIndexCommand");
		} 
		else if(event.equals(EVENT_CHECKER_UPDATE_CIBIL_DETAILS)){
			objArray=new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
			"CheckerUpdateCibilDetailsCommand");
		}
		else if (event.equals(EVENT_CREATE_CHECKER_CIBIL_DETAILS)){
			objArray=new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
			"CreateCheckerCibilDetailsCommand");
		}
		else if (event.equals(EVENT_VALIDATE_PAN_NO_WITH_NSDL)){
			objArray=new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("ValidatePANNoWithNSDLCommand");
		}
		else if (event.equals(EVENT_VALIDATE_LEI_CODE_WITH_CCIL)){
			objArray=new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("ValidateLEICodeWithCCILCommand");
		}
		else if (event.equals(EVENT_VALIDATE_LEI_DETAILS_CHANGE)){
			objArray=new ICommand[1];
			objArray[0] = new ValidateLEICodeChangeCommand();
		}
		else if (event.equals(EVENT_POPULATE_RM_DATA)){
			objArray=new ICommand[1];
			objArray[0] = new PopulateRMDataCommand();
		}
		else if (event.equals(EVENT_REFRESH_SECURITY_TYPE)){
			objArray=new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
			"RefreshSecurityTypeCommand");
		}else if(event.equals(EVENT_UPDATE_SESSION_ADD_CO_BORROWER_DETAILS)
				|| event.equals(EVENT_UPDATE_SESSION_VIEW_CO_BORROWER_DETAILS)
				|| event.equals(EVENT_UPDATE_SESSION_EDIT_CO_BORROWER_DETAILS)
				|| event.equals(EVENT_UPDATE_SESSION_REMOVE_CO_BORROWER_DETAILS)
				) {
			objArray=new ICommand[1];
			objArray[0] = new UpdateSessionPartyDetailsCommand();
		} else if(event.equals(EVENT_PREPARE_ADD_CO_BORROWER_DETAILS)) {
			objArray=new ICommand[1];
			objArray[0] = new PrepareAddCoBorrowerDetailsCommand();
		}else if(event.equals(EVENT_ADD_CO_BORROWER_DETAILS)) {
			objArray=new ICommand[1];
			objArray[0] = new AddCoBorrowerDetailsCommand();
		}else if(event.equals(EVENT_PREPARE_EDIT_CO_BORROWER_DETAILS)) {
			objArray=new ICommand[1];
			objArray[0] = new PrepareEditCoBorrowerDetailsCommand();
		}else if(event.equals(EVENT_EDIT_CO_BORROWER_DETAILS)
				|| event.equals(EVENT_VIEW_CO_BORROWER_DETAILS)) {
			objArray=new ICommand[1];
			objArray[0] = new EditCoBorrowerDetailsCommand();
		}else if(event.equals(EVENT_SAVE_CO_BORROWER_DETAILS)) {
			objArray=new ICommand[1];
			objArray[0] = new SaveCoBorrowerDetailsCommand();
		}else if(event.equals(EVENT_REMOVE_CO_BORROWER_DETAILS)) {
			objArray=new ICommand[1];
			objArray[0] = new RemoveCoBorrowerDetailsCommand();
		}else if(event.equals(EVENT_RETURN_CO_BORROWER_DETAILS)) {
			objArray=new ICommand[1];
			objArray[0] = new ReturnPartyDetailsCommand();
		}else {
			objArray=new ICommand[1];
			objArray[0] = new UpdateSessionPartyDetailsCommand();
		}

		DefaultLogger.info(this, "Command Chain: "+Arrays.toString(objArray));
		return (objArray);
	}

	/**
	 * This method is called only for create and Update command to validate the
	 * form and return the ActionErrors object.
	 * 
	 * @param aForm
	 *            is of type ActionForm
	 * @param locale
	 *            of type Locale
	 * @return ActionErrors
	 */

	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		DefaultLogger.debug(this, "VALIDATION REQUIRED...");
		if(aForm instanceof CoBorrowerDetailsForm) {
			return CoBorrowerDetailsValidator.validateInput((CoBorrowerDetailsForm)aForm, locale);
		}else {
			return ManualInputCustomerValidator.validateInput(aForm, locale);
		}
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;

		if (event.equals(EVENT_CREATE_CUSTOMER)
				|| event.equals(EVENT_CREATE_CUSTOMER_BRMAKER)
				|| event.equals(EVENT_LIST_CUSTOMERS)
				|| event.equals(EVENT_SAVE_OTHER_SYSTEM)
				|| event.equals(EVENT_SAVE_OTHER_SYSTEM_IN_EDIT)
				|| event.equals(EVENT_SAVE_OTHER_SYSTEM_IN_RESUBMIT)
				|| event.equals(EVENT_SAVE_PARTY_GROUP)
				|| event.equals(EVENT_SAVE_PARTY_GROUP_IN_EDIT)
				|| event.equals(EVENT_SAVE_PARTY_GROUP_IN_RESUBMIT)
				|| event.equals(EVENT_SAVE_DIRECTOR)
				|| event.equals(EVENT_SAVE_DIRECTOR_IN_EDIT)
				|| event.equals(EVENT_SAVE_DIRECTOR_IN_RESUBMIT)
				|| event.equals(EVENT_SAVE_VENDOR)
				|| event.equals(EVENT_SAVE_VENDOR_IN_EDIT)
				|| event.equals(EVENT_SAVE_VENDOR_IN_RESUBMIT)
				|| event.equals(EVENT_EDIT_CUSTOMER)
				|| event.equals(EVENT_EDIT_CUSTOMER_BRMAKER)
				|| event.equals(EVENT_MAKER_RESUBMIT_CREATE_CUSTOMER)
				|| event.equals(EVENT_MAKER_RESUBMIT_CREATE_CUSTOMER_BRMAKER)
				|| event.equals(EVENT_MAKER_RESUBMIT_UPDATE_CUSTOMER)
				|| event.equals(EVENT_MAKER_RESUBMIT_UPDATE_CUSTOMER_BRMAKER)	
				|| event.equals(EVENT_SAVE_EDITED_SYSTEM)
				|| event.equals(EVENT_SAVE_EDITED_SYSTEM_IN_EDIT)
				|| event.equals(EVENT_SAVE_EDITED_SYSTEM_IN_RESUBMIT)
				|| event.equals(EVENT_SAVE_EDITED_PARTY_GROUP)
				|| event.equals(EVENT_SAVE_EDITED_PARTY_GROUP_IN_EDIT)
				|| event.equals(EVENT_SAVE_EDITED_PARTY_GROUP_IN_RESUBMIT)
				|| event.equals(EVENT_VALIDATE_CUSTOMER_FORM)
				|| event.equals(EVENT_VALIDATE_CUSTOMER_FORM_SAVE)
				|| event.equals(EVENT_VALIDATE_CUSTOMER_FORM_IN_EDIT)
				|| event.equals(EVENT_VALIDATE_CUSTOMER_FORM_IN_EDIT_SAVE)
				|| event.equals(EVENT_VALIDATE_CUSTOMER_FORM_IN_RESUBMIT)
				|| event.equals(EVENT_UPDATE_DRAFT_CUSTOMER)
				|| event.equals(EVENT_UPDATE_DRAFT_CUSTOMER_BRMAKER)
				|| event.equals(EVENT_CREATE_DRAFT_CUSTOMER)
				|| event.equals(EVENT_CREATE_DRAFT_CUSTOMER_BRMAKER)
				|| event.equals(EVENT_SAVE_CUSTOMER_IN_EDIT)
				|| event.equals(EVENT_SAVE_CUSTOMER)
				|| event.equals(EVENT_SAVE_CRI_FAC_LIST)
				|| event.equals("edit_cri_facility")
				|| event.equals("process_cri_facility")
				|| event.equals(EVENT_SAVE_EDITED_DIRECTOR_IN_EDIT)
				|| event.equals(EVENT_SAVE_EDITED_DIRECTOR_IN_RESUBMIT)
				|| event.equals(EVENT_SAVE_EDITED_DIRECTOR)
				|| event.equals(EVENT_SAVE_EDITED_VENDOR_IN_EDIT)
				|| event.equals(EVENT_SAVE_EDITED_VENDOR_IN_RESUBMIT)
				|| event.equals(EVENT_SAVE_EDITED_VENDOR)
				//Uma Khot:Valid Rating CR
				|| event.equals(EVENT_CREATE_CUSTOMER_PARTYMAKER)
				|| event.equals(EVENT_MAKER_RESUBMIT_CREATE_CUSTOMER_PARTYMAKER)
				|| event.equals(EVENT_MAKER_RESUBMIT_UPDATE_CUSTOMER_PARTYMAKER)
				|| event.equals(EVENT_UPDATE_DRAFT_CUSTOMER_PARTYMAKER)
				|| event.equals(EVENT_CREATE_DRAFT_CUSTOMER_PARTYMAKER)
				|| event.equals(EVENT_EDIT_CUSTOMER_PARTYMAKER)
				||event.equals(EVENT_SAVE_OTHER_SYSTEM_PARTYMAKER)
				|| event.equals(EVENT_SAVE_OTHER_SYSTEM_IN_EDIT_PARTYMAKER)
				|| event.equals(EVENT_SAVE_OTHER_SYSTEM_IN_RESUBMIT_PARTYMAKER)
				|| event.equals(EVENT_SAVE_EDITED_SYSTEM_PARTYMAKER)
				|| event.equals(EVENT_SAVE_EDITED_SYSTEM_IN_EDIT_PARTYMAKER)
				|| event.equals(EVENT_SAVE_EDITED_SYSTEM_IN_RESUBMIT_PARTYMAKER)
				|| event.equals(EVENT_SAVE_CUSTOMER_IN_EDIT_PARTYMAKER)
				|| event.equals(EVENT_SAVE_CUSTOMER_PARTYMAKER)
				|| event.equals(EVENT_ADD_CO_BORROWER_DETAILS)
				|| event.equals(EVENT_SAVE_CO_BORROWER_DETAILS)

		)

		{
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if (EVENT_CREATE_CUSTOMER.equals(event)) {
			errorEvent = "create_customer_error";
		}
		else if (EVENT_CREATE_CUSTOMER_BRMAKER.equals(event)) {
			errorEvent = "create_customer_brmaker_error";
		}else if (EVENT_LIST_CUSTOMERS.equals(event)) {
			errorEvent = "list_customers_error";
		} else if (EVENT_SAVE_OTHER_SYSTEM.equals(event)) {
			errorEvent = "save_other_system_error";
		} else if (EVENT_SAVE_OTHER_SYSTEM_IN_EDIT.equals(event)) {
			errorEvent = "save_other_system_in_edit_error";
		} else if (EVENT_SAVE_OTHER_SYSTEM_IN_RESUBMIT.equals(event)) {
			errorEvent = "save_other_system_in_resubmit_error";
		} else if (EVENT_SAVE_PARTY_GROUP.equals(event)) {
			errorEvent = "save_party_group_error";
		} else if (EVENT_SAVE_PARTY_GROUP_IN_EDIT.equals(event)) {
			errorEvent = "save_party_group_in_edit_error";
		} else if (EVENT_SAVE_PARTY_GROUP_IN_RESUBMIT.equals(event)) {
			errorEvent = "save_party_group_in_resubmit_error";
		} else if (EVENT_SAVE_DIRECTOR.equals(event)) {
			errorEvent = "save_director_error";
		} else if (EVENT_SAVE_DIRECTOR_IN_EDIT.equals(event)) {
			errorEvent = "save_director_in_edit_error";
		} else if (EVENT_SAVE_DIRECTOR_IN_RESUBMIT.equals(event)) {
			errorEvent = "save_director_in_resubmit_error";
		} else if (EVENT_SAVE_VENDOR.equals(event)) {
			errorEvent = "save_vendor_error"; 
		} else if (EVENT_SAVE_VENDOR_IN_EDIT.equals(event)) {
			errorEvent = "save_vendor_in_edit_error";
		} else if (EVENT_SAVE_VENDOR_IN_RESUBMIT.equals(event)) {
			errorEvent = "save_vendor_in_resubmit_error";
		}else if (EVENT_EDIT_CUSTOMER.equals(event)
		//			|| EVENT_REMOVE_CO_BORROWER_DETAILS.equals(event)
		) {
			errorEvent = "edit_customer_error";
		}else if (EVENT_EDIT_CUSTOMER_BRMAKER.equals(event)) {
			errorEvent = "edit_customer_brmaker_error";
		} else if (EVENT_MAKER_RESUBMIT_CREATE_CUSTOMER.equals(event)) {
			errorEvent = "maker_resubmit_create_customer_error";
		}else if (EVENT_MAKER_RESUBMIT_CREATE_CUSTOMER_BRMAKER.equals(event)) {
			errorEvent = "maker_resubmit_create_customer_brmaker_error";
		} else if (EVENT_MAKER_RESUBMIT_UPDATE_CUSTOMER.equals(event)) {
			errorEvent = "maker_resubmit_update_customer_error";
		} else if (EVENT_MAKER_RESUBMIT_UPDATE_CUSTOMER_BRMAKER.equals(event)) {
			errorEvent = "maker_resubmit_update_customer_brmaker_error";
		} else if (EVENT_SAVE_EDITED_SYSTEM.equals(event)) {
			errorEvent = "save_edited_system_error";
		} else if (EVENT_SAVE_EDITED_SYSTEM_IN_EDIT.equals(event)) {
			errorEvent = "save_edited_system_in_edit_error";
		} else if (EVENT_SAVE_EDITED_SYSTEM_IN_RESUBMIT.equals(event)) {
			errorEvent = "save_edited_system_in_resubmit_error";
		} else if (EVENT_SAVE_EDITED_PARTY_GROUP.equals(event)) {
			errorEvent = "save_edited_party_group_error";
		} else if (EVENT_SAVE_EDITED_PARTY_GROUP_IN_EDIT.equals(event)) {
			errorEvent = "save_edited_party_group_in_edit_error";
		} else if (EVENT_SAVE_EDITED_PARTY_GROUP_IN_RESUBMIT.equals(event)) {
			errorEvent = "save_edited_party_group_in_resubmit_error";
		} else if (EVENT_VALIDATE_CUSTOMER_FORM.equals(event)) {
			errorEvent = "validate_customer_form";
		}  else if ( EVENT_VALIDATE_CUSTOMER_FORM_SAVE.equals(event)) {
			errorEvent = "validate_customer_form_save";
		}else if (EVENT_VALIDATE_CUSTOMER_FORM_IN_EDIT.equals(event)) {
			errorEvent = "validate_customer_form_in_edit";
		}else if (EVENT_VALIDATE_CUSTOMER_FORM_IN_EDIT_SAVE.equals(event)) {
			errorEvent = "validate_customer_form_in_edit_save";
		} else if (EVENT_VALIDATE_CUSTOMER_FORM_IN_RESUBMIT.equals(event)) {
			errorEvent = "validate_customer_form_in_resubmit";
		}else if (EVENT_SAVE_CUSTOMER.equals(event)) {
			errorEvent = "save_customer_error";
		} else if (EVENT_SAVE_CUSTOMER_IN_EDIT.equals(event)) {
			errorEvent = "save_customer_in_edit_error";
		} else if (EVENT_CREATE_DRAFT_CUSTOMER.equals(event)) {
			errorEvent = "create_draft_customer_error";
		}else if (EVENT_CREATE_DRAFT_CUSTOMER_BRMAKER.equals(event)) {
			errorEvent = "create_draft_customer_brmaker_error";
		} else if (EVENT_UPDATE_DRAFT_CUSTOMER.equals(event)) {
			errorEvent = "update_draft_customer_error";
		}else if (EVENT_SAVE_CRI_FAC_LIST.equals(event)) {
			errorEvent = "save_cri_fac_error";
		}else if ("edit_cri_facility".equals(event)) {
			errorEvent = "edit_cri_facility_error";
		}else if (EVENT_CHECKER_REJECT_CREATE_CUSTOMER.equals(event)) {
			errorEvent = "checker_reject_create_customer_error";
		}else if (EVENT_CHECKER_REJECT_UPDATE_CUSTOMER.equals(event)) {
			errorEvent = "checker_reject_update_customer_error";
		}else if (EVENT_CHECKER_APPROVE_CREATE_CUSTOMER.equals(event)) {
			errorEvent = "checker_approve_create_customer_error";
		}else if (EVENT_CHECKER_APPROVE_UPDATE_CUSTOMER.equals(event)) {
			errorEvent = "checker_approve_update_customer_error";
		}else if (EVENT_CHECKER_REJECT_CREATE_CUSTOMER_BRCHECKER.equals(event)) {
			errorEvent = "checker_reject_create_customer_brchecker_error";
		}else if (EVENT_CHECKER_REJECT_UPDATE_CUSTOMER_BRMAKER.equals(event)) {
			errorEvent = "checker_reject_update_customer_brmaker_error";
		}else if ("process_cri_facility".equals(event)) {
			errorEvent = "process_cri_facility_error";
		}
		//Uma Khot:Valid Rating CR.
		else if (EVENT_CREATE_DRAFT_CUSTOMER_PARTYMAKER.equals(event)) {
			errorEvent = "create_draft_customer_partymaker_error";
		}
		else if (EVENT_MAKER_RESUBMIT_CREATE_CUSTOMER_PARTYMAKER.equals(event)) {
			errorEvent = "maker_resubmit_create_customer_partymaker_error";
		} else if (EVENT_MAKER_RESUBMIT_UPDATE_CUSTOMER_PARTYMAKER.equals(event)) {
			errorEvent = "maker_resubmit_update_customer_partymaker_error";
		}
		else if (EVENT_CHECKER_REJECT_CREATE_CUSTOMER_PARTYMAKER.equals(event)) {
			errorEvent = "checker_reject_create_customer_partymaker_error";
		}else if (EVENT_CHECKER_APPROVE_CREATE_CUSTOMER_PARTYMAKER.equals(event)) {
			errorEvent = "checker_approve_create_customer_partymaker_error";
		}
		if (EVENT_CREATE_CUSTOMER_PARTYMAKER.equals(event)) {
			errorEvent = "create_customer_partymaker_error";
		}
		else if (EVENT_CHECKER_REJECT_UPDATE_CUSTOMER_PARTYMAKER.equals(event)) {
			errorEvent = "checker_reject_update_customer_partymaker_error";
		}else if (EVENT_CHECKER_APPROVE_UPDATE_CUSTOMER_PARTYMAKER.equals(event)) {
			errorEvent = "checker_approve_update_customer_partymaker_error";
		}
		
		else if (EVENT_SAVE_OTHER_SYSTEM_PARTYMAKER.equals(event)) {
			errorEvent = "save_other_system_error_partymaker";
		} else if (EVENT_SAVE_OTHER_SYSTEM_IN_EDIT_PARTYMAKER.equals(event)) {
			errorEvent = "save_other_system_in_edit_error_partymaker";
		} else if (EVENT_SAVE_OTHER_SYSTEM_IN_RESUBMIT_PARTYMAKER.equals(event)) {
			errorEvent = "save_other_system_in_resubmit_error_partymaker";
		} 	else if (EVENT_SAVE_EDITED_SYSTEM_PARTYMAKER.equals(event)) {
			errorEvent = "save_edited_system_error_partymaker";
		} else if (EVENT_SAVE_EDITED_SYSTEM_IN_EDIT_PARTYMAKER.equals(event)) {
			errorEvent = "save_edited_system_in_edit_error_partymaker";
		} else if (EVENT_SAVE_EDITED_SYSTEM_IN_RESUBMIT_PARTYMAKER.equals(event)) {
			errorEvent = "save_edited_system_in_resubmit_error_partymaker";
		} else if (EVENT_EDIT_CUSTOMER_PARTYMAKER.equals(event)) {
			errorEvent = "edit_customer_partymaker_error";
		} else if (EVENT_UPDATE_DRAFT_CUSTOMER_PARTYMAKER.equals(event)) {
			errorEvent = "update_draft_customer_partymaker_error";
		} else if (EVENT_SAVE_CUSTOMER_PARTYMAKER.equals(event)) {
			errorEvent = "save_customer_partymaker_error";
		} else if (EVENT_SAVE_CUSTOMER_IN_EDIT_PARTYMAKER.equals(event)) {
			errorEvent = "save_customer_in_edit_partymaker_error";
		} else if (EVENT_SAVE_EDITED_DIRECTOR_IN_EDIT.equals(event)) {
			errorEvent = "save_edited_director_in_edit_error";
		} else if (EVENT_SAVE_EDITED_DIRECTOR_IN_RESUBMIT.equals(event)) {
			errorEvent = "save_edited_director_in_resubmit_error";
		} else if (EVENT_SAVE_EDITED_DIRECTOR.equals(event)) {
			errorEvent = "save_edited_director_error";
		}else if (EVENT_SAVE_EDITED_VENDOR_IN_EDIT.equals(event)) {
			errorEvent = "save_edited_vendor_in_edit_error";
		} else if (EVENT_SAVE_EDITED_VENDOR_IN_RESUBMIT.equals(event)) {
			errorEvent = "save_edited_vendor_in_resubmit_error";
		} else if (EVENT_SAVE_EDITED_VENDOR.equals(event)) {
			errorEvent = "save_edited_vendor_error";
		} else if (EVENT_ADD_CO_BORROWER_DETAILS.equals(event)) {
			errorEvent = EVENT_PREPARE_ADD_CO_BORROWER_DETAILS;
		} else if (EVENT_SAVE_CO_BORROWER_DETAILS.equals(event)) {
			errorEvent = EVENT_PREPARE_EDIT_CO_BORROWER_DETAILS;
		}
		else if ( EVENT_REMOVE_CO_BORROWER_DETAILS.equals(event) ) {
			errorEvent = EVENT_RETURN_CO_BORROWER_DETAILS;
		}
			 
		return errorEvent;
	}

	/**
	 * This method is used to determine which the page to be displayed next
	 * using the event Result hashmap and exception hashmap.It returns the page
	 * object .
	 * 
	 * @param event
	 *            is of type String
	 * @param resultMap
	 *            is of type HashMap
	 * @param exceptionMap
	 *            is of type HashMap
	 * @return IPage
	 */
	public IPage getNextPage(String event, HashMap resultMap,
			HashMap exceptionMap) {
		Page aPage = new Page();
		String fromPage = (String) resultMap.get("fromPage");
		DefaultLogger.debug(this, " Exception map error is "
				+ exceptionMap.isEmpty());

		if ((resultMap.get("wip") != null)
				&& (resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference("work_in_process");
			return aPage;
		} else if (event.equals(EVENT_VALIDATE_CUSTOMER_FORM) || event.equals(EVENT_VALIDATE_CUSTOMER_FORM_SAVE)) {
			if (fromPage != null
					&& (fromPage.equals("display_summary_details")
							|| fromPage.equals("display_financial_details")
							|| fromPage.equals("display_cri_details") || fromPage
							.equals("display_cibil_details")
							|| fromPage.equals("display_udf_details")
							|| "display_banking_method_details".equals(fromPage))) {
				aPage.setPageReference("display_summary_details");
				return aPage;
			} else {
				aPage.setPageReference("display_summary_details");
				return aPage;
			}
		} else if (event.equals(EVENT_VALIDATE_CUSTOMER_FORM_IN_EDIT) || event.equals(EVENT_VALIDATE_CUSTOMER_FORM_IN_EDIT_SAVE)) {
			if (fromPage != null
					&& (fromPage.equals("edit_summary_details")
							|| fromPage.equals("edit_financial_details")
							|| fromPage.equals("edit_cri_details") || fromPage
							.equals("edit_cibil_details")
							|| fromPage.equals("edit_udf_details")
							|| fromPage.equals("edit_banking_method_details"))) {
				aPage.setPageReference("edit_summary_details");
				return aPage;
			} else {
				aPage.setPageReference("edit_summary_details");
				return aPage;
			}
		}else if (event.equals(EVENT_VALIDATE_CUSTOMER_FORM_IN_RESUBMIT)) {
			if (fromPage != null
					&& (fromPage.equals("process_summary_details")
							|| fromPage.equals("process_financial_details")
							|| fromPage.equals("process_cri_details") 
							|| fromPage.equals("process_cibil_details")
							|| fromPage.equals("process_udf_details")
							|| fromPage.equals("process_banking_method_details"))) {
				aPage.setPageReference("process_summary_details");
				return aPage;
			} else {
				aPage.setPageReference("process_summary_details");
				return aPage;
			}
		}else if(event.equals("checker_reject_create_customer_error")){
			aPage.setPageReference("create_checker_process");
			return aPage;
		}
		else if(event.equals("checker_reject_update_customer_error")){
			aPage.setPageReference(EVENT_MAKER_UPDATE_CUSTOMER);
			return aPage;
		}else if(event.equals("checker_approve_create_customer_error")){
			aPage.setPageReference("create_checker_process");
			return aPage;
		}
		else if(event.equals("checker_approve_update_customer_error")){
			aPage.setPageReference(EVENT_MAKER_UPDATE_CUSTOMER);
			return aPage;
		}else if(event.equals("checker_reject_create_customer_brchecker_error")){
			aPage.setPageReference("create_checker_process_brchecker");
			return aPage;
		}
		else if(event.equals("checker_reject_update_customer_brmaker_error")){
			aPage.setPageReference(EVENT_MAKER_UPDATE_CUSTOMER_BRMAKER);
			return aPage;
		}else if(event.equals(EVENT_VALIDATE_PAN_NO_WITH_NSDL)){
			aPage.setPageReference("validate_pan_no_with_NSDL");
			return aPage;
		}else if(event.equals(EVENT_VALIDATE_LEI_CODE_WITH_CCIL)){
			aPage.setPageReference("validate_lei_code_with_CCIL");
			return aPage;
		}
		else if(event.equals(EVENT_POPULATE_RM_DATA)){
			aPage.setPageReference(EVENT_POPULATE_RM_DATA);
			return aPage;
		}
		//Start: Uma:Valid Ratinng CR
		else if(event.equals("checker_reject_create_customer_partymaker_error")){
			aPage.setPageReference("create_checker_process_partymaker");
			return aPage;
		}
		else if(event.equals("checker_approve_create_customer_partymaker_error")){
			aPage.setPageReference("create_checker_process_partymaker");
			return aPage;
		}
		else if(event.equals("checker_reject_update_customer_partymaker_error")){
			aPage.setPageReference(EVENT_MAKER_UPDATE_CUSTOMER_PARTYMAKER);
			return aPage;
		}
			else if(event.equals("checker_approve_update_customer_partymaker_error")){
			aPage.setPageReference(EVENT_MAKER_UPDATE_CUSTOMER_PARTYMAKER);
			return aPage;
		}else if(event.equals(EVENT_UPDATE_SESSION_ADD_CO_BORROWER_DETAILS)){
			aPage.setPageReference(EVENT_UPDATE_SESSION_ADD_CO_BORROWER_DETAILS);
			return aPage;
		}else if(event.equals(EVENT_UPDATE_SESSION_VIEW_CO_BORROWER_DETAILS)){
			aPage.setPageReference(EVENT_UPDATE_SESSION_VIEW_CO_BORROWER_DETAILS);
			return aPage;
		}else if(event.equals(EVENT_UPDATE_SESSION_EDIT_CO_BORROWER_DETAILS)){
			aPage.setPageReference(EVENT_UPDATE_SESSION_EDIT_CO_BORROWER_DETAILS);
			return aPage;
		}else if(event.equals(EVENT_UPDATE_SESSION_REMOVE_CO_BORROWER_DETAILS)){
			aPage.setPageReference(EVENT_UPDATE_SESSION_REMOVE_CO_BORROWER_DETAILS);
			return aPage;
		}else if (EVENT_PREPARE_ADD_CO_BORROWER_DETAILS.equals(event)) {
			aPage.setPageReference(EVENT_PREPARE_ADD_CO_BORROWER_DETAILS);
			return aPage;
		}else if (EVENT_PREPARE_EDIT_CO_BORROWER_DETAILS.equals(event)) {
			aPage.setPageReference(EVENT_PREPARE_EDIT_CO_BORROWER_DETAILS);
			return aPage;
		}else if (EVENT_ADD_CO_BORROWER_DETAILS.equals(event)
				|| EVENT_SAVE_CO_BORROWER_DETAILS.equals(event)) {
			aPage.setPageReference(EVENT_RETURN_CO_BORROWER_DETAILS);
			return aPage;
		}else if (EVENT_VIEW_CO_BORROWER_DETAILS.equals(event)) {
			aPage.setPageReference(EVENT_VIEW_CO_BORROWER_DETAILS);
			return aPage;
		}else if (EVENT_REMOVE_CO_BORROWER_DETAILS.equals(event)) {
			aPage.setPageReference(EVENT_RETURN_CO_BORROWER_DETAILS);
			return aPage;
		}else if (EVENT_EDIT_CO_BORROWER_DETAILS.equals(event)) {
			aPage.setPageReference(EVENT_EDIT_CO_BORROWER_DETAILS);
			return aPage;
		}else if(event.equals(EVENT_RETURN_CO_BORROWER_DETAILS)){

		  if(EVENT_PREPARE_CREATE_CUSTOMER.equals(fromPage) || EVENT_SAVE_VENDOR.equals(fromPage) || EVENT_DISPLAY_SUMMARY_DETAILS.equals(fromPage))

			  aPage.setPageReference(EVENT_RETURN_CREATE_CO_BORROWER_DETAILS); 
		  else
			  aPage.setPageReference(EVENT_RETURN_CO_BORROWER_DETAILS);
			return aPage;
		}else {
			aPage.setPageReference(getReference(event));
			return aPage;
		}
	}

	/**
	 * method which determines the forward name for a particular event
	 * 
	 * @param event
	 *            as String
	 * @return String
	 */
	private String getReference(String event) {
		String forwardName = "submit_fail";
		if (EVENT_PREPARE.equals(event)) {
			forwardName = "prepare_list_page";

		}  else if (EVENT_PREPARE_BRANCH_MAKER.equals(event)) {
			forwardName = "prepare_list_page_brmaker";

		} else if (EVENT_PREPARE_CHECKER.equals(event)) {
			forwardName = "prepare_list_page_checker";

		}else if (EVENT_PREPARE_BRCHECKER.equals(event)) {
			forwardName = "prepare_list_page_brchecker";

		}else if (EVENT_VIEW.equals(event) || "view_customer".equals(event)
				|| "view_summary_details".equals(event)) {
			forwardName = "view";
		}else if ("view_customer_branch".equals(event)) {
				forwardName = "view_branch";	
		}else if ( "view_customer_checker".equals(event) ||  "view_customer_brchecker".equals(event)  || "view_summary_details_checker".equals(event)) {
			forwardName = "view_for_checker";

		} else if ("first_search".equals(event)) {
			forwardName = "ajax_search_cif_result";

		} else if ("subsequent_search".equals(event)) {
			forwardName = "ajax_search_cif_result";

		} else if (EVENT_PREPARE_EDIT_CUSTOMER_DETAILS.equals(event)) {
			forwardName = EVENT_PREPARE_EDIT_CUSTOMER_DETAILS;

		} else if (EVENT_PREPARE_EDIT_CUSTOMER_DETAILS_BRMAKER.equals(event)) {
			forwardName = EVENT_PREPARE_EDIT_CUSTOMER_DETAILS_BRMAKER;

		} else if (EVENT_EDIT_CUSTOMER.equals(event)
				|| EVENT_MAKER_RESUBMIT_UPDATE_CUSTOMER.equals(event)
					|| EVENT_MAKER_RESUBMIT_UPDATE_CUSTOMER_BRMAKER.equals(event)
				|| EVENT_UPDATE_DRAFT_CUSTOMER.equals(event)	
				|| EVENT_UPDATE_DRAFT_CUSTOMER_BRMAKER.equals(event)	
					|| EVENT_EDIT_CUSTOMER_BRMAKER.equals(event)
					|| EVENT_EDIT_CUSTOMER_PARTYMAKER.equals(event)
					|| EVENT_UPDATE_DRAFT_CUSTOMER_PARTYMAKER.equals(event)	
					|| EVENT_MAKER_RESUBMIT_UPDATE_CUSTOMER_PARTYMAKER.equals(event)	
		) {
			forwardName = EVENT_EDIT_CUSTOMER;

		} else if (EVENT_LIST_CUSTOMERS.equals(event) || EVENT_CHECKER_LIST_PARTY_DETAIL.equals(event) || EVENT_LIST_CUSTOMER_CHECKER.equals(event) || EVENT_BRCHECKER_LIST_PARTY_DETAIL.equals(event) ||  EVENT_LIST_CUSTOMER_BRCHECKER.equals(event) ) {
			forwardName = EVENT_LIST_CUSTOMERS;

		}  else if ( EVENT_LIST_CUSTOMERS_BRMAKER.equals(event)) {
			forwardName = EVENT_LIST_CUSTOMERS_BRANCH;

		}else if (EVENT_PREPARE_CREATE_CUSTOMER.equals(event)) {
			forwardName = EVENT_PREPARE_CREATE_CUSTOMER;

		}else if (EVENT_PREPARE_CREATE_CUSTOMER_BRMAKER.equals(event)) {
			forwardName = EVENT_PREPARE_CREATE_CUSTOMER_BRMAKER;

		} else if (EVENT_CREATE_CUSTOMER.equals(event)
				|| EVENT_MAKER_RESUBMIT_CREATE_CUSTOMER.equals(event)
					|| EVENT_MAKER_RESUBMIT_CREATE_CUSTOMER_BRMAKER.equals(event)
			|| EVENT_CREATE_DRAFT_CUSTOMER.equals(event)
				|| EVENT_CREATE_DRAFT_CUSTOMER_BRMAKER.equals(event)
			|| EVENT_CREATE_CUSTOMER_BRMAKER.equals(event)
			
			|| EVENT_CREATE_CUSTOMER_PARTYMAKER.equals(event)
			|| EVENT_CREATE_DRAFT_CUSTOMER_PARTYMAKER.equals(event)
			|| EVENT_MAKER_RESUBMIT_CREATE_CUSTOMER_PARTYMAKER.equals(event)
			
		) {
			forwardName = EVENT_CREATE_CUSTOMER;

		} else if (EVENT_PREPARE_DELETE_CUSTOMER_DETAILS.equals(event)
				|| (EVENT_PREPARE_DELETE_SUMMARY_DETAILS.equals(event))
				|| (EVENT_PREPARE_DELETE_FINANCIAL_DETAILS.equals(event))
				|| (EVENT_PREPARE_DELETE_CRI_DETAILS.equals(event))
				|| (EVENT_PREPARE_DELETE_CIBIL_DETAILS.equals(event))) {
			forwardName = EVENT_PREPARE_DELETE_CUSTOMER_DETAILS;
		} else if (EVENT_MAKER_DELETE_CUSTOMER.equals(event)
				|| EVENT_MAKER_RESUBMIT_DELETE_CUSTOMER.equals(event)) {
			forwardName = EVENT_MAKER_DELETE_CUSTOMER;

		} else if (EVENT_DELETE_CUSTOMER_DETAILS.equals(event)) {
			forwardName = EVENT_DELETE_CUSTOMER_DETAILS;

		} else if (EVENT_TOTRACK_MAKER.equals(event)
				|| (EVENT_TO_TRACK_SUMMARY_DETAILS.equals(event))
				|| (EVENT_TO_TRACK_FINANCIAL_DETAILS.equals(event))
				|| (EVENT_TO_TRACK_CRI_DETAILS.equals(event))
				|| (EVENT_TO_TRACK_CIBIL_DETAILS.equals(event))
				|| EVENT_TOTRACK_CHECKER.equals(event)
				|| EVENT_TO_TRACK_UDF_DETAILS.equals(event)) {
			forwardName = EVENT_TOTRACK_MAKER;

		} else if (EVENT_MAKER_SAVE_PROCESS.equals(event))
				 {
			forwardName = EVENT_MAKER_SAVE_PROCESS;

		} else if (EVENT_MAKER_SAVE_PROCESS_BRMAKER.equals(event))
				 {
			forwardName = EVENT_MAKER_SAVE_PROCESS_BRMAKER;

		}  else if (EVENT_CREATE_CHECKER_PROCESS.equals(event)
				|| (EVENT_CREATE_CHECKER_SUMMARY_DETAILS.equals(event))
				|| (EVENT_CREATE_CHECKER_FINANCIAL_DETAILS.equals(event))
				|| (EVENT_CREATE_CHECKER_CRI_DETAILS.equals(event))
				|| (EVENT_CREATE_CHECKER_CIBIL_DETAILS.equals(event))
				|| (EVENT_CREATE_CHECKER_UDF_DETAILS.equals(event))
				|| (EVENT_CREATE_CHECKER_BANKING_METHOD_DETAILS).equals(event)) {
			forwardName = EVENT_CREATE_CHECKER_PROCESS;

		} else if (EVENT_CREATE_CHECKER_PROCESS_BRCHECKER.equals(event)) {
			forwardName = EVENT_CREATE_CHECKER_PROCESS_BRCHECKER;

		}else if (EVENT_MAKER_EDIT.equals(event)) {
			forwardName = EVENT_MAKER_EDIT;

		} else if ((EVENT_MAKER_UPDATE_CUSTOMER.equals(event))
				|| (EVENT_CHECKER_UPDATE_SUMMARY_DETAILS.equals(event))
				|| (EVENT_CHECKER_UPDATE_FINANCIAL_DETAILS.equals(event))
				|| (EVENT_CHECKER_UPDATE_CRI_DETAILS.equals(event))
				|| (EVENT_CHECKER_UPDATE_CIBIL_DETAILS.equals(event))
				|| EVENT_CHECKER_UPDATE_UDF_DETAILS.equals(event)
				|| EVENT_CHECKER_UPDATE_BANKING_METHOD_DETAILS.equals(event)) {
			forwardName = EVENT_MAKER_UPDATE_CUSTOMER;

		} else if ((EVENT_MAKER_UPDATE_CUSTOMER_BRMAKER.equals(event))
				) {
			forwardName = EVENT_MAKER_UPDATE_CUSTOMER_BRMAKER;

		}else if (EVENT_CHECKER_APPROVE_CREATE_CUSTOMER.equals(event) || EVENT_CHECKER_APPROVE_CREATE_CUSTOMER_BRCHECKER.equals(event) || EVENT_CHECKER_APPROVE_CREATE_CUSTOMER_PARTYMAKER.equals(event)) {
			forwardName = EVENT_CHECKER_APPROVE_CREATE_CUSTOMER;
		} else if (EVENT_CHECKER_REJECT_CREATE_CUSTOMER.equals(event) || EVENT_CHECKER_REJECT_CREATE_CUSTOMER_BRCHECKER.equals(event) || EVENT_CHECKER_REJECT_CREATE_CUSTOMER_PARTYMAKER.equals(event)) {
			forwardName = EVENT_CHECKER_REJECT_CREATE_CUSTOMER;
		} else if (EVENT_CHECKER_APPROVE_UPDATE_CUSTOMER.equals(event) || EVENT_CHECKER_APPROVE_UPDATE_CUSTOMER_BRMAKER.equals(event) || EVENT_CHECKER_APPROVE_UPDATE_CUSTOMER_PARTYMAKER.equals(event)) {
			forwardName = EVENT_CHECKER_APPROVE_UPDATE_CUSTOMER;
		} else if (EVENT_CHECKER_REJECT_UPDATE_CUSTOMER.equals(event) || EVENT_CHECKER_REJECT_UPDATE_CUSTOMER_BRMAKER.equals(event)  || EVENT_CHECKER_REJECT_UPDATE_CUSTOMER_PARTYMAKER.equals(event)) {
			forwardName = EVENT_CHECKER_REJECT_UPDATE_CUSTOMER;
		} else if (EVENT_MAKER_PREPARE_RESUBMIT_UPDATE.equals(event)) {
			forwardName = EVENT_MAKER_PREPARE_RESUBMIT_UPDATE;
		} else if (EVENT_MAKER_RESUBMIT_UPDATE.equals(event)) {
			forwardName = EVENT_MAKER_RESUBMIT_UPDATE;
		} else if (EVENT_MAKER_RESUBMIT_UPDATE_BRMAKER.equals(event)) {
			forwardName = EVENT_MAKER_RESUBMIT_UPDATE_BRMAKER;
		} else if (EVENT_TOTRACK_MAKER_DELETE.equals(event)) {
			forwardName = EVENT_TOTRACK_MAKER_DELETE;
		} else if (EVENT_TODO_CHECKER_DELETE.equals(event)
				|| (EVENT_TODO_CHECKER_DELETE_FINANCIAL_DETAILS.equals(event))
				|| (EVENT_TODO_CHECKER_DELETE_CRI_DETAILS.equals(event))
				|| (EVENT_TODO_CHECKER_DELETE_CIBIL_DETAILS.equals(event))
				|| (EVENT_TODO_CHECKER_DELETE_SUMMARY_DETAILS.equals(event))) {
			forwardName = EVENT_TODO_CHECKER_DELETE;
		} else if (EVENT_CHECKER_APPROVE_DELETE_CUSTOMER.equals(event)) {
			forwardName = EVENT_CHECKER_APPROVE_DELETE_CUSTOMER;
		} else if (EVENT_CHECKER_REJECT_DELETE_CUSTOMER.equals(event)) {
			forwardName = EVENT_CHECKER_REJECT_DELETE_CUSTOMER;
		} else if (EVENT_REJECTED_DELETE_READ.equals(event)) {
			forwardName = EVENT_REJECTED_DELETE_READ;
		} else if (EVENT_PREPARE_CLOSE_PAGE.equals(event)
				|| (EVENT_CLOSE_FINANCIAL_DETAILS.equals(event))
				|| (EVENT_MAKER_SAVE_CLOSE.equals(event))
				|| (EVENT_CLOSE_CRI_DETAILS.equals(event))
				|| (EVENT_CLOSE_CIBIL_DETAILS.equals(event))
				|| (EVENT_CLOSE_SUMMARY_DETAILS.equals(event))
				|| (EVENT_CLOSE_UDF_DETAILS.equals(event))
				|| (EVENT_CLOSE_BANKING_METHOD_DETAILS.equals(event))) {
			forwardName = EVENT_PREPARE_CLOSE_PAGE;
		}
		 else if (EVENT_PREPARE_CLOSE_PAGE_BRMAKER.equals(event)
				 ||	(EVENT_MAKER_SAVE_CLOSE_BRMAKER.equals(event))) {
				forwardName = EVENT_PREPARE_CLOSE_PAGE_BRMAKER;
			}
		else if (EVENT_CLOSE_PAGE.equals(event) || EVENT_CLOSE_PAGE_BRMAKER.equals(event) ||  EVENT_CLOSE_PAGE_PARTYMAKER.equals(event)) {
			forwardName = EVENT_CLOSE_PAGE;
		} else if (EVENT_MAKER_CLOSE_DRAFT_CUSTOMER.equals(event) || EVENT_MAKER_CLOSE_DRAFT_CUSTOMER_BRMAKER.equals(event) || EVENT_MAKER_CLOSE_DRAFT_CUSTOMER_PARTYMAKER.equals(event)) {
			forwardName = EVENT_CLOSE_PAGE;
		} else if (EVENT_PROCESS_PAGE.equals(event)
				|| (EVENT_PROCESS_SUMMARY_DETAILS.equals(event))
				|| (EVENT_PROCESS_FINANCIAL_DETAILS.equals(event))
				|| (EVENT_PROCESS_CRI_DETAILS.equals(event))
				|| (EVENT_PROCESS_CIBIL_DETAILS.equals(event))
				|| EVENT_PROCESS_UDF_DETAILS.equals(event)
				|| EVENT_PROCESS_BANKING_METHOD_DETAILS.equals(event)) {
			forwardName = EVENT_PROCESS_PAGE;
		} else if (EVENT_PROCESS_PAGE_BRMAKER.equals(event)) {
			forwardName = EVENT_PROCESS_PAGE_BRMAKER;
		} else if ((EVENT_DISPLAY_SUMMARY_DETAILS.equals(event))
				|| (EVENT_DISPLAY_FINANCIAL_DETAILS.equals(event))
				|| (EVENT_DISPLAY_CRI_DETAILS.equals(event))
				|| (EVENT_DISPLAY_UDF_DETAILS.equals(event))
				|| (EVENT_DISPLAY_CIBIL_DETAILS.equals(event))
				|| (EVENT_DISPLAY_BANKING_METHOD_DETAILS.equals(event))
				) {
			forwardName = EVENT_DISPLAY_SUMMARY_DETAILS;
		}else if (EVENT_CALCULATE_FINANCIAL_DETAIL.equals(event)
				|| EVENT_CALCULATE_FINANCIAL_DETAIL_IN_EDIT.equals(event)
				|| EVENT_CALCULATE_FINANCIAL_DETAIL_IN_RESUBMIT.equals(event)){
			forwardName = EVENT_CALCULATE_FINANCIAL_DETAIL;
		}  
		
		else if ((EVENT_VIEW_SUMMARY_DETAILS.equals(event))
				|| (EVENT_VIEW_FINANCIAL_DETAILS.equals(event))
				|| (EVENT_VIEW_CRI_DETAILS.equals(event))
				|| (EVENT_VIEW_CIBIL_DETAILS.equals(event))
				|| EVENT_VIEW_UDF_DETAILS.equals(event)
				|| EVENT_VIEW_BANKING_METHOD_DETAILS.equals(event)) {
			forwardName = EVENT_VIEW_SUMMARY_DETAILS;
		}  else if ((EVENT_VIEW_SUMMARY_DETAILS_BRANCH.equals(event))
				|| (EVENT_VIEW_FINANCIAL_DETAILS_BRANCH.equals(event))
				|| (EVENT_VIEW_CRI_DETAILS_BRANCH.equals(event))
				|| (EVENT_VIEW_CIBIL_DETAILS_BRANCH.equals(event))
				|| EVENT_VIEW_UDF_DETAILS_BRANCH.equals(event)
				|| EVENT_VIEW_BANKING_METHOD_DETAILS_BRANCH.equals(event)) {
			forwardName = EVENT_VIEW_SUMMARY_DETAILS_BRANCH;
		}  else if ((EVENT_VIEW_SUMMARY_DETAILS_CHECKER.equals(event))
				|| (EVENT_VIEW_FINANCIAL_DETAILS_CHECKER.equals(event))
				|| (EVENT_VIEW_CRI_DETAILS_CHECKER.equals(event))
				|| (EVENT_VIEW_CIBIL_DETAILS_CHECKER.equals(event))
				|| (EVENT_UDF_DETAILS_CHECKER.equals(event))
				|| (EVENT_VIEW_BANKING_METHOD_DETAILS_CHECKER.equals(event))) {
			forwardName = EVENT_VIEW_SUMMARY_DETAILS_CHECKER;
		} else if ((EVENT_EDIT_SUMMARY_DETAILS.equals(event))
				|| (EVENT_EDIT_FINANCIAL_DETAILS.equals(event))
				|| (EVENT_EDIT_CRI_DETAILS.equals(event))
				|| (EVENT_EDIT_CIBIL_DETAILS.equals(event))
				|| (EVENT_EDIT_UDF_DETAILS.equals(event))
				|| (EVENT_EDIT_BANKING_METHOD_DETAILS.equals(event))) {
			forwardName = EVENT_EDIT_SUMMARY_DETAILS;
		} else if (event.equals("refresh_region_id")) {
			forwardName = "refresh_region_id";
		} else if (event.equals("refresh_rbi_industry_code")) {
			forwardName = "refresh_rbi_ind_code";
		}  
		else if (event.equals("refresh_state_id")) {
			forwardName = "refresh_state_id";
		} else if (event.equals("refresh_city_id")) {
			forwardName = "refresh_city_id";
		} else if (event.equals("refresh_rm_id")) {
			forwardName = "refresh_rm_id";
		} else if (event.equals("add_other_system")
				|| event.equals("add_other_system_in_edit")
				|| event.equals("add_other_system_in_resubmit")) {
			forwardName = "add_other_system";
		} else if (event.equals("list_pagination")) {
			forwardName = "list_customers";
		} else if (event.equals("save_other_system")) {
			forwardName = "display_summary_details";
		} else if (event.equals("save_other_system_in_edit")) {
			forwardName = "edit_summary_details";
		} else if (event.equals("save_other_system_in_resubmit")) {
			forwardName = "process_summary_details";
		} else if (event.equals("display_bank_list")
				|| event.equals("display_bank_list_in_edit")
				|| event.equals("display_bank_list_ifsc_code")
				|| event.equals("display_bank_list_in_edit_ifsc_code")
				|| event.equals("display_bank_list_in_resubmit_ifsc_code")
				|| event.equals("display_bank_list_in_resubmit")
				|| event.equals("view_other_bank_by_index")
				|| event.equals("view_other_bank_by_index_in_edit")
				|| event.equals("view_other_bank_by_index_in_resubmit")
		) {
			forwardName = "display_bank_list";
		} else if (event.equals("save_other_bank")) {
			forwardName = EVENT_DISPLAY_SUMMARY_DETAILS;
		} else if (event.equals("save_other_bank_in_edit")) {
			forwardName = EVENT_EDIT_SUMMARY_DETAILS;
		} else if (event.equals("save_other_bank_in_resubmit")) {
			forwardName = EVENT_PROCESS_SUMMARY_DETAILS;
		} else if (event.equals("add_sub_line")
				|| event.equals("add_sub_line_in_edit")
				|| event.equals("add_sub_line_in_resubmit")) {
			forwardName = "add_sub_line";
		}else if (event.equals("add_vendor_name")
					|| event.equals("add_vendor_name_in_edit")
					|| event.equals("add_vendor_name_in_resubmit")) {
				forwardName = "add_vendor";	
		}else if (event.equals("save_vendor_name")|| event.equals("save_edited_vendor") ||event.equals("vendor_deleted")){
			forwardName = "display_summary_details";
		}else if(event.equals("save_vendor_name_in_edit")|| event.equals("save_edited_vendor_in_edit")|| event.equals("vendor_deleted_in_edit")) {
			forwardName = "edit_summary_details";
			}else if(event.equals("save_vendor_name_in_resubmit")|| event.equals("save_edited_vendor_in_resubmit")|| event.equals("vendor_deleted_in_resubmit")){
			forwardName = "process_summary_details";	
		} else if (event.equals("display_party_list")
				|| event.equals("edit_display_party_list")
				|| event.equals("edit_display_party_list_in_edit")
				|| event.equals("display_party_list_in_edit")
				|| event.equals("display_party_list_in_resubmit")
				|| event.equals("edit_display_party_list_in_resubmit")) {
			forwardName = "display_party_list";
		} else if (event.equals("add_party_group")
				|| event.equals("add_party_group_in_edit")
				|| event.equals("add_party_group_in_resubmit")) {
			forwardName = "add_sub_line";
		} else if (event.equals("save_party_group")) {
			forwardName = "display_summary_details";
		} else if (event.equals("save_party_group_in_edit") || event.equals("edit_cri_facility")) {
			forwardName = "edit_summary_details";
		} else if (event.equals("save_party_group_in_resubmit")  || event.equals("process_cri_facility")) {
			forwardName = "process_summary_details";
		} else if (event.equals("edit_system")
				|| event.equals("edit_system_in_edit")
				|| event.equals("edit_system_in_resubmit")) {
			forwardName = "edit_system";
		} else if (event.equals("save_edited_system")) {
			forwardName = EVENT_DISPLAY_SUMMARY_DETAILS;
		} else if (event.equals("save_edited_system_in_edit")) {
			forwardName = EVENT_EDIT_SUMMARY_DETAILS;
		} else if (event.equals("save_edited_system_in_resubmit")) {
			forwardName = EVENT_PROCESS_SUMMARY_DETAILS;
		} else if (event.equals("delete_system")
				|| event.equals("delete_system_in_edit")
				|| event.equals("delete_system_in_resubmit")) {
			forwardName = "delete_system";
		} else if (event.equals("system_deleted")) {
			forwardName = EVENT_DISPLAY_SUMMARY_DETAILS;
		} else if (event.equals("system_deleted_in_edit")) {
			forwardName = EVENT_EDIT_SUMMARY_DETAILS;
		} else if (event.equals("system_deleted_in_resubmit")) {
			forwardName = EVENT_PROCESS_SUMMARY_DETAILS;
		} else if (event.equals("edit_subline_party")
				|| event.equals("edit_subline_party_in_edit")
				|| event.equals("edit_subline_party_in_resubmit")) {
			forwardName = "edit_subline_party";
		} else if (event.equals("edit_vendor")  
				|| event.equals("edit_vendor_in_edit")
				|| event.equals("edit_vendor_in_resubmit")) {
			forwardName = "edit_vendor";
		} else if (event.equals("edited_party_group")
				|| event.equals("edited_party_group_in_edit")
				|| event.equals("edited_party_group_in_resubmit")) {
			forwardName = "edit_subline_party";
		} else if (event.equals("save_edited_party_group")) {
			forwardName = EVENT_DISPLAY_SUMMARY_DETAILS;
		} else if (event.equals("save_edited_party_group_in_edit")) {
			forwardName = EVENT_EDIT_SUMMARY_DETAILS;
		} else if (event.equals("save_edited_party_group_in_resubmit")) {
			forwardName = EVENT_PROCESS_SUMMARY_DETAILS;
		} else if (event.equals("delete_subline_party")
				|| event.equals("delete_subline_party_in_edit")
				|| event.equals("delete_subline_party_in_resubmit")) {
			forwardName = "delete_subline_party";
		} else if (event.equals("delete_vendor")
				|| event.equals("delete_vendor_in_edit")
				|| event.equals("delete_vendor_in_resubmit")) {
			forwardName = "delete_vendor";
		} else if (event.equals("confirm_delete_party_group")) {
			forwardName = EVENT_DISPLAY_SUMMARY_DETAILS;
		} else if (event.equals("confirm_delete_party_group_in_edit")) {
			forwardName = EVENT_EDIT_SUMMARY_DETAILS;
		} else if (event.equals("confirm_delete_party_group_in_resubmit")) {
			forwardName = EVENT_PROCESS_SUMMARY_DETAILS;
		} else if (event.equals("copy_office_to_reg")) {
			forwardName = EVENT_DISPLAY_SUMMARY_DETAILS;
		} else if (event.equals("copy_office_to_reg_edit")) {
			forwardName = EVENT_EDIT_SUMMARY_DETAILS;
		} else if (event.equals("copy_office_to_reg_resubmit")) {
			forwardName = EVENT_PROCESS_SUMMARY_DETAILS;
		} else if (event.equals(EVENT_ADD_DIRECTOR)
				|| event.equals(EVENT_ADD_DIRECTOR_IN_EDIT)
				|| event.equals(EVENT_ADD_DIRECTOR_IN_RESUBMIT)) {
			forwardName = EVENT_ADD_DIRECTOR;
		} else if (event.equals(EVENT_SAVE_DIRECTOR)) {
			forwardName = EVENT_DISPLAY_SUMMARY_DETAILS;
		} else if (event.equals(EVENT_SAVE_DIRECTOR_IN_EDIT)) {
			forwardName = EVENT_EDIT_SUMMARY_DETAILS;
		} else if (event.equals(EVENT_SAVE_DIRECTOR_IN_RESUBMIT)) {
			forwardName = EVENT_PROCESS_SUMMARY_DETAILS;
		} else if (event.equals(EVENT_EDIT_DIRECTOR)
				|| event.equals(EVENT_EDIT_DIRECTOR_IN_EDIT)
				|| event.equals(EVENT_EDIT_DIRECTOR_IN_RESUBMIT)) {
			forwardName = EVENT_EDIT_DIRECTOR;
		} else if (event.equals(EVENT_SAVE_EDITED_DIRECTOR)) {
			forwardName = EVENT_DISPLAY_SUMMARY_DETAILS;
		} else if (event.equals(EVENT_SAVE_EDITED_DIRECTOR_IN_EDIT)) {
			forwardName = EVENT_EDIT_SUMMARY_DETAILS;
		} else if (event.equals(EVENT_SAVE_EDITED_DIRECTOR_IN_RESUBMIT)) {
			forwardName = EVENT_PROCESS_SUMMARY_DETAILS;
		} else if (event.equals(EVENT_DELETE_DIRECTOR)
				|| event.equals(EVENT_DELETE_DIRECTOR_IN_EDIT)
				|| event.equals(EVENT_DELETE_DIRECTOR_IN_RESUBMIT)) {
			forwardName = EVENT_DELETE_DIRECTOR;
		} else if (event.equals(EVENT_TO_TRACK_VIEW_DIRECTOR)
	   ||	event.equals(EVENT_CHECKER_UPDATE_VIEW_DIRECTOR)
	   ||	event.equals(EVENT_CHECKER_CREATE_VIEW_DIRECTOR)
			||	event.equals(EVENT_CLOSE_MAKER_VIEW_DIRECTOR)
			||	event.equals(EVENT_CHECKER_VIEW_DIRECTOR)
			||	event.equals(EVENT_CHECKER_VIEW_DIRECTOR_BRMAKER)
	        ||	event.equals(EVENT_MAKER_VIEW_DIRECTOR))
	        {
			forwardName = EVENT_TO_TRACK_VIEW_DIRECTOR;
		} else if (event.equals(EVENT_CONFIRM_DELETE_DIRECTOR)) {
			forwardName = EVENT_DISPLAY_SUMMARY_DETAILS;
		} else if (event.equals(EVENT_DELETE_BANKING_METHOD)) {
			forwardName = EVENT_DISPLAY_SUMMARY_DETAILS;
		} else if (event.equals(EVENT_DELETE_BANKING_METHOD_IN_EDIT)) {
			forwardName = EVENT_EDIT_SUMMARY_DETAILS;
		} else if (event.equals(EVENT_DELETE_BANKING_METHOD_IN_RESUBMIT)) {
			forwardName = EVENT_PROCESS_SUMMARY_DETAILS;
		} else if (event.equals(EVENT_CONFIRM_DELETE_DIRECTOR_IN_EDIT)) {
			forwardName = EVENT_EDIT_SUMMARY_DETAILS;
		} else if (event.equals(EVENT_CONFIRM_DELETE_DIRECTOR_IN_RESUBMIT)) {
			forwardName = EVENT_PROCESS_SUMMARY_DETAILS;
		} else if ((event != null) && event.equals("create_customer_error")) {
			forwardName = EVENT_PREPARE_CREATE_CUSTOMER;
		}else if ((event != null) && event.equals("create_customer_brmaker_error")) {
			forwardName = EVENT_PREPARE_CREATE_CUSTOMER_BRMAKER;
		}else if ((event != null) && event.equals("list_customers_error")) {
			forwardName = "prepare_list_page";
		} else if ((event != null) && event.equals("save_other_system_error")) {
			forwardName = EVENT_ADD_OTHER_SYSTEM;
		} else if ((event != null)
				&& event.equals("save_other_system_in_edit_error")) {
			forwardName = EVENT_ADD_OTHER_SYSTEM;
		} else if ((event != null)
				&& event.equals("save_other_system_in_resubmit_error")) {
			forwardName = EVENT_ADD_OTHER_SYSTEM;
		} else if ((event != null) && event.equals("save_party_group_error")) {
			forwardName = EVENT_ADD_SUB_LINE;
		} else if ((event != null)
				&& event.equals("save_party_group_in_edit_error")) {
			forwardName = EVENT_ADD_SUB_LINE;
		} else if ((event != null)
				&& event.equals("save_party_group_in_resubmit_error")) {
			forwardName = EVENT_ADD_SUB_LINE;
		}else if ((event != null) && event.equals("save_vendor_error")) {
			forwardName = "add_vendor";
		} else if ((event != null)
				&& event.equals("save_vendor_in_edit_error")) {
			forwardName = "add_vendor";
		} else if ((event != null)
				&& event.equals("save_vendor_in_resubmit_error")) {
			forwardName = "add_vendor"; 
		}else if ((event != null) && event.equals("save_director_error")) {
			forwardName = EVENT_ADD_DIRECTOR;
		} else if ((event != null)
				&& event.equals("save_director_in_edit_error")) {
			forwardName = EVENT_ADD_DIRECTOR;
		} else if ((event != null)
				&& event.equals("save_director_in_resubmit_error")) {
			forwardName = EVENT_ADD_DIRECTOR;
		} else if ((event != null) && event.equals("edit_customer_error")) {
			forwardName = EVENT_PREPARE_EDIT_CUSTOMER_DETAILS;
		}  else if ((event != null) && event.equals("edit_customer_brmaker_error")) {
			forwardName = EVENT_PREPARE_EDIT_CUSTOMER_DETAILS_BRMAKER;
		}else if ((event != null)
				&& event.equals("maker_resubmit_create_customer_error")) {
			forwardName = EVENT_PROCESS_PAGE;
		} else if ((event != null)
				&& event.equals("maker_resubmit_update_customer_error")) {
			forwardName = EVENT_PROCESS_PAGE;
		} else if ((event != null)
				&& event.equals("maker_resubmit_create_customer_brmaker_error")) {
			forwardName = EVENT_PROCESS_PAGE_BRMAKER;
		} else if ((event != null)
				&& event.equals("maker_resubmit_update_customer_brmaker_error")) {
			forwardName = EVENT_PROCESS_PAGE_BRMAKER;
		} else if ((event != null) && event.equals("save_edited_system_error")) {
			forwardName = EVENT_EDIT_SYSTEM;
		} else if ((event != null)
				&& event.equals("save_edited_system_in_edit_error")) {
			forwardName = EVENT_EDIT_SYSTEM;
		} else if ((event != null)
				&& event.equals("save_edited_system_in_resubmit_error")) {
			forwardName = EVENT_EDIT_SYSTEM;
		} else if ((event != null)
				&& event.equals("save_edited_party_group_error")) {
			forwardName = EVENT_EDIT_SUBLINE_PARTY; 
		} else if ((event != null)
				&& event.equals("save_edited_party_group_in_edit_error")) {
			forwardName = EVENT_EDIT_SUBLINE_PARTY;
		} else if ((event != null)
				&& event.equals("save_edited_party_group_in_resubmit_error")) {
			forwardName = EVENT_EDIT_SUBLINE_PARTY;
		}else if ((event != null)
				&& event.equals("save_edited_vendor_error")) {
			forwardName = "edit_vendor"; 
		} else if ((event != null)
				&& event.equals("save_edited_vendor_in_edit_error")) {
			forwardName = "edit_vendor";
		} else if ((event != null)
				&& event.equals("save_edited_vendor_in_resubmit_error")) {
			forwardName = "edit_vendor";
		}  else if (event.equals("save_customer") || event.equals("save_customer_in_edit") || event.equals("save_customer_brmaker") || event.equals("save_customer_in_edit_brmaker") || EVENT_SAVE_CUSTOMER_IN_EDIT_PARTYMAKER.equals(event) || event.equals("save_customer_partymaker")) {
			forwardName = "save_customer";
		}else if (event.equals("save_customer_error")) {
			forwardName = "display_summary_details";
		}else if (event.equals("save_customer_in_edit_error")) {
			forwardName = "edit_summary_details";
		}else if (event.equals("create_draft_customer_error")) {
			forwardName = "process_summary_details";
		}else if (event.equals("create_draft_customer_brmaker_error")) {
			forwardName = "maker_save_process_brmaker";
		}else if (event.equals("update_draft_customer_error")) {
			forwardName = "process_summary_details";
		}else if (event.equals("update_draft_customer_brmaker_error")) {
			forwardName = "maker_save_process_brmaker";
		}else if (event.equals(EVENT_ADD_CRI_FAC_LIST) || event.equals(EVENT_EDIT_CRI_FAC_LIST) || event.equals(EVENT_PROCESS_CRI_FAC_LIST)
				) {
			forwardName = EVENT_ADD_CRI_FAC_LIST;
		}else if (event.equals(EVENT_SAVE_CRI_FAC_LIST)	) {
			forwardName = EVENT_DISPLAY_SUMMARY_DETAILS;
		}else if (event.equals(EVENT_REMOVE_CRI_FAC)) {
			forwardName = EVENT_DISPLAY_SUMMARY_DETAILS;
		}else if ((event != null) && event.equals("save_cri_fac_error")) {
			forwardName = EVENT_ADD_CRI_FAC_LIST;
		}else if ((event != null) && event.equals("edit_cri_facility_error")) {
			forwardName = EVENT_ADD_CRI_FAC_LIST;
		}else if ((event != null) && event.equals("process_cri_facility_error")) {
			forwardName = EVENT_ADD_CRI_FAC_LIST;
		}else if (event.equals(EVENT_EDIT_REMOVE_CRI_FAC)) {
			forwardName = EVENT_EDIT_SUMMARY_DETAILS;
		}else if (event.equals(EVENT_PROCESS_REMOVE_CRI_FAC)) {
			forwardName = EVENT_PROCESS_SUMMARY_DETAILS;
		}else if ((event != null) && event.equals("edit_cri_limit_error")) {
			forwardName = EVENT_EDIT_CRI_LIMIT_LIST;
		}else if (EVENT_REFRESH_FACILITY_LIST.equals(event)) {
			return "refresh_fac_list";
		}else if (EVENT_REFRESH_FACILITY_DETAIL_LIST.equals(event)) {
			return "refresh_fac_detail_list";
		}else if (EVENT_REFRESH_TRANCH_DETAIL_LIST.equals(event)) {
			return "refresh_tranch_detail_list";
		}else if (event.equals(EVENT_VALIDATE_PAN_NO_WITH_NSDL)){
			return "validate_pan_no_with_NSDL";
		}else if (event.equals(EVENT_VALIDATE_LEI_CODE_WITH_CCIL)){
			return "validate_lei_code_with_CCIL";
		}else if (event.equals(EVENT_VALIDATE_LEI_DETAILS_CHANGE)){
			return "validate_lei_details_change";
		}else if (event.equals(EVENT_POPULATE_RM_DATA)){
			return "populate_rm_data";
		}
		//Uma Khot:Added for Valid Rating CR
		else if (event.equals("save_customer_partymaker_error")) {
			forwardName = "display_summary_details_partymaker";
		}else if (event.equals("save_customer_in_edit_partymaker_error")) {
			forwardName = "edit_summary_details_partymaker";
		}
		else if (EVENT_CREATE_CHECKER_PROCESS_PARTYMAKER.equals(event)) {
			forwardName = EVENT_CREATE_CHECKER_PROCESS_PARTYMAKER;

		}
		else if (EVENT_DISPLAY_SUMMARY_DETAILS_PARTYMAKER.equals(event)) {
			forwardName = EVENT_DISPLAY_SUMMARY_DETAILS_PARTYMAKER;
		}
		else if (EVENT_PREPARE_CREATE_CUSTOMER_PARTYMAKER.equals(event)) {
			forwardName = EVENT_PREPARE_CREATE_CUSTOMER_PARTYMAKER;

		}else if ((event != null) && event.equals("create_customer_partymaker_error")) {
			forwardName = EVENT_PREPARE_CREATE_CUSTOMER_PARTYMAKER;
		}
		 else if ((event != null) && event.equals("edit_customer_partymaker_error")) {
				forwardName = EVENT_PREPARE_EDIT_CUSTOMER_DETAILS_PARTYMAKER;
			} 
		else if (EVENT_PREPARE_PARTY_MAKER.equals(event)) {
			forwardName = "prepare_list_page_partymaker";

		}else if (EVENT_LIST_CUSTOMERS_PARTY_MAKER.equals(event)) {
			forwardName = "list_customers_partymaker";

		}else if (EVENT_VIEW_CUSTOMERS_PARTY_MAKER.equals(event)) {
				forwardName = "view_partymaker";	
		}else if (EVENT_PREPARE_EDIT_CUSTOMER_DETAILS_PARTYMAKER.equals(event)) {
			forwardName = EVENT_PREPARE_EDIT_CUSTOMER_DETAILS_PARTYMAKER;

		} else if (event.equals(EVENT_ADD_OTHER_SYSTEM_PARTYMAKER)
				|| event.equals(EVENT_ADD_OTHER_SYSTEM_IN_EDIT_PARTYMAKER)
				|| event.equals(EVENT_ADD_OTHER_SYSTEM_IN_RESUBMIT_PARTYMAKER)) {
			forwardName = "add_other_system_partymaker";
		}
		else if (EVENT_EDIT_SUMMARY_DETAILS_PARTYMAKER.equals(event)) {
			forwardName = EVENT_EDIT_SUMMARY_DETAILS_PARTYMAKER;
		}else if (EVENT_PROCESS_SUMMARY_DETAILS_PARTYMAKER.equals(event)) {
			forwardName = EVENT_PROCESS_SUMMARY_DETAILS_PARTYMAKER;
		}
		else if ((event != null) && event.equals("save_other_system_error_partymaker")) {
			forwardName = EVENT_ADD_OTHER_SYSTEM_PARTYMAKER;
		} else if ((event != null)
				&& event.equals("save_other_system_in_edit_error_partymaker")) {
			forwardName = EVENT_ADD_OTHER_SYSTEM_PARTYMAKER;
		} else if ((event != null)
				&& event.equals("save_other_system_in_resubmit_error_partymaker")) {
			forwardName = EVENT_ADD_OTHER_SYSTEM_PARTYMAKER;
		}else if ((event != null) && event.equals("save_edited_system_error_partymaker")) {
			forwardName = EVENT_EDIT_SYSTEM_PARTYMAKER;
		} else if ((event != null)
				&& event.equals("save_edited_system_in_edit_error_partymaker")) {
			forwardName = EVENT_EDIT_SYSTEM_PARTYMAKER;
		} else if ((event != null)
				&& event.equals("save_edited_system_in_resubmit_error_partymaker")) {
			forwardName = EVENT_EDIT_SYSTEM_PARTYMAKER;
		}else if (event.equals(EVENT_EDIT_SYSTEM_PARTYMAKER)
				|| event.equals(EVENT_EDIT_SYSTEM_IN_EDIT_PARTYMAKER)
				|| event.equals(EVENT_EDIT_SYSTEM_IN_RESUBMIT_PARTYMAKER)) {
			forwardName = "edit_system_partymaker";
		} else if ((event != null) && event.equals("save_edited_system_error")) {
			forwardName = EVENT_EDIT_SYSTEM;
		}  else if ((event != null)
				&& event.equals("save_edited_system_in_resubmit_error")) {
			forwardName = EVENT_EDIT_SYSTEM;
		}else if (event.equals("save_edited_system_partymaker")) {
			forwardName = EVENT_DISPLAY_SUMMARY_DETAILS_PARTYMAKER;
		} else if (event.equals("save_edited_system_in_edit_partymaker")) {
			forwardName = EVENT_EDIT_SUMMARY_DETAILS_PARTYMAKER;
		} else if (event.equals("save_edited_system_in_resubmit_partymaker")) {
			forwardName = EVENT_PROCESS_SUMMARY_DETAILS_PARTYMAKER;
		}else if (event.equals("save_other_system_partymaker")) {
			forwardName = EVENT_DISPLAY_SUMMARY_DETAILS_PARTYMAKER;
		} else if (event.equals("save_other_system_in_edit_partymaker")) {
			forwardName = EVENT_EDIT_SUMMARY_DETAILS_PARTYMAKER;
		} else if (event.equals("save_other_system_in_resubmit_partymaker")) {
			forwardName = EVENT_PROCESS_SUMMARY_DETAILS_PARTYMAKER;
		} else if (event.equals("delete_system_partymaker")
				|| event.equals("delete_system_in_edit_partymaker")
				|| event.equals("delete_system_in_resubmit_partymaker")) {
			forwardName = "delete_system_partymaker";
		}else if (event.equals("system_deleted_partymaker")) {
			forwardName = EVENT_DISPLAY_SUMMARY_DETAILS_PARTYMAKER;
		} else if (event.equals("system_deleted_in_edit_partymaker")) {
			forwardName = EVENT_EDIT_SUMMARY_DETAILS_PARTYMAKER;
		} else if (event.equals("system_deleted_in_resubmit_partymaker")) {
			forwardName = EVENT_PROCESS_SUMMARY_DETAILS_PARTYMAKER;
		}else if ("view_customer_partymaker".equals(event)) {
				forwardName = "view_partymaker";	
		} else if ((EVENT_VIEW_SUMMARY_DETAILS_PARTYMAKER.equals(event))
				|| (EVENT_VIEW_FINANCIAL_DETAILS_PARTYMAKER.equals(event))
				|| (EVENT_VIEW_CRI_DETAILS_PARTYMAKER.equals(event))
				|| (EVENT_VIEW_CIBIL_DETAILS_PARTYMAKER.equals(event))
				|| EVENT_VIEW_UDF_DETAILS_PARTYMAKER.equals(event)
				|| EVENT_VIEW_BANKING_METHOD_DETAILS_PARTYMAKER.equals(event)) {
			forwardName = EVENT_VIEW_SUMMARY_DETAILS_PARTYMAKER;
		}else if (event.equals("save_edited_system_in_edit_partymaker")) {
			forwardName = EVENT_EDIT_SUMMARY_DETAILS_PARTYMAKER;
		} else if (EVENT_PREPARE_CLOSE_PAGE_PARTYMAKER.equals(event)
				 ||	(EVENT_MAKER_SAVE_CLOSE_PARTYMAKER.equals(event))) {
			forwardName = EVENT_PREPARE_CLOSE_PAGE_PARTYMAKER;
		}else if (EVENT_MAKER_SAVE_PROCESS_PARTYMAKER.equals(event))
				 {
			forwardName = EVENT_MAKER_SAVE_PROCESS_PARTYMAKER;

		}else if (event.equals("create_draft_customer_partymaker_error")) {
			forwardName = "maker_save_process_partymaker";
		}else if (event.equals("update_draft_customer_partymaker_error")) {
			//forwardName = "maker_save_process_partymaker";
			forwardName = "process_summary_details_partymaker";
		}else if (EVENT_PROCESS_PAGE_PARTYMAKER.equals(event)) {
			forwardName = EVENT_PROCESS_PAGE_PARTYMAKER;
		}else if ((event != null)
				&& event.equals("maker_resubmit_create_customer_partymaker_error")) {
			forwardName = EVENT_PROCESS_PAGE_PARTYMAKER;
		} else if ((event != null)
				&& event.equals("maker_resubmit_update_customer_partymaker_error")) {
			forwardName = EVENT_PROCESS_PAGE_PARTYMAKER;
		}else if ((event != null)
				&& event.equals(EVENT_MAKER_UPDATE_CUSTOMER_PARTYMAKER)) {
			forwardName = EVENT_MAKER_UPDATE_CUSTOMER_PARTYMAKER;
		}else if (event.equals("save_edited_director_in_edit_error")) {
			forwardName = EVENT_EDIT_DIRECTOR;
		}else if (event.equals("save_edited_director_in_resubmit_error")) {
			forwardName = EVENT_EDIT_DIRECTOR;
		}else if (event.equals("save_edited_director_error")) {
			forwardName = EVENT_EDIT_DIRECTOR;
		}else if (event.equals("refresh_security_type")) {
			forwardName = EVENT_REFRESH_SECURITY_TYPE;
		}

		return forwardName;
	}

}
