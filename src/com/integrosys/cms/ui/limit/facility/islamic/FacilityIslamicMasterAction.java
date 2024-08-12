package com.integrosys.cms.ui.limit.facility.islamic;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainAction;

public class FacilityIslamicMasterAction extends FacilityMainAction {
	
	protected ICommand[] getCommandChain(String event) {
		if (EVENT_VIEW_EDIT.equals(event) || EVENT_VIEW_EDIT_WO_FRAME.equals(event) 
				|| TAB_ISLAMIC_MASTER_CHECKER.equals(event) || TAB_ISLAMIC_MASTER_VIEW_WO_FRAME.equals(event) 
				|| TAB_ISLAMIC_MASTER_VIEW.equals(event)||TAB_ISLAMIC_MASTER_CHECKER_PROCESS.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ReadFacilityIslamicMasterCommand")};
		}
		else if (EVENT_NAVIGATE.equals(event) || EVENT_NAVIGATE_WO_FRAME.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("SaveFacilityIslamicMasterToSessionCommand"),
					(ICommand) getNameCommandMap().get("NavigateFacilityCommand"),
                    (ICommand) getNameCommandMap().get("FacilityObjectValidatorCommand")};
		}
		else if (EVENT_NAVIGATE_WO_FRAME_CHECKER.equals(event) || EVENT_NAVIGATE_VIEW_WO_FRAME.equals(event)
				|| EVENT_NAVIGATE_VIEW.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("NavigateFacilityCommand") };
		}
		// before saving the whole object, we need to save the current tab first
		else if (EVENT_SAVE_WHOLE_OBJ.equals(event) || EVENT_SAVE_WHOLE_OBJ_WO_FRAME.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("SaveFacilityIslamicMasterToSessionCommand"),
					(ICommand) getNameCommandMap().get("SaveFacilityMainCommand"), };
		}
		else if (EVENT_SUBMIT.equals(event) || EVENT_SUBMIT_WO_FRAME.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("SaveFacilityIslamicMasterToSessionCommand"),
					(ICommand) getNameCommandMap().get("FacilityObjectValidatorCommand"),
					(ICommand) getNameCommandMap().get("SubmitFacilityMainCommand"), };
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
		else if (EVENT_ERROR.equals(event) || EVENT_ERROR_WO_FRAME.equals(event)) {
			return null;
		}

		// Unrecognized Command
		return null;
	}

	protected IPage getNextPage(String event, HashMap map, HashMap exceptionMap) {
		String forward = "";

		if (EVENT_VIEW_EDIT.equals(event) || EVENT_ERROR.equals(event)) {
			forward = EVENT_VIEW_EDIT;
		}
		else if (EVENT_NAVIGATE.equals(event) || EVENT_NAVIGATE_WO_FRAME.equals(event)
				|| EVENT_NAVIGATE_WO_FRAME_CHECKER.equals(event) || EVENT_NAVIGATE_VIEW_WO_FRAME.equals(event)
				|| EVENT_NAVIGATE_VIEW.equals(event)) {
			return super.getNextPage(event, map, exceptionMap);
		}
		else if (EVENT_SAVE_WHOLE_OBJ.equals(event) || EVENT_SAVE_WHOLE_OBJ_WO_FRAME.equals(event)
				|| EVENT_SUBMIT.equals(event) || EVENT_SUBMIT_WO_FRAME.equals(event) || EVENT_APPROVE.equals(event)
				|| EVENT_REJECT.equals(event)) {
			return super.getNextPage(event, map, exceptionMap);
		}
		else if (EVENT_VIEW_EDIT_WO_FRAME.equals(event) || EVENT_ERROR_WO_FRAME.equals(event)) {
			forward = EVENT_VIEW_EDIT_WO_FRAME;
		}
		else if (EVENT_CLOSE.equals(event)) {
			forward = EVENT_CLOSE;
		}
		else if (TAB_ISLAMIC_MASTER_CHECKER.equals(event)) {
			forward = TAB_ISLAMIC_MASTER_CHECKER;
		}
		else if (TAB_ISLAMIC_MASTER_CHECKER_PROCESS.equals(event)) {
			forward = TAB_ISLAMIC_MASTER_CHECKER_PROCESS;
		}		
		else if (TAB_ISLAMIC_MASTER_VIEW_WO_FRAME.equals(event)) {
			forward = TAB_ISLAMIC_MASTER_VIEW_WO_FRAME;
		}
		else if (TAB_ISLAMIC_MASTER_VIEW.equals(event)) {
			forward = TAB_ISLAMIC_MASTER_VIEW;
		}
		
		DefaultLogger.debug(this, "The name of struts forward is " + forward);

		Page page = new Page();
		page.setPageReference(forward);

		return page;
	}

	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		DefaultLogger.debug(this, "Inside validate Input child class");
		return FacilityIslamicMasterFormValidator.validateInput((FacilityIslamicMasterForm) aForm, locale);
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
}
