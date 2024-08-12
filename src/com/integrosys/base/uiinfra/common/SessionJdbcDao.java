package com.integrosys.base.uiinfra.common;

/**
 * Jdbc based DAO to deal with the user session, such as clear session on
 * startup, clear session using session id, etc.
 * 
 * @author Chong Jun Yong
 * 
 */
public interface SessionJdbcDao {
	/**
	 * Clear all user session from the persistent storage
	 * 
	 * @return number of session get cleared
	 */
	public int clearSession();

	/**
	 * Clear user session using login id.
	 * 
	 * @param loginId the login id of the user
	 */
	public void clearSessionByLoginId(String loginId);

	/**
	 * Clear user session using session id
	 * 
	 * @param sessionId the session id of the user
	 */
	public void clearSessionBySessionId(String sessionId);
}
