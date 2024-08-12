package com.integrosys.cms.ui.collateral.document;

import org.apache.struts.action.ActionErrors;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.document.IDocumentCollateral;
import com.integrosys.cms.ui.collateral.AbstractCollateralStpValidator;

import java.util.Map;

public abstract class AbstractDocumentStpValidator extends AbstractCollateralStpValidator  {
	protected boolean validateDocumentCollateral(Map context) {
		if (!validateCommonCollateral(context)) {
			return false;
		}
		return true;
	}

	public boolean validate(Map context) {
		// TODO Auto-generated method stub
		return false;
	}
	
	protected ActionErrors validateAndAccumulateDocumentation(Map context) {
		ActionErrors errorMessages = validateAndAccumulateCommonCollateral(context);

		return errorMessages;
	}
}
