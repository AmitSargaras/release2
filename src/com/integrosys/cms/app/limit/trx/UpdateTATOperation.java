/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/trx/UpdateTATOperation.java,v 1.1 2003/08/06 06:22:21 phtan Exp $
 */
package com.integrosys.cms.app.limit.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows the update of TAT
 * 
 * @author $Author: phtan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/06 06:22:21 $ Tag: $Name: $
 */
public class UpdateTATOperation extends AbstractLimitProfileTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public UpdateTATOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_UPDATE_TAT;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. Update Staging Record 2. Update Actual Record 3. Update Transaction
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxResult
	 * @throws TrxOperationException on error
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			ILimitProfileTrxValue trxValue = super.getLimitProfileTrxValue(value);

			trxValue = super.updateStagingLimitProfile(trxValue);
			trxValue = super.updateActualLimitProfile(trxValue);
			trxValue = super.updateTransaction(trxValue);

			return super.prepareResult(trxValue);
		}
		catch (TrxOperationException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TrxOperationException("Caught Exception!", e);
		}
	}
}