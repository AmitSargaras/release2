package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * Description: This exception represents a generic exception that occurs during
 * the execution of GeneralCharge module services.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/18 08:05:09 $ Tag: $Name: $
 */
public class GeneralChargeException extends OFAException {

	/*
	 * OFAException constructor - no message, and no exception to chain
	 * 
	 * @param nil
	 * 
	 * @return OFAException
	 */
	public GeneralChargeException() {
		super();
	}

	/*
	 * OFAException constructor - no previous exception to chain
	 * 
	 * @param String msg - message for this exception
	 * 
	 * @return OFAException
	 */
	public GeneralChargeException(String pMsg) {
		super(pMsg);
	} // OFAException()

	/*
	 * OFAException constructor - chain a previous exception
	 * 
	 * @param Throwable pEx - previous exception to chain
	 * 
	 * @return OFAException
	 */
	public GeneralChargeException(Throwable pEx) {
		super(pEx);

	} // OFAException()

	/*
	 * OFAException constructor - add a message, and chain a previous exception
	 * 
	 * @param String pMsg - message to add
	 * 
	 * @param Throwable pEx - previous exception to chain
	 * 
	 * @return OFAException
	 */
	public GeneralChargeException(String pMsg, Throwable pEx) {
		super(pMsg, pEx);
	}

} // OFAException

