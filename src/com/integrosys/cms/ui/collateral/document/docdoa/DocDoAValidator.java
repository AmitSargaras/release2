package com.integrosys.cms.ui.collateral.document.docdoa;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.collateral.document.DocumentValidator;

public class DocDoAValidator {

	public static ActionErrors validateInput(DocDoAForm aForm, Locale locale) {

		ActionErrors errors = DocumentValidator.validateInput(aForm, locale);
		String errorCode = null;

		if (!(aForm.getEvent().equals("approve") || aForm.getEvent().equals("reject"))) {

			if (!(errorCode = Validator.checkDate(aForm.getAwardedDate(), false, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("awardedDate", new ActionMessage("error.date.mandatory", "1", "256"));
			}

			if (!(errorCode = Validator.checkString(aForm.getDeedAssignmtTypeCode(), false, 0, 250))
					.equals(Validator.ERROR_NONE)) {
				errors.add("deedAssignmtTypeCode", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
						errorCode), "0", 250 + ""));
			}

			if (!(errorCode = Validator.checkString(aForm.getProjectName(), false, 0, 250))
					.equals(Validator.ERROR_NONE)) {
				errors.add("projectName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						250 + ""));
			}

			if (!(errorCode = Validator.checkString(aForm.getIsLetterInstruct(), false, 0, 250))
					.equals(Validator.ERROR_NONE)) {
				errors.add("isLetterInstruct", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"0", 250 + ""));
			}

			if (!(errorCode = Validator.checkString(aForm.getIsLetterUndertake(), false, 0, 250))
					.equals(Validator.ERROR_NONE)) {
				errors.add("isLetterUndertake", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"0", 250 + ""));
			}

			if (!(errorCode = Validator.checkString(aForm.getBlanketAssignment(), false, 0, 250))
					.equals(Validator.ERROR_NONE)) {
				errors.add("blanketAssignment", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"0", 250 + ""));
			}

			DocDoAValidationHelper.validateInput(aForm, locale, errors);

		}

		return errors;

	}
}
