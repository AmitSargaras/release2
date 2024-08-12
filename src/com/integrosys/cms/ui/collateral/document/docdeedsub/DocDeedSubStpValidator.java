package com.integrosys.cms.ui.collateral.document.docdeedsub;

import org.apache.struts.action.ActionErrors;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.document.IDocumentCollateral;
import com.integrosys.cms.ui.collateral.document.AbstractDocumentStpValidator;

import java.util.Map;

public class DocDeedSubStpValidator extends AbstractDocumentStpValidator {
	public boolean validate(Map context) {
		if (!validateDocumentCollateral(context)) {
			return false;
		}
		return true;
	}

	public ActionErrors validateAndAccumulate(Map context) {
		ActionErrors errorMessages = validateAndAccumulateDocumentation(context);
		
		return errorMessages;
	}
}
