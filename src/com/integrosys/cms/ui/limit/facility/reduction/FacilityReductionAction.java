package com.integrosys.cms.ui.limit.facility.reduction;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainAction;

public class FacilityReductionAction extends FacilityMainAction {
	protected ICommand[] getCommandChain(String event) {
		if (EVENT_LIST.equals(event) || EVENT_LIST_WO_FRAME.equals(event) ||
				TAB_FACILITY_REDUCTION_CHECKER.equals(event) ||
				TAB_FACILITY_REDUCTION_CHECKER_PROCESS.equals(event) ||
				TAB_FACILITY_REDUCTION_VIEW.equals(event) ||
				TAB_FACILITY_REDUCTION_VIEW_WO_FRAME.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ReadListReductionCommand")};
		}	
		else if (EVENT_EDIT.equals(event) || EVENT_EDIT_WO_FRAME.equals(event)
				|| EVENT_VIEW_DETAIL_CHECKER.equals(event) || EVENT_VIEW_DETAIL_ONLY.equals(event)
				|| EVENT_VIEW_DETAIL_ONLY_WITH_FRAME.equals(event)||EVENT_VIEW_DETAIL_CHECKER_PROCESS.equals(event)
				|| EVENT_ERROR.equals(event) || EVENT_ERROR_WO_FRAME.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ReadReductionCommand") };
		}	
		else if (EVENT_UPDATE.equals(event) || EVENT_UPDATE_WO_FRAME.equals(event)) {
            return new ICommand[] { (ICommand) getNameCommandMap().get("SaveReductionDetailToSessionCommand")	};
        }		
		return super.getCommandChain(event);
	}
	
	protected IPage getNextPage(String event, HashMap map, HashMap exceptionMap) {
		String forward = "";
		
		if (EVENT_ADD.equals(event) || EVENT_EDIT.equals(event) || EVENT_ERROR.equals(event)) {
			forward = INPUT;
		} 
		else if (EVENT_EDIT_WO_FRAME.equals(event) || EVENT_ERROR_WO_FRAME.equals(event)) {
			forward = INPUT_WO_FRAME;
		}		
		else if (EVENT_UPDATE_WO_FRAME.equals(event)) {
			forward = TAB_FACILITY_REDUCTION+"_no_frame";
		}
		else if (EVENT_UPDATE.equals(event)) {
			forward = TAB_FACILITY_REDUCTION;
		}		
		else if (EVENT_LIST.equals(event) || EVENT_LIST_WO_FRAME.equals(event) ||
				TAB_FACILITY_REDUCTION_CHECKER.equals(event) || 
				TAB_FACILITY_REDUCTION_CHECKER_PROCESS.equals(event) ||
				TAB_FACILITY_REDUCTION_VIEW.equals(event) ||
				TAB_FACILITY_REDUCTION_VIEW_WO_FRAME.equals(event)||
				EVENT_VIEW_DETAIL_CHECKER.equals(event) ||
				EVENT_VIEW_DETAIL_CHECKER_PROCESS.equals(event) ||
				EVENT_VIEW_DETAIL_ONLY.equals(event) ||
				EVENT_VIEW_DETAIL_ONLY_WITH_FRAME.equals(event)) {
			forward = event;
		}		
		else {
			return super.getNextPage(event, map, exceptionMap);
		}
		
		DefaultLogger.debug(this, "The name of struts forward is " + forward);

		Page page = new Page();
		page.setPageReference(forward);

		return page;		
	}
	
	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		DefaultLogger.debug(this, "Inside validate Input child class");
		return FacilityReductionFormValidator.validateInput((FacilityReductionForm)aForm, locale);
	}
	
	protected boolean isValidationRequired(String event) {
		return (EVENT_SAVE.equals(event) || EVENT_UPDATE.equals(event) || 
				EVENT_SAVE_WO_FRAME.equals(event) || EVENT_UPDATE_WO_FRAME.equals(event));
	}	
	
	protected String getErrorEvent(String event) {
		if (EVENT_UPDATE.equals(event)) {
			return EVENT_ERROR;
		}
		else if (EVENT_UPDATE_WO_FRAME.equals(event)) {
			return EVENT_ERROR_WO_FRAME;
		}
		return null;
	}	
}
