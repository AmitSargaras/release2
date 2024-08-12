package com.integrosys.cms.host.eai.security;

/**
 * Exception to be raised when the security is not found in the persistent
 * storage or the message itself.
 * 
 * @author Thurein
 * @author Chong Jun Yong
 */
public class NoSuchSecurityException extends EAISecurityMessageException {

	private static final long serialVersionUID = 6430605120610306379L;

	private static final String COL_NO_FOUND_ERROR_CODE = "COL_NOT_FOUND";

	/**
	 * Constructor to provide Security info information
	 * 
	 * @param securityId source security id of the collateral
	 * @param sourceId source id of the security
	 */
	public NoSuchSecurityException(String securityId, String sourceId) {
		super("Security is not found in the system; for Security ID [" + securityId + "], Source ID [" + sourceId + "]");
	}

	public NoSuchSecurityException(long cmsSecurityId) {
		super("Security is not found in the system; for CMS security key [" + cmsSecurityId + "]");
	}

	public String getErrorCode() {
		return COL_NO_FOUND_ERROR_CODE;
	}
}
