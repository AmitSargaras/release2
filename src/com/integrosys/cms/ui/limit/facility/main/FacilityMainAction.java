package com.integrosys.cms.ui.limit.facility.main;

import java.util.HashMap;
import java.util.Map;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.IPin;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.trx.IFacilityTrxValue;

public class FacilityMainAction extends CommonAction implements IPin {

	private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	protected ICommand[] getCommandChain(String event) {
		if (EVENT_LIST.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ReadFacilityMainCommand") };
		}
		else if (EVENT_CANCEL_FACILITY.equals(event)) {
			return null;
		}
		else if (EVENT_VIEW_EDIT.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ReadFacilityMasterCommand") };
		}
		else if (EVENT_CLOSE.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("CloseFacilityMainCommand") };
		}
		else if (EVENT_APPROVE.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ApproveFacilityMainCommand") };
		}
		else if (EVENT_REJECT.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("RejectFacilityMainCommand") };
		}		
		else if (EVENT_NAVIGATE_WO_FRAME_CHECKER.equals(event) || EVENT_NAVIGATE_VIEW_WO_FRAME.equals(event)
				|| EVENT_NAVIGATE_VIEW.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("NavigateFacilityCommand") };
		}
		else if (EVENT_NAVIGATE.equals(event) || EVENT_NAVIGATE_WO_FRAME.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("NavigateFacilityCommand"),
                    (ICommand) getNameCommandMap().get("FacilityObjectValidatorCommand")};
		}
		else if (EVENT_SAVE_WHOLE_OBJ.equals(event) || EVENT_SAVE_WHOLE_OBJ_WO_FRAME.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("SaveFacilityMainCommand") };
		}
		else if (EVENT_SUBMIT.equals(event) || EVENT_SUBMIT_WO_FRAME.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("FacilityObjectValidatorCommand"),
					(ICommand) getNameCommandMap().get("SubmitFacilityMainCommand") };
		}		

		// Unrecognized event.
		return null;
	}

	protected IPage getNextPage(String event, HashMap map, HashMap exceptionMap) {
		String forward = "";
		boolean isWip = false;
		IFacilityTrxValue facilityTrxValue = null;

		if (map != null) {
			facilityTrxValue = (IFacilityTrxValue) map.get("facilityTrxValue");
		}
		if (facilityTrxValue != null) {
			String status = facilityTrxValue.getStatus();
			isWip = ICMSConstant.STATE_DRAFT.equals(status) || ICMSConstant.STATE_PENDING_UPDATE.equals(status)
					|| ICMSConstant.STATE_REJECTED.equals(status) || ICMSConstant.STATE_PENDING_CREATE.equals(status)
					|| ICMSConstant.STATE_REJECTED_CREATE.equals(event)
					|| ICMSConstant.STATE_REJECTED_UPDATE.equals(event);
		}
		if (EVENT_LIST.equals(event)) {
			forward = EVENT_LIST;
		}
		else if (EVENT_VIEW_EDIT.equals(event)) {
			if (isWip) {
				forward = WORK_IN_PROGRESS;
			}
			else {
				forward = TAB_MASTER;
			}
		}	
		else if (EVENT_VIEW_EDIT_WO_FRAME.equals(event) || EVENT_ERROR_WO_FRAME.equals(event)) {
			forward = EVENT_VIEW_EDIT_WO_FRAME;
		}
		else if (EVENT_CLOSE.equals(event)) {
			forward = EVENT_CLOSE;
		}		
		else if (EVENT_NAVIGATE.equals(event)) {
			String nextTab = (String) map.get("nextTab");
			forward = nextTab;
			/*
			if (TAB_MASTER.equals(nextTab)) {
				forward = TAB_MASTER;
			}
			else if (TAB_BNM_CODES.equals(nextTab)) {
				forward = TAB_BNM_CODES;
			}
			else if (TAB_OFFICER.equals(nextTab)) {
				forward = TAB_OFFICER;
			}
			else if (TAB_RELATIONSHIP.equals(nextTab)) {
				forward = TAB_RELATIONSHIP;
			}
			else if (TAB_INSURANCE.equals(nextTab)) {
				forward = TAB_INSURANCE;
			}
			else if (TAB_ISLAMIC_MASTER.equals(nextTab)) {
				forward = TAB_ISLAMIC_MASTER;
			}
			else if (TAB_ISLAMIC_BBA_VARI_PACKAGE.equals(nextTab)) {
				forward = TAB_ISLAMIC_BBA_VARI_PACKAGE;
			}
			else if (TAB_MULTI_TIER_FINANCING.equals(nextTab)) {
				forward = TAB_MULTI_TIER_FINANCING;
			}
			*/
		}
		else if (EVENT_NAVIGATE_WO_FRAME.equals(event)) {
			String nextTab = (String) map.get("nextTab");
			if (TAB_ISLAMIC_RENTAL_RENEWAL.equals(nextTab) ||
				TAB_ISLAMIC_SECURITY_DEPOSIT.equals(nextTab) ||
				TAB_FACILITY_INCREMENTAL.equals(nextTab) ||
				TAB_FACILITY_REDUCTION.equals(nextTab)) {
				forward = nextTab + "_no_frame";	
			} else {
				forward = nextTab;
			}
			/*
			if (TAB_MASTER_WO_FRAME.equals(nextTab)) {
				forward = TAB_MASTER_WO_FRAME;
			}
			else if (TAB_BNM_CODES_WO_FRAME.equals(nextTab)) {
				forward = TAB_BNM_CODES_WO_FRAME;
			}
			else if (TAB_OFFICER_WO_FRAME.equals(nextTab)) {
				forward = TAB_OFFICER_WO_FRAME;
			}
			else if (TAB_RELATIONSHIP_WO_FRAME.equals(nextTab)) {
				forward = TAB_RELATIONSHIP_WO_FRAME;
			}
			else if (TAB_INSURANCE_WO_FRAME.equals(nextTab)) {
				forward = TAB_INSURANCE_WO_FRAME;
			}
			else if (TAB_ISLAMIC_MASTER_WO_FRAME.equals(nextTab)) {
				forward = TAB_ISLAMIC_MASTER_WO_FRAME;
			}
			else if (TAB_ISLAMIC_BBA_VARI_PACKAGE_WO_FRAME.equals(nextTab)) {
				forward = TAB_ISLAMIC_BBA_VARI_PACKAGE_WO_FRAME;
			}
			else if (TAB_MULTI_TIER_FINANCING_WO_FRAME.equals(nextTab)) {
				forward = TAB_MULTI_TIER_FINANCING_WO_FRAME;
			}
			*/
		}
		else if (EVENT_NAVIGATE_WO_FRAME_CHECKER.equals(event)) {
			String nextTab = (String) map.get("nextTab");
			forward = "navigate_"+nextTab;
			/*
			if (TAB_MASTER_CHECKER.equals(nextTab)) {
				forward = TAB_MASTER_CHECKER;
			}
			else if (TAB_MASTER_CHECKER_PROCESS.equals(nextTab)) {
				forward = TAB_MASTER_CHECKER_PROCESS;
			}
			else if (TAB_BNM_CODES_CHECKER.equals(nextTab)) {
				forward = TAB_BNM_CODES_CHECKER;
			}
			else if (TAB_BNM_CODES_CHECKER_PROCESS.equals(nextTab)) {
				forward = TAB_BNM_CODES_CHECKER_PROCESS;
			}
			else if (TAB_OFFICER_CHECKER.equals(nextTab)) {
				forward = TAB_OFFICER_CHECKER;
			}
			else if (TAB_OFFICER_CHECKER_PROCESS.equals(nextTab)) {
				forward = TAB_OFFICER_CHECKER_PROCESS;
			}
			else if (TAB_RELATIONSHIP_CHECKER.equals(nextTab)) {
				forward = TAB_RELATIONSHIP_CHECKER;
			}
			else if (TAB_RELATIONSHIP_CHECKER_PROCESS.equals(nextTab)) {
				forward = TAB_RELATIONSHIP_CHECKER_PROCESS;
			}
			else if (TAB_INSURANCE_CHECKER.equals(nextTab)) {
				forward = TAB_INSURANCE_CHECKER;
			}
			else if (TAB_INSURANCE_CHECKER_PROCESS.equals(nextTab)) {
				forward = TAB_INSURANCE_CHECKER_PROCESS;
			}
			else if (TAB_ISLAMIC_MASTER_CHECKER.equals(nextTab)) {
				forward = TAB_ISLAMIC_MASTER_CHECKER;
			}
			else if (TAB_ISLAMIC_MASTER_CHECKER_PROCESS.equals(nextTab)) {
				forward = TAB_ISLAMIC_MASTER_CHECKER_PROCESS;
			}
			else if (TAB_ISLAMIC_BBA_VARI_PACKAGE_CHECKER.equals(nextTab)) {
				forward = TAB_ISLAMIC_BBA_VARI_PACKAGE_CHECKER;
			}
			else if (TAB_ISLAMIC_BBA_VARI_PACKAGE_CHECKER_PROCESS.equals(nextTab)) {
				forward = TAB_ISLAMIC_BBA_VARI_PACKAGE_CHECKER_PROCESS;
			}
			else if (TAB_MULTI_TIER_FINANCING_CHECKER.equals(nextTab)) {
				forward = TAB_MULTI_TIER_FINANCING_CHECKER;
			}
			else if (TAB_MULTI_TIER_FINANCING_CHECKER_PROCESS.equals(nextTab)) {
				forward = TAB_MULTI_TIER_FINANCING_CHECKER_PROCESS;
			}
			*/
		}
		else if (EVENT_NAVIGATE_VIEW_WO_FRAME.equals(event)) {
			String nextTab = (String) map.get("nextTab");
			forward = nextTab;
			/*
			if (TAB_MASTER_VIEW_WO_FRAME.equals(nextTab)) {
				forward = TAB_MASTER_VIEW_WO_FRAME;
			}
			else if (TAB_BNM_CODES_VIEW_WO_FRAME.equals(nextTab)) {
				forward = TAB_BNM_CODES_VIEW_WO_FRAME;
			}
			else if (TAB_OFFICER_VIEW_WO_FRAME.equals(nextTab)) {
				forward = TAB_OFFICER_VIEW_WO_FRAME;
			}
			else if (TAB_RELATIONSHIP_VIEW_WO_FRAME.equals(nextTab)) {
				forward = TAB_RELATIONSHIP_VIEW_WO_FRAME;
			}
			else if (TAB_INSURANCE_VIEW_WO_FRAME.equals(nextTab)) {
				forward = TAB_INSURANCE_VIEW_WO_FRAME;
			}
			else if (TAB_ISLAMIC_MASTER_VIEW_WO_FRAME.equals(nextTab)) {
				forward = TAB_ISLAMIC_MASTER_VIEW_WO_FRAME;
			}
			else if (TAB_ISLAMIC_BBA_VARI_PACKAGE_VIEW_WO_FRAME.equals(nextTab)) {
				forward = TAB_ISLAMIC_BBA_VARI_PACKAGE_VIEW_WO_FRAME;
			}
			else if (TAB_MULTI_TIER_FINANCING_VIEW_WO_FRAME.equals(nextTab)) {
				forward = TAB_MULTI_TIER_FINANCING_VIEW_WO_FRAME;
			}
			*/
		}
		else if (EVENT_NAVIGATE_VIEW.equals(event)) {
			String nextTab = (String) map.get("nextTab");
			forward = nextTab;
			/*
			if (TAB_MASTER_VIEW.equals(nextTab)) {
				forward = TAB_MASTER_VIEW;
			}
			else if (TAB_BNM_CODES_VIEW.equals(nextTab)) {
				forward = TAB_BNM_CODES_VIEW;
			}
			else if (TAB_OFFICER_VIEW.equals(nextTab)) {
				forward = TAB_OFFICER_VIEW;
			}
			else if (TAB_RELATIONSHIP_VIEW.equals(nextTab)) {
				forward = TAB_RELATIONSHIP_VIEW;
			}
			else if (TAB_INSURANCE_VIEW.equals(nextTab)) {
				forward = TAB_INSURANCE_VIEW;
			}
			else if (TAB_ISLAMIC_MASTER_VIEW.equals(nextTab)) {
				forward = TAB_ISLAMIC_MASTER_VIEW;
			}
			else if (TAB_ISLAMIC_BBA_VARI_PACKAGE_VIEW.equals(nextTab)) {
				forward = TAB_ISLAMIC_BBA_VARI_PACKAGE_VIEW;
			}
			else if (TAB_MULTI_TIER_FINANCING_VIEW.equals(nextTab)) {
				forward = TAB_MULTI_TIER_FINANCING_VIEW;
			}
			*/
		}
		else if (EVENT_SAVE_WHOLE_OBJ.equals(event) || EVENT_SAVE_WHOLE_OBJ_WO_FRAME.equals(event)) {
			forward = EVENT_SAVE_WHOLE_OBJ;
		}
		else if (EVENT_SUBMIT.equals(event) || EVENT_SUBMIT_WO_FRAME.equals(event)) {
			boolean isError = false;
			if (map.get("isError") != null) {
				isError = ((Boolean) map.get("isError")).booleanValue();
			}
			if (isError) {
				forward = (String) map.get("currentTab");
			}
			else {
				forward = EVENT_SUBMIT;
			}
		}
		else if (EVENT_APPROVE.equals(event)) {
			forward = EVENT_APPROVE;
		}
		else if (EVENT_REJECT.equals(event)) {
			forward = EVENT_REJECT;
		}

		DefaultLogger.debug(this, "The name of struts forward is " + forward);

		Page page = new Page();
		page.setPageReference(forward);

		return page;
	}
	
	protected boolean isValidationRequired(String event) {
		return (EVENT_NAVIGATE.equals(event) || EVENT_NAVIGATE_WO_FRAME.equals(event)
				|| EVENT_SAVE_WHOLE_OBJ.equals(event) || EVENT_SAVE_WHOLE_OBJ_WO_FRAME.equals(event)
				|| EVENT_SUBMIT.equals(event) || EVENT_SUBMIT_WO_FRAME.equals(event));
	}	

	protected String getErrorEvent(String event) {
		if (EVENT_NAVIGATE.equals(event) || EVENT_SAVE_WHOLE_OBJ.equals(event) || EVENT_SUBMIT.equals(event)) {
			return EVENT_ERROR;
		}
		else if (EVENT_NAVIGATE_WO_FRAME.equals(event) || EVENT_SAVE_WHOLE_OBJ_WO_FRAME.equals(event)
				|| EVENT_SUBMIT_WO_FRAME.equals(event)) {
			return EVENT_ERROR_WO_FRAME;
		}
		return null;
	}	
	
	public static final String EVENT_VIEW_EDIT = "view_edit";

	public static final String EVENT_CANCEL_FACILITY = "cancel_facility";

	public static final String EVENT_SAVE = "save";

	public static final String EVENT_UPDATE = "update";

	public static final String EVENT_NAVIGATE = "navigate";

	public static final String EVENT_ADD = "add";

	public static final String EVENT_EDIT = "edit";

	// public static final String TAB_MAIN = "main";

	public static final String TAB_MASTER = "master";

	public static final String TAB_BNM_CODES = "bnm_codes";

	public static final String TAB_OFFICER = "officer";

	public static final String TAB_RELATIONSHIP = "relationship";

	public static final String TAB_INSURANCE = "insurance";

	public static final String WORK_IN_PROGRESS = "work_in_progress";

	public static final String EVENT_SAVE_WHOLE_OBJ = "save_whole_obj";

	public static final String EVENT_DELETE = "delete";

	public static final String EVENT_SUBMIT = "submit";

	public static final String INPUT = "input";
	
	public static final String EVENT_VIEW = "view";

	public static final String EVENT_ERROR = "error";

	public static final String EVENT_PREPARE_CLOSE = "prepare_close";

	public static final String TAB_MASTER_WO_FRAME = "master_no_frame";

	public static final String TAB_BNM_CODES_WO_FRAME = "bnm_codes_no_frame";

	public static final String TAB_OFFICER_WO_FRAME = "officer_no_frame";

	public static final String TAB_RELATIONSHIP_WO_FRAME = "relationship_no_frame";

	public static final String TAB_INSURANCE_WO_FRAME = "insurance_no_frame";

	public static final String EVENT_VIEW_EDIT_WO_FRAME = "view_edit_no_frame";

	public static final String EVENT_NAVIGATE_WO_FRAME = "navigate_no_frame";

	public static final String EVENT_NAVIGATE_WO_FRAME_CHECKER = "navigate_no_frame_checker";

	public static final String EVENT_SAVE_WHOLE_OBJ_WO_FRAME = "save_whole_obj_no_frame";

	public static final String EVENT_ERROR_WO_FRAME = "error_no_frame";

	public static final String EVENT_LIST_WO_FRAME = "list_no_frame";

	public static final String EVENT_EDIT_WO_FRAME = "edit_no_frame";

	public static final String EVENT_DELETE_WO_FRAME = "delete_no_frame";

	public static final String INPUT_WO_FRAME = "input_no_frame";

	public static final String EVENT_ADD_WO_FRAME = "add_no_frame";

	public static final String EVENT_SAVE_WO_FRAME = "save_no_frame";

	public static final String EVENT_UPDATE_WO_FRAME = "update_no_frame";

	public static final String EVENT_CLOSE = "close";

	public static final String EVENT_MASTER_CHECKER_READ = "master_checker";
	
	public static final String EVENT_MASTER_CHECKER_PROCESS_READ = "master_checker_process";

	public static final String EVENT_SUBMIT_WO_FRAME = "submit_no_frame";

	public static final String TAB_MASTER_CHECKER = "master_checker";
	
	public static final String TAB_MASTER_CHECKER_PROCESS = "master_checker_process";

	public static final String TAB_BNM_CODES_CHECKER = "bnm_codes_checker";
	
	public static final String TAB_BNM_CODES_CHECKER_PROCESS = "bnm_codes_checker_process";

	public static final String TAB_OFFICER_CHECKER = "officer_checker";
	
	public static final String TAB_OFFICER_CHECKER_PROCESS = "officer_checker_process";

	public static final String TAB_RELATIONSHIP_CHECKER = "relationship_checker";
	
	public static final String TAB_RELATIONSHIP_CHECKER_PROCESS = "relationship_checker_process";

	public static final String TAB_INSURANCE_CHECKER = "insurance_checker";
	
	public static final String TAB_INSURANCE_CHECKER_PROCESS = "insurance_checker_process";

	public static final String EVENT_APPROVE = "approve";

	public static final String EVENT_REJECT = "reject";

	public static final String EVENT_VIEW_DETAIL_CHECKER = "view_detail_checker";

	public static final int RECORD_PER_PAGE = 10;

	public static final String EVENT_VIEW_WO_FRAME = "view_no_frame";

	public static final String TAB_MASTER_VIEW_WO_FRAME = "master_view_no_frame";

	public static final String TAB_BNM_CODES_VIEW_WO_FRAME = "bnm_codes_view_no_frame";

	public static final String TAB_OFFICER_VIEW_WO_FRAME = "officer_view_no_frame";

	public static final String TAB_RELATIONSHIP_VIEW_WO_FRAME = "relationship_view_no_frame";

	public static final String TAB_INSURANCE_VIEW_WO_FRAME = "insurance_view_no_frame";

	public static final String EVENT_NAVIGATE_VIEW_WO_FRAME = "navigate_view_no_frame";

	public static final String EVENT_VIEW_DETAIL_ONLY = "view_detail_only";
	
	public static final String EVENT_VIEW_DETAIL_CHECKER_PROCESS = "view_detail_checker_process";

	public static final String EVENT_SEARCH_CUSTOMER = "search_customer";

	public static final String EVENT_SEARCH_CUSTOMER_WO_FRAME = "search_customer_no_frame";

	public static final String EVENT_PREPARE_SEARCH_CUSTOMER = "prepare_search_customer";

	public static final String EVENT_PREPARE_SEARCH_CUSTOMER_WO_FRAME = "prepare_search_customer_no_frame";

	public static final String SEARCH_BY_NAME = "1";

	public static final String SEARCH_BY_NAME_WO_FRAME = "4";

	public static final String SEARCH_BY_LEGAL_ID = "2";

	public static final String SEARCH_BY_LEGAL_ID_WO_FRAME = "5";

	public static final String SEARCH_BY_ID_NUMBER = "3";

	public static final String SEARCH_BY_ID_NUMBER_WO_FRAME = "6";

	public static final String TAB_MASTER_VIEW = "master_view";

	public static final String TAB_BNM_CODES_VIEW = "bnm_codes_view";

	public static final String TAB_OFFICER_VIEW = "officer_view";

	public static final String TAB_RELATIONSHIP_VIEW = "relationship_view";

	public static final String TAB_INSURANCE_VIEW = "insurance_view";

	public static final String EVENT_NAVIGATE_VIEW = "navigate_view";

	public static final String EVENT_VIEW_DETAIL_ONLY_WITH_FRAME = "view_detail_only_w_frame";
	
	public static final String TAB_ISLAMIC_MASTER ="islamic_master";
	
	public static final String TAB_ISLAMIC_MASTER_WO_FRAME = "islamic_master_no_frame";
	
	public static final String TAB_ISLAMIC_MASTER_VIEW = "islamic_master_view";
	
	public static final String TAB_ISLAMIC_MASTER_VIEW_WO_FRAME = "islamic_master_view_no_frame";
	
	public static final String TAB_ISLAMIC_MASTER_CHECKER = "islamic_master_checker";
	
	public static final String TAB_ISLAMIC_MASTER_CHECKER_PROCESS = "islamic_master_checker_process";
	
	public static final String TAB_ISLAMIC_BBA_VARI_PACKAGE = "islamic_bba";
	
	public static final String TAB_ISLAMIC_BBA_VARI_PACKAGE_WO_FRAME = "islamic_bba_no_frame";
	
	public static final String TAB_ISLAMIC_BBA_VARI_PACKAGE_VIEW = "islamic_bba_view";
	
	public static final String TAB_ISLAMIC_BBA_VARI_PACKAGE_VIEW_WO_FRAME = "islamic_bba_view_no_frame";
	
	public static final String TAB_ISLAMIC_BBA_VARI_PACKAGE_CHECKER = "islamic_bba_checker";
	
	public static final String TAB_ISLAMIC_BBA_VARI_PACKAGE_CHECKER_PROCESS = "islamic_bba_checker_process";
	
	public static final String TAB_MULTI_TIER_FINANCING = "multi_tier_financing";
	
	public static final String TAB_MULTI_TIER_FINANCING_WO_FRAME= "multi_tier_financing_no_frame";
	
	public static final String TAB_MULTI_TIER_FINANCING_VIEW = "multi_tier_financing_view";
	
	public static final String TAB_MULTI_TIER_FINANCING_VIEW_WO_FRAME = "multi_tier_financing_view_no_frame";
	
	public static final String TAB_MULTI_TIER_FINANCING_CHECKER = "multi_tier_financing_checker";
	
	public static final String TAB_MULTI_TIER_FINANCING_CHECKER_PROCESS = "multi_tier_financing_checker_process";
	
	public static final String TAB_FACILITY_MESSAGE = "facility_message";
	
	public static final String TAB_ISLAMIC_RENTAL_RENEWAL = "islamic_rental_renewal";
	
	public static final String TAB_ISLAMIC_RENTAL_RENEWAL_VIEW = "islamic_rental_renewal_view";
	
	public static final String TAB_ISLAMIC_RENTAL_RENEWAL_VIEW_WO_FRAME = "islamic_rental_renewal_view_no_frame";
	
	public static final String TAB_ISLAMIC_RENTAL_RENEWAL_CHECKER = "islamic_rental_renewal_checker";
	
	public static final String TAB_ISLAMIC_RENTAL_RENEWAL_CHECKER_PROCESS = "islamic_rental_renewal_checker_process";
	
	public static final String TAB_ISLAMIC_SECURITY_DEPOSIT = "islamic_security_deposit";
	
	public static final String TAB_ISLAMIC_SECURITY_DEPOSIT_VIEW = "islamic_security_deposit_view";
	
	public static final String TAB_ISLAMIC_SECURITY_DEPOSIT_VIEW_WO_FRAME = "islamic_security_deposit_view_no_frame";
	
	public static final String TAB_ISLAMIC_SECURITY_DEPOSIT_CHECKER = "islamic_security_deposit_checker";
	
	public static final String TAB_ISLAMIC_SECURITY_DEPOSIT_CHECKER_PROCESS = "islamic_security_deposit_checker_process";
	
	public static final String TAB_FACILITY_INCREMENTAL = "incremental";
	
	public static final String TAB_FACILITY_INCREMENTAL_VIEW = "incremental_view";
	
	public static final String TAB_FACILITY_INCREMENTAL_VIEW_WO_FRAME = "incremental_view_no_frame";
	
	public static final String TAB_FACILITY_INCREMENTAL_CHECKER = "incremental_checker";
	
	public static final String TAB_FACILITY_INCREMENTAL_CHECKER_PROCESS = "incremental_checker_process";
	
	public static final String TAB_FACILITY_REDUCTION = "reduction";
	
	public static final String TAB_FACILITY_REDUCTION_VIEW = "reduction_view";
	
	public static final String TAB_FACILITY_REDUCTION_VIEW_WO_FRAME = "reduction_view_no_frame";
	
	public static final String TAB_FACILITY_REDUCTION_CHECKER = "reduction_checker";
		
	public static final String TAB_FACILITY_REDUCTION_CHECKER_PROCESS = "reduction_checker_process";
}


