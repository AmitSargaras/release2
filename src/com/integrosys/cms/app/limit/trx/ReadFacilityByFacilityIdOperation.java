package com.integrosys.cms.app.limit.trx;

import java.rmi.RemoteException;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Read facility transaction using facility master primary key, which in turn
 * become the reference id to retrieve the transaction value together with the
 * transaction type.
 * 
 * @author Chong Jun Yong
 * @since 03.09.2008
 * 
 */
public class ReadFacilityByFacilityIdOperation extends AbstractReadFacilityOperation {

	protected ICMSTrxValue doInTrxManager(ICMSTrxValue trxValue) throws TransactionException {
		if (StringUtils.isBlank(trxValue.getInstanceName())) {
			throw new IllegalStateException(
					"instance name must not be blank when using it to retrieve transaction value");
		}

		if (!StringUtils.isBlank(trxValue.getReferenceID())) {
			try {
				return getTrxManager().findTrxByRefIDAndTrxType(trxValue.getReferenceID(), trxValue.getInstanceName());
			}
			catch (RemoteException e) {
				throw new TrxOperationException("failed to retrieve trx value using ref id on remote interface", e
						.getCause());
			}
		}

		if (!StringUtils.isBlank(trxValue.getStagingReferenceID())) {
			try {
				return getTrxManager().getTrxByStageRefIDAndTrxType(trxValue.getStagingReferenceID(),
						trxValue.getInstanceName());
			}
			catch (RemoteException e) {
				throw new TrxOperationException(
						"failed to retrieve trx value using staging ref id on remote interface", e.getCause());
			}
		}

		throw new TrxOperationException("reference id and staging reference of supplied trx value is empty");
	}

	public String getOperationName() {
		return FacilityReadController.ACTION_READ_TRX_BY_FAC_MASTER_ID;
	}

}
