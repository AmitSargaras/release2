package com.integrosys.cms.ui.collateral.property.addtionalDocumentFacilityDetails;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.cms.ui.collateral.addtionalDocumentFacilityDetails.AddtionalDocumentFacilityDetailsAction;
import com.integrosys.cms.ui.collateral.property.PropertyAction;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/08/13 06:43:27 $ Tag: $Name: $
 */ 

public class PropertyAddtionalDocumentFacilityDetailsAction extends PropertyAction {

	public ICommand[] getCommandChain(String event) {
		return AddtionalDocumentFacilityDetailsAction.getCommandChain(event);
	}

	
	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		return AddtionalDocumentFacilityDetailsAction.validateInput(aForm, locale);
	}
	
	protected boolean isValidationRequired(String event) {
		return AddtionalDocumentFacilityDetailsAction.isValidationRequired(event);
	}

	protected String getErrorEvent(String event) {
		return AddtionalDocumentFacilityDetailsAction.getErrorEvent(event);
	}

	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		return AddtionalDocumentFacilityDetailsAction.getNextPage(event, resultMap, exceptionMap);
	}
}
