package com.integrosys.cms.app.customer.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;

/**
 * Home interface to the SBCustomerManager session bean
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/23 10:07:44 $ Tag: $Name: $
 */
public interface SBCustomerManagerHome extends javax.ejb.EJBHome {
	/**
	 * Default Create method
	 * 
	 * @throws CreateException on error
	 * @throws RemoteException
	 */
	public SBCustomerManager create() throws javax.ejb.CreateException, java.rmi.RemoteException;
}