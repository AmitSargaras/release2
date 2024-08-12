/*
 * Copyright Integro Technologies Pte Ltd
 */
//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.document.docpledge;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;

/**
 * Created by IntelliJ IDEA. User: kienleong Date: 2007/02/28 To change this
 * template use Options | File Templates.
 */

public class DocPledgeValidationHelper {
	public static ActionErrors validateInput(DocPledgeForm aForm, Locale locale, ActionErrors errors) {

		String errorCode = null;

		if (!(errorCode = Validator.checkDate(aForm.getAwardedDate(), true, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("awardedDate", new ActionMessage("error.date.mandatory", "1", "256"));
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.DocumentValidationHelper",
					" aForm.getAwardedDate()= " + aForm.getAwardedDate());
		}

		return errors;
	}
}
