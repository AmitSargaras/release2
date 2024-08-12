package com.integrosys.cms.app.valuationAgency.bus;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * General exception class for the diary item package. This exception is thrown
 * for any errors during diary item processing
 * 
 * @author rajib.aich
 * @version $Revision: 1.2 $
 * @since $Date: 2004/05/17 02:39:04 $ Tag: $Name: $
 */

public class ValuationAgencyException extends OFAException {

	/**
	 * Constructor
	 * 
	 * @param msg
	 *            - the message string
	 */
	public ValuationAgencyException(String msg) {
		super(msg);
	}

	/**
	 * Constructor
	 * 
	 * @param msg
	 *            - message String
	 * @param t
	 *            - Throwable
	 */
	public ValuationAgencyException(String msg, Throwable t) {
		super(msg, t);
	}
}
