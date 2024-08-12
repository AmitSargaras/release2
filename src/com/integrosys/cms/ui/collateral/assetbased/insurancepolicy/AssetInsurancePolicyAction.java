package com.integrosys.cms.ui.collateral.assetbased.insurancepolicy;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.cms.ui.collateral.assetbased.AssetBasedAction;
import com.integrosys.cms.ui.collateral.insurancepolicy.InsurancePolicyAction;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/08/12 02:47:05 $ Tag: $Name: $
 */

public class AssetInsurancePolicyAction extends AssetBasedAction {

	public ICommand[] getCommandChain(String event) {
		return InsurancePolicyAction.getCommandChain(event);
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
		return InsurancePolicyAction.validateInput(aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		return InsurancePolicyAction.isValidationRequired(event);
	}

	protected String getErrorEvent(String event) {
		return InsurancePolicyAction.getErrorEvent(event);
	}

	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		return InsurancePolicyAction.getNextPage(event, resultMap, exceptionMap);
	}
}
