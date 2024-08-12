package com.integrosys.cms.app.commoncode.trx;

import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.commoncode.bus.CommonCodeTypeException;
import com.integrosys.cms.app.commoncode.bus.ICommonCodeType;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class CheckerApproveCreateCommonCodeTypeOperation extends AbstractCommonCodeTypeTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveCreateCommonCodeTypeOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_COMMON_CODE_TYPE;
	}

	/**
	 * Process the transaction 1. Create the actual data 2. Update the staging
	 * data with the actual data 3. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ICommonCodeTypeTrxValue trxValue = getCommonCodeTypeTrxValue(anITrxValue);

		trxValue = createActualCommonCodeType(trxValue);
		trxValue = updateCommonCodeTypeTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Create the actual document item
	 * @param anICommonCodeTypeTrxValue - ICommonCodeTypeTrxValue
	 * @return ICommonCodeTypeTrxValue - the common code type trx value
	 * @throws TrxOperationException on errors
	 */
	private ICommonCodeTypeTrxValue createActualCommonCodeType(ICommonCodeTypeTrxValue anICommonCodeTypeTrxValue)
			throws TrxOperationException {
		try {
			ICommonCodeType commonCodeType = anICommonCodeTypeTrxValue.getStagingCommonCodeType();
			ICommonCodeType actualCommonCodeType = getSBCommonCodeTypeBusManager().createCommonCodeType(commonCodeType);
			anICommonCodeTypeTrxValue.setCommonCodeType(actualCommonCodeType);
			anICommonCodeTypeTrxValue.setReferenceID(String.valueOf(actualCommonCodeType.getCommonCategoryId()));
			return anICommonCodeTypeTrxValue;
		}
		catch (CommonCodeTypeException cex) {
			throw new TrxOperationException(cex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException("Exception in createActualCommonCodeType(): " + ex.toString());
		}
	}
}
