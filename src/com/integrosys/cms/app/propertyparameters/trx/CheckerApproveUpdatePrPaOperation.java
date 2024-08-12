package com.integrosys.cms.app.propertyparameters.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.propertyparameters.bus.IPropertyParameters;
import com.integrosys.cms.app.propertyparameters.bus.PropertyParametersException;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Feb 1, 2007 Time: 10:06:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class CheckerApproveUpdatePrPaOperation extends AbstractProPaTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveUpdatePrPaOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_PRPA;
	}

	/**
	 * Process the transaction 1. Update the actual data 2. Update the
	 * transaction record
	 * @param anITrxValue of ITrxValue type
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IPrPaTrxValue trxValue = getCCDocumentLocationTrxValue(anITrxValue);
		trxValue = updateActualCCDocumentLocation(trxValue);
		trxValue = updateCCDocumentLocationTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Post Processing after the trx operation 1. To reset the cc collaboration
	 * task trx status
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	// public ITrxResult postProcess(ITrxResult result) throws
	// TrxOperationException
	// {
	// result = super.postProcess(result);
	//
	// IPrPaTrxValue trxValue = (IPrPaTrxValue)result.getTrxValue();
	// IPropertyParameters actual = trxValue.getPrPa();
	// try
	// {
	// ICollaborationTaskProxyManager mgr =
	// CollaborationTaskProxyManagerFactory.getProxyManager();
	// mgr.systemUpdateCCCollaborationTask(actual.getLimitProfileID(),
	// actual.getDocLocationCategory(), actual.getCustomerID());
	// }
	// catch(CollaborationTaskException ex)
	// {
	// throw new TrxOperationException("Caught Exception in postProcess", ex);
	// }
	// return result;
	// }
	/**
	 * Update the actual document item
	 * @param anITrxValue of ITrxValue type
	 * @return ICCDocumentLocationTrxValue - the document item trx value
	 * @throws TrxOperationException on errors
	 */
	private IPrPaTrxValue updateActualCCDocumentLocation(IPrPaTrxValue anICCDocumentLocationTrxValue)
			throws TrxOperationException {
		try {
			IPropertyParameters staging = anICCDocumentLocationTrxValue.getStagingPrPa();
			IPropertyParameters actual = anICCDocumentLocationTrxValue.getPrPa();
			IPropertyParameters updActual = (IPropertyParameters) CommonUtil.deepClone(staging);
			updActual = mergeCCDocumentLocation(actual, updActual);
			IPropertyParameters actualCCDocumentLocation = getBusDelegrate().updatePropertyParameters(updActual);
			anICCDocumentLocationTrxValue.setPrPa(actualCCDocumentLocation);
			anICCDocumentLocationTrxValue.setReferenceID(String.valueOf(actualCCDocumentLocation.getParameterId()));
			return anICCDocumentLocationTrxValue;
		}
		catch (PropertyParametersException ex) {
			throw new TrxOperationException(ex);
		}
		catch (ConcurrentUpdateException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("Exception in updateActualCCDocumentLocation(): " + ex.toString());
		}
	}
}
