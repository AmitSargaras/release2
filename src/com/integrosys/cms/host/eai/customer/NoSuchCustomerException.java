package com.integrosys.cms.host.eai.customer;

import com.integrosys.cms.host.eai.customer.bus.CustomerIdInfo;

/**
 * Exception to be raised when there is no customer found in the system.
 * 
 * @author Thurein
 * @author Chong Jun Yong
 * @version 1.0
 * @since 13-Dec-2008
 */
public class NoSuchCustomerException extends EAICustomerMessageException {

	private static final long serialVersionUID = -5487506759422398095L;

	private static final String CUSTOMER_NOT_FOUND_ERROR_CODE = "CUST_NOT_FOUND";

	/**
	 * Default constructor to provide cif number and source of the customer
	 * 
	 * @param cifNo cif number, the unique identifier of the customer
	 * @param source source of the customer
	 */
	public NoSuchCustomerException(String cifNo, String source) {
		super("Customer not found in the system; CIF [" + cifNo + "], CIF Source [" + source + "]");
	}

	/**
	 * Default constructor to provide cif number of the customer
	 * 
	 * @param cifNo cif number, the unique identifier of the customer
	 */
	public NoSuchCustomerException(String cifNo) {
		super("Customer not found in the system; CIF [" + cifNo + "]");
	}

	/**
	 * Default constructor to provide customer ID info. The ID info might be
	 * used to search the customer, and no customer match the ID info criteria.
	 * 
	 * @param idInfo customer id info
	 */
	public NoSuchCustomerException(CustomerIdInfo idInfo) {
		super("No customer in the system match the ID info provided [" + idInfo + "]");
	}

	public String getErrorCode() {
		return CUSTOMER_NOT_FOUND_ERROR_CODE;
	}

}
