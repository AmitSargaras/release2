package com.integrosys.cms.ui.collateral.pledge;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.collateral.CollateralAction;
import com.integrosys.cms.ui.collateral.ReadReturnCollateralCommand;

public class PledgeAction {
	public static ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (CollateralAction.EVENT_PREPARE_ADD_PLEDGE.equals(event) || 
				CollateralAction.EVENT_REFRESH.equals(event) || 
				CollateralAction.EVENT_VIEW.equals(event) || 
				CollateralAction.EVENT_EDIT_PLEDGE_PREPARE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadDetailPledgeCommand();
		}
		else if (CollateralAction.EVENT_CREATE_PLEDGE.equals(event) || 
				CollateralAction.EVENT_UPDATE_PLEDGE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SavePledgeCommand();
		}
		else if (CollateralAction.EVENT_CANCEL.equals(event) ||
				CollateralAction.EVENT_READ_RETURN.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadReturnCollateralCommand();
		}

		DefaultLogger.debug(PledgeAction.class.getName(), "*******" + event + "================" + objArray);
		return (objArray);
	}

	public static IPage getNextPage(String event, HashMap map, HashMap exceptionMap) {
		String forward = "";
		if (CollateralAction.EVENT_PREPARE_ADD_PLEDGE.equals(event) || 
				CollateralAction.EVENT_REFRESH.equals(event) ||
				CollateralAction.EVENT_EDIT_PLEDGE_PREPARE.equals(event)) {
			forward = CollateralAction.EVENT_PREPARE_ADD_PLEDGE;
		}
		else if (CollateralAction.EVENT_VIEW.equals(event)) {
			forward = CollateralAction.EVENT_VIEW;
		}
		else if (CollateralAction.EVENT_CREATE_PLEDGE.equals(event) || 
				CollateralAction.EVENT_UPDATE_PLEDGE.equals(event) ||
				CollateralAction.EVENT_READ_RETURN.equals(event) || 
				CollateralAction.EVENT_CANCEL.equals(event)) {
			String subtype = (String) map.get("subtype");
			if (subtype == null) {
				// todo forward to error page after populating the exceptionMap
				throw new RuntimeException("URL passed is wrong");
			}
			else if (CollateralAction.EVENT_READ_RETURN.equals(event)) {
				forward = subtype + "_" + (String) map.get("from_event");
			}
			else {
				forward = subtype + "_update";
			}
		}
		DefaultLogger.debug(PledgeAction.class.getName(), "The name of struts forward is " + forward);

		Page page = new Page();
		page.setPageReference(forward);

		return page;
	}

	public static ActionErrors validateInput(ActionForm aForm, Locale locale) {
		return PledgeFormValidator.validateInput((PledgeForm) aForm, locale);
	}

	public static boolean isValidationRequired(String event) {
		return (CollateralAction.EVENT_CREATE_PLEDGE.equals(event) || 
				CollateralAction.EVENT_UPDATE_PLEDGE.equals(event));
	}

	public static String getErrorEvent(String event) {
		if (CollateralAction.EVENT_CREATE_PLEDGE.equals(event)) {
			return CollateralAction.EVENT_PREPARE_ADD_PLEDGE;
		}
		else if (CollateralAction.EVENT_UPDATE_PLEDGE.equals(event)) {
			return CollateralAction.EVENT_EDIT_PLEDGE_PREPARE;
		}
		return null;
	}
}
