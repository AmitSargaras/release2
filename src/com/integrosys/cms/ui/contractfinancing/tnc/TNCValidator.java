/**
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.contractfinancing.tnc;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.0 $
 * @since $Date: 2007/Mar/09 $ Tag: $Name: $
 */
public class TNCValidator {

	public static ActionErrors validateInput(com.integrosys.cms.ui.contractfinancing.tnc.TNCForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode;

		try {
			DefaultLogger.debug("in TNCValidator", ">>>>>>>>>> start validate...");

			if (TNCAction.EVENT_CREATE.equals(form.getEvent())) {
				if (!(errorCode = Validator.checkString(form.getTerms(), true, 1, 10)).equals(Validator.ERROR_NONE)) {
					errors.add("terms", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
							"10"));
				}

				if (form.getTerms().equals("OTH")) {
					if (!(errorCode = Validator.checkString(form.getTermsOthers(), true, 1, 50))
							.equals(Validator.ERROR_NONE)) {
						errors.add("termsOthers", new ActionMessage(ErrorKeyMapper
								.map(ErrorKeyMapper.STRING, errorCode), "1", "50"));
					}
				}
			}

			if (!(errorCode = Validator.checkDate(form.getTncDate(), true, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("tncDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
			}
			if (!(errorCode = Validator.checkString(form.getConditions(), true, 1, 100)).equals(Validator.ERROR_NONE)) {
				errors.add("conditions", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						"100"));
			}
			if (!(errorCode = Validator.checkString(form.getRemarks(), false, 0, 500)).equals(Validator.ERROR_NONE)) {
				errors.add("remarks", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						"500"));
			}

			DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return errors;
	}
}