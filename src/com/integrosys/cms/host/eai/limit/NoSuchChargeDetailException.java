package com.integrosys.cms.host.eai.limit;

/**
 * Exception to be raised whenever the Charge Detail is not found in the system.
 * 
 * @author Chong Jun Yong
 * 
 */
public class NoSuchChargeDetailException extends EaiLimitProfileMessageException {

	private static final long serialVersionUID = 310396005657042895L;

	private static final String CHARGE_DETAIL_NOT_FOUND_ERROR_CODE = "CHARGE_NOT_FOUND";

	/**
	 * Default constructor to provide error that Charge detail is not found for
	 * the AA and Security.
	 * 
	 * @param losAANumber LOS AA number
	 * @param securityId Security Id (from the source)
	 */
	public NoSuchChargeDetailException(String losAANumber, String securityId) {
		super("Charge Detail not found in the system; LOS AA Number [" + losAANumber + "], Security Id [" + securityId
				+ "]");
	}

	/**
	 * Default constructor to provide error that Charge detail is not found for
	 * the AA and CMS Charge detail keys given.
	 * 
	 * @param cmsLimitProfileId CMS AA internal key
	 * @param cmsChargeDetailId CMS Charge Detail internal key
	 */
	public NoSuchChargeDetailException(long cmsLimitProfileId, long cmsChargeDetailId) {
		super("Charge Detail not found in the system; CMS AA Key [" + cmsLimitProfileId + "], CMS Charge Detail Key ["
				+ cmsChargeDetailId + "]");
	}

	public String getErrorCode() {
		return CHARGE_DETAIL_NOT_FOUND_ERROR_CODE;
	}

}
