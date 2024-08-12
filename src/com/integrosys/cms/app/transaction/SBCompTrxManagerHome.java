/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/SBCompTrxManagerHome.java,v 1.1 2003/07/23 12:39:08 kllee Exp $
 */
package com.integrosys.cms.app.transaction;

import java.rmi.RemoteException;

import javax.ejb.CreateException;

import com.integrosys.base.businfra.transaction.SBTrxPersistenceManager;

/**
 * Home interface to the SBCompTrxManager session bean
 * 
 * @author Alfred Lee
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/23 12:39:08 $
 */
public interface SBCompTrxManagerHome extends javax.ejb.EJBHome {
	/**
	 * Default Create method
	 * 
	 * @throws CreateException on error
	 * @throws RemoteException
	 */
	public SBTrxPersistenceManager create() throws javax.ejb.CreateException, java.rmi.RemoteException;
}