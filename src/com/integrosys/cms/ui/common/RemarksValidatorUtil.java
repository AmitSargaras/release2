/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/common/RemarksValidatorUtil.java,v 1.2 2005/07/15 06:31:25 hshii Exp $
 */

package com.integrosys.cms.ui.common;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;

/**
 * Common class to provide validation for all maker-checker remarks Caller
 * should do this: String errorCode; ActionErrors errors; if (!(errorCode =
 * RemarksValidatorUtil.checkRemarks(form.getRemarks()).equals(ERROR_NONE)) {
 * errors.add("attributeName", RemarksValidatorUtil.getErrorMessage(errorCode);
 * }
 */
public class RemarksValidatorUtil {

	/**
	 * Check remarks
	 * 
	 */
	private static boolean MANDATORY = false;

	private static final int MIN_HEIGHT = 0;

	private static final int MAX_HEIGHT = 3;

	private static final int MIN_LENGTH = 0;

	private static final int MAX_LENGTH = 250;

	private static final String ERROR_KEY = "error.checkbox.remarks.";

	public static String checkRemarks(String input, boolean mandatory) {
		return Validator.checkTextBox(input, MANDATORY, MIN_LENGTH, MAX_LENGTH, MIN_HEIGHT, MAX_HEIGHT);
	}

	public static ActionMessage getErrorMessage(String errorCode) {
		ActionMessage error;
		if (errorCode.equals(Validator.ERROR_LESS_THAN) || errorCode.equals(Validator.ERROR_GREATER_THAN)) {
			return new ActionMessage(ERROR_KEY + errorCode, new Integer(MIN_LENGTH), new Integer(MAX_LENGTH));
		}
		else if (errorCode.equals(Validator.ERROR_HEIGHT_GREATER_THAN)
				|| errorCode.equals(Validator.ERROR_HEIGHT_LESS_THAN)) {
			return new ActionMessage(ERROR_KEY + errorCode, new Integer(MIN_HEIGHT), new Integer(MAX_HEIGHT));
		}

		return new ActionMessage(ERROR_KEY + errorCode);
	}

	public static String checkRemarks(String input, boolean mandatory, int maxLength, int maxHeight) {
		return Validator.checkTextBox(input, mandatory, MIN_LENGTH, maxLength, MIN_HEIGHT, maxHeight);
	}

	public static ActionMessage getErrorMessage(String errorCode, int maxLength, int maxHeight) {
		ActionMessage error;
		if (errorCode.equals(Validator.ERROR_LESS_THAN) || errorCode.equals(Validator.ERROR_GREATER_THAN)) {
			return new ActionMessage(ERROR_KEY + errorCode, new Integer(MIN_LENGTH), new Integer(maxLength));
		}
		else if (errorCode.equals(Validator.ERROR_HEIGHT_GREATER_THAN)
				|| errorCode.equals(Validator.ERROR_HEIGHT_LESS_THAN)) {
			return new ActionMessage(ERROR_KEY + errorCode, new Integer(MIN_HEIGHT), new Integer(maxHeight));
		}

		return new ActionMessage(ERROR_KEY + errorCode);
	}
}