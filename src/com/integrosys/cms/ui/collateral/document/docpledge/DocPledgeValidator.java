//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral.document.docpledge;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.collateral.document.DocumentValidator;

/**
 * Created by IntelliJ IDEA. User: jerlin Date: 2007/03/16 To change this
 * template use Options | File Templates.
 */
public class DocPledgeValidator {
	public static ActionErrors validateInput(DocPledgeForm aForm, Locale locale) {

		ActionErrors errors = DocumentValidator.validateInput(aForm, locale);

		String errorCode = null;

		if (!(aForm.getEvent().equals("approve") || aForm.getEvent().equals("reject"))) {
			if (!(errorCode = Validator.checkDate(aForm.getAwardedDate(), false, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("awardedDate", new ActionMessage("error.date.mandatory", "1", "256"));
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

			DocPledgeValidationHelper.validateInput(aForm, locale, errors);
		}

		return errors;

	}
}
