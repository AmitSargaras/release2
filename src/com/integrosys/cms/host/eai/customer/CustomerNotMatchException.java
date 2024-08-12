package com.integrosys.cms.host.eai.customer;

/**
 * Exception to be raised whenever the CIF id between the system and the
 * incoming one is not matched.
 * @author Chong Jun Yong
 * 
 */
public class CustomerNotMatchException extends EAICustomerMessageException {

	private static final long serialVersionUID = 6077190862314768251L;

	private static final String CUST_NOT_MATCH_ERROR_CODE = "CUST_NOT_MATCH";

	/**
	 * Constructor to provide actual and expected CIF id
	 * @param actualCifId actual CIF id in the system
	 * @param expectedCifId expected CIF id in the incoming message
	 */
	public CustomerNotMatchException(String actualCifId, String expectedCifId) {
		super("Customer CIF not match; actual CIF in the system [" + actualCifId + "], expected CIF [" + expectedCifId
				+ "]");
	}

	public String getErrorCode() {
		return CUST_NOT_MATCH_ERROR_CODE;
	}

}
