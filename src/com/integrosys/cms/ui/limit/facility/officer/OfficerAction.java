package com.integrosys.cms.ui.limit.facility.officer;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainAction;

public class OfficerAction extends FacilityMainAction {

	protected ICommand[] getCommandChain(String event) {
		if (EVENT_LIST.equals(event) || EVENT_LIST_WO_FRAME.equals(event) || TAB_OFFICER_CHECKER.equals(event)
				|| TAB_OFFICER_VIEW_WO_FRAME.equals(event) || TAB_OFFICER_VIEW.equals(event)
				||TAB_OFFICER_CHECKER_PROCESS.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ReadListOfficerCommand"),
					(ICommand) getNameCommandMap().get("CompareOfficerListCommand")};
		}
		else if (EVENT_NAVIGATE.equals(event) || EVENT_NAVIGATE_WO_FRAME.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("NavigateFacilityCommand"),
                    (ICommand) getNameCommandMap().get("FacilityObjectValidatorCommand")};
		}
		else if (EVENT_NAVIGATE_WO_FRAME_CHECKER.equals(event) || EVENT_NAVIGATE_VIEW_WO_FRAME.equals(event)
				|| EVENT_NAVIGATE_VIEW.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ReadListOfficerCommand"),
					(ICommand) getNameCommandMap().get("CompareOfficerListCommand"),
					(ICommand) getNameCommandMap().get("NavigateFacilityCommand") };
		}
		else if (EVENT_SAVE.equals(event) || EVENT_SAVE_WO_FRAME.equals(event)) {
            return new ICommand[] { (ICommand) getNameCommandMap().get("SaveOfficerDetailToSessionCommand")
            	//	,(ICommand) getNameCommandMap().get("FacilityObjectValidatorCommand")
            		};
        }
		else if (EVENT_EDIT.equals(event) || EVENT_EDIT_WO_FRAME.equals(event)
				|| EVENT_VIEW_DETAIL_CHECKER.equals(event) || EVENT_VIEW_DETAIL_ONLY.equals(event)
				|| EVENT_VIEW_DETAIL_ONLY_WITH_FRAME.equals(event)||EVENT_VIEW_DETAIL_CHECKER_PROCESS.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ReadOfficerCommand") };
		}
		// before saving the whole object, we need to save the current tab first
		else if (EVENT_SAVE_WHOLE_OBJ.equals(event) || EVENT_SAVE_WHOLE_OBJ_WO_FRAME.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("SaveFacilityMainCommand") };
		}
		else if (EVENT_DELETE.equals(event) || EVENT_DELETE_WO_FRAME.equals(event)) {
            return new ICommand[] { (ICommand) getNameCommandMap().get("DeleteOfficerCommand"),
                    (ICommand) getNameCommandMap().get("FacilityObjectValidatorCommand")};
        }
		else if (EVENT_SUBMIT.equals(event) || EVENT_SUBMIT_WO_FRAME.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("FacilityObjectValidatorCommand"),
					(ICommand) getNameCommandMap().get("SubmitFacilityMainCommand") };
		}
		else if (EVENT_UPDATE.equals(event) || EVENT_UPDATE_WO_FRAME.equals(event)) {
            return new ICommand[] { (ICommand) getNameCommandMap().get("SaveOfficerDetailToSessionCommand")
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
		else if (EVENT_ADD.equals(event) || EVENT_EDIT.equals(event)) {
			forward = INPUT;
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
			forward = TAB_OFFICER_WO_FRAME;
		}
		else if (EVENT_SAVE.equals(event) || EVENT_UPDATE.equals(event) || EVENT_DELETE.equals(event)) {
			forward = TAB_OFFICER;
		}
		else if (EVENT_CLOSE.equals(event)) {
			forward = EVENT_CLOSE;
		}
		else if (TAB_OFFICER_CHECKER.equals(event)) {
			forward = TAB_OFFICER_CHECKER;
		}
		else if (TAB_OFFICER_CHECKER_PROCESS.equals(event)) {
			forward = TAB_OFFICER_CHECKER_PROCESS;
		}
		else if (EVENT_VIEW_DETAIL_CHECKER.equals(event)) {
			forward = EVENT_VIEW_DETAIL_CHECKER;
		}
		else if (EVENT_VIEW_DETAIL_CHECKER_PROCESS.equals(event)) {
			forward = EVENT_VIEW_DETAIL_CHECKER_PROCESS;
		}
		else if (TAB_OFFICER_VIEW_WO_FRAME.equals(event)) {
			forward = TAB_OFFICER_VIEW_WO_FRAME;
		}
		else if (EVENT_VIEW_DETAIL_ONLY.equals(event)) {
			forward = EVENT_VIEW_DETAIL_ONLY;
		}
		else if (TAB_OFFICER_VIEW.equals(event)) {
			forward = TAB_OFFICER_VIEW;
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
		return OfficerFormValidator.validateInput((OfficerForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		return (EVENT_SAVE.equals(event) || EVENT_UPDATE.equals(event) || EVENT_SAVE_WO_FRAME.equals(event) || EVENT_UPDATE_WO_FRAME
				.equals(event));

	}

	protected String getErrorEvent(String event) {
		if (EVENT_SAVE.equals(event)) {
			return EVENT_ADD;
		}
		if (EVENT_UPDATE.equals(event)) {
			return EVENT_EDIT;
		}
		else if (EVENT_SAVE_WO_FRAME.equals(event)) {
			return EVENT_ADD_WO_FRAME;
		}
		else if (EVENT_UPDATE_WO_FRAME.equals(event)) {
			return EVENT_EDIT_WO_FRAME;
		}
		return null;
	}
}
