package com.integrosys.cms.ui.login;

import java.util.Vector;

import javax.naming.NamingException;

import com.integrosys.base.businfra.login.ICredentials;
import com.integrosys.base.businfra.login.LDAPConnection;

public class ADLoginHelper {
	
	LDAPConnection connection;
	
/*	public static OBLoginInfo authenticateAndLogin(ICredentials credentials) throws AuthenticationException {
		LDAPConnection conn = new WinLDAPConnection(credentials.getLoginId(), credentials.getClearTextPassword());
		try {
			conn.getDirContext();
			OBLoginInfo loginInfo = new OBLoginInfo();
			loginInfo.setSessionExpiry(60000);
			loginInfo.setLoginId(credentials.getLoginId());
			loginInfo.setCompanyId(credentials.getCompanyId());
			loginInfo.setLastLoginTime(new java.util.Date());
			loginInfo.setLastLogoutTime(new java.util.Date());
			return loginInfo;
		} catch (NamingException e) {
			AuthenticationException ae = new AuthenticationException(e.getMessage());
			ae.setErrorCode(LoginErrorCodes.INVALID_CREDENTIALS);
			throw ae;
		}
		
	}*/
	
	public static Vector authenticateAndLogin(ICredentials credentials) {
		Vector searchResult = null;
		LDAPConnection conn = new WinLDAPConnection(credentials.getLoginId(), credentials.getClearTextPassword());
		try {
			conn.getDirContext();
			searchResult = new Vector();
		} catch (NamingException e) {
			searchResult = null;
		}
		return searchResult;
	}
}
