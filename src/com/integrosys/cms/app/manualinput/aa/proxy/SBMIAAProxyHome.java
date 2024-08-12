/*
 * Created on Apr 10, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.manualinput.aa.proxy;

import javax.ejb.EJBHome;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public interface SBMIAAProxyHome extends EJBHome {
	public SBMIAAProxy create() throws javax.ejb.CreateException, java.rmi.RemoteException;
}
