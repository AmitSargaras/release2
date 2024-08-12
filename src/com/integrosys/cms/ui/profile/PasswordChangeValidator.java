package com.integrosys.cms.ui.profile;

import java.util.Arrays;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.component.login.ui.LoginValidator;

public class PasswordChangeValidator {

	public static ActionErrors validateInput(PasswordChangeForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();
		LoginValidator validator = new LoginValidator(form.getRealm(), form.getRole());
		// the array values should be from the JSP form
		String[] formFields = new String[] { "newPasswd", "confirmPasswd", "oldPasswd" };
		errors = validator.validatePwdChange(form.getRole(), form.getNewPasswd(), form.getConfirmPasswd(), form
				.getOldPasswd(), formFields);

		if (((errors == null) || (errors.size() == 0))) {
			formFields = new String[] { "loginIdError", "newPasswd", "confirmPasswd" };
			validator.validateOthersPwdChange(form.getLoginId(), form.getNewPasswd(), form.getConfirmPasswd(),
					formFields, errors);
		}

		if (((errors == null) || (errors.size() == 0)) && chkDiffPasswd(form.getOldPasswd(), form.getNewPasswd(), 3)) {
			errors.add("newPasswd", new ActionMessage("error.password.oldnewpassword.differ.3char", "1", "256"));
		}

		if (((errors == null) || (errors.size() == 0)) && chkContainSimpleWord(form.getNewPasswd())) {
			errors.add("newPasswd", new ActionMessage("error.password.new.password.simple", "1", "256"));
		}

		return errors;
	}

	// If s1 and s2 are different by at least 3 characters, it will return
	// false, else return true.
	private static boolean chkDiffPasswd(String s1, String s2, int numDiff) {
		boolean result = false;
		// s1.toUpperCase();
		// s2.toUpperCase();
		DefaultLogger.debug("PasswordChangeValidator", "old psw: " + s1 + "\tnew psw: " + s2);
		char[] c1 = s1.toUpperCase().toCharArray();
		char[] c2 = s2.toUpperCase().toCharArray();
		Arrays.sort(c1);
		Arrays.sort(c2);

		int diff1 = 0;
		int diff2 = 0;
		boolean finishChk = false;
		int pC1 = 0;
		int pC2 = 0;
		while (!finishChk) {
			if (c1[pC1] == c2[pC2]) {
				pC1++;
				pC2++;
			}
			else if (c1[pC1] < c2[pC2]) {
				pC1++;
				diff1++;
			}
			else {
				pC2++;
				diff2++;
			}

			if (pC1 == c1.length) {
				finishChk = true;
				diff2 += c2.length - pC2;
			}
			else if (pC2 == c2.length) {
				finishChk = true;
				diff1 += c1.length - pC1;
			}

			if ((diff1 >= numDiff) || (diff2 >= numDiff)) {
				return false;
			}
		}

		if ((diff1 < numDiff) && (diff2 < numDiff)) {
			return true;
		}
		return result;
	}

	private static boolean chkContainSimpleWord(String newPassword) {
		boolean result = false;
		String simpleWord = PropertyManager.getValue("password.simpleword");
		DefaultLogger.debug("PasswordChangeValidator", simpleWord);
		if (simpleWord != null) {
			StringTokenizer st = new StringTokenizer(simpleWord, ",");
			Vector vword = new Vector();

			while (st.hasMoreTokens()) {
				vword.addElement(st.nextToken());
			}

			if (vword.contains(newPassword.toUpperCase())) {
				result = true;
			}
		}
		return result;
	}

}