package com.integrosys.base.businfra.authentication;

import com.integrosys.base.businfra.login.AuthenticationException;

/**
 * <p>
 * Authentication Service to authenticate and/or login a credential supplied.
 * <p>
 * Subclass can deal with various kind of authentication mechanism. Can be local
 * or remote.
 * 
 * @author Chong Jun Yong
 * @see com.integrosys.base.businfra.login.SBAuthenticationManagerBean
 */
public interface AuthenticationManager {
	/**
	 * Authentication credential subject and return result, can be true or
	 * false.
	 * 
	 * @param credential credential object, minimumly consist of login id and
	 *        password
	 * @return authentication result, can be true or false
	 * @throws AuthenticationException if there is any error encounter, such as
	 *         invalid user, password mismatch.
	 */
	public Object authenticate(Object credential) throws AuthenticationException;

	/**
	 * Authentication credential subject and return login info at the same time,
	 * which to be used later by the login system.
	 * 
	 * @param credential credential object, minimumly consist of login id and
	 *        password
	 * @return authentication result, can be true or false
	 * @throws AuthenticationException if there is any error encounter, such as
	 *         invalid user, password mismatch.
	 */
	public Object authenticateAndLogin(Object credential) throws AuthenticationException;

	/**
	 * Change password of the login info. Credential info need to be provided
	 * with old password supplied as well.
	 * 
	 * @param credential credential object, minimumly consist of login id and
	 *        password (in this case, old password)
	 * @param newPassword newly password to replace old password.
	 * @return change password result, could be true or false to indicate the
	 *         success
	 * @throws AuthenticationException if there is any error encountered, such
	 *         as invalid user, password mismatch, fail password policy.
	 */
	public Object changePassword(Object credential, Object newPassword) throws AuthenticationException;

	/**
	 * Retrieve the password policy used for the credential.
	 * 
	 * @param credential the credential object, must at least consist of login
	 *        id and/or role
	 * @return the password policy used for the credential
	 * @throws AuthenticationException if there any error encountered, such as
	 *         invalid user.
	 */
	public Object retrievePasswordPolicy(Object credential) throws AuthenticationException;
}