package com.integrosys.cms.ui.collateral.document.doclou;

import org.apache.struts.action.ActionErrors;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.document.IDocumentCollateral;
import com.integrosys.cms.ui.collateral.document.AbstractDocumentStpValidator;

import java.util.Map;

public class DocLouStpValidator extends AbstractDocumentStpValidator {
	public boolean validate(Map context) {
		return true;
	}

	public ActionErrors validateAndAccumulate(Map context) {
		ActionErrors errorMessages = validateAndAccumulateDocumentation(context);
		
		return errorMessages;
	}
}
