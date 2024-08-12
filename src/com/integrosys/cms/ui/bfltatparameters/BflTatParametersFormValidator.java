package com.integrosys.cms.ui.bfltatparameters;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/09/21 07:45:13 $ Tag: $Name: $
 */
public class BflTatParametersFormValidator {
	public static ActionErrors validateInput(BflTatParametersForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		boolean mandatoryInd = true;
		int minLength = 0;
		int maxLength = 2;
		if (!(Validator.checkString(aForm.getCountryCode(), mandatoryInd, minLength, maxLength))
				.equals(Validator.ERROR_NONE)) {
			errors.add("countryCode", new ActionMessage("error.string.country.mandatory", String.valueOf(minLength),
					String.valueOf(maxLength)));
		}
		return errors;
	}
}
