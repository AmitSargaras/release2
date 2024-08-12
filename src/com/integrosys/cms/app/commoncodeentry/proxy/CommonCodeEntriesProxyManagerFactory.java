/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * CommonCodeEntriesManagerFactory.java
 *
 * Created on February 6, 2007, 11:10 AM
 *
 * Purpose:  Factory class that provides the concrete ICommonCodeEntriesProxy interface implemented class
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.app.commoncodeentry.proxy;

/**
 * 
 * @author Eric
 */
public class CommonCodeEntriesProxyManagerFactory {
	/**
	 * 
	 * @return ICommonCodeEntriesProxy - the object which implements the
	 *         inteface
	 */
	public static ICommonCodeEntriesProxy getICommonCodeEntriesProxy() {
		return new CommonCodeEntriesProxyManagerImpl(); // return an new
														// instance
	}
}
