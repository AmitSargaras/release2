package com.integrosys.base.businfra.authentication;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.login.AuthenticationException;
import com.integrosys.base.businfra.login.IAuthenticator;
import com.integrosys.base.businfra.login.ICredentials;
import com.integrosys.base.businfra.login.IPolicy;
import com.integrosys.base.businfra.login.InvalidCredentialsException;
import com.integrosys.component.login.app.LoginErrorCodes;

/**
 * Implementation of {@link AuthenticationManager} to interface with CMS own
 * authentication manager.
 * 
 * @author Chong Jun Yong
 * 
 */
public class CMSAuthenticationManager implements AuthenticationManager {

	private IAuthenticator authenticator;

	public void setAuthenticator(IAuthenticator authenticator) {
		this.authenticator = authenticator;
	}

	public IAuthenticator getAuthenticator() {
		return authenticator;
	}

	public Object authenticate(Object credential) throws AuthenticationException {
		Validate.notNull(credential, "'credential' to be authenticated must not be null.");
		Validate.isTrue(credential instanceof ICredentials, "'credential' must be instance of ICredential");

		ICredentials actualCredential = (ICredentials) credential;

		try {
			return new Boolean(getAuthenticator().authenticate(actualCredential));
		}
		catch (InvalidCredentialsException ex) {
			AuthenticationException aex = new AuthenticationException("invalid login info provided, login id ["
					+ actualCredential.getLoginId() + "]", ex);
			aex.setErrorCode(LoginErrorCodes.INVALID_CREDENTIALS);
			throw aex;
		}
	}

	public Object authenticateAndLogin(Object credential) throws AuthenticationException {
		Validate.notNull(credential, "'credential' to be authenticated must not be null.");
		Validate.isTrue(credential instanceof ICredentials, "'credential' must be instance of ICredential");

		ICredentials actualCredential = (ICredentials) credential;

		try {
			return getAuthenticator().login(actualCredential);
		}
		catch (InvalidCredentialsException ex) {
			AuthenticationException aex = new AuthenticationException("invalid login info provided, login id ["
					+ actualCredential.getLoginId() + "]", ex);
			aex.setErrorCode(LoginErrorCodes.INVALID_CREDENTIALS);
			throw aex;
		}
	}

	public Object changePassword(Object credential, Object newPassword) throws AuthenticationException {
		Validate.notNull(credential, "'credential' to be authenticated must not be null.");
		Validate.isTrue(credential instanceof ICredentials, "'credential' must be instance of ICredential");
		Validate.notNull(newPassword, "'newPassword' to be authenticated must not be null.");

		ICredentials actualCredential = (ICredentials) credential;
		String actualNewPassword = (String) newPassword;

		try {
			getAuthenticator().changePassword(actualCredential, actualNewPassword);
		}
		catch (InvalidCredentialsException ex) {
			AuthenticationException aex = new AuthenticationException("invalid login info provided, login id ["
					+ actualCredential.getLoginId() + "]", ex);
			aex.setErrorCode(LoginErrorCodes.INVALID_CREDENTIALS);
			throw aex;
		}

		return null;
	}

	public Object retrievePasswordPolicy(Object credential) throws AuthenticationException {
		Validate.notNull(credential, "'credential' to be retrieve password policy must not be null.");

		ICredentials actualCredential = (ICredentials) credential;

		try {
			IPolicy passwordPolicy = getAuthenticator().retrievePolicy(null, actualCredential);
			return passwordPolicy;
		}
		catch (InvalidCredentialsException e) {
			AuthenticationException aex = new AuthenticationException("invalid login info provided, login id ["
					+ actualCredential.getLoginId() + "]", e);
			aex.setErrorCode(LoginErrorCodes.INVALID_CREDENTIALS);
			throw aex;
		}
	}
}
