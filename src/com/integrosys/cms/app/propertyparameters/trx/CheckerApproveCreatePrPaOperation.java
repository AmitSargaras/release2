package com.integrosys.cms.app.propertyparameters.trx;

import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyparameters.bus.IPropertyParameters;
import com.integrosys.cms.app.propertyparameters.bus.PropertyParametersException;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Feb 1, 2007 Time: 10:05:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class CheckerApproveCreatePrPaOperation extends AbstractProPaTrxOperation {
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_PRPA;
	}

	/**
	 * Process the transaction 1. Create the actual data 2. Update the
	 * transaction record
	 * @param anITrxValue of ITrxValue type
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IPrPaTrxValue trxValue = getCCDocumentLocationTrxValue(anITrxValue);
		trxValue = createActualCCDocumentLocation(trxValue);
		trxValue = updateCCDocumentLocationTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Create the actual document item
	 * @param anITrxValue of ITrxValue type
	 * @return ICCDocumentLocationTrxValue - the document item trx value
	 * @throws TrxOperationException on errors
	 */
	private IPrPaTrxValue createActualCCDocumentLocation(IPrPaTrxValue anICCDocumentLocationTrxValue)
			throws TrxOperationException {
		try {
			IPropertyParameters colDocumentLocation = anICCDocumentLocationTrxValue.getStagingPrPa();
			IPropertyParameters actualColDocumentLocation = getBusDelegrate().createPropertyParameters(
					colDocumentLocation);
			anICCDocumentLocationTrxValue.setPrPa(actualColDocumentLocation);
			anICCDocumentLocationTrxValue.setReferenceID(String.valueOf(actualColDocumentLocation.getParameterId()));
			return anICCDocumentLocationTrxValue;
		}
		catch (PropertyParametersException ex) {
			throw new TrxOperationException(ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException("Exception in createActualCCDocumentLocation(): " + ex.toString());
		}
	}

}
