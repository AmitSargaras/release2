package com.integrosys.cms.ui.profile;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.constant.BusinfraJNDIConstant;
import com.integrosys.base.businfra.login.AuthenticationException;
import com.integrosys.base.businfra.login.ICredentials;
import com.integrosys.base.businfra.login.InvalidCredentialsException;
import com.integrosys.base.businfra.login.OBCredentials;
import com.integrosys.base.businfra.login.SBAuthenticationManager;
import com.integrosys.base.businfra.login.SBAuthenticationManagerHome;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.login.app.LoginErrorCodes;

public class PasswordChangeCommand extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Default Constructor
	 */
	public PasswordChangeCommand() {

	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "event", "java.lang.String", REQUEST_SCOPE },
				{ "passwordChange", "com.integrosys.cms.ui.profile.OBPasswordChange", FORM_SCOPE },
				{ IGlobalConstant.GLOBAL_USER_LOGIN_ID, "java.lang.String", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_AUTHENTICATION_ROLE, "java.lang.String", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_AUTHENTICATION_REALM, "java.lang.String", GLOBAL_SCOPE },
				{ "locale", "java.util.Locale", REQUEST_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "event", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here preparation for company borrower is
	 * done.
	 * 
	 * @param map is of type HashMap
	 * @throws CommandProcessingException on errors
	 * @throws CommandValidationException on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap result = new HashMap();
		try {
			OBPasswordChange pwdChange = (OBPasswordChange) map.get("passwordChange");
			// revalidate the hidden form fields
			String loginId = (String) map.get(IGlobalConstant.GLOBAL_USER_LOGIN_ID);
			String role = (String) map.get(IGlobalConstant.GLOBAL_AUTHENTICATION_ROLE);
			String realm = (String) map.get(IGlobalConstant.GLOBAL_AUTHENTICATION_REALM);
			if (!loginId.equals(pwdChange.getLoginId())) {
				throw new CommandValidationException();
			}
			if (!role.equals(pwdChange.getRole())) {
				throw new CommandValidationException();
			}
			if (!realm.equals(pwdChange.getRealm())) {
				throw new CommandValidationException();
			}
			ICredentials credentials = getCredentials(pwdChange);
			getAuthenticationManager().changePassword(credentials, pwdChange.getNewPasswd());

			// return result;
		}
		catch (AuthenticationException atex) {
			String errorCode = null;
			if (atex.getErrorCode() != null) {
				errorCode = atex.getErrorCode();
			}
			else {
				errorCode = LoginErrorCodes.GENERAL_LOGIN_ERROR;
			}
			HashMap aMap = new HashMap();
			aMap.put("newPasswd", new ActionMessage("error.string." + errorCode));
			returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, aMap);
		}
		catch (InvalidCredentialsException ex) {
			ex.printStackTrace();
			HashMap aMap = new HashMap();
			aMap.put("oldPasswd", new ActionMessage("error.string.invalid.credentials"));
			returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, aMap);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new CommandProcessingException();
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		return returnMap;
	}

	private SBAuthenticationManager getAuthenticationManager() {
		return (SBAuthenticationManager) BeanController.getEJB(BusinfraJNDIConstant.SB_AUTHENTICATION_MGR_HOME,
				SBAuthenticationManagerHome.class.getName());
	}

	private ICredentials getCredentials(OBPasswordChange anOb) {
		OBCredentials credentials = new OBCredentials();
		credentials.setLoginId(anOb.getLoginId());
		credentials.setPassword(anOb.getOldPasswd()); // set for old password .
		credentials.setPinType(anOb.getPinType());
		credentials.setRealm(anOb.getRealm());
		credentials.setRole(anOb.getRole());
		return (ICredentials) credentials;
	}

}
