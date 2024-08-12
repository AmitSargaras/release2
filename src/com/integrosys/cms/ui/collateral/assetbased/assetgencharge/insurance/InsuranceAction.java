/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/insurance/InsuranceAction.java,v 1.3 2005/08/26 10:11:56 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.insurance;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeAction;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/08/26 10:11:56 $ Tag: $Name: $
 */

public class InsuranceAction extends AssetGenChargeAction {

	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (EVENT_CREATE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CreateInsuranceCommand();
		}
		else if (EVENT_PREPARE_UPDATE_SUB.equals(event) || EVENT_PREPARE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadInsuranceCommand();
			objArray[1] = new PrepareInsuranceCommand();
		}
		else if (EVENT_READ.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadInsuranceCommand();
		}
		else if (EVENT_UPDATE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new UpdateInsuranceCommand();
		}
		else if (EVENT_READ_RETURN.equals(event) || EVENT_CANCEL.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReturnInsuranceCommand();
		}
		else if (EVENT_PREPARE_FORM.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareInsuranceCommand();
		}
		else if (EVENT_REFRESH.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new RefreshInsuranceCommand();
			objArray[1] = new PrepareInsuranceCommand();
		}
		else {
			objArray = super.getCommandChain(event);
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
		return InsuranceValidator.validateInput((InsuranceForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (EVENT_CREATE.equals(event) || EVENT_UPDATE.equals(event) || EVENT_REFRESH.equals(event)) {
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = event;
		if (EVENT_CREATE.equals(event) || EVENT_UPDATE.equals(event) || EVENT_REFRESH.equals(event)) {
			errorEvent = EVENT_PREPARE_FORM;
		}

		return errorEvent;
	}

	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		if (event.equals(EVENT_FORWARD)) {
			return super.getNextPage(event, resultMap, exceptionMap);
		}
		if (EVENT_READ_RETURN.equals(event)) {
			aPage.setPageReference(getReturnReference((String) resultMap.get("from_page"), (String) resultMap
					.get("tab")));
		}
		else if (EVENT_READ.equals(event)) {
			aPage.setPageReference(getReadReference((String) resultMap.get("from_page")));
		}
		else {
			aPage.setPageReference(getReference(event, (String) resultMap.get("tab")));
		}
		return aPage;
	}

	private String getReference(String event, String tab) {
		if (event.equals(EVENT_UPDATE) || event.equals(EVENT_CREATE) || event.equals(EVENT_CANCEL)) {
			return tab + "_" + EVENT_UPDATE_RETURN;
		}
		if (event.equals(EVENT_PREPARE_UPDATE_SUB) || event.equals(EVENT_PREPARE_FORM) || event.equals(EVENT_REFRESH)) {
			return EVENT_PREPARE;
		}
		return event;
	}

	private String getReturnReference(String from_event, String tab) {
		if (from_event.equals(EVENT_PREPARE_CLOSE)) {
			return tab + "_" + EVENT_CLOSE_RETURN;
		}
		if (from_event.equals(EVENT_TRACK)) {
			return tab + "_" + EVENT_TRACK_RETURN;
		}
		if (from_event.equals(EVENT_PROCESS)) {
			return tab + "_" + EVENT_PROCESS_RETURN;
		}
		if (from_event.equals(EVENT_READ)) {
			return tab + "_" + EVENT_READ_RETURN;
		}
		if (from_event.equals(EVENT_PREPARE_UPDATE) || from_event.equals(EVENT_PROCESS_UPDATE)) {
			return tab + "_" + EVENT_UPDATE_RETURN;
		}

		return from_event;
	}

	private String getReadReference(String from_event) {
		if (from_event.equals(EVENT_PREPARE_CLOSE) || from_event.equals(EVENT_READ) || from_event.equals(EVENT_TRACK)
				|| from_event.equals(EVENT_PREPARE_UPDATE) || from_event.equals(EVENT_PROCESS_UPDATE)) {
			return "view_insurance";
		}
		if (from_event.equals(EVENT_PROCESS)) {
			return "process_insurance";
		}
		return from_event;
	}
}
