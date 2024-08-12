/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/CMSPersistenceTrxManager.java,v 1.1 2003/06/26 07:45:37 kllee Exp $
 */
package com.integrosys.cms.app.transaction;

import com.integrosys.base.businfra.transaction.TransactionException;

/**
 * The transaction manager is the abstract class that implements logic to
 * facilitate chain of command within ITrxProcesses. The supporting
 * <code>com.integrosys.base.businfra.transaction.DefaultTrxProcess</code>
 * implements the TrxOperation procedures
 * 
 * 
 * @author Alfred Lee
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/26 07:45:37 $
 */
public abstract class CMSPersistenceTrxManager {
	/**
	 * Default Constructor
	 */
	public CMSPersistenceTrxManager() {
	}

	/**
	 * Method to create a transaction record
	 * 
	 * @param value is the ICMSTrxValue transaction object
	 * @return ICMSTrxValue
	 * @throws TransactionException, RemoteException on errors
	 */
	public abstract ICMSTrxValue createTransaction(ICMSTrxValue value) throws TransactionException;

	/**
	 * Method to update a transaction record
	 * 
	 * @param value is the ICMSTrxValue transaction object
	 * @return ICMSTrxValue
	 * @throws TransactionException, RemoteException on errors
	 */
	public abstract ICMSTrxValue updateTransaction(ICMSTrxValue value) throws TransactionException;

	/**
	 * Method to return transaction data given a Transaction ID Not implemented.
	 * This manager only implements the <code>getTrxProcessList</code> method.
	 * 
	 * @param trxID is the String value of the Transaction ID
	 * @return ICMSTrxValue
	 * @throws TransactionException if any other error occurs
	 */
	public abstract ICMSTrxValue getTransaction(String trxID) throws TransactionException;

	/**
	 * Get the transaction by reference ID and transaction type
	 * 
	 * @param referenceID - String
	 * @return ITrxValue - the obj containing the transaction info
	 * @throws TransactionException
	 */
	public abstract ICMSTrxValue getTrxByRefIDAndTrxType(String referenceID, String trxType)
			throws TransactionException;
}