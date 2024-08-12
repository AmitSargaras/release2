package com.integrosys.cms.host.eai.limit;

import com.integrosys.cms.host.eai.RecordAlreadyExistsException;

/**
 * Exception to be raised whenever there is creation of limit which already in
 * the system.
 * 
 * @author Chong Jun Yong
 * 
 */
public class LimitAlreadyExistsException extends RecordAlreadyExistsException {
	private static final long serialVersionUID = 3432490682224718741L;

	private static final String LIMIT_ALREADY_EXISTS_ERROR_CODE = "LIMIT_EXISTS";

	/**
	 * Default constructor to provide error whenever the Limit in the message
	 * already created in the system
	 * 
	 * @param cifId Customer Id
	 * @param losAANumber LOS AA Number
	 * @param limitId Limit Id from Source
	 */
	public LimitAlreadyExistsException(String cifId, String losAANumber, String limitId) {
		super("Limit already exists in the system, but try to create again; CIF [" + cifId + "], LOS AA Number ["
				+ losAANumber + "], Limit Id [" + limitId + "]");
	}

	/**
	 * Default constructor to provide error wheneer the Limit (after retrieve
	 * based on key in message) already created in the system
	 * 
	 * @param cmsLimitProfileId CMS AA internal key
	 * @param cmsLimitId CMS Limit key
	 */
	public LimitAlreadyExistsException(long cmsLimitProfileId, long cmsLimitId) {
		super("Limit already exists in the system, but try to create again; CMS AA Key [" + cmsLimitProfileId
				+ "], CMS Limit Key [" + cmsLimitId + "]");
	}

	public String getErrorCode() {
		return LIMIT_ALREADY_EXISTS_ERROR_CODE;
	}
}
