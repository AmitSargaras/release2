/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/ITrxRouteOperation.java,v 1.4 2003/07/23 07:37:20 hltan Exp $
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
public interface ITrxRouteOperation extends java.io.Serializable {
	/**
	 * Sets the next route requirements into the ITrxValue.
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxValue containing the required routing information for next
	 *         user
	 * @throws TransactionException on error
	 */
	public ITrxValue getNextRoute(ITrxValue value) throws TransactionException;
}
