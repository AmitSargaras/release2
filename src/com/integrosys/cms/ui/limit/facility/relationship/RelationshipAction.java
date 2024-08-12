package com.integrosys.cms.ui.limit.facility.relationship;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainAction;

public class RelationshipAction extends FacilityMainAction {

	protected ICommand[] getCommandChain(String event) {
		if (EVENT_LIST.equals(event) || EVENT_LIST_WO_FRAME.equals(event) || TAB_RELATIONSHIP_CHECKER.equals(event)
				|| TAB_RELATIONSHIP_VIEW_WO_FRAME.equals(event) || TAB_RELATIONSHIP_VIEW.equals(event)
				||TAB_RELATIONSHIP_CHECKER_PROCESS.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ReadListRelationshipCommand"),
					(ICommand) getNameCommandMap().get("CompareRelationshipListCommand") };
		}
		else if (EVENT_NAVIGATE.equals(event) || EVENT_NAVIGATE_WO_FRAME.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("NavigateFacilityCommand"),
                    (ICommand) getNameCommandMap().get("FacilityObjectValidatorCommand")};
		}
		else if (EVENT_NAVIGATE_WO_FRAME_CHECKER.equals(event) || EVENT_NAVIGATE_VIEW_WO_FRAME.equals(event)
				|| EVENT_NAVIGATE_VIEW.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ReadListRelationshipCommand"),
					(ICommand) getNameCommandMap().get("CompareRelationshipListCommand"),
					(ICommand) getNameCommandMap().get("NavigateFacilityCommand") };
		}
		else if (EVENT_SAVE.equals(event) || EVENT_SAVE_WO_FRAME.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("SaveRelationshipDetailToSessionCommand")
				//	,(ICommand) getNameCommandMap().get("FacilityObjectValidatorCommand")
                    };
		}
		else if (EVENT_EDIT.equals(event) || EVENT_EDIT_WO_FRAME.equals(event)
				|| EVENT_VIEW_DETAIL_CHECKER.equals(event) || EVENT_VIEW_DETAIL_ONLY.equals(event)
				|| EVENT_VIEW_DETAIL_ONLY_WITH_FRAME.equals(event)||EVENT_VIEW_DETAIL_CHECKER_PROCESS.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ReadRelationshipCommand") };
		}
		// before saving the whole object, we need to save the current tab first
		else if (EVENT_SAVE_WHOLE_OBJ.equals(event) || EVENT_SAVE_WHOLE_OBJ_WO_FRAME.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("SaveFacilityMainCommand"), };
		}
		else if (EVENT_DELETE.equals(event) || EVENT_DELETE_WO_FRAME.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("DeleteRelationshipCommand"),
                    (ICommand) getNameCommandMap().get("FacilityObjectValidatorCommand")};
		}
		else if (EVENT_SUBMIT.equals(event) || EVENT_SUBMIT_WO_FRAME.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("FacilityObjectValidatorCommand"),
					(ICommand) getNameCommandMap().get("SubmitFacilityMainCommand"), };
		}
		else if (EVENT_UPDATE.equals(event) || EVENT_UPDATE_WO_FRAME.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("SaveRelationshipDetailToSessionCommand")
				//	,(ICommand) getNameCommandMap().get("FacilityObjectValidatorCommand")
                    };
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
		else if (EVENT_SEARCH_CUSTOMER.equals(event) || EVENT_SEARCH_CUSTOMER_WO_FRAME.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("SearchCustomerCommand") };
		}
		else if (EVENT_ADD.equals(event) || EVENT_ADD_WO_FRAME.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("PrepareAddCommand") };
		}

		// Unrecognized event.
		return null;
	}

	protected IPage getNextPage(String event, HashMap map, HashMap exceptionMap) {
		String forward = "";
		if (EVENT_LIST.equals(event)) {
			forward = EVENT_LIST;
		}
		else if (EVENT_NAVIGATE.equals(event) || EVENT_NAVIGATE_WO_FRAME.equals(event)
				|| EVENT_NAVIGATE_WO_FRAME_CHECKER.equals(event) || EVENT_NAVIGATE_VIEW_WO_FRAME.equals(event)
				|| EVENT_NAVIGATE_VIEW.equals(event)) {
			return super.getNextPage(event, map, exceptionMap);
		}
		else if (EVENT_PREPARE_SEARCH_CUSTOMER.equals(event)) {
			forward = EVENT_SEARCH_CUSTOMER;
		}
		else if (EVENT_ADD.equals(event) || EVENT_EDIT.equals(event)) {
			forward = INPUT;
		}
		else if (EVENT_PREPARE_SEARCH_CUSTOMER_WO_FRAME.equals(event)) {
			forward = EVENT_SEARCH_CUSTOMER_WO_FRAME;
		}
		else if (EVENT_ADD_WO_FRAME.equals(event) || EVENT_EDIT_WO_FRAME.equals(event)) {
			forward = INPUT_WO_FRAME;
		}
		else if (EVENT_SAVE_WHOLE_OBJ.equals(event) || EVENT_SAVE_WHOLE_OBJ_WO_FRAME.equals(event)
				|| EVENT_SUBMIT.equals(event) || EVENT_SUBMIT_WO_FRAME.equals(event) || EVENT_APPROVE.equals(event)
				|| EVENT_REJECT.equals(event)) {
			return super.getNextPage(event, map, exceptionMap);
		}
		else if (EVENT_LIST_WO_FRAME.equals(event)) {
			forward = EVENT_LIST_WO_FRAME;
		}
		else if (EVENT_SAVE_WO_FRAME.equals(event) || EVENT_UPDATE_WO_FRAME.equals(event)
				|| EVENT_DELETE_WO_FRAME.equals(event)) {
			forward = TAB_RELATIONSHIP_WO_FRAME;
		}
		else if (EVENT_SAVE.equals(event) || EVENT_UPDATE.equals(event) || EVENT_DELETE.equals(event)) {
			forward = TAB_RELATIONSHIP;
		}
		else if (EVENT_CLOSE.equals(event)) {
			forward = EVENT_CLOSE;
		}
		else if (TAB_RELATIONSHIP_CHECKER.equals(event)) {
			forward = TAB_RELATIONSHIP_CHECKER;
		}
		else if (TAB_RELATIONSHIP_CHECKER_PROCESS.equals(event)) {
			forward = TAB_RELATIONSHIP_CHECKER_PROCESS;
		}
		else if (EVENT_VIEW_DETAIL_CHECKER.equals(event)) {
			forward = EVENT_VIEW_DETAIL_CHECKER;
		}
		else if (TAB_RELATIONSHIP_VIEW_WO_FRAME.equals(event)) {
			forward = TAB_RELATIONSHIP_VIEW_WO_FRAME;
		}
		else if (EVENT_VIEW_DETAIL_ONLY.equals(event)) {
			forward = EVENT_VIEW_DETAIL_ONLY;
		}
		else if (EVENT_VIEW_DETAIL_CHECKER_PROCESS.equals(event)) {
			forward = EVENT_VIEW_DETAIL_CHECKER_PROCESS;
		}
		else if (EVENT_SEARCH_CUSTOMER.equals(event)) {
			forward = EVENT_SEARCH_CUSTOMER;
		}
		else if (EVENT_SEARCH_CUSTOMER_WO_FRAME.equals(event)) {
			forward = EVENT_SEARCH_CUSTOMER_WO_FRAME;
		}
		else if (TAB_RELATIONSHIP_VIEW.equals(event)) {
			forward = TAB_RELATIONSHIP_VIEW;
		}
		else if (EVENT_VIEW_DETAIL_ONLY_WITH_FRAME.equals(event)) {
			forward = EVENT_VIEW_DETAIL_ONLY_WITH_FRAME;
		}

		DefaultLogger.debug(this, "The name of struts forward is " + forward);

		Page page = new Page();
		page.setPageReference(forward);

		return page;
	}

	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		DefaultLogger.debug(this, "Inside validate Input child class");
		return RelationshipFormValidator.validateInput((RelationshipForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		return (EVENT_SAVE.equals(event) || EVENT_UPDATE.equals(event) || EVENT_SAVE_WO_FRAME.equals(event)
				|| EVENT_UPDATE_WO_FRAME.equals(event) || EVENT_SEARCH_CUSTOMER.equals(event)
				|| EVENT_SEARCH_CUSTOMER_WO_FRAME.equals(event) || EVENT_ADD.equals(event) || EVENT_ADD_WO_FRAME
				.equals(event));

	}

	protected String getErrorEvent(String event) {
		if (EVENT_SAVE.equals(event)) {
			return EVENT_ADD;
		}
		else if (EVENT_UPDATE.equals(event)) {
			return EVENT_EDIT;
		}
		else if (EVENT_SAVE_WO_FRAME.equals(event)) {
			return EVENT_ADD_WO_FRAME;
		}
		else if (EVENT_UPDATE_WO_FRAME.equals(event)) {
			return EVENT_EDIT_WO_FRAME;
		}
		else if (EVENT_SEARCH_CUSTOMER.equals(event) || EVENT_ADD.equals(event)) {
			return EVENT_PREPARE_SEARCH_CUSTOMER;
		}
		else if (EVENT_SEARCH_CUSTOMER_WO_FRAME.equals(event)) {
			return EVENT_PREPARE_SEARCH_CUSTOMER_WO_FRAME;
		}
		else if(EVENT_ADD_WO_FRAME.equals(event)){
			return EVENT_SEARCH_CUSTOMER_WO_FRAME;
		}
		return null;
	}
}
