package com.integrosys.cms.app.user.proxy;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Home interface to the SBStdUserProxy session bean
 * 
 * @author $Author: dli $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/08/08 08:27:12 $ Tag: $Name: $
 */
public interface SBStdUserProxyHome extends EJBHome {
	/**
	 * Default Create method
	 * 
	 * @throws CreateException on error creating the ejb object
	 * @throws RemoteException on error during remote method call
	 */
	public SBStdUserProxy create() throws CreateException, RemoteException;
}
