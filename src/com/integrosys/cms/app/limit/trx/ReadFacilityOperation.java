package com.integrosys.cms.app.limit.trx;

import java.rmi.RemoteException;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Transaction operation to read facility
 * 
 * @author Chong Jun Yong
 * @since 03.09.2008
 */
public class ReadFacilityOperation extends AbstractReadFacilityOperation {

	private static final long serialVersionUID = -7343782897052453588L;

	public String getOperationName() {
		return FacilityReadController.ACTION_READ_TRX;
	}

	protected ICMSTrxValue doInTrxManager(ICMSTrxValue trxValue) throws TransactionException {
		if (StringUtils.isBlank(trxValue.getTransactionID())) {
			throw new IllegalStateException(
					"transaction id must not be blank when using it to retrieve transaction value");
		}

		try {
			return getTrxManager().getTransaction(trxValue.getTransactionID());
		}
		catch (RemoteException ex) {
			throw new TransactionException("failed to to retrieve transaction using transaction id ["
					+ trxValue.getTransactionID() + "], throwing root cause", ex.getCause());
		}
	}

}
