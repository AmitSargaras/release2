/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * SBCommonCodeEntriesMangerHome.java
 *
 * Created on February 6, 2007, 11:42 AM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.app.commoncodeentry.proxy;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * 
 * @author Eric
 */
public interface SBCommonCodeEntriesProxyManagerHome extends EJBHome {
	public SBCommonCodeEntriesProxyManager create() throws CreateException, RemoteException;
}
