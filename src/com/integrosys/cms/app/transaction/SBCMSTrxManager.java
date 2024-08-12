/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/SBCMSTrxManager.java,v 1.16 2005/04/30 08:30:57 lyng Exp $
 */
package com.integrosys.cms.app.transaction;

import java.rmi.RemoteException;
import java.util.Collection;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;

/**
 * This is the remote interface to the SBCMSTrxManager session bean
 * 
 * @author Alfred Lee
 */
public interface SBCMSTrxManager extends javax.ejb.EJBObject {
	/**
	 * Method to create a transaction record
	 * 
	 * @param value is the ICMSTrxValue transaction object
	 * @return ICMSTrxValue
	 * @throws TransactionException
	 * @throws RemoteException on errors
	 */
	public ICMSTrxValue createTransaction(ICMSTrxValue value) throws TransactionException, RemoteException;

	/**
	 * Method to update a transaction record
	 * 
	 * @param value is the ICMSTrxValue transaction object
	 * @return ICMSTrxValue
	 * @throws TransactionException, RemoteException on errors
	 */
	public ICMSTrxValue updateTransaction(ICMSTrxValue value) throws TransactionException, RemoteException;

	/**
	 * Get the transaction by reference ID and transaction type
	 * 
	 * @param referenceID - String
	 * @return ITrxValue - the obj containing the transaction info
	 * @throws TransactionException, RemoteException on errors
	 */
	public ICMSTrxValue getTrxByRefIDAndTrxType(String referenceID, String trxType) throws TransactionException,
			RemoteException;

	/**
	 * Get the transaction by stage reference ID and transaction type
	 * 
	 * @param stageReferenceID - String
	 * @param trxType - String
	 * @return ITrxValue - the obj containing the transaction info
	 * @throws TransactionException, RemoteException on errors
	 */
	public ICMSTrxValue getTrxByStageRefIDAndTrxType(String stageReferenceID, String trxType)
			throws TransactionException, RemoteException;

	/**
	 * Find the transaction by reference ID and transaction type. Return
	 * transaction info if the transaction is found, otherwise return null.
	 * 
	 * @param referenceID - String
	 * @param trxType - String
	 * @return ITrxValue - the obj containing the transaction info if found,
	 *         otherwise null
	 * @throws TransactionException, RemoteException on errors
	 */
	public ICMSTrxValue findTrxByRefIDAndTrxType(String referenceID, String trxType) throws TransactionException,
			RemoteException;

	/**
	 * Method to return transaction data given a Transaction ID Not implemented.
	 * This manager only implements the <code>getTrxProcessList</code> method.
	 * 
	 * @param trxID is the String value of the Transaction ID
	 * @return ICMSTrxValue
	 * @throws TransactionException, RemoteException if any other error occurs
	 */
	public ICMSTrxValue getTransaction(String trxID) throws TransactionException, RemoteException;

	/*
	 * searchNextRouteList added since 20/05/2004
	 */
	public Collection searchNextRouteList(CMSTrxSearchCriteria criteria) throws SearchDAOException, RemoteException;

	/*
	 * authLevel() added since 31/08/2004
	 */
	public int authLevel(CMSTrxSearchCriteria criteria) throws RemoteException;

	public SearchResult searchTransactions(CMSTrxSearchCriteria criteria) throws SearchDAOException, RemoteException;

	public SearchResult searchWorkflowTransactions(CMSTrxSearchCriteria criteria) throws SearchDAOException,
			RemoteException;

	public SearchResult searchPendingCases(CMSTrxSearchCriteria criteria) throws SearchDAOException, RemoteException;

	public int getTransactionCount(CMSTrxSearchCriteria criteria) throws SearchDAOException, RemoteException;

	public int getAllTransactionCount(CMSTrxSearchCriteria criteria) throws SearchDAOException, RemoteException;

	public int getWorkflowTrxCount(CMSTrxSearchCriteria criteria) throws SearchDAOException, RemoteException;

	public Collection getLPTodoList() throws SearchDAOException, RemoteException;

	/**
	 * Get all transactions given a parent transaction ID
	 * 
	 * @param parentTrxID is of type String
	 * @param trxType the String value of the transaction type
	 * @return ICMSTrxValue[] or null if no such data is found
	 * @throws TransactionException, RemoteException on error
	 */
	public ICMSTrxValue[] getTrxByParentTrxID(String parentTrxID, String trxType) throws TransactionException, RemoteException;

    /**
	  * Get all transactions given a parent transaction ID and transaction type
	  *
	  * @param parentTrxID is of type String
	  * @param trxType the String value of the transaction type
	  * @return ICMSTrxValue[] or null if no such data is found
	  * @throws TransactionException, RemoteException on error
	  */
    //public ICMSTrxValue[] getTrxByParentTrxIDAndTrxType(String parentTrxID, String trxType) throws TransactionException, RemoteException;

	/**
	 * Get a working transaction which the status <> CLOSED by transaction type, if not exists return null
	 *
	 * @param trxType us the String value of the transaction type
	 * @return ICMSTrxValue or null if no such data is found
	 * @throws TransactionException, RemoteException
	 */
	public ICMSTrxValue getWorkingTrxByTrxType(String trxType) throws TransactionException, RemoteException;

	/**
	 * Get list of working transaction which the status <> CLOSED by transaction type, if not exists return null
	 *
	 * @param trxType us the String value of the transaction type
	 * @return ICMSTrxValue or null if no such data is found
	 * @throws TransactionException, RemoteException
	 */
	public ICMSTrxValue[] getWorkingTrxListByTrxType(String trxType) throws TransactionException, RemoteException;
}