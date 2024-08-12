package com.integrosys.cms.host.eai.limit;

import com.integrosys.cms.host.eai.RecordAlreadyExistsException;

/**
 * Exception to be raised whenever the same limit security linkage (ie, same
 * limit and collateral) is being created again.
 * @author Chong Jun Yong
 * 
 */
public class LimitSecMapAlreadyExistsException extends RecordAlreadyExistsException {

	private static final long serialVersionUID = -893710107149516532L;

	private static final String LIMIT_SEC_MAP_EXISTS_ERROR_CODE = "LMTSEC_MAP_EXISTS";

	/**
	 * Constructor to provide source limit and collateral id
	 * @param sourceLimitId source limit id
	 * @param sourceSecurityId source security/collateral id
	 */
	public LimitSecMapAlreadyExistsException(String sourceLimitId, String sourceSecurityId) {
		super("Limit Security Linkage already exists in the system, but try to create again; Limit Id ["
				+ sourceLimitId + "], Security Id [" + sourceSecurityId + "]");
	}

	/**
	 * Constructor to provide CMS limit and collateral internal key
	 * @param cmsLimitId CMS limit internal key
	 * @param cmsCollateralId CMS collateral internal key
	 */
	public LimitSecMapAlreadyExistsException(long cmsLimitId, long cmsCollateralId) {
		super("Limit Security Linkage already exists in the system, but try to create again; CMS Limit internal key ["
				+ cmsLimitId + "], CMS Collateral internal key [" + cmsCollateralId + "]");
	}

	public String getErrorCode() {
		return LIMIT_SEC_MAP_EXISTS_ERROR_CODE;
	}

}
