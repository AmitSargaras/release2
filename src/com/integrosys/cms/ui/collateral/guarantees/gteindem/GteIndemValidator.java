//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral.guarantees.gteindem;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;

import com.integrosys.cms.ui.collateral.guarantees.GuaranteesValidator;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class GteIndemValidator {
	public static ActionErrors validateInput(GteIndemForm aForm, Locale locale) {

		ActionErrors errors = GuaranteesValidator.validateInput(aForm, locale);

		String errorCode = null;

		if (!(aForm.getEvent().equals("approve") || aForm.getEvent().equals("reject"))) {
			GteIndemValidationHelper.validateInput(aForm, locale, errors);
		}

		return errors;

	}
}
