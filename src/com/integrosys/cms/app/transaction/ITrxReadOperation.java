/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/ITrxReadOperation.java,v 1.2 2003/06/12 11:58:56 hltan Exp $
 */
package com.integrosys.cms.app.transaction;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;

/**
 * This interface represents any unit of work that is responsible for providing
 * routing facility to a transaction operation.
 * 
 * @author Alfred Lee
 */
public interface ITrxReadOperation extends java.io.Serializable {
	/**
	 * This method is used to read a transaction object given a transaction ID
	 * 
	 * @param value is the ITrxValue object containing the parameters required
	 *        for retrieving a record, such as the transaction ID.
	 * @return ITrxValue containing the requested data.
	 * @throws TransactionException if any other errors occur.
	 */
	public ITrxValue getTransaction(ITrxValue value) throws TransactionException;
}
