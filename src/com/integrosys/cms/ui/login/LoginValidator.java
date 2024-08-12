package com.integrosys.cms.ui.login;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.techinfra.validation.ValidatorConstant;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.component.login.app.IPasswordPolicy;
import com.integrosys.component.login.app.JNDIConstant;
import com.integrosys.component.login.app.LoginConstant;
import com.integrosys.component.login.app.PersistentEntityType;
import com.integrosys.component.login.app.PolicyLookupManager;
import com.integrosys.component.login.app.SBPasswordManager;
import com.integrosys.component.login.app.SBPasswordManagerHome;

public class LoginValidator {

	protected String role;

	protected String realm;// indicates SME or Retail

	protected String pinType;

	protected int userNameMin;

	protected int userNameMax;

	protected int passwordMin;

	protected int passwordMax;

	protected int minAlphaChars;

	protected int maxRepeatedChars;

	public static final String PROPERTY_PASSWORD_SIMPLE_WORD = "password.simpleword";

	public static final String PROPERTY_LOGINID_FULL_NUMERIC_NOT_ALLOWED = "integrosys.los.smeuser.loginID.alphanumeric.strict";

	// protected int companyMin;
	// protected int companyMax;

	public LoginValidator(String rlm) {
		this(rlm, null);
	}

	public LoginValidator(String rlm, String userRole) {
		this(rlm, userRole, null);
	}

	// for retail pin type is required
	public LoginValidator(String rlm, String userRole, String pinType) {
		// anyway currently role checking is not done, so safely ignore
		role = PersistentEntityType.LOS_USER;
		String passwordPolicyId = PolicyLookupManager.getInstance().getPolicyId(rlm, role);
		SBPasswordManager pwdManager = getPasswordManager();
		IPasswordPolicy passwordPolicy = null;
		try {
			passwordPolicy = pwdManager.getPasswordPolicy(new Long(passwordPolicyId));
		}
		catch (Throwable t) {
			IllegalStateException ex = new IllegalStateException("failed to retrieve password policy for id ["
					+ passwordPolicyId + "] through password manager remote interface.");
			ex.initCause(t);
			throw ex;
		}

		if ((pinType == null) || pinType.equals(LoginConstant.SME_PIN_TYPE1)) {
			pinType = "";
		}
		else {
			this.pinType = pinType;
		}

		realm = rlm;
		userNameMin = PropertyManager.getInt("integrosys." + realm + ".login.username.minlength");
		userNameMax = PropertyManager.getInt("integrosys." + realm + ".login.username.maxlength");
		passwordMin = passwordPolicy.getPwdMinLength();
		passwordMax = passwordPolicy.getPwdMaxLength();
		minAlphaChars = passwordPolicy.getMinAlphaChars();
		maxRepeatedChars = passwordPolicy.getMaxRepeatedChars();
	}

	public ActionErrors validateInput(String userName, String password) {
		String errorCode;
		ActionErrors errors = new ActionErrors();
		if (!(errorCode = Validator.checkString(userName, true, userNameMin, userNameMax)).equals(Validator.ERROR_NONE)) {
			errors.add("userName", new ActionMessage(
					ErrorKeyMapper.map(ErrorKeyMapper.STRING + ".userName", errorCode), "1", "35"));
		}
		if (!(errorCode = Validator.checkString(password, true, passwordMin, passwordMax)).equals(Validator.ERROR_NONE)) {
			errors.add("password", new ActionMessage(
					ErrorKeyMapper.map(ErrorKeyMapper.STRING + ".password", errorCode), "1", "35"));
		}
		return errors;
	}

	public ActionErrors validatePwdChange(String loginID, String newPwd, String confPwd, String oldPwd) {
		String[] formFields = new String[] { "newPassword", "confirmNewPassword", "oldPassword" };
		return validatePwdChange(loginID, newPwd, confPwd, oldPwd, formFields);
	}

	public ActionErrors validatePwdChange(String loginID, String newPwd, String confPwd, String oldPwd,
			String formFields[]) {
		String errorCode;
		ActionErrors errors = new ActionErrors();
		DefaultLogger.debug(this,"LoginValidator break point ...1, pin type:" + pinType);
		if (!(errorCode = Validator.checkString(newPwd, true, passwordMin, passwordMax)).equals(Validator.ERROR_NONE)) {
			if ((pinType == null) || pinType.trim().equals("")) {
				errors.add(formFields[0], new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING + ".newPassword",
						errorCode), "1", "35"));
			}
			else {
				errors.add(formFields[0], new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING
						+ ".secNewPassword", errorCode), "1", "35"));
			}
		}
		if (!(errorCode = Validator.checkString(confPwd, true, passwordMin, passwordMax)).equals(Validator.ERROR_NONE)) {
			if ((pinType == null) || pinType.trim().equals("")) {
				errors.add(formFields[1], new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING
						+ ".confirmNewPassword", errorCode), "1", "35"));
			}
			else {
				errors.add(formFields[1], new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING
						+ ".secConfirmNewPassword", errorCode), "1", "35"));
			}
		}
		if (!(errorCode = Validator.checkString(oldPwd, true, passwordMin, passwordMax)).equals(Validator.ERROR_NONE)) {
			if ((pinType == null) || pinType.trim().equals("")) {
				errors.add(formFields[2], new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING + ".oldPassword",
						errorCode), "1", "35"));
			}
			else {
				errors.add(formFields[2], new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING
						+ ".secOldPassword", errorCode), "1", "35"));
			}
		}
		if ((newPwd.trim().length() > 0) && (errors.size(formFields[0]) == 0) && !isValidPasswordString(newPwd)) {
			errors.add(formFields[0], new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING
					+ ".newPassword.format", "invalid"), "1", "35"));
		}
		if ((newPwd.trim().length() > 0) && (errors.size(formFields[0]) == 0)
				&& !hasMinAlphaChars(newPwd, minAlphaChars)) {
			errors.add(formFields[0], new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING + ".newPassword",
					"minalpha"), "1", "35"));
		}
		if ((newPwd.trim().length() > 0) && (errors.size(formFields[0]) == 0)
				&& hasRepeatedChars(newPwd, maxRepeatedChars)) {
			errors.add(formFields[0], new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING + ".newPassword",
					"maxrepeat"), "1", "35"));
		}
		if ((newPwd.trim().length() > 0) && (errors.size(formFields[0]) == 0) && (errors.size(formFields[0]) == 0)
				&& isValidPasswordString(newPwd)) {
			if ((confPwd.trim().length() > 0) && !newPwd.equals(confPwd)) {
				errors.add(formFields[0], new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING + ".new-conf",
						"mismatch"), "1", "35"));
			}
			else if ((oldPwd.trim().length() > 0) && newPwd.equals(oldPwd)) {
				errors.add(formFields[0], new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING + ".new-old",
						"match"), "1", "35"));
			}
			else if (newPwd.equalsIgnoreCase(loginID)) {
				errors.add(formFields[0], new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING + ".new-loginid",
						"match"), "1", "35"));
			}
		}
		if ((newPwd.trim().length() > 0) && (errors.size(formFields[0]) == 0)) {
			if ((newPwd.trim().length() > 0) && !isAtLeastOneNumaricChar(newPwd)) {
				errors.add(formFields[0], new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING + ".newPassword",
						"minnumaric"), "1", "35"));
			}
		}
		if (newPwd.trim().length() > 0) {// && errors.size(formFields[0]) == 0){
			ArrayList simplePwds = getSimplePasswords();
			for (Iterator iterator = simplePwds.iterator(); iterator.hasNext();) {
				String simplePass = (String) iterator.next();
				// if (newPwd.equalsIgnoreCase(simplePass)){
				if (simplePass != null) {
					if (newPwd.toUpperCase().indexOf(simplePass.toUpperCase()) >= 0) {
						errors.add(formFields[0], new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING
								+ ".newPassword", "simple.password"), "1", "35"));
						break;
					}
				}
			}
		}
		return errors;
	}

	// should be used to validate while changing/assigning password to other
	// users
	// e.g:company admin creating company user
	// limitation: can't verify with old password
	// if ActionErrors passed is null, will create a new one else add to the
	// existing
	public ActionErrors validateOthersPwdChange(String loginID, String newPwd, String confPwd,
			ActionErrors addToActionErrors) {
		String[] formFields = new String[] { "userLoginId", "newPassword", "confirmNewPassword" };
		return validateOthersPwdChange(loginID, newPwd, confPwd, formFields, addToActionErrors);
	}

	public ActionErrors validateOthersPwdChange(String loginID, String newPwd, String confPwd, String formFields[],
			ActionErrors addToActionErrors) {
		String errorCode;
		ActionErrors errors;
		if (addToActionErrors == null) {
			errors = new ActionErrors();
		}
		else {
			errors = addToActionErrors;
		}
		if (!(errorCode = Validator.checkString(loginID, true, userNameMin, userNameMax)).equals(Validator.ERROR_NONE)) {
			errors.add(formFields[0], new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING + ".userName",
					errorCode), "1", "35"));
		}
		if (PropertyManager.getBoolean(PROPERTY_LOGINID_FULL_NUMERIC_NOT_ALLOWED)) {
			if (Validator.checkNumericString(loginID, true, userNameMin, userNameMax).equals(
					ValidatorConstant.ERROR_NONE)) {
				errors.add(formFields[0], new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING
						+ ".userName.numeric", ValidatorConstant.ERROR_NONE), "1", "35"));
			}
		}
		if ((loginID.trim().length() > 0) && !isValidLoginIdString(loginID)) {
			errors.add(formFields[0], new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING + ".userName.format",
					"invalid"), "1", "35"));
		}
		if (!(errorCode = Validator.checkString(newPwd, true, passwordMin, passwordMax)).equals(Validator.ERROR_NONE)) {
			if ((pinType == null) || pinType.trim().equals("")) {
				errors.add(formFields[1], new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING + ".newPassword",
						errorCode), "1", "35"));
			}
			else {
				errors.add(formFields[1], new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING
						+ ".secNewPassword", errorCode), "1", "35"));
			}
		}
		if (!(errorCode = Validator.checkString(confPwd, true, passwordMin, passwordMax)).equals(Validator.ERROR_NONE)) {
			if ((pinType == null) || pinType.trim().equals("")) {
				errors.add(formFields[2], new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING
						+ ".confirmNewPassword", errorCode), "1", "35"));
			}
			else {
				errors.add(formFields[2], new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING
						+ ".secConfirmNewPassword", errorCode), "1", "35"));
			}
		}
		if ((newPwd.trim().length() > 0) && (errors.size(formFields[1]) == 0) && !isValidPasswordString(newPwd)) {
			errors.add(formFields[1], new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING
					+ ".newPassword.format", "invalid"), "1", "35"));
		}
		if ((newPwd.trim().length() > 0) && (errors.size(formFields[1]) == 0)
				&& !hasMinAlphaChars(newPwd, minAlphaChars)) {
			errors.add(formFields[1], new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING + ".newPassword",
					"minalpha"), "1", "35"));
		}
		if ((newPwd.trim().length() > 0) && (errors.size(formFields[1]) == 0)
				&& hasRepeatedChars(newPwd, maxRepeatedChars)) {
			errors.add(formFields[1], new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING + ".newPassword",
					"maxrepeat"), "1", "35"));
		}
		if ((newPwd.trim().length() > 0) && (errors.size(formFields[1]) == 0) && isValidPasswordString(newPwd)) {
			if ((confPwd.trim().length() > 0) && !newPwd.equals(confPwd)) {
				errors.add(formFields[1], new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING + ".new-conf",
						"mismatch"), "1", "35"));
			}
			else if (newPwd.equalsIgnoreCase(loginID)) {
				errors.add(formFields[1], new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING + ".new-loginid",
						"match"), "1", "35"));
			}
		}
		if ((newPwd.trim().length() > 0) && (errors.size(formFields[0]) == 0)) {
			if ((newPwd.trim().length() > 0) && !isAtLeastOneNumaricChar(newPwd)) {
				errors.add(formFields[1], new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING + ".newPassword",
						"minnumaric"), "1", "35"));
			}
		}
		if (newPwd.trim().length() > 0) {// && errors.size(formFields[1]) == 0){
			ArrayList simplePwds = getSimplePasswords();
			for (Iterator iterator = simplePwds.iterator(); iterator.hasNext();) {
				String simplePass = (String) iterator.next();
				// if (newPwd.equalsIgnoreCase(simplePass)){
				if (simplePass != null) {
					if (newPwd.toUpperCase().indexOf(simplePass.toUpperCase()) >= 0) {
						errors.add(formFields[1], new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING
								+ ".newPassword", "simple.password"), "1", "35"));
						break;
					}
				}
			}
		}
		return errors;
	}

	// normally oldLoginId will be from session
	public ActionErrors validateLoginIdChange(String loginId, String confLoginId, String oldLoginId) {
		String errorCode;
		ActionErrors errors = new ActionErrors();
		if (!(errorCode = Validator.checkString(loginId, true, userNameMin, userNameMax)).equals(Validator.ERROR_NONE)) {
			errors.add("newLoginId", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING + ".userName",
					errorCode), "1", "35"));
		}
		if (!(errorCode = Validator.checkString(confLoginId, true, userNameMin, userNameMax))
				.equals(Validator.ERROR_NONE)) {
			errors.add("confirmNewLoginId", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING
					+ ".confUserName", errorCode), "1", "35"));
		}
		if (Validator.checkNumericString(loginId, true, userNameMin, userNameMax).equals(ValidatorConstant.ERROR_NONE)) {
			errors.add("newLoginId", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING + ".userName.numeric",
					ValidatorConstant.ERROR_NONE), "1", "35"));
		}
		if ((loginId.trim().length() > 0) && !isValidLoginIdString(loginId)) {
			errors.add("newLoginId", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING + ".userName.format",
					"invalid"), "1", "35"));
		}
		// redundant checking
		if (loginId.equalsIgnoreCase(oldLoginId)) {
			errors.add("newLoginId", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING + ".userName.old",
					"same"), "1", "35"));
		}
		if ((errors.size("newLoginId") == 0) && (confLoginId != null) && !(loginId.equalsIgnoreCase(confLoginId))) {
			errors.add("newLoginId", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING + ".userName.new-conf",
					"mismatch"), "1", "35"));
		}
		return errors;
	}

	public ActionErrors validateLoginId(String loginId, String formField, ActionErrors addToActionErrors) {
		String errorCode;
		ActionErrors errors;
		if (addToActionErrors == null) {
			errors = new ActionErrors();
		}
		else {
			errors = addToActionErrors;
		}
		if (formField == null) {
			formField = "userLoginId";
		}
		if (!(errorCode = Validator.checkString(loginId, true, userNameMin, userNameMax)).equals(Validator.ERROR_NONE)) {
			errors.add(formField, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING + ".userName", errorCode),
					"1", "35"));
		}

		if (PropertyManager.getBoolean(PROPERTY_LOGINID_FULL_NUMERIC_NOT_ALLOWED)) {
			if (Validator.checkNumericString(loginId, true, userNameMin, userNameMax).equals(
					ValidatorConstant.ERROR_NONE)) {
				errors.add(formField, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING + ".userName.numeric",
						ValidatorConstant.ERROR_NONE), "1", "35"));
			}
		}
		if ((loginId.trim().length() > 0) && !isValidLoginIdString(loginId)) {
			errors.add(formField, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING + ".userName.format",
					"invalid"), "1", "35"));
		}
		return errors;
	}

	private boolean isValidPasswordString(String str) {
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (Character.isWhitespace(ch)) {
				return false;
			}
			if (!isValidEnglishCharOrDigit(ch)) {
				// enter the hex equivalent of allowed special characters
				// !@#$%^&*()_+-=<>[{}'";:/,. are the allowed characters for TFB
				// ? question mark will not be accepted(to avoid thai chars)
				if ((ch != 0x0021) && (ch != 0x0040) && (ch != 0x0023) && (ch != 0x0024) && (ch != 0x0025)
						&& (ch != 0x005E) && (ch != 0x0026) && (ch != 0x002A) && (ch != 0x0028) && (ch != 0x0029)
						&& (ch != 0x005F) && (ch != 0x002B) && (ch != 0x002D) && (ch != 0x003D) && (ch != 0x003C)
						&& (ch != 0x003E) && (ch != 0x005B) && (ch != 0x007B) && (ch != 0x007D) && (ch != 0x0027)
						&& (ch != 0x0022) && (ch != 0x003B) && (ch != 0x003A) && (ch != 0x002F) && (ch != 0x002C)
						&& (ch != 0x002E)) {
					return false;
				}
			}
		}
		return true;
	}

	// repeats -- how many times a character can repeat in String str
	private boolean hasRepeatedChars(String str, int repeats) {
		for (int i = 0; i < str.length(); i++) {
			char[] holder = new char[str.toCharArray().length];
			for (int k = 0; k < repeats + 1; k++) {
				holder[k] = str.charAt(i);
			}
			if (str.indexOf(new String(holder).trim()) > -1) {
				return true;
			}
		}
		return false;
	}

	// to check for min number of alpha characters
	private boolean hasMinAlphaChars(String str, int count) {
		int found = 0;
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (isValidEnglishChar(ch)) {
				found++;
			}
		}
		if (found >= count) {
			return true;
		}
		return false;
	}

	private boolean isValidLoginIdString(String str) {
		if ((str == null) || (str.length() == 0)) {
			return false;
		}
		else {
			for (int i = 0; i < str.length(); i++) {
				if (Character.isWhitespace(str.charAt(i))) {
					return false;
				}

				if (!isValidEnglishCharOrDigit(str.charAt(i))) {
					if ((str.charAt(i) != 0x005F) && (str.charAt(i) != 0x002E)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	private boolean isValidEnglishCharOrDigit(char ch) {
		if (((ch >= 0x0041) && (ch <= 0x005A)) || ((ch >= 0x0061) && (ch <= 0x007A))
				|| ((ch >= 0x0030) && (ch <= 0x0039))) {
			return true;
		}
		return false;
	}

	private boolean isValidEnglishChar(char ch) {
		if (((ch >= 0x0041) && (ch <= 0x005A)) || ((ch >= 0x0061) && (ch <= 0x007A))) {
			return true;
		}
		return false;
	}

	private SBPasswordManager getPasswordManager() {
		SBPasswordManager passManager = (SBPasswordManager) BeanController.getEJB(JNDIConstant.SB_PWDPOLICY_MGR_JNDI,
				SBPasswordManagerHome.class.getName());
		return passManager;
	}

	private boolean isAtLeastOneNumaricChar(String pwd) {
		for (int i = 0; i < pwd.length(); i++) {
			if ((pwd.charAt(i) >= 0x0030) && (pwd.charAt(i) <= 0x0039)) {
				return true;
			}
		}
		return false;
	}

	public ArrayList getSimplePasswords() {
		ArrayList tokens = null;
		String simpleStr = PropertyManager.getValue(PROPERTY_PASSWORD_SIMPLE_WORD);
		if ((simpleStr != null) && (simpleStr.length() > 2)) {
			tokens = new ArrayList();
			StringTokenizer tokenizer = new StringTokenizer(simpleStr, ",");
			while (tokenizer.hasMoreTokens()) {
				tokens.add(tokenizer.nextToken());
			}
		}
		return tokens;
	}
}
