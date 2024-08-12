package com.integrosys.cms.host.eai.limit;


/**
 * Exception to be raised when the Limit info provided doesn't belong to the AA
 * expected (normally the one in the message).
 * 
 * @author Chong Jun Yong
 * 
 */
public class LimitProfileMismatchException extends EaiLimitProfileMessageException {

	private static final long serialVersionUID = 5690226478629798859L;

	private static final String AA_MISMATCH_ERROR_CODE = "AA_MISMATCH";

	/**
	 * Default constructor to provide CMS Limit internal key, expected and
	 * actual AA number
	 * 
	 * @param cmsLimitId CMS Limit internal key
	 * @param expectedAANumber Expected AA Number (the one in the message)
	 * @param actualAANumber Actual AA Number (the one in the persistent
	 *        storage)
	 */
	public LimitProfileMismatchException(long cmsLimitId, String expectedAANumber, String actualAANumber) {
		super("Limit with CMS internal key [" + cmsLimitId + "] is belong to AA [" + actualAANumber
				+ "], but not AA (in the message) [" + expectedAANumber + "]");
	}

	/**
	 * Default constructor to provide LOS Limit key, expected and actual AA
	 * number
	 * 
	 * @param limitId LOS Limit Id
	 * @param expectedAANumber Expected AA Number (the one in the message)
	 * @param actualAANumber Actual AA Number (the one in the persistent
	 *        storage)
	 */
	public LimitProfileMismatchException(String limitId, String expectedAANumber, String actualAANumber) {
		super("Limit with LOS key [" + limitId + "] is belong to AA [" + actualAANumber
				+ "], but not AA (in the message) [" + expectedAANumber + "]");
	}

	public String getErrorCode() {
		return AA_MISMATCH_ERROR_CODE;
	}
}
