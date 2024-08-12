package com.integrosys.cms.app.limit.proxy;

import java.rmi.RemoteException;

import javax.ejb.CreateException;

/**
 * Home interface to the SBLimitProxy session bean
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/09 08:59:25 $ Tag: $Name: $
 */
public interface SBLimitProxyHome extends javax.ejb.EJBHome {
	/**
	 * Default Create method
	 * 
	 * @throws CreateException on error
	 * @throws RemoteException
	 */
	public SBLimitProxy create() throws javax.ejb.CreateException, java.rmi.RemoteException;
}