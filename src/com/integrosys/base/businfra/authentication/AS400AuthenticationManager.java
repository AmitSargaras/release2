package com.integrosys.base.businfra.authentication;

import java.io.IOException;
import java.util.Date;
import java.beans.PropertyVetoException;

import org.apache.commons.lang.Validate;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400SecurityException;
import com.integrosys.base.businfra.login.AuthenticationException;
import com.integrosys.base.businfra.login.ICredentials;
import com.integrosys.base.businfra.login.IPersistentBroker;
import com.integrosys.base.businfra.login.OBLoginInfo;
import com.integrosys.component.login.app.LoginConstant;
import com.integrosys.component.login.app.LoginErrorCodes;
import com.integrosys.component.login.app.OBLOSCustomer;
import com.integrosys.component.login.app.PersistentEntityType;

/**
 * Implementation of {@link AuthenticationManager} to interface with AS400 for
 * authentication and change password purpose only.
 * 
 * @author Chong Jun Yong
 * 
 */
public class AS400AuthenticationManager implements AuthenticationManager {

	private AS400 as400system;

	private IPersistentBroker persistentBroker;

	public void setAs400system(AS400 as400system) {
		this.as400system = as400system;
	}

	public AS400 getAs400system() {
		return as400system;
	}

	/**
	 * @return the persistentProker
	 */
	public IPersistentBroker getPersistentBroker() {
		return persistentBroker;
	}

	/**
	 * @param persistentProker the persistentProker to set
	 */
	public void setPersistentBroker(IPersistentBroker persistentBroker) {
		this.persistentBroker = persistentBroker;
	}

	public Object authenticate(Object credential) throws AuthenticationException {
		Validate.notNull(credential, "'credential' to be authenticated must not be null.");
		Validate.isTrue(credential instanceof ICredentials, "'credential' must be instance of ICredential");

		ICredentials actualCredential = (ICredentials) credential;

		try {
			return new Boolean(getAs400system().authenticate(actualCredential.getLoginId(),
					actualCredential.getClearTextPassword()));
		}
		catch (AS400SecurityException e) {
			handleAs400SecurityException(e, actualCredential);
		}
		catch (IOException e) {
			throw new AuthenticationException("Failed to logon to as400 system [" + getAs400system().getSystemName()
					+ "]", e);
		}

		return null;
	}

	public Object authenticateAndLogin(Object credential) throws AuthenticationException {
		Validate.notNull(credential, "'credential' to be authenticated must not be null.");
		Validate.isTrue(credential instanceof ICredentials, "'credential' must be instance of ICredential");

		ICredentials actualCredential = (ICredentials) credential;
		authenticate(actualCredential);

		OBLOSCustomer smeCust = new OBLOSCustomer();
		smeCust.setLoginId(actualCredential.getLoginId());
		smeCust.setRole(actualCredential.getRole());

		OBLOSCustomer cust = (OBLOSCustomer) getPersistentBroker().retrieve(smeCust, PersistentEntityType.LOS_USER);

        //Andy Wong, 17 Nov 2008: failed user login when credential not found in CMS
        if(cust == null) {
            throw throwAuthenticationException(
                        "fail to authenticate user because no setup in CMS, user id provided ["
                                + actualCredential.getLoginId() + "]", LoginErrorCodes.INVALID_CREDENTIALS, null);
        }
        
        OBLoginInfo loginInfo = new OBLoginInfo();
		loginInfo.setLoginId(cust.getLoginId());
		loginInfo.setStatus(cust.getStatus());
		loginInfo.setSessionExpiry(Integer.parseInt(cust.getSessionExpiry()));
		loginInfo.setForcePasswordChange(Boolean.valueOf(cust.getForcePasswordChange()).booleanValue());
		if (cust.getLastLoginTime() != null) {
			loginInfo.setLastLoginTime(cust.getLastLoginTime());
		}
		if (cust.getLastLogoutTime() != null) {
			loginInfo.setLastLogoutTime(cust.getLastLogoutTime());
		}

		cust.setLoginAttempts(new Integer(LoginConstant.SME_PIN_TYPE1_RESET).toString());
		cust.setLastLoginTime(new Date());
		cust.setLastLoginIp(actualCredential.getLastLoginIp());
		getPersistentBroker().update(cust, PersistentEntityType.LOS_USER);

		return loginInfo;
	}

	public Object changePassword(Object credential, Object newPassword) throws AuthenticationException {
		Validate.notNull(credential, "'credential' to be authenticated must not be null.");
		Validate.notNull(newPassword, "'newPassword' to be authenticated must not be null.");
		Validate.isTrue(credential instanceof ICredentials, "'credential' must be instance of ICredential");

		ICredentials actualCredential = (ICredentials) credential;

		try {
            //Andy Wong: need to reset AS400 services since AS400 bean is singleton, causing old userId retrieved
            getAs400system().resetAllServices();
            getAs400system().setUserId(actualCredential.getLoginId());
			getAs400system().changePassword(actualCredential.getClearTextPassword(), (String) newPassword);
		} catch (AS400SecurityException e) {
			handleAs400SecurityException(e, actualCredential);
		} catch (IOException e) {
			throw new AuthenticationException("Failed to logon to as400 system [" + getAs400system().getSystemName()
					+ "]", e);
		} catch (PropertyVetoException e) {
            throw new AuthenticationException("Failed to set user Id in as400 API", e);
        }

        return null;
	}

	public Object retrievePasswordPolicy(Object credential) throws AuthenticationException {
		throw new IllegalStateException("this implementation shouldn't be implemented by AS400 authentication manager.");
	}

	protected void handleAs400SecurityException(AS400SecurityException e, ICredentials credential)
			throws AuthenticationException {
		switch (e.getReturnCode()) {
		case AS400SecurityException.PASSWORD_ERROR:
			throw throwAuthenticationException(
					"fail to authenticate user because of error password, user id provided [" + credential.getLoginId()
							+ "]", LoginErrorCodes.USED_PASSWORD, e);
		case AS400SecurityException.PASSWORD_INCORRECT:
			throw throwAuthenticationException(
					"fail to authenticate user because of incorrect password, user id provided ["
							+ credential.getLoginId() + "]", LoginErrorCodes.INVALID_CREDENTIALS, e);
		case AS400SecurityException.PASSWORD_EXPIRED:
			throw throwAuthenticationException(
					"fail to authenticate user because of password is expired, user id provided ["
							+ credential.getLoginId() + "]", LoginErrorCodes.ACCT_EXPIRED, e);
		case AS400SecurityException.USERID_DISABLE:
			throw throwAuthenticationException("user is disabled in the system, user id provided ["
					+ credential.getLoginId() + "]", LoginErrorCodes.ACCT_DISABLED, e);
//		case AS400SecurityException.PASSWORD_INCORRECT_USERID_DISABLE:
//			throw throwAuthenticationException(
//					"fail to authenticate user because of incorrect password, user will be disabled, user id provided ["
//							+ credential.getLoginId() + "]", LoginErrorCodes.ACCT_DISABLED, e);
//		case AS400SecurityException.PASSWORD_NOT_SET:
//			throw throwAuthenticationException(
//					"fail to authenticate user because of password is not set, possible? user id provided ["
//							+ credential.getLoginId() + "]", LoginErrorCodes.GENERAL_LOGIN_ERROR, e);
//		case AS400SecurityException.USERID_NOT_SET:
//			throw throwAuthenticationException("user id is not set, possible? user id provided ["
//					+ credential.getLoginId() + "]", LoginErrorCodes.GENERAL_LOGIN_ERROR, e);
//		case AS400SecurityException.USERID_UNKNOWN:
//			throw throwAuthenticationException("no such user in the system, user id provided ["
//					+ credential.getLoginId() + "]", LoginErrorCodes.GENERAL_LOGIN_ERROR, e);
		default:
			throw throwAuthenticationException("fail to authenticate user [" + credential.getLoginId() + "]",
					LoginErrorCodes.GENERAL_LOGIN_ERROR, e);
		}
	}

	/**
	 * Create a new Authentication based on the message, error code and
	 * exception (AS400SecurityException) supplied. Then caller can based on the
	 * error code to throw necessary login exception.
	 * 
	 * @param msg error message to construct the exception
	 * @param errorCdoe error code, cms specific.
	 * @param e instance of AS400SecurityException thrown when interface with
	 *        AS400 system.
	 * @return new constructed authentication exception.
	 */
	protected AuthenticationException throwAuthenticationException(String msg, String errorCdoe,
			AS400SecurityException e) {
		AuthenticationException ex = new AuthenticationException(msg, e);
		ex.setErrorCode(errorCdoe);
		return ex;
	}
}
