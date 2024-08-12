package com.integrosys.cms.host.eai.security;

import com.integrosys.cms.host.eai.RecordAlreadyExistsException;

/**
 * Exception to be raised whenever the security to be created into system
 * already exists.
 * 
 * @author Chong Jun Yong
 * 
 */
public class SecurityAlreadyExistsException extends RecordAlreadyExistsException {

	private static final long serialVersionUID = 2332632492011462011L;

	private static final String SECURITY_ALREADY_EXISTS_ERROR_CODE = "SECURITY_EXISTS";

	/**
	 * Default constructor to provide source security id, and source id,
	 * normally the information from message itself.
	 * @param securityId security id from source
	 * @param sourceId source id
	 */
	public SecurityAlreadyExistsException(String securityId, String sourceId) {
		super("Security already exists in the system, but try to create again; Security Id [" + securityId
				+ "], Source Id [" + sourceId + "]");
	}

	/**
	 * Default construct to provide CMS Security internal key
	 * @param cmsSecurityId CMS Security internal key
	 */
	public SecurityAlreadyExistsException(long cmsSecurityId) {
		super("Security already exists in the system, but try to create again; CMS Security Key [" + cmsSecurityId + "]");
	}

	public String getErrorCode() {
		return SECURITY_ALREADY_EXISTS_ERROR_CODE;
	}
}
