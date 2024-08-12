/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.caseCreationUpdate;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.IPin;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.checklist.camreceipt.DownloadImageCommand;
import com.integrosys.cms.ui.checklist.camreceipt.ImageTagResultPrintCommand;
import com.integrosys.cms.ui.checklist.secreceipt.RetriveImageCommand;

/**
 * @author $Author: Abhijit R$ Action For CaseCreation
 */
public class MaintainCaseCreationAction extends CommonAction implements IPin {

	private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	public static final String FIRST_SORT = "LOGIN_ID";

	public static final String SECOND_SORT = "USER_NAME";

	// public static final String EVENT_LIST = "maker_list_caseCreationUpdate";
	public static final String MAKER_EDIT_CASECREATIONUPDATE_BRANCH_MENU="maker_edit_caseCreationUpdate_branch_menu";
	public static final String MAKER_VIEW_CASECREATIONUPDATE_BRANCH="maker_view_caseCreationUpdate_branch";
	public static final String MAKER_LIST_CASECREATION = "maker_list_caseCreationUpdate";
	public static final String MAKER_LIST_CASECREATION_BRANCH_MENU = "maker_list_casecreation_branch_menu";
	public static final String SEARCH_CASE_CREATION_UPDATE = "search_case_creation_update";
	public static final String REFRESH_COORDINATOR_DETAIL = "refresh_coordinator_detail";
	public static final String SEARCH_CASE_CREATION_UPDATE_PAGINATE = "search_case_creation_update_paginate";
	public static final String MAKER_LIST_CASECREATION_CPUT_MENU = "maker_list_casecreation_cput_menu";
	public static final String CHECKER_LIST_CASECREATION = "checker_list_caseCreationUpdate";
	public static final String CHECKER_VIEW_CASECREATION = "checker_view_caseCreationUpdate";
	public static final String MAKER_VIEW_CASECREATION = "maker_view_caseCreationUpdate";
	public static final String MAKER_EDIT_CASECREATION_READ = "maker_edit_caseCreationUpdate_read";
	public static final String MAKER_CONFIRM_RESUBMIT_EDIT = "maker_confirm_resubmit_edit";
	public static final String MAKER_CONFIRM_RESUBMIT_CREATE = "maker_confirm_resubmit_create";
	public static final String MAKER_CONFIRM_RESUBMIT_DELETE = "maker_confirm_resubmit_delete";
	public static final String MAKER_EDIT_CASECREATION = "maker_edit_caseCreationUpdate";
	public static final String MAKER_EDIT_CASECREATION_CPUT_MENU = "maker_edit_caseCreationUpdate_cput_menu";
	public static final String MAKER_DELETE_CASECREATION = "maker_delete_caseCreationUpdate";
	public static final String CHECKER_EDIT_READ = "checker_edit_read";
	public static final String REJECTED_DELETE_READ = "rejected_delete_read";
	public static final String CHECKER_APPROVE_EDIT = "checker_approve_edit";
	public static final String CHECKER_REJECT_EDIT = "checker_reject_edit";
	public static final String CHECKER_REJECT_CREATE = "checker_reject_create";
	public static final String CHECKER_REJECT_DELETE = "checker_reject_delete";
	public static final String MAKER_PREPARE_CLOSE = "maker_prepare_close";
	public static final String MAKER_PREPARE_RESUBMIT = "maker_prepare_resubmit";
	public static final String MAKER_CONFIRM_CLOSE = "maker_confirm_close";
	public static final String REDIRECT_LIST_CASECREATION = "redirect_list_caseCreationUpdate";
	public static final String MAKER_DELETE_CASECREATION_READ = "maker_delete_caseCreationUpdate_read";
	public static final String MAKER_EDIT_REJECT_EDIT = "maker_edit_reject_edit";
	public static final String MAKER_SEARCH_CASECREATION = "maker_search_caseCreationUpdate";
	public static final String LIST_PAGINATION = "list_pagination";
	public static final String LIST_PAGINATION_BRANCH = "list_pagination_branch";
	public static final String LIST_PAGINATION_BRANCH_CPUT = "list_pagination_branch_cput";
	public static final String LIST_PAGINATION_CPUT = "list_pagination_cput";
	public static final String CHECKER_LIST_PAGINATION = "checker_list_pagination";
	public static final String MAKER_SEARCH_LIST_CASECREATION = "maker_search_list_caseCreationUpdate";
	public static final String CHECKER_SEARCH_LIST_CASECREATION = "checker_search_list_caseCreationUpdate";
	public static final String CHECKER_PROCESS_EDIT = "checker_process_edit";
	public static final String CHECKER_PROCESS_DELETE = "checker_process_delete";
	public static final String CHECKER_PROCESS_CREATE = "checker_process_create";
	public static final String MAKER_PREPARE_RESUBMIT_DELETE = "maker_prepare_resubmit_delete";
	public static final String MAKER_PREPARE_CREATE_CASECREATION = "maker_prepare_create_caseCreationUpdate";
	public static final String MAKER_CREATE_CASECREATION = "maker_create_caseCreationUpdate";
	public static final String MAKER_DRAFT_CASECREATION = "maker_draft_caseCreationUpdate";
	public static final String MAKER_SAVE_PROCESS = "maker_save_process";
	public static final String MAKER_SAVE_CREATE = "maker_save_create";
	public static final String MAKER_PREPARE_RESUBMIT_CREATE = "maker_prepare_resubmit_create";
	public static final String MAKER_UPDATE_DRAFT_CASECREATION = "maker_update_draft_caseCreationUpdate";
	public static final String MAKER_UPDATE_SAVE_PROCESS = "maker_update_save_process";
	public static final String MAKER_SAVE_UPDATE = "maker_save_update";
	public static final String MAKER_DRAFT_CLOSE_PROCESS = "maker_draft_close_process";
	public static final String MAKER_CONFIRM_DRAFT_CLOSE = "maker_confirm_draft_close";

	//CaseCreation Master Upload
	public static final String CHECKER_APPROVE_INSERT = "checker_approve_insert";
	public static final String MAKER_PREPARE_UPLOAD_CASECREATION = "maker_prepare_upload_caseCreationUpdate";
	public static final String MAKER_EVENT_UPLOAD_CASECREATION = "maker_event_upload_caseCreationUpdate";
	public static final String MAKER_REJECTED_DELETE_READ = "maker_rejected_delete_read";
	public static final String CHECKER_PROCESS_CREATE_INSERT = "checker_process_create_insert";
	public static final String CHECKER_REJECT_INSERT = "checker_reject_insert";
	public static final String MAKER_PREPARE_INSERT_CLOSE = "maker_prepare_insert_close";
	public static final String MAKER_CONFIRM_INSERT_CLOSE = "maker_confirm_insert_close";
	 public static final String EVENT_RETRIEVE_IMAGE_GALLARY_CASE = "retrieveImageGallaryCaseCreation";
		public static final String EVENT_RETRIEVE_INDVDL_IMAGE_CASE = "retrieveIndividualImageCaseCreation";
		public static final String EVENT_RETRIEVE_IMAGE_GALLARY_CASE_MENU = "retrieveImageGallaryCaseCreationMenu";
	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event
	 *            is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {

		DefaultLogger.debug(this,"In Maintain CaseCreation Action with event --" + event);

		ICommand objArray[] = null;
		if ((event.equals(CHECKER_LIST_CASECREATION))
				|| event.equals(MAKER_LIST_CASECREATION)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get("ListCaseCreationCmd");
			objArray[1] = new ListOptionsCmd();
		} else if (event.equals(MAKER_LIST_CASECREATION_CPUT_MENU)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get("ListCaseCreationCPUTMenuCmd");
			objArray[1] = new ListOptionsCmd();
		}
		else if (event.equals(MAKER_LIST_CASECREATION_BRANCH_MENU) || event.equals(SEARCH_CASE_CREATION_UPDATE) || event.equals(SEARCH_CASE_CREATION_UPDATE_PAGINATE)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get("ListCaseCreationBranchMenuCmd");
			objArray[1] = new ListOptionsCmd();
		}
		else if (event.equals(REFRESH_COORDINATOR_DETAIL)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get("RefreshCoordinatorDetailCmd");
			objArray[1] = new ListOptionsCmd();
		}
		
		else if ((event.equals(CHECKER_VIEW_CASECREATION))
				|| event.equals(MAKER_VIEW_CASECREATION)
				|| event.equals(MAKER_VIEW_CASECREATIONUPDATE_BRANCH)
				|| event.equals("maker_edit_caseCreationUpdate_branch_menu_error")) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerReadCaseCreationCmd");
			objArray[1] = new ListOptionsCaseBranchCmd();
		} else if ((event != null) && event.equals(MAKER_EDIT_CASECREATION_READ)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerReadCaseCreationCmd");
		} else if ((event.equals(MAKER_CONFIRM_RESUBMIT_EDIT))
				|| event.equals(MAKER_EDIT_CASECREATION)
				|| event.equals(MAKER_EDIT_CASECREATIONUPDATE_BRANCH_MENU)
				|| event.equals(MAKER_EDIT_CASECREATION_CPUT_MENU)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerEditCaseCreationCmd");
		} else if ((event != null) && (event.equals(MAKER_DELETE_CASECREATION_READ))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerReadCaseCreationCmd");

		} else if ((event.equals(MAKER_CONFIRM_RESUBMIT_DELETE))
				|| event.equals(MAKER_DELETE_CASECREATION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerDeleteCaseCreationCmd");
		} else if ((event.equals(MAKER_SEARCH_LIST_CASECREATION))
				|| event.equals(CHECKER_SEARCH_LIST_CASECREATION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"SearchListCaseCreationCommand");

		} else if ((event.equals(LIST_PAGINATION))
				|| event.equals(CHECKER_LIST_PAGINATION)
				|| event.equals(LIST_PAGINATION_BRANCH)
				|| event.equals(LIST_PAGINATION_BRANCH_CPUT)
				|| event.equals(LIST_PAGINATION_CPUT)
		) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("PaginationCmd");

		} else if ((event.equals(CHECKER_PROCESS_EDIT) || event
				.equals(REJECTED_DELETE_READ))

				|| event.equals(CHECKER_PROCESS_DELETE)
				|| event.equals(CHECKER_PROCESS_CREATE)
				|| event.equals(MAKER_SAVE_PROCESS)
				|| event.equals(MAKER_UPDATE_SAVE_PROCESS)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerReadCaseCreationCmd");
			objArray[1] = new ListOptionsCmd();
		} else if ((event != null) && event.equals(CHECKER_APPROVE_EDIT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerApproveEditCaseCreationCmd");
		} else if ((event.equals(CHECKER_REJECT_CREATE))
				|| event.equals(CHECKER_REJECT_EDIT)
				|| event.equals(CHECKER_REJECT_DELETE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerRejectEditCaseCreationCmd");
		} else if ((event.equals(MAKER_PREPARE_CLOSE))
				|| event.equals(MAKER_PREPARE_RESUBMIT)
				|| event.equals(MAKER_PREPARE_RESUBMIT_DELETE)
				|| event.equals(MAKER_PREPARE_RESUBMIT_CREATE)
				|| event.equals(MAKER_DRAFT_CLOSE_PROCESS)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerReadCaseCreationCmd");
			objArray[1] = new ListOptionsCmd();
		} else if ((event != null)
				&& (event.equals(MAKER_CONFIRM_CLOSE) || event
						.equals(MAKER_CONFIRM_DRAFT_CLOSE))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCloseCaseCreationCmd");
		} else if ((event != null)
				&& event.equals(MAKER_PREPARE_CREATE_CASECREATION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareCreateCaseCreationCmd");

		} else if ((event.equals(MAKER_CONFIRM_RESUBMIT_CREATE))
				|| event.equals(MAKER_CREATE_CASECREATION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCreateCaseCreationCmd");

		} else if (event.equals(MAKER_DRAFT_CASECREATION)
				|| event.equals(MAKER_UPDATE_DRAFT_CASECREATION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerSaveCaseCreationCmd");

		} else if (event.equals(MAKER_SAVE_CREATE)
				|| event.equals(MAKER_SAVE_UPDATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerEditCaseCreationCmd");

		}else if (event.equals(MAKER_PREPARE_UPLOAD_CASECREATION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareUploadCaseCreationCmd");

		}else if (event.equals(MAKER_EVENT_UPLOAD_CASECREATION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerUploadCaseCreationCmd");

		}else if (event.equals(MAKER_REJECTED_DELETE_READ)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadFileInsertListCmd");

		}else if ((event != null) && event.equals(CHECKER_APPROVE_INSERT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerApproveInsertCaseCreationCmd");
		}else if ( event.equals(CHECKER_REJECT_INSERT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerRejectInsertCaseCreationCmd");
		}else if ((event != null) && event.equals(CHECKER_PROCESS_CREATE_INSERT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadFileInsertListCmd");
		}else if ((event != null) && event.equals(MAKER_PREPARE_INSERT_CLOSE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadFileInsertListCmd");
		}else if ((event != null) && (event.equals(MAKER_CONFIRM_INSERT_CLOSE))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerInsertCloseCaseCreationCmd");
		}else if("view_image_page".equals(event)|| "view_image_page_menu".equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new ViewImageCommand();
//            objArray[1] = new FrameFlagSetterCommand();
        }else if (event.equals("retrieveImage")) {
			objArray = new ICommand[1];
			objArray[0] =new RetriveImageCommand();
		}
        else if (event.equals(EVENT_RETRIEVE_IMAGE_GALLARY_CASE) || event.equals(EVENT_RETRIEVE_INDVDL_IMAGE_CASE) || event.equals(EVENT_RETRIEVE_IMAGE_GALLARY_CASE_MENU)) {
			objArray = new ICommand[1];
			objArray[0] = new com.integrosys.cms.ui.caseCreationUpdate.RetriveImageCommand();
		}else if (event.equals("downloadImage")) {
			objArray = new ICommand[1];
			objArray[0] = new DownloadImageCommand();
		}else if ("print_image".equals(event) || "view_image".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ImageTagResultPrintCommand();
		}else if("maker_edit_caseCreationUpdate_cput_menu_error".equals(event) || "maker_add_edit_caseCreationUpdate_error".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerReadCaseCreationCmd");
			objArray[1] = new ListOptionsCaseBranchCmd();
		}

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
	public ActionErrors validateInput(ActionForm aForm, Locale locale)  {
		return CaseCreationValidator.validateInput(aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals(MAKER_EDIT_REJECT_EDIT)
				|| event.equals(MAKER_SEARCH_CASECREATION)
				|| event.equals(MAKER_CONFIRM_RESUBMIT_EDIT)
				|| event.equals(MAKER_CREATE_CASECREATION)
				|| event.equals(MAKER_SAVE_UPDATE)
				|| event.equals("maker_draft_caseCreationUpdate")
//				|| event.equals(MAKER_CONFIRM_RESUBMIT_DELETE)
				|| event.equals(MAKER_CONFIRM_RESUBMIT_CREATE)
				|| event.equals("maker_update_draft_caseCreationUpdate"))

		{
			result = false;
		}else if("maker_edit_caseCreationUpdate".equals(event) || "maker_edit_caseCreationUpdate_cput_menu".equals(event) || "maker_edit_caseCreationUpdate_branch_menu".equals(event) || MAKER_CONFIRM_RESUBMIT_DELETE.equals(event)) {
			result = true;
		}
		return result;
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
		DefaultLogger.debug(this, " Exception map error is "
				+ exceptionMap.isEmpty());
		if ((resultMap.get("wip") != null)
				&& (resultMap.get("wip")).equals("wip")) {
			if(event.equalsIgnoreCase("maker_view_caseCreationUpdate_branch")){
				aPage.setPageReference(getReference("work_in_process_branch_menu"));
			}else{
			aPage.setPageReference(getReference("work_in_process"));
			}
			return aPage;
		} else if ((resultMap.get("Error_EmpId") != null)
				&& (resultMap.get("Error_EmpId")).equals("Error_EmpId")) {
			DefaultLogger.debug(this, "Inside Error_EmpId");
			aPage.setPageReference("maker_add_edit_caseCreationUpdate_page");
			return aPage;
		} else if(event.equalsIgnoreCase("maker_add_edit_caseCreationUpdate_error")) {
			aPage.setPageReference("maker_add_edit_caseCreationUpdate_page");
			return aPage;
		}else if ((resultMap.get("errorEveList") != null) && ((String) resultMap.get("errorEveList")).equals("errorEveList")) {
			aPage.setPageReference("maker_fileupload_caseCreationUpdate_page");
			return aPage;
		}else if(event.equalsIgnoreCase("checker_reject_edit_error")) {
			aPage.setPageReference("checker_caseCreationUpdate_page");
			return aPage;
		}else if(event.equalsIgnoreCase("search_case_creation_update_error")) {
			aPage.setPageReference("maker_list_casecreation_branch_menu_page");
			return aPage;
		}else {
			aPage.setPageReference(getReference(event));
			return aPage;
		}
		/*if ((resultMap.get("wip") != null)
				&& (resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference(getReference("work_in_process"));
			return aPage;
		} else if ((resultMap.get("Error_EmpId") != null)
				&& (resultMap.get("Error_EmpId")).equals("Error_EmpId")) {
			DefaultLogger.debug(this, "Inside Error_EmpId");
			aPage.setPageReference("maker_add_edit_caseCreationUpdate_page");
			return aPage;
		} else {
			aPage.setPageReference(getReference(event));
			return aPage;
		}*/
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if ("maker_add_caseCreationUpdate".equals(event)) {
			errorEvent = "maker_add_edit_caseCreationUpdate_error";
		} else if ("maker_edit_caseCreationUpdate".equals(event)
				|| event.equals("maker_confirm_resubmit_edit")|| event.equals("maker_update_draft_caseCreationUpdate")) {
			errorEvent = "maker_add_edit_caseCreationUpdate_error";
		} else if ("maker_edit_reject_add".equals(event)) {
			errorEvent = "maker_add_edit_caseCreationUpdate_error";
		} else if ("maker_edit_reject_edit".equals(event)) {
			errorEvent = "maker_add_edit_caseCreationUpdate_error";
		} else if ("checker_approve_edit".equals(event)) {
			errorEvent = "checker_approve_edit_error";
		} else if ("checker_approve_add".equals(event)) {
			errorEvent = "checker_add_read_error";
		} else if (event.equals("maker_search_caseCreationUpdate")) {
			errorEvent = "maker_prepare_search_caseCreationUpdate";
		}else if (event.equals("maker_create_caseCreationUpdate")|| event.equals("maker_draft_caseCreationUpdate")) {
			errorEvent = "maker_prepare_create_caseCreationUpdate";
		}else if (event.equals("maker_save_update")) {
			errorEvent = "maker_save_update_error";
		}else if (event.equals("maker_save_update")) {
			errorEvent = "maker_save_update_error";
		}else if (event.equals("maker_confirm_resubmit_delete")) {
			errorEvent = "maker_confirm_resubmit_delete_error";
		}else if (event.equals("checker_reject_edit")) {
			errorEvent = "checker_reject_edit_error";
		}else if (event.equals("checker_reject_create")) {
			errorEvent = "checker_reject_create_error";
		}else if (event.equals("checker_reject_delete")) {
			errorEvent = "checker_reject_delete_error";
		}else if (event.equals("search_case_creation_update")) {
			errorEvent = "search_case_creation_update_error";
		}else if (event.equals("maker_edit_caseCreationUpdate_cput_menu")) {
			errorEvent = "maker_edit_caseCreationUpdate_cput_menu_error";
		}else if (event.equals("maker_edit_caseCreationUpdate_branch_menu")) {
			errorEvent = "maker_edit_caseCreationUpdate_branch_menu_error";
		}
		return errorEvent;
	}

	/**
	 * method which determines the forward name for a particular event
	 * 
	 * @param event
	 *            as String
	 * @return String
	 */
	private String getReference(String event) {
		String forwardName = null;
		if ((event.equals("checker_list_caseCreationUpdate"))
				|| event.equals("maker_list_caseCreationUpdate")) {
			forwardName = "maker_list_caseCreationUpdate_page";
		}
		 else if ((event != null) && event.equals("maker_list_casecreation_branch_menu")) {
				forwardName = "maker_list_casecreation_branch_menu_page";
			}
		 else if ((event != null) && event.equals("search_case_creation_update")||event.equals("search_case_creation_update_paginate")||event.equals("search_case_creation_update_error")) {
				forwardName = "maker_list_casecreation_branch_menu_page";
			}
		 else if ((event != null) && event.equals("maker_list_casecreation_cput_menu")) {
				forwardName = "maker_list_casecreation_branch_menu_page";
			}
		else if ((event != null) && event.equals("maker_search_caseCreationUpdate")) {
			forwardName = "maker_list_caseCreationUpdate_page";
		} else if (event.equals("checker_list_pagination")) {
			forwardName = "checker_caseCreationUpdate_page";
		}else if ( event.equals("list_pagination")) {
			forwardName = "maker_list_caseCreationUpdate_page";
		}else if ((event != null) && event.equals("redirect_list_caseCreationUpdate")) {
			forwardName = "maker_list_caseCreationUpdate_page";
		} else if ((event != null) && event.equals("checker_list_caseCreationUpdate")) {
			forwardName = "checker_list_caseCreationUpdate_page";
		} else if ((event != null) && event.equals("maker_prepare_add_caseCreationUpdate")) {
			forwardName = "maker_add_edit_caseCreationUpdate_page";
		} else if ((event != null) && event.equals("maker_add_caseCreationUpdate")) {
			forwardName = "common_submit_page";
		} else if ((event.equals("checker_view_caseCreationUpdate"))
				|| event.equals("maker_view_caseCreationUpdate")) {
			forwardName = "maker_view_page";
		}  else if (event.equals("maker_view_caseCreationUpdate_branch")) {
			forwardName = "maker_view_branch_menu_page";
		} else if (event.equals("list_pagination_branch")) {
			forwardName = "maker_view_branch_menu_page";
		} else if (event.equals("list_pagination_branch_cput")) {
			forwardName = "maker_view_page";
		}  else if (event.equals("list_pagination_cput")) {
				forwardName = "maker_view_page";
			} else if (event.equals("maker_confirm_resubmit_delete_error")) {
			forwardName = "maker_prepare_resubmit_delete";
		}else if ((event != null) && event.equals("maker_edit_caseCreationUpdate_read")) {
			forwardName = "maker_add_edit_caseCreationUpdate_page";
		} else if ((event.equals("maker_confirm_resubmit_edit"))
				|| event.equals("maker_edit_caseCreationUpdate")
				|| event.equals("maker_create_caseCreationUpdate")
				|| event.equals("maker_draft_caseCreationUpdate")
				|| event.equals("maker_save_create")
				|| event.equals("maker_save_update")
				|| event.equals("maker_update_draft_caseCreationUpdate")
				|| event.equals("maker_event_upload_caseCreationUpdate")) {
			forwardName = "common_submit_page";
		} else if ((event != null) && event.equals("maker_delete_caseCreationUpdate_read")) {
			forwardName = "maker_view_delete_page";
		} else if ((event != null) && event.equals("maker_edit_caseCreationUpdate_branch_menu")
				|| event.equals("maker_confirm_resubmit_delete")
				|| event.equals("maker_confirm_resubmit_create")
				|| event.equals("maker_edit_caseCreationUpdate_cput_menu")
		) {
			forwardName = "common_submit_branch_menu_page";
		} else if ((event != null)
				&& (event.equals("maker_save_process") || event
						.equals("maker_update_save_process")|| event
						.equals("maker_save_update_error"))) {
			forwardName = "maker_view_save_page";
		} else if ((event != null) && event.equals("maker_delete_caseCreationUpdate")) {
			forwardName = "common_submit_page";
		} else if ((event != null) && event.equals("checker_add_read")) {
			forwardName = "checker_caseCreationUpdate_page";
		} else if ((event != null) && event.equals("checker_add_read_error")) {
			forwardName = "checker_caseCreationUpdate_page";
		} else if ((event != null) && event.equals("checker_edit_read")) {
			forwardName = "checker_caseCreationUpdate_page";
		} 
		else if ((event != null) && event.equals("checker_delete_read")) {
			forwardName = "checker_caseCreationUpdate_page";
		} else if ((event != null) && event.equals("checker_approve_add")) {
			forwardName = "common_approve_page";
		} else if ((event != null) && event.equals("checker_approve_edit")) {
			forwardName = "common_approve_page";
		} else if ((event != null) && event.equals("checker_approve_delete")) {
			forwardName = "common_approve_page";
		} else if ((event != null) && event.equals("checker_reject_add")) {
			forwardName = "common_reject_page";
		} else if ((event != null) && event.equals("checker_reject_edit")) {
			forwardName = "common_reject_page";
		} else if ((event != null) && event.equals("checker_reject_delete")) {
			forwardName = "common_reject_page";
		} else if ((event != null) && event.equals("rejected_add_read")) {
			forwardName = "maker_add_edit_caseCreationUpdate_page";
		} else if ((event != null) && event.equals("rejected_edit_read")) {
			forwardName = "maker_add_edit_caseCreationUpdate_page";
		} else if ((event != null) && event.equals("rejected_delete_read")) {
			forwardName = "maker_view_todo_page";
		} else if ((event != null) && event.equals("maker_cncl_reject_add")) {
			forwardName = "common_close_page";
		} else if ((event != null) && event.equals("maker_cncl_reject_edit")) {
			forwardName = "common_close_page";
		} else if ((event != null) && event.equals("maker_cncl_reject_delete")) {
			forwardName = "common_close_page";
		} else if ((event != null) && event.equals("maker_edit_reject_add")) {
			forwardName = "common_submit_page";
		} else if ((event != null) && event.equals("maker_edit_reject_edit")) {
			forwardName = "common_submit_page";
		} else if ((event != null) && event.equals("maker_edit_reject_delete")) {
			forwardName = "common_submit_page";
		} else if ((event != null) && event.equals("work_in_process")) {
			forwardName = "work_in_process_page";
		}else if ((event != null) && event.equals("work_in_process_branch_menu")) {
			forwardName = "work_in_process_branch_menu_page";
		} else if ((event != null)
				&& event.equals("maker_add_edit_caseCreationUpdate_error")) {
			forwardName = "maker_add_edit_caseCreationUpdate_page";
		} else if ((event != null) && event.equals("rejected_read")) {
			forwardName = "rejected_read_page";
		} else if ((event != null)
				&& event.equals("checker_approve_edit_error")) {
			forwardName = "checker_caseCreationUpdate_page";
		} else if ((event != null)
				&& event.equals("maker_prepare_search_caseCreationUpdate")) {
			forwardName = "search_caseCreationUpdate_page";
		} else if ((event.equals("checker_search_list_caseCreationUpdate"))
				|| event.equals("maker_search_list_caseCreationUpdate")) {
			forwardName = "maker_list_caseCreationUpdate_page";
		} else if ((event.equals("checker_process_edit"))
				|| event.equals("checker_process_delete")
				|| event.equals("checker_process_create")) {
			forwardName = "checker_caseCreationUpdate_page";
		} else if ((event != null) && event.equals("maker_prepare_resubmit")) {
			forwardName = "maker_prepare_resubmit";
		} else if ((event != null)
				&& (event.equals("maker_prepare_close") || event
						.equals("maker_draft_close_process"))) {
			forwardName = "maker_prepare_close";
		} else if ((event != null)
				&& event.equals("maker_prepare_resubmit_delete")) {
			forwardName = "maker_prepare_resubmit_delete";
		} else if ((event != null)
				&& (event.equals(MAKER_CONFIRM_CLOSE) || event
						.equals(MAKER_CONFIRM_DRAFT_CLOSE))) {
			forwardName = "common_close_page";
		} else if ((event != null)
				&& (event.equals("maker_prepare_create_caseCreationUpdate"))) {
			forwardName = "prepare_create_caseCreationUpdate";
		}else if ((event != null)
				&& (event.equals("maker_create_caseCreationUpdate_error"))) {
			forwardName = "prepare_create_caseCreationUpdate";
		} else if (event.equals("maker_prepare_resubmit_create")) {
			forwardName = "maker_prepare_resubmit";
		}else if ((event != null) && event.equals("maker_prepare_upload_caseCreationUpdate")) {
			forwardName = "prepare_upload_caseCreationUpdate_page";
		}else if ((event != null) && event.equals("maker_rejected_delete_read")) {
			forwardName = "maker_view_insert_todo_page";
		}else if ((event != null) && event.equals("checker_approve_insert")) {
			forwardName = "common_approve_page";
		}else if ((event != null) && event.equals("checker_reject_insert")) {
		forwardName = "common_reject_page";
		}else if (event.equals("checker_process_create_insert")) {
			forwardName = "checker_caseCreationUpdate_insert_page";
		}else if ((event != null)
				&& (event.equals(MAKER_CONFIRM_INSERT_CLOSE))) {
			forwardName = "common_close_page";
		} else if ((event != null)
				&& (event.equals(MAKER_PREPARE_INSERT_CLOSE))) {
			forwardName = "maker_prepare_insert_close";
		} else if ( event.equals("checker_reject_edit_error")
				||event.equals("checker_reject_create_error")
				||event.equals("checker_reject_delete_error")) {
			forwardName = "checker_caseCreationUpdate_page";
		}else if ((event != null)
				&& (event.equals(REFRESH_COORDINATOR_DETAIL))) {
			forwardName = "refresh_coordinator_detail";
		}else if ("view_image_page".equals(event)) {
			forwardName = "view_image_page";
		}else if ("retrieveImage".equals(event)) {
			forwardName = "imagePath";
		}else if ("view_image_page_menu".equals(event)) {
			forwardName = "view_image_page_menu";
		} else if (EVENT_RETRIEVE_INDVDL_IMAGE_CASE.equals(event)) {
			forwardName = "imagePath";
		} else if (EVENT_RETRIEVE_IMAGE_GALLARY_CASE.equals(event)) {
			forwardName = "image_gallary";
		}else if ( EVENT_RETRIEVE_IMAGE_GALLARY_CASE_MENU.equals(event)) {
			forwardName = "image_gallary_menu";
		}else if ("downloadImage".equals(event)) {
			forwardName = "downloadImage";
		}else if ("print_image".equals(event)) {
			forwardName = "print_image";
		}else if ("view_image".equals(event)) {
			forwardName = "view_image";
		}else if ("maker_edit_caseCreationUpdate_cput_menu_error".equals(event)) {
			forwardName = "maker_edit_caseCreationUpdate_cput_menu_error";
		}else if ("maker_edit_caseCreationUpdate_branch_menu_error".equals(event)) {
			forwardName = "maker_view_branch_menu_page";
		}

		return forwardName;
	}

}