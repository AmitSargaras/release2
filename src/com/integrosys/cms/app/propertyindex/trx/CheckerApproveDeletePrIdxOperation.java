package com.integrosys.cms.app.propertyindex.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyindex.bus.IPropertyIdx;
import com.integrosys.cms.app.propertyindex.bus.PropertyIdxException;

/**
 * Title: CLIMS Description: Copyright: Integro Technologies Sdn Bhd Author:
 * Andy Wong Date: Jan 18, 2008
 */

public class CheckerApproveDeletePrIdxOperation extends AbstractPropertyIdxTrxOperation {

	private static final long serialVersionUID = 1L;

	/**
	 * Default Constructor
	 */
	public CheckerApproveDeletePrIdxOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_DELETE_PRIDX;
	}

	/**
	 * Process the transaction 1. Update the actual data 2. Update the
	 * transaction record
	 * 
	 * @param anITrxValue of ITrxValue type
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IPropertyIdxTrxValue trxValue = getPropertyIdxTrxValue(anITrxValue);
		trxValue = updateActualPropertyIdx(trxValue);
		trxValue = updatePropertyIdxTrx(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual document item
	 * 
	 * @param anITrxValue of ITrxValue type
	 * @return ICCPropertyIdxTrxValue - the document item trx value
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException on
	 *         errors
	 */
	private IPropertyIdxTrxValue updateActualPropertyIdx(IPropertyIdxTrxValue anICCPropertyIdxTrxValue)
			throws TrxOperationException {
		try {
			IPropertyIdx staging = anICCPropertyIdxTrxValue.getStagingPrIdx();
			IPropertyIdx actual = anICCPropertyIdxTrxValue.getPrIdx();

			IPropertyIdx updatedPropertyIdx = getPropertyIdxBusManager().updateToWorkingCopy(actual, staging);
			anICCPropertyIdxTrxValue.setPrIdx(updatedPropertyIdx);

			return anICCPropertyIdxTrxValue;
		}
		catch (PropertyIdxException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("Exception in updateActualPropertyIdx(): " + ex.toString());
		}
	}
}
