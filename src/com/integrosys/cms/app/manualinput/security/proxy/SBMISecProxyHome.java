/*
 * Created on Apr 3, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.manualinput.security.proxy;

import javax.ejb.EJBHome;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public interface SBMISecProxyHome extends EJBHome {
	public SBMISecProxy create() throws javax.ejb.CreateException, java.rmi.RemoteException;
}
