package com.integrosys.cms.ui.collateral.pledge;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.cms.ui.collateral.nocollateral.NoCollateralAction;

public class CleanPledgeAction extends NoCollateralAction {
	public ICommand[] getCommandChain(String event) {
		return PledgeAction.getCommandChain(event);
	}

	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		return PledgeAction.validateInput(aForm, locale);
	}
	
	protected boolean isValidationRequired(String event) {
		return PledgeAction.isValidationRequired(event);
	}
	
	protected String getErrorEvent(String event) {
		return PledgeAction.getErrorEvent(event);
	}
	
	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		return PledgeAction.getNextPage(event, resultMap, exceptionMap);
	}	
}
