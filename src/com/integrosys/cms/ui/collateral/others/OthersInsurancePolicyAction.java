package com.integrosys.cms.ui.collateral.others;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.cms.ui.collateral.insurancepolicy.InsurancePolicyAction;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/08/13 06:43:27 $ Tag: $Name: $
 */

public class OthersInsurancePolicyAction extends OthersAction {

	public ICommand[] getCommandChain(String event) {
		return InsurancePolicyAction.getCommandChain(event);
	}

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
