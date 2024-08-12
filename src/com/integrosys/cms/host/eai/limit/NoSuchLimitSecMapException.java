package com.integrosys.cms.host.eai.limit;

/**
 * Exception to be raised whenever the Limit Security Map is not found in the
 * system.
 * 
 * @author Chong Jun Yong
 * 
 */
public class NoSuchLimitSecMapException extends EaiLimitProfileMessageException {

	private static final long serialVersionUID = -5262604052609156028L;

	private static final String NO_LIMIT_SEC_MAP_FOUND_ERROR_CODE = "NO_LMTSEC_MAP_FOUND";

	/**
	 * Default constructor to provide error when the CMS Limit Sec Map key is
	 * not found.
	 * 
	 * @param losAANumber LOS AA Number
	 * @param cmsLimitSecMapId CMS Limit Security Map internal key
	 */
	public NoSuchLimitSecMapException(String losAANumber, long cmsLimitSecMapId) {
		super("Limit Security Linkage not found in the system; LOS AA Number [" + losAANumber
				+ "], CMS Limit Security Map key [" + cmsLimitSecMapId + "]");
	}

	/**
	 * Default constructor to provide all CMS internal key info on Limit,
	 * Collateral, AA when limit security map is not found. Normally this was
	 * raised when the limit charge map cannot find the corresponding limit
	 * security map.
	 * 
	 * @param cmsLimitId CMS Limit internal key
	 * @param cmsSecurityId CMS Collateral internal key
	 * @param cmsLimitProfileId CMS AA internal key
	 * @param isActual to indicate whether retrieve from actual or staging table
	 */
	public NoSuchLimitSecMapException(long cmsLimitId, long cmsSecurityId, long cmsLimitProfileId, boolean isActual) {
		super((isActual ? " " : "Staging") + "Limit Security Linkage not found in the system; CMS Limit internal key ["
				+ cmsLimitId + "], CMS Collateral internal key [" + cmsSecurityId + "], CMS AA internal key ["
				+ cmsLimitProfileId + "]");
	}

	/**
	 * Default constructor to provide error when the Limit Sec Map is not found
	 * for the collateral (either actual or staging, determined by one of the
	 * argument).
	 * 
	 * @param losAANumber LOS AA Number
	 * @param cmsSecurityId CMS collateral internal key
	 * @param isActual to indicate whether this is from staging or actual table.
	 */
	public NoSuchLimitSecMapException(String losAANumber, long cmsSecurityId, boolean isActual) {
		super((isActual ? "Staging " : "") + "Limit Security Linkage not found in the system; LOS AA Number ["
				+ losAANumber + "], CMS Collateral key [" + cmsSecurityId + "]");
	}

	public String getErrorCode() {
		return NO_LIMIT_SEC_MAP_FOUND_ERROR_CODE;
	}
}
