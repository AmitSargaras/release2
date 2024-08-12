package com.integrosys.cms.host.eai.limit;

import com.integrosys.cms.host.eai.RecordAlreadyExistsException;

/**
 * Exception to be raised to indicate that the AA / Limit Profile is found in
 * the system but SI trying to create again.
 * 
 * @author Chong Jun Yong
 * 
 */
public class LimitProfileAlreadyExistsException extends RecordAlreadyExistsException {

	private static final long serialVersionUID = -1999593766747935405L;

	private static final String LIMIT_PROFILE_ALREADY_EXISTS_ERROR_CODE = "AA_EXISTS";

	private String cifId;

	private String hostAANumer;

	private String losAANumber;

	/**
	 * Default constructor to provide, cif number, host AA number, LOS AA number
	 * 
	 * @param cifId the cif number, customer id
	 * @param hostAANumber the host AA number, might be the same for same
	 *        customer from same place(center / branch)
	 * @param losAANumber the LOS AA number
	 */
	public LimitProfileAlreadyExistsException(String cifId, String hostAANumber, String losAANumber) {
		super("AA already exists in the system; for CIF [" + cifId + "], Host AA Number [" + hostAANumber
				+ "], LOS AA Number [" + losAANumber + "].");
		this.cifId = cifId;
		this.hostAANumer = hostAANumber;
		this.losAANumber = losAANumber;
	}

	/**
	 * Default constructor to provide, cif number, LOS AA number
	 * 
	 * @param cifId the cif number, customer id,
	 * @param losAANumber the LOS AA number
	 */
	public LimitProfileAlreadyExistsException(String cifId, String losAANumber) {
		super("AA already exists in the system; for CIF [" + cifId + "], LOS AA Number [" + losAANumber + "].");
		this.cifId = cifId;
		this.losAANumber = losAANumber;
	}

	public String getCifId() {
		return cifId;
	}

	public String getHostAANumer() {
		return hostAANumer;
	}

	public String getLosAANumber() {
		return losAANumber;
	}

	public String getErrorCode() {
		return LIMIT_PROFILE_ALREADY_EXISTS_ERROR_CODE;
	}
}
