package com.integrosys.cms.app.commoncode.proxy;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public interface SBCommonCodeTypeProxyManagerHome extends EJBHome {
	public SBCommonCodeTypeProxyManager create() throws CreateException, RemoteException;
}
