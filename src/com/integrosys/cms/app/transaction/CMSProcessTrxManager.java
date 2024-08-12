/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/CMSProcessTrxManager.java,v 1.2 2003/08/07 12:50:12 sathish Exp $
 */
package com.integrosys.cms.app.transaction;

import com.integrosys.base.businfra.transaction.DefaultTrxProcess;
import com.integrosys.base.businfra.transaction.ITrxProcess;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TransactionManager;
import com.integrosys.base.businfra.transaction.TrxParameterException;

/**
 * The transaction manager is the abstract class that implements logic to
 * facilitate chain of command within ITrxProcesses. The supporting
 * <code>com.integrosys.base.businfra.transaction.DefaultTrxProcess</code>
 * implements the TrxOperation procedures
 * 
 * 
 * @author Alfred Lee
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/07 12:50:12 $
 */
public class CMSProcessTrxManager extends TransactionManager {

	/**
	 * Default Constructor
	 */
	public CMSProcessTrxManager() {
	}

	/**
	 * Return a list of ITrxProcess used to drive the transaction engine
	 * processing logic.
	 * 
	 * @return ITrxProcess[]
	 */
	public ITrxProcess[] getTrxProcessList() {
		return (new ITrxProcess[] { new CMSTrxAuthzProcess(), new DefaultTrxProcess() });
	}

	/**
	 * Method to return transaction data given a Transaction ID Not implemented.
	 * This manager only implements the <code>getTrxProcessList</code> method.
	 * 
	 * @param trxID is the String value of the Transaction ID
	 * @throws TrxParameterException if the input parameters are incorrect or
	 *         contain errors
	 * @throws TransactionException if any other error occurs
	 */
	public ITrxValue getTransaction(String trxID) throws TrxParameterException, TransactionException {
		throw new TransactionException("This method is not implemented!");
	}
}