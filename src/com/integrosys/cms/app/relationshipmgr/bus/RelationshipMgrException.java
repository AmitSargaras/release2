package com.integrosys.cms.app.relationshipmgr.bus;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * General exception class for the Relationship Manager package. This exception is thrown
 * for any errors during Relationship Manager processing
 * 
 * @author $Author: Dattatray Thorat 
 * @version 1.0
 * @since $Date: 31/03/2011 02:37:00 
 */

public class RelationshipMgrException extends OFAException{
	
	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public RelationshipMgrException(String msg){
		super(msg);
	}
	
	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public RelationshipMgrException(String msg, Throwable t) {
		super(msg, t);
	}


}
