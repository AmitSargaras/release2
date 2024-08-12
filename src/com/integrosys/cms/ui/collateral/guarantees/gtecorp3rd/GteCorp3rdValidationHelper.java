/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/guarantees/gtecorp3rd/GteCorp3rdValidationHelper.java,v 1.5 2003/11/03 04:31:43 hshii Exp $
 */
//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.guarantees.gtecorp3rd;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2003/11/03 04:31:43 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class GteCorp3rdValidationHelper {
	public static ActionErrors validateInput(GteCorp3rdForm aForm, Locale Locale, ActionErrors errors) {
		String errorCode;
		boolean isMandatory = false;
		if ("submit".equals(aForm.getEvent())) {
			isMandatory = true;
		}

		if (aForm.getEvent().equals("update") || aForm.getEvent().equals("submit")) {

			if (!(errorCode = Validator.checkString(aForm.getValCurrency(), isMandatory, 1, 3))
					.equals(Validator.ERROR_NONE)) {
			}

			if (!(errorCode = Validator.checkString(aForm.getSecIssueBank(), false, 0, 250))
					.equals(Validator.ERROR_NONE)) {
				errors.add("secIssueBank", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						250 + ""));
			}

			if (!(errorCode = Validator.checkInteger(aForm.getSecuredPortion(), false, 0, 100))
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
			}
		}

		return errors;
	}
}
