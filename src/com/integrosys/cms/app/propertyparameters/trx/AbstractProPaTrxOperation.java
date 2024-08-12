package com.integrosys.cms.app.propertyparameters.trx;

import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.propertyparameters.bus.IPropertyParameters;
import com.integrosys.cms.app.propertyparameters.bus.PropertyParametersBusDelegate;
import com.integrosys.cms.app.propertyparameters.bus.PropertyParametersException;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Feb 1, 2007 Time: 9:58:18 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractProPaTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

	protected IPrPaTrxValue prepareTrxValue(IPrPaTrxValue anICCDocumentLocationTrxValue) {
		if (anICCDocumentLocationTrxValue != null) {
			IPropertyParameters actual = anICCDocumentLocationTrxValue.getPrPa();
			IPropertyParameters staging = anICCDocumentLocationTrxValue.getStagingPrPa();
			if ((actual != null)
					&& (actual.getParameterId() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)) {
				anICCDocumentLocationTrxValue.setReferenceID(String.valueOf(actual.getParameterId()));
			}
			else {
				anICCDocumentLocationTrxValue.setReferenceID(null);
			}
			if ((staging != null)
					&& (staging.getParameterId() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)) {
				anICCDocumentLocationTrxValue.setStagingReferenceID(String.valueOf(staging.getParameterId()));
			}
			else {
				anICCDocumentLocationTrxValue.setStagingReferenceID(null);
			}
			return anICCDocumentLocationTrxValue;
		}
		return null;
	}

	protected IPrPaTrxValue updateCCDocumentLocationTransaction(IPrPaTrxValue anICCDocumentLocationTrxValue)
			throws TrxOperationException {
		try {
			anICCDocumentLocationTrxValue = prepareTrxValue(anICCDocumentLocationTrxValue);
			ICMSTrxValue tempValue = super.updateTransaction(anICCDocumentLocationTrxValue);
			OBPrPaTrxValue newValue = new OBPrPaTrxValue(tempValue);
			newValue.setPrPa(anICCDocumentLocationTrxValue.getPrPa());
			newValue.setStagingPrPa(anICCDocumentLocationTrxValue.getStagingPrPa());
			return newValue;
		}
		catch (TransactionException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}

	protected IPrPaTrxValue createStagingCCDocumentLocation(IPrPaTrxValue anICCDocumentLocationTrxValue)
			throws TrxOperationException {
		try {
			DefaultLogger.debug(this, "anICCDocumentLocationTrxValue.getStagingPrPa() is null ?"
					+ (anICCDocumentLocationTrxValue.getStagingPrPa() == null));
			IPropertyParameters colDocumentLocation = getBusDelegrate().createStgPropertyParameters(
					anICCDocumentLocationTrxValue.getStagingPrPa());
			anICCDocumentLocationTrxValue.setStagingPrPa(colDocumentLocation);
			anICCDocumentLocationTrxValue.setStagingReferenceID(String.valueOf(colDocumentLocation.getParameterId()));
			return anICCDocumentLocationTrxValue;
		}
		catch (PropertyParametersException ex) {
			throw new TrxOperationException(ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException(ex.toString());
		}
	}

	protected IPrPaTrxValue getCCDocumentLocationTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			DefaultLogger.debug(this, "anITrxValue is null? " + (anITrxValue == null));
			return (IPrPaTrxValue) anITrxValue;
		}
		catch (ClassCastException ex) {
			throw new TrxOperationException("The ITrxValue is not of type OBCCDocumentLocationTrxValue: "
					+ ex.toString());
		}
	}

	protected IPropertyParameters mergeCCDocumentLocation(IPropertyParameters anOriginal, IPropertyParameters aCopy)
			throws TrxOperationException {
		aCopy.setParameterId(anOriginal.getParameterId());
		aCopy.setVersionTime(anOriginal.getVersionTime());
		return aCopy;
	}

	protected ITrxResult prepareResult(IPrPaTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

	protected PropertyParametersBusDelegate getBusDelegrate() {
		return new PropertyParametersBusDelegate();
	}
}
