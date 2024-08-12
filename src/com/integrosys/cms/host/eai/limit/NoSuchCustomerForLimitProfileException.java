package com.integrosys.cms.host.eai.limit;

/**
 * Exception to be raised when the dependency check on the customer info using
 * the customer info in AA / Limit Profile message failed
 * 
 * @author Chong Jun Yong
 * 
 */
public class NoSuchCustomerForLimitProfileException extends EaiLimitProfileMessageException {
	private static final long serialVersionUID = 8631520303362867448L;

	private static final String NO_CUST_FOUND_FOR_AA_ERROR_CODE = "NO_CUST_FOUND";

	private String cifId;

	private String cifSource;

	/**
	 * Default constructor to provide, cif number, cif source, host AA number,
	 * LOS AA number
	 * 
	 * @param cifId the cif number, customer id
	 * @param cifSource the source of the customer info coming
	 * @param hostAANumber the host AA number, might be the same for same
	 *        customer from same place(center / branch)
	 * @param losAANumber the LOS AA number
	 */
	public NoSuchCustomerForLimitProfileException(String cifId, String cifSource, String hostAANumber,
			String losAANumber) {
		super("Customer not found in the system for the AA; Host AA Number [" + hostAANumber + "], LOS AA Number ["
				+ losAANumber + "], for CIF [" + cifId + "], CIF Source [" + cifSource + "]");
		this.cifId = cifId;
		this.cifSource = cifSource;
	}

	/**
	 * Default constructor to provide, cif number, cif source, LOS AA number
	 * 
	 * @param cifId the cif number, customer id
	 * @param cifSource the source of the customer info coming
	 * @param losAANumber the LOS AA number
	 */
	public NoSuchCustomerForLimitProfileException(String cifId, String cifSource, String losAANumber) {
		super("Customer not found in the system for the AA; LOS AA Number [" + losAANumber + "], for CIF [" + cifId
				+ "], CIF Source [" + cifSource + "]");
		this.cifId = cifId;
		this.cifSource = cifSource;
	}

	public String getCifId() {
		return cifId;
	}

	public String getCifSource() {
		return cifSource;
	}

	/**
	 * To append more message after detailed message has given, such as action
	 * to be taken if encounter this exception
	 */
	public String getMessage() {
		StringBuffer buf = new StringBuffer(super.getMessage());
		buf.append("; Please check whether Customer, CIF [").append(cifId).append("], ");
		buf.append("CIF Source [").append(cifSource).append("] ");
		buf.append("has been published successfully into CMS.");

		return buf.toString();
	}

	public String getErrorCode() {
		return NO_CUST_FOUND_FOR_AA_ERROR_CODE;
	}
}
