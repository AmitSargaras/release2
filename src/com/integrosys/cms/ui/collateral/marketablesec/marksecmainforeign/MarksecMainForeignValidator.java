//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral.marketablesec.marksecmainforeign;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;

import com.integrosys.cms.ui.collateral.marketablesec.MarketableSecValidator;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class MarksecMainForeignValidator {
	public static ActionErrors validateInput(MarksecMainForeignForm aForm, Locale locale) {

		ActionErrors errors = MarketableSecValidator.validateInput(aForm, locale);
		if (!(aForm.getEvent().equals("approve") || aForm.getEvent().equals("reject"))) {
			MarksecMainForeignValidationHelper.validateInput(aForm, locale, errors);
		}

		return errors;

	}
}
