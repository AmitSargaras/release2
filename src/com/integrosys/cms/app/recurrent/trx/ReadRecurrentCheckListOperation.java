/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/ReadRecurrentCheckListOperation.java,v 1.1 2003/07/28 02:19:24 hltan Exp $
 */
package com.integrosys.cms.app.recurrent.trx;

//ofa

import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This operation is responsible for the creation of a checklist doc transaction
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/28 02:19:24 $ Tag: $Name: $
 */
public class ReadRecurrentCheckListOperation extends AbstractRecurrentCheckListReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadRecurrentCheckListOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_RECURRENT_CHECKLIST;
	}

	public ICMSTrxValue doInTrxManager(ICMSTrxValue trxValue) throws TransactionException {
		try {
			return (ICMSTrxValue) getTrxManager().getTransaction(trxValue.getTransactionID());
		}
		catch (RemoteException e) {
			logger.error("remote exception encountered [" + e + "]", e);
			throw new TransactionException("remote exception encountered [" + e + "]", e);
		}
	}
}