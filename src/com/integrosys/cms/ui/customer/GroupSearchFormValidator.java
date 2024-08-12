package com.integrosys.cms.ui.customer;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;

/**
 * Created by IntelliJ IDEA. User: pooja Date: Jul 4, 2003 Time: 11:23:06 AM To
 * change this template use Options | File Templates.
 */
public class GroupSearchFormValidator {

	public static ActionErrors validateInput(GroupSearchForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = "";
		try {

			if (aForm.getGobutton() != null) {
				if (aForm.getGobutton().equals("1")) {
					if (!(errorCode = Validator.checkString(aForm.getGroupName(), true, 0, 40))
							.equals(Validator.ERROR_NONE)) {
						errors.add("legalName", new ActionMessage("error.string.legalname"));
					}
				}

				if (aForm.getGobutton().equals("2")) {
					if (!(errorCode = Validator.checkNumericString(aForm.getGroupID(), true, 1, 10))
							.equals(Validator.ERROR_NONE)) {
						errors.add("legalId", new ActionMessage("error.mandatory"));
					}
				}
			}
		}
		catch (Exception e) {
		}
		return errors;
	}
}