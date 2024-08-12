package com.integrosys.cms.ui.collateral.cash.cashrepo;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;

import com.integrosys.cms.ui.collateral.cash.CashValidator;

/**
 * User: Naveen Date: Feb 20, 2007 Time: 6:42:25 PM To change this template use
 * Options | File Templates.
 */
public class CashRepoValidator {
	public static ActionErrors validateInput(CashRepoForm aForm, Locale locale) {

		ActionErrors errors = CashValidator.validateInput(aForm, locale);

		String errorCode = null;
		if (!(aForm.getEvent().equals("approve") || aForm.getEvent().equals("reject"))) {
			CashRepoValidationHelper.validateInput(aForm, locale, errors);
		}

		return errors;

	}
}
