package com.integrosys.cms.ui.valuationAgency;

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
 * @author rajib.aich R$ Action For Valuation Agency
 */
public class ValuationAgencyAction extends CommonAction {

	private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	public static final String EVENT_MAKER_LIST_VALUATION = "maker_list_valuation";
	public static final String EVENT_CHECKER_LIST_VALUATION = "checker_list_valuation";
	public static final String EVENT_PREPARE = "prepare";
	public static final String EVENT_SAVE = "save";
	public static final String EVENT_SAVE_NOOP = "saveNoop";
	public static final String EVENT_CANCEL = "cancel";

	public static final String MAKER_CREATE_VALUATION_AGENCY = "maker_create_valuation_agency";

	public static final String MAKER_ADD_VALUATION_AGENCY = "maker_submit_valuationAgency";

	public static final String REJECTED_DELETE_READ = "rejected_delete_read";

	public static final String CHECKER_REJECT_EDIT = "checker_reject_edit";

	public static final String MAKER_PREPARE_CLOSE = "maker_prepare_close";

	public static final String MAKER_CONFIRM_CLOSE = "maker_confirm_close";

	public static final String MAKER_PREPARE_RESUBMIT = "maker_prepare_resubmit_create";

	public static final String MAKER_CONFIRM_RESUBMIT_EDIT = "maker_confirm_resubmit_edit";

	public static final String MAKER_VIEW_VALUATION_AGENCY = "maker_view_valuationAgency";
	public static final String CHECKER_VIEW_VALUATION_AGENCY = "checker_view_valuationAgency";

	public static final String MAKER_DELETE_VALUATION_AGENCY_READ = "maker_delete_valuationAgency_read";

	public static final String MAKER_EDIT_VALUATION_AGENCY_READ = "maker_edit_valuationAgency_read";

	public static final String MAKER_EDIT_VALUATION_AGENCY = "maker_edit_valuationAgency";

	public static final String CHECKER_PROCESS_CREATE = "checker_process_create";

	public static final String CHECKER_PROCESS_EDIT = "checker_process_edit";

	public static final String MAKER_DISABLE_VALUATION_AGENCY_READ = "maker_disable_valuationAgency_read";

	public static final String MAKER_ENABLE_VALUATION_AGENCY_READ = "maker_enable_valuationAgency_read";

	public static final String MAKER_DISABLE_VALUATION_AGENCY = "maker_disable_valuationAgency";

	public static final String MAKER_ENABLE_VALUATION_AGENCY = "maker_enable_valuationAgency";

	public static final String CHECKER_PROCESS_DISABLE = "checker_process_disable";

	public static final String CHECKER_PROCESS_ENABLE = "checker_process_enable";

	public static final String CHECKER_APPROVE_EDIT = "checker_approve_edit";

	public static final String MAKER_PREPARE_RESUBMIT_ENABLE = "maker_prepare_resubmit_enable";

	public static final String MAKER_PREPARE_RESUBMIT_DISABLE = "maker_prepare_resubmit_disable";

	public static final String MAKER_CONFIRM_RESUBMIT_DISABLE = "maker_confirm_resubmit_disable";

	public static final String MAKER_CONFIRM_RESUBMIT_ENABLE = "maker_confirm_resubmit_enable";

	public static final String MAKER_DRAFT_VALUATION_AGENCY = "maker_draft_valuationAgency";

	public static final String MAKER_DRAFT_CLOSE_PROCESS = "maker_draft_close_process";
	public static final String MAKER_CONFIRM_DRAFT_CLOSE = "maker_confirm_draft_close";

	public static final String MAKER_UPDATE_SAVE_PROCESS = "maker_update_save_process";

	public static final String MAKER_SAVE_UPDATE = "maker_save_update";

	public static final String MAKER_SAVE_CREATE = "maker_save_create";

	public static final String MAKER_UPDATE_DRAFT_VALUATION_AGENCY = "maker_update_draft_valuationAgency";

	public static final String MAKER_PREPARE_RESUBMIT_DELETE = "maker_prepare_resubmit_delete";

	public static final String LIST_PAGINATION = "list_pagination";
	
	public static final String LIST_CHECKER_PAGINATION = "checker_list_pagination";

	public static final String EVENT_REFRESH_REGION_ID = "refresh_region_id";

	public static final String EVENT_REFRESH_STATE_ID = "refresh_state_id";

	public static final String EVENT_REFRESH_CITY_ID = "refresh_city_id";

	public static final String EVENT_CHECKER_VIEW_VALUATION_AGENCY = "checker_view_valuationAgency";

	// **************************************UPLOAD**********************************

	public static final String MAKER_PREPARE_UPLOAD_VALUATION_AGENCY = "maker_prepare_upload_valuation_agency";

	public static final String MAKER_EVENT_UPLOAD_VALUATION_AGENCY = "maker_event_upload_valuation_agency";

	public static final String CHECKER_PROCESS_CREATE_INSERT = "checker_process_create_insert";

	public static final String CHECKER_APPROVE_INSERT = "checker_approve_insert";

	public static final String CHECKER_REJECT_INSERT = "checker_reject_insert";

	public static final String EVENT_MAKER_REJECTED_DELETE_READ = "maker_rejected_delete_read";

	public static final String MAKER_PREPARE_INSERT_CLOSE = "maker_prepare_insert_close";

	public static final String MAKER_CONFIRM_INSERT_CLOSE = "maker_confirm_insert_close";

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event
	 *            is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		System.out
				.println("In Maintain ValuationAgencyAction Action with event "
						+ event);

		ICommand objArray[] = null;
		if (EVENT_MAKER_LIST_VALUATION.equals(event)
				|| EVENT_CHECKER_LIST_VALUATION.equals(event)) {

			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"ListValuationAgencyCommand");

		} else if (EVENT_SAVE.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get(
					"SaveExchangeRateItemCommand") };
		} else if (EVENT_SAVE_NOOP.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get(
					"PrepareExchangeRateItemCommand") };
		} else if (EVENT_CANCEL.equals(event)) {
			return null;
		} else if (MAKER_CREATE_VALUATION_AGENCY.equals(event)) {

			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCreateValuationAgencyCmd");

		} else if ((event != null) && event.equals(MAKER_ADD_VALUATION_AGENCY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerSubmitValuationAgencyCmd");

		}

		else if (REJECTED_DELETE_READ.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CreateReadValuationAgencyCmd");
		}

		else if ((event != null) && event.equals(CHECKER_APPROVE_EDIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerApproveEditValuationAgencyCmd");
		}

		else if (event.equals(CHECKER_REJECT_EDIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerRejectEditValuationAgencyCmd");
		}

		else if (event.equals(MAKER_VIEW_VALUATION_AGENCY)
				|| event.equals(EVENT_CHECKER_VIEW_VALUATION_AGENCY)) {

			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerReadValuationAgencyCmd");

		}

		else if ((event != null)
				&& event.equals(MAKER_EDIT_VALUATION_AGENCY_READ)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerReadValuationAgencyCmd");
		}

		else if ((event.equals(MAKER_CONFIRM_RESUBMIT_EDIT))
				|| event.equals(MAKER_EDIT_VALUATION_AGENCY)) {

			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerEditValuationAgencyCmd");
		} else if (event.equals(CHECKER_PROCESS_CREATE)
				|| event.equals(CHECKER_PROCESS_EDIT)
				|| event.equals(MAKER_UPDATE_SAVE_PROCESS)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadValuationAgencyCmd");
		}

		else if ((event.equals(MAKER_ENABLE_VALUATION_AGENCY_READ))
				|| event.equals(MAKER_DISABLE_VALUATION_AGENCY_READ)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerReadValuationAgencyCmd");

		}

		else if ((event.equals(MAKER_CONFIRM_RESUBMIT_DISABLE))
				|| event.equals(MAKER_DISABLE_VALUATION_AGENCY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerDisableValuationAgencyCmd");

		} else if ((event.equals(MAKER_CONFIRM_RESUBMIT_ENABLE))
				|| event.equals(MAKER_ENABLE_VALUATION_AGENCY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerEnableValuationAgencyCmd");
		}

		else if (event.equals(CHECKER_PROCESS_DISABLE)
				|| event.equals(CHECKER_PROCESS_ENABLE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadValuationAgencyCmd");
		} else if ((event.equals(MAKER_PREPARE_CLOSE))
				|| event.equals(MAKER_PREPARE_RESUBMIT)
				|| event.equals(MAKER_PREPARE_RESUBMIT_DELETE)
				|| event.equals(MAKER_PREPARE_RESUBMIT_ENABLE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadValuationAgencyCmd");
		}

		else if (event.equals(MAKER_PREPARE_RESUBMIT_DISABLE)
				|| event.equals(MAKER_PREPARE_RESUBMIT_ENABLE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadValuationAgencyCmd");
		}

		else if (event.equals(MAKER_DRAFT_VALUATION_AGENCY)
				|| event.equals(MAKER_UPDATE_DRAFT_VALUATION_AGENCY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerSaveValuationAgencyCmd");

		}

		else if ((event.equals(MAKER_PREPARE_CLOSE))
				|| event.equals(MAKER_PREPARE_RESUBMIT)
				|| event.equals(MAKER_DRAFT_CLOSE_PROCESS)) {

			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadValuationAgencyCmd");
		}

		else if ((event != null)
				&& (event.equals(MAKER_CONFIRM_CLOSE) || event
						.equals(MAKER_CONFIRM_DRAFT_CLOSE))) {

			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCloseValuationAgencyCmd");
		}

		else if (event.equals(MAKER_SAVE_UPDATE)
				|| event.equals(MAKER_SAVE_CREATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerEditValuationAgencyCmd");

		}

		else if (event.equals(LIST_PAGINATION) ||event.equals(LIST_CHECKER_PAGINATION) ) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("PaginationCmd");

		}

		else if (event.equals(EVENT_REFRESH_REGION_ID)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"RefreshCountryCommmand");
		} else if (event.equals(EVENT_REFRESH_STATE_ID)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"RefreshCityCommmand");
		}

		else if (event.equals(EVENT_REFRESH_CITY_ID)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"RefreshCityTownCommand");
		}

		// **********************UPLOAD********************************

		else if (event.equals(MAKER_PREPARE_UPLOAD_VALUATION_AGENCY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareUploadValuationAgencyCmd");

		}

		else if (event.equals(MAKER_EVENT_UPLOAD_VALUATION_AGENCY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerUploadValuationAgencyCmd");

		}

		else if ((event != null) && event.equals(CHECKER_PROCESS_CREATE_INSERT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadFileInsertListCmd");
		}

		else if ((event != null) && event.equals(CHECKER_APPROVE_INSERT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand)

			getNameCommandMap().get("CheckerApproveInsertValuationAgencyCmd");
		}

		else if (event.equals(CHECKER_REJECT_INSERT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerRejectInsertValuationAgencyCmd");
		}

		else if (event.equals(EVENT_MAKER_REJECTED_DELETE_READ)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadFileInsertListCmd");

		} else if ((event != null) && event.equals(MAKER_PREPARE_INSERT_CLOSE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadFileInsertListCmd");
		}

		else if ((event != null) && (event.equals(MAKER_CONFIRM_INSERT_CLOSE))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerInsertCloseValuationAgencyCmd");
		}
		
		
		return (objArray);

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
		String forward = null;

		if ((resultMap.get("wip") != null)
				&& (resultMap.get("wip")).equals("wip")) {
			forward = "work_in_process_page";

		} else if ((resultMap.get("errorEveList") != null) && ((String)

		resultMap.get("errorEveList")).equals("errorEveList")) {

			aPage.setPageReference("maker_fileupload_valuation_agency_page");
			return aPage;
		}

		else if (EVENT_PREPARE.equals(event)) {
			forward = "prepare";
		} else if (EVENT_SAVE.equals(event)) {
			if (exceptionMap.isEmpty()) {
				forward = "save";
			} else {
				forward = "prepare";
			}
		} else if (EVENT_SAVE_NOOP.equals(event)) {
			forward = "prepare";
		} else if (EVENT_CANCEL.equals(event)) {
			forward = "save";
		} else if (EVENT_MAKER_LIST_VALUATION.equals(event)) {
			forward = EVENT_MAKER_LIST_VALUATION;
		} else if (MAKER_CREATE_VALUATION_AGENCY.equals(event)) {
			forward = MAKER_CREATE_VALUATION_AGENCY;
		} else if ("maker_submit_valuationAgency".equals(event)) {
			forward = "common_submit_page";
		}

		else if ("rejected_delete_read".equals(event)) {
			forward = "maker_view_todo_page";
		} else if ((event != null) && event.equals("checker_approve_edit")) {
			forward = "common_approve_page";
		} else if ((event != null)
				&& (event.equals("maker_prepare_close") || event
						.equals("maker_draft_close_process")))

		{
			forward = "maker_prepare_close";
		}

		else if ((event != null) && (event.equals("maker_confirm_close"))
				|| event.equals(MAKER_CONFIRM_DRAFT_CLOSE)) {
			forward = "common_close_page";
		} else if ((event != null)
				&& event.equals("maker_prepare_resubmit_create")) {
			forward = "maker_prepare_resubmit";
		}

		else if (event.equals("maker_confirm_resubmit_edit")
				|| event.equals("maker_edit_valuationAgency")
				|| event.equals("maker_draft_valuationAgency")
				|| event.equals("maker_update_draft_valuationAgency")
				|| event.equals("maker_save_create")
				|| event.equals("maker_event_upload_valuation_agency")
				|| event.equals("maker_save_update")) {
			forward = "common_submit_page";
		}

		else if ((event != null) && event.equals("rejected_delete_read")) {
			forward = "maker_view_todo_page";
		}

		else if ((event != null)
				&& event.equals("maker_edit_valuationAgency_read")) {
			forward = "maker_add_edit_valuationAgency_page";
		}

		else if (event.equals("maker_view_valuationAgency") ||

		event.equals("checker_view_valuationAgency")) {

			forward = "maker_view_page";
		} else if (event.equals("maker_view_valuationAgency")) {

			forward = "checker_view_page";
		}

		else if ((event != null)
				&& event.equals("maker_edit_valuationAgency_read")) {
			forward = "maker_add_edit_valuationAgency_page";
		}

		else if ("checker_process_create".equals(event)
				|| event.equals("checker_process_edit")) {

			forward = "checker_valuationAgency_page";
		}

		else if ((event != null)
				&& event.equals("maker_disable_valuationAgency_read")) {
			forward = "maker_view_disable_page";
		}

		else if ((event != null)
				&& event.equals("maker_enable_valuationAgency_read")) {
			forward = "maker_view_enable_page";
		}

		else if ((event != null)
				&& event.equals("maker_disable_valuationAgency")) {
			forward = "common_submit_page";
		}

		else if ((event != null)
				&& event.equals("maker_enable_valuationAgency")) {
			forward = "common_submit_page";
		}

		else if ((event.equals("maker_confirm_resubmit_edit"))
				|| event.equals("maker_edit_directorMaster")
				|| event.equals("maker_confirm_resubmit_disable")
				|| event.equals("maker_create_directorMaster")
				|| event.equals("maker_confirm_resubmit_create")
				|| event.equals("maker_draft_directorMaster")
				|| event.equals("maker_update_draft_directorMaster")
				|| event.equals("maker_update_draft_directorMaster")
				|| event.equals("maker_confirm_resubmit_enable")) {
			forward = "common_submit_page";
		}

		else if ((event != null) && (event.equals("checker_process_disable"))) {
			forward = "checker_disable_valuationAgency_page";
		}

		else if ((event != null) && (event.equals("checker_process_enable"))) {
			forward = "checker_enable_valuationAgency_page";
		}

		else if ((event != null)
				&& event.equals("maker_prepare_resubmit_disable")) {
			forward = "maker_prepare_resubmit_disable";
		} else if (event.equals("maker_prepare_resubmit_enable")
				|| event.equals("maker_prepare_resubmit_delete")) {
			forward = "maker_prepare_resubmit_enable";
		}

		else if ((event != null)
				&& (event.equals("maker_save_process") || event
						.equals("maker_update_save_process"))) {
			forward = "maker_view_save_page";
		} else if ((event != null)
				&& event.equals("maker_submit_valuationAgency_error")) {
			forward = "maker_submit_valuationAgencyaError";
		}

		else if ((event != null)
				&& event.equals("maker_edit_valuationAgency_error")) {
			forward = "maker_edit_valuationAgencyError";
		}

		else if ((event != null)
				&& event.equals("maker_confirm_resubmit_enable_error")) {
			forward = "maker_confirm_resubmit_enableError";
		} else if ((event != null)
				&& event.equals("maker_confirm_resubmit_disable_error")) {
			forward = "maker_confirm_resubmit_disableError";
		}

		else if ((event != null)
				&& event.equals("maker_confirm_resubmit_edit_error")) {
			forward = "maker_confirm_resubmit_editError";
		}

		else if ((event != null) && event.equals("maker_save_update_error")) {
			forward = "maker_save_updateError";
		}

		else if ((event != null) && event.equals("maker_save_create_error")) {
			forward = "maker_save_createError";
		} else if ((event != null) && event.equals("checker_reject_edit")) {
			forward = "common_reject_page";
		} else if ((event != null) && event.equals("checker_reject_edit_error")) {
			forward = "checker_reject_editError";
		} else if (event.equals("list_pagination")) {
			forward = "maker_list_valuationAgency_page";
		} else if (event.equals("list_pagination")) {
			forward = "checker_list_valuationAgency_page";
		}

		else if (event.equals("refresh_region_id")) {
			forward = "refresh_region_id";
		}

		else if (event.equals("refresh_state_id")) {
			forward = "refresh_state_id";
		}

		else if (event.equals("refresh_city_id")) {
			forward = "refresh_city_id";
		}

		// **********************UPLOAD********************************

		else if ((event != null) &&

		event.equals("maker_prepare_upload_valuation_agency")) {
			forward = "maker_prepare_upload_valuation_agency_page";
		}

		else if (event.equals("checker_process_create_insert")) {
			forward = "checker_valuation_agency_insert_page";
		}

		else if ((event != null) && event.equals("checker_approve_insert")) {
			forward = "common_submit_page";
		}

		else if ((event != null) && event.equals("checker_reject_insert")) {
			forward = "common_reject_page";
		}

		else if ((event != null) &&

		event.equals(EVENT_MAKER_REJECTED_DELETE_READ)) {
			forward = EVENT_MAKER_REJECTED_DELETE_READ;
		}

		else if ((event != null) && (event.equals(MAKER_PREPARE_INSERT_CLOSE))) {
			forward = "maker_prepare_insert_close";
		}

		else if ((event != null)
				&& (event.equals("maker_confirm_insert_close"))) {
			forward = "common_submit_page";
		} else if ((event != null)
				&& (event.equals("checker_reject_insert_error"))) {
			forward = "checker_reject_insert_error_page";
		}
		else if ((event != null)
				&& event.equals("maker_update_draft_valuationAgency_error")) {
			forward = "maker_update_draft_valuationAgencyError";
		}
		else if ((event != null) &&

		(event.equals("maker_event_upload_valuation_agency_error"))) {
			forward = "maker_event_upload_valuation_agency_error_page";
		} else if (EVENT_CHECKER_LIST_VALUATION.equals(event) || event.equals("checker_list_pagination") )
			forward = EVENT_CHECKER_LIST_VALUATION;

		else {
			aPage.setPageReference(getReference(event));
			return aPage;
		}

		DefaultLogger.debug(this, "the forward is " + forward);

		// Page page = new Page();
		aPage.setPageReference(forward);
		return aPage;
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
		return ValuationAgencyValidator.validateInput(aForm, locale);
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if (event.equals("maker_submit_valuationAgency")||event.equals("maker_draft_valuationAgency")) {
			errorEvent = "maker_submit_valuationAgency_error";
		} else if (event.equals("maker_edit_valuationAgency")) {
			errorEvent = "maker_edit_valuationAgency_error";
		} else if (event.equals("maker_confirm_resubmit_enable")) {
			errorEvent = "maker_confirm_resubmit_enable_error";
		} else if (event.equals("maker_confirm_resubmit_disable")) {
			errorEvent = "maker_confirm_resubmit_disable_error";
		}

		else if (event.equals("maker_confirm_resubmit_edit")) {
			errorEvent = "maker_confirm_resubmit_edit_error";
		}

		else if (event.equals("maker_save_update")) {
			errorEvent = "maker_save_update_error";
		}

		else if (event.equals("maker_save_create")) {
			errorEvent = "maker_save_create_error";
		} else if (event.equals("checker_reject_edit")) {
			errorEvent = "checker_reject_edit_error";
		}

		// **********************UPLOAD********************************

		else if (event.equals("checker_reject_insert")) {
			errorEvent = CHECKER_PROCESS_CREATE_INSERT;
		}

		else if (event.equals("maker_event_upload_valuation_agency")) {
			errorEvent = "maker_event_upload_valuation_agency_error";
		}
		else if (event.equals("maker_list_valuation")) {
			errorEvent = "maker_list_valuation";
		}
		else if (event.equals("checker_list_valuation")) {
			errorEvent = "checker_list_valuation";
		}
		
		else if (event.equals("maker_update_draft_valuationAgency")) {
			errorEvent = "maker_update_draft_valuationAgency_error";
		}

		return errorEvent;
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals(MAKER_ADD_VALUATION_AGENCY)
				|| event.equals(MAKER_EDIT_VALUATION_AGENCY)
				|| event.equals(MAKER_CONFIRM_RESUBMIT_ENABLE)
				|| event.equals(MAKER_CONFIRM_RESUBMIT_DISABLE)
				|| event.equals(MAKER_CONFIRM_RESUBMIT_EDIT)
				|| event.equals(MAKER_SAVE_UPDATE)
				||event.equals(MAKER_UPDATE_DRAFT_VALUATION_AGENCY)
				|| event.equals(MAKER_SAVE_CREATE)
				|| event.equals(MAKER_DRAFT_VALUATION_AGENCY)
				|| event.equals("maker_list_valuation")
				|| event.equals("checker_list_valuation")
		)

		{
			result = true;
		}
		return result;
	}

	private String getReference(String event) {
		String forwardName = null;
		if ((event != null) && event.equals("maker_create_valuation_agency")) {
			forwardName = "maker_create_valuation_agency";
		}else if (event.equals("maker_list_valuation")) {
			forwardName = "maker_list_valuationAgency_page";
		}
		else if (event.equals("checker_list_valuation")) {
			forwardName = "checker_list_valuation_page";
		}
		else if ((event != null)
				&& event.equals("maker_submit_valuationAgency")) {
			forwardName = "common_submit_page";
		} else if ((event != null) && event.equals("rejected_delete_read")) {
			forwardName = "maker_view_todo_page";
		} else if ((event != null) && event.equals("checker_process_create")) {
			forwardName = "checker_valuationAgency_page";
		} else if ((event != null) && event.equals("checker_approve_edit")) {
			forwardName = "common_approve_page";
		} else if ((event != null)
				&& event.equals("checker_approve_edit_error")) {
			forwardName = "checker_valuationAgency_page";
		} else if ((event != null) && event.equals("checker_reject_edit")) {
			forwardName = "common_reject_page";
		} else if ((event != null)
				&& (event.equals("maker_prepare_close") || event
						.equals("maker_draft_close_process"))) {
			forwardName = "maker_prepare_close";
		}

		else if ((event != null) && (event.equals("maker_confirm_close"))
				|| event.equals(MAKER_CONFIRM_DRAFT_CLOSE)) {
			forwardName = "common_close_page";
		}

		else if ((event != null)
				&& event.equals("maker_prepare_resubmit_create")) {
			forwardName = "maker_prepare_resubmit";
		} else if (event.equals("maker_confirm_resubmit_edit")
				|| event.equals("maker_edit_valuationAgency")
				|| event.equals("maker_draft_valuationAgency")
				|| event.equals("maker_update_draft_valuationAgency")
				|| event.equals("maker_save_create")
				|| event.equals("maker_save_update")) {
			forwardName = "common_submit_page";
		}

		else if ((event != null) && event.equals("rejected_delete_read")) {
			forwardName = "maker_view_todo_page";
		}

		else if ((event != null)
				&& event.equals("maker_edit_valuationAgency_read")) {
			forwardName = "maker_add_edit_valuationAgency_page";
		} else if (event.equals("maker_view_valuationAgency")) {

			forwardName = "maker_view_page";
		}

		else if ((event != null)
				&& event.equals("maker_edit_valuationAgency_read")) {
			forwardName = "maker_add_edit_valuationAgency_page";
		}

		else if ((event != null)
				&& event.equals("maker_disable_valuationAgency_read")) {
			forwardName = "maker_view_disable_page";
		}

		else if ((event != null)
				&& event.equals("maker_enable_valuationAgency_read")) {
			forwardName = "maker_view_enable_page";
		}

		else if ((event != null)
				&& event.equals("maker_disable_valuationAgency")) {
			forwardName = "common_submit_page";
		} else if ((event != null)
				&& event.equals("maker_enable_valuationAgency")) {
			forwardName = "common_submit_page";
		}

		else if ((event.equals("maker_confirm_resubmit_edit"))
				|| event.equals("maker_edit_directorMaster")
				|| event.equals("maker_confirm_resubmit_disable")
				|| event.equals("maker_create_directorMaster")
				|| event.equals("maker_confirm_resubmit_create")
				|| event.equals("maker_draft_directorMaster")
				|| event.equals("maker_event_upload_valuation_agency")
				|| event.equals("maker_update_draft_directorMaster")
				|| event.equals("maker_confirm_resubmit_enable")

		) {
			forwardName = "common_submit_page";
		}

		else if ((event != null) && (event.equals("checker_process_disable"))) {
			forwardName = "checker_disable_directorMaster_page";
		}

		else if ((event != null) && (event.equals("checker_process_enable"))) {
			forwardName = "checker_enable_directorMaster_page";
		}

		else if ((event != null)
				&& event.equals("maker_prepare_resubmit_disable")) {
			forwardName = "maker_prepare_resubmit_disable";
		}

		else if (event.equals("maker_prepare_resubmit_enable")
				|| event.equals("maker_prepare_resubmit_delete")) {
			forwardName = "maker_prepare_resubmit_enable";
		}

		else if ((event != null)
				&& (event.equals("maker_save_process") || event
						.equals("maker_update_save_process"))) {
			forwardName = "maker_view_save_page";
		} else if ((event != null)
				&& event.equals("maker_submit_valuationAgency_error")) {
			forwardName = "maker_submit_valuationAgencyaError";
		}

		else if ((event != null)
				&& event.equals("maker_edit_valuationAgency_error")) {
			forwardName = "maker_edit_valuationAgencyError";
		}

		else if ((event != null)
				&& event.equals("maker_confirm_resubmit_enable_error")) {
			forwardName = "maker_confirm_resubmit_enableError";
		}

		else if ((event != null)
				&& event.equals("maker_confirm_resubmit_disable_error")) {
			forwardName = "maker_confirm_resubmit_disableError";
		}

		else if ((event != null)
				&& event.equals("maker_confirm_resubmit_edit_error")) {
			forwardName = "maker_confirm_resubmit_editError";
		} else if ((event != null) && event.equals("maker_save_update_error")) {
			forwardName = "maker_save_updateError";
		}

		else if ((event != null) && event.equals("maker_save_create_error")) {
			forwardName = "maker_save_createError";
		} else if ((event != null) && event.equals("checker_reject_edit_error")) {
			forwardName = "checker_reject_editError";
		}

		else if (event.equals("list_pagination")) {
			forwardName = "maker_list_valuationAgency_page";
		}

		else if (event.equals("refresh_region_id")) {
			forwardName = "refresh_region_id";
		}

		else if (event.equals("refresh_state_id")) {
			forwardName = "refresh_state_id";
		}

		else if (event.equals("refresh_city_id")) {
			forwardName = "refresh_city_id";
		}

		// **********************UPLOAD********************************

		else if ((event != null) &&

		event.equals("maker_prepare_upload_valuation_agency")) {
			forwardName = "maker_prepare_upload_valuation_agency_page";
		}

		else if (event.equals("checker_process_create_insert")) {
			forwardName = "checker_valuation_agency_insert_page";
		}

		else if ((event != null) && event.equals("checker_approve_insert")) {
			forwardName = "common_submit_page";
		}

		else if ((event != null) && event.equals("checker_reject_insert")) {
			forwardName = "common_reject_page";
		}

		else if ((event != null) &&

		event.equals(EVENT_MAKER_REJECTED_DELETE_READ)) {
			forwardName = EVENT_MAKER_REJECTED_DELETE_READ;
		}

		else if ((event != null) && (event.equals(MAKER_PREPARE_INSERT_CLOSE))) {
			forwardName = "maker_prepare_insert_close";
		} else if ((event != null)
				&& (event.equals("maker_confirm_insert_close"))) {
			forwardName = "common_submit_page";
		}

		else if ((event != null)
				&& (event.equals("checker_reject_insert_error"))) {
			forwardName = "checker_reject_insert_error_page";
		}

		else if ((event != null) &&

		(event.equals("maker_event_upload_valuation_agency_error"))) {
			forwardName = "maker_event_upload_valuation_agency_error_page";
		}
		
		else if ((event != null)
				&& event.equals("maker_update_draft_valuationAgency_error")) {
			forwardName = "maker_update_draft_valuationAgencyError";
		}

		return forwardName;
	}

}
