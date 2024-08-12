package com.integrosys.cms.ui.limit.limitaccount;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;

public class LimitAccountValidator {
	public static ActionErrors validateInput(LimitAccountForm aForm, Locale locale) {
		String errorCode = "";
		ActionErrors errors = new ActionErrors();

		if (!(errorCode = Validator.checkString(aForm.getHostSystemCountry(), true, 1, 20))
				.equals(Validator.ERROR_NONE)) {
			errors.add("hostSystemCountry", new ActionMessage("error.string.mandatory", "1", "20"));
		}
		if (!(errorCode = Validator.checkString(aForm.getHostSystemName(), true, 1, 20)).equals(Validator.ERROR_NONE)) {
			errors.add("hostSystemName", new ActionMessage("error.string.mandatory", "1", "20"));
		}
		if (!(errorCode = Validator.checkString(aForm.getAccountNo(), true, 1, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("accountNo", new ActionMessage("error.string.mandatory", "1", "50"));
		}

		return errors;
	}
}