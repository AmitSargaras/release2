/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/SBCMSTrxManagerHome.java,v 1.2 2003/06/12 11:58:56 hltan Exp $
 */
package com.integrosys.cms.app.transaction;

import java.rmi.RemoteException;

import javax.ejb.CreateException;

/**
 * Home interface to the SBCMSTrxManager session bean
 * 
 * @author Alfred Lee
 * @version $Revision: 1.2 $
 * @since $Date: 2003/06/12 11:58:56 $
 */
public interface SBCMSTrxManagerHome extends javax.ejb.EJBHome {
	/**
	 * Default Create method
	 * 
	 * @throws CreateException on error
	 * @throws RemoteException
	 */
	public SBCMSTrxManager create() throws javax.ejb.CreateException, java.rmi.RemoteException;
}