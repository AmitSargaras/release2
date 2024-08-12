/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/guarantees/gteslcsame/GteSLCSameValidationHelper.java,v 1.2 2003/07/23 10:23:36 hshii Exp $
 */

//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.guarantees.gteslcsame;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/23 10:23:36 $ Tag: $Name: $
 */
public class GteSLCSameValidationHelper {
	private static String LOGOBJ = GteSLCSameValidationHelper.class.getName();

	public static ActionErrors validateInput(GteSLCSameForm aForm, Locale locale, ActionErrors errors) {
		String errorCode = null;

		boolean isMandatory = false;
		if ("submit".equals(aForm.getEvent())) {
			isMandatory = true;
		}

		/*if (!(errorCode = Validator.checkString(aForm.getSecIssueBank(), isMandatory, 0, 250))
				.equals(Validator.ERROR_NONE)) {
			errors.add("secIssueBank", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					250 + ""));
		}*/

		/*if (!(errorCode = Validator.checkDate(aForm.getIssuingDate(), isMandatory, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("issuingDate", new ActionMessage("error.date.mandatory", "1", "256"));
			DefaultLogger.debug(LOGOBJ, " aForm.getIssuingDate()= " + aForm.getIssuingDate());
		}

		if (!(errorCode = Validator.checkString(aForm.getBeneName(), isMandatory, 1, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("beneName", new ActionMessage("error.string.mandatory", "1", "50"));
		}
*/
		/*if (!(errorCode = Validator.checkString(aForm.getGuaRefNo(), isMandatory, 1, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("guaRefNo", new ActionMessage("error.string.mandatory", "1", "50"));
		}*/

		/*if (!(errorCode = Validator.checkInteger(aForm.getSecuredPortion(), false, 0, 100))
				.equals(Validator.ERROR_NONE)) {
			String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode);
			errors.add("securedPortion", new ActionMessage(errorMessage, "0", "100"));
		}
		else {
			if (aForm.getSecuredPortion() != null) {
				String securedPortion = aForm.getSecuredPortion();
				for (int i = 0; i < securedPortion.length(); i++) {
					char aa = ',';
					if (aa == securedPortion.charAt(i)) {
						errors.add("securedPortion", new ActionMessage("error.integer.format"));
						break;
					}
				}
			}
		}

		if (!(errorCode = Validator.checkInteger(aForm.getUnsecuredPortion(), false, 0, 100))
				.equals(Validator.ERROR_NONE)) {
			String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode);
			errors.add("unsecuredPortion", new ActionMessage(errorMessage, "0", "100"));
		}
		else {
			if (aForm.getUnsecuredPortion() != null) {
				String unSecuredPortion = aForm.getUnsecuredPortion();
				for (int i = 0; i < unSecuredPortion.length(); i++) {
					char aa = ',';
					if (aa == unSecuredPortion.charAt(i)) {
						errors.add("unsecuredPortion", new ActionMessage("error.integer.format"));
						break;
					}
				}
			}
		}*/

		return errors;
	}
}
