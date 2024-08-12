/*
 * Created by IntelliJ IDEA.
 * User: Sulin
 * Date: Oct 20, 2003
 * Time: 2:51:15 PM
 */
package com.integrosys.cms.host.eai.limit;

/**
 * Exception to be raised when the limit is not found in the persistent storage
 * or the message itself.
 * 
 * @author Chong Jun Yong
 * @since 1.0
 */
public class NoSuchLimitException extends EaiLimitProfileMessageException {

	private static final long serialVersionUID = -119349544127172966L;

	private static final String FAC_NOT_FOUND_ERROR_CODE = "FAC_NOT_FOUND";

	/**
	 * Default Constructor to provide error message
	 * 
	 * @param msg error message for this exception
	 */
	public NoSuchLimitException(String msg) {
		super(msg);
	}

	/**
	 * Default Constructor to provide error message and throwable cause.
	 * 
	 * @param msg error message for this exception
	 * @param cause exception that cause this exception to be thrown
	 */
	public NoSuchLimitException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * Default Constructor to provide the error that CMS Limit key provided is
	 * not found in the system.
	 * 
	 * @param losAANumber LOS AA Number
	 * @param cmsLimitProfileId CMS AA Key
	 * @param cmsLimitId CMS Limit Key
	 */
	public NoSuchLimitException(String losAANumber, long cmsLimitProfileId, long cmsLimitId) {
		super("Limit Info not found in the system; LOS AA Number [" + losAANumber + "], CMS AA Key ["
				+ cmsLimitProfileId + "], CMS Limit Key [" + cmsLimitId + "]");
	}

	/**
	 * Default Constructor to provide the error that CMS Limit key provided is
	 * not found in the system.
	 * 
	 * @param cmsLimitProfileId CMS AA Key
	 * @param cmsLimitId CMS Limit Key
	 */
	public NoSuchLimitException(long cmsLimitProfileId, long cmsLimitId) {
		super("Limit Info not found in the system; CMS AA Key [" + cmsLimitProfileId + "], CMS Limit Key ["
				+ cmsLimitId + "]");
	}

	/**
	 * Constructor to provide limit info information, such le id, sub profile
	 * id, limit profile id and limit id
	 * 
	 * @param cifId the customer le id / CIF id
	 * @param subProfileId customer sub profile id
	 * @param cmsLimitProfileId AA limit profile id
	 * @param limitId limit id
	 */
	public NoSuchLimitException(String cifId, long subProfileId, long cmsLimitProfileId, String limitId) {
		super("Limit Info not found in the system; CIF [" + cifId + "], SubProfile ID [" + subProfileId
				+ "], CMS AA Key [" + cmsLimitProfileId + "], Limit ID [" + limitId + "].");
	}

	/**
	 * Constructor to provide limit info, such as LE Id, LOS Limit Id
	 * @param CIFNo cif number
	 * @param LOSLimitId LOS Limit Id
	 */
	public NoSuchLimitException(String CIFNo, String LOSLimitId) {
		super("Limit Info not found in the system;  CIF [" + CIFNo + "], LOS LimitId [" + LOSLimitId + "]");
	}

	public String getErrorCode() {
		return FAC_NOT_FOUND_ERROR_CODE;
	}
}
