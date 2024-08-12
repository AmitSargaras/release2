package com.integrosys.cms.ui.limit.facility.rentalrenewal;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainAction;

public class FacilityIslamicRentalRenewalAction extends FacilityMainAction {
	protected ICommand[] getCommandChain(String event) {
		if (EVENT_VIEW_EDIT.equals(event) || EVENT_VIEW_EDIT_WO_FRAME.equals(event) 
				|| TAB_ISLAMIC_RENTAL_RENEWAL_CHECKER.equals(event) || TAB_ISLAMIC_RENTAL_RENEWAL_VIEW_WO_FRAME.equals(event) 
				|| TAB_ISLAMIC_RENTAL_RENEWAL_VIEW.equals(event)|| TAB_ISLAMIC_RENTAL_RENEWAL_CHECKER_PROCESS.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("ReadFacilityIslamicRentalRenewalCommand")};
		}
		else if (EVENT_NAVIGATE.equals(event) || EVENT_NAVIGATE_WO_FRAME.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("SaveFacilityIslamicRentalRenewalToSessionCommand"),
					(ICommand) getNameCommandMap().get("NavigateFacilityCommand"),
                    (ICommand) getNameCommandMap().get("FacilityObjectValidatorCommand")};
		}
		// before saving the whole object, we need to save the current tab first
		else if (EVENT_SAVE_WHOLE_OBJ.equals(event) || EVENT_SAVE_WHOLE_OBJ_WO_FRAME.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("SaveFacilityIslamicRentalRenewalToSessionCommand"),
					(ICommand) getNameCommandMap().get("SaveFacilityMainCommand"), };
		}
		else if (EVENT_SUBMIT.equals(event) || EVENT_SUBMIT_WO_FRAME.equals(event)) {
			return new ICommand[] { (ICommand) getNameCommandMap().get("SaveFacilityIslamicRentalRenewalToSessionCommand"),
					(ICommand) getNameCommandMap().get("FacilityObjectValidatorCommand"),
					(ICommand) getNameCommandMap().get("SubmitFacilityMainCommand"), };
		} else {
			return super.getCommandChain(event);
		}
	
		// Unrecognized Command
		//return null;		
	}

	protected IPage getNextPage(String event, HashMap map, HashMap exceptionMap) {
		String forward = "";

		if (TAB_ISLAMIC_RENTAL_RENEWAL_CHECKER.equals(event) ||
				TAB_ISLAMIC_RENTAL_RENEWAL_VIEW_WO_FRAME.equals(event) ||
				TAB_ISLAMIC_RENTAL_RENEWAL_VIEW.equals(event) ||
				TAB_ISLAMIC_RENTAL_RENEWAL_CHECKER_PROCESS.equals(event)) {
			forward = event;
		} else if (EVENT_VIEW_EDIT.equals(event) || EVENT_ERROR.equals(event)) {
				forward = EVENT_VIEW_EDIT;				
		} else {
			return super.getNextPage(event, map, exceptionMap);
		}
		
		DefaultLogger.debug(this, "The name of struts forward is " + forward);

		Page page = new Page();
		page.setPageReference(forward);

		return page;
	}

	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		DefaultLogger.debug(this, "Inside validate Input child class");
		return FacilityIslamicRentalRenewalFormValidator.validateInput((FacilityIslamicRentalRenewalForm) aForm, locale);
	}
}
