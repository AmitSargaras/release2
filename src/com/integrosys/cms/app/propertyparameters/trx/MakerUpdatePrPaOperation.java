package com.integrosys.cms.app.propertyparameters.trx;

import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyparameters.bus.IPropertyParameters;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Feb 1, 2007 Time: 10:03:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class MakerUpdatePrPaOperation extends AbstractProPaTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerUpdatePrPaOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_UPDATE_PRPA;
	}

	/**
	 * Pre process. Prepares the transaction object for persistance Get the
	 * parent transaction ID to be appended as trx parent ref
	 * @param anITrxValue is of type ITrxValue
	 * @return ITrxValue
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException on
	 *         error
	 */
	public ITrxValue preProcess(ITrxValue anITrxValue) throws TrxOperationException {
		anITrxValue = super.preProcess(anITrxValue);
		IPrPaTrxValue trxValue = getCCDocumentLocationTrxValue(anITrxValue);
		IPropertyParameters staging = trxValue.getStagingPrPa();
		try {
			if (staging != null) {
				ICMSTrxValue parentTrx = null;

				if (staging.getParameterId() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
					parentTrx = getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(staging.getParameterId()),
							ICMSConstant.INSTANCE_AUTO_VAL_PARAM);
					trxValue.setTrxReferenceID(parentTrx.getTransactionID());
				}
				return trxValue;
			}
			return trxValue;
		}
		catch (TransactionException ex) {
			throw new TrxOperationException(ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException("Exception in preProcess: " + ex.toString());
		}
	}

	/**
	 * Process the transaction 1. Create the staging data 2. Update the
	 * transaction record
	 * @param anITrxValue of ITrxValue type
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IPrPaTrxValue trxValue = super.getCCDocumentLocationTrxValue(anITrxValue);
		trxValue = createStagingCCDocumentLocation(trxValue);
		trxValue = updateCCDocumentLocationTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}
