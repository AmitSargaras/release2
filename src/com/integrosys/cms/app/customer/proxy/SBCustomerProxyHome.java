package com.integrosys.cms.app.customer.proxy;

import java.rmi.RemoteException;

import javax.ejb.CreateException;

/**
 * Home interface to the SBCustomerProxy session bean
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/03 08:02:10 $ Tag: $Name: $
 */
public interface SBCustomerProxyHome extends javax.ejb.EJBHome {
	/**
	 * Default Create method
	 * 
	 * @throws CreateException on error
	 * @throws RemoteException
	 */
	public SBCustomerProxy create() throws javax.ejb.CreateException, java.rmi.RemoteException;
}