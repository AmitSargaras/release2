/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/TrxAuthzOperationException.java,v 1.2 2003/06/12 11:58:56 hltan Exp $
 */
package com.integrosys.cms.app.transaction;

import com.integrosys.base.businfra.transaction.TrxOperationException;

/**
 * This exception is thrown when an exception occured during the execution of an
 * ITrxAuthzOperation
 * 
 * @author Alfred Lee
 */
public class TrxAuthzOperationException extends TrxOperationException {
	public TrxAuthzOperationException() {
		super();
	}

	public TrxAuthzOperationException(String msg) {
		super(msg);
	}

	public TrxAuthzOperationException(Throwable t) {
		super(t);
	}

	public TrxAuthzOperationException(String msg, Throwable t) {
		super(msg, t);
	}
}
