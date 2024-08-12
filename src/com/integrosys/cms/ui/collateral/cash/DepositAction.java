//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral.cash;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.collateral.ReadReturnCollateralCommand;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 2:49:22 PM
 * To change this template use Options | File Templates.
 */
// this needs to extend from Collateral Action to fit the IPin interface. This
// is a workaround till IPin is removed from
// Marker interface.
public class DepositAction extends CashAction {

	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = new ICommand[1];
		if (EVENT_CREATE.equals(event)) {
			objArray[0] = new AddDepositCommand();
		}
		else if (EVENT_PREPARE.equals(event)) {
			objArray[0] = new PrepareDepositCommand();
		}
		else if (EVENT_PREPARE_UPDATE_SUB.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareDepositCommand();
			objArray[1] = new ReadDepositCommand();
		}
		else if (EVENT_UPDATE.equals(event)) {
			objArray[0] = new UpdateDepositCommand();
		}
		else if (EVENT_DELETE.equals(event)) {
			objArray[0] = new DeleteDepositCommand();
		}
		else if (EVENT_READ.equals(event)) {
			objArray[0] = new ReadDepositCommand();
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
		return DepositValidator.validateInput((DepositForm) aForm, locale);
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
			String subtype = (String) resultMap.get("subtype");
			if (subtype == null) {
				// todo forward to error page after populating the exceptionMap
				throw new RuntimeException("URL passed is wrong");
			}
			else if (EVENT_READ_RETURN.equals(event)) {
				aPage.setPageReference(subtype + "_" + (String) resultMap.get("from_event"));
			}
			else {
				aPage.setPageReference(subtype + "_update");
			}
		}
		else {
			aPage.setPageReference(event);
		}
		return aPage;
	}

}
