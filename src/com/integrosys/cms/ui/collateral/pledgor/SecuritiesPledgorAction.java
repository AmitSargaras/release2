package com.integrosys.cms.ui.collateral.pledgor;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.collateral.ReadReturnCollateralCommand;
import com.integrosys.cms.ui.collateral.marketablesec.MarketableSecAction;

public class SecuritiesPledgorAction extends MarketableSecAction {

	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (EVENT_PREPARE_SEARCH.equals(event)) {
			//do nothing
		}
		else if (EVENT_SEARCH_PLEDGOR.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SearchPledgorCommand();
		}
        else if (EVENT_EDIT_PLEDGOR_PREPARE.equals(event)) {
            objArray = new ICommand[1];
			objArray[0] = new ReadPledgorCommand();
        }
        else if (EVENT_EDIT_PLEDGOR.equals(event)) {
            objArray = new ICommand[1];
			objArray[0] = new UpdatePledgorCommand();
        }
        else if (EVENT_SAVE_PLEDGOR.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new SavePledgorCommand();
			objArray[1] = new ReadReturnCollateralCommand();
		}
		else if (EVENT_READ_RETURN.equals(event) || EVENT_CANCEL.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadReturnCollateralCommand();
		}
		// Unrecognized Command
		return objArray;
	}

	public IPage getNextPage(String event, HashMap map, HashMap exceptionMap) {
		String forward = "";

		if (EVENT_PREPARE_SEARCH.equals(event)) {
			forward = EVENT_PREPARE_SEARCH;
		}
		else if (EVENT_SEARCH_PLEDGOR.equals(event)) {
			forward = EVENT_SEARCH_PLEDGOR;
		}
        else if (EVENT_EDIT_PLEDGOR_PREPARE.equals(event)) {
            forward = EVENT_EDIT_PLEDGOR_PREPARE;
        }
        else if (EVENT_SAVE_PLEDGOR.equals(event) || EVENT_EDIT_PLEDGOR.equals(event) || EVENT_READ_RETURN.equals(event) || EVENT_CANCEL.equals(event)) {
			String subtype = (String) map.get("subtype");
            if (subtype == null) {
				// todo forward to error page after populating the exceptionMap
				throw new RuntimeException("URL passed is wrong");
			}
			else if (EVENT_READ_RETURN.equals(event)) {
				forward = subtype + "_" + (String) map.get("from_event");
			}
            else {
				forward = subtype + "_update_frame";
			}
		}

		DefaultLogger.debug(this, "The name of struts forward is " + forward);

		Page page = new Page();
		page.setPageReference(forward);

		return page;
	}

	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		DefaultLogger.debug(this, "Inside validate Input child class");
		return PledgorFormValidator.validateInput((PledgorForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		return (EVENT_SEARCH_PLEDGOR.equals(event) || EVENT_SAVE_PLEDGOR.equals(event));
	}

	protected String getErrorEvent(String event) {
		if (EVENT_SEARCH_PLEDGOR.equals(event)) {
			return EVENT_PREPARE_SEARCH;
		}
		else if (EVENT_SAVE_PLEDGOR.equals(event)) {
			return EVENT_SEARCH_PLEDGOR;
		}
		return null;
	}

	public static final String EVENT_ERROR_SAVE = "error_save";
}
