/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/fixedassetsoth/FAOAction.java,v 1.2 2005/08/26 10:11:31 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.fixedassetsoth;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.collateral.CollateralConstant;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeAction;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.NavigationGenChargeCommand;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/08/26 10:11:31 $ Tag: $Name: $
 */

public class FAOAction extends AssetGenChargeAction {
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (EVENT_PREPARE_FORM.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareFAOCommand();
		}
		else if (EVENT_UPDATE_RETURN.equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new ReadFAOCommand();
			objArray[1] = new PrepareFAOCommand();
			objArray[2] = new NavigationGenChargeCommand();
		}
		else if (EVENT_READ_RETURN.equals(event) || EVENT_CLOSE_RETURN.equals(event)
				|| EVENT_TRACK_RETURN.equals(event) || EVENT_PROCESS_RETURN.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadFAOCommand();
		}
		else if (EVENT_REFRESH.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new RefreshFAOCommand();
			objArray[1] = new PrepareFAOCommand();
		}
		else if (event.endsWith(EVENT_DELETE_ITEM)) {
			objArray = new ICommand[2];
			objArray[0] = new DeleteFAOCommand();
			objArray[1] = new PrepareFAOCommand();
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
		return FAOValidator.validateInput((FAOForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (EVENT_EDIT.equals(event) || EVENT_REFRESH.equals(event) || event.endsWith(EVENT_PREPARE_UPDATE_SUB)
				|| event.endsWith(EVENT_DELETE_ITEM) || event.endsWith(EVENT_PREPARE)) {
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = event;
		if (EVENT_EDIT.equals(event) || EVENT_REFRESH.equals(event) || event.endsWith(EVENT_PREPARE_UPDATE_SUB)
				|| event.endsWith(EVENT_DELETE_ITEM) || event.endsWith(EVENT_PREPARE)) {
			errorEvent = EVENT_PREPARE_FORM;
		}

		return errorEvent;
	}

	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		if (event.equals(EVENT_EDIT) || event.equals(EVENT_FORWARD)) {
			return super.getNextPage(event, resultMap, exceptionMap);
		}
		if (event.equals(EVENT_UPDATE_RETURN)) {
			String forwardPage = (String) resultMap.get("forwardPage");
			DefaultLogger.debug(this, "forwardPage is: " + resultMap.get("forwardPage"));
			if (forwardPage.endsWith(EVENT_READ)) {
				aPage.setPageReference(EVENT_READ_RETURN);
			}
			else {
				aPage.setPageReference(EVENT_UPDATE_RETURN);
			}
			return aPage;
		}

		if (event.endsWith(EVENT_PREPARE)) {
			aPage.setPageReference(getAddItemReference(event));
		}
		else if (event.endsWith(EVENT_PREPARE_UPDATE_SUB)) {
			aPage.setPageReference(getUpdateItemReference(event));
		}
		else if (event.endsWith(EVENT_VIEW)) {
			aPage.setPageReference(getViewItemReference(event));
		}
		else {
			aPage.setPageReference(getReference(event));
		}
		return aPage;
	}

	private String getReference(String event) {
		if (event.equals(EVENT_PREPARE_FORM) || event.equals(EVENT_REFRESH) || event.endsWith(EVENT_DELETE_ITEM)) {
			return EVENT_UPDATE_RETURN;
		}
		if (event.equals(EVENT_CLOSE_RETURN) || event.equals(EVENT_TRACK_RETURN)) {
			return EVENT_READ_RETURN;
		}
		return event;
	}

	private String getAddItemReference(String event) {
		if (event.startsWith(CollateralConstant.FAO)) {
			return "add_" + CollateralConstant.FAO;
		}
		if (event.startsWith(CollateralConstant.INSURANCE_POLICY)) {
			return "add_" + CollateralConstant.INSURANCE_POLICY;
		}
		return event;
	}

	private String getUpdateItemReference(String event) {
		if (event.startsWith(CollateralConstant.FAO)) {
			return "update_" + CollateralConstant.FAO;
		}
		if (event.startsWith(CollateralConstant.INSURANCE_POLICY)) {
			return "update_" + CollateralConstant.INSURANCE_POLICY;
		}
		return event;
	}

	private String getViewItemReference(String event) {
		if (event.startsWith(CollateralConstant.FAO)) {
			return "view_" + CollateralConstant.FAO;
		}
		if (event.startsWith(CollateralConstant.INSURANCE_POLICY)) {
			return "view_" + CollateralConstant.INSURANCE_POLICY;
		}
		return event;
	}
}
