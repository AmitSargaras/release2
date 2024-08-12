/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/ReadRecurrentCheckListIDOperation.java,v 1.2 2003/08/07 02:37:31 hltan Exp $
 */
package com.integrosys.cms.app.recurrent.annexure.trx;

//ofa

import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This operation is responsible for reading a recurrent checklist trx based on
 * a checklist ID
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/07 02:37:31 $ Tag: $Name: $
 */
public class ReadAnnexureIDOperation extends AbstractAnnexureReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadAnnexureIDOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_ANNEXURE_CHECKLIST_ID;
	}

	public ICMSTrxValue doInTrxManager(ICMSTrxValue trxValue) throws TransactionException {
		try {
			return (ICMSTrxValue) getTrxManager().getTrxByRefIDAndTrxType(trxValue.getReferenceID(),
					trxValue.getTransactionType());
		}
		catch (RemoteException e) {
			logger.error("remote exception encountered [" + e + "]", e);
			throw new TransactionException("remote exception encountered [" + e + "]", e);
		}
	}
}