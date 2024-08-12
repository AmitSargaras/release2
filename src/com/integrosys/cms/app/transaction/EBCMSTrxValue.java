/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/EBCMSTrxValue.java,v 1.2 2003/06/12 11:58:56 hltan Exp $
 */
package com.integrosys.cms.app.transaction;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * This is the remote interface to the EBCMSTrxValue entity bean
 * 
 * @author Alfred Lee
 */
public interface EBCMSTrxValue extends EJBObject {
	/**
	 * Return a transaction object.
	 * 
	 * @return OBCMSTrxValue
	 * @throws TransactionException on error
	 * @throws RemoteException
	 */
	public OBCMSTrxValue getTransaction() throws TransactionException, RemoteException;

	/**
	 * Sets the transaction object.
	 * 
	 * @param value is the ICMSTrxValue object
	 * @throws TransactionException if any transaction related exceptions occur
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 * @throws RemoteException
	 */
	public void setTransaction(ICMSTrxValue value) throws TransactionException, ConcurrentUpdateException,
			RemoteException;
}