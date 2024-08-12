/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/LimitException.java,v 1.1 2003/07/07 04:57:54 kllee Exp $
 */
package com.integrosys.cms.app.limitbooking.bus;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * Description: This exception represents a generic exception that
 * occurs during the execution of Limit Booking module services.
 *
* @author  $Author$
* @version $Revision$
* @since   $Date$
* Tag:     $Name$
*/
public class LimitBookingException extends OFAException {

	/*
      OFAException constructor - no message, and no
      exception to chain
      @param nil
      @return OFAException
    */
    public LimitBookingException() {
        super();
    }

    /*
      OFAException constructor - no previous exception to chain
      @param String msg - message for this exception
      @return OFAException
    */
    public LimitBookingException(String pMsg) {
	  super(pMsg);
    } // OFAException()

    /*
      OFAException constructor - chain a previous exception
      @param Throwable pEx - previous exception to chain
      @return OFAException
    */
    public LimitBookingException(Throwable pEx) {
	    super(pEx);

    } // OFAException()


    /*
      OFAException constructor - add a message, and chain a previous exception
      @param String pMsg - message to add
      @param Throwable pEx - previous exception to chain
      @return OFAException
    */
    public LimitBookingException(String pMsg,Throwable pEx) {
	  super(pMsg, pEx);
	 }



} // OFAException

