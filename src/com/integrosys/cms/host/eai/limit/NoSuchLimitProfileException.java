package com.integrosys.cms.host.eai.limit;

/**
 * Exception to be raised whenever AA / Limit Profile is not found in the
 * system.
 * 
 * @author Chong Jun Yong
 * 
 */
public class NoSuchLimitProfileException extends EaiLimitProfileMessageException {

	private static final long serialVersionUID = 2113683018199943228L;

	private static final String AA_NOT_FOUND_ERROR_CODE = "AA_NOT_FOUND";

	private String cifId;

	private String hostAANumer;

	private String losAANumber;

	private String losAASource;

	/**
	 * Default constructer to provide LOS AA Number
	 * 
	 * @param losAANumber LOS AA Number
	 */
	public NoSuchLimitProfileException(String losAANumber) {
		super("AA not found in the system; for LOS AA Number [" + losAANumber + "]");
	}

	/**
	 * Default construct to provide cif number, sub profile id, and limit
	 * profile id (CMS internal key)
	 * 
	 * @param cifId the cif number, customer id
	 * @param subProfileId customer sub profile id,
	 * @param cmsLimitProfileId cms limit profile id, internal key
	 */
	public NoSuchLimitProfileException(String cifId, long subProfileId, long cmsLimitProfileId) {
		super("AA not found in the system; for CIF [" + cifId + "], Sub Profile Id [" + subProfileId
				+ "], AA CMS Key [" + cmsLimitProfileId + "]");
	}

	/**
	 * Default constructor to provide, cif number, host AA number, LOS AA number
	 * 
	 * @param cifId the cif number, customer id
	 * @param hostAANumber the host AA number, might be the same for same
	 *        customer from same place(center / branch)
	 * @param losAANumber the LOS AA number
	 */
	public NoSuchLimitProfileException(String cifId, String hostAANumber, String losAANumber) {
		super("AA not found in the system; for CIF [" + cifId + "], Host AA Number [" + hostAANumber
				+ "], LOS AA Number [" + losAANumber + "].");
		this.cifId = cifId;
		this.hostAANumer = hostAANumber;
		this.losAANumber = losAANumber;
	}

	/**
	 * Default constructor to provide, LOS AA number and LOS AA source
	 * 
	 * @param losAANumber the LOS AA number
	 * @param losAASource the LOS AA source
	 */
	public NoSuchLimitProfileException(String losAANumber, String losAASource) {
		super("AA not found in the system; for LOS AA Number [" + losAANumber + "], Source [" + losAASource + "]");
		this.losAANumber = losAANumber;
		this.losAASource = losAASource;
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

	public String getLosAASource() {
		return losAASource;
	}

	public String getErrorCode() {
		return AA_NOT_FOUND_ERROR_CODE;
	}
}
