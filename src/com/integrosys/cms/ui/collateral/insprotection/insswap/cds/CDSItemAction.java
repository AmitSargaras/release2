//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral.insprotection.insswap.cds;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.collateral.ReadReturnCollateralCommand;
import com.integrosys.cms.ui.collateral.insprotection.insswap.InsSwapAction;

// this needs to extend from Collateral Action to fit the IPin interface. This is a workaround till IPin is removed from
// Marker interface.

public class CDSItemAction extends InsSwapAction {

	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = new ICommand[1];
		if (EVENT_CREATE.equals(event)) {
			objArray[0] = new AddCDSItemCommand();
		}
		else if (EVENT_PREPARE.equals(event)) {
			objArray[0] = new PrepareCDSItemCommand();
		}
		else if (EVENT_PREPARE_UPDATE_SUB.equals(event) || EVENT_READ.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareCDSItemCommand();
			objArray[1] = new ReadCDSItemCommand();
		}
		else if (EVENT_UPDATE.equals(event)) {
			objArray[0] = new UpdateCDSItemCommand();
		}
		else if (EVENT_READ_RETURN.equals(event) || EVENT_CANCEL.equals(event)) {
			objArray[0] = new ReadReturnCollateralCommand();
		}

		return objArray;
	}

	/**
	 * This method is called only for create and Update command to validate the
	 * form and return the ActionErrors object.
	 * 
	 * @param aForm is of type ActionForm
	 * @param locale of type Locale
	 * @return ActionErrors
	 */
	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		DefaultLogger.debug(this, "VALIDATION REQUIRED...");
		return CDSItemValidator.validateInput((CDSItemForm) aForm, locale);
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
		if (EVENT_CREATE.equals(event) || EVENT_UPDATE.equals(event) || EVENT_READ_RETURN.equals(event)
				|| EVENT_CANCEL.equals(event)) {
			if (EVENT_READ_RETURN.equals(event)) {
				aPage.setPageReference("return_" + (String) resultMap.get("from_event"));
			}
			else {
				aPage.setPageReference(EVENT_UPDATE_RETURN);
			}
		}
		else if (EVENT_PREPARE.equals(event) || EVENT_REFRESH.equals(event)) {
			aPage.setPageReference(EVENT_PREPARE);
		}
		else {
			String from_event = (String) resultMap.get("from_event");
			if (EVENT_READ.equals(event) && (from_event != null) && from_event.equals("process")) {
				aPage.setPageReference(EVENT_PROCESS);
			}
			else {
				aPage.setPageReference(event);
			}
		}
		return aPage;
	}

}
