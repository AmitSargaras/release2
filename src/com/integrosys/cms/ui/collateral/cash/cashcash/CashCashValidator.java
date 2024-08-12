package com.integrosys.cms.ui.collateral.cash.cashcash;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;

import com.integrosys.cms.ui.collateral.cash.CashValidator;

/**
 * User: Naveen Date: Feb 20, 2007 Time: 6:42:25 PM To change this template use
 * Options | File Templates.
 */
public class CashCashValidator {
	public static ActionErrors validateInput(CashCashForm aForm, Locale locale) {

		ActionErrors errors = CashValidator.validateInput(aForm, locale);

		String errorCode = null;
		if (!(aForm.getEvent().equals("approve") || aForm.getEvent().equals("reject"))) {
			CashCashValidationHelper.validateInput(aForm, locale, errors);
		}

		return errors;

	}
}
