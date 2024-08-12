/*
 * Created on Mar 17, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.sharesecurity.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public interface EBShareSecurity extends EJBObject {
	public IShareSecurity getValue() throws RemoteException, Exception;

	public void setValue(IShareSecurity sec) throws RemoteException, Exception;
}
