/*
 * Created on 2007-2-16
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.manualinput.limit.proxy;

import javax.ejb.EJBHome;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public interface SBMILmtProxyHome extends EJBHome {
	public SBMILmtProxy create() throws javax.ejb.CreateException, java.rmi.RemoteException;
}
