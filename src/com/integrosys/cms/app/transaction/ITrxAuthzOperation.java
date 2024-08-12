/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/ITrxAuthzOperation.java,v 1.2 2003/06/12 11:58:56 hltan Exp $
 */
package com.integrosys.cms.app.transaction;

import com.integrosys.base.businfra.transaction.ITrxValue;

/**
 * This interface represents any unit of work that is responsible for providing
 * authorisation facility to a transaction operation.
 * 
 * @author Alfred Lee
 */
public interface ITrxAuthzOperation extends java.io.Serializable {
	/**
	 * Identifies if authorisation is successful.
	 * 
	 * @param value is the Transation object to be given.
	 * @return boolean true if authorisation is successful, and false otherwise.
	 * @throws TrxAuthzOperationException on errors.
	 */
	public boolean authorize(ITrxValue value) throws TrxAuthzOperationException;
}
