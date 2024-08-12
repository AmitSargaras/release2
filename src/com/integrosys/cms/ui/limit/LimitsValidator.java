package com.integrosys.cms.ui.limit;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

public class LimitsValidator {
	public static ActionErrors validateInput(LimitsForm aForm, Locale locale) {

		ActionErrors errors = new ActionErrors();

		String errorCode = "";

		if (!(errorCode = Validator.checkNumber(aForm.getRequiredSecurityCoverage(), false, 0, 100))
				.equals(Validator.ERROR_NONE)) {
			errors.add("requiredSecurityCoverage", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
					errorCode), "0", 100 + ""));

		}

		return errors;

	}
}