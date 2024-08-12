//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral.cash.cashhkdusd;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;

import com.integrosys.cms.ui.collateral.cash.CashValidator;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class CashHKDUSDValidator {
	public static ActionErrors validateInput(CashHKDUSDForm aForm, Locale locale) {

		ActionErrors errors = CashValidator.validateInput(aForm, locale);

		String errorCode = null;
		if (!(aForm.getEvent().equals("approve") || aForm.getEvent().equals("reject"))) {
			CashHKDUSDValidationHelper.validateInput(aForm, locale, errors);
		}

		return errors;

	}
}
