/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/bus/DDNException.java,v 1.1 2003/08/13 11:27:25 hltan Exp $
 */
package com.integrosys.cms.app.securityenvelope.bus;

//ofa
import com.integrosys.base.techinfra.exception.OFAException;

/**
 * 
 *
 * @author  $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since   $Date: 2003/08/13 11:27:25 $
 * Tag:     $Name:  $
 */

/**
 * Title: CLIMS 
 * Description: General exception class for the ddn package. It should be thrown when there is any exception encountered in the
 * ddn process that requires no special handling.
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Erene Wong 
 * Date: Jan 25, 2010
 */

public class SecEnvelopeException extends OFAException
{
	/**
	* Default Constructor
	*/
	public SecEnvelopeException()
	{
		super();
	}

	/**
	* Constructor
	* @param msg - the message string
	*/
	public SecEnvelopeException(String msg)
	{
		super(msg);
	}

	/**
	* Constructor
	* @param t - Throwable
	*/
	public SecEnvelopeException(Throwable t)
	{
		super(t);
	}

	/**
	* Constructor
	* @param msg - message String
	* @param t - Throwable
	*/
	public SecEnvelopeException(String msg, Throwable t)
	{
		super(msg, t);
	}
}
