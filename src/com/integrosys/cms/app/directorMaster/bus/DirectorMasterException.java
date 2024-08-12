package com.integrosys.cms.app.directorMaster.bus;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * Description:
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-04 15:13:16 +0800 (Tue, 03 May 2011) $
 */

public class DirectorMasterException extends OFAException {

	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public DirectorMasterException(String msg) {
		super(msg);
	}

	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public DirectorMasterException(String msg, Throwable t) {
		super(msg, t);
	}
}
