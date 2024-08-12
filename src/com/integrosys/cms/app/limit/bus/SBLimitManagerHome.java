package com.integrosys.cms.app.limit.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;

/**
 * Home interface to the SBLimitManager session bean
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/07 06:53:05 $ Tag: $Name: $
 */
public interface SBLimitManagerHome extends javax.ejb.EJBHome {
	/**
	 * Default Create method
	 * 
	 * @throws CreateException on error
	 * @throws RemoteException
	 */
	public SBLimitManager create() throws javax.ejb.CreateException, java.rmi.RemoteException;
}