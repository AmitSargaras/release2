/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/debtors/DebtorsAction.java,v 1.2 2005/04/01 09:38:52 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.debtors;

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
 * @version $Revision: 1.2 $
 * @since $Date: 2005/04/01 09:38:52 $ Tag: $Name: $
 */

public class DebtorsAction extends AssetGenChargeAction {
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (EVENT_PREPARE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareDebtorsCommand();
		}
		else if (EVENT_PREPARE_UPDATE.equals(event) || EVENT_PROCESS_UPDATE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadDebtorsCommand();
			objArray[1] = new PrepareDebtorsCommand();
		}
		else if (EVENT_READ.equals(event) || EVENT_PREPARE_CLOSE.equals(event) || EVENT_TRACK.equals(event)
				|| EVENT_PROCESS.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadDebtorsCommand();
		}
		else if (EVENT_REFRESH.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new RefreshDebtorsCommand();
			objArray[1] = new PrepareDebtorsCommand();
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
		return DebtorsValidator.validateInput((DebtorsForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (EVENT_EDIT.equals(event) || EVENT_REFRESH.equals(event)) {
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = event;
		if (EVENT_EDIT.equals(event) || EVENT_REFRESH.equals(event)) {
			errorEvent = EVENT_PREPARE;
		}

		return errorEvent;
	}

	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		if (event.equals(EVENT_EDIT) || event.equals(EVENT_FORWARD)) {
			return super.getNextPage(event, resultMap, exceptionMap);
		}
		aPage.setPageReference(getReference(event));
		return aPage;
	}

	private String getReference(String event) {
		if (event.equals(EVENT_PREPARE) || event.equals(EVENT_PROCESS_UPDATE) || event.equals(EVENT_REFRESH)) {
			return EVENT_PREPARE_UPDATE;
		}
		if (event.equals(EVENT_PREPARE_CLOSE) || event.equals(EVENT_TRACK)) {
			return EVENT_READ;
		}
		return event;
	}
}
