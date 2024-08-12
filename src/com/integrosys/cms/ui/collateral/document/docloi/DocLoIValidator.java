package com.integrosys.cms.ui.collateral.document.docloi;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;

import com.integrosys.cms.ui.collateral.document.DocumentValidator;

public class DocLoIValidator {

	public static ActionErrors validateInput(DocLoIForm aForm, Locale locale) {

		ActionErrors errors = DocumentValidator.validateInput(aForm, locale);

		if (!(aForm.getEvent().equals("approve") || aForm.getEvent().equals("reject"))) {

			DocLoIValidationHelper.validateInput(aForm, locale, errors);

		}

		return errors;

	}
}
