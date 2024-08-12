/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/CMSReadTrxManager.java,v 1.1 2003/06/26 07:45:37 kllee Exp $
 */
package com.integrosys.cms.app.transaction;

import com.integrosys.base.businfra.transaction.ITrxProcess;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TransactionManager;
import com.integrosys.base.businfra.transaction.TrxParameterException;

/**
 * class parallel to SBTrxManager to load the DefaultReadProcess
 * @author $Author: kllee $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/26 07:45:37 $ Tag: $Name: $
 */

public class CMSReadTrxManager extends TransactionManager {
	public CMSReadTrxManager() {
		super();
	}

	public ITrxProcess[] getTrxProcessList() {
		return (new ITrxProcess[] { new DefaultReadProcess() });
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