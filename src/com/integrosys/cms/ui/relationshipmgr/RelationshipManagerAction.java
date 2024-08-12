package com.integrosys.cms.ui.relationshipmgr;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;

/**
 * Describe this class Purpose : Relationship Manager Actions Description :
 * Handling Actions of Relationship Manager
 * 
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1 $ Tag : $Name$
 */

public class RelationshipManagerAction extends CommonAction {

	public static final String LIST_RELATIONSHIP_MANAGER = "list_relationship_manager";

	public static final String PREPARE_CREATE_RELATIONSHIP_MANAGER = "prepare_create_relationship_mgr";

	public static final String MAKER_SUBMIT_CREATE_RELATIONSHIP_MANAGER = "maker_submit_create_relationship_mgr";

	public static final String MAKER_SAVE_CREATE_RELATIONSHIP_MANAGER = "maker_save_create_relationship_mgr";

	public static final String MAKER_SAVE_UPDATE_RELATIONSHIP_MANAGER = "maker_save_update_relationship_mgr";

	public static final String MAKER_SAVE_PROCESS = "maker_save_process";

	public static final String MAKER_SAVE_UPDATE_PROCESS = "maker_update_save_process";

	public static final String MAKER_CONFIRM_RESUBMIT_DRAFT = "maker_confirm_resubmit_draft";

	public static final String MAKER_DRAFT_CLOSE_RELATIONSHIP_MANAGER = "maker_draft_close_process";

	public static final String MAKER_CONFIRM_DRAFT_CLOSE = "maker_confirm_draft_close";

	public static final String SUBMIT_CREATE_RELATIONSHIP_MANAGER = "maker_submit_create_relationship_mgr";

	public static final String VIEW_RELATIONSHIP_MANAGER_BY_INDEX = "view_relationship_mgr_by_index";

	public static final String PREPARE_EDIT_RELATIONSHIP_MANAGER = "prepare_maker_submit_edit";

	public static final String MAKER_SUBMIT_EDIT_RELATIONSHIP_MANAGER = "maker_submit_edit";

	public static final String PREPARE_DELETE_RELATIONSHIP_MANAGER = "prepare_maker_submit_remove";

	public static final String MAKER_SUBMIT_DELETE_RELATIONSHIP_MANAGER = "maker_submit_remove";

	public static final String FIRST_SORT = "LOGIN_ID";

	public static final String SECOND_SORT = "USER_NAME";

	public static final String CHECKER_LIST_RELATIONSHIP_MANAGER = "checker_list_relationship_mgr";

	public static final String CHECKER_VIEW_RELATIONSHIP_MANAGER = "checker_view_relationship_mgr";

	public static final String MAKER_CONFIRM_RESUBMIT_EDIT = "maker_confirm_resubmit_edit";

	public static final String MAKER_CONFIRM_RESUBMIT_UPDATE = "maker_confirm_resubmit_update";

	public static final String CHECKER_EDIT_READ = "checker_edit_read";

	public static final String REJECTED_DELETE_READ = "rejected_delete_read";

	public static final String CHECKER_APPROVE_EDIT = "checker_approve_edit";

	public static final String CHECKER_REJECT_EDIT = "checker_reject_edit";

	public static final String MAKER_PREPARE_CLOSE = "maker_prepare_close";

	public static final String MAKER_EDIT_RELATIONSHIP_MANAGER_READ = "maker_edit_relationship_mgr_read";

	public static final String MAKER_PREPARE_RESUBMIT = "maker_prepare_resubmit";

	public static final String MAKER_CONFIRM_CLOSE = "maker_confirm_close";

	public static final String REDIRECT_LIST_RELATIONSHIP_MANAGER = "redirect_list_relationship_mgr";

	public static final String MAKER_DELETE_RELATIONSHIP_MANAGER_READ = "maker_delete_relationship_mgr_read";

	public static final String MAKER_EDIT_REJECT_EDIT = "maker_edit_reject_edit";

	public static final String CHECKER_PROCESS_CREATE = "checker_process_create";

	public static final String CHECKER_PROCESS_DELETE = "checker_process_delete";

	public static final String CHECKER_PROCESS_EDIT = "checker_process_edit";

	public static final String LIST_PAGINATION = "list_pagination";

	public static final String MAKER_RESUBMIT_CREATE_RELATIONSHIP_MANAGER = "maker_prepare_resubmit_create";

	public static final String CHECKER_RELATIONSHIP_MANAGER_PAGE = "checker_relationship_mgr_page";

	public static final String COMMON_SUBMIT_PAGE = "common_submit_page";

	public static final String COMMON_CLOSE_PAGE = "common_close_page";

	public static final String COMMON_APPROVE_PAGE = "common_approve_page";

	public static final String MAKER_VIEW_DELETE_PAGE = "maker_view_delete_page";

	public static final String MAKER_VIEW_TODO_PAGE = "maker_view_todo_page";

	public static final String CHECKER_VIEW_DELETE_PAGE = "checker_view_delete_page";

	public static final String REJECTED_READ_PAGE = "rejected_read_page";

	public static final String WORK_IN_PROGRESS_PAGE = "work_in_process_page";

	public static final String MAKER_CNCL_REJECT_ADD = "maker_cncl_reject_add";

	public static final String MAKER_CNCL_REJECT_EDIT = "maker_cncl_reject_edit";

	public static final String MAKER_CNCL_REJECT_DELETE = "maker_cncl_reject_delete";

	public static final String MAKER_EDIT_REJECT_ADD = "maker_edit_reject_add";

	public static final String MAKER_EDIT_REJECT_DELETE = "maker_edit_reject_delete";

	public static final String WORK_IN_PROCESS = "work_in_process";

	public static final String REJECTED_READ = "rejected_read";

	public static final String MAKER_PREPARE_UPLOAD_RELATIONSHIP_MANAGER = "maker_prepare_upload_relationship_mgr";

	public static final String MAKER_UPLOAD_RELATIONSHIP_MANAGER = "maker_upload_relationship_mgr";
	public static final String CHECKER_APPROVE_INSERT = "checker_approve_insert";
	public static final String MAKER_REJECTED_DELETE_READ = "maker_rejected_delete_read";
	public static final String CHECKER_PROCESS_CREATE_INSERT = "checker_process_create_insert";
	public static final String CHECKER_REJECT_INSERT = "checker_reject_insert";
	public static final String MAKER_PREPARE_INSERT_CLOSE = "maker_prepare_insert_close";
	public static final String MAKER_CONFIRM_INSERT_CLOSE = "maker_confirm_insert_close";
	public static final String POPULATE_FIELD = "populateFields";
	public static final String ADD_CAD = "addCad";
	public static final String ADD_CAD_ERROR = "addCadError";
	public static final String REMOVE_CAD = "removeCad";
	public static final String ADD_EDIT_CAD = "addEditCad";
	public static final String ADD_RESUBMIT_CAD = "addResubmitCad";
	public static final String ADD_RESUBMIT_CAD_REJECTED = "addResubmitCad_rejected";
	public static final String ADD_RESUBMIT_CAD_REJECTED_ERROR = "addResubmitCad_rejected_error";
	public static final String ADD_RESUBMIT_CAD_DRAFT = "addResubmitCad_draft";
	public static final String ADD_RESUBMIT_CAD_DRAFT_ERROR = "addResubmitCad_draft_error";
	public static final String REMOVE_EDIT_CAD = "removeEditCad";
	public static final String RESUBMIT_EDIT_CAD = "removeResubmitCad";
	public static final String RESUBMIT_EDIT_CAD_REJECTED = "removeResubmitCad_rejected";
	public static final String RESUBMIT_EDIT_CAD_REJECTED_ERROR = "removeResubmitCad_rejected_error";
	public static final String RESUBMIT_EDIT_CAD_DRAFT = "removeResubmitCad_draft";
	public static final String RESUBMIT_EDIT_CAD_DRAFT_ERROR = "removeResubmitCad_draft_error";
	public static final String EVENT_PAGINATE = "paginate";
	
	private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	protected ICommand[] getCommandChain(String event) {
		ICommand[] objArray = null;

		if (event != null) {

			if (LIST_RELATIONSHIP_MANAGER.equals(event)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get(
						"ListRelationShipManagerCmd");
			} else if (PREPARE_CREATE_RELATIONSHIP_MANAGER.equals(event)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get(
						"PrepareCreateRelationshipMgrCmd");
			} else if (MAKER_SUBMIT_CREATE_RELATIONSHIP_MANAGER.equals(event)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get(
						"MakerSubmitCreateRelationshipMgrCommand");
			} else if (VIEW_RELATIONSHIP_MANAGER_BY_INDEX.equals(event)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get(
						"ViewRelationShipManagerByIndexCommand");
			} else if (PREPARE_DELETE_RELATIONSHIP_MANAGER.equals(event)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get(
						"PrepareDeleteRelationShipManagerCommand");
			} else if (MAKER_SUBMIT_DELETE_RELATIONSHIP_MANAGER.equals(event)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get(
						"MakerSubmitDeleteRelationShipManagerCommand");
			} else if (PREPARE_EDIT_RELATIONSHIP_MANAGER.equals(event)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get(
						"PrepareEditRelationShipManagerCommand");
			} else if ((event.equals(MAKER_CONFIRM_RESUBMIT_EDIT))
					|| MAKER_SUBMIT_EDIT_RELATIONSHIP_MANAGER.equals(event)
					|| MAKER_CONFIRM_RESUBMIT_UPDATE.equals(event)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get(
						"MakerSubmitEditRelationShipManagerCommand");
			} else if ((event.equals(CHECKER_EDIT_READ) || event
					.equals(REJECTED_DELETE_READ))
					|| event.equals(CHECKER_VIEW_RELATIONSHIP_MANAGER)
					|| event.equals(CHECKER_PROCESS_CREATE)
					|| event.equals(CHECKER_PROCESS_DELETE)
					|| event.equals(MAKER_PREPARE_CLOSE)
					|| event.equals(MAKER_RESUBMIT_CREATE_RELATIONSHIP_MANAGER)
					|| event.equals(CHECKER_PROCESS_EDIT)
					|| event.equals(MAKER_SAVE_PROCESS)
					|| event.equals(MAKER_DRAFT_CLOSE_RELATIONSHIP_MANAGER)
					|| event.equals(MAKER_SAVE_UPDATE_PROCESS)
					|| event.equals(MAKER_PREPARE_RESUBMIT)
					|| event.equals("errorOnApproval")) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get(
						"CheckerReadRelationShipManagerCmd");
			} else if ((event != null) && event.equals(CHECKER_APPROVE_EDIT)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get(
						"CheckerApproveEditRelationShipManagerCmd");
			} else if ((event != null) && event.equals(CHECKER_REJECT_EDIT)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get(
						"CheckerRejectEditRelationShipManagerCmd");
			} else if ((event != null)
					&& event.equals(MAKER_EDIT_RELATIONSHIP_MANAGER_READ)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get(
						"MakerReadRelationShipManagerCmd");
			} else if (event.equals(MAKER_CONFIRM_CLOSE)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get(
						"MakerCloseRelationShipManagerCmd");
			}

			else if (event.equals(MAKER_CONFIRM_DRAFT_CLOSE)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get(
						"MakerDraftCloseRelationShipManagerCmd");
			} else if (event.equals(MAKER_CONFIRM_RESUBMIT_DRAFT)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get(
						"MakerSubmitDraftRelationShipManagerCommand");
			} else if (MAKER_SAVE_CREATE_RELATIONSHIP_MANAGER.equals(event)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get(
						"SaveCreateRelationshipMgrCommand");
			} else if (MAKER_SAVE_UPDATE_RELATIONSHIP_MANAGER.equals(event)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get(
						"MakerSubmitEditRelationShipManagerCommand");
			} else if (event.equals(MAKER_UPLOAD_RELATIONSHIP_MANAGER)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get(
						"MakerUploadRelationshipManagerCmd");
			} else if ((event != null)
					&& (event.equals(MAKER_REJECTED_DELETE_READ)
							|| event.equals(CHECKER_PROCESS_CREATE_INSERT) || event
							.equals(MAKER_PREPARE_INSERT_CLOSE))) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get(
						"CheckerReadFileInsertListCmd");

			} else if ((event != null) && event.equals(CHECKER_APPROVE_INSERT)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get(
						"CheckerApproveInsertRelationshipMgrCmd");
			} else if (event.equals(CHECKER_REJECT_INSERT)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get(
						"CheckerRejectInsertRelationshipMgrCmd");
			} else if ((event != null)
					&& (event.equals(MAKER_CONFIRM_INSERT_CLOSE))) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get(
						"MakerInsertCloseRelationshipMgrCmd");
			}else if ( POPULATE_FIELD.equals(event) ) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("PopulateFieldsCommand");
			}else if ( ADD_CAD.equals(event) || ADD_EDIT_CAD.equals(event) || ADD_CAD_ERROR.equals(event) || ADD_RESUBMIT_CAD.equals(event) || ADD_RESUBMIT_CAD_REJECTED.equals(event) || ADD_RESUBMIT_CAD_DRAFT.equals(event)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("AddCadCommand");
			}else if(REMOVE_CAD.equals(event) || REMOVE_EDIT_CAD.equals(event) || RESUBMIT_EDIT_CAD.equals(event) || RESUBMIT_EDIT_CAD_REJECTED.equals(event) || RESUBMIT_EDIT_CAD_DRAFT.equals(event)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("RemoveCadCommand");
			}else if ( EVENT_PAGINATE.equals(event) ) {
				return new ICommand[] { (ICommand) getNameCommandMap().get("PaginateRelationshipMgrListCommand") };
			}
		} else {
			throw new RelationshipMgrException("Action Event is null");
		}
		return objArray;
	}

	protected IPage getNextPage(String event, HashMap resultMap,
			HashMap exceptionMap) {

		Page page = new Page();

		if ((resultMap.get("wip") != null)
				&& ((String) resultMap.get("wip")).equals("wip")) {
			page.setPageReference(WORK_IN_PROGRESS_PAGE);
			return page;
		}else if ((resultMap.get("errorEveList") != null) && ((String) resultMap.get("errorEveList")).equals("errorEveList")) {
			page.setPageReference("maker_fileupload_relationship_mgr_page");
			return page;
		}else

		if (LIST_RELATIONSHIP_MANAGER.equals(event) || event.equals("listError") || event.equals(EVENT_PAGINATE) ) {
			event = LIST_RELATIONSHIP_MANAGER;
		} else if (PREPARE_CREATE_RELATIONSHIP_MANAGER.equals(event)) {
			event = PREPARE_CREATE_RELATIONSHIP_MANAGER;
		}
		else if (VIEW_RELATIONSHIP_MANAGER_BY_INDEX.equals(event)) {
			event = VIEW_RELATIONSHIP_MANAGER_BY_INDEX;
		}
		else if (PREPARE_DELETE_RELATIONSHIP_MANAGER.equals(event)) {
			event = PREPARE_DELETE_RELATIONSHIP_MANAGER;
		}
		else if (MAKER_SUBMIT_DELETE_RELATIONSHIP_MANAGER.equals(event)) {
			event = MAKER_SUBMIT_DELETE_RELATIONSHIP_MANAGER;
		}
		else if (PREPARE_EDIT_RELATIONSHIP_MANAGER.equals(event) || event.equals("errorSubmitEvent")) {
			event = PREPARE_EDIT_RELATIONSHIP_MANAGER;
		}
		else if (MAKER_SUBMIT_EDIT_RELATIONSHIP_MANAGER.equals(event)) {
			event = MAKER_SUBMIT_EDIT_RELATIONSHIP_MANAGER;
		}
		else if (CHECKER_EDIT_READ.equals(event)) {
			event = CHECKER_RELATIONSHIP_MANAGER_PAGE;
		}
		else if (CHECKER_PROCESS_CREATE.equals(event) 
				||event.equals("errorOnApproval")) {
			event = CHECKER_PROCESS_CREATE;
		}
		else if (CHECKER_PROCESS_DELETE.equals(event)) {
			event = CHECKER_RELATIONSHIP_MANAGER_PAGE;
		}
		else if (CHECKER_APPROVE_EDIT.equals(event)) {
			event = COMMON_APPROVE_PAGE;
		}
		else if (REJECTED_DELETE_READ.equals(event)) {
			event = MAKER_VIEW_TODO_PAGE;
		}
		else if (MAKER_PREPARE_CLOSE.equals(event)) {
			event = MAKER_PREPARE_CLOSE;
		}
		else if (MAKER_RESUBMIT_CREATE_RELATIONSHIP_MANAGER.equals(event) || event.equals("errorReSubmitEvent")) {
			event = MAKER_PREPARE_RESUBMIT;
		}
		else if (MAKER_CONFIRM_RESUBMIT_EDIT.equals(event)) {
			event = COMMON_SUBMIT_PAGE;
		}
		else if (CHECKER_PROCESS_EDIT.equals(event)) {
			event = CHECKER_RELATIONSHIP_MANAGER_PAGE;
		}
		else if (MAKER_SAVE_PROCESS.equals(event)) {
			event = MAKER_SAVE_PROCESS;
		}
		else if (MAKER_SAVE_UPDATE_PROCESS.equals(event)) {
			event = MAKER_SAVE_UPDATE_PROCESS;
		}
		else if (MAKER_DRAFT_CLOSE_RELATIONSHIP_MANAGER.equals(event)) {
			event = MAKER_PREPARE_CLOSE;
		}
		else if (MAKER_CONFIRM_RESUBMIT_DRAFT.equals(event)) {
			event = COMMON_SUBMIT_PAGE;
		}
		else if (MAKER_SAVE_UPDATE_RELATIONSHIP_MANAGER.equals(event)) {
			event = MAKER_SAVE_UPDATE_RELATIONSHIP_MANAGER;
		}
		else if (MAKER_CONFIRM_RESUBMIT_UPDATE.equals(event)) {
			event = COMMON_SUBMIT_PAGE;
		}
		else if (MAKER_PREPARE_UPLOAD_RELATIONSHIP_MANAGER.equals(event)) {
			event = MAKER_PREPARE_UPLOAD_RELATIONSHIP_MANAGER;
		}
		else if (MAKER_UPLOAD_RELATIONSHIP_MANAGER.equals(event)) {
			event = COMMON_SUBMIT_PAGE;
		} else if ((event != null) && event.equals(CHECKER_REJECT_INSERT)) {
			event = "common_reject_page";
		} else if (event.equals(CHECKER_PROCESS_CREATE_INSERT)) {
			event = "checker_relationship_mgr_insert_page";
		} else if ((event != null)
				&& (event.equals(MAKER_CONFIRM_INSERT_CLOSE))) {
			event = COMMON_CLOSE_PAGE;
		} else if ((event != null)
				&& (event.equals(MAKER_PREPARE_INSERT_CLOSE))) {
			event = MAKER_PREPARE_INSERT_CLOSE;
		} 
		else if (event != null && event.equals(MAKER_REJECTED_DELETE_READ)) {
			event = "maker_view_insert_todo_page";
		}
		else if(MAKER_UPLOAD_RELATIONSHIP_MANAGER.equals(event)){
			event = MAKER_PREPARE_UPLOAD_RELATIONSHIP_MANAGER;
		}
		else if(CHECKER_APPROVE_INSERT.equals(event)){
			event = COMMON_SUBMIT_PAGE;
		}

		page.setPageReference(event);
		return page;
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;

		if (MAKER_SUBMIT_EDIT_RELATIONSHIP_MANAGER.equals(event)
				|| SUBMIT_CREATE_RELATIONSHIP_MANAGER.equals(event)
				|| MAKER_CONFIRM_RESUBMIT_DRAFT.equals(event)
				|| MAKER_CONFIRM_RESUBMIT_UPDATE.equals(event)
				|| MAKER_SAVE_CREATE_RELATIONSHIP_MANAGER.equals(event)
				|| MAKER_CONFIRM_RESUBMIT_EDIT.equals(event)
				|| ADD_CAD.equals(event)
				|| ADD_EDIT_CAD.equals(event)
				|| ADD_RESUBMIT_CAD.equals(event)
				|| ADD_RESUBMIT_CAD_REJECTED.equals(event)
				|| ADD_RESUBMIT_CAD_DRAFT.equals(event)
				|| RESUBMIT_EDIT_CAD_REJECTED.equals(event)
				|| RESUBMIT_EDIT_CAD_DRAFT.equals(event)
				|| POPULATE_FIELD.equals(event)
				|| MAKER_SAVE_UPDATE_RELATIONSHIP_MANAGER.equals(event)) {
			result = true;
		}
		return result;
	}

	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		return RelationshipMgrValidator.validateRelationshipMgrForm(
				(RelationshipMgrForm) aForm, locale);
	}

	protected String getErrorEvent(String event) {
		String errorEvent = "";

		if (MAKER_SUBMIT_EDIT_RELATIONSHIP_MANAGER.equals(event)
				|| MAKER_RESUBMIT_CREATE_RELATIONSHIP_MANAGER.equals(event)
				|| MAKER_SAVE_UPDATE_RELATIONSHIP_MANAGER.equals(event)  || ADD_EDIT_CAD.equals(event) || MAKER_SAVE_UPDATE_RELATIONSHIP_MANAGER.equals(event)) {
			errorEvent = "errorSubmitEvent" ;
		} else if (SUBMIT_CREATE_RELATIONSHIP_MANAGER.equals(event) || MAKER_SAVE_CREATE_RELATIONSHIP_MANAGER.equals(event)
				) {
			errorEvent = PREPARE_CREATE_RELATIONSHIP_MANAGER;
		} else if (MAKER_CONFIRM_RESUBMIT_DRAFT.equals(event)) {
			errorEvent = MAKER_SAVE_PROCESS;
		} else if (MAKER_CONFIRM_RESUBMIT_UPDATE.equals(event) || ADD_RESUBMIT_CAD.equals(event)) {
			errorEvent = MAKER_SAVE_UPDATE_PROCESS;
		}
		else if (ADD_RESUBMIT_CAD_REJECTED.equals(event) ) {
			errorEvent = ADD_RESUBMIT_CAD_REJECTED_ERROR;
		}
		else if (ADD_RESUBMIT_CAD_DRAFT.equals(event) ) {
			errorEvent = ADD_RESUBMIT_CAD_DRAFT_ERROR;
		}
		else if (RESUBMIT_EDIT_CAD_REJECTED.equals(event) ) {
			errorEvent = RESUBMIT_EDIT_CAD_REJECTED_ERROR;
		}
		else if (RESUBMIT_EDIT_CAD_DRAFT.equals(event) ) {
			errorEvent = RESUBMIT_EDIT_CAD_DRAFT_ERROR;
		}
		else if (CHECKER_REJECT_EDIT.equals(event)) {
			errorEvent = CHECKER_PROCESS_CREATE;
		}else if(CHECKER_REJECT_INSERT.equals(event)){
			errorEvent = CHECKER_PROCESS_CREATE_INSERT;
		}else if(MAKER_CONFIRM_RESUBMIT_EDIT.equals(event)){
			errorEvent = "errorReSubmitEvent" ;
		}else if(POPULATE_FIELD.equals(event)){
			errorEvent = PREPARE_CREATE_RELATIONSHIP_MANAGER ;
		}
		else if (LIST_RELATIONSHIP_MANAGER.equals(event))
			errorEvent = "listError";
		
		else if (CHECKER_LIST_RELATIONSHIP_MANAGER.equals(event))
			errorEvent = "checkerListError";
		
		else if (CHECKER_APPROVE_EDIT.equals(event))
			errorEvent = "errorOnApproval";
		
		else if(ADD_CAD.equals(event))
			errorEvent = ADD_CAD_ERROR;
		return errorEvent;
	}
}
