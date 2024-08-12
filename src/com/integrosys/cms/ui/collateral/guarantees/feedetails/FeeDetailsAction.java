package com.integrosys.cms.ui.collateral.guarantees.feedetails;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.collateral.ReadReturnCollateralCommand;
import com.integrosys.cms.ui.collateral.guarantees.gtegovtlink.GteGovtLinkAction;

/**
 * Created by IntelliJ IDEA. User: jitendra Date: May 7, 2007 Time: 6:01:28 PM
 * To change this template use File | Settings | File Templates.
 */

public class FeeDetailsAction extends GteGovtLinkAction {

	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = new ICommand[1];
		if (EVENT_CREATE.equals(event)) {
			objArray[0] = new AddFeeDetailsCommand();
		}
		else if (EVENT_READ.equals(event)) {
			objArray[0] = new ReadFeeDetailsCommand();
		}
		else if (EVENT_PREPARE.equals(event)) {
			objArray[0] = new PrepareFeeDetailsCommand();
		}
		else if (EVENT_READ_RETURN.equals(event) || EVENT_CANCEL.equals(event)) {
			objArray[0] = new ReadReturnCollateralCommand();
		}
		else if (EVENT_PREPARE_UPDATE.equals(event)) {
			objArray[0] = new ReadFeeDetailsCommand();
		}
		else if (EVENT_UPDATE.equals(event)) {
			objArray[0] = new UpdateFeeDetailsCommand();
		}
		else {
			objArray = super.getCommandChain(event);
		}

		return objArray;
	}

	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		DefaultLogger.debug(this, "VALIDATION REQUIRED...");
		return FeeDetailsValidator.validateInput((FeeDetailsForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (EVENT_CREATE.equals(event) || EVENT_UPDATE.equals(event)) {
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = event;
		if (EVENT_CREATE.equals(event)) {
			errorEvent = EVENT_PREPARE;
		}
		else if (EVENT_UPDATE.equals(event)) {
			errorEvent = EVENT_PREPARE;
		}

		return errorEvent;
	}

	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		DefaultLogger.debug(this, "FeeDetailsAction event = " + event);

		if (EVENT_CREATE.equals(event) || EVENT_UPDATE.equals(event) || EVENT_READ_RETURN.equals(event)
				|| EVENT_CANCEL.equals(event)) {

			// aPage.setPageReference("GteGovtLink_update");

			DefaultLogger.debug(this, "ResultMap is :" + resultMap);

			String subtype = (String) resultMap.get("subtype");

			DefaultLogger.debug(this, "FeeDetailsAction subtype = " + subtype);

			if (subtype == null) {
				throw new RuntimeException("URL passed is wrong");
			}
			else if (EVENT_READ_RETURN.equals(event)) {
				aPage.setPageReference(subtype + "_" + (String) resultMap.get("from_event"));
			}
			else {
				aPage.setPageReference("GteGovtLink_update");
			}

		}
		else if (event.equals(EVENT_EDIT) || event.equals(EVENT_FORWARD)) {
			DefaultLogger.debug(this, "forwardPage is: " + resultMap.get("forwardPage"));
			aPage.setPageReference((String) resultMap.get("forwardPage"));
			return aPage;
		}
		else {
			aPage.setPageReference(event);
		}
		return aPage;
	}

}
